package net.got.entity.client.mammoth;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.mammoth.GotMammothEntity;
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

    private void selectAndApplyAnimation(GotMammothRenderState state) {
        float t = state.ageInTicks;
        if (state.isDeadOrDying) {
            model.applyAnimation(GotMammothAnimations.DEATH, t, 1.0F);
        } else if (state.isSprinting || (state.isAngry && state.isMoving)) {
            model.applyAnimation(GotMammothAnimations.RUN, t, 1.0F);
        } else if (state.isAttacking) {
            model.applyAnimation(GotMammothAnimations.ATTACK, t, 1.0F);
        } else if (state.isMoving || state.isInWater) {
            model.applyAnimation(GotMammothAnimations.WALK, t, 1.0F);
        } else {
            model.applyAnimation(GotMammothAnimations.IDLE, t, 1.0F);
        }
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
