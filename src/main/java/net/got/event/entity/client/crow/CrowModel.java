package net.got.event.entity.client.crow;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import java.util.HashMap;
import java.util.Map;

public class CrowModel extends EntityModel<CrowRenderState> {

    final ModelPart body;
    final ModelPart head;
    final ModelPart beak;
    final ModelPart tail;
    final ModelPart wingLeft;
    final ModelPart wingRight;
    private final ModelPart root;
    private final Map<AnimationDefinition, KeyframeAnimation> bakedAnimations = new HashMap<>();

    public CrowModel(ModelPart root) {
        super(root);
        this.root      = root;
        this.body      = root.getChild("body");
        this.head      = root.getChild("head");
        this.beak      = this.head.getChild("beak");
        this.tail      = root.getChild("tail");
        this.wingLeft  = root.getChild("wing_left");
        this.wingRight = root.getChild("wing_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        body.addOrReplaceChild("body_r1",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-2.5F, -8.0F, -2.5F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 5.0F, -1.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 13)
                        .addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 13.5F, -3.5F));

        head.addOrReplaceChild("beak",
                CubeListBuilder.create().texOffs(16, 13)
                        .addBox(-0.5F, -2.0F, -3.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition tail = root.addOrReplaceChild("tail",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 22.0F, 0.0F));

        tail.addOrReplaceChild("tail_r1",
                CubeListBuilder.create().texOffs(26, 0)
                        .addBox(-2.0F, -1.0F, -0.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -8.5F, 3.5F, 0.5236F, 0.0F, 0.0F));

        PartDefinition wingLeft = root.addOrReplaceChild("wing_left",
                CubeListBuilder.create(),
                PartPose.offset(2.5F, 14.5F, 0.0F));

        wingLeft.addOrReplaceChild("wing_left_r1",
                CubeListBuilder.create().texOffs(0, 21)
                        .addBox(0.0F, -1.0F, -1.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition wingRight = root.addOrReplaceChild("wing_right",
                CubeListBuilder.create(),
                PartPose.offset(-2.5F, 14.5F, 0.0F));

        wingRight.addOrReplaceChild("wing_right_r1",
                CubeListBuilder.create().texOffs(14, 21)
                        .addBox(-1.0F, -1.0F, -1.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    public void applyAnimation(AnimationDefinition definition, float ageInTicks, float weight) {
        body.resetPose();
        head.resetPose();
        beak.resetPose();
        tail.resetPose();
        wingLeft.resetPose();
        wingRight.resetPose();
        bakedAnimations.computeIfAbsent(definition, d -> d.bake(this.root)).apply((long) (ageInTicks * 50F), weight);
    }

    @Override
    public void setupAnim(CrowRenderState state) {
        
    }
}