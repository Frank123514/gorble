package net.got.block;

import net.got.init.GotModBlockEntities;
import net.got.init.GotModRecipeTypes;
import net.got.menu.OvenMenu;
import net.got.recipe.OvenRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Block entity for the Oven.
 *
 * Slot layout (matches OFAW):
 *   0 - 8  : 3×3 input grid
 *   9      : output slot
 *   10     : fuel slot
 *
 * ContainerData (4 values, matches OFAW):
 *   data[0] : cookingProgress  (current ticks cooked)
 *   data[1] : cookingTime      (ticks needed for the current recipe)
 *   data[2] : burnTime         (remaining fuel ticks)
 *   data[3] : burnDuration     (total ticks of the current fuel item)
 */
public class OvenBlockEntity extends BaseContainerBlockEntity {

    private NonNullList<ItemStack> items =
            NonNullList.withSize(OvenMenu.TOTAL_SLOTS, ItemStack.EMPTY);

    private int cookingProgress;
    private int cookingTime;    // max progress for the matched recipe this tick
    private int burnTime;
    private int burnDuration;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override public int get(int i) {
            return switch (i) {
                case 0 -> cookingProgress;
                case 1 -> cookingTime;
                case 2 -> burnTime;
                case 3 -> burnDuration;
                default -> 0;
            };
        }
        @Override public void set(int i, int v) {
            switch (i) {
                case 0 -> cookingProgress = v;
                case 1 -> cookingTime      = v;
                case 2 -> burnTime         = v;
                case 3 -> burnDuration     = v;
            }
        }
        @Override public int getCount() { return 4; }
    };

    public OvenBlockEntity(BlockPos pos, BlockState state) {
        super(GotModBlockEntities.OVEN.get(), pos, state);
    }

    // ── BaseContainerBlockEntity ───────────────────────────────────────────

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.got.oven");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new OvenMenu(id, inventory, this, dataAccess);
    }

    @Override
    protected NonNullList<ItemStack> getItems() { return items; }

    @Override
    protected void setItems(NonNullList<ItemStack> items) { this.items = items; }

    @Override
    public int getContainerSize() { return OvenMenu.TOTAL_SLOTS; }

    // ── Fuel helper ────────────────────────────────────────────────────────

    private static int getFuelTime(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        var data = stack.getItemHolder().getData(NeoForgeDataMaps.FURNACE_FUELS);
        return data != null ? data.burnTime() : 0;
    }

    // ── Server tick ────────────────────────────────────────────────────────

    public static void serverTick(Level level, BlockPos pos, BlockState state, OvenBlockEntity be) {
        boolean wasLit = be.burnTime > 0;

        // Build input and find recipe first -- fuel should only burn when cooking
        List<ItemStack> gridItems = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            gridItems.add(be.items.get(i));
        }
        CraftingInput input = shrinkGrid(gridItems);

        ServerLevel serverLevel = (ServerLevel) level;
        Optional<RecipeHolder<OvenRecipe>> match =
                serverLevel.getServer().getRecipeManager()
                        .getRecipeFor(GotModRecipeTypes.OVEN.get(), input, serverLevel);

        boolean hasRecipe = match.isPresent();

        // ── Fuel management -- only burn fuel when there is a recipe ───────
        if (be.burnTime > 0) {
            be.burnTime--;
        }
        if (be.burnTime == 0 && hasRecipe) {
            ItemStack fuel = be.items.get(OvenMenu.FUEL_SLOT);
            int fuelTime = getFuelTime(fuel);
            if (fuelTime > 0) {
                be.burnDuration = fuelTime;
                be.burnTime     = fuelTime;
                fuel.shrink(1);
                if (fuel.isEmpty()) be.items.set(OvenMenu.FUEL_SLOT, ItemStack.EMPTY);
            } else {
                be.burnDuration = 0;
            }
        }

        // ── Cooking logic ──────────────────────────────────────────────────
        if (be.burnTime > 0 && hasRecipe) {
            OvenRecipe recipe = match.get().value();
            be.cookingTime = recipe.getCookingTime();
            be.cookingProgress++;

            if (be.cookingProgress >= recipe.getCookingTime()) {
                be.cookingProgress = 0;
                ItemStack result = recipe.assemble(input, level.registryAccess());
                ItemStack output = be.items.get(OvenMenu.OUTPUT_SLOT);

                boolean canOutput = output.isEmpty()
                        || (ItemStack.isSameItemSameComponents(output, result)
                        && output.getCount() + result.getCount() <= output.getMaxStackSize());

                if (canOutput) {
                    if (output.isEmpty()) {
                        be.items.set(OvenMenu.OUTPUT_SLOT, result);
                    } else {
                        output.grow(result.getCount());
                    }
                    consumeIngredients(be);
                    be.setChanged();
                }
            }
        } else if (!hasRecipe) {
            // No matching recipe -- reset progress
            be.cookingProgress = 0;
            be.cookingTime     = 0;
        }

        // ── Update block-state LIT flag ────────────────────────────────────
        boolean isLit = be.burnTime > 0;
        if (wasLit != isLit) {
            state = state.setValue(AbstractFurnaceBlock.LIT, isLit);
            level.setBlock(pos, state, 3);
            be.setChanged();
        }
    }

    /**
     * Shrinks the 3x3 grid to the bounding box of non-empty items so that
     * patterns smaller than 3x3 (e.g. 1x3, 2x2) match correctly.
     */
    private static CraftingInput shrinkGrid(List<ItemStack> grid) {
        int minCol = 3, maxCol = -1, minRow = 3, maxRow = -1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (!grid.get(col + row * 3).isEmpty()) {
                    if (col < minCol) minCol = col;
                    if (col > maxCol) maxCol = col;
                    if (row < minRow) minRow = row;
                    if (row > maxRow) maxRow = row;
                }
            }
        }
        if (maxCol < 0) return CraftingInput.of(1, 1, List.of(ItemStack.EMPTY));
        int w = maxCol - minCol + 1;
        int h = maxRow - minRow + 1;
        List<ItemStack> trimmed = new ArrayList<>(w * h);
        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                trimmed.add(grid.get(col + row * 3));
            }
        }
        return CraftingInput.of(w, h, trimmed);
    }

    /**
     * Shrink every non-empty grid slot by 1 after a successful craft.
     * Mirrors OFAW's per-ingredient consumption behaviour.
     */
    private static void consumeIngredients(OvenBlockEntity be) {
        for (int i = 0; i < 9; i++) {
            ItemStack slot = be.items.get(i);
            if (!slot.isEmpty()) {
                slot.shrink(1);
                if (slot.isEmpty()) be.items.set(i, ItemStack.EMPTY);
            }
        }
    }

    // ── NBT persistence ────────────────────────────────────────────────────

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("CookingProgress", cookingProgress);
        tag.putInt("CookingTime",     cookingTime);
        tag.putInt("BurnTime",        burnTime);
        tag.putInt("BurnDuration",    burnDuration);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);
        cookingProgress = tag.getInt("CookingProgress");
        cookingTime     = tag.getInt("CookingTime");
        burnTime        = tag.getInt("BurnTime");
        burnDuration    = tag.getInt("BurnDuration");
    }
}