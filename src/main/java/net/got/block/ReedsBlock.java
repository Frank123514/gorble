package net.got.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;

/**
 * Reeds — a 2-block-tall wetland plant.
 *
 * Extends DoublePlantBlock directly.  DoublePlantBlock already:
 *   - Declares the HALF (DoubleBlockHalf) blockstate property.
 *   - Calls setPlacedBy() to place the upper half automatically
 *     whenever the lower half is placed by a player.
 *   - Removes both halves when either is broken via playerWillDestroy().
 *   - Handles updateShape() so the upper half pops off if the lower is removed.
 *
 * All we need to do is:
 *   1. Define what ground is valid  (mayPlaceOn)
 *   2. Define survival rules per-half  (canSurvive)
 */
public class ReedsBlock extends DoublePlantBlock {

    public ReedsBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    // ── Ground check ─────────────────────────────────────────────────────────

    @Override
    protected boolean mayPlaceOn(BlockState groundState, BlockGetter level, BlockPos groundPos) {
        // Water-edge terrain: dirt family, sand, gravel, clay, mud
        return groundState.is(BlockTags.DIRT)
                || groundState.is(Blocks.SAND)
                || groundState.is(Blocks.GRAVEL)
                || groundState.is(Blocks.CLAY)
                || groundState.is(Blocks.MUD);
    }

    // ── Per-half survival ─────────────────────────────────────────────────────

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            // Upper half only needs the lower half directly beneath it.
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        }

        // Lower half: needs valid ground one block below AND adjacent water.
        BlockPos groundPos = pos.below();
        if (!mayPlaceOn(level.getBlockState(groundPos), level, groundPos)) {
            return false;
        }
        return hasAdjacentWater(level, pos);
    }

    // ── Water check ───────────────────────────────────────────────────────────

    private boolean hasAdjacentWater(LevelReader level, BlockPos pos) {
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            // Horizontally adjacent water at the same level
            FluidState side = level.getFluidState(pos.relative(dir));
            if (side.is(FluidTags.WATER)) return true;

            // One block below the adjacent position (sloped shorelines)
            FluidState sideBelow = level.getFluidState(pos.relative(dir).below());
            if (sideBelow.is(FluidTags.WATER)) return true;
        }
        return false;
    }
}