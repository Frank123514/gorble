package net.got.event.entity.client.heron;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom heron model for {@link net.got.event.entity.heron.GotHeronEntity},
 * exported from heronmodel.bbmodel via Blockbench 5.1.4.
 *
 * <p>Animations are driven by {@link GotHeronAnimations} via
 * {@link #applyAnimation}, called each frame from {@link GotHeronRenderer}.
 *
 * <p>neck is now a root-level sibling of head so the two can be animated
 * independently (fixes the flying pose head/neck detachment).
 */
public class GotHeronModel extends EntityModel<GotHeronRenderState> {

    final ModelPart body;
    final ModelPart head;
    final ModelPart beak;
    final ModelPart neck;
    final ModelPart leg0;
    final ModelPart leg1;
    final ModelPart wing0;
    final ModelPart wing1;
    final ModelPart tail;
    private final ModelPart root;
    private final Map<AnimationDefinition, KeyframeAnimation> bakedAnimations = new HashMap<>();

    public GotHeronModel(ModelPart root) {
        super(root);
        this.root  = root;
        this.body  = root.getChild("body");
        this.head  = root.getChild("head");
        this.beak  = this.head.getChild("beak");
        this.neck  = root.getChild("neck");   // now a root child, not under head
        this.leg0  = root.getChild("leg0");
        this.leg1  = root.getChild("leg1");
        this.wing0 = root.getChild("wing0");
        this.wing1 = root.getChild("wing1");
        this.tail  = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        body.addOrReplaceChild("body_r1",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-3.0F, -12.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 6.0F, -2.0F, -0.4363F, 0.0F, 0.0F));

        // head pivot — same offset as before, neck removed from here
        PartDefinition head = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 8.0F, -4.0F));

        head.addOrReplaceChild("head_r1",
                CubeListBuilder.create().texOffs(24, 30)
                        .addBox(-1.0F, -14.0F, -8.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.4F, 2.7F, 7.6F, 0.0524F, 0.0F, 0.0F));

        PartDefinition beak = head.addOrReplaceChild("beak",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        beak.addOrReplaceChild("beak_r1",
                CubeListBuilder.create().texOffs(36, 6)
                        .addBox(-1.0F, -12.0F, -8.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.6F, 1.7F, 5.0F, 0.0873F, 0.0F, 0.0F));

        beak.addOrReplaceChild("beak_r2",
                CubeListBuilder.create().texOffs(36, 10)
                        .addBox(-1.0F, -3.0219F, -7.3722F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.6F, -5.9F, 3.3F, -0.0873F, 0.0F, 0.0F));

        // neck is now a root-level part — same world-space pivot as before (0, 8, -4)
        PartDefinition neck = partdefinition.addOrReplaceChild("neck",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 8.0F, -4.0F));

        neck.addOrReplaceChild("head_r2",
                CubeListBuilder.create().texOffs(12, 30)
                        .addBox(-1.0F, -17.0F, -6.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.4F, 9.0F, 3.7F, -0.1309F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("leg0",
                CubeListBuilder.create().texOffs(24, 0)
                        .addBox(-1.0F, -1.0F, -3.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, 14.0F, 1.0F));

        partdefinition.addOrReplaceChild("leg1",
                CubeListBuilder.create().texOffs(0, 30)
                        .addBox(-1.0F, -1.0F, -3.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.0F, 14.0F, 1.0F));

        PartDefinition wing0 = partdefinition.addOrReplaceChild("wing0",
                CubeListBuilder.create(),
                PartPose.offset(-3.0F, 8.0F, 0.0F));

        wing0.addOrReplaceChild("wing0_r1",
                CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-4.0F, -12.0F, -3.0F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, 10.0F, -3.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition wing1 = partdefinition.addOrReplaceChild("wing1",
                CubeListBuilder.create(),
                PartPose.offset(3.0F, 8.0F, 0.0F));

        wing1.addOrReplaceChild("wing1_r1",
                CubeListBuilder.create().texOffs(20, 16)
                        .addBox(3.0F, -12.0F, -3.0F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.0F, 10.0F, -3.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        tail.addOrReplaceChild("tail_r1",
                CubeListBuilder.create().texOffs(36, 0)
                        .addBox(-2.0F, -2.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -12.4F, 3.9F, 0.3229F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    public void applyAnimation(AnimationDefinition definition, float ageInTicks, float weight) {
        body.resetPose();
        head.resetPose();
        beak.resetPose();
        neck.resetPose();
        leg0.resetPose();
        leg1.resetPose();
        wing0.resetPose();
        wing1.resetPose();
        tail.resetPose();
        bakedAnimations.computeIfAbsent(definition, d -> d.bake(this.root)).apply((long)(ageInTicks * 50F), weight);
    }

    @Override
    public void setupAnim(GotHeronRenderState state) {
        // Intentionally empty — handled by applyAnimation() in GotHeronRenderer.
    }
}
