package net.got.entity.client.horse;

import net.got.GotMod;
import net.got.entity.horse.GotHorseEntity;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.resources.ResourceLocation;

/**
 * Client-side renderer for {@link net.got.entity.horse.GotHorseEntity}.
 *
 * <p>Extends vanilla MobRenderer with HorseModel to reuse the vanilla horse model system,
 * overriding only the texture location to point at the GOT warhorse skin.
 * The vanilla horse model layer (saddle, mane, tail) is preserved automatically.
 */
public class GotHorseRenderer extends MobRenderer<GotHorseEntity, HorseRenderState, HorseModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "textures/entity/got_horse.png");

    public GotHorseRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new HorseModel(ctx.bakeLayer(ModelLayers.HORSE)), 0.9f);
    }

    @Override
    public ResourceLocation getTextureLocation(HorseRenderState state) {
        return TEXTURE;
    }

    @Override
    public HorseRenderState createRenderState() {
        return new HorseRenderState();
    }
}