package net.got.worldgen.biome.placers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class OrchardFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<OrchardFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance)
                    .apply(instance, OrchardFoliagePlacer::new));

    public OrchardFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return TreePlacers.GOT_ORCHARD_FOLIAGE_PLACER.get();
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
        int r = foliageRadius;

        placeRoundLayer(level, setter, random, config, crown.above(0),
                Math.max(1, r - 1), r * r, 0.55f, random);

        placeRoundLayer(level, setter, random, config, crown.above(1),
                r, r * r + r, 0.25f, random);

        placeRoundLayer(level, setter, random, config, crown.above(2),
                r, r * r + r, 0.10f, random);

        placeRoundLayer(level, setter, random, config, crown.above(3),
                Math.max(1, r - 1), (r - 1) * (r - 1) + r, 0.30f, random);

        if (r >= 2) {
            placeRoundLayer(level, setter, random, config, crown.above(4),
                    1, 2, 0.0f, random);
        }
    }

    private void placeRoundLayer(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos centre,
            int r,
            int maxDistSq,
            float cornerSkip,
            RandomSource rng) {

        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                int distSq = dx * dx + dz * dz;
                if (distSq > maxDistSq) continue;

                boolean outerRing = distSq >= maxDistSq - r;
                if (outerRing && rng.nextFloat() < cornerSkip) continue;

                tryPlaceLeaf(level, setter, random, config,
                        centre.offset(dx, 0, dz));
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        
        return 5;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int dy, int dz,
                                         int range, boolean large) {
        return false;
    }
}
