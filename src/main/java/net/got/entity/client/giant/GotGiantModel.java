package net.got.entity.client.giant;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.joml.Vector3f;

/**
 * Custom giant model for {@link net.got.entity.giant.GotGiantEntity}.
 *
 * <p>Anatomy overview — all measurements in model units (1 mu = 1/16 block):
 * <ul>
 *   <li>Body core: wide barrel chest, massive shoulders.</li>
 *   <li>Head: broad skull, heavy brow-ridge, thick neck.</li>
 *   <li>Arms: extremely long, reaching near the ground when idle.</li>
 *   <li>Legs: thick pillars; digitigrade posture.</li>
 *   <li>Club: attached to right hand as a child part.</li>
 * </ul>
 *
 * <p>Animations are applied per-frame by {@link GotGiantRenderer} via
 * {@link #applyAnimation(AnimationDefinition, float, float)}.
 */
public class GotGiantModel extends EntityModel<GotGiantRenderState> {

    // ── Model part references ─────────────────────────────────────────────────

    final ModelPart root;
    final ModelPart body;
    final ModelPart chest;
    final ModelPart waist;
    final ModelPart head;
    final ModelPart brow;
    final ModelPart jaw;
    final ModelPart neck;
    // Arms
    final ModelPart arm_upper_right;
    final ModelPart arm_lower_right;
    final ModelPart hand_right;
    final ModelPart club;             // child of hand_right
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

    private static final Vector3f ANIM_VEC = new Vector3f();

    // ── Constructor ───────────────────────────────────────────────────────────

    public GotGiantModel(ModelPart root) {
        super(root);
        this.root             = root;
        this.body             = root.getChild("body");
        this.chest            = this.body.getChild("chest");
        this.waist            = this.body.getChild("waist");
        this.head             = root.getChild("head");
        this.brow             = this.head.getChild("brow");
        this.jaw              = this.head.getChild("jaw");
        this.neck             = root.getChild("neck");
        // Right arm chain
        this.arm_upper_right  = root.getChild("arm_upper_right");
        this.arm_lower_right  = this.arm_upper_right.getChild("arm_lower_right");
        this.hand_right       = this.arm_lower_right.getChild("hand_right");
        this.club             = this.hand_right.getChild("club");
        // Left arm chain
        this.arm_upper_left   = root.getChild("arm_upper_left");
        this.arm_lower_left   = this.arm_upper_left.getChild("arm_lower_left");
        this.hand_left        = this.arm_lower_left.getChild("hand_left");
        // Right leg chain
        this.leg_upper_right  = root.getChild("leg_upper_right");
        this.leg_lower_right  = this.leg_upper_right.getChild("leg_lower_right");
        this.foot_right       = this.leg_lower_right.getChild("foot_right");
        // Left leg chain
        this.leg_upper_left   = root.getChild("leg_upper_left");
        this.leg_lower_left   = this.leg_upper_left.getChild("leg_lower_left");
        this.foot_left        = this.leg_lower_left.getChild("foot_left");
    }

    // ── Layer definition (geometry) ───────────────────────────────────────────

    /**
     * Builds the giant mesh.
     *
     * <p>The giant's neutral pose has arms hanging long at the sides,
     * knees very slightly bent, and a forward head-tilt for the
     * heavy-browed look.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();

        // ── Body ──────────────────────────────────────────────────────────────
        PartDefinition body = parts.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-9F, -12F, -5F, 18, 12, 10),
                PartPose.offset(0F, -12F, 0F));

        body.addOrReplaceChild("chest",
                CubeListBuilder.create()
                        .texOffs(56, 0).addBox(-10F, -5F, -5F, 20, 10, 11),
                PartPose.offset(0F, -6F, 0F));

        body.addOrReplaceChild("waist",
                CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-8F, 0F, -4F, 16, 6, 8),
                PartPose.offset(0F, 0F, 0F));

        // ── Head & neck ───────────────────────────────────────────────────────
        parts.addOrReplaceChild("neck",
                CubeListBuilder.create()
                        .texOffs(96, 0).addBox(-4F, -5F, -3F, 8, 5, 6),
                PartPose.offset(0F, -24F, 1F));

        PartDefinition head = parts.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 60).addBox(-8F, -12F, -7F, 16, 12, 14),
                PartPose.offsetAndRotation(0F, -29F, 0F, 0.15F, 0F, 0F));

        head.addOrReplaceChild("brow",
                CubeListBuilder.create()
                        .texOffs(60, 60).addBox(-8F, -2F, -8F, 16, 3, 3),
                PartPose.offset(0F, -8F, 0F));

        head.addOrReplaceChild("jaw",
                CubeListBuilder.create()
                        .texOffs(60, 66).addBox(-5F, 0F, -6F, 10, 4, 6),
                PartPose.offset(0F, -2F, 0F));

        // ── Right arm (weapon arm) ────────────────────────────────────────────
        PartDefinition armUR = parts.addOrReplaceChild("arm_upper_right",
                CubeListBuilder.create()
                        .texOffs(112, 0).addBox(-5F, 0F, -4F, 7, 16, 7),
                PartPose.offsetAndRotation(-11F, -24F, 0F, 0.1F, 0F, 0.15F));

        PartDefinition armLR = armUR.addOrReplaceChild("arm_lower_right",
                CubeListBuilder.create()
                        .texOffs(112, 23).addBox(-4F, 0F, -3F, 6, 15, 6),
                PartPose.offset(-1F, 16F, 0F));

        PartDefinition handR = armLR.addOrReplaceChild("hand_right",
                CubeListBuilder.create()
                        .texOffs(112, 44).addBox(-4F, 0F, -3F, 7, 6, 6),
                PartPose.offset(0F, 15F, 0F));

        // Club — wooden log weapon in right hand
        handR.addOrReplaceChild("club",
                CubeListBuilder.create()
                        .texOffs(140, 0).addBox(-2F, -32F, -2F, 5, 34, 5),  // long pole
                PartPose.offsetAndRotation(0F, -2F, 2F, -0.3F, 0F, 0F));

        // ── Left arm ──────────────────────────────────────────────────────────
        PartDefinition armUL = parts.addOrReplaceChild("arm_upper_left",
                CubeListBuilder.create()
                        .texOffs(112, 0).mirror().addBox(-2F, 0F, -4F, 7, 16, 7),
                PartPose.offsetAndRotation(11F, -24F, 0F, 0.1F, 0F, -0.15F));

        PartDefinition armLL = armUL.addOrReplaceChild("arm_lower_left",
                CubeListBuilder.create()
                        .texOffs(112, 23).mirror().addBox(-2F, 0F, -3F, 6, 15, 6),
                PartPose.offset(1F, 16F, 0F));

        armLL.addOrReplaceChild("hand_left",
                CubeListBuilder.create()
                        .texOffs(112, 44).mirror().addBox(-3F, 0F, -3F, 7, 6, 6),
                PartPose.offset(0F, 15F, 0F));

        // ── Right leg ─────────────────────────────────────────────────────────
        PartDefinition legUR = parts.addOrReplaceChild("leg_upper_right",
                CubeListBuilder.create()
                        .texOffs(56, 28).addBox(-5F, 0F, -5F, 10, 18, 10),
                PartPose.offset(-5F, -12F, 0F));

        PartDefinition legLR = legUR.addOrReplaceChild("leg_lower_right",
                CubeListBuilder.create()
                        .texOffs(56, 56).addBox(-4F, 0F, -4F, 8, 16, 8),
                PartPose.offset(0F, 18F, 0F));

        legLR.addOrReplaceChild("foot_right",
                CubeListBuilder.create()
                        .texOffs(56, 80).addBox(-5F, 0F, -8F, 10, 5, 12),
                PartPose.offset(0F, 16F, 0F));

        // ── Left leg ──────────────────────────────────────────────────────────
        PartDefinition legUL = parts.addOrReplaceChild("leg_upper_left",
                CubeListBuilder.create()
                        .texOffs(56, 28).mirror().addBox(-5F, 0F, -5F, 10, 18, 10),
                PartPose.offset(5F, -12F, 0F));

        PartDefinition legLL = legUL.addOrReplaceChild("leg_lower_left",
                CubeListBuilder.create()
                        .texOffs(56, 56).mirror().addBox(-4F, 0F, -4F, 8, 16, 8),
                PartPose.offset(0F, 18F, 0F));

        legLL.addOrReplaceChild("foot_left",
                CubeListBuilder.create()
                        .texOffs(56, 80).mirror().addBox(-5F, 0F, -8F, 10, 5, 12),
                PartPose.offset(0F, 16F, 0F));

        return LayerDefinition.create(mesh, 256, 128);
    }

    // ── Render ────────────────────────────────────────────────────────────────

    @Override
    public void setupAnim(GotGiantRenderState state) {
        super.setupAnim(state);
        // Bobbing idle breathe is driven by animations; reset each frame first.
        this.head.xRot     = 0.15F; // maintain natural forward-lean of head
        this.arm_upper_right.zRot =  0.15F;
        this.arm_upper_left.zRot  = -0.15F;
    }

    // ── Animation application ─────────────────────────────────────────────────

    /**
     * Applies a {@link AnimationDefinition} to all model parts.
     *
     * <p>This is called once per frame from the renderer with the pre-selected
     * animation and the appropriate time value (looping or one-shot).
     *
     * @param animation  the definition to apply
     * @param time       animation time in ticks (looping) or elapsed ticks (one-shot)
     * @param weight     blend weight (0..1); pass 1.0 for full override
     */
    public void applyAnimation(AnimationDefinition animation, float time, float weight) {
        KeyframeAnimations.animate(this, animation, (long)(time * 1000L / 20L), weight, ANIM_VEC);
    }
}