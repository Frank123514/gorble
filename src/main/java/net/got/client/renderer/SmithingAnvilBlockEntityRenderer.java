package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.got.block.SmithingAnvilBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmithingAnvilBlockEntityRenderer implements BlockEntityRenderer<SmithingAnvilBlockEntity> {

    private final ItemModelResolver itemModelResolver;
    private final ItemStackRenderState renderState = new ItemStackRenderState();

    public SmithingAnvilBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemModelResolver = ctx.getItemModelResolver();
    }

    @Override
    public void render(SmithingAnvilBlockEntity be, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource,
                       int packedLight, int packedOverlay, Vec3 cameraPos) {

        ItemStack input = be.getInputItem();
        ItemStack toRender = input.isEmpty() ? be.getLastCraftedItem() : input;
        if (toRender.isEmpty()) return;

        // Sample light from the air above the anvil top face, not the block itself
        int light = getLightAbove(be.getLevel(), be.getBlockPos());

        // Resolve item model into reusable render state
        itemModelResolver.updateForTopItem(
                renderState,
                toRender,
                ItemDisplayContext.FIXED,
                be.getLevel(),
                null,
                0
        );

        if (renderState.isEmpty()) return;

        poseStack.pushPose();
        poseStack.translate(0.5, 1.025, 0.5);
        poseStack.scale(0.75f, 0.75f, 0.75f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90f));

        renderState.render(poseStack, bufferSource, light, packedOverlay);

        poseStack.popPose();
    }

    private static int getLightAbove(Level level, BlockPos pos) {
        if (level == null) return LightTexture.FULL_BRIGHT;
        BlockPos above = pos.above();
        int block = level.getBrightness(LightLayer.BLOCK, above);
        int sky   = level.getBrightness(LightLayer.SKY,   above);
        return LightTexture.pack(block, sky);
    }

}