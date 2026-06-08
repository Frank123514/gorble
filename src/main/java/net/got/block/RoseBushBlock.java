package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import javax.annotation.Nullable;

/**
 * RoseBushBlock — a 2-block-tall decorative rose bush.
 *
 * Uses vanilla DoubleBlockHalf (lower/upper) exactly like minecraft:rose_bush.
 * Place the lower half; the upper half is placed automatically.
 * Breaking either half removes both. Drops only from lower (loot table guards this).
 */
public class RoseBushBlock extends BushBlock {

    public static final MapCodec<RoseBushBlock> CODEC =
            MapCodec.unit(() -> new RoseBushBlock(Properties.of()));

    @Override
    public MapCodec<? extends BushBlock> codec() { return CODEC; }

    public static final EnumProperty<DoubleBlockHalf> HALF =
            BlockStateProperties.DOUBLE_BLOCK_HALF;

    public RoseBushBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    // ── Placement ─────────────────────────────────────────────────────────────

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos   = ctx.getClickedPos();
        Level    level = ctx.getLevel();
        if (pos.getY() < level.getMaxY() - 1
                && level.getBlockState(pos.above()).canBeReplaced(ctx)) {
            return defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state,
                            @Nullable LivingEntity placer, ItemStack stack) {
        level.setBlockAndUpdate(pos.above(),
                defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));
    }

    // ── Survival check ────────────────────────────────────────────────────────

    @Override
    public BlockState updateShape(BlockState state, LevelReader level,
                                  ScheduledTickAccess scheduledTickAccess,
                                  BlockPos pos, Direction facing,
                                  BlockPos facingPos, BlockState facingState,
                                  net.minecraft.util.RandomSource random) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (facing == Direction.UP && half == DoubleBlockHalf.LOWER) {
            return facingState.is(this) && facingState.getValue(HALF) == DoubleBlockHalf.UPPER
                    ? state
                    : Blocks.AIR.defaultBlockState();
        } else if (facing == Direction.DOWN && half == DoubleBlockHalf.UPPER) {
            return facingState.is(this) && facingState.getValue(HALF) == DoubleBlockHalf.LOWER
                    ? state
                    : Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, level, scheduledTickAccess,
                pos, facing, facingPos, facingState, random);
    }

    // ── Break both halves ─────────────────────────────────────────────────────

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            DoubleBlockHalf half = state.getValue(HALF);
            BlockPos otherPos    = half == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
            BlockState otherState = level.getBlockState(otherPos);
            if (otherState.is(this) && otherState.getValue(HALF) != half) {
                level.destroyBlock(otherPos, false, player);
            }
        }
        super.playerWillDestroy(level, pos, state, player);
        return state;
    }
}
