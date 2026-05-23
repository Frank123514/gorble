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
import net.minecraft.world.level.Level;

/**
 * OvenMenu — ported from OFAW (1.16.5) to NeoForge 1.21.4.
 *
 * Slot layout visible in the GUI:
 *   - 3×3 input grid  (container slots 0–8)  at positions (30+col*18, 17+row*18)
 *   - output slot      (container slot 9)     at (124, 35)
 *   - fuel slot        (container slot 10)    at (8, 53)
 *   - player inventory 27 slots              at (8+col*18, 84+row*18)
 *   - hotbar 9 slots                         at (8+col*18, 142)
 *
 * ContainerData (4 ints):
 *   0 = cookingProgress, 1 = cookingTotalTime, 2 = litTime, 3 = litDuration
 */
public class OvenMenu extends AbstractContainerMenu {

    private final Container   container;
    private final ContainerData data;

    /** Client-side constructor (called by MenuType factory). */
    public OvenMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv,
                new SimpleContainer(OvenBlockEntity.NUM_SLOTS),
                new SimpleContainerData(OvenBlockEntity.NUM_DATA));
    }

    /** Server-side constructor. */
    public OvenMenu(int windowId, Inventory playerInv,
                    Container container, ContainerData data) {
        super(GotModMenus.OVEN.get(), windowId);
        this.container = container;
        this.data      = data;

        checkContainerSize(container, OvenBlockEntity.NUM_SLOTS);
        checkContainerDataCount(data, OvenBlockEntity.NUM_DATA);
        container.startOpen(playerInv.player);

        Level level = playerInv.player.level();

        // Player inventory (rows 0-2)
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));

        // Hotbar
        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));

        // Output slot (container slot 9) — players cannot place items here
        this.addSlot(new ResultSlot(playerInv.player, container,
                OvenBlockEntity.SLOT_OUTPUT, 124, 35));

        // Fuel slot (container slot 10)
        this.addSlot(new FuelSlot(container, OvenBlockEntity.SLOT_FUEL,
                8, 53, level));

        // 3×3 input grid (container slots 0–8)
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                this.addSlot(new Slot(container, col + row * 3,
                        30 + col * 18, 17 + row * 18));

        this.addDataSlots(data);
    }

    // ── Progress helpers (used by OvenScreen) ─────────────────────────────────

    public boolean isCrafting() {
        return data.get(OvenBlockEntity.DATA_COOKING_PROGRESS) > 0;
    }

    public boolean isFlaming() {
        return data.get(OvenBlockEntity.DATA_LIT_TIME) > 0;
    }

    /** Arrow progress scaled to [0, 26] pixels. */
    public int getArrowScaledProgress() {
        int progress    = data.get(OvenBlockEntity.DATA_COOKING_PROGRESS);
        int maxProgress = data.get(OvenBlockEntity.DATA_COOKING_TOTAL);
        return maxProgress != 0 && progress != 0 ? progress * 26 / maxProgress : 0;
    }

    /** Flame height scaled to [0, 13] pixels. */
    public int getFlameScaledProgress() {
        int duration = data.get(OvenBlockEntity.DATA_LIT_DURATION);
        if (duration == 0) duration = 200;
        return data.get(OvenBlockEntity.DATA_LIT_TIME) * 13 / duration;
    }

    // ── Shift-click logic ─────────────────────────────────────────────────────

    private static final int PLAYER_INV_START = 0;
    private static final int PLAYER_INV_END   = 36; // 27 + 9 hotbar
    private static final int TE_FIRST         = 36; // output, fuel, then grid
    private static final int TE_LAST          = TE_FIRST + OvenBlockEntity.NUM_SLOTS; // 47

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack  = slot.getItem();
        ItemStack copy   = stack.copy();

        if (index >= TE_FIRST && index < TE_LAST) {
            // TE slot → player inventory
            if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, false))
                return ItemStack.EMPTY;
        } else {
            // Player slot → try fuel first, then input grid
            if (!this.moveItemStackTo(stack, TE_FIRST + 1, TE_FIRST + 2, false)) { // fuel slot
                if (!this.moveItemStackTo(stack, TE_FIRST + 2, TE_LAST, false)) {  // input grid
                    // Fall back within player inv
                    if (index < 27) {
                        if (!this.moveItemStackTo(stack, 27, PLAYER_INV_END, false))
                            return ItemStack.EMPTY;
                    } else {
                        if (!this.moveItemStackTo(stack, PLAYER_INV_START, 27, false))
                            return ItemStack.EMPTY;
                    }
                }
            }
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

        FuelSlot(Container container, int slot, int x, int y, Level level) {
            super(container, slot, x, y);
            this.level = level;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getBurnTime(
                    GotModRecipeTypes.OVEN.get(),
                    level.fuelValues()) > 0;
        }
    }

    private static class ResultSlot extends Slot {
        ResultSlot(Player player, Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }
        @Override public boolean mayPlace(ItemStack stack) { return false; }
    }
}
