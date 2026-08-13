package net.got.event.entity.client.brownbear;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import java.util.HashMap;
import java.util.Map;

public class BrownBearModel extends EntityModel<BrownBearRenderState> {

    final ModelPart body;
    final ModelPart head;
    final ModelPart snout;
    final ModelPart jaw;
    final ModelPart ear_right;
    final ModelPart ear_left;
    final ModelPart tail;
    final ModelPart neck;
    final ModelPart leg_front_right;
    final ModelPart leg_front_right_lower;
    final ModelPart leg_front_left;
    final ModelPart leg_front_left_lower;
    final ModelPart leg_back_right;
    final ModelPart leg_back_right_lower;
    final ModelPart leg_back_left;
    final ModelPart leg_back_left_lower;
    private final ModelPart root;
    private final Map<AnimationDefinition, KeyframeAnimation> bakedAnimations = new HashMap<>();

    public BrownBearModel(ModelPart root) {
        super(root);
        this.root                   = root;
        this.body                   = root.getChild("body");
        this.head                   = root.getChild("head");
        this.snout                  = this.head.getChild("snout");
        this.jaw                    = this.snout.getChild("jaw");
        this.ear_right              = this.head.getChild("ear_right");
        this.ear_left               = this.head.getChild("ear_left");
        this.tail                   = root.getChild("tail");
        this.neck                   = root.getChild("neck");
        this.leg_front_right        = root.getChild("leg_front_right");
        this.leg_front_right_lower  = this.leg_front_right.getChild("leg_front_right_lower");
        this.leg_front_left         = root.getChild("leg_front_left");
        this.leg_front_left_lower   = this.leg_front_left.getChild("leg_front_left_lower");
        this.leg_back_right         = root.getChild("leg_back_right");
        this.leg_back_right_lower   = this.leg_back_right.getChild("leg_back_right_lower");
        this.leg_back_left          = root.getChild("leg_back_left");
        this.leg_back_left_lower    = this.leg_back_left.getChild("leg_back_left_lower");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 21).addBox(-4.0F, -4.0F, -5.0F, 12.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-4.0F, 3.0F, -5.5F, 12.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 12.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

        body.addOrReplaceChild("body_torso_r1", CubeListBuilder.create()
                        .texOffs(0, 40).addBox(-6.0F, -6.1198F, -5.1726F, 12.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.0F, 17.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(),
                PartPose.offset(0.0F, 11.0F, -14.0F));

        head.addOrReplaceChild("head_r1", CubeListBuilder.create()
                        .texOffs(42, 40).addBox(-3.5F, -3.564F, -3.1106F, 7.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create()
                        .texOffs(46, 19).addBox(-1.0F, 0.3F, -5.7F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        snout.addOrReplaceChild("snout_r1", CubeListBuilder.create()
                        .texOffs(46, 12).addBox(-2.5F, -1.1878F, -2.0603F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.3F, -3.4F, 0.2182F, 0.0F, 0.0F));

        PartDefinition jaw = snout.addOrReplaceChild("jaw", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create()
                        .texOffs(64, 12).addBox(-2.5F, -1.1969F, -2.2324F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 3.3F, -2.5F, 0.1309F, 0.0F, 0.0F));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create(),
                PartPose.offset(-4.0F, -3.0F, 0.0F));

        ear_right.addOrReplaceChild("ear_right_r1", CubeListBuilder.create()
                        .texOffs(36, 59).addBox(1.5F, -0.4921F, -0.463F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 0.0F, 2.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create(),
                PartPose.offset(3.0F, -1.0F, 0.0F));

        ear_left.addOrReplaceChild("ear_left_r1", CubeListBuilder.create()
                        .texOffs(36, 62).addBox(-1.5F, -1.4883F, -0.3758F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.2475F, -1.0214F, 1.8463F, 0.0873F, 0.0F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(),
                PartPose.offset(0.5F, 10.5F, 17.8F));

        tail.addOrReplaceChild("tail_r1", CubeListBuilder.create()
                        .texOffs(50, 65).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5796F, 1.0F, -0.7138F, 0.2618F, 0.0F, 0.0F));

        PartDefinition neck = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create(),
                PartPose.offset(-0.5F, 11.0F, -14.0F));

        neck.addOrReplaceChild("neck_lower_r1", CubeListBuilder.create()
                        .texOffs(44, 33).addBox(-3.0F, 3.0F, -2.0F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -0.5F, 4.8F, -0.2618F, 0.0F, 0.0F));

        neck.addOrReplaceChild("neck_upper_r1", CubeListBuilder.create()
                        .texOffs(44, 21).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.1F, 3.8F, 0.2182F, 0.0F, 0.0F));

        PartDefinition leg_front_right = partdefinition.addOrReplaceChild("leg_front_right", CubeListBuilder.create(),
                PartPose.offset(-3.5F, 14.0F, -8.0F));

        leg_front_right.addOrReplaceChild("leg_front_right_upper_r1", CubeListBuilder.create()
                        .texOffs(18, 59).addBox(-1.5F, -3.3218F, -2.4662F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 0.0F, 2.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leg_front_right_lower = leg_front_right.addOrReplaceChild("leg_front_right_lower",
                CubeListBuilder.create()
                        .texOffs(66, 28).addBox(-5.2F, 4.0F, -9.6F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.2F, 5.0F, 9.0F));

        leg_front_right_lower.addOrReplaceChild("leg_front_right_lower_r1", CubeListBuilder.create()
                        .texOffs(36, 65).addBox(-1.0F, -3.0872F, -2.0038F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.2F, 0.5F, -6.8F, -0.0873F, 0.0F, 0.0F));

        PartDefinition leg_front_left = partdefinition.addOrReplaceChild("leg_front_left", CubeListBuilder.create(),
                PartPose.offset(5.5F, 14.0F, -7.0F));

        leg_front_left.addOrReplaceChild("leg_front_left_upper_r1", CubeListBuilder.create()
                        .texOffs(0, 59).addBox(-1.5F, -2.3256F, -2.5534F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, -0.658F, 1.0603F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leg_front_left_lower = leg_front_left.addOrReplaceChild("leg_front_left_lower",
                CubeListBuilder.create()
                        .texOffs(66, 33).addBox(1.0F, 3.1395F, -2.2294F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, 5.842F, 0.6603F));

        leg_front_left_lower.addOrReplaceChild("leg_front_left_lower_r1", CubeListBuilder.create()
                        .texOffs(66, 0).addBox(-2.0F, -3.0872F, -2.0038F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, -0.3605F, 0.5706F, -0.0873F, 0.0F, 0.0F));

        PartDefinition leg_back_right = partdefinition.addOrReplaceChild("leg_back_right", CubeListBuilder.create(),
                PartPose.offset(-14.5F, 14.0F, 11.0F));

        leg_back_right.addOrReplaceChild("leg_back_right_upper_r1", CubeListBuilder.create()
                        .texOffs(46, 0).addBox(-1.5F, -2.843F, -3.1376F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(9.0F, 0.0F, 3.2F, -0.1309F, 0.0F, 0.0F));

        PartDefinition leg_back_right_lower = leg_back_right.addOrReplaceChild("leg_back_right_lower",
                CubeListBuilder.create()
                        .texOffs(66, 23).addBox(-1.0F, 9.0F, 0.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(9.0F, 0.0F, 0.0F));

        leg_back_right_lower.addOrReplaceChild("leg_back_right_lower_r1", CubeListBuilder.create()
                        .texOffs(62, 64).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, 5.5F, 3.2F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leg_back_left = partdefinition.addOrReplaceChild("leg_back_left", CubeListBuilder.create(),
                PartPose.offset(4.5F, 14.0F, 13.0F));

        leg_back_left.addOrReplaceChild("leg_back_left_upper_r1", CubeListBuilder.create()
                        .texOffs(42, 53).addBox(-3.5F, -2.843F, -3.1376F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -0.9389F, 1.4341F, -0.1309F, 0.0F, 0.0F));

        PartDefinition leg_back_left_lower = leg_back_left.addOrReplaceChild("leg_back_left_lower",
                CubeListBuilder.create()
                        .texOffs(66, 18).addBox(-2.0F, 3.1971F, -2.5153F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 5.7611F, 0.7341F));

        leg_back_left_lower.addOrReplaceChild("leg_back_left_lower_r1", CubeListBuilder.create()
                        .texOffs(62, 53).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.3029F, 0.6847F, 0.0873F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void applyAnimation(AnimationDefinition animation, float ageInTicks, float weight) {
        body.resetPose();
        head.resetPose();
        snout.resetPose();
        jaw.resetPose();
        ear_right.resetPose();
        ear_left.resetPose();
        tail.resetPose();
        neck.resetPose();
        leg_front_right.resetPose();
        leg_front_right_lower.resetPose();
        leg_front_left.resetPose();
        leg_front_left_lower.resetPose();
        leg_back_right.resetPose();
        leg_back_right_lower.resetPose();
        leg_back_left.resetPose();
        leg_back_left_lower.resetPose();
        bakedAnimations.computeIfAbsent(animation, d -> d.bake(this.root)).apply((long)(ageInTicks * 50F), weight);
    }

    @Override
    public void setupAnim(BrownBearRenderState renderState) {
        
    }
}
