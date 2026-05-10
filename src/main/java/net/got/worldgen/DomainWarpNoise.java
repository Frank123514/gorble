package net.got.worldgen;

/**
 * Gorble's own noise engine — a complete replacement for the old
 * {@code BlendedNoise} (ME-ported Perlin) with three new building blocks:
 *
 * <ol>
 *   <li><b>Value-gradient noise</b> — a fast hash-based gradient noise
 *       that produces a richer, more organic look than classic Perlin.</li>
 *   <li><b>Domain-warp fBm</b> — evaluates fBm noise, then uses a second
 *       noise field to "warp" the input coordinates before a final sample.
 *       This is the technique that makes terrain look geologically bent and
 *       twisted rather than smoothly bumpy.  Used for primary mountain shape
 *       and river meanders.</li>
 *   <li><b>Ridged noise</b> — absolute-value fBm folded to produce sharp
 *       ridgelines on mountain peaks, distinct from rounded hills.</li>
 * </ol>
 *
 * All three share a single compact permutation table with 512 entries so
 * the whole class has no mutable state and is safe for parallel access.
 */
public final class DomainWarpNoise {

    private DomainWarpNoise() {}

    // ── Permutation table ──────────────────────────────────────────────────
    // Independently generated — not taken from ME or vanilla Minecraft.
    private static final int[] PERM;
    static {
        // LCG shuffle of 0..255 with seed 0xDEADC0DE_B00B1E5L
        int[] base = new int[256];
        long s = 0xDEADC0DEB00B1E5L;
        for (int i = 0; i < 256; i++) {
            s = s * 6364136223846793005L + 1442695040888963407L;
            base[i] = i;
        }
        // Fisher-Yates
        java.util.Random rng = new java.util.Random(0xDEADC0DEB00B1E5L);
        for (int i = 255; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int t = base[i]; base[i] = base[j]; base[j] = t;
        }
        PERM = new int[512];
        for (int i = 0; i < 512; i++) PERM[i] = base[i & 255];
    }

    // ── 2D gradient vectors (8 directions, unit circle) ────────────────────
    private static final float[] GX = { 1, -1,  1, -1,  0,  0,  1, -1};
    private static final float[] GY = { 1,  1, -1, -1,  1, -1,  0,  0};

    // ── Core noise [-1, 1] ─────────────────────────────────────────────────

    /**
     * 2D gradient noise in the range [−1, 1].
     * All other methods build on top of this.
     */
    public static double noise(double x, double y) {
        int ix = (int) Math.floor(x);
        int iy = (int) Math.floor(y);
        double fx = x - ix;
        double fy = y - iy;

        double u = fade(fx);
        double v = fade(fy);

        int g00 = hash(ix,     iy    );
        int g10 = hash(ix + 1, iy    );
        int g01 = hash(ix,     iy + 1);
        int g11 = hash(ix + 1, iy + 1);

        double n00 = dot(g00, fx,       fy      );
        double n10 = dot(g10, fx - 1.0, fy      );
        double n01 = dot(g01, fx,       fy - 1.0);
        double n11 = dot(g11, fx - 1.0, fy - 1.0);

        return lerp(v, lerp(u, n00, n10), lerp(u, n01, n11));
    }

    // ── fBm (standard) ─────────────────────────────────────────────────────

    /**
     * Standard fractional Brownian motion summing {@code octaves} octaves.
     *
     * @param x       x coordinate
     * @param y       y coordinate
     * @param octaves number of octaves (typically 4–6)
     * @param lacunarity  frequency multiplier per octave (typically 2.0)
     * @param gain        amplitude multiplier per octave (typically 0.5)
     * @return sum in approximately [−1, 1]
     */
    public static double fbm(double x, double y, int octaves, double lacunarity, double gain) {
        double value = 0;
        double amplitude = 1.0;
        double norm = 0;
        for (int i = 0; i < octaves; i++) {
            value += amplitude * noise(x, y);
            norm  += amplitude;
            x         *= lacunarity;
            y         *= lacunarity;
            amplitude *= gain;
        }
        return value / norm;
    }

    // ── Domain-warp fBm ───────────────────────────────────────────────────

    /**
     * Domain-warped noise: evaluates a warp displacement field using fBm, then
     * re-samples fBm at the warped coordinates.
     *
     * <p>The warp pass shifts the final sample point by
     * {@code warpStrength * (fbm(x, y), fbm(x+5.2, y+1.3))}, giving organic
     * geological folding that plain fBm cannot produce.
     *
     * @param x           world x / scale
     * @param y           world z / scale
     * @param octaves     octaves for both warp and final fBm passes
     * @param warpStrength how far (in noise-space units) the warp displaces (0 = off)
     * @return warped fBm value, approximately [−1, 1]
     */
    public static double warpedFbm(double x, double y, int octaves, double warpStrength) {
        if (warpStrength == 0) return fbm(x, y, octaves, 2.0, 0.5);

        // warp displacement field — offset seeds so they're independent
        double wx = fbm(x,          y,          octaves, 2.0, 0.5);
        double wy = fbm(x + 5.2,    y + 1.3,    octaves, 2.0, 0.5);

        return fbm(x + warpStrength * wx, y + warpStrength * wy, octaves, 2.0, 0.5);
    }

    // ── Ridged noise ──────────────────────────────────────────────────────

    /**
     * Ridged multi-fractal noise: like fBm but each octave is
     * {@code 1 − |noise|} which creates sharp ridges at the zero crossings.
     *
     * <p>Used for mountain ridge lines and eroded cliff details.
     *
     * @return value in [0, 1] where 1 = sharp ridge, 0 = valley
     */
    public static double ridged(double x, double y, int octaves) {
        double value = 0;
        double amplitude = 1.0;
        double norm  = 0;
        double offset = 1.0;
        for (int i = 0; i < octaves; i++) {
            double n = offset - Math.abs(noise(x, y));
            n = n * n;  // sharpen
            value += amplitude * n;
            norm  += amplitude;
            x         *= 2.0;
            y         *= 2.0;
            amplitude *= 0.5;
        }
        return value / norm;
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private static int hash(int x, int y) {
        return PERM[(PERM[x & 255] + y) & 255] & 7;
    }

    private static double dot(int g, double x, double y) {
        return GX[g] * x + GY[g] * y;
    }
}
