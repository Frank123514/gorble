package net.got.block;

import com.mojang.serialization.MapCodec;
import net.got.init.GotModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nullable;

/**
 * BellowsBlock — a decorative block that can only be placed flush against
 * a ForgeBlock's left or right nozzle face.
 *
 * Placement rules:
 *  - The block being clicked must be a ForgeBlock.
 *  - The face clicked must be the LEFT or RIGHT nozzle side of that forge
 *    (relative to the forge's FACING direction).
 *
 * The bellows face the same direction as the forge they are attached to.
 * SIDE property tells us whether we are on the LEFT or RIGHT nozzle.
 *
 * The model is a simple hand-cranked bellows box: a tapered body with
 * a nozzle tip pointing inward toward the forge.
 */
public class BellowsBlock extends Block implements SimpleWaterloggedBlock {

    public static final MapCodec<BellowsBlock> CODEC = simpleCodec(BellowsBlock::new);

    /** Which horizontal direction the attached forge is facing. */
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    /** Which nozzle this bellows is attached to. */
    public enum NozzleSide implements net.minecraft.util.StringRepresentable {
        LEFT("left"), RIGHT("right");

        private final String name;
        NozzleSide(String name) { this.name = name; }

        @Override public String getSerializedName() { return name; }
        @Override public String toString() { return name; }
    }

    public static final EnumProperty<NozzleSide> SIDE =
            EnumProperty.create("side", NozzleSide.class);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // ── Shapes (one per forge-facing direction, mirrored for left/right) ──────
    // The bellows occupies a slim 4×8×6 region beside the forge base.
    // These are defined for NORTH-facing forge; rotated at runtime via getShape.

    /** Forge facing NORTH: nozzle exits toward -X (left) or +X (right). */
    private static final VoxelShape SHAPE_NORTH_LEFT  = Block.box(-4,  1,  5,  0,  9,  11);
    private static final VoxelShape SHAPE_NORTH_RIGHT = Block.box(16,  1,  5, 20,  9,  11);

    private static final VoxelShape SHAPE_SOUTH_LEFT  = Block.box(16,  1,  5, 20,  9,  11);
    private static final VoxelShape SHAPE_SOUTH_RIGHT = Block.box(-4,  1,  5,  0,  9,  11);

    private static final VoxelShape SHAPE_EAST_LEFT   = Block.box( 5,  1, -4, 11,  9,   0);
    private static final VoxelShape SHAPE_EAST_RIGHT  = Block.box( 5,  1, 16, 11,  9,  20);

    private static final VoxelShape SHAPE_WEST_LEFT   = Block.box( 5,  1, 16, 11,  9,  20);
    private static final VoxelShape SHAPE_WEST_RIGHT  = Block.box( 5,  1, -4, 11,  9,   0);

    // ─────────────────────────────────────────────────────────────────────────

    public BellowsBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SIDE, NozzleSide.LEFT)
                .setValue(WATERLOGGED, false));
    }

    @Override public MapCodec<BellowsBlock> codec() { return CODEC; }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SIDE, WATERLOGGED);
    }

    // ── Placement ─────────────────────────────────────────────────────────────

    /**
     * Returns the placement state if — and only if — the player is clicking
     * a valid nozzle face of a ForgeBlock. Returns null otherwise (prevents
     * placement).
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos clickedPos  = ctx.getClickedPos().relative(ctx.getClickedFace().getOpposite());
        BlockState forgeState = ctx.getLevel().getBlockState(clickedPos);

        if (!forgeState.is(GotModBlocks.FORGE.get())) return null;

        Direction forgeFacing = forgeState.getValue(ForgeBlock.FACING);
        Direction clickedFace = ctx.getClickedFace();

        // Determine which horizontal directions are the nozzle sides for this forge.
        Direction leftSide  = forgeFacing.getCounterClockWise();
        Direction rightSide = forgeFacing.getClockWise();

        NozzleSide side;
        if (clickedFace == leftSide)       side = NozzleSide.LEFT;
        else if (clickedFace == rightSide) side = NozzleSide.RIGHT;
        else return null; // clicked top/bottom/front/back of forge — not a nozzle

        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, forgeFacing)
                .setValue(SIDE, side)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    /**
     * Break the bellows if the forge it's attached to is removed or no longer
     * exposes the correct nozzle face.
     */
    @Override
    public BlockState updateShape(BlockState state, LevelReader level,
                                  net.minecraft.world.level.redstone.NeighborUpdater neighborUpdater,
                                  BlockPos pos, Direction direction, BlockPos neighborPos,
                                  BlockState neighborState, net.minecraft.util.RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            ((LevelAccessor) level).scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay((LevelAccessor) level));
        }

        if (!canSurvive(state, level, pos)) {
            return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction forgeFacing = state.getValue(FACING);
        NozzleSide side       = state.getValue(SIDE);

        // The forge sits on the nozzle-side of this block
        Direction toForge = side == NozzleSide.LEFT
                ? forgeFacing.getClockWise()       // bellows is left, so forge is to the right
                : forgeFacing.getCounterClockWise();

        BlockPos forgePos  = pos.relative(toForge);
        BlockState neighbor = level.getBlockState(forgePos);

        return neighbor.is(GotModBlocks.FORGE.get())
                && neighbor.getValue(ForgeBlock.FACING) == forgeFacing;
    }

    // ── Visuals ───────────────────────────────────────────────────────────────

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
                               CollisionContext ctx) {
        Direction facing = state.getValue(FACING);
        NozzleSide side  = state.getValue(SIDE);
        return switch (facing) {
            case NORTH -> side == NozzleSide.LEFT  ? SHAPE_NORTH_LEFT  : SHAPE_NORTH_RIGHT;
            case SOUTH -> side == NozzleSide.LEFT  ? SHAPE_SOUTH_LEFT  : SHAPE_SOUTH_RIGHT;
            case EAST  -> side == NozzleSide.LEFT  ? SHAPE_EAST_LEFT   : SHAPE_EAST_RIGHT;
            case WEST  -> side == NozzleSide.LEFT  ? SHAPE_WEST_LEFT   : SHAPE_WEST_RIGHT;
            default    -> Shapes.block();
        };
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
