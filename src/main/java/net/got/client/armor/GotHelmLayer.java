package net.got.client.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Renders a worn {@link HelmItem}'s own Blockbench item model on the
 * head.
 * <p>
 * Since Minecraft 1.21.9, armor rendering is submission-based: Models are
 * walked part-by-part and their cubes are submitted with one shared
 * equipment texture, with no remaining hook to substitute an entirely
 * different render for a single worn slot. So instead of hijacking the
 * vanilla armor layer (see {@link GotHelmModel}, which is now just an
 * empty placeholder that makes the vanilla layer submit nothing), this is
 * a standalone layer added alongside it that resolves the equipped head
 * item through the normal item-model pipeline and submits it positioned
 * at the parent model's head.
 */
public class GotHelmLayer<S extends HumanoidRenderState, M extends HumanoidModel<S> & HeadedModel> extends RenderLayer<S, M> {

    private final ItemModelResolver itemModelResolver;
    private final ItemStackRenderState itemRenderState = new ItemStackRenderState();

    public GotHelmLayer(RenderLayerParent<S, M> parent, ItemModelResolver itemModelResolver) {
        super(parent);
        this.itemModelResolver = itemModelResolver;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, S renderState, float limbSwing, float limbSwingAmount) {
        ItemStack headStack = renderState.headEquipment;
        if (headStack.isEmpty() || !(headStack.getItem() instanceof HelmItem)) {
            return;
        }

        poseStack.pushPose();
        getParentModel().translateToHead(poseStack);

        this.itemRenderState.clear();
        this.itemModelResolver.updateForTopItem(this.itemRenderState, headStack, ItemDisplayContext.HEAD, null, null, 0);
        this.itemRenderState.submit(poseStack, collector, packedLight, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }
}