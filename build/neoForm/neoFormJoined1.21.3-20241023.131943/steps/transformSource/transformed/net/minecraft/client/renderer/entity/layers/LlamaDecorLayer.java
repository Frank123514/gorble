package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LlamaRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.EquipmentModels;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LlamaDecorLayer extends RenderLayer<LlamaRenderState, LlamaModel> {
    private final LlamaModel adultModel;
    private final LlamaModel babyModel;
    private final EquipmentLayerRenderer equipmentRenderer;

    public LlamaDecorLayer(RenderLayerParent<LlamaRenderState, LlamaModel> renderer, EntityModelSet models, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer);
        this.equipmentRenderer = equipmentRenderer;
        this.adultModel = new LlamaModel(models.bakeLayer(ModelLayers.LLAMA_DECOR));
        this.babyModel = new LlamaModel(models.bakeLayer(ModelLayers.LLAMA_BABY_DECOR));
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, LlamaRenderState renderState, float yRot, float xRot) {
        ItemStack itemstack = renderState.bodyItem;
        Equippable equippable = itemstack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && equippable.model().isPresent()) {
            this.renderEquipment(poseStack, bufferSource, renderState, itemstack, equippable.model().get(), packedLight);
        } else if (renderState.isTraderLlama) {
            this.renderEquipment(poseStack, bufferSource, renderState, ItemStack.EMPTY, EquipmentModels.TRADER_LLAMA, packedLight);
        }
    }

    private void renderEquipment(
        PoseStack poseStack, MultiBufferSource bufferSource, LlamaRenderState renderState, ItemStack item, ResourceLocation equipmentModel, int packedLight
    ) {
        LlamaModel llamamodel = renderState.isBaby ? this.babyModel : this.adultModel;
        llamamodel.setupAnim(renderState);
        this.equipmentRenderer.renderLayers(EquipmentModel.LayerType.LLAMA_BODY, equipmentModel, llamamodel, item, poseStack, bufferSource, packedLight);
    }
}
