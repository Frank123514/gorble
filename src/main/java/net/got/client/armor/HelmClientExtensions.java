package net.got.client.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

/**
 * Supplies the shared head-anchor model at render time and hands it the
 * itemstack being worn, so {@link GotHelmModel} renders the helm's own
 * Blockbench item model instead of vanilla's flat re-skinned head box.
 * One instance per helm item, registered via RegisterClientExtensionsEvent
 * in ClientSetup.
 */
public class HelmClientExtensions implements IClientItemExtensions {

    private GotHelmModel cachedModel;

    @Override
    public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
        if (layerType != EquipmentClientInfo.LayerType.HUMANOID) {
            return original;
        }
        if (cachedModel == null) {
            ModelPart root = Minecraft.getInstance().getEntityModels().bakeLayer(GotHelmModels.HEAD_ANCHOR);
            cachedModel = new GotHelmModel(root);
        }
        return cachedModel;
    }
}