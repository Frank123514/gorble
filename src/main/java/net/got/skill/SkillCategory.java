package net.got.skill;

public enum SkillCategory {
    COMBAT   ("Combat",   0xFFB0402A),
    GATHERING("Gathering",0xFF4A7A3A),
    CRAFTING ("Crafting", 0xFF7A5A2A);

    public final String label;

    public final int colour;

    SkillCategory(String label, int colour) {
        this.label = label;
        this.colour = colour;
    }
}