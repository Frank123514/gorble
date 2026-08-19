package net.francis.got.worldgen.biome.placers;

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

public class PalmFoliagePlacer extends FoliagePlacer {

    private final int frondLength;
    
    private final int frondCount;

    public static final MapCodec<PalmFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance)
                    .and(ExtraCodecs.POSITIVE_INT.fieldOf("frond_length").forGetter(p -> p.frondLength))
                    .and(ExtraCodecs.POSITIVE_INT.fieldOf("frond_count").forGetter(p -> p.frondCount))
                    .apply(instance, PalmFoliagePlacer::new));

    public PalmFoliagePlacer(IntProvider radius, IntProvider offset, int frondLength, int frondCount) {
        super(radius, offset);
        this.frondLength = frondLength;
        this.frondCount  = frondCount;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return TreePlacers.GOT_PALM_FOLIAGE_PLACER.get();
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

        float velocity     =  0.35f;
        
        float acceleration = -0.22f;

        int   count1       = frondCount;
        double angleStep1  = 360.0 / count1;
        double angleOffset1 = random.nextDouble() * 30.0;

        for (int i = 0; i < count1; i++) {
            double angleDeg = angleStep1 * i + angleOffset1;
            createArcFrond(level, setter, random, config, crown,
                    frondLength, Math.toRadians(angleDeg), acceleration, velocity);
        }

        int    count2       = count1 - 1;
        double angleStep2   = 360.0 / count2;
        double angleOffset2 = angleOffset1 + random.nextDouble() * 20.0;

        for (int i = 0; i < count2; i++) {
            double angleDeg = angleStep2 * i + angleOffset2;
            
            createArcFrond(level, setter, random, config, crown,
                    frondLength, Math.toRadians(angleDeg), acceleration - 0.05f, velocity + 0.25f);
        }
    }

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

            placeLeafAt(level, setter, random, config,
                    (int) Math.round(posX), (int) Math.round(posY), (int) Math.round(posZ));

            if ((int) Math.round(lastY) != (int) Math.round(posY)) {
                placeLeafAt(level, setter, random, config,
                        (int) Math.round(posX), (int) Math.round(posY) + 1, (int) Math.round(posZ));
            }

            vel += acceleration;
        }
    }

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