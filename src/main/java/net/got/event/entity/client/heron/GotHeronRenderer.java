package net.got.event.entity.client.heron;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.client.model.GotModelLayers;
import net.got.event.entity.heron.GotHeronEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

/**
 * Renderer for {@link GotHeronEntity}.
 *
 * <h3>Animation priority (highest first)</h3>
 * <ol>
 *   <li>Flying (not on ground)          → {@link GotHeronAnimations#FLY}</li>
 *   <li>In water and moving             → {@link GotHeronAnimations#WADE}</li>
 *   <li>Moving on ground                → {@link GotHeronAnimations#WALK}</li>
 *   <li>Idle (default)                  → {@link GotHeronAnimations#IDLE}</li>
 * </ol>
 */
public class GotHeronRenderer
        extends MobRenderer<GotHeronEntity, GotHeronRenderState, GotHeronModel> {

    private static final Identifier TEXTURE_GREY =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_heron.png");
    private static final Identifier TEXTURE_BLUE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_heron_blue.png");
    private static final Identifier TEXTURE_WHITE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_heron_white.png");
    private static final Identifier TEXTURE_NIGHT =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_heron_night.png");

    public GotHeronRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GotHeronModel(ctx.bakeLayer(GotModelLayers.GOT_HERON)),
                0.4F);
    }

    // ── Render state ──────────────────────────────────────────────────────────

    @Override
    public GotHeronRenderState createRenderState() {
        return new GotHeronRenderState();
    }

    @Override
    public void extractRenderState(GotHeronEntity entity,
                                   GotHeronRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isInWater = entity.isInWater();
        state.isMoving  = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.airTicks  = entity.airTicks;
        state.isFlying  = entity.airTicks >= 15 && !entity.isInWater();
        state.variant   = entity.getVariant();
    }

    // ── Animation ─────────────────────────────────────────────────────────────

    @Override
    public void render(GotHeronRenderState state,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {
        selectAndApplyAnimation(state);
        super.render(state, poseStack, bufferSource, packedLight);
    }

    private void selectAndApplyAnimation(GotHeronRenderState state) {
        float t = state.ageInTicks;
        if (state.isFlying) {
            model.applyAnimation(GotHeronAnimations.FLY, t, 1.0F);
        } else if (state.isInWater && state.isMoving) {
            model.applyAnimation(GotHeronAnimations.WADE, t, 1.0F);
        } else if (state.isMoving) {
            model.applyAnimation(GotHeronAnimations.WALK, t, 1.0F);
        } else {
            model.applyAnimation(GotHeronAnimations.IDLE, t, 1.0F);
        }
    }

    // ── Texture ───────────────────────────────────────────────────────────────

    @Override
    public Identifier getTextureLocation(GotHeronRenderState state) {
        return switch (state.variant) {
            case 1  -> TEXTURE_BLUE;
            case 2  -> TEXTURE_WHITE;
            case 3  -> TEXTURE_NIGHT;
            default -> TEXTURE_GREY;
        };
    }

    // ── Scale ─────────────────────────────────────────────────────────────────

    @Override
    protected void scale(GotHeronRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(0.9F, 0.9F, 0.9F);
        }
        super.scale(state, poseStack);
    }
}