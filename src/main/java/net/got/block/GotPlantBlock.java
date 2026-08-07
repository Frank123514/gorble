package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GotPlantBlock extends BushBlock {

    public static final MapCodec<BushBlock> CODEC = simpleCodec(GotPlantBlock::new);
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public GotPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return (pState.is(BlockTags.DIRT) || pState.is(Blocks.FARMLAND))
                && pState.isFaceSturdy(pLevel, pPos, Direction.UP);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}