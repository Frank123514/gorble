package net.got.entity.client.mammoth;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.mammoth.GotMammothEntity;
import net.got.entity.giant.GotGiantEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GotMammothRenderer
        extends MobRenderer<GotMammothEntity, GotMammothRenderState, GotMammothModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/animals/got_mammoth.png");

    // ── One-shot timer — stored on the renderer, not the render state ─────────
    private AnimationDefinition lastAnimation = null;
    private float animationStartTick = 0F;

    // Clip length in ticks — must match Builder.withLength() in GotMammothAnimations.
    private static final float ATTACK_LENGTH_TICKS = 1.0F * 20F;

    public GotMammothRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GotMammothModel(ctx.bakeLayer(GotModelLayers.GOT_MAMMOTH)),
                1.4F);
    }

    @Override
    public GotMammothRenderState createRenderState() {
        return new GotMammothRenderState();
    }

    @Override
    public void extractRenderState(GotMammothEntity entity,
                                   GotMammothRenderState state,
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
    public void render(GotMammothRenderState state,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {
        selectAndApplyAnimation(state);
        super.render(state, poseStack, bufferSource, packedLight);
    }

    private void selectAndApplyAnimation(GotMammothRenderState state) {
        AnimationDefinition anim = chooseAnimation(state);

        float localTime = state.ageInTicks - animationStartTick;
        boolean clipFinished = anim == GotMammothAnimations.ATTACK
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

    private static AnimationDefinition chooseAnimation(GotMammothRenderState state) {
        if (state.isDeadOrDying) {
            return GotMammothAnimations.DEATH;
        } else if (state.isAttacking) {
            return GotMammothAnimations.ATTACK;
        } else if (state.isSprinting || (state.isAngry && state.isMoving)) {
            return GotMammothAnimations.RUN;
        } else if (state.isMoving || state.isInWater) {
            return GotMammothAnimations.WALK;
        } else {
            return GotMammothAnimations.IDLE;
        }
    }

    private static boolean isOneShot(AnimationDefinition anim) {
        return anim == GotMammothAnimations.ATTACK
                || anim == GotMammothAnimations.DEATH;
    }

    @Override
    public ResourceLocation getTextureLocation(GotMammothRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(GotMammothRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.65F, 0.65F, 0.65F);
        } else {
            // Larger render scale — matches the expanded 4.5×5.0 hitbox
            poseStack.scale(2.2F, 2.2F, 2.2F);
        }
        super.scale(state, poseStack);
    }
}