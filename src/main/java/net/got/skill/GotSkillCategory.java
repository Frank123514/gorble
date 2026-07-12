package net.got.skill;

/**
 * The three groupings {@link GotSkill}s are organised under on the Skills
 * tab. Purely a presentation / filtering concern - gameplay logic never
 * branches on category, only on the individual {@link GotSkill}.
 */
public enum GotSkillCategory {
    COMBAT   ("Combat",   0xFFB0402A),
    GATHERING("Gathering",0xFF4A7A3A),
    CRAFTING ("Crafting", 0xFF7A5A2A);

    /** Heading shown above this category's row of skills in the UI. */
    public final String label;

    /** Accent colour (ARGB) used for this category's heading and progress bars. */
    public final int colour;

    GotSkillCategory(String label, int colour) {
        this.label = label;
        this.colour = colour;
    }
}