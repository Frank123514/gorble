package net.got.menu;

import net.got.block.OvenBlockEntity;
import net.got.init.GotModMenus;
import net.got.init.GotModRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.CommonHooks;

/**
 * OvenMenu — container screen wiring for the Oven.
 *
 * GUI layout (176×166, same as vanilla furnace):
 *   Slot 0 — input  (top-left,  56,17)
 *   Slot 1 — fuel   (bot-left,  56,53)
 *   Slot 2 — output (right,    116,35)
 */
public class OvenMenu extends AbstractContainerMenu {

    private final Container   container;
    private final ContainerData data;

    // ── Client-side constructor (matches MenuType BiFunction<Integer, Inventory, T>) ──
    public OvenMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv,
                new SimpleContainer(OvenBlockEntity.NUM_SLOTS),
                new SimpleContainerData(OvenBlockEntity.NUM_DATA));
    }

    // ── Server-side constructor ───────────────────────────────────────────────
    public OvenMenu(int windowId, Inventory playerInv, Container container, ContainerData data) {
        super(GotModMenus.OVEN.get(), windowId);
        this.container = container;
        this.data      = data;

        checkContainerSize(container, OvenBlockEntity.NUM_SLOTS);
        checkContainerDataCount(data, OvenBlockEntity.NUM_DATA);
        container.startOpen(playerInv.player);

        // ── Oven slots ────────────────────────────────────────────────────────
        this.addSlot(new Slot(container, OvenBlockEntity.SLOT_INPUT,  56, 17));
        this.addSlot(new FuelSlot(container, OvenBlockEntity.SLOT_FUEL, 56, 53));
        this.addSlot(new ResultSlot(playerInv.player, container, OvenBlockEntity.SLOT_OUTPUT, 116, 35));

        // ── Player inventory (3 rows × 9) ─────────────────────────────────────
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

        // ── Hotbar ────────────────────────────────────────────────────────────
        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));

        this.addDataSlots(data);
    }

    // ── Data accessors ────────────────────────────────────────────────────────

    public boolean isLit() {
        return data.get(OvenBlockEntity.DATA_LIT_TIME) > 0;
    }

    /** 0.0–1.0 fraction of fuel remaining. */
    public float getFuelProgress() {
        int duration = data.get(OvenBlockEntity.DATA_LIT_DURATION);
        return duration == 0 ? 0f :
                (float) data.get(OvenBlockEntity.DATA_LIT_TIME) / (float) duration;
    }

    /** 0.0–1.0 fraction of cooking complete. */
    public float getCookProgress() {
        int total = data.get(OvenBlockEntity.DATA_COOKING_TOTAL);
        return total == 0 ? 0f :
                (float) data.get(OvenBlockEntity.DATA_COOKING_PROGRESS) / (float) total;
    }

    // ── Quick-move (shift-click) ──────────────────────────────────────────────

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            copy = stack.copy();

            int invStart = OvenBlockEntity.NUM_SLOTS;
            int invEnd   = invStart + 27;
            int hotEnd   = invEnd + 9;

            if (index == OvenBlockEntity.SLOT_OUTPUT) {
                if (!this.moveItemStackTo(stack, invStart, hotEnd, true)) return ItemStack.EMPTY;
                slot.onQuickCraft(stack, copy);
            } else if (index >= invStart) {
                // Try fuel slot first, then input
                if (!this.moveItemStackTo(stack, OvenBlockEntity.SLOT_FUEL, OvenBlockEntity.SLOT_FUEL + 1, false)) {
                    if (!this.moveItemStackTo(stack, OvenBlockEntity.SLOT_INPUT, OvenBlockEntity.SLOT_INPUT + 1, false)) {
                        if (index < invEnd) {
                            if (!this.moveItemStackTo(stack, invEnd, hotEnd, false)) return ItemStack.EMPTY;
                        } else {
                            if (!this.moveItemStackTo(stack, invStart, invEnd, false)) return ItemStack.EMPTY;
                        }
                    }
                }
            } else {
                if (!this.moveItemStackTo(stack, invStart, hotEnd, false)) return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();

            if (stack.getCount() == copy.getCount()) return ItemStack.EMPTY;
            slot.onTake(player, stack);
        }
        return copy;
    }

    @Override
    public boolean stillValid(Player player) { return container.stillValid(player); }

    @Override
    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }

    // ── Custom slot types ─────────────────────────────────────────────────────

    private static class FuelSlot extends Slot {
        FuelSlot(Container container, int slot, int x, int y) { super(container, slot, x, y); }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return CommonHooks.getBurnTime(stack, GotModRecipeTypes.OVEN.get()) > 0;
        }
    }

    private static class ResultSlot extends Slot {
        ResultSlot(Player player, Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }
        @Override public boolean mayPlace(ItemStack stack) { return false; }
    }
}
