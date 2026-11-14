package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Wrapped in GotBodyArmorModel(root, true) at render time: torso + arms + legs visible
// (no head), because the jerkin's skirt is made of pants cubes riding on the legs.
// Converted from northern_leather_armor_player_model_classic. Blockbench's Java export mirrors X
// (and flips Y), so the parts it calls right_* belong on the LEFT side in game and vice versa:
// the bbmodel's right_sleeve / right_pants (and their UVs) go on the left arm / leg, and the
// left_* ones on the right. Rotation signs are kept as exported, which makes the skirt halves
// flare OUTWARD by 5 degrees (+-0.0873 rad on Z).
// Texture sheet is 64x64 (textures/entity/equipment/humanoid/boiled_leather_jerkin.png).
public class BoiledLeatherJerkinModel {

	/**
	 * Outermost layer (same slot as the brigandine): larger than the gambeson (0.25) and the
	 * hauberk (0.5) so it stacks over both without clipping. UVs don't change with deformation.
	 */
	private static final float DEFORM = 0.75F;

	private static final float SKIRT_FLARE = 0.0873F;

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "boiled_leather_jerkin"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// "hat" must hang off "head" (HumanoidModel does head.getChild("hat")), not off root.
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		// Body / arms / legs carry no cubes of their own -- the real geometry is on the jacket / sleeves / pants.
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		body.addOrReplaceChild("jacket",
				CubeListBuilder.create()
						.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(DEFORM)),
				PartPose.ZERO);

		PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
		rightArm.addOrReplaceChild("right_sleeve",
				CubeListBuilder.create()
						.texOffs(24, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(DEFORM)),
				PartPose.ZERO);

		PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
		leftArm.addOrReplaceChild("left_sleeve",
				CubeListBuilder.create()
						.texOffs(16, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(DEFORM)),
				PartPose.ZERO);

		PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
		rightLeg.addOrReplaceChild("right_pants",
				CubeListBuilder.create()
						.texOffs(16, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(DEFORM)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, SKIRT_FLARE));

		PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));
		leftLeg.addOrReplaceChild("left_pants",
				CubeListBuilder.create()
						.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(DEFORM)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -SKIRT_FLARE));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}