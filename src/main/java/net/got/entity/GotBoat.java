package net.got.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

/**
 * Custom boat entity for GoT wood types.
 * Stores a texture ResourceLocation (read by GotBoatRenderer) and a drop-item supplier
 * so the boat drops its own item rather than an oak boat when broken.
 */
public class GotBoat extends Boat {

    private final ResourceLocation boatTexture;
    private final Supplier<Item> dropItemSupplier;

    public GotBoat(EntityType<? extends GotBoat> type, Level level,
                   ResourceLocation texture, Supplier<Item> dropItem) {
        super(type, level, () -> Items.OAK_PLANKS);
        this.boatTexture = texture;
        this.dropItemSupplier = dropItem;
    }

    /** Called by GotBoatRenderer to determine which texture to use. */
    public ResourceLocation getBoatTexture() {
        return boatTexture;
    }

    @Override
    public Item getDropItem() {
        return dropItemSupplier.get();
    }
}
