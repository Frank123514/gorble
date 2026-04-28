package net.got.worldgen.surface;

import net.got.worldgen.GotPerlinNoise;

/**
 * Four independent stateless noise channels used by {@link GotSurfaceNoiseMixer},
 * {@link GotSurfaceNoisePaths}, and {@link GotUnderwaterNoiseMixer}.
 *
 * <p>Directly mirrors LOTR's four static {@code PerlinNoiseGenerator} instances
 * in {@code MiddleEarthSurfaceConfig} (noiseGen1–4), but implemented via
 * {@link GotPerlinNoise} so no state is needed.  Each channel has a unique fixed
 * seed; calling the same channel at the same XZ always returns the same value
 * (deterministic), but different channels produce statistically independent fields.
 *
 * <h3>Multi-scale queries</h3>
 * <p>Like LOTR's {@code getIteratedNoise()}, each query accepts:
 * <ul>
 *   <li>{@code scales} — spatial frequencies for each sample (mandatory)</li>
 *   <li>{@code xScales} — per-sample X-axis multiplier (defaults to 1.0)</li>
 *   <li>{@code zScales} — per-sample Z-axis multiplier (defaults to 1.0)</li>
 *   <li>{@code weights} — per-sample contribution weight (defaults to 1)</li>
 * </ul>
 * The return value is a weighted average across all samples, giving a result
 * approximately in [−1, 1].
 */
public final class GotSurfaceNoiseBank {

    // ── Per-channel seeds (fixed — do NOT change without regenerating all worlds) ──

    private static final int SEED_CH1 = 0x79FA_1954;
    private static final int SEED_CH2 = 0x2B4A_87C3;
    private static final int SEED_CH3 = 0x5E2D_A107;
    private static final int SEED_CH4 = 0x1D8F_3B62;

    private GotSurfaceNoiseBank() {}

    // ── Public API ────────────────────────────────────────────────────────

    /**
     * Queries a single noise channel.
     *
     * @param channelIndex 1–4
     * @param x            world block X
     * @param z            world block Z
     * @param scales       one or more spatial frequency multipliers
     * @return weighted-average noise in approximately [−1, 1]
     */
    public static double getChannel(int channelIndex, int x, int z, double... scales) {
        return getChannel(channelIndex, x, z, scales, null, null, null);
    }

    /**
     * Full-featured channel query with per-sample X/Z anisotropy and weights.
     *
     * @param channelIndex 1–4
     * @param x            world block X
     * @param z            world block Z
     * @param scales       spatial frequency multipliers — one entry per sample
     * @param xScales      X-axis multiplier per sample (null → all 1.0)
     * @param zScales      Z-axis multiplier per sample (null → all 1.0)
     * @param weights      contribution weight per sample (null → all 1)
     * @return weighted-average noise in approximately [−1, 1]
     */
    public static double getChannel(int channelIndex, int x, int z,
                                    double[] scales,
                                    double[] xScales,
                                    double[] zScales,
                                    int[]    weights) {
        int seed = seedFor(channelIndex);
        return iteratedNoise(seed, x, z, scales, xScales, zScales, weights);
    }

    // ── Convenience passthrough used by SurfaceNoisePaths ─────────────────

    /**
     * Samples channel 1 at a single scale.  Convenience method for code that
     * mirrors LOTR's {@code getNoise1(x, z, scale)} shorthand.
     */
    public static double getNoise1(int x, int z, double scale) {
        return getChannel(1, x, z, scale);
    }

    /**
     * Samples channel 2 at a single scale.
     */
    public static double getNoise2(int x, int z, double scale) {
        return getChannel(2, x, z, scale);
    }

    // ── Internal ──────────────────────────────────────────────────────────

    private static int seedFor(int channelIndex) {
        return switch (channelIndex) {
            case 1 -> SEED_CH1;
            case 2 -> SEED_CH2;
            case 3 -> SEED_CH3;
            case 4 -> SEED_CH4;
            default -> throw new IllegalArgumentException(
                    "channelIndex out of range [1–4]: " + channelIndex);
        };
    }

    /**
     * Weighted multi-scale Perlin query — exact port of LOTR's
     * {@code getIteratedNoise()}, with a diagonal-banding fix.
     *
     * <h3>Why y=0 causes diagonal lines</h3>
     * <p>Sampling 3D Perlin noise with a fixed {@code y=0} places every query
     * exactly on a horizontal lattice plane.  At those lattice boundaries the
     * gradient dot-products are dominated by the ±X and ±Z components of the
     * 12 cube-edge gradient vectors, which produces visible 45° diagonal streaks
     * in the XZ output — the "diagonal lines" artifact seen in-game.
     *
     * <h3>Fix</h3>
     * <p>Each sample uses a small, irrational Y offset derived from the channel
     * index and sample index.  Moving the sampling plane off the y=0 lattice
     * boundary mixes in the ±Y gradient components, breaking the XZ symmetry
     * and eliminating diagonal banding.  The offsets are irrational multiples
     * (13.7, 7.3) so no two channel/sample combinations land on the same
     * sub-lattice line.
     */
    private static double iteratedNoise(int seed,
                                        int x, int z,
                                        double[] scales,
                                        double[] xScales,
                                        double[] zScales,
                                        int[]    weights) {
        if (scales == null || scales.length == 0)
            throw new IllegalArgumentException("scales must have at least one entry");

        // Fill defaults (mirrors LOTR's ArrayUtils.isEmpty checks)
        if (xScales == null || xScales.length == 0) xScales = ones(scales.length);
        if (zScales == null || zScales.length == 0) zScales = ones(scales.length);
        if (weights == null || weights.length == 0) weights  = intOnes(scales.length);

        double noise = 0.0;
        int    total = 0;

        for (int i = 0; i < scales.length; i++) {
            double cx = x * xScales[i] * scales[i];
            double cz = z * zScales[i] * scales[i];

            // XOR seed with a per-sample constant so each sample at the same
            // position is drawn from an independent region of the noise field.
            int sampleSeed = seed ^ (0x9E3779B9 * (i + 1));

            // Use a unique irrational Y offset per channel (encoded in seed bits)
            // and per octave (i * 7.3f) so the sampling plane is never on the
            // y=0 lattice boundary, which eliminates diagonal gradient banding.
            float yOffset = ((seed & 0xFF) * (1f / 255f) * 13.7f) + i * 7.3f;

            noise += GotPerlinNoise.sample((float) cx, yOffset, (float) cz, sampleSeed) * weights[i];
            total += weights[i];
        }

        return noise / total;
    }

    private static double[] ones(int n) {
        double[] a = new double[n];
        java.util.Arrays.fill(a, 1.0);
        return a;
    }

    private static int[] intOnes(int n) {
        int[] a = new int[n];
        java.util.Arrays.fill(a, 1);
        return a;
    }
}