package net.got.init;

import net.got.GotMod;
import net.got.recipe.BakingRecipe;
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

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BakingRecipe>> BAKING =
            REGISTRY.register("baking", () -> new RecipeSerializer<>() {
                @Override
                public com.mojang.serialization.MapCodec<BakingRecipe> codec() {
                    return BakingRecipe.CODEC;
                }

                @Override
                public net.minecraft.network.codec.StreamCodec<
                        net.minecraft.network.RegistryFriendlyByteBuf, BakingRecipe> streamCodec() {
                    return BakingRecipe.STREAM_CODEC;
                }
            });

    // ── Recipe book category registry ──────────────────────────────────────
    //
    // In NeoForge 21.3.x RecipeBookCategory is a plain registry object — not
    // an interface or enum.  Register one for our custom baking recipe type so
    // that recipeBookCategory() has a valid instance to return.

    public static final DeferredRegister<RecipeBookCategory> CATEGORY_REGISTRY =
            DeferredRegister.create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, GotMod.MODID);

    public static final Supplier<RecipeBookCategory> BAKING_CATEGORY =
            CATEGORY_REGISTRY.register("baking", RecipeBookCategory::new);
}