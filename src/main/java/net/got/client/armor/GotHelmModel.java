package net.got.client.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * A minimal HumanoidModel whose head renders a helm's own Blockbench item
 * model (elements + a "head" display entry, same file used for gui/hand/
 * ground) instead of hand-coded ModelPart cubes.
 *
 * HumanoidArmorLayer copies the parent body model's head rotation/position
 * onto {@link #head} before calling renderToBuffer, exactly like it does
 * for vanilla armor -- this override reads that transform back off the
 * head part and hands the equipped stack to the standard item model
 * pipeline (ItemModelResolver / ItemStackRenderState) instead of drawing
 * cubes. The stack's own "display": {"head": ...} entry controls the fit;
 * tune it in Blockbench, no recompile needed.
 */
public class GotHelmModel extends HumanoidModel<HumanoidRenderState> {

    private final ItemStackRenderState renderState = new ItemStackRenderState();
    private ItemStack stack = ItemStack.EMPTY;

    public GotHelmModel(ModelPart root) {
        super(root);
        this.hat.visible = false;
        this.body.visible = false;
        this.rightArm.visible = false;
        this.leftArm.visible = false;
        this.rightLeg.visible = false;
        this.leftLeg.visible = false;
        this.head.visible = true;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        if (stack.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        this.head.translateAndRotate(poseStack);

        Minecraft.getInstance().getItemModelResolver()
                .updateForTopItem(renderState, stack, ItemDisplayContext.HEAD, false, null, null, 0);

        // Single-layer helm models only need one render type; route everything
        // through the VertexConsumer HumanoidArmorLayer already gave us.
        MultiBufferSource bufferSource = renderType -> vertexConsumer;
        renderState.render(poseStack, bufferSource, packedLight, packedOverlay);

        poseStack.popPose();
    }
}
