package net.got.client.armor;

import java.util.function.Function;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.slf4j.Logger;

/**
 * Supplies the custom baked head model for one helm type at render time.
 * One instance per helm, registered against its item via
 * RegisterClientExtensionsEvent in ClientSetup.
 *
 * Each helm model is now its own bone-only EntityModel (see
 * HalfhelmModel, BascinetModel, SkullCapModel, KettleHelmModel, and co.) instead of the old HumanoidModel-wrapped GotHelmModel, so this
 * takes a constructor reference (e.g. HalfhelmModel::new) rather than
 * assuming a fixed model type.
 *
 * The cached model is keyed to the EntityModelSet instance it was baked
 * from. Resource-pack / F3+T reloads swap Minecraft's EntityModelSet out
 * for a new one; without this check the old cache would keep being
 * returned (harmless) or -- if the very first bake for this item ever
 * happened to race a reload/registration timing edge case -- a bad bake
 * would get cached permanently and crash every subsequent render. Instead
 * we re-bake whenever the model set changes, and if a bake ever comes
 * back missing the parts HumanoidModel needs, we log it and fall back to
 * vanilla's model for that frame rather than crashing the renderer.
 */
public class GotHelmClientExtensions implements IClientItemExtensions {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final ModelLayerLocation layerLocation;
    private final Function<ModelPart, ? extends Model> modelFactory;
    private EntityModelSet cachedModelSet;
    private Model cachedModel;
    private boolean loggedFailure;

    public GotHelmClientExtensions(ModelLayerLocation layerLocation, Function<ModelPart, ? extends Model> modelFactory) {
        this.layerLocation = layerLocation;
        this.modelFactory = modelFactory;
    }

    @Override
    public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
        if (layerType != EquipmentClientInfo.LayerType.HUMANOID) {
            return original;
        }

        EntityModelSet currentModelSet = Minecraft.getInstance().getEntityModels();
        if (cachedModel == null || cachedModelSet != currentModelSet) {
            try {
                ModelPart root = currentModelSet.bakeLayer(layerLocation);
                cachedModel = modelFactory.apply(root);
                cachedModelSet = currentModelSet;
            } catch (RuntimeException e) {
                if (!loggedFailure) {
                    LOGGER.error("Failed to bake helm model for layer {}, falling back to vanilla armor model", layerLocation, e);
                    loggedFailure = true;
                }
                // Don't cache the failure -- try again next render instead of
                // permanently wedging this item on the broken vanilla fallback.
                return original;
            }
        }
        return cachedModel;
    }
}