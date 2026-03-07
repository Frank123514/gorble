package net.got.datagen;

import net.got.GotMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

/**
 * Generates the en_us.json language file for all GOT blocks and items.
 * Run with: ./gradlew runData
 */
public class GotLanguageProvider extends LanguageProvider {

    public GotLanguageProvider(PackOutput output) {
        super(output, GotMod.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // ── Creative tabs ─────────────────────────────────────────────
        add("itemGroup.got.got_building",  "GoT: Building Blocks");
        add("itemGroup.got.got_nature",    "GoT: Natural Blocks");
        add("itemGroup.got.got_materials", "GoT: Materials");

        // ── Wood types ────────────────────────────────────────────────
        addWood("weirwood",         "Weirwood");
        addWood("aspen",            "Aspen");
        addWood("alder",            "Alder");
        addWood("pine",             "Pine");
        addWood("fir",              "Fir");
        addWood("sentinal",         "Sentinal");
        addWood("ironwood",         "Ironwood");
        addWood("beech",            "Beech");
        addWood("soldier_pine",     "Soldier Pine");
        addWood("ash",              "Ash");
        addWood("hawthorn",         "Hawthorn");
        addWood("blackbark",        "Blackbark");
        addWood("bloodwood",        "Bloodwood");
        addWood("blue_mahoe",       "Blue Mahoe");
        addWood("cottonwood",       "Cottonwood");
        addWood("black_cottonwood", "Black Cottonwood");
        addWood("cinnamon",         "Cinnamon");
        addWood("clove",            "Clove");
        addWood("ebony",            "Ebony");
        addWood("elm",              "Elm");
        addWood("cedar",            "Cedar");
        addWood("apple",            "Apple");
        addWood("goldenheart",      "Goldenheart");
        addWood("linden",           "Linden");
        addWood("mahogany",         "Mahogany");
        addWood("maple",            "Maple");
        addWood("myrrh",            "Myrrh");
        addWood("redwood",          "Redwood");
        addWood("chestnut",         "Chestnut");
        addWood("willow",           "Willow");
        addWood("wormtree",         "Wormtree");

        // ── Stone regions ─────────────────────────────────────────────

        // ── Natural stone types ───────────────────────────────────────────
        addStoneRegion("basalt",            "Basalt");
        addStoneRegion("granite",           "Granite");
        addStoneRegion("limestone",         "Limestone");
        addStoneRegion("sandstone",         "Sandstone");
        addStoneRegion("red_sandstone",     "Red Sandstone");
        addStoneRegion("slate",             "Slate");
        addStoneRegion("oily_black",  "Oily Black Stone");
        addStoneRegion("fused_black", "Fused Black Stone");
        addStoneRegion("marble",            "Marble");

        // ── Ores & raw materials ──────────────────────────────────────
        block("copper_ore",           "Copper Ore");
        block("tin_ore",              "Tin Ore");
        block("silver_ore",           "Silver Ore");
        block("amber_ore",            "Amber Ore");
        block("topaz_ore",            "Topaz Ore");
        block("amethyst_ore",         "Amethyst Ore");
        block("opal_ore",             "Opal Ore");
        block("ruby_ore",             "Ruby Ore");
        block("sapphire_ore",         "Sapphire Ore");
        block("dragonglass",          "Dragonglass");
        block("valyrian_ore",         "Valyrian Steel Ore");

        item("raw_copper",            "Raw Copper");
        item("raw_tin",               "Raw Tin");
        item("raw_silver",            "Raw Silver");
        item("raw_valyrian_steel",    "Raw Valyrian Steel");
        item("copper_ingot",          "Copper Ingot");
        item("tin_ingot",             "Tin Ingot");
        item("silver_ingot",          "Silver Ingot");
        item("bronze_ingot",          "Bronze Ingot");
        item("valyrian_steel_ingot",  "Valyrian Steel Ingot");

        item("amber",                 "Amber");
        item("amethyst",              "Amethyst");
        item("dragonglass_shard",     "Dragonglass Shard");
        item("opal",                  "Opal");
        item("ruby",                  "Ruby");
        item("sapphire",              "Sapphire");
        item("topaz",                 "Topaz");

        // ── Tools & armour ────────────────────────────────────────────
        for (String tier : new String[]{"copper", "bronze"}) {
            String t = capitalize(tier);
            item(tier + "_sword",     t + " Sword");
            item(tier + "_pickaxe",   t + " Pickaxe");
            item(tier + "_axe",       t + " Axe");
            item(tier + "_shovel",    t + " Shovel");
            item(tier + "_hoe",       t + " Hoe");
            item(tier + "_helmet",    t + " Helmet");
            item(tier + "_chestplate",t + " Chestplate");
            item(tier + "_leggings",  t + " Leggings");
            item(tier + "_boots",     t + " Boots");
        }
    }

    // ── Wood helper ───────────────────────────────────────────────────

    private void addWood(String id, String name) {
        block(id + "_log",              name + " Log");
        block(id + "_wood",             name + " Wood");
        block("stripped_" + id + "_log",  "Stripped " + name + " Log");
        block("stripped_" + id + "_wood", "Stripped " + name + " Wood");
        block(id + "_planks",           name + " Planks");
        block(id + "_leaves",           name + " Leaves");
        block(id + "_sapling",          name + " Sapling");
        block(id + "_stairs",           name + " Stairs");
        block(id + "_slab",             name + " Slab");
        block(id + "_fence",            name + " Fence");
        block(id + "_fence_gate",       name + " Fence Gate");
        block(id + "_pressure_plate",   name + " Pressure Plate");
        block(id + "_button",           name + " Button");
        item(id + "_door",             name + " Door");
        item(id + "_trapdoor",         name + " Trapdoor");
        item(id + "_branch",           name + " Branch");
        item(id + "_sign",             name + " Sign");
        item(id + "_wall_sign",        name + " Wall Sign");
        item(id + "_hanging_sign",     name + " Hanging Sign");
        item(id + "_wall_hanging_sign",name + " Wall Hanging Sign");
        item(id + "_boat",              name + " Boat");
        item(id + "_chest_boat",        name + " Boat with Chest");
    }

    // ── Stone region helper ───────────────────────────────────────────

    private void addStoneRegion(String id, String name) {
        block(id + "_pillar",              name + " Pillar");
        block(id + "_rock",               name + " Rock");
        block(id + "_brick",              name + " Brick");
        block("cracked_" + id + "_brick", "Cracked " + name + " Brick");
        block("mossy_" + id + "_brick",   "Mossy " + name + " Brick");
        block(id + "_cobblestone",        name + " Cobblestone");
        block("mossy_" + id + "_cobblestone", "Mossy " + name + " Cobblestone");
        block("smooth_" + id + "_rock", "Smooth " + name + " Rock");
        // Slabs, stairs, walls
        for (String base : new String[]{
                id + "_rock", id + "_brick",
                "cracked_" + id + "_brick", "mossy_" + id + "_brick",
                id + "_cobblestone", "mossy_" + id + "_cobblestone",
                "smooth_" + id + "_rock"}) {
            block(base + "_slab",   toTitle(base) + " Slab");
            block(base + "_stairs", toTitle(base) + " Stairs");
            block(base + "_wall",   toTitle(base) + " Wall");
        }
        block(id + "_rock_button",         name + " Rock Button");
        block(id + "_rock_pressure_plate", name + " Rock Pressure Plate");
    }

    // ── Low-level helpers ─────────────────────────────────────────────

    /** Adds block.got.{id} and item.got.{id} with the same label. */
    private void block(String id, String name) {
        add("block.got." + id, name);
        add("item.got." + id, name);
    }

    private void item(String id, String name) {
        add("item.got." + id, name);
    }

    private static String capitalize(String s) {
        return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private static String toTitle(String snake) {
        StringBuilder sb = new StringBuilder();
        for (String part : snake.split("_")) {
            if (!sb.isEmpty()) sb.append(' ');
            sb.append(capitalize(part));
        }
        return sb.toString();
    }
}