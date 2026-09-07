package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Geometry-only, wrapped in GotHelmModel at render time -- see BascinetModel.
// Converted from got_halfhelm_on_player.bbmodel (group "got_halfhelm (edit me)").
// The group has rotation=[0,-90,0] around origin=[0.5,24,-0.5] in Blockbench space.
// Each cube's from/to is un-rotated by +90° Y around that pivot, then mapped to
// Minecraft head-local coords via: mc_x=-bb_xMax, mc_y=-(bb_yMax-24), mc_z=bb_zMin.
// Inverse rotation formula (pivot px=0.5, pz=-0.5):
//   x_unrot = -z_bb,  z_unrot = x_bb - 1
//
//   part_2 (dome,       inflate=1.0):    BB unrotated x[-4,4]  y[24,32]         z[-4,4]
//   part_3 (lower ring, inflate=0.765):  BB unrotated x[-3,3]  y[32.425,33.425] z[-3,3]
//   part_4 (top ring,   inflate=0.523):  BB unrotated x[-2,2]  y[33.975,34.975] z[-2,2]
public class HalfhelmModel {

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "halfhelm"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create()
						.texOffs(0, 0) .addBox(-4.0F, -8.0F,    -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
						.texOffs(0, 16).addBox(-3.0F, -9.425F,  -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.765F))
						.texOffs(0, 23).addBox(-2.0F, -10.975F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.52315F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -(float)(Math.PI / 2), 0.0F));

		// "hat" must hang off "head" (HumanoidModel does head.getChild("hat")), not off root.
		head.addOrReplaceChild("hat", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("body",      CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm",  CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(-1.9F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg",  CubeListBuilder.create().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
}