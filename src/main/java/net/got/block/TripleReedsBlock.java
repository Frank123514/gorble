package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

/**
 * TripleReedsBlock — a 3-block-tall waterloggable reed.
 * Can be placed on water-edge terrain OR directly submerged in water.
 */
public class TripleReedsBlock extends TriplePlantBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<BushBlock> CODEC =
            MapCodec.unit(() -> new TripleReedsBlock(Properties.of()));

    @Override
    public MapCodec<BushBlock> codec() { return CODEC; }

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TripleReedsBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder); // adds SECTION
        builder.add(WATERLOGGED);
    }

    // ── Placement ─────────────────────────────────────────────────────────────

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState base = super.getStateForPlacement(ctx);
        if (base == null) return null;
        boolean inWater = ctx.getLevel().getFluidState(ctx.getClickedPos()).is(FluidTags.WATER);
        return base.setValue(WATERLOGGED, inWater);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state,
                            @Nullable net.minecraft.world.entity.LivingEntity placer,
                            net.minecraft.world.item.ItemStack stack) {
        if (!level.isClientSide) {
            // Middle/top inherit waterlogged=false; they're above the waterline
            level.setBlock(pos.above(),  defaultBlockState().setValue(SECTION, 1).setValue(WATERLOGGED, false), Block.UPDATE_ALL);
            level.setBlock(pos.above(2), defaultBlockState().setValue(SECTION, 2).setValue(WATERLOGGED, false), Block.UPDATE_ALL);
        }
    }

    // ── Survival ─────────────────────────────────────────────────────────────

    @Override
    protected boolean canSurviveAtBottom(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos groundPos = pos.below();
        if (!mayPlaceOn(level.getBlockState(groundPos), level, groundPos)) return false;
        return state.getValue(WATERLOGGED) || hasAdjacentWater(level, pos);
    }

    public boolean mayPlaceOn(BlockState ground, BlockGetter level, BlockPos groundPos) {
        return ground.is(BlockTags.DIRT)
                || ground.is(Blocks.SAND)
                || ground.is(Blocks.GRAVEL)
                || ground.is(Blocks.CLAY)
                || ground.is(Blocks.MUD);
    }

    private boolean hasAdjacentWater(LevelReader level, BlockPos pos) {
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (level.getFluidState(pos.relative(dir)).is(FluidTags.WATER)) return true;
            if (level.getFluidState(pos.relative(dir).below()).is(FluidTags.WATER)) return true;
        }
        return false;
    }

    // ── Waterlogging ──────────────────────────────────────────────────────────

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader,
                                     ScheduledTickAccess tickAccess,
                                     BlockPos pos, Direction direction,
                                     BlockPos neighborPos, BlockState neighborState,
                                     RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(state, levelReader, tickAccess, pos,
                direction, neighborPos, neighborState, random);
    }
}
