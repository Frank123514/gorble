package net.got.event.entity.npc;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

/**
 * A 9-slot personal stash for an NPC.
 *
 * <p>This container travels with the entity (saved to the entity's NBT via
 * {@link #save} / {@link #load}) rather than living in a block entity.
 * It represents the NPC's personal earnings, goods they are selling,
 * and items they have gathered while working.
 *
 * <p>The NPC trade menu exposes this to the player so they can see what
 * the NPC is carrying and execute trades.
 */
public final class NpcInventory extends SimpleContainer {

    private static final String NBT_KEY = "NpcInventory";
    public static final int SIZE = 9;

    public NpcInventory() {
        super(SIZE);
    }

    // ── NBT ──────────────────────────────────────────────────────────────────

    public void save(CompoundTag entityTag, HolderLookup.Provider registries) {
        ListTag list = new ListTag();
        for (int i = 0; i < SIZE; i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag entry = new CompoundTag();
                entry.putByte("Slot", (byte) i);
                entry.put("Item", stack.save(registries));
                list.add(entry);
            }
        }
        entityTag.put(NBT_KEY, list);
    }

    public void load(CompoundTag entityTag, HolderLookup.Provider registries) {
        clearContent();
        if (!entityTag.contains(NBT_KEY)) return;
        ListTag list = entityTag.getListOrEmpty(NBT_KEY);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entry = list.getCompoundOrEmpty(i);
            int slot = entry.getByteOr("Slot", (byte) 0) & 0xFF;
            if (slot < SIZE) {
                setItem(slot, ItemStack.parseOptional(registries, entry.getCompoundOrEmpty("Item")));
            }
        }
    }
}
