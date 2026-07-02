package net.got.worldgen;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

/**
 * Computes distance-to-edge fields across the biomemap for river/lake biomes,
 * then uses those fields to carve a tight, steep-walled valley that hugs the
 * true biome boundary instead of a wide, softly-blended one.
 *
 * <h3>Why this exists</h3>
 * <p>Rivers are narrow (often 1 pixel wide) and relatively shallow compared to
 * surrounding terrain. The bicubic B-spline blending in {@link GotChunkGenerator}
 * averages river pixels with their neighbours, which does two unwanted things:
 * <ul>
 *   <li>A 1-pixel-wide river surrounded by hills gets blended nearly flat —
 *       the river "fizzles out" instead of cutting a channel.</li>
 *   <li>The <em>land</em> pixels directly next to the river get pulled
 *       <em>down</em> toward the river's low base height by the same blend,
 *       which pushes the visible shoreline outward past the actual biome
 *       boundary painted on the map — the water looks much wider/softer than
 *       the shape that was actually drawn.</li>
 * </ul>
 *
 * <p>This resolver addresses both, with the land-side fix doing most of the
 * work:
 * <ul>
 *   <li><b>Land side</b> — a positive "bank" bonus radiates outward from the
 *       river edge, restoring nearby land pixels to their natural height.
 *       This cancels out the blend-down effect described above, so the bank
 *       rises sharply right at the true boundary instead of sagging into a
 *       wide floodplain. This is what makes the valley hug the actual
 *       river/lake shape painted on the biomemap.</li>
 *   <li><b>River side</b> — a small negative depth bonus radiates inward from
 *       the river edge, so a narrow river still reads as a definite channel
 *       even when blended with much higher neighbouring terrain. This is kept
 *       modest — the steep, tight bank transition above already does most of
 *       the work of keeping rivers from fizzling out, so this only needs to
 *       nudge the channel down a bit further, not carve a canyon.</li>
 * </ul>
 *
 * <h3>How it works</h3>
 * <p>On biomemap load, {@link #compute} runs two two-pass Manhattan distance
 * transforms identical to the one in {@link MountainSlopemapResolver}:
 * <ul>
 *   <li>{@code riverDist} — for each river pixel, how deep inside its river
 *       blob it sits (1 = edge, higher = more interior). 0 for land.</li>
 *   <li>{@code landDist} — for each land pixel, how far it sits from the
 *       nearest river pixel (1 = immediately adjacent, higher = further
 *       away). 0 for river pixels.</li>
 * </ul>
 *
 * <p>In {@link #elevationBonus}, these are converted into a signed height
 * adjustment:
 * <pre>
 *   river pixel: t = clamp(riverDist / RAMP_PIXELS, 0, 1)
 *                bonus = -MAX_DEPTH_BONUS * smoothstep(t)     (≤ 0, digs down)
 *
 *   land pixel:  t = clamp((landDist - 1) / LAND_RAMP_PIXELS, 0, 1)
 *                bonus = MAX_BANK_BOOST * (1 - smoothstep(t)) (≥ 0, pushes up)
 * </pre>
 *
 * <p>Both fields are sampled across the same 4x4 bicubic neighbourhood used
 * for height/variation in {@code GotChunkGenerator}, so the transition stays
 * smooth pixel-to-pixel while still being much narrower than a plain blend.
 *
 * <h3>Biome eligibility</h3>
 * <p>Any biome whose {@code base_height} in {@code biome_colors.json} is below
 * {@link GotChunkGenerator#SEA_LEVEL} is treated as a water biome and
 * participates in the valley field. This covers rivers, lakes, ocean shores,
 * and any future water biomes automatically.
 *
 * <h3>Thread safety</h3>
 * <p>{@link #compute} is called off the main thread in
 * {@link MapReloadListener#prepare}. {@link #apply} pushes the result on the
 * main thread. Reads via {@link #elevationBonus} are safe after {@link #apply}.
 */
public final class RiverSlopemapResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    // ── Tuning ─────────────────────────────────────────────────────────────

    /**
     * Maximum extra depth (blocks) carved at the core of a river pixel —
     * i.e. the pixel furthest from any non-river edge.
     *
     * Kept modest on purpose: the steep bank transition (below) already
     * prevents rivers from fizzling out when blended with high terrain, so
     * this only needs to give the channel a bit of extra definition, not do
     * the heavy lifting like it used to.
     */
    public static final float MAX_DEPTH_BONUS = 10f;

    /**
     * Distance in biomemap pixels over which the river-side valley ramps
     * from 0 to full depth. Kept short and tight so the drop happens right
     * at the river core instead of grading gradually.
     */
    public static final int RAMP_PIXELS = 2;

    /**
     * Maximum height (blocks) restored to a land pixel immediately adjacent
     * to a river/lake edge. This exists purely to counteract the bicubic
     * blend pulling nearby land down toward the river's low base height —
     * without it, the visible shoreline creeps outward past the actual
     * biome boundary painted on the map. Setting this too high can cause a
     * visible lip right at the shoreline, so keep it modest relative to
     * {@link #MAX_DEPTH_BONUS}.
     */
    public static final float MAX_BANK_BOOST = 10f;

    /**
     * Distance in biomemap pixels (measured from the first land pixel next
     * to a river, which is distance 1) over which the bank boost fades back
     * to 0. Kept short so only the immediate shoreline is affected — this is
     * what "squeezes" the terrain in to hug the true biome boundary instead
     * of reshaping the whole floodplain.
     */
    public static final int LAND_RAMP_PIXELS = 2;

    // ── Live state ─────────────────────────────────────────────────────────

    /** Immutable pair of distance fields produced by {@link #compute}. */
    public static final class Field {
        final short[][] riverDist;
        final short[][] landDist;

        Field(short[][] riverDist, short[][] landDist) {
            this.riverDist = riverDist;
            this.landDist  = landDist;
        }
    }

    private static volatile Field field       = null;
    private static volatile int   fieldWidth  = 0;
    private static volatile int   fieldHeight = 0;

    private RiverSlopemapResolver() {}

    // ── Compute ────────────────────────────────────────────────────────────

    /**
     * Builds the river-interior and land-edge distance fields from the
     * freshly loaded biomemap. Call off the main thread (in
     * {@link MapReloadListener#prepare}).
     *
     * @param pixels  [x][z] = 0xRRGGBB pixel color
     * @param width   image width in pixels
     * @param height  image height in pixels
     * @return paired distance fields, or null if pixels is empty
     */
    public static Field compute(int[][] pixels, int width, int height) {
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

        short[][] riverDist = distanceTransform(isRiver, width, height, true);
        short[][] landDist  = distanceTransform(isRiver, width, height, false);

        int maxDist = 0;
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                if (riverDist[x][z] > maxDist) maxDist = riverDist[x][z];
        LOGGER.info("[GoT] RiverSlopemap: max river interior distance = {} pixels ({} world blocks)",
                maxDist, maxDist * BiomemapLoader.MAP_SCALE);

        return new Field(riverDist, landDist);
    }

    /**
     * Two-pass Manhattan distance transform, same algorithm as
     * {@link MountainSlopemapResolver#compute}.
     *
     * @param isRiver     river/water classification, [x][z]
     * @param forRiver    if true, compute distance-to-nearest-land for each
     *                    river pixel (land pixels get 0). If false, compute
     *                    distance-to-nearest-river for each land pixel
     *                    (river pixels get 0).
     */
    private static short[][] distanceTransform(boolean[][] isRiver, int width, int height, boolean forRiver) {
        short[][] dist = new short[width][height];
        final short INF = 30000;

        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                dist[x][z] = (isRiver[x][z] == forRiver) ? INF : 0;

        // Forward pass: top-left → bottom-right
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                if (isRiver[x][z] != forRiver) continue;
                short best = INF;
                if (x > 0) best = (short) Math.min(best, dist[x-1][z] + 1);
                if (z > 0) best = (short) Math.min(best, dist[x][z-1] + 1);
                dist[x][z] = best;
            }
        }

        // Backward pass: bottom-right → top-left
        for (int x = width - 1; x >= 0; x--) {
            for (int z = height - 1; z >= 0; z--) {
                if (isRiver[x][z] != forRiver) continue;
                short best = dist[x][z];
                if (x < width  - 1) best = (short) Math.min(best, dist[x+1][z] + 1);
                if (z < height - 1) best = (short) Math.min(best, dist[x][z+1] + 1);
                dist[x][z] = best;
            }
        }

        return dist;
    }

    // ── Apply ──────────────────────────────────────────────────────────────

    /** Push a freshly computed field pair into the static store. Main thread only. */
    public static void apply(Field newField, int width, int height) {
        field       = newField;
        fieldWidth  = width;
        fieldHeight = height;
    }

    // ── Query ──────────────────────────────────────────────────────────────

    /**
     * Returns the signed elevation bonus for biomemap pixel (px, pz).
     * Negative digs a river pixel deeper; positive restores a nearby land
     * pixel's height so the bank hugs the true biome boundary. Returns 0 if
     * this pixel is unaffected (deep land, far from any river) or the field
     * is unloaded.
     */
    public static float elevationBonus(int px, int pz) {
        Field f = field;
        if (f == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));

        int rd = f.riverDist[px][pz];
        if (rd > 0) {
            float t = Math.min(rd / (float) RAMP_PIXELS, 1f);
            float smoothT = t * t * (3f - 2f * t);
            return -MAX_DEPTH_BONUS * smoothT;
        }

        int ld = f.landDist[px][pz];
        if (ld > 0) {
            float t = Math.min((ld - 1) / (float) LAND_RAMP_PIXELS, 1f);
            float smoothT = t * t * (3f - 2f * t);
            return MAX_BANK_BOOST * (1f - smoothT);
        }

        return 0f;
    }

    /** @return true if the distance fields have been loaded and applied. */
    public static boolean isLoaded() { return field != null; }
}