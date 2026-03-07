package net.minecraft.client.renderer.entity.state;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitchRenderState extends LivingEntityRenderState {
    public int entityId;
    public boolean isHoldingItem;
}
