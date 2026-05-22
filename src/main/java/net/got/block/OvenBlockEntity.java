package net.got.block;

import net.got.block.OvenBlock;
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
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * OvenBlockEntity — furnace-style block entity for the Oven block.
 *
 * Slot layout:
 *   0 — input ingredient
 *   1 — fuel
 *   2 — output
 */
public class OvenBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    public static final int SLOT_INPUT   = 0;
    public static final int SLOT_FUEL    = 1;
    public static final int SLOT_OUTPUT  = 2;
    public static final int NUM_SLOTS    = 3;

    public static final int DATA_LIT_TIME         = 0;
    public static final int DATA_LIT_DURATION     = 1;
    public static final int DATA_COOKING_PROGRESS = 2;
    public static final int DATA_COOKING_TOTAL    = 3;
    public static final int NUM_DATA              = 4;

    private NonNullList<ItemStack> items = NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);

    // Cache the recipe lookup for performance
    private final RecipeManager.CachedCheck<SingleRecipeInput, OvenRecipe> quickCheck;

    private int litTime;
    private int litDuration;
    private int cookingProgress;
    private int cookingTotalTime;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override public int get(int i) {
            return switch (i) {
                case DATA_LIT_TIME         -> litTime;
                case DATA_LIT_DURATION     -> litDuration;
                case DATA_COOKING_PROGRESS -> cookingProgress;
                case DATA_COOKING_TOTAL    -> cookingTotalTime;
                default -> 0;
            };
        }
        @Override public void set(int i, int v) {
            switch (i) {
                case DATA_LIT_TIME         -> litTime = v;
                case DATA_LIT_DURATION     -> litDuration = v;
                case DATA_COOKING_PROGRESS -> cookingProgress = v;
                case DATA_COOKING_TOTAL    -> cookingTotalTime = v;
            }
        }
        @Override public int getCount() { return NUM_DATA; }
    };

    public OvenBlockEntity(BlockPos pos, BlockState state) {
        super(GotModBlockEntities.OVEN.get(), pos, state);
        this.quickCheck = RecipeManager.createCheck(GotModRecipeTypes.OVEN.get());
    }

    // ── Required abstract overrides (BaseContainerBlockEntity) ────────────────

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    // ── Server tick ───────────────────────────────────────────────────────────

    public static void serverTick(Level level, BlockPos pos, BlockState state, OvenBlockEntity be) {
        boolean wasLit = be.isLit();
        boolean dirty  = false;

        if (be.isLit()) {
            be.litTime--;
        }

        ItemStack inputStack = be.items.get(SLOT_INPUT);
        Optional<RecipeHolder<OvenRecipe>> recipe = inputStack.isEmpty()
                ? Optional.empty()
                : be.quickCheck.getRecipeFor(new SingleRecipeInput(inputStack), level);

        if (be.isLit() || (recipe.isPresent() && be.hasFuel())) {
            if (!be.isLit() && be.canBurn(level, recipe)) {
                be.litDuration = be.getBurnDuration(be.items.get(SLOT_FUEL));
                be.litTime     = be.litDuration;
                if (be.isLit()) {
                    dirty = true;
                    ItemStack fuel = be.items.get(SLOT_FUEL);
                    fuel.shrink(1);
                    if (fuel.isEmpty()) be.items.set(SLOT_FUEL, ItemStack.EMPTY);
                }
            }

            if (be.isLit() && be.canBurn(level, recipe)) {
                be.cookingProgress++;
                if (be.cookingProgress >= be.cookingTotalTime) {
                    be.cookingProgress   = 0;
                    be.cookingTotalTime  = be.getCookingTime(level);
                    if (be.burn(level, recipe)) dirty = true;
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

    public boolean isLit() { return litTime > 0; }

    private boolean hasFuel() {
        return getBurnDuration(items.get(SLOT_FUEL)) > 0;
    }

    private int getBurnDuration(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        // CommonHooks.getBurnTime takes (ItemStack) in NeoForge 21.4 and uses the
        // item's registered burn time (no recipe-type parameter needed).
        return CommonHooks.getBurnTime(stack, GotModRecipeTypes.OVEN.get());
    }

    private boolean canBurn(Level level, Optional<RecipeHolder<OvenRecipe>> recipe) {
        if (items.get(SLOT_INPUT).isEmpty() || recipe.isEmpty()) return false;
        ItemStack result = recipe.get().value().assemble(
                new SingleRecipeInput(items.get(SLOT_INPUT)), level.registryAccess());
        if (result.isEmpty()) return false;
        ItemStack output = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(output, result)) return false;
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private boolean burn(Level level, Optional<RecipeHolder<OvenRecipe>> recipe) {
        if (recipe.isEmpty() || !canBurn(level, recipe)) return false;
        ItemStack result = recipe.get().value().assemble(
                new SingleRecipeInput(items.get(SLOT_INPUT)), level.registryAccess());
        ItemStack output = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) {
            items.set(SLOT_OUTPUT, result.copy());
        } else if (ItemStack.isSameItemSameComponents(output, result)) {
            output.grow(result.getCount());
        }
        items.get(SLOT_INPUT).shrink(1);
        if (items.get(SLOT_INPUT).isEmpty()) items.set(SLOT_INPUT, ItemStack.EMPTY);
        return true;
    }

    private int getCookingTime(Level level) {
        ItemStack input = items.get(SLOT_INPUT);
        if (input.isEmpty()) return 200;
        return quickCheck.getRecipeFor(new SingleRecipeInput(input), level)
                .map(r -> r.value().getCookingTime())
                .orElse(200);
    }

    // ── Container ─────────────────────────────────────────────────────────────

    @Override public int getContainerSize()          { return NUM_SLOTS; }
    @Override public boolean isEmpty()               { return items.stream().allMatch(ItemStack::isEmpty); }
    @Override public ItemStack getItem(int slot)     { return items.get(slot); }

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
        boolean sameItem = !stack.isEmpty() && ItemStack.isSameItemSameComponents(stack, existing);
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) stack.setCount(getMaxStackSize());
        if (slot == SLOT_INPUT && !sameItem) {
            cookingTotalTime = getCookingTime(level);
            cookingProgress  = 0;
            setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return BaseContainerBlockEntity.stillValidBlockEntity(this, player);
    }

    @Override public void clearContent() { items.clear(); }

    // ── WorldlyContainer (hopper/automation) ──────────────────────────────────

    private static final int[] SLOTS_TOP    = { SLOT_INPUT };
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
        if (slot == SLOT_FUEL)   return getBurnDuration(stack) > 0;
        return true;
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

    // ── NBT ──────────────────────────────────────────────────────────────────

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items = NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);
        litTime          = tag.getInt("BurnTime");
        litDuration      = tag.getInt("BurnDuration");
        cookingProgress  = tag.getInt("CookTime");
        cookingTotalTime = tag.getInt("CookTimeTotal");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("BurnTime",      litTime);
        tag.putInt("BurnDuration",  litDuration);
        tag.putInt("CookTime",      cookingProgress);
        tag.putInt("CookTimeTotal", cookingTotalTime);
    }

    public ContainerData getDataAccess() { return dataAccess; }
}
