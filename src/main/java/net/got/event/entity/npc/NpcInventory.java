package net.got.event.entity.npc;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

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

    public void save(ValueOutput output) {
        ValueOutput.ValueOutputList list = output.childrenList(NBT_KEY);
        for (int i = 0; i < SIZE; i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()) {
                ValueOutput entry = list.addChild();
                entry.putByte("Slot", (byte) i);
                entry.store("Item", ItemStack.CODEC, stack);
            }
        }
    }

    public void load(ValueInput input) {
        clearContent();
        for (ValueInput entry : input.childrenListOrEmpty(NBT_KEY)) {
            int slot = entry.getByteOr("Slot", (byte) 0) & 0xFF;
            if (slot < SIZE) {
                setItem(slot, entry.read("Item", ItemStack.CODEC).orElse(ItemStack.EMPTY));
            }
        }
    }
}
