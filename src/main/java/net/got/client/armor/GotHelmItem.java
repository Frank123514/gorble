package net.got.client.armor;

import net.minecraft.world.item.Item;

/**
 * Marker helmet item that renders via its own Blockbench item model
 * (models/item/*.json with a "head" display entry) when worn, instead of
 * vanilla's flat re-skinned head box. Client render hookup happens in
 * {@link GotHelmClientExtensions}, registered per-item via
 * RegisterClientExtensionsEvent in ClientSetup.
 */
public class GotHelmItem extends Item {

    public GotHelmItem(Properties properties) {
        super(properties);
    }
}
