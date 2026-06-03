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
 * <h3>Animation priority (highest first)</h3>
 * <ol>
 *   <li>Sprinting / attacking (charge run) → {@link GotMammothAnimations#CHARGE}</li>
 *   <li>Moving                             → {@link GotMammothAnimations#WALK}</li>
 *   <li>Idle (default)                     → {@link GotMammothAnimations#IDLE}</li>
 * </ol>
 */
public class GotMammothRenderer
        extends MobRenderer<GotMammothEntity, GotMammothRenderState, GotMammothModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/animals/got_mammoth.png");

    public GotMammothRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GotMammothModel(ctx.bakeLayer(GotModelLayers.GOT_MAMMOTH)),
                2.5F);    // large shadow radius
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
        state.isSprinting = entity.isSprinting();
        state.isMoving    = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.isAttacking = entity.isAttacking;
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
        if (state.isSprinting || state.isAttacking) {
            model.applyAnimation(GotMammothAnimations.CHARGE, t, 1.0F);
        } else if (state.isMoving) {
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
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1.8F, 1.8F, 1.8F);   // mammoths tower over everything
        }
        super.scale(state, poseStack);
    }
}
