package net.got.worldgen.biome.placers;

import com.mojang.serialization.Codec;
import net.got.block.TripleReedsBlock;
import net.got.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TripleReedsPatchFeature extends Feature<NoneFeatureConfiguration> {

    public static final int TRIES     = 32;
    public static final int XZ_SPREAD = 7;

    public TripleReedsPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level  = ctx.level();
        BlockPos      origin = ctx.origin();
        RandomSource  rand   = ctx.random();

        boolean placed = false;

        for (int i = 0; i < TRIES; i++) {
            int dx = rand.nextInt(XZ_SPREAD * 2 + 1) - XZ_SPREAD;
            int dz = rand.nextInt(XZ_SPREAD * 2 + 1) - XZ_SPREAD;

            BlockPos ground = origin.offset(dx, 0, dz);

            if (!isValidGround(level, ground)) continue;

            int waterDepth = 0;
            for (int w = 1; w <= 3; w++) {
                if (level.getFluidState(ground.above(w)).is(FluidTags.WATER)) {
                    waterDepth = w;
                } else {
                    break;
                }
            }

            if (waterDepth < 1 || waterDepth > 2) continue;

            BlockPos bottom = ground.above(1);

            if (!canReplace(level, bottom) ||
                !canReplace(level, bottom.above(1)) ||
                !canReplace(level, bottom.above(2))) continue;

            Block reedBlock = ModBlocks.REEDS.get();
            BlockState s0 = reedBlock.defaultBlockState()
                    .setValue(TripleReedsBlock.SECTION, 0)
                    .setValue(TripleReedsBlock.WATERLOGGED, true);
            BlockState s1 = reedBlock.defaultBlockState()
                    .setValue(TripleReedsBlock.SECTION, 1)
                    .setValue(TripleReedsBlock.WATERLOGGED, waterDepth >= 2);
            BlockState s2 = reedBlock.defaultBlockState()
                    .setValue(TripleReedsBlock.SECTION, 2)
                    .setValue(TripleReedsBlock.WATERLOGGED, false);

            level.setBlock(bottom,          s0, Block.UPDATE_CLIENTS);
            level.setBlock(bottom.above(1), s1, Block.UPDATE_CLIENTS);
            level.setBlock(bottom.above(2), s2, Block.UPDATE_CLIENTS);
            placed = true;
        }

        return placed;
    }

    private static boolean isValidGround(WorldGenLevel level, BlockPos ground) {
        var state = level.getBlockState(ground);
        return state.is(net.minecraft.tags.BlockTags.DIRT)
                || state.is(net.minecraft.world.level.block.Blocks.SAND)
                || state.is(net.minecraft.world.level.block.Blocks.GRAVEL)
                || state.is(net.minecraft.world.level.block.Blocks.CLAY)
                || state.is(net.minecraft.world.level.block.Blocks.MUD);
    }

    private static boolean canReplace(WorldGenLevel level, BlockPos pos) {
        var state = level.getBlockState(pos);
        return state.isAir() || level.getFluidState(pos).is(FluidTags.WATER);
    }
}
