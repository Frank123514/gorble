package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class RenderLayer<S extends EntityRenderState, M extends EntityModel<? super S>> {
    private final RenderLayerParent<S, M> renderer;

    public RenderLayer(RenderLayerParent<S, M> renderer) {
        this.renderer = renderer;
    }

    protected static <S extends LivingEntityRenderState> void coloredCutoutModelCopyLayerRender(
        EntityModel<S> model, ResourceLocation textureLocation, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, int color
    ) {
        if (!renderState.isInvisible) {
            model.setupAnim(renderState);
            renderColoredCutoutModel(model, textureLocation, poseStack, bufferSource, packedLight, renderState, color);
        }
    }

    protected static void renderColoredCutoutModel(
        EntityModel<?> model,
        ResourceLocation textureLocation,
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        int packedLight,
        LivingEntityRenderState renderState,
        int color
    ) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(textureLocation));
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(renderState, 0.0F), color);
    }

    public M getParentModel() {
        return this.renderer.getModel();
    }

    public abstract void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot);
}
