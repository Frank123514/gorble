package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WingsLayer<S extends HumanoidRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
    private final ElytraModel elytraModel;
    private final ElytraModel elytraBabyModel;
    private final EquipmentLayerRenderer equipmentRenderer;

    public WingsLayer(RenderLayerParent<S, M> renderer, EntityModelSet models, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer);
        this.elytraModel = new ElytraModel(models.bakeLayer(ModelLayers.ELYTRA));
        this.elytraBabyModel = new ElytraModel(models.bakeLayer(ModelLayers.ELYTRA_BABY));
        this.equipmentRenderer = equipmentRenderer;
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot) {
        ItemStack itemstack = renderState.chestItem;
        Equippable equippable = itemstack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && !equippable.model().isEmpty()) {
            ResourceLocation resourcelocation = getPlayerElytraTexture(renderState);
            ElytraModel elytramodel = renderState.isBaby ? this.elytraBabyModel : this.elytraModel;
            ResourceLocation resourcelocation1 = equippable.model().get();
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.0F, 0.125F);
            elytramodel.setupAnim(renderState);
            this.equipmentRenderer
                .renderLayers(EquipmentModel.LayerType.WINGS, resourcelocation1, elytramodel, itemstack, poseStack, bufferSource, packedLight, resourcelocation);
            poseStack.popPose();
        }
    }

    @Nullable
    private static ResourceLocation getPlayerElytraTexture(HumanoidRenderState renderState) {
        if (renderState instanceof PlayerRenderState playerrenderstate) {
            PlayerSkin playerskin = playerrenderstate.skin;
            if (playerskin.elytraTexture() != null) {
                return playerskin.elytraTexture();
            }

            if (playerskin.capeTexture() != null && playerrenderstate.showCape) {
                return playerskin.capeTexture();
            }
        }

        return null;
    }
}
