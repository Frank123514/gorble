package net.got.client.animation;

import net.minecraft.client.animation.AnimationDefinition;

public final class GotPlayerAnimator {

    public static final GotPlayerAnimator INSTANCE = new GotPlayerAnimator();
    private GotPlayerAnimator() {}

    private static final org.slf4j.Logger LOGGER =
            org.slf4j.LoggerFactory.getLogger("GotPlayerAnimator");

    private AnimationDefinition currentAnimation = null;
    private GotArmPose currentPose = GotArmPose.NONE;
    private boolean isBlocking = false;
    private float animTicks = 0F;   // counts up each client tick while playing

    // ── Internal helpers ──────────────────────────────────────────────────────

    private AnimationDefinition animationFor(GotArmPose pose) {
        return switch (pose) {
            case SWORD      -> GotPlayerCombatAnimations.SWORD_ATTACK;
            case GREATSWORD -> GotPlayerCombatAnimations.GREATSWORD_ATTACK;
            case AXE        -> GotPlayerCombatAnimations.AXE_ATTACK;
            case SPEAR      -> GotPlayerCombatAnimations.SPEAR_ATTACK;
            case BLOCK      -> GotPlayerCombatAnimations.SWORD_BLOCK;
            default         -> null;
        };
    }

    // ── Called by GotCombatAnimationHandler ───────────────────────────────────

    public void triggerAttack(GotArmPose pose) {
        if (pose == GotArmPose.NONE || pose == GotArmPose.BLOCK) return;
        LOGGER.info("[GOT-ANIM] triggerAttack pose={}", pose);
        currentPose      = pose;
        currentAnimation = animationFor(pose);
        animTicks        = 0F;
    }

    public void setBlocking(boolean blocking) {
        this.isBlocking = blocking;
        if (blocking && currentPose != GotArmPose.BLOCK) {
            currentPose      = GotArmPose.BLOCK;
            currentAnimation = GotPlayerCombatAnimations.SWORD_BLOCK;
            animTicks        = 0F;
        } else if (!blocking && currentPose == GotArmPose.BLOCK) {
            currentPose      = GotArmPose.NONE;
            currentAnimation = null;
            animTicks        = 0F;
        }
    }

    /** Called every client tick by GotCombatAnimationHandler. */
    public void tick() {
        if (currentAnimation == null) return;
        animTicks += 1F;
        float maxTicks = currentAnimation.lengthInSeconds() * 20F;
        if (!currentAnimation.looping() && animTicks >= maxTicks) {
            if (isBlocking) {
                animTicks = maxTicks; // hold last frame
            } else {
                currentAnimation = null;
                currentPose      = GotArmPose.NONE;
                animTicks        = 0F;
            }
        }
    }

    // ── Called by GotPlayerRenderer ───────────────────────────────────────────

    public AnimationDefinition getCurrentAnimation() {
        return currentAnimation;
    }

    public float getCurrentAnimationTicks() {
        return animTicks;
    }

    /**
     * Returns true when a finished one-shot animation should hold its last
     * frame (e.g. the player is still blocking).
     */
    public boolean shouldHoldLastFrame() {
        return isBlocking;
    }

    /**
     * Called by the renderer when a one-shot animation has finished and is
     * NOT being held. Clears animation state so the next call to
     * getCurrentAnimation() returns null.
     */
    public void onAnimationFinished() {
        currentAnimation = null;
        currentPose      = GotArmPose.NONE;
        animTicks        = 0F;
    }
}