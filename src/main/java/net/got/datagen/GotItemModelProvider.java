package net.got.datagen;

import net.got.GotMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Generates item model JSONs for every GOT item.
 */
public class GotItemModelProvider extends ItemModelProvider {

    public GotItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, GotMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerWoodBlockItems();
        registerStoneBlockItems();
        registerOreBlockItems();
        registerToolItems();
        registerArmorItems();
        registerSimpleItems();
    }

    // ── Block items ───────────────────────────────────────────────────

    private void registerWoodBlockItems() {
        for (String w : GotBlockStateProvider.WOOD_TYPES) {
            blockItem(w + "_log");
            blockItem(w + "_wood");
            blockItem("stripped_" + w + "_log");
            blockItem(w + "_planks");
            blockItem(w + "_leaves");
            blockItem(w + "_sapling");
            blockItem(w + "_stairs");
            blockItem(w + "_slab");
            withExistingParent(w + "_fence", modLoc("block/" + w + "_fence_inventory"));
            blockItem(w + "_fence_gate");
            blockItem(w + "_pressure_plate");
            withExistingParent(w + "_button", modLoc("block/" + w + "_button_inventory"));
        }
    }

    private void registerStoneBlockItems() {
        for (String region : GotBlockStateProvider.STONE_REGIONS) {
            blockItem(region + "_pillar");
            withExistingParent(region + "_rock_button",
                    modLoc("block/" + region + "_rock_button_inventory"));
            blockItem(region + "_rock_pressure_plate");

            String[] patterns = {
                    "{r}_rock", "{r}_brick", "cracked_{r}_brick", "mossy_{r}_brick",
                    "{r}_cobblestone", "mossy_{r}_cobblestone", "polished_{r}_rock"
            };
            for (String pat : patterns) {
                String base = pat.replace("{r}", region);
                blockItem(base);
                blockItem(base + "_slab");
                blockItem(base + "_stairs");
                withExistingParent(base + "_wall", modLoc("block/" + base + "_wall_inventory"));
            }
        }
    }

    private void registerOreBlockItems() {
        for (String ore : new String[]{
                "copper_ore", "tin_ore", "amber_ore", "topaz_ore",
                "silver_ore", "amethyst_ore", "opal_ore", "ruby_ore",
                "sapphire_ore", "dragonglass", "valyrian_ore"}) {
            blockItem(ore);
        }
    }

    // ── Tools ─────────────────────────────────────────────────────────

    private void registerToolItems() {
        for (String tier : new String[]{"copper", "bronze"}) {
            for (String tool : new String[]{"sword", "pickaxe", "axe", "shovel", "hoe"}) {
                handheld(tier + "_" + tool);
            }
        }
    }

    // ── Armour ────────────────────────────────────────────────────────

    private void registerArmorItems() {
        for (String tier : new String[]{"copper", "bronze"}) {
            for (String piece : new String[]{"helmet", "chestplate", "leggings", "boots"}) {
                generatedItem(tier + "_" + piece);
            }
        }
    }

    // ── Simple items ──────────────────────────────────────────────────

    private void registerSimpleItems() {
        for (String gem : new String[]{"amber","amethyst","dragonglass_shard","opal","ruby","sapphire","topaz"}) {
            generatedItem(gem);
        }
        for (String raw : new String[]{"raw_copper","raw_silver","raw_tin","raw_valyrian_steel"}) {
            generatedItem(raw);
        }
        for (String ingot : new String[]{"copper_ingot","silver_ingot","tin_ingot","bronze_ingot","valyrian_steel_ingot"}) {
            generatedItem(ingot);
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────

    private void blockItem(String name) {
        withExistingParent(name, modLoc("block/" + name));
    }

    private void handheld(String name) {
        withExistingParent(name, ResourceLocation.withDefaultNamespace("item/handheld"))
                .texture("layer0", modLoc("item/" + name));
    }

    private void generatedItem(String name) {
        withExistingParent(name, ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", modLoc("item/" + name));
    }
}