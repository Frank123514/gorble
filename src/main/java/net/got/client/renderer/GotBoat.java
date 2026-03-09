package net.got.client.renderer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.resources.ResourceLocation;

public interface GotBoat {
    ResourceLocation getTextureLocation(BoatRenderState state);

    ModelPart createWaterPatch();
}
