package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.EquipmentModelSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EquipmentLayerRenderer {
    private static final int NO_LAYER_COLOR = 0;
    private final EquipmentModelSet equipmentModels;
    private final Function<EquipmentLayerRenderer.LayerTextureKey, ResourceLocation> layerTextureLookup;
    private final Function<EquipmentLayerRenderer.TrimSpriteKey, TextureAtlasSprite> trimSpriteLookup;

    public EquipmentLayerRenderer(EquipmentModelSet equipmentModels, TextureAtlas trimsAtlas) {
        this.equipmentModels = equipmentModels;
        this.layerTextureLookup = Util.memoize(p_371648_ -> p_371648_.layer.getTextureLocation(p_371648_.layerType));
        this.trimSpriteLookup = Util.memoize(p_371220_ -> {
            ResourceLocation resourcelocation = p_371220_.trim.getTexture(p_371220_.layerType, p_371220_.equipmentModelId);
            return trimsAtlas.getSprite(resourcelocation);
        });
    }

    public void renderLayers(
        EquipmentModel.LayerType layerType,
        ResourceLocation equipmentModel,
        Model armorModel,
        ItemStack item,
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        int packedLight
    ) {
        this.renderLayers(layerType, equipmentModel, armorModel, item, poseStack, bufferSource, packedLight, null);
    }

    public void renderLayers(
        EquipmentModel.LayerType layerType,
        ResourceLocation equipmentModel,
        Model armorModel,
        ItemStack item,
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        int packedLight,
        @Nullable ResourceLocation playerTexture
    ) {
        armorModel = getArmorModelHook(item, layerType, armorModel);
        List<EquipmentModel.Layer> list = this.equipmentModels.get(equipmentModel).getLayers(layerType);
        if (!list.isEmpty()) {
            net.neoforged.neoforge.client.extensions.common.IClientItemExtensions extensions = net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(item);
            int i = extensions.getDefaultDyeColor(item);
            boolean flag = item.hasFoil();

            int idx = 0;
            for (EquipmentModel.Layer equipmentmodel$layer : list) {
                int j = extensions.getArmorLayerTintColor(item, equipmentmodel$layer, idx, i);
                if (j != 0) {
                    ResourceLocation resourcelocation = equipmentmodel$layer.usePlayerTexture() && playerTexture != null
                        ? playerTexture
                        : this.layerTextureLookup.apply(new EquipmentLayerRenderer.LayerTextureKey(layerType, equipmentmodel$layer));
                    resourcelocation = net.neoforged.neoforge.client.ClientHooks.getArmorTexture(item, layerType, equipmentmodel$layer, resourcelocation);
                    VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.armorCutoutNoCull(resourcelocation), flag);
                    armorModel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, j);
                    flag = false;
                }
                idx++;
            }

            ArmorTrim armortrim = item.get(DataComponents.TRIM);
            if (armortrim != null) {
                TextureAtlasSprite textureatlassprite = this.trimSpriteLookup.apply(new EquipmentLayerRenderer.TrimSpriteKey(armortrim, layerType, equipmentModel));
                VertexConsumer vertexconsumer1 = textureatlassprite.wrap(bufferSource.getBuffer(Sheets.armorTrimsSheet(armortrim.pattern().value().decal())));
                armorModel.renderToBuffer(poseStack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }
    }

    public static int getColorForLayer(EquipmentModel.Layer layer, int color) {
        Optional<EquipmentModel.Dyeable> optional = layer.dyeable();
        if (optional.isPresent()) {
            int i = optional.get().colorWhenUndyed().map(ARGB::opaque).orElse(0);
            return color != 0 ? color : i;
        } else {
            return -1;
        }
    }

    /**
     * Hook to allow item-sensitive armor model. for HumanoidArmorLayer.
     */
    protected net.minecraft.client.model.Model getArmorModelHook(ItemStack itemStack, EquipmentModel.LayerType layerType, Model model) {
        return net.neoforged.neoforge.client.ClientHooks.getArmorModel(itemStack, layerType, model);
    }

    @OnlyIn(Dist.CLIENT)
    static record LayerTextureKey(EquipmentModel.LayerType layerType, EquipmentModel.Layer layer) {
    }

    @OnlyIn(Dist.CLIENT)
    static record TrimSpriteKey(ArmorTrim trim, EquipmentModel.LayerType layerType, ResourceLocation equipmentModelId) {
    }
}
