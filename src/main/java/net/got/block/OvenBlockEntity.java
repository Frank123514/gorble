package net.got.block;

import net.got.init.GotModBlockEntities;
import net.got.menu.OvenMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OvenBlockEntity extends BaseContainerBlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(OvenMenu.TOTAL_SLOTS, ItemStack.EMPTY);

    private int burnTime;
    private int burnDuration;

    protected final ContainerData dataAccess = new ContainerData() {
        @Override public int get(int i) {
            return switch(i) { case 0 -> burnTime; case 1 -> burnDuration; default -> 0; };
        }
        @Override public void set(int i, int v) {
            switch(i) { case 0 -> burnTime = v; case 1 -> burnDuration = v; }
        }
        @Override public int getCount() { return 2; }
    };

    public OvenBlockEntity(BlockPos pos, BlockState state) {
        super(GotModBlockEntities.OVEN.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.got.oven");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new OvenMenu(id, inventory, this, dataAccess);
    }

    @Override
    protected NonNullList<ItemStack> getItems() { return items; }

    @Override
    protected void setItems(NonNullList<ItemStack> items) { this.items = items; }

    @Override
    public int getContainerSize() { return OvenMenu.TOTAL_SLOTS; }

    /** Tick: burn fuel down, update lit state. */
    public static void serverTick(net.minecraft.world.level.Level level, BlockPos pos,
                                   BlockState state, OvenBlockEntity be) {
        boolean wasLit = be.burnTime > 0;
        if (be.burnTime > 0) {
            be.burnTime--;
        }
        // Refuel if fuel slot has an item and burn is out
        if (be.burnTime == 0) {
            ItemStack fuel = be.items.get(OvenMenu.FUEL_SLOT);
            int fuelTime = net.neoforged.neoforge.common.CommonHooks.getBurnTime(fuel, null);
            if (fuelTime > 0) {
                be.burnDuration = fuelTime;
                be.burnTime = fuelTime;
                fuel.shrink(1);
                if (fuel.isEmpty()) be.items.set(OvenMenu.FUEL_SLOT, ItemStack.EMPTY);
            } else {
                be.burnDuration = 0;
            }
        }
        boolean isLit = be.burnTime > 0;
        if (wasLit != isLit) {
            state = state.setValue(net.minecraft.world.level.block.AbstractFurnaceBlock.LIT, isLit);
            level.setBlock(pos, state, 3);
            be.setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("BurnTime", burnTime);
        tag.putInt("BurnDuration", burnDuration);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);
        burnTime = tag.getInt("BurnTime");
        burnDuration = tag.getInt("BurnDuration");
    }
}
