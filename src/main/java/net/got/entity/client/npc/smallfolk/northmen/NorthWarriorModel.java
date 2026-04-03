package net.got.entity.client.npc.smallfolk.northmen;

import net.got.GotMod;
import net.got.entity.npc.smallfolk.northmen.NorthWarriorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

/**
 * GeckoLib model for the North Warrior NPC.
 *
 * <p>Asset paths that Blockbench / your resource-pack must provide:
 * <ul>
 *   <li>Geometry:  {@code assets/got/geo/entity/northmen/north_warrior.geo.json}</li>
 *   <li>Animation: {@code assets/got/animations/entity/northmen/north_warrior.animation.json}</li>
 * </ul>
 * Textures are resolved per-variant in {@link NorthWarriorRenderer}.
 */
public class NorthWarriorModel extends DefaultedEntityGeoModel<NorthWarriorEntity> {

    public NorthWarriorModel() {
        super(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "northmen/north_warrior"));
    }
}