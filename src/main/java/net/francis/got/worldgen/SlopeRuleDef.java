package net.francis.got.worldgen;

import net.minecraft.world.level.block.state.BlockState;

public record SlopeRuleDef(float minSlope, BlockState block, int depth, float jitter) {}
