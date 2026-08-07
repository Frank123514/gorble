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

/**
 * Places a single rounded, partially-embedded boulder mound — an irregular
 * lumpy rock pile instead of vanilla's {@code minecraft:forest_rock}, which
 * just drops one block on the surface.
 *
 * <h2>Shape</h2>
 * A main vertically-squashed sphere ({@code height_scale} controls the
 * squash) centered a bit below the found surface, so it pokes up as a mound
 * with a naturally grounded, buried base instead of floating. At most one
 * smaller "shoulder" lobe is fused tightly onto the side, offset lower than
 * the main mass, so the boulder reads as a single compact mound with a
 * slightly stepped silhouette rather than a scattered cluster of separate
 * rocks. Per-column simplex noise jitters each lobe's effective radius (with
 * its own noise offset) so the outline is lumpy/rocky rather than perfectly
 * smooth, without reading as "scattered."
 *
 * <h2>Placement safety</h2>
 * Before placing anything, the boulder's own shape (every lobe, with jitter)
 * is scanned up front using the same inside-the-shape test the actual
 * placement loop uses — not a full bounding box around it. If that scan
 * finds water/lava, any log or leaf block (i.e. a tree), or any other solid
 * block that isn't one of {@code targets} and isn't naturally replaceable
 * (grass, flowers, snow layers, etc.) anywhere the boulder's shape actually
 * reaches — which in practice means a structure or other foreign object —
 * placement is aborted entirely with no partial boulder left behind.
 * Structures generate before decoration features in vanilla's chunk
 * pipeline, so by the time this feature runs any structure blocks are
 * already physically in the world and get caught by this same "foreign
 * block" check.
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
        // A couple of lumps across the whole boulder, not fine grain.
        double noiseScale = 1.2 / Math.max(1, radius);

        // Main center sits partway into the ground so the mass pokes up
        // above the surface with a naturally grounded, partially-buried
        // base instead of floating on top of it.
        int embed = Math.max(1, radius / 2);
        BlockPos mainCenter = surface.below(embed);

        List<Lobe> lobes = new ArrayList<>();
        lobes.add(new Lobe(mainCenter, radius, heightScale, 0.0, 0.0));

        // At most one small "shoulder" lobe, fused tightly against the main
        // mass, so the boulder reads as one compact mound instead of a
        // scattered pile of separate rocks. Kept close and modest in size —
        // this is a nudge to the silhouette, not a second rock.
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

        // Margin around the main center that comfortably covers every lobe
        // plus jitter headroom. Kept tighter than before since lobes now sit
        // much closer to the main mass.
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
                        // Above-ground portion: only build out into open air,
                        // ground poking through (e.g. surface grass), or
                        // naturally replaceable plants (flowers, tall grass) —
                        // never through unrelated solid terrain/structures.
                        if (!existing.isAir() && !existing.canBeReplaced() && !isTarget(existing, cfg)) continue;
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

    /**
     * Scans only the cells the boulder's actual shape will touch (any cell
     * inside any lobe — same test {@link #insideAnyLobe} uses for real
     * placement below), not the whole bounding cube around it. Returns true
     * (abort placement) if any of those cells is: a fluid (water/lava), a
     * log or leaf block (a tree), or any other solid block that isn't a
     * configured target and isn't naturally replaceable — which in practice
     * covers structures and any other foreign object sitting where the
     * boulder would actually go.
     *
     * <p><b>Bug history:</b> this used to scan the whole {@code reach}
     * bounding cube unconditionally (13×13×13 = 2197 cells for the default
     * {@code radius: 3} config), rejecting placement for a foreign block
     * anywhere in that cube — including its corners, up to ~10 blocks away
     * diagonally, well outside where a ~3-4 block-radius lumpy sphere could
     * ever actually reach. In practice that meant placement failed
     * constantly near anything (a path, a fence, a single non-target block)
     * within {@code reach} blocks even though the boulder itself would
     * never have touched it. Gating each cell on {@code insideAnyLobe} —
     * the exact same test the placement loop below already uses — fixes
     * that: only cells the boulder shape genuinely occupies can block
     * placement.
     */
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
     *                     (default 0.2).
     */
    public record Config(
            BlockStateProvider block,
            List<BlockState>   targets,
            int                radius,
            double             heightScale,
            double             jitter
    ) implements FeatureConfiguration {}
}