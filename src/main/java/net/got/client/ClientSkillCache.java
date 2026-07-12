package net.got.client;

import net.got.network.SkillSyncPayload;
import net.got.skill.GotSkill;
import net.got.skill.GotSkillPerks;
import net.got.skill.SkillLevelCurve;
import net.got.skill.SkillPerk;

import java.util.HashSet;
import java.util.Set;

/**
 * Client-side mirror of the local player's skill state.
 *
 * <p>Updated whenever a {@link SkillSyncPayload} arrives from the server.
 * Use this class in all client-side rendering code (the Skills screen)
 * instead of touching server-only player NBT.
 */
public final class ClientSkillCache {

    private static final int[] xpBySkillOrdinal = new int[GotSkill.values().length];
    private static Set<String> unlockedPerkIds = new HashSet<>();

    // ── Called by the client-side packet handler ───────────────────────────

    public static void onSyncReceived(SkillSyncPayload payload) {
        int[] xp = payload.xpBySkillOrdinal();
        for (int i = 0; i < xpBySkillOrdinal.length && i < xp.length; i++) {
            xpBySkillOrdinal[i] = xp[i];
        }
        unlockedPerkIds = new HashSet<>(payload.unlockedPerkIds());
    }

    // ── Accessors ────────────────────────────────────────────────────────────

    public static int getXp(GotSkill skill) {
        return xpBySkillOrdinal[skill.ordinal()];
    }

    public static int getLevel(GotSkill skill) {
        return SkillLevelCurve.levelForTotalXp(getXp(skill));
    }

    public static float getProgressFraction(GotSkill skill) {
        return SkillLevelCurve.progressFraction(getXp(skill), getLevel(skill));
    }

    public static int getEarnedPerkPoints(GotSkill skill) {
        return getLevel(skill) - SkillLevelCurve.MIN_LEVEL;
    }

    public static int getSpentPerkPoints(GotSkill skill) {
        int spent = 0;
        for (SkillPerk perk : GotSkillPerks.forSkill(skill)) {
            if (hasPerk(perk.id())) spent += perk.pointCost();
        }
        return spent;
    }

    public static int getAvailablePerkPoints(GotSkill skill) {
        return getEarnedPerkPoints(skill) - getSpentPerkPoints(skill);
    }

    public static boolean hasPerk(String perkId) {
        return unlockedPerkIds.contains(perkId);
    }

    /** Whether {@code perk} could be unlocked right now (level, chain order, points all satisfied). */
    public static boolean canUnlock(SkillPerk perk) {
        if (hasPerk(perk.id())) return false;
        if (getLevel(perk.skill()) < perk.levelRequirement()) return false;
        if (getAvailablePerkPoints(perk.skill()) < perk.pointCost()) return false;
        for (SkillPerk earlier : GotSkillPerks.forSkill(perk.skill())) {
            if (earlier.tier() < perk.tier() && !hasPerk(earlier.id())) return false;
        }
        return true;
    }

    private ClientSkillCache() {}
}