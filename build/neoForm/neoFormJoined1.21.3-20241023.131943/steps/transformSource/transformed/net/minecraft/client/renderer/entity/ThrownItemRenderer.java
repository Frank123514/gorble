package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.ThrownItemRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThrownItemRenderer<T extends Entity & ItemSupplier> extends EntityRenderer<T, ThrownItemRenderState> {
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public ThrownItemRenderer(EntityRendererProvider.Context context, float scale, boolean fullBright) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.scale = scale;
        this.fullBright = fullBright;
    }

    public ThrownItemRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F, false);
    }

    @Override
    protected int getBlockLightLevel(T entity, BlockPos pos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(entity, pos);
    }

    public void render(ThrownItemRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(this.scale, this.scale, this.scale);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        if (renderState.itemModel != null) {
            this.itemRenderer
                .render(renderState.item, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY, renderState.itemModel);
        }

        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    public ThrownItemRenderState createRenderState() {
        return new ThrownItemRenderState();
    }

    public void extractRenderState(T entity, ThrownItemRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        ItemStack itemstack = entity.getItem();
        reusedState.itemModel = !itemstack.isEmpty() ? this.itemRenderer.getModel(itemstack, entity.level(), null, entity.getId()) : null;
        reusedState.item = itemstack.copy();
    }
}
