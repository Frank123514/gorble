package net.got.client.animation;

import net.minecraft.client.animation.AnimationDefinition;

/**
 * Singleton that owns all player animation state on the client.
 *
 * <p>Combo logic: pressing attack during the recovery window of SWORD_ATTACK
 * (after the strike lands, ~t > 0.4 s) chains into SWORD_ATTACK_2 instead
 * of restarting from the beginning.
 *
 * <p>Base locomotion animations (idle, walk, run from GotPlayerBaseAnimations)
 * are played as a low-priority layer; combat animations override them.
 */
public final class GotPlayerAnimator {

    public static final GotPlayerAnimator INSTANCE = new GotPlayerAnimator();
    private GotPlayerAnimator() {}

    private static final org.slf4j.Logger LOGGER =
            org.slf4j.LoggerFactory.getLogger("GotPlayerAnimator");

    // ── Combat state ──────────────────────────────────────────────────────────
    private AnimationDefinition currentAnimation = null;
    private GotArmPose currentPose = GotArmPose.NONE;
    private boolean isBlocking = false;
    private float animTicks = 0F;

    /** True when the player queued a follow-up attack during the recovery window. */
    private boolean comboQueued = false;

    // ── Base locomotion state (set externally by GotCombatAnimationHandler) ──
    private AnimationDefinition baseAnimation = null;
    private float baseTicks = 0F;

    // ── Combo thresholds (in ticks, at 20 tps) ───────────────────────────────
    /** Earliest tick in SWORD_ATTACK where a combo input is accepted (~t=0.40 s). */
    private static final float COMBO_WINDOW_OPEN  = 0.40F * 20F;
    /** Latest tick (full animation length) up to which combo is accepted. */
    private static final float SWORD_ATTACK_TICKS = 0.9F * 20F;

    // ── Internal helpers ──────────────────────────────────────────────────────

    private AnimationDefinition animationFor(GotArmPose pose) {
        return switch (pose) {
            case SWORD        -> GotPlayerCombatAnimations.SWORD_ATTACK;
            case SWORD_COMBO_2 -> GotPlayerCombatAnimations.SWORD_ATTACK_2;
            case GREATSWORD   -> GotPlayerCombatAnimations.GREATSWORD_ATTACK;
            case AXE          -> GotPlayerCombatAnimations.AXE_ATTACK;
            case SPEAR        -> GotPlayerCombatAnimations.SPEAR_ATTACK;
            case BLOCK        -> GotPlayerCombatAnimations.SWORD_BLOCK;
            default           -> null;
        };
    }

    // ── Called by GotCombatAnimationHandler ───────────────────────────────────

    public void triggerAttack(GotArmPose pose) {
        if (pose == GotArmPose.NONE || pose == GotArmPose.BLOCK) return;

        // ── Combo check: sword → sword_combo_2 ────────────────────────────────
        if (pose == GotArmPose.SWORD
                && currentPose == GotArmPose.SWORD
                && animTicks >= COMBO_WINDOW_OPEN
                && animTicks < SWORD_ATTACK_TICKS) {
            // Queue combo — it fires at the end of attack 1 in tick()
            comboQueued = true;
            LOGGER.debug("[GOT-ANIM] Combo queued at t={}ticks", animTicks);
            return;
        }

        LOGGER.info("[GOT-ANIM] triggerAttack pose={}", pose);
        comboQueued      = false;
        currentPose      = pose;
        currentAnimation = animationFor(pose);
        animTicks        = 0F;
    }

    public void setBlocking(boolean blocking) {
        this.isBlocking = blocking;
        if (blocking && currentPose != GotArmPose.BLOCK) {
            comboQueued      = false;
            currentPose      = GotArmPose.BLOCK;
            currentAnimation = GotPlayerCombatAnimations.SWORD_BLOCK;
            animTicks        = 0F;
        } else if (!blocking && currentPose == GotArmPose.BLOCK) {
            currentPose      = GotArmPose.NONE;
            currentAnimation = null;
            animTicks        = 0F;
        }
    }

    /** Sets the base locomotion animation (idle, walk, etc.). Pass null to clear. */
    public void setBaseAnimation(AnimationDefinition anim) {
        if (anim != baseAnimation) {
            baseAnimation = anim;
            baseTicks     = 0F;
        }
    }

    /** Called every client tick by GotCombatAnimationHandler. */
    public void tick() {
        // Tick base locomotion counter
        if (baseAnimation != null) {
            baseTicks += 1F;
        }

        if (currentAnimation == null) return;
        animTicks += 1F;

        float maxTicks = currentAnimation.lengthInSeconds() * 20F;

        if (!currentAnimation.looping() && animTicks >= maxTicks) {
            if (isBlocking) {
                animTicks = maxTicks; // hold last frame
            } else if (comboQueued && currentPose == GotArmPose.SWORD) {
                // Fire combo attack
                comboQueued      = false;
                currentPose      = GotArmPose.SWORD_COMBO_2;
                currentAnimation = GotPlayerCombatAnimations.SWORD_ATTACK_2;
                animTicks        = 0F;
                LOGGER.info("[GOT-ANIM] Combo fired: SWORD_COMBO_2");
            } else {
                currentAnimation = null;
                currentPose      = GotArmPose.NONE;
                animTicks        = 0F;
            }
        }
    }

    // ── Called by GotPlayerRenderer ───────────────────────────────────────────

    /** The active combat animation, or null if none. */
    public AnimationDefinition getCurrentAnimation() {
        return currentAnimation;
    }

    public float getCurrentAnimationTicks() {
        return animTicks;
    }

    /** The base locomotion animation (idle/walk/run), or null if none. */
    public AnimationDefinition getBaseAnimation() {
        return baseAnimation;
    }

    public float getBaseAnimationTicks() {
        return baseTicks;
    }

    public boolean shouldHoldLastFrame() {
        return isBlocking;
    }

    public void onAnimationFinished() {
        currentAnimation = null;
        currentPose      = GotArmPose.NONE;
        animTicks        = 0F;
    }
}
