package net.got.entity.client.stag;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame render snapshot for {@link net.got.entity.stag.GotStagEntity}.
 *
 * <p>Extends {@link LivingEntityRenderState} — the stag renderer does not
 * inherit from {@code AbstractHorseRenderer}, and the stag entity no longer
 * extends {@code Horse}, so no horse-specific state fields are needed.
 *
 * <p>Fields are populated each frame by
 * {@link GotStagRenderer#extractRenderState} and consumed by the animation
 * selector in {@link GotStagRenderer#render}.
 */
public class GotStagRenderState extends LivingEntityRenderState {

    /** True when the stag is submerged in a liquid. */
    public boolean isInWater;

    /** True when the stag is sprinting (e.g. fleeing). */
    public boolean isSprinting;

    /**
     * True when the stag has meaningful horizontal velocity.
     * Derived from {@code Entity#getDeltaMovement().horizontalDistanceSqr() > 1e-6}.
     */
    public boolean isMoving;
}
