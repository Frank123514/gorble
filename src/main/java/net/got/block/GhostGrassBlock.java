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

public class GhostGrassBlock extends TriplePlantBlock {

    public static final MapCodec<BushBlock> CODEC =
            MapCodec.unit(() -> new GhostGrassBlock(Properties.of()));

    private static final int SPREAD_RADIUS = 12;
    
    private static final int MAX_CONVERSIONS_PER_TICK = 3;

    @Override
    public MapCodec<BushBlock> codec() { return CODEC; }

    public GhostGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level,
                           BlockPos pos, RandomSource random) {

        if (state.getValue(SECTION) != 0) {
            BlockPos below = pos.below(state.getValue(SECTION));
            BlockState belowState = level.getBlockState(below);
            if (belowState.is(this) && belowState.getValue(SECTION) == 0) {
                
                if (random.nextInt(5) < 3) {
                    performAggressiveSpread(level, below, random);
                }
            }
            return;
        }

        performAggressiveSpread(level, pos, random);
    }

    private void performAggressiveSpread(ServerLevel level, BlockPos center, RandomSource random) {
        int conversions = 0;

        for (int attempt = 0; attempt < MAX_CONVERSIONS_PER_TICK && conversions < 2; attempt++) {
            
            int searchRadius = random.nextInt(SPREAD_RADIUS) + 1;

            for (int check = 0; check < 12; check++) {
                
                int dx = random.nextInt(searchRadius * 2 + 1) - searchRadius;
                int dy = random.nextInt(6) - 2;
                int dz = random.nextInt(searchRadius * 2 + 1) - searchRadius;

                if (dx*dx + dz*dz > searchRadius*searchRadius) continue;

                BlockPos target = center.offset(dx, dy, dz);
                BlockState targetState = level.getBlockState(target);

                if (targetState.is(this)) continue;

                if (isAnyPlant(targetState)) {
                    if (tryConvertToGhostGrass(level, target, targetState)) {
                        conversions++;
                        break;
                    }
                }
            }
        }
    }

    private boolean tryConvertToGhostGrass(ServerLevel level, BlockPos target, BlockState targetState) {
        Block block = targetState.getBlock();

        BlockState below = level.getBlockState(target.below());
        if (!isValidGround(below)) return false;

        boolean isTall = block instanceof DoublePlantBlock;
        boolean isTriple = block instanceof TriplePlantBlock;

        int blocksNeeded;
        if (isTriple) {
            blocksNeeded = 0;
        } else if (isTall) {
            blocksNeeded = 1;
        } else {
            blocksNeeded = 2;
        }

        for (int i = 1; i <= blocksNeeded; i++) {
            BlockState above = level.getBlockState(target.above(i));
            if (!above.isAir() && !above.canBeReplaced()) {
                return false;
            }
        }

        if (isTall) {
            level.removeBlock(target.above(), false);
        } else if (isTriple) {
            level.removeBlock(target.above(), false);
            level.removeBlock(target.above(2), false);
        }

        level.setBlock(target,          defaultBlockState().setValue(SECTION, 0), Block.UPDATE_ALL);
        level.setBlock(target.above(),  defaultBlockState().setValue(SECTION, 1), Block.UPDATE_ALL);
        level.setBlock(target.above(2), defaultBlockState().setValue(SECTION, 2), Block.UPDATE_ALL);

        return true;
    }

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