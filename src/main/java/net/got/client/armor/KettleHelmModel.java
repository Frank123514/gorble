package net.got.client.armor;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Pasted as-is into net.got.client.armor; only modid/ResourceLocation->Identifier were fixed to compile.

public class KettleHelmModel extends EntityModel<EntityRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "kettle_helm"), "main");
	private final ModelPart bone;

	public KettleHelmModel(ModelPart root) {
		super(root);
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 14).addBox(-12.5F, -16.5F, 4.5F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-15.5F, -13.5F, 1.5F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 25).addBox(-11.5F, -17.5F, 5.5F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 25).addBox(-10.5F, -18.5F, 6.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityRenderState renderState) {

	}

}