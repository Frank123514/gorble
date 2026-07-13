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
 *
 * <h2>Speckle mode</h2>
 * Setting {@code "mode": "speckle"} switches from one big oval blob to a dense
 * scatter of many small, independently-sized flecks across the whole radius —
 * the "snow dusting the ground in irregular drifts" look, rather than a single
 * puddle-shaped patch. In this mode:
 * <ul>
 *   <li>{@code stretch_x}/{@code stretch_z} and the per-patch rotation are ignored
 *       (each fleck doesn't need its own oval — the graininess comes from
 *       high-frequency noise instead).</li>
 *   <li>{@code scale_low} controls the size of the loose drifts/clusters flecks
 *       gather into (lower = bigger clusters).</li>
 *   <li>{@code scale_high} controls the size of individual flecks (higher = finer
 *       grain/dust; lower = chunkier speckles).</li>
 *   <li>{@code threshold} controls overall coverage density (lower = more covered
 *       in speckle, similar to blob mode).</li>
 *   <li>{@code warp_weight}/{@code scale_warp} add extra raggedness to fleck edges;
 *       set warp_weight low (or 0) for cleaner, rounder speckles.</li>
 * </ul>
 * <b>Speckle preset (radius 10–16, dense snow dusting)</b>:
 * mode=speckle, threshold=0.02, warp_weight=0.15, scale_low=0.05, scale_high=1.1, scale_warp=0.6
 */
public class NoisyBlockPatchFeature extends Feature<NoisyBlockPatchFeature.Config> {

    // ── Mode ──────────────────────────────────────────────────────────────────

    /**
     * {@link #BLOB} — the original single stretched/rotated organic oval.
     * {@link #SPECKLE} — a dense scatter of many small independent flecks
     * (no rotation/stretch), for a fine "dusted" look like snow speckled
     * unevenly across the ground.
     */
    public enum Mode implements StringRepresentable {
        BLOB("blob"),
        SPECKLE("speckle");

        public static final Codec<Mode> CODEC = StringRepresentable.fromEnum(Mode::values);

        private final String id;
        Mode(String id) { this.id = id; }
        @Override public String getSerializedName() { return id; }
    }

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

        boolean speckle = cfg.mode() == Mode.SPECKLE;

        // Random rotation angle per patch — ovals won't all face the same way.
        // Skipped in speckle mode: flecks are small and unstretched, so a
        // per-patch rotation has no visible effect there.
        double angle = speckle ? 0.0 : (seed & 0xFFFFL) / 65536.0 * Math.PI; // 0..π
        double cos   = Math.cos(angle);
        double sin   = Math.sin(angle);

        int r = cfg.radius();
        boolean placed = false;

        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {

                // Quick bounding-circle cull (generous — just avoids wasting
                // noise calls far outside the radius; the real edge shaping
                // happens via the falloff below, not this cutoff).
                double distSq = dx * dx + dz * dz;
                double maxDistSq = (double) (r + 1) * (r + 1);
                if (distSq > maxDistSq) continue;

                double wx, wz;
                if (speckle) {
                    // No rotation/stretch — flecks scatter freely in world
                    // space instead of all sharing one oval envelope.
                    wx = dx + origin.getX();
                    wz = dz + origin.getZ();
                } else {
                    // ── 1. Rotate the offset so the oval isn't axis-aligned ──
                    double rx =  cos * dx + sin * dz;
                    double rz = -sin * dx + cos * dz;

                    // ── 2. Stretch into noise space (creates the oval) ───────
                    double sx = rx / cfg.stretchX();
                    double sz = rz / cfg.stretchZ();

                    // Absolute world position for the low-freq envelope
                    // (keeps patches from looking the same in adjacent chunks)
                    wx = sx + origin.getX();
                    wz = sz + origin.getZ();
                }

                // ── 3. Low-frequency envelope ─────────────────────────────────
                // Blob mode: overall oval shape. Speckle mode: loose drift/
                // cluster grouping that flecks scatter within.
                double low = noise1.eval(wx * cfg.scaleLow(), wz * cfg.scaleLow());

                // ── 4. High-freq layer, optionally domain-warped ─────────────
                // Blob mode: ragged edges on the one big oval.
                // Speckle mode: this IS the grain — each little peak/trough of
                // this high-frequency layer becomes one fleck, so scale_high
                // directly controls fleck size (higher = finer dust).
                double warpX = noiseWarpX.eval(wx * cfg.scaleWarp(), wz * cfg.scaleWarp());
                double warpZ = noiseWarpZ.eval(wx * cfg.scaleWarp() + 31.7, wz * cfg.scaleWarp() + 17.3);
                double high  = noise2.eval(
                        (wx + warpX * r * 0.5 * cfg.warpWeight()) * cfg.scaleHigh(),
                        (wz + warpZ * r * 0.5 * cfg.warpWeight()) * cfg.scaleHigh());

                double value = speckle
                        // Speckle: blend the cluster envelope with fine grain so
                        // flecks are denser in some drifts, sparser in others,
                        // instead of scattered perfectly uniformly.
                        ? low * 0.5 + high * (1.0 - cfg.warpWeight() * 0.3)
                        : low + cfg.warpWeight() * high;

                // ── Radial falloff ───────────────────────────────────────────
                // Push value down as we approach the patch radius so the
                // threshold crossing happens organically inside the circle,
                // via noise, rather than at a hard circular boundary. Without
                // this, low thresholds (e.g. 0.02 for dense speckle) rarely
                // dip below cutoff before hitting the bounding-circle cull,
                // so the circle itself becomes the visible edge.
                double dist = Math.sqrt(distSq) / (r + 1);
                // Only start fading in the outer half of the radius, so the
                // core of the patch is unaffected and full-strength.
                double falloff = dist <= 0.5 ? 0.0 : (dist - 0.5) * 2.0;
                value -= falloff * falloff * 0.6;

                if (value < cfg.threshold()) continue;

                // ── Find the surface block at this XZ ────────────────────────
                BlockPos surface = origin.offset(dx, 0, dz);
                surface = findSurface(level, surface, 4);
                if (surface == null) continue;

                BlockState surfaceState = level.getBlockState(surface);
                BlockState prospective = cfg.block().getState(rand, surface);

                // ── Target check ─────────────────────────────────────────────
                if (!isTarget(surfaceState, cfg)) continue;

                // ── Place ─────────────────────────────────────────────────────
                BlockPos placePos;
                if (cfg.placeAbove()) {
                    // Layer-on-top mode (e.g. snow): only valid if the space
                    // above the surface is empty.
                    if (!level.getBlockState(surface.above()).isAir()) continue;
                    placePos = surface.above();
                } else {
                    // Replace-in-place mode (e.g. stone/dirt showing through
                    // grass): swap the surface block itself.
                    placePos = surface;
                }
                level.setBlock(placePos, prospective, Block.UPDATE_CLIENTS);
                placed = true;

                // Vanilla grass blocks track a SNOWY flag that swaps their
                // side texture whenever snow sits on top of them, but placing
                // a block straight into the world like this doesn't flip it
                // automatically. Do it manually here so a grass block newly
                // buried by this feature reads as snowy immediately instead
                // of only after some other trigger updates its state.
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

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static BlockPos findSurface(WorldGenLevel level, BlockPos pos, int yRange) {
        // Scan strictly top-down from above the expected surface and take the
        // FIRST solid block found with air above it. Scanning top-down (rather
        // than bottom-up) prevents latching onto a block placed earlier in this
        // same feature pass (e.g. an adjacent overlapping patch), which would
        // otherwise cause chunks to stack upward into floating stair-steps.
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
     * @param block      Block to place.
     * @param targets    Surface blocks that may be replaced.
     * @param radius     Search radius in blocks (default 7).
     * @param mode       {@link Mode#BLOB} (default) for one big organic oval, or
     *                   {@link Mode#SPECKLE} for a dense scatter of small flecks.
     * @param stretchX   How much wider the oval is on its local X axis (default 1.8).
     *                   E.g. 2.0 = twice as long as it is wide. Ignored in speckle mode.
     * @param stretchZ   Local Z axis scale — keep at 1.0 and only vary stretchX
     *                   to control the aspect ratio simply. Ignored in speckle mode.
     * @param threshold  Noise cutoff — lower = fatter patch / denser speckle (default 0.10).
     * @param warpWeight Blend weight of domain-warped layer — higher = more jagged edges
     *                   in blob mode, or patchier drifts in speckle mode (default 0.55).
     * @param scaleLow   Low-freq noise frequency — overall blob shape, or speckle
     *                   drift/cluster size (default 0.18).
     * @param scaleHigh  High-freq noise frequency — blob edge raggedness, or
     *                   individual fleck size in speckle mode (default 0.42;
     *                   try ~1.0+ for fine dust in speckle mode).
     * @param scaleWarp  Warp-offset noise frequency (default 0.25).
     */
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