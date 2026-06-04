package net.got.entity.client.direwolf;

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
}

