package net.got.entity.client.stag;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.stag.GotStagEntity;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for {@link GotStagEntity}.
 *
 * <p>Extends vanilla {@link AbstractHorseRenderer} — the same base used by
 * vanilla {@code HorseRenderer}, {@code DonkeyRenderer}, etc. — so all horse
 * rendering logic (saddle layers, baby scaling, shadow) is inherited for free.
 *
 * <p>The model is {@link GotStagModel}, which itself extends vanilla
 * {@link HorseModel}, meaning the full vanilla animation pipeline runs
 * unchanged on the stag's geometry.
 */
public class GotStagRenderer
        extends AbstractHorseRenderer<GotStagEntity, HorseRenderState, GotStagModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/got_stag.png");

    public GotStagRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GotStagModel(ctx.bakeLayer(GotModelLayers.GOT_STAG)), 0.9f);
    }

    // ── Render state ──────────────────────────────────────────────────────────

    @Override
    public HorseRenderState createRenderState() {
        return new GotStagRenderState();
    }

    /**
     * {@code AbstractHorseRenderer#extractRenderState} already populates all
     * horse animation fields from the entity.  We just call super — nothing
     * stag-specific is needed here.
     */
    @Override
    public void extractRenderState(GotStagEntity entity,
                                   HorseRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
    }

    // ── Texture ───────────────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(HorseRenderState state) {
        return TEXTURE;
    }

    // ── Scale ─────────────────────────────────────────────────────────────────

    @Override
    protected void scale(HorseRenderState state, PoseStack poseStack) {
        super.scale(state, poseStack);   // handles baby scaling automatically
    }
}