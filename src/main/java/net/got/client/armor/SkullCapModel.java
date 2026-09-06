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

// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Pasted as-is into net.got.client.armor; only modid/ResourceLocation->Identifier were fixed to
// compile, and the exported field names with spaces (illegal Java identifiers -- "Right Arm" etc)
// were collapsed to camelCase (rightArm etc). No geometry/logic was touched.

public class SkullCapModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "skull_cap"), "main");
	private final ModelPart bone;
	private final ModelPart Waist;
	private final ModelPart Head;
	private final ModelPart Body;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public SkullCapModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.Waist = root.getChild("Waist");
		this.Head = root.getChild("Head");
		this.Body = root.getChild("Body");
		this.rightArm = root.getChild("Right Arm");
		this.leftArm = root.getChild("Left Arm");
		this.rightLeg = root.getChild("Right Leg");
		this.leftLeg = root.getChild("Left Leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-12.5F, -16.5F, 4.5F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(-11.5F, -17.5F, 5.5F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-10.5F, -18.5F, 6.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		PartDefinition Waist = partdefinition.addOrReplaceChild("Waist", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1047F, 0.0873F, 0.0F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightArm = partdefinition.addOrReplaceChild("Right Arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition leftArm = partdefinition.addOrReplaceChild("Left Arm", CubeListBuilder.create(), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.2094F, 0.0F, 0.0F));

		PartDefinition rightLeg = partdefinition.addOrReplaceChild("Right Leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.192F, 0.0F, 0.0349F));

		PartDefinition leftLeg = partdefinition.addOrReplaceChild("Left Leg", CubeListBuilder.create(), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, -0.1745F, 0.0F, -0.0349F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Waist.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
