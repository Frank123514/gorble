package net.minecraft.world.level.block;

import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CreakingHeartBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class CreakingHeartBlock extends BaseEntityBlock {
    public static final MapCodec<CreakingHeartBlock> CODEC = simpleCodec(CreakingHeartBlock::new);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final EnumProperty<CreakingHeartBlock.CreakingHeartState> CREAKING = BlockStateProperties.CREAKING;

    @Override
    public MapCodec<CreakingHeartBlock> codec() {
        return CODEC;
    }

    protected CreakingHeartBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(CREAKING, CreakingHeartBlock.CreakingHeartState.DISABLED));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CreakingHeartBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return null;
        } else {
            return state.getValue(CREAKING) != CreakingHeartBlock.CreakingHeartState.DISABLED
                ? createTickerHelper(blockEntityType, BlockEntityType.CREAKING_HEART, CreakingHeartBlockEntity::serverTick)
                : null;
        }
    }

    public static boolean canSummonCreaking(Level level) {
        return level.dimensionType().natural() && level.isNight();
    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles).
     */
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (canSummonCreaking(level)) {
            if (state.getValue(CREAKING) != CreakingHeartBlock.CreakingHeartState.DISABLED) {
                if (random.nextInt(16) == 0 && isSurroundedByLogs(level, pos)) {
                    level.playLocalSound(
                        (double)pos.getX(),
                        (double)pos.getY(),
                        (double)pos.getZ(),
                        SoundEvents.CREAKING_HEART_IDLE,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }
        }
    }

    @Override
    protected BlockState updateShape(
        BlockState state,
        LevelReader level,
        ScheduledTickAccess scheduledTickAccess,
        BlockPos pos,
        Direction direction,
        BlockPos neighborPos,
        BlockState neighborState,
        RandomSource random
    ) {
        BlockState blockstate = super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
        return updateState(blockstate, level, pos);
    }

    private static BlockState updateState(BlockState state, LevelReader level, BlockPos pos) {
        boolean flag = hasRequiredLogs(state, level, pos);
        CreakingHeartBlock.CreakingHeartState creakingheartblock$creakingheartstate = state.getValue(CREAKING);
        return flag && creakingheartblock$creakingheartstate == CreakingHeartBlock.CreakingHeartState.DISABLED
            ? state.setValue(CREAKING, CreakingHeartBlock.CreakingHeartState.DORMANT)
            : state;
    }

    public static boolean hasRequiredLogs(BlockState state, LevelReader level, BlockPos pos) {
        Direction.Axis direction$axis = state.getValue(AXIS);

        for (Direction direction : direction$axis.getDirections()) {
            BlockState blockstate = level.getBlockState(pos.relative(direction));
            if (!blockstate.is(BlockTags.PALE_OAK_LOGS) || blockstate.getValue(AXIS) != direction$axis) {
                return false;
            }
        }

        return true;
    }

    private static boolean isSurroundedByLogs(LevelAccessor level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.is(BlockTags.PALE_OAK_LOGS)) {
                return false;
            }
        }

        return true;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateState(this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()), context.getLevel(), context.getClickedPos());
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate.
     */
    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return RotatedPillarBlock.rotatePillar(state, rotation);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, CREAKING);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (level.getBlockEntity(pos) instanceof CreakingHeartBlockEntity creakingheartblockentity) {
            creakingheartblockentity.removeProtector(null);
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.getBlockEntity(pos) instanceof CreakingHeartBlockEntity creakingheartblockentity) {
            creakingheartblockentity.removeProtector(player.damageSources().playerAttack(player));
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    /**
     * Returns the analog signal this block emits. This is the signal a comparator can read from it.
     */
    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (state.getValue(CREAKING) != CreakingHeartBlock.CreakingHeartState.ACTIVE) {
            return 0;
        } else {
            return level.getBlockEntity(pos) instanceof CreakingHeartBlockEntity creakingheartblockentity
                ? creakingheartblockentity.getAnalogOutputSignal()
                : 0;
        }
    }

    public static enum CreakingHeartState implements StringRepresentable {
        DISABLED("disabled"),
        DORMANT("dormant"),
        ACTIVE("active");

        private final String name;

        private CreakingHeartState(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
