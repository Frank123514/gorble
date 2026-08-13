package net.got.event.entity.npc;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public final class NpcInventory extends SimpleContainer {

    private static final String NBT_KEY = "NpcInventory";
    public static final int SIZE = 9;

    public NpcInventory() {
        super(SIZE);
    }

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
