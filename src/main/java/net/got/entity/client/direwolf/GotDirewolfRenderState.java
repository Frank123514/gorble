package net.got.entity.client.direwolf;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame render snapshot for {@link net.got.entity.direwolf.GotDirewolfEntity}.
 */
public class GotDirewolfRenderState extends LivingEntityRenderState {

    /** True when submerged in water. */
    public boolean isInWater;

    /** True when sprinting / fleeing / chasing. */
    public boolean isSprinting;

    /** True when the wolf has meaningful horizontal velocity. */
    public boolean isMoving;

    /** True when actively attacking a target. */
    public boolean isAttacking;

    /** True when sitting and idle — triggers the howl animation. */
    public boolean isHowling;

    /** True when ordered to sit (tamed only). */
    public boolean isSitting;

    // ── One-shot animation tracking ──────────────────────────────────────────

    /**
     * The animation that was selected on the previous render frame.
     * Used to detect when a new one-shot animation begins so the local
     * timer can be reset to zero.
     */
    public AnimationDefinition lastAnimation = null;

    /**
     * The {@code ageInTicks} value recorded when the current one-shot
     * animation started.  Subtracted from {@code ageInTicks} to give a
     * local timer that always starts at 0 for one-shot animations.
     */
    public float animationStartTick = 0F;
}
