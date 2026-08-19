package net.francis.got.worldgen.biome.placers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class PalmTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<PalmTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, PalmTrunkPlacer::new));

    public PalmTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return TreePlacers.GOT_PALM_TRUNK_PLACER.get();
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

        Direction[] horizontals = Direction.Plane.HORIZONTAL.stream().toArray(Direction[]::new);
        Direction leanDir = horizontals[random.nextInt(horizontals.length)];

        int maxDrift = freeTreeHeight >= 12 ? 2 : (freeTreeHeight >= 8 ? 1 : 0);
        
        int driftStepsLeft = random.nextInt(maxDrift + 1);
        int driftInterval  = driftStepsLeft > 0
                ? Math.max(1, (freeTreeHeight * 2 / 3) / driftStepsLeft)
                : Integer.MAX_VALUE;

        for (Direction d : Direction.Plane.HORIZONTAL) {
            Direction.Axis axis = (d == Direction.EAST || d == Direction.WEST)
                    ? Direction.Axis.X : Direction.Axis.Z;
            placeLog(level, placer, random, startPos.relative(d), config,
                    s -> s.setValue(RotatedPillarBlock.AXIS, axis));
        }

        BlockPos cursor = startPos;
        int driftPlaced = 0;

        for (int y = 0; y < freeTreeHeight; y++) {
            
            BlockPos trunkPos = cursor.above(y);
            placeLog(level, placer, random, trunkPos, config);

            if (y >= freeTreeHeight / 3
                    && driftPlaced < driftStepsLeft
                    && y % driftInterval == 0) {

                cursor = cursor.relative(leanDir);
                driftPlaced++;

                Direction.Axis stepAxis = (leanDir == Direction.EAST || leanDir == Direction.WEST)
                        ? Direction.Axis.X : Direction.Axis.Z;
                placeLog(level, placer, random, trunkPos.relative(leanDir), config,
                        s -> s.setValue(RotatedPillarBlock.AXIS, stepAxis));
            }
        }

        BlockPos crown = cursor.above(freeTreeHeight);
        List<FoliagePlacer.FoliageAttachment> foliage = new ArrayList<>();
        foliage.add(new FoliagePlacer.FoliageAttachment(crown, 0, false));
        return foliage;
    }
}
