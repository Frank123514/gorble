package net.got.client.renderer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.resources.ResourceLocation;

public class GotBoatRenderer extends AbstractBoatRenderer implements GotBoat {

    private final ResourceLocation texture;
    private final EntityModel<BoatRenderState> model;
    private final RenderType renderType;
    private final ModelPart waterPatch;

    public GotBoatRenderer(EntityRendererProvider.Context ctx, boolean isChestBoat, String woodType) {
        super(ctx);
        this.texture = isChestBoat
                ? ResourceLocation.fromNamespaceAndPath("got", "textures/entity/chest_boat/" + woodType + ".png")
                : ResourceLocation.fromNamespaceAndPath("got", "textures/entity/boat/" + woodType + ".png");

        ModelLayerLocation layerLocation = new ModelLayerLocation(
                ResourceLocation.withDefaultNamespace(isChestBoat ? "chest_boat/oak" : "boat/oak"),
                "main"
        );

        ModelPart root = ctx.bakeLayer(layerLocation);

        net.minecraft.client.model.BoatModel boatModel = new net.minecraft.client.model.BoatModel(root);
        this.model = boatModel;
        this.renderType = this.model.renderType(this.texture);

// Get the water patch directly from the baked model parts
        root = ctx.bakeLayer(layerLocation);
        this.waterPatch = root.getChild("bottom");
    }

    @Override
    public ResourceLocation getTextureLocation(BoatRenderState state) {
        return this.texture;
    }

    @Override
    protected EntityModel<BoatRenderState> model() {
        return this.model;
    }

    @Override
    protected RenderType renderType() {
        return this.renderType;
    }

    @Override
    public ModelPart createWaterPatch() {
        return this.waterPatch;
    }
}