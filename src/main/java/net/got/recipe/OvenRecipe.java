package net.got.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.init.GotModRecipeSerializers;
import net.got.init.GotModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

/**
 * Single-input, single-output recipe processed by the Oven.
 * Only recipes with "type": "got:oven" are accepted.
 */
public class OvenRecipe implements Recipe<SingleRecipeInput> {

    private final Ingredient ingredient;
    private final ItemStack  result;
    private final float      experience;
    private final int        cookingTime;

    public OvenRecipe(Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        this.ingredient  = ingredient;
        this.result      = result;
        this.experience  = experience;
        this.cookingTime = cookingTime;
    }

    public Ingredient getIngredient() { return ingredient; }
    public float      getExperience() { return experience; }
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

    /**
     * MC 1.21.4: placementInfo() replaces getIngredients() / canCraftInDimensions().
     */
    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(ingredient);
    }

    /**
     * MC 1.21.4: getResultItem() was removed from Recipe; expose result via a plain getter instead.
     */
    public ItemStack getResult() {
        return result;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
    }

    @Override
    public RecipeSerializer<OvenRecipe> getSerializer() {
        return GotModRecipeSerializers.OVEN_RECIPE.get();
    }

    @Override
    public RecipeType<OvenRecipe> getType() {
        return GotModRecipeTypes.OVEN.get();
    }

    // ── Serializer ────────────────────────────────────────────────────────────

    public static class Serializer implements RecipeSerializer<OvenRecipe> {

        public static final MapCodec<OvenRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(r -> r.ingredient),
                ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result),
                com.mojang.serialization.Codec.FLOAT
                        .optionalFieldOf("experience", 0.0F).forGetter(r -> r.experience),
                com.mojang.serialization.Codec.INT
                        .optionalFieldOf("cookingtime", 200).forGetter(r -> r.cookingTime)
        ).apply(inst, OvenRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, OvenRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC,                         r -> r.ingredient,
                        ItemStack.STREAM_CODEC,                                   r -> r.result,
                        net.minecraft.network.codec.ByteBufCodecs.FLOAT,          r -> r.experience,
                        net.minecraft.network.codec.ByteBufCodecs.INT,            r -> r.cookingTime,
                        OvenRecipe::new
                );

        @Override public MapCodec<OvenRecipe> codec()             { return CODEC; }
        @Override public StreamCodec<RegistryFriendlyByteBuf, OvenRecipe> streamCodec() { return STREAM_CODEC; }
    }
}
