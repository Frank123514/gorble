package net.got.entity.client.horse;

import net.got.GotMod;
import net.got.entity.horse.GotHorseEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

/**
 * GeckoLib model for {@link GotHorseEntity}.
 *
 * <p>Reads resources from:
 * <ul>
 *   <li>Geo:       {@code assets/got/geo/entity/horse/got_horse.geo.json}</li>
 *   <li>Texture:   {@code assets/got/textures/entity/got_horse.png}</li>
 *   <li>Animation: {@code assets/got/animations/entity/horse/got_horse.animation.json}</li>
 * </ul>
 */
public class GotHorseModel extends GeoModel<GotHorseEntity> {

    private static final ResourceLocation GEO =
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "geo/entity/horse/got_horse.geo.json");

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "textures/entity/got_horse.png");

    private static final ResourceLocation ANIMATION =
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "animations/entity/horse/got_horse.animation.json");

    @Override
    public ResourceLocation getModelResource(GotHorseEntity entity, GeoRenderer<GotHorseEntity> renderer) {
        return GEO;
    }

    @Override
    public ResourceLocation getTextureResource(GotHorseEntity entity, GeoRenderer<GotHorseEntity> renderer) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(GotHorseEntity entity) {
        return ANIMATION;
    }
}
