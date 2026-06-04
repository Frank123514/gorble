package net.got.entity.client.mammoth;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.mammoth.GotMammothEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for {@link GotMammothEntity}.
 *
 * <h3>Animation dispatch (highest priority first):</h3>
 * <ol>
 *   <li>Dead                          → {@link GotMammothAnimations#DEATH} (one-shot)</li>
 *   <li>Angry / sprinting             → {@link GotMammothAnimations#RUN} (charge)</li>
 *   <li>Attacking (angry + not moving)→ {@link GotMammothAnimations#ATTACK} (one-shot)</li>
 *   <li>Moving / swimming             → {@link GotMammothAnimations#WALK}</li>
 *   <li>Idle                          → {@link GotMammothAnimations#IDLE}</li>
 * </ol>
 *
 * <h3>One-shot fix:</h3>
 * ATTACK and DEATH are non-looping.  The global {@code ageInTicks} clock keeps
 * advancing, so seeking into those animations with the raw age would skip past
 * the end and show nothing.  We track a per-renderer start tick and subtract it
 * so every one-shot always begins at local time 0.
 */
public class GotMammothRenderer
        extends MobRenderer<GotMammothEntity, GotMammothRenderState, GotMammothModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/animals/got_mammoth.png");

    public GotMammothRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GotMammothModel(ctx.bakeLayer(GotModelLayers.GOT_MAMMOTH)),
                1.4F);
    }

    // ── Render state ──────────────────────────────────────────────────────────

    @Override
    public GotMammothRenderState createRenderState() {
        return new GotMammothRenderState();
    }

    @Override
    public void extractRenderState(GotMammothEntity entity,
                                   GotMammothRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isInWater    = entity.isInWater();
        state.isSprinting  = entity.isSprinting();
        state.isMoving     = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.isAngry      = entity.isAngry();
        state.isDeadOrDying = entity.isDeadOrDying();
        state.isAttacking  = entity.isAttacking();
    }

    // ── Animation ─────────────────────────────────────────────────────────────

    @Override
    public void render(GotMammothRenderState state,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {
        selectAndApplyAnimation(state);
        super.render(state, poseStack, bufferSource, packedLight);
    }

    // Clip length in ticks for each one-shot — matches Builder.withLength() values.
    private static final float ATTACK_LENGTH_TICKS = 1.0F * 20F;

    private void selectAndApplyAnimation(GotMammothRenderState state) {
        AnimationDefinition anim = chooseAnimation(state);

        // Reset the local timer whenever:
        //   (a) we switch to a different animation, OR
        //   (b) the ATTACK clip has finished — lets it retrigger on every swing.
        //       DEATH is intentionally excluded: it should hold its final pose.
        float localTime = state.ageInTicks - state.animationStartTick;
        boolean clipFinished = anim == GotMammothAnimations.ATTACK
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

    /** Returns true for animations that must not loop (no {@code .looping()} call). */
    private static boolean isOneShot(AnimationDefinition anim) {
        return anim == GotMammothAnimations.ATTACK
                || anim == GotMammothAnimations.DEATH;
    }

    // ── Texture ───────────────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(GotMammothRenderState state) {
        return TEXTURE;
    }

    // ── Scale ─────────────────────────────────────────────────────────────────

    @Override
    protected void scale(GotMammothRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.55F, 0.55F, 0.55F);
        } else {
            poseStack.scale(1.4F, 1.4F, 1.4F);
        }
        super.scale(state, poseStack);
    }
}