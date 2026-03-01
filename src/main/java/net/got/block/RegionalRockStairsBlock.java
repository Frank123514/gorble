package net.got.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RegionalRockStairsBlock extends StairBlock {
    public RegionalRockStairsBlock(BlockBehaviour.Properties properties) {
        super(Blocks.STONE.defaultBlockState(), properties);
    }
}