package net.got.event.entity;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class GotChestBoat extends ChestBoat {

    private final Identifier boatTexture;

    public GotChestBoat(EntityType<? extends GotChestBoat> type, Level level,
                        Identifier texture, Supplier<Item> dropItem) {
        super(type, level, dropItem);
        this.boatTexture = texture;
    }

    public Identifier getBoatTexture() {
        return boatTexture;
    }
}
