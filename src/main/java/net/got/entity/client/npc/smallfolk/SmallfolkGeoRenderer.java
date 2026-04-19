package net.got.entity.client.npc.smallfolk;

import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * GeckoLib renderer replacing the previous custom model/render-state pipeline.
 */
public final class SmallfolkGeoRenderer<T extends SmallfolkEntity> extends GeoEntityRenderer<T> {

    public SmallfolkGeoRenderer(EntityRendererProvider.Context context,
                                ResourceLocation[] maleTextures,
                                ResourceLocation[] femaleTextures) {
        super(context, new SmallfolkGeoModel<>(maleTextures, femaleTextures));
        this.shadowRadius = 0.5f;
    }
}