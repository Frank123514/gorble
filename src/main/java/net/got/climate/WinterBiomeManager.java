package net.got.climate;

import net.got.GotMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

public final class WinterBiomeManager {

    private static volatile boolean isWinterApplied = false;

    public static boolean isWinterApplied() {
        return isWinterApplied;
    }

    public static void applyWinter(ServerLevel overworld) {
        if (isWinterApplied) {
            GotMod.LOGGER.warn("[WinterBiomeManager] applyWinter called but already applied — skipping.");
            return;
        }
        isWinterApplied = true;
        processRenderedChunks(overworld, true);
        GotMod.LOGGER.info("[WinterBiomeManager] Winter applied.");
    }

    public static void revertWinter(ServerLevel overworld) {
        if (!isWinterApplied) {
            GotMod.LOGGER.warn("[WinterBiomeManager] revertWinter called but not applied — skipping.");
            return;
        }
        isWinterApplied = false;
        processRenderedChunks(overworld, false);
        GotMod.LOGGER.info("[WinterBiomeManager] Winter reverted.");
    }

    public static void restoreIfWinter(ServerLevel overworld) {
        if (SeasonManager.getCurrentSeason().isWinter() && !isWinterApplied) {
            GotMod.LOGGER.info("[WinterBiomeManager] Server restarted mid-winter — re-applying winter.");
            applyWinter(overworld);
        }
    }

    /**
     * Processes all chunks within each player's render distance.
     * freeze=true: place snow and freeze water (winter onset).
     * freeze=false: melt snow and thaw ice (spring/summer/autumn onset).
     */
    private static void processRenderedChunks(ServerLevel overworld, boolean freeze) {
        for (ServerPlayer player : overworld.players()) {
            int rd = player.requestedViewDistance();
            ChunkPos center = new ChunkPos(player.blockPosition());
            for (int dx = -rd; dx <= rd; dx++) {
                for (int dz = -rd; dz <= rd; dz++) {
                    LevelChunk chunk = overworld.getChunkSource().getChunkNow(
                            center.x + dx, center.z + dz);
                    if (chunk == null) continue;
                    if (freeze) freezeChunk(overworld, chunk);
                    else thawChunk(overworld, chunk);
                    player.connection.send(new ClientboundLevelChunkWithLightPacket(
                            chunk, overworld.getLightEngine(), null, null));
                }
            }
        }
    }

    private static void freezeChunk(ServerLevel level, LevelChunk chunk) {
        ChunkPos cp = chunk.getPos();
        for (int x = cp.getMinBlockX(); x <= cp.getMaxBlockX(); x++) {
            for (int z = cp.getMinBlockZ(); z <= cp.getMaxBlockZ(); z++) {
                BlockPos surface = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos(x, 0, z));
                Biome biome = level.getBiome(surface).value();
                if (!biome.hasPrecipitation()) continue;

                // Freeze water
                BlockPos waterPos = surface.below();
                if (level.getBlockState(waterPos).is(Blocks.WATER) &&
                        biome.shouldFreeze(level, waterPos, false)) {
                    level.setBlockAndUpdate(waterPos, Blocks.ICE.defaultBlockState());
                }

                // Place snow
                if (level.isEmptyBlock(surface) &&
                        Blocks.SNOW.defaultBlockState().canSurvive(level, surface) &&
                        level.getBrightness(LightLayer.BLOCK, surface) < 10) {
                    level.setBlockAndUpdate(surface,
                            Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, 1));
                }
            }
        }
    }

    private static void thawChunk(ServerLevel level, LevelChunk chunk) {
        ChunkPos cp = chunk.getPos();
        for (int x = cp.getMinBlockX(); x <= cp.getMaxBlockX(); x++) {
            for (int z = cp.getMinBlockZ(); z <= cp.getMaxBlockZ(); z++) {
                BlockPos surface = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos(x, 0, z));

                // Remove snow layer
                BlockState state = level.getBlockState(surface);
                if (state.is(Blocks.SNOW)) {
                    level.setBlockAndUpdate(surface, Blocks.AIR.defaultBlockState());
                }

                // Thaw ice back to water
                BlockPos below = surface.below();
                BlockState belowState = level.getBlockState(below);
                if (belowState.is(Blocks.ICE)) {
                    level.setBlockAndUpdate(below, Blocks.WATER.defaultBlockState());
                }
            }
        }
    }

    private WinterBiomeManager() {}
}