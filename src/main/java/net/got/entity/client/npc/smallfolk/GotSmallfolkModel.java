package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

/**
 * Custom male Smallfolk model — created in Blockbench 5.1.3 and integrated
 * into the GotSmallfolk rendering pipeline.
 *
 * <p>Uses a standard (Steve-width, 4 px) arm geometry with a hierarchical
 * root → waist → body structure matching the female model's layout, so both
 * share the same walk-cycle and talk-animation logic in {@link #setupAnim}.
 *
 * <p>Implements {@link SmallfolkModelParts} so that {@link SmallfolkArmorLayer}
 * and {@link SmallfolkHeldItemLayer} can access skeleton parts without
 * knowing the concrete model class.
 *
 * <p>Exported for Minecraft 1.17+ / Mojang mappings, adapted for the 1.21
 * render pipeline (SmallfolkRenderState replaces the raw Entity generic).
 */
public class GotSmallfolkModel extends EntityModel<SmallfolkRenderState>
        implements SmallfolkModelParts {

    /**
     * Layer location — bake with
     * {@code EntityRendererProvider.Context#bakeLayer} and pass the resulting
     * {@link ModelPart} to {@link #GotSmallfolkModel(ModelPart)}.
     */
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("got", "gotsmallfolkmodel"), "main");

    // ── Model parts ───────────────────────────────────────────────────────────
    private final ModelPart root;
    private final ModelPart waist;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart cape;
    private final ModelPart leftArm;
    private final ModelPart leftSleeve;
    private final ModelPart leftItem;
    private final ModelPart rightArm;
    private final ModelPart rightSleeve;
    private final ModelPart rightItem;
    private final ModelPart jacket;
    private final ModelPart leftLeg;
    private final ModelPart leftPants;
    private final ModelPart rightLeg;
    private final ModelPart rightPants;

    // ── Constructor ───────────────────────────────────────────────────────────

    public GotSmallfolkModel(ModelPart root) {
        // EntityModel(ModelPart) stores the root and exposes it via root().
        // renderToBuffer() is final in Model (1.21.x) and calls root().render(...),
        // so passing the outer baked ModelPart here renders the full hierarchy.
        super(root);
        this.root        = root.getChild("root");
        this.waist       = this.root.getChild("waist");
        this.body        = this.waist.getChild("body");
        this.head        = this.body.getChild("head");
        this.hat         = this.head.getChild("hat");
        this.cape        = this.body.getChild("cape");
        this.leftArm     = this.body.getChild("leftArm");
        this.leftSleeve  = this.leftArm.getChild("leftSleeve");
        this.leftItem    = this.leftArm.getChild("leftItem");
        this.rightArm    = this.body.getChild("rightArm");
        this.rightSleeve = this.rightArm.getChild("rightSleeve");
        this.rightItem   = this.rightArm.getChild("rightItem");
        this.jacket      = this.body.getChild("jacket");
        this.leftLeg     = this.root.getChild("leftLeg");
        this.leftPants   = this.leftLeg.getChild("leftPants");
        this.rightLeg    = this.root.getChild("rightLeg");
        this.rightPants  = this.rightLeg.getChild("rightPants");
    }

    // ── Layer definition ──────────────────────────────────────────────────────

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition waist = root.addOrReplaceChild("waist",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition body = waist.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("hat",
                CubeListBuilder.create()
                        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("cape",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 3.0F));

        // Standard (Steve-width, 4 px) left arm
        PartDefinition leftArm = body.addOrReplaceChild("leftArm",
                CubeListBuilder.create()
                        .texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        leftArm.addOrReplaceChild("leftSleeve",
                CubeListBuilder.create()
                        .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        leftArm.addOrReplaceChild("leftItem",
                CubeListBuilder.create(),
                PartPose.offset(1.0F, 7.0F, 1.0F));

        // Standard (Steve-width, 4 px) right arm
        PartDefinition rightArm = body.addOrReplaceChild("rightArm",
                CubeListBuilder.create()
                        .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        rightArm.addOrReplaceChild("rightSleeve",
                CubeListBuilder.create()
                        .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        rightArm.addOrReplaceChild("rightItem",
                CubeListBuilder.create(),
                PartPose.offset(-1.0F, 7.0F, 1.0F));

        body.addOrReplaceChild("jacket",
                CubeListBuilder.create()
                        .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftLeg = root.addOrReplaceChild("leftLeg",
                CubeListBuilder.create()
                        .texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.9F, -12.0F, 0.0F));
        leftLeg.addOrReplaceChild("leftPants",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightLeg = root.addOrReplaceChild("rightLeg",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.9F, -12.0F, 0.0F));
        rightLeg.addOrReplaceChild("rightPants",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    // ── Animation ─────────────────────────────────────────────────────────────
    //
    // Priority order (highest wins, applied in sequence so later overwrites earlier):
    //   riding (early-out) → idle → walk → attack → bow aim → shield block → talking

    @Override
    public void setupAnim(SmallfolkRenderState state) {

        // ── Reset rotations each frame so poses don't accumulate ──────────────
        head.xRot = 0f;  head.yRot = 0f;  head.zRot = 0f;
        body.xRot = 0f;  body.yRot = 0f;  body.zRot = 0f;
        leftArm.xRot  = 0f;  leftArm.yRot  = 0f;  leftArm.zRot  = 0f;
        rightArm.xRot = 0f;  rightArm.yRot = 0f;  rightArm.zRot = 0f;
        leftLeg.xRot  = 0f;  leftLeg.yRot  = 0f;  leftLeg.zRot  = 0f;
        rightLeg.xRot = 0f;  rightLeg.yRot = 0f;  rightLeg.zRot = 0f;

        // ── Head look ─────────────────────────────────────────────────────────
        // Only drive head yaw/pitch from talk animations when actually talking.
        // Previously this was applied unconditionally, which clobbered the head
        // rotation every frame even for idle/walking NPCs (always looked forward).
        if (state.isTalking) {
            head.yRot = state.talkHeadYaw;
            head.xRot = state.talkHeadPitch;
        }

        // ── Riding / mount pose ───────────────────────────────────────────────
        // When the NPC is a passenger on a horse (or any vehicle) the legs splay
        // forward and outward so the figure sits astride the mount naturally.
        // We return early to suppress all walk / idle / attack animations —
        // the horse's own movement drives the apparent motion.
        if (state.isRiding) {
            leftLeg.xRot  = -1.4137167f;    // ~-81° — upper leg swings forward
            leftLeg.yRot  =  0.31415927f;   // angled inward over the saddle
            leftLeg.zRot  =  0.07853982f;   // slight outward roll
            rightLeg.xRot = -1.4137167f;
            rightLeg.yRot = -0.31415927f;
            rightLeg.zRot = -0.07853982f;
            return;
        }

        // ── Idle animation (vanilla MC style) ────────────────────────────────
        float age       = state.ageInTicks;
        float idleBlend = 1f - Math.min(state.walkAnimationSpeed * 6f, 1f);

        float bob    = (float) Math.sin(age * 0.067f);
        float armOsc = (float) Math.cos(age * 0.09f) * 0.075f + 0.075f;

        body.xRot     += bob * 0.015f * idleBlend;
        rightArm.zRot  =  armOsc  * idleBlend;
        leftArm.zRot   = -armOsc  * idleBlend;
        rightArm.xRot +=  bob * 0.015f * idleBlend;
        leftArm.xRot  += -bob * 0.015f * idleBlend;
        head.xRot     -= bob * 0.020f  * idleBlend;

        // ── Walk cycle ────────────────────────────────────────────────────────
        float swing     = state.walkAnimationPos;
        float intensity = state.walkAnimationSpeed * 1.4F;

        leftLeg.xRot  =  (float) Math.cos(swing) * intensity;
        rightLeg.xRot = -(float) Math.cos(swing) * intensity;
        leftArm.xRot  += -(float) Math.cos(swing) * intensity * 0.85f;
        rightArm.xRot +=  (float) Math.cos(swing) * intensity * 0.85f;

        // ── Attack arm swing ──────────────────────────────────────────────────
        // attackTime runs 0 → 1 over one swing (driven by mob.swing() in
        // GotMeleeAttackGoal).  Right arm sweeps forward like a sword slash.
        if (state.attackTime > 0f) {
            float t      = state.attackTime;
            float stroke = t < 0.5f ? t * 2f : (1f - t) * 2f;
            float arc    = (float) Math.sin(stroke * Math.PI);
            rightArm.xRot -= arc * 2.0f;   // strong forward sweep
            rightArm.zRot -= arc * 0.30f;  // slight inward roll at peak
            rightArm.yRot += arc * 0.20f;  // subtle horizontal component
        }

        // ── Bow / crossbow aim ────────────────────────────────────────────────
        if (state.isAimingBow) {
            rightArm.xRot = -1.10f;
            rightArm.yRot = -0.30f;
            rightArm.zRot =  0.05f;
            leftArm.xRot  = -0.95f;
            leftArm.yRot  =  0.30f;
            leftArm.zRot  = -0.05f;
            head.xRot    -= 0.20f;
        }

        // ── Shield block ──────────────────────────────────────────────────────
        if (state.isShieldBlocking) {
            leftArm.xRot = -0.85f;
            leftArm.yRot =  0.25f;
            leftArm.zRot =  0.20f;
        }

        // ── Talking gesture ───────────────────────────────────────────────────
        if (state.isTalking) {
            rightArm.xRot -= state.talkGesture * 0.8F;
        }
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

    // ── Render ────────────────────────────────────────────────────────────────
    // renderToBuffer() is final in net.minecraft.client.model.Model as of 1.21.x.
    // The base implementation calls root().render(...), rendering all children
    // transitively — no override needed.
}