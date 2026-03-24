package net.got.init;

import net.got.GotMod;
import net.got.recipe.BakingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GotModRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> REGISTRY =
            DeferredRegister.create(Registries.RECIPE_TYPE, GotMod.MODID);

    // FIX: was typed as RecipeType<AbstractCookingRecipe>, which is wrong because
    //      AbstractCookingRecipe requires a CookingBookCategory in its constructor
    //      and our BakingRecipe does not extend it. Type it as BakingRecipe instead.
    public static final DeferredHolder<RecipeType<?>, RecipeType<BakingRecipe>> BAKING =
            REGISTRY.register("baking", () -> RecipeType.simple(
                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "baking")));
}