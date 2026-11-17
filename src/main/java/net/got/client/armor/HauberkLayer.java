package net.got.client.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.GotMod;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemStack;

/**
 * Draws the hauberk worn in the extra hauberk slot. It is bigger than the gambeson's model and smaller than the
 * brigandine's and the plate armor's, so it sits between them. The worn stack reaches the layer through
 * render-state data.
 */
public class HauberkLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {

    public static final ContextKey<ItemStack> WORN_KEY =
            new ContextKey<>(Identifier.fromNamespaceAndPath(GotMod.MODID, "worn_hauberk"));

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "textures/entity/equipment/humanoid/hauberk.png");

    private final GotBodyArmorModel model;

    public HauberkLayer(RenderLayerParent<S, M> parent, GotBodyArmorModel model) {
        super(parent);
        this.model = model;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, S state,
                       float yRot, float xRot) {
        ItemStack worn = state.getRenderData(WORN_KEY);
        if (worn == null || worn.isEmpty() || state.isInvisible) return;

        // 7th argument is the entity OUTLINE colour (not a tint); non-zero draws a solid silhouette. 0 = none.
        collector.order(1).submitModel(model, state, poseStack, model.renderType(TEXTURE),
                light, OverlayTexture.NO_OVERLAY, 0, null);
    }
}
