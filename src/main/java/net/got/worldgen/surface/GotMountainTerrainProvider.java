package net.got.worldgen.surface;

import net.got.worldgen.GotPerlinNoise;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;

/**
 * Layers biome-specific blocks onto terrain above configurable height thresholds —
 * a direct port of LOTR Renewed's {@code MountainTerrainProvider}.
 *
 * <h3>How it works</h3>
 * <p>Each {@link Layer} specifies a minimum Y ({@code above}) above which the
 * provider may replace surface and sub-surface blocks.  Layers are evaluated in
 * definition order; the first passing layer wins.  A small per-column noise value
 * ({@code stoneNoiseDepth}) is subtracted from {@code above} so the threshold
 * isn't a perfectly flat horizontal plane.
 *
 * <h3>Usage</h3>
 * <pre>{@code
 * config.setMountainTerrain(GotMountainTerrainProvider.create(
 *     Layer.builder().above(150).state(Blocks.POWDER_SNOW.defaultBlockState()).topOnly().build(),
 *     Layer.builder().above(120).useStone().build()
 * ));
 * }</pre>
 */
public final class GotMountainTerrainProvider {

    /** Sentinel — never replaces anything. */
    public static final GotMountainTerrainProvider NONE = new GotMountainTerrainProvider(List.of());

    // ── Noise for threshold jitter ────────────────────────────────────────

    /** Frequency for the height-threshold jitter noise. */
    private static final float JITTER_FREQ = 1f / 28f;
    /** Seed for height-threshold jitter — distinct from terrain/surface seeds. */
    private static final int   JITTER_SEED = 0xD3AD_B33F;
    /** Max jitter depth in blocks. */
    private static final int   JITTER_MAX  = 4;

    /**
     * Returns a small per-column noise depth in [0, JITTER_MAX] used to jitter
     * the {@code above} threshold so mountain layer transitions are organic
     * rather than perfectly horizontal.
     */
    public static int computeStoneNoiseDepth(int wx, int wz) {
        float n = GotPerlinNoise.sample(wx * JITTER_FREQ, 0f, wz * JITTER_FREQ, JITTER_SEED);
        // n ∈ [-1,1] → map to [0, JITTER_MAX]
        return (int) ((n + 1f) * 0.5f * JITTER_MAX);
    }

    // ── Layer ─────────────────────────────────────────────────────────────

    /**
     * A single height-conditional replacement rule.
     */
    public static final class Layer {

        private final int        above;         // min Y to apply (exclusive lower bound)
        private final BlockState state;         // block to place (null when useStone = true)
        private final boolean    useStone;      // use the biome's local stone instead
        private final boolean    replaceStone;  // if false, skip when current block is stone
        private final boolean    topOnly;       // only replace the topmost solid block

        private Layer(Builder b) {
            this.above        = b.above;
            this.state        = b.state;
            this.useStone     = b.useStone;
            this.replaceStone = b.replaceStone;
            this.topOnly      = b.topOnly;
        }

        /**
         * True if this layer should replace the block at Y {@code y} given the
         * noise-jittered depth and whether the block is stone.
         */
        public boolean passes(int y, BlockState in, BlockState stone, boolean top, int stoneNoiseDepth) {
            if (topOnly && !top) return false;
            if (!replaceStone && in.is(stone.getBlock())) return false;
            return y >= above - stoneNoiseDepth;
        }

        /** The block to place; if {@code useStone}, returns {@code stone}. */
        public BlockState getState(BlockState stone) {
            return useStone ? stone : state;
        }

        // ── Builder ───────────────────────────────────────────────────────

        public static Builder builder() { return new Builder(); }

        public static final class Builder {
            private int        above        = -1;
            private BlockState state        = Blocks.STONE.defaultBlockState();
            private boolean    useStone     = false;
            private boolean    replaceStone = true;
            private boolean    topOnly      = false;

            private Builder() {}

            /** Y threshold; blocks at or above this Y (minus jitter) are affected. */
            public Builder above(int above) { this.above = above; return this; }

            /** Explicit block to place. */
            public Builder state(BlockState state) {
                this.state    = state;
                this.useStone = false;
                return this;
            }

            /**
             * Use the biome's local stone type instead of an explicit block.
             * Also calls {@link #excludeStone()} so existing stone is not replaced
             * by itself redundantly.
             */
            public Builder useStone() {
                this.state    = null;
                this.useStone = true;
                return excludeStone();
            }

            /**
             * Don't replace blocks that are already equal to the local stone
             * (avoids needlessly touching the majority of the column).
             */
            public Builder excludeStone() { this.replaceStone = false; return this; }

            /** Only replace the topmost surface block, not sub-surface filler. */
            public Builder topOnly() { this.topOnly = true; return this; }

            public Layer build() {
                if (above < 0)
                    throw new IllegalStateException("above Y not set (must be ≥ 0)");
                if (state == null && !useStone)
                    throw new IllegalStateException("must call state() or useStone()");
                return new Layer(this);
            }
        }
    }

    // ── GotMountainTerrainProvider ────────────────────────────────────────

    private final List<Layer> layers;

    private GotMountainTerrainProvider(List<Layer> layers) {
        this.layers = layers;
    }

    public static GotMountainTerrainProvider create(Layer... layers) {
        return new GotMountainTerrainProvider(Arrays.asList(layers));
    }

    public static GotMountainTerrainProvider create(Layer.Builder... builders) {
        return new GotMountainTerrainProvider(
                Arrays.stream(builders).map(Layer.Builder::build).toList());
    }

    /**
     * Returns the replacement block for position (x, z, y), or {@code in}
     * (unchanged) if no layer passes.
     *
     * @param x              world block X
     * @param z              world block Z
     * @param y              world block Y
     * @param in             current block state
     * @param stone          the biome's local stone block (e.g. granite, andesite)
     * @param top            true if this is the topmost solid block in the column
     * @param stoneNoiseDepth per-column jitter value from {@link #computeStoneNoiseDepth}
     */
    public BlockState getReplacement(int x, int z, int y,
                                     BlockState in, BlockState stone,
                                     boolean top, int stoneNoiseDepth) {
        for (Layer layer : layers) {
            if (layer.passes(y, in, stone, top, stoneNoiseDepth)) {
                return layer.getState(stone);
            }
        }
        return in;
    }

    public boolean isNone() { return layers.isEmpty(); }
}