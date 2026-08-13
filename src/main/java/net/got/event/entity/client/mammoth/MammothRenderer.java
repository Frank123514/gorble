package net.got.event.entity.client.mammoth;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.client.model.ModelLayers;
import net.got.event.entity.mammoth.MammothEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

public class MammothRenderer
        extends MobRenderer<MammothEntity, MammothRenderState, MammothModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_mammoth.png");

    private AnimationDefinition lastAnimation = null;
    private float animationStartTick = 0F;

    private static final float ATTACK_LENGTH_TICKS = 1.0F * 20F;

    public MammothRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new MammothModel(ctx.bakeLayer(ModelLayers.GOT_MAMMOTH)),
                1.4F);
    }

    @Override
    public MammothRenderState createRenderState() {
        return new MammothRenderState();
    }

    @Override
    public void extractRenderState(MammothEntity entity,
                                   MammothRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isInWater     = entity.isInWater();
        state.isSprinting   = entity.isSprinting();
        state.isMoving      = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.isAngry       = entity.isAngry();
        state.isDeadOrDying = entity.isDeadOrDying();
        state.isAttacking   = entity.isAttacking();
        state.hasGiantRider = entity.hasGiantRider();
    }

    @Override
    public void submit(MammothRenderState state,
                       PoseStack poseStack,
                       SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        selectAndApplyAnimation(state);
        super.submit(state, poseStack, collector, cameraState);
    }

    private void selectAndApplyAnimation(MammothRenderState state) {
        AnimationDefinition anim = chooseAnimation(state);

        float localTime = state.ageInTicks - animationStartTick;
        boolean clipFinished = anim == MammothAnimations.ATTACK
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

    private static AnimationDefinition chooseAnimation(MammothRenderState state) {
        if (state.isDeadOrDying) {
            return MammothAnimations.DEATH;
        } else if (state.isAttacking) {
            return MammothAnimations.ATTACK;
        } else if (state.isSprinting || (state.isAngry && state.isMoving)) {
            return MammothAnimations.RUN;
        } else if (state.isMoving || state.isInWater) {
            return MammothAnimations.WALK;
        } else {
            return MammothAnimations.IDLE;
        }
    }

    private static boolean isOneShot(AnimationDefinition anim) {
        return anim == MammothAnimations.ATTACK
                || anim == MammothAnimations.DEATH;
    }

    @Override
    public Identifier getTextureLocation(MammothRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(MammothRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.65F, 0.65F, 0.65F);
        } else {
            
            poseStack.scale(2.2F, 2.2F, 2.2F);
        }
        super.scale(state, poseStack);
    }
}