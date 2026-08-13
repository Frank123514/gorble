package net.got.event.entity.client.direwolf;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.client.model.ModelLayers;
import net.got.event.entity.direwolf.DirewolfEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

public class DirewolfRenderer
        extends MobRenderer<DirewolfEntity, DirewolfRenderState, DirewolfModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_direwolf.png");

    private AnimationDefinition lastAnimation = null;
    private float animationStartTick = 0F;

    private static final float ATTACK_LENGTH_TICKS = 1.4815F * 20F;

    public DirewolfRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new DirewolfModel(ctx.bakeLayer(ModelLayers.GOT_DIREWOLF)),
                0.8F);
    }

    @Override
    public DirewolfRenderState createRenderState() {
        return new DirewolfRenderState();
    }

    @Override
    public void extractRenderState(DirewolfEntity entity,
                                   DirewolfRenderState state,
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
    public void submit(DirewolfRenderState state,
                       PoseStack poseStack,
                       SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        selectAndApplyAnimation(state);
        super.submit(state, poseStack, collector, cameraState);
    }

    private void selectAndApplyAnimation(DirewolfRenderState state) {
        AnimationDefinition anim = chooseAnimation(state);

        float localTime = state.ageInTicks - animationStartTick;
        boolean clipFinished = anim == DirewolfAnimations.ATTACK
                && localTime >= ATTACK_LENGTH_TICKS;

        if (anim != lastAnimation || clipFinished) {
            animationStartTick = state.ageInTicks;
            lastAnimation = anim;
            localTime = 0F;
        }

        if (!isOneShot(anim)) {
            localTime = state.ageInTicks;
        }

        model.applyAnimation(anim, localTime, 1.0F);
    }

    private static AnimationDefinition chooseAnimation(DirewolfRenderState state) {
        if (state.isAttacking) {
            return DirewolfAnimations.ATTACK;
        } else if (state.isSprinting || state.isInWater) {
            return DirewolfAnimations.RUN;
        } else if (state.isMoving) {
            return DirewolfAnimations.WALK;
        } else if (state.isSitting) {
            return DirewolfAnimations.SIT;
        } else if (state.isHowling) {
            return DirewolfAnimations.HOWL;
        } else {
            return DirewolfAnimations.IDLE;
        }
    }

    private static boolean isOneShot(AnimationDefinition anim) {
        return anim == DirewolfAnimations.ATTACK
                || anim == DirewolfAnimations.DEATH;
    }

    @Override
    public Identifier getTextureLocation(DirewolfRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(DirewolfRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }
        super.scale(state, poseStack);
    }
}