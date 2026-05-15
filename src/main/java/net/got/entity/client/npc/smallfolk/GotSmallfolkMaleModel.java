package net.got.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

/**
 * Male Smallfolk NPC model.
 * Made with Blockbench 5.1.4 – Minecraft 1.21.4 / NeoForge, Mojang mappings.
 *
 * Stays as EntityModel (not HumanoidModel) to preserve the custom bone
 * hierarchy (root > waist > body > ...) and 4-wide male arm geometry.
 * Vanilla HumanoidModel.setupAnim logic is ported directly so all standard
 * walk / crouch / attack / swim / item-use animations work identically.
 */
public class GotSmallfolkMaleModel extends EntityModel<SmallfolkRenderState>
        implements SmallfolkModelParts, ArmedModel {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("got", "smallfolk_male"), "main");

    // ── Bone references ───────────────────────────────────────────────────────
    private final ModelPart shield;
    private final ModelPart root;
    private final ModelPart waist;
    private final ModelPart body;
    private final ModelPart armor_body;
    private final ModelPart head;
    private final ModelPart armor_head;
    private final ModelPart hat;
    private final ModelPart cape;
    private final ModelPart leftArm;
    private final ModelPart armor_left_arm;
    private final ModelPart leftSleeve;
    private final ModelPart leftItem;
    private final ModelPart rightArm;
    private final ModelPart armor_right_arm;
    private final ModelPart rightSleeve;
    private final ModelPart rightItem;
    private final ModelPart jacket;
    private final ModelPart leftLeg;
    private final ModelPart armor_left_leg;
    private final ModelPart armor_left_boot;
    private final ModelPart leftPants;
    private final ModelPart rightLeg;
    private final ModelPart armor_right_leg;
    private final ModelPart armor_right_boot;
    private final ModelPart rightPants;

    public GotSmallfolkMaleModel(ModelPart root) {
        super(root);
        this.shield          = root.getChild("shield");
        this.root            = root.getChild("root");
        this.waist           = this.root.getChild("waist");
        this.body            = this.waist.getChild("body");
        this.armor_body      = this.body.getChild("armor_body");
        this.head            = this.body.getChild("head");
        this.armor_head      = this.head.getChild("armor_head");
        this.hat             = this.head.getChild("hat");
        this.cape            = this.body.getChild("cape");
        this.leftArm         = this.body.getChild("leftArm");
        this.armor_left_arm  = this.leftArm.getChild("armor_left_arm");
        this.leftSleeve      = this.leftArm.getChild("leftSleeve");
        this.leftItem        = this.leftArm.getChild("leftItem");
        this.rightArm        = this.body.getChild("rightArm");
        this.armor_right_arm = this.rightArm.getChild("armor_right_arm");
        this.rightSleeve     = this.rightArm.getChild("rightSleeve");
        this.rightItem       = this.rightArm.getChild("rightItem");
        this.jacket          = this.body.getChild("jacket");
        this.leftLeg         = this.root.getChild("leftLeg");
        this.armor_left_leg  = this.leftLeg.getChild("armor_left_leg");
        this.armor_left_boot = this.leftLeg.getChild("armor_left_boot");
        this.leftPants       = this.leftLeg.getChild("leftPants");
        this.rightLeg        = this.root.getChild("rightLeg");
        this.armor_right_leg  = this.rightLeg.getChild("armor_right_leg");
        this.armor_right_boot = this.rightLeg.getChild("armor_right_boot");
        this.rightPants       = this.rightLeg.getChild("rightPants");
    }

    // ── Layer definition ──────────────────────────────────────────────────────

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("shield",
                CubeListBuilder.create(), PartPose.offset(1.0F, 8.5F, 3.0F));

        PartDefinition root = partdefinition.addOrReplaceChild("root",
                CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition waist = root.addOrReplaceChild("waist",
                CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition body = waist.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -12.0F, 0.0F));

        body.addOrReplaceChild("armor_body",
                CubeListBuilder.create()
                        .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("armor_head",
                CubeListBuilder.create()
                        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("hat",
                CubeListBuilder.create()
                        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("cape",
                CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

        // Male arms — 4 wide
        PartDefinition leftArm = body.addOrReplaceChild("leftArm",
                CubeListBuilder.create()
                        .texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 2.0F, 0.0F));

        leftArm.addOrReplaceChild("armor_left_arm",
                CubeListBuilder.create()
                        .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leftArm.addOrReplaceChild("leftSleeve",
                CubeListBuilder.create()
                        .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leftArm.addOrReplaceChild("leftItem",
                CubeListBuilder.create(), PartPose.offset(1.0F, 9.3F, 2.0F));

        PartDefinition rightArm = body.addOrReplaceChild("rightArm",
                CubeListBuilder.create()
                        .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        rightArm.addOrReplaceChild("armor_right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        rightArm.addOrReplaceChild("rightSleeve",
                CubeListBuilder.create()
                        .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        rightArm.addOrReplaceChild("rightItem",
                CubeListBuilder.create(), PartPose.offset(-1.0F, 9.3F, 2.0F));

        body.addOrReplaceChild("jacket",
                CubeListBuilder.create()
                        .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftLeg = root.addOrReplaceChild("leftLeg",
                CubeListBuilder.create()
                        .texOffs(16, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.9F, -12.0F, 0.0F));

        leftLeg.addOrReplaceChild("armor_left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leftLeg.addOrReplaceChild("armor_left_boot",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leftLeg.addOrReplaceChild("leftPants",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightLeg = root.addOrReplaceChild("rightLeg",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.9F, -12.0F, 0.0F));

        rightLeg.addOrReplaceChild("armor_right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        rightLeg.addOrReplaceChild("armor_right_boot",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        rightLeg.addOrReplaceChild("rightPants",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    // ── Vanilla HumanoidModel animation logic, ported to our bone names ───────

    @Override
    public void setupAnim(SmallfolkRenderState state) {
        super.setupAnim(state);

        HumanoidModel.ArmPose leftArmPose  = state.leftArmPose;
        HumanoidModel.ArmPose rightArmPose = state.rightArmPose;
        float swimAmount   = state.swimAmount;
        boolean fallFlying = state.isFallFlying;

        // Head
        this.head.xRot = state.xRot * (float)(Math.PI / 180.0);
        this.head.yRot = state.yRot * (float)(Math.PI / 180.0);
        if (fallFlying) {
            this.head.xRot = -(float)(Math.PI / 4.0);
        } else if (swimAmount > 0.0F) {
            this.head.xRot = Mth.rotLerpRad(swimAmount, this.head.xRot, -(float)(Math.PI / 4.0));
        }

        // Limb swing
        float limbPos    = state.walkAnimationPos;
        float limbAmount = state.walkAnimationSpeed;
        float speed      = state.speedValue;
        this.rightArm.xRot = Mth.cos(limbPos * 0.6662F + (float)Math.PI) * 2.0F * limbAmount * 0.5F / speed;
        this.leftArm.xRot  = Mth.cos(limbPos * 0.6662F) * 2.0F * limbAmount * 0.5F / speed;
        this.rightLeg.xRot = Mth.cos(limbPos * 0.6662F) * 1.4F * limbAmount / speed;
        this.leftLeg.xRot  = Mth.cos(limbPos * 0.6662F + (float)Math.PI) * 1.4F * limbAmount / speed;
        this.rightLeg.yRot =  0.005F;
        this.leftLeg.yRot  = -0.005F;
        this.rightLeg.zRot =  0.005F;
        this.leftLeg.zRot  = -0.005F;

        // Riding
        if (state.isPassenger) {
            this.rightArm.xRot += -(float)(Math.PI / 5.0);
            this.leftArm.xRot  += -(float)(Math.PI / 5.0);
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot =  (float)(Math.PI / 10.0);
            this.rightLeg.zRot =  0.07853982F;
            this.leftLeg.xRot  = -1.4137167F;
            this.leftLeg.yRot  = -(float)(Math.PI / 10.0);
            this.leftLeg.zRot  = -0.07853982F;
        }

        // Item use arm poses
        boolean mainRight = state.mainArm == HumanoidArm.RIGHT;
        if (state.isUsingItem) {
            boolean mainHand = state.useItemHand == net.minecraft.world.InteractionHand.MAIN_HAND;
            if (mainHand == mainRight) {
                poseRightArm(state, rightArmPose);
            } else {
                poseLeftArm(state, leftArmPose);
            }
        } else {
            boolean twoHanded = mainRight ? leftArmPose.isTwoHanded() : rightArmPose.isTwoHanded();
            if (mainRight != twoHanded) {
                poseLeftArm(state, leftArmPose);
                poseRightArm(state, rightArmPose);
            } else {
                poseRightArm(state, rightArmPose);
                poseLeftArm(state, leftArmPose);
            }
        }

        // Attack swing
        setupAttackAnimation(state);

        // Crouch
        if (state.isCrouching) {
            this.body.xRot    =  0.5F;
            this.rightArm.xRot += 0.4F;
            this.leftArm.xRot  += 0.4F;
            this.rightLeg.z   +=  4.0F;
            this.leftLeg.z    +=  4.0F;
            this.head.y       +=  4.2F;
            this.body.y       +=  3.2F;
            this.leftArm.y    +=  3.2F;
            this.rightArm.y   +=  3.2F;
        }

        // Arm bob (idle sway)
        if (rightArmPose != HumanoidModel.ArmPose.SPYGLASS) {
            AnimationUtils.bobModelPart(this.rightArm, state.ageInTicks,  1.0F);
        }
        if (leftArmPose != HumanoidModel.ArmPose.SPYGLASS) {
            AnimationUtils.bobModelPart(this.leftArm,  state.ageInTicks, -1.0F);
        }

        // Swimming
        if (swimAmount > 0.0F) {
            float f7 = limbPos % 26.0F;
            HumanoidArm attackArm = state.attackArm;
            float f3 = attackArm == HumanoidArm.RIGHT && state.attackTime > 0.0F ? 0.0F : swimAmount;
            float f4 = attackArm == HumanoidArm.LEFT  && state.attackTime > 0.0F ? 0.0F : swimAmount;
            if (!state.isUsingItem) {
                if (f7 < 14.0F) {
                    this.leftArm.xRot  = Mth.rotLerpRad(f4, this.leftArm.xRot, 0.0F);
                    this.rightArm.xRot = Mth.lerp(f3, this.rightArm.xRot, 0.0F);
                    this.leftArm.yRot  = Mth.rotLerpRad(f4, this.leftArm.yRot, (float)Math.PI);
                    this.rightArm.yRot = Mth.lerp(f3, this.rightArm.yRot, (float)Math.PI);
                    this.leftArm.zRot  = Mth.rotLerpRad(f4, this.leftArm.zRot, (float)Math.PI + 1.8707964F * quadraticArmUpdate(f7) / quadraticArmUpdate(14.0F));
                    this.rightArm.zRot = Mth.lerp(f3, this.rightArm.zRot, (float)Math.PI - 1.8707964F * quadraticArmUpdate(f7) / quadraticArmUpdate(14.0F));
                } else if (f7 >= 14.0F && f7 < 22.0F) {
                    float f8 = (f7 - 14.0F) / 8.0F;
                    this.leftArm.xRot  = Mth.rotLerpRad(f4, this.leftArm.xRot, (float)(Math.PI / 2.0) * f8);
                    this.rightArm.xRot = Mth.lerp(f3, this.rightArm.xRot, (float)(Math.PI / 2.0) * f8);
                    this.leftArm.yRot  = Mth.rotLerpRad(f4, this.leftArm.yRot, (float)Math.PI);
                    this.rightArm.yRot = Mth.lerp(f3, this.rightArm.yRot, (float)Math.PI);
                    this.leftArm.zRot  = Mth.rotLerpRad(f4, this.leftArm.zRot, 5.012389F - 1.8707964F * f8);
                    this.rightArm.zRot = Mth.lerp(f3, this.rightArm.zRot, 1.2707963F + 1.8707964F * f8);
                } else if (f7 >= 22.0F && f7 < 26.0F) {
                    float f5 = (f7 - 22.0F) / 4.0F;
                    this.leftArm.xRot  = Mth.rotLerpRad(f4, this.leftArm.xRot, (float)(Math.PI / 2.0) - (float)(Math.PI / 2.0) * f5);
                    this.rightArm.xRot = Mth.lerp(f3, this.rightArm.xRot, (float)(Math.PI / 2.0) - (float)(Math.PI / 2.0) * f5);
                    this.leftArm.yRot  = Mth.rotLerpRad(f4, this.leftArm.yRot, (float)Math.PI);
                    this.rightArm.yRot = Mth.lerp(f3, this.rightArm.yRot, (float)Math.PI);
                    this.leftArm.zRot  = Mth.rotLerpRad(f4, this.leftArm.zRot, (float)Math.PI);
                    this.rightArm.zRot = Mth.lerp(f3, this.rightArm.zRot, (float)Math.PI);
                }
            }
            this.leftLeg.xRot  = Mth.lerp(swimAmount, this.leftLeg.xRot,  0.3F * Mth.cos(limbPos * 0.33333334F + (float)Math.PI));
            this.rightLeg.xRot = Mth.lerp(swimAmount, this.rightLeg.xRot, 0.3F * Mth.cos(limbPos * 0.33333334F));
        }

        // Talk/interact animation — applied last so it layers on top of everything
        applyTalkAnimation(state);
    }

    /**
     * Applies the server-driven talk animation floats from
     * {@link net.got.entity.npc.GotNpcTalkAnimations} additively on top of
     * the base pose. All three values smoothly lerp back to zero server-side
     * when the NPC stops talking, so no client-side blending is needed.
     *
     * <ul>
     *   <li>{@code talkHeadYaw}   — adds side-to-side head turn (sinusoidal ±0.3 rad)</li>
     *   <li>{@code talkHeadPitch} — adds head nod (sinusoidal ±0.15 rad)</li>
     *   <li>{@code talkGesture}   — rotates right arm forward when active (±0.5 rad xRot),
     *       with a matching counter-rotation on the body to keep it grounded</li>
     * </ul>
     */
    private void applyTalkAnimation(SmallfolkRenderState state) {
        if (!state.isTalking && state.talkHeadYaw == 0f
                && state.talkHeadPitch == 0f && state.talkGesture == 0f) return;

        // Head oscillation — additive on top of vanilla look rotation
        this.head.yRot += state.talkHeadYaw;
        this.head.xRot += state.talkHeadPitch;

        // Right-arm gesture — raise arm forward, subtle body lean into gesture
        this.rightArm.xRot -= state.talkGesture;
        this.body.yRot     += state.talkGesture * 0.1F;
    }

    private void poseRightArm(SmallfolkRenderState state, HumanoidModel.ArmPose pose) {
        switch (pose) {
            case EMPTY ->
                    this.rightArm.yRot = 0.0F;
            case BLOCK ->
                    poseBlockingArm(this.rightArm, true);
            case BOW_AND_ARROW -> {
                this.rightArm.yRot = -0.1F + this.head.yRot;
                this.leftArm.yRot  =  0.1F + this.head.yRot + 0.4F;
                this.rightArm.xRot = -(float)(Math.PI / 2.0) + this.head.xRot;
                this.leftArm.xRot  = -(float)(Math.PI / 2.0) + this.head.xRot;
            }
            case THROW_SPEAR -> {
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float)Math.PI;
                this.rightArm.yRot = 0.0F;
            }
            case CROSSBOW_CHARGE ->
                    AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, state.maxCrossbowChargeDuration, state.ticksUsingItem, true);
            case CROSSBOW_HOLD ->
                    AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
            case SPYGLASS -> {
                this.rightArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - (state.isCrouching ? 0.2617994F : 0.0F), -2.4F, 3.3F);
                this.rightArm.yRot = this.head.yRot - 0.2617994F;
            }
            case TOOT_HORN -> {
                this.rightArm.xRot = Mth.clamp(this.head.xRot, -1.2F, 1.2F) - 1.4835298F;
                this.rightArm.yRot = this.head.yRot - (float)(Math.PI / 6.0);
            }
            case BRUSH -> {
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float)(Math.PI / 5.0);
                this.rightArm.yRot = 0.0F;
            }
            default -> {
                this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float)(Math.PI / 10.0);
                this.rightArm.yRot = 0.0F;
            }
        }
    }

    private void poseLeftArm(SmallfolkRenderState state, HumanoidModel.ArmPose pose) {
        switch (pose) {
            case EMPTY ->
                    this.leftArm.yRot = 0.0F;
            case BLOCK ->
                    poseBlockingArm(this.leftArm, false);
            case BOW_AND_ARROW -> {
                this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
                this.leftArm.yRot  =  0.1F + this.head.yRot;
                this.rightArm.xRot = -(float)(Math.PI / 2.0) + this.head.xRot;
                this.leftArm.xRot  = -(float)(Math.PI / 2.0) + this.head.xRot;
            }
            case THROW_SPEAR -> {
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float)Math.PI;
                this.leftArm.yRot = 0.0F;
            }
            case CROSSBOW_CHARGE ->
                    AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, state.maxCrossbowChargeDuration, state.ticksUsingItem, false);
            case CROSSBOW_HOLD ->
                    AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, false);
            case SPYGLASS -> {
                this.leftArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - (state.isCrouching ? 0.2617994F : 0.0F), -2.4F, 3.3F);
                this.leftArm.yRot = this.head.yRot + 0.2617994F;
            }
            case TOOT_HORN -> {
                this.leftArm.xRot = Mth.clamp(this.head.xRot, -1.2F, 1.2F) - 1.4835298F;
                this.leftArm.yRot = this.head.yRot + (float)(Math.PI / 6.0);
            }
            case BRUSH -> {
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float)(Math.PI / 5.0);
                this.leftArm.yRot = 0.0F;
            }
            default -> {
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float)(Math.PI / 10.0);
                this.leftArm.yRot = 0.0F;
            }
        }
    }

    private void poseBlockingArm(ModelPart arm, boolean isRightArm) {
        arm.xRot = arm.xRot * 0.5F - 0.9424779F + Mth.clamp(this.head.xRot, -1.3962634F, 0.43633232F);
        arm.yRot = (isRightArm ? -30.0F : 30.0F) * (float)(Math.PI / 180.0)
                + Mth.clamp(this.head.yRot, -(float)(Math.PI / 6.0), (float)(Math.PI / 6.0));
    }

    private void setupAttackAnimation(SmallfolkRenderState state) {
        float attackTime = state.attackTime;
        if (attackTime <= 0.0F) return;

        HumanoidArm attackArm = state.attackArm;
        ModelPart swingingArm = (attackArm == HumanoidArm.RIGHT) ? this.rightArm : this.leftArm;

        this.body.yRot = Mth.sin(Mth.sqrt(attackTime) * (float)(Math.PI * 2.0)) * 0.2F;
        if (attackArm == HumanoidArm.LEFT) this.body.yRot *= -1.0F;

        float ageScale = state.ageScale;
        this.rightArm.z =  Mth.sin(this.body.yRot) * 5.0F * ageScale;
        this.rightArm.x = -Mth.cos(this.body.yRot) * 5.0F * ageScale;
        this.leftArm.z  = -Mth.sin(this.body.yRot) * 5.0F * ageScale;
        this.leftArm.x  =  Mth.cos(this.body.yRot) * 5.0F * ageScale;
        this.rightArm.yRot += this.body.yRot;
        this.leftArm.yRot  += this.body.yRot;
        this.leftArm.xRot  += this.body.yRot;

        float f  = 1.0F - attackTime;
        f *= f; f *= f; f = 1.0F - f;
        float f3 = Mth.sin(f * (float)Math.PI);
        float f4 = Mth.sin(attackTime * (float)Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
        swingingArm.xRot -= f3 * 1.2F + f4;
        swingingArm.yRot += this.body.yRot * 2.0F;
        swingingArm.zRot += Mth.sin(attackTime * (float)Math.PI) * -0.4F;
    }

    private float quadraticArmUpdate(float value) {
        return -65.0F * value + value * value;
    }

    // ── ArmedModel ────────────────────────────────────────────────────────────

    @Override
    public ModelPart getArm(HumanoidArm side) {
        return side == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    @Override
    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        getArm(side).translateAndRotate(poseStack);
    }

    // ── SmallfolkModelParts ───────────────────────────────────────────────────

    @Override public ModelPart sfHead()            { return head; }
    @Override public ModelPart sfBody()            { return body; }
    @Override public ModelPart sfRightArm()        { return rightArm; }
    @Override public ModelPart sfLeftArm()         { return leftArm; }
    @Override public ModelPart sfRightLeg()        { return rightLeg; }
    @Override public ModelPart sfLeftLeg()         { return leftLeg; }
    @Override public ModelPart sfRightItemAnchor() { return rightItem; }
    @Override public ModelPart sfLeftItemAnchor()  { return leftItem; }
}