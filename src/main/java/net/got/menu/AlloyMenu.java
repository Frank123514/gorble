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
 * AlloyMenu — container menu for the Forge's alloying mode.
 *
 * Uses SLOT_ALLOY_A/B/C/D (slots 2-5) and SLOT_OUTPUT (slot 1) — completely
 * separate from heat treating's SLOT_HEAT_A/B/C/D (slots 6-9).
 * Only SLOT_FUEL (slot 0) is shared.
 *
 * Slot pixel positions from forge.png:
 *   Input A-D : (20,17) (38,17) (56,17) (74,17)
 *   Fuel      : (48,53)
 *   Output    : (134,18)
 */
public class AlloyMenu extends AbstractContainerMenu {

    public static final int INPUT_A_X = 20;  public static final int INPUT_A_Y = 17;
    public static final int INPUT_B_X = 38;  public static final int INPUT_B_Y = 17;
    public static final int INPUT_C_X = 56;  public static final int INPUT_C_Y = 17;
    public static final int INPUT_D_X = 74;  public static final int INPUT_D_Y = 17;
    public static final int FUEL_X    = 48;  public static final int FUEL_Y    = 53;
    public static final int OUTPUT_X  = 134; public static final int OUTPUT_Y  = 18;

    private final Container     container;
    private final ContainerData data;
    private final Level         level;

    private static final int PLAYER_INV_START = 0;
    private static final int PLAYER_INV_END   = 36;
    private static final int TE_INPUT_A_IDX   = 36;
    private static final int TE_INPUT_B_IDX   = 37;
    private static final int TE_INPUT_C_IDX   = 38;
    private static final int TE_INPUT_D_IDX   = 39;
    private static final int TE_FUEL_IDX      = 40;
    private static final int TE_OUTPUT_IDX    = 41;

    public AlloyMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv,
                new SimpleContainer(ForgeBlockEntity.NUM_SLOTS),
                new SimpleContainerData(ForgeBlockEntity.NUM_DATA));
    }

    public AlloyMenu(int windowId, Inventory playerInv,
                      Container container, ContainerData data) {
        super(GotModMenus.ALLOY.get(), windowId);
        this.container = container;
        this.data      = data;
        this.level     = playerInv.player.level();

        checkContainerSize(container, ForgeBlockEntity.NUM_SLOTS);
        checkContainerDataCount(data, ForgeBlockEntity.NUM_DATA);
        container.startOpen(playerInv.player);

        // Player inventory
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));

        // Hotbar
        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));

        // Block entity slots — alloy inputs, fuel, output
        this.addSlot(new SingleItemSlot(container, ForgeBlockEntity.SLOT_ALLOY_A, INPUT_A_X, INPUT_A_Y));
        this.addSlot(new SingleItemSlot(container, ForgeBlockEntity.SLOT_ALLOY_B, INPUT_B_X, INPUT_B_Y));
        this.addSlot(new SingleItemSlot(container, ForgeBlockEntity.SLOT_ALLOY_C, INPUT_C_X, INPUT_C_Y));
        this.addSlot(new SingleItemSlot(container, ForgeBlockEntity.SLOT_ALLOY_D, INPUT_D_X, INPUT_D_Y));
        this.addSlot(new FuelSlot(container, ForgeBlockEntity.SLOT_FUEL, FUEL_X, FUEL_Y, level));
        this.addSlot(new ResultSlot(container, ForgeBlockEntity.SLOT_OUTPUT, OUTPUT_X, OUTPUT_Y));

        this.addDataSlots(data);
    }

    // ── Progress helpers ──────────────────────────────────────────────────────

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

    public ItemStack getInputA() { return container.getItem(ForgeBlockEntity.SLOT_ALLOY_A); }
    public ItemStack getInputB() { return container.getItem(ForgeBlockEntity.SLOT_ALLOY_B); }
    public ItemStack getInputC() { return container.getItem(ForgeBlockEntity.SLOT_ALLOY_C); }
    public ItemStack getInputD() { return container.getItem(ForgeBlockEntity.SLOT_ALLOY_D); }

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
                if (!this.moveItemStackTo(stack, TE_INPUT_A_IDX, TE_INPUT_D_IDX + 1, false))
                    return ItemStack.EMPTY;
            }
        } else if (index == TE_OUTPUT_IDX) {
            if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, true))
                return ItemStack.EMPTY;
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

    private static class ResultSlot extends Slot {
        ResultSlot(Container c, int slot, int x, int y) { super(c, slot, x, y); }
        @Override public boolean mayPlace(ItemStack stack) { return false; }
    }
}
