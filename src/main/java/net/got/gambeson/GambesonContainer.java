package net.got.gambeson;

import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** One-slot Container backed by the entity's gambeson attachment (what the menu Slot talks to). */
public final class GambesonContainer implements Container {

    private final LivingEntity owner;

    public GambesonContainer(LivingEntity owner) {
        this.owner = owner;
    }

    @Override public int getContainerSize() { return 1; }

    @Override public boolean isEmpty() { return GambesonSlotData.get(owner).isEmpty(); }

    @Override public ItemStack getItem(int slot) { return GambesonSlotData.get(owner); }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack current = GambesonSlotData.get(owner);
        if (current.isEmpty() || amount <= 0) return ItemStack.EMPTY;
        ItemStack removed = current.split(amount);
        GambesonSlotData.set(owner, current);
        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack current = GambesonSlotData.get(owner);
        GambesonSlotData.set(owner, ItemStack.EMPTY);
        return current;
    }

    @Override public void setItem(int slot, ItemStack stack) { GambesonSlotData.set(owner, stack); }

    /** Re-sends the (possibly mutated in place) stack to clients. */
    @Override public void setChanged() { GambesonSlotData.set(owner, GambesonSlotData.get(owner)); }

    @Override public boolean stillValid(Player player) { return owner.isAlive(); }

    @Override public void clearContent() { GambesonSlotData.set(owner, ItemStack.EMPTY); }
}
