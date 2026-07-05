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
 * <p>This resolver addresses both, entirely from the river side — land
 * pixels are never raised above their natural height:
 * <ul>
 *   <li><b>River side only</b> — a negative depth bonus radiates inward from
 *       the river edge over a very short, tight ramp ({@link #RAMP_PIXELS}).
 *       Compressing the full drop into just a pixel or two makes the bank
 *       face steep, which is what keeps a narrow river reading as a definite
 *       channel instead of fizzling out when blended with higher neighbouring
 *       terrain — and because the drop starts exactly at the river/land
 *       boundary painted on the biomemap, the channel still hugs the true
 *       boundary shape without needing to touch the land side at all.</li>
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
 * <p>In {@link #elevationBonus}, {@code riverDist} is converted into a signed
 * height adjustment; land pixels always return 0:
 * <pre>
 *   river pixel: t = clamp(riverDist / RAMP_PIXELS, 0, 1)
 *                bonus = -MAX_DEPTH_BONUS * smoothstep(t)     (≤ 0, digs down)
 *
 *   land pixel:  bonus = 0                                    (untouched)
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
     * Now doing all the work of keeping rivers from fizzling out (the land
     * boost that used to help with this has been removed), so this stays
     * meaningfully deep — it's the entire mechanism for channel definition.
     */
    public static final float MAX_DEPTH_BONUS = 14f;

    /**
     * Distance in biomemap pixels over which the river-side valley ramps
     * from 0 to full depth. This is what actually controls bank steepness:
     * the smaller this is, the fewer pixels the drop from land height down
     * to full river depth is spread across, so the bank face is steeper.
     * Kept very short and tight so the drop happens right at the river edge
     * instead of grading gradually into a shallow slope.
     */
    public static final int RAMP_PIXELS = 1;

    /**
     * Land pixels no longer get an elevation boost — natural terrain height
     * is left untouched right up to the river's edge. Steepness/definition
     * of the bank now comes entirely from the river-side depth ramp below
     * (a tight {@link #RAMP_PIXELS} carving the channel down sharply),
     * rather than from artificially raising the surrounding land.
     */
    public static final float MAX_BANK_BOOST = 0f;

    /** Unused now that the land-side boost is disabled; kept for reference. */
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
     * Negative digs a river pixel deeper, over a very short ramp so the bank
     * face is steep rather than gradual. Land pixels are never boosted —
     * this returns 0 for any land pixel, no matter how close to a river.
     * Also returns 0 if the field is unloaded.
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