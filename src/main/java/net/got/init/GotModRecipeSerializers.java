package net.got.init;

import net.got.GotMod;
import net.got.recipe.OvenRecipe;
import net.got.recipe.SmithyRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GotModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, GotMod.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, OvenRecipe.Serializer> OVEN_RECIPE =
            REGISTRY.register("oven", OvenRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, SmithyRecipe.Serializer> SMITHY_RECIPE =
            REGISTRY.register("smithy", SmithyRecipe.Serializer::new);
}