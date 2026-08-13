package net.got.worldgen.biome.placers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.worldgen.SimplexNoise;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import net.minecraft.util.StringRepresentable;

import java.util.List;

public class NoisyBlockPatchFeature extends Feature<NoisyBlockPatchFeature.Config> {

    public enum Mode implements StringRepresentable {
        BLOB("blob"),
        SPECKLE("speckle");

        public static final Codec<Mode> CODEC = StringRepresentable.fromEnum(Mode::values);

        private final String id;
        Mode(String id) { this.id = id; }
        @Override public String getSerializedName() { return id; }
    }

    public static final Codec<Config> CONFIG_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BlockStateProvider.CODEC
                    .fieldOf("block")
                    .forGetter(Config::block),
            BlockState.CODEC.listOf()
                    .fieldOf("targets")
                    .forGetter(Config::targets),
            Codec.intRange(1, 32)
                    .optionalFieldOf("radius", 7)
                    .forGetter(Config::radius),
            Mode.CODEC
                    .optionalFieldOf("mode", Mode.BLOB)
                    .forGetter(Config::mode),
            Codec.doubleRange(0.1, 8.0)
                    .optionalFieldOf("stretch_x", 1.8)
                    .forGetter(Config::stretchX),
            Codec.doubleRange(0.1, 8.0)
                    .optionalFieldOf("stretch_z", 1.0)
                    .forGetter(Config::stretchZ),
            Codec.doubleRange(-1.0, 1.0)
                    .optionalFieldOf("threshold", 0.10)
                    .forGetter(Config::threshold),
            Codec.doubleRange(0.0, 2.0)
                    .optionalFieldOf("warp_weight", 0.55)
                    .forGetter(Config::warpWeight),
            Codec.doubleRange(0.01, 2.0)
                    .optionalFieldOf("scale_low", 0.18)
                    .forGetter(Config::scaleLow),
            Codec.doubleRange(0.01, 2.0)
                    .optionalFieldOf("scale_high", 0.42)
                    .forGetter(Config::scaleHigh),
            Codec.doubleRange(0.01, 2.0)
                    .optionalFieldOf("scale_warp", 0.25)
                    .forGetter(Config::scaleWarp),
            Codec.BOOL
                    .optionalFieldOf("place_above", false)
                    .forGetter(Config::placeAbove)
    ).apply(inst, Config::new));

    public NoisyBlockPatchFeature(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> ctx) {
        WorldGenLevel level  = ctx.level();
        BlockPos      origin = ctx.origin();
        RandomSource  rand   = ctx.random();
        Config        cfg    = ctx.config();

        long seed = rand.nextLong();
        SimplexNoise noise1     = SimplexNoise.seeded(seed);
        SimplexNoise noiseWarpX = SimplexNoise.seeded(seed + 1_000_003L);
        SimplexNoise noiseWarpZ = SimplexNoise.seeded(seed + 2_000_003L);
        SimplexNoise noise2     = SimplexNoise.seeded(seed + 3_000_003L);

        boolean speckle = cfg.mode() == Mode.SPECKLE;

        double angle = speckle ? 0.0 : (seed & 0xFFFFL) / 65536.0 * Math.PI;
        double cos   = Math.cos(angle);
        double sin   = Math.sin(angle);

        int r = cfg.radius();
        boolean placed = false;

        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {

                double distSq = dx * dx + dz * dz;
                double maxDistSq = (double) (r + 1) * (r + 1);
                if (distSq > maxDistSq) continue;

                double wx, wz;
                if (speckle) {
                    
                    wx = dx + origin.getX();
                    wz = dz + origin.getZ();
                } else {
                    
                    double rx =  cos * dx + sin * dz;
                    double rz = -sin * dx + cos * dz;

                    double sx = rx / cfg.stretchX();
                    double sz = rz / cfg.stretchZ();

                    wx = sx + origin.getX();
                    wz = sz + origin.getZ();
                }

                double low = noise1.eval(wx * cfg.scaleLow(), wz * cfg.scaleLow());

                double warpX = noiseWarpX.eval(wx * cfg.scaleWarp(), wz * cfg.scaleWarp());
                double warpZ = noiseWarpZ.eval(wx * cfg.scaleWarp() + 31.7, wz * cfg.scaleWarp() + 17.3);
                double high  = noise2.eval(
                        (wx + warpX * r * 0.5 * cfg.warpWeight()) * cfg.scaleHigh(),
                        (wz + warpZ * r * 0.5 * cfg.warpWeight()) * cfg.scaleHigh());

                double value = speckle
                        
                        ? low * 0.5 + high * (1.0 - cfg.warpWeight() * 0.3)
                        : low + cfg.warpWeight() * high;

                double dist = Math.sqrt(distSq) / (r + 1);
                
                double falloff = dist <= 0.5 ? 0.0 : (dist - 0.5) * 2.0;
                value -= falloff * falloff * 0.6;

                if (value < cfg.threshold()) continue;

                BlockPos surface = origin.offset(dx, 0, dz);
                surface = findSurface(level, surface, 4);
                if (surface == null) continue;

                BlockState surfaceState = level.getBlockState(surface);
                BlockState prospective = cfg.block().getState(rand, surface);

                if (!isTarget(surfaceState, cfg)) continue;

                BlockPos placePos;
                if (cfg.placeAbove()) {
                    
                    if (!level.getBlockState(surface.above()).isAir()) continue;
                    placePos = surface.above();
                } else {
                    
                    placePos = surface;
                }
                level.setBlock(placePos, prospective, Block.UPDATE_CLIENTS);
                placed = true;

                if (cfg.placeAbove()
                        && surfaceState.getBlock() == Blocks.GRASS_BLOCK
                        && !surfaceState.getValue(BlockStateProperties.SNOWY)) {
                    level.setBlock(surface, surfaceState.setValue(BlockStateProperties.SNOWY, true),
                            Block.UPDATE_CLIENTS);
                }
            }
        }

        return placed;
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
            Mode               mode,
            double             stretchX,
            double             stretchZ,
            double             threshold,
            double             warpWeight,
            double             scaleLow,
            double             scaleHigh,
            double             scaleWarp,
            boolean            placeAbove
    ) implements FeatureConfiguration {}
}