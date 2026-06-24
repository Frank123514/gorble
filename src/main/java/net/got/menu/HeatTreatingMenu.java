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
 *
 * Uses SLOT_HEAT_A/B/C/D (slots 6-9) — completely separate from alloying's
 * SLOT_ALLOY_A/B/C/D (slots 2-5). Only SLOT_FUEL (slot 0) is shared.
 *
 * Slot pixel positions from heating.png:
 *   Heat A-D : (53,17) (71,17) (89,17) (107,17)
 *   Fuel     : (80,53)
 */
public class HeatTreatingMenu extends AbstractContainerMenu {

    public static final int HEAT_A_X = 53; public static final int HEAT_A_Y = 17;
    public static final int HEAT_B_X = 71; public static final int HEAT_B_Y = 17;
    public static final int HEAT_C_X = 89; public static final int HEAT_C_Y = 17;
    public static final int HEAT_D_X = 107; public static final int HEAT_D_Y = 17;
    public static final int FUEL_X   = 80;  public static final int FUEL_Y   = 53;

    private final Container   container;
    private final ContainerData data;
    private final Level         level;

    private static final int PLAYER_INV_START  = 0;
    private static final int PLAYER_INV_END    = 36;
    private static final int TE_HEAT_A_IDX     = 36;
    private static final int TE_HEAT_B_IDX     = 37;
    private static final int TE_HEAT_C_IDX     = 38;
    private static final int TE_HEAT_D_IDX     = 39;
    private static final int TE_FUEL_IDX       = 40;

    public HeatTreatingMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv,
                new SimpleContainer(ForgeBlockEntity.NUM_SLOTS),
                new SimpleContainerData(ForgeBlockEntity.NUM_DATA));
    }

    public HeatTreatingMenu(int windowId, Inventory playerInv,
                            Container container, ContainerData data) {
        super(GotModMenus.HEAT_TREATING.get(), windowId);
        this.container = container;
        this.data      = data;
        this.level     = playerInv.player.level();

        checkContainerSize(container, ForgeBlockEntity.NUM_SLOTS);
        checkContainerDataCount(data, ForgeBlockEntity.NUM_DATA);
        container.startOpen(playerInv.player);

        // Player main inventory
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));

        // Hotbar
        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));

        // Four independent heat slots
        this.addSlot(new SingleItemSlot(container, ForgeBlockEntity.SLOT_HEAT_A, HEAT_A_X, HEAT_A_Y));
        this.addSlot(new SingleItemSlot(container, ForgeBlockEntity.SLOT_HEAT_B, HEAT_B_X, HEAT_B_Y));
        this.addSlot(new SingleItemSlot(container, ForgeBlockEntity.SLOT_HEAT_C, HEAT_C_X, HEAT_C_Y));
        this.addSlot(new SingleItemSlot(container, ForgeBlockEntity.SLOT_HEAT_D, HEAT_D_X, HEAT_D_Y));
        this.addSlot(new FuelSlot(container, ForgeBlockEntity.SLOT_FUEL, FUEL_X, FUEL_Y, level));

        this.addDataSlots(data);
    }

    // ── Progress helpers ──────────────────────────────────────────────────────

    public boolean isFlaming() {
        return data.get(ForgeBlockEntity.DATA_LIT_TIME) > 0;
    }

    public int getFlameProgress() {
        int duration = data.get(ForgeBlockEntity.DATA_LIT_DURATION);
        if (duration == 0) duration = 200;
        return data.get(ForgeBlockEntity.DATA_LIT_TIME) * 13 / duration;
    }

    public float getHeatFraction(int index) {
        int total = data.get(ForgeBlockEntity.DATA_COOKING_TOTAL);
        if (total <= 0) return 0f;
        int progress = switch (index) {
            case 0 -> data.get(ForgeBlockEntity.DATA_HEAT_PROGRESS_A);
            case 1 -> data.get(ForgeBlockEntity.DATA_HEAT_PROGRESS_B);
            case 2 -> data.get(ForgeBlockEntity.DATA_HEAT_PROGRESS_C);
            case 3 -> data.get(ForgeBlockEntity.DATA_HEAT_PROGRESS_D);
            default -> 0;
        };
        return Math.max(0f, Math.min(1f, progress / (float) total));
    }

    public float getMaxHeatFraction() {
        float max = 0f;
        for (int i = 0; i < 4; i++) max = Math.max(max, getHeatFraction(i));
        return max;
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
                if (!this.moveItemStackTo(stack, TE_HEAT_A_IDX, TE_HEAT_D_IDX + 1, false))
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

    @Override public boolean stillValid(Player player) { return container.stillValid(player); }

    @Override
    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }

    // ── Inner slot types ──────────────────────────────────────────────────────

    private static class SingleItemSlot extends Slot {
        SingleItemSlot(Container c, int slot, int x, int y) { super(c, slot, x, y); }
        @Override public int getMaxStackSize() { return 1; }
        @Override public int getMaxStackSize(ItemStack stack) { return 1; }
    }

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
}
