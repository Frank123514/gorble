package net.francis.got.skill;

public enum Skill {

    COMBAT("combat", "Combat", SkillCategory.COMBAT,
            "The art of the blade and axe. Grows every time you land a blow in melee.",
            'C'),
    ARCHERY("archery", "Archery", SkillCategory.COMBAT,
            "Marksmanship with bow and crossbow. Grows every time a loosed shaft finds its mark.",
            'A'),
    DEFENSE("defense", "Defense", SkillCategory.COMBAT,
            "Toughness in the face of harm. Grows every time you weather an attack.",
            'D'),

    MINING("mining", "Mining", SkillCategory.GATHERING,
            "Delving stone and ore. Grows every time you break rock with a pick.",
            'M'),
    WOODCUTTING("woodcutting", "Woodcutting", SkillCategory.GATHERING,
            "Felling the forests of Westeros. Grows every time you drop a log with an axe.",
            'W'),
    FARMING("farming", "Farming", SkillCategory.GATHERING,
            "Coaxing a harvest from the earth. Grows every time you reap a ripened crop.",
            'F'),

    SMITHING("smithing", "Smithing", SkillCategory.CRAFTING,
            "Working hot metal at the anvil. Grows with every well-struck hammer blow.",
            'S'),
    COOKING("cooking", "Cooking", SkillCategory.CRAFTING,
            "Turning raw stores into a proper meal. Grows every time you pull a finished dish from the oven.",
            'K');

    public final String id;
    public final String displayName;
    public final SkillCategory category;
    public final String description;
    
    public final char glyph;

    Skill(String id, String displayName, SkillCategory category, String description, char glyph) {
        this.id = id;
        this.displayName = displayName;
        this.category = category;
        this.description = description;
        this.glyph = glyph;
    }

    public static Skill byId(String id) {
        for (Skill skill : values()) {
            if (skill.id.equals(id)) return skill;
        }
        return null;
    }
}