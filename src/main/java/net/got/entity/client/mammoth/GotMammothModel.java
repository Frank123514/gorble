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
 * <p>The mammoth is the largest land creature in the mod — a massive domed head,
 * articulated trunk (two segments), curved tusks, four pillar legs, and a
 * small tufted tail.
 *
 * <p>Part hierarchy:
 * <pre>
 *   root
 *   ├── body
 *   ├── head
 *   │   ├── trunk_upper
 *   │   │   └── trunk_lower
 *   │   ├── tusk_left
 *   │   └── tusk_right
 *   ├── tail
 *   ├── leg_front_left
 *   ├── leg_front_right
 *   ├── leg_back_left
 *   └── leg_back_right
 * </pre>
 *
 * <p>Animations driven by {@link GotMammothAnimations} via {@link #applyAnimation}.
 */
public class GotMammothModel extends EntityModel<GotMammothRenderState> {

    final ModelPart body;
    final ModelPart head;
    final ModelPart trunkUpper;
    final ModelPart trunkLower;
    final ModelPart tuskLeft;
    final ModelPart tuskRight;
    final ModelPart tail;
    final ModelPart legFrontLeft;
    final ModelPart legFrontRight;
    final ModelPart legBackLeft;
    final ModelPart legBackRight;

    public GotMammothModel(ModelPart root) {
        super(root);
        this.body         = root.getChild("body");
        this.head         = root.getChild("head");
        this.trunkUpper   = this.head.getChild("trunk_upper");
        this.trunkLower   = this.trunkUpper.getChild("trunk_lower");
        this.tuskLeft     = this.head.getChild("tusk_left");
        this.tuskRight    = this.head.getChild("tusk_right");
        this.tail         = root.getChild("tail");
        this.legFrontLeft  = root.getChild("leg_front_left");
        this.legFrontRight = root.getChild("leg_front_right");
        this.legBackLeft   = root.getChild("leg_back_left");
        this.legBackRight  = root.getChild("leg_back_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // ── Body ─────────────────────────────────────────────────────────────
        // Enormous barrel body: 20W x 14H x 28D (rotated horizontal)
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        body.addOrReplaceChild("body_r1",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-10.0F, -18.0F, -7.0F, 20.0F, 14.0F, 28.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 12.0F, -3.0F, -0.1745F, 0.0F, 0.0F));

        // ── Head ─────────────────────────────────────────────────────────────
        // Large domed head: 14W x 14H x 12D
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 42)
                        .addBox(-7.0F, -8.0F, -10.0F, 14.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 6.0F, -14.0F, 0.1745F, 0.0F, 0.0F));

        // Trunk — upper segment
        PartDefinition trunkUpper = head.addOrReplaceChild("trunk_upper",
                CubeListBuilder.create().texOffs(52, 42)
                        .addBox(-2.0F, 0.0F, -3.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 5.5F, -9.0F, 0.3491F, 0.0F, 0.0F));

        // Trunk — lower segment (articulated — bends downward by default)
        trunkUpper.addOrReplaceChild("trunk_lower",
                CubeListBuilder.create().texOffs(68, 42)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 8.5F, -0.5F, 0.5236F, 0.0F, 0.0F));

        // Tusks
        head.addOrReplaceChild("tusk_left",
                CubeListBuilder.create().texOffs(52, 55)
                        .addBox(0.0F, -2.0F, -10.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, 5.0F, -9.5F, -0.1745F, -0.2618F, 0.0F));

        head.addOrReplaceChild("tusk_right",
                CubeListBuilder.create().texOffs(52, 55)
                        .addBox(-2.0F, -2.0F, -10.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 5.0F, -9.5F, -0.1745F, 0.2618F, 0.0F));

        // ── Tail ─────────────────────────────────────────────────────────────
        root.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(76, 42)
                        .addBox(-1.5F, -1.0F, 0.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 10.0F, 13.5F, -0.5236F, 0.0F, 0.0F));

        // ── Legs ─────────────────────────────────────────────────────────────
        // Massive pillar legs: 7W x 14H x 7D
        root.addOrReplaceChild("leg_front_left",
                CubeListBuilder.create().texOffs(0, 68)
                        .addBox(-3.5F, 0.0F, -3.5F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(6.5F, 10.0F, -9.0F));

        root.addOrReplaceChild("leg_front_right",
                CubeListBuilder.create().texOffs(28, 68)
                        .addBox(-3.5F, 0.0F, -3.5F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.5F, 10.0F, -9.0F));

        root.addOrReplaceChild("leg_back_left",
                CubeListBuilder.create().texOffs(56, 68)
                        .addBox(-3.5F, 0.0F, -3.5F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(6.5F, 10.0F, 9.0F));

        root.addOrReplaceChild("leg_back_right",
                CubeListBuilder.create().texOffs(84, 68)
                        .addBox(-3.5F, 0.0F, -3.5F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.5F, 10.0F, 9.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    private static final Vector3f ANIM_VEC = new Vector3f();

    public void applyAnimation(AnimationDefinition definition, float ageInTicks, float weight) {
        body.resetPose();
        head.resetPose();
        trunkUpper.resetPose();
        trunkLower.resetPose();
        tuskLeft.resetPose();
        tuskRight.resetPose();
        tail.resetPose();
        legFrontLeft.resetPose();
        legFrontRight.resetPose();
        legBackLeft.resetPose();
        legBackRight.resetPose();
        KeyframeAnimations.animate(this, definition, (long) (ageInTicks * 50F), weight, ANIM_VEC);
    }

    @Override
    public void setupAnim(GotMammothRenderState state) {
        // Intentionally empty — handled by applyAnimation() in GotMammothRenderer.
    }
}