package net.got.entity.client.stag;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame render snapshot for {@link net.got.entity.stag.GotStagEntity}.
 *
 * <p>Extends {@link LivingEntityRenderState} (not {@code HorseRenderState}
 * from the old implementation) because the stag renderer no longer inherits
 * from {@code AbstractHorseRenderer}.
 *
 * <p>Fields are populated each frame by
 * {@link GotStagRenderer#extractRenderState} and consumed by the animation
 * selector in {@link GotStagRenderer#render}.
 */
public class GotStagRenderState extends LivingEntityRenderState {

    /** True when the stag is rearing up ({@code AbstractHorse#isStanding}). */
    public boolean isStanding;

    /** True when the stag is submerged in a liquid. */
    public boolean isInWater;

    /** True when the stag is sprinting. */
    public boolean isSprinting;

    /**
     * True when the stag has meaningful horizontal velocity.
     * Derived from {@code Entity#getDeltaMovement().horizontalDistanceSqr() > 1e-6}.
     */
    public boolean isMoving;

    /** True when the stag has been tamed by a player. */
    public boolean isTame;
}
