package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

/**
 * A single shared, cubeless anchor layer used by every custom-shape helm.
 * The actual geometry now lives entirely in each helm's Blockbench item
 * model (models/item/*.json, authored with a "head" display entry) and is
 * rendered directly through the item model pipeline in {@link GotHelmModel}
 * -- this layer only exists to give HumanoidArmorLayer a head ModelPart to
 * copy rotation/position onto, same as it does for vanilla armor.
 */
public final class GotHelmModels {

    private GotHelmModels() {}

    public static final ModelLayerLocation HEAD_ANCHOR =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "helm_head_anchor"), "main");

    public static LayerDefinition createHeadAnchorLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        CubeListBuilder empty = CubeListBuilder.create();
        root.addOrReplaceChild("head", empty, PartPose.ZERO);
        root.addOrReplaceChild("hat", empty, PartPose.ZERO);
        root.addOrReplaceChild("body", empty, PartPose.ZERO);
        root.addOrReplaceChild("right_arm", empty, PartPose.ZERO);
        root.addOrReplaceChild("left_arm", empty, PartPose.ZERO);
        root.addOrReplaceChild("right_leg", empty, PartPose.ZERO);
        root.addOrReplaceChild("left_leg", empty, PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 64);
    }
}
