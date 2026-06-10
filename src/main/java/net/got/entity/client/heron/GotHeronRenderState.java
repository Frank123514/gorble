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

    /** True when the heron is actively flying (not on the ground).
     * Derived from {@code !Entity#onGround()}. */
    public boolean isFlying;

    /** Raw airTicks from entity — heron enters flight mode after ~15 ticks airborne. */
    public int airTicks;

    /** Texture variant: 0 = grey, 1 = blue, 2 = white, 3 = night. */
    public int variant;
}