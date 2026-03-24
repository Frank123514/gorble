package net.got.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.got.GotMod;
import net.got.init.GotModBlocks;
import net.got.init.GotModRecipeTypes;
import net.got.recipe.BakingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

/**
 * JEI plugin for the GOT mod — updated for JEI 20.0.0.4 / MC 1.21.4.
 *
 * Key API changes from previous versions:
 *
 * 1. RecipeType constructor:
 *    - The static factory RecipeType.create(String, String, Class) is deprecated for removal.
 *    - Use the public constructor: new RecipeType<>(ResourceLocation, Class).
 *
 * 2. RecipeManager on the client (MC 1.21.2+):
 *    - ClientLevel no longer exposes getRecipeManager().
 *    - ClientPacketListener#getRecipeManager() was renamed to ClientPacketListener#recipes()
 *      which returns a RecipeAccess, not a RecipeManager.
 *    - Since RecipeAccess only exposes property sets (inputs), not full recipe lists,
 *      we cannot use it to enumerate all baking recipes for JEI display.
 *    - The correct 1.21.4 approach: use Minecraft.getInstance().getConnection().recipes()
 *      which is a ClientRecipeContainer.  It does NOT provide getAllRecipesFor().
 *    - Solution: cache recipes via NeoForge's RecipesUpdatedEvent fired on the client,
 *      and store them in a static list that JEI reads during registerRecipes.
 *
 * 3. addRecipeCatalyst:
 *    - addRecipeCatalyst(ItemStack, IRecipeType<?>...) is deprecated.
 *    - Use addRecipeCatalyst(ItemStack, RecipeType<?>...) — the RecipeType overload.
 */
@JeiPlugin
public class GotJeiPlugin implements IModPlugin {

    /**
     * Use the public constructor new RecipeType<>(uid, recipeClass)
     * instead of the deprecated RecipeType.create(namespace, path, class).
     */
    public static final RecipeType<BakingRecipe> BAKING_TYPE =
            new RecipeType<>(
                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "baking"),
                    BakingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new OvenRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // MC 1.21.2+: recipes are server-side only; the client only receives RecipeAccess
        // (property sets for slot filtering), not the full RecipeManager.
        // Retrieve the synced recipe list from the client connection's recipe container.
        // ClientPacketListener#recipes() returns a RecipeAccess; cast to the NeoForge
        // ClientRecipeContainer to access byType(), which lists all synced BakingRecipes.
        //
        // If the connection or recipe container is not yet available (e.g. in a world with
        // no server), gracefully return an empty list so JEI does not crash.
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null) {
            registration.addRecipes(BAKING_TYPE, List.of());
            return;
        }

        // NeoForge 21.4: connection.recipes() is a RecipeAccess backed by
        // ClientRecipeContainer, which internally holds the synced RecipeMap.
        // We cast to net.minecraft.world.item.crafting.RecipeManager if the server
        // is integrated (single-player), otherwise rely on the cached recipes.
        List<BakingRecipe> recipes;
        try {
            // In integrated server / LAN, the server-side RecipeManager is accessible.
            var server = Minecraft.getInstance().getSingleplayerServer();
            if (server != null) {
                recipes = server.getRecipeManager()
                        .recipeMap()
                        .byType(GotModRecipeTypes.BAKING.get())
                        .stream()
                        .map(RecipeHolder::value)
                        .toList();
            } else {
                // Dedicated server: recipes were sent via RecipesUpdatedEvent and cached.
                recipes = GotJeiRecipeCache.getBakingRecipes();
            }
        } catch (Exception e) {
            recipes = List.of();
        }

        registration.addRecipes(BAKING_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // Use the non-deprecated RecipeType<?>... overload.
        registration.addRecipeCatalyst(
                new ItemStack(GotModBlocks.OVEN.get()),
                BAKING_TYPE
        );
    }
}
