package net.got.skill;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the per-player skill state stored in {@link ServerPlayer#getPersistentData()},
 * same storage convention as {@link net.got.faction.PlayerFactionState}.
 *
 * <p>Persisted data, all nested under the {@code got.skills} compound:
 * <ul>
 *   <li>{@code xp.<skillId>}    — total accumulated XP (int) for that skill.</li>
 *   <li>{@code perks}           — list of unlocked perk id strings, across all skills.</li>
 * </ul>
 *
 * <p>Use the static helper methods everywhere skill data is read or written to
 * keep all NBT keys in one place. Levels are never stored directly - they're
 * always derived from XP via {@link SkillLevelCurve} so the two can never drift.
 */
public final class PlayerSkillState {

    private static final String ROOT       = "got.skills";
    private static final String KEY_XP     = "xp";
    private static final String KEY_PERKS  = "perks";

    // ── Read helpers ─────────────────────────────────────────────────────────

    private static CompoundTag root(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(ROOT)) data.put(ROOT, new CompoundTag());
        return data.getCompound(ROOT);
    }

    /** Total accumulated XP the player has in the given skill. */
    public static int getXp(ServerPlayer player, GotSkill skill) {
        CompoundTag root = root(player);
        if (!root.contains(KEY_XP)) return 0;
        CompoundTag xp = root.getCompound(KEY_XP);
        return xp.contains(skill.id) ? xp.getInt(skill.id) : 0;
    }

    /** Current level (1-100) in the given skill, derived from XP. */
    public static int getLevel(ServerPlayer player, GotSkill skill) {
        return SkillLevelCurve.levelForTotalXp(getXp(player, skill));
    }

    /** Perk points earned so far in this skill - one per level gained past 1. */
    public static int getEarnedPerkPoints(ServerPlayer player, GotSkill skill) {
        return getLevel(player, skill) - SkillLevelCurve.MIN_LEVEL;
    }

    /** Perk points already spent unlocking perks in this skill. */
    public static int getSpentPerkPoints(ServerPlayer player, GotSkill skill) {
        int spent = 0;
        for (SkillPerk perk : GotSkillPerks.forSkill(skill)) {
            if (hasPerk(player, perk.id())) spent += perk.pointCost();
        }
        return spent;
    }

    /** Perk points currently available to spend in this skill. */
    public static int getAvailablePerkPoints(ServerPlayer player, GotSkill skill) {
        return getEarnedPerkPoints(player, skill) - getSpentPerkPoints(player, skill);
    }

    /** Returns every unlocked perk id, across all skills. */
    public static List<String> getUnlockedPerkIds(ServerPlayer player) {
        CompoundTag root = root(player);
        List<String> ids = new ArrayList<>();
        if (!root.contains(KEY_PERKS)) return ids;
        ListTag list = root.getList(KEY_PERKS, net.minecraft.nbt.Tag.TAG_STRING);
        for (int i = 0; i < list.size(); i++) ids.add(list.getString(i));
        return ids;
    }

    /** Whether the player has unlocked the given perk. */
    public static boolean hasPerk(ServerPlayer player, String perkId) {
        return getUnlockedPerkIds(player).contains(perkId);
    }

    // ── Write helpers ────────────────────────────────────────────────────────

    /**
     * Adds {@code amount} XP to a skill, clamped to the level-100 cap.
     * Purely a data mutation - callers wanting level-up messages/perk-point
     * notifications should go through {@link SkillXpService#grantXp} instead.
     *
     * @return the skill's new total XP.
     */
    public static int addXp(ServerPlayer player, GotSkill skill, int amount) {
        if (amount == 0) return getXp(player, skill);
        CompoundTag root = root(player);
        CompoundTag xp = root.contains(KEY_XP) ? root.getCompound(KEY_XP) : new CompoundTag();
        int updated = SkillLevelCurve.clampTotalXp(getXp(player, skill) + amount);
        xp.putInt(skill.id, updated);
        root.put(KEY_XP, xp);
        return updated;
    }

    /**
     * Attempts to unlock a perk for the player. Validates level requirement,
     * chain order (previous tier must already be unlocked) and available
     * points server-side - never trust the client's request blindly.
     *
     * @return {@code true} if the perk was unlocked, {@code false} if the
     *         request was invalid for any reason.
     */
    public static boolean unlockPerk(ServerPlayer player, SkillPerk perk) {
        if (perk == null) return false;
        if (hasPerk(player, perk.id())) return false; // already unlocked
        if (getLevel(player, perk.skill()) < perk.levelRequirement()) return false;
        if (getAvailablePerkPoints(player, perk.skill()) < perk.pointCost()) return false;

        // Chain order: every lower tier in this skill must already be unlocked.
        for (SkillPerk earlier : GotSkillPerks.forSkill(perk.skill())) {
            if (earlier.tier() < perk.tier() && !hasPerk(player, earlier.id())) return false;
        }

        CompoundTag root = root(player);
        ListTag list = root.contains(KEY_PERKS)
                ? root.getList(KEY_PERKS, net.minecraft.nbt.Tag.TAG_STRING)
                : new ListTag();
        list.add(StringTag.valueOf(perk.id()));
        root.put(KEY_PERKS, list);
        return true;
    }

    private PlayerSkillState() {}
}