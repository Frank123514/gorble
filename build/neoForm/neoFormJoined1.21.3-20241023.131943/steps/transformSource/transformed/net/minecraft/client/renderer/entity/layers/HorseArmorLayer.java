package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HorseArmorLayer extends RenderLayer<HorseRenderState, HorseModel> {
    private final HorseModel adultModel;
    private final HorseModel babyModel;
    private final EquipmentLayerRenderer equipmentRenderer;

    public HorseArmorLayer(RenderLayerParent<HorseRenderState, HorseModel> renderer, EntityModelSet entityModels, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer);
        this.equipmentRenderer = equipmentRenderer;
        this.adultModel = new HorseModel(entityModels.bakeLayer(ModelLayers.HORSE_ARMOR));
        this.babyModel = new HorseModel(entityModels.bakeLayer(ModelLayers.HORSE_BABY_ARMOR));
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, HorseRenderState renderState, float yRot, float xRot) {
        ItemStack itemstack = renderState.bodyArmorItem;
        Equippable equippable = itemstack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && !equippable.model().isEmpty()) {
            HorseModel horsemodel = renderState.isBaby ? this.babyModel : this.adultModel;
            ResourceLocation resourcelocation = equippable.model().get();
            horsemodel.setupAnim(renderState);
            this.equipmentRenderer.renderLayers(EquipmentModel.LayerType.HORSE_BODY, resourcelocation, horsemodel, itemstack, poseStack, bufferSource, packedLight);
        }
    }
}
