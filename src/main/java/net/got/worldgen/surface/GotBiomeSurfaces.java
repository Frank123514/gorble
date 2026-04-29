package net.got.worldgen.surface;

import net.got.init.GotModBlocks;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

/**
 * Per-biome surface patch configuration — vanilla MC noise driven.
 *
 * <p>Uses {@code minecraft:surface} and {@code minecraft:surface_secondary} noise
 * with NARROW threshold bands for fine scattered flecks on ALL patch blocks.
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
        // ALL biomes use NARROW bands (~0.14 width) for fine scattered flecks.
        // This makes BOTH blocks in a PatchEntry scatter as tiny flecks.

        // NORTH — gravel (70%) + coarse dirt (30%), both as fine flecks
        register("north", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState(),        7,
                                Blocks.COARSE_DIRT.defaultBlockState(), 3),
                        -0.08, 0.06)
                .build());

        // NORTH HILLS — gravel (75%) + stone (25%), fine flecks
        register("north_hills", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState(), 3,
                                Blocks.STONE.defaultBlockState(),  1),
                        -0.10, 0.04)
                .build());

        // NORTH MOUNTAINS — stone flecks + height layers
        register("north_mountains", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.STONE.defaultBlockState()), -0.12, 0.02)
                .powderSnowAbove(145)
                .snowBlockAbove(130)
                .stoneAbove(105)
                .build());

        // FROSTFANGS — stone flecks
        register("frostfangs", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.STONE.defaultBlockState()), -0.06, 0.08)
                .powderSnowAbove(140)
                .snowBlockAbove(120)
                .stoneAbove(90)
                .build());

        // ALWAYS WINTER — gravel flecks
        register("always_winter", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState()), -0.10, 0.04)
                .secondary()
                .powderSnowAbove(100)
                .snowBlockAbove(88)
                .build());

        // WOLFSWOOD — podzol + coarse dirt flecks
        register("wolfswood", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.PODZOL.defaultBlockState(),        3,
                                Blocks.COARSE_DIRT.defaultBlockState(), 1),
                        -0.08, 0.06)
                .podzol()
                .build());

        // HAUNTED FOREST — podzol flecks, secondary noise
        register("haunted_forest", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.PODZOL.defaultBlockState(),        2,
                                Blocks.COARSE_DIRT.defaultBlockState(), 1),
                        -0.12, 0.02)
                .secondary()
                .podzol()
                .build());

        // IRONWOOD — dense podzol flecks, secondary noise
        register("ironwood", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.PODZOL.defaultBlockState(),        1,
                                Blocks.COARSE_DIRT.defaultBlockState(), 1),
                        -0.14, 0.00)
                .secondary()
                .podzol()
                .build());

        // BARROWLANDS — coarse dirt + gravel flecks
        register("barrowlands", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.COARSE_DIRT.defaultBlockState(), 2,
                                Blocks.GRAVEL.defaultBlockState(),      1),
                        -0.08, 0.06)
                .build());

        // STONY SHORE — gravel + stone flecks
        register("stony_shore", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState(), 4,
                                Blocks.STONE.defaultBlockState(),  1),
                        -0.10, 0.04)
                .build());

        // IRON HILLS — stone + gravel flecks, secondary noise
        register("iron_hills", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.STONE.defaultBlockState(),  3,
                                Blocks.GRAVEL.defaultBlockState(), 2),
                        -0.06, 0.08)
                .secondary()
                .stoneAbove(110)
                .build());

        // SHEEPSHEAD HILLS — sparse gravel flecks
        register("sheepshead_hills", BiomeConfig.builder()
                .patch(PatchEntry.of(Blocks.GRAVEL.defaultBlockState()), -0.04, 0.10)
                .secondary()
                .build());
    }

    // ── Public API ────────────────────────────────────────────────────────

    private static void register(String name, BiomeConfig config) { REGISTRY.put(name, config); }

    public static BiomeConfig getConfig(String biomePath) { return REGISTRY.get(biomePath); }

    private GotBiomeSurfaces() {}
}