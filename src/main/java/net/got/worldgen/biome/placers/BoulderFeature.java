package net.got.worldgen.biome.placers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.worldgen.SimplexNoise;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Places a single rounded, partially-embedded boulder cluster — an irregular
 * lumpy rock pile instead of vanilla's {@code minecraft:forest_rock}, which
 * just drops one block on the surface.
 *
 * <h2>Shape</h2>
 * A main vertically-squashed sphere ({@code height_scale} controls the
 * squash) centered a bit below the found surface, so it pokes up as a mound
 * with a naturally grounded, buried base instead of floating. One or two
 * smaller "shoulder" lobes are fused onto the side, offset lower than the
 * main mass, so the boulder reads as an asymmetric rock pile with a stepped
 * silhouette rather than a single perfect dome. Per-column simplex noise
 * jitters each lobe's effective radius (with its own noise offset) so the
 * outline is lumpy/rocky rather than smooth.
 *
 * <h2>JSON example</h2>
 * <pre>{@code
 * {
 *   "type": "got:boulder",
 *   "config": {
 *     "block":        { "type": "minecraft:simple_state_provider", "state": { "Name": "minecraft:stone" } },
 *     "targets":      [ { "Name": "minecraft:grass_block" }, { "Name": "minecraft:dirt" }, { "Name": "minecraft:stone" } ],
 *     "radius":       2,
 *     "height_scale": 0.75,
 *     "jitter":       0.3
 *   }
 * }
 * }</pre>
 */
public class BoulderFeature extends Feature<BoulderFeature.Config> {

    public static final Codec<Config> CONFIG_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BlockStateProvider.CODEC
                    .fieldOf("block")
                    .forGetter(Config::block),
            BlockState.CODEC.listOf()
                    .fieldOf("targets")
                    .forGetter(Config::targets),
            Codec.intRange(1, 6)
                    .optionalFieldOf("radius", 2)
                    .forGetter(Config::radius),
            Codec.doubleRange(0.3, 1.5)
                    .optionalFieldOf("height_scale", 0.75)
                    .forGetter(Config::heightScale),
            Codec.doubleRange(0.0, 1.0)
                    .optionalFieldOf("jitter", 0.3)
                    .forGetter(Config::jitter)
    ).apply(inst, Config::new));

    public BoulderFeature(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> ctx) {
        WorldGenLevel level  = ctx.level();
        BlockPos      origin = ctx.origin();
        RandomSource  rand   = ctx.random();
        Config        cfg    = ctx.config();

        BlockPos surface = findSurface(level, origin, 4);
        if (surface == null) return false;

        int radius = cfg.radius();
        double heightScale = cfg.heightScale();
        double jitter = cfg.jitter();

        SimplexNoise noise = SimplexNoise.seeded(rand.nextLong());
        // A couple of lumps across the whole boulder, not fine grain.
        double noiseScale = 1.2 / Math.max(1, radius);

        // Main center sits partway into the ground so the mass pokes up
        // above the surface with a naturally grounded, partially-buried
        // base instead of floating on top of it.
        int embed = Math.max(1, radius / 2);
        BlockPos mainCenter = surface.below(embed);

        List<Lobe> lobes = new ArrayList<>();
        lobes.add(new Lobe(mainCenter, radius, heightScale, 0.0, 0.0));

        // Fuse one or two smaller "shoulder" lobes onto the main mass,
        // offset to a side and sitting lower, so the boulder reads as an
        // asymmetric rock pile with a stepped silhouette instead of a
        // single perfect dome.
        int extraLobes = radius >= 2 ? 1 + rand.nextInt(2) : rand.nextInt(2);
        for (int i = 0; i < extraLobes; i++) {
            double angle = rand.nextDouble() * Math.PI * 2;
            double dist  = radius * (0.45 + rand.nextDouble() * 0.35);
            int lobeDx = (int) Math.round(Math.cos(angle) * dist);
            int lobeDz = (int) Math.round(Math.sin(angle) * dist);
            int lobeDy = -(1 + rand.nextInt(Math.max(1, radius / 2 + 1)));
            double lobeRadius = radius * (0.5 + rand.nextDouble() * 0.3);
            double lobeHeightScale = heightScale * (0.7 + rand.nextDouble() * 0.4);
            lobes.add(new Lobe(
                    mainCenter.offset(lobeDx, lobeDy, lobeDz),
                    lobeRadius, lobeHeightScale,
                    (i + 1) * 37.0, (i + 1) * 71.0));
        }

        // Generous margin: shoulder lobes can sit up to ~radius*0.8 away from
        // the main center and extend up to ~radius*1.05 further with jitter,
        // so a small fixed radius+2 pad isn't always enough headroom.
        int reach = radius + 4;
        boolean placed = false;
        for (int dx = -reach; dx <= reach; dx++) {
            for (int dz = -reach; dz <= reach; dz++) {
                for (int dy = -reach; dy <= reach; dy++) {
                    BlockPos pos = mainCenter.offset(dx, dy, dz);
                    if (!insideAnyLobe(pos, lobes, noise, noiseScale, jitter)) continue;

                    BlockState existing = level.getBlockState(pos);

                    if (dy >= 0) {
                        // Above-ground portion: only build out into open air
                        // or ground poking through (e.g. surface grass),
                        // never through unrelated solid terrain/structures.
                        if (!existing.isAir() && !isTarget(existing, cfg)) continue;
                    } else {
                        // Embedded portion: only carve into ordinary ground,
                        // never air pockets/caves/water below the surface.
                        if (!isTarget(existing, cfg)) continue;
                    }

                    level.setBlock(pos, cfg.block().getState(rand, pos), Block.UPDATE_CLIENTS);
                    placed = true;
                }
            }
        }

        return placed;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private record Lobe(BlockPos center, double radius, double heightScale,
                        double noiseOffsetX, double noiseOffsetZ) {}

    private static boolean insideAnyLobe(BlockPos pos, List<Lobe> lobes, SimplexNoise noise,
                                         double noiseScale, double jitter) {
        for (Lobe lobe : lobes) {
            int lx = pos.getX() - lobe.center().getX();
            int ly = pos.getY() - lobe.center().getY();
            int lz = pos.getZ() - lobe.center().getZ();

            double jitterAmt = noise.eval(
                    (pos.getX() + lobe.noiseOffsetX()) * noiseScale,
                    (pos.getZ() + lobe.noiseOffsetZ()) * noiseScale);
            double effRadius = lobe.radius() * (1.0 + jitterAmt * jitter);

            double scaledDy = ly / lobe.heightScale();
            double distSq = lx * lx + lz * lz + scaledDy * scaledDy;
            if (distSq <= effRadius * effRadius) return true;
        }
        return false;
    }

    private static BlockPos findSurface(WorldGenLevel level, BlockPos pos, int yRange) {
        for (int dy = yRange; dy >= -yRange; dy--) {
            BlockPos candidate = pos.above(dy);
            BlockState state = level.getBlockState(candidate);
            if (state.isAir()) continue;
            if (!level.getBlockState(candidate.above()).isAir()) continue;
            return candidate;
        }
        return null;
    }

    private static boolean isTarget(BlockState state, Config cfg) {
        for (BlockState target : cfg.targets()) {
            if (state.is(target.getBlock())) return true;
        }
        return false;
    }

    // ── Config record ─────────────────────────────────────────────────────────

    /**
     * @param block       Block to place.
     * @param targets     Ground blocks the boulder is allowed to carve into
     *                     (embedded base) or poke up through (surface layer).
     * @param radius       Base radius in blocks, before jitter (default 2).
     * @param heightScale  Vertical squash — 1.0 is a perfect sphere, lower
     *                     values flatten it into more of a rounded mound
     *                     (default 0.75).
     * @param jitter       0..1 — how much the per-column radius varies for a
     *                     lumpy, irregular outline instead of a smooth dome
     *                     (default 0.3).
     */
    public record Config(
            BlockStateProvider block,
            List<BlockState>   targets,
            int                radius,
            double             heightScale,
            double             jitter
    ) implements FeatureConfiguration {}
}