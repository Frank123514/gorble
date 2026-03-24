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
 *
 * NeoForge 21.4 (MC 1.21.4) changes from earlier 21.x:
 *  - GatherDataEvent split into GatherDataEvent.Client / GatherDataEvent.Server.
 *  - Model providers are registered via event.createProvider(Constructor::new),
 *    NOT gen.addProvider(bool, instance) — the old addProvider overload that
 *    accepted a DataProvider instance no longer accepts ModelProvider subclasses
 *    because ModelProvider is no longer a DataProvider directly.
 *  - ExistingFileHelper is gone entirely from the datagen API.
 *
 * Run with: ./gradlew runClientData
 * Output:   src/generated/resources/
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class GotDataGenerators {

    // ── Client data run ───────────────────────────────────────────────

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        var gen    = event.getGenerator();
        var output = gen.getPackOutput();
        var lookup = event.getLookupProvider();

        // ── Server-side data (recipes, tags, loot) ────────────────────
        gen.addProvider(true, new RecipeProvider.Runner(output, lookup) {
            @Override
            public String getName() { return "GoT Recipes"; }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries,
                                                          RecipeOutput recipeOutput) {
                return new GotRecipeProvider(registries, recipeOutput);
            }
        });

        var blockTags = new GotBlockTagsProvider(output, lookup);
        gen.addProvider(true, blockTags);
        gen.addProvider(true,
                new GotItemTagsProvider(output, lookup, blockTags.contentsGetter()));

        gen.addProvider(true, new GotLootTableProvider(output, lookup));

        // ── Client-side assets (blockstates + models) ─────────────────
        // ModelProvider subclasses must be registered via createProvider(),
        // not addProvider() — this is the API change in NeoForge 21.4.
        event.createProvider(GotBlockStateProvider::new);
        event.createProvider(GotItemModelProvider::new);

        // Language file (LanguageProvider is still a plain DataProvider)
        gen.addProvider(true, new GotLanguageProvider(output));
    }

    // ── Server data run ───────────────────────────────────────────────

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        var gen    = event.getGenerator();
        var output = gen.getPackOutput();
        var lookup = event.getLookupProvider();

        gen.addProvider(true, new RecipeProvider.Runner(output, lookup) {
            @Override
            public String getName() { return "GoT Recipes (server)"; }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries,
                                                          RecipeOutput recipeOutput) {
                return new GotRecipeProvider(registries, recipeOutput);
            }
        });

        var blockTags = new GotBlockTagsProvider(output, lookup);
        gen.addProvider(true, blockTags);
        gen.addProvider(true,
                new GotItemTagsProvider(output, lookup, blockTags.contentsGetter()));

        gen.addProvider(true, new GotLootTableProvider(output, lookup));
    }

    private GotDataGenerators() {}
}