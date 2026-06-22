package net.got.block;

import net.got.init.GotModBlockEntities;
import net.got.init.GotModRecipeTypes;
import net.got.menu.SmithingAnvilMenu;
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
 * SmithingAnvilBlockEntity — powers the Smithing Anvil block.
 * <p>
 * Accepts a heated (malleable) metal ingot from the Forge's heat-treating mode
 * and a fuel source. The player selects a SmithyRecipe from the recipe panel
 * and the anvil processes the ingot into a finished item.
 * <p>
 * Slot layout:
 *   0 — smithing input  (the heated ingot / source material)
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
public class SmithingAnvilBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    // ── Slot indices ──────────────────────────────────────────────────────────
    public static final int SLOT_INPUT  = 0;
    public static final int SLOT_FUEL   = 1;
    public static final int SLOT_OUTPUT = 2;
    public static final int NUM_SLOTS   = 3;

    // ── ContainerData indices ─────────────────────────────────────────────────
    public static final int DATA_COOKING_PROGRESS = 0;
    public static final int DATA_COOKING_TOTAL    = 1;
    public static final int DATA_LIT_TIME         = 2;
    public static final int DATA_LIT_DURATION     = 3;
    public static final int DATA_SELECTED_RECIPE  = 4;
    public static final int NUM_DATA              = 5;

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
                case DATA_LIT_DURATION     -> litDuration        = v;
                case DATA_SELECTED_RECIPE  -> selectedRecipeIdx  = v;
            }
        }
        @Override
        public int getCount() { return NUM_DATA; }
    };

    public SmithingAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(GotModBlockEntities.SMITHING_ANVIL.get(), pos, state);
    }

    // ── Server tick ───────────────────────────────────────────────────────────

    public static void serverTick(Level level, BlockPos pos, BlockState state,
                                  SmithingAnvilBlockEntity be) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        boolean wasLit = be.isLit();
        boolean dirty  = false;

        if (be.isLit()) be.litTime--;

        dirty |= be.tickSmithing(serverLevel);

        if (wasLit != be.isLit()) {
            dirty = true;
            level.setBlock(pos, state.setValue(SmithingAnvilBlock.LIT, be.isLit()), 3);
        }

        if (dirty) setChanged(level, pos, state);
    }

    // ── Smithing tick ─────────────────────────────────────────────────────────

    private boolean tickSmithing(ServerLevel level) {
        boolean dirty = false;

        if (items.get(SLOT_INPUT).isEmpty()) {
            if (selectedRecipeIdx != -1) {
                selectedRecipeIdx = -1;
                cookingProgress = 0;
                dirty = true;
            }
        }

        RecipeHolder<SmithyRecipe> recipe = getSelectedSmithingRecipe(level);

        if (recipe != null) {
            if (!isLit() && canBurn(recipe) && hasFuel(level)) {
                dirty |= lightFuel(level);
            }

            if (isLit() && canBurn(recipe)) {
                cookingProgress++;
                if (cookingProgress >= cookingTotalTime) {
                    cookingProgress = 0;
                    cookingTotalTime = recipe.value().getCookingTime();
                    if (burn(recipe)) dirty = true;
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
        ItemStack input = items.get(SLOT_INPUT);
        input.shrink(1);
        if (input.isEmpty()) items.set(SLOT_INPUT, ItemStack.EMPTY);
        return true;
    }

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

    // ── Public API used by menus / network ────────────────────────────────────

    public void setSelectedRecipeIndex(int idx) {
        this.selectedRecipeIdx = idx;
        this.cookingProgress   = 0;
        this.cookingTotalTime  = 200;
        setChanged();
    }

    public ItemStack getInputItem() { return items.get(SLOT_INPUT); }

    public ContainerData getDataAccess() { return dataAccess; }

    // ── Container ─────────────────────────────────────────────────────────────

    @Override public NonNullList<ItemStack> getItems()           { return items; }
    @Override public void setItems(NonNullList<ItemStack> items) { this.items = items; }
    @Override public int  getContainerSize()                     { return NUM_SLOTS; }
    @Override public boolean isEmpty() { return items.stream().allMatch(ItemStack::isEmpty); }
    @Override public ItemStack getItem(int slot)                 { return items.get(slot); }

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
        return Component.translatable("container.got.smithing_anvil");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new SmithingAnvilMenu(id, inventory, this, dataAccess);
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