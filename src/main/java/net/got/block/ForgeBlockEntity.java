package net.got.block;

import net.got.init.GotModBlockEntities;
import net.got.init.GotModRecipeTypes;
import net.got.menu.AlloyMenu;
import net.got.menu.SmithyMenu;
import net.got.recipe.AlloyRecipe;
import net.got.recipe.AlloyRecipeInput;
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
 * ForgeBlockEntity — powers the Forge block.
 * <p>
 * The Forge has two independent processing modes that the player switches
 * between in the GUI:
 * <ul>
 *   <li><b>Smithing</b> — melts one ingot and crafts it into an item made of
 *       that metal (the original Smithy behaviour, unchanged — see
 *       {@link SmithyMenu} / SmithyScreen / {@link SmithyRecipe}).</li>
 *   <li><b>Alloying</b> — melts and combines two metals into a new metal
 *       (e.g. copper + tin -> bronze), which can then be used in Smithing
 *       mode. See {@link AlloyMenu} / AlloyScreen / {@link AlloyRecipe}.</li>
 * </ul>
 * Both modes share the same fuel and output slot; each mode has its own
 * input slot(s) so switching modes never discards items already loaded.
 *
 * Slot layout:
 *   0 — smithing input  (the ingot / source material)
 *   1 — fuel
 *   2 — output (shared by both modes)
 *   3 — alloy input A
 *   4 — alloy input B
 *
 * ContainerData layout:
 *   0 — cookingProgress
 *   1 — cookingTotalTime
 *   2 — litTime
 *   3 — litDuration
 *   4 — selectedRecipeIndex  (-1 = none; meaning depends on current mode)
 *   5 — mode (0 = SMITHING, 1 = ALLOYING)
 */
public class ForgeBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    // ── Modes ─────────────────────────────────────────────────────────────────
    public static final int MODE_SMITHING = 0;
    public static final int MODE_ALLOYING = 1;

    // ── Slot indices ──────────────────────────────────────────────────────────
    public static final int SLOT_INPUT      = 0; // smithing input (kept name for SmithyMenu compatibility)
    public static final int SLOT_FUEL       = 1; // shared fuel slot
    public static final int SLOT_OUTPUT     = 2; // shared output slot
    public static final int SLOT_ALLOY_A    = 3;
    public static final int SLOT_ALLOY_B    = 4;
    public static final int NUM_SLOTS       = 5;

    // ── ContainerData indices ─────────────────────────────────────────────────
    public static final int DATA_COOKING_PROGRESS  = 0;
    public static final int DATA_COOKING_TOTAL     = 1;
    public static final int DATA_LIT_TIME          = 2;
    public static final int DATA_LIT_DURATION      = 3;
    public static final int DATA_SELECTED_RECIPE   = 4;
    public static final int DATA_MODE              = 5;
    public static final int NUM_DATA               = 6;

    // ── State ─────────────────────────────────────────────────────────────────
    private NonNullList<ItemStack> items =
            NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);
    private int cookingProgress   = 0;
    private int cookingTotalTime  = 200;
    private int litTime           = 0;
    private int litDuration       = 0;
    private int selectedRecipeIdx = -1;
    private int mode              = MODE_SMITHING;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int i) {
            return switch (i) {
                case DATA_COOKING_PROGRESS -> cookingProgress;
                case DATA_COOKING_TOTAL    -> cookingTotalTime;
                case DATA_LIT_TIME         -> litTime;
                case DATA_LIT_DURATION     -> litDuration;
                case DATA_SELECTED_RECIPE  -> selectedRecipeIdx;
                case DATA_MODE             -> mode;
                default -> 0;
            };
        }
        @Override
        public void set(int i, int v) {
            switch (i) {
                case DATA_COOKING_PROGRESS -> cookingProgress   = v;
                case DATA_COOKING_TOTAL    -> cookingTotalTime  = v;
                case DATA_LIT_TIME         -> litTime           = v;
                case DATA_LIT_DURATION     -> litDuration        = v;
                case DATA_SELECTED_RECIPE  -> selectedRecipeIdx  = v;
                case DATA_MODE             -> mode               = v;
            }
        }
        @Override
        public int getCount() { return NUM_DATA; }
    };

    public ForgeBlockEntity(BlockPos pos, BlockState state) {
        super(GotModBlockEntities.FORGE.get(), pos, state);
    }

    // ── Server tick ───────────────────────────────────────────────────────────

    public static void serverTick(Level level, BlockPos pos, BlockState state,
                                  ForgeBlockEntity be) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        boolean wasLit = be.isLit();
        boolean dirty  = false;

        if (be.isLit()) be.litTime--;

        if (be.mode == MODE_SMITHING) {
            dirty |= be.tickSmithing(serverLevel);
        } else {
            dirty |= be.tickAlloying(serverLevel);
        }

        if (wasLit != be.isLit()) {
            dirty = true;
            level.setBlock(pos, state.setValue(ForgeBlock.LIT, be.isLit()), 3);
        }

        if (dirty) setChanged(level, pos, state);
    }

    // ── Smithing mode tick ────────────────────────────────────────────────────

    private boolean tickSmithing(ServerLevel level) {
        boolean dirty = false;

        // If input changes or is empty, invalidate selected recipe
        if (items.get(SLOT_INPUT).isEmpty()) {
            if (selectedRecipeIdx != -1) {
                selectedRecipeIdx = -1;
                cookingProgress = 0;
                dirty = true;
            }
        }

        RecipeHolder<SmithyRecipe> recipe = getSelectedSmithingRecipe(level);

        if (recipe != null) {
            if (!isLit() && canBurnSmithing(recipe) && hasFuel(level)) {
                dirty |= lightFuel(level);
            }

            if (isLit() && canBurnSmithing(recipe)) {
                cookingProgress++;
                if (cookingProgress >= cookingTotalTime) {
                    cookingProgress = 0;
                    cookingTotalTime = recipe.value().getCookingTime();
                    if (burnSmithing(recipe)) dirty = true;
                }
            } else if (!isLit()) {
                cookingProgress = Math.max(0, cookingProgress - 2);
            }
        } else if (cookingProgress > 0) {
            cookingProgress = Math.max(0, cookingProgress - 2);
            dirty = true;
        }

        return dirty;
    }

    // ── Alloying mode tick ────────────────────────────────────────────────────

    private boolean tickAlloying(ServerLevel level) {
        boolean dirty = false;

        if (items.get(SLOT_ALLOY_A).isEmpty() || items.get(SLOT_ALLOY_B).isEmpty()) {
            if (selectedRecipeIdx != -1) {
                selectedRecipeIdx = -1;
                cookingProgress = 0;
                dirty = true;
            }
        }

        RecipeHolder<AlloyRecipe> recipe = getSelectedAlloyRecipe(level);

        if (recipe != null) {
            if (!isLit() && canBurnAlloy(recipe) && hasFuel(level)) {
                dirty |= lightFuel(level);
            }

            if (isLit() && canBurnAlloy(recipe)) {
                cookingProgress++;
                if (cookingProgress >= cookingTotalTime) {
                    cookingProgress = 0;
                    cookingTotalTime = recipe.value().getCookingTime();
                    if (burnAlloy(recipe)) dirty = true;
                }
            } else if (!isLit()) {
                cookingProgress = Math.max(0, cookingProgress - 2);
            }
        } else if (cookingProgress > 0) {
            cookingProgress = Math.max(0, cookingProgress - 2);
            dirty = true;
        }

        return dirty;
    }

    /** Lights the fuel slot. Returns true if state changed (now lit). */
    private boolean lightFuel(Level level) {
        litDuration = getBurnDuration(level, items.get(SLOT_FUEL));
        litTime     = litDuration;
        if (!isLit()) return false;
        ItemStack fuel = items.get(SLOT_FUEL);
        if (fuel.getItem() == Items.LAVA_BUCKET) {
            items.set(SLOT_FUEL, new ItemStack(Items.BUCKET));
        } else {
            fuel.shrink(1);
            if (fuel.isEmpty()) items.set(SLOT_FUEL, ItemStack.EMPTY);
        }
        return true;
    }

    // ── Internal helpers ──────────────────────────────────────────────────────

    public boolean isLit() { return litTime > 0; }

    public int getMode() { return mode; }

    public void setMode(int mode) {
        this.mode = mode;
        setChanged();
    }

    private boolean hasFuel(Level level) {
        return getBurnDuration(level, items.get(SLOT_FUEL)) > 0;
    }

    private int getBurnDuration(Level level, ItemStack stack) {
        if (stack.isEmpty()) return 0;
        return stack.getBurnTime(GotModRecipeTypes.SMITHY.get(), level.fuelValues());
    }

    private boolean canBurnSmithing(RecipeHolder<SmithyRecipe> recipe) {
        return canProduce(recipe.value().getResult());
    }

    private boolean canBurnAlloy(RecipeHolder<AlloyRecipe> recipe) {
        return canProduce(recipe.value().getResult());
    }

    private boolean canProduce(ItemStack result) {
        ItemStack output = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(output, result)) return false;
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private boolean burnSmithing(RecipeHolder<SmithyRecipe> recipe) {
        if (!canBurnSmithing(recipe)) return false;
        depositOutput(recipe.value().getResult());
        ItemStack input = items.get(SLOT_INPUT);
        input.shrink(1);
        if (input.isEmpty()) items.set(SLOT_INPUT, ItemStack.EMPTY);
        return true;
    }

    private boolean burnAlloy(RecipeHolder<AlloyRecipe> recipe) {
        if (!canBurnAlloy(recipe)) return false;
        depositOutput(recipe.value().getResult());
        ItemStack a = items.get(SLOT_ALLOY_A);
        ItemStack b = items.get(SLOT_ALLOY_B);
        a.shrink(1);
        b.shrink(1);
        if (a.isEmpty()) items.set(SLOT_ALLOY_A, ItemStack.EMPTY);
        if (b.isEmpty()) items.set(SLOT_ALLOY_B, ItemStack.EMPTY);
        return true;
    }

    private void depositOutput(ItemStack result) {
        ItemStack result2 = result.copy();
        ItemStack output = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) {
            items.set(SLOT_OUTPUT, result2);
        } else {
            output.grow(result2.getCount());
        }
    }

    /**
     * Returns the currently selected smithing recipe if it still matches the
     * input, or null if nothing is selected or it no longer matches.
     */
    @Nullable
    private RecipeHolder<SmithyRecipe> getSelectedSmithingRecipe(ServerLevel level) {
        if (selectedRecipeIdx < 0) return null;
        List<RecipeHolder<SmithyRecipe>> matching = getMatchingSmithingRecipes(level);
        if (selectedRecipeIdx >= matching.size()) {
            selectedRecipeIdx = -1;
            return null;
        }
        return matching.get(selectedRecipeIdx);
    }

    @Nullable
    private RecipeHolder<AlloyRecipe> getSelectedAlloyRecipe(ServerLevel level) {
        if (selectedRecipeIdx < 0) return null;
        List<RecipeHolder<AlloyRecipe>> matching = getMatchingAlloyRecipes(level);
        if (selectedRecipeIdx >= matching.size()) {
            selectedRecipeIdx = -1;
            return null;
        }
        return matching.get(selectedRecipeIdx);
    }

    /** All smithing recipes matching the current input, sorted by resource-location string. */
    public List<RecipeHolder<SmithyRecipe>> getMatchingSmithingRecipes(Level level) {
        ItemStack input = items.get(SLOT_INPUT);
        if (input.isEmpty()) return List.of();
        if (!(level instanceof ServerLevel serverLevel)) return List.of();
        if (!(serverLevel.recipeAccess() instanceof RecipeManager rm)) return List.of();

        SingleRecipeInput recipeInput = new SingleRecipeInput(input);
        return rm.recipeMap().byType(GotModRecipeTypes.SMITHY.get()).stream()
                .filter(h -> h.value().matches(recipeInput, serverLevel))
                .sorted(Comparator.comparing(h -> h.id().toString()))
                .collect(Collectors.toList());
    }

    /** All alloy recipes matching the current pair of inputs, sorted by resource-location string. */
    public List<RecipeHolder<AlloyRecipe>> getMatchingAlloyRecipes(Level level) {
        ItemStack a = items.get(SLOT_ALLOY_A);
        ItemStack b = items.get(SLOT_ALLOY_B);
        if (a.isEmpty() || b.isEmpty()) return List.of();
        if (!(level instanceof ServerLevel serverLevel)) return List.of();
        if (!(serverLevel.recipeAccess() instanceof RecipeManager rm)) return List.of();

        AlloyRecipeInput recipeInput = new AlloyRecipeInput(a, b);
        return rm.recipeMap().byType(GotModRecipeTypes.ALLOY.get()).stream()
                .filter(h -> h.value().matches(recipeInput, serverLevel))
                .sorted(Comparator.comparing(h -> h.id().toString()))
                .collect(Collectors.toList());
    }

    // ── Public API used by menus / network ─────────────────────────────────────

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
        if (slot == SLOT_INPUT || slot == SLOT_ALLOY_A || slot == SLOT_ALLOY_B) {
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

    private static final int[] SLOTS_TOP    = { SLOT_INPUT, SLOT_ALLOY_A, SLOT_ALLOY_B };
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
        return Component.translatable("container.got.forge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return mode == MODE_ALLOYING
                ? new AlloyMenu(id, inventory, this, dataAccess)
                : new SmithyMenu(id, inventory, this, dataAccess);
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
        mode              = tag.getInt("Mode");
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
        tag.putInt("Mode",           mode);
    }
}
