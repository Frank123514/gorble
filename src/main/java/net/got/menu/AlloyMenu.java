package net.got.menu;

import net.got.block.ForgeBlockEntity;
import net.got.init.GotModMenus;
import net.got.init.GotModRecipeTypes;
import net.got.recipe.AlloyRecipe;
import net.got.recipe.AlloyRecipeInput;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AlloyMenu — container menu for the Forge block's alloying mode.
 * Mirrors {@link SmithyMenu} but exposes the two alloy input slots instead
 * of the single smithing input slot, and lists matching {@link AlloyRecipe}s.
 *
 * GUI positions (relative to window top-left):
 *   Input A : (20, 9)   — upper input
 *   Input B : (20, 29)  — lower input, melted together with A
 *   Fuel    : (20, 53)  — same fuel slot the Forge uses for smithing
 *   Output  : (143, 35) — same output slot the Forge uses for smithing
 */
public class AlloyMenu extends AbstractContainerMenu {

    public static final int INPUT_A_X = 20;
    public static final int INPUT_A_Y = 9;
    public static final int INPUT_B_X = 20;
    public static final int INPUT_B_Y = 29;
    public static final int FUEL_X    = 20;
    public static final int FUEL_Y    = 53;
    public static final int OUTPUT_X  = 143;
    public static final int OUTPUT_Y  = 33;

    private final Container     container;
    private final ContainerData data;
    private final Level         level;

    private static final int PLAYER_INV_START = 0;
    private static final int PLAYER_INV_END   = 36;
    private static final int TE_INPUT_A_IDX   = 36;
    private static final int TE_INPUT_B_IDX   = 37;
    private static final int TE_FUEL_IDX      = 38;
    private static final int TE_OUTPUT_IDX    = 39;

    /** Client-side constructor (called by MenuType factory). */
    public AlloyMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv,
                new SimpleContainer(ForgeBlockEntity.NUM_SLOTS),
                new SimpleContainerData(ForgeBlockEntity.NUM_DATA));
    }

    /** Server-side constructor (called by ForgeBlockEntity). */
    public AlloyMenu(int windowId, Inventory playerInv,
                      Container container, ContainerData data) {
        super(GotModMenus.ALLOY.get(), windowId);
        this.container = container;
        this.data      = data;
        this.level     = playerInv.player.level();

        checkContainerSize(container, ForgeBlockEntity.NUM_SLOTS);
        checkContainerDataCount(data, ForgeBlockEntity.NUM_DATA);
        container.startOpen(playerInv.player);

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));

        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));

        this.addSlot(new Slot(container, ForgeBlockEntity.SLOT_ALLOY_A, INPUT_A_X, INPUT_A_Y));
        this.addSlot(new Slot(container, ForgeBlockEntity.SLOT_ALLOY_B, INPUT_B_X, INPUT_B_Y));
        this.addSlot(new FuelSlot(container, ForgeBlockEntity.SLOT_FUEL, FUEL_X, FUEL_Y, level));
        this.addSlot(new ResultSlot(container, ForgeBlockEntity.SLOT_OUTPUT, OUTPUT_X, OUTPUT_Y));

        this.addDataSlots(data);
    }

    // ── Progress helpers (used by AlloyScreen) ────────────────────────────────

    public boolean isCrafting() {
        return data.get(ForgeBlockEntity.DATA_COOKING_PROGRESS) > 0;
    }

    public boolean isFlaming() {
        return data.get(ForgeBlockEntity.DATA_LIT_TIME) > 0;
    }

    public int getArrowProgress() {
        int progress = data.get(ForgeBlockEntity.DATA_COOKING_PROGRESS);
        int total    = data.get(ForgeBlockEntity.DATA_COOKING_TOTAL);
        return (total != 0 && progress != 0) ? progress * 24 / total : 0;
    }

    public int getFlameProgress() {
        int duration = data.get(ForgeBlockEntity.DATA_LIT_DURATION);
        if (duration == 0) duration = 200;
        return data.get(ForgeBlockEntity.DATA_LIT_TIME) * 13 / duration;
    }

    public int getSelectedRecipeIndex() {
        return data.get(ForgeBlockEntity.DATA_SELECTED_RECIPE);
    }

    public ItemStack getInputA() { return container.getItem(ForgeBlockEntity.SLOT_ALLOY_A); }
    public ItemStack getInputB() { return container.getItem(ForgeBlockEntity.SLOT_ALLOY_B); }

    public Container getContainer() { return container; }

    // ── Client-side recipe list ───────────────────────────────────────────────

    public List<RecipeHolder<AlloyRecipe>> getMatchingRecipes() {
        ItemStack a = getInputA();
        ItemStack b = getInputB();
        if (a.isEmpty() || b.isEmpty()) return List.of();
        AlloyRecipeInput ri = new AlloyRecipeInput(a, b);
        return net.got.client.AlloyClientRecipes.get()
                .stream()
                .filter(h -> h.value().matches(ri, level))
                .sorted(Comparator.comparing(h -> h.id().toString()))
                .collect(Collectors.toList());
    }

    // ── Shift-click ───────────────────────────────────────────────────────────

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy  = stack.copy();

        if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
            if (!this.moveItemStackTo(stack, TE_FUEL_IDX, TE_FUEL_IDX + 1, false)) {
                if (!this.moveItemStackTo(stack, TE_INPUT_A_IDX, TE_INPUT_A_IDX + 1, false)) {
                    if (!this.moveItemStackTo(stack, TE_INPUT_B_IDX, TE_INPUT_B_IDX + 1, false))
                        return ItemStack.EMPTY;
                }
            }
        } else {
            if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, false))
                return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        if (stack.getCount() == copy.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) { return container.stillValid(player); }

    @Override
    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }

    // ── Inner slot types ──────────────────────────────────────────────────────

    private static class FuelSlot extends Slot {
        private final Level level;
        FuelSlot(Container c, int slot, int x, int y, Level level) {
            super(c, slot, x, y);
            this.level = level;
        }
        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getBurnTime(GotModRecipeTypes.SMITHY.get(),
                    level.fuelValues()) > 0;
        }
    }

    private static class ResultSlot extends Slot {
        ResultSlot(Container c, int slot, int x, int y) {
            super(c, slot, x, y);
        }
        @Override public boolean mayPlace(ItemStack stack) { return false; }
    }
}
