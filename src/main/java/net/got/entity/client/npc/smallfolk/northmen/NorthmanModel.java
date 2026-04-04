package net.got.entity.client.npc.smallfolk.northmen;

import net.got.GotMod;
import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.northmen.NorthmanEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

/**
 * GeckoLib model for the Northman civilian NPC.
 *
 * <p>Male NPCs use the standard {@code humanoid.geo.json} skeleton.
 * Female NPCs automatically use the smaller {@code humanoid_female.geo.json}
 * skeleton — no separate model class required. GeckoLib calls
 * {@link #getModelResource} per entity, so each gets the right geometry.
 * Both skeletons share the same animation file; bone names must match.
 *
 * <ul>
 *   <li>Male geo:   {@code assets/got/geo/entity/npc/humanoid.geo.json}</li>
 *   <li>Female geo: {@code assets/got/geo/entity/npc/humanoid_female.geo.json}</li>
 *   <li>Animation:  {@code assets/got/animations/entity/northmen/northman.animation.json}</li>
 * </ul>
 */
public class NorthmanModel extends DefaultedEntityGeoModel<NorthmanEntity> {

    private static final ResourceLocation FEMALE_GEO =
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID,
                    "geo/entity/npc/humanoid_female.geo.json");

    public NorthmanModel() {
        super(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "npc/humanoid"), true);
    }

    @Override
    public ResourceLocation getModelResource(NorthmanEntity entity, GeoRenderer<NorthmanEntity> renderer) {
        return entity.getGender() == NpcGender.FEMALE
                ? FEMALE_GEO
                : super.getModelResource(entity, renderer);
    }

    @Override
    public ResourceLocation getAnimationResource(NorthmanEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(GotMod.MODID,
                "animations/entity/northmen/northman.animation.json");
    }
}