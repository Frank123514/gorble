package net.got.client.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

/**
 * Supplies the custom baked head model for one helm type at render time.
 * One instance per helm, registered against its item via
 * RegisterClientExtensionsEvent in ClientSetup.
 *
 * Signature verified against NeoForge 1.21.10/1.21.11's actual
 * IClientItemExtensions: getHumanoidArmorModel now takes
 * (ItemStack, EquipmentClientInfo.LayerType, Model) and returns Model --
 * no more LivingEntity/EquipmentSlot/HumanoidModel<?> like older versions.
 */
public class GotHelmClientExtensions implements IClientItemExtensions {

    private final ModelLayerLocation layerLocation;
    private GotHelmModel cachedModel;

    public GotHelmClientExtensions(ModelLayerLocation layerLocation) {
        this.layerLocation = layerLocation;
    }

    @Override
    public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
        if (layerType != EquipmentClientInfo.LayerType.HUMANOID) {
            return original;
        }
        if (cachedModel == null) {
            ModelPart root = Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
            cachedModel = new GotHelmModel(root);
        }
        return cachedModel;
    }
}