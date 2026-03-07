package net.got.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractBoat;

public interface GotBoatRender {
    ResourceLocation getTextureLocation(AbstractBoat entity);
}
