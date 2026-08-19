package net.francis.got.skill;

public record SkillPerk(
        String id,
        Skill skill,
        int tier,
        String name,
        String description,
        int levelRequirement,
        int pointCost,
        PerkEffectType effectType,
        double magnitude
) {}