package net.got.recipe;

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

public class BakingRecipe implements Recipe<SingleRecipeInput> {

    private final Ingredient ingredient;
    private final ItemStack  result;
    private final float      experience;
    private final int        cookingTime;

    public BakingRecipe(Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        this.ingredient  = ingredient;
        this.result      = result;
        this.experience  = experience;
        this.cookingTime = cookingTime;
    }

    // ── Accessors ──────────────────────────────────────────────────────────

    public Ingredient getIngredient()  { return ingredient; }
    public float      getExperience()  { return experience; }
    public int        getCookingTime() { return cookingTime; }

    /**
     * Returns a copy of the result stack.
     * Used by JEI display (OvenRecipeCategory) since Recipe.getResultItem()
     * was removed in 1.21.4.
     */
    public ItemStack getResult() { return result.copy(); }

    // ── Recipe interface ───────────────────────────────────────────────────

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return ingredient.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    /** Not placed via a crafting grid — use NOT_PLACEABLE. */
    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    /**
     * RecipeBookCategory is a registry object in NeoForge 21.3.x — it is NOT
     * an interface or enum.  Return the registered instance from
     * GotModRecipeSerializers where we declare the DeferredRegister for it.
     */
    @Override
    public RecipeBookCategory recipeBookCategory() {
        return GotModRecipeSerializers.BAKING_CATEGORY.get();
    }

    @Override
    public RecipeSerializer<BakingRecipe> getSerializer() {
        return GotModRecipeSerializers.BAKING.get();
    }

    @Override
    public RecipeType<BakingRecipe> getType() {
        return GotModRecipeTypes.BAKING.get();
    }

    // ── Codec / StreamCodec ────────────────────────────────────────────────

    public static final MapCodec<BakingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC
                    .fieldOf("ingredient")
                    .forGetter(r -> r.ingredient),
            ItemStack.STRICT_CODEC
                    .fieldOf("result")
                    .forGetter(r -> r.result),
            com.mojang.serialization.Codec.FLOAT
                    .optionalFieldOf("experience", 0.0f)
                    .forGetter(r -> r.experience),
            com.mojang.serialization.Codec.INT
                    .optionalFieldOf("cookingtime", 200)
                    .forGetter(r -> r.cookingTime)
    ).apply(inst, BakingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BakingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, r -> r.ingredient,
                    ItemStack.STREAM_CODEC,           r -> r.result,
                    ByteBufCodecs.FLOAT,              r -> r.experience,
                    ByteBufCodecs.VAR_INT,            r -> r.cookingTime,
                    BakingRecipe::new
            );
}