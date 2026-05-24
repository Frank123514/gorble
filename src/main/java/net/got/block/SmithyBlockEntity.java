package net.got.block;

import net.got.init.GotModBlockEntities;
import net.got.init.GotModRecipeTypes;
import net.got.menu.SmithyMenu;
import net.got.recipe.SmithyRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SmithyBlockEntity — powers the Smithy block.
 *
 * Slot layout:
 *   0 — input  (the ingot / source material)
 *   1 — fuel
 *   2 — output
 *
 * ContainerData layout:
 *   0 — cookingProgress
 *   1 — cookingTotalTime
 *   2 — litTime
 *   3 — litDuration
 *   4 — selectedRecipeIndex  (-1 = none)
 */
public class SmithyBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    // ── Slot indices ──────────────────────────────────────────────────────────
    public static final int SLOT_INPUT  = 0;
    public static final int SLOT_FUEL   = 1;
    public static final int SLOT_OUTPUT = 2;
    public static final int NUM_SLOTS   = 3;

    // ── ContainerData indices ─────────────────────────────────────────────────
    public static final int DATA_COOKING_PROGRESS  = 0;
    public static final int DATA_COOKING_TOTAL     = 1;
    public static final int DATA_LIT_TIME          = 2;
    public static final int DATA_LIT_DURATION      = 3;
    public static final int DATA_SELECTED_RECIPE   = 4;
    public static final int NUM_DATA               = 5;

    // ── State ─────────────────────────────────────────────────────────────────
    private NonNullList<ItemStack> items =
            NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);
    private int cookingProgress   = 0;
    private int cookingTotalTime  = 200;
    private int litTime           = 0;
    private int litDuration       = 0;
    private int selectedRecipeIdx = -1;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int i) {
            return switch (i) {
                case DATA_COOKING_PROGRESS -> cookingProgress;
                case DATA_COOKING_TOTAL    -> cookingTotalTime;
                case DATA_LIT_TIME         -> litTime;
                case DATA_LIT_DURATION     -> litDuration;
                case DATA_SELECTED_RECIPE  -> selectedRecipeIdx;
                default -> 0;
            };
        }
        @Override
        public void set(int i, int v) {
            switch (i) {
                case DATA_COOKING_PROGRESS -> cookingProgress   = v;
                case DATA_COOKING_TOTAL    -> cookingTotalTime  = v;
                case DATA_LIT_TIME         -> litTime           = v;
                case DATA_LIT_DURATION     -> litDuration       = v;
                case DATA_SELECTED_RECIPE  -> selectedRecipeIdx = v;
            }
        }
        @Override
        public int getCount() { return NUM_DATA; }
    };

    public SmithyBlockEntity(BlockPos pos, BlockState state) {
        super(GotModBlockEntities.SMITHY.get(), pos, state);
    }

    // ── Server tick ───────────────────────────────────────────────────────────

    public static void serverTick(Level level, BlockPos pos, BlockState state,
                                  SmithyBlockEntity be) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        boolean wasLit = be.isLit();
        boolean dirty  = false;

        if (be.isLit()) be.litTime--;

        // If input changes or is empty, invalidate selected recipe
        if (be.items.get(SLOT_INPUT).isEmpty()) {
            if (be.selectedRecipeIdx != -1) {
                be.selectedRecipeIdx = -1;
                be.cookingProgress = 0;
                dirty = true;
            }
        }

        // Find the selected recipe
        RecipeHolder<SmithyRecipe> recipe = be.getSelectedRecipe(serverLevel);

        if (recipe != null) {
            // Try to light from fuel
            if (!be.isLit() && be.canBurn(recipe) && be.hasFuel(level)) {
                be.litDuration = be.getBurnDuration(level, be.items.get(SLOT_FUEL));
                be.litTime     = be.litDuration;
                if (be.isLit()) {
                    dirty = true;
                    ItemStack fuel = be.items.get(SLOT_FUEL);
                    if (fuel.getItem() == Items.LAVA_BUCKET) {
                        be.items.set(SLOT_FUEL, new ItemStack(Items.BUCKET));
                    } else {
                        fuel.shrink(1);
                        if (fuel.isEmpty()) be.items.set(SLOT_FUEL, ItemStack.EMPTY);
                    }
                }
            }

            if (be.isLit() && be.canBurn(recipe)) {
                be.cookingProgress++;
                if (be.cookingProgress >= be.cookingTotalTime) {
                    be.cookingProgress = 0;
                    be.cookingTotalTime = recipe.value().getCookingTime();
                    if (be.burn(recipe)) {
                        dirty = true;
                        // Keep selectedRecipeIdx so it keeps producing (stonecutter-style)
                    }
                }
            } else if (!be.isLit()) {
                be.cookingProgress = Math.max(0, be.cookingProgress - 2);
            }
        } else {
            // No valid recipe selected — slowly cool down progress
            if (be.cookingProgress > 0) {
                be.cookingProgress = Math.max(0, be.cookingProgress - 2);
                dirty = true;
            }
        }

        if (wasLit != be.isLit()) {
            dirty = true;
            level.setBlock(pos, state.setValue(SmithyBlock.LIT, be.isLit()), 3);
        }

        if (dirty) setChanged(level, pos, state);
    }

    // ── Internal helpers ──────────────────────────────────────────────────────

    public boolean isLit() { return litTime > 0; }

    private boolean hasFuel(Level level) {
        return getBurnDuration(level, items.get(SLOT_FUEL)) > 0;
    }

    private int getBurnDuration(Level level, ItemStack stack) {
        if (stack.isEmpty()) return 0;
        return stack.getBurnTime(GotModRecipeTypes.SMITHY.get(), level.fuelValues());
    }

    private boolean canBurn(RecipeHolder<SmithyRecipe> recipe) {
        ItemStack result = recipe.value().getResult();
        ItemStack output = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(output, result)) return false;
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private boolean burn(RecipeHolder<SmithyRecipe> recipe) {
        if (!canBurn(recipe)) return false;
        ItemStack result = recipe.value().getResult().copy();
        ItemStack output = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) {
            items.set(SLOT_OUTPUT, result);
        } else {
            output.grow(result.getCount());
        }
        // Consume one item from input
        ItemStack input = items.get(SLOT_INPUT);
        input.shrink(1);
        if (input.isEmpty()) items.set(SLOT_INPUT, ItemStack.EMPTY);
        return true;
    }

    /**
     * Returns the currently selected recipe if it still matches the input,
     * or null if nothing is selected or it no longer matches.
     */
    @Nullable
    private RecipeHolder<SmithyRecipe> getSelectedRecipe(ServerLevel level) {
        if (selectedRecipeIdx < 0) return null;
        List<RecipeHolder<SmithyRecipe>> matching = getMatchingRecipes(level);
        if (selectedRecipeIdx >= matching.size()) {
            selectedRecipeIdx = -1;
            return null;
        }
        return matching.get(selectedRecipeIdx);
    }

    /** All smithy recipes matching the current input, sorted by resource-location string. */
    public List<RecipeHolder<SmithyRecipe>> getMatchingRecipes(Level level) {
        ItemStack input = items.get(SLOT_INPUT);
        if (input.isEmpty()) return List.of();
        if (!(level instanceof ServerLevel serverLevel)) return List.of();
        if (!(serverLevel.recipeAccess() instanceof RecipeManager rm)) return List.of();
        SingleRecipeInput recipeInput = new SingleRecipeInput(input);
        return rm.getAllRecipesFor(GotModRecipeTypes.SMITHY.get())
                .stream()
                .filter(h -> h.value().matches(recipeInput, serverLevel))
                .sorted(Comparator.comparing(h -> h.id().toString()))
                .collect(Collectors.toList());
    }

    // ── Public API used by menu / network ─────────────────────────────────────

    public void setSelectedRecipeIndex(int idx) {
        this.selectedRecipeIdx = idx;
        this.cookingProgress   = 0;
        this.cookingTotalTime  = 200;
        setChanged();
    }

    public ItemStack getInputItem() { return items.get(SLOT_INPUT); }

    public ContainerData getDataAccess() { return dataAccess; }

    // ── Container ─────────────────────────────────────────────────────────────

    @Override public NonNullList<ItemStack> getItems()              { return items; }
    @Override public void setItems(NonNullList<ItemStack> items)    { this.items = items; }
    @Override public int  getContainerSize()                        { return NUM_SLOTS; }
    @Override public boolean isEmpty() { return items.stream().allMatch(ItemStack::isEmpty); }
    @Override public ItemStack getItem(int slot)                    { return items.get(slot); }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) stack.setCount(getMaxStackSize());
        // Input changed → reset recipe selection
        if (slot == SLOT_INPUT) {
            selectedRecipeIdx = -1;
            cookingProgress   = 0;
            setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override public void clearContent() { items.clear(); }

    // ── WorldlyContainer ──────────────────────────────────────────────────────

    private static final int[] SLOTS_TOP    = { SLOT_INPUT };
    private static final int[] SLOTS_BOTTOM = { SLOT_OUTPUT, SLOT_FUEL };
    private static final int[] SLOTS_SIDE   = { SLOT_FUEL };

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN)  return SLOTS_BOTTOM;
        if (side == Direction.UP)    return SLOTS_TOP;
        return SLOTS_SIDE;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == SLOT_OUTPUT;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == SLOT_OUTPUT) return false;
        if (slot == SLOT_FUEL)
            return level != null && getBurnDuration(level, stack) > 0;
        return true;
    }

    // ── Menu ─────────────────────────────────────────────────────────────────

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.got.smithy");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new SmithyMenu(id, inventory, this, dataAccess);
    }

    // ── NBT ──────────────────────────────────────────────────────────────────

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items = NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);
        cookingProgress   = tag.getInt("CookTime");
        cookingTotalTime  = tag.getInt("CookTimeTotal");
        litTime           = tag.getInt("BurnTime");
        litDuration       = tag.getInt("BurnDuration");
        selectedRecipeIdx = tag.getInt("SelectedRecipe");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("CookTime",       cookingProgress);
        tag.putInt("CookTimeTotal",  cookingTotalTime);
        tag.putInt("BurnTime",       litTime);
        tag.putInt("BurnDuration",   litDuration);
        tag.putInt("SelectedRecipe", selectedRecipeIdx);
    }
}