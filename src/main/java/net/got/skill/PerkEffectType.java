package net.got.skill;

/**
 * What a {@link SkillPerk} actually does mechanically. {@link SkillPerk#magnitude()}
 * is interpreted differently depending on this type - see each constant's doc.
 * Applied in {@link SkillPerkEffects}.
 */
public enum PerkEffectType {
    /** magnitude = extra melee damage as a fraction, e.g. 0.10 = +10%. Applied as an ATTACK_DAMAGE attribute modifier. */
    MELEE_DAMAGE_MULT,
    /** magnitude = extra arrow/bolt damage as a fraction, e.g. 0.10 = +10%. Applied directly to fired projectiles. */
    RANGED_DAMAGE_MULT,
    /** magnitude = flat bonus armor points. Applied as an ARMOR attribute modifier. */
    ARMOR_BONUS,
    /** magnitude = extra block-break speed as a fraction, e.g. 0.10 = +10% faster. */
    BREAK_SPEED_MULT,
    /** magnitude = chance in [0,1] of an extra drop when harvesting/mining/chopping/cooking. */
    BONUS_DROP_CHANCE,
    /** magnitude = extra XP gained on that skill's own actions, as a fraction. */
    XP_GAIN_MULT,
    /** magnitude = extra +/- half-width added to the Smithing Anvil's hit-timing sweet zone. */
    SMITHING_ZONE_BONUS,
    /** magnitude = chance in [0,1] of an extra item when a smithing hammer strike completes a piece. */
    SMITHING_BONUS_OUTPUT_CHANCE
}