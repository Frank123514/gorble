package net.got.event;

import net.got.GotMod;
import net.got.climate.PlayerTemperatureSystem;
import net.got.faction.GotFactions;
import net.got.network.OpenFactionScreenPayload;
import net.got.network.PlayerTemperaturePayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Server-side player event handler for the faction system and climate tracking.
 *
 * <p><b>Faction persistence:</b> the player's chosen faction id is stored under
 * the key {@code "got.faction"} in {@link net.minecraft.world.entity.player.Player#getPersistentData()}.
 * An empty string or absent key means "not yet chosen".
 *
 * <p><b>First-spawn trigger:</b> {@link #onPlayerLoggedIn} fires after the player entity
 * is fully loaded on the server. If their persistent data contains no valid faction id,
 * an {@link OpenFactionScreenPayload} is sent so the client shows the selection screen.
 *
 * <p><b>Temperature sync:</b> {@link #onPlayerTick} sends a
 * {@link PlayerTemperaturePayload} to the client once per second so the HUD
 * always displays the authoritative server-side temperature value.
 *
 * <p><b>Disconnect cleanup:</b> {@link #onPlayerLoggedOut} removes the player's
 * temperature entry from {@link PlayerTemperatureSystem} to prevent a memory leak.
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class GotPlayerEvents {

    /** NBT key used to persist the chosen faction on the player entity. */
    public static final String NBT_KEY = "got.faction";

    /** How often (ticks) to send a temperature update to the client. */
    private static final int TEMP_SYNC_INTERVAL = 20;

    // ── Login ──────────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        if (!hasFaction(player)) {
            // Delay one tick so the client's world is fully loaded before the screen fires
            GotMod.queueServerWork(5, () ->
                    PacketDistributor.sendToPlayer(player, new OpenFactionScreenPayload()));
        }
    }

    // ── Logout / disconnect cleanup ────────────────────────────────────────────

    /**
     * Removes the player's temperature entry when they disconnect.
     * Without this the UUID→float map in {@link PlayerTemperatureSystem} would
     * grow unboundedly over the lifetime of the server process.
     */
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        PlayerTemperatureSystem.removePlayer(event.getEntity().getUUID());
    }

    // ── Respawn (death / dimension change) ────────────────────────────────────
    //
    // PlayerEvent.Clone fires when a player dies or travels between dimensions.
    // We re-send the screen if they still haven't chosen — this catches the edge
    // case where a player died before confirming.

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        // Copy faction data from the old player to the new player instance
        CompoundTag oldData = event.getOriginal().getPersistentData();
        if (oldData.contains(NBT_KEY)) {
            player.getPersistentData().putString(NBT_KEY, oldData.getString(NBT_KEY));
        }

        // If still unchosen, show the screen again on the next tick
        if (!hasFaction(player)) {
            GotMod.queueServerWork(5, () ->
                    PacketDistributor.sendToPlayer(player, new OpenFactionScreenPayload()));
        }
    }

    // ── Temperature sync (S→C) ────────────────────────────────────────────────

    /**
     * Pushes the current server-side temperature to the client once per second.
     * The client stores the received value in {@link net.got.client.gui.overlay.TemperatureHudOverlay}
     * for rendering in the HUD.
     */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.isSpectator()) return;
        if (player.tickCount % TEMP_SYNC_INTERVAL != 0) return;

        float temp = PlayerTemperatureSystem.getTemperature(player.getUUID());
        PacketDistributor.sendToPlayer(player, new PlayerTemperaturePayload(temp));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Returns {@code true} if the player has already confirmed a valid faction.
     */
    public static boolean hasFaction(ServerPlayer player) {
        String factionId = getFactionId(player);
        return !factionId.isEmpty() && GotFactions.BY_ID.containsKey(factionId);
    }

    /**
     * Returns the stored faction id, or an empty string if none is set.
     */
    public static String getFactionId(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return data.contains(NBT_KEY) ? data.getString(NBT_KEY) : "";
    }

    /**
     * Stores the chosen faction id on the player's persistent data.
     * Called from the {@link net.got.network.GotNetwork} handler when
     * {@link net.got.network.SelectFactionPayload} is received.
     */
    public static void setFactionId(ServerPlayer player, String factionId) {
        player.getPersistentData().putString(NBT_KEY, factionId);
        GotMod.LOGGER.info("[GoT] Player {} chose faction: {}", player.getName().getString(), factionId);
    }

    private GotPlayerEvents() {}
}