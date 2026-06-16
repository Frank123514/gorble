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
 * Foliage placer for aspen and birch — tall, slender hardwoods whose canopy
 * reads as a thin, ragged column rather than a rounded dome.
 *
 * <h3>Shape</h3>
 * Produces a tall narrow stack of leaf discs with a real air gap of one
 * empty block between each disc, so the canopy looks airy and "perforated"
 * rather than a solid blob, and rises well above where a normal hardwood
 * crown would stop.
 *
 * <ul>
 *   <li>Each tier is a flat disc of leaves 1–2 blocks thick.  The bottom
 *       disc is the widest (tiers * 2 blocks across); each tier up shrinks
 *       by one block of radius.</li>
 *   <li>Between tiers, one Y-level is left completely empty so light and
 *       sky show through the canopy in patches.</li>
 *   <li>The tip is a 3-tall pointed cap with radius 1, giving the silhouette
 *       a thin spire rather than a flat top.</li>
 *   <li>A randomised ±1-block jitter on each tier's disc radius breaks
 *       perfect symmetry between trees of the same species.</li>
 * </ul>
 *
 * <h3>Parameters</h3>
 * <ul>
 *   <li>{@code radius} (IntProvider) — radius of the widest (base) tier.
 *       Typical: 2.</li>
 *   <li>{@code offset} (IntProvider) — vertical offset of the attachment.
 *       Usually 0.</li>
 *   <li>{@code tiers} — number of distinct disc tiers below the tip cap.
 *       Typical: 4.  More tiers = taller canopy.</li>
 * </ul>
 */
public class GotAspenFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<GotAspenFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance)
                    .and(ExtraCodecs.POSITIVE_INT.fieldOf("tiers").forGetter(p -> p.tiers))
                    .apply(instance, GotAspenFoliagePlacer::new));

    /** Number of distinct disc tiers below the tip spike. */
    private final int tiers;

    public GotAspenFoliagePlacer(IntProvider radius, IntProvider offset, int tiers) {
        super(radius, offset);
        this.tiers = tiers;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return GotTreePlacers.GOT_ASPEN_FOLIAGE_PLACER.get();
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
        int baseRadius = foliageRadius;

        // Build tiers from bottom to top.
        // Each tier occupies 2 Y-levels (double layer for thickness), then
        // 1 Y-level gap (empty) before the next tier.
        // stride = 3 (2 leaf + 1 gap)
        int stride = 3;

        for (int tier = 0; tier < tiers; tier++) {
            int y = tier * stride;

            // Radius shrinks linearly from baseRadius at tier=0 to 1 at tier=tiers-1
            int r = Math.max(1, baseRadius - tier);

            // Small random jitter so each tree is slightly different
            int jitter = random.nextInt(2) == 0 ? -1 : 0;  // 0 or -1
            int actualR = Math.max(1, r + jitter);

            // Two leaf layers per tier — gives visual thickness like a real branch whorl
            placeTierDisc(level, setter, random, config, crown.above(y),     actualR);
            placeTierDisc(level, setter, random, config, crown.above(y + 1), Math.max(1, actualR - 1));
            // y+2 is the gap — left empty intentionally
        }

        // Pointed tip: 3 layers narrowing to a single block
        int tipBase = tiers * stride;
        placeTierDisc(level, setter, random, config, crown.above(tipBase),     1);
        placeTierDisc(level, setter, random, config, crown.above(tipBase + 1), 1);
        tryPlaceLeaf(level, setter, random, config, crown.above(tipBase + 2)); // single apex block
    }

    /**
     * Places a circular disc of leaves at the given position.
     * Uses a squared Euclidean distance test so the disc is round, not square.
     */
    private void placeTierDisc(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos centre,
            int r) {

        int rSq = r * r + r; // slightly generous cutoff for natural rounding
        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                if (dx * dx + dz * dz > rSq) continue;
                tryPlaceLeaf(level, setter, random, config, centre.offset(dx, 0, dz));
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        // tiers * 3 (2 leaf + 1 gap each) + 3 for the tip spike
        return tiers * 3 + 3;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int dy, int dz,
                                         int range, boolean large) {
        return false;
    }
}
