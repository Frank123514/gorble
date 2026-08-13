package net.got.event.entity.client.brownbear;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.brownbear.BrownBearEntity;
import net.got.event.entity.client.model.ModelLayers;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

public class BrownBearRenderer
        extends MobRenderer<BrownBearEntity, BrownBearRenderState, BrownBearModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_brown_bear.png");

    private static final float ATTACK_LENGTH_TICKS = 1.2F * 20F;
    private static final float STAND_LENGTH_TICKS  = 1.6667F * 20F;

    private AnimationDefinition lastAnimation = null;
    private float animationStartTick = 0F;

    public BrownBearRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new BrownBearModel(ctx.bakeLayer(ModelLayers.GOT_BROWN_BEAR)),
                0.8F);
    }

    @Override
    public BrownBearRenderState createRenderState() {
        return new BrownBearRenderState();
    }

    @Override
    public void extractRenderState(BrownBearEntity entity,
                                   BrownBearRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isInWater     = entity.isInWater();
        state.isSprinting   = entity.isSprinting();
        state.isMoving      = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.isAngry       = entity.isAngry();
        state.isDeadOrDying = entity.isDeadOrDying();
        state.isAttacking   = entity.isAttacking();
        state.isStanding    = entity.isStanding();
    }

    @Override
    public void submit(BrownBearRenderState state,
                       PoseStack poseStack,
                       SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        selectAndApplyAnimation(state);
        super.submit(state, poseStack, collector, cameraState);
    }

    private void selectAndApplyAnimation(BrownBearRenderState state) {
        AnimationDefinition anim = chooseAnimation(state);

        float localTime  = state.ageInTicks - animationStartTick;
        float clipLength  = clipLengthFor(anim);
        boolean clipDone  = isOneShot(anim) && localTime >= clipLength;

        if (anim != lastAnimation || clipDone) {
            animationStartTick = state.ageInTicks;
            lastAnimation      = anim;
            localTime          = 0F;
        }

        if (!isOneShot(anim)) {
            localTime = state.ageInTicks;
        }

        model.applyAnimation(anim, localTime, 1.0F);
    }

    private static AnimationDefinition chooseAnimation(BrownBearRenderState state) {
        if (state.isDeadOrDying) {
            return BrownBearAnimations.IDLE;
        } else if (state.isAttacking) {
            return BrownBearAnimations.ATTACK;
        } else if (state.isStanding) {
            return BrownBearAnimations.STAND;
        } else if (state.isSprinting || (state.isAngry && state.isMoving)) {
            return BrownBearAnimations.RUN;
        } else if (state.isMoving || state.isInWater) {
            return BrownBearAnimations.WALK;
        } else {
            return BrownBearAnimations.IDLE;
        }
    }

    private static boolean isOneShot(AnimationDefinition anim) {
        return anim == BrownBearAnimations.ATTACK
                || anim == BrownBearAnimations.STAND;
    }

    private static float clipLengthFor(AnimationDefinition anim) {
        if (anim == BrownBearAnimations.ATTACK) return ATTACK_LENGTH_TICKS;
        if (anim == BrownBearAnimations.STAND)  return STAND_LENGTH_TICKS;
        return 0F;
    }

    @Override
    public Identifier getTextureLocation(BrownBearRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(BrownBearRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1.6F, 1.6F, 1.6F);
        }
        super.scale(state, poseStack);
    }
}