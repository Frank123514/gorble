package net.got.worldgen;

import net.minecraft.world.level.block.state.BlockState;

/**
 * One entry in a biome's slope-surface rule list.
 *
 * <p>When the terrain slope at a surface column meets or exceeds
 * {@link #minSlope()} (with per-column noise jitter applied), the top
 * {@link #depth()} blocks are replaced with {@link #block()}.
 *
 * @param minSlope  Maximum height-diff to any immediate neighbour that triggers
 *                  the rule.  With the ±1 sampling used by
 *                  {@link SlopeSurfaceResolver#computeSlope}: {@code 1.0} = any
 *                  step edge, {@code 2.0} = 2-block drop, {@code 3.0+} = cliff.
 * @param block     Block state to place in matching columns.
 * @param depth     How many blocks below the surface top to replace (≥ 1).
 * @param jitter    Noise magnitude added to the threshold per-column.  Positive
 *                  values break smooth blob edges into organic shapes; {@code 0}
 *                  disables jitter for a hard edge (default {@code 0.25}).
 */
public record SlopeRuleDef(float minSlope, BlockState block, int depth, float jitter) {}
