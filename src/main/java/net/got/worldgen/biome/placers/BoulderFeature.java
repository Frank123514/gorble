package net.got.worldgen.biome.placers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.worldgen.SimplexNoise;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
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
                    .optionalFieldOf("jitter", 0.2)
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
        
        double noiseScale = 1.2 / Math.max(1, radius);

        int embed = Math.max(1, radius / 2);
        BlockPos mainCenter = surface.below(embed);

        List<Lobe> lobes = new ArrayList<>();
        lobes.add(new Lobe(mainCenter, radius, heightScale, 0.0, 0.0));

        boolean hasShoulder = radius >= 2 && rand.nextFloat() < 0.5f;
        if (hasShoulder) {
            double angle = rand.nextDouble() * Math.PI * 2;
            double dist  = radius * (0.25 + rand.nextDouble() * 0.2);
            int lobeDx = (int) Math.round(Math.cos(angle) * dist);
            int lobeDz = (int) Math.round(Math.sin(angle) * dist);
            int lobeDy = -(1 + rand.nextInt(Math.max(1, radius / 2 + 1)));
            double lobeRadius = radius * (0.45 + rand.nextDouble() * 0.25);
            double lobeHeightScale = heightScale * (0.75 + rand.nextDouble() * 0.3);
            lobes.add(new Lobe(
                    mainCenter.offset(lobeDx, lobeDy, lobeDz),
                    lobeRadius, lobeHeightScale,
                    37.0, 71.0));
        }

        int reach = radius + 3;

        if (hasObstruction(level, mainCenter, reach, lobes, noise, noiseScale, jitter, cfg)) return false;

        boolean placed = false;
        for (int dx = -reach; dx <= reach; dx++) {
            for (int dz = -reach; dz <= reach; dz++) {
                for (int dy = -reach; dy <= reach; dy++) {
                    BlockPos pos = mainCenter.offset(dx, dy, dz);
                    if (!insideAnyLobe(pos, lobes, noise, noiseScale, jitter)) continue;

                    BlockState existing = level.getBlockState(pos);

                    if (dy >= 0) {
                        
                        if (!existing.isAir() && !existing.canBeReplaced() && !isTarget(existing, cfg)) continue;
                    } else {
                        
                        if (!isTarget(existing, cfg)) continue;
                    }

                    level.setBlock(pos, cfg.block().getState(rand, pos), Block.UPDATE_CLIENTS);
                    placed = true;
                }
            }
        }

        return placed;
    }

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

    private static boolean hasObstruction(WorldGenLevel level, BlockPos mainCenter, int reach,
                                          List<Lobe> lobes, SimplexNoise noise, double noiseScale,
                                          double jitter, Config cfg) {
        for (int dx = -reach; dx <= reach; dx++) {
            for (int dz = -reach; dz <= reach; dz++) {
                for (int dy = -reach; dy <= reach; dy++) {
                    BlockPos pos = mainCenter.offset(dx, dy, dz);
                    if (!insideAnyLobe(pos, lobes, noise, noiseScale, jitter)) continue;

                    BlockState state = level.getBlockState(pos);

                    if (!state.getFluidState().isEmpty()) return true;
                    if (state.is(BlockTags.LOGS) || state.is(BlockTags.LEAVES)) return true;

                    if (!state.isAir() && !state.canBeReplaced()
                            && !isTarget(state, cfg)) {
                        return true;
                    }
                }
            }
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

    public record Config(
            BlockStateProvider block,
            List<BlockState>   targets,
            int                radius,
            double             heightScale,
            double             jitter
    ) implements FeatureConfiguration {}
}