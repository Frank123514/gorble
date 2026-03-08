package net.got.item;

import net.got.entity.GotBoat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BoatItem;

/**
 * Custom BoatItem for GoT wood types.
 *
 * Extends vanilla BoatItem but supplies our custom EntityType at construction,
 * bypassing any vanilla hardcoded oak-variant assumptions in BoatItem.
 * The entity type is stored in the parent and used when the item is right-clicked.
 */
public class GotBoatItem extends BoatItem {

    public GotBoatItem(EntityType<GotBoat> entityType, Properties properties) {
        super(entityType, properties);
    }
}
