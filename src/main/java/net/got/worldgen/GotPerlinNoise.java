package net.got.worldgen;

/**
 * Stateless 3D Perlin gradient noise.
 *
 * <h3>Design</h3>
 * <p>Rather than storing a permutation table (which would require per-instance
 * allocation and make the class non-static), gradient selection is driven by a
 * deterministic integer hash of the lattice coordinates mixed with a per-octave
 * seed constant.  Different seed values produce statistically independent fields.
 *
 * <p>Gradient vectors are the 12 edge midpoints of a cube:
 * <pre>
 *   (±1, ±1, 0), (±1, 0, ±1), (0, ±1, ±1)
 * </pre>
 * These are the standard Perlin gradient set and produce smooth, isotropic noise
 * with no obvious directional bias.
 *
 * <h3>Output range</h3>
 * <p>Raw output is in approximately [−0.75, 0.75].  Every {@code sample()} call
 * multiplies by {@link #NORM} ({@value #NORM}) to normalise to [−1, 1] so
 * callers can treat the result as full-range without manual rescaling.
 *
 * <h3>Continuity</h3>
 * <p>The quintic fade curve {@code 6t⁵ − 15t⁴ + 10t³} zero-clamps both the
 * first and second derivatives at lattice boundaries, giving C² continuity across
 * cell edges.  Multi-octave sums therefore have no visible grid-aligned creases.
 */
public final class GotPerlinNoise {

    /**
     * Normalisation factor applied inside {@link #sample} so callers always
     * receive values in [−1, 1].  The raw 3D Perlin gradient sum is bounded
     * by ≈ 0.75 for the 12-vector gradient set; dividing by that upper bound
     * maps it to the full unit range.
     */
    private static final float NORM = 1.0f / 0.75f;

    private GotPerlinNoise() {}

    // ── Public API ────────────────────────────────────────────────────────

    /**
     * Samples 3D Perlin noise at the given float lattice coordinates.
     *
     * @param x    lattice X — scale by your desired frequency before calling
     * @param y    lattice Y — scale by a different frequency than XZ for anisotropy
     * @param z    lattice Z — same frequency as X for isotropic XZ
     * @param seed per-octave seed constant; use a distinct value for each octave
     * @return noise value in [−1, 1]
     */
    public static float sample(float x, float y, float z, int seed) {
        // Integer lattice cell containing (x, y, z).
        int ix = (int) Math.floor(x);
        int iy = (int) Math.floor(y);
        int iz = (int) Math.floor(z);

        // Local offsets within the cell (0..1).
        float fx = x - ix;
        float fy = y - iy;
        float fz = z - iz;

        // Quintic fade — C² continuity, zero derivative and curvature at 0 and 1.
        float ux = fade(fx);
        float uy = fade(fy);
        float uz = fade(fz);

        // Gradient dot products at all 8 lattice corners.
        float n000 = grad(ix,     iy,     iz,     seed, fx,     fy,     fz);
        float n100 = grad(ix + 1, iy,     iz,     seed, fx - 1, fy,     fz);
        float n010 = grad(ix,     iy + 1, iz,     seed, fx,     fy - 1, fz);
        float n110 = grad(ix + 1, iy + 1, iz,     seed, fx - 1, fy - 1, fz);
        float n001 = grad(ix,     iy,     iz + 1, seed, fx,     fy,     fz - 1);
        float n101 = grad(ix + 1, iy,     iz + 1, seed, fx - 1, fy,     fz - 1);
        float n011 = grad(ix,     iy + 1, iz + 1, seed, fx,     fy - 1, fz - 1);
        float n111 = grad(ix + 1, iy + 1, iz + 1, seed, fx - 1, fy - 1, fz - 1);

        // Trilinear interpolation using faded weights.
        float x0 = lerp(ux, n000, n100);
        float x1 = lerp(ux, n010, n110);
        float x2 = lerp(ux, n001, n101);
        float x3 = lerp(ux, n011, n111);
        float y0  = lerp(uy, x0, x1);
        float y1  = lerp(uy, x2, x3);

        return lerp(uz, y0, y1) * NORM;
    }

    /**
     * Samples three-octave fractional Brownian motion (fBm) using {@link #sample}.
     *
     * <p>Octave frequencies and amplitudes:
     * <pre>
     *   Coarse  freq × 1.0   amplitude 0.50
     *   Mid     freq × 2.5   amplitude 0.35
     *   Fine    freq × 6.0   amplitude 0.15
     * </pre>
     *
     * <p>The three seeds are derived from the base seed so callers only need one
     * value per noise field.
     *
     * @param x    lattice X (at coarse frequency)
     * @param y    lattice Y (at coarse frequency)
     * @param z    lattice Z (at coarse frequency)
     * @param seed base seed; mid/fine octaves use {@code seed ^ 0x5D3F1A} and {@code seed ^ 0xC7B42E}
     * @return fBm value in approximately [−1, 1]
     */
    public static float fbm(float x, float y, float z, int seed) {
        float coarse = sample(x,          y,          z,          seed);
        float mid    = sample(x * 2.5f,   y * 2.5f,   z * 2.5f,   seed ^ 0x5D3F1A);
        float fine   = sample(x * 6.0f,   y * 6.0f,   z * 6.0f,   seed ^ 0xC7B42E);
        return coarse * 0.50f + mid * 0.35f + fine * 0.15f;
    }

    // ── Internal maths ────────────────────────────────────────────────────

    /**
     * Computes the gradient contribution at a lattice corner.
     *
     * <p>Selects one of the 12 edge-midpoint gradient vectors via a
     * fast integer hash of the lattice coordinates, then returns its
     * dot product with the offset vector {@code (dx, dy, dz)}.
     *
     * <p>The 12 gradients and the bit-manipulation selection are from
     * Ken Perlin's reference implementation, adapted here to be seed-dependent.
     */
    private static float grad(int ix, int iy, int iz, int seed,
                               float dx, float dy, float dz) {
        int h = hash(ix, iy, iz, seed) & 15;
        // Select two of {dx, dy, dz} and apply sign via two bits.
        // h & 8 → swap which axis is 'u'; h & 4 → which axis is 'v'.
        float u = h < 8 ? dx : dy;
        float v = h < 4 ? dy : (h == 12 || h == 14) ? dx : dz;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    /** Quintic fade: {@code 6t⁵ − 15t⁴ + 10t³}. */
    private static float fade(float t) {
        return t * t * t * (t * (t * 6f - 15f) + 10f);
    }

    private static float lerp(float t, float a, float b) { return a + t * (b - a); }

    /**
     * Deterministic integer hash of a 3D lattice point mixed with a seed.
     *
     * <p>Uses the classic xorshift+multiply chain.  The three coordinate primes
     * (1619, 31337, 6547) are chosen to minimise correlation along each axis;
     * the seed prime (1013) injects the per-octave variation.
     */
    private static int hash(int x, int y, int z, int seed) {
        int n = x * 1619 + y * 31337 + z * 6547 + seed * 1013;
        n = (n << 13) ^ n;
        return n * (n * n * 15731 + 789221) + 1376312589;
    }
}
