package net.got.init;

import net.got.GotMod;
import net.got.recipe.OvenRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class GotModRecipeSerializers {

    // ── Recipe serializer registry ─────────────────────────────────────────

    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY =
            DeferredRegister.create(net.minecraft.core.registries.Registries.RECIPE_SERIALIZER, GotMod.MODID);

    /**
     * Serializer for the shaped oven recipe.
     * Recipe JSON: "type": "got:oven"
     */
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<OvenRecipe>> OVEN =
            REGISTRY.register("oven", () -> new RecipeSerializer<>() {
                @Override
                public com.mojang.serialization.MapCodec<OvenRecipe> codec() {
                    return OvenRecipe.CODEC;
                }

                @Override
                public net.minecraft.network.codec.StreamCodec<
                        net.minecraft.network.RegistryFriendlyByteBuf, OvenRecipe> streamCodec() {
                    return OvenRecipe.STREAM_CODEC;
                }
            });

    // ── Recipe book category registry ──────────────────────────────────────
    //
    // RecipeBookCategory is a plain registry object in NeoForge 21.x.
    // Register one for the oven so that recipeBookCategory() has a valid instance.

    public static final DeferredRegister<RecipeBookCategory> CATEGORY_REGISTRY =
            DeferredRegister.create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, GotMod.MODID);

    public static final Supplier<RecipeBookCategory> OVEN_CATEGORY =
            CATEGORY_REGISTRY.register("oven", RecipeBookCategory::new);
}
