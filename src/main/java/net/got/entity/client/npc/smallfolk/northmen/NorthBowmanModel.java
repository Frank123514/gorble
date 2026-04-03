package net.got.entity.client.npc.smallfolk.northmen;

import net.got.GotMod;
import net.got.entity.npc.smallfolk.northmen.NorthBowmanEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

/**
 * GeckoLib model for the North Bowman NPC.
 *
 * <p>Asset paths that Blockbench / your resource-pack must provide:
 * <ul>
 *   <li>Geometry:  {@code assets/got/geo/entity/northmen/north_bowman.geo.json}</li>
 *   <li>Animation: {@code assets/got/animations/entity/northmen/north_bowman.animation.json}</li>
 * </ul>
 * Textures are resolved per-variant in {@link NorthBowmanRenderer}.
 */
public class NorthBowmanModel extends DefaultedEntityGeoModel<NorthBowmanEntity> {

    public NorthBowmanModel() {
        super(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "northmen/north_bowman"));
    }
}