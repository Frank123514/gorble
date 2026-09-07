package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

/**
 * Geometry only -- no model class here. The ModelPart this bakes gets
 * wrapped in {@link GotHelmModel} (a plain HumanoidModel) at render time via
 * GotHelmClientExtensions. That's the part that matters: since the 1.21.2+
 * equipment rework, HumanoidArmorLayer's armor-model type is bound to
 * HumanoidModel, so only a real HumanoidModel gets head look / body pose
 * copied onto it. A bare EntityModel with one floating "bone" never will,
 * regardless of how correct its geometry is.
 *
 * Converted directly from bascinet.bbmodel. In that file the three dome
 * cubes are children of a "bone" group sitting at Blockbench origin
 * (-8, 0, -8) -- not the head. That's not a stylistic choice, it's the bug:
 * (-8, 0, -8) is down around waist/torso height in Blockbench's coordinate
 * space, nowhere near where a helmet belongs, which is exactly why the
 * in-game dome looked nothing like the Blockbench preview (Blockbench just
 * shows the cubes relative to their own bone, wherever that bone is;
 * Minecraft renders that bone's PartPose literally, wherever it is).
 *
 * The conversion below keeps the three cubes' sizes and offsets *relative
 * to each other* exactly as authored, and re-anchors the whole stack to
 * sit flush on the neckline (mc y = 0) instead, using PartPose.offset(0,0,0)
 * like vanilla's own head part. Per-axis: Blockbench x -> -x, Blockbench
 * z -> z unchanged, Blockbench y -> (lowest y in the group) - y. UV anchors
 * came straight from the file's uv_offset per cube, unchanged.
 */
public class BascinetModel {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "bascinet"), "main");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.5F, -8.0F, -3.5F, 8.0F, 8.0F, 8.0F)
                        .texOffs(0, 16).addBox(-3.5F, -9.0F, -2.5F, 6.0F, 1.0F, 6.0F)
                        .texOffs(0, 23).addBox(-2.5F, -10.0F, -1.5F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // "hat" must hang off "head" (HumanoidModel does head.getChild("hat")), not off root.
        head.addOrReplaceChild("hat", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}