package net.got.worldgen.surface;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

/**
 * Per-biome surface patch configuration — vanilla MC noise driven.
 *
 * <p>All threshold heights are calibrated to the new LOTR Renewed-equivalent
 * depth/scale values in {@link net.got.worldgen.layer.GotBiomeRegistry}:
 *
 * <ul>
 *   <li>Plains (north) avg surface ≈ Y 71 — stone never shows at surface</li>
 *   <li>Hills avg ≈ Y 91 — stone outcrops from Y 85 upward</li>
 *   <li>Mountains avg ≈ Y 116 — stone from Y 90, snow from Y 128, powder from Y 142</li>
 *   <li>Frostfangs avg ≈ Y 128 — stone from Y 85, snow from Y 118, powder from Y 134</li>
 *   <li>Rivers — gravel/sand bottom matching LOTR river appearance</li>
 * </ul>
 */
public final class GotBiomeSurfaces {

    // ── Weighted block choice ─────────────────────────────────────────────

    public static final class PatchEntry {
        private final BlockState[] states;
        private final int[]        weights;
        private final int          total;

        private PatchEntry(BlockState[] states, int[] weights) {
            this.states  = states;
            this.weights = weights;
            int t = 0;
            for (int w : weights) t += w;
            this.total = t;
        }

        public static PatchEntry of(BlockState state) {
            return new PatchEntry(new BlockState[]{ state }, new int[]{ 1 });
        }

        public static PatchEntry of(BlockState s1, int w1, BlockState s2, int w2) {
            return new PatchEntry(new BlockState[]{ s1, s2 }, new int[]{ w1, w2 });
        }

        public BlockState pick(RandomSource rand) {
            if (states.length == 1) return states[0];
            int pick = rand.nextInt(total);
            int acc  = 0;
            for (int i = 0; i < states.length; i++) {
                acc += weights[i];
                if (pick < acc) return states[i];
            }
            return states[states.length - 1];
        }
    }

    // ── Per-biome config ──────────────────────────────────────────────────

    public static final class BiomeConfig {
        public final Double     minThreshold;
        public final Double     maxThreshold;
        public final boolean    useSecondary;
        public final PatchEntry mainPatch;
        public final boolean    podzol;
        public final int        powderSnowAbove;
        public final int        snowBlockAbove;
        public final int        stoneAbove;

        private BiomeConfig(Builder b) {
            this.minThreshold    = b.minThreshold;
            this.maxThreshold    = b.maxThreshold;
            this.useSecondary    = b.useSecondary;
            this.mainPatch       = b.mainPatch;
            this.podzol          = b.podzol;
            this.powderSnowAbove = b.powderSnowAbove;
            this.snowBlockAbove  = b.snowBlockAbove;
            this.stoneAbove      = b.stoneAbove;
        }

        public boolean hasPatch() { return minThreshold != null && mainPatch != null; }

        public static Builder builder() { return new Builder(); }

        public static final class Builder {
            private Double     minThreshold    = null;
            private Double     maxThreshold    = null;
            private boolean    useSecondary    = false;
            private PatchEntry mainPatch       = null;
            private boolean    podzol          = false;
            private int        powderSnowAbove = -1;
            private int        snowBlockAbove  = -1;
            private int        stoneAbove      = -1;

            private Builder() {}

            public Builder patch(PatchEntry entry, double min, double max) {
                this.mainPatch    = entry;
                this.minThreshold = min;
                this.maxThreshold = max;
                return this;
            }

            public Builder secondary()            { this.useSecondary    = true; return this; }
            public Builder podzol()               { this.podzol          = true; return this; }
            public Builder powderSnowAbove(int y) { this.powderSnowAbove = y;   return this; }
            public Builder snowBlockAbove(int y)  { this.snowBlockAbove  = y;   return this; }
            public Builder stoneAbove(int y)      { this.stoneAbove      = y;   return this; }

            public BiomeConfig build() { return new BiomeConfig(this); }
        }
    }

    // ── Registry ──────────────────────────────────────────────────────────

    private static final Map<String, BiomeConfig> REGISTRY = new HashMap<>();

    static {

        // ── NORTH — Rohan / Eriador rolling plains ─────────────────────
        // Avg surface Y ≈ 71.  Scattered gravel flecks on gently sloping ground,
        // coarse dirt on exposed spots — matches LOTR's "Rohan" surface feel.
        register("north", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState(),        7,
                                Blocks.COARSE_DIRT.defaultBlockState(),   3),
                        -0.08, 0.06)
                .build());

        // ── BARROWLANDS — Barrow-downs ─────────────────────────────────
        // Avg surface Y ≈ 79. Exposed coarse dirt and gravel on barrow tops.
        register("barrowlands", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.COARSE_DIRT.defaultBlockState(),   2,
                                Blocks.GRAVEL.defaultBlockState(),        1),
                        -0.08, 0.06)
                .build());

        // ── WOLFSWOOD — Mirkwood-edge dense forest ─────────────────────
        // Avg surface Y ≈ 75. Podzol + coarse dirt under the canopy.
        register("wolfswood", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.PODZOL.defaultBlockState(),        3,
                                Blocks.COARSE_DIRT.defaultBlockState(),   1),
                        -0.08, 0.06)
                .podzol()
                .build());

        // ── HAUNTED FOREST — Dead Marshes canopy ───────────────────────
        // Avg surface Y ≈ 71. Dense podzol with secondary noise for patchy feel.
        register("haunted_forest", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.PODZOL.defaultBlockState(),        2,
                                Blocks.COARSE_DIRT.defaultBlockState(),   1),
                        -0.12, 0.02)
                .secondary()
                .podzol()
                .build());

        // ── IRONWOOD — Fangorn lower slopes ────────────────────────────
        // Avg surface Y ≈ 79. Dense podzol / coarse dirt forest floor.
        register("ironwood", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.PODZOL.defaultBlockState(),        1,
                                Blocks.COARSE_DIRT.defaultBlockState(),   1),
                        -0.14, 0.00)
                .secondary()
                .podzol()
                .build());

        // ── NORTH HILLS — Emyn Muil ─────────────────────────────────────
        // Avg surface Y ≈ 91.  Stone outcrops start at Y 85 — matching LOTR's
        // Emyn Muil where rock breaks through the grass on upper slopes.
        register("north_hills", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState(),        3,
                                Blocks.STONE.defaultBlockState(),         1),
                        -0.10, 0.04)
                .stoneAbove(85)
                .build());

        // ── NORTH MOUNTAINS — Misty Mountains ──────────────────────────
        // Avg surface Y ≈ 116, peaks reaching Y 140+.
        // Stone face from Y 90.  Snow block from Y 128.  Powder snow crown Y 142.
        // Calibrated so the mountain spine looks capped in white like LOTR.
        register("north_mountains", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.STONE.defaultBlockState()), -0.12, 0.02)
                .stoneAbove(90)
                .snowBlockAbove(128)
                .powderSnowAbove(142)
                .build());

        // ── FROSTFANGS — Caradhras / extreme north peaks ────────────────
        // Avg surface Y ≈ 128, peaks Y 155+.
        // Aggressive stone and snow thresholds so even mid-slopes are snow-clad.
        register("frostfangs", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.STONE.defaultBlockState()), -0.06, 0.08)
                .stoneAbove(85)
                .snowBlockAbove(118)
                .powderSnowAbove(134)
                .build());

        // ── ALWAYS WINTER — Forodwaith frozen plateau ───────────────────
        // Avg surface Y ≈ 91.  Lower snow threshold — the whole plateau is cold.
        register("always_winter", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState()), -0.10, 0.04)
                .secondary()
                .snowBlockAbove(82)
                .powderSnowAbove(96)
                .build());

        // ── STONY SHORE — coastal rock shelves ─────────────────────────
        // At sea level.  Heavy gravel + stone — matches LOTR coastal biomes.
        register("stony_shore", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState(),        4,
                                Blocks.STONE.defaultBlockState(),         1),
                        -0.10, 0.04)
                .build());

        // ── IRON HILLS ──────────────────────────────────────────────────
        // Avg surface Y ≈ 91.  Stone face starts at Y 88 (very rocky biome).
        register("iron_hills", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.STONE.defaultBlockState(),         3,
                                Blocks.GRAVEL.defaultBlockState(),        2),
                        -0.06, 0.08)
                .secondary()
                .stoneAbove(88)
                .build());

        // ── SHEEPSHEAD HILLS ─────────────────────────────────────────────
        // Avg surface Y ≈ 83.  Sparse gravel — open wind-blown downs.
        register("sheepshead_hills", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState()), -0.04, 0.10)
                .secondary()
                .build());

        // ── RIVER ───────────────────────────────────────────────────────
        // Average bed Y ≈ 60 (3 below sea).  LOTR rivers have a gravel/sand
        // bottom — apply it over a wide noise range so the whole riverbed
        // is gravel-covered, not just scattered patches.
        register("river", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState(),        3,
                                Blocks.SAND.defaultBlockState(),          1),
                        -0.60, 0.60)
                .build());

        // ── NECK RIVER ──────────────────────────────────────────────────
        // Neck swamp channels: muddy clay-and-gravel bottom.
        register("neck_river", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState(),        2,
                                Blocks.CLAY.defaultBlockState(),          1),
                        -0.60, 0.60)
                .build());

        // ── FROZEN RIVER ─────────────────────────────────────────────────
        // Frozen channels: gravel bottom under the ice.
        register("frozen_river", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState()), -0.60, 0.60)
                .build());
    }

    // ── Public API ─────────────────────────────────────────────────────────

    private static void register(String name, BiomeConfig config) { REGISTRY.put(name, config); }

    public static BiomeConfig getConfig(String biomePath) { return REGISTRY.get(biomePath); }

    private GotBiomeSurfaces() {}
}