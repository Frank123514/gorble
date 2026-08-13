package net.got.worldgen.biome.placers;

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

public class PineTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<PineTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, PineTrunkPlacer::new));

    public PineTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return TreePlacers.GOT_PINE_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level,
                                                            BiConsumer<BlockPos, BlockState> placer,
                                                            RandomSource random,
                                                            int freeTreeHeight,
                                                            BlockPos startPos,
                                                            TreeConfiguration config) {
        setDirtAt(level, placer, random, startPos.below(), config);

        List<FoliagePlacer.FoliageAttachment> foliage = new ArrayList<>();

        for (int y = 0; y < freeTreeHeight; y++) {
            BlockPos trunkPos = startPos.above(y);
            placeLog(level, placer, random, trunkPos, config);

            if (y > 3 && y < freeTreeHeight - 3 && y % 3 == 0) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                placeHorizontalStub(level, placer, random, config, trunkPos.relative(direction), direction);

                if (random.nextFloat() < 0.2F) {
                    Direction opposite = direction.getOpposite();
                    placeHorizontalStub(level, placer, random, config, trunkPos.relative(opposite), opposite);
                }
            }
        }

        foliage.add(new FoliagePlacer.FoliageAttachment(startPos.above(freeTreeHeight), 0, false));
        return foliage;
    }

    private void placeHorizontalStub(LevelSimulatedReader level,
                                     BiConsumer<BlockPos, BlockState> placer,
                                     RandomSource random,
                                     TreeConfiguration config,
                                     BlockPos pos,
                                     Direction direction) {
        Direction.Axis axis = switch (direction) {
            case EAST, WEST -> Direction.Axis.X;
            case NORTH, SOUTH -> Direction.Axis.Z;
            default -> Direction.Axis.Y;
        };

        placeLog(level, placer, random, pos, config, state -> state.setValue(RotatedPillarBlock.AXIS, axis));
    }
}
