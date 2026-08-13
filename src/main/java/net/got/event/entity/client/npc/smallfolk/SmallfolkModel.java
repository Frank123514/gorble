package net.got.event.entity.client.npc.smallfolk;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

public class SmallfolkModel extends HumanoidModel<SmallfolkRenderState>
        implements SmallfolkModelParts {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "smallfolk"), "main");

    private final ModelPart breasts;
    private final ModelPart rightArmFemale;
    private final ModelPart leftArmFemale;

    public SmallfolkModel(ModelPart root) {
        super(root);
        this.breasts         = body.getChild("breasts");
        this.rightArmFemale  = root.getChild("right_arm_female");
        this.leftArmFemale   = root.getChild("left_arm_female");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("hat",
                CubeListBuilder.create()
                        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        head.addOrReplaceChild("armor_head",
                CubeListBuilder.create()
                        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("armor_body",
                CubeListBuilder.create()
                        .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        body.addOrReplaceChild("jacket",
                CubeListBuilder.create()
                        .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        body.addOrReplaceChild("cape",
                CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

        body.addOrReplaceChild("breasts",
                CubeListBuilder.create()
                        .texOffs(24, 3).addBox(-3.0F, -2.0F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, -1.7F, -0.3491F, 0.0F, 0.0F));

        PartDefinition rightArm = root.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        rightArm.addOrReplaceChild("armor_right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        rightArm.addOrReplaceChild("right_sleeve",
                CubeListBuilder.create()
                        .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        rightArm.addOrReplaceChild("right_item",
                CubeListBuilder.create(), PartPose.offset(-1.0F, 9.3F, 2.0F));

        PartDefinition leftArm = root.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 2.0F, 0.0F));

        leftArm.addOrReplaceChild("armor_left_arm",
                CubeListBuilder.create()
                        .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        leftArm.addOrReplaceChild("left_sleeve",
                CubeListBuilder.create()
                        .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        leftArm.addOrReplaceChild("left_item",
                CubeListBuilder.create(), PartPose.offset(1.0F, 9.3F, 2.0F));

        PartDefinition rightArmFemale = root.addOrReplaceChild("right_arm_female",
                CubeListBuilder.create()
                        .texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 2.5F, 0.0F));

        rightArmFemale.addOrReplaceChild("armor_right_arm_female",
                CubeListBuilder.create()
                        .texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        rightArmFemale.addOrReplaceChild("right_sleeve_female",
                CubeListBuilder.create()
                        .texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        rightArmFemale.addOrReplaceChild("right_item_female",
                CubeListBuilder.create(), PartPose.offset(-1.0F, 9.3F, 2.0F));

        PartDefinition leftArmFemale = root.addOrReplaceChild("left_arm_female",
                CubeListBuilder.create()
                        .texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 2.5F, 0.0F));

        leftArmFemale.addOrReplaceChild("armor_left_arm_female",
                CubeListBuilder.create()
                        .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        leftArmFemale.addOrReplaceChild("left_sleeve_female",
                CubeListBuilder.create()
                        .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        leftArmFemale.addOrReplaceChild("left_item_female",
                CubeListBuilder.create(), PartPose.offset(1.0F, 9.3F, 2.0F));

        PartDefinition rightLeg = root.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.9F, 12.0F, 0.0F));

        rightLeg.addOrReplaceChild("armor_right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        rightLeg.addOrReplaceChild("armor_right_boot",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_pants",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        PartDefinition leftLeg = root.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(16, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.9F, 12.0F, 0.0F));

        leftLeg.addOrReplaceChild("armor_left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        leftLeg.addOrReplaceChild("armor_left_boot",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_pants",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(SmallfolkRenderState state) {
        super.setupAnim(state);

        if (state.isFemale) {
            
            breasts.visible        = true;
            rightArmFemale.visible = true;
            leftArmFemale.visible  = true;

            rightArm.visible = false;
            leftArm.visible  = false;

            rightArmFemale.loadPose(rightArm.storePose());
            leftArmFemale.loadPose(leftArm.storePose());

            ModelPart rightSleeveFemale = rightArmFemale.getChild("right_sleeve_female");
            ModelPart leftSleeveFemale  = leftArmFemale.getChild("left_sleeve_female");
            if (rightSleeveFemale != null && rightArm.getChild("right_sleeve") != null) {
                rightSleeveFemale.loadPose(rightArm.getChild("right_sleeve").storePose());
            }
            if (leftSleeveFemale != null && leftArm.getChild("left_sleeve") != null) {
                leftSleeveFemale.loadPose(leftArm.getChild("left_sleeve").storePose());
            }

        } else {
            
            breasts.visible        = false;
            rightArmFemale.visible = false;
            leftArmFemale.visible  = false;

            rightArm.visible = true;
            leftArm.visible  = true;
        }

        applyTalkAnimation(state);
    }

    private void applyTalkAnimation(SmallfolkRenderState state) {
        if (!state.isTalking && state.talkHeadYaw == 0f
                && state.talkHeadPitch == 0f && state.talkGesture == 0f) return;

        this.head.yRot += state.talkHeadYaw;
        this.head.xRot += state.talkHeadPitch;

        if (state.isFemale) {
            this.rightArmFemale.xRot -= state.talkGesture;
        } else {
            this.rightArm.xRot -= state.talkGesture;
        }
        this.body.yRot += state.talkGesture * 0.1F;
    }

    @Override public ModelPart sfHead()            { return head; }
    @Override public ModelPart sfBody()            { return body; }
    @Override public ModelPart sfRightArm()        { return rightArm; }
    @Override public ModelPart sfLeftArm()         { return leftArm; }
    @Override public ModelPart sfRightLeg()        { return rightLeg; }
    @Override public ModelPart sfLeftLeg()         { return leftLeg; }
    @Override public ModelPart sfRightItemAnchor() { return rightArm.getChild("right_item"); }
    @Override public ModelPart sfLeftItemAnchor()  { return leftArm.getChild("left_item"); }
}