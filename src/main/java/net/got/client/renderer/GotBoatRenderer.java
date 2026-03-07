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
 * Borrows vanilla's oak boat/chest_boat model layers (always registered by MC itself)
 * since all boat types share identical geometry. The texture is swapped per wood type
 * via getTextureLocation().
 */
public class GotBoatRenderer extends BoatRenderer implements GotBoatRender {
    private final ResourceLocation textureLocation;

    // Vanilla always registers these for oak boats — borrow them for the geometry.
    private static final ModelLayerLocation VANILLA_BOAT =
            new ModelLayerLocation(ResourceLocation.withDefaultNamespace("boat/oak"), "main");
    private static final ModelLayerLocation VANILLA_CHEST_BOAT =
            new ModelLayerLocation(ResourceLocation.withDefaultNamespace("chest_boat/oak"), "main");

    public GotBoatRenderer(EntityRendererProvider.Context ctx, boolean isChestBoat, String woodType) {
        super(ctx, isChestBoat ? VANILLA_CHEST_BOAT : VANILLA_BOAT);
        this.textureLocation = ResourceLocation.fromNamespaceAndPath("got",
                "textures/entity/" + (isChestBoat ? "chest_boat/" + woodType : "boat/" + woodType) + ".png");
    }

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