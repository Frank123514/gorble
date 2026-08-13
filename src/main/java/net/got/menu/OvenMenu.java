package net.got.menu;

import net.got.block.OvenBlockEntity;
import net.got.init.ModMenus;
import net.got.init.ModRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OvenMenu extends AbstractContainerMenu {

    private final Container   container;
    private final ContainerData data;

    public OvenMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv,
                new SimpleContainer(OvenBlockEntity.NUM_SLOTS),
                new SimpleContainerData(OvenBlockEntity.NUM_DATA));
    }

    public OvenMenu(int windowId, Inventory playerInv,
                    Container container, ContainerData data) {
        super(ModMenus.OVEN.get(), windowId);
        this.container = container;
        this.data      = data;

        checkContainerSize(container, OvenBlockEntity.NUM_SLOTS);
        checkContainerDataCount(data, OvenBlockEntity.NUM_DATA);
        container.startOpen(playerInv.player);

        Level level = playerInv.player.level();

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));

        for (int col = 0; col < 9; col++)
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));

        this.addSlot(new ResultSlot(playerInv.player, container,
                OvenBlockEntity.SLOT_OUTPUT, 124, 35));

        this.addSlot(new FuelSlot(container, OvenBlockEntity.SLOT_FUEL,
                8, 53, level));

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                this.addSlot(new Slot(container, col + row * 3,
                        30 + col * 18, 17 + row * 18));

        this.addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(OvenBlockEntity.DATA_COOKING_PROGRESS) > 0;
    }

    public boolean isFlaming() {
        return data.get(OvenBlockEntity.DATA_LIT_TIME) > 0;
    }

    public int getArrowScaledProgress() {
        int progress    = data.get(OvenBlockEntity.DATA_COOKING_PROGRESS);
        int maxProgress = data.get(OvenBlockEntity.DATA_COOKING_TOTAL);
        return maxProgress != 0 && progress != 0 ? progress * 26 / maxProgress : 0;
    }

    public int getFlameScaledProgress() {
        int duration = data.get(OvenBlockEntity.DATA_LIT_DURATION);
        if (duration == 0) duration = 200;
        return data.get(OvenBlockEntity.DATA_LIT_TIME) * 13 / duration;
    }

    private static final int PLAYER_INV_START = 0;
    private static final int PLAYER_INV_END   = 36;
    private static final int TE_FIRST         = 36;
    private static final int TE_LAST          = TE_FIRST + OvenBlockEntity.NUM_SLOTS;

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack  = slot.getItem();
        ItemStack copy   = stack.copy();

        if (index >= TE_FIRST && index < TE_LAST) {
            
            if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, false))
                return ItemStack.EMPTY;
        } else {
            
            if (!this.moveItemStackTo(stack, TE_FIRST + 1, TE_FIRST + 2, false)) {
                if (!this.moveItemStackTo(stack, TE_FIRST + 2, TE_LAST, false)) {
                    
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

    private static class FuelSlot extends Slot {
        private final Level level;

        FuelSlot(Container container, int slot, int x, int y, Level level) {
            super(container, slot, x, y);
            this.level = level;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getBurnTime(
                    ModRecipeTypes.OVEN.get(),
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
