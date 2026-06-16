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
 * Produces a wide, organically rounded canopy that mimics the reference tree silhouette:
 * <ul>
 *   <li>An oblate ellipsoid forms the main canopy volume — wider than tall.</li>
 *   <li>Several small "lobe" clusters are placed around the equator to break up
 *       the silhouette and simulate leaf masses sitting on branches.</li>
 *   <li>Per-block Perlin-style noise erodes the ellipsoid surface so the outline
 *       is lumpy and organic rather than mathematically smooth.</li>
 *   <li>The very bottom of the ellipsoid is clipped off so the underside feels
 *       open and natural — leaves don't hang straight down to trunk level.</li>
 * </ul>
 *
 * <h3>Parameters</h3>
 * <ul>
 *   <li>{@code radius} (IntProvider) — half-width of the canopy in X/Z.
 *       Typical value: 3–5.</li>
 *   <li>{@code offset} (IntProvider) — vertical offset of the attachment point.
 *       Usually 0.</li>
 *   <li>{@code layers} — vertical height of the ellipsoid (half-height = layers/2).
 *       Controls how tall the canopy is. Typical: 4–6.</li>
 * </ul>
 */
public class GotBroadleafFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<GotBroadleafFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance)
                    .and(net.minecraft.util.ExtraCodecs.POSITIVE_INT
                            .fieldOf("layers").forGetter(p -> p.layers))
                    .apply(instance, GotBroadleafFoliagePlacer::new));

    /** Vertical span of the canopy. Half-height of the ellipsoid = layers / 2f. */
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

        // The attachment point is the top of the trunk. We want the canopy to
        // sit ON TOP of it, so we shift the ellipsoid centre up by half its
        // vertical height.
        float halfH = layers / 2.0f;
        float halfR = foliageRadius;     // horizontal semi-axis

        // Centre the ellipsoid so its bottom third sits just above the trunk top.
        // Shift up by (halfH * 0.55) so the widest part is roughly 55% up.
        BlockPos centre = attachment.pos().above(offset).above(Math.round(halfH * 0.55f));

        // Bounding box to iterate over
        int searchR = foliageRadius + 2;
        int searchH = layers + 1;

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int dy = -(int) halfH; dy <= searchH; dy++) {
            for (int dx = -searchR; dx <= searchR; dx++) {
                for (int dz = -searchR; dz <= searchR; dz++) {

                    // --- Main ellipsoid test ---
                    // Use an oblate ellipsoid: (x/rx)^2 + (y/ry)^2 + (z/rz)^2 <= 1
                    // rx = rz = halfR (wide),  ry = halfH (shorter vertically)
                    // Add per-position noise so the surface is lumpy.
                    float noise = surfaceNoise(dx, dy, dz, random);
                    float rx = halfR + noise;
                    float ry = halfH + noise * 0.5f;

                    float ellipsoid = sq(dx / rx) + sq(dy / ry) + sq(dz / rx);

                    // Clip the lower quarter of the ellipsoid so there's open
                    // air under the canopy (looks more natural).
                    // Below the bottom 25% of the ellipsoid, require a tighter fit.
                    float bottomClip = -halfH * 0.25f;
                    if (dy < bottomClip) {
                        // Only keep blocks that are well inside the ellipsoid
                        if (ellipsoid > 0.55f) continue;
                    } else {
                        if (ellipsoid > 1.0f) continue;
                    }

                    mutable.setWithOffset(centre, dx, dy, dz);
                    tryPlaceLeaf(level, setter, random, config, mutable);
                }
            }
        }

        // --- Lobe clusters ---
        // Place 4–6 small spherical clusters offset outward near the equator of
        // the ellipsoid. These break up the outline and look like leaf masses
        // resting on large branches, matching the reference image.
        int lobeCount = 4 + random.nextInt(3); // 4–6
        float lobeOrbit = halfR * 0.85f;       // how far out from centre
        float lobeRadius = halfR * 0.55f;      // size of each cluster

        for (int i = 0; i < lobeCount; i++) {
            // Evenly distribute lobes around the circumference with slight jitter
            double baseAngle = (2.0 * Math.PI * i) / lobeCount;
            double angle = baseAngle + (random.nextDouble() - 0.5) * 0.6;

            int lobeX = (int) Math.round(Math.cos(angle) * lobeOrbit);
            int lobeZ = (int) Math.round(Math.sin(angle) * lobeOrbit);
            // Lobes sit slightly above the equator so they peek out over the top
            int lobeY = random.nextInt(Math.max(1, (int) (halfH * 0.5f)));

            BlockPos lobeCentre = centre.offset(lobeX, lobeY, lobeZ);

            for (int lx = -(int) lobeRadius - 1; lx <= (int) lobeRadius + 1; lx++) {
                for (int lz = -(int) lobeRadius - 1; lz <= (int) lobeRadius + 1; lz++) {
                    for (int ly = -(int) lobeRadius; ly <= (int) lobeRadius; ly++) {
                        float lobeNoise = (random.nextFloat() - 0.5f) * 0.5f;
                        float lr = lobeRadius + lobeNoise;
                        if (sq(lx / lr) + sq(ly / lr) + sq(lz / lr) > 1.0f) continue;
                        mutable.setWithOffset(lobeCentre, lx, ly, lz);
                        tryPlaceLeaf(level, setter, random, config, mutable);
                    }
                }
            }
        }
    }

    /**
     * Cheap per-position surface noise so the ellipsoid boundary is uneven.
     * Uses the position to seed some random variation rather than true coherent
     * noise — good enough for a block-art tree canopy.
     */
    private float surfaceNoise(int dx, int dy, int dz, RandomSource random) {
        // Hash the position into a 0..1 float, scale to desired amplitude.
        // We call this once per block, not per-call, so the variation is
        // deterministic relative to relative position while still being random
        // between different tree placements (random is already seeded per-tree).
        int hash = (dx * 1619 + dy * 31337 + dz * 6271) ^ (dx * dz);
        float t = ((hash & 0xFFFF) / 65535.0f);   // 0..1
        return (t - 0.5f) * 1.2f;                  // -0.6 .. +0.6 block units
    }

    private static float sq(float v) {
        return v * v;
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