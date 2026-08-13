package net.got.event.entity.client.heron;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class HeronRenderState extends LivingEntityRenderState {

    public boolean isInWater;

    public boolean isMoving;

    public boolean isFlying;

    public int airTicks;

    public int variant;
}