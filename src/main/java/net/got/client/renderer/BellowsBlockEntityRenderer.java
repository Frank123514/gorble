package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.got.block.BellowsBlock;
import net.got.block.BellowsBlockEntity;
import net.got.entity.client.model.GotModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Geometry copied directly from the Blockbench export (bellowModel.java).
 * The only change is top_board's child top_board_r1 gets its zRot animated.
 */
@OnlyIn(Dist.CLIENT)
public class BellowsBlockEntityRenderer implements BlockEntityRenderer<BellowsBlockEntity> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/block/bellows.png");

    private static final float MAX_PRESS_ANGLE = 0.45f;

    // top-level parts (children of root)
    private final ModelPart body;
    private final ModelPart topBoard;
    private final ModelPart bottomBoard;
    private final ModelPart nozzle;
    private final ModelPart handle;

    // the actual animated sub-part
    private final ModelPart topBoardR1;

    public BellowsBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        ModelPart root = ctx.bakeLayer(GotModelLayers.BELLOWS).getChild("root");
        this.body        = root.getChild("body");
        this.topBoard    = root.getChild("top_board");
        this.bottomBoard = root.getChild("bottom_board");
        this.nozzle      = root.getChild("nozzle");
        this.handle      = root.getChild("handle");
        this.topBoardR1  = this.topBoard.getChild("top_board_r1");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Root anchored at Y=24 (ground level), matching Blockbench export exactly
        PartDefinition root = partdefinition.addOrReplaceChild("root",
                CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        // Body (static — body_a, body_b, body_b_3 inline; body_c and body_b_2 as children)
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-6.0F, 4.0F, -4.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(40, 22).addBox(-5.0F, 3.0F, -3.5F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(40, 30).addBox(-4.1F, 1.4F, -3.5F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -6.0F, 0.0F));

        body.addOrReplaceChild("body_c_r1",
                CubeListBuilder.create()
                        .texOffs(0, 31).addBox(-7.0F, -0.5F, -4.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        body.addOrReplaceChild("body_b_2_r1",
                CubeListBuilder.create()
                        .texOffs(0, 40).addBox(-4.7284F, -0.5481F, -4.0F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, 2.5F, 0.5F, 0.0F, 0.0F, 0.3927F));

        // Top board (animated — child top_board_r1 gets zRot adjusted each frame)
        PartDefinition topBoard = root.addOrReplaceChild("top_board",
                CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 0.0F));

        topBoard.addOrReplaceChild("top_board_r1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8.1485F, -0.2371F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.9F, 6.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        // Bottom board (static)
        root.addOrReplaceChild("bottom_board",
                CubeListBuilder.create()
                        .texOffs(0, 11).addBox(-7.0F, 0.0F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        // Nozzle (static)
        root.addOrReplaceChild("nozzle",
                CubeListBuilder.create()
                        .texOffs(38, 40).addBox(-8.0F, 3.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(48, 0).addBox(-6.0F, 3.6F, -1.0F, 2.0F, 1.5F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(14.0F, -6.0F, 0.0F));

        // Handle (static, with rotated top child)
        PartDefinition handle = root.addOrReplaceChild("handle",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-18.0F, 12.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(8.0F, -13.0F, 0.0F));

        handle.addOrReplaceChild("handle_top_r1",
                CubeListBuilder.create()
                        .texOffs(38, 47).addBox(-1.8858F, -0.735F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-15.6F, 4.6F, 0.0F, 0.0F, 0.0F, 0.3927F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void render(BellowsBlockEntity be, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource,
                       int packedLight, int packedOverlay) {

        BlockState state = be.getBlockState();
        float progress = getAnimationProgress(be, partialTick);

        poseStack.pushPose();

        // Standard block entity transform: centre, flip Y upright
        poseStack.translate(0.5, 1.5, 0.5);
        float yRot = state.getValue(BellowsBlock.FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));

        // Animate: top_board_r1 rests at 0.3927 rad, presses further negative
        topBoardR1.zRot = 0.3927F + (progress * -MAX_PRESS_ANGLE);

        var consumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));

        body.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        topBoard.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        bottomBoard.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        nozzle.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        handle.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }

    private float getAnimationProgress(BellowsBlockEntity be, float partialTick) {
        if (!be.pumping) return 0f;
        float t = be.animationProgress + partialTick;
        float half = BellowsBlockEntity.MAX_TICKS / 2f;
        if (t > half) t = BellowsBlockEntity.MAX_TICKS - t;
        return Math.max(0f, t / half);
    }
}