package net.got.worldgen;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

/**
 * Computes a distance-to-edge field across the biomemap for mountain biomes,
 * then uses that distance to smoothly ramp terrain height up toward the
 * interior of each mountain blob, the way real mountain flanks rise into
 * their ridgeline instead of jumping straight to full height at the biome
 * border.
 *
 * <h3>How it works</h3>
 * <p>When the biomemap loads, {@link #compute} runs a fast two-pass
 * Manhattan-distance transform over every pixel. For each mountain pixel it
 * records how many pixels away the nearest non-mountain pixel is — i.e. how
 * deep inside the mountain blob it sits. Edge pixels get distance 1, pixels
 * one step in get distance 2, and so on up to the core.
 *
 * <p>{@link #rampWeight} turns that distance into a 0-to-1 blend weight
 * using a smoothstep curve, so the climb feels natural rather than linear:
 * slow start right at the edge, steepest partway up the flank, levelling
 * off toward the core.
 *
 * <p>Crucially, this class only ever hands back a blend *weight* — never a
 * height value. The caller ({@link GotChunkGenerator#computeRawSurfaceY})
 * uses that weight, PER BIOMEMAP PIXEL, to blend that pixel's contribution
 * to the height field between {@link #FOOT_HEIGHT} (a modest, roughly
 * sea-level value used right at the mountain's edge) and the pixel's own
 * configured {@code base_height} from {@code biome_colors.json} (the full
 * peak, used once deep enough inside the blob). That blend happens BEFORE
 * the ordinary bicubic terrain blend runs, not after — so the long-distance
 * climb and the normal short-range biome-border blend are one continuous
 * height field instead of two separate systems that can double up into an
 * abrupt cliff. The height mountains actually reach at their core is always
 * exactly whatever's painted on the biomemap color entry — the same fixed
 * number for a narrow ridge and a sprawling range — rather than something
 * that scales with how wide the blob of pixels is. Only how quickly/
 * gradually that fixed height is reached depends on the blob's shape, which
 * is the whole point of a slope.
 *
 * <h3>Biome eligibility</h3>
 * <p>A biome is treated as a mountain biome (participates in the distance
 * field) when its {@code height_variation} in {@code biome_colors.json} meets
 * or exceeds {@link #MOUNTAIN_VARIATION_THRESHOLD}. This means plains/forests
 * are untouched; only biomes you've already flagged as hilly/mountainous gain
 * the slope behaviour.
 *
 * <h3>Thread safety</h3>
 * <p>{@link #compute} is called off the main thread in
 * {@code MapReloadListener#prepare}. {@link #apply} pushes the result to the
 * volatile store on the main thread. Reads via {@link #rampWeight} are safe
 * any time after {@link #apply} returns.
 */
public final class MountainSlopemapResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Height-variation threshold (blocks of noise amplitude) above which a
     * biome participates in the mountain distance field. Biomes below this
     * value are ignored by the slopemap system entirely.
     */
    public static final float MOUNTAIN_VARIATION_THRESHOLD = 30f;

    /**
     * Distance in biomemap pixels over which the ramp weight climbs from 0
     * (right at the mountain's edge) to 1 (fully at the mountain biome's own
     * configured {@code base_height}). 1 pixel = {@value BiomemapLoader#MAP_SCALE}
     * blocks in world space. At MAP_SCALE=46, RAMP_PIXELS=10 → ramp covers
     * ~460 world blocks (~28 chunks), giving a gradual, realistic mountain
     * flank climbing up to its configured peak height.
     */
    public static final int RAMP_PIXELS = 10;

    /**
     * The height a mountain-classified pixel contributes to the ordinary
     * short-range terrain blend right at its own edge (ramp weight ≈ 0),
     * before it's had any distance to climb toward its own configured
     * peak. Deliberately close to a typical lowland biome's base_height
     * (most sit in the 60s) so a mountain border blends naturally into
     * whatever's next to it instead of jumping toward the peak value
     * immediately. This is what actually makes the climb take the full
     * {@link #RAMP_PIXELS}-pixel distance instead of mostly happening in
     * the first pixel or two via the ordinary bicubic blend.
     */
    public static final float FOOT_HEIGHT = 66f;

    // ── Live state ─────────────────────────────────────────────────────────

    /**
     * [px][pz] = distance in pixels to nearest non-mountain pixel.
     * 0 means the pixel is not a mountain biome.
     * Set to null until first compute/apply cycle completes.
     */
    private static volatile short[][] distanceField = null;
    private static volatile int fieldWidth  = 0;
    private static volatile int fieldHeight = 0;

    private MountainSlopemapResolver() {}

    // ── Compute ────────────────────────────────────────────────────────────

    /**
     * Builds the distance field from a freshly loaded biomemap pixel grid.
     * Call off the main thread (in {@code MapReloadListener#prepare}).
     *
     * @param pixels     [x][z] = 0xRRGGBB pixel color
     * @param width      image width in pixels
     * @param height     image height in pixels
     * @return distance field [x][z] = distance to nearest non-mountain edge
     */
    public static short[][] compute(int[][] pixels, int width, int height) {
        if (pixels == null || width == 0 || height == 0) return null;

        // Step 1: classify each pixel as mountain (true) or not
        boolean[][] isMountain = new boolean[width][height];
        int mountainCount = 0;
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                GotBiomeTerrainParams.Params p =
                        GotBiomeTerrainParams.forColor(pixels[x][z]);
                if (p.heightVariation() >= MOUNTAIN_VARIATION_THRESHOLD && !p.isWater()) {
                    isMountain[x][z] = true;
                    mountainCount++;
                }
            }
        }

        LOGGER.info("[GoT] MountainSlopemap: {} / {} pixels classified as mountain",
                mountainCount, width * height);

        // Step 2: two-pass Manhattan distance transform
        // Pass 1: top-left → bottom-right
        // Pass 2: bottom-right → top-left
        // Non-mountain pixels get distance 0. Mountain pixels get the minimum
        // Manhattan distance to any non-mountain neighbor.
        // We use short to save memory; distances beyond 32767 are clamped (unreachable in practice).

        short[][] dist = new short[width][height];
        final short INF = 30000;

        // initialise: non-mountain = 0, mountain = INF
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                dist[x][z] = isMountain[x][z] ? INF : 0;

        // Forward pass (top-left → bottom-right)
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                if (!isMountain[x][z]) continue;
                short best = INF;
                if (x > 0)       best = (short) Math.min(best, dist[x-1][z] + 1);
                if (z > 0)       best = (short) Math.min(best, dist[x][z-1] + 1);
                dist[x][z] = best;
            }
        }

        // Backward pass (bottom-right → top-left)
        for (int x = width - 1; x >= 0; x--) {
            for (int z = height - 1; z >= 0; z--) {
                if (!isMountain[x][z]) continue;
                short best = dist[x][z];
                if (x < width  - 1) best = (short) Math.min(best, dist[x+1][z] + 1);
                if (z < height - 1) best = (short) Math.min(best, dist[x][z+1] + 1);
                dist[x][z] = best;
            }
        }

        // Log the max distance found (= radius of the widest mountain core)
        int maxDist = 0;
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                if (dist[x][z] > maxDist) maxDist = dist[x][z];
        LOGGER.info("[GoT] MountainSlopemap: max interior distance = {} pixels ({} world blocks)",
                maxDist, maxDist * BiomemapLoader.MAP_SCALE);

        return dist;
    }

    // ── Apply ──────────────────────────────────────────────────────────────

    /** Push a freshly computed distance field into the static store. Main thread only. */
    public static void apply(short[][] field, int width, int height) {
        distanceField = field;
        fieldWidth    = width;
        fieldHeight   = height;
    }

    // ── Query ──────────────────────────────────────────────────────────────

    /**
     * Returns how far, on a 0-to-1 scale, a biomemap pixel at (px, pz) has
     * climbed from its mountain biome's edge toward full height. Returns 0
     * if the pixel is not a mountain biome or the field is unloaded.
     *
     * <p>The weight is:
     * <pre>
     *   t = clamp(distance / RAMP_PIXELS, 0, 1)
     *   weight = t*t*(3 - 2*t)          // smoothstep
     * </pre>
     *
     * <p>The caller uses this purely to blend toward a fixed target height —
     * see {@link GotChunkGenerator#computeRawSurfaceY}. This class never
     * decides what that target height is; it only decides how much of it to
     * apply at a given point.
     */
    public static float rampWeight(int px, int pz) {
        short[][] field = distanceField;
        if (field == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));
        int d = field[px][pz];
        if (d == 0) return 0f;

        float t = Math.min(d / (float) RAMP_PIXELS, 1f);
        return t * t * (3f - 2f * t); // smoothstep
    }

    /** @return true if the distance field has been loaded and applied. */
    public static boolean isLoaded() { return distanceField != null; }
}