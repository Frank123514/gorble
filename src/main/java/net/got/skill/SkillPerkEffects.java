package net.got.skill;

import net.got.GotMod;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class SkillPerkEffects {

    private static final Identifier COMBAT_DAMAGE_MODIFIER =
            GotMod.id("skill_combat_melee_damage");
    private static final Identifier DEFENSE_ARMOR_MODIFIER =
            GotMod.id("skill_defense_armor_bonus");

    public static double sumEffect(ServerPlayer player, Skill skill, PerkEffectType type) {
        double total = 0;
        for (SkillPerk perk : SkillPerks.forSkill(skill)) {
            if (perk.effectType() == type && PlayerSkillState.hasPerk(player, perk.id())) {
                total += perk.magnitude();
            }
        }
        return total;
    }

    public static float rangedDamageMultiplier(ServerPlayer player) {
        return (float) (1.0 + sumEffect(player, Skill.ARCHERY, PerkEffectType.RANGED_DAMAGE_MULT));
    }

    public static float breakSpeedMultiplier(ServerPlayer player, Skill skill) {
        return (float) (1.0 + sumEffect(player, skill, PerkEffectType.BREAK_SPEED_MULT));
    }

    public static float bonusDropChance(ServerPlayer player, Skill skill) {
        return (float) Math.min(1.0, sumEffect(player, skill, PerkEffectType.BONUS_DROP_CHANCE));
    }

    public static float xpGainMultiplier(ServerPlayer player, Skill skill) {
        return (float) (1.0 + sumEffect(player, skill, PerkEffectType.XP_GAIN_MULT));
    }

    public static int smithingZoneBonus(ServerPlayer player) {
        return (int) Math.round(sumEffect(player, Skill.SMITHING, PerkEffectType.SMITHING_ZONE_BONUS));
    }

    public static float smithingBonusOutputChance(ServerPlayer player) {
        return (float) Math.min(1.0, sumEffect(player, Skill.SMITHING, PerkEffectType.SMITHING_BONUS_OUTPUT_CHANCE));
    }

    public static void applyAttributeModifiers(ServerPlayer player) {
        applyPercentModifier(player, Attributes.ATTACK_DAMAGE, COMBAT_DAMAGE_MODIFIER,
                sumEffect(player, Skill.COMBAT, PerkEffectType.MELEE_DAMAGE_MULT));
        applyFlatModifier(player, Attributes.ARMOR, DEFENSE_ARMOR_MODIFIER,
                sumEffect(player, Skill.DEFENSE, PerkEffectType.ARMOR_BONUS));
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