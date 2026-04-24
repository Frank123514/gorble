package net.got.entity.client.horse;

import net.got.entity.horse.GotHorseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * GeckoLib renderer for {@link GotHorseEntity}.
 *
 * <p>Replaces the old vanilla {@code MobRenderer<HorseModel>} with a
 * {@link GeoEntityRenderer} driven by the geo model and animation JSON.
 * Shadow radius is kept at 0.9 to match the entity's 1.4-wide footprint.
 */
public class GotHorseRenderer extends GeoEntityRenderer<GotHorseEntity> {

    public GotHorseRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GotHorseGeoModel());
        this.shadowRadius = 0.9f;
    }
}
