package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.got.block.BellowsBlock;
import net.got.block.BellowsBlockEntity;
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

    private static final float MAX_PRESS_ANGLE = 0.45f;

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
        float progress = getAnimationProgress(be, partialTick);

        poseStack.pushPose();

        poseStack.translate(0.5, 1.5, 0.5);
        float yRot = state.getValue(BellowsBlock.FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));

        // Rotate top_board around the nozzle end (X=14 in model space = offset 14/16 from root).
        // To pivot around X=14 instead of X=0:
        //   1. shift the part +14 in X so the pivot point is at origin
        //   2. rotate
        //   3. shift back -14
        float pivotX = 14.0f / 16.0f; // nozzle end in block units
        model.topBoard.x = pivotX * 16f;           // shift pivot to origin (model units)
        model.topBoard.zRot = -progress * MAX_PRESS_ANGLE;
        model.topBoard.x = -(pivotX * 16f) + model.topBoard.x; // this doesn't work inline

        // Simpler: just set the pivot offset and rotation directly
        // top_board PartPose offset is (0, -11, 0). The nozzle is at X=14 from root.
        // Rotating around X=14 means we translate X by +14, rotate, translate X by -14.
        model.topBoard.x = 14.0f;
        model.topBoard.zRot = -progress * MAX_PRESS_ANGLE;
        // Compensate translation so nozzle end stays fixed
        model.topBoard.x = 14.0f * (1f - (float)Math.cos(-progress * MAX_PRESS_ANGLE));
        model.topBoard.y = -11.0f + 14.0f * (float)Math.sin(-progress * MAX_PRESS_ANGLE);

        var consumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }

    private float getAnimationProgress(BellowsBlockEntity be, float partialTick) {
        if (!be.pumping) return 0f;
        float t = be.animationProgress + partialTick;
        float half = BellowsBlockEntity.MAX_TICKS / 2f;
        if (t > half) t = BellowsBlockEntity.MAX_TICKS - t;
        return Math.max(0f, Math.min(1f, t / half));
    }
}
