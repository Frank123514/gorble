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

import java.util.List;

/**
 * Places a single rounded, partially-embedded boulder — an irregular lumpy
 * dome instead of vanilla's {@code minecraft:forest_rock}, which just drops
 * one block on the surface.
 *
 * <h2>Shape</h2>
 * A vertically-squashed sphere ({@code height_scale} controls the squash)
 * centered a bit below the found surface, so it pokes up as a mound with a
 * naturally grounded, buried base instead of floating. Per-column simplex
 * noise jitters the effective radius so the outline is lumpy/rocky rather
 * than a perfect dome.
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

        // Center sits partway into the ground so the dome pokes up above the
        // surface with a naturally grounded, partially-buried base instead
        // of floating on top of it.
        int embed = Math.max(1, radius / 2);
        BlockPos center = surface.below(embed);

        boolean placed = false;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                // Per-column radius jitter for a lumpy, rocky outline instead
                // of a perfect dome.
                double jitterAmt = noise.eval(
                        (center.getX() + dx) * noiseScale,
                        (center.getZ() + dz) * noiseScale);
                double effRadius = radius * (1.0 + jitterAmt * jitter);

                for (int dy = -radius; dy <= radius; dy++) {
                    double scaledDy = dy / heightScale;
                    double distSq = dx * dx + dz * dz + scaledDy * scaledDy;
                    if (distSq > effRadius * effRadius) continue;

                    BlockPos pos = center.offset(dx, dy, dz);
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
