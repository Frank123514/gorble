package net.got.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Reeds — a wetland plant that grows alongside rivers, bogs, and marshes.
 *
 * <h3>Placement rules (mirrors vanilla sugar cane)</h3>
 * <ul>
 *   <li>Can be stacked on top of another {@code ReedsBlock}.</li>
 *   <li>Otherwise must sit on a dirt-type, sand, or clay block that has at
 *       least one horizontally adjacent water source block or flowing water.</li>
 *   <li>Destroyed (drops item) if any of those conditions are no longer met.</li>
 * </ul>
 *
 * <h3>Growth</h3>
 * Uses an internal {@code age} counter (0–15).  Each random-tick increments the
 * age; at age 15 a new block is placed above (if the column is shorter than 3)
 * and the age resets to 0.  Bone-meal can be applied to force-advance the age
 * or grow an extra block immediately.
 *
 * <h3>Drops</h3>
 * The loot table simply drops one Reeds item, identical to how sugar cane works.
 */
public class ReedsBlock extends Block implements BonemealableBlock {

    /** Maximum stack height (3 blocks, same as vanilla sugar cane). */
    private static final int MAX_HEIGHT = 3;

    /**
     * Internal growth timer.  Increments each random tick; resets to 0 when a
     * new block grows above.
     */
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;

    /** Hit-box is a 0.875-wide cross (14/16), identical to vanilla sugar cane. */
    private static final VoxelShape SHAPE =
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public ReedsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    // ── Block state ───────────────────────────────────────────────────────────

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    // ── Shape ─────────────────────────────────────────────────────────────────

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level,
                               BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    // ── Survival / placement ──────────────────────────────────────────────────

    /**
     * Returns the default block state for player placement, or {@code null} if
     * the target location is not valid.
     */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState existing = context.getLevel().getBlockState(context.getClickedPos());
        if (existing.is(this)) {
            return null; // already occupied
        }
        return this.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        // Can always stack on top of another reeds block.
        BlockState below = level.getBlockState(pos.below());
        if (below.is(this)) {
            return true;
        }

        // Otherwise the block below must be a valid ground material.
        if (!isValidGround(below)) {
            return false;
        }

        // At least one horizontal neighbour must be (or directly above) water.
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos adjPos = pos.below().relative(dir);
            FluidState fluid = level.getFluidState(adjPos);
            BlockState adjBlock = level.getBlockState(adjPos);
            if (fluid.is(FluidTags.WATER) || adjBlock.is(Blocks.FROSTED_ICE)) {
                return true;
            }
        }
        return false;
    }

    /** Valid ground types: any dirt-family block, sand, gravel, clay, or mud. */
    private static boolean isValidGround(BlockState state) {
        return state.is(BlockTags.DIRT)
                || state.is(Blocks.SAND)
                || state.is(Blocks.RED_SAND)
                || state.is(Blocks.GRAVEL)
                || state.is(Blocks.CLAY)
                || state.is(Blocks.MUD)
                || state.is(Blocks.MUDDY_MANGROVE_ROOTS);
    }

    // ── Neighbour updates ─────────────────────────────────────────────────────

    @Override
    public BlockState updateShape(BlockState state,
                                  LevelReader level,
                                  ScheduledTickAccess scheduledTickAccess,
                                  BlockPos pos,
                                  Direction direction,
                                  BlockPos neighbourPos,
                                  BlockState neighbourState,
                                  RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            scheduledTickAccess.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighbourPos, neighbourState, random);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    // ── Growth (random tick) ──────────────────────────────────────────────────

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Only the topmost block in the column grows.
        if (!level.isEmptyBlock(pos.above())) {
            return;
        }

        int age = state.getValue(AGE);
        if (age < 15) {
            // Advance the internal growth counter.
            level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_INVISIBLE);
        } else {
            // Age rolled over — attempt to add a new block above if not at max height.
            if (columnHeight(level, pos) < MAX_HEIGHT) {
                level.setBlock(pos.above(), this.defaultBlockState(), Block.UPDATE_ALL);
            }
            // Reset age regardless (prevents runaway growth attempts).
            level.setBlock(pos, state.setValue(AGE, 0), Block.UPDATE_INVISIBLE);
        }
    }

    /** Counts how many consecutive reeds blocks exist in this column (including {@code top}). */
    private static int columnHeight(Level level, BlockPos top) {
        int height = 1;
        BlockPos check = top.below();
        while (level.getBlockState(check).is(level.getBlockState(top).getBlock())) {
            height++;
            check = check.below();
            if (height >= MAX_HEIGHT) break;
        }
        return height;
    }

    // ── Bone meal ─────────────────────────────────────────────────────────────

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos,
                                         BlockState state) {
        // Bone-meal is useful when the column is not yet at max height.
        return level.isEmptyBlock(pos.above())
                && columnHeight((Level) level, pos) < MAX_HEIGHT;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random,
                                     BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random,
                                BlockPos pos, BlockState state) {
        // Advance age to 15 so the next random tick (or this call) triggers growth.
        int age = state.getValue(AGE);
        if (age < 15) {
            level.setBlock(pos, state.setValue(AGE, 15), Block.UPDATE_INVISIBLE);
        }
        // Immediately attempt growth.
        if (level.isEmptyBlock(pos.above())
                && columnHeight(level, pos) < MAX_HEIGHT) {
            level.setBlock(pos.above(), this.defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(pos, state.setValue(AGE, 0), Block.UPDATE_INVISIBLE);
        }
    }
}