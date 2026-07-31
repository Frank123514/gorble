package net.got.worldgen;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Computes two distance fields across the biomemap for mountain biomes:
 * <ol>
 *   <li><b>Edge distance</b> — how far a mountain pixel sits from the
 *       nearest non-mountain pixel. Drives {@link #rampWeight}, the long
 *       climb from a mountain's border up toward its interior.</li>
 *   <li><b>Ridge distance</b> — how far a mountain pixel sits from the
 *       blob's own medial axis (skeleton) — the line of pixels equidistant
 *       from the two "sides" of the mountain shape, i.e. its natural
 *       ridgeline. Drives {@link #ridgeWeight}.</li>
 * </ol>
 *
 * <h3>Why two fields</h3>
 * <p>{@link #rampWeight} alone only answers "is this pixel deep enough
 * inside the blob to be at full height" — and once it says yes, EVERY
 * pixel past that distance answers yes identically, so a wide mountain
 * blob's whole interior sits at one flat height. Real mountain flanks
 * don't do that: they climb up from both sides and meet at an actual
 * ridge crest, not a plateau. {@link #ridgeWeight} supplies that: it's 1
 * exactly on the blob's skeleton (the ridgeline) and falls back off
 * toward 0 moving away from it — including back toward 0 in the middle
 * of a wide blob between two separate ridge branches. The caller
 * ({@link GotChunkGenerator#computeRawSurfaceY}) combines both weights so
 * the flanks rise via {@code rampWeight} and, once inside the blob, still
 * taper toward the actual skeleton line via {@code ridgeWeight} rather
 * than flattening out — and because the skeleton is the real medial axis
 * of whatever shape was painted, this scales correctly with blob width
 * automatically: a wide blob gets a long ridge with wide flanks, a narrow
 * one gets a ridge running right down its middle with a quick taper.
 *
 * <h3>How the skeleton is found</h3>
 * <p>The mountain/not-mountain classification is thinned down to a
 * 1-pixel-wide skeleton using the standard Zhang–Suen thinning algorithm
 * (repeatedly strips boundary pixels that don't disconnect the shape,
 * from both directions, until only the medial axis survives). A second
 * Manhattan distance transform then measures every mountain pixel's
 * distance to the nearest surviving skeleton pixel.
 *
 * <h3>Thread safety</h3>
 * <p>{@link #compute} is called off the main thread in
 * {@code MapReloadListener#prepare}. {@link #apply} pushes the result to
 * the volatile store on the main thread. Reads via {@link #rampWeight}/
 * {@link #ridgeWeight} are safe any time after {@link #apply} returns.
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
     * Distance in biomemap pixels over which {@link #ridgeWeight} falls
     * from 1 (right on the skeleton/ridgeline) to 0. Deliberately tighter
     * than {@link #RAMP_PIXELS} so the crest reads as an actual ridge
     * rather than another broad plateau — at MAP_SCALE=46 this covers
     * ~276 world blocks from the ridgeline out to fully tapered.
     */
    public static final int RIDGE_FALLOFF_PIXELS = 6;

    /**
     * Minimum distance in biomemap pixels enforced between two selected
     * peak points (see {@link #compute}'s greedy peak selection). Keeps
     * summits from clustering right on top of each other along a wide
     * ridgeline — real ranges space their peaks out.
     */
    public static final int PEAK_MIN_SPACING_PIXELS = 8;

    /**
     * Distance in biomemap pixels over which {@link #peakWeight} falls
     * from 1 (right on a selected summit point) to 0. The falloff is
     * LINEAR rather than smoothstepped — a straight cone reads as an
     * actual pointed pyramid; a smoothstepped one reads as a rounded dome.
     */
    public static final int PEAK_RADIUS_PIXELS = 6;

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

    /**
     * Spacing, in biomemap pixels, between the parallel sub-ridges that
     * {@link #foldWeight} carves into a mountain blob's interior. Real
     * ranges don't climb from edge to a single central crest and flatten
     * out — a wide range folds into several roughly-parallel ridgelines
     * with valleys between them, mirroring the range's own boundary
     * contour. Because {@link #foldWeight} is driven by edge-distance
     * (already computed for {@link #rampWeight}), the fold count scales
     * automatically with local blob width: a narrow spur only fits
     * part of one period and reads as a single smooth ridge, a wide one
     * fits several full periods and reads as multiple folds.
     */
    public static final int FOLD_PERIOD_PIXELS = 7;

    /**
     * How much height {@link #foldWeight}'s undulation can shave off at
     * its lowest point (the trough between two fold ridges), as a
     * fraction of the height already earned above {@link #FOOT_HEIGHT}.
     * Deliberately smaller than the skeleton/ridge system's own swing
     * ({@link #RIDGE_SHOULDER_FRACTION} vs {@link #RIDGE_VALLEY_FLOOR})
     * so folds read as secondary texture on top of the main divide, not
     * as tall as the primary ridgeline itself.
     */
    public static final float FOLD_STRENGTH = 0.16f;

    /**
     * Fold-crest closeness (see {@link #foldWeight}) above which a pixel
     * qualifies as a candidate for a secondary summit on one of the
     * outer parallel ridges, in {@link #compute}'s peak selection.
     */
    private static final float FOLD_PEAK_CREST_THRESHOLD = 0.82f;

    /**
     * Minimum edge-distance (pixels) before a pixel can even be
     * considered for a fold-ridge summit — skips the thin foothill
     * fringe right at a mountain's border so summits stay believably
     * up in the range rather than popping up at its very foot.
     */
    private static final int FOLD_PEAK_MIN_EDGE_DIST = 3;

    /**
     * Approximate spacing, in biomemap pixels, between the low gaps
     * {@link #passWeight} cuts into the connecting ridge crest — real
     * routes (the High Road through the Bloody Gate, say) thread through
     * a range at exactly these kinds of saddle points rather than over
     * the top of it.
     */
    public static final int PASS_PERIOD_PIXELS = 22;

    /**
     * How far {@link #passWeight} can pull the ridge crest down toward
     * {@link #RIDGE_VALLEY_FLOOR}, on a 0..1 scale. Kept under 1 so even
     * a full pass stays a walkable saddle rather than a flat notch cut
     * clean down to the valley floor.
     */
    public static final float PASS_DEPTH = 0.8f;

    /**
     * Fraction of {@link #PASS_PERIOD_PIXELS}-scaled value-noise below
     * which a point counts as sitting in a pass. Low, so passes are
     * occasional narrow gaps along a ridge's length, not a ridge that's
     * mostly gaps.
     */
    private static final float PASS_NOISE_THRESHOLD = 0.16f;

    // ── Live state ─────────────────────────────────────────────────────────

    /**
     * [px][pz] = distance in pixels to nearest non-mountain pixel.
     * 0 means the pixel is not a mountain biome.
     */
    private static volatile short[][] edgeDistanceField = null;

    /**
     * [px][pz] = distance in pixels to nearest skeleton/ridgeline pixel.
     * Meaningless (but harmless) for non-mountain pixels; callers only
     * ever read it where {@link #rampWeight} is already > 0.
     */
    private static volatile short[][] ridgeDistanceField = null;

    /**
     * [px][pz] = distance in pixels to nearest selected peak point (see
     * {@link #compute}). Meaningless (but harmless) for non-mountain
     * pixels; callers only ever read it where {@link #rampWeight} is
     * already > 0.
     */
    private static volatile short[][] peakDistanceField = null;

    private static volatile int fieldWidth  = 0;
    private static volatile int fieldHeight = 0;

    private MountainSlopemapResolver() {}

    /** Bundle of all three distance fields handed back by {@link #compute}. */
    public record Fields(short[][] edgeDistance, short[][] ridgeDistance, short[][] peakDistance) {}

    // ── Compute ────────────────────────────────────────────────────────────

    /**
     * Builds both distance fields from a freshly loaded biomemap pixel grid.
     * Call off the main thread (in {@code MapReloadListener#prepare}).
     *
     * @param pixels     [x][z] = 0xRRGGBB pixel color
     * @param width      image width in pixels
     * @param height     image height in pixels
     * @return both distance fields, or null if there's no biomemap loaded
     */
    public static Fields compute(int[][] pixels, int width, int height) {
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

        short[][] edgeDist = manhattanDistanceTransform(isMountain, width, height);

        // Log the max distance found (= radius of the widest mountain core)
        int maxDist = 0;
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                if (edgeDist[x][z] > maxDist) maxDist = edgeDist[x][z];
        LOGGER.info("[GoT] MountainSlopemap: max interior distance = {} pixels ({} world blocks)",
                maxDist, maxDist * BiomemapLoader.MAP_SCALE);

        // Step 2: thin the mountain shape down to its 1-pixel skeleton
        // (medial axis / ridgeline), then measure every pixel's distance
        // to the nearest surviving skeleton pixel.
        boolean[][] skeleton = zhangSuenThin(isMountain, width, height);

        int skeletonCount = 0;
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                if (skeleton[x][z]) skeletonCount++;
        LOGGER.info("[GoT] MountainSlopemap: skeleton/ridgeline is {} pixels", skeletonCount);

        // The ridge distance transform is seeded from skeleton pixels
        // (distance 0) rather than non-mountain pixels; reuse the same
        // transform by treating "is skeleton" as the "is seed" mask.
        short[][] ridgeDist = manhattanDistanceTransform(invert(skeleton, width, height), width, height);

        // Step 3: pick a handful of skeleton pixels to act as actual
        // summit points, so the ridgeline reads as a string of distinct
        // peaks instead of one continuous wall at uniform height. Peaks
        // come straight from the mountain's own painted shape — no extra
        // "ridge noise" — by picking the LOCALLY WIDEST skeleton points
        // first (edgeDist there is the mountain's own core radius, i.e.
        // a natural summit candidate), then greedily skipping any
        // candidate too close to an already-picked peak. Iterating the
        // full sorted list (not just the top few) means even a narrow
        // spur that never gets very wide still ends up with its own peak
        // once nothing closer is left to claim that spacing — no biome
        // is left peak-less just because it's slender.
        List<int[]> skeletonPixels = new ArrayList<>();
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                if (skeleton[x][z]) skeletonPixels.add(new int[]{x, z, edgeDist[x][z]});

        // Also offer up fold-crest pixels (see foldWeight) as peak
        // candidates — the same greedy pass below still favors deeper/
        // wider points first via the edgeDist sort, so the true skeleton
        // naturally wins the tallest summits; fold-crest points only
        // pick up leftover spacing on the outer parallel ridges once
        // nothing closer to the main divide is left to claim it.
        int peakCandidateCount = skeletonPixels.size();
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                if (!isMountain[x][z]) continue;
                int d = edgeDist[x][z];
                if (d < FOLD_PEAK_MIN_EDGE_DIST) continue;
                float phase = (d % FOLD_PERIOD_PIXELS) / (float) FOLD_PERIOD_PIXELS;
                float crest = 0.5f - 0.5f * (float) Math.cos(2.0 * Math.PI * phase);
                if (crest >= FOLD_PEAK_CREST_THRESHOLD) skeletonPixels.add(new int[]{x, z, d});
            }
        }
        skeletonPixels.sort((a, b) -> b[2] - a[2]);
        LOGGER.info("[GoT] MountainSlopemap: {} skeleton + {} fold-crest peak candidates",
                peakCandidateCount, skeletonPixels.size() - peakCandidateCount);

        boolean[][] isPeak = new boolean[width][height];
        List<int[]> peaks = new ArrayList<>();
        int minSpacingSq = PEAK_MIN_SPACING_PIXELS * PEAK_MIN_SPACING_PIXELS;
        for (int[] c : skeletonPixels) {
            boolean tooClose = false;
            for (int[] p : peaks) {
                int dx = c[0] - p[0], dz = c[1] - p[1];
                if (dx * dx + dz * dz < minSpacingSq) { tooClose = true; break; }
            }
            if (!tooClose) {
                peaks.add(c);
                isPeak[c[0]][c[1]] = true;
            }
        }
        LOGGER.info("[GoT] MountainSlopemap: selected {} peak points from {} skeleton pixels",
                peaks.size(), skeletonPixels.size());

        short[][] peakDist = manhattanDistanceTransform(invert(isPeak, width, height), width, height);

        return new Fields(edgeDist, ridgeDist, peakDist);
    }

    /**
     * Two-pass Manhattan distance transform. Pixels where {@code mask} is
     * true get the minimum distance to any pixel where {@code mask} is
     * false (their "seed"/boundary); {@code mask}-false pixels get 0.
     */
    private static short[][] manhattanDistanceTransform(boolean[][] mask, int width, int height) {
        short[][] dist = new short[width][height];
        final short INF = 30000;

        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                dist[x][z] = mask[x][z] ? INF : 0;

        // Forward pass (top-left → bottom-right)
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                if (!mask[x][z]) continue;
                short best = INF;
                if (x > 0) best = (short) Math.min(best, dist[x-1][z] + 1);
                if (z > 0) best = (short) Math.min(best, dist[x][z-1] + 1);
                dist[x][z] = best;
            }
        }

        // Backward pass (bottom-right → top-left)
        for (int x = width - 1; x >= 0; x--) {
            for (int z = height - 1; z >= 0; z--) {
                if (!mask[x][z]) continue;
                short best = dist[x][z];
                if (x < width  - 1) best = (short) Math.min(best, dist[x+1][z] + 1);
                if (z < height - 1) best = (short) Math.min(best, dist[x][z+1] + 1);
                dist[x][z] = best;
            }
        }

        return dist;
    }

    private static boolean[][] invert(boolean[][] mask, int width, int height) {
        boolean[][] out = new boolean[width][height];
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                out[x][z] = !mask[x][z];
        return out;
    }

    /**
     * Zhang–Suen skeletonization. Repeatedly strips foreground pixels from
     * the boundary of {@code shape} (in two sub-passes per iteration) that
     * can be removed without breaking the shape's connectivity, until
     * nothing more can be removed — leaving a 1-pixel-wide medial axis.
     * Does not modify {@code shape}; returns a new array.
     */
    private static boolean[][] zhangSuenThin(boolean[][] shape, int width, int height) {
        boolean[][] img = new boolean[width][height];
        for (int x = 0; x < width; x++)
            img[x] = shape[x].clone();

        // Safety cap: a full thinning pass removes at least the outermost
        // ring each full iteration, so this always converges well before
        // half the smaller map dimension.
        int maxIterations = Math.max(width, height) / 2 + 4;

        for (int iter = 0; iter < maxIterations; iter++) {
            boolean changedStep1 = thinStep(img, width, height, true);
            boolean changedStep2 = thinStep(img, width, height, false);
            if (!changedStep1 && !changedStep2) break;
        }

        return img;
    }

    /** One Zhang–Suen sub-pass. Returns true if any pixel was removed. */
    private static boolean thinStep(boolean[][] img, int width, int height, boolean isFirstSubIteration) {
        boolean[][] toRemove = new boolean[width][height];
        boolean changed = false;

        for (int x = 1; x < width - 1; x++) {
            for (int z = 1; z < height - 1; z++) {
                if (!img[x][z]) continue;

                // P2..P9 clockwise from north, matching the standard
                // Zhang–Suen neighbor numbering.
                boolean p2 = img[x][z-1];
                boolean p3 = img[x+1][z-1];
                boolean p4 = img[x+1][z];
                boolean p5 = img[x+1][z+1];
                boolean p6 = img[x][z+1];
                boolean p7 = img[x-1][z+1];
                boolean p8 = img[x-1][z];
                boolean p9 = img[x-1][z-1];

                int b = count(p2, p3, p4, p5, p6, p7, p8, p9);
                if (b < 2 || b > 6) continue;

                int a = transitions(p2, p3, p4, p5, p6, p7, p8, p9);
                if (a != 1) continue;

                if (isFirstSubIteration) {
                    if (p2 && p4 && p6) continue;
                    if (p4 && p6 && p8) continue;
                } else {
                    if (p2 && p4 && p8) continue;
                    if (p2 && p6 && p8) continue;
                }

                toRemove[x][z] = true;
                changed = true;
            }
        }

        if (changed) {
            for (int x = 0; x < width; x++)
                for (int z = 0; z < height; z++)
                    if (toRemove[x][z]) img[x][z] = false;
        }

        return changed;
    }

    private static int count(boolean... neighbors) {
        int n = 0;
        for (boolean b : neighbors) if (b) n++;
        return n;
    }

    /** Counts 0→1 transitions going around P2..P9,P2 in order. */
    private static int transitions(boolean... p) {
        int n = 0;
        for (int i = 0; i < p.length; i++) {
            boolean cur  = p[i];
            boolean next = p[(i + 1) % p.length];
            if (!cur && next) n++;
        }
        return n;
    }

    // ── Apply ──────────────────────────────────────────────────────────────

    /** Push freshly computed distance fields into the static store. Main thread only. */
    public static void apply(Fields fields, int width, int height) {
        edgeDistanceField  = fields.edgeDistance();
        ridgeDistanceField = fields.ridgeDistance();
        peakDistanceField  = fields.peakDistance();
        fieldWidth  = width;
        fieldHeight = height;
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
        short[][] field = edgeDistanceField;
        if (field == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));
        int d = field[px][pz];
        if (d == 0) return 0f;

        float t = Math.min(d / (float) RAMP_PIXELS, 1f);
        return t * t * (3f - 2f * t); // smoothstep
    }

    /**
     * Returns how close, on a 0-to-1 scale, a biomemap pixel at (px, pz) is
     * to the mountain blob's own skeleton/ridgeline — 1 exactly on it,
     * falling to 0 over {@link #RIDGE_FALLOFF_PIXELS}. Returns 0 if the
     * field is unloaded. Only meaningful where {@link #rampWeight} is
     * already > 0 — the caller gates on that first.
     */
    public static float ridgeWeight(int px, int pz) {
        short[][] field = ridgeDistanceField;
        if (field == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));
        int d = field[px][pz];
        if (d <= 0) return 1f;

        float t = Math.min(d / (float) RIDGE_FALLOFF_PIXELS, 1f);
        float weight = t * t * (3f - 2f * t); // smoothstep
        return 1f - weight;
    }

    /**
     * Returns how close, on a 0-to-1 scale, a biomemap pixel at (px, pz) is
     * to the nearest selected summit point — 1 exactly on it, falling to 0
     * over {@link #PEAK_RADIUS_PIXELS}. The falloff is deliberately LINEAR
     * (not smoothstepped like {@link #rampWeight}/{@link #ridgeWeight}) so
     * a peak reads as an actual pointed pyramid rather than a rounded
     * dome. Returns 0 if the field is unloaded. Only meaningful where
     * {@link #rampWeight} is already > 0 — the caller gates on that first.
     */
    public static float peakWeight(int px, int pz) {
        short[][] field = peakDistanceField;
        if (field == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));
        int d = field[px][pz];
        if (d <= 0) return 1f;

        float t = Math.min(d / (float) PEAK_RADIUS_PIXELS, 1f);
        return 1f - t; // linear, not smoothstep — a real cone, not a dome
    }

    /**
     * Returns how close, on a 0-to-1 scale, a biomemap pixel at (px, pz)
     * sits to one of the parallel fold-ridge crests carved every
     * {@link #FOLD_PERIOD_PIXELS} of edge-distance — 1 exactly on a
     * crest line, 0 exactly in the trough between two of them. Because
     * it's driven purely by edge-distance, the crest lines trace the
     * mountain's own boundary contour and their count scales with local
     * blob width automatically. Returns 0 if the field is unloaded or
     * the pixel isn't a mountain pixel. Only meaningful where
     * {@link #rampWeight} is already > 0 — the caller gates on that
     * first.
     */
    public static float foldWeight(int px, int pz) {
        short[][] field = edgeDistanceField;
        if (field == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));
        int d = field[px][pz];
        if (d <= 0) return 0f;

        float phase = (d % FOLD_PERIOD_PIXELS) / (float) FOLD_PERIOD_PIXELS;
        return 0.5f - 0.5f * (float) Math.cos(2.0 * Math.PI * phase);
    }

    /**
     * Returns how deep a mountain pass cuts at (px, pz), on a scale of
     * 0 (no pass — full ridge height) up to {@link #PASS_DEPTH} (as deep
     * as a pass ever cuts). Driven by smooth value-noise at a frequency
     * of {@link #PASS_PERIOD_PIXELS}, thresholded low so passes are
     * occasional narrow gaps rather than a ridge that's mostly gaps.
     * The caller is expected to gate this by {@link #ridgeWeight} so the
     * cut only lands on the actual ridge crest, not the open flanks.
     */
    public static float passWeight(int px, int pz) {
        float n = valueNoise(px / (float) PASS_PERIOD_PIXELS, pz / (float) PASS_PERIOD_PIXELS);
        if (n >= PASS_NOISE_THRESHOLD) return 0f;

        float t = (PASS_NOISE_THRESHOLD - n) / PASS_NOISE_THRESHOLD;
        float smoothed = t * t * (3f - 2f * t); // smoothstep
        return smoothed * PASS_DEPTH;
    }

    /** Cheap deterministic integer hash → [0, 1). Used by {@link #valueNoise}. */
    private static float hash2(int x, int z) {
        int h = x * 374761393 + z * 668265263;
        h = (h ^ (h >>> 13)) * 1274126177;
        h = h ^ (h >>> 16);
        return (h & 0xFFFFFF) / (float) 0x1000000;
    }

    /** Bilinear-interpolated, smoothstepped value noise, period 1 world unit. */
    private static float valueNoise(float x, float z) {
        int x0 = (int) Math.floor(x);
        int z0 = (int) Math.floor(z);
        float fx = x - x0;
        float fz = z - z0;

        float v00 = hash2(x0,     z0);
        float v10 = hash2(x0 + 1, z0);
        float v01 = hash2(x0,     z0 + 1);
        float v11 = hash2(x0 + 1, z0 + 1);

        float sx = fx * fx * (3f - 2f * fx);
        float sz = fz * fz * (3f - 2f * fz);

        float a = v00 + (v10 - v00) * sx;
        float b = v01 + (v11 - v01) * sx;
        return a + (b - a) * sz;
    }

    /** @return true if the distance fields have been loaded and applied. */
    public static boolean isLoaded() { return edgeDistanceField != null; }
}