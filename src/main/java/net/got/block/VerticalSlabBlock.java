package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerticalSlabBlock extends Block implements SimpleWaterloggedBlock {

    public static final MapCodec<VerticalSlabBlock> CODEC = simpleCodec(VerticalSlabBlock::new);

    public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class,
            Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 0, 16, 16, 8);
    private static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 8, 16, 16, 16);
    private static final VoxelShape WEST_SHAPE  = Block.box(0, 0, 0, 8, 16, 16);
    private static final VoxelShape EAST_SHAPE  = Block.box(8, 0, 0, 16, 16, 16);

    public VerticalSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, Type.SINGLE)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public MapCodec<VerticalSlabBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        if (state.getValue(TYPE) == Type.DOUBLE) {
            return Shapes.block();
        }
        return switch (state.getValue(FACING)) {
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        BlockState existing = ctx.getLevel().getBlockState(pos);
        if (existing.is(this) && existing.getValue(TYPE) == Type.SINGLE) {
            return existing.setValue(TYPE, Type.DOUBLE).setValue(WATERLOGGED, false);
        }

        FluidState fluid = ctx.getLevel().getFluidState(pos);
        Direction clickedFace = ctx.getClickedFace();
        Direction facing;

        if (clickedFace.getAxis().isHorizontal()) {

            BlockPos neighborPos = pos.relative(clickedFace.getOpposite());
            BlockState neighbor = ctx.getLevel().getBlockState(neighborPos);
            facing = (neighbor.is(this) && neighbor.getValue(TYPE) == Type.SINGLE)
                    ? neighbor.getValue(FACING)
                    : clickedFace.getOpposite();
        } else {
            facing = ctx.getHorizontalDirection();
        }

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(TYPE, Type.SINGLE)
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        if (state.getValue(TYPE) == Type.DOUBLE || !stack.is(this.asItem())) {
            return false;
        }
        if (!ctx.replacingClickedOnBlock()) {
            return true;
        }
        Direction clickedFace = ctx.getClickedFace();
        if (!clickedFace.getAxis().isHorizontal()) {

            return false;
        }
        Direction existingFacing = state.getValue(FACING);

        return clickedFace == existingFacing.getOpposite();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level,
                                     ScheduledTickAccess scheduledTickAccess,
                                     BlockPos pos, Direction direction, BlockPos neighborPos,
                                     BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return state;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.NORMAL;
    }

    public enum Type implements StringRepresentable {
        SINGLE("single"),
        DOUBLE("double");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}