package net.got.skill;

public final class SkillLevelCurve {

    public static final int MAX_LEVEL = 100;
    public static final int MIN_LEVEL = 1;

    public static int totalXpForLevel(int level) {
        int clamped = Math.clamp(level, MIN_LEVEL, MAX_LEVEL);
        return 25 * clamped * (clamped - 1);
    }

    public static int xpToClearLevel(int level) {
        if (level >= MAX_LEVEL) return 0;
        return 50 * level;
    }

    public static int levelForTotalXp(int totalXp) {
        if (totalXp <= 0) return MIN_LEVEL;
        
        double approx = (25.0 + Math.sqrt(625.0 + 100.0 * totalXp)) / 50.0;
        int level = (int) Math.floor(approx);
        level = Math.clamp(level, MIN_LEVEL, MAX_LEVEL);
        while (level < MAX_LEVEL && totalXpForLevel(level + 1) <= totalXp) level++;
        while (level > MIN_LEVEL && totalXpForLevel(level) > totalXp) level--;
        return level;
    }

    public static int xpIntoLevel(int totalXp, int level) {
        return Math.max(0, totalXp - totalXpForLevel(level));
    }

    public static float progressFraction(int totalXp, int level) {
        if (level >= MAX_LEVEL) return 1.0f;
        int need = xpToClearLevel(level);
        return need <= 0 ? 1.0f : Math.clamp(xpIntoLevel(totalXp, level) / (float) need, 0.0f, 1.0f);
    }

    public static int clampTotalXp(int totalXp) {
        return Math.clamp(totalXp, 0, totalXpForLevel(MAX_LEVEL));
    }

    private SkillLevelCurve() {}
}