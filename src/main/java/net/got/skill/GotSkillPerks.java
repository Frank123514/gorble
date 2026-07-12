package net.got.skill;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Static registry of every {@link SkillPerk}, three per {@link GotSkill}
 * arranged as a simple linear chain (tier 1 → 2 → 3, each gated behind the
 * previous tier plus a level requirement). Not data-driven - like
 * {@link net.got.faction.GotFactions}, these are baked in at class-init time.
 */
public final class GotSkillPerks {

    private static final Map<GotSkill, List<SkillPerk>> BY_SKILL = new LinkedHashMap<>();
    private static final Map<String, SkillPerk> BY_ID = new LinkedHashMap<>();

    private static void def(GotSkill skill, int tier, String id, String name, String description,
                            int levelReq, PerkEffectType type, double magnitude) {
        SkillPerk perk = new SkillPerk(id, skill, tier, name, description, levelReq, 1, type, magnitude);
        BY_SKILL.computeIfAbsent(skill, s -> new java.util.ArrayList<>()).add(perk);
        BY_ID.put(id, perk);
    }

    static {
        // ── Combat ───────────────────────────────────────────────────────────
        def(GotSkill.COMBAT, 1, "combat_heavy_swing", "Heavy Swing",
                "Put more weight behind every strike. +10% melee damage.",
                15, PerkEffectType.MELEE_DAMAGE_MULT, 0.10);
        def(GotSkill.COMBAT, 2, "combat_brute_force", "Brute Force",
                "Years at the whetstone pay off. +10% melee damage.",
                40, PerkEffectType.MELEE_DAMAGE_MULT, 0.10);
        def(GotSkill.COMBAT, 3, "combat_killing_blow", "Killing Blow",
                "A master's final stroke. +20% melee damage.",
                75, PerkEffectType.MELEE_DAMAGE_MULT, 0.20);

        // ── Archery ──────────────────────────────────────────────────────────
        def(GotSkill.ARCHERY, 1, "archery_steady_aim", "Steady Aim",
                "A calmer hand on the string. +10% arrow damage.",
                15, PerkEffectType.RANGED_DAMAGE_MULT, 0.10);
        def(GotSkill.ARCHERY, 2, "archery_eagle_eye", "Eagle Eye",
                "You judge the wind before it moves. +10% arrow damage.",
                40, PerkEffectType.RANGED_DAMAGE_MULT, 0.10);
        def(GotSkill.ARCHERY, 3, "archery_piercing_shot", "Piercing Shot",
                "Loosed with enough force to punch through mail. +15% arrow damage.",
                75, PerkEffectType.RANGED_DAMAGE_MULT, 0.15);

        // ── Defense ──────────────────────────────────────────────────────────
        def(GotSkill.DEFENSE, 1, "defense_thick_skin", "Thick Skin",
                "Old wounds toughen you. +2 armor.",
                15, PerkEffectType.ARMOR_BONUS, 2.0);
        def(GotSkill.DEFENSE, 2, "defense_iron_will", "Iron Will",
                "You've learned to ride out a blow. +2 armor.",
                40, PerkEffectType.ARMOR_BONUS, 2.0);
        def(GotSkill.DEFENSE, 3, "defense_unbreakable", "Unbreakable",
                "Precious few things can put you down now. +4 armor.",
                75, PerkEffectType.ARMOR_BONUS, 4.0);

        // ── Mining ───────────────────────────────────────────────────────────
        def(GotSkill.MINING, 1, "mining_quick_pick", "Quick Pick",
                "A cleaner swing through stone. +10% mining speed.",
                15, PerkEffectType.BREAK_SPEED_MULT, 0.10);
        def(GotSkill.MINING, 2, "mining_deep_delve", "Deep Delve",
                "Countless hours below ground. +15% mining speed.",
                40, PerkEffectType.BREAK_SPEED_MULT, 0.15);
        def(GotSkill.MINING, 3, "mining_prospectors_luck", "Prospector's Luck",
                "An eye for the richest vein. 15% chance of a bonus drop when mining.",
                75, PerkEffectType.BONUS_DROP_CHANCE, 0.15);

        // ── Woodcutting ──────────────────────────────────────────────────────
        def(GotSkill.WOODCUTTING, 1, "woodcutting_sharp_axe", "Sharp Axe",
                "You know just where to bite the blade in. +10% chopping speed.",
                15, PerkEffectType.BREAK_SPEED_MULT, 0.10);
        def(GotSkill.WOODCUTTING, 2, "woodcutting_timber", "Timber!",
                "Trees fall faster and cleaner under your axe. +15% chopping speed.",
                40, PerkEffectType.BREAK_SPEED_MULT, 0.15);
        def(GotSkill.WOODCUTTING, 3, "woodcutting_bountiful_harvest", "Bountiful Harvest",
                "Every trunk yields a little more than it should. 15% chance of a bonus log.",
                75, PerkEffectType.BONUS_DROP_CHANCE, 0.15);

        // ── Farming ──────────────────────────────────────────────────────────
        def(GotSkill.FARMING, 1, "farming_green_thumb", "Green Thumb",
                "The land favours you. 10% chance of a bonus crop when harvesting.",
                15, PerkEffectType.BONUS_DROP_CHANCE, 0.10);
        def(GotSkill.FARMING, 2, "farming_fertile_hands", "Fertile Hands",
                "Your fields grow richer by the season. 15% chance of a bonus crop.",
                40, PerkEffectType.BONUS_DROP_CHANCE, 0.15);
        def(GotSkill.FARMING, 3, "farming_master_farmer", "Master Farmer",
                "A lifetime's knowledge of soil and season. +25% Farming XP gained.",
                75, PerkEffectType.XP_GAIN_MULT, 0.25);

        // ── Smithing ─────────────────────────────────────────────────────────
        def(GotSkill.SMITHING, 1, "smithing_steady_hand", "Steady Hand",
                "A more forgiving rhythm at the anvil. Widens the hammer's timing window.",
                15, PerkEffectType.SMITHING_ZONE_BONUS, 2.0);
        def(GotSkill.SMITHING, 2, "smithing_practiced_hands", "Practiced Hands",
                "The marker's dance becomes second nature. Widens the timing window further.",
                40, PerkEffectType.SMITHING_ZONE_BONUS, 2.0);
        def(GotSkill.SMITHING, 3, "smithing_master_smith", "Master Smith",
                "Your strikes waste nothing. 20% chance of an extra piece when a strike completes.",
                75, PerkEffectType.SMITHING_BONUS_OUTPUT_CHANCE, 0.20);

        // ── Cooking ──────────────────────────────────────────────────────────
        def(GotSkill.COOKING, 1, "cooking_efficient_cook", "Efficient Cook",
                "You waste less over the fire. 10% chance of an extra dish when cooking finishes.",
                15, PerkEffectType.BONUS_DROP_CHANCE, 0.10);
        def(GotSkill.COOKING, 2, "cooking_seasoned_chef", "Seasoned Chef",
                "A practiced hand with the spit and pot. 15% chance of an extra dish.",
                40, PerkEffectType.BONUS_DROP_CHANCE, 0.15);
        def(GotSkill.COOKING, 3, "cooking_master_chef", "Master Chef",
                "Renowned even in a lord's kitchens. +25% Cooking XP gained.",
                75, PerkEffectType.XP_GAIN_MULT, 0.25);
    }

    /** Returns the ordered (tier 1 → 3) perk chain for a skill. Never null. */
    public static List<SkillPerk> forSkill(GotSkill skill) {
        return BY_SKILL.getOrDefault(skill, List.of());
    }

    /** Looks up a perk by its id, or {@code null} if unknown. */
    public static SkillPerk byId(String id) {
        return BY_ID.get(id);
    }

    private GotSkillPerks() {}
}