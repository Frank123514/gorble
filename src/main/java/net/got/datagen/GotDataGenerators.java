package net.got.datagen;

import net.got.GotMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

/**
 * Entry point for all GOT data generators.
 * Run with: ./gradlew runData
 * Output: src/generated/resources/
 */
@EventBusSubscriber(modid = GotMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class GotDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var gen    = event.getGenerator();
        var output = gen.getPackOutput();
        var efh    = event.getExistingFileHelper();
        var lookup = event.getLookupProvider();

        // ── Server-side ────────────────────────────────────────────────
        // In NeoForge 1.21.3, RecipeProvider is NOT a DataProvider itself.
        // RecipeProvider.Runner is the DataProvider wrapper; override
        // createRecipeProvider() to supply our subclass.
        gen.addProvider(event.includeServer(), new RecipeProvider.Runner(output, lookup) {
            @Override
            public String getName() {
                return "";
            }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries,
                                                          RecipeOutput recipeOutput) {
                return new GotRecipeProvider(registries, recipeOutput);
            }
        });

        var blockTags = new GotBlockTagsProvider(output, lookup, efh);
        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(),
                new GotItemTagsProvider(output, lookup, blockTags.contentsGetter(), efh));

        gen.addProvider(event.includeServer(), new GotLootTableProvider(output, lookup));

        // ── Client-side ────────────────────────────────────────────────
        gen.addProvider(event.includeClient(), new GotBlockStateProvider(output, efh));
        gen.addProvider(event.includeClient(), new GotItemModelProvider(output, efh));
    }

    private GotDataGenerators() {}
}