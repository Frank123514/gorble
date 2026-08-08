package net.got.climate;

import net.got.GotMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;

/**
 * Directly freezes exposed surface water north of the frozen latitude line.
 *
 * This is intentionally independent of vanilla's {@code Biome#shouldFreeze}
 * (see {@code BiomeMixin}), which only ever turns water into ice when it's
 * adjacent to already-cold/snowy land — meaning ice can only spread in from
 * an existing frozen shore. North of the latitude line we don't want that
 * shore-dependency: open water far from any shore should still ice over.
 *
 * <p>This runs once, on {@link ChunkEvent.Load}, and freezes every eligible
 * surface water column in the chunk right then — before the chunk is ever
 * sent to a client. Whether a given column freezes is decided by sampling
 * static Perlin noise at that column's (x, z) and comparing it against the
 * latitude gradient — the same trick LOTR Renewed's {@code SeaBiome#isSeaFrozen}
 * uses. Because Perlin noise at a fixed coordinate always returns the same
 * value, this is NOT a coin flip re-rolled on every check: a given block's
 * fate is fixed by its position, so there's nothing to "catch up" over time
 * or flicker between checks. Near the line only the noise peaks clear the
 * low threshold (patchy edge); deep in the fade zone almost every peak
 * clears it (solid ice) — that's the gradient, baked into the terrain
 * rather than rolled per-tick.
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class LatitudeIceHandler {

    // Fixed seed so the noise field (and therefore which columns freeze) is
    // stable across restarts/chunk reloads — same rationale as LOTR's
    // iceNoiseGen using a hardcoded seed rather than the world seed.
    private static final PerlinSimplexNoise ICE_NOISE = new PerlinSimplexNoise(RandomSource.create(5231241491057810726L), java.util.List.of(0));

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (levelAccessor.isClientSide()) return;
        if (!(levelAccessor instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        ChunkAccess chunk = event.getChunk();
        ChunkPos chunkPos = chunk.getPos();
        int minX = chunkPos.getMinBlockX();
        int minZ = chunkPos.getMinBlockZ();

        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                int worldX = minX + dx;
                int worldZ = minZ + dz;

                float strength = LatitudeClimate.latitudeStrength(worldX, worldZ);
                if (strength <= 0f) continue;

                int heightmapY = chunk.getHeight(Heightmap.Types.MOTION_BLOCKING, worldX, worldZ);
                BlockPos heightmapPos = new BlockPos(worldX, heightmapY, worldZ);

                // MOTION_BLOCKING stops at the first non-air block, and water
                // counts as motion-blocking — so the heightmap position itself
                // can BE the top water block, not the block below it. Check
                // both: if the heightmap block is water, that's our surface;
                // otherwise fall back to one below (heightmap sitting on land
                // or another solid block at the water's edge).
                BlockState atHeightmap = chunk.getBlockState(heightmapPos);
                BlockPos surfaceWater = (atHeightmap.getBlock() == Blocks.WATER)
                        ? heightmapPos
                        : heightmapPos.below();

                BlockState waterState = chunk.getBlockState(surfaceWater);
                if (waterState.getBlock() != Blocks.WATER) continue;
                if (!waterState.getFluidState().isSource()) continue;

                if (!isFrozenByLatitudeNoise(worldX, worldZ, strength)) continue;

                chunk.setBlockState(surfaceWater, Blocks.ICE.defaultBlockState(), 3);
            }
        }
    }

    /**
     * Deterministic freeze test: {@code strength} is the 0..1 latitude
     * gradient from {@link LatitudeClimate#latitudeStrength}. At 0 nothing
     * freezes, at 1 everything freezes; in between, static noise sampled at
     * this exact (x, z) decides — so the same column always gives the same
     * answer, and the gradient reads as a naturally patchy ice edge instead
     * of a hard line or a shifting dice roll.
     */
    private static boolean isFrozenByLatitudeNoise(int worldX, int worldZ, float strength) {
        if (strength >= 1f) return true;

        double noise1 = ICE_NOISE.getValue(worldX * 0.1, worldZ * 0.1, false);
        double noise2 = ICE_NOISE.getValue(worldX * 0.03, worldZ * 0.03, false);
        double noiseAvg = (noise1 + noise2) / 2.0;
        double noiseNorm = (noiseAvg + 1.0) / 2.0; // -1..1 -> 0..1

        return noiseNorm < strength;
    }

    private LatitudeIceHandler() {}
}