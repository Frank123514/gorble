package net.got.hauberk;

import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** One-slot Container backed by the entity's hauberk attachment (what the menu Slot talks to). */
public final class HauberkContainer implements Container {

    private final LivingEntity owner;

    public HauberkContainer(LivingEntity owner) {
        this.owner = owner;
    }

    @Override public int getContainerSize() { return 1; }

    @Override public boolean isEmpty() { return HauberkSlotData.get(owner).isEmpty(); }

    @Override public ItemStack getItem(int slot) { return HauberkSlotData.get(owner); }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack current = HauberkSlotData.get(owner);
        if (current.isEmpty() || amount <= 0) return ItemStack.EMPTY;
        ItemStack removed = current.split(amount);
        HauberkSlotData.set(owner, current);
        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack current = HauberkSlotData.get(owner);
        HauberkSlotData.set(owner, ItemStack.EMPTY);
        return current;
    }

    @Override public void setItem(int slot, ItemStack stack) { HauberkSlotData.set(owner, stack); }

    /** Re-sends the (possibly mutated in place) stack to clients. */
    @Override public void setChanged() { HauberkSlotData.set(owner, HauberkSlotData.get(owner)); }

    @Override public boolean stillValid(Player player) { return owner.isAlive(); }

    @Override public void clearContent() { HauberkSlotData.set(owner, ItemStack.EMPTY); }
}
