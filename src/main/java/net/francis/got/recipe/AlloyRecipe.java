package net.francis.got.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.francis.got.init.ModRecipeSerializers;
import net.francis.got.init.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class AlloyRecipe implements Recipe<AlloyRecipeInput> {

    private final Ingredient ingredientA;
    private final Ingredient ingredientB;
    private final ItemStack  result;
    private final int        cookingTime;

    public AlloyRecipe(Ingredient ingredientA, Ingredient ingredientB, ItemStack result, int cookingTime) {
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.result      = result;
        this.cookingTime = cookingTime;
    }

    public Ingredient getIngredientA() { return ingredientA; }
    public Ingredient getIngredientB() { return ingredientB; }
    public ItemStack   getResult()     { return result; }
    public int         getCookingTime(){ return cookingTime; }

    @Override
    public boolean matches(AlloyRecipeInput input, Level level) {
        ItemStack[] stacks = {
            input.itemA(), input.itemB(), input.itemC(), input.itemD()
        };
        
        for (ItemStack s : stacks) {
            if (s.isEmpty()) return false;
        }
        return matchesRatio(stacks, ingredientA, ingredientB)
            || matchesRatio(stacks, ingredientB, ingredientA);
    }

    private static boolean matchesRatio(ItemStack[] stacks, Ingredient three, Ingredient one) {
        int threeCount = 0, oneCount = 0;
        for (ItemStack s : stacks) {
            if (three.test(s)) threeCount++;
            else if (one.test(s)) oneCount++;
        }
        return threeCount == 3 && oneCount == 1;
    }

    @Override
    public ItemStack assemble(AlloyRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.createFromOptionals(
                java.util.List.of(
                    java.util.Optional.of(ingredientA),
                    java.util.Optional.of(ingredientA),
                    java.util.Optional.of(ingredientA),
                    java.util.Optional.of(ingredientB)));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
    }

    @Override
    public RecipeSerializer<AlloyRecipe> getSerializer() {
        return ModRecipeSerializers.ALLOY_RECIPE.get();
    }

    @Override
    public RecipeType<AlloyRecipe> getType() {
        return ModRecipeTypes.ALLOY.get();
    }

    public static class Serializer implements RecipeSerializer<AlloyRecipe> {

        public static final MapCodec<AlloyRecipe> CODEC =
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        Ingredient.CODEC
                                .fieldOf("ingredient_a")
                                .forGetter(r -> r.ingredientA),
                        Ingredient.CODEC
                                .fieldOf("ingredient_b")
                                .forGetter(r -> r.ingredientB),
                        ItemStack.STRICT_CODEC
                                .fieldOf("result")
                                .forGetter(r -> r.result),
                        Codec.INT
                                .optionalFieldOf("cookingtime", 240)
                                .forGetter(r -> r.cookingTime)
                ).apply(inst, AlloyRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AlloyRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, r -> r.ingredientA,
                        Ingredient.CONTENTS_STREAM_CODEC, r -> r.ingredientB,
                        ItemStack.STREAM_CODEC,            r -> r.result,
                        ByteBufCodecs.INT,                 r -> r.cookingTime,
                        AlloyRecipe::new
                );

        @Override
        public MapCodec<AlloyRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlloyRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
