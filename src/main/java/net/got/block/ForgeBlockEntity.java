package net.got.block;

import net.got.init.GotModBlockEntities;
import net.got.init.GotModRecipeTypes;
import net.got.menu.AlloyMenu;
import net.got.menu.HeatTreatingMenu;
import net.got.recipe.AlloyRecipe;
import net.got.recipe.AlloyRecipeInput;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ForgeBlockEntity — powers the Forge block.
 *
 * Two completely independent modes. Only the fuel slot is shared.
 *
 * Slot layout:
 *   0 — fuel                    (shared)
 *   1 — alloy output            (alloying only)
 *   2 — alloy input A           (alloying only)
 *   3 — alloy input B           (alloying only)
 *   4 — alloy input C           (alloying only)
 *   5 — alloy input D           (alloying only)
 *   6 — heat slot A             (heat treating only)
 *   7 — heat slot B             (heat treating only)
 *   8 — heat slot C             (heat treating only)
 *   9 — heat slot D             (heat treating only)
 *
 * ContainerData layout:
 *   0 — cookingProgress         (alloying progress ticks)
 *   1 — cookingTotalTime        (alloying total ticks / heat-treating total ticks)
 *   2 — litTime
 *   3 — litDuration
 *   4 — selectedRecipeIndex     (-1 = none; alloying only)
 *   5 — mode (0 = HEAT_TREATING, 1 = ALLOYING)
 *   6 — heatProgressA
 *   7 — heatProgressB
 *   8 — heatProgressC
 *   9 — heatProgressD
 */
public class ForgeBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    // ── Modes ─────────────────────────────────────────────────────────────────
    public static final int MODE_HEAT_TREATING = 0;
    public static final int MODE_ALLOYING      = 1;

    // ── Slot indices ──────────────────────────────────────────────────────────
    public static final int SLOT_FUEL       = 0;
    public static final int SLOT_OUTPUT     = 1;  // alloying output
    public static final int SLOT_ALLOY_A    = 2;
    public static final int SLOT_ALLOY_B    = 3;
    public static final int SLOT_ALLOY_C    = 4;
    public static final int SLOT_ALLOY_D    = 5;
    public static final int SLOT_HEAT_A     = 6;
    public static final int SLOT_HEAT_B     = 7;
    public static final int SLOT_HEAT_C     = 8;
    public static final int SLOT_HEAT_D     = 9;
    public static final int NUM_SLOTS       = 10;

    public static final int[] HEAT_SLOTS  = { SLOT_HEAT_A, SLOT_HEAT_B, SLOT_HEAT_C, SLOT_HEAT_D };
    public static final int[] ALLOY_SLOTS = { SLOT_ALLOY_A, SLOT_ALLOY_B, SLOT_ALLOY_C, SLOT_ALLOY_D };

    // ── ContainerData indices ─────────────────────────────────────────────────
    public static final int DATA_COOKING_PROGRESS = 0;
    public static final int DATA_COOKING_TOTAL    = 1;
    public static final int DATA_LIT_TIME         = 2;
    public static final int DATA_LIT_DURATION     = 3;
    public static final int DATA_SELECTED_RECIPE  = 4;
    public static final int DATA_MODE             = 5;
    public static final int DATA_HEAT_PROGRESS_A  = 6;
    public static final int DATA_HEAT_PROGRESS_B  = 7;
    public static final int DATA_HEAT_PROGRESS_C  = 8;
    public static final int DATA_HEAT_PROGRESS_D  = 9;
    public static final int NUM_DATA              = 10;

    private static final int HEAT_TIME = 200;

    // ── State ─────────────────────────────────────────────────────────────────
    private NonNullList<ItemStack> items =
            NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);
    private int cookingProgress   = 0;
    private int cookingTotalTime  = 200;
    private int litTime           = 0;
    private int litDuration       = 0;
    private int selectedRecipeIdx = -1;
    private int mode              = MODE_HEAT_TREATING;
    private final int[] heatProgress = new int[4];

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
                case DATA_HEAT_PROGRESS_A  -> heatProgress[0];
                case DATA_HEAT_PROGRESS_B  -> heatProgress[1];
                case DATA_HEAT_PROGRESS_C  -> heatProgress[2];
                case DATA_HEAT_PROGRESS_D  -> heatProgress[3];
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
                case DATA_SELECTED_RECIPE  -> selectedRecipeIdx = v;
                case DATA_MODE             -> mode             = v;
                case DATA_HEAT_PROGRESS_A  -> heatProgress[0]  = v;
                case DATA_HEAT_PROGRESS_B  -> heatProgress[1]  = v;
                case DATA_HEAT_PROGRESS_C  -> heatProgress[2]  = v;
                case DATA_HEAT_PROGRESS_D  -> heatProgress[3]  = v;
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

        if (be.mode == MODE_HEAT_TREATING) {
            dirty |= be.tickHeatTreating(serverLevel);
        } else {
            dirty |= be.tickAlloying(serverLevel);
        }

        if (wasLit != be.isLit()) {
            dirty = true;
            level.setBlock(pos, state.setValue(ForgeBlock.LIT, be.isLit()), 3);
        }

        if (dirty) setChanged(level, pos, state);
    }

    // ── Heat-treating tick ────────────────────────────────────────────────────

    private boolean tickHeatTreating(ServerLevel level) {
        boolean dirty = false;

        boolean anyHeatable = false;
        for (int i = 0; i < HEAT_SLOTS.length; i++) {
            ItemStack stack = items.get(HEAT_SLOTS[i]);
            if (!stack.isEmpty() && heatProgress[i] < cookingTotalTime) {
                anyHeatable = true;
                break;
            }
        }

        if (anyHeatable && !isLit() && hasFuel(level)) {
            dirty |= lightFuel(level);
        }

        for (int i = 0; i < HEAT_SLOTS.length; i++) {
            int slot = HEAT_SLOTS[i];
            ItemStack stack = items.get(slot);

            if (stack.isEmpty()) {
                if (heatProgress[i] != 0) { heatProgress[i] = 0; dirty = true; }
                continue;
            }

            if (heatProgress[i] >= cookingTotalTime && cookingTotalTime > 0) continue;

            if (isLit()) {
                heatProgress[i]++;
                dirty = true;
                if (heatProgress[i] >= cookingTotalTime) {
                    heatProgress[i] = cookingTotalTime;
                    finishHeating(slot);
                }
            } else if (heatProgress[i] > 0) {
                heatProgress[i] = Math.max(0, heatProgress[i] - 2);
                dirty = true;
            }
        }

        if (cookingTotalTime != HEAT_TIME) { cookingTotalTime = HEAT_TIME; dirty = true; }

        return dirty;
    }

    private void finishHeating(int slot) {
        ItemStack input = items.get(slot);
        if (input.isEmpty()) return;
        ItemStack result = input.copyWithCount(input.getCount());
        result.set(net.got.init.GotModDataComponents.HOT.get(), net.minecraft.util.Unit.INSTANCE);
        items.set(slot, result);
    }

    // ── Alloying tick ─────────────────────────────────────────────────────────

    /**
     * Returns the two horizontal directions perpendicular to the forge's facing —
     * i.e., the "left" and "right" sides of the forge.
     */
    private Direction[] getSideDirections(ServerLevel level) {
        BlockState state = level.getBlockState(worldPosition);
        Direction facing = state.hasProperty(ForgeBlock.FACING)
                ? state.getValue(ForgeBlock.FACING)
                : Direction.NORTH;
        return new Direction[]{ facing.getClockWise(), facing.getCounterClockWise() };
    }

    /**
     * Returns true if both perpendicular sides of the forge have a BellowsBlock
     * whose FACING points inward (toward the forge center).
     */
    private boolean hasBellowsOnBothSides(ServerLevel level) {
        for (Direction side : getSideDirections(level)) {
            BlockPos neighborPos = worldPosition.relative(side);
            BlockState neighborState = level.getBlockState(neighborPos);
            if (!(neighborState.getBlock() instanceof BellowsBlock)) return false;
        }
        return true;
    }

    /**
     * Triggers the pump animation on the bellows on both sides of the forge.
     * Safe to call every tick — tryStartPump() is a no-op while already pumping.
     */
    private void triggerBellowsPump(ServerLevel level) {
        for (Direction side : getSideDirections(level)) {
            BlockPos neighborPos = worldPosition.relative(side);
            if (level.getBlockEntity(neighborPos) instanceof BellowsBlockEntity bellows) {
                bellows.tryStartPump();
            }
        }
    }

    private boolean tickAlloying(ServerLevel level) {
        boolean dirty = false;

        // Alloying requires bellows on both sides of the forge.
        // Without them, stall and slowly drain any existing progress.
        if (!hasBellowsOnBothSides(level)) {
            if (selectedRecipeIdx != -1) {
                selectedRecipeIdx = -1;
                dirty = true;
            }
            if (cookingProgress > 0) {
                cookingProgress = Math.max(0, cookingProgress - 2);
                dirty = true;
            }
            return dirty;
        }

        boolean anyEmpty = false;
        for (int slot : ALLOY_SLOTS) {
            if (items.get(slot).isEmpty()) { anyEmpty = true; break; }
        }
        if (anyEmpty) {
            if (selectedRecipeIdx != -1) {
                selectedRecipeIdx = -1;
                cookingProgress = 0;
                dirty = true;
            }
        }

        if (selectedRecipeIdx < 0) {
            List<RecipeHolder<AlloyRecipe>> matching = getMatchingAlloyRecipes(level);
            if (!matching.isEmpty()) {
                selectedRecipeIdx = 0;
                cookingTotalTime  = matching.get(0).value().getCookingTime();
            }
        }

        RecipeHolder<AlloyRecipe> recipe = getSelectedAlloyRecipe(level);

        if (recipe != null) {
            if (!isLit() && canBurnAlloy(recipe) && hasFuel(level)) dirty |= lightFuel(level);

            if (isLit() && canBurnAlloy(recipe)) {
                cookingProgress++;
                dirty = true;
                // Auto-pump both bellows continuously: restart their animation
                // each MAX_TICKS cycle so it loops while alloying is running.
                if (cookingProgress % BellowsBlockEntity.MAX_TICKS == 1) {
                    triggerBellowsPump(level);
                }
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

    // ── Fuel ──────────────────────────────────────────────────────────────────

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

    public boolean isLit() { return litTime > 0; }
    public int getMode()   { return mode; }

    public void setMode(int mode) {
        this.mode = mode;
        this.cookingProgress = 0;
        this.selectedRecipeIdx = -1;
        // Do NOT reset heatProgress or slot contents — each mode keeps its own state
        setChanged();
    }

    private boolean hasFuel(Level level) {
        return getBurnDuration(level, items.get(SLOT_FUEL)) > 0;
    }

    private int getBurnDuration(Level level, ItemStack stack) {
        if (stack.isEmpty()) return 0;
        return stack.getBurnTime(GotModRecipeTypes.SMITHY.get(), level.fuelValues());
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

    private boolean burnAlloy(RecipeHolder<AlloyRecipe> recipe) {
        if (!canBurnAlloy(recipe)) return false;
        depositOutput(recipe.value().getResult());
        for (int slot : ALLOY_SLOTS) {
            ItemStack s = items.get(slot);
            s.shrink(1);
            if (s.isEmpty()) items.set(slot, ItemStack.EMPTY);
        }
        return true;
    }

    private void depositOutput(ItemStack result) {
        ItemStack result2 = result.copy();
        ItemStack output  = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) items.set(SLOT_OUTPUT, result2);
        else output.grow(result2.getCount());
    }

    @Nullable
    private RecipeHolder<AlloyRecipe> getSelectedAlloyRecipe(ServerLevel level) {
        if (selectedRecipeIdx < 0) return null;
        List<RecipeHolder<AlloyRecipe>> matching = getMatchingAlloyRecipes(level);
        if (selectedRecipeIdx >= matching.size()) { selectedRecipeIdx = -1; return null; }
        return matching.get(selectedRecipeIdx);
    }

    public List<RecipeHolder<AlloyRecipe>> getMatchingAlloyRecipes(Level level) {
        ItemStack a = items.get(SLOT_ALLOY_A), b = items.get(SLOT_ALLOY_B);
        ItemStack c = items.get(SLOT_ALLOY_C), d = items.get(SLOT_ALLOY_D);
        if (a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()) return List.of();
        if (!(level instanceof ServerLevel serverLevel)) return List.of();
        RecipeManager rm = serverLevel.getServer().getRecipeManager();
        AlloyRecipeInput input = new AlloyRecipeInput(a, b, c, d);
        return rm.recipeMap().byType(GotModRecipeTypes.ALLOY.get()).stream()
                .filter(h -> h.value().matches(input, serverLevel))
                .sorted(Comparator.comparing(h -> h.id().toString()))
                .collect(Collectors.toList());
    }

    public void setSelectedRecipeIndex(int idx) {
        this.selectedRecipeIdx = idx;
        this.cookingProgress   = 0;
        this.cookingTotalTime  = 200;
        setChanged();
    }

    public ContainerData getDataAccess() { return dataAccess; }

    // ── Container ─────────────────────────────────────────────────────────────

    @Override public NonNullList<ItemStack> getItems()            { return items; }
    @Override public void setItems(NonNullList<ItemStack> items)  { this.items = items; }
    @Override public int  getContainerSize()                      { return NUM_SLOTS; }
    @Override public boolean isEmpty() { return items.stream().allMatch(ItemStack::isEmpty); }
    @Override public ItemStack getItem(int slot)                  { return items.get(slot); }

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

        // Reset per-slot progress when a heat slot changes
        for (int i = 0; i < HEAT_SLOTS.length; i++) {
            if (slot == HEAT_SLOTS[i]) {
                heatProgress[i] = 0;
                setChanged();
                return;
            }
        }

        // Reset alloy progress when an alloy input slot changes
        for (int alloySlot : ALLOY_SLOTS) {
            if (slot == alloySlot) {
                selectedRecipeIdx = -1;
                cookingProgress   = 0;
                setChanged();
                return;
            }
        }

        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override public void clearContent() { items.clear(); }

    // ── WorldlyContainer ──────────────────────────────────────────────────────

    private static final int[] SLOTS_TOP    = { SLOT_HEAT_A, SLOT_HEAT_B, SLOT_HEAT_C, SLOT_HEAT_D,
            SLOT_ALLOY_A, SLOT_ALLOY_B, SLOT_ALLOY_C, SLOT_ALLOY_D };
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

    // ── Menu ──────────────────────────────────────────────────────────────────

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.got.forge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return mode == MODE_ALLOYING
                ? new AlloyMenu(id, inventory, this, dataAccess)
                : new HeatTreatingMenu(id, inventory, this, dataAccess);
    }

    // ── NBT ───────────────────────────────────────────────────────────────────

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
        int[] savedHeat = tag.getIntArray("HeatProgress");
        for (int i = 0; i < heatProgress.length; i++) {
            heatProgress[i] = i < savedHeat.length ? savedHeat[i] : 0;
        }
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
        tag.putIntArray("HeatProgress", heatProgress);
    }
}