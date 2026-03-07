package net.got.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

/**
 * Custom boat entity for GoT wood types.
 * Stores a texture ResourceLocation that GotBoatRenderer reads via getBoatTexture().
 * The texture is set at registration time in GotModBoatEntities via a lambda factory.
 */
public class GotBoat extends Boat {

    private final ResourceLocation boatTexture;

    public GotBoat(EntityType<? extends GotBoat> type, Level level, ResourceLocation texture) {
        super(type, level, () -> Items.OAK_PLANKS);
        this.boatTexture = texture;
    }

    /** Called by GotBoatRenderer to determine which texture to use. */
    public ResourceLocation getBoatTexture() {
        return boatTexture;
    }
}