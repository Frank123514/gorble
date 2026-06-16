package net.got.worldgen;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

/**
 * Computes a distance-to-edge field across the biomemap for river/lake biomes,
 * then uses that distance to carve a valley depression into the surrounding terrain.
 *
 * <h3>Why this exists</h3>
 * <p>Rivers are narrow (often 1 pixel wide) and relatively shallow compared to
 * surrounding terrain.  The bicubic B-spline blending in {@link GotChunkGenerator}
 * averages river pixels with their neighbours, which means a 1-pixel-wide river
 * surrounded by hills gets blended nearly flat — the river "fizzles out" instead
 * of cutting a channel through the landscape.
 *
 * <p>This resolver fixes that by adding a <em>negative</em> height bonus
 * (a depth bonus) that radiates outward from river pixels, pulling the land
 * down into a valley shape.  The depression is widest and deepest right at the
 * river core and fades to zero a few biomemap pixels away.  This means:
 * <ul>
 *   <li>Rivers surrounded by mountains still carve a visible gorge.</li>
 *   <li>Narrow 1-pixel rivers gain a proper floodplain indent on both banks.</li>
 *   <li>The transition looks natural because the bonus is bicubically interpolated
 *       alongside all the other terrain parameters.</li>
 * </ul>
 *
 * <h3>How it works</h3>
 * <p>On biomemap load, {@link #compute} runs a two-pass Manhattan distance
 * transform identical to the one in {@link MountainSlopemapResolver}, but
 * classifying river/lake pixels instead of mountain pixels.  Each river pixel
 * gets a distance value of how deep inside its river blob it sits (1 = edge,
 * higher = more interior).  Non-river pixels get 0.
 *
 * <p>In {@link #depthBonus}, the distance is converted into a <em>negative</em>
 * height adjustment that is subtracted from the terrain surface:
 * <pre>
 *   t        = clamp(distance / RAMP_PIXELS, 0, 1)
 *   smoothT  = t*t*(3 - 2*t)             // smoothstep
 *   bonus    = -MAX_DEPTH_BONUS * smoothT  // negative = digging down
 * </pre>
 *
 * <p>Additionally, the bonus is also sampled for non-river pixels that sit
 * <em>next to</em> a river pixel (their distance field contribution bleeds
 * outward via the bicubic interpolation in {@code GotChunkGenerator}).  This
 * naturally widens the visible valley without any extra work.
 *
 * <h3>Biome eligibility</h3>
 * <p>Any biome whose {@code base_height} in {@code biome_colors.json} is below
 * {@link GotChunkGenerator#SEA_LEVEL} is treated as a water biome and
 * participates in the valley field.  This covers rivers, lakes, ocean shores,
 * and any future water biomes automatically.
 *
 * <h3>Thread safety</h3>
 * <p>{@link #compute} is called off the main thread in
 * {@link MapReloadListener#prepare}. {@link #apply} pushes the result on the
 * main thread. Reads via {@link #depthBonus} are safe after {@link #apply}.
 */
public final class RiverSlopemapResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    // ── Tuning ─────────────────────────────────────────────────────────────

    /**
     * Maximum extra depth (blocks) carved at the core of a river pixel —
     * i.e. the pixel furthest from any non-river edge.
     *
     * River base_height is ~50, surrounding land is ~68-70 → a 14 block gap
     * before blending.  This bonus ensures rivers stay well below sea level
     * even when blended with very high neighbouring terrain.
     *
     * Set conservatively: the bonus only reaches full value deep inside wide
     * rivers.  Narrow 1-pixel rivers get a fraction of this at their edges.
     */
    public static final float MAX_DEPTH_BONUS = 20f;

    /**
     * Distance in biomemap pixels over which the valley ramps from 0 to full.
     * At MAP_SCALE=46, RAMP_PIXELS=3 → ramp covers ~138 world blocks (~8 chunks).
     *
     * Kept short so the depression is tight around the river rather than
     * turning every nearby hill into a cliff.  The bicubic interpolation already
     * spreads the influence somewhat beyond this radius.
     */
    public static final int RAMP_PIXELS = 3;

    // ── Live state ─────────────────────────────────────────────────────────

    /**
     * [px][pz] = distance in pixels to nearest non-river pixel.
     * 0 means the pixel is not a river/water biome.
     * null until first compute/apply cycle completes.
     */
    private static volatile short[][] distanceField = null;
    private static volatile int fieldWidth  = 0;
    private static volatile int fieldHeight = 0;

    private RiverSlopemapResolver() {}

    // ── Compute ────────────────────────────────────────────────────────────

    /**
     * Builds the river distance field from the freshly loaded biomemap.
     * Call off the main thread (in {@link MapReloadListener#prepare}).
     *
     * @param pixels  [x][z] = 0xRRGGBB pixel color
     * @param width   image width in pixels
     * @param height  image height in pixels
     * @return distance field [x][z], non-river pixels = 0
     */
    public static short[][] compute(int[][] pixels, int width, int height) {
        if (pixels == null || width == 0 || height == 0) return null;

        // Classify: river = any pixel whose biome has base_height < SEA_LEVEL
        // (oceans, rivers, lakes) — this matches GotBiomeTerrainParams.isWater().
        boolean[][] isRiver = new boolean[width][height];
        int riverCount = 0;
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                GotBiomeTerrainParams.Params p =
                        GotBiomeTerrainParams.forColor(pixels[x][z]);
                if (p.isWater()) {
                    isRiver[x][z] = true;
                    riverCount++;
                }
            }
        }

        LOGGER.info("[GoT] RiverSlopemap: {} / {} pixels classified as water/river",
                riverCount, width * height);

        // Two-pass Manhattan distance transform — same algorithm as MountainSlopemapResolver.
        short[][] dist = new short[width][height];
        final short INF = 30000;

        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                dist[x][z] = isRiver[x][z] ? INF : 0;

        // Forward pass: top-left → bottom-right
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                if (!isRiver[x][z]) continue;
                short best = INF;
                if (x > 0) best = (short) Math.min(best, dist[x-1][z] + 1);
                if (z > 0) best = (short) Math.min(best, dist[x][z-1] + 1);
                dist[x][z] = best;
            }
        }

        // Backward pass: bottom-right → top-left
        for (int x = width - 1; x >= 0; x--) {
            for (int z = height - 1; z >= 0; z--) {
                if (!isRiver[x][z]) continue;
                short best = dist[x][z];
                if (x < width  - 1) best = (short) Math.min(best, dist[x+1][z] + 1);
                if (z < height - 1) best = (short) Math.min(best, dist[x][z+1] + 1);
                dist[x][z] = best;
            }
        }

        int maxDist = 0;
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                if (dist[x][z] > maxDist) maxDist = dist[x][z];
        LOGGER.info("[GoT] RiverSlopemap: max river interior distance = {} pixels ({} world blocks)",
                maxDist, maxDist * BiomemapLoader.MAP_SCALE);

        return dist;
    }

    // ── Apply ──────────────────────────────────────────────────────────────

    /** Push a freshly computed field into the static store. Main thread only. */
    public static void apply(short[][] field, int width, int height) {
        distanceField = field;
        fieldWidth    = width;
        fieldHeight   = height;
    }

    // ── Query ──────────────────────────────────────────────────────────────

    /**
     * Returns the depth bonus (always ≤ 0) for biomemap pixel (px, pz).
     * Returns 0 if this pixel is not a river biome or the field is unloaded.
     *
     * <p>The bonus is negative — callers subtract it from raw surface height
     * (or equivalently add it, since it's already negative):
     * <pre>
     *   t       = clamp(distance / RAMP_PIXELS, 0, 1)
     *   smoothT = t*t*(3 - 2*t)
     *   bonus   = -MAX_DEPTH_BONUS * smoothT     (≤ 0)
     * </pre>
     */
    public static float depthBonus(int px, int pz) {
        short[][] field = distanceField;
        if (field == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));
        int d = field[px][pz];
        if (d == 0) return 0f;

        float t = Math.min(d / (float) RAMP_PIXELS, 1f);
        float smoothT = t * t * (3f - 2f * t);
        return -MAX_DEPTH_BONUS * smoothT;
    }

    /** @return true if the distance field has been loaded and applied. */
    public static boolean isLoaded() { return distanceField != null; }
}
