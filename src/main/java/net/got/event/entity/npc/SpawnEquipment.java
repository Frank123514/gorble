package net.got.event.entity.npc;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class SpawnEquipment {

    public static final SpawnEquipment EMPTY = new SpawnEquipment(List.of());

    private final List<Item> items;

    private SpawnEquipment(List<Item> items) {
        this.items = items;
    }

    public static SpawnEquipment of(Item... items) {
        return new SpawnEquipment(List.of(items));
    }

    public ItemStack pick(RandomSource rand) {
        if (items.isEmpty()) return ItemStack.EMPTY;
        return new ItemStack(items.get(rand.nextInt(items.size())));
    }
}
