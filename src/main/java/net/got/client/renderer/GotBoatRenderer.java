package net.got.client.renderer;

import net.got.entity.GotBoat;
import net.got.entity.GotChestBoat;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractBoat;

/**
 * Renderer for all GoT custom boat types.
 *
 * In 1.21.3, EntityRenderer uses the render-state pattern:
 *   extractRenderState() copies entity data -> state
 *   getTextureLocation(state) reads the texture back
 *
 * We extend BoatRenderState to carry a boatTexture field, inject it in
 * extractRenderState(), and return it from getTextureLocation().
 */
public class GotBoatRenderer extends BoatRenderer {

    private static final ModelLayerLocation VANILLA_BOAT =
            new ModelLayerLocation(ResourceLocation.withDefaultNamespace("boat/oak"), "main");
    private static final ModelLayerLocation VANILLA_CHEST_BOAT =
            new ModelLayerLocation(ResourceLocation.withDefaultNamespace("chest_boat/oak"), "main");

    private final ResourceLocation fallbackTexture;

    public GotBoatRenderer(EntityRendererProvider.Context ctx, boolean isChestBoat, String woodType) {
        super(ctx, isChestBoat ? VANILLA_CHEST_BOAT : VANILLA_BOAT);
        this.fallbackTexture = ResourceLocation.fromNamespaceAndPath("got",
                "textures/entity/boat/" + woodType + (isChestBoat ? "_chest_boat" : "") + ".png");
    }

    @Override
    public GotBoatRenderState createRenderState() {
        return new GotBoatRenderState();
    }

    @Override
    public void extractRenderState(AbstractBoat entity,
                                   net.minecraft.client.renderer.entity.state.BoatRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        if (state instanceof GotBoatRenderState got) {
            if (entity instanceof GotBoat boat) {
                got.boatTexture = boat.getBoatTexture();
            } else if (entity instanceof GotChestBoat boat) {
                got.boatTexture = boat.getBoatTexture();
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(net.minecraft.client.renderer.entity.state.BoatRenderState state) {
        if (state instanceof GotBoatRenderState got && got.boatTexture != null) {
            return got.boatTexture;
        }
        return fallbackTexture;
    }
}
