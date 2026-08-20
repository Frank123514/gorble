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
public final class PlayerEvents {

    @Deprecated
    public static final String NBT_KEY = PlayerFactionState.KEY_FACTION;

    private static final int VITALS_SYNC_INTERVAL = 20;

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        if (!PlayerFactionState.hasFaction(player)) {
            
            GotMod.queueServerWork(5, () ->
                    PacketDistributor.sendToPlayer(player, new OpenFactionScreenPayload()));
        } else {
            
            syncFactionToClient(player);
        }

        net.got.skill.SkillPerkEffects.applyAttributeModifiers(player);
        net.got.skill.SkillXpService.syncToClient(player);
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

        if (oldData.contains(PlayerFactionState.KEY_FACTION)) {
            player.getPersistentData().putString(
                    PlayerFactionState.KEY_FACTION,
                    oldData.getStringOr(PlayerFactionState.KEY_FACTION, ""));
        }
        if (oldData.contains(PlayerFactionState.KEY_STANDING)) {
            player.getPersistentData().putInt(
                    PlayerFactionState.KEY_STANDING,
                    oldData.getIntOr(PlayerFactionState.KEY_STANDING, 0));
        }
        if (oldData.contains(PlayerFactionState.KEY_TITLE)) {
            player.getPersistentData().putString(
                    PlayerFactionState.KEY_TITLE,
                    oldData.getStringOr(PlayerFactionState.KEY_TITLE, ""));
        }

        if (!PlayerFactionState.hasFaction(player)) {
            GotMod.queueServerWork(5, () ->
                    PacketDistributor.sendToPlayer(player, new OpenFactionScreenPayload()));
        } else {
            syncFactionToClient(player);
        }

        net.got.skill.PlayerSkillState.copyAcrossRespawn(oldData, player);
        net.got.skill.SkillPerkEffects.applyAttributeModifiers(player);
        net.got.skill.SkillXpService.syncToClient(player);
    }

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

        net.got.skill.SkillXpService.syncToClient(player);
    }

    public static void syncFactionToClient(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new FactionSyncPayload(
                PlayerFactionState.getFactionId(player),
                PlayerFactionState.getStanding(player),
                PlayerFactionState.getCachedTitle(player)
        ));
    }

    @Deprecated
    public static boolean hasFaction(ServerPlayer player) {
        return PlayerFactionState.hasFaction(player);
    }

    @Deprecated
    public static String getFactionId(ServerPlayer player) {
        return PlayerFactionState.getFactionId(player);
    }

    @Deprecated
    public static void setFactionId(ServerPlayer player, String factionId) {
        PlayerFactionState.setFaction(player, factionId);
        GotMod.LOGGER.info("[GoT] Player {} chose faction: {}",
                player.getName().getString(), factionId);
        syncFactionToClient(player);
    }
}
