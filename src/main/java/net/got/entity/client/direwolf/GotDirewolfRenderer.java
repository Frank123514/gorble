package net.got.entity.client.direwolf;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.direwolf.GotDirewolfEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for {@link GotDirewolfEntity}.
 *
 * <h3>One-shot fix:</h3>
 * ATTACK and DEATH are non-looping.  The global {@code ageInTicks} clock keeps
 * advancing, so seeking into those animations with the raw age would skip past
 * the end and show nothing.  We track a per-renderer start tick and subtract it
 * so every one-shot always begins at local time 0.
 */
public class GotDirewolfRenderer
        extends MobRenderer<GotDirewolfEntity, GotDirewolfRenderState, GotDirewolfModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/animals/got_direwolf.png");

    public GotDirewolfRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GotDirewolfModel(ctx.bakeLayer(GotModelLayers.GOT_DIREWOLF)),
                0.8F);
    }

    @Override
    public GotDirewolfRenderState createRenderState() {
        return new GotDirewolfRenderState();
    }

    @Override
    public void extractRenderState(GotDirewolfEntity entity,
                                   GotDirewolfRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isInWater   = entity.isInWater();
        state.isSprinting = entity.isSprinting();
        state.isMoving    = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.isAttacking = entity.isAttacking();
        state.isHowling   = entity.isHowling();
        state.isSitting   = entity.isInSittingPose();  // Use visual pose, not ordered state
    }

    @Override
    public void render(GotDirewolfRenderState state,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {
        selectAndApplyAnimation(state);
        super.render(state, poseStack, bufferSource, packedLight);
    }

    // Clip length in ticks for each one-shot — matches Builder.withLength() values.
    private static final float ATTACK_LENGTH_TICKS = 1.4815F * 20F;

    private void selectAndApplyAnimation(GotDirewolfRenderState state) {
        AnimationDefinition anim = chooseAnimation(state);

        // Reset the local timer whenever:
        //   (a) we switch to a different animation, OR
        //   (b) the ATTACK clip has finished — lets it retrigger on every bite.
        //       DEATH is intentionally excluded: it should hold its final pose.
        float localTime = state.ageInTicks - state.animationStartTick;
        boolean clipFinished = anim == GotDirewolfAnimations.ATTACK
                && localTime >= ATTACK_LENGTH_TICKS;

        if (anim != state.lastAnimation || clipFinished) {
            state.animationStartTick = state.ageInTicks;
            state.lastAnimation = anim;
            localTime = 0F;
        }

        if (!isOneShot(anim)) {
            localTime = state.ageInTicks; // loops use raw global clock
        }

        model.applyAnimation(anim, localTime, 1.0F);
    }

    /** Select the highest-priority animation for the current frame. */
    private static AnimationDefinition chooseAnimation(GotDirewolfRenderState state) {
        if (state.isAttacking) {
            return GotDirewolfAnimations.ATTACK;
        } else if (state.isSprinting || state.isInWater) {
            return GotDirewolfAnimations.RUN;
        } else if (state.isMoving) {
            return GotDirewolfAnimations.WALK;
        } else if (state.isSitting) {
            return GotDirewolfAnimations.SIT;
        } else if (state.isHowling) {
            return GotDirewolfAnimations.HOWL;
        } else {
            return GotDirewolfAnimations.IDLE;
        }
    }

    /** Returns true for animations that must not loop (no {@code .looping()} call). */
    private static boolean isOneShot(AnimationDefinition anim) {
        return anim == GotDirewolfAnimations.ATTACK
                || anim == GotDirewolfAnimations.DEATH;
    }

    /**
     * Returns true for one-shots that should retrigger when the clip ends.
     * ATTACK repeats for every bite; DEATH holds its final pose so it is excluded.
     */
    private static boolean isRepeatingOneShot(AnimationDefinition anim) {
        return anim == GotDirewolfAnimations.ATTACK;
    }

    @Override
    public ResourceLocation getTextureLocation(GotDirewolfRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(GotDirewolfRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }
        super.scale(state, poseStack);
    }
}