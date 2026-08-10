package net.got.event.entity.client.giant;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.giant.GotGiantEntity;
import net.got.event.entity.client.model.GotModelLayers;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class GotGiantRenderer
        extends MobRenderer<GotGiantEntity, GotGiantRenderState, GotGiantModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/giant/got_giant.png");

    // One-shot clip lengths in ticks (must match GotGiantAnimations durations × 20)
    private static final float ATTACK_LENGTH_TICKS = 1.25F * 20F;
    private static final float ROAR_LENGTH_TICKS   = 2.5F  * 20F;
    private static final float DEATH_LENGTH_TICKS  = 3.0F  * 20F;
    private static final float MOUNT_LENGTH_TICKS  = 1.5F  * 20F;

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
        state.isMounting    = entity.isMounting();
        state.isRiding      = entity.isRiding();

        // Animation selection — state is tracked per-entity on the render state
        // (NOT on the renderer instance, which is shared across all giants).
        AnimationDefinition anim = chooseAnimation(state);
        float localTime = state.ageInTicks - state.animationStartTick;
        float clipLength = clipLengthFor(anim);
        boolean clipDone = isOneShot(anim) && localTime >= clipLength;

        if (anim != state.lastAnimation || clipDone) {
            // New clip or one-shot finished — restart the clock.
            // For attack: if isAttacking just became true again (re-hit during
            // the hold window), we intentionally restart so the full swing plays.
            state.animationStartTick = state.ageInTicks;
            state.lastAnimation      = anim;
            localTime                = 0F;
        }

        // Looping animations use absolute game time; one-shots use elapsed ticks
        // since the clip started (so they don't seek into the middle).
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
        } else if (state.isMounting) {
            // One-shot climb animation plays when the giant first mounts
            return GotGiantAnimations.MOUNT;
        } else if (state.isRiding) {
            // Seated riding pose — moving or stationary
            return state.isMoving ? GotGiantAnimations.RIDE_MOVING
                                  : GotGiantAnimations.RIDE_IDLE;
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
                || anim == GotGiantAnimations.DEATH
                || anim == GotGiantAnimations.MOUNT;
    }

    private static float clipLengthFor(AnimationDefinition anim) {
        if (anim == GotGiantAnimations.ATTACK) return ATTACK_LENGTH_TICKS;
        if (anim == GotGiantAnimations.ROAR)   return ROAR_LENGTH_TICKS;
        if (anim == GotGiantAnimations.DEATH)  return DEATH_LENGTH_TICKS;
        if (anim == GotGiantAnimations.MOUNT)  return MOUNT_LENGTH_TICKS;
        return 0F;
    }

    @Override
    public Identifier getTextureLocation(GotGiantRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(GotGiantRenderState state, PoseStack poseStack) {
        // Roughly 1.3× a player's height — just taller than a mammoth.
        poseStack.scale(1.3F, 1.3F, 1.3F);
        super.scale(state, poseStack);
    }
}