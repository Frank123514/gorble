package net.got.datagen;

import net.got.GotMod;
import net.got.init.GotModItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.Objects;

/**
 * Generates recipe JSONs for all GOT blocks and items.
 *
 * In NeoForge 1.21.3 RecipeProvider:
 *  - Constructor takes (HolderLookup.Provider registries, RecipeOutput output)
 *  - Abstract method is buildRecipes() with no parameters
 *  - ShapedRecipeBuilder.shaped / ShapelessRecipeBuilder.shapeless require HolderGetter<Item>
 *  - .save() requires ResourceKey<Recipe<?>> (use recipeKey() helper below)
 *  - Registered via method reference:  GotRecipeProvider::new
 */
public class GotRecipeProvider extends RecipeProvider {

    // In 1.21.3 RecipeProvider these are protected fields we inherit:
    //   protected final HolderLookup.Provider registries;
    //   protected final RecipeOutput output;

    public GotRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> items = this.registries.lookupOrThrow(Registries.ITEM);
        buildWoodRecipes(items);
        buildSmeltingRecipes(items);
        buildAlloyRecipes(items);
        buildToolRecipes(items);
        buildArmourRecipes(items);
    }

    // ── Wood crafting ─────────────────────────────────────────────────

    private void buildWoodRecipes(HolderGetter<Item> items) {
        for (String w : GotBlockStateProvider.WOOD_TYPES) {
            Item log         = item(w + "_log");
            Item wood        = item(w + "_wood");
            Item strippedLog = item("stripped_" + w + "_log");
            Item strippedWood= item("stripped_" + w + "_wood");
            Item planks      = item(w + "_planks");
            Item slab        = item(w + "_slab");
            Item stick       = Items.STICK;

            // ── Regular log/wood → planks ─────────────────────────────
            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, planks, 4)
                    .requires(log)
                    .unlockedBy("has_log", has(log))
                    .save(this.output, rk(w + "_planks_from_log"));

            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, planks, 4)
                    .requires(wood)
                    .unlockedBy("has_wood", has(wood))
                    .save(this.output, rk(w + "_planks_from_wood"));

            // 4 logs → 3 wood (bark block)
            ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, item(w + "_wood"), 3)
                    .define('L', log)
                    .pattern("LL")
                    .pattern("LL")
                    .unlockedBy("has_log", has(log))
                    .save(this.output, rk(w + "_wood"));

            // ── Stripped log/wood → planks ────────────────────────────
            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, planks, 4)
                    .requires(strippedLog)
                    .unlockedBy("has_stripped_log", has(strippedLog))
                    .save(this.output, rk(w + "_planks_from_stripped_log"));

            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, planks, 4)
                    .requires(strippedWood)
                    .unlockedBy("has_stripped_wood", has(strippedWood))
                    .save(this.output, rk(w + "_planks_from_stripped_wood"));

            // 4 stripped logs → 3 stripped wood
            ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, item("stripped_" + w + "_wood"), 3)
                    .define('L', strippedLog)
                    .pattern("LL")
                    .pattern("LL")
                    .unlockedBy("has_stripped_log", has(strippedLog))
                    .save(this.output, rk("stripped_" + w + "_wood"));

            // ── Doors & trapdoors ─────────────────────────────────────
            // 6 planks → 3 doors
            ShapedRecipeBuilder.shaped(items, RecipeCategory.REDSTONE, item(w + "_door"), 3)
                    .define('P', planks)
                    .pattern("PP")
                    .pattern("PP")
                    .pattern("PP")
                    .unlockedBy("has_planks", has(planks))
                    .save(this.output, rk(w + "_door"));

            // 6 planks → 2 trapdoors
            ShapedRecipeBuilder.shaped(items, RecipeCategory.REDSTONE, item(w + "_trapdoor"), 2)
                    .define('P', planks)
                    .pattern("PPP")
                    .pattern("PPP")
                    .unlockedBy("has_planks", has(planks))
                    .save(this.output, rk(w + "_trapdoor"));

            // 6 planks → 4 stairs
            ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, item(w + "_stairs"), 4)
                    .define('P', planks)
                    .pattern("P  ")
                    .pattern("PP ")
                    .pattern("PPP")
                    .unlockedBy("has_planks", has(planks))
                    .save(this.output, rk(w + "_stairs"));

            // 3 planks → 6 slabs
            ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, slab, 6)
                    .define('P', planks)
                    .pattern("PPP")
                    .unlockedBy("has_planks", has(planks))
                    .save(this.output, rk(w + "_slab"));

            // Fence: 2 planks + 4 sticks → 3
            ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, item(w + "_fence"), 3)
                    .define('P', planks).define('S', stick)
                    .pattern("PSP").pattern("PSP")
                    .unlockedBy("has_planks", has(planks))
                    .save(this.output, rk(w + "_fence"));

            // Fence gate: 2 planks + 4 sticks → 1
            ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, item(w + "_fence_gate"), 1)
                    .define('P', planks).define('S', stick)
                    .pattern("SPS").pattern("SPS")
                    .unlockedBy("has_planks", has(planks))
                    .save(this.output, rk(w + "_fence_gate"));

            // Pressure plate: 2 planks → 1
            ShapedRecipeBuilder.shaped(items, RecipeCategory.REDSTONE, item(w + "_pressure_plate"), 1)
                    .define('P', planks)
                    .pattern("PP")
                    .unlockedBy("has_planks", has(planks))
                    .save(this.output, rk(w + "_pressure_plate"));

            // Button: 1 plank → 1
            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.REDSTONE, item(w + "_button"), 1)
                    .requires(planks)
                    .unlockedBy("has_planks", has(planks))
                    .save(this.output, rk(w + "_button"));
        }
    }

    // ── Smelting / blasting ───────────────────────────────────────────

    private void buildSmeltingRecipes(HolderGetter<Item> items) {
        // Ore → raw material
        smeltAndBlast(items, item("copper_ore"),    GotModItems.RAW_COPPER.get(),          0.7f, "copper_ore");
        smeltAndBlast(items, item("tin_ore"),       GotModItems.RAW_TIN.get(),             0.7f, "tin_ore");
        smeltAndBlast(items, item("silver_ore"),    GotModItems.RAW_SILVER.get(),          0.7f, "silver_ore");
        smeltAndBlast(items, item("valyrian_ore"),  GotModItems.RAW_VALYRIAN_STEEL.get(),  1.0f, "valyrian_ore");

        // Gem ores → gem
        smeltAndBlast(items, item("amber_ore"),    GotModItems.AMBER.get(),             0.7f, "amber_ore");
        smeltAndBlast(items, item("topaz_ore"),    GotModItems.TOPAZ.get(),             0.7f, "topaz_ore");
        smeltAndBlast(items, item("amethyst_ore"), GotModItems.AMETHYST.get(),          0.7f, "amethyst_ore");
        smeltAndBlast(items, item("opal_ore"),     GotModItems.OPAL.get(),              0.7f, "opal_ore");
        smeltAndBlast(items, item("ruby_ore"),     GotModItems.RUBY.get(),              0.7f, "ruby_ore");
        smeltAndBlast(items, item("sapphire_ore"), GotModItems.SAPPHIRE.get(),          0.7f, "sapphire_ore");
        smeltAndBlast(items, item("dragonglass"),  GotModItems.DRAGONGLASS_SHARD.get(), 0.7f, "dragonglass_ore");

        // Raw → ingot
        smeltAndBlast(items, GotModItems.RAW_COPPER.get(),         GotModItems.COPPER_INGOT.get(),          0.7f, "raw_copper");
        smeltAndBlast(items, GotModItems.RAW_TIN.get(),            GotModItems.TIN_INGOT.get(),             0.7f, "raw_tin");
        smeltAndBlast(items, GotModItems.RAW_SILVER.get(),         GotModItems.SILVER_INGOT.get(),          0.7f, "raw_silver");
        smeltAndBlast(items, GotModItems.RAW_VALYRIAN_STEEL.get(), GotModItems.VALYRIAN_STEEL_INGOT.get(), 1.0f, "raw_valyrian_steel");
    }

    private void smeltAndBlast(HolderGetter<Item> items, ItemLike ingredient, ItemLike result,
                               float xp, String name) {
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, xp, 200)
                .unlockedBy("has_ingredient", has(ingredient))
                .save(this.output, rk("smelting/" + name));

        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(ingredient), RecipeCategory.MISC, result, xp, 100)
                .unlockedBy("has_ingredient", has(ingredient))
                .save(this.output, rk("blasting/" + name));
    }

    // ── Alloying ──────────────────────────────────────────────────────

    private void buildAlloyRecipes(HolderGetter<Item> items) {
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, GotModItems.BRONZE_INGOT.get(), 2)
                .requires(GotModItems.COPPER_INGOT.get())
                .requires(GotModItems.TIN_INGOT.get())
                .unlockedBy("has_copper", has(GotModItems.COPPER_INGOT.get()))
                .save(this.output, rk("bronze_ingot_from_alloy"));
    }

    // ── Tools ─────────────────────────────────────────────────────────

    private void buildToolRecipes(HolderGetter<Item> items) {
        buildTools(items, "copper", GotModItems.COPPER_INGOT.get());
        buildTools(items, "bronze", GotModItems.BRONZE_INGOT.get());
    }

    private void buildTools(HolderGetter<Item> items, String tier, Item ingot) {
        Item s = Items.STICK;
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, item(tier + "_sword"), 1)
                .define('I', ingot).define('S', s)
                .pattern("I").pattern("I").pattern("S")
                .unlockedBy("has_ingot", has(ingot))
                .save(this.output, rk(tier + "_sword"));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, item(tier + "_pickaxe"), 1)
                .define('I', ingot).define('S', s)
                .pattern("III").pattern(" S ").pattern(" S ")
                .unlockedBy("has_ingot", has(ingot))
                .save(this.output, rk(tier + "_pickaxe"));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, item(tier + "_axe"), 1)
                .define('I', ingot).define('S', s)
                .pattern("II").pattern("IS").pattern(" S")
                .unlockedBy("has_ingot", has(ingot))
                .save(this.output, rk(tier + "_axe"));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, item(tier + "_shovel"), 1)
                .define('I', ingot).define('S', s)
                .pattern("I").pattern("S").pattern("S")
                .unlockedBy("has_ingot", has(ingot))
                .save(this.output, rk(tier + "_shovel"));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, item(tier + "_hoe"), 1)
                .define('I', ingot).define('S', s)
                .pattern("II").pattern(" S").pattern(" S")
                .unlockedBy("has_ingot", has(ingot))
                .save(this.output, rk(tier + "_hoe"));
    }

    // ── Armour ────────────────────────────────────────────────────────

    private void buildArmourRecipes(HolderGetter<Item> items) {
        buildArmour(items, "copper", GotModItems.COPPER_INGOT.get());
        buildArmour(items, "bronze", GotModItems.BRONZE_INGOT.get());
    }

    private void buildArmour(HolderGetter<Item> items, String tier, Item ingot) {
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, item(tier + "_helmet"), 1)
                .define('I', ingot)
                .pattern("III").pattern("I I")
                .unlockedBy("has_ingot", has(ingot))
                .save(this.output, rk(tier + "_helmet"));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, item(tier + "_chestplate"), 1)
                .define('I', ingot)
                .pattern("I I").pattern("III").pattern("III")
                .unlockedBy("has_ingot", has(ingot))
                .save(this.output, rk(tier + "_chestplate"));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, item(tier + "_leggings"), 1)
                .define('I', ingot)
                .pattern("III").pattern("I I").pattern("I I")
                .unlockedBy("has_ingot", has(ingot))
                .save(this.output, rk(tier + "_leggings"));

        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, item(tier + "_boots"), 1)
                .define('I', ingot)
                .pattern("I I").pattern("I I")
                .unlockedBy("has_ingot", has(ingot))
                .save(this.output, rk(tier + "_boots"));
    }

    // ── Helpers ───────────────────────────────────────────────────────

    private Item item(String name) {
        return Objects.requireNonNull(
                BuiltInRegistries.ITEM.getValue(
                        ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name)),
                "Unknown item for recipe: " + name);
    }

    /** Creates a ResourceKey<Recipe<?>> for the given path under the got: namespace. */
    private ResourceKey<Recipe<?>> rk(String path) {
        return ResourceKey.create(Registries.RECIPE,
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, path));
    }
}