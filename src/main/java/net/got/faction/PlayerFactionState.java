package net.got.faction;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public final class PlayerFactionState {

    public static final String KEY_FACTION  = "got.faction";
    public static final String KEY_STANDING = "got.standing";
    public static final String KEY_TITLE    = "got.title";

    public static final int STANDING_MIN    =     0;
    public static final int STANDING_MAX    = 10_000;
    public static final int STANDING_DEFAULT=   200;

    public static final int GAIN_QUEST_MINOR  =  50;
    public static final int GAIN_QUEST_MAJOR  = 150;
    public static final int GAIN_KILL_ENEMY   =  10;
    public static final int GAIN_TRADE        =   5;
    public static final int GAIN_GIFT         =  25;

    public static final int LOSS_ATTACK_GUARD   = -100;
    public static final int LOSS_ATTACK_CIVILIAN=  -50;
    public static final int LOSS_CRIMINAL_ACT   =  -75;
    public static final int LOSS_BETRAY_QUEST   = -200;

    public static String getFactionId(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return data.getStringOr(KEY_FACTION, "");
    }

    public static boolean hasFaction(ServerPlayer player) {
        String id = getFactionId(player);
        return !id.isEmpty() && Factions.BY_ID.containsKey(id);
    }

    public static int getStanding(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return data.getIntOr(KEY_STANDING, STANDING_DEFAULT);
    }

    public static String getCachedTitle(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return data.getStringOr(KEY_TITLE, "");
    }

    public static FactionData.RankTitle getCurrentRank(ServerPlayer player) {
        FactionData faction = Factions.get(getFactionId(player));
        if (faction == null) return null;
        return faction.rankFor(getStanding(player));
    }

    public static void setFaction(ServerPlayer player, String factionId) {
        CompoundTag data = player.getPersistentData();
        data.putString(KEY_FACTION, factionId);
        data.putInt(KEY_STANDING, STANDING_DEFAULT);
        refreshTitle(player);
    }

    public static int modifyStanding(ServerPlayer player, int delta) {
        int current = getStanding(player);
        int updated = Math.clamp(current + delta, STANDING_MIN, STANDING_MAX);
        player.getPersistentData().putInt(KEY_STANDING, updated);
        refreshTitle(player);
        return updated;
    }

    public static void setStanding(ServerPlayer player, int value) {
        int clamped = Math.clamp(value, STANDING_MIN, STANDING_MAX);
        player.getPersistentData().putInt(KEY_STANDING, clamped);
        refreshTitle(player);
    }

    public static void clearFaction(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        data.remove(KEY_FACTION);
        data.remove(KEY_STANDING);
        data.remove(KEY_TITLE);
    }

    private static void refreshTitle(ServerPlayer player) {
        FactionData.RankTitle rank = getCurrentRank(player);
        String title = (rank != null) ? rank.title() : "";
        player.getPersistentData().putString(KEY_TITLE, title);
    }

    private PlayerFactionState() {}
}
