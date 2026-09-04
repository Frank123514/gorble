package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.item.Item;

/**
 * A helmet item that renders with a fully custom head model instead of
 * vanilla's flat re-skinned head box. Used for every helm type from the
 * AWOIAF Armament#Headwear pass (skull cap, conical cap, kettle helm, padded
 * coif, mail coif, halfhelm, bascinet, and both greathelm variants).
 *
 * The actual client-side render hookup happens in {@link GotHelmClientExtensions},
 * registered per-item via RegisterClientExtensionsEvent in ClientSetup -- this
 * class just carries the ModelLayerLocation that identifies which baked
 * geometry belongs to this specific helm.
 */
public class GotHelmItem extends Item {

    private final ModelLayerLocation layerLocation;

    public GotHelmItem(Properties properties, ModelLayerLocation layerLocation) {
        super(properties);
        this.layerLocation = layerLocation;
    }

    public ModelLayerLocation getLayerLocation() {
        return layerLocation;
    }
}