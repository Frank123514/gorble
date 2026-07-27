package net.got.worldgen;

import com.mojang.logging.LogUtils;
import net.got.init.GotModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.slf4j.Logger;

/**
 * Recolors the ground under and around small puddles into
 * {@code got:quagmire} and {@code minecraft:mud}, purely via code —
 * no worldgen feature/JSON involved.
 *
 * <h3>How it works</h3>
 * <p>After vanilla {@code buildSurface} runs (and after
 * {@link SlopeSurfaceResolver}), {@link #apply} does two passes over
 * the chunk's 16x16 columns:
 * <ol>
 *   <li><b>Wet-column pass</b> — for each column, re-uses
 *       {@link GotChunkGenerator#computeSurfaceY} to find the ground
 *       floor, then checks the actual placed blocks a few steps above
 *       it for real water (tagged {@link FluidTags#WATER}). Direct
 *       block check, not a height-vs-sea-level guess, so it finds
 *       puddles at any elevation.</li>
 *   <li><b>Coverage pass</b> — samples a dedicated, domain-warped
 *       {@link SimplexNoise} field (own seeded instances, same
 *       low-freq-envelope + warped-high-freq-speckle technique as
 *       {@link net.got.worldgen.biome.placers.NoisyBlockPatchFeature}
 *       uses for mud splashes) — <b>not</b> the terrain-shape noise —
 *       then fades that value down by distance from the nearest wet
 *       column (only in the outer half of {@link #HALO_RADIUS}, so
 *       the threshold crossing happens gradually instead of at a
 *       hard circular edge). One threshold decides coverage, a
 *       second (higher) threshold on that same value decides mud vs
 *       quagmire.</li>
 * </ol>
 *
 * <p>Only natural ground blocks ({@link #isTargetBlock}) are ever
 * replaced. Water itself is never touched.
 */
public final class PuddleSurfaceResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    // ── Tuning ──────────────────────────────────────────────────────────────

    /** How many blocks above the floor to check for actual water. */
    private static final int WATER_CHECK_HEIGHT = 6;

    /** Radius (blocks) the muddy halo reaches out from a wet column. */
    private static final double HALO_RADIUS = 15.0;

    /** Radius (blocks) from a wet column where coverage is forced regardless
     *  of noise, so ground touching water is never left uncovered. */
    private static final double GUARANTEED_COVER_RADIUS = 2.5;

    /** Coverage cutoff on the noise value — lower = fatter halo. */
    private static final double COVERAGE_THRESHOLD = -0.20;
    /** Mud-vs-quagmire cutoff — compared against a high-freq-dominated
     *  split value (see mudSplit below), not the coverage value directly.
     *  Higher = more quagmire, less mud. */
    private static final double MUD_THRESHOLD = 0.15;

    // ── Dedicated coverage noise ─────────────────────────────────────────────
    // Low-freq envelope (overall blotch shape) + domain-warped high-freq
    // layer (speckled/ragged edges) — same technique NoisyBlockPatchFeature
    // uses for mud splashes — sampled directly in world space, independent
    // of the terrain-shape noise. This is what actually gives the coverage
    // field its speckled, organic look instead of one smooth gradient.
    private static final double SCALE_LOW  = 0.045; // overall blotch/cluster size
    private static final double SCALE_HIGH = 0.30;  // fine speckle grain size
    private static final double SCALE_WARP = 0.16;  // warp-offset frequency
    private static final double WARP_WEIGHT = 0.6;  // how much the speckle layer contributes
    private static final double WARP_AMP    = HALO_RADIUS * 0.6; // world-unit warp displacement

    private static volatile SimplexNoise noiseLow   = SimplexNoise.seeded(0L);
    private static volatile SimplexNoise noiseWarpX = SimplexNoise.seeded(1L);
    private static volatile SimplexNoise noiseWarpZ = SimplexNoise.seeded(2L);
    private static volatile SimplexNoise noiseHigh  = SimplexNoise.seeded(3L);

    public static void initSeed(long worldSeed) {
        noiseLow   = SimplexNoise.seeded(worldSeed ^ 0x51ED270B39ACL);
        noiseWarpX = SimplexNoise.seeded(worldSeed ^ 0x9E3779B97F4A7C15L);
        noiseWarpZ = SimplexNoise.seeded(worldSeed ^ 0xC0FFEE1AFADEL);
        noiseHigh  = SimplexNoise.seeded(worldSeed ^ 0xB16B00B5DEADBEEFL);
        LOGGER.debug("[GoT] PuddleSurfaceResolver noise (re)seeded for world seed {}", worldSeed);
    }

    private PuddleSurfaceResolver() {}

    private static volatile boolean loggedFirstCall = false;

    // ── Application ───────────────────────────────────────────────────────

    public static void apply(ChunkAccess chunk, WorldGenLevel region) {
        if (!loggedFirstCall) {
            loggedFirstCall = true;
            LOGGER.info("[GoT][DEBUG] PuddleSurfaceResolver.apply: FIRST CALL — chunk={}",
                    chunk.getPos());
        }

        int baseX = chunk.getPos().getMinBlockX();
        int baseZ = chunk.getPos().getMinBlockZ();
        int minY  = region.getMinY();

        int[] floorY  = new int[256];
        boolean[] wet = new boolean[256];

        // ── Pass 1: find real wet columns via actual placed water ──────────
        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int idx = lx * 16 + lz;
                int wx = baseX + lx;
                int wz = baseZ + lz;

                int surfaceY = GotChunkGenerator.computeSurfaceY(wx, wz);
                floorY[idx] = surfaceY;
                if (surfaceY <= minY) continue;

                for (int dy = 1; dy <= WATER_CHECK_HEIGHT; dy++) {
                    BlockPos checkPos = new BlockPos(lx, surfaceY + dy, lz);
                    BlockState state = chunk.getBlockState(checkPos);
                    if (state.getFluidState().is(FluidTags.WATER)) {
                        wet[idx] = true;
                        break;
                    }
                    if (state.isAir()) break;
                }
            }
        }

        // ── Pass 2: dedicated speckle-noise coverage, faded by wet-distance ─
        int placedThisChunk = 0;
        double searchRadius = HALO_RADIUS + 1.0;

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int idx = lx * 16 + lz;
                int surfaceY = floorY[idx];
                if (surfaceY <= minY) continue;

                double nearestWetDist = wet[idx] ? 0.0
                        : nearestWetDistance(wet, lx, lz, searchRadius);
                if (Double.isInfinite(nearestWetDist)) continue;

                int wx = baseX + lx;
                int wz = baseZ + lz;

                // Dedicated domain-warped coverage noise — NOT the terrain
                // shape noise. Low-freq envelope gives the overall blotch,
                // the warped high-freq layer breaks it up into the ragged,
                // speckled edges puddles/quagmire actually want.
                double low    = noiseLow.eval(wx * SCALE_LOW, wz * SCALE_LOW);
                double warpXv = noiseWarpX.eval(wx * SCALE_WARP, wz * SCALE_WARP);
                double warpZv = noiseWarpZ.eval(wx * SCALE_WARP + 31.7, wz * SCALE_WARP + 17.3);
                double high   = noiseHigh.eval(
                        (wx + warpXv * WARP_AMP) * SCALE_HIGH,
                        (wz + warpZv * WARP_AMP) * SCALE_HIGH);
                double value  = low + WARP_WEIGHT * high;

                // Radial falloff by distance from the nearest wet column —
                // only fades in the outer half of the halo, so the threshold
                // crossing happens organically instead of at a hard edge.
                double dist = nearestWetDist / (HALO_RADIUS + 1.0);
                double falloff = dist <= 0.5 ? 0.0 : (dist - 0.5) * 2.0;
                value -= falloff * falloff * 0.6;

                if (value < COVERAGE_THRESHOLD && nearestWetDist > GUARANTEED_COVER_RADIUS) continue;

                BlockPos localPos = new BlockPos(lx, surfaceY, lz);
                BlockState curr = chunk.getBlockState(localPos);
                if (curr.isAir() || curr.liquid()) continue;
                if (!isTargetBlock(curr)) continue;

                // Mud-vs-quagmire split gets its OWN value, weighted toward
                // the high-frequency layer. Reusing `value` directly (which
                // is low-freq-dominated) meant the mud threshold only ever
                // triggered near the smooth peaks of the low-freq envelope,
                // where there's barely any high-freq wiggle left to rough up
                // the edge — that's what read as "blocky" next to quagmire's
                // jagged boundary (which sits on the steep flank, where the
                // high-freq layer actually has room to matter).
                double mudSplit = high + 0.35 * low;

                BlockState replacement = mudSplit > MUD_THRESHOLD
                        ? Blocks.MUD.defaultBlockState()
                        : GotModBlocks.QUAGMIRE.get().defaultBlockState();

                if (!curr.equals(replacement)) {
                    chunk.setBlockState(localPos, replacement, false);
                    placedThisChunk++;
                }
            }
        }

        if (placedThisChunk > 0) {
            LOGGER.info("[GoT][DEBUG] PuddleSurfaceResolver: placed {} block(s) in chunk {}",
                    placedThisChunk, chunk.getPos());
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    /** Nearest wet-column distance within {@code maxRadius}, or +Infinity if none found. */
    private static double nearestWetDistance(boolean[] wet, int lx, int lz, double maxRadius) {
        int r = (int) Math.ceil(maxRadius);
        int minX = Math.max(0, lx - r);
        int maxX = Math.min(15, lx + r);
        int minZ = Math.max(0, lz - r);
        int maxZ = Math.min(15, lz + r);

        double bestSq = maxRadius * maxRadius;
        boolean found = false;
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                if (!wet[x * 16 + z]) continue;
                int dx = x - lx, dz = z - lz;
                double distSq = dx * dx + dz * dz;
                if (distSq <= bestSq) {
                    bestSq = distSq;
                    found = true;
                }
            }
        }
        return found ? Math.sqrt(bestSq) : Double.POSITIVE_INFINITY;
    }

    private static boolean isTargetBlock(BlockState state) {
        return state.is(Blocks.DIRT)
                || state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.COARSE_DIRT)
                || state.is(Blocks.PODZOL)
                || state.is(Blocks.SAND)
                || state.is(Blocks.GRAVEL)
                || state.is(Blocks.DIRT_PATH)
                || state.is(GotModBlocks.QUAGMIRE.get());
    }
}