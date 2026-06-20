package net.got.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

/**
 * RecipeInput for the Forge's alloying mode — the two metal slots that get
 * melted together. Order is not significant; AlloyRecipe#matches checks
 * both orderings.
 */
public record AlloyRecipeInput(ItemStack itemA, ItemStack itemB) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> itemA;
            case 1 -> itemB;
            default -> throw new IllegalArgumentException("No item for index " + index);
        };
    }

    @Override
    public int size() { return 2; }
}
