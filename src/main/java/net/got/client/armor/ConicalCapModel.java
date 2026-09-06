package net.got.client.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

// Same shape as the Blockbench-exported helm classes (bone-only EntityModel).
// No .bbmodel export exists for this helm, so geometry is carried over
// unchanged from the old GotHelmModels entry (already head-space, no
// re-derivation needed -- PartPose.ZERO instead of the raw bone pivot offset).

public class ConicalCapModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "conical_cap"), "main");
	private final ModelPart bone;

	public ConicalCapModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create()
		.texOffs(0, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-1.5F, 3.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 16).addBox(-0.5F, 5.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
