package net.got.climate;

import net.got.GotMod;
import net.got.worldgen.biome.GotConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Dusts ground north of the frozen latitude line by placing our
 * {@code got:noisy_patch_snow} configured feature — the same speckled-drift
 * snow look used elsewhere — the snow counterpart to {@link LatitudeIceHandler}.
 *
 * <p>{@link ChunkEvent.Load} decides whether a chunk qualifies, gated off its
 * own separate latitude line ({@link LatitudeClimate#snowLatitudeStrength}) —
 * independent of the ice/temperature line {@link LatitudeIceHandler} uses —
 * combined with the same fixed-seed noise gate for a patchy edge, and
 * enqueues it. The actual placement happens later, on the main server tick,
 * and only once every chunk the feature's radius could reach is already
 * loaded — see {@link #onLevelTick} for why that split matters.
 *
 * <p><b>Layering:</b> the further north a chunk sits, the more times
 * {@link #onLevelTick} places the feature on top of itself for that same
 * chunk (see {@code passesFor}), each with a different random seed so the
 * overlapping patches don't land in identical shapes. {@code
 * got:noisy_block_patch} (the feature type backing {@code noisy_patch_snow})
 * now stacks its own layer count when a later pass lands on ground its own
 * earlier pass already snowed, instead of skipping it — so repeated
 * overlapping passes build up visibly deeper snow instead of just re-tracing
 * the same single-layer dusting. Right at the line a chunk still only gets
 * one pass (today's shallow, patchy dusting); deep in the fade zone it gets
 * several, so coverage and depth both grow the further north you go.
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class LatitudeSnowHandler {

    // Same fixed seed as LatitudeIceHandler, so the patchy edge of "where
    // snow starts" lines up with where the water actually starts icing over,
    // instead of using an unrelated, independently-rolled noise field.
    private static final PerlinSimplexNoise SNOW_NOISE =
            new PerlinSimplexNoise(RandomSource.create(5231241491057810726L), List.of(0));

    // How many queued chunks we'll actually attempt to place per tick. Keeps
    // a big backlog (e.g. right after a long-distance teleport) from turning
    // into a single-tick lag spike — it just drains over the next several
    // ticks instead, same as a chunk loading screen trickling in.
    private static final int MAX_PLACEMENTS_PER_TICK = 12;

    // Extra layering passes on top of the guaranteed first one, at full
    // (strength == 1) saturation. A chunk right at the line gets 1 pass
    // total; a chunk deep in the fade zone gets 1 + MAX_EXTRA_PASSES.
    private static final int MAX_EXTRA_PASSES = 4;

    // Chunks whose neighborhood never fully loads (world border, player
    // teleporting again before they finish, etc.) shouldn't sit in the
    // queue forever. This is generous — plenty of time for a big burst of
    // chunk loads to settle down — without being unbounded.
    private static final int MAX_AGE_TICKS = 20 * 30; // 30 seconds

    private record Pending(ChunkPos chunkPos, int ageTicks) {
        Pending aged() { return new Pending(chunkPos, ageTicks + 1); }
    }

    // ChunkEvent.Load can fire off the main server thread (it's part of the
    // chunk generation pipeline), so the enqueue side needs to be a
    // thread-safe queue even though it's only ever drained on the main
    // thread during onLevelTick.
    private static final Queue<Pending> PENDING = new ConcurrentLinkedQueue<>();

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (levelAccessor.isClientSide()) return;
        if (!(levelAccessor instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        ChunkPos chunkPos = event.getChunk().getPos();

        // Sample the gradient at the chunk centre to decide whether this
        // chunk gets a snow patch — noisy_patch_snow already scatters flecks
        // over a wide radius (14) on its own, so there's no need to loop all
        // 256 columns the way a hand-placed dusting would.
        int centerX = chunkPos.getMinBlockX() + 8;
        int centerZ = chunkPos.getMinBlockZ() + 8;

        float strength = LatitudeClimate.snowLatitudeStrength(centerX, centerZ);
        if (strength <= 0f) return;
        if (!isSnowyByLatitudeNoise(centerX, centerZ, strength)) return;

        // Don't touch the level here at all — just remember this chunk
        // qualified. Actual placement (and the neighbor-loaded check that
        // makes it safe) happens on the next server tick in onLevelTick.
        PENDING.add(new Pending(chunkPos, 0));
    }

    /**
     * Drains the queue built up by {@link #onChunkLoad}, placing snow for
     * chunks whose full neighborhood is loaded and re-queuing the rest.
     *
     * <p>{@code noisy_patch_snow} has radius 14, wide enough to reach into
     * the chunks around whichever one we're placing at. Reading/writing
     * blocks via the {@code ServerLevel} for a chunk that isn't loaded yet
     * forces it to be generated right then and there, synchronously, on
     * whatever thread asks for it. Doing that from inside
     * {@code ChunkEvent.Load} — which runs as part of the chunk generation
     * pipeline itself — meant a big teleport that fires off a whole
     * screenful of chunk loads at once could cascade into chunks
     * recursively generating each other on the same small worldgen thread
     * pool and stall or deadlock it entirely.
     *
     * <p>The fix isn't to skip chunks whose neighbors aren't loaded yet —
     * during a mass chunk-load burst almost none of them would be, since
     * they're all loading together, which just traded the freeze for a
     * blocky, load-order-dependent gap pattern instead of the intended
     * speckle. Instead, this retries: a chunk that isn't ready yet just
     * stays in the queue and gets checked again next tick, so as soon as
     * its neighbors settle in (safely, on their own, never forced by us)
     * it gets its patch — full coverage, no forced generation, no freeze.
     */
    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;
        if (PENDING.isEmpty()) return;

        Holder<ConfiguredFeature<?, ?>> feature = level.registryAccess()
                .lookupOrThrow(Registries.CONFIGURED_FEATURE)
                .get(GotConfiguredFeatures.NOISY_PATCH_SNOW)
                .orElse(null);
        if (feature == null) {
            GotMod.LOGGER.warn("[LatitudeSnowHandler] configured feature not found: {}",
                    GotConfiguredFeatures.NOISY_PATCH_SNOW.location());
            PENDING.clear();
            return;
        }

        // Drain into a local batch so we're not fighting the concurrent
        // producer side (onChunkLoad) while iterating.
        Queue<Pending> batch = new ArrayDeque<>();
        Pending next;
        while ((next = PENDING.poll()) != null) batch.add(next);

        int placedThisTick = 0;
        for (Pending pending : batch) {
            ChunkPos chunkPos = pending.chunkPos();

            if (!neighborhoodLoaded(level, chunkPos)) {
                if (pending.ageTicks() < MAX_AGE_TICKS) {
                    PENDING.add(pending.aged());
                }
                continue;
            }

            if (placedThisTick >= MAX_PLACEMENTS_PER_TICK) {
                // Neighborhood's ready but we're at budget for this tick —
                // re-queue at age 0 so it's simply tried again next tick
                // rather than burning through its age limit for no reason.
                PENDING.add(new Pending(chunkPos, 0));
                continue;
            }

            int centerX = chunkPos.getMinBlockX() + 8;
            int centerZ = chunkPos.getMinBlockZ() + 8;
            int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, centerX, centerZ);
            BlockPos origin = new BlockPos(centerX, surfaceY, centerZ);

            // More passes the further north this chunk sits, so overlapping
            // patches actually build up depth via the stacking behaviour in
            // NoisyBlockPatchFeature, instead of one single-layer dusting.
            float strength = LatitudeClimate.snowLatitudeStrength(centerX, centerZ);
            int passes = 1 + Math.round(strength * MAX_EXTRA_PASSES);

            for (int i = 0; i < passes; i++) {
                RandomSource featureRandom = RandomSource.create(
                        chunkPos.toLong() ^ 5231241491057810726L ^ ((long) i * 0x9E3779B97F4A7C15L));
                feature.value().place(level, level.getChunkSource().getGenerator(), featureRandom, origin);
            }
            placedThisTick++;
        }
    }

    /**
     * Deterministic snow-chunk gate — same noise-sampling shape as
     * {@code LatitudeIceHandler#isFrozenByLatitudeNoise}, so a given chunk's
     * snow-or-not fate is stable across reloads and reads as the same kind
     * of patchy gradient edge the ice uses. The {@code strength} it's fed,
     * though, now comes from the separate snow line
     * ({@link LatitudeClimate#snowLatitudeStrength}), not the ice line.
     */
    private static boolean isSnowyByLatitudeNoise(int worldX, int worldZ, float strength) {
        if (strength >= 1f) return true;

        double noise1 = SNOW_NOISE.getValue(worldX * 0.1, worldZ * 0.1, false);
        double noise2 = SNOW_NOISE.getValue(worldX * 0.03, worldZ * 0.03, false);
        double noiseAvg = (noise1 + noise2) / 2.0;
        double noiseNorm = (noiseAvg + 1.0) / 2.0; // -1..1 -> 0..1

        return noiseNorm < strength;
    }

    /**
     * True only if the origin chunk and all 8 chunks around it are already
     * loaded. The feature's radius (14 blocks) can reach at most one chunk
     * over in any direction from our chunk-center origin, so this 3×3
     * neighborhood always covers it with margin to spare — without ever
     * calling anything that would ask the chunk source to load/generate one
     * on our behalf.
     */
    private static boolean neighborhoodLoaded(ServerLevel level, ChunkPos originChunk) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (!level.hasChunk(originChunk.x + dx, originChunk.z + dz)) return false;
            }
        }
        return true;
    }

    private LatitudeSnowHandler() {}
}
