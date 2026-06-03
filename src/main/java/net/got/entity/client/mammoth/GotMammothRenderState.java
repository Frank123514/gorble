package net.got.entity.client.mammoth;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame render snapshot for {@link net.got.entity.mammoth.GotMammothEntity}.
 */
public class GotMammothRenderState extends LivingEntityRenderState {

    /** True when the mammoth is sprinting (charging). */
    public boolean isSprinting;

    /** True when the mammoth has meaningful horizontal velocity. */
    public boolean isMoving;

    /** True when the mammoth has an active attack target. */
    public boolean isAttacking;
}
