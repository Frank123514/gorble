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

public class BroadleafFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<BroadleafFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance)
                    .and(net.minecraft.util.ExtraCodecs.POSITIVE_INT
                            .fieldOf("layers").forGetter(p -> p.layers))
                    .apply(instance, BroadleafFoliagePlacer::new));

    private final int layers;

    public BroadleafFoliagePlacer(IntProvider radius, IntProvider offset, int layers) {
        super(radius, offset);
        this.layers = layers;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return TreePlacers.GOT_BROADLEAF_FOLIAGE_PLACER.get();
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

        float halfH = (layers / 2.0f) * 0.85f;
        float halfR = foliageRadius * 0.85f;

        BlockPos centre = attachment.pos().above(offset).above(Math.round(halfH));

        int searchR = foliageRadius + 1;

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int dx = -searchR; dx <= searchR; dx++) {
            for (int dz = -searchR; dz <= searchR; dz++) {
                for (int dy = -(int) halfH; dy <= (int) halfH; dy++) {
                    if (isPointInside(dx, dy, dz, halfR, halfH, random)) {
                        mutable.setWithOffset(centre, dx, dy, dz);
                        tryPlaceLeaf(level, setter, random, config, mutable);
                    }
                }
            }
        }
    }

    private static boolean isPointInside(int dx, int dy, int dz, float halfR, float halfH, RandomSource random) {
        
        float randomness = -0.15f + random.nextFloat() * 0.3f;
        float rr = (halfR + randomness) * (halfR + randomness);
        float rh = (halfH + randomness) * (halfH + randomness);

        float ex = (dx * dx) / rr;
        float ey = (dy * dy) / rh;
        float ez = (dz * dz) / rr;

        return ex + ey + ez <= 1.0f;
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return layers;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int dy, int dz,
                                         int range, boolean large) {
        
        return false;
    }
}