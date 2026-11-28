package net.got.cloak;

import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** One-slot Container backed by the entity's cloak attachment (what the menu Slot talks to). */
public final class CloakContainer implements Container {

    private final LivingEntity owner;

    public CloakContainer(LivingEntity owner) {
        this.owner = owner;
    }

    @Override public int getContainerSize() { return 1; }

    @Override public boolean isEmpty() { return CloakSlotData.get(owner).isEmpty(); }

    @Override public ItemStack getItem(int slot) { return CloakSlotData.get(owner); }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack current = CloakSlotData.get(owner);
        if (current.isEmpty() || amount <= 0) return ItemStack.EMPTY;
        ItemStack removed = current.split(amount);
        CloakSlotData.set(owner, current);
        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack current = CloakSlotData.get(owner);
        CloakSlotData.set(owner, ItemStack.EMPTY);
        return current;
    }

    @Override public void setItem(int slot, ItemStack stack) { CloakSlotData.set(owner, stack); }

    /** Re-sends the (possibly mutated in place) stack to clients. */
    @Override public void setChanged() { CloakSlotData.set(owner, CloakSlotData.get(owner)); }

    @Override public boolean stillValid(Player player) { return owner.isAlive(); }

    @Override public void clearContent() { CloakSlotData.set(owner, ItemStack.EMPTY); }
}
