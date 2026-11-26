package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Converted from the Blockbench "boots" export. Worn in the vanilla BOOTS slot (equipment layer
// "humanoid"), wrapped in GotLegArmorModel at render time.
// Same vanilla player UV layout as the breeches: in-game right leg = leg layer (0,16) + pants layer (0,32),
// left leg = leg layer (16,48) + pants layer (0,48). The inner layer paints the bottom 4px of the leg
// (plus the sole); the outer layer is a 2px cuff just above the foot.
// Texture sheet is 64x64 (textures/entity/equipment/humanoid/leather_boots.png).
public class LeatherBootsModel {

	/** Inner (foot) layer: same size as the breeches so the two meet cleanly. Well under the jerkin's 0.75. */
	private static final float DEFORM = 0.4F;
	/** Outer cuff layer: inner + 0.15 so it never z-fights with the foot layer. */
	private static final float CUFF_DEFORM = 0.55F;

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "leather_boots"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// "hat" must hang off "head" (HumanoidModel does head.getChild("hat")), not off root.
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg",
				CubeListBuilder.create()
						.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(DEFORM)),
				PartPose.offset(-1.9F, 12.0F, 0.0F));
		rightLeg.addOrReplaceChild("right_cuff",
				CubeListBuilder.create()
						.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(CUFF_DEFORM)),
				PartPose.ZERO);

		PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg",
				CubeListBuilder.create()
						.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(DEFORM)),
				PartPose.offset(1.9F, 12.0F, 0.0F));
		leftLeg.addOrReplaceChild("left_cuff",
				CubeListBuilder.create()
						.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(CUFF_DEFORM)),
				PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
