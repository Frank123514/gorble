package net.got.entity.client.stag;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.stag.GotStagEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for {@link GotStagEntity}.
 *
 * <p>Uses the custom {@link GotStagModel} (converted from
 * {@code assets/got/geo/got_stag.geo.json}).  All animation is driven inside
 * {@link GotStagModel#setupAnim}.
 */
public class GotStagRenderer
        extends MobRenderer<GotStagEntity, GotStagRenderState, GotStagModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/got_stag.png");

    public GotStagRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GotStagModel(ctx.bakeLayer(GotModelLayers.GOT_STAG)), 0.9f);
    }

    // ── Render state ──────────────────────────────────────────────────────────

    @Override
    public GotStagRenderState createRenderState() {
        return new GotStagRenderState();
    }

    @Override
    public void extractRenderState(GotStagEntity entity, GotStagRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isStanding = entity.isStanding();
        state.isEating   = entity.isEating();
    }

    // ── Texture & scale ───────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(GotStagRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(GotStagRenderState state, PoseStack poseStack, float partialTick) {
        if (state.isBaby) poseStack.scale(0.65f, 0.65f, 0.65f);
    }
}