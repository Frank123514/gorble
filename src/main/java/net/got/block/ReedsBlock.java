package net.got.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;

public class ReedsBlock extends DoublePlantBlock {

    public ReedsBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState groundState, BlockGetter level, BlockPos groundPos) {
        
        return groundState.is(BlockTags.DIRT)
                || groundState.is(Blocks.SAND)
                || groundState.is(Blocks.GRAVEL)
                || groundState.is(Blocks.CLAY)
                || groundState.is(Blocks.MUD);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        }

        BlockPos groundPos = pos.below();
        if (!mayPlaceOn(level.getBlockState(groundPos), level, groundPos)) {
            return false;
        }
        return hasAdjacentWater(level, pos);
    }

    private boolean hasAdjacentWater(LevelReader level, BlockPos pos) {
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            
            FluidState side = level.getFluidState(pos.relative(dir));
            if (side.is(FluidTags.WATER)) return true;

            FluidState sideBelow = level.getFluidState(pos.relative(dir).below());
            if (sideBelow.is(FluidTags.WATER)) return true;
        }
        return false;
    }
}