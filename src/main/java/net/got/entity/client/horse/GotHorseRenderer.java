package net.got.entity.client.horse;

import net.got.entity.horse.GotHorseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * Client-side GeckoLib renderer for {@link GotHorseEntity}.
 *
 * <p>Uses {@link GotHorseModel} to render the custom detailed horse model
 * with full animation support. Saddle, bridle, and armour visibility is
 * handled via the entity's data accessors which GeckoLib reads through
 * the model's bone visibility.
 */
public class GotHorseRenderer extends GeoEntityRenderer<GotHorseEntity> {

    public GotHorseRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GotHorseModel());
        // Slightly larger shadow than a human NPC — horse is a big animal
        this.shadowRadius = 0.9f;
    }
}
