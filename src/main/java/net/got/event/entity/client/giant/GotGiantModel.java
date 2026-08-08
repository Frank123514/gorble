package net.got.event.entity.client.giant;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom giant model for {@link net.got.event.entity.giant.GotGiantEntity}.
 *
 * <p>Anatomy overview — all measurements in model units (1 mu = 1/16 block):
 * <ul>
 *   <li>Body core: wide barrel chest, massive shoulders.</li>
 *   <li>Head: broad skull, heavy brow-ridge, thick neck, full beard.</li>
 *   <li>Arms: extremely long, reaching near the ground when idle.</li>
 *   <li>Legs: thick pillars; slightly digitigrade posture.</li>
 *   <li>Club: attached to right hand as a child part.</li>
 * </ul>
 *
 * <p>Animations are applied each frame inside {@link #setupAnim(GotGiantRenderState)}
 * using the clip selected by {@link GotGiantRenderer#extractRenderState}.
 *
 * <p>Model exported from Blockbench 5.1.4 (256×256 texture atlas).
 */
public class GotGiantModel extends EntityModel<GotGiantRenderState> {

    // ── Model part references ─────────────────────────────────────────────────

    final ModelPart root;
    final ModelPart body;
    final ModelPart shoulders;   // replaces "chest" — child of body
    final ModelPart waist;
    final ModelPart neck;
    final ModelPart head;
    final ModelPart beard;       // replaces "brow"/"jaw" — child of head
    // Arms
    final ModelPart arm_upper_right;
    final ModelPart arm_lower_right;
    final ModelPart hand_right;
    final ModelPart club;        // child of hand_right
    final ModelPart arm_upper_left;
    final ModelPart arm_lower_left;
    final ModelPart hand_left;
    // Legs
    final ModelPart leg_upper_right;
    final ModelPart leg_lower_right;
    final ModelPart foot_right;
    final ModelPart leg_upper_left;
    final ModelPart leg_lower_left;
    final ModelPart foot_left;
    private final Map<AnimationDefinition, KeyframeAnimation> bakedAnimations = new HashMap<>();


    // ── Constructor ───────────────────────────────────────────────────────────

    public GotGiantModel(ModelPart root) {
        super(root);
        this.root            = root;
        this.body            = root.getChild("body");
        this.shoulders       = this.body.getChild("shoulders");
        this.waist           = this.body.getChild("waist");
        this.neck            = root.getChild("neck");
        this.head            = root.getChild("head");
        this.beard           = this.head.getChild("beard");
        // Right arm chain
        this.arm_upper_right = root.getChild("arm_upper_right");
        this.arm_lower_right = this.arm_upper_right.getChild("arm_lower_right");
        this.hand_right      = this.arm_lower_right.getChild("hand_right");
        this.club            = this.hand_right.getChild("club");
        // Left arm chain
        this.arm_upper_left  = root.getChild("arm_upper_left");
        this.arm_lower_left  = this.arm_upper_left.getChild("arm_lower_left");
        this.hand_left       = this.arm_lower_left.getChild("hand_left");
        // Right leg chain
        this.leg_upper_right = root.getChild("leg_upper_right");
        this.leg_lower_right = this.leg_upper_right.getChild("leg_lower_right");
        this.foot_right      = this.leg_lower_right.getChild("foot_right");
        // Left leg chain
        this.leg_upper_left  = root.getChild("leg_upper_left");
        this.leg_lower_left  = this.leg_upper_left.getChild("leg_lower_left");
        this.foot_left       = this.leg_lower_left.getChild("foot_left");
    }

    // ── Layer definition (geometry) ───────────────────────────────────────────

    /**
     * Builds the giant mesh from the Blockbench 5.1.4 export.
     *
     * <p>The giant's neutral pose has arms hanging long at the sides,
     * knees very slightly bent, and a forward head-tilt for the
     * heavy-browed look.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // ── Body ──────────────────────────────────────────────────────────────
        PartDefinition body = partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, -15.0F, 0.0F));

        body.addOrReplaceChild("body_r1",
                CubeListBuilder.create().texOffs(60, 47)
                        .addBox(-10.0F, -11.0F, -6.0F, 19.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.0F, 1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition shoulders = body.addOrReplaceChild("shoulders",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, -6.0F, 0.0F));

        shoulders.addOrReplaceChild("shoulders_r1",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-14.0F, -6.0F, -5.0F, 27.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 0.4F, 0.2182F, 0.0F, 0.0F));

        PartDefinition waist = body.addOrReplaceChild("waist",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        waist.addOrReplaceChild("waist_r1",
                CubeListBuilder.create().texOffs(0, 21)
                        .addBox(-11.0F, -6.0F, -6.0F, 21.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 9.0F, -0.2F, -0.1309F, 0.0F, 0.0F));

        // ── Neck ──────────────────────────────────────────────────────────────
        partdefinition.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(104, 36)
                        .addBox(-4.0F, -8.0F, -3.0F, 8.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -27.0F, 1.0F));

        // ── Head ──────────────────────────────────────────────────────────────
        PartDefinition head = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 47)
                        .addBox(-8.0F, -16.9663F, -7.4483F, 16.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -26.0F, -5.0F, -0.15F, 0.0F, 0.0F));

        head.addOrReplaceChild("beard",
                CubeListBuilder.create().texOffs(110, 107)
                        .addBox(-6.0F, -3.9663F, -7.4483F, 12.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.0F, 0.0F));

        // ── Right arm (weapon arm) ────────────────────────────────────────────
        PartDefinition arm_upper_right = partdefinition.addOrReplaceChild("arm_upper_right",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-11.0F, -27.0F, 0.0F, -0.1F, 0.0F, -0.15F));

        arm_upper_right.addOrReplaceChild("arm_upper_right_r1",
                CubeListBuilder.create().texOffs(76, 97)
                        .addBox(-6.3327F, -2.9813F, -4.0349F, 8.0F, 16.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0865F, -0.0114F, 0.2613F));

        PartDefinition arm_lower_right = arm_upper_right.addOrReplaceChild("arm_lower_right",
                CubeListBuilder.create(),
                PartPose.offset(-1.0F, 16.0F, 0.0F));

        PartDefinition hand_right = arm_lower_right.addOrReplaceChild("hand_right",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 15.0F, 0.0F));

        hand_right.addOrReplaceChild("hand_right_r1",
                CubeListBuilder.create().texOffs(66, 21)
                        .addBox(-6.3327F, -2.8707F, -5.8053F, 9.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.3F, -17.0F, 1.9F, -0.1753F, -0.0114F, 0.2613F));

        // Club — child of hand_right
        hand_right.addOrReplaceChild("club",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -2.0F, 2.0F, 0.3F, 0.0F, 0.0F));

        // ── Left arm ──────────────────────────────────────────────────────────
        PartDefinition arm_upper_left = partdefinition.addOrReplaceChild("arm_upper_left",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(11.0F, -27.0F, 0.0F, -0.1F, 0.0F, 0.15F));

        arm_upper_left.addOrReplaceChild("arm_upper_left_r1",
                CubeListBuilder.create().texOffs(0, 98)
                        .addBox(-1.6679F, -2.9814F, -4.0349F, 8.0F, 16.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0869F, 0.0076F, -0.2615F));

        PartDefinition arm_lower_left = arm_upper_left.addOrReplaceChild("arm_lower_left",
                CubeListBuilder.create(),
                PartPose.offset(1.0F, 16.0F, 0.0F));

        PartDefinition hand_left = arm_lower_left.addOrReplaceChild("hand_left",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 15.0F, 0.0F));

        hand_left.addOrReplaceChild("hand_right_r2",
                CubeListBuilder.create().texOffs(60, 71)
                        .addBox(-2.6673F, -2.8707F, -5.8053F, 9.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.3F, -17.0F, 1.9F, -0.1753F, 0.0114F, -0.2613F));

        // ── Right leg ─────────────────────────────────────────────────────────
        PartDefinition leg_upper_right = partdefinition.addOrReplaceChild("leg_upper_right",
                CubeListBuilder.create(),
                PartPose.offset(-7.0F, 6.0F, -0.4F));

        leg_upper_right.addOrReplaceChild("leg_upper_right_r1",
                CubeListBuilder.create().texOffs(0, 75)
                        .addBox(-5.0F, 10.0F, -6.0F, 9.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.0F, -16.0F, 1.0F, -0.0869F, 0.0076F, 0.1306F));

        PartDefinition leg_lower_right = leg_upper_right.addOrReplaceChild("leg_lower_right",
                CubeListBuilder.create(),
                PartPose.offset(-1.0F, 11.0F, -2.0F));

        leg_lower_right.addOrReplaceChild("leg_lower_right_r1",
                CubeListBuilder.create().texOffs(76, 0)
                        .addBox(-4.0F, 5.0F, -5.0F, 9.0F, 11.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.8F, -11.1818F, -0.0308F, 0.0872F, -0.0038F, 0.0435F));

        PartDefinition foot_right = leg_lower_right.addOrReplaceChild("foot_right",
                CubeListBuilder.create(),
                PartPose.offset(-1.0F, 5.8182F, -0.0308F));

        foot_right.addOrReplaceChild("foot_right_r1",
                CubeListBuilder.create().texOffs(110, 92)
                        .addBox(-5.0F, 13.0F, -8.0F, 9.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.1F, -15.0F, 2.0F, 0.0F, 0.0F, 0.0436F));

        // ── Left leg ──────────────────────────────────────────────────────────
        PartDefinition leg_upper_left = partdefinition.addOrReplaceChild("leg_upper_left",
                CubeListBuilder.create(),
                PartPose.offset(7.0F, 6.0F, -0.4F));

        leg_upper_left.addOrReplaceChild("leg_upper_left_r1",
                CubeListBuilder.create().texOffs(38, 97)
                        .addBox(-4.0F, 10.0F, -6.0F, 9.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.0F, -16.0F, 1.0F, -0.0869F, -0.0076F, -0.1306F));

        PartDefinition leg_lower_left = leg_upper_left.addOrReplaceChild("leg_lower_left",
                CubeListBuilder.create(),
                PartPose.offset(-2.0F, 10.0F, -1.0F));

        leg_lower_left.addOrReplaceChild("leg_lower_left_r1",
                CubeListBuilder.create().texOffs(98, 71)
                        .addBox(-5.0F, 5.0F, -5.0F, 9.0F, 11.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.8F, -9.9761F, -0.7274F, 0.0872F, 0.0038F, -0.0435F));

        PartDefinition foot_left = leg_lower_left.addOrReplaceChild("foot_left",
                CubeListBuilder.create(),
                PartPose.offset(3.0F, 7.0239F, 1.2726F));

        foot_left.addOrReplaceChild("foot_right_r2",
                CubeListBuilder.create().texOffs(104, 21)
                        .addBox(-4.0F, 13.0F, -8.0F, 9.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.1F, -15.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    // ── Render ────────────────────────────────────────────────────────────────

    @Override
    public void setupAnim(GotGiantRenderState state) {
        super.setupAnim(state);
        // Reset to natural rest-pose offsets before applying keyframes.
        this.head.xRot            = -0.15F;
        this.arm_upper_right.zRot = -0.15F;
        this.arm_upper_left.zRot  =  0.15F;

        // Apply the animation chosen by the renderer.
        // This MUST live here inside setupAnim — if it were called from
        // render() it would run before setupAnim and be overwritten by
        // super.setupAnim()'s reset pass.
        if (state.animationToPlay != null) {
            bakedAnimations.computeIfAbsent(state.animationToPlay, d -> d.bake(this.root)).apply(
                    (long)(state.animationTime * 1000L / 20L),
                    1.0F);
        }
    }
}