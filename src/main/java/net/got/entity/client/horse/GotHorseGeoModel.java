package net.got.entity.client.horse;

import net.got.entity.horse.GotHorseEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

/**
 * GeckoLib model for {@link GotHorseEntity}.
 *
 * <p>Points at:
 * <ul>
 *   <li>Geometry : {@code assets/got/geo/got_horse.geo.json}</li>
 *   <li>Animations: {@code assets/got/animations/got_horse.animation.json}</li>
 *   <li>Texture  : {@code assets/got/textures/entity/got_horse.png}</li>
 * </ul>
 */
public class GotHorseGeoModel extends GeoModel<GotHorseEntity> {

    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath("got", "geo/got_horse.geo.json");
    private static final ResourceLocation ANIMATIONS =
            ResourceLocation.fromNamespaceAndPath("got", "animations/got_horse.animation.json");
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/got_horse.png");

    @Override
    public ResourceLocation getModelResource(GotHorseEntity animatable, GeoRenderer<GotHorseEntity> renderer) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GotHorseEntity animatable, GeoRenderer<GotHorseEntity> renderer) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(GotHorseEntity animatable) {
        return ANIMATIONS;
    }
}
