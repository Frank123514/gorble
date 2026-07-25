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

/**
 * Foliage placer for large broadleaf hardwood trees (ash, beech, elm, chestnut,
 * willow, cottonwood, ironwood, weirwood, ebony, etc.).
 *
 * <h3>Shape</h3>
 * A single round-ish ellipsoid, same idea as Middle Earth's {@code OvalFoliagePlacer}:
 * a plain oval-blob canopy with a small amount of per-block randomness on the
 * boundary so it doesn't look mathematically perfect. No lobes, no bands, no
 * bottom clipping — just one clean rounded volume.
 *
 * <h3>Parameters</h3>
 * <ul>
 *   <li>{@code radius} (IntProvider) — half-width of the canopy in X/Z.
 *       Typical value: 3–5.</li>
 *   <li>{@code offset} (IntProvider) — vertical offset of the attachment point.
 *       Usually 0.</li>
 *   <li>{@code layers} — full vertical height of the canopy (half-height = layers/2).
 *       Typical: 4–6.</li>
 * </ul>
 */
public class GotBroadleafFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<GotBroadleafFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance)
                    .and(net.minecraft.util.ExtraCodecs.POSITIVE_INT
                            .fieldOf("layers").forGetter(p -> p.layers))
                    .apply(instance, GotBroadleafFoliagePlacer::new));

    /** Full vertical span of the canopy. Half-height of the ellipsoid = layers / 2f. */
    private final int layers;

    public GotBroadleafFoliagePlacer(IntProvider radius, IntProvider offset, int layers) {
        super(radius, offset);
        this.layers = layers;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return GotTreePlacers.GOT_BROADLEAF_FOLIAGE_PLACER.get();
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

        // Scaled down slightly from the raw radius/layers config so the
        // canopy reads as smaller relative to the trunk.
        float halfH = (layers / 2.0f) * 0.85f;
        float halfR = foliageRadius * 0.85f;

        // Centre the ellipsoid roughly on top of the trunk attachment point.
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

    /**
     * Simple ellipsoid test with a small amount of per-point randomness on
     * the radius, so the boundary isn't a mathematically perfect oval.
     */
    private static boolean isPointInside(int dx, int dy, int dz, float halfR, float halfH, RandomSource random) {
        // Smaller jitter range than before (was ±0.35) — keeps the boundary
        // from looking rough/jagged while still avoiding a perfectly
        // mathematical oval.
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
        // All placement decisions are made inside createFoliage.
        return false;
    }
}