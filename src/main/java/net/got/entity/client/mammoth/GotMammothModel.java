package net.got.entity.client.mammoth;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.joml.Vector3f;

/**
 * Custom mammoth model for {@link net.got.entity.mammoth.GotMammothEntity}.
 *
 * <p>Geometry imported from mammoth.bbmodel (Blockbench 5.1.4).
 *
 * <p>Part hierarchy:
 * <pre>
 *   root
 *   ├── body
 *   ├── head
 *   │   ├── trunk_a
 *   │   │   └── trunk_b
 *   │   │       └── trunk_c
 *   │   │           └── trunk_d
 *   │   ├── tusk_left
 *   │   │   └── tusk_left_a
 *   │   │       └── tusk_left_b
 *   │   │           └── tusk_left_c
 *   │   │               └── tusk_left_d
 *   │   └── tusk_right
 *   │       └── tusk_right_a
 *   │           └── tusk_right_b
 *   │               └── tusk_right_c
 *   │                   └── tusk_right_d
 *   ├── tail
 *   ├── leg_front_left
 *   ├── leg_front_right
 *   ├── leg_back_left
 *   ├── leg_back_right
 *   └── shoulder
 * </pre>
 *
 * <p>Animations driven by {@link GotMammothAnimations} via {@link #applyAnimation}.
 */
public class GotMammothModel extends EntityModel<GotMammothRenderState> {

    // ── Primary animatable parts ──────────────────────────────────────────────
    final ModelPart body;
    final ModelPart head;
    // Trunk — 4 segments
    final ModelPart trunkA;
    final ModelPart trunkB;
    final ModelPart trunkC;
    final ModelPart trunkD;
    // Tusks — 4 segments each
    final ModelPart tuskLeft;
    final ModelPart tuskLeftA;
    final ModelPart tuskLeftB;
    final ModelPart tuskLeftC;
    final ModelPart tuskLeftD;
    final ModelPart tuskRight;
    final ModelPart tuskRightA;
    final ModelPart tuskRightB;
    final ModelPart tuskRightC;
    final ModelPart tuskRightD;
    // Tail + legs + shoulder
    final ModelPart tail;
    final ModelPart legFrontLeft;
    final ModelPart legFrontRight;
    final ModelPart legBackLeft;
    final ModelPart legBackRight;
    final ModelPart shoulder;

    public GotMammothModel(ModelPart root) {
        super(root);
        // Blockbench wraps everything in a "root" child of the mesh root
        ModelPart r = root.getChild("root");
        this.body          = r.getChild("body");
        this.head          = r.getChild("head");
        this.trunkA        = this.head.getChild("trunk_a");
        this.trunkB        = this.trunkA.getChild("trunk_b");
        this.trunkC        = this.trunkB.getChild("trunk_c");
        this.trunkD        = this.trunkC.getChild("trunk_d");
        this.tuskLeft      = this.head.getChild("tusk_left");
        this.tuskLeftA     = this.tuskLeft.getChild("tusk_left_a");
        this.tuskLeftB     = this.tuskLeftA.getChild("tusk_left_b");
        this.tuskLeftC     = this.tuskLeftB.getChild("tusk_left_c");
        this.tuskLeftD     = this.tuskLeftC.getChild("tusk_left_d");
        this.tuskRight     = this.head.getChild("tusk_right");
        this.tuskRightA    = this.tuskRight.getChild("tusk_right_a");
        this.tuskRightB    = this.tuskRightA.getChild("tusk_right_b");
        this.tuskRightC    = this.tuskRightB.getChild("tusk_right_c");
        this.tuskRightD    = this.tuskRightC.getChild("tusk_right_d");
        this.tail          = r.getChild("tail");
        this.legFrontLeft  = r.getChild("leg_front_left");
        this.legFrontRight = r.getChild("leg_front_right");
        this.legBackLeft   = r.getChild("leg_back_left");
        this.legBackRight  = r.getChild("leg_back_right");
        this.shoulder      = r.getChild("shoulder");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        // ── Body ──────────────────────────────────────────────────────────────
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        body.addOrReplaceChild("body_torso_r1",
                CubeListBuilder.create().texOffs(0, 35)
                        .addBox(-10.0F, 7.1745F, -8.0038F, 20.0F, 14.0F, 17.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 11.0F, 9.0F, -1.5272F, 0.0F, 0.0F));

        body.addOrReplaceChild("body_r1",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-10.0F, 6.0F, -7.0F, 20.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -8.0F, 7.0F, -1.4399F, 0.0F, 0.0F));

        // ── Head ──────────────────────────────────────────────────────────────
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 66)
                        .addBox(-7.0F, 1.8481F, -11.7365F, 14.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.0F, -14.0F, 0.1745F, 0.0F, 0.0F));

        // Trunk — segment A (upper, attaches to head)
        PartDefinition trunk_a = head.addOrReplaceChild("trunk_a",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -5.5F, -7.0F, 0.3491F, 0.0F, 0.0F));

        trunk_a.addOrReplaceChild("trunk_a_r1",
                CubeListBuilder.create().texOffs(100, 94)
                        .addBox(-2.0F, -9.3397F, -32.134F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 15.5F, 23.2F, -0.1745F, 0.0F, 0.0F));

        // Trunk — segment B
        PartDefinition trunk_b = trunk_a.addOrReplaceChild("trunk_b",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -8.5F, -0.5F, 0.5236F, 0.0F, 0.0F));

        trunk_b.addOrReplaceChild("trunk_b_r1",
                CubeListBuilder.create().texOffs(104, 17)
                        .addBox(-1.5F, -21.8695F, -33.6688F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 41.3F, -1.6F, -0.9163F, 0.0F, 0.0F));

        // Trunk — segment C
        PartDefinition trunk_c = trunk_b.addOrReplaceChild("trunk_c",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        trunk_c.addOrReplaceChild("trunk_c_r1",
                CubeListBuilder.create().texOffs(106, 37)
                        .addBox(-1.5F, -20.8695F, -33.6688F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 38.3F, -7.0F, -1.2217F, 0.0F, 0.0F));

        // Trunk — segment D (tip)
        PartDefinition trunk_d = trunk_c.addOrReplaceChild("trunk_d",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        trunk_d.addOrReplaceChild("trunk_d_r1",
                CubeListBuilder.create().texOffs(36, 107)
                        .addBox(-0.5F, -20.8695F, -33.6688F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, 31.2F, -19.0F, -1.7017F, 0.0F, 0.0F));

        // ── Left Tusk ─────────────────────────────────────────────────────────
        PartDefinition tusk_left = head.addOrReplaceChild("tusk_left",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(4.0F, -5.0F, -9.5F, -0.1745F, -0.2618F, 0.0F));

        PartDefinition tusk_left_a = tusk_left.addOrReplaceChild("tusk_left_a",
                CubeListBuilder.create(),
                PartPose.offset(-4.0F, 11.0F, 23.5F));

        tusk_left_a.addOrReplaceChild("tusk_left_a_r1",
                CubeListBuilder.create().texOffs(102, 52)
                        .addBox(0.6671F, -4.8225F, -26.4664F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.3F, -10.0F, 3.2F, 0.2618F, 0.1309F, 0.3054F));

        PartDefinition tusk_left_b = tusk_left_a.addOrReplaceChild("tusk_left_b",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tusk_left_b.addOrReplaceChild("tusk_left_b_r1",
                CubeListBuilder.create().texOffs(102, 74)
                        .addBox(1.6671F, -3.8225F, -26.4664F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(8.4F, -16.0F, 2.2F, 0.2618F, 0.1309F, 0.3054F));

        PartDefinition tusk_left_c = tusk_left_b.addOrReplaceChild("tusk_left_c",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tusk_left_c.addOrReplaceChild("tusk_left_c_r1",
                CubeListBuilder.create().texOffs(0, 107)
                        .addBox(-2.0825F, -6.0188F, -0.547F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(8.3F, -13.0F, -24.5F, 0.8727F, 0.0F, 0.0F));

        PartDefinition tusk_left_d = tusk_left_c.addOrReplaceChild("tusk_left_d",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tusk_left_d.addOrReplaceChild("tusk_left_d_r1",
                CubeListBuilder.create().texOffs(20, 107)
                        .addBox(-1.0825F, -4.0188F, -0.547F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.5F, -16.0F, -29.9F, 1.6636F, 0.3477F, 0.0317F));

        // ── Right Tusk ────────────────────────────────────────────────────────
        PartDefinition tusk_right = head.addOrReplaceChild("tusk_right",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-4.0F, -5.0F, -9.5F, -0.1745F, 0.2618F, 0.0F));

        PartDefinition tusk_right_a = tusk_right.addOrReplaceChild("tusk_right_a",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tusk_right_a.addOrReplaceChild("tusk_right_a_r1",
                CubeListBuilder.create().texOffs(102, 63)
                        .addBox(-5.6671F, -4.8225F, -26.4664F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.3F, 1.0F, 26.7F, 0.2618F, -0.1309F, -0.3054F));

        PartDefinition tusk_right_b = tusk_right_a.addOrReplaceChild("tusk_right_b",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tusk_right_b.addOrReplaceChild("tusk_left_b_r2",
                CubeListBuilder.create().texOffs(106, 28)
                        .addBox(-5.6671F, -3.8225F, -26.4664F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.4F, -5.0F, 25.7F, 0.2618F, -0.1309F, -0.3054F));

        PartDefinition tusk_right_c = tusk_right_b.addOrReplaceChild("tusk_right_c",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tusk_right_c.addOrReplaceChild("tusk_right_c_r1",
                CubeListBuilder.create().texOffs(10, 107)
                        .addBox(-0.9175F, -6.0188F, -0.547F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.2F, -2.0F, -1.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition tusk_right_d = tusk_right_c.addOrReplaceChild("tusk_right_d",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tusk_right_d.addOrReplaceChild("tusk_left_d_r2",
                CubeListBuilder.create().texOffs(28, 107)
                        .addBox(-0.9175F, -4.0188F, -0.547F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.5F, -5.0F, -6.4F, 1.6636F, -0.3477F, -0.0317F));

        // ── Tail ──────────────────────────────────────────────────────────────
        PartDefinition tail = root.addOrReplaceChild("tail",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -10.0F, 13.5F, -0.5236F, 0.0F, 0.0F));

        tail.addOrReplaceChild("tail_r1",
                CubeListBuilder.create().texOffs(48, 100)
                        .addBox(-1.5F, -6.5572F, 12.9887F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 19.0F, 18.5F, 1.7453F, 0.0F, 0.0F));

        // ── Legs ──────────────────────────────────────────────────────────────
        PartDefinition leg_front_left = root.addOrReplaceChild("leg_front_left",
                CubeListBuilder.create().texOffs(76, 17)
                        .addBox(-3.0F, -2.0F, -3.7F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(6.5F, -10.0F, -9.0F));

        leg_front_left.addOrReplaceChild("leg_front_left_c_r1",
                CubeListBuilder.create().texOffs(74, 100)
                        .addBox(-10.0F, -17.9128F, -11.1038F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.5F, 4.0F, 7.2F, 0.0F, 0.0F, 0.0F));

        leg_front_left.addOrReplaceChild("leg_front_b_r1",
                CubeListBuilder.create().texOffs(0, 92)
                        .addBox(4.0F, -20.9128F, -11.1038F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-6.5F, 9.9F, 9.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leg_front_right = root.addOrReplaceChild("leg_front_right",
                CubeListBuilder.create().texOffs(74, 52)
                        .addBox(-4.0F, -2.0F, -3.7F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.5F, -10.0F, -9.0F));

        leg_front_right.addOrReplaceChild("leg_front_right_c_r1",
                CubeListBuilder.create().texOffs(100, 84)
                        .addBox(-10.0F, -17.9128F, -11.1038F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(6.5F, 4.0F, 7.2F, 0.0F, 0.0F, 0.0F));

        leg_front_right.addOrReplaceChild("leg_front_right_b_r1",
                CubeListBuilder.create().texOffs(24, 92)
                        .addBox(-10.0F, -20.9128F, -11.1038F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(6.5F, 10.0F, 9.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leg_back_left = root.addOrReplaceChild("leg_back_left",
                CubeListBuilder.create()
                        .texOffs(52, 84).addBox(-2.5F, -14.0F, 4.1F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(74, 35).addBox(-4.0F, -4.0F, 3.3F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(6.5F, -10.0F, 9.0F));

        PartDefinition leg_back_right = root.addOrReplaceChild("leg_back_right",
                CubeListBuilder.create()
                        .texOffs(76, 84).addBox(-3.5F, -14.0F, 4.1F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(76, 0).addBox(-4.0F, -4.0F, 3.3F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.5F, -10.0F, 9.0F));

        // ── Shoulder ──────────────────────────────────────────────────────────
        PartDefinition shoulder = root.addOrReplaceChild("shoulder",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        shoulder.addOrReplaceChild("shoulder_r1",
                CubeListBuilder.create().texOffs(52, 66)
                        .addBox(-6.0F, -5.0F, -12.0F, 12.0F, 5.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    private static final Vector3f ANIM_VEC = new Vector3f();

    /**
     * Apply a keyframe animation to this model.
     * All parts are reset to their rest pose before each application so
     * animations are self-contained and do not accumulate across frames.
     */
    public void applyAnimation(AnimationDefinition definition, float ageInTicks, float weight) {
        // Reset every animated part to its rest pose
        body.resetPose();
        head.resetPose();
        trunkA.resetPose();
        trunkB.resetPose();
        trunkC.resetPose();
        trunkD.resetPose();
        tuskLeft.resetPose();
        tuskLeftA.resetPose();
        tuskLeftB.resetPose();
        tuskLeftC.resetPose();
        tuskLeftD.resetPose();
        tuskRight.resetPose();
        tuskRightA.resetPose();
        tuskRightB.resetPose();
        tuskRightC.resetPose();
        tuskRightD.resetPose();
        tail.resetPose();
        legFrontLeft.resetPose();
        legFrontRight.resetPose();
        legBackLeft.resetPose();
        legBackRight.resetPose();
        shoulder.resetPose();
        KeyframeAnimations.animate(this, definition, (long) (ageInTicks * 50F), weight, ANIM_VEC);
    }

    @Override
    public void setupAnim(GotMammothRenderState state) {
        // Intentionally empty — handled by applyAnimation() in GotMammothRenderer.
    }
}
