package net.got.event.entity.client.direwolf;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.client.model.GotModelLayers;
import net.got.event.entity.direwolf.GotDirewolfEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class GotDirewolfRenderer
        extends MobRenderer<GotDirewolfEntity, GotDirewolfRenderState, GotDirewolfModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_direwolf.png");

    // ── One-shot timer — stored on the renderer, not the render state ─────────
    // RenderState is wiped each frame; these fields must survive across frames.
    private AnimationDefinition lastAnimation = null;
    private float animationStartTick = 0F;

    // Clip length in ticks — must match Builder.withLength() in GotDirewolfAnimations.
    private static final float ATTACK_LENGTH_TICKS = 1.4815F * 20F;

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
        state.isSitting   = entity.isInSittingPose();
    }

    @Override
    public void render(GotDirewolfRenderState state,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {
        selectAndApplyAnimation(state);
        super.render(state, poseStack, bufferSource, packedLight);
    }

    private void selectAndApplyAnimation(GotDirewolfRenderState state) {
        AnimationDefinition anim = chooseAnimation(state);

        float localTime = state.ageInTicks - animationStartTick;
        boolean clipFinished = anim == GotDirewolfAnimations.ATTACK
                && localTime >= ATTACK_LENGTH_TICKS;

        if (anim != lastAnimation || clipFinished) {
            animationStartTick = state.ageInTicks;
            lastAnimation = anim;
            localTime = 0F;
        }

        if (!isOneShot(anim)) {
            localTime = state.ageInTicks; // looping anims use the raw global clock
        }

        model.applyAnimation(anim, localTime, 1.0F);
    }

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

    private static boolean isOneShot(AnimationDefinition anim) {
        return anim == GotDirewolfAnimations.ATTACK
                || anim == GotDirewolfAnimations.DEATH;
    }

    @Override
    public Identifier getTextureLocation(GotDirewolfRenderState state) {
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
