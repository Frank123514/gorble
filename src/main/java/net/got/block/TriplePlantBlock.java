package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;

public class TriplePlantBlock extends BushBlock {

    public static final MapCodec<BushBlock> CODEC = MapCodec.unit(
            () -> new TriplePlantBlock(Properties.of()));

    @Override
    public MapCodec<BushBlock> codec() { return CODEC; }

    public static final IntegerProperty SECTION = IntegerProperty.create("section", 0, 2);

    public TriplePlantBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(SECTION, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SECTION);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos   = ctx.getClickedPos();
        Level    level = ctx.getLevel();
        if (pos.getY() < level.getMaxY() - 2
                && level.getBlockState(pos.above()).canBeReplaced(ctx)
                && level.getBlockState(pos.above(2)).canBeReplaced(ctx)) {
            return defaultBlockState().setValue(SECTION, 0);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state,
                            @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide()) {
            level.setBlock(pos.above(),  defaultBlockState().setValue(SECTION, 1), Block.UPDATE_ALL);
            level.setBlock(pos.above(2), defaultBlockState().setValue(SECTION, 2), Block.UPDATE_ALL);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        int section = state.getValue(SECTION);
        if (section == 0) return canSurviveAtBottom(state, level, pos);
        BlockState below = level.getBlockState(pos.below());
        return below.is(this) && below.getValue(SECTION) == section - 1;
    }

    protected boolean canSurviveAtBottom(BlockState state, LevelReader level, BlockPos pos) {
        return super.canSurvive(state, level, pos);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader levelReader,
                                     ScheduledTickAccess scheduledTickAccess,
                                     BlockPos pos, Direction direction,
                                     BlockPos neighborPos, BlockState neighborState,
                                     RandomSource random) {
        if (!canSurvive(state, levelReader, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, levelReader, scheduledTickAccess, pos,
                direction, neighborPos, neighborState, random);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            int     section = state.getValue(SECTION);
            BlockPos bottom = switch (section) {
                case 2 -> pos.below(2);
                case 1 -> pos.below();
                default -> pos;
            };
            for (int i = 0; i < 3; i++) {
                BlockPos    target      = bottom.above(i);
                BlockState  targetState = level.getBlockState(target);
                if (targetState.is(this) && targetState.getValue(SECTION) == i) {
                    if (i == 0) {
                        level.destroyBlock(target, !player.isCreative());
                    } else {
                        level.removeBlock(target, false);
                    }
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
