package net.got.mixin;

import net.got.client.animation.GotPlayerAnimator;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Injects GOT animation layers into vanilla PlayerModel.setupAnim().
 *
 * Two passes, applied after vanilla:
 *   1. Base locomotion (idle/walk/run/fall from GotPlayerBaseAnimations).
 *   2. Combat animation (attack combo, block) — overrides base on affected bones.
 */
@Mixin(PlayerModel.class)
public abstract class PlayerRendererMixin {

    private static final Vector3f GOT_ANIM_VEC = new Vector3f();

    @Inject(method = "setupAnim", at = @At("TAIL"), remap = false)
    private void got_applyAnimation(PlayerRenderState state, CallbackInfo ci) {
        GotPlayerAnimator animator = GotPlayerAnimator.INSTANCE;

        @SuppressWarnings("unchecked")
        PlayerModel model = (PlayerModel)(Object) this;

        // ── Layer 1: base locomotion ──────────────────────────────────────────
        var baseAnim = animator.getBaseAnimation();
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
        var combatAnim = animator.getCurrentAnimation();
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
