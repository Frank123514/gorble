package net.got.worldgen;

import java.util.HashMap;
import java.util.Map;

/**
 * LOTR Renewed-style biome region-variant system for the GoT mod.
 *
 * <h2>What variants do</h2>
 * <p>Variants never change which biome a position belongs to. They modify two
 * terrain parameters in-place, matching the LOTR Renewed sub-biome/region system:
 *
 * <ul>
 *   <li><b>dScale</b> — changes terrain hilliness (primary knob).
 *       Positive = hillier, negative = flatter. Transitions are smooth because
 *       the 3D noise field is continuous — no staircase artefacts.</li>
 *   <li><b>dDepth</b> — shifts the baseline elevation. Keep small (≤ ±0.10) to
 *       avoid terrace artefacts at patch boundaries. Larger negative values are
 *       used only for the "foothills" mountain variant.</li>
 *   <li><b>treeDensityBonus</b> — extra tree-placement passes. 0 = unchanged.</li>
 * </ul>
 *
 * <h2>Region grain</h2>
 * <p>Each variant is determined by a hash bucketed at {@link #GRAIN} blocks.
 * GRAIN = 300 matches LOTR Renewed's large coherent sub-region patches — you
 * travel 300+ blocks before the sub-region type changes, creating clearly
 * recognisable landscape zones within each biome.
 *
 * <h2>LOTR Renewed biome analogues and their variants</h2>
 * <pre>
 *  north        ≈ Rohan / Eriador:  highland, valley, forest_patch
 *  barrowlands  ≈ Barrow-downs:     barrow_ridge, flat_moor
 *  wolfswood    ≈ Mirkwood edge:    deep_wood, ridge, clearing
 *  haunted_forest ≈ Dol Guldur env: deep_hollow
 *  ironwood     ≈ Fangorn lower:    ironwood_slopes
 *  north_hills  ≈ Emyn Muil:        crag, valley, forested_slopes
 *  north_mountains ≈ Misty Mtns:    peak, foothills (key LOTR feature), valley
 *  always_winter ≈ Forodwaith:      ice_fields, frozen_ridge
 *  frostfangs   ≈ Forodwaith peak:  glacier_shelf
 *  neck         ≈ Dagorlad bog:     marsh_flat
 *  iron_hills   ≈ Iron Hills:       rocky_crest, forested_skirts
 *  sheepshead_hills ≈ Emyn Beraid:  high_downs, dale
 * </pre>
 */
public final class GotSubBiomeSystem {

    /**
     * Side-length in blocks of each variant hash cell.
     * 300 blocks matches LOTR Renewed's large coherent region patches.
     */
    public static final int GRAIN = 300;

    /**
     * @param name             Human-readable label (shown in F3 debug screen).
     * @param threshold        Cumulative probability gate [0,1]. Ascending order per biome.
     * @param dDepth           Baseline elevation delta. Keep ≤ ±0.10 unless making
     *                         a foothills transition. No artifact risk below 0.10.
     * @param dScale           Hilliness delta. Primary terrain knob. No artifact risk.
     * @param treeDensityBonus Extra tree passes. 0 = none, 8–16 = denser forest.
     */
    public record Variant(String name, float threshold, float dDepth, float dScale, int treeDensityBonus) {}

    private static final Map<String, Variant[]> VARIANTS = new HashMap<>();

    static {

        // ═══════════════════════════════════════════════════════════════════
        // NORTH  (depth 0.125, scale 0.15) — Rohan / Eriador rolling plains
        //
        //  highland    12 % → pronounced upland patches, hillier
        //  valley      22 % cumul → gentle lowland pocket, slightly flat
        //  forest_patch 38 % cumul → tree-dense sheltered areas
        // ═══════════════════════════════════════════════════════════════════
        define("north",
                new Variant("highland",     0.12f,  0.00f, +0.120f,  0),
                new Variant("valley",       0.22f, -0.02f, -0.050f,  0),
                new Variant("forest_patch", 0.38f,  0.00f, -0.020f, 12)
        );

        // ═══════════════════════════════════════════════════════════════════
        // BARROWLANDS  (depth 0.30, scale 0.25) — Barrow-downs
        //
        //  barrow_ridge 15 % → steep mound country, pronounced barrow shapes
        //  flat_moor    28 % cumul → open desolate moorland
        // ═══════════════════════════════════════════════════════════════════
        define("barrowlands",
                new Variant("barrow_ridge", 0.15f,  0.00f, +0.160f,  0),
                new Variant("flat_moor",    0.28f,  0.00f, -0.080f,  0)
        );

        // ═══════════════════════════════════════════════════════════════════
        // WOLFSWOOD  (depth 0.20, scale 0.20) — Mirkwood edge / dense pine forest
        //
        //  deep_wood  22 % → flat dense canopy, uniform forest floor
        //  ridge      32 % cumul → forested ridge — hillier, extra tree pass
        //  clearing   40 % cumul → open glade with sparse trees
        // ═══════════════════════════════════════════════════════════════════
        define("wolfswood",
                new Variant("deep_wood", 0.22f,  0.00f, -0.050f, 16),
                new Variant("ridge",     0.32f,  0.00f, +0.140f,  8),
                new Variant("clearing",  0.40f,  0.00f, -0.020f,  0)
        );

        // ═══════════════════════════════════════════════════════════════════
        // HAUNTED FOREST  (depth 0.10, scale 0.12) — Dead Marshes canopy
        //
        //  deep_hollow 15 % → sunken flat bowl — eerie low ground
        // ═══════════════════════════════════════════════════════════════════
        define("haunted_forest",
                new Variant("deep_hollow", 0.15f, -0.02f, -0.100f,  8)
        );

        // ═══════════════════════════════════════════════════════════════════
        // IRONWOOD  (depth 0.25, scale 0.25) — Fangorn lower slopes
        //
        //  ironwood_slopes 18 % → rocky exposed ridges, hillier, denser trees
        // ═══════════════════════════════════════════════════════════════════
        define("ironwood",
                new Variant("ironwood_slopes", 0.18f, 0.00f, +0.130f,  8)
        );

        // ═══════════════════════════════════════════════════════════════════
        // NORTH HILLS  (depth 0.55, scale 0.35) — Emyn Muil
        //
        //  crag           8 % → dramatic exposed rocky crags, highest within hills
        //  valley        20 % cumul → sheltered inter-hill valleys
        //  forested_slope 35 % cumul → lower wooded flanks
        // ═══════════════════════════════════════════════════════════════════
        define("north_hills",
                new Variant("crag",           0.08f,  0.00f, +0.220f,  0),
                new Variant("valley",         0.20f, -0.08f, -0.140f,  0),
                new Variant("forested_slope", 0.35f,  0.00f, -0.040f,  8)
        );

        // ═══════════════════════════════════════════════════════════════════
        // NORTH MOUNTAINS  (depth 1.10, scale 0.55) — Misty Mountains
        //
        //  KEY LOTR FEATURE — foothills variant:
        //  The foothills sub-region lowers the mountain base significantly,
        //  creating the characteristic stepped approach — wide lower slopes
        //  before the true peaks, exactly as seen in LOTR Renewed.
        //
        //  peak       12 % → extreme altitude zone, highest and sharpest
        //  foothills  40 % cumul → mountain foothills — much lower baseline,
        //                          moderate hilliness; this is the dominant view
        //  valley     52 % cumul → sheltered mountain glens, pine-forested
        // ═══════════════════════════════════════════════════════════════════
        define("north_mountains",
                new Variant("peak",      0.12f, +0.080f, +0.120f,  0),
                new Variant("foothills", 0.40f, -0.350f, -0.220f,  4),
                new Variant("valley",    0.52f, -0.220f, -0.180f,  4)
        );

        // ═══════════════════════════════════════════════════════════════════
        // ALWAYS WINTER  (depth 0.50, scale 0.25) — Forodwaith plateau
        //
        //  ice_fields    18 % → desolate flat frozen plain
        //  frozen_ridge  28 % cumul → raised icy ridgeline
        // ═══════════════════════════════════════════════════════════════════
        define("always_winter",
                new Variant("ice_fields",   0.18f, -0.04f, -0.100f,  0),
                new Variant("frozen_ridge", 0.28f,  0.00f, +0.100f,  0)
        );

        // ═══════════════════════════════════════════════════════════════════
        // FROSTFANGS  (depth 1.40, scale 0.65) — Caradhras / extreme north
        //
        //  glacier_shelf 12 % → flat ice shelf at foot of extreme peaks,
        //                        dramatic scale drop matching LOTR glacier zones
        // ═══════════════════════════════════════════════════════════════════
        define("frostfangs",
                new Variant("glacier_shelf", 0.12f, -0.100f, -0.200f,  0)
        );

        // ═══════════════════════════════════════════════════════════════════
        // NECK  (depth -0.05, scale 0.05) — Dagorlad / swamp lowlands
        //
        //  marsh_flat 14 % → deeper standing-water zone, almost perfectly flat
        // ═══════════════════════════════════════════════════════════════════
        define("neck",
                new Variant("marsh_flat", 0.14f, -0.020f, -0.030f,  0)
        );

        // ═══════════════════════════════════════════════════════════════════
        // IRON HILLS  (depth 0.55, scale 0.35)
        //
        //  rocky_crest     10 % → exposed ore-bearing summit
        //  forested_skirts 25 % cumul → lower wooded hill flanks
        // ═══════════════════════════════════════════════════════════════════
        define("iron_hills",
                new Variant("rocky_crest",     0.10f,  0.00f, +0.180f,  0),
                new Variant("forested_skirts", 0.25f,  0.00f, -0.060f,  8)
        );

        // ═══════════════════════════════════════════════════════════════════
        // SHEEPSHEAD HILLS  (depth 0.35, scale 0.28)
        //
        //  high_downs 12 % → open windy summit
        //  dale       22 % cumul → flat sheltered valley between hills
        // ═══════════════════════════════════════════════════════════════════
        define("sheepshead_hills",
                new Variant("high_downs", 0.12f,  0.00f, +0.150f,  0),
                new Variant("dale",       0.22f, -0.06f, -0.120f,  0)
        );
    }

    // ── Public API ─────────────────────────────────────────────────────────

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

    /** Returns {dDepth, dScale} for the terrain sampler, or {0,0} if no variant. */
    public static float[] getTerrainDelta(String baseName, int wx, int wz) {
        Variant v = activeVariant(baseName, wx, wz);
        return v == null ? ZERO : new float[]{ v.dDepth(), v.dScale() };
    }

    /** Debug label: "biome/variant" or just "biome". */
    public static String activeVariantName(String baseName, int wx, int wz) {
        Variant v = activeVariant(baseName, wx, wz);
        return v != null ? baseName + "/" + v.name() : baseName;
    }

    /**
     * Position-stable noise bucketed at {@link #GRAIN} blocks.
     * Every block in the same cell returns the same value → large coherent patches
     * matching LOTR Renewed's region-variant system.
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