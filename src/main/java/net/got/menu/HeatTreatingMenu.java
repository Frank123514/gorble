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
 * Four ingot slots in a row, a shared fuel slot below them, and no separate
 * output slot — each ingot slot doubles as its own result slot. Drop a raw
 * ingot into any of the four slots and, once heated, the malleable ("heated")
 * result simply reappears in that same slot. The heated result can then be
 * taken to a Smithing Anvil and worked into a finished item.
 * <p>
 * Slot pixel positions are measured directly from heating.png:
 *   Ingot A-D : (53,17) (71,17) (89,17) (107,17)  — 18×18 each, matching the
 *               alloying mode's input row exactly so the textures line up.
 *   Fuel      : (81,53)                            — 18×18
 * <p>
 * Slot layout in the menu (indices used by AbstractContainerMenu.slots):
 *   0–26  — player main inventory
 *   27–35 — player hotbar
 *   36    — ingot slot A (container slot = SLOT_ALLOY_A)
 *   37    — ingot slot B (container slot = SLOT_ALLOY_B)
 *   38    — ingot slot C (container slot = SLOT_ALLOY_C)
 *   39    — ingot slot D (container slot = SLOT_ALLOY_D)
 *   40    — forge fuel   (container slot = SLOT_FUEL)
 */
public class HeatTreatingMenu extends AbstractContainerMenu {

    public static final int INGOT_A_X = 53; public static final int INGOT_A_Y = 17;
    public static final int INGOT_B_X = 71; public static final int INGOT_B_Y = 17;
    public static final int INGOT_C_X = 89; public static final int INGOT_C_Y = 17;
    public static final int INGOT_D_X = 107; public static final int INGOT_D_Y = 17;
    public static final int FUEL_X    = 81; public static final int FUEL_Y    = 53;

    private final Container   container;
    private final ContainerData data;
    private final Level         level;

    private static final int PLAYER_INV_START = 0;
    private static final int PLAYER_INV_END   = 36;
    private static final int TE_INGOT_A_IDX   = 36;
    private static final int TE_INGOT_B_IDX   = 37;
    private static final int TE_INGOT_C_IDX   = 38;
    private static final int TE_INGOT_D_IDX   = 39;
    private static final int TE_FUEL_IDX      = 40;

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

        // Four ingot slots — each one is its own result slot, so a plain Slot
        // is all that's needed (no ResultSlot restriction on placement).
        this.addSlot(new Slot(container, ForgeBlockEntity.SLOT_ALLOY_A, INGOT_A_X, INGOT_A_Y));
        this.addSlot(new Slot(container, ForgeBlockEntity.SLOT_ALLOY_B, INGOT_B_X, INGOT_B_Y));
        this.addSlot(new Slot(container, ForgeBlockEntity.SLOT_ALLOY_C, INGOT_C_X, INGOT_C_Y));
        this.addSlot(new Slot(container, ForgeBlockEntity.SLOT_ALLOY_D, INGOT_D_X, INGOT_D_Y));
        this.addSlot(new FuelSlot(container, ForgeBlockEntity.SLOT_FUEL, FUEL_X, FUEL_Y, level));

        this.addDataSlots(data);
    }

    // ── Progress helpers (used by HeatTreatingScreen) ─────────────────────────

    public boolean isFlaming() {
        return data.get(ForgeBlockEntity.DATA_LIT_TIME) > 0;
    }

    public int getFlameProgress() {
        int duration = data.get(ForgeBlockEntity.DATA_LIT_DURATION);
        if (duration == 0) duration = 200;
        return data.get(ForgeBlockEntity.DATA_LIT_TIME) * 13 / duration;
    }

    /** Returns 0–1 heat fraction for ingot slot {@code index} (0=A .. 3=D). */
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

    /** Highest heat fraction across all four slots — drives the temperature gauge. */
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
                if (!this.moveItemStackTo(stack, TE_INGOT_A_IDX, TE_INGOT_D_IDX + 1, false))
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
}
