package net.got.init;

import net.got.GotMod;
import net.got.recipe.OvenRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GotModRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> REGISTRY =
            DeferredRegister.create(Registries.RECIPE_TYPE, GotMod.MODID);

    /**
     * The shaped-cooking recipe type for the Oven.
     * Recipe JSON must use "type": "got:oven".
     */
    public static final DeferredHolder<RecipeType<?>, RecipeType<OvenRecipe>> OVEN =
            REGISTRY.register("oven", () -> RecipeType.simple(
                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "oven")));
}
