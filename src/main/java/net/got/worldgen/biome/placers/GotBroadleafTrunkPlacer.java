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
 * Trunk placer for our large broadleaf hardwoods (ash, beech, chestnut, elm,
 * hawthorn, willow, ebony, goldenheart, black cottonwood, cottonwood,
 * ironwood, weirwood).
 *
 * <p>This is a line-for-line copy of vanilla's {@code FancyTrunkPlacer} —
 * same canopy silhouette ({@link #treeShape}), the same random branch
 * scattering and pruning, and the same foliage attachment logic — with one
 * deliberate change: {@link #thickenBase} flares a couple of extra rings of
 * logs out around the ground level before the normal single-wide trunk
 * column is drawn, so the tree reads as having a thicker, root-flared base
 * instead of rising straight up out of the ground like a pole.
 */
public class GotBroadleafTrunkPlacer extends TrunkPlacer {

    // Same magic numbers vanilla's FancyTrunkPlacer uses to shape the canopy
    // and scatter branches — left untouched so the silhouette matches.
    private static final double TRUNK_HEIGHT_SCALE = 0.618D;
    private static final double CLUSTER_DENSITY_MAGIC = 1.382D;
    private static final double BRANCH_SLOPE = 0.381D;
    private static final double BRANCH_LENGTH_MAGIC = 0.328D;

    public static final MapCodec<GotBroadleafTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, GotBroadleafTrunkPlacer::new));

    public GotBroadleafTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return GotTreePlacers.GOT_BROADLEAF_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> placer,
            RandomSource random,
            int freeTreeHeight,
            BlockPos startPos,
            TreeConfiguration config) {

        int treeHeight = freeTreeHeight + 2;
        int trunkHeight = Mth.floor((double) treeHeight * TRUNK_HEIGHT_SCALE);

        setDirtAt(level, placer, random, startPos.below(), config);
        thickenBase(level, placer, random, startPos, config);

        int clusterCount = Math.min(1, Mth.floor(CLUSTER_DENSITY_MAGIC + Math.pow((double) treeHeight / 13.0D, 2.0D)));
        int topY = startPos.getY() + trunkHeight;
        int y = treeHeight - 5;

        List<FoliageCoords> foliageCoords = new ArrayList<>();
        foliageCoords.add(new FoliageCoords(startPos.above(y), topY));

        for (; y >= 0; --y) {
            float radius = treeShape(treeHeight, y);
            if (radius >= 0.0F) {
                for (int n = 0; n < clusterCount; ++n) {
                    double dist = radius * ((double) random.nextFloat() + BRANCH_LENGTH_MAGIC);
                    double angle = (double) (random.nextFloat() * 2.0F) * Math.PI;
                    double dx = dist * Math.sin(angle) + 0.5D;
                    double dz = dist * Math.cos(angle) + 0.5D;
                    BlockPos branchEnd = startPos.offset(Mth.floor(dx), y - 1, Mth.floor(dz));
                    BlockPos branchTip = branchEnd.above(5);

                    if (makeLimb(level, placer, random, branchEnd, branchTip, false, config)) {
                        int dxFromCenter = startPos.getX() - branchEnd.getX();
                        int dzFromCenter = startPos.getZ() - branchEnd.getZ();
                        double droppedY = (double) branchEnd.getY()
                                - Math.sqrt((double) (dxFromCenter * dxFromCenter + dzFromCenter * dzFromCenter)) * BRANCH_SLOPE;
                        int branchBaseY = droppedY > (double) topY ? topY : (int) droppedY;
                        BlockPos branchBase = new BlockPos(startPos.getX(), branchBaseY, startPos.getZ());

                        if (makeLimb(level, placer, random, branchBase, branchEnd, false, config)) {
                            foliageCoords.add(new FoliageCoords(branchEnd, branchBase.getY()));
                        }
                    }
                }
            }
        }

        makeLimb(level, placer, random, startPos, startPos.above(trunkHeight), true, config);
        makeBranches(level, placer, random, treeHeight, startPos, foliageCoords, config);

        List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();
        for (FoliageCoords coords : foliageCoords) {
            if (trimBranches(treeHeight, coords.branchBase() - startPos.getY())) {
                attachments.add(coords.attachment());
            }
        }
        return attachments;
    }

    /**
     * Flares the base of the trunk out a little, like the root crown of a
     * real hardwood, instead of letting it rise as a plain 1×1 column.
     * The ground layer gets a full ring (cardinals + diagonals) and the
     * layer above gets just the four cardinal neighbours, tapering back
     * into the normal single-wide trunk from there on up.
     */
    private void thickenBase(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> placer,
                              RandomSource random, BlockPos startPos, TreeConfiguration config) {
        // Ground layer: full 3x3 ring around the core column.
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dz = -1; dz <= 1; ++dz) {
                if (dx == 0 && dz == 0) {
                    continue;
                }
                BlockPos ringPos = startPos.offset(dx, 0, dz);
                setDirtAt(level, placer, random, ringPos.below(), config);
                placeLog(level, placer, random, ringPos, config);
            }
        }

        // One layer up: just the four cardinal neighbours, so the flare tapers.
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            placeLog(level, placer, random, startPos.above().relative(direction), config);
        }
    }

    private boolean makeLimb(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> placer, RandomSource random,
                              BlockPos basePos, BlockPos offsetPos, boolean modifyWorld, TreeConfiguration config) {
        if (!modifyWorld && basePos.equals(offsetPos)) {
            return true;
        }

        BlockPos delta = offsetPos.offset(-basePos.getX(), -basePos.getY(), -basePos.getZ());
        int steps = getSteps(delta);
        float fx = (float) delta.getX() / (float) steps;
        float fy = (float) delta.getY() / (float) steps;
        float fz = (float) delta.getZ() / (float) steps;
        Direction.Axis axis = getLogAxis(basePos, offsetPos);

        for (int step = 0; step <= steps; ++step) {
            BlockPos pos = basePos.offset(
                    Mth.floor(0.5F + (float) step * fx),
                    Mth.floor(0.5F + (float) step * fy),
                    Mth.floor(0.5F + (float) step * fz));

            if (modifyWorld) {
                if (axis == Direction.Axis.Y) {
                    placeLog(level, placer, random, pos, config);
                } else {
                    placeLog(level, placer, random, pos, config, state -> state.setValue(RotatedPillarBlock.AXIS, axis));
                }
            } else if (!isFree(level, pos)) {
                return false;
            }
        }

        return true;
    }

    private int getSteps(BlockPos delta) {
        int x = Mth.abs(delta.getX());
        int y = Mth.abs(delta.getY());
        int z = Mth.abs(delta.getZ());
        return Math.max(x, Math.max(y, z));
    }

    private Direction.Axis getLogAxis(BlockPos pos, BlockPos otherPos) {
        Direction.Axis axis = Direction.Axis.Y;
        int dx = Math.abs(otherPos.getX() - pos.getX());
        int dz = Math.abs(otherPos.getZ() - pos.getZ());
        int max = Math.max(dx, dz);
        if (max > 0) {
            axis = dx == max ? Direction.Axis.X : Direction.Axis.Z;
        }

        return axis;
    }

    private boolean trimBranches(int maxHeight, int currentHeight) {
        return (double) currentHeight >= (double) maxHeight * 0.2D;
    }

    private void makeBranches(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> placer, RandomSource random,
                               int maxHeight, BlockPos pos, List<FoliageCoords> foliageCoords, TreeConfiguration config) {
        for (FoliageCoords coords : foliageCoords) {
            int branchY = coords.branchBase();
            BlockPos branchPos = new BlockPos(pos.getX(), branchY, pos.getZ());
            if (!branchPos.equals(coords.attachment().pos()) && trimBranches(maxHeight, branchY - pos.getY())) {
                makeLimb(level, placer, random, branchPos, coords.attachment().pos(), true, config);
            }
        }
    }

    private static float treeShape(int height, int currentY) {
        if ((float) currentY < (float) height * 0.3F) {
            return -1.0F;
        }

        float half = (float) height / 2.0F;
        float deltaFromMid = half - (float) currentY;
        float radius = Mth.sqrt(half * half - deltaFromMid * deltaFromMid);
        if (deltaFromMid == 0.0F) {
            radius = half;
        } else if (Math.abs(deltaFromMid) >= half) {
            return 0.0F;
        }

        return radius * 0.5F;
    }

    private static final class FoliageCoords {
        private final FoliagePlacer.FoliageAttachment attachment;
        private final int branchBase;

        private FoliageCoords(BlockPos attachmentPos, int branchBase) {
            this.attachment = new FoliagePlacer.FoliageAttachment(attachmentPos, 0, false);
            this.branchBase = branchBase;
        }

        private FoliagePlacer.FoliageAttachment attachment() {
            return attachment;
        }

        private int branchBase() {
            return branchBase;
        }
    }
}
