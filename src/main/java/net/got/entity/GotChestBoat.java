package net.got.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

/**
 * Custom chest-boat entity for GoT wood types.
 * Passes the drop-item supplier directly to AbstractBoat (getDropItem() is final in 1.21.x).
 */
public class GotChestBoat extends ChestBoat {

    private final ResourceLocation boatTexture;

    public GotChestBoat(EntityType<? extends GotChestBoat> type, Level level,
                        ResourceLocation texture, Supplier<Item> dropItem) {
        super(type, level, dropItem);
        this.boatTexture = texture;
    }

    /** Called by GotBoatRenderer to determine which texture to use. */
    public ResourceLocation getBoatTexture() {
        return boatTexture;
    }
}
