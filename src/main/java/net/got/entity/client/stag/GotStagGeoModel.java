package net.got.entity.client.stag;

import net.got.entity.stag.GotStagEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

/**
 * GeckoLib model for {@link GotStagEntity}.
 *
 * <p>Points at:
 * <ul>
 *   <li>Geometry : {@code assets/got/geo/got_stag.geo.json}</li>
 *   <li>Animations: {@code assets/got/animations/got_stag.animation.json}</li>
 *   <li>Texture  : {@code assets/got/textures/entity/got_stag.png}</li>
 * </ul>
 */
public class GotStagGeoModel extends GeoModel<GotStagEntity> {

    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath("got", "geo/got_stag.geo.json");
    private static final ResourceLocation ANIMATIONS =
            ResourceLocation.fromNamespaceAndPath("got", "animations/got_stag.animation.json");
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/got_stag.png");

    @Override
    public ResourceLocation getModelResource(GotStagEntity animatable, GeoRenderer<GotStagEntity> renderer) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GotStagEntity animatable, GeoRenderer<GotStagEntity> renderer) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(GotStagEntity animatable) {
        return ANIMATIONS;
    }
}
