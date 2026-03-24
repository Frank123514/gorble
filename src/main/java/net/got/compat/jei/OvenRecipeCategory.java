package net.got.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.got.GotMod;
import net.got.recipe.BakingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.stream.StreamSupport;

public class OvenRecipeCategory implements IRecipeCategory<BakingRecipe> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "textures/gui/oven_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public OvenRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = guiHelper.createDrawableItemStack(
                new ItemStack(net.got.init.GotModBlocks.OVEN.get()));
    }

    @Override
    public RecipeType<BakingRecipe> getRecipeType() {
        return GotJeiPlugin.BAKING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.got.oven");
    }

    @SuppressWarnings("removal")
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BakingRecipe recipe, IFocusGroup focuses) {
        Ingredient ingredient = recipe.getIngredient();

        // ingredient.getValues() returns HolderSet<Item> (Iterable, not array).
        // Stream it via StreamSupport, then map each Holder<Item> to an ItemStack.
        List<ItemStack> inputs = StreamSupport
                .stream(ingredient.getValues().spliterator(), false)
                .map(holder -> new ItemStack(holder.value()))
                .toList();

        builder.addSlot(RecipeIngredientRole.INPUT, 56, 17)
                .addItemStacks(inputs);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 35)
                .addItemStacks(List.of(recipe.getResult()));
    }
}