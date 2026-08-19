package net.francis.got.recipe;

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

public class OvenRecipe implements Recipe<CraftingInput> {

    private final ShapedRecipePattern pattern;
    private final ItemStack           result;
    private final float               experience;
    private final int                 cookingTime;

    public OvenRecipe(ShapedRecipePattern pattern, ItemStack result,
                      float experience, int cookingTime) {
        this.pattern     = pattern;
        this.result      = result;
        this.experience  = experience;
        this.cookingTime = cookingTime;
    }

    public float getExperience()  { return experience; }
    public int   getCookingTime() { return cookingTime; }
    public ItemStack getResult()  { return result; }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return pattern.matches(input);
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.createFromOptionals(pattern.ingredients());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
    }

    @Override
    public RecipeSerializer<OvenRecipe> getSerializer() {
        return ModRecipeSerializers.OVEN_RECIPE.get();
    }

    @Override
    public RecipeType<OvenRecipe> getType() {
        return ModRecipeTypes.OVEN.get();
    }

    public static class Serializer implements RecipeSerializer<OvenRecipe> {

        public static final MapCodec<OvenRecipe> CODEC =
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        ShapedRecipePattern.MAP_CODEC
                                .forGetter(r -> r.pattern),
                        ItemStack.STRICT_CODEC
                                .fieldOf("result")
                                .forGetter(r -> r.result),
                        com.mojang.serialization.Codec.FLOAT
                                .optionalFieldOf("experience", 0.0F)
                                .forGetter(r -> r.experience),
                        com.mojang.serialization.Codec.INT
                                .optionalFieldOf("cookingtime", 200)
                                .forGetter(r -> r.cookingTime)
                ).apply(inst, OvenRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, OvenRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        ShapedRecipePattern.STREAM_CODEC,      r -> r.pattern,
                        ItemStack.STREAM_CODEC,                r -> r.result,
                        ByteBufCodecs.FLOAT,                   r -> r.experience,
                        ByteBufCodecs.INT,                     r -> r.cookingTime,
                        OvenRecipe::new
                );

        @Override
        public MapCodec<OvenRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, OvenRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
