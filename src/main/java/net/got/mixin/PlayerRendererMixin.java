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
 * Injects GOT's animations into vanilla PlayerModel.setupAnim().
 *
 * <p>This is the sole animation application point. GotPlayerRenderer /
 * GotPlayerModel must NOT also call KeyframeAnimations.animate() — doing so
 * causes every bone to be transformed twice per frame, producing garbage poses.
 *
 * <p>Time source: renderState.ageInTicks is partial-tick interpolated by the
 * render engine (e.g. 14.73 at 60 fps between ticks 14 and 15). We pass this
 * into GotPlayerAnimator.notifyRenderFrame() which records the ageInTicks at
 * the moment each animation starts, then getSmoothCombatTicks() /
 * getSmoothBaseTicks() return (currentAge - startAge) — a float local time
 * that advances smoothly every frame. Multiplying by 50 converts ticks→ms for
 * KeyframeAnimations.animate(). This is why the animations look smooth in
 * Blockbench and must look equally smooth in-game.
 *
 * <p>Reset strategy: every bone except the head is reset to rest pose before
 * either GOT layer runs, because KeyframeAnimations.animate() is additive.
 * Without this, GOT's offsets stack on top of vanilla's arm-swing/crouch pose.
 * The head is excluded so vanilla's look-direction (mouse look) survives;
 * GOT head keyframes add on top of it intentionally.
 */
@Mixin(PlayerModel.class)
public abstract class PlayerRendererMixin {

    private static final Vector3f GOT_ANIM_VEC = new Vector3f();

    @Inject(method = "setupAnim", at = @At("TAIL"), remap = false)
    private void got_applyAnimation(PlayerRenderState state, CallbackInfo ci) {
        GotPlayerAnimator animator = GotPlayerAnimator.INSTANCE;

        // Tell the animator what render-time ageInTicks we're at this frame.
        // It uses this to detect animation transitions and record smooth start times.
        animator.notifyRenderFrame(state.ageInTicks);

        AnimationDefinition baseAnim   = animator.getBaseAnimation();
        AnimationDefinition combatAnim = animator.getCurrentAnimation();

        if (baseAnim == null && combatAnim == null) {
            return;
        }

        @SuppressWarnings("unchecked")
        PlayerModel model = (PlayerModel)(Object) this;

        // ── Reset bones GOT fully owns back to rest pose ───────────────────────
        // (head intentionally excluded — see class javadoc)
        model.body.resetPose();
        model.rightArm.resetPose();
        model.leftArm.resetPose();
        model.rightLeg.resetPose();
        model.leftLeg.resetPose();

        // ── Layer 1: base locomotion ──────────────────────────────────────────
        if (baseAnim != null) {
            float localTicks = animator.getSmoothBaseTicks(state.ageInTicks);
            KeyframeAnimations.animate(
                    model,
                    baseAnim,
                    (long)(localTicks * 50F),
                    1.0F,
                    GOT_ANIM_VEC
            );
        }

        // ── Layer 2: combat (overrides base for affected bones) ───────────────
        if (combatAnim != null) {
            float localTicks = animator.getSmoothCombatTicks(state.ageInTicks);
            KeyframeAnimations.animate(
                    model,
                    combatAnim,
                    (long)(localTicks * 50F),
                    1.0F,
                    GOT_ANIM_VEC
            );
        }
    }
}