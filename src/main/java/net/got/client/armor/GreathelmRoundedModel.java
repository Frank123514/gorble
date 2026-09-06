package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Geometry-only, wrapped in GotHelmModel at render time -- see BascinetModel.
// Boxes were already positioned correctly in head-local space; only the
// structural part changed: "bone" -> "head", EntityModel wrapper dropped,
// empty sibling limbs added.
public class GreathelmRoundedModel {

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "greathelm_rounded"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create()
						.texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
						.texOffs(0, 20).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
						.texOffs(32, 20).addBox(-2.0F, 2.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
						.texOffs(0, 30).addBox(-0.5F, 1.0F, -5.1F, 1.0F, 3.0F, 10.2F, new CubeDeformation(0.0F))
						.texOffs(22, 30).addBox(-4.0F, -7.0F, -5.1F, 8.0F, 1.0F, 10.2F, new CubeDeformation(0.0F)),
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