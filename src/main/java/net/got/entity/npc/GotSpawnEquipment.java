package net.got.entity.npc;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

/**
 * A weighted table of items that an NPC can spawn holding, exactly mirroring
 * LOTR's {@code SpawnEquipmentTable}.
 *
 * <p>Each entry is equally weighted. Use {@link #of(Item...)} to construct one:
 * <pre>{@code
 *   private static final GotSpawnEquipment WEAPONS = GotSpawnEquipment.of(
 *       Items.IRON_SWORD, Items.STONE_SWORD, Items.WOODEN_SWORD);
 * }</pre>
 *
 * <p>Then in {@code finalizeSpawn}: {@code setMainhandItem(WEAPONS.pick(random));}
 */
public final class GotSpawnEquipment {

    /** A table that always returns {@link ItemStack#EMPTY}. */
    public static final GotSpawnEquipment EMPTY = new GotSpawnEquipment(List.of());

    private final List<Item> items;

    private GotSpawnEquipment(List<Item> items) {
        this.items = items;
    }

    /** Build a table from a vararg list of items. */
    public static GotSpawnEquipment of(Item... items) {
        return new GotSpawnEquipment(List.of(items));
    }

    /**
     * Returns a new {@link ItemStack} of a randomly selected item,
     * or {@link ItemStack#EMPTY} if the table is empty.
     */
    public ItemStack pick(RandomSource rand) {
        if (items.isEmpty()) return ItemStack.EMPTY;
        return new ItemStack(items.get(rand.nextInt(items.size())));
    }
}
