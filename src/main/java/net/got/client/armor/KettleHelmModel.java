package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Geometry-only, wrapped in GotHelmModel at render time -- see BascinetModel.
// Original had the same floating "bone" at PartPose.offset(8, 24, -8) as
// Bascinet/Halfhelm. Rebuilt head-centered, keeping the same 4 boxes/UVs and
// the same relative shape idea: a low dome, a wide flat brim ring (the
// original's 14x14, zero-height box -- the signature kettle-hat silhouette),
// then two small crown rings at the peak.
public class KettleHelmModel {

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "kettle_helm"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create()
						.texOffs(0, 14).addBox(-4.2F, -9.0F, -4.2F, 8.4F, 5.0F, 8.4F, new CubeDeformation(0.0F))
						.texOffs(0, 0).addBox(-7.0F, -4.5F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
						.texOffs(0, 25).addBox(-3.0F, -10.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
						.texOffs(24, 25).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}