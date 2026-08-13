package net.got.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AlloyRecipeInput(ItemStack itemA, ItemStack itemB,
                                ItemStack itemC, ItemStack itemD)
        implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> itemA;
            case 1 -> itemB;
            case 2 -> itemC;
            case 3 -> itemD;
            default -> throw new IllegalArgumentException("No item for index " + index);
        };
    }

    @Override
    public int size() { return 4; }
}
