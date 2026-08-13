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

public class AspenFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<AspenFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance)
                    .and(ExtraCodecs.POSITIVE_INT.fieldOf("tiers").forGetter(p -> p.tiers))
                    .apply(instance, AspenFoliagePlacer::new));

    private final int tiers;

    public AspenFoliagePlacer(IntProvider radius, IntProvider offset, int tiers) {
        super(radius, offset);
        this.tiers = tiers;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return TreePlacers.GOT_ASPEN_FOLIAGE_PLACER.get();
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

        double maxR = foliageRadius;
        BlockPos base = attachment.pos().above(offset).below(5);

        for (int y = 0; y < foliageHeight; y++) {
            double t = (double) y / (foliageHeight - 1);

            double sliceR;
            double coneJoin = 0.6;
            if (t <= coneJoin) {
                double u = (t / coneJoin) - 1.0;
                sliceR = maxR * Math.sqrt(1.0 - u * u);
            } else {
                sliceR = maxR * (1.0 - t) / (1.0 - coneJoin);
            }

            boolean applyDropout = t <= coneJoin;
            placeDisc(level, setter, random, config, base.offset(0, y, 0),
                    Math.max(0.3, sliceR), applyDropout);
        }
    }

    private void placeDisc(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos centre,
            double r,
            boolean dropout) {

        int ri = (int) Math.ceil(r);
        for (int dx = -ri; dx <= ri; dx++) {
            for (int dz = -ri; dz <= ri; dz++) {
                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist > r + 0.5) continue;

                if (dropout) {
                    
                    double edgeFactor = dist / r;
                    
                    if (edgeFactor > 0.6) {
                        
                        double dropChance = ((edgeFactor - 0.6) / 0.4) * 0.6;
                        if (random.nextDouble() < dropChance) continue;
                    }
                }

                tryPlaceLeaf(level, setter, random, config, centre.offset(dx, 0, dz));
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return tiers;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int dy, int dz,
                                         int range, boolean large) {
        return false;
    }
}
