package net.got.event;

import net.got.GotMod;
import net.got.climate.PlayerTemperatureSystem;
import net.got.climate.PlayerThirstSystem;
import net.got.faction.PlayerFactionState;
import net.got.network.FactionSyncPayload;
import net.got.network.OpenFactionScreenPayload;
import net.got.network.PlayerVitalsPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = GotMod.MODID)
public final class GotPlayerEvents {

    // ── Legacy NBT key kept for migration only ────────────────────────────────
    /** @deprecated Use {@link PlayerFactionState#KEY_FACTION} instead. */
    @Deprecated
    public static final String NBT_KEY = PlayerFactionState.KEY_FACTION;

    private static final int VITALS_SYNC_INTERVAL = 20;

    // ── Login ─────────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        if (!PlayerFactionState.hasFaction(player)) {
            // Show faction selection screen after a short delay so the world loads first.
            GotMod.queueServerWork(5, () ->
                    PacketDistributor.sendToPlayer(player, new OpenFactionScreenPayload()));
        } else {
            // Sync current state to the (re-)logging-in client.
            syncFactionToClient(player);
        }
    }

    // ── Logout ────────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        var uuid = event.getEntity().getUUID();
        PlayerTemperatureSystem.remove(uuid);
        PlayerThirstSystem.remove(uuid);
    }

    // ── Respawn / clone ───────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        CompoundTag oldData = event.getOriginal().getPersistentData();

        // Copy faction and standing across the respawn boundary.
        if (oldData.contains(PlayerFactionState.KEY_FACTION)) {
            player.getPersistentData().putString(
                    PlayerFactionState.KEY_FACTION,
                    oldData.getString(PlayerFactionState.KEY_FACTION));
        }
        if (oldData.contains(PlayerFactionState.KEY_STANDING)) {
            player.getPersistentData().putInt(
                    PlayerFactionState.KEY_STANDING,
                    oldData.getInt(PlayerFactionState.KEY_STANDING));
        }
        if (oldData.contains(PlayerFactionState.KEY_TITLE)) {
            player.getPersistentData().putString(
                    PlayerFactionState.KEY_TITLE,
                    oldData.getString(PlayerFactionState.KEY_TITLE));
        }

        if (!PlayerFactionState.hasFaction(player)) {
            GotMod.queueServerWork(5, () ->
                    PacketDistributor.sendToPlayer(player, new OpenFactionScreenPayload()));
        } else {
            syncFactionToClient(player);
        }
    }

    // ── Tick ──────────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.isSpectator()) return;
        if (player.tickCount % VITALS_SYNC_INTERVAL != 0) return;

        var uuid = player.getUUID();
        PacketDistributor.sendToPlayer(player, new PlayerVitalsPayload(
                PlayerTemperatureSystem.getBodyTemp(uuid),
                PlayerThirstSystem.getThirst(uuid)
        ));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Sends the player's current faction state to their client.
     * Call this after any standing or faction change.
     */
    public static void syncFactionToClient(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new FactionSyncPayload(
                PlayerFactionState.getFactionId(player),
                PlayerFactionState.getStanding(player),
                PlayerFactionState.getCachedTitle(player)
        ));
    }

    // ── Legacy shims (kept so existing call sites compile without changes) ────

    /** @deprecated Use {@link PlayerFactionState#hasFaction(ServerPlayer)}. */
    @Deprecated
    public static boolean hasFaction(ServerPlayer player) {
        return PlayerFactionState.hasFaction(player);
    }

    /** @deprecated Use {@link PlayerFactionState#getFactionId(ServerPlayer)}. */
    @Deprecated
    public static String getFactionId(ServerPlayer player) {
        return PlayerFactionState.getFactionId(player);
    }

    /** @deprecated Use {@link PlayerFactionState#setFaction(ServerPlayer, String)}. */
    @Deprecated
    public static void setFactionId(ServerPlayer player, String factionId) {
        PlayerFactionState.setFaction(player, factionId);
        GotMod.LOGGER.info("[GoT] Player {} chose faction: {}",
                player.getName().getString(), factionId);
        syncFactionToClient(player);
    }
}
