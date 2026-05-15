package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

/**
 * Male Smallfolk NPC model.
 * Made with Blockbench 5.1.4 – Minecraft 1.21.4 / NeoForge, Mojang mappings.
 *
 * <p>Extends {@link HumanoidModel} directly so all vanilla walk/attack/swim/
 * crouch/item-use animations are inherited for free. The custom 4-wide male
 * arm geometry is achieved purely through the {@link LayerDefinition} — the
 * model hierarchy and bone names match exactly what {@link HumanoidModel}
 * expects so it wires itself up correctly.
 *
 * <p>The only bespoke behaviour is {@link #applyTalkAnimation} which layers
 * server-driven gesture floats on top of the vanilla pose.
 */
public class GotSmallfolkMaleModel extends HumanoidModel<SmallfolkRenderState>
        implements SmallfolkModelParts {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("got", "smallfolk_male"), "main");

    public GotSmallfolkMaleModel(ModelPart root) {
        super(root);
    }

    // ── Layer definition ──────────────────────────────────────────────────────
    //
    // HumanoidModel expects these exact child names on the root part:
    //   head, hat, body, right_arm, left_arm, right_leg, left_leg
    // We provide them with our custom geometry (4-wide arms) plus extra
    // children (armor_*, sleeve, jacket, cape, item anchors) that HumanoidModel
    // ignores but which still render as part of the hierarchy.

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // ── Head ──────────────────────────────────────────────────────────────
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("hat",
                CubeListBuilder.create()
                        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        head.addOrReplaceChild("armor_head",
                CubeListBuilder.create()
                        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        // ── Body ──────────────────────────────────────────────────────────────
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("armor_body",
                CubeListBuilder.create()
                        .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        body.addOrReplaceChild("jacket",
                CubeListBuilder.create()
                        .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        body.addOrReplaceChild("cape",
                CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

        // ── Right arm (4-wide, male) ───────────────────────────────────────────
        PartDefinition rightArm = root.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        rightArm.addOrReplaceChild("armor_right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        rightArm.addOrReplaceChild("right_sleeve",
                CubeListBuilder.create()
                        .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        rightArm.addOrReplaceChild("right_item",
                CubeListBuilder.create(), PartPose.offset(-1.0F, 9.3F, 2.0F));

        // ── Left arm (4-wide, male) ────────────────────────────────────────────
        PartDefinition leftArm = root.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 2.0F, 0.0F));

        leftArm.addOrReplaceChild("armor_left_arm",
                CubeListBuilder.create()
                        .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        leftArm.addOrReplaceChild("left_sleeve",
                CubeListBuilder.create()
                        .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        leftArm.addOrReplaceChild("left_item",
                CubeListBuilder.create(), PartPose.offset(1.0F, 9.3F, 2.0F));

        // ── Right leg ─────────────────────────────────────────────────────────
        PartDefinition rightLeg = root.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.9F, 12.0F, 0.0F));

        rightLeg.addOrReplaceChild("armor_right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        rightLeg.addOrReplaceChild("armor_right_boot",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_pants",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        // ── Left leg ──────────────────────────────────────────────────────────
        PartDefinition leftLeg = root.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(16, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.9F, 12.0F, 0.0F));

        leftLeg.addOrReplaceChild("armor_left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        leftLeg.addOrReplaceChild("armor_left_boot",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_pants",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    // ── Animation ─────────────────────────────────────────────────────────────

    @Override
    public void setupAnim(SmallfolkRenderState state) {
        super.setupAnim(state);
        applyTalkAnimation(state);
    }

    /**
     * Layers server-driven talk animation floats on top of the vanilla pose.
     * All three values lerp back to zero server-side when talking stops,
     * so no client-side blending is needed.
     */
    private void applyTalkAnimation(SmallfolkRenderState state) {
        if (!state.isTalking && state.talkHeadYaw == 0f
                && state.talkHeadPitch == 0f && state.talkGesture == 0f) return;

        this.head.yRot     += state.talkHeadYaw;
        this.head.xRot     += state.talkHeadPitch;
        this.rightArm.xRot -= state.talkGesture;
        this.body.yRot     += state.talkGesture * 0.1F;
    }

    // ── SmallfolkModelParts ───────────────────────────────────────────────────

    @Override public ModelPart sfHead()            { return head; }
    @Override public ModelPart sfBody()            { return body; }
    @Override public ModelPart sfRightArm()        { return rightArm; }
    @Override public ModelPart sfLeftArm()         { return leftArm; }
    @Override public ModelPart sfRightLeg()        { return rightLeg; }
    @Override public ModelPart sfLeftLeg()         { return leftLeg; }
    @Override public ModelPart sfRightItemAnchor() { return rightArm.getChild("right_item"); }
    @Override public ModelPart sfLeftItemAnchor()  { return leftArm.getChild("left_item"); }
}