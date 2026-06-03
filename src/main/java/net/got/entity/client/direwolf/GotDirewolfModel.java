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
 * <p>The direwolf is considerably larger and bulkier than a vanilla wolf —
 * broad chest, thick legs, heavy neck, and a long bushy tail.
 *
 * <p>Part hierarchy:
 * <pre>
 *   root
 *   ├── body
 *   ├── neck
 *   ├── head
 *   │   ├── snout
 *   │   ├── ear_left
 *   │   └── ear_right
 *   ├── tail_a
 *   │   └── tail_b
 *   ├── leg_front_left
 *   ├── leg_front_right
 *   ├── leg_back_left
 *   └── leg_back_right
 * </pre>
 *
 * <p>Animations are driven by {@link GotDirewolfAnimations} via
 * {@link #applyAnimation}, called each frame from {@link GotDirewolfRenderer}.
 */
public class GotDirewolfModel extends EntityModel<GotDirewolfRenderState> {

    final ModelPart body;
    final ModelPart neck;
    final ModelPart head;
    final ModelPart snout;
    final ModelPart earLeft;
    final ModelPart earRight;
    final ModelPart tailA;
    final ModelPart tailB;
    final ModelPart legFrontLeft;
    final ModelPart legFrontRight;
    final ModelPart legBackLeft;
    final ModelPart legBackRight;

    public GotDirewolfModel(ModelPart root) {
        super(root);
        this.body          = root.getChild("body");
        this.neck          = root.getChild("neck");
        this.head          = root.getChild("head");
        this.snout         = this.head.getChild("snout");
        this.earLeft       = this.head.getChild("ear_left");
        this.earRight      = this.head.getChild("ear_right");
        this.tailA         = root.getChild("tail_a");
        this.tailB         = this.tailA.getChild("tail_b");
        this.legFrontLeft  = root.getChild("leg_front_left");
        this.legFrontRight = root.getChild("leg_front_right");
        this.legBackLeft   = root.getChild("leg_back_left");
        this.legBackRight  = root.getChild("leg_back_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // ── Body ─────────────────────────────────────────────────────────────
        // Large barrel chest: 12W x 9H x 16D, rotated horizontal
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        body.addOrReplaceChild("body_r1",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-6.0F, -14.0F, -4.5F, 12.0F, 9.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 8.0F, -2.0F, -0.1745F, 0.0F, 0.0F));

        // ── Neck ─────────────────────────────────────────────────────────────
        PartDefinition neck = root.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(56, 0)
                        .addBox(-3.0F, -8.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 9.5F, -7.0F, -0.5236F, 0.0F, 0.0F));

        // ── Head ─────────────────────────────────────────────────────────────
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 25)
                        .addBox(-4.0F, -5.0F, -5.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 5.5F, -11.0F));

        // Snout
        head.addOrReplaceChild("snout",
                CubeListBuilder.create().texOffs(30, 25)
                        .addBox(-2.5F, -2.0F, -4.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.5F, -5.0F));

        // Ears
        head.addOrReplaceChild("ear_left",
                CubeListBuilder.create().texOffs(0, 39)
                        .addBox(0.0F, -4.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, -4.5F, -2.0F, 0.0F, 0.0F, 0.3491F));

        head.addOrReplaceChild("ear_right",
                CubeListBuilder.create().texOffs(10, 39)
                        .addBox(-3.0F, -4.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -4.5F, -2.0F, 0.0F, 0.0F, -0.3491F));

        // ── Tail ─────────────────────────────────────────────────────────────
        PartDefinition tailA = root.addOrReplaceChild("tail_a",
                CubeListBuilder.create().texOffs(56, 15)
                        .addBox(-2.0F, 0.0F, -1.5F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 9.0F, 8.0F, -0.7854F, 0.0F, 0.0F));

        tailA.addOrReplaceChild("tail_b",
                CubeListBuilder.create().texOffs(56, 27)
                        .addBox(-1.5F, 0.0F, -1.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 7.5F, 0.0F, -0.3491F, 0.0F, 0.0F));

        // ── Legs ─────────────────────────────────────────────────────────────
        // Front legs — offset forward from body center
        root.addOrReplaceChild("leg_front_left",
                CubeListBuilder.create().texOffs(0, 45)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(4.0F, 13.0F, -6.0F));

        root.addOrReplaceChild("leg_front_right",
                CubeListBuilder.create().texOffs(16, 45)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-4.0F, 13.0F, -6.0F));

        // Back legs — offset rearward from body center
        root.addOrReplaceChild("leg_back_left",
                CubeListBuilder.create().texOffs(32, 45)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(4.0F, 13.0F, 6.0F));

        root.addOrReplaceChild("leg_back_right",
                CubeListBuilder.create().texOffs(48, 45)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-4.0F, 13.0F, 6.0F));

        return LayerDefinition.create(mesh, 128, 64);
    }

    private static final Vector3f ANIM_VEC = new Vector3f();

    public void applyAnimation(AnimationDefinition definition, float ageInTicks, float weight) {
        body.resetPose();
        neck.resetPose();
        head.resetPose();
        snout.resetPose();
        earLeft.resetPose();
        earRight.resetPose();
        tailA.resetPose();
        tailB.resetPose();
        legFrontLeft.resetPose();
        legFrontRight.resetPose();
        legBackLeft.resetPose();
        legBackRight.resetPose();
        KeyframeAnimations.animate(this, definition, (long) (ageInTicks * 50F), weight, ANIM_VEC);
    }

    @Override
    public void setupAnim(GotDirewolfRenderState state) {
        // Intentionally empty — handled by applyAnimation() in GotDirewolfRenderer.
    }
}