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
 * The dome sits on "head", positioned in head-local space (centered on
 * vanilla's -4,-8,-4 to 4,0,4 head box) instead of the raw Blockbench
 * root-pivot numbers the old export had. The other limbs are declared with
 * no boxes -- HumanoidModel's constructor requires those named children to
 * exist, but they carry zero geometry and stay hidden for a helmet.
 */
public class BascinetModel {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "bascinet"), "main");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Stepped dome: base shell hugs the vanilla head box, then two
        // shrinking rings build the peak above it. Same three boxes/UVs as
        // the old version (0,0 / 0,16 / 0,23 on the 32x32 texture) -- only
        // the positions changed, so your existing bascinet.png should still
        // roughly line up. Nudge in Blockbench if the art doesn't match.
        partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
                        .texOffs(0, 16).addBox(-3.0F, -9.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 23).addBox(-2.0F, -10.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}