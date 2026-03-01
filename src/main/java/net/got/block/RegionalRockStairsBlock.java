package net.got.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RegionalRockStairsBlock extends StairBlock {
    // Uses Blocks.STONE.defaultBlockState() as the base — shape is identical for all
    // full-cube stone blocks, so this is safe and avoids needing a second constructor arg.
    public RegionalRockStairsBlock(BlockBehaviour.Properties properties) {
        super(Blocks.STONE.defaultBlockState(), properties);
    }
}
