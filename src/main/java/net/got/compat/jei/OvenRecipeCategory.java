package net.got.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.got.GotMod;
import net.got.recipe.OvenRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

import java.util.List;
import java.util.Optional;

/**
 * JEI category for the Oven.
 *
 * Displays a 3x3 ingredient grid on the left and the output on the right,
 * matching the in-game oven GUI layout.
 *
 * Expected oven_jei.png layout (120x60):
 *   A 3x3 slot grid outline starting at (2, 2), each slot 18px.
 *   An arrow pointing right around x=58.
 *   An output slot outline at (80, 20).
 */
public class OvenRecipeCategory implements IRecipeCategory<OvenRecipe> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "textures/gui/oven_jei.png");

    // Background: wide enough for 3x3 grid + arrow + output
    private static final int BG_WIDTH  = 120;
    private static final int BG_HEIGHT = 60;

    // Slot layout (relative to background top-left)
    private static final int GRID_X    = 2;
    private static final int GRID_Y    = 2;
    private static final int SLOT_SIZE = 18;
    private static final int OUTPUT_X  = 80;
    private static final int OUTPUT_Y  = 20;

    private final IDrawable background;
    private final IDrawable icon;

    public OvenRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, BG_WIDTH, BG_HEIGHT);
        this.icon = guiHelper.createDrawableItemStack(
                new ItemStack(net.got.init.GotModBlocks.OVEN.get()));
    }

    @Override
    public RecipeType<OvenRecipe> getRecipeType() {
        return GotJeiPlugin.OVEN_TYPE;
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
    public void setRecipe(IRecipeLayoutBuilder builder, OvenRecipe recipe, IFocusGroup focuses) {
        ShapedRecipePattern pattern = recipe.getPattern();

        // In NeoForge 1.21.x, ShapedRecipePattern.ingredients() returns
        // List<Optional<Ingredient>>, not NonNullList<Ingredient>.
        List<Optional<Ingredient>> ingredients = pattern.ingredients();
        int width  = pattern.width();
        int height = pattern.height();

        // Add each non-empty ingredient slot at its grid position
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Optional<Ingredient> optIng = ingredients.get(col + row * width);
                if (optIng.isEmpty()) continue;
                Ingredient ing = optIng.get();
                if (ing.isEmpty()) continue;

                // addIngredients(Ingredient) is the correct JEI API -- it handles
                // all item variants without reaching into Ingredient internals.
                builder.addSlot(RecipeIngredientRole.INPUT,
                                GRID_X + col * SLOT_SIZE,
                                GRID_Y + row * SLOT_SIZE)
                        .addIngredients(ing);
            }
        }

        // Output slot
        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_X, OUTPUT_Y)
                .addItemStacks(List.of(recipe.getResult()));
    }
}