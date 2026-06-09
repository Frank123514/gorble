package net.got.entity.client.giant;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.giant.GotGiantEntity;
import net.got.entity.client.model.GotModelLayers;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GotGiantRenderer
        extends MobRenderer<GotGiantEntity, GotGiantRenderState, GotGiantModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/giant/got_giant.png");

    // One-shot clip lengths in ticks (must match GotGiantAnimations durations × 20)
    private static final float ATTACK_LENGTH_TICKS = 1.25F * 20F;
    private static final float ROAR_LENGTH_TICKS   = 2.5F  * 20F;
    private static final float DEATH_LENGTH_TICKS  = 3.0F  * 20F;

    private AnimationDefinition lastAnimation  = null;
    private float animationStartTick           = 0F;

    public GotGiantRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GotGiantModel(ctx.bakeLayer(GotModelLayers.GOT_GIANT)),
                0.8F); // shadow radius scaled down to match new entity size
    }

    @Override
    public GotGiantRenderState createRenderState() {
        return new GotGiantRenderState();
    }

    @Override
    public void extractRenderState(GotGiantEntity entity,
                                   GotGiantRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isAttacking   = entity.isAttacking();
        state.isRoaring     = entity.isRoaring();
        state.isEnraged     = entity.isEnraged();
        state.isMoving      = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.isSprinting   = entity.isSprinting();
        state.isDeadOrDying = entity.isDeadOrDying();

        // Resolve which animation to play and store it in the render state so
        // the model's setupAnim can apply it (setupAnim runs inside super.render,
        // which is the correct place — applying animations from render() runs
        // before setupAnim and gets overwritten).
        AnimationDefinition anim = chooseAnimation(state);
        float localTime = state.ageInTicks - animationStartTick;
        float clipLength = clipLengthFor(anim);
        boolean clipDone = isOneShot(anim) && localTime >= clipLength;

        if (anim != lastAnimation || clipDone) {
            animationStartTick = state.ageInTicks;
            lastAnimation      = anim;
            localTime          = 0F;
        }

        // Looping animations use absolute game time; one-shots use elapsed time.
        state.animationToPlay = anim;
        state.animationTime   = isOneShot(anim) ? localTime : state.ageInTicks;
    }

    private static AnimationDefinition chooseAnimation(GotGiantRenderState state) {
        if (state.isDeadOrDying) {
            return GotGiantAnimations.DEATH;
        } else if (state.isAttacking) {
            return GotGiantAnimations.ATTACK;
        } else if (state.isRoaring) {
            return GotGiantAnimations.ROAR;
        } else if (state.isSprinting || (state.isEnraged && state.isMoving)) {
            return GotGiantAnimations.RUN;
        } else if (state.isMoving) {
            return GotGiantAnimations.WALK;
        } else {
            return GotGiantAnimations.IDLE;
        }
    }

    private static boolean isOneShot(AnimationDefinition anim) {
        return anim == GotGiantAnimations.ATTACK
                || anim == GotGiantAnimations.ROAR
                || anim == GotGiantAnimations.DEATH;
    }

    private static float clipLengthFor(AnimationDefinition anim) {
        if (anim == GotGiantAnimations.ATTACK) return ATTACK_LENGTH_TICKS;
        if (anim == GotGiantAnimations.ROAR)   return ROAR_LENGTH_TICKS;
        if (anim == GotGiantAnimations.DEATH)  return DEATH_LENGTH_TICKS;
        return 0F;
    }

    @Override
    public ResourceLocation getTextureLocation(GotGiantRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(GotGiantRenderState state, PoseStack poseStack) {
        // Roughly 1.3× a player's height — just taller than a mammoth.
        poseStack.scale(1.3F, 1.3F, 1.3F);
        super.scale(state, poseStack);
    }
}