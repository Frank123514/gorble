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

    // ── Called by PlayerRendererMixin ─────────────────────────────────────────

    public AnimationDefinition getCurrentAnimation() {
        return currentAnimation;
    }

    public float getCurrentAnimationTicks() {
        return animTicks;
    }
}