package net.got.skill;

import net.got.GotMod;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Reads {@link PlayerSkillState}'s unlocked perks and turns them into actual
 * gameplay numbers - damage multipliers, break-speed bonuses, drop chances,
 * etc. Two flavours of effect live here:
 *
 * <ul>
 *   <li><b>Attribute-based</b> (melee damage, armor) - applied as named
 *       {@link AttributeModifier}s via {@link #applyAttributeModifiers}, which
 *       must be (re-)called on login, respawn, and whenever a perk unlocks,
 *       since transient modifiers are not saved to disk.</li>
 *   <li><b>Read-on-demand</b> (ranged damage, break speed, drop chance, XP
 *       gain, smithing) - queried directly at the point of use since there's
 *       no matching vanilla attribute to hang them on.</li>
 * </ul>
 */
public final class SkillPerkEffects {

    private static final Identifier COMBAT_DAMAGE_MODIFIER =
            GotMod.id("skill_combat_melee_damage");
    private static final Identifier DEFENSE_ARMOR_MODIFIER =
            GotMod.id("skill_defense_armor_bonus");

    /** Sums the magnitude of every unlocked perk of {@code type} within {@code skill}. */
    public static double sumEffect(ServerPlayer player, GotSkill skill, PerkEffectType type) {
        double total = 0;
        for (SkillPerk perk : GotSkillPerks.forSkill(skill)) {
            if (perk.effectType() == type && PlayerSkillState.hasPerk(player, perk.id())) {
                total += perk.magnitude();
            }
        }
        return total;
    }

    /** Multiplier to apply to arrow/bolt base damage, e.g. {@code 1.15} for +15%. */
    public static float rangedDamageMultiplier(ServerPlayer player) {
        return (float) (1.0 + sumEffect(player, GotSkill.ARCHERY, PerkEffectType.RANGED_DAMAGE_MULT));
    }

    /** Multiplier to apply to block-break speed for skills that grant it (Mining, Woodcutting). */
    public static float breakSpeedMultiplier(ServerPlayer player, GotSkill skill) {
        return (float) (1.0 + sumEffect(player, skill, PerkEffectType.BREAK_SPEED_MULT));
    }

    /** Chance in [0,1] of a bonus drop/dish for skills that grant it. */
    public static float bonusDropChance(ServerPlayer player, GotSkill skill) {
        return (float) Math.min(1.0, sumEffect(player, skill, PerkEffectType.BONUS_DROP_CHANCE));
    }

    /** Multiplier applied to XP gained in a given skill, e.g. {@code 1.25} for +25%. */
    public static float xpGainMultiplier(ServerPlayer player, GotSkill skill) {
        return (float) (1.0 + sumEffect(player, skill, PerkEffectType.XP_GAIN_MULT));
    }

    /** Extra +/- half-width to add to the Smithing Anvil's hit-timing sweet zone. */
    public static int smithingZoneBonus(ServerPlayer player) {
        return (int) Math.round(sumEffect(player, GotSkill.SMITHING, PerkEffectType.SMITHING_ZONE_BONUS));
    }

    /** Chance in [0,1] of an extra crafted item when a smithing hammer strike completes a piece. */
    public static float smithingBonusOutputChance(ServerPlayer player) {
        return (float) Math.min(1.0, sumEffect(player, GotSkill.SMITHING, PerkEffectType.SMITHING_BONUS_OUTPUT_CHANCE));
    }

    // ── Attribute-based effects ─────────────────────────────────────────────

    /**
     * (Re-)applies the Combat melee-damage and Defense armor attribute
     * modifiers from currently-unlocked perks. Transient modifiers are not
     * persisted, so this must run on login, on respawn/clone, and again
     * immediately after any perk unlock.
     */
    public static void applyAttributeModifiers(ServerPlayer player) {
        applyPercentModifier(player, Attributes.ATTACK_DAMAGE, COMBAT_DAMAGE_MODIFIER,
                sumEffect(player, GotSkill.COMBAT, PerkEffectType.MELEE_DAMAGE_MULT));
        applyFlatModifier(player, Attributes.ARMOR, DEFENSE_ARMOR_MODIFIER,
                sumEffect(player, GotSkill.DEFENSE, PerkEffectType.ARMOR_BONUS));
    }

    private static void applyPercentModifier(ServerPlayer player, Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
                                             Identifier id, double fraction) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) return;
        instance.removeModifier(id);
        if (fraction > 0) {
            instance.addTransientModifier(new AttributeModifier(
                    id, fraction, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    private static void applyFlatModifier(ServerPlayer player, Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
                                          Identifier id, double flat) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) return;
        instance.removeModifier(id);
        if (flat > 0) {
            instance.addTransientModifier(new AttributeModifier(
                    id, flat, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private SkillPerkEffects() {}
}