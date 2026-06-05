package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.client.animation.GotPlayerAnimator;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;

/**
 * Replaces the vanilla PlayerRenderer for GOT players.
 *
 * <p>Two animation layers are maintained per-renderer:
 * <ul>
 *   <li>Base locomotion (idle/walk/run/fall) from GotPlayerBaseAnimations.</li>
 *   <li>Combat animation (attack combo, block) from GotPlayerCombatAnimations.</li>
 * </ul>
 *
 * <p>Registered in ClientSetup via EntityRenderersEvent.RegisterRenderers for
 * both PLAYER and PLAYER_SLIM (slim-arm variant).
 */
public class GotPlayerRenderer extends PlayerRenderer {

    // ── Per-renderer combat animation state ───────────────────────────────────
    private AnimationDefinition currentAnimation = null;
    private float animStartTick  = 0F;
    private boolean animPlaying  = false;

    // ── Per-renderer base locomotion animation state ───────────────────────────
    private AnimationDefinition currentBaseAnimation = null;
    private float baseStartTick  = 0F;

    public GotPlayerRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, slim);
        EntityModelSet models = context.getModelSet();
        GotPlayerModel ourModel = new GotPlayerModel(
                models.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER),
                slim
        );
        this.model = ourModel;
    }

    @Override
    public void render(PlayerRenderState renderState,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {

        syncAnimatorState(renderState.ageInTicks);

        GotPlayerModel gotModel = (GotPlayerModel) this.model;

        // Push combat animation
        gotModel.activeAnimation     = currentAnimation;
        gotModel.animationTimeTicks  = currentAnimationLocalTime(renderState.ageInTicks);

        // Push base locomotion animation
        gotModel.activeBaseAnimation     = currentBaseAnimation;
        gotModel.baseAnimationTimeTicks  = baseAnimationLocalTime(renderState.ageInTicks);

        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    private void syncAnimatorState(float ageInTicks) {
        GotPlayerAnimator anim = GotPlayerAnimator.INSTANCE;

        // ── Combat ─────────────────────────────────────────────────────────────
        AnimationDefinition desiredCombat = anim.getCurrentAnimation();
        if (desiredCombat != currentAnimation) {
            currentAnimation = desiredCombat;
            animStartTick    = ageInTicks;
            animPlaying      = desiredCombat != null;
        }
        if (currentAnimation != null) {
            float localTicks  = ageInTicks - animStartTick;
            float lengthTicks = currentAnimation.lengthInSeconds() * 20F;
            if (!currentAnimation.looping() && localTicks >= lengthTicks) {
                if (anim.shouldHoldLastFrame()) {
                    animStartTick = ageInTicks - lengthTicks;
                } else {
                    currentAnimation = null;
                    anim.onAnimationFinished();
                    animPlaying = false;
                }
            }
        }

        // ── Base locomotion ────────────────────────────────────────────────────
        AnimationDefinition desiredBase = anim.getBaseAnimation();
        if (desiredBase != currentBaseAnimation) {
            currentBaseAnimation = desiredBase;
            baseStartTick        = ageInTicks;
        }
    }

    private float currentAnimationLocalTime(float ageInTicks) {
        if (currentAnimation == null) return 0F;
        float localTicks  = ageInTicks - animStartTick;
        float lengthTicks = currentAnimation.lengthInSeconds() * 20F;
        return Math.min(localTicks, lengthTicks);
    }

    private float baseAnimationLocalTime(float ageInTicks) {
        if (currentBaseAnimation == null) return 0F;
        return ageInTicks - baseStartTick;
    }
}
