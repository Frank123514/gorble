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
 * In 1.21.3, BoatRenderer uses ModelLayerLocation to determine the texture path.
 * The texture is loaded from: assets/<namespace>/textures/entity/<path>.png
 * where <path> is from the ModelLayerLocation.
 */
public class GotBoatRenderer extends BoatRenderer implements GotBoatRender {
    private final ResourceLocation textureLocation;

    public GotBoatRenderer(EntityRendererProvider.Context ctx, boolean isChestBoat, String woodType) {
        super(ctx, new ModelLayerLocation(
                ResourceLocation.fromNamespaceAndPath("got", isChestBoat ? "chest_boat/" + woodType : "boat/" + woodType),
                "main"
        ));
        // The texture location follows the ModelLayerLocation path
        this.textureLocation = ResourceLocation.fromNamespaceAndPath("got", "entity/" + (isChestBoat ? "chest_boat/" + woodType : "boat/" + woodType));
    }

    /**
     * Override getTextureLocation to return our custom texture.
     * In 1.21.3, this method signature takes the render state or entity type.
     */
    @Override
    public ResourceLocation getTextureLocation(AbstractBoat entity) {
        if (entity instanceof GotBoat got) {
            return got.getBoatTexture();
        }
        if (entity instanceof GotChestBoat got) {
            return got.getBoatTexture();
        }
        return textureLocation;
    }
}