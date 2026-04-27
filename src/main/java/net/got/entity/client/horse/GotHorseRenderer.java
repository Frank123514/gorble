package net.got.entity.client.horse;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.got.entity.horse.GotHorseEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * Entity renderer for {@link GotHorseEntity}.
 *
 * <p>Rendering stack (back to front):
 * <ol>
 *   <li>Base coat — resolved per-entity by {@link GotHorseGeoModel#getTextureResource}.</li>
 *   <li>Markings overlay — {@link GotHorseMarkingsLayer} (translucent, skipped if markings == 0).</li>
 *   <li>Horse-armour overlay — {@link GotHorseArmorLayer} (cutout, skipped when no armour equipped).</li>
 * </ol>
 */
public class GotHorseRenderer extends GeoEntityRenderer<GotHorseEntity> {

    public GotHorseRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GotHorseGeoModel());
        this.shadowRadius = 0.9f;

        // Register overlay layers in draw order (markings first, then armour on top).
        addRenderLayer(new GotHorseMarkingsLayer(this));
        addRenderLayer(new GotHorseArmorLayer(this));
    }

    @Override
    public void preRender(PoseStack poseStack, GotHorseEntity animatable, BakedGeoModel model,
                          MultiBufferSource bufferSource, VertexConsumer buffer,
                          boolean isReRender, float partialTick, int packedLight,
                          int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer,
                isReRender, partialTick, packedLight, packedOverlay, colour);
        if (animatable.isBaby()) {
            poseStack.scale(0.65f, 0.65f, 0.65f);
            this.shadowRadius = 0.585f;
        } else {
            this.shadowRadius = 0.9f;
        }
    }
}
