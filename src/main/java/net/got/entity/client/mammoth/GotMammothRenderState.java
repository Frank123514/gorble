package net.got.entity.client.mammoth;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame render snapshot for {@link net.got.entity.mammoth.GotMammothEntity}.
 */
public class GotMammothRenderState extends LivingEntityRenderState {

    /** True when the mammoth is submerged in water. */
    public boolean isInWater;

    /** True when the mammoth is actively charging (sprinting). */
    public boolean isSprinting;

    /** True when the mammoth has meaningful horizontal velocity. */
    public boolean isMoving;

    /** True when the mammoth is in combat / angered state. */
    public boolean isAngry;

    /** True when the mammoth is dead or dying (triggers death fall animation). */
    public boolean isDeadOrDying;

    /** True when the mammoth is executing a melee attack swing. */
    public boolean isAttacking;

    /** True when a giant is currently riding this mammoth. */
    public boolean hasGiantRider;
}
