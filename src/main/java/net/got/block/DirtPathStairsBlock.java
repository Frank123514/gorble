package net.got.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DirtPathStairsBlock extends StairBlock {
    
    private static final VoxelShape CLIP_BOTTOM = Block.box(-16.0D, 0.0D, -16.0D, 32.0D, 15.0D, 32.0D);
    private static final VoxelShape CLIP_TOP    = Block.box(-16.0D, 1.0D, -16.0D, 32.0D, 16.0D, 32.0D);

    public DirtPathStairsBlock(BlockState baseState, Properties properties) {
        super(baseState, properties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape shape = super.getShape(state, level, pos, context);
        VoxelShape clip = state.getValue(HALF) == Half.TOP ? CLIP_TOP : CLIP_BOTTOM;
        return Shapes.join(shape, clip, BooleanOp.AND);
    }
}