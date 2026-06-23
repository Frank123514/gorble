package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.got.block.SmithingAnvilBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Renders the input ingot sitting on top of the Smithing Anvil in the world.
 *
 * The anvil top face sits at y=16 (the full block top minus the horn geometry
 * that overhangs to y=16). We place the item flat on the surface using the
 * FIXED display transform (which is 1:1 scale, no rotation by default),
 * then tilt it slightly so it reads clearly from above.
 */
@OnlyIn(Dist.CLIENT)
public class SmithingAnvilBlockEntityRenderer implements BlockEntityRenderer<SmithingAnvilBlockEntity> {

    public SmithingAnvilBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(SmithingAnvilBlockEntity be, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource,
                       int packedLight, int packedOverlay) {

        ItemStack input = be.getInputItem();
        if (input.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        ItemRenderer itemRenderer = mc.getItemRenderer();

        poseStack.pushPose();

        // The anvil top surface is at roughly y=1.0 (block-relative).
        // We center X/Z and sit just above the top face.
        poseStack.translate(0.5, 1.05, 0.5);

        // Lay the item flat (face-up) — rotate 90° around X so it lies horizontal
        poseStack.mulPose(Axis.XP.rotationDegrees(90f));

        // Shrink slightly so it fits nicely on the anvil top
        poseStack.scale(0.55f, 0.55f, 0.55f);

        BakedModel model = itemRenderer.getModel(input, be.getLevel(), null, 0);
        itemRenderer.render(input, ItemDisplayContext.FIXED, false,
                poseStack, bufferSource, packedLight, packedOverlay, model);

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(SmithingAnvilBlockEntity be) {
        return false;
    }
}
