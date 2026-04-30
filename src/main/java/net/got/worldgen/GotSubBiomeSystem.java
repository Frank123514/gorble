package net.got.worldgen;

import java.util.HashMap;
import java.util.Map;

/**
 * LOTR-style biome variant system for the GoT mod.
 *
 * <h2>What variants actually do</h2>
 * <p>Variants never change which biome a position belongs to. They modify two
 * terrain parameters in-place:
 *
 * <ul>
 *   <li><b>dScale</b> — changes terrain hilliness. This is the primary knob.
 *       Positive = hillier, negative = flatter. Scale transitions smoothly
 *       because the 3D noise field is continuous — no staircase artifacts.</li>
 *   <li><b>dDepth</b> — shifts the baseline elevation. Use sparingly and small
 *       (≤ ±4 blocks). Large values cause terrace/staircase artifacts at patch
 *       boundaries. Only valid use case: small negative value for a hollow/lake.</li>
 *   <li><b>treeDensityBonus</b> — extra tree-placement passes added in
 *       {@link GotChunkGenerator#applyBiomeDecoration}. 0 = unchanged, 1-2 = denser.</li>
 * </ul>
 */
public final class GotSubBiomeSystem {

    /** Side-length in blocks of each variant hash cell. */
    public static final int GRAIN = 192;

    /**
     * @param name             Human-readable label (debug only).
     * @param threshold        Cumulative probability gate [0,1]. Ascending order per biome.
     * @param dDepth           Baseline elevation delta. Keep at 0 unless making a hollow. ≤ ±4.
     * @param dScale           Hilliness delta. Primary terrain knob. No artifact risk.
     * @param treeDensityBonus Extra tree passes. 0=none, 1=denser, 2=packed.
     */
    public record Variant(String name, float threshold, float dDepth, float dScale, int treeDensityBonus) {}

    private static final Map<String, Variant[]> VARIANTS = new HashMap<>();

    static {
        // ── north  (depth=70, scale=15) ──────────────────────────────────────────
        // highland  12%: rolling upland — hillier patches in the plains
        // marsh     20% cumulative: shallow hollow, can pool water, calm flat terrain
        // pine_grove 35% cumulative: sheltered low pocket where pines cluster densely
        define("north",
            new Variant("highland",   0.12f,  10f, +18f, 0),
            new Variant("marsh",      0.20f,  0f, -14f, 0),
            new Variant("pine_grove", 0.35f,  0f,  -4f, 12)
        );

        // ── barrowlands  (depth=75, scale=20) ────────────────────────────────────
        // barrow_ridge 15%: steep mound country — scale lift creates pronounced shapes
        define("barrowlands",
            new Variant("barrow_ridge", 0.15f, 0f, +16f, 0)
        );

        // ── wolfswood  (depth=74, scale=22) ──────────────────────────────────────
        // deep_wood 22%: flatter, denser — packed canopy, uniform forest floor
        // ridge     32% cumulative: forested ridge — hillier + 1 extra tree pass
        define("wolfswood",
            new Variant("deep_wood", 0.22f, 0f,  -6f, 16),
            new Variant("ridge",     0.32f, 0f, +16f, 8)
        );

        // ── haunted_forest  (depth=70, scale=18) ─────────────────────────────────
        // deep_hollow 15%: sunken flat bowl — small depth dip, much lower scale
        define("haunted_forest",
            new Variant("deep_hollow", 0.15f,  0f, -16f, 8)
        );

        // ── ironwood  (depth=76, scale=25) ────────────────────────────────────────
        // ironwood_slopes 18%: rocky exposed ridges, hillier, denser trees
        define("ironwood",
            new Variant("ironwood_slopes", 0.18f, 0f, +14f, 8)
        );

        // ── neck  (depth=62, scale=8) ─────────────────────────────────────────────
        // marsh_flat 14%: deeper standing-water zone, very flat
        define("neck",
            new Variant("marsh_flat", 0.14f,  0f, -10f, 0)
        );

        // ── north_hills  (depth=88, scale=35) ────────────────────────────────────
        // sheepshead_top 10%: dramatic peak zone within the hill range
        define("north_hills",
            new Variant("sheepshead_top", 0.10f, 0f, +20f, 0)
        );

        // ── always_winter  (depth=85, scale=28) ──────────────────────────────────
        // windswept_flat 15%: desolate open ice plain
        define("always_winter",
            new Variant("windswept_flat", 0.15f, 0f, -16f, 0)
        );

        // ── frostfangs  (depth=135, scale=90) ────────────────────────────────────
        // glacier_shelf 12%: flat ice shelf at foot of peaks, dramatic scale drop
        define("frostfangs",
            new Variant("glacier_shelf", 0.12f,  0f, -20f, 0)
        );
    }

    // ── Public API ────────────────────────────────────────────────────────────────

    /**
     * Returns the active variant for this position+biome, or null for no variant.
     * Both terrain and feature hooks call this.
     */
    public static Variant activeVariant(String baseName, int wx, int wz) {
        Variant[] vs = VARIANTS.get(baseName);
        if (vs == null) return null;
        float n = noiseAt(wx, wz);
        for (Variant v : vs) { if (n < v.threshold()) return v; }
        return null;
    }

    /** Returns {dDepth, dScale} for bilinearBlend, or {0,0} if no variant. */
    public static float[] getTerrainDelta(String baseName, int wx, int wz) {
        Variant v = activeVariant(baseName, wx, wz);
        return v == null ? ZERO : new float[]{ v.dDepth(), v.dScale() };
    }

    /** Debug: "biome/variant" or just "biome". */
    public static String activeVariantName(String baseName, int wx, int wz) {
        Variant v = activeVariant(baseName, wx, wz);
        return v != null ? baseName + "/" + v.name() : baseName;
    }

    /**
     * Position-stable noise bucketed at GRAIN blocks.
     * Every block in the same cell returns the same value → large coherent patches.
     */
    public static float noiseAt(int wx, int wz) {
        int rx = Math.floorDiv(wx, GRAIN);
        int rz = Math.floorDiv(wz, GRAIN);
        long h = (long) rx * 1234567891L ^ (long) rz * 987654323L ^ 0x9E3779B97F4A7C15L;
        h ^= h >>> 33; h *= 0xff51afd7ed558ccdL;
        h ^= h >>> 33; h *= 0xc4ceb9fe1a85ec53L;
        h ^= h >>> 33;
        return (h & 0xFFFFFFL) / (float) 0x1000000L;
    }

    private static final float[] ZERO = { 0f, 0f };
    private static void define(String biome, Variant... variants) { VARIANTS.put(biome, variants); }
    private GotSubBiomeSystem() {}
}
