package net.got.entity.client.horse;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/**
 * Translucent markings overlay rendered on top of the base coat.
 * Index 0 (none) is a no-op.
 *
 * <p>Uses a second instance of {@link GotHorseModel} (baked from
 * {@link GotModelLayers#GOT_HORSE}) so the overlay shares the same
 * animated pose as the primary model.
 */
public class GotHorseMarkingsLayer
        extends RenderLayer<GotHorseRenderState, GotHorseModel> {

    private static final ResourceLocation[] MARKINGS_TEXTURES = {
            null,
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_markings_blackdots.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_markings_white.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_markings_whitedots.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_markings_whitefield.png"),
    };

    private final GotHorseModel overlayModel;

    public GotHorseMarkingsLayer(RenderLayerParent<GotHorseRenderState, GotHorseModel> parent,
                                 EntityModelSet models) {
        super(parent);
        this.overlayModel = new GotHorseModel(models.bakeLayer(GotModelLayers.GOT_HORSE));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       GotHorseRenderState state, float yRot, float xRot) {
        int idx = state.markingsIndex;
        if (idx <= 0 || idx >= MARKINGS_TEXTURES.length || MARKINGS_TEXTURES[idx] == null) return;

        // Copy pose from parent model to overlay model
        this.getParentModel().copyState(this.overlayModel);

        this.overlayModel.renderToBuffer(poseStack,
                buffer.getBuffer(RenderType.entityTranslucent(MARKINGS_TEXTURES[idx])),
                packedLight, OverlayTexture.NO_OVERLAY);
    }
}