package net.got.entity.client.direwolf;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame render snapshot for {@link net.got.entity.direwolf.GotDirewolfEntity}.
 *
 * <p>Fields populated each frame by {@link GotDirewolfRenderer#extractRenderState}
 * and consumed by the animation selector in {@link GotDirewolfRenderer#render}.
 */
public class GotDirewolfRenderState extends LivingEntityRenderState {

    /** True when the direwolf is sprinting / fleeing / chasing. */
    public boolean isSprinting;

    /**
     * True when the direwolf has meaningful horizontal velocity.
     * Derived from {@code Entity#getDeltaMovement().horizontalDistanceSqr() > 1e-6}.
     */
    public boolean isMoving;

    /** True when the direwolf has an active attack target. */
    public boolean isAttacking;

    /** True when the direwolf is sitting (ordered to sit or resting). */
    public boolean isSitting;
}
