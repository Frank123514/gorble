package net.got.datagen;

import net.got.GotMod;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Objects;

/**
 * Generates item model JSONs for every GOT item.
 *
 * NeoForge 21.4 (MC 1.21.4): ItemModelProvider from
 * net.neoforged.neoforge.client.model.generators is gone.
 * We now extend ModelProvider and use ItemModelGenerators.
 *
 * NOTE: Block-item models for blocks that have an associated BlockItem are
 * generated automatically by ModelProvider using the block model as parent,
 * so most block items do NOT need to be listed here.  Only items that need
 * a custom model (flat, handheld, doors, etc.) are registered explicitly.
 */
public class GotItemModelProvider extends ModelProvider {

    public GotItemModelProvider(PackOutput output) {
        super(output, GotMod.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators g) {
        registerWoodItems(g);
        registerToolItems(g);
        registerArmorItems(g);
        registerSimpleItems(g);
    }

    // ── Wood items that need non-standard models ───────────────────────

    private void registerWoodItems(ItemModelGenerators g) {
        for (String w : GotBlockStateProvider.WOOD_TYPES) {
            // Doors: flat item/generated with textures/item/{w}_door.png
            g.generateFlatItem(item(w + "_door"), ModelTemplates.FLAT_ITEM);

            // Branches, signs, boats — simple flat items
            g.generateFlatItem(item(w + "_branch"),           ModelTemplates.FLAT_ITEM);
            g.generateFlatItem(item(w + "_sign"),             ModelTemplates.FLAT_ITEM);
            g.generateFlatItem(item(w + "_hanging_sign"),     ModelTemplates.FLAT_ITEM);
            g.generateFlatItem(item(w + "_boat"),             ModelTemplates.FLAT_ITEM);
            g.generateFlatItem(item(w + "_chest_boat"),       ModelTemplates.FLAT_ITEM);
        }
    }

    // ── Tools ─────────────────────────────────────────────────────────

    private void registerToolItems(ItemModelGenerators g) {
        for (String tier : new String[]{"copper", "bronze"}) {
            for (String tool : new String[]{"sword", "pickaxe", "axe", "shovel", "hoe"}) {
                g.generateFlatItem(item(tier + "_" + tool), ModelTemplates.FLAT_HANDHELD_ITEM);
            }
        }
    }

    // ── Armour ────────────────────────────────────────────────────────

    private void registerArmorItems(ItemModelGenerators g) {
        for (String tier : new String[]{"copper", "bronze"}) {
            for (String piece : new String[]{"helmet", "chestplate", "leggings", "boots"}) {
                g.generateFlatItem(item(tier + "_" + piece), ModelTemplates.FLAT_ITEM);
            }
        }
    }

    // ── Simple items ──────────────────────────────────────────────────

    private void registerSimpleItems(ItemModelGenerators g) {
        for (String gem : new String[]{
                "amber", "amethyst", "dragonglass_shard", "opal", "ruby", "sapphire", "topaz"}) {
            g.generateFlatItem(item(gem), ModelTemplates.FLAT_ITEM);
        }
        for (String raw : new String[]{
                "raw_copper", "raw_silver", "raw_tin", "raw_valyrian_steel"}) {
            g.generateFlatItem(item(raw), ModelTemplates.FLAT_ITEM);
        }
        for (String ingot : new String[]{
                "copper_ingot", "silver_ingot", "tin_ingot", "bronze_ingot", "valyrian_steel_ingot"}) {
            g.generateFlatItem(item(ingot), ModelTemplates.FLAT_ITEM);
        }
    }

    // ── Helper ────────────────────────────────────────────────────────

    private Item item(String name) {
        return Objects.requireNonNull(
                BuiltInRegistries.ITEM.getValue(
                        ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name)),
                "Unknown GOT item: " + name);
    }
}