package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.client.animation.GotArmPose;
import net.got.client.animation.GotPlayerAnimator;
import net.got.client.animation.GotPlayerCombatAnimations;
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
 * This follows the exact same pattern as GotDirewolfRenderer:
 *   - The renderer owns the model
 *   - render() sets animation state on the model, then calls super.render()
 *   - super.render() calls setupAnim() on the model, which applies vanilla
 *     poses first, then our combat animation on top via KeyframeAnimations
 *
 * Registered in ClientSetup via EntityRenderersEvent.RegisterRenderers for
 * both PLAYER and PLAYER_SLIM (slim-arm variant).
 *
 * No mixin needed. No bone adapter. No fighting the engine.
 */
public class GotPlayerRenderer extends PlayerRenderer {

    // We track animation state here on the renderer (survives across frames),
    // not in the render state (which is rebuilt every frame).
    private AnimationDefinition currentAnimation = null;
    private float animStartTick = 0F;
    private boolean animPlaying = false;

    public GotPlayerRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, slim);
        // Replace the vanilla PlayerModel with our subclass that supports animations.
        // We have to bake the layer ourselves since super already did it with the
        // wrong model class — we just replace the field via the accessor below.
        EntityModelSet models = context.getModelSet();
        GotPlayerModel ourModel = new GotPlayerModel(
                models.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER),
                slim
        );
        // PlayerRenderer stores the model in the `model` field inherited from
        // EntityRenderer. We replace it with our subclass.
        this.model = ourModel;
    }

    @Override
    public void render(PlayerRenderState renderState,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {

        // Sync the global animator state into our per-renderer fields
        syncAnimatorState(renderState.ageInTicks);

        // Tell the model what animation to play this frame
        GotPlayerModel gotModel = (GotPlayerModel) this.model;
        gotModel.activeAnimation   = currentAnimation;
        gotModel.animationTimeTicks = currentAnimationLocalTime(renderState.ageInTicks);

        // Now let vanilla render (which calls setupAnim, which applies our anim)
        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    private void syncAnimatorState(float ageInTicks) {
        GotPlayerAnimator anim = GotPlayerAnimator.INSTANCE;
        AnimationDefinition desired = anim.getCurrentAnimation();

        if (desired != currentAnimation) {
            // Animation changed — reset local timer
            currentAnimation = desired;
            animStartTick    = ageInTicks;
            animPlaying      = desired != null;
        }

        if (currentAnimation != null) {
            float localTicks = ageInTicks - animStartTick;
            float lengthTicks = currentAnimation.lengthInSeconds() * 20F;

            if (!currentAnimation.looping() && localTicks >= lengthTicks) {
                // One-shot finished
                if (anim.shouldHoldLastFrame()) {
                    // Block pose — hold final frame
                    animStartTick = ageInTicks - lengthTicks;
                } else {
                    currentAnimation = null;
                    anim.onAnimationFinished();
                    animPlaying = false;
                }
            }
        }
    }

    private float currentAnimationLocalTime(float ageInTicks) {
        if (currentAnimation == null) return 0F;
        float localTicks = ageInTicks - animStartTick;
        float lengthTicks = currentAnimation.lengthInSeconds() * 20F;
        return Math.min(localTicks, lengthTicks);
    }
}