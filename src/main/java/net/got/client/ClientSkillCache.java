package net.got.client;

import net.got.network.SkillSyncPayload;
import net.got.skill.Skill;
import net.got.skill.SkillPerks;
import net.got.skill.SkillLevelCurve;
import net.got.skill.SkillPerk;

import java.util.HashSet;
import java.util.Set;

public final class ClientSkillCache {

    private static final int[] xpBySkillOrdinal = new int[Skill.values().length];
    private static Set<String> unlockedPerkIds = new HashSet<>();

    public static void onSyncReceived(SkillSyncPayload payload) {
        int[] xp = payload.xpBySkillOrdinal();
        for (int i = 0; i < xpBySkillOrdinal.length && i < xp.length; i++) {
            xpBySkillOrdinal[i] = xp[i];
        }
        unlockedPerkIds = new HashSet<>(payload.unlockedPerkIds());
    }

    public static int getXp(Skill skill) {
        return xpBySkillOrdinal[skill.ordinal()];
    }

    public static int getLevel(Skill skill) {
        return SkillLevelCurve.levelForTotalXp(getXp(skill));
    }

    public static float getProgressFraction(Skill skill) {
        return SkillLevelCurve.progressFraction(getXp(skill), getLevel(skill));
    }

    public static int getEarnedPerkPoints(Skill skill) {
        return getLevel(skill) - SkillLevelCurve.MIN_LEVEL;
    }

    public static int getSpentPerkPoints(Skill skill) {
        int spent = 0;
        for (SkillPerk perk : SkillPerks.forSkill(skill)) {
            if (hasPerk(perk.id())) spent += perk.pointCost();
        }
        return spent;
    }

    public static int getAvailablePerkPoints(Skill skill) {
        return getEarnedPerkPoints(skill) - getSpentPerkPoints(skill);
    }

    public static boolean hasPerk(String perkId) {
        return unlockedPerkIds.contains(perkId);
    }

    public static boolean canUnlock(SkillPerk perk) {
        if (hasPerk(perk.id())) return false;
        if (getLevel(perk.skill()) < perk.levelRequirement()) return false;
        if (getAvailablePerkPoints(perk.skill()) < perk.pointCost()) return false;
        for (SkillPerk earlier : SkillPerks.forSkill(perk.skill())) {
            if (earlier.tier() < perk.tier() && !hasPerk(earlier.id())) return false;
        }
        return true;
    }

    private ClientSkillCache() {}
}