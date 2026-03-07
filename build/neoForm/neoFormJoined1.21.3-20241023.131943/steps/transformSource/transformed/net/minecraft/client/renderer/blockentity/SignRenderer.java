package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SignRenderer implements BlockEntityRenderer<SignBlockEntity> {
    private static final int BLACK_TEXT_OUTLINE_COLOR = -988212;
    private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
    private static final float RENDER_SCALE = 0.6666667F;
    private static final Vec3 TEXT_OFFSET = new Vec3(0.0, 0.33333334F, 0.046666667F);
    private final Map<WoodType, SignRenderer.Models> signModels;
    private final Font font;

    public SignRenderer(BlockEntityRendererProvider.Context context) {
        this.signModels = WoodType.values()
            .collect(
                ImmutableMap.toImmutableMap(
                    p_173645_ -> (WoodType)p_173645_,
                    p_359254_ -> new SignRenderer.Models(
                            createSignModel(context.getModelSet(), p_359254_, true), createSignModel(context.getModelSet(), p_359254_, false)
                        )
                )
            );
        this.font = context.getFont();
    }

    public void render(SignBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockstate = blockEntity.getBlockState();
        SignBlock signblock = (SignBlock)blockstate.getBlock();
        WoodType woodtype = SignBlock.getWoodType(signblock);
        SignRenderer.Models signrenderer$models = this.signModels.get(woodtype);
        Model model = blockstate.getBlock() instanceof StandingSignBlock ? signrenderer$models.standing() : signrenderer$models.wall();
        this.renderSignWithText(blockEntity, poseStack, bufferSource, packedLight, packedOverlay, blockstate, signblock, woodtype, model);
    }

    public float getSignModelRenderScale() {
        return 0.6666667F;
    }

    public float getSignTextRenderScale() {
        return 0.6666667F;
    }

    void renderSignWithText(
        SignBlockEntity signEntity,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int packedLight,
        int packedOverlay,
        BlockState state,
        SignBlock signBlock,
        WoodType woodType,
        Model model
    ) {
        poseStack.pushPose();
        this.translateSign(poseStack, -signBlock.getYRotationDegrees(state), state);
        this.renderSign(poseStack, buffer, packedLight, packedOverlay, woodType, model);
        this.renderSignText(
            signEntity.getBlockPos(),
            signEntity.getFrontText(),
            poseStack,
            buffer,
            packedLight,
            signEntity.getTextLineHeight(),
            signEntity.getMaxTextLineWidth(),
            true
        );
        this.renderSignText(
            signEntity.getBlockPos(),
            signEntity.getBackText(),
            poseStack,
            buffer,
            packedLight,
            signEntity.getTextLineHeight(),
            signEntity.getMaxTextLineWidth(),
            false
        );
        poseStack.popPose();
    }

    void translateSign(PoseStack poseStack, float yRot, BlockState state) {
        poseStack.translate(0.5F, 0.75F * this.getSignModelRenderScale(), 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        if (!(state.getBlock() instanceof StandingSignBlock)) {
            poseStack.translate(0.0F, -0.3125F, -0.4375F);
        }
    }

    void renderSign(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, WoodType woodType, Model model) {
        poseStack.pushPose();
        float f = this.getSignModelRenderScale();
        poseStack.scale(f, -f, -f);
        Material material = this.getSignMaterial(woodType);
        VertexConsumer vertexconsumer = material.buffer(buffer, model::renderType);
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    Material getSignMaterial(WoodType woodType) {
        return Sheets.getSignMaterial(woodType);
    }

    void renderSignText(
        BlockPos pos,
        SignText text,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int packedLight,
        int lineHeight,
        int maxWidth,
        boolean isFrontText
    ) {
        poseStack.pushPose();
        this.translateSignText(poseStack, isFrontText, this.getTextOffset());
        int i = getDarkColor(text);
        int j = 4 * lineHeight / 2;
        FormattedCharSequence[] aformattedcharsequence = text.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), p_277227_ -> {
            List<FormattedCharSequence> list = this.font.split(p_277227_, maxWidth);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        int k;
        boolean flag;
        int l;
        if (text.hasGlowingText()) {
            k = text.getColor().getTextColor();
            flag = isOutlineVisible(pos, k);
            l = 15728880;
        } else {
            k = i;
            flag = false;
            l = packedLight;
        }

        for (int i1 = 0; i1 < 4; i1++) {
            FormattedCharSequence formattedcharsequence = aformattedcharsequence[i1];
            float f = (float)(-this.font.width(formattedcharsequence) / 2);
            if (flag) {
                this.font.drawInBatch8xOutline(formattedcharsequence, f, (float)(i1 * lineHeight - j), k, i, poseStack.last().pose(), buffer, l);
            } else {
                this.font
                    .drawInBatch(
                        formattedcharsequence,
                        f,
                        (float)(i1 * lineHeight - j),
                        k,
                        false,
                        poseStack.last().pose(),
                        buffer,
                        Font.DisplayMode.POLYGON_OFFSET,
                        0,
                        l
                    );
            }
        }

        poseStack.popPose();
    }

    private void translateSignText(PoseStack poseStack, boolean isFrontText, Vec3 offset) {
        if (!isFrontText) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        }

        float f = 0.015625F * this.getSignTextRenderScale();
        poseStack.translate(offset);
        poseStack.scale(f, -f, f);
    }

    Vec3 getTextOffset() {
        return TEXT_OFFSET;
    }

    static boolean isOutlineVisible(BlockPos pos, int textColor) {
        if (textColor == DyeColor.BLACK.getTextColor()) {
            return true;
        } else {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer localplayer = minecraft.player;
            if (localplayer != null && minecraft.options.getCameraType().isFirstPerson() && localplayer.isScoping()) {
                return true;
            } else {
                Entity entity = minecraft.getCameraEntity();
                return entity != null && entity.distanceToSqr(Vec3.atCenterOf(pos)) < (double)OUTLINE_RENDER_DISTANCE;
            }
        }
    }

    public static int getDarkColor(SignText signText) {
        int i = signText.getColor().getTextColor();
        if (i == DyeColor.BLACK.getTextColor() && signText.hasGlowingText()) {
            return -988212;
        } else {
            double d0 = 0.4;
            int j = (int)((double)ARGB.red(i) * 0.4);
            int k = (int)((double)ARGB.green(i) * 0.4);
            int l = (int)((double)ARGB.blue(i) * 0.4);
            return ARGB.color(0, j, k, l);
        }
    }

    public static Model createSignModel(EntityModelSet modelSet, WoodType woodType, boolean standingSign) {
        ModelLayerLocation modellayerlocation = standingSign ? ModelLayers.createStandingSignModelName(woodType) : ModelLayers.createWallSignModelName(woodType);
        return new Model.Simple(modelSet.bakeLayer(modellayerlocation), RenderType::entityCutoutNoCull);
    }

    public static LayerDefinition createSignLayer(boolean standingSign) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("sign", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F), PartPose.ZERO);
        if (standingSign) {
            partdefinition.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F), PartPose.ZERO);
        }

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public net.minecraft.world.phys.AABB getRenderBoundingBox(SignBlockEntity blockEntity) {
        if (blockEntity.getBlockState().getBlock() instanceof StandingSignBlock) {
            net.minecraft.core.BlockPos pos = blockEntity.getBlockPos();
            return new net.minecraft.world.phys.AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.125, pos.getZ() + 1.0);
        }
        return BlockEntityRenderer.super.getRenderBoundingBox(blockEntity);
    }

    @OnlyIn(Dist.CLIENT)
    static record Models(Model standing, Model wall) {
    }
}
