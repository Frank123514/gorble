package net.got.client.armor;

import java.util.function.Function;

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
 * Each helm model is now its own bone-only EntityModel (see
 * HalfhelmModel, BascinetModel, SkullCapModel, KettleHelmModel, and co.) instead of the old HumanoidModel-wrapped GotHelmModel, so this
 * takes a constructor reference (e.g. HalfhelmModel::new) rather than
 * assuming a fixed model type.
 */
public class GotHelmClientExtensions implements IClientItemExtensions {

    private final ModelLayerLocation layerLocation;
    private final Function<ModelPart, ? extends Model> modelFactory;
    private Model cachedModel;

    public GotHelmClientExtensions(ModelLayerLocation layerLocation, Function<ModelPart, ? extends Model> modelFactory) {
        this.layerLocation = layerLocation;
        this.modelFactory = modelFactory;
    }

    @Override
    public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
        if (layerType != EquipmentClientInfo.LayerType.HUMANOID) {
            return original;
        }
        if (cachedModel == null) {
            ModelPart root = Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
            cachedModel = modelFactory.apply(root);
        }
        return cachedModel;
    }
}