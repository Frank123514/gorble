package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.got.block.BellowsBlock;
import net.got.block.BellowsBlockEntity;
import net.got.client.animation.BellowsAnimations;
import net.got.client.model.BellowsModel;
import net.got.entity.client.model.GotModelLayers;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BellowsBlockEntityRenderer implements BlockEntityRenderer<BellowsBlockEntity> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/block/bellows.png");

    private final BellowsModel model;

    public BellowsBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new BellowsModel(ctx.bakeLayer(GotModelLayers.BELLOWS));
    }

    public static LayerDefinition createBodyLayer() {
        return BellowsModel.createBodyLayer();
    }

    @Override
    public void render(BellowsBlockEntity be, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource,
                       int packedLight, int packedOverlay) {

        BlockState state = be.getBlockState();

        // Drive animation from the block entity's existing tick counter.
        // animationProgress ticks map to seconds: ticks * (1/20) * animLength normalises time.
        // KeyframeAnimations.animate expects milliseconds: ticks * 50ms.
        // Always call applyAnimation — even when not pumping — so the shared model
        // instance is reset to rest pose before each bellows is rendered.
        // (A single BellowsModel is reused for every bellows in the world.)
        if (be.pumping) {
            float ageInTicks = be.animationProgress + partialTick;
            model.applyAnimation(BellowsAnimations.PUMPING, ageInTicks, 1.0F);
        } else {
            model.applyAnimation(BellowsAnimations.PUMPING, 0f, 0.0F);
        }

        poseStack.pushPose();

        poseStack.translate(0.5, 1.5, 0.5);
        float yRot = state.getValue(BellowsBlock.FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(270f - yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));

        var consumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }
}