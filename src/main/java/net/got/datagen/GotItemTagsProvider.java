package net.got.datagen;

import net.got.GotMod;
import net.got.init.GotModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;      // correct package in 1.21.3
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.concurrent.CompletableFuture;

/**
 * Generates item_tags JSONs for every GOT item.
 */
public class GotItemTagsProvider extends ItemTagsProvider {

    public GotItemTagsProvider(PackOutput output,
                               CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider) {
        super(output, lookupProvider, blockTagProvider, GotMod.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        copyWoodTags();
        copyOreTags();
        addMaterialTags();
    }

    private void copyWoodTags() {
        copy(BlockTags.LOGS,                   ItemTags.LOGS);
        copy(BlockTags.LOGS_THAT_BURN,         ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.LEAVES,                 ItemTags.LEAVES);
        copy(BlockTags.SAPLINGS,               ItemTags.SAPLINGS);
        copy(BlockTags.PLANKS,                 ItemTags.PLANKS);
        copy(BlockTags.WOODEN_STAIRS,          ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS,           ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES,          ItemTags.WOODEN_FENCES);
        copy(BlockTags.FENCE_GATES,            ItemTags.FENCE_GATES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_BUTTONS,         ItemTags.WOODEN_BUTTONS);
    }

    private void copyOreTags() {
        copy(Tags.Blocks.ORES,                 Tags.Items.ORES);
        copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
    }

    private void addMaterialTags() {
        tag(Tags.Items.GEMS).add(
                GotModItems.AMBER.get(), GotModItems.AMETHYST.get(),
                GotModItems.DRAGONGLASS_SHARD.get(), GotModItems.OPAL.get(),
                GotModItems.RUBY.get(), GotModItems.SAPPHIRE.get(),
                GotModItems.TOPAZ.get());

        addGemTag("amber",       GotModItems.AMBER);
        addGemTag("amethyst",    GotModItems.AMETHYST);
        addGemTag("dragonglass", GotModItems.DRAGONGLASS_SHARD);
        addGemTag("opal",        GotModItems.OPAL);
        addGemTag("ruby",        GotModItems.RUBY);
        addGemTag("sapphire",    GotModItems.SAPPHIRE);
        addGemTag("topaz",       GotModItems.TOPAZ);

        tag(Tags.Items.INGOTS).add(
                GotModItems.COPPER_INGOT.get(),  GotModItems.SILVER_INGOT.get(),
                GotModItems.TIN_INGOT.get(),     GotModItems.BRONZE_INGOT.get(),
                GotModItems.VALYRIAN_STEEL_INGOT.get());

        addIngotTag("copper",         GotModItems.COPPER_INGOT);
        addIngotTag("silver",         GotModItems.SILVER_INGOT);
        addIngotTag("tin",            GotModItems.TIN_INGOT);
        addIngotTag("bronze",         GotModItems.BRONZE_INGOT);
        addIngotTag("valyrian_steel", GotModItems.VALYRIAN_STEEL_INGOT);

        tag(Tags.Items.RAW_MATERIALS).add(
                GotModItems.RAW_COPPER.get(),        GotModItems.RAW_SILVER.get(),
                GotModItems.RAW_TIN.get(),           GotModItems.RAW_VALYRIAN_STEEL.get());

        addRawTag("copper",         GotModItems.RAW_COPPER);
        addRawTag("silver",         GotModItems.RAW_SILVER);
        addRawTag("tin",            GotModItems.RAW_TIN);
        addRawTag("valyrian_steel", GotModItems.RAW_VALYRIAN_STEEL);
    }

    // ── Helpers ───────────────────────────────────────────────────────

    private void addGemTag(String gem, DeferredItem<Item> item) {
        tag(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("neoforge", "gems/" + gem)))
                .add(item.get());
    }

    private void addIngotTag(String material, DeferredItem<Item> item) {
        tag(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("neoforge", "ingots/" + material)))
                .add(item.get());
    }

    private void addRawTag(String material, DeferredItem<Item> item) {
        tag(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("neoforge", "raw_materials/" + material)))
                .add(item.get());
    }
}