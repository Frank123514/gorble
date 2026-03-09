package net.got.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.item.BoatItem;

/**
 * Custom BoatItem for GoT wood types — works for both regular and chest boats.
 *
 * In NeoForge 1.21.3, BoatItem's constructor takes EntityType<? extends AbstractBoat>,
 * so the same item class can be used for both GotBoat and GotChestBoat entity types.
 */
public class GotBoatItem extends BoatItem {

    public GotBoatItem(EntityType<? extends AbstractBoat> entityType, Properties properties) {
        super(entityType, properties);
    }
}
