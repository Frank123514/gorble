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

/**
 * Palm trunk placer that mimics a real palm tree:
 *
 *  • Trunk grows mostly straight but accumulates a gentle random lean
 *    (1–2 blocks of horizontal drift over the full height), giving each
 *    tree a slightly different silhouette.
 *  • Every few blocks the trunk is allowed to dog-leg one block in a
 *    cardinal direction, exactly like the segmented growth of a real
 *    date palm.
 *  • The base is slightly wider: a single ring of horizontally-oriented
 *    log blocks is placed at ground level to suggest the bulging base
 *    common in date palms and coconut palms.
 *  • A single FoliageAttachment is emitted at the crown so that
 *    GotPalmFoliagePlacer (or any other foliage placer) knows where to
 *    place leaves.
 */
public class GotPalmTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<GotPalmTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, GotPalmTrunkPlacer::new));

    public GotPalmTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return GotTreePlacers.GOT_PALM_TRUNK_PLACER.get();
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

        // ── 1. Choose a lean direction and magnitude ──────────────────────────
        // Pick a random cardinal direction to lean toward.
        Direction[] horizontals = Direction.Plane.HORIZONTAL.stream().toArray(Direction[]::new);
        Direction leanDir = horizontals[random.nextInt(horizontals.length)];

        // Total horizontal drift: 0 or 1 block for shorter trees, up to 2 for taller.
        int maxDrift = freeTreeHeight >= 12 ? 2 : (freeTreeHeight >= 8 ? 1 : 0);
        // Spread the drift steps evenly across the upper two-thirds of the trunk.
        int driftStepsLeft = random.nextInt(maxDrift + 1); // 0..maxDrift
        int driftInterval  = driftStepsLeft > 0
                ? Math.max(1, (freeTreeHeight * 2 / 3) / driftStepsLeft)
                : Integer.MAX_VALUE;

        // ── 2. Place bulging base ring ────────────────────────────────────────
        //  One ring of horizontal logs at y=0 suggests the swollen palm base.
        for (Direction d : Direction.Plane.HORIZONTAL) {
            Direction.Axis axis = (d == Direction.EAST || d == Direction.WEST)
                    ? Direction.Axis.X : Direction.Axis.Z;
            placeLog(level, placer, random, startPos.relative(d), config,
                    s -> s.setValue(RotatedPillarBlock.AXIS, axis));
        }

        // ── 3. Walk up the trunk ──────────────────────────────────────────────
        BlockPos cursor = startPos;
        int driftPlaced = 0;

        for (int y = 0; y < freeTreeHeight; y++) {
            // Place the vertical log at current column position, height y.
            BlockPos trunkPos = cursor.above(y);
            placeLog(level, placer, random, trunkPos, config);

            // Decide if this is a drift step (only in upper 2/3, and only if we
            // still have drift steps remaining).
            if (y >= freeTreeHeight / 3
                    && driftPlaced < driftStepsLeft
                    && y % driftInterval == 0) {

                // Move cursor one block in the lean direction.
                cursor = cursor.relative(leanDir);
                driftPlaced++;

                // Place a horizontal connector log to bridge the step visually.
                Direction.Axis stepAxis = (leanDir == Direction.EAST || leanDir == Direction.WEST)
                        ? Direction.Axis.X : Direction.Axis.Z;
                placeLog(level, placer, random, trunkPos.relative(leanDir), config,
                        s -> s.setValue(RotatedPillarBlock.AXIS, stepAxis));
            }
        }

        // Crown is at the top of the (possibly shifted) cursor column.
        BlockPos crown = cursor.above(freeTreeHeight);
        List<FoliagePlacer.FoliageAttachment> foliage = new ArrayList<>();
        foliage.add(new FoliagePlacer.FoliageAttachment(crown, 0, false));
        return foliage;
    }
}
