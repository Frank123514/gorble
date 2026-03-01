package net.got.block;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Shared class for all regional rock pillar variants.
 * Extends RotatedPillarBlock to inherit the AXIS block-state
 * and correct placement behaviour automatically.
 */
public class RegionalRockPillarBlock extends RotatedPillarBlock {
    public RegionalRockPillarBlock(Properties properties) {
        super(properties.strength(1.5f, 6f).requiresCorrectToolForDrops());
    }

    @Override
    public int getLightBlock(BlockState state) {
        return 15;
    }
}
