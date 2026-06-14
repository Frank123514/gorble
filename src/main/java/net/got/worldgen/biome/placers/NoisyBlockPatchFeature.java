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
 * Places an organically-shaped, ovular surface patch driven by domain-warped
 * simplex noise — mud splashes, gravel scatters, bare-earth patches, etc.
 *
 * <h2>Shape</h2>
 * Before noise is sampled, each candidate offset (dx, dz) is:
 * <ol>
 *   <li><b>Stretched</b> — divided by {@code stretch_x} / {@code stretch_z}
 *       to squash the noise space into an oval (e.g. stretchX=1.8, stretchZ=1.0
 *       makes the patch ~1.8× wider than it is tall).</li>
 *   <li><b>Rotated</b> — by a random angle chosen per-patch from the seed,
 *       so ovals don't all align with the world axes.</li>
 * </ol>
 * Two noise layers are then combined in noise space:
 * <pre>
 *   n = noise_low(sx, sz)
 *     + warp_weight * noise_high(sx + warp_x(sx,sz), sz + warp_z(sx,sz))
 * </pre>
 * A block is placed when {@code n > threshold}.
 *
 * <h2>JSON example</h2>
 * <pre>{@code
 * {
 *   "type": "got:noisy_block_patch",
 *   "config": {
 *     "block":      { "type": "minecraft:simple_state_provider", "state": { "Name": "minecraft:mud" } },
 *     "targets":    [ { "Name": "minecraft:dirt" }, { "Name": "minecraft:grass_block" } ],
 *     "radius":     8,
 *     "stretch_x":  1.8,
 *     "stretch_z":  1.0,
 *     "threshold":  0.10,
 *     "warp_weight":0.55,
 *     "scale_low":  0.18,
 *     "scale_high": 0.42,
 *     "scale_warp": 0.25
 *   }
 * }
 * }</pre>
 *
 * <h2>Preset guidance (radius 6–10)</h2>
 * <ul>
 *   <li><b>Mud splash</b>:     stretchX=1.8, stretchZ=1.0, threshold=0.10, warp=0.55, low=0.18, high=0.42, warpScale=0.25</li>
 *   <li><b>Gravel scatter</b>: stretchX=2.0, stretchZ=1.0, threshold=0.20, warp=0.70, low=0.22, high=0.55, warpScale=0.30</li>
 *   <li><b>Sandy clearing</b>: stretchX=1.6, stretchZ=1.0, threshold=0.05, warp=0.40, low=0.14, high=0.35, warpScale=0.20</li>
 *   <li><b>Dirt patch</b>:     stretchX=1.7, stretchZ=1.0, threshold=0.12, warp=0.60, low=0.20, high=0.45, warpScale=0.28</li>
 * </ul>
 */
public class NoisyBlockPatchFeature extends Feature<NoisyBlockPatchFeature.Config> {

    // ── Codec ─────────────────────────────────────────────────────────────────

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
                    .forGetter(Config::scaleWarp)
    ).apply(inst, Config::new));

    public NoisyBlockPatchFeature(Codec<Config> codec) {
        super(codec);
    }

    // ── Feature placement ─────────────────────────────────────────────────────

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

        // Random rotation angle per patch — ovals won't all face the same way
        double angle = (seed & 0xFFFFL) / 65536.0 * Math.PI; // 0..π (half turn is enough for symmetry)
        double cos   = Math.cos(angle);
        double sin   = Math.sin(angle);

        int r = cfg.radius();
        boolean placed = false;

        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {

                // Quick bounding-circle cull before the more expensive noise calls
                if (dx * dx + dz * dz > (r + 1) * (r + 1)) continue;

                // ── 1. Rotate the offset so the oval isn't axis-aligned ──────
                double rx =  cos * dx + sin * dz;
                double rz = -sin * dx + cos * dz;

                // ── 2. Stretch into noise space (creates the oval) ───────────
                double sx = rx / cfg.stretchX();
                double sz = rz / cfg.stretchZ();

                // Absolute world position for the low-freq envelope
                // (keeps patches from looking the same in adjacent chunks)
                double wx = sx + origin.getX();
                double wz = sz + origin.getZ();

                // ── 3. Low-frequency envelope — overall blob shape ───────────
                double low = noise1.eval(wx * cfg.scaleLow(), wz * cfg.scaleLow());

                // ── 4. Domain-warp the high-freq layer for ragged edges ───────
                double warpX = noiseWarpX.eval(wx * cfg.scaleWarp(), wz * cfg.scaleWarp());
                double warpZ = noiseWarpZ.eval(wx * cfg.scaleWarp() + 31.7, wz * cfg.scaleWarp() + 17.3);
                double high  = noise2.eval(
                        (wx + warpX * r * 0.5) * cfg.scaleHigh(),
                        (wz + warpZ * r * 0.5) * cfg.scaleHigh());

                double value = low + cfg.warpWeight() * high;
                if (value < cfg.threshold()) continue;

                // ── Find the surface block at this XZ ────────────────────────
                BlockPos surface = origin.offset(dx, 0, dz);
                surface = findSurface(level, surface, 4);
                if (surface == null) continue;

                // ── Target check ─────────────────────────────────────────────
                if (!isTarget(level.getBlockState(surface), cfg)) continue;

                // ── Air-above check ───────────────────────────────────────────
                if (!level.getBlockState(surface.above()).isAir()) continue;

                // ── Place ─────────────────────────────────────────────────────
                level.setBlock(surface, cfg.block().getState(rand, surface), Block.UPDATE_CLIENTS);
                placed = true;
            }
        }

        return placed;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static BlockPos findSurface(WorldGenLevel level, BlockPos pos, int yRange) {
        for (int dy = yRange; dy >= -yRange; dy--) {
            BlockPos candidate = pos.above(dy);
            if (!level.getBlockState(candidate).isAir() &&
                 level.getBlockState(candidate.above()).isAir()) {
                return candidate;
            }
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
     * @param block      Block to place.
     * @param targets    Surface blocks that may be replaced.
     * @param radius     Search radius in blocks (default 7).
     * @param stretchX   How much wider the oval is on its local X axis (default 1.8).
     *                   E.g. 2.0 = twice as long as it is wide.
     * @param stretchZ   Local Z axis scale — keep at 1.0 and only vary stretchX
     *                   to control the aspect ratio simply.
     * @param threshold  Noise cutoff — lower = fatter patch (default 0.10).
     * @param warpWeight Blend weight of domain-warped layer — higher = more jagged (default 0.55).
     * @param scaleLow   Low-freq noise frequency (default 0.18).
     * @param scaleHigh  High-freq noise frequency (default 0.42).
     * @param scaleWarp  Warp-offset noise frequency (default 0.25).
     */
    public record Config(
            BlockStateProvider block,
            List<BlockState>   targets,
            int                radius,
            double             stretchX,
            double             stretchZ,
            double             threshold,
            double             warpWeight,
            double             scaleLow,
            double             scaleHigh,
            double             scaleWarp
    ) implements FeatureConfiguration {}
}
