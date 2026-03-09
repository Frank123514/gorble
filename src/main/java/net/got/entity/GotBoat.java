package net.got.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

/**
 * Custom boat entity for GoT wood types.
 * Stores a texture ResourceLocation (read by GotBoatRenderer) and passes the
 * drop-item supplier directly to AbstractBoat (getDropItem() is final in 1.21.x).
 */
public class GotBoat extends Boat {

    private final ResourceLocation boatTexture;

    public GotBoat(EntityType<? extends GotBoat> type, Level level,
                   ResourceLocation texture, Supplier<Item> dropItem) {
        super(type, level, dropItem);
        this.boatTexture = texture;
    }

    /** Called by GotBoatRenderer to determine which texture to use. */
    public ResourceLocation getBoatTexture() {
        return boatTexture;
    }
}
