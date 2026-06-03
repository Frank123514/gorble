package net.got.entity.client.direwolf;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.joml.Vector3f;

/**
 * Custom direwolf model for {@link net.got.entity.direwolf.GotDirewolfEntity}.
 *
 * <p>Geometry imported from direwolf.bbmodel (Blockbench 5.1.4).
 *
 * <p>Part hierarchy:
 * <pre>
 *   root
 *   ├── body
 *   ├── neck
 *   ├── head
 *   │   ├── snout
 *   │   │   └── jaw
 *   │   ├── ear_left
 *   │   └── ear_right
 *   ├── tail_a
 *   │   └── tail_b
 *   │       └── tail_c
 *   ├── shoulder_front_left
 *   │   └── leg_front_left
 *   │       └── leg_front_left_lower
 *   ├── shoulder_front_right
 *   │   └── leg_front_right
 *   │       └── leg_front_right_lower
 *   ├── shoulder_back_left
 *   │   └── leg_back_left
 *   │       └── leg_back_left_lower
 *   └── shoulder_back_right
 *       └── leg_back_right
 *           └── leg_back_right_lower
 * </pre>
 *
 * <p>Animations driven by {@link GotDirewolfAnimations} via {@link #applyAnimation},
 * called each frame from {@link GotDirewolfRenderer}.
 */
public class GotDirewolfModel extends EntityModel<GotDirewolfRenderState> {

    // ── Primary animatable parts ──────────────────────────────────────────────
    final ModelPart body;
    final ModelPart neck;
    final ModelPart head;
    final ModelPart snout;
    final ModelPart jaw;
    final ModelPart earLeft;
    final ModelPart earRight;
    // Tail — 3 segments
    final ModelPart tailA;
    final ModelPart tailB;
    final ModelPart tailC;
    // Front left leg hierarchy
    final ModelPart shoulderFrontLeft;
    final ModelPart legFrontLeft;
    final ModelPart legFrontLeftLower;
    // Front right leg hierarchy
    final ModelPart shoulderFrontRight;
    final ModelPart legFrontRight;
    final ModelPart legFrontRightLower;
    // Back left leg hierarchy
    final ModelPart shoulderBackLeft;
    final ModelPart legBackLeft;
    final ModelPart legBackLeftLower;
    // Back right leg hierarchy
    final ModelPart shoulderBackRight;
    final ModelPart legBackRight;
    final ModelPart legBackRightLower;

    public GotDirewolfModel(ModelPart root) {
        super(root);
        // Blockbench wraps everything in a "root" child of the mesh root
        ModelPart r = root.getChild("root");
        this.body                = r.getChild("body");
        this.neck                = r.getChild("neck");
        this.head                = r.getChild("head");
        this.snout               = this.head.getChild("snout");
        this.jaw                 = this.snout.getChild("jaw");
        this.earLeft             = this.head.getChild("ear_left");
        this.earRight            = this.head.getChild("ear_right");
        this.tailA               = r.getChild("tail_a");
        this.tailB               = this.tailA.getChild("tail_b");
        this.tailC               = this.tailB.getChild("tail_c");
        this.shoulderFrontLeft   = r.getChild("shoulder_front_left");
        this.legFrontLeft        = this.shoulderFrontLeft.getChild("leg_front_left");
        this.legFrontLeftLower   = this.legFrontLeft.getChild("leg_front_left_lower");
        this.shoulderFrontRight  = r.getChild("shoulder_front_right");
        this.legFrontRight       = this.shoulderFrontRight.getChild("leg_front_right");
        this.legFrontRightLower  = this.legFrontRight.getChild("leg_front_right_lower");
        this.shoulderBackLeft    = r.getChild("shoulder_back_left");
        this.legBackLeft         = this.shoulderBackLeft.getChild("leg_back_left");
        this.legBackLeftLower    = this.legBackLeft.getChild("leg_back_left_lower");
        this.shoulderBackRight   = r.getChild("shoulder_back_right");
        this.legBackRight        = this.shoulderBackRight.getChild("leg_back_right");
        this.legBackRightLower   = this.legBackRight.getChild("leg_back_right_lower");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        // ── Body ──────────────────────────────────────────────────────────────
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 21)
                        .addBox(-5.5F, 1.5F, -8.8F, 11.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -11.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        body.addOrReplaceChild("body_r1",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-6.0F, 2.9924F, -4.6743F, 12.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 3.0F, -1.4835F, 0.0F, 0.0F));

        // ── Neck ──────────────────────────────────────────────────────────────
        PartDefinition neck = root.addOrReplaceChild("neck",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -9.5F, -7.0F, -0.5236F, 0.0F, 0.0F));

        neck.addOrReplaceChild("neck_r1",
                CubeListBuilder.create().texOffs(40, 36)
                        .addBox(-3.0F, -9.8986F, -9.0926F, 6.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 13.5F, 2.0F, -0.7418F, 0.0F, 0.0F));

        // ── Head ──────────────────────────────────────────────────────────────
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(40, 21)
                        .addBox(-4.0F, -1.0F, -5.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.5F, -13.0F));

        // Snout + jaw
        PartDefinition snout = head.addOrReplaceChild("snout",
                CubeListBuilder.create()
                        .texOffs(46, 0).addBox(-2.5F, 1.0F, -5.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(34, 57).addBox(-1.0F, 2.2F, -5.2F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.5F, -5.0F));

        PartDefinition jaw = snout.addOrReplaceChild("jaw",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        jaw.addOrReplaceChild("jaw_r1",
                CubeListBuilder.create().texOffs(16, 57)
                        .addBox(-3.0F, -0.0076F, -2.8257F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, 0.0F, -1.7F, -0.0873F, 0.0F, 0.0F));

        // Ears
        PartDefinition ear_left = head.addOrReplaceChild("ear_left",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(2.5F, 4.5F, -2.0F, 0.0F, 0.0F, 0.3491F));

        ear_left.addOrReplaceChild("ear_left_r1",
                CubeListBuilder.create().texOffs(70, 26)
                        .addBox(1.9824F, 0.9319F, -13.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.0F, 2.0F, 15.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-2.5F, 4.5F, -2.0F, 0.0F, 0.0F, -0.3491F));

        ear_right.addOrReplaceChild("ear_right_r1",
                CubeListBuilder.create().texOffs(70, 31)
                        .addBox(-4.9824F, 0.9319F, -13.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, 2.0F, 15.0F, 0.0F, 0.0F, 0.6109F));

        // ── Tail — 3 segments ─────────────────────────────────────────────────
        PartDefinition tail_a = root.addOrReplaceChild("tail_a",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -9.0F, 8.0F, -0.7854F, 0.0F, 0.0F));

        tail_a.addOrReplaceChild("tail_a_r1",
                CubeListBuilder.create().texOffs(58, 53)
                        .addBox(-1.0F, -21.29F, 12.4712F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, 9.0F, -8.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tail_b = tail_a.addOrReplaceChild("tail_b",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.3491F, 0.0F, 0.0F));

        tail_b.addOrReplaceChild("tail_b_r1",
                CubeListBuilder.create().texOffs(0, 57)
                        .addBox(-1.5F, -28.3121F, 10.3456F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, 15.0F, -6.8F, -0.1309F, 0.0F, 0.0F));

        PartDefinition tail_c = tail_b.addOrReplaceChild("tail_c",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tail_c.addOrReplaceChild("tail_c_r1",
                CubeListBuilder.create().texOffs(66, 43)
                        .addBox(-2.0F, -2.5777F, 0.7077F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -11.0F, 7.0F, -0.1745F, 0.0F, 0.0F));

        // ── Front Left Leg Hierarchy ───────────────────────────────────────────
        PartDefinition shoulder_front_left = root.addOrReplaceChild("shoulder_front_left",
                CubeListBuilder.create().texOffs(46, 9)
                        .addBox(-1.5F, -2.0F, -3.0F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(4.0F, -13.0F, -6.0F));

        PartDefinition leg_front_left = shoulder_front_left.addOrReplaceChild("leg_front_left",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leg_front_left.addOrReplaceChild("leg_front_left_upper_r1",
                CubeListBuilder.create().texOffs(16, 64)
                        .addBox(3.0F, -17.9782F, -8.4995F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 13.0F, 6.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition leg_front_left_lower = leg_front_left.addOrReplaceChild("leg_front_left_lower",
                CubeListBuilder.create().texOffs(66, 48)
                        .addBox(-1.0F, -10.0F, -2.2F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leg_front_left_lower.addOrReplaceChild("leg_front_left_lower_r1",
                CubeListBuilder.create().texOffs(52, 65)
                        .addBox(-1.0F, -3.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.1F, 0.2F, 0.0873F, 0.0F, 0.0F));

        // ── Front Right Leg Hierarchy ──────────────────────────────────────────
        PartDefinition shoulder_front_right = root.addOrReplaceChild("shoulder_front_right",
                CubeListBuilder.create().texOffs(40, 53)
                        .addBox(-6.5F, -38.0F, -9.0F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition leg_front_right = shoulder_front_right.addOrReplaceChild("leg_front_right",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leg_front_right.addOrReplaceChild("leg_front_right_upper_r1",
                CubeListBuilder.create().texOffs(28, 64)
                        .addBox(-6.0F, -17.9782F, -8.4995F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -23.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition leg_front_right_lower = leg_front_right.addOrReplaceChild("leg_front_right_lower",
                CubeListBuilder.create().texOffs(0, 68)
                        .addBox(-1.0F, -3.9F, -2.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, -42.1F, -5.8F));

        leg_front_right_lower.addOrReplaceChild("leg_front_right_lower_r1",
                CubeListBuilder.create().texOffs(66, 0)
                        .addBox(-1.0F, -3.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        // ── Back Left Leg Hierarchy ────────────────────────────────────────────
        PartDefinition shoulder_back_left = root.addOrReplaceChild("shoulder_back_left",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 23.0F, 0.0F));

        shoulder_back_left.addOrReplaceChild("shoulder_back_left_r1",
                CubeListBuilder.create().texOffs(0, 44)
                        .addBox(-2.0F, -2.0347F, -2.803F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.5F, -36.0F, 12.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition leg_back_left = shoulder_back_left.addOrReplaceChild("leg_back_left",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leg_back_left.addOrReplaceChild("leg_back_left_upper_r1",
                CubeListBuilder.create().texOffs(40, 64)
                        .addBox(3.0F, -18.7957F, 10.3823F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -24.3F, -0.5F, -0.1309F, 0.0F, 0.0F));

        PartDefinition leg_back_left_lower = leg_back_left.addOrReplaceChild("leg_back_left_lower",
                CubeListBuilder.create().texOffs(70, 18)
                        .addBox(-6.0F, -46.0F, 11.7F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leg_back_left_lower.addOrReplaceChild("leg_back_left_lower_r1",
                CubeListBuilder.create().texOffs(64, 65)
                        .addBox(-1.0F, -3.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, -42.1F, 14.1F, 0.0873F, 0.0F, 0.0F));

        // ── Back Right Leg Hierarchy ───────────────────────────────────────────
        PartDefinition shoulder_back_right = root.addOrReplaceChild("shoulder_back_right",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 23.0F, 0.0F));

        shoulder_back_right.addOrReplaceChild("shoulder_back_right_r1",
                CubeListBuilder.create().texOffs(20, 44)
                        .addBox(-2.0F, -2.0347F, -2.803F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.5F, -36.0F, 12.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition leg_back_right = shoulder_back_right.addOrReplaceChild("leg_back_right",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leg_back_right.addOrReplaceChild("leg_back_right_upper_r1",
                CubeListBuilder.create().texOffs(64, 9)
                        .addBox(-5.0F, -19.7957F, 10.3823F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, -23.3F, -0.7F, -0.1309F, 0.0F, 0.0F));

        PartDefinition leg_back_right_lower = leg_back_right.addOrReplaceChild("leg_back_right_lower",
                CubeListBuilder.create().texOffs(70, 22)
                        .addBox(3.0F, -46.0F, 11.7F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leg_back_right_lower.addOrReplaceChild("leg_back_right_lower_r1",
                CubeListBuilder.create().texOffs(66, 36)
                        .addBox(-1.0F, -3.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -42.1F, 14.1F, 0.0873F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    private static final Vector3f ANIM_VEC = new Vector3f();

    /**
     * Apply a keyframe animation to this model.
     * All parts are reset to their rest pose before each application so
     * animations are self-contained and do not accumulate across frames.
     */
    public void applyAnimation(AnimationDefinition definition, float ageInTicks, float weight) {
        body.resetPose();
        neck.resetPose();
        head.resetPose();
        snout.resetPose();
        jaw.resetPose();
        earLeft.resetPose();
        earRight.resetPose();
        tailA.resetPose();
        tailB.resetPose();
        tailC.resetPose();
        shoulderFrontLeft.resetPose();
        legFrontLeft.resetPose();
        legFrontLeftLower.resetPose();
        shoulderFrontRight.resetPose();
        legFrontRight.resetPose();
        legFrontRightLower.resetPose();
        shoulderBackLeft.resetPose();
        legBackLeft.resetPose();
        legBackLeftLower.resetPose();
        shoulderBackRight.resetPose();
        legBackRight.resetPose();
        legBackRightLower.resetPose();
        KeyframeAnimations.animate(this, definition, (long) (ageInTicks * 50F), weight, ANIM_VEC);
    }

    @Override
    public void setupAnim(GotDirewolfRenderState state) {
        // Intentionally empty — handled by applyAnimation() in GotDirewolfRenderer.
    }
}
