package net.got.block;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.*;
public class CloveLogBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public CloveLogBlock(Properties p) { super(p.sound(SoundType.WOOD).strength(2f).ignitedByLava()); this.registerDefaultState(this.stateDefinition.any().setValue(AXIS,Direction.Axis.Y)); }
    @Override public int getLightBlock(BlockState s) { return 15; }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> b) { super.createBlockStateDefinition(b); b.add(AXIS); }
    @Override public BlockState getStateForPlacement(BlockPlaceContext c) { return super.getStateForPlacement(c).setValue(AXIS,c.getClickedFace().getAxis()); }
    @Override public BlockState rotate(BlockState s,Rotation r) { return RotatedPillarBlock.rotatePillar(s,r); }
    @Override public int getFlammability(BlockState s,BlockGetter w,BlockPos p,Direction f) { return 5; }
}