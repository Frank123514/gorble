package net.got.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

/**
 * Stripped log for all GOT wood types.
 * Identical behaviour to the regular log blocks — rotatable on all axes,
 * flammable — but carries no stripped-block reference of its own.
 */
public class GotStrippedLogBlock extends Block {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public GotStrippedLogBlock(Properties p) {
        super(p.sound(SoundType.WOOD).strength(2f).ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public int getLightBlock(BlockState state) { return 15; }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(AXIS, ctx.getClickedFace().getAxis());
    }

    @Override
    public BlockState rotate(BlockState state,
            net.minecraft.world.level.block.Rotation rot) {
        return RotatedPillarBlock.rotatePillar(state, rot);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world,
            BlockPos pos, Direction face) { return 5; }
}
