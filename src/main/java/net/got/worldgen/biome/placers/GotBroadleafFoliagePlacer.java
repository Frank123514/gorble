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
 * Produces a wide, irregularly layered dome canopy.  The crown is built from
 * several horizontal leaf layers stacked upward, each with a radius that grows
 * from the base up to the mid-section then tapers in again toward the crown tip.
 * Corners are probabilistically skipped on each layer so the outline looks
 * organic rather than square.
 *
 * <ul>
 *   <li>Base layers (lower half): full wide radius, heavy corner erosion for
 *       a hanging, irregular underbelly.  The lowest ring is the widest.</li>
 *   <li>Mid layers: full radius, lighter erosion, densest coverage.</li>
 *   <li>Upper layers: radius shrinks toward the tip; the top layer is a
 *       small 1-block cap.</li>
 * </ul>
 *
 * <h3>Parameters</h3>
 * <ul>
 *   <li>{@code radius} (IntProvider) — half-width of the widest layer in blocks.
 *       Typical value: 3–4.</li>
 *   <li>{@code offset} (IntProvider) — vertical offset of the attachment point.
 *       Usually 0.</li>
 *   <li>{@code layers} — total number of leaf layers.  Controls canopy height.
 *       Typical: 5–7.</li>
 * </ul>
 */
public class GotBroadleafFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<GotBroadleafFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance)
                    .and(net.minecraft.util.ExtraCodecs.POSITIVE_INT
                            .fieldOf("layers").forGetter(p -> p.layers))
                    .apply(instance, GotBroadleafFoliagePlacer::new));

    /** Total number of horizontal leaf layers making up the canopy. */
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

        BlockPos crown = attachment.pos().above(offset);
        int fullRadius  = foliageRadius;   // max radius (widest layer)

        // The crown tip is at (crown + layers - 1).
        // We build from bottom to top so y=0 is the lowest layer.
        for (int y = 0; y < layers; y++) {
            // Fraction through the canopy: 0 = bottom, 1 = top.
            float t = (float) y / (layers - 1);

            // Radius profile: starts at fullRadius at y=0, rises to fullRadius+1
            // at the 40% mark (densest mid-section), then tapers to 0 at the tip.
            // Using a tent curve peaking slightly below mid gives the classic
            // broadleaf "full belly, pointed crown" silhouette.
            int layerRadius;
            if (t < 0.40f) {
                // Lower half: constant full radius, occasionally +1 on wide layers
                layerRadius = fullRadius + (t < 0.20f && fullRadius >= 3 ? 1 : 0);
            } else {
                // Upper portion: taper from fullRadius down to 1
                float taper = (t - 0.40f) / 0.60f;          // 0 → 1 over upper 60%
                layerRadius = Math.max(1, Math.round(fullRadius * (1f - taper)));
            }

            // Corner erosion probability: heavy at the base, lighter in the
            // mid section, very tight near the crown tip.
            float cornerSkip;
            if (t < 0.20f) {
                cornerSkip = 0.60f;   // ragged underside
            } else if (t < 0.55f) {
                cornerSkip = 0.30f;   // moderate mid-canopy rounding
            } else {
                cornerSkip = 0.10f;   // near-solid crown cap
            }

            placeLayer(level, setter, random, config,
                    crown.above(y), layerRadius, cornerSkip);
        }
    }

    /**
     * Places a single horizontal layer of leaves centred on {@code centre},
     * spanning a square of side {@code 2*r+1} with organic corner erosion.
     *
     * @param cornerSkipChance probability [0,1] that a corner-region block is skipped
     */
    private void placeLayer(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos centre,
            int r,
            float cornerSkipChance) {

        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                // Chebyshev distance to the ring edge
                int cheby = Math.max(Math.abs(dx), Math.abs(dz));

                // Corner blocks are those where both |dx| and |dz| are large
                boolean isCorner = Math.abs(dx) == r && Math.abs(dz) == r;
                boolean isNearCorner = Math.abs(dx) >= r - 1 && Math.abs(dz) >= r - 1
                        && cheby == r;

                if (isCorner && random.nextFloat() < cornerSkipChance) continue;
                if (isNearCorner && random.nextFloat() < cornerSkipChance * 0.5f) continue;

                tryPlaceLeaf(level, setter, random, config,
                        centre.offset(dx, 0, dz));
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return layers;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int dy, int dz,
                                         int range, boolean large) {
        // Handled per-layer inside createFoliage; always return false here.
        return false;
    }
}
