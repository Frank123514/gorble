package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Calendar;
import net.minecraft.client.model.ChestModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
    private final ChestModel singleModel;
    private final ChestModel doubleLeftModel;
    private final ChestModel doubleRightModel;
    private boolean xmasTextures;

    public ChestRenderer(BlockEntityRendererProvider.Context context) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
            this.xmasTextures = true;
        }

        this.singleModel = new ChestModel(context.bakeLayer(ModelLayers.CHEST));
        this.doubleLeftModel = new ChestModel(context.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT));
        this.doubleRightModel = new ChestModel(context.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT));
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        boolean flag = level != null;
        BlockState blockstate = flag ? blockEntity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.hasProperty(ChestBlock.TYPE) ? blockstate.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        if (blockstate.getBlock() instanceof AbstractChestBlock<?> abstractchestblock) {
            boolean flag1 = chesttype != ChestType.SINGLE;
            poseStack.pushPose();
            float f = blockstate.getValue(ChestBlock.FACING).toYRot();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(-f));
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> neighborcombineresult;
            if (flag) {
                neighborcombineresult = abstractchestblock.combine(blockstate, level, blockEntity.getBlockPos(), true);
            } else {
                neighborcombineresult = DoubleBlockCombiner.Combiner::acceptNone;
            }

            float f1 = neighborcombineresult.apply(ChestBlock.opennessCombiner(blockEntity)).get(partialTick);
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            int i = neighborcombineresult.apply(new BrightnessCombiner<>()).applyAsInt(packedLight);
            Material material = this.getMaterial(blockEntity, chesttype);
            VertexConsumer vertexconsumer = material.buffer(bufferSource, RenderType::entityCutout);
            if (flag1) {
                if (chesttype == ChestType.LEFT) {
                    this.render(poseStack, vertexconsumer, this.doubleLeftModel, f1, i, packedOverlay);
                } else {
                    this.render(poseStack, vertexconsumer, this.doubleRightModel, f1, i, packedOverlay);
                }
            } else {
                this.render(poseStack, vertexconsumer, this.singleModel, f1, i, packedOverlay);
            }

            poseStack.popPose();
        }
    }

    private void render(PoseStack poseStack, VertexConsumer buffer, ChestModel model, float openness, int packedLight, int packedOverlay) {
        model.setupAnim(openness);
        model.renderToBuffer(poseStack, buffer, packedLight, packedOverlay);
    }

    protected Material getMaterial(T blockEntity, ChestType chestType) {
        return Sheets.chooseMaterial(blockEntity, chestType, this.xmasTextures);
    }

    @Override
    public net.minecraft.world.phys.AABB getRenderBoundingBox(T blockEntity) {
        net.minecraft.core.BlockPos pos = blockEntity.getBlockPos();
        return net.minecraft.world.phys.AABB.encapsulatingFullBlocks(pos.offset(-1, 0, -1), pos.offset(1, 1, 1));
    }
}
