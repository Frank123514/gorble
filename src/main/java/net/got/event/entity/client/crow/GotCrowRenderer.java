package net.got.event.entity.client.crow;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.client.model.GotModelLayers;
import net.got.event.entity.crow.GotCrowEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

/**
 * Renderer for {@link GotCrowEntity}.
 *
 * <h3>Animation priority (highest first)</h3>
 * <ol>
 *   <li>Flying (not on ground, airTicks ≥ 10) → {@link GotCrowAnimations#FLY}</li>
 *   <li>Moving on ground                      → {@link GotCrowAnimations#WALK}</li>
 *   <li>Idle (default)                        → {@link GotCrowAnimations#IDLE}</li>
 * </ol>
 */
public class GotCrowRenderer
        extends MobRenderer<GotCrowEntity, GotCrowRenderState, GotCrowModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_crow.png");

    public GotCrowRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GotCrowModel(ctx.bakeLayer(GotModelLayers.GOT_CROW)),
                0.25F);
    }

    // ── Render state ──────────────────────────────────────────────────────────

    @Override
    public GotCrowRenderState createRenderState() {
        return new GotCrowRenderState();
    }

    @Override
    public void extractRenderState(GotCrowEntity entity,
                                   GotCrowRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isInWater = entity.isInWater();
        state.isMoving  = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.airTicks  = entity.airTicks;
        state.isFlying  = entity.airTicks >= 10 && !entity.isInWater();
    }

    // ── Animation ─────────────────────────────────────────────────────────────

    @Override
    public void render(GotCrowRenderState state,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {
        selectAndApplyAnimation(state);
        super.render(state, poseStack, bufferSource, packedLight);
    }

    private void selectAndApplyAnimation(GotCrowRenderState state) {
        float t = state.ageInTicks;
        if (state.isFlying) {
            model.applyAnimation(GotCrowAnimations.FLY, t, 1.0F);
        } else if (state.isMoving) {
            model.applyAnimation(GotCrowAnimations.WALK, t, 1.0F);
        } else {
            model.applyAnimation(GotCrowAnimations.IDLE, t, 1.0F);
        }
    }

    // ── Texture ───────────────────────────────────────────────────────────────

    @Override
    public Identifier getTextureLocation(GotCrowRenderState state) {
        return TEXTURE;
    }

    // ── Scale ─────────────────────────────────────────────────────────────────

    @Override
    protected void scale(GotCrowRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(0.7F, 0.7F, 0.7F);
        }
        super.scale(state, poseStack);
    }
}