package net.got.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.item.BoatItem;

public class GotBoatItem extends BoatItem {

    public GotBoatItem(EntityType<? extends AbstractBoat> entityType, Properties properties) {
        super(entityType, properties);
    }
}
