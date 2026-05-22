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
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.Optional;

/**
 * Block entity for the Oven.
 *
 * Complete rewrite — the old implementation used CraftingInput and
 * ShapedRecipePattern.matches() which is designed for the crafting table and
 * never worked correctly when called from a furnace-style ticker.
 *
 * This version:
 *  - Builds an OvenInput directly from the 9 grid slots and passes it to
 *    RecipeManager.getRecipeFor(OVEN type, OvenInput, level).
 *  - Uses a straightforward fuel-then-cook loop identical to vanilla
 *    AbstractFurnaceBlockEntity but adapted for a cooking-time recipe.
 *  - Preserves the same slot layout and ContainerData layout so OvenMenu
 *    and OvenScreen require no changes.
 *
 * Slot layout (unchanged):
 *   0 – 8  : 3×3 input grid
 *   9      : output
 *   10     : fuel
 *
 * ContainerData (unchanged):
 *   [0] cookingProgress   [1] cookingTime   [2] burnTime   [3] burnDuration
 */
public class OvenBlockEntity extends BaseContainerBlockEntity {

    private NonNullList<ItemStack> items =
            NonNullList.withSize(OvenMenu.TOTAL_SLOTS, ItemStack.EMPTY);

    private int cookingProgress;
    private int cookingTime;
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
                case 1 -> cookingTime     = v;
                case 2 -> burnTime        = v;
                case 3 -> burnDuration    = v;
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

    @Override protected NonNullList<ItemStack> getItems()                       { return items; }
    @Override protected void setItems(NonNullList<ItemStack> items)             { this.items = items; }
    @Override public int getContainerSize()                                      { return OvenMenu.TOTAL_SLOTS; }

    // ── Fuel helper ────────────────────────────────────────────────────────

    private static int getFuelTime(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        var data = stack.getItemHolder().getData(NeoForgeDataMaps.FURNACE_FUELS);
        return data != null ? data.burnTime() : 0;
    }

    // ── Build OvenInput ────────────────────────────────────────────────────

    /**
     * Snapshot the 9 grid slots into an OvenInput for recipe lookup.
     * We never shrink the grid — OvenRecipe.matches() works on the full 9-slot
     * grid directly, so small patterns just leave the extra slots empty.
     */
    private OvenRecipe.OvenInput buildInput() {
        NonNullList<ItemStack> grid = NonNullList.withSize(9, ItemStack.EMPTY);
        for (int i = 0; i < 9; i++) {
            grid.set(i, items.get(i).copy());
        }
        return new OvenRecipe.OvenInput(grid);
    }

    // ── Server tick ────────────────────────────────────────────────────────

    public static void serverTick(Level level, BlockPos pos, BlockState state,
                                  OvenBlockEntity be) {
        boolean wasLit = be.burnTime > 0;

        // ── Recipe lookup ─────────────────────────────────────────────────
        OvenRecipe.OvenInput input = be.buildInput();
        Optional<RecipeHolder<OvenRecipe>> matchOpt =
                ((net.minecraft.server.level.ServerLevel) level)
                        .getServer().getRecipeManager()
                        .getRecipeFor(GotModRecipeTypes.OVEN.get(), input, level);

        boolean hasRecipe = matchOpt.isPresent();

        // ── Fuel management ───────────────────────────────────────────────
        // Burn down existing fuel every tick.
        if (be.burnTime > 0) {
            be.burnTime--;
        }
        // Consume a new fuel item only when we have a recipe and the fire is out.
        if (be.burnTime == 0 && hasRecipe) {
            ItemStack fuel    = be.items.get(OvenMenu.FUEL_SLOT);
            int       fuelVal = getFuelTime(fuel);
            if (fuelVal > 0) {
                be.burnDuration = fuelVal;
                be.burnTime     = fuelVal;
                fuel.shrink(1);
                if (fuel.isEmpty()) be.items.set(OvenMenu.FUEL_SLOT, ItemStack.EMPTY);
                be.setChanged();
            } else {
                be.burnDuration = 0;
            }
        }

        // ── Cooking logic ─────────────────────────────────────────────────
        if (be.burnTime > 0 && hasRecipe) {
            OvenRecipe recipe = matchOpt.get().value();
            be.cookingTime = recipe.getCookingTime();
            be.cookingProgress++;

            if (be.cookingProgress >= be.cookingTime) {
                be.cookingProgress = 0;

                ItemStack result = recipe.assemble(input, level.registryAccess());
                ItemStack output = be.items.get(OvenMenu.OUTPUT_SLOT);

                boolean canOutput = output.isEmpty()
                        || (ItemStack.isSameItemSameComponents(output, result)
                        && output.getCount() + result.getCount() <= output.getMaxStackSize());

                if (canOutput) {
                    if (output.isEmpty()) {
                        be.items.set(OvenMenu.OUTPUT_SLOT, result.copy());
                    } else {
                        output.grow(result.getCount());
                    }
                    consumeIngredients(be);
                    be.setChanged();
                }
            }
        } else if (!hasRecipe) {
            // No matching recipe — reset progress so the arrow doesn't stick.
            be.cookingProgress = 0;
            be.cookingTime     = 0;
        }

        // ── Update LIT block state ────────────────────────────────────────
        boolean isLit = be.burnTime > 0;
        if (wasLit != isLit) {
            level.setBlock(pos, state.setValue(AbstractFurnaceBlock.LIT, isLit), 3);
            be.setChanged();
        }
    }

    /** Shrink every non-empty ingredient slot by 1 after a successful craft. */
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