package net.got.block;

import net.got.init.GotModBlockEntities;
import net.got.init.GotModRecipeTypes;
import net.got.menu.OvenMenu;
import net.got.recipe.OvenRecipe;
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
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * OvenBlockEntity — ported from OFAW (1.16.5) to NeoForge 1.21.4.
 *
 * Slot layout (11 slots total, matching OFAW):
 *   0–8  — 3×3 shaped input grid
 *   9    — output
 *   10   — fuel
 *
 * ContainerData layout:
 *   0 — cookingProgress
 *   1 — cookingTotalTime (maxProgress)
 *   2 — litTime
 *   3 — litDuration
 */
public class OvenBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    // ── Slot indices ──────────────────────────────────────────────────────────

    public static final int NUM_INPUT_SLOTS = 9;
    public static final int SLOT_OUTPUT     = 9;
    public static final int SLOT_FUEL       = 10;
    public static final int NUM_SLOTS       = 11;

    // ── ContainerData indices ─────────────────────────────────────────────────

    public static final int DATA_COOKING_PROGRESS = 0;
    public static final int DATA_COOKING_TOTAL    = 1;
    public static final int DATA_LIT_TIME         = 2;
    public static final int DATA_LIT_DURATION     = 3;
    public static final int NUM_DATA              = 4;

    // ── State ─────────────────────────────────────────────────────────────────

    private NonNullList<ItemStack> items =
            NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);

    private int cookingProgress  = 0;
    private int cookingTotalTime = 200;
    private int litTime          = 0;
    private int litDuration      = 0;

    private final RecipeManager.CachedCheck<CraftingInput, OvenRecipe> quickCheck;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int i) {
            return switch (i) {
                case DATA_COOKING_PROGRESS -> cookingProgress;
                case DATA_COOKING_TOTAL    -> cookingTotalTime;
                case DATA_LIT_TIME         -> litTime;
                case DATA_LIT_DURATION     -> litDuration;
                default -> 0;
            };
        }

        @Override
        public void set(int i, int v) {
            switch (i) {
                case DATA_COOKING_PROGRESS -> cookingProgress  = v;
                case DATA_COOKING_TOTAL    -> cookingTotalTime = v;
                case DATA_LIT_TIME         -> litTime          = v;
                case DATA_LIT_DURATION     -> litDuration      = v;
            }
        }

        @Override
        public int getCount() { return NUM_DATA; }
    };

    public OvenBlockEntity(BlockPos pos, BlockState state) {
        super(GotModBlockEntities.OVEN.get(), pos, state);
        this.quickCheck = RecipeManager.createCheck(GotModRecipeTypes.OVEN.get());
    }

    // ── Tick ─────────────────────────────────────────────────────────────────

    public static void serverTick(Level level, BlockPos pos, BlockState state,
                                  OvenBlockEntity be) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        boolean wasLit = be.isLit();
        boolean dirty  = false;

        if (be.isLit()) be.litTime--;

        CraftingInput input = be.buildCraftingInput();
        Optional<RecipeHolder<OvenRecipe>> recipe = be.quickCheck.getRecipeFor(input, serverLevel);

        if (be.isLit() || (recipe.isPresent() && be.hasFuel(level))) {
            if (!be.isLit() && be.canBurn(serverLevel, recipe, input)) {
                be.litDuration = be.getBurnDuration(level, be.items.get(SLOT_FUEL));
                be.litTime     = be.litDuration;
                if (be.isLit()) {
                    dirty = true;
                    ItemStack fuel = be.items.get(SLOT_FUEL);
                    // Consume fuel; lava buckets leave an empty bucket
                    if (fuel.getItem() == Items.LAVA_BUCKET) {
                        be.items.set(SLOT_FUEL, new ItemStack(Items.BUCKET));
                    } else {
                        fuel.shrink(1);
                        if (fuel.isEmpty()) be.items.set(SLOT_FUEL, ItemStack.EMPTY);
                    }
                }
            }

            if (be.isLit() && be.canBurn(serverLevel, recipe, input)) {
                be.cookingProgress++;
                if (be.cookingProgress >= be.cookingTotalTime) {
                    be.cookingProgress   = 0;
                    be.cookingTotalTime  = be.getRecipeCookTime(serverLevel);
                    if (be.burn(serverLevel, recipe, input)) dirty = true;
                }
            } else {
                be.cookingProgress = 0;
            }
        } else if (!be.isLit() && be.cookingProgress > 0) {
            be.cookingProgress = Math.max(0, be.cookingProgress - 2);
        }

        if (wasLit != be.isLit()) {
            dirty = true;
            level.setBlock(pos, state.setValue(OvenBlock.LIT, be.isLit()), 3);
        }

        if (dirty) setChanged(level, pos, state);
    }

    // ── Internal helpers ──────────────────────────────────────────────────────

    /** Build a 3×3 CraftingInput from the 9 input slots. */
    private CraftingInput buildCraftingInput() {
        java.util.List<ItemStack> grid = new java.util.ArrayList<>(NUM_INPUT_SLOTS);
        for (int i = 0; i < NUM_INPUT_SLOTS; i++) grid.add(items.get(i));
        return CraftingInput.of(3, 3, grid);
    }

    public boolean isLit() { return litTime > 0; }

    private boolean hasFuel(Level level) {
        return getBurnDuration(level, items.get(SLOT_FUEL)) > 0;
    }

    private int getBurnDuration(Level level, ItemStack stack) {
        if (stack.isEmpty()) return 0;
        return stack.getBurnTime(GotModRecipeTypes.OVEN.get(), level.fuelValues());
    }

    private boolean canBurn(ServerLevel level,
                             Optional<RecipeHolder<OvenRecipe>> recipe,
                             CraftingInput input) {
        if (recipe.isEmpty()) return false;
        ItemStack result = recipe.get().value().assemble(input, level.registryAccess());
        if (result.isEmpty()) return false;

        ItemStack output = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(output, result)) return false;
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    /**
     * Perform one craft cycle: consume one item from each non-empty input slot,
     * deposit the result into the output slot. Buckets (water/lava/milk) are
     * replaced with empty buckets (matching OFAW's behaviour).
     */
    private boolean burn(ServerLevel level,
                         Optional<RecipeHolder<OvenRecipe>> recipe,
                         CraftingInput input) {
        if (recipe.isEmpty() || !canBurn(level, recipe, input)) return false;

        ItemStack result = recipe.get().value().assemble(input, level.registryAccess());
        ItemStack output = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) {
            items.set(SLOT_OUTPUT, result.copy());
        } else if (ItemStack.isSameItemSameComponents(output, result)) {
            output.grow(result.getCount());
        }

        // Consume one item from each occupied input slot
        for (int i = 0; i < NUM_INPUT_SLOTS; i++) {
            ItemStack s = items.get(i);
            if (!s.isEmpty()) {
                if (s.getItem() == Items.WATER_BUCKET
                        || s.getItem() == Items.LAVA_BUCKET
                        || s.getItem() == Items.MILK_BUCKET) {
                    items.set(i, new ItemStack(Items.BUCKET));
                } else {
                    s.shrink(1);
                    if (s.isEmpty()) items.set(i, ItemStack.EMPTY);
                }
            }
        }
        return true;
    }

    private int getRecipeCookTime(ServerLevel level) {
        CraftingInput input = buildCraftingInput();
        return quickCheck.getRecipeFor(input, level)
                .map(r -> r.value().getCookingTime())
                .orElse(200);
    }

    // ── Container ─────────────────────────────────────────────────────────────

    @Override public NonNullList<ItemStack> getItems() { return items; }
    @Override public void setItems(NonNullList<ItemStack> items) { this.items = items; }

    @Override public int  getContainerSize() { return NUM_SLOTS; }
    @Override public boolean isEmpty()       { return items.stream().allMatch(ItemStack::isEmpty); }
    @Override public ItemStack getItem(int slot) { return items.get(slot); }

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
        ItemStack existing = items.get(slot);
        boolean sameItem = !stack.isEmpty()
                && ItemStack.isSameItemSameComponents(stack, existing);
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) stack.setCount(getMaxStackSize());

        // If an input slot changed, recalculate total cook time
        if (slot < NUM_INPUT_SLOTS && !sameItem) {
            if (level instanceof ServerLevel serverLevel) {
                cookingTotalTime = getRecipeCookTime(serverLevel);
            }
            cookingProgress = 0;
            setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override public void clearContent() { items.clear(); }

    // ── WorldlyContainer ──────────────────────────────────────────────────────

    // Matching OFAW: up=input[0], down={output,fuel}, sides={fuel}
    private static final int[] SLOTS_TOP    = { 0 };
    private static final int[] SLOTS_BOTTOM = { SLOT_OUTPUT, SLOT_FUEL };
    private static final int[] SLOTS_SIDE   = { SLOT_FUEL };

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) return SLOTS_BOTTOM;
        if (side == Direction.UP)   return SLOTS_TOP;
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
        return true; // input slots accept anything
    }

    // ── Menu ─────────────────────────────────────────────────────────────────

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.got.oven");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new OvenMenu(id, inventory, this, dataAccess);
    }

    public ContainerData getDataAccess() { return dataAccess; }

    // ── NBT ──────────────────────────────────────────────────────────────────

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items = NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);
        cookingProgress  = tag.getInt("CookTime");
        cookingTotalTime = tag.getInt("CookTimeTotal");
        litTime          = tag.getInt("BurnTime");
        litDuration      = tag.getInt("BurnDuration");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("CookTime",      cookingProgress);
        tag.putInt("CookTimeTotal", cookingTotalTime);
        tag.putInt("BurnTime",      litTime);
        tag.putInt("BurnDuration",  litDuration);
    }
}
