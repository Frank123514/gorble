package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Wrapped in GotBodyArmorModel(root, false) at render time: torso + arms visible (no head, no legs).
// Converted from fur_cloak.bbmodel. mc_y = 24 - bb_yMax, x/z are taken relative to each group's pivot and
// NOT x-flipped -- Blockbench's own Java export mirrors X on asymmetric parts, which would put the
// shoulder mantle pieces inside the torso. Only the three custom cubes (back mantle + two shoulder pieces) are
// exported; the head / body / arm / leg cubes in the .bbmodel are just the player reference.
// Texture sheet is 128x128 (textures/entity/equipment/humanoid/fur_cloak.png).
public class FurCloakModel {

	/** Same 0.25 inflate as the .bbmodel. Smaller than every armor layer, so the fur mantle hangs behind them. */
	private static final float FUR_DEFORM = 0.25F;

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "fur_cloak"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// "hat" must hang off "head" (HumanoidModel does head.getChild("hat")), not off root.
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		// Body / arms / legs carry no cubes of their own -- the real geometry is on the children.
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		// Back mantle: 18 wide, 20 tall, 4 thick, from the shoulders down past the hips, wider than the shoulders.
		body.addOrReplaceChild("cloak_back",
				CubeListBuilder.create()
						.texOffs(0, 0).addBox(-9.0F, 0.0F, 0.0F, 18.0F, 20.0F, 4.0F, new CubeDeformation(FUR_DEFORM)),
				PartPose.ZERO);

		PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
		rightArm.addOrReplaceChild("right_pad",
				CubeListBuilder.create()
						.texOffs(64, 32).addBox(-4.0F, -3.0F, -3.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(FUR_DEFORM)),
				PartPose.ZERO);

		PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
		leftArm.addOrReplaceChild("left_pad",
				CubeListBuilder.create()
						.texOffs(44, 16).addBox(-1.0F, -3.0F, -3.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(FUR_DEFORM)),
				PartPose.ZERO);

		// HumanoidModel's constructor needs the leg parts to exist; they carry no cubes and are hidden.
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}
