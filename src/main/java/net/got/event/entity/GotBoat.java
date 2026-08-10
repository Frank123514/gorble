package net.got.event.entity;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

/**
 * Custom boat entity for GoT wood types.
 * Stores a texture Identifier (read by GotBoatRenderer) and passes the
 * drop-item supplier directly to AbstractBoat (getDropItem() is final in 1.21.x).
 */
public class GotBoat extends Boat {

    private final Identifier boatTexture;

    public GotBoat(EntityType<? extends GotBoat> type, Level level,
                   Identifier texture, Supplier<Item> dropItem) {
        super(type, level, dropItem);
        this.boatTexture = texture;
    }

    /** Called by GotBoatRenderer to determine which texture to use. */
    public Identifier getBoatTexture() {
        return boatTexture;
    }
}
