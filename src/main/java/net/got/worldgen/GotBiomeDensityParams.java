package net.got.worldgen;

import java.util.HashMap;
import java.util.Map;

/**
 * Per-biome terrain parameters for the 3D density-field generator.
 *
 * <h3>How the two parameters work</h3>
 * <pre>
 *   density(x, y, z) = (depth - y)  +  noise3D(x, y, z) * scale
 * </pre>
 *
 * <ul>
 *   <li><b>depth</b> — the Y level where the density function crosses zero
 *       when noise = 0.  Think of it as the "resting" surface elevation.
 *       Below {@code depth} the column is solid by default; above, it is air.</li>
 *   <li><b>scale</b> — how much the 3-D noise is allowed to push the surface
 *       away from {@code depth}, in blocks.  A scale of 60 means the surface
 *       can be as much as 60 blocks above or below the baseline, producing
 *       dramatic mountain peaks and deep ravines.  A scale of 5 gives nearly
 *       flat terrain with only gentle undulation.</li>
 * </ul>
 *
 * <h3>Biome terrain guide</h3>
 * <pre>
 *   DEEP_OCEAN     depth= 28  scale=  6   deep flat seabed
 *   OCEAN          depth= 40  scale=  5   open ocean floor
 *   RIVER          depth= 50  scale=  3   carved river bed, near-flat
 *   NECK           depth= 62  scale=  8   wetland flats, just at sea level
 *   NORTH          depth= 68  scale= 15   rolling plains
 *   STONY_SHORE    depth= 66  scale= 12   slightly eroded coastal ground
 *   BARROWLANDS    depth= 72  scale= 18   gentle rolling barrows
 *   HAUNTED_FOREST depth= 70  scale= 18   moderate forest floor
 *   WOLFSWOOD      depth= 74  scale= 22   hilly forested terrain
 *   IRONWOOD       depth= 76  scale= 25   denser forest hills
 *   NORTH_HILLS    depth= 88  scale= 35   distinct rolling hills
 *   ALWAYS_WINTER  depth= 85  scale= 28   high windswept plateau
 *   NORTH_MOUNTAINS depth=120 scale= 58   tall mountain range
 *   FROSTFANGS     depth=135  scale= 65   extreme jagged peaks
 * </pre>
 */
public final class GotBiomeDensityParams {

    // ── Parameter record ──────────────────────────────────────────────────

    public static final class Params {
        /**
         * Baseline surface Y — the density function's zero-crossing when noise
         * is zero.  Higher values produce elevated terrain.
         */
        public final float depth;

        /**
         * Noise amplitude in blocks.  Controls how much the 3-D noise can push
         * the surface above or below {@code depth}.
         * <ul>
         *   <li>0–5   : essentially flat (ocean floors, river beds)</li>
         *   <li>10–20 : rolling plains / gentle hills</li>
         *   <li>30–45 : distinct hills and ridges</li>
         *   <li>55–70 : tall, dramatic mountains</li>
         * </ul>
         */
        public final float scale;

        /**
         * True for river, ocean, and deep-ocean biomes.
         * Water biomes contribute their low {@code depth} to the biome blend,
         * naturally carving river beds and sea floors below {@link GotChunkGenerator#SEA_LEVEL}.
         * Empty columns at or below sea level are filled with water by the generator.
         */
        public final boolean isWater;

        Params(float depth, float scale, boolean isWater) {
            this.depth   = depth;
            this.scale   = scale;
            this.isWater = isWater;
        }
    }

    // ── Biome name → Params table ─────────────────────────────────────────

    private static final Map<String, Params> BY_NAME  = new HashMap<>();
    private static final Map<Integer, Params> BY_COLOR = new HashMap<>();
    private static final Params FALLBACK;

    static {
        // ── Land biomes ───────────────────────────────────────────────────
        //                            depth   scale
        land("north",                 70f,    15f);   // rolling plains
        land("barrowlands",           75f,    20f);   // gentle barrow hills
        land("stony_shore",           66f,    12f);   // coastal rock shelves
        land("north_hills",           88f,    35f);   // pronounced hill range
        land("neck",                  62f,     8f);   // near-sea-level wetlands
        land("ironwood",              76f,    25f);   // forested hill country
        land("wolfswood",             74f,    22f);   // dense forested slopes
        land("haunted_forest",        70f,    18f);   // eerily flat dark forest
        land("north_mountains",      120f,    75f);   // tall mountain range
        land("frostfangs",           135f,    90f);   // extreme jagged peaks
        land("always_winter",         85f,    28f);   // frozen high plateau

        // ── Water biomes ──────────────────────────────────────────────────
        //                            depth   scale
        water("river",                55f,     3f);   // shallow carved bed
        water("neck_river",           55f,     3f);   // neck wetland channels
        water("frozen_river",         55f,     3f);   // frozen river bed
        water("ocean",                45f,     5f);   // open ocean floor
        water("deep_ocean",           35f,     6f);   // abyssal depths

        // ── Colour palette (must mirror GotBiomeSource exactly) ───────────
        BY_COLOR.put(0x949038, BY_NAME.get("north"));
        BY_COLOR.put(0xADA942, BY_NAME.get("barrowlands"));
        BY_COLOR.put(0x92B0AC, BY_NAME.get("stony_shore"));
        BY_COLOR.put(0x808F81, BY_NAME.get("north_hills"));
        BY_COLOR.put(0x2F4A33, BY_NAME.get("neck"));
        BY_COLOR.put(0x02450D, BY_NAME.get("ironwood"));
        BY_COLOR.put(0x047D17, BY_NAME.get("wolfswood"));
        BY_COLOR.put(0x537053, BY_NAME.get("haunted_forest"));
        BY_COLOR.put(0x00229D, BY_NAME.get("ocean"));
        BY_COLOR.put(0x110751, BY_NAME.get("deep_ocean"));
        BY_COLOR.put(0x2D6796, BY_NAME.get("river"));
        BY_COLOR.put(0x35A180, BY_NAME.get("neck_river"));
        BY_COLOR.put(0x4B91E6, BY_NAME.get("frozen_river"));
        BY_COLOR.put(0xBBCCCD, BY_NAME.get("frostfangs"));
        BY_COLOR.put(0xFFFFFF, BY_NAME.get("always_winter"));
        BY_COLOR.put(0xA5B7B9, BY_NAME.get("north_mountains"));

        FALLBACK = BY_NAME.get("ocean");
    }

    private static void land(String name, float depth, float scale) {
        BY_NAME.put(name, new Params(depth, scale, false));
    }

    private static void water(String name, float depth, float scale) {
        BY_NAME.put(name, new Params(depth, scale, true));
    }

    private GotBiomeDensityParams() {}

    // ── Public API ────────────────────────────────────────────────────────

    /**
     * Returns the {@link Params} for the given 24-bit biomemap RGB colour.
     * Uses exact lookup first; falls back to nearest-colour-distance for
     * compressed or anti-aliased border pixels.
     */
    public static Params forColor(int rgb) {
        Params exact = BY_COLOR.get(rgb);
        if (exact != null) return exact;

        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >>  8) & 0xFF;
        int b =  rgb        & 0xFF;

        int    bestDist = Integer.MAX_VALUE;
        Params best     = FALLBACK;

        for (Map.Entry<Integer, Params> e : BY_COLOR.entrySet()) {
            int c  = e.getKey();
            int dr = ((c >> 16) & 0xFF) - r;
            int dg = ((c >>  8) & 0xFF) - g;
            int db = ( c        & 0xFF) - b;
            int d  = dr * dr + dg * dg + db * db;
            if (d < bestDist) { bestDist = d; best = e.getValue(); }
        }

        return best;
    }

    /** Returns the {@link Params} by biome registry path (e.g. {@code "north_mountains"}). */
    public static Params forName(String name) {
        return BY_NAME.getOrDefault(name, FALLBACK);
    }
}
