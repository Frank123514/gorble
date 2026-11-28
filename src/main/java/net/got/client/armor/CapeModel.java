package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Wrapped in GotBodyArmorModel(root, false) at render time: torso + arms visible (no head, no legs).
// Converted from cape.bbmodel. mc_y = 24 - bb_yMax, x/z are taken relative to each group's pivot and
// NOT x-flipped -- Blockbench's own Java export mirrors X on asymmetric parts, which would put the
// shoulder pieces inside the torso. Only the three custom cubes (back panel + two shoulder pieces) are
// exported; the head / body / arm / leg cubes in the .bbmodel are just the player reference.
// Texture sheet is 128x128 (textures/entity/equipment/humanoid/cape.png).
public class CapeModel {

	/** Same 0.25 inflate as the .bbmodel. Smaller than every armor layer, so the cape's back panel hangs behind them. */
	private static final float CAPE_DEFORM = 0.25F;

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "cape"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// "hat" must hang off "head" (HumanoidModel does head.getChild("hat")), not off root.
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		// Body / arms / legs carry no cubes of their own -- the real geometry is on the children.
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		// Back panel: 14 wide, 18 tall, 3 thick, behind the torso from the shoulders to about knee height.
		body.addOrReplaceChild("cape_back",
				CubeListBuilder.create()
						.texOffs(0, 0).addBox(-7.0F, 0.0F, 1.0F, 14.0F, 18.0F, 3.0F, new CubeDeformation(CAPE_DEFORM)),
				PartPose.ZERO);

		PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
		rightArm.addOrReplaceChild("right_pad",
				CubeListBuilder.create()
						.texOffs(64, 22).addBox(-2.0F, -3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(CAPE_DEFORM)),
				PartPose.ZERO);

		PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
		leftArm.addOrReplaceChild("left_pad",
				CubeListBuilder.create()
						.texOffs(64, 16).addBox(-1.0F, -3.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(CAPE_DEFORM)),
				PartPose.ZERO);

		// HumanoidModel's constructor needs the leg parts to exist; they carry no cubes and are hidden.
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}
