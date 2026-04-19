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
                    parts,
                    parts.sfRightArm(),
                    parts.sfRightItemAnchor(),
                    ItemDisplayContext.THIRD_PERSON_RIGHT_HAND);
        }

        // ── Off hand (left) ───────────────────────────────────────────────────
        if (!state.offHandItem.isEmpty()) {
            renderAtAnchor(poseStack, buffer, packedLight,
                    state.offHandItem,
                    parts,
                    parts.sfLeftArm(),
                    parts.sfLeftItemAnchor(),
                    ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
        }
    }

    /**
     * Walks the full parent chain so the item correctly inherits every
     * accumulated transform (idle bob, walk cycle, attack swing, riding pose).
     *
     * <p>The model hierarchy above the arm is:
     * <pre>mesh-root -&gt; "root"(0,24,0) -&gt; "waist"(0,-12,0) -&gt; body -&gt; arm -&gt; anchor</pre>
     * The render-layer poseStack is at mesh-root (entity-root) level. "root" and
     * "waist" are never animated, so their combined y-offset (24-12=12 pixels =
     * 0.75 blocks) is applied as a single static translate. "body" carries idle
     * and attack animation and must go through translateAndRotate. Then arm and
     * anchor complete the chain.
     */
    private void renderAtAnchor(PoseStack poseStack, MultiBufferSource buffer,
                                int packedLight, ItemStack stack,
                                SmallfolkModelParts parts, ModelPart arm, ModelPart anchor,
                                ItemDisplayContext ctx) {
        poseStack.pushPose();

        // Static offset: "root"(y=+24) + "waist"(y=-12) = net y=+12 pixels = 0.75 blocks.
        poseStack.translate(0.0f, 12.0f / 16.0f, 0.0f);
        // Body may carry idle/attack animation; must use translateAndRotate.
        parts.sfBody().translateAndRotate(poseStack);
        // Now at the body pivot -- apply arm then palm anchor.
        arm.translateAndRotate(poseStack);
        anchor.translateAndRotate(poseStack);

        // Scale to hand size. No axis negations here: LivingEntityRenderer already applies
        // scale(-1,-1,1) which inverts x and y for all entity rendering. Item JSON display
        // contexts (THIRD_PERSON_RIGHT/LEFT_HAND) are calibrated for that coordinate system.
        // Adding our own -y/-z negations double-flips those axes, reversing the blade direction
        // so it points back through the arm instead of outward from the hand.
        poseStack.scale(0.4375f, 0.4375f, 0.4375f);

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