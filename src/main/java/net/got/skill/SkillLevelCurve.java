package net.got.skill;

/**
 * The XP curve shared by every {@link GotSkill}. Skyrim-style: level 1 starts
 * at 0 XP, and each level requires progressively more XP than the last, so
 * early levels come quickly from casual use and later levels demand real
 * dedication to that skill.
 *
 * <p>Requirement per level is linear ({@code 50 * level} XP to clear that
 * level), which makes total XP-to-reach-level a triangular-number curve:
 * <pre>
 *   totalXpForLevel(L) = 25 * L * (L - 1)
 * </pre>
 * so going from 1→2 costs 50 XP, 2→3 costs 100 XP, ... 99→100 costs 4,950 XP,
 * for 247,500 total XP to max out a single skill at level {@link #MAX_LEVEL}.
 */
public final class SkillLevelCurve {

    public static final int MAX_LEVEL = 100;
    public static final int MIN_LEVEL = 1;

    /** Total accumulated XP required to have fully reached {@code level}. */
    public static int totalXpForLevel(int level) {
        int clamped = Math.clamp(level, MIN_LEVEL, MAX_LEVEL);
        return 25 * clamped * (clamped - 1);
    }

    /** XP required to climb from {@code level} to {@code level + 1}. */
    public static int xpToClearLevel(int level) {
        if (level >= MAX_LEVEL) return 0;
        return 50 * level;
    }

    /** Derives the current level from an accumulated total-XP value. */
    public static int levelForTotalXp(int totalXp) {
        if (totalXp <= 0) return MIN_LEVEL;
        // Solve 25*L^2 - 25*L - totalXp = 0 for L via the quadratic formula,
        // then walk down/up one step to correct for float rounding at the
        // boundary - cheap and exact since levels only run to 100.
        double approx = (25.0 + Math.sqrt(625.0 + 100.0 * totalXp)) / 50.0;
        int level = (int) Math.floor(approx);
        level = Math.clamp(level, MIN_LEVEL, MAX_LEVEL);
        while (level < MAX_LEVEL && totalXpForLevel(level + 1) <= totalXp) level++;
        while (level > MIN_LEVEL && totalXpForLevel(level) > totalXp) level--;
        return level;
    }

    /** How far into the current level {@code totalXp} sits, in [0, xpToClearLevel(level)). */
    public static int xpIntoLevel(int totalXp, int level) {
        return Math.max(0, totalXp - totalXpForLevel(level));
    }

    /** Progress through the current level as a fraction in [0, 1]. Always 1.0 at {@link #MAX_LEVEL}. */
    public static float progressFraction(int totalXp, int level) {
        if (level >= MAX_LEVEL) return 1.0f;
        int need = xpToClearLevel(level);
        return need <= 0 ? 1.0f : Math.clamp(xpIntoLevel(totalXp, level) / (float) need, 0.0f, 1.0f);
    }

    /** Caps a total-XP value at the amount needed for {@link #MAX_LEVEL}. */
    public static int clampTotalXp(int totalXp) {
        return Math.clamp(totalXp, 0, totalXpForLevel(MAX_LEVEL));
    }

    private SkillLevelCurve() {}
}