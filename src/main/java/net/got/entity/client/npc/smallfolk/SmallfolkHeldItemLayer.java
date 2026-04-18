package net.got.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Renders held items (weapons, tools, shields) in the hands of Smallfolk NPCs.
 *
 * <p>Positions items at the palm anchors ({@code rightItem} / {@code leftItem})
 * which are zero-volume children of the arm parts. We reconstruct the full
 * parent-chain transform manually (arm → anchor) so the item correctly inherits
 * the walk cycle, attack swing, riding pose, etc.
 *
 * <p>Both {@link GotSmallfolkModel} and {@link GotSmallfolkFemaleModel}
 * implement {@link SmallfolkModelParts}, so this layer works for either.
 */
public class SmallfolkHeldItemLayer
        extends RenderLayer<SmallfolkRenderState, EntityModel<SmallfolkRenderState>> {

    private final ItemRenderer itemRenderer;

    public SmallfolkHeldItemLayer(
            RenderLayerParent<SmallfolkRenderState, EntityModel<SmallfolkRenderState>> parent,
            ItemRenderer itemRenderer) {
        super(parent);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       SmallfolkRenderState state, float yRot, float xRot) {

        EntityModel<SmallfolkRenderState> raw = this.getParentModel();
        if (!(raw instanceof SmallfolkModelParts parts)) return;

        // ── Main hand (right) ─────────────────────────────────────────────────
        if (!state.mainHandItem.isEmpty()) {
            renderAtAnchor(poseStack, buffer, packedLight,
                    state.mainHandItem,
                    parts.sfRightArm(),
                    parts.sfRightItemAnchor(),
                    ItemDisplayContext.THIRD_PERSON_RIGHT_HAND);
        }

        // ── Off hand (left) ───────────────────────────────────────────────────
        if (!state.offHandItem.isEmpty()) {
            renderAtAnchor(poseStack, buffer, packedLight,
                    state.offHandItem,
                    parts.sfLeftArm(),
                    parts.sfLeftItemAnchor(),
                    ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
        }
    }

    /**
     * Walks the arm → anchor chain manually so the item inherits all
     * accumulated parent transforms (walk cycle, attack swing, riding pose).
     *
     * <p>The model's render pass has already returned by the time render layers
     * run, so the poseStack is back at the entity root. We must re-apply the
     * arm and anchor transforms ourselves.
     */
    private void renderAtAnchor(PoseStack poseStack, MultiBufferSource buffer,
                                int packedLight, ItemStack stack,
                                ModelPart arm, ModelPart anchor,
                                ItemDisplayContext ctx) {
        poseStack.pushPose();

        // Re-apply the arm part's local transform (rotation + pivot offset).
        arm.translateAndRotate(poseStack);
        // Then apply the anchor's local offset to reach the palm position.
        anchor.translateAndRotate(poseStack);

        // Flip from model-space into item-render-space and scale to hand size.
        poseStack.scale(0.5f, -0.5f, -0.5f);

        itemRenderer.renderStatic(
                stack,
                ctx,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                /* level = */ null,
                /* seed = */ 0);

        poseStack.popPose();
    }
}