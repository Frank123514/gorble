package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Geometry-only, wrapped in GotHelmModel at render time -- see BascinetModel.
// Original had the same floating "bone" at PartPose.offset(8, 24, -8), plus
// a set of unused, oddly-capitalized empty siblings (Waist/Head/Body/"Right
// Arm"/etc, with stray rotations) left over from whatever full-body rig this
// was exported from -- none of that did anything since GotHelmModel hides
// everything but the real "head" child anyway, so it's dropped here in favor
// of the standard lowercase HumanoidModel part names.
// Boxes are a third shorter than Bascinet/Halfhelm's dome (3 tall vs 8),
// matching the low-profile cap shape the original sizes implied.
public class SkullCapModel {

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "skull_cap"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create()
						.texOffs(0, 0).addBox(-4.0F, -8.5F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
						.texOffs(0, 11).addBox(-3.0F, -9.5F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
						.texOffs(0, 18).addBox(-2.0F, -10.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
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