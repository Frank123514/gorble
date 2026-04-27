package net.got.entity.client.horse;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.got.entity.horse.GotHorseEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

/**
 * Translucent markings overlay rendered on top of the base coat.
 * Index 0 (none) is a no-op.
 */
public class GotHorseMarkingsLayer extends GeoRenderLayer<GotHorseEntity> {

    /** Indexed by {@link GotHorseEntity#getMarkingsIndex()} (0-4). 0 = none → null. */
    private static final ResourceLocation[] MARKINGS_TEXTURES = {
        null,
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_markings_blackdots.png"),
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_markings_white.png"),
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_markings_whitedots.png"),
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_markings_whitefield.png"),
    };

    public GotHorseMarkingsLayer(GeoRenderer<GotHorseEntity> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, GotHorseEntity animatable, BakedGeoModel bakedModel,
                       RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
                       float partialTick, int packedLight, int packedOverlay, int colour) {
        int idx = animatable.getMarkingsIndex();
        if (idx <= 0 || idx >= MARKINGS_TEXTURES.length) return;

        RenderType overlayType = RenderType.entityTranslucent(MARKINGS_TEXTURES[idx]);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable,
                overlayType, bufferSource.getBuffer(overlayType),
                partialTick, packedLight, packedOverlay, -1);
    }
}
