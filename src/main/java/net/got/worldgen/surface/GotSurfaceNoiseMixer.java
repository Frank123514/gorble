package net.got.worldgen.surface;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;

/**
 * Replaces top and filler blocks on the terrain surface based on noise-driven
 * conditions — a direct port of LOTR Renewed's {@code SurfaceNoiseMixer}.
 *
 * <h3>How it works</h3>
 * <p>Each {@link Condition} picks one of the four {@link GotSurfaceNoiseBank}
 * channels and evaluates it at (x, z) with configurable scales.  If the noise
 * value exceeds a {@code threshold}, the condition's block (or one of its
 * weighted blocks, chosen randomly) replaces whatever the vanilla surface builder
 * placed.  Conditions are tested in order; the first that passes wins.
 *
 * <h3>Usage</h3>
 * <pre>{@code
 * GotSurfaceNoiseMixer mixer = GotSurfaceNoiseMixer.createNoiseMixer(
 *     Condition.builder()
 *         .channel(1).scales(0.4, 0.07).threshold(0.25)
 *         .state(Blocks.GRAVEL)
 *         .build()
 * );
 * }</pre>
 */
public final class GotSurfaceNoiseMixer {

    /** Sentinel instance that never replaces anything. */
    public static final GotSurfaceNoiseMixer NONE = new GotSurfaceNoiseMixer(List.of());

    // ── Condition ─────────────────────────────────────────────────────────

    /**
     * A single noise-driven replacement rule.
     */
    public static final class Condition {

        private final int      channelIndex;  // 1–4
        private final double[] scales;
        private final double[] xScales;
        private final double[] zScales;
        private final int[]    weights;
        private final double   threshold;
        private final BlockState[] states;    // block choices
        private final int[]        stateWeights;
        private final boolean  topOnly;

        private Condition(Builder b) {
            this.channelIndex  = b.channelIndex;
            this.scales        = b.scales;
            this.xScales       = b.xScales;
            this.zScales       = b.zScales;
            this.weights       = b.weights;
            this.threshold     = b.threshold;
            this.states        = b.states;
            this.stateWeights  = b.stateWeights;
            this.topOnly       = b.topOnly;
        }

        /** Returns true if this condition is active at (x, z). */
        public boolean passes(int x, int z, boolean top) {
            if (topOnly && !top) return false;
            double noise = GotSurfaceNoiseBank.getChannel(
                    channelIndex, x, z, scales, xScales, zScales, weights);
            return noise > threshold;
        }

        /** Picks the replacement block (weighted-random if multiple states set). */
        public BlockState getState(RandomSource random) {
            if (states.length == 1) return states[0];

            int total = 0;
            for (int w : stateWeights) total += w;
            int pick = random.nextInt(total);
            int acc  = 0;
            for (int i = 0; i < states.length; i++) {
                acc += stateWeights[i];
                if (pick < acc) return states[i];
            }
            return states[states.length - 1];
        }

        // ── Builder ───────────────────────────────────────────────────────

        public static Builder builder() { return new Builder(); }

        public static final class Builder {
            private int      channelIndex = 1;
            private double[] scales;
            private double[] xScales;
            private double[] zScales;
            private int[]    weights;
            private double   threshold = Double.NEGATIVE_INFINITY;
            private BlockState[] states;
            private int[]        stateWeights;
            private boolean  topOnly = false;

            private Builder() {}

            /** Which noise channel to evaluate (1–4). */
            public Builder channel(int channelIndex) {
                this.channelIndex = channelIndex; return this;
            }

            /** Spatial frequency multipliers — one per sample; required. */
            public Builder scales(double... scales) {
                this.scales = scales; return this;
            }

            /** Optional per-sample X anisotropy. */
            public Builder xScales(double... xScales) {
                this.xScales = xScales; return this;
            }

            /** Optional per-sample Z anisotropy. */
            public Builder zScales(double... zScales) {
                this.zScales = zScales; return this;
            }

            /** Optional per-sample contribution weights. */
            public Builder weights(int... weights) {
                this.weights = weights; return this;
            }

            /** Noise must exceed this value for the condition to pass. */
            public Builder threshold(double threshold) {
                this.threshold = threshold; return this;
            }

            /** Single replacement block (weight = 1). */
            public Builder state(BlockState state) {
                this.states       = new BlockState[]{ state };
                this.stateWeights = new int[]{ 1 };
                return this;
            }

            /**
             * Multiple replacement blocks with weights.
             * Pass alternating (BlockState, Integer) pairs:
             * {@code states(Blocks.GRAVEL.defaultBlockState(), 3, Blocks.STONE.defaultBlockState(), 1)}
             */
            public Builder states(Object... entries) {
                int n = entries.length / 2;
                this.states       = new BlockState[n];
                this.stateWeights = new int[n];
                for (int i = 0; i < n; i++) {
                    this.states[i]       = (BlockState) entries[i * 2];
                    this.stateWeights[i] = (Integer)    entries[i * 2 + 1];
                }
                return this;
            }

            /** Only replace the topmost solid block in a column (not filler). */
            public Builder topOnly() {
                this.topOnly = true; return this;
            }

            public Condition build() {
                if (channelIndex < 1 || channelIndex > 4)
                    throw new IllegalStateException("channelIndex must be 1–4");
                if (scales == null || scales.length == 0)
                    throw new IllegalStateException("scales must be set");
                if (threshold == Double.NEGATIVE_INFINITY)
                    throw new IllegalStateException("threshold must be set");
                if (states == null || states.length == 0)
                    throw new IllegalStateException("at least one block state must be set");
                return new Condition(this);
            }
        }
    }

    // ── GotSurfaceNoiseMixer ──────────────────────────────────────────────

    private final List<Condition> conditions;

    private GotSurfaceNoiseMixer(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public static GotSurfaceNoiseMixer createNoiseMixer(Condition... conditions) {
        return new GotSurfaceNoiseMixer(Arrays.asList(conditions));
    }

    public static GotSurfaceNoiseMixer createNoiseMixer(Condition.Builder... builders) {
        return new GotSurfaceNoiseMixer(
                Arrays.stream(builders).map(Condition.Builder::build).toList());
    }

    /**
     * Returns the replacement block for position (x, z), or {@code in}
     * (unchanged) if no condition passes.
     *
     * @param x    world block X
     * @param z    world block Z
     * @param in   the current block state at this position
     * @param top  true if this is the topmost solid block in the column
     * @param rand used for weighted-random block selection
     */
    public BlockState getReplacement(int x, int z, BlockState in, boolean top, RandomSource rand) {
        for (Condition c : conditions) {
            if (c.passes(x, z, top)) return c.getState(rand);
        }
        return in;
    }

    public boolean isNone() { return conditions.isEmpty(); }
}