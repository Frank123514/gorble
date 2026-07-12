package net.got.skill;

/**
 * A single node in a skill's perk tree. Perks form a simple three-tier chain
 * per skill (see {@link GotSkillPerks}) - tier 2 requires tier 1 already
 * unlocked, tier 3 requires tier 2. Each costs {@link #pointCost()} perk
 * points, which the player earns automatically as that skill levels up
 * (one point per level - see {@link SkillXpService}).
 *
 * @param id               Unique registry key, e.g. {@code "combat_heavy_swing"}. Stable - persisted in save data.
 * @param skill            The skill this perk belongs to.
 * @param tier             Position in the chain (1, 2 or 3).
 * @param name              Display name, e.g. {@code "Heavy Swing"}.
 * @param description      Flavour + mechanical summary shown in the perk tree UI.
 * @param levelRequirement Minimum skill level required before this perk can be unlocked.
 * @param pointCost        Perk points spent to unlock (always 1 currently, kept configurable).
 * @param effectType       What kind of bonus this perk grants.
 * @param magnitude        Size of the bonus - meaning depends on {@link #effectType}.
 */
public record SkillPerk(
        String id,
        GotSkill skill,
        int tier,
        String name,
        String description,
        int levelRequirement,
        int pointCost,
        PerkEffectType effectType,
        double magnitude
) {}