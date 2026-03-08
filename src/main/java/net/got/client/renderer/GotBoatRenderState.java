package net.got.client.renderer;

import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.resources.ResourceLocation;

/** Extended BoatRenderState that carries the per-wood texture ResourceLocation. */
public class GotBoatRenderState extends BoatRenderState {
    public ResourceLocation boatTexture = null;
}
