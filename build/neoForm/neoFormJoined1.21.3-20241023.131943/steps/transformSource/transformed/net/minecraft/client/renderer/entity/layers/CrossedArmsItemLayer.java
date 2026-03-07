package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrossedArmsItemLayer<S extends LivingEntityRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
    private final ItemRenderer itemRenderer;

    public CrossedArmsItemLayer(RenderLayerParent<S, M> renderer, ItemRenderer itemRenderer) {
        super(renderer);
        this.itemRenderer = itemRenderer;
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot) {
        BakedModel bakedmodel = renderState.getMainHandItemModel();
        if (bakedmodel != null) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.4F, -0.4F);
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            ItemStack itemstack = renderState.getMainHandItem();
            this.itemRenderer.render(itemstack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY, bakedmodel);
            poseStack.popPose();
        }
    }
}
