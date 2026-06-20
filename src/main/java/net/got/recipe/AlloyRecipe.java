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
 * AlloyRecipe — two-ingredient recipe processed by the Forge block's
 * alloying mode. Melts and combines two metals into a result (typically
 * an ingot of a new alloy metal, e.g. copper + tin -> bronze).
 *
 * JSON format (type "got:alloy"):
 * <pre>
 * {
 *   "type": "got:alloy",
 *   "ingredient_a": { "item": "got:copper_ingot" },
 *   "ingredient_b": { "item": "got:tin_ingot" },
 *   "result": { "id": "got:bronze_ingot", "count": 2 },
 *   "cookingtime": 240
 * }
 * </pre>
 */
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

    // ── Getters ───────────────────────────────────────────────────────────────

    public Ingredient getIngredientA() { return ingredientA; }
    public Ingredient getIngredientB() { return ingredientB; }
    public ItemStack   getResult()     { return result; }
    public int         getCookingTime(){ return cookingTime; }

    // ── Recipe<AlloyRecipeInput> ──────────────────────────────────────────────

    @Override
    public boolean matches(AlloyRecipeInput input, Level level) {
        // Either slot can hold either ingredient — order doesn't matter to the player.
        return (ingredientA.test(input.itemA()) && ingredientB.test(input.itemB()))
                || (ingredientA.test(input.itemB()) && ingredientB.test(input.itemA()));
    }

    @Override
    public ItemStack assemble(AlloyRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.createFromOptionals(
                java.util.List.of(java.util.Optional.of(ingredientA), java.util.Optional.of(ingredientB)));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
    }

    @Override
    public RecipeSerializer<AlloyRecipe> getSerializer() {
        return GotModRecipeSerializers.ALLOY_RECIPE.get();
    }

    @Override
    public RecipeType<AlloyRecipe> getType() {
        return GotModRecipeTypes.ALLOY.get();
    }

    // ── Serializer ────────────────────────────────────────────────────────────

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
