package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public class RushesBlock extends DoublePlantBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<DoublePlantBlock> CODEC =
            MapCodec.unit(() -> new RushesBlock(Properties.of()));

    @Override
    public MapCodec<DoublePlantBlock> codec() { return CODEC; }

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RushesBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    protected boolean mayPlaceOn(BlockState ground, BlockGetter level, BlockPos groundPos) {
        return ground.is(BlockTags.DIRT)
                || ground.is(Blocks.SAND)
                || ground.is(Blocks.GRAVEL)
                || ground.is(Blocks.CLAY)
                || ground.is(Blocks.MUD);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
        
        BlockPos groundPos = pos.below();
        if (!mayPlaceOn(level.getBlockState(groundPos), level, groundPos)) return false;
        return state.getValue(WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState base = super.getStateForPlacement(ctx);
        if (base == null) return null;
        boolean inWater = ctx.getLevel().getFluidState(ctx.getClickedPos()).is(FluidTags.WATER);
        return base.setValue(WATERLOGGED, inWater);
    }

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