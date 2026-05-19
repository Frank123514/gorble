package net.got.climate;

import net.got.GotMod;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 * Tracks whether winter climate mutations are active and manages weather.
 *
 * <p>Temperature mutation is now handled entirely by {@code BiomeMixin}, which
 * intercepts {@code Biome.getBaseTemperature()} at the bytecode level and
 * returns {@code 0.0f} for all eligible biomes when winter is applied.  This
 * replaces the previous {@code Unsafe.putObject} approach, which silently
 * failed because NeoForge's {@code ReplaceFieldWithGetterAccess} coremod
 * redirects every {@code GETFIELD Biome.climateSettings} to a generated
 * getter, making the raw field write invisible to callers.
 *
 * <p>This class is responsible for:
 * <ul>
 *   <li>Maintaining the {@link #isWinterApplied} flag that {@code BiomeMixin} reads</li>
 *   <li>Resyncing nearby chunks to clients so the updated temperature is reflected
 *       in weather particle rendering immediately</li>
 *   <li>Starting and stopping precipitation via {@link ServerLevel#setWeatherParameters}</li>
 * </ul>
 */
public final class WinterBiomeManager {

    /** True while winter climate mutations are active. Read by {@code BiomeMixin}. */
    private static volatile boolean isWinterApplied = false;

    /** Returns {@code true} while winter mutations are active. */
    public static boolean isWinterApplied() {
        return isWinterApplied;
    }

    /**
     * Activates winter: sets the flag (BiomeMixin takes it from here),
     * resyncs chunks to clients, then starts precipitation one tick later.
     */
    public static void applyWinter(ServerLevel overworld) {
        if (isWinterApplied) {
            GotMod.LOGGER.warn("[WinterBiomeManager] applyWinter called but winter is already applied — skipping.");
            return;
        }

        isWinterApplied = true;

        // Resync nearby chunks to all players so the client receives the updated
        // biome temperatures before precipitation starts.  Without this, the client
        // still sees the old temperature and renders rain instead of snow.
        resyncChunks(overworld);

        // Start precipitation one tick later so the client has processed the
        // chunk packets and will render snow instead of rain.
        // Duration: ~15 real minutes of snowfall.
        overworld.getServer().execute(
                () -> overworld.setWeatherParameters(0, 18_000, true, false));

        GotMod.LOGGER.info("[WinterBiomeManager] Winter applied — BiomeMixin will return 0.0f for all eligible biomes.");
    }

    /**
     * Deactivates winter: clears the flag, resyncs chunks, stops precipitation.
     */
    public static void revertWinter(ServerLevel overworld) {
        if (!isWinterApplied) {
            GotMod.LOGGER.warn("[WinterBiomeManager] revertWinter called but winter is not applied — skipping.");
            return;
        }

        isWinterApplied = false;

        resyncChunks(overworld);

        // Stop precipitation so the world doesn't immediately re-rain.
        overworld.setWeatherParameters(6_000, 0, false, false);

        GotMod.LOGGER.info("[WinterBiomeManager] Winter reverted.");
    }

    /**
     * Re-applies winter after a server restart (biome registry is repopulated
     * each load, but the Mixin flag just needs to be set again).
     */
    public static void restoreIfWinter(ServerLevel overworld) {
        if (SeasonManager.getCurrentSeason().isWinter() && !isWinterApplied) {
            GotMod.LOGGER.info("[WinterBiomeManager] Server restarted mid-winter — re-applying winter.");
            applyWinter(overworld);
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Resends a 7×7 grid of chunks around each online player so the client
     * receives the updated biome data and switches between rain and snow particles.
     */
    private static void resyncChunks(ServerLevel overworld) {
        for (ServerPlayer player : overworld.players()) {
            ChunkPos playerChunk = new ChunkPos(player.blockPosition());
            for (int dx = -3; dx <= 3; dx++) {
                for (int dz = -3; dz <= 3; dz++) {
                    LevelChunk chunk = overworld.getChunk(playerChunk.x + dx, playerChunk.z + dz);
                    player.connection.send(new ClientboundLevelChunkWithLightPacket(
                            chunk, overworld.getLightEngine(), null, null));
                }
            }
        }
    }

    private WinterBiomeManager() {}
}