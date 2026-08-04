package net.got.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.init.GotModRecipeSerializers;
import net.got.init.GotModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

/**
 * SmithyRecipe — single-ingredient recipe processed by the Smithy block.
 *
 * JSON format (type "got:smithy") — mirrors stonecutter recipe format:
 * <pre>
 * {
 *   "type": "got:smithy",
 *   "ingredient": { "item": "got:bronze_ingot" },
 *   "result": { "id": "got:bronze_longsword_crossguard_pommel", "count": 1 },
 *   "cookingtime": 200
 * }
 * </pre>
 */
public class SmithyRecipe implements Recipe<SingleRecipeInput> {

    private final Ingredient ingredient;
    private final ItemStack  result;
    private final int        cookingTime;

    public SmithyRecipe(Ingredient ingredient, ItemStack result, int cookingTime) {
        this.ingredient  = ingredient;
        this.result      = result;
        this.cookingTime = cookingTime;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public Ingredient getIngredient() { return ingredient; }
    public ItemStack  getResult()     { return result; }
    public int        getCookingTime(){ return cookingTime; }

    // ── Recipe<SingleRecipeInput> ─────────────────────────────────────────────

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return ingredient.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.createFromOptionals(
                java.util.List.of(java.util.Optional.of(ingredient)));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
    }

    @Override
    public RecipeSerializer<SmithyRecipe> getSerializer() {
        return GotModRecipeSerializers.SMITHY_RECIPE.get();
    }

    @Override
    public RecipeType<SmithyRecipe> getType() {
        return GotModRecipeTypes.SMITHY.get();
    }

    // ── Serializer ────────────────────────────────────────────────────────────

    public static class Serializer implements RecipeSerializer<SmithyRecipe> {

        public static final MapCodec<SmithyRecipe> CODEC =
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        Ingredient.CODEC
                                .fieldOf("ingredient")
                                .forGetter(r -> r.ingredient),
                        ItemStack.STRICT_CODEC
                                .fieldOf("result")
                                .forGetter(r -> r.result),
                        Codec.INT
                                .optionalFieldOf("cookingtime", 200)
                                .forGetter(r -> r.cookingTime)
                ).apply(inst, SmithyRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SmithyRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, r -> r.ingredient,
                        ItemStack.STREAM_CODEC,            r -> r.result,
                        ByteBufCodecs.INT,                 r -> r.cookingTime,
                        SmithyRecipe::new
                );

        @Override
        public MapCodec<SmithyRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SmithyRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
