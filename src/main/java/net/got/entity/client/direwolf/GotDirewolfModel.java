package net.got.entity.client.direwolf;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.joml.Vector3f;

/**
 * Custom direwolf model for {@link net.got.entity.direwolf.GotDirewolfEntity},
 * adapted from direwolf.bbmodel via Blockbench 5.1.4.
 *
 * Animations are driven by {@link GotDirewolfAnimations} via
 * {@link #applyAnimation}, called each frame from {@link GotDirewolfRenderer}.
 */
public class GotDirewolfModel extends EntityModel<GotDirewolfRenderState> {

    final ModelPart body;
    final ModelPart neck;
    final ModelPart head;
    final ModelPart snout;
    final ModelPart jaw;
    final ModelPart ear_right;
    final ModelPart ear_left;
    final ModelPart tail_a;
    final ModelPart tail_b;
    final ModelPart tail_c;
    final ModelPart shoulder_front_left;
    final ModelPart leg_front_left;
    final ModelPart leg_front_left_lower;
    final ModelPart shoulder_front_right;
    final ModelPart leg_front_right;
    final ModelPart leg_front_right_lower;
    final ModelPart shoulder_back_left;
    final ModelPart leg_back_left;
    final ModelPart leg_back_left_lower;
    final ModelPart shoulder_back_right;
    final ModelPart leg_back_right;
    final ModelPart leg_back_right_lower;

    public GotDirewolfModel(ModelPart root) {
        super(root);
        this.body                  = root.getChild("body");
        this.neck                  = root.getChild("neck");
        this.head                  = root.getChild("head");
        this.snout                 = this.head.getChild("snout");
        this.jaw                   = this.snout.getChild("jaw");
        this.ear_right             = this.head.getChild("ear_right");
        this.ear_left              = this.head.getChild("ear_left");
        this.tail_a                = root.getChild("tail_a");
        this.tail_b                = this.tail_a.getChild("tail_b");
        this.tail_c                = this.tail_b.getChild("tail_c");
        this.shoulder_front_left   = root.getChild("shoulder_front_left");
        this.leg_front_left        = this.shoulder_front_left.getChild("leg_front_left");
        this.leg_front_left_lower  = this.leg_front_left.getChild("leg_front_left_lower");
        this.shoulder_front_right  = root.getChild("shoulder_front_right");
        this.leg_front_right       = this.shoulder_front_right.getChild("leg_front_right");
        this.leg_front_right_lower = this.leg_front_right.getChild("leg_front_right_lower");
        this.shoulder_back_left    = root.getChild("shoulder_back_left");
        this.leg_back_left         = this.shoulder_back_left.getChild("leg_back_left");
        this.leg_back_left_lower   = this.leg_back_left.getChild("leg_back_left_lower");
        this.shoulder_back_right   = root.getChild("shoulder_back_right");
        this.leg_back_right        = this.shoulder_back_right.getChild("leg_back_right");
        this.leg_back_right_lower  = this.leg_back_right.getChild("leg_back_right_lower");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 21).addBox(-5.5F, -18.7856F, -2.9829F, 11.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -3.0F, -1.5708F, 0.0F, 0.0F));
        body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -11.9962F, -4.5872F, 12.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7144F, 7.8171F, 1.4835F, 0.0F, 0.0F));

        PartDefinition neck = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 5.5F, -10.0F, 0.5236F, 0.0F, 0.0F));
        neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(40, 36).addBox(-3.0F, 0.1993F, -10.0463F, 6.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.7006F, 2.6058F, 0.7418F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(40, 21).addBox(-4.0F, -4.1819F, -2.8333F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, -15.0F));

        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create()
                .texOffs(46, 0).addBox(-2.5F, -2.0659F, -1.9571F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(34, 57).addBox(-1.0F, -2.2659F, -2.1571F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.3181F, -5.8333F));

        PartDefinition jaw = snout.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 1.9341F, 3.0429F));
        jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(16, 57).addBox(-3.0F, -0.9962F, -2.9128F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -1.7F, 0.0873F, 0.0F, 0.0F));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create(), PartPose.offsetAndRotation(3.5F, -4.6819F, 2.1667F, 0.0F, 0.0F, -0.3491F));
        ear_right.addOrReplaceChild("ear_right_r1", CubeListBuilder.create().texOffs(70, 26).addBox(2.2412F, -3.9659F, -13.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.6237F, -0.4626F, 13.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -4.6819F, 2.1667F, 0.0F, 0.0F, 0.3491F));
        ear_left.addOrReplaceChild("ear_left_r1", CubeListBuilder.create().texOffs(70, 31).addBox(-5.2412F, -3.9659F, -13.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.684F, -0.1206F, 13.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition tail_a = partdefinition.addOrReplaceChild("tail_a", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 7.0F, 16.0F, 0.7854F, 0.0F, 0.0F));
        tail_a.addOrReplaceChild("tail_a_r1", CubeListBuilder.create().texOffs(58, 53).addBox(-1.0F, 12.8635F, 11.6521F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1653F, -12.7457F, -15.4922F, 0.1745F, 0.0F, 0.0F));

        PartDefinition tail_b = tail_a.addOrReplaceChild("tail_b", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6653F, 8.7543F, 1.5078F, 0.3491F, 0.0F, 0.0F));
        tail_b.addOrReplaceChild("tail_b_r1", CubeListBuilder.create().texOffs(0, 57).addBox(-1.5F, 21.6128F, 9.3919F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4227F, -22.6592F, -13.7399F, 0.1309F, 0.0F, 0.0F));

        PartDefinition tail_c = tail_b.addOrReplaceChild("tail_c", CubeListBuilder.create(), PartPose.offset(0.0773F, 5.3408F, 1.0601F));
        tail_c.addOrReplaceChild("tail_c_r1", CubeListBuilder.create().texOffs(66, 43).addBox(-2.0F, 0.8365F, -0.2582F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -2.0F, -1.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition shoulder_front_left = partdefinition.addOrReplaceChild("shoulder_front_left", CubeListBuilder.create().texOffs(46, 9).addBox(-1.5F, -4.3188F, -1.9362F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 14.0F, -6.0F));

        PartDefinition leg_front_left = shoulder_front_left.addOrReplaceChild("leg_front_left", CubeListBuilder.create(), PartPose.offset(0.0F, 2.6812F, 0.0638F));
        leg_front_left.addOrReplaceChild("leg_front_left_upper_r1", CubeListBuilder.create().texOffs(28, 64).addBox(3.0F, 12.9782F, -8.4995F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -16.0F, 7.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition leg_front_left_lower = leg_front_left.addOrReplaceChild("leg_front_left_lower", CubeListBuilder.create().texOffs(66, 48).addBox(-1.0F, 2.9337F, -2.2025F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 1.0F));
        leg_front_left_lower.addOrReplaceChild("leg_front_left_lower_r1", CubeListBuilder.create().texOffs(52, 65).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0337F, 0.1975F, -0.0873F, 0.0F, 0.0F));

        PartDefinition shoulder_front_right = partdefinition.addOrReplaceChild("shoulder_front_right", CubeListBuilder.create().texOffs(40, 53).addBox(-2.5F, -4.1221F, -2.0255F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 14.0F, -6.0F));

        PartDefinition leg_front_right = shoulder_front_right.addOrReplaceChild("leg_front_right", CubeListBuilder.create(), PartPose.offset(-1.0F, 2.8779F, -0.0255F));
        leg_front_right.addOrReplaceChild("leg_front_right_upper_r1", CubeListBuilder.create().texOffs(40, 64).addBox(-1.0F, -2.7449F, -1.8073F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition leg_front_right_lower = leg_front_right.addOrReplaceChild("leg_front_right_lower", CubeListBuilder.create().texOffs(0, 68).addBox(-1.0F, 2.9058F, -2.4F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.1F, 1.2F));
        leg_front_right_lower.addOrReplaceChild("leg_front_right_lower_r1", CubeListBuilder.create().texOffs(66, 0).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0058F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition shoulder_back_left = partdefinition.addOrReplaceChild("shoulder_back_left", CubeListBuilder.create(), PartPose.offset(4.0F, 14.0F, 11.0F));
        shoulder_back_left.addOrReplaceChild("shoulder_back_left_r1", CubeListBuilder.create().texOffs(0, 44).addBox(-2.0F, -4.9653F, -2.803F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.5141F, -0.0598F, 0.1745F, 0.0F, 0.0F));

        PartDefinition leg_back_left = shoulder_back_left.addOrReplaceChild("leg_back_left", CubeListBuilder.create(), PartPose.offset(1.0F, 3.5141F, 1.4402F));
        leg_back_left.addOrReplaceChild("leg_back_left_upper_r1", CubeListBuilder.create().texOffs(64, 9).addBox(-1.0F, -4.8948F, -1.4095F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.3F, -0.5F, 0.1309F, 0.0F, 0.0F));

        PartDefinition leg_back_left_lower = leg_back_left.addOrReplaceChild("leg_back_left_lower", CubeListBuilder.create().texOffs(70, 22).addBox(-2.0F, 3.0F, -2.3F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));
        leg_back_left_lower.addOrReplaceChild("leg_back_left_lower_r1", CubeListBuilder.create().texOffs(64, 65).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.1F, 0.1F, -0.0873F, 0.0F, 0.0F));

        PartDefinition shoulder_back_right = partdefinition.addOrReplaceChild("shoulder_back_right", CubeListBuilder.create(), PartPose.offset(-5.0F, 13.0F, 11.0F));
        shoulder_back_right.addOrReplaceChild("shoulder_back_right_r1", CubeListBuilder.create().texOffs(20, 44).addBox(-2.0F, -4.9653F, -2.803F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 1.5131F, -0.0162F, 0.1745F, 0.0F, 0.0F));

        PartDefinition leg_back_right = shoulder_back_right.addOrReplaceChild("leg_back_right", CubeListBuilder.create(), PartPose.offset(1.0F, 4.5131F, 0.4838F));
        leg_back_right.addOrReplaceChild("leg_back_right_upper_r1", CubeListBuilder.create().texOffs(16, 64).addBox(-1.0F, -3.8948F, -1.4095F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.3F, 0.3F, 0.1309F, 0.0F, 0.0F));

        PartDefinition leg_back_right_lower = leg_back_right.addOrReplaceChild("leg_back_right_lower", CubeListBuilder.create().texOffs(70, 18).addBox(-1.0F, 3.0F, -2.3F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 3.0F, 1.0F));
        leg_back_right_lower.addOrReplaceChild("leg_back_right_lower_r1", CubeListBuilder.create().texOffs(66, 36).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.1F, 0.1F, -0.0873F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    // ── Animation ─────────────────────────────────────────────────────────────

    private static final Vector3f ANIM_VEC = new Vector3f();

    public void applyAnimation(AnimationDefinition definition, float ageInTicks, float weight) {
        body.resetPose();
        neck.resetPose();
        head.resetPose();
        snout.resetPose();
        jaw.resetPose();
        ear_right.resetPose();
        ear_left.resetPose();
        tail_a.resetPose();
        tail_b.resetPose();
        tail_c.resetPose();
        shoulder_front_left.resetPose();
        leg_front_left.resetPose();
        leg_front_left_lower.resetPose();
        shoulder_front_right.resetPose();
        leg_front_right.resetPose();
        leg_front_right_lower.resetPose();
        shoulder_back_left.resetPose();
        leg_back_left.resetPose();
        leg_back_left_lower.resetPose();
        shoulder_back_right.resetPose();
        leg_back_right.resetPose();
        leg_back_right_lower.resetPose();
        KeyframeAnimations.animate(this, definition, (long)(ageInTicks * 50F), weight, ANIM_VEC);
    }

    @Override
    public void setupAnim(GotDirewolfRenderState state) {
        // Handled by applyAnimation() in GotDirewolfRenderer.
    }
}