package net.got.worldgen;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

/**
 * Computes a distance-to-edge field across the biomemap for mountain biomes,
 * then uses that distance to automatically ramp terrain height upward toward
 * the interior of each mountain blob.
 *
 * <h3>How it works</h3>
 * <p>When the biomemap loads, {@link #compute} runs a fast two-pass
 * Manhattan-distance transform over every pixel. For each mountain pixel it
 * records how many pixels away the nearest non-mountain pixel is — i.e. how
 * deep inside the mountain blob it sits. Edge pixels get distance 1, pixels
 * one step in get distance 2, and so on up to the core.
 *
 * <p>In {@link #slopedHeight}, that distance is converted into a height bonus
 * that is added on top of the biome's {@code base_height}. The bonus ramps
 * smoothly from 0 at the edge up to {@link #MAX_HEIGHT_BONUS} blocks at the
 * core, using a smoothstep curve so the slope feels natural rather than linear.
 *
 * <p>The result: paint one flat mountain color on the biomemap, get a mountain
 * that automatically rises toward its center and slopes down at its edges —
 * exactly the slopemap behavior described by the Westeros hachure art style,
 * where ridgelines are at the center of each range and flanks radiate outward.
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
 * volatile store on the main thread. Reads via {@link #slopedHeight} are safe
 * any time after {@link #apply} returns.
 */
public final class MountainSlopemapResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Height-variation threshold (blocks of noise amplitude) above which a
     * biome participates in the mountain distance field.  Biomes below this
     * value are ignored by the slopemap system entirely.
     *
     * Set to 18 so that north_hills (30), frostfangs (75), north_mountains*
     * (20-40) all qualify, while plains/forests (10) and rivers (1-2) do not.
     */
    public static final float MOUNTAIN_VARIATION_THRESHOLD = 30f;

    /**
     * Maximum extra height (blocks) added at the core of a mountain blob —
     * i.e. the pixel furthest from any non-mountain edge.
     *
     * At distance D from the edge, the bonus is:
     *   bonus = MAX_HEIGHT_BONUS * smoothstep(min(D / RAMP_PIXELS, 1))
     *
     * This means a small isolated peak (narrow blob) may never reach the full
     * bonus; only wide ranges with deep interiors get full height. That's
     * physically correct — narrow ridges are lower than broad massifs.
     */
    public static final float MAX_HEIGHT_BONUS = 160f;

    /**
     * Distance in biomemap pixels over which the height ramps from 0 to full.
     * 1 pixel = {@value BiomemapLoader#MAP_SCALE} blocks in world space.
     * At MAP_SCALE=46, RAMP_PIXELS=12 → ramp covers ~552 world blocks (~34 chunks),
     * giving a gradual, realistic mountain flank.
     */
    public static final int RAMP_PIXELS = 10;

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
     * Returns the height bonus in blocks for a biomemap pixel at (px, pz).
     * Returns 0 if the pixel is not a mountain biome or the field is unloaded.
     *
     * <p>The bonus is:
     * <pre>
     *   t = clamp(distance / RAMP_PIXELS, 0, 1)
     *   smoothT = t*t*(3 - 2*t)          // smoothstep
     *   bonus = MAX_HEIGHT_BONUS * smoothT
     * </pre>
     *
     * This produces a gentle S-curve: slow start at the edge, steepest slope
     * partway up the flank, then levelling off toward the core — which is
     * exactly how real mountain profiles look.
     */
    public static float heightBonus(int px, int pz) {
        short[][] field = distanceField;
        if (field == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));
        int d = field[px][pz];
        if (d == 0) return 0f;

        float t = Math.min(d / (float) RAMP_PIXELS, 1f);
        float smoothT = t * t * (3f - 2f * t); // smoothstep
        return MAX_HEIGHT_BONUS * smoothT;
    }

    /** @return true if the distance field has been loaded and applied. */
    public static boolean isLoaded() { return distanceField != null; }
}
