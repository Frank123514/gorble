package net.got.init;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GotModRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> REGISTRY =
            DeferredRegister.create(Registries.RECIPE_TYPE, GotMod.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AbstractCookingRecipe>> BAKING =
            REGISTRY.register("baking", () -> RecipeType.simple(
                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "baking")));
}
