package net.got.coif;

import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** One-slot Container backed by the entity's coif attachment (what the menu Slot talks to). */
public final class CoifContainer implements Container {

    private final LivingEntity owner;

    public CoifContainer(LivingEntity owner) {
        this.owner = owner;
    }

    @Override public int getContainerSize() { return 1; }

    @Override public boolean isEmpty() { return CoifSlotData.get(owner).isEmpty(); }

    @Override public ItemStack getItem(int slot) { return CoifSlotData.get(owner); }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack current = CoifSlotData.get(owner);
        if (current.isEmpty() || amount <= 0) return ItemStack.EMPTY;
        ItemStack removed = current.split(amount);
        CoifSlotData.set(owner, current);
        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack current = CoifSlotData.get(owner);
        CoifSlotData.set(owner, ItemStack.EMPTY);
        return current;
    }

    @Override public void setItem(int slot, ItemStack stack) { CoifSlotData.set(owner, stack); }

    /** Re-sends the (possibly mutated in place) stack to clients. */
    @Override public void setChanged() { CoifSlotData.set(owner, CoifSlotData.get(owner)); }

    @Override public boolean stillValid(Player player) { return owner.isAlive(); }

    @Override public void clearContent() { CoifSlotData.set(owner, ItemStack.EMPTY); }
}
