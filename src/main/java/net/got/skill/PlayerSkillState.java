package net.got.skill;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public final class PlayerSkillState {

    private static final String ROOT       = "got.skills";
    private static final String KEY_XP     = "xp";
    private static final String KEY_PERKS  = "perks";

    private static CompoundTag root(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(ROOT)) data.put(ROOT, new CompoundTag());
        return data.getCompoundOrEmpty(ROOT);
    }

    public static int getXp(ServerPlayer player, Skill skill) {
        CompoundTag root = root(player);
        if (!root.contains(KEY_XP)) return 0;
        CompoundTag xp = root.getCompoundOrEmpty(KEY_XP);
        return xp.getIntOr(skill.id, 0);
    }

    public static int getLevel(ServerPlayer player, Skill skill) {
        return SkillLevelCurve.levelForTotalXp(getXp(player, skill));
    }

    public static int getEarnedPerkPoints(ServerPlayer player, Skill skill) {
        return getLevel(player, skill) - SkillLevelCurve.MIN_LEVEL;
    }

    public static int getSpentPerkPoints(ServerPlayer player, Skill skill) {
        int spent = 0;
        for (SkillPerk perk : SkillPerks.forSkill(skill)) {
            if (hasPerk(player, perk.id())) spent += perk.pointCost();
        }
        return spent;
    }

    public static int getAvailablePerkPoints(ServerPlayer player, Skill skill) {
        return getEarnedPerkPoints(player, skill) - getSpentPerkPoints(player, skill);
    }

    public static List<String> getUnlockedPerkIds(ServerPlayer player) {
        CompoundTag root = root(player);
        List<String> ids = new ArrayList<>();
        if (!root.contains(KEY_PERKS)) return ids;
        ListTag list = root.getListOrEmpty(KEY_PERKS);
        for (int i = 0; i < list.size(); i++) ids.add(list.getStringOr(i, ""));
        return ids;
    }

    public static boolean hasPerk(ServerPlayer player, String perkId) {
        return getUnlockedPerkIds(player).contains(perkId);
    }

    public static void copyAcrossRespawn(CompoundTag oldPersistentData, ServerPlayer newPlayer) {
        if (oldPersistentData.contains(ROOT)) {
            newPlayer.getPersistentData().put(ROOT, oldPersistentData.getCompoundOrEmpty(ROOT).copy());
        }
    }

    public static int addXp(ServerPlayer player, Skill skill, int amount) {
        if (amount == 0) return getXp(player, skill);
        CompoundTag root = root(player);
        CompoundTag xp = root.getCompoundOrEmpty(KEY_XP);
        int updated = SkillLevelCurve.clampTotalXp(getXp(player, skill) + amount);
        xp.putInt(skill.id, updated);
        root.put(KEY_XP, xp);
        return updated;
    }

    public static boolean unlockPerk(ServerPlayer player, SkillPerk perk) {
        if (perk == null) return false;
        if (hasPerk(player, perk.id())) return false;
        if (getLevel(player, perk.skill()) < perk.levelRequirement()) return false;
        if (getAvailablePerkPoints(player, perk.skill()) < perk.pointCost()) return false;

        for (SkillPerk earlier : SkillPerks.forSkill(perk.skill())) {
            if (earlier.tier() < perk.tier() && !hasPerk(player, earlier.id())) return false;
        }

        CompoundTag root = root(player);
        ListTag list = root.getListOrEmpty(KEY_PERKS);
        list.add(StringTag.valueOf(perk.id()));
        root.put(KEY_PERKS, list);
        return true;
    }

    private PlayerSkillState() {}
}