package net.got.event;

import net.got.GotMod;
import net.got.climate.PlayerTemperatureSystem;
import net.got.climate.PlayerThirstSystem;
import net.got.faction.PlayerFactionState;
import net.got.network.FactionSyncPayload;
import net.got.network.PlayIntroSequencePayload;
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

        maybeResumeIntro(player);
        if (PlayerFactionState.hasFaction(player)) {
            syncFactionToClient(player);
        }

        net.got.skill.SkillPerkEffects.applyAttributeModifiers(player);
        net.got.skill.SkillXpService.syncToClient(player);
    }

    /**
     * Drives the black-screen intro from login instead of jumping straight
     * to the faction screen. Only fires on a world actually generated with
     * the knownworld preset (chunk generator check) — a normal vanilla
     * world with this mod installed is left alone entirely. A player who's
     * already finished character creation never sees it again; one who
     * picked a faction but never clicked through the closing line resumes
     * there instead of replaying character creation.
     */
    private static void maybeResumeIntro(ServerPlayer player) {
        if (!isKnownWorld(player)) return;
        if (net.got.intro.IntroState.hasEnteredKnownWorld(player)) return;

        boolean resumeAtFinalLine = PlayerFactionState.hasFaction(player);
        GotMod.queueServerWork(5, () ->
                PacketDistributor.sendToPlayer(player, new PlayIntroSequencePayload(resumeAtFinalLine)));
    }

    private static boolean isKnownWorld(ServerPlayer player) {
        return player.level().getChunkSource().getGenerator()
                instanceof net.got.worldgen.GotChunkGenerator;
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
        if (oldData.contains(net.got.intro.IntroState.KEY_ENTERED_KNOWN_WORLD)) {
            player.getPersistentData().putBoolean(
                    net.got.intro.IntroState.KEY_ENTERED_KNOWN_WORLD,
                    oldData.getBooleanOr(net.got.intro.IntroState.KEY_ENTERED_KNOWN_WORLD, false));
        }
        if (oldData.contains(net.got.intro.IntroState.KEY_CHARACTER_NAME)) {
            player.getPersistentData().putString(
                    net.got.intro.IntroState.KEY_CHARACTER_NAME,
                    oldData.getStringOr(net.got.intro.IntroState.KEY_CHARACTER_NAME, ""));
        }
        if (oldData.contains(net.got.intro.IntroState.KEY_PENDING_WAYPOINT)) {
            player.getPersistentData().putString(
                    net.got.intro.IntroState.KEY_PENDING_WAYPOINT,
                    oldData.getStringOr(net.got.intro.IntroState.KEY_PENDING_WAYPOINT, ""));
        }

        maybeResumeIntro(player);
        if (PlayerFactionState.hasFaction(player)) {
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
