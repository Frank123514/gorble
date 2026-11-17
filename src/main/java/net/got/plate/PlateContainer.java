package net.got.plate;

import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** One-slot Container backed by the entity's attachment for one plate piece (what the menu Slot talks to). */
public final class PlateContainer implements Container {

    private final LivingEntity owner;
    private final PlatePiece piece;

    public PlateContainer(LivingEntity owner, PlatePiece piece) {
        this.owner = owner;
        this.piece = piece;
    }

    @Override public int getContainerSize() { return 1; }

    @Override public boolean isEmpty() { return PlateSlotData.get(owner, piece).isEmpty(); }

    @Override public ItemStack getItem(int slot) { return PlateSlotData.get(owner, piece); }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack current = PlateSlotData.get(owner, piece);
        if (current.isEmpty() || amount <= 0) return ItemStack.EMPTY;
        ItemStack removed = current.split(amount);
        PlateSlotData.set(owner, piece, current);
        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack current = PlateSlotData.get(owner, piece);
        PlateSlotData.set(owner, piece, ItemStack.EMPTY);
        return current;
    }

    @Override public void setItem(int slot, ItemStack stack) { PlateSlotData.set(owner, piece, stack); }

    /** Re-sends the (possibly mutated in place) stack to clients. */
    @Override public void setChanged() { PlateSlotData.set(owner, piece, PlateSlotData.get(owner, piece)); }

    @Override public boolean stillValid(Player player) { return owner.isAlive(); }

    @Override public void clearContent() { PlateSlotData.set(owner, piece, ItemStack.EMPTY); }
}
