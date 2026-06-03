package net.got.entity.client.heron;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame render snapshot for {@link net.got.entity.heron.GotHeronEntity}.
 *
 * <p>Fields are populated each frame by
 * {@link GotHeronRenderer#extractRenderState} and consumed by the animation
 * selector in {@link GotHeronRenderer#render}.
 */
public class GotHeronRenderState extends LivingEntityRenderState {

    /** True when the heron is submerged in a liquid (wading/swimming). */
    public boolean isInWater;

    /**
     * True when the heron has meaningful horizontal velocity.
     * Derived from {@code Entity#getDeltaMovement().horizontalDistanceSqr() > 1e-6}.
     */
    public boolean isMoving;

    /**
     * True when the heron is actively flying (not on the ground).
     * Derived from {@code !Entity#onGround()}.
     */
    public boolean isFlying;

    /** Ticks the heron has been continuously airborne. */
    public int airTicks;
}