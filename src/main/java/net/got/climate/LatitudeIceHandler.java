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

@EventBusSubscriber(modid = GotMod.MODID)
public final class LatitudeIceHandler {

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

    private static boolean isFrozenByLatitudeNoise(int worldX, int worldZ, float strength) {
        if (strength >= 1f) return true;

        double noise1 = ICE_NOISE.getValue(worldX * 0.1, worldZ * 0.1, false);
        double noise2 = ICE_NOISE.getValue(worldX * 0.03, worldZ * 0.03, false);
        double noiseAvg = (noise1 + noise2) / 2.0;
        double noiseNorm = (noiseAvg + 1.0) / 2.0;

        return noiseNorm < strength;
    }

    private LatitudeIceHandler() {}
}