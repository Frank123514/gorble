package net.got.worldgen.biome.placers;

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

/**
 * Trunk placer for redwoods: an immensely tall, dead-straight single-wide
 * shaft with a thick, irregular, multi-block flared base — like a swollen
 * bole with buttress-like roots that quickly tapers to a clean single
 * column for the vast majority of the height.
 *
 * <p>Real redwoods self-prune their lower limbs as they age, leaving a long
 * clean bole under a crown that only begins near the very top, so branch
 * stubs here are restricted to the upper fraction of the trunk and even
 * then only appear some of the time.
 */
public class GotRedwoodTrunkPlacer extends TrunkPlacer {

    // The flared base spans multiple blocks and multiple layers, giving that
    // thick, almost-clustered look at ground level before tapering fast.
    private static final int FLARE_LAYERS = 3;
    private static final float BRANCH_START_FRACTION = 0.72F;
    private static final float BRANCH_CHANCE = 0.35F;

    public static final MapCodec<GotRedwoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, GotRedwoodTrunkPlacer::new));

    public GotRedwoodTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return GotTreePlacers.GOT_REDWOOD_TRUNK_PLACER.get();
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

    /**
     * Builds a thick, irregular flared base that looks like a swollen bole
     * with buttress roots. The base is 2-3 blocks wide at ground level and
     * tapers over a few layers before becoming a clean single column.
     */
    private void placeFlaredBase(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> placer,
                                 RandomSource random, BlockPos startPos, TreeConfiguration config) {

        // Layer 0 (ground level): a 3x3-ish irregular blob with the center
        // always present and corners randomly filled.
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                // Center is always placed; corners are random
                if (dx == 0 && dz == 0) {
                    placeLog(level, placer, random, startPos.offset(dx, 0, dz), config);
                } else if (random.nextFloat() < 0.75F) {
                    BlockPos flarePos = startPos.offset(dx, 0, dz);
                    setDirtAt(level, placer, random, flarePos.below(), config);
                    placeLog(level, placer, random, flarePos, config);
                }
            }
        }

        // Layer 1 (one block up): smaller flare, mostly a plus shape with
        // occasional diagonal fill for irregularity.
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) {
                    placeLog(level, placer, random, startPos.offset(dx, 1, dz), config);
                } else if (Math.abs(dx) + Math.abs(dz) == 1) { // cardinal neighbors only
                    if (random.nextFloat() < 0.6F) {
                        placeLog(level, placer, random, startPos.offset(dx, 1, dz), config);
                    }
                } else if (random.nextFloat() < 0.15F) { // rare diagonal
                    placeLog(level, placer, random, startPos.offset(dx, 1, dz), config);
                }
            }
        }

        // Layer 2 (two blocks up): very slight flare, just cardinal sides
        // occasionally, mostly already single-wide.
        for (int[] side : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
            if (random.nextFloat() < 0.35F) {
                placeLog(level, placer, random, startPos.offset(side[0], 2, side[1]), config);
            }
        }

        // The main trunk block at layers 1 and 2 is already placed by the
        // loop above (center at dx=0,dz=0), so no extra call needed.
    }
}