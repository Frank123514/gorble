package net.got.entity.client.npc.smallfolk;

import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

/**
 * Shared GeckoLib model for all Smallfolk tiers.
 */
public final class SmallfolkGeoModel<T extends SmallfolkEntity> extends GeoModel<T> {

    private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath("got", "geo/smallfolk.geo.json");
    private static final ResourceLocation ANIMATIONS = ResourceLocation.fromNamespaceAndPath("got", "animations/smallfolk.animation.json");

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    public SmallfolkGeoModel(ResourceLocation[] maleTextures, ResourceLocation[] femaleTextures) {
        this.maleTextures = maleTextures;
        this.femaleTextures = femaleTextures;
    }

    @Override
    public ResourceLocation getModelResource(T animatable, GeoRenderer<T> renderer) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable, GeoRenderer<T> renderer) {
        boolean female = animatable.getGender() == NpcGender.FEMALE;
        if (female) {
            int idx = animatable.getVariant() - animatable.getVariantsPerGender();
            return femaleTextures[Math.abs(idx) % femaleTextures.length];
        }
        return maleTextures[animatable.getVariant() % maleTextures.length];
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return ANIMATIONS;
    }
}