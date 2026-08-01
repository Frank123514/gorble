package net.got.event.entity.client.brownbear;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame render snapshot for {@link net.got.event.entity.brownbear.GotBrownBearEntity}.
 */
public class GotBrownBearRenderState extends LivingEntityRenderState {

    /** True when the bear is submerged in water. */
    public boolean isInWater;

    /** True when the bear is actively sprinting. */
    public boolean isSprinting;

    /** True when the bear has meaningful horizontal velocity. */
    public boolean isMoving;

    /** True when the bear is angered / in combat state. */
    public boolean isAngry;

    /** True when the bear is dead or dying. */
    public boolean isDeadOrDying;

    /** True when the bear is executing a melee attack swing. */
    public boolean isAttacking;

    /** True when the bear is rearing up on its hind legs (STAND animation). */
    public boolean isStanding;
}
