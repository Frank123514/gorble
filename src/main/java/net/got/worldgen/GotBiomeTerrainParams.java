package net.got.worldgen;

import java.util.HashMap;
import java.util.Map;

/**
 * Per-biome terrain parameters.
 *
 * <ul>
 *   <li><b>baseY</b> — surface Y when noise contribution is zero.</li>
 *   <li><b>scale</b> — amplitude multiplier for smooth fBm noise.
 *       0 = flat, 1 = full amplitude.</li>
 *   <li><b>isWater</b> — true for rivers, oceans, and deep oceans.
 *       Water biomes are excluded from neighbour blending and placed at
 *       their exact {@code baseY} with no noise, ready for proper
 *       per-type implementation later.</li>
 * </ul>
 *
 * <h3>Amplitude constant</h3>
 * <pre>
 *   AMP_SMOOTH = 35  — smooth noise ∈ [-1,1] → ±scale*35 blocks around baseY
 * </pre>
 *
 * Resulting Y ranges (typical):
 * <pre>
 *   MOUNTAINS  baseY=155 scale=2.5  → Y ~67-242
 *   FROSTFANGS baseY=170 scale=2.5  → Y ~82-257
 *   ALW_WINTER baseY=185 scale=2.5  → Y ~97-272
 *   HILLS      baseY=68  scale=0.7  → Y ~43-92
 *   PLAINS     baseY=63  scale=0.25 → Y ~54-72
 *   WETLANDS   baseY=57  scale=0.10 → Y ~53-61
 *   RIVER      baseY=54  isWater    → flat Y 54
 *   OCEAN      baseY=36  isWater    → flat Y 36
 *   DEEP_OCEAN baseY=22  isWater    → flat Y 22
 * </pre>
 */
public final class GotBiomeTerrainParams {

    // ── Amplitude constant ────────────────────────────────────────────────

    /** Half-amplitude for smooth fBm at scale=1.0. */
    public static final float AMP_SMOOTH = 42f;

    // ── Parameter record ─────────────────────────────────────────────────

    public static final class Params {
        /** Surface Y when noise is zero. */
        public final float   baseY;
        /** Noise amplitude multiplier (0 = flat). */
        public final float   scale;
        /**
         * True for rivers, oceans, and deep oceans.
         * These biomes skip neighbour blending and sit at their exact baseY.
         */
        public final boolean isWater;

        private Params(float baseY, float scale, boolean isWater) {
            this.baseY   = baseY;
            this.scale   = scale;
            this.isWater = isWater;
        }
    }

    // ── Biome name → Params table ─────────────────────────────────────────

    private static final Map<String, Params> PARAMS_BY_NAME = new HashMap<>();

    static {
        // Mountains
        // Mountains — baseY raised slightly since σ=1.6 blending spreads height
        // into surrounding pixels; scale reduced from 2.5 as AMP_SMOOTH is now 42.
        // Net peak height stays roughly the same; slopes are gradual rather than cliffs.
        put("north_mountains", 100f, 1.15f);
        put("frostfangs",      120f, 1.70f);
        put("always_winter",   76f, 0.65f);

        // Hills / forest
        put("north_hills",     85f, 0.75f);
        put("barrowlands",     76f, 0.65f); // baseY +10
        put("stony_shore",     63f, 0.55f);
        put("ironwood",        70f, 0.70f);
        put("wolfswood",       69f, 0.65f);
        put("haunted_forest",  67f, 0.60f);

        // Plains
        put("north",           68f, 0.25f); // baseY +5

        // Wetlands
        put("neck",            54f, 0.10f);

        // Rivers — water, flat, no noise
        putWater("river",        50f);
        putWater("neck_river",   50f);
        putWater("frozen_river", 50f);

        // Ocean — water, flat, no noise
        putWater("ocean",      36f);
        putWater("deep_ocean", 22f);
    }

    private static void put(String name, float baseY, float scale) {
        PARAMS_BY_NAME.put(name, new Params(baseY, scale, false));
    }

    private static void putWater(String name, float baseY) {
        PARAMS_BY_NAME.put(name, new Params(baseY, 0f, true));
    }

    // ── Colour → Params (for fast lookup by biomemap colour) ─────────────

    private static final Map<Integer, Params> PARAMS_BY_COLOR = new HashMap<>();
    private static final Params FALLBACK;

    static {
        // Colour palette mirrors GotBiomeSource / biome_colors.json exactly.
        PARAMS_BY_COLOR.put(0x949038, PARAMS_BY_NAME.get("north"));
        PARAMS_BY_COLOR.put(0xADA942, PARAMS_BY_NAME.get("barrowlands"));
        PARAMS_BY_COLOR.put(0x92B0AC, PARAMS_BY_NAME.get("stony_shore"));
        PARAMS_BY_COLOR.put(0x808F81, PARAMS_BY_NAME.get("north_hills"));
        PARAMS_BY_COLOR.put(0x2F4A33, PARAMS_BY_NAME.get("neck"));
        PARAMS_BY_COLOR.put(0x02450D, PARAMS_BY_NAME.get("ironwood"));
        PARAMS_BY_COLOR.put(0x047D17, PARAMS_BY_NAME.get("wolfswood"));
        PARAMS_BY_COLOR.put(0x537053, PARAMS_BY_NAME.get("haunted_forest"));
        PARAMS_BY_COLOR.put(0x00229D, PARAMS_BY_NAME.get("ocean"));
        PARAMS_BY_COLOR.put(0x110751, PARAMS_BY_NAME.get("deep_ocean"));
        PARAMS_BY_COLOR.put(0x2D6796, PARAMS_BY_NAME.get("river"));
        PARAMS_BY_COLOR.put(0x35A180, PARAMS_BY_NAME.get("neck_river"));
        PARAMS_BY_COLOR.put(0x4B91E6, PARAMS_BY_NAME.get("frozen_river"));
        PARAMS_BY_COLOR.put(0xBBCCCD, PARAMS_BY_NAME.get("frostfangs"));
        PARAMS_BY_COLOR.put(0xFFFFFF, PARAMS_BY_NAME.get("always_winter"));
        PARAMS_BY_COLOR.put(0xA5B7B9, PARAMS_BY_NAME.get("north_mountains"));

        FALLBACK = PARAMS_BY_NAME.get("ocean"); // safe border fallback
    }

    private GotBiomeTerrainParams() {}

    // ── Public API ────────────────────────────────────────────────────────

    /**
     * Returns terrain params for the given 24-bit biomemap RGB colour.
     * Uses exact lookup first; falls back to nearest-colour-distance for
     * compressed / anti-aliased border pixels — exactly like GotBiomeSource.
     */
    public static Params forColor(int rgb) {
        Params exact = PARAMS_BY_COLOR.get(rgb);
        if (exact != null) return exact;

        // Nearest-colour fallback
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >>  8) & 0xFF;
        int b =  rgb        & 0xFF;

        int   bestDist   = Integer.MAX_VALUE;
        Params bestParams = FALLBACK;

        for (Map.Entry<Integer, Params> e : PARAMS_BY_COLOR.entrySet()) {
            int c  = e.getKey();
            int dr = ((c >> 16) & 0xFF) - r;
            int dg = ((c >>  8) & 0xFF) - g;
            int db = ( c        & 0xFF) - b;
            int d  = dr*dr + dg*dg + db*db;
            if (d < bestDist) { bestDist = d; bestParams = e.getValue(); }
        }

        return bestParams;
    }

    /** Returns terrain params by biome registry path (e.g. "north_mountains"). */
    public static Params forName(String biomeName) {
        return PARAMS_BY_NAME.getOrDefault(biomeName, FALLBACK);
    }
}