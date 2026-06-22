package net.got.menu;

import net.got.block.ForgeBlockEntity;
import net.got.init.GotModMenus;
import net.got.init.GotModRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * HeatTreatingMenu — container menu for the Forge's heat-treating mode.
 * <p>
 * Simple two-slot furnace-style layout: one input slot for raw metal ingots,
 * one fuel slot, and one output slot. The output is a "heated ingot" — a
 * malleable metal item that can then be placed in the Smithing Anvil and
 * worked into finished goods.
 * <p>
 * Slot layout in the menu (indices used by AbstractContainerMenu.slots):
 *   0–26  — player main inventory
 *   27–35 — player hotbar
 *   36    — forge input  (container slot 0 = SLOT_HEAT_INPUT)
 *   37    — forge fuel   (container slot 1 = SLOT_FUEL)
 *   38    — forge output (container slot 2 = SLOT_OUTPUT)
 */
public class HeatTreatingMenu extends AbstractContainerMenu {

    public static final int INPUT_X  = 56;
    public static final int INPUT_Y  = 17;
    public static final int FUEL_X   = 56;
    public static final int FUEL_Y   = 53;
    public static final int OUTPUT_X = 116;
    public static final int OUTPUT_Y = 35;

    private final Container   container;
    private final ContainerData data;
    private final Level         level;

    private static final int PLAYER_INV_START = 0;
    private static final int PLAYER_INV_END   = 36;
    private static final int TE_INPUT_IDX     = 36;
    private static final int TE_FUEL_IDX      = 37;
    private static final int TE_OUTPUT_IDX    = 38;

    /** Client-side constructor (called by MenuType factory). */
    public HeatTreatingMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv,
                new SimpleContainer(ForgeBlockEntity.NUM_SLOTS),
                new SimpleContainerData(ForgeBlockEntity.NUM_DATA));
    }

    /** Server-side constructor (called by ForgeBlockEntity in heat-treating mode). */
    public HeatTreatingMenu(int windowId, Inventory playerInv,
                            Container container, ContainerData data) {
        super(GotModMenus.HEAT_TREATING.get(), windowId);
        this.container = container;
        this.data      = data;
        this.level     = playerInv.player.level();

        checkContainerSize(container, ForgeBlockEntity.NUM_SLOTS);
        checkContainerDataCount(data, ForgeBlockEntity.NUM_DATA);
        container.startOpen(playerInv.player);

        // Player main inventory (rows 0–2)
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));

        // Hotbar
        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));

        // Forge slots (heat treating uses SLOT_HEAT_INPUT, SLOT_FUEL, SLOT_OUTPUT)
        this.addSlot(new Slot(container, ForgeBlockEntity.SLOT_HEAT_INPUT, INPUT_X, INPUT_Y));
        this.addSlot(new FuelSlot(container, ForgeBlockEntity.SLOT_FUEL, FUEL_X, FUEL_Y, level));
        this.addSlot(new ResultSlot(playerInv.player, container,
                ForgeBlockEntity.SLOT_OUTPUT, OUTPUT_X, OUTPUT_Y));

        this.addDataSlots(data);
    }

    // ── Progress helpers (used by HeatTreatingScreen) ─────────────────────────

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

    public Container getContainer() { return container; }

    // ── Shift-click ───────────────────────────────────────────────────────────

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy  = stack.copy();

        if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
            if (!this.moveItemStackTo(stack, TE_FUEL_IDX, TE_FUEL_IDX + 1, false)) {
                if (!this.moveItemStackTo(stack, TE_INPUT_IDX, TE_INPUT_IDX + 1, false))
                    return ItemStack.EMPTY;
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
        ResultSlot(Player player, Container c, int slot, int x, int y) {
            super(c, slot, x, y);
        }
        @Override public boolean mayPlace(ItemStack stack) { return false; }
    }
}