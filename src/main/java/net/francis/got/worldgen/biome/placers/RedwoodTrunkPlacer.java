package net.francis.got.worldgen.biome.placers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class RedwoodTrunkPlacer extends TrunkPlacer {

    private static final int FLARE_LAYERS = 3;
    private static final float BRANCH_START_FRACTION = 0.72F;
    private static final float BRANCH_CHANCE = 0.35F;

    public static final MapCodec<RedwoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, RedwoodTrunkPlacer::new));

    public RedwoodTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return TreePlacers.GOT_REDWOOD_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> placer,
            RandomSource random,
            int freeTreeHeight,
            BlockPos startPos,
            TreeConfiguration config) {

        setDirtAt(level, placer, random, startPos.below(), config);
        placeFlaredBase(level, placer, random, startPos, config);

        int branchStartY = Mth.floor((float) freeTreeHeight * BRANCH_START_FRACTION);

        for (int y = 0; y < freeTreeHeight; y++) {
            BlockPos trunkPos = startPos.above(y);
            placeLog(level, placer, random, trunkPos, config);

            if (y >= branchStartY && y < freeTreeHeight - 1 && random.nextFloat() < BRANCH_CHANCE) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                Direction.Axis axis = (direction == Direction.EAST || direction == Direction.WEST)
                        ? Direction.Axis.X : Direction.Axis.Z;
                placeLog(level, placer, random, trunkPos.relative(direction), config,
                        state -> state.setValue(RotatedPillarBlock.AXIS, axis));
            }
        }

        List<FoliagePlacer.FoliageAttachment> foliage = new ArrayList<>();
        foliage.add(new FoliagePlacer.FoliageAttachment(startPos.above(freeTreeHeight), 0, false));
        return foliage;
    }

    private void placeFlaredBase(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> placer,
                                 RandomSource random, BlockPos startPos, TreeConfiguration config) {

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                
                if (dx == 0 && dz == 0) {
                    placeLog(level, placer, random, startPos.offset(dx, 0, dz), config);
                } else if (random.nextFloat() < 0.75F) {
                    BlockPos flarePos = startPos.offset(dx, 0, dz);
                    setDirtAt(level, placer, random, flarePos.below(), config);
                    placeLog(level, placer, random, flarePos, config);
                }
            }
        }

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) {
                    placeLog(level, placer, random, startPos.offset(dx, 1, dz), config);
                } else if (Math.abs(dx) + Math.abs(dz) == 1) {
                    if (random.nextFloat() < 0.6F) {
                        placeLog(level, placer, random, startPos.offset(dx, 1, dz), config);
                    }
                } else if (random.nextFloat() < 0.15F) {
                    placeLog(level, placer, random, startPos.offset(dx, 1, dz), config);
                }
            }
        }

        for (int[] side : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
            if (random.nextFloat() < 0.35F) {
                placeLog(level, placer, random, startPos.offset(side[0], 2, side[1]), config);
            }
        }

    }
}