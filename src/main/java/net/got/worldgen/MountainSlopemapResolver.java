package net.got.worldgen;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class MountainSlopemapResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final float MOUNTAIN_VARIATION_THRESHOLD = 30f;

    public static final int RAMP_PIXELS = 10;

    public static final int RIDGE_FALLOFF_PIXELS = 6;

    public static final int PEAK_MIN_SPACING_PIXELS = 8;

    public static final int PEAK_RADIUS_PIXELS = 6;

    public static final float FOOT_HEIGHT = 66f;

    public static final int FOLD_PERIOD_PIXELS = 7;

    public static final float FOLD_STRENGTH = 0.16f;

    private static final float FOLD_PEAK_CREST_THRESHOLD = 0.82f;

    private static final int FOLD_PEAK_MIN_EDGE_DIST = 3;

    public static final int PASS_PERIOD_PIXELS = 22;

    public static final float PASS_DEPTH = 0.8f;

    private static final float PASS_NOISE_THRESHOLD = 0.16f;

    private static volatile short[][] edgeDistanceField = null;

    private static volatile short[][] ridgeDistanceField = null;

    private static volatile short[][] peakDistanceField = null;

    private static volatile int fieldWidth  = 0;
    private static volatile int fieldHeight = 0;

    private MountainSlopemapResolver() {}

    public record Fields(short[][] edgeDistance, short[][] ridgeDistance, short[][] peakDistance) {}

    // builds the mountain distance fields used for slope/ridge/peak weighting
    public static Fields compute(int[][] pixels, int width, int height) {
        if (pixels == null || width == 0 || height == 0) return null;

        // mark pixels as mountain based on height variation
        boolean[][] isMountain = new boolean[width][height];
        int mountainCount = 0;
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                BiomeTerrainParams.Params p =
                        BiomeTerrainParams.forColor(pixels[x][z]);
                if (p.heightVariation() >= MOUNTAIN_VARIATION_THRESHOLD && !p.isWater()) {
                    isMountain[x][z] = true;
                    mountainCount++;
                }
            }
        }

        LOGGER.info("[GoT] MountainSlopemap: {} / {} pixels classified as mountain",
                mountainCount, width * height);

        short[][] edgeDist = manhattanDistanceTransform(isMountain, width, height);

        int maxDist = 0;
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                if (edgeDist[x][z] > maxDist) maxDist = edgeDist[x][z];
        LOGGER.info("[GoT] MountainSlopemap: max interior distance = {} pixels ({} world blocks)",
                maxDist, maxDist * BiomemapLoader.MAP_SCALE);

        // thin mountain mask down to a 1-pixel-wide ridgeline
        boolean[][] skeleton = zhangSuenThin(isMountain, width, height);

        int skeletonCount = 0;
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                if (skeleton[x][z]) skeletonCount++;
        LOGGER.info("[GoT] MountainSlopemap: skeleton/ridgeline is {} pixels", skeletonCount);

        short[][] ridgeDist = manhattanDistanceTransform(invert(skeleton, width, height), width, height);

        List<int[]> skeletonPixels = new ArrayList<>();
        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                if (skeleton[x][z]) skeletonPixels.add(new int[]{x, z, edgeDist[x][z]});

        // also add fold-crest points as peak candidates (periodic ripple pattern)
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

        // pick peaks from candidates, skipping any too close to an already-picked peak
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

    // two-pass distance transform: distance (in pixels) from each true cell to nearest false cell
    private static short[][] manhattanDistanceTransform(boolean[][] mask, int width, int height) {
        short[][] dist = new short[width][height];
        final short INF = 30000;

        for (int x = 0; x < width; x++)
            for (int z = 0; z < height; z++)
                dist[x][z] = mask[x][z] ? INF : 0;

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                if (!mask[x][z]) continue;
                short best = INF;
                if (x > 0) best = (short) Math.min(best, dist[x-1][z] + 1);
                if (z > 0) best = (short) Math.min(best, dist[x][z-1] + 1);
                dist[x][z] = best;
            }
        }

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

    // Zhang-Suen skeletonization: repeatedly strips edge pixels until only the ridge line remains
    private static boolean[][] zhangSuenThin(boolean[][] shape, int width, int height) {
        boolean[][] img = new boolean[width][height];
        for (int x = 0; x < width; x++)
            img[x] = shape[x].clone();

        int maxIterations = Math.max(width, height) / 2 + 4;

        for (int iter = 0; iter < maxIterations; iter++) {
            boolean changedStep1 = thinStep(img, width, height, true);
            boolean changedStep2 = thinStep(img, width, height, false);
            if (!changedStep1 && !changedStep2) break;
        }

        return img;
    }

    private static boolean thinStep(boolean[][] img, int width, int height, boolean isFirstSubIteration) {
        boolean[][] toRemove = new boolean[width][height];
        boolean changed = false;

        for (int x = 1; x < width - 1; x++) {
            for (int z = 1; z < height - 1; z++) {
                if (!img[x][z]) continue;

                boolean p2 = img[x][z-1];
                boolean p3 = img[x+1][z-1];
                boolean p4 = img[x+1][z];
                boolean p5 = img[x+1][z+1];
                boolean p6 = img[x][z+1];
                boolean p7 = img[x-1][z+1];
                boolean p8 = img[x-1][z];
                boolean p9 = img[x-1][z-1];

                // standard Zhang-Suen removal conditions (neighbor count + transition count)
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

    private static int transitions(boolean... p) {
        int n = 0;
        for (int i = 0; i < p.length; i++) {
            boolean cur  = p[i];
            boolean next = p[(i + 1) % p.length];
            if (!cur && next) n++;
        }
        return n;
    }

    public static void apply(Fields fields, int width, int height) {
        edgeDistanceField  = fields.edgeDistance();
        ridgeDistanceField = fields.ridgeDistance();
        peakDistanceField  = fields.peakDistance();
        fieldWidth  = width;
        fieldHeight = height;
    }

    public static float rampWeight(int px, int pz) {
        short[][] field = edgeDistanceField;
        if (field == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));
        int d = field[px][pz];
        if (d == 0) return 0f;

        float t = Math.min(d / (float) RAMP_PIXELS, 1f);
        return t * t * (3f - 2f * t);
    }

    public static float ridgeWeight(int px, int pz) {
        short[][] field = ridgeDistanceField;
        if (field == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));
        int d = field[px][pz];
        if (d <= 0) return 1f;

        float t = Math.min(d / (float) RIDGE_FALLOFF_PIXELS, 1f);
        float weight = t * t * (3f - 2f * t);
        return 1f - weight;
    }

    public static float peakWeight(int px, int pz) {
        short[][] field = peakDistanceField;
        if (field == null) return 0f;
        px = Math.max(0, Math.min(fieldWidth  - 1, px));
        pz = Math.max(0, Math.min(fieldHeight - 1, pz));
        int d = field[px][pz];
        if (d <= 0) return 1f;

        float t = Math.min(d / (float) PEAK_RADIUS_PIXELS, 1f);
        return 1f - t;
    }

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

    public static float passWeight(int px, int pz) {
        float n = valueNoise(px / (float) PASS_PERIOD_PIXELS, pz / (float) PASS_PERIOD_PIXELS);
        if (n >= PASS_NOISE_THRESHOLD) return 0f;

        float t = (PASS_NOISE_THRESHOLD - n) / PASS_NOISE_THRESHOLD;
        float smoothed = t * t * (3f - 2f * t);
        return smoothed * PASS_DEPTH;
    }

    // cheap integer hash used as a pseudo-random noise source
    private static float hash2(int x, int z) {
        int h = x * 374761393 + z * 668265263;
        h = (h ^ (h >>> 13)) * 1274126177;
        h = h ^ (h >>> 16);
        return (h & 0xFFFFFF) / (float) 0x1000000;
    }

    // bilinear-interpolated value noise between the 4 surrounding hashed corners
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

    public static boolean isLoaded() { return edgeDistanceField != null; }
}