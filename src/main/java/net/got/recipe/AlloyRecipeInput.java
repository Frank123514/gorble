package net.got.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

/**
 * RecipeInput for the Forge's alloying mode — four input slots that get melted
 * together in a 3:1 ratio.  Slots A/B/C hold the "majority" metal (×3) and
 * slot D holds the "minority" metal (×1).
 */
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
