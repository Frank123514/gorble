package net.got.init;

import net.got.GotMod;
import net.got.recipe.OvenRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GotModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, GotMod.MODID);

    /**
     * Serializer for the Oven's shaped-cooking recipe.
     * Recipe JSONs must use {@code "type": "got:oven"}.
     *
     * The serializer is a thin wrapper that delegates to OvenRecipe.CODEC
     * and OvenRecipe.STREAM_CODEC — no anonymous inner class needed.
     */
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<OvenRecipe>> OVEN =
            REGISTRY.register("oven", OvenRecipeSerializer::new);

    // ── Inner serializer class ─────────────────────────────────────────────

    public static final class OvenRecipeSerializer implements RecipeSerializer<OvenRecipe> {

        @Override
        public com.mojang.serialization.MapCodec<OvenRecipe> codec() {
            return OvenRecipe.CODEC;
        }

        @Override
        public net.minecraft.network.codec.StreamCodec<
                net.minecraft.network.RegistryFriendlyByteBuf, OvenRecipe> streamCodec() {
            return OvenRecipe.STREAM_CODEC;
        }
    }
}