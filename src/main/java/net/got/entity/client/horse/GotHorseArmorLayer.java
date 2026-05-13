package net.got.entity.client.horse;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * Renders a horse-armour texture overlay when the horse has armour equipped
 * in its {@link net.minecraft.world.entity.EquipmentSlot#BODY} slot.
 *
 * <p>Uses a second instance of {@link GotHorseModel} baked from
 * {@link GotModelLayers#GOT_HORSE}.
 */
public class GotHorseArmorLayer
        extends RenderLayer<GotHorseRenderState, GotHorseModel> {

    private static final ResourceLocation ARMOR_IRON =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/armor/iron.png");
    private static final ResourceLocation ARMOR_LEATHER =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/armor/leather.png");

    private final GotHorseModel overlayModel;

    public GotHorseArmorLayer(RenderLayerParent<GotHorseRenderState, GotHorseModel> parent,
                              EntityModelSet models) {
        super(parent);
        this.overlayModel = new GotHorseModel(models.bakeLayer(GotModelLayers.GOT_HORSE));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       GotHorseRenderState state, float yRot, float xRot) {
        if (state.bodyArmorItem == null) return;
        ResourceLocation armorTex = resolveArmorTexture(state.bodyArmorItem);
        if (armorTex == null) return;

        // Copy pose from parent model to overlay model
        this.getParentModel().copyState(this.overlayModel);

        this.overlayModel.renderToBuffer(poseStack,
                buffer.getBuffer(RenderType.entityCutoutNoCull(armorTex)),
                packedLight, OverlayTexture.NO_OVERLAY);
    }

    private static ResourceLocation resolveArmorTexture(Item item) {
        if (item == Items.LEATHER_HORSE_ARMOR) return ARMOR_LEATHER;
        if (item == Items.IRON_HORSE_ARMOR
                || item == Items.GOLDEN_HORSE_ARMOR
                || item == Items.DIAMOND_HORSE_ARMOR) return ARMOR_IRON;
        return null;
    }
}