package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Geometry-only, wrapped in GotHelmModel at render time -- see BascinetModel.
// Converted directly from skull_cap.bbmodel, same floating-bone issue and
// same fix (see BascinetModel for the coordinate rule). That file also
// carries a full set of unused, oddly-capitalized empty siblings (Waist/
// Head/Body/"Right Arm"/etc, presumably left over from whatever full-body
// reference rig this was built against) -- none of them have any children,
// so they're dropped here in favor of the standard lowercase HumanoidModel
// part names. The cap's dome is a third the height of Bascinet/Halfhelm's
// (3 tall vs 8), matching the low-profile shape in the source file.
public class SkullCapModel {

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "skull_cap"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create()
						.texOffs(0, 0).addBox(-4.5F, -3.0F, -3.5F, 8.0F, 3.0F, 8.0F)
						.texOffs(0, 11).addBox(-3.5F, -4.0F, -2.5F, 6.0F, 1.0F, 6.0F)
						.texOffs(0, 18).addBox(-2.5F, -5.0F, -1.5F, 4.0F, 1.0F, 4.0F),
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