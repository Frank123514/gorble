package net.got.entity.client.stag;

import net.got.entity.stag.GotStagEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * GeckoLib renderer for {@link GotStagEntity}.
 *
 * <p>Shadow radius matches the stag's large body — same as the warhorse
 * since the stag is a comparable size.
 */
public class GotStagRenderer extends GeoEntityRenderer<GotStagEntity> {

    public GotStagRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GotStagGeoModel());
        this.shadowRadius = 0.9f;
    }
}
