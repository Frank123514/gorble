package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Geometry-only, wrapped in GotHelmModel at render time -- see BascinetModel.
// Converted directly from kettle_helm.bbmodel, same floating-bone issue and
// same fix as Bascinet/Halfhelm (see that file for the coordinate rule).
// The brim is authored as a literal zero-height plate (14x0x14) in the
// source file -- kept faithful to the art, but bumped to 0.5 tall here so
// it actually has thickness to render; a true zero-height box degenerates
// to invisible top/bottom faces with no visible sides.
public class KettleHelmModel {

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "kettle_helm"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create()
						.texOffs(0, 14).addBox(-4.5F, -3.0F, -3.5F, 8.0F, 3.0F, 8.0F)
						.texOffs(0, 0).addBox(-7.5F, -0.25F, -6.5F, 14.0F, 0.5F, 14.0F)
						.texOffs(0, 25).addBox(-3.5F, -4.0F, -2.5F, 6.0F, 1.0F, 6.0F)
						.texOffs(24, 25).addBox(-2.5F, -5.0F, -1.5F, 4.0F, 1.0F, 4.0F),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		// "hat" must hang off "head" (HumanoidModel does head.getChild("hat")), not off root.
		head.addOrReplaceChild("hat", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(-1.9F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}