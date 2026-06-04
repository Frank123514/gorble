package net.got.entity.client.mammoth;

import net.minecraft.client.animation.AnimationDefinition;
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
