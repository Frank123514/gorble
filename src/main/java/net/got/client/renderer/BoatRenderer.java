package net.got.client.renderer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.AbstractBoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.resources.Identifier;

public class BoatRenderer extends AbstractBoatRenderer implements GotBoat {

    private final Identifier texture;
    private final EntityModel<BoatRenderState> model;
    private final RenderType renderType;
    private final ModelPart waterPatch;

    public BoatRenderer(EntityRendererProvider.Context ctx, boolean isChestBoat, String woodType) {
        super(ctx);
        this.texture = isChestBoat
                ? Identifier.fromNamespaceAndPath("got", "textures/entity/chest_boat/" + woodType + ".png")
                : Identifier.fromNamespaceAndPath("got", "textures/entity/boat/" + woodType + ".png");

        ModelLayerLocation layerLocation = new ModelLayerLocation(
                Identifier.withDefaultNamespace(isChestBoat ? "chest_boat/oak" : "boat/oak"),
                "main"
        );

        ModelPart root = ctx.bakeLayer(layerLocation);

        net.minecraft.client.model.object.boat.BoatModel boatModel = new net.minecraft.client.model.object.boat.BoatModel(root);
        this.model = boatModel;
        this.renderType = this.model.renderType(this.texture);

        root = ctx.bakeLayer(layerLocation);
        this.waterPatch = root.getChild("bottom");
    }

    @Override
    public Identifier getTextureLocation(BoatRenderState state) {
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