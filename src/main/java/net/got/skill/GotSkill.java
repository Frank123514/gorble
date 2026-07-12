package net.got.skill;

/**
 * Every skill the player can level up through use, Skyrim-style: no XP is
 * ever spent or awarded manually - swinging a sword raises {@link #COMBAT},
 * breaking ore raises {@link #MINING}, and so on. See {@link PlayerSkillState}
 * for the persisted level/XP data, {@link SkillXpService} for granting XP,
 * and {@link GotSkillPerks} for each skill's perk tree.
 *
 * <p>{@link #id} is the NBT/network key and must never change once players
 * have save data using it.
 */
public enum GotSkill {

    // ── Combat ───────────────────────────────────────────────────────────────
    COMBAT("combat", "Combat", GotSkillCategory.COMBAT,
            "The art of the blade and axe. Grows every time you land a blow in melee.",
            'C'),
    ARCHERY("archery", "Archery", GotSkillCategory.COMBAT,
            "Marksmanship with bow and crossbow. Grows every time a loosed shaft finds its mark.",
            'A'),
    DEFENSE("defense", "Defense", GotSkillCategory.COMBAT,
            "Toughness in the face of harm. Grows every time you weather an attack.",
            'D'),

    // ── Gathering ────────────────────────────────────────────────────────────
    MINING("mining", "Mining", GotSkillCategory.GATHERING,
            "Delving stone and ore. Grows every time you break rock with a pick.",
            'M'),
    WOODCUTTING("woodcutting", "Woodcutting", GotSkillCategory.GATHERING,
            "Felling the forests of Westeros. Grows every time you drop a log with an axe.",
            'W'),
    FARMING("farming", "Farming", GotSkillCategory.GATHERING,
            "Coaxing a harvest from the earth. Grows every time you reap a ripened crop.",
            'F'),

    // ── Crafting ─────────────────────────────────────────────────────────────
    SMITHING("smithing", "Smithing", GotSkillCategory.CRAFTING,
            "Working hot metal at the anvil. Grows with every well-struck hammer blow.",
            'S'),
    COOKING("cooking", "Cooking", GotSkillCategory.CRAFTING,
            "Turning raw stores into a proper meal. Grows every time you pull a finished dish from the oven.",
            'K');

    /** Stable NBT/network key - never rename once shipped. */
    public final String id;
    public final String displayName;
    public final GotSkillCategory category;
    public final String description;
    /** Single-letter glyph drawn on the skill's plaque until real icons exist. */
    public final char glyph;

    GotSkill(String id, String displayName, GotSkillCategory category, String description, char glyph) {
        this.id = id;
        this.displayName = displayName;
        this.category = category;
        this.description = description;
        this.glyph = glyph;
    }

    /** Looks up a skill by its {@link #id}, or {@code null} if unknown (e.g. stale save data). */
    public static GotSkill byId(String id) {
        for (GotSkill skill : values()) {
            if (skill.id.equals(id)) return skill;
        }
        return null;
    }
}