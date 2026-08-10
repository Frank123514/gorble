package net.got.client.renderer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.resources.Identifier;

public interface GotBoat {
    Identifier getTextureLocation(BoatRenderState state);

    ModelPart createWaterPatch();
}
