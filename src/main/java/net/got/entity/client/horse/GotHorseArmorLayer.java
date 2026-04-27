package net.got.entity.client.horse;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.got.entity.horse.GotHorseEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

/**
 * Renders a horse-armour texture overlay when the gothorse has armour equipped
 * in its {@link EquipmentSlot#BODY} slot.
 *
 * Leather → leather.png. Iron / gold / diamond → iron.png.
 */
public class GotHorseArmorLayer extends GeoRenderLayer<GotHorseEntity> {

    private static final ResourceLocation ARMOR_IRON =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/armor/iron.png");
    private static final ResourceLocation ARMOR_LEATHER =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/armor/leather.png");

    public GotHorseArmorLayer(GeoRenderer<GotHorseEntity> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, GotHorseEntity animatable, BakedGeoModel bakedModel,
                       RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
                       float partialTick, int packedLight, int packedOverlay, int colour) {
        ItemStack armorStack = animatable.getItemBySlot(EquipmentSlot.BODY);
        if (armorStack.isEmpty()) return;

        ResourceLocation armorTex = resolveArmorTexture(armorStack.getItem());
        if (armorTex == null) return;

        RenderType armorType = RenderType.entityCutoutNoCull(armorTex);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable,
                armorType, bufferSource.getBuffer(armorType),
                partialTick, packedLight, packedOverlay, -1);
    }

    private static ResourceLocation resolveArmorTexture(Item item) {
        if (item == Items.LEATHER_HORSE_ARMOR)  return ARMOR_LEATHER;
        if (item == Items.IRON_HORSE_ARMOR
         || item == Items.GOLDEN_HORSE_ARMOR
         || item == Items.DIAMOND_HORSE_ARMOR)  return ARMOR_IRON;
        return null;
    }
}
