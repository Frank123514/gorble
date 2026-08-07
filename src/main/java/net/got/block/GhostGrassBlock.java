package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * GhostGrassBlock — a 3-block-tall pale ghost grass that spreads aggressively
 * by searching a large radius and converting ANY plant it finds.
 *
 * Spreading rules (fires on random tick of ANY section):
 *   1. Search a large radius (12 blocks) for any BushBlock-based plant
 *   2. Convert the closest valid plant to ghost grass
 *   3. Upper sections moderately trigger bottom section spreads
 */
public class GhostGrassBlock extends TriplePlantBlock {

    public static final MapCodec<BushBlock> CODEC =
            MapCodec.unit(() -> new GhostGrassBlock(Properties.of()));

    // Search radius - how far to look for plants to convert (12 = large area but not overwhelming)
    private static final int SPREAD_RADIUS = 12;
    // Maximum conversion attempts per tick
    private static final int MAX_CONVERSIONS_PER_TICK = 3;

    @Override
    public MapCodec<BushBlock> codec() { return CODEC; }

    public GhostGrassBlock(Properties properties) {
        super(properties);
    }

    // ── Spreading ─────────────────────────────────────────────────────────────

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        // ALL sections tick constantly for maximum spread speed
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level,
                           BlockPos pos, RandomSource random) {

        // Upper sections moderately force the bottom to spread
        if (state.getValue(SECTION) != 0) {
            BlockPos below = pos.below(state.getValue(SECTION));
            BlockState belowState = level.getBlockState(below);
            if (belowState.is(this) && belowState.getValue(SECTION) == 0) {
                // 60% chance for upper sections to trigger a spread (reduced from 90%)
                if (random.nextInt(5) < 3) {
                    performAggressiveSpread(level, below, random);
                }
            }
            return;
        }

        // Bottom section performs the aggressive spread
        performAggressiveSpread(level, pos, random);
    }

    /**
     * Aggressively searches a large radius and converts ANY plant found.
     * Uses a spiral search pattern from center outward for efficiency.
     */
    private void performAggressiveSpread(ServerLevel level, BlockPos center, RandomSource random) {
        int conversions = 0;

        // Try multiple conversions per tick (max 3, reduced from 5)
        for (int attempt = 0; attempt < MAX_CONVERSIONS_PER_TICK && conversions < 2; attempt++) {
            // Search in expanding radius - start close, go outward
            int searchRadius = random.nextInt(SPREAD_RADIUS) + 1;

            // Search random positions within this radius (reduced checks from 20 to 12)
            for (int check = 0; check < 12; check++) {
                // Pick random position within radius (spherical)
                int dx = random.nextInt(searchRadius * 2 + 1) - searchRadius;
                int dy = random.nextInt(6) - 2; // Vertical range: 2 below to 4 above (reduced from 8)
                int dz = random.nextInt(searchRadius * 2 + 1) - searchRadius;

                // Skip if outside spherical radius (optimization)
                if (dx*dx + dz*dz > searchRadius*searchRadius) continue;

                BlockPos target = center.offset(dx, dy, dz);
                BlockState targetState = level.getBlockState(target);

                // Skip if already ghost grass
                if (targetState.is(this)) continue;

                // INDISCRIMINATE: Convert ANY BushBlock (grass, fern, flower, sapling, etc.)
                if (isAnyPlant(targetState)) {
                    if (tryConvertToGhostGrass(level, target, targetState)) {
                        conversions++;
                        break; // Move to next conversion attempt
                    }
                }
            }
        }
    }

    /**
     * Converts a target position to ghost grass if valid.
     * Returns true if conversion succeeded.
     */
    private boolean tryConvertToGhostGrass(ServerLevel level, BlockPos target, BlockState targetState) {
        Block block = targetState.getBlock();

        // Check if block below is valid ground
        BlockState below = level.getBlockState(target.below());
        if (!isValidGround(below)) return false;

        // Handle different plant types
        boolean isTall = block instanceof DoublePlantBlock;
        boolean isTriple = block instanceof TriplePlantBlock;

        // Calculate space needed
        int blocksNeeded;
        if (isTriple) {
            blocksNeeded = 0;
        } else if (isTall) {
            blocksNeeded = 1;
        } else {
            blocksNeeded = 2;
        }

        // Check space above
        for (int i = 1; i <= blocksNeeded; i++) {
            BlockState above = level.getBlockState(target.above(i));
            if (!above.isAir() && !above.canBeReplaced()) {
                return false;
            }
        }

        // Remove upper parts of existing plant if needed
        if (isTall) {
            level.removeBlock(target.above(), false);
        } else if (isTriple) {
            level.removeBlock(target.above(), false);
            level.removeBlock(target.above(2), false);
        }

        // Place ghost grass (3 blocks tall)
        level.setBlock(target,          defaultBlockState().setValue(SECTION, 0), Block.UPDATE_ALL);
        level.setBlock(target.above(),  defaultBlockState().setValue(SECTION, 1), Block.UPDATE_ALL);
        level.setBlock(target.above(2), defaultBlockState().setValue(SECTION, 2), Block.UPDATE_ALL);

        return true;
    }

    /**
     * INDISCRIMINATE: Returns true for ANY BushBlock-based plant.
     * This includes: grass, fern, flowers, saplings, mushrooms, etc.
     */
    private boolean isAnyPlant(BlockState state) {
        Block block = state.getBlock();
        if (!(block instanceof BushBlock)) return false;
        if (block instanceof TriplePlantBlock && !(block instanceof GhostGrassBlock)) {
            return true;
        }
        return true;
    }

    private boolean isValidGround(BlockState state) {
        return state.is(BlockTags.DIRT)
                || state.is(BlockTags.SAND)
                || state.is(net.minecraft.world.level.block.Blocks.GRASS_BLOCK)
                || state.is(net.minecraft.world.level.block.Blocks.GRAVEL)
                || state.is(net.minecraft.world.level.block.Blocks.CLAY)
                || state.is(net.minecraft.world.level.block.Blocks.MUD)
                || state.is(BlockTags.NYLIUM)
                || state.is(net.minecraft.world.level.block.Blocks.END_STONE);
    }
}