package net.got.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

/**
 * Custom chest-boat entity for GoT wood types.
 */
public class GotChestBoat extends ChestBoat {

    private final ResourceLocation boatTexture;
    private final Supplier<Item> dropItemSupplier;

    public GotChestBoat(EntityType<? extends GotChestBoat> type, Level level,
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
