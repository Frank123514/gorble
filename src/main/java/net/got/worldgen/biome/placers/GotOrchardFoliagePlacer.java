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
 * Foliage placer for small orchard and fruit trees (apple, pear, cherry, plum,
 * peach, fig, olive, pomegranate, almond, crabapple, citrus, etc.).
 *
 * <h3>Shape</h3>
 * Produces a compact, roughly spherical crown that is slightly wider at its
 * mid-section and droops gently at the base — the classic "lollipop" silhouette
 * of a well-pruned orchard tree, as opposed to the tall spreading dome of a
 * large hardwood.
 *
 * <ul>
 *   <li>The canopy is 4 layers tall: droop, low, mid (widest), and cap.</li>
 *   <li>The lowest (droop) layer uses a strict Euclidean circle so it hangs
 *       in a clean rounded hem rather than a square block.</li>
 *   <li>Mid layers fill solidly at the full radius.</li>
 *   <li>The cap is a single-block-radius disc with light corner erosion.</li>
 *   <li>All layers use Euclidean distance trimming so the tree reads as round,
 *       not rectangular.</li>
 * </ul>
 *
 * <h3>Parameters</h3>
 * <ul>
 *   <li>{@code radius} (IntProvider) — half-width of the widest layer.  2 gives
 *       a tight tree; 3 gives a slightly fuller one.</li>
 *   <li>{@code offset} (IntProvider) — vertical offset of the attachment.
 *       Usually 0.</li>
 * </ul>
 */
public class GotOrchardFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<GotOrchardFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance)
                    .apply(instance, GotOrchardFoliagePlacer::new));

    public GotOrchardFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return GotTreePlacers.GOT_ORCHARD_FOLIAGE_PLACER.get();
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
        int r = foliageRadius; // widest mid-layer radius

        // Layer layout (bottom to top):
        //  y=0 : droop hem — radius r-1, strict Euclidean circle (drooping skirt)
        //  y=1 : low      — radius r,   Euclidean with light corner erosion
        //  y=2 : mid      — radius r,   Euclidean, full density (widest)
        //  y=3 : cap      — radius r-1, Euclidean, some corner erosion
        //  y=4 : tip      — radius 1,   3×3 solid cap (only if r >= 2)

        // y=0 droop: tight Euclidean circle, slightly smaller radius
        placeRoundLayer(level, setter, random, config, crown.above(0),
                Math.max(1, r - 1), r * r, 0.55f, random);

        // y=1 low: full radius, slight corner rounding
        placeRoundLayer(level, setter, random, config, crown.above(1),
                r, r * r + r, 0.25f, random);

        // y=2 mid: full radius, densest layer
        placeRoundLayer(level, setter, random, config, crown.above(2),
                r, r * r + r, 0.10f, random);

        // y=3 cap: one smaller
        placeRoundLayer(level, setter, random, config, crown.above(3),
                Math.max(1, r - 1), (r - 1) * (r - 1) + r, 0.30f, random);

        // y=4 tip: 1-block-radius crown nub for taller orchard trees
        if (r >= 2) {
            placeRoundLayer(level, setter, random, config, crown.above(4),
                    1, 2, 0.0f, random);
        }
    }

    /**
     * Places one horizontal disc of leaves.
     *
     * @param centre      centre block of the disc
     * @param r           bounding square half-width (iterates -r..r)
     * @param maxDistSq   Euclidean squared-distance cutoff; blocks beyond this are skipped
     * @param cornerSkip  extra probability to skip blocks at the outer ring
     * @param random      random source for corner erosion
     */
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

                // Corner erosion: outermost ring only
                boolean outerRing = distSq >= maxDistSq - r;
                if (outerRing && rng.nextFloat() < cornerSkip) continue;

                tryPlaceLeaf(level, setter, random, config,
                        centre.offset(dx, 0, dz));
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        // 5 layers (y=0..4), or 4 if radius will be 1
        return 5;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int dy, int dz,
                                         int range, boolean large) {
        return false;
    }
}
