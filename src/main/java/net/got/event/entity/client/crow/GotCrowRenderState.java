package net.got.event.entity.client.crow;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame render snapshot for {@link net.got.event.entity.crow.GotCrowEntity}.
 */
public class GotCrowRenderState extends LivingEntityRenderState {

    /** True when the crow is in water. */
    public boolean isInWater;

    /** True when the crow has horizontal velocity. */
    public boolean isMoving;

    /** Raw airTicks from entity — crow enters flight mode after ~10 ticks airborne. */
    public int airTicks;

    /** True when the crow is in flight (airTicks >= 10 and not in water). */
    public boolean isFlying;
}