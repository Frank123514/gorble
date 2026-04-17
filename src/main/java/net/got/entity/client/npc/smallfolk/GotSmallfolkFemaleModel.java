package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

/**
 * Custom female Smallfolk model — created in Blockbench 5.1.3 and integrated
 * into the GotSmallfolk rendering pipeline.
 *
 * <p>Uses a standard (Steve-width, 4 px) arm geometry, NOT slim arms.
 * The breasts sub-part adds the feminine silhouette without requiring any
 * vanilla "slim arm" (Alex) model hackery.
 *
 * <p>Exported for Minecraft 1.17+ / Mojang mappings, updated for 1.21 render
 * pipeline (SmallfolkRenderState replaces the raw Entity generic).
 */
public class GotSmallfolkFemaleModel extends EntityModel<SmallfolkRenderState> {

    /**
     * Layer location — bake this with
     * {@code EntityRendererProvider.Context#bakeLayer} and pass the resulting
     * {@link ModelPart} to {@link #GotSmallfolkFemaleModel(ModelPart)}.
     */
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("got", "gotsmallfolkfemalemodel"), "main");

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
    private final ModelPart breasts;
    private final ModelPart leftLeg;
    private final ModelPart leftPants;
    private final ModelPart rightLeg;
    private final ModelPart rightPants;

    // ── Constructor ───────────────────────────────────────────────────────────

    public GotSmallfolkFemaleModel(ModelPart root) {
        // EntityModel(ModelPart) stores the root and exposes it via root().
        // renderToBuffer() is now final in Model and calls root().render(...),
        // so passing the outer baked ModelPart here is correct — the inner
        // "root" child and all descendants are rendered transitively.
        super(root);
        this.root      = root.getChild("root");
        this.waist     = this.root.getChild("waist");
        this.body      = this.waist.getChild("body");
        this.head      = this.body.getChild("head");
        this.hat       = this.head.getChild("hat");
        this.cape      = this.body.getChild("cape");
        this.leftArm   = this.body.getChild("leftArm");
        this.leftSleeve  = this.leftArm.getChild("leftSleeve");
        this.leftItem    = this.leftArm.getChild("leftItem");
        this.rightArm  = this.body.getChild("rightArm");
        this.rightSleeve = this.rightArm.getChild("rightSleeve");
        this.rightItem   = this.rightArm.getChild("rightItem");
        this.jacket    = this.body.getChild("jacket");
        this.breasts   = this.body.getChild("breasts");
        this.leftLeg   = this.root.getChild("leftLeg");
        this.leftPants   = this.leftLeg.getChild("leftPants");
        this.rightLeg  = this.root.getChild("rightLeg");
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

        // Standard (non-slim) left arm — 3 wide, matching Steve geometry
        PartDefinition leftArm = body.addOrReplaceChild("leftArm",
                CubeListBuilder.create()
                        .texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        leftArm.addOrReplaceChild("leftSleeve",
                CubeListBuilder.create()
                        .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        leftArm.addOrReplaceChild("leftItem",
                CubeListBuilder.create(),
                PartPose.offset(1.0F, 7.0F, 1.0F));

        // Standard (non-slim) right arm
        PartDefinition rightArm = body.addOrReplaceChild("rightArm",
                CubeListBuilder.create()
                        .texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        rightArm.addOrReplaceChild("rightSleeve",
                CubeListBuilder.create()
                        .texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        rightArm.addOrReplaceChild("rightItem",
                CubeListBuilder.create(),
                PartPose.offset(-1.0F, 7.0F, 1.0F));

        body.addOrReplaceChild("jacket",
                CubeListBuilder.create()
                        .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // Breasts sub-part — angled forward using an X-rotation of ~-27.5° (-0.4800 rad)
        PartDefinition breasts = body.addOrReplaceChild("breasts",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 5.0F, -2.5F));
        breasts.addOrReplaceChild("breasts_r1",
                CubeListBuilder.create()
                        .texOffs(24, 3).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4800F, 0.0F, 0.0F));

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

    @Override
    public void setupAnim(SmallfolkRenderState state) {
        // Head look via render-state yaw/pitch (populated by SmallfolkRenderer)
        head.yRot = state.talkHeadYaw;
        head.xRot = state.talkHeadPitch;

        // Basic walk cycle — mirror the vanilla bipedal swing
        float swing     = state.walkAnimationPos;
        float intensity = state.walkAnimationSpeed * 0.5F;

        leftLeg.xRot  =  (float) Math.cos(swing)        * intensity;
        rightLeg.xRot = -(float) Math.cos(swing)        * intensity;
        leftArm.xRot  = -(float) Math.cos(swing)        * intensity;
        rightArm.xRot =  (float) Math.cos(swing)        * intensity;

        // Talking gesture — raise right arm slightly
        if (state.isTalking) {
            rightArm.xRot -= state.talkGesture * 0.8F;
        }
    }

    // ── Render ────────────────────────────────────────────────────────────────
    // renderToBuffer() is final in net.minecraft.client.model.Model as of 1.21.x.
    // The base implementation calls root().render(...), which renders the ModelPart
    // passed to super() above and all its children — no override needed.
}