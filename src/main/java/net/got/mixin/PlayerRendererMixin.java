package net.got.mixin;

import net.got.client.animation.GotPlayerAnimator;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Injects GOT's animations into vanilla PlayerModel.setupAnim(), making them
 * REPLACE vanilla's pose rather than play on top of it.
 *
 * <p>This is the only mechanism that actually reaches the live PlayerModel
 * instance NeoForge renders every player with. There is no supported NeoForge
 * API to swap out the player's EntityRenderer/EntityModel wholesale —
 * EntityRenderersEvent.AddLayers only allows adding extra render layers on top
 * of the existing player renderer, it can't replace the base model. So this
 * mixin is load-bearing, not redundant.
 *
 * <p>Two layers, both applied after vanilla's own setupAnim has run:
 * <ol>
 *   <li>Base locomotion (idle/walk/run/fall from GotPlayerBaseAnimations).</li>
 *   <li>Combat animation (attack combo, block) — overrides base on affected bones.</li>
 * </ol>
 *
 * <p>Every bone except the head is reset to rest pose before either GOT layer
 * runs, because KeyframeAnimations.animate() is additive onto whatever pose
 * the bone already has — without the reset, GOT's animation stacks on top of
 * vanilla's arm-swing/crouch pose instead of replacing it. The head is left
 * un-reset on purpose: vanilla sets head rotation from the player's actual
 * look direction (mouse look) in this same setupAnim call, and that has to
 * survive. GOT's head keyframes (small idle sway/tilt) add on top of look
 * direction instead of overriding it.
 *
 * <p>The combat layer is applied after base WITHOUT a second reset, so combat
 * overrides base only on the bones it actually keys (arms/torso during a
 * swing) while base's leg motion from walking/running keeps playing underneath.
 */
@Mixin(PlayerModel.class)
public abstract class PlayerRendererMixin {

    private static final Vector3f GOT_ANIM_VEC = new Vector3f();

    @Inject(method = "setupAnim", at = @At("TAIL"), remap = false)
    private void got_applyAnimation(PlayerRenderState state, CallbackInfo ci) {
        GotPlayerAnimator animator = GotPlayerAnimator.INSTANCE;

        @SuppressWarnings("unchecked")
        PlayerModel model = (PlayerModel)(Object) this;

        AnimationDefinition baseAnim   = animator.getBaseAnimation();
        AnimationDefinition combatAnim = animator.getCurrentAnimation();

        // Nothing for GOT to do this frame — leave vanilla's pose as-is.
        if (baseAnim == null && combatAnim == null) {
            return;
        }

        // ── Reset bones GOT fully owns back to rest pose ───────────────────────
        // (head intentionally excluded — see class javadoc)
        model.body.resetPose();
        model.rightArm.resetPose();
        model.leftArm.resetPose();
        model.rightLeg.resetPose();
        model.leftLeg.resetPose();

        // ── Layer 1: base locomotion ──────────────────────────────────────────
        if (baseAnim != null) {
            KeyframeAnimations.animate(
                    model,
                    baseAnim,
                    (long)(animator.getBaseAnimationTicks() * 50F),
                    1.0F,
                    GOT_ANIM_VEC
            );
        }

        // ── Layer 2: combat (overrides base for affected bones) ───────────────
        if (combatAnim != null) {
            float ticks = animator.getCurrentAnimationTicks();
            KeyframeAnimations.animate(
                    model,
                    combatAnim,
                    (long)(ticks * 50F),
                    1.0F,
                    GOT_ANIM_VEC
            );
        }
    }
}
