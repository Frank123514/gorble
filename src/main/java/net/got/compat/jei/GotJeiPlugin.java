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
import net.got.recipe.OvenRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

@JeiPlugin
public class GotJeiPlugin implements IModPlugin {

    // RecipeType(ResourceLocation, Class) constructor — available in JEI 19.x (MC 1.21.4)
    // and is NOT deprecated. The deprecated static factory RecipeType.create(String, String, Class)
    // was from the 19.x transition period and removed in 20.x.
    public static final RecipeType<OvenRecipe> OVEN_TYPE =
            new RecipeType<>(
                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "oven"),
                    OvenRecipe.class
            );

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
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null) {
            registration.addRecipes(OVEN_TYPE, List.of());
            return;
        }

        List<OvenRecipe> recipes;
        try {
            var server = Minecraft.getInstance().getSingleplayerServer();
            if (server != null) {
                recipes = server.getRecipeManager()
                        .recipeMap()
                        .byType(GotModRecipeTypes.OVEN.get())
                        .stream()
                        .map(RecipeHolder::value)
                        .toList();
            } else {
                recipes = GotJeiRecipeCache.getOvenRecipes();
            }
        } catch (Exception e) {
            recipes = List.of();
        }

        registration.addRecipes(OVEN_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(GotModBlocks.OVEN.get()),
                OVEN_TYPE
        );
    }
}