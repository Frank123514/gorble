package net.got.event.entity.client.giant;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.giant.GiantEntity;
import net.got.event.entity.client.model.ModelLayers;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class GiantRenderer
        extends MobRenderer<GiantEntity, GiantRenderState, GiantModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/giant/got_giant.png");

    private static final float ATTACK_LENGTH_TICKS = 1.25F * 20F;
    private static final float ROAR_LENGTH_TICKS   = 2.5F  * 20F;
    private static final float DEATH_LENGTH_TICKS  = 3.0F  * 20F;
    private static final float MOUNT_LENGTH_TICKS  = 1.5F  * 20F;

    public GiantRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GiantModel(ctx.bakeLayer(ModelLayers.GOT_GIANT)),
                0.8F);
    }

    @Override
    public GiantRenderState createRenderState() {
        return new GiantRenderState();
    }

    @Override
    public void extractRenderState(GiantEntity entity,
                                   GiantRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isAttacking   = entity.isAttacking();
        state.isRoaring     = entity.isRoaring();
        state.isEnraged     = entity.isEnraged();
        state.isMoving      = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.isSprinting   = entity.isSprinting();
        state.isDeadOrDying = entity.isDeadOrDying();
        state.isMounting    = entity.isMounting();
        state.isRiding      = entity.isRiding();

        AnimationDefinition anim = chooseAnimation(state);
        float localTime = state.ageInTicks - state.animationStartTick;
        float clipLength = clipLengthFor(anim);
        boolean clipDone = isOneShot(anim) && localTime >= clipLength;

        if (anim != state.lastAnimation || clipDone) {
            
            state.animationStartTick = state.ageInTicks;
            state.lastAnimation      = anim;
            localTime                = 0F;
        }

        state.animationToPlay = anim;
        state.animationTime   = isOneShot(anim) ? localTime : state.ageInTicks;
    }

    private static AnimationDefinition chooseAnimation(GiantRenderState state) {
        if (state.isDeadOrDying) {
            return GiantAnimations.DEATH;
        } else if (state.isAttacking) {
            return GiantAnimations.ATTACK;
        } else if (state.isRoaring) {
            return GiantAnimations.ROAR;
        } else if (state.isMounting) {
            
            return GiantAnimations.MOUNT;
        } else if (state.isRiding) {
            
            return state.isMoving ? GiantAnimations.RIDE_MOVING
                                  : GiantAnimations.RIDE_IDLE;
        } else if (state.isSprinting || (state.isEnraged && state.isMoving)) {
            return GiantAnimations.RUN;
        } else if (state.isMoving) {
            return GiantAnimations.WALK;
        } else {
            return GiantAnimations.IDLE;
        }
    }

    private static boolean isOneShot(AnimationDefinition anim) {
        return anim == GiantAnimations.ATTACK
                || anim == GiantAnimations.ROAR
                || anim == GiantAnimations.DEATH
                || anim == GiantAnimations.MOUNT;
    }

    private static float clipLengthFor(AnimationDefinition anim) {
        if (anim == GiantAnimations.ATTACK) return ATTACK_LENGTH_TICKS;
        if (anim == GiantAnimations.ROAR)   return ROAR_LENGTH_TICKS;
        if (anim == GiantAnimations.DEATH)  return DEATH_LENGTH_TICKS;
        if (anim == GiantAnimations.MOUNT)  return MOUNT_LENGTH_TICKS;
        return 0F;
    }

    @Override
    public Identifier getTextureLocation(GiantRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(GiantRenderState state, PoseStack poseStack) {
        
        poseStack.scale(1.3F, 1.3F, 1.3F);
        super.scale(state, poseStack);
    }
}