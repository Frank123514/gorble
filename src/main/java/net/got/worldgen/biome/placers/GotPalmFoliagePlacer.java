package net.got.worldgen.biome.placers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

/**
 * Palm foliage placer that creates smooth arching frond arms radiating
 * outward from the crown, using continuous floating-point arc physics
 * inspired by Middle Earth mod's palm foliage placer.
 *
 * Each frond arm is traced with a velocity + acceleration curve so
 * leaves naturally sweep upward then droop, filling in two leaf blocks
 * per vertical step for visual thickness.
 *
 * Two overlapping rings of fronds are spawned at slightly offset angles
 * so the canopy looks full and layered rather than sparse.
 */
public class GotPalmFoliagePlacer extends FoliagePlacer {

    /** Number of leaf blocks traced along each frond arm. */
    private final int frondLength;
    /** Number of frond arms in the primary ring. */
    private final int frondCount;

    public static final MapCodec<GotPalmFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance)
                    .and(ExtraCodecs.POSITIVE_INT.fieldOf("frond_length").forGetter(p -> p.frondLength))
                    .and(ExtraCodecs.POSITIVE_INT.fieldOf("frond_count").forGetter(p -> p.frondCount))
                    .apply(instance, GotPalmFoliagePlacer::new));

    public GotPalmFoliagePlacer(IntProvider radius, IntProvider offset, int frondLength, int frondCount) {
        super(radius, offset);
        this.frondLength = frondLength;
        this.frondCount  = frondCount;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return GotTreePlacers.GOT_PALM_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            int maxFreeTreeHeight,
            FoliageAttachment attachment,
            int foliageHeight,
            int foliageRadius,
            int offset) {

        BlockPos crown = attachment.pos().above(offset);

        // ── Physics parameters (tuned to match Middle Earth aesthetic) ────────
        // velocity: initial upward kick at the frond base (positive = up)
        float velocity     =  0.35f;
        // acceleration: gravity pulling the frond down each step
        float acceleration = -0.22f;

        // ── Ring 1: primary fronds ────────────────────────────────────────────
        int   count1       = frondCount;
        double angleStep1  = 360.0 / count1;
        double angleOffset1 = random.nextDouble() * 30.0;

        for (int i = 0; i < count1; i++) {
            double angleDeg = angleStep1 * i + angleOffset1;
            createArcFrond(level, setter, random, config, crown,
                    frondLength, Math.toRadians(angleDeg), acceleration, velocity);
        }

        // ── Ring 2: secondary fronds, offset angle + shorter/steeper droop ───
        int    count2       = count1 - 1;
        double angleStep2   = 360.0 / count2;
        double angleOffset2 = angleOffset1 + random.nextDouble() * 20.0;

        for (int i = 0; i < count2; i++) {
            double angleDeg = angleStep2 * i + angleOffset2;
            // Slightly stronger droop and a gentler initial rise for variety
            createArcFrond(level, setter, random, config, crown,
                    frondLength, Math.toRadians(angleDeg), acceleration - 0.05f, velocity + 0.25f);
        }
    }

    /**
     * Traces one frond arm outward from {@code startPos} in the given
     * horizontal angle, following a velocity+acceleration arc vertically.
     *
     * At each step the floating-point position is rounded to block coords.
     * Whenever the arc changes Y level, an extra leaf block is placed at
     * the previous height to bridge the gap and keep fronds thick.
     */
    private void createArcFrond(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos startPos,
            int length,
            double angleRad,
            float acceleration,
            float velocity) {

        double dirX = Math.cos(angleRad);
        double dirZ = Math.sin(angleRad);

        // Current continuous position starts one block below crown so the
        // first step lands at crown height, matching Middle Earth behaviour.
        double posX = startPos.getX();
        double posY = startPos.getY() - 1.0;
        double posZ = startPos.getZ();

        double lastY = posY;
        float  vel   = velocity;

        for (int step = 0; step < length; step++) {
            lastY = posY;

            posX += dirX;
            posY += Math.max(-1.0, vel);
            posZ += dirZ;

            // Primary leaf block at current arc position
            placeLeafAt(level, setter, random, config,
                    (int) Math.round(posX), (int) Math.round(posY), (int) Math.round(posZ));

            // Fill-in block: when Y changes, also place a leaf at the old Y
            // so the frond never has single-pixel-thin transitions.
            if ((int) Math.round(lastY) != (int) Math.round(posY)) {
                placeLeafAt(level, setter, random, config,
                        (int) Math.round(posX), (int) Math.round(posY) + 1, (int) Math.round(posZ));
            }

            vel += acceleration;
        }
    }

    /** Convenience wrapper: build a BlockPos and call tryPlaceLeaf. */
    private void placeLeafAt(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            int x, int y, int z) {
        tryPlaceLeaf(level, setter, random, config, new BlockPos(x, y, z));
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int dy, int dz,
                                         int range, boolean large) {
        return false;
    }
}