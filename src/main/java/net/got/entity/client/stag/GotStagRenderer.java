package net.got.entity.client.stag;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.stag.GotStagEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for {@link GotStagEntity}.
 *
 * <p>Extends {@link MobRenderer} (replacing the old {@code AbstractHorseRenderer})
 * so the fully custom {@link GotStagModel} — which no longer extends
 * {@code HorseModel} — can be used without any horse-specific machinery.
 *
 * <h3>Animation dispatch</h3>
 * <p>{@link #render} calls {@link #selectAndApplyAnimation} before the
 * super-call, writing bone transforms into the model via
 * {@link GotStagModel#applyAnimation}. Priority (highest first):
 * <ol>
 *   <li>Rearing     → {@link GotStagAnimations#REAR}</li>
 *   <li>Sprinting   → {@link GotStagAnimations#RUN}</li>
 *   <li>Moving / swimming → {@link GotStagAnimations#WALK}</li>
 *   <li>Idle + tamed → {@link GotStagAnimations#IDLE} + {@link GotStagAnimations#TAIL_WAG}</li>
 *   <li>Default     → {@link GotStagAnimations#IDLE}</li>
 * </ol>
 */
public class GotStagRenderer
        extends MobRenderer<GotStagEntity, GotStagRenderState, GotStagModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/animals/got_stag.png");

    public GotStagRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GotStagModel(ctx.bakeLayer(GotModelLayers.GOT_STAG)),
                0.7F);
    }

    // ── Render state ──────────────────────────────────────────────────────────

    @Override
    public GotStagRenderState createRenderState() {
        return new GotStagRenderState();
    }

    @Override
    public void extractRenderState(GotStagEntity entity,
                                   GotStagRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isStanding  = entity.isStanding();
        state.isInWater   = entity.isInWater();
        state.isSprinting = entity.isSprinting();
        state.isMoving    = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.isTame      = entity.isTamed();
    }

    // ── Animation ─────────────────────────────────────────────────────────────

    /**
     * In 1.21.4, {@code render} takes {@code (state, poseStack, bufferSource, packedLight)}
     * — the entity is no longer passed. We apply the animation here so the model's
     * bone transforms are ready before {@code super.render} calls {@code setupAnim}.
     */
    @Override
    public void render(GotStagRenderState state,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {
        selectAndApplyAnimation(state);
        super.render(state, poseStack, bufferSource, packedLight);
    }

    private void selectAndApplyAnimation(GotStagRenderState state) {
        float t = state.ageInTicks;
        if (state.isStanding) {
            model.applyAnimation(GotStagAnimations.REAR, t, 1.0F);
        } else if (state.isSprinting) {
            model.applyAnimation(GotStagAnimations.RUN, t, 1.0F);
        } else if (state.isMoving || state.isInWater) {
            model.applyAnimation(GotStagAnimations.WALK, t, 1.0F);
        } else {
            model.applyAnimation(GotStagAnimations.IDLE, t, 1.0F);
            if (state.isTame) {
                model.applyAnimation(GotStagAnimations.TAIL_WAG, t, 1.0F);
            }
        }
    }

    // ── Texture ───────────────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(GotStagRenderState state) {
        return TEXTURE;
    }

    // ── Scale ─────────────────────────────────────────────────────────────────

    @Override
    protected void scale(GotStagRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        super.scale(state, poseStack);
    }
}
