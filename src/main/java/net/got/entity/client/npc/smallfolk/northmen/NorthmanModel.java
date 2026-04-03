package net.got.entity.client.npc.smallfolk.northmen;

import net.got.GotMod;
import net.got.entity.npc.smallfolk.northmen.NorthmanEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

/**
 * GeckoLib model for the Northman civilian NPC.
 *
 * <p>Asset paths that Blockbench / your resource-pack must provide:
 * <ul>
 *   <li>Geometry:  {@code assets/got/geo/entity/northmen/northman.geo.json}</li>
 *   <li>Animation: {@code assets/got/animations/entity/northmen/northman.animation.json}</li>
 * </ul>
 * Textures are resolved per-variant in {@link NorthmanRenderer}.
 */
public class NorthmanModel extends DefaultedEntityGeoModel<NorthmanEntity> {

    public NorthmanModel() {
        super(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "northmen/northman"));
    }
}