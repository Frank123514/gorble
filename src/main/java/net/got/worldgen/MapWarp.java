package net.got.worldgen;

/**
 * Shared fBm double-pass domain warp used by EVERY map-pixel lookup in the mod.
 *
 * <h3>Why this class exists</h3>
 * Both {@link HeightmapLoader} (terrain elevation) and {@link BiomemapLoader}
 * (biome colours) must warp their pixel coordinates with the <em>same</em>
 * function, using the <em>same</em> parameters.  If either class uses different
 * warp settings, biome borders will no longer line up with terrain features —
 * you'll see forest tiles floating over rivers, or river water sitting on land.
 *
 * <h3>Algorithm — Inigo Quilez double-pass fBm warp</h3>
 * <pre>
 *   q  = fbm( p )                      // pass 1
 *   r  = fbm( p + q * WARP1_SCALE )    // pass 2 — feeds q back in
 *   warped = p + r * WARP2_SCALE       // final displacement
 * </pre>
 * Feeding the first-pass output into the second makes the distortion fold back
 * on itself, producing turbulent, swirly, erosion-like shapes instead of the
 * smooth gentle bends that a single noise pass gives.
 *
 * <h3>How to use</h3>
 * Replace every raw pixel-coordinate calculation in your map-loader classes with:
 * <pre>
 *   float[] wc = MapWarp.warp(worldX, worldZ, imageWidth, imageHeight);
 *   float mapX = wc[0];
 *   float mapZ = wc[1];
 *   // now sample your pixel array with (mapX, mapZ)
 * </pre>
 */
public final class MapWarp {

    /* ------------------------------------------------------------------ */
    /* Shared warp parameters — MUST be identical for every caller         */
    /* ------------------------------------------------------------------ */

    /**
     * Base frequency of the fBm noise in pixels⁻¹.
     * 1/18 = one broad noise cycle per 18 pixels (~500 blocks).
     * Lower = wider, lazier meanders. Higher = tighter wiggles.
     */
    public static final float FBM_BASE_FREQ = 1f / 18f;

    /** fBm octave count. More = finer erosion detail at the cost of more hash calls. */
    public static final int FBM_OCTAVES = 5;

    /** Amplitude ratio per octave (geometric decay). 0.5 = halves each time. */
    public static final float FBM_PERSISTENCE = 0.5f;

    /** Frequency ratio per octave (geometric growth). 2.0 = doubles each time. */
    public static final float FBM_LACUNARITY = 2.0f;

    /**
     * Scale of the first warp pass in pixels.
     * Controls how strongly q feeds into the second fBm evaluation.
     * Higher = more dramatic first-pass twist before the second pass runs.
     */
    public static final float WARP1_SCALE = 3.5f;

    /**
     * Final displacement amplitude in heightmap/biomemap pixels.
     * One pixel = {@value HeightmapLoader#MAP_SCALE} world blocks.
     * 2.8 px ≈ 78 blocks of organic bank / border shift.
     *
     * Increase for more dramatic meandering.
     * Decrease if you notice geography distortion.
     */
    public static final float WARP2_SCALE = 2.8f;

    /* ------------------------------------------------------------------ */
    /* Private channel seeds — keep the 4 fBm fields decorrelated         */
    /* ------------------------------------------------------------------ */

    private static final int[] SEEDS = { 1337, 7919, 4253, 9001 };

    private MapWarp() {}

    /* ------------------------------------------------------------------ */
    /* Public API                                                          */
    /* ------------------------------------------------------------------ */

    /**
     * Returns warped pixel coordinates for a world-block position.
     *
     * <p>Both the elevation lookup in {@link HeightmapLoader} and the colour
     * lookup in {@link BiomemapLoader} must call this method with their own
     * {@code imageWidth}/{@code imageHeight} values.  Because both maps share
     * the same scale ({@value HeightmapLoader#MAP_SCALE} blocks per pixel) and
     * the same warp parameters, their warped pixel coordinates will be
     * numerically identical, keeping terrain and biomes perfectly aligned.</p>
     *
     * @param worldX      world block X coordinate
     * @param worldZ      world block Z coordinate
     * @param imageWidth  pixel width of the map image
     * @param imageHeight pixel height of the map image
     * @return {@code float[2]} = { warped map-X, warped map-Z }
     */
    public static float[] warp(int worldX, int worldZ,
                               int imageWidth, int imageHeight) {
        // Raw (un-warped) pixel coordinates
        float rawX = worldX / (float) HeightmapLoader.MAP_SCALE + imageWidth  * 0.5f;
        float rawZ = worldZ / (float) HeightmapLoader.MAP_SCALE + imageHeight * 0.5f;

        // Scale to fBm frequency domain
        float px = rawX * FBM_BASE_FREQ;
        float pz = rawZ * FBM_BASE_FREQ;

        // ── Pass 1: q = fbm(p) ──────────────────────────────────────────
        float qx = fbm(px,           pz,           SEEDS[0]);
        float qz = fbm(px + 5.2f,    pz + 1.3f,    SEEDS[1]);   // decorrelated offset

        // ── Pass 2: r = fbm(p + q * scale1) ─────────────────────────────
        float rx = fbm(px + qx * WARP1_SCALE,             pz + qz * WARP1_SCALE,             SEEDS[2]);
        float rz = fbm(px + qx * WARP1_SCALE + 1.7f,      pz + qz * WARP1_SCALE + 9.2f,      SEEDS[3]);

        // ── Final displacement ───────────────────────────────────────────
        return new float[]{ rawX + rx * WARP2_SCALE,
                rawZ + rz * WARP2_SCALE };
    }

    /* ------------------------------------------------------------------ */
    /* Fractal Brownian Motion                                             */
    /* ------------------------------------------------------------------ */

    /**
     * Sums {@link #FBM_OCTAVES} value-noise octaves and normalises to [−1, 1].
     */
    private static float fbm(float x, float z, int seed) {
        float value     = 0f;
        float amplitude = 0.5f;
        float frequency = 1f;
        float maxVal    = 0f;

        for (int i = 0; i < FBM_OCTAVES; i++) {
            value    += valueNoise(x * frequency, z * frequency, seed + i * 997) * amplitude;
            maxVal   += amplitude;
            amplitude *= FBM_PERSISTENCE;
            frequency *= FBM_LACUNARITY;
        }

        return value / maxVal;
    }

    /* ------------------------------------------------------------------ */
    /* Value noise                                                         */
    /* ------------------------------------------------------------------ */

    /** Smooth 2D value noise in [−1, 1], bilinearly interpolated with smoothstep. */
    private static float valueNoise(float x, float z, int seed) {
        int   ix = (int) Math.floor(x);
        int   iz = (int) Math.floor(z);
        float fx = x - ix;
        float fz = z - iz;

        float ux = fx * fx * (3f - 2f * fx);   // smoothstep
        float uz = fz * fz * (3f - 2f * fz);

        float v00 = hash(ix,     iz,     seed);
        float v10 = hash(ix + 1, iz,     seed);
        float v01 = hash(ix,     iz + 1, seed);
        float v11 = hash(ix + 1, iz + 1, seed);

        return lerp(uz, lerp(ux, v00, v10), lerp(ux, v01, v11));
    }

    /** Deterministic integer hash → [−1, 1]. */
    private static float hash(int x, int z, int seed) {
        int n = x * 1619 + z * 31337 + seed * 6971;
        n = (n << 13) ^ n;
        n = n * (n * n * 15731 + 789221) + 1376312589;
        return 1f - (n & 0x7fffffff) / 1073741824f;
    }

    private static float lerp(float t, float a, float b) { return a + t * (b - a); }
}