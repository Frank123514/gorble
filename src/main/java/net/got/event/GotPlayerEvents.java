package net.got.event;

import net.got.GotMod;
import net.got.climate.PlayerTemperatureSystem;
import net.got.climate.PlayerThirstSystem;
import net.got.faction.GotFactions;
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

    public static final String NBT_KEY = "got.faction";
    private static final int VITALS_SYNC_INTERVAL = 20;

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!hasFaction(player)) {
            GotMod.queueServerWork(5, () ->
                    PacketDistributor.sendToPlayer(player, new OpenFactionScreenPayload()));
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        var uuid = event.getEntity().getUUID();
        PlayerTemperatureSystem.remove(uuid);
        PlayerThirstSystem.remove(uuid);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        CompoundTag oldData = event.getOriginal().getPersistentData();
        if (oldData.contains(NBT_KEY)) {
            player.getPersistentData().putString(NBT_KEY, oldData.getString(NBT_KEY));
        }
        if (!hasFaction(player)) {
            GotMod.queueServerWork(5, () ->
                    PacketDistributor.sendToPlayer(player, new OpenFactionScreenPayload()));
        }
    }

    /** Syncs both body temp and thirst to the client once per second. */
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

    public static boolean hasFaction(ServerPlayer player) {
        String id = getFactionId(player);
        return !id.isEmpty() && GotFactions.BY_ID.containsKey(id);
    }

    public static String getFactionId(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return data.contains(NBT_KEY) ? data.getString(NBT_KEY) : "";
    }

    public static void setFactionId(ServerPlayer player, String factionId) {
        player.getPersistentData().putString(NBT_KEY, factionId);
        GotMod.LOGGER.info("[GoT] Player {} chose faction: {}", player.getName().getString(), factionId);
    }

    private GotPlayerEvents() {}
}
