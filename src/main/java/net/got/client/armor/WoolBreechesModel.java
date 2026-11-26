package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Converted from the Blockbench "breeches" export. Worn in the vanilla LEGGINGS slot (equipment layer
// "humanoid_leggings"), wrapped in GotLegArmorModel at render time.
// The Blockbench Java export mirrors X, so the parts it calls right_* are placed on the LEFT in game;
// here the vanilla player layout is used directly: in-game right leg = UV (0,16), left leg = UV (16,48).
// The painted area covers the upper 8px of each leg (thigh to just above the boot), so only the single
// leg layer is needed -- there is no outer "pants" layer.
// Texture sheet is 64x64 (textures/entity/equipment/humanoid_leggings/wool_breeches.png).
public class WoolBreechesModel {

	/**
	 * Slimmer than the boiled leather jerkin (0.75) and the hauberk (0.5): it sits between the gambeson
	 * (0.25) and the hauberk, so heavier armor stacks over it without clipping. UVs don't change with deformation.
	 */
	private static final float DEFORM = 0.4F;

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "wool_breeches"), "main");

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// "hat" must hang off "head" (HumanoidModel does head.getChild("hat")), not off root.
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));

		partdefinition.addOrReplaceChild("right_leg",
				CubeListBuilder.create()
						.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(DEFORM)),
				PartPose.offset(-1.9F, 12.0F, 0.0F));

		partdefinition.addOrReplaceChild("left_leg",
				CubeListBuilder.create()
						.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(DEFORM)),
				PartPose.offset(1.9F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}
