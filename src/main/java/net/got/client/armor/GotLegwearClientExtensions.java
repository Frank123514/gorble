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
 * Supplies the custom baked model for a leg-slot or feet-slot armor item (wool breeches, leather boots).
 * Same idea as {@link GotHelmClientExtensions}, but it answers for one chosen equipment layer type:
 * HUMANOID_LEGGINGS for the leggings slot, HUMANOID for the boots slot. Any other layer type falls
 * through to vanilla's model. The cached model is re-baked whenever Minecraft swaps its EntityModelSet
 * (resource reload / F3+T).
 */
public class GotLegwearClientExtensions implements IClientItemExtensions {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final ModelLayerLocation layerLocation;
    private final EquipmentClientInfo.LayerType layerType;
    private final Function<ModelPart, ? extends Model> modelFactory;
    private EntityModelSet cachedModelSet;
    private Model cachedModel;
    private boolean loggedFailure;

    public GotLegwearClientExtensions(ModelLayerLocation layerLocation, EquipmentClientInfo.LayerType layerType) {
        this(layerLocation, layerType, GotLegArmorModel::new);
    }

    public GotLegwearClientExtensions(ModelLayerLocation layerLocation, EquipmentClientInfo.LayerType layerType,
                                      Function<ModelPart, ? extends Model> modelFactory) {
        this.layerLocation = layerLocation;
        this.layerType = layerType;
        this.modelFactory = modelFactory;
    }

    @Override
    public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType requestedLayer, Model original) {
        if (requestedLayer != this.layerType) {
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
                    LOGGER.error("Failed to bake leg armor model for layer {}, falling back to vanilla armor model", layerLocation, e);
                    loggedFailure = true;
                }
                return original;   // don't cache the failure; retry next render
            }
        }
        return cachedModel;
    }
}
