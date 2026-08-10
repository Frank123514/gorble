package net.got.event.entity.client.brownbear;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.brownbear.GotBrownBearEntity;
import net.got.event.entity.client.model.GotModelLayers;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class GotBrownBearRenderer
        extends MobRenderer<GotBrownBearEntity, GotBrownBearRenderState, GotBrownBearModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_brown_bear.png");

    private static final float ATTACK_LENGTH_TICKS = 1.2F * 20F;
    private static final float STAND_LENGTH_TICKS  = 1.6667F * 20F;

    private AnimationDefinition lastAnimation = null;
    private float animationStartTick = 0F;

    public GotBrownBearRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GotBrownBearModel(ctx.bakeLayer(GotModelLayers.GOT_BROWN_BEAR)),
                0.8F);
    }

    @Override
    public GotBrownBearRenderState createRenderState() {
        return new GotBrownBearRenderState();
    }

    @Override
    public void extractRenderState(GotBrownBearEntity entity,
                                   GotBrownBearRenderState state,
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
    public void render(GotBrownBearRenderState state,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {
        selectAndApplyAnimation(state);
        super.render(state, poseStack, bufferSource, packedLight);
    }

    private void selectAndApplyAnimation(GotBrownBearRenderState state) {
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

    private static AnimationDefinition chooseAnimation(GotBrownBearRenderState state) {
        if (state.isDeadOrDying) {
            return GotBrownBearAnimations.IDLE;
        } else if (state.isAttacking) {
            return GotBrownBearAnimations.ATTACK;
        } else if (state.isStanding) {
            return GotBrownBearAnimations.STAND;
        } else if (state.isSprinting || (state.isAngry && state.isMoving)) {
            return GotBrownBearAnimations.RUN;
        } else if (state.isMoving || state.isInWater) {
            return GotBrownBearAnimations.WALK;
        } else {
            return GotBrownBearAnimations.IDLE;
        }
    }

    private static boolean isOneShot(AnimationDefinition anim) {
        return anim == GotBrownBearAnimations.ATTACK
                || anim == GotBrownBearAnimations.STAND;
    }

    private static float clipLengthFor(AnimationDefinition anim) {
        if (anim == GotBrownBearAnimations.ATTACK) return ATTACK_LENGTH_TICKS;
        if (anim == GotBrownBearAnimations.STAND)  return STAND_LENGTH_TICKS;
        return 0F;
    }

    @Override
    public Identifier getTextureLocation(GotBrownBearRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(GotBrownBearRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1.6F, 1.6F, 1.6F);
        }
        super.scale(state, poseStack);
    }
}