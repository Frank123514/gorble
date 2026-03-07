package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.ParrotRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.Parrot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParrotOnShoulderLayer extends RenderLayer<PlayerRenderState, PlayerModel> {
    private final ParrotModel model;
    private final ParrotRenderState parrotState = new ParrotRenderState();

    public ParrotOnShoulderLayer(RenderLayerParent<PlayerRenderState, PlayerModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new ParrotModel(modelSet.bakeLayer(ModelLayers.PARROT));
        this.parrotState.pose = ParrotModel.Pose.ON_SHOULDER;
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, PlayerRenderState renderState, float yRot, float xRot) {
        Parrot.Variant parrot$variant = renderState.parrotOnLeftShoulder;
        if (parrot$variant != null) {
            this.renderOnShoulder(poseStack, bufferSource, packedLight, renderState, parrot$variant, yRot, xRot, true);
        }

        Parrot.Variant parrot$variant1 = renderState.parrotOnRightShoulder;
        if (parrot$variant1 != null) {
            this.renderOnShoulder(poseStack, bufferSource, packedLight, renderState, parrot$variant1, yRot, xRot, false);
        }
    }

    private void renderOnShoulder(
        PoseStack poseStack,
        MultiBufferSource buffer,
        int packedLight,
        PlayerRenderState renderState,
        Parrot.Variant variant,
        float yRot,
        float xRot,
        boolean leftShoulder
    ) {
        poseStack.pushPose();
        poseStack.translate(leftShoulder ? 0.4F : -0.4F, renderState.isCrouching ? -1.3F : -1.5F, 0.0F);
        this.parrotState.ageInTicks = renderState.ageInTicks;
        this.parrotState.walkAnimationPos = renderState.walkAnimationPos;
        this.parrotState.walkAnimationSpeed = renderState.walkAnimationSpeed;
        this.parrotState.yRot = yRot;
        this.parrotState.xRot = xRot;
        this.model.setupAnim(this.parrotState);
        this.model
            .renderToBuffer(
                poseStack, buffer.getBuffer(this.model.renderType(ParrotRenderer.getVariantTexture(variant))), packedLight, OverlayTexture.NO_OVERLAY
            );
        poseStack.popPose();
    }
}
