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
 * Accepts a heated (malleable) metal ingot from the Forge's heat-treating mode.
 * The player selects a SmithyRecipe from the recipe panel, then must strike the
 * anvil with a {@link net.got.item.SmithingHammerItem} three times to actually
 * work the ingot into the finished item — no other tool will do, and there's
 * no passive cooking timer involved.
 * <p>
 * Slot layout:
 *   0 — smithing input  (the heated ingot / source material)
 *   1 — output
 *
 * ContainerData layout:
 *   0 — hitCount            (0–HITS_REQUIRED)
 *   1 — selectedRecipeIndex (-1 = none)
 */
public class SmithingAnvilBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    /** Number of hammer strikes needed to finish a recipe. */
    public static final int HITS_REQUIRED = 3;

    // ── Slot indices ──────────────────────────────────────────────────────────
    public static final int SLOT_INPUT  = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int NUM_SLOTS   = 2;

    // ── ContainerData indices ─────────────────────────────────────────────────
    public static final int DATA_HIT_COUNT        = 0;
    public static final int DATA_SELECTED_RECIPE  = 1;
    public static final int NUM_DATA              = 2;

    // ── State ─────────────────────────────────────────────────────────────────
    private NonNullList<ItemStack> items =
            NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);
    private int hitCount          = 0;
    private int selectedRecipeIdx = -1;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int i) {
            return switch (i) {
                case DATA_HIT_COUNT        -> hitCount;
                case DATA_SELECTED_RECIPE  -> selectedRecipeIdx;
                default -> 0;
            };
        }
        @Override
        public void set(int i, int v) {
            switch (i) {
                case DATA_HIT_COUNT        -> hitCount          = v;
                case DATA_SELECTED_RECIPE  -> selectedRecipeIdx = v;
            }
        }
        @Override
        public int getCount() { return NUM_DATA; }
    };

    public SmithingAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(GotModBlockEntities.SMITHING_ANVIL.get(), pos, state);
    }

    // ── Hammer strikes ────────────────────────────────────────────────────────

    /**
     * Called from {@link SmithingAnvilBlock#attack} when the player hits this
     * anvil while holding a {@link net.got.item.SmithingHammerItem}.
     * <p>
     * Returns the outcome so the block can play the right sound/particles:
     * a hit that doesn't complete the recipe yet, a hit that finishes it,
     * or no-op because there's nothing to work on.
     */
    public HitResult hitWithHammer(ServerLevel level) {
        RecipeHolder<SmithyRecipe> recipe = getSelectedSmithingRecipe(level);
        if (items.get(SLOT_INPUT).isEmpty() || recipe == null || !canBurn(recipe)) {
            return HitResult.NOTHING_TO_WORK;
        }

        hitCount++;
        if (hitCount >= HITS_REQUIRED) {
            hitCount = 0;
            boolean crafted = burn(recipe);
            setChanged();
            return crafted ? HitResult.COMPLETED : HitResult.NOTHING_TO_WORK;
        }

        setChanged();
        return HitResult.PROGRESSED;
    }

    public enum HitResult { NOTHING_TO_WORK, PROGRESSED, COMPLETED }

    public int getHitCount() { return hitCount; }

    // ── Internal helpers ──────────────────────────────────────────────────────

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
        this.hitCount          = 0;
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
            hitCount          = 0;
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
    private static final int[] SLOTS_BOTTOM = { SLOT_OUTPUT };
    private static final int[] SLOTS_SIDE   = { SLOT_INPUT };

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
        return slot != SLOT_OUTPUT;
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
        hitCount          = tag.getInt("HitCount");
        selectedRecipeIdx = tag.getInt("SelectedRecipe");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("HitCount",       hitCount);
        tag.putInt("SelectedRecipe", selectedRecipeIdx);
    }
}