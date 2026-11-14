package net.got.brigandine;

import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** One-slot Container backed by the entity's brigandine attachment (what the menu Slot talks to). */
public final class BrigandineContainer implements Container {

    private final LivingEntity owner;

    public BrigandineContainer(LivingEntity owner) {
        this.owner = owner;
    }

    @Override public int getContainerSize() { return 1; }

    @Override public boolean isEmpty() { return BrigandineSlotData.get(owner).isEmpty(); }

    @Override public ItemStack getItem(int slot) { return BrigandineSlotData.get(owner); }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack current = BrigandineSlotData.get(owner);
        if (current.isEmpty() || amount <= 0) return ItemStack.EMPTY;
        ItemStack removed = current.split(amount);
        BrigandineSlotData.set(owner, current);
        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack current = BrigandineSlotData.get(owner);
        BrigandineSlotData.set(owner, ItemStack.EMPTY);
        return current;
    }

    @Override public void setItem(int slot, ItemStack stack) { BrigandineSlotData.set(owner, stack); }

    /** Re-sends the (possibly mutated in place) stack to clients. */
    @Override public void setChanged() { BrigandineSlotData.set(owner, BrigandineSlotData.get(owner)); }

    @Override public boolean stillValid(Player player) { return owner.isAlive(); }

    @Override public void clearContent() { BrigandineSlotData.set(owner, ItemStack.EMPTY); }
}
