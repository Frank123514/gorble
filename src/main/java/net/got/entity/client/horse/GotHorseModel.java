package net.got.entity.client.horse;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

/**
 * Custom Java model for {@link net.got.entity.horse.GotHorseEntity}.
 *
 * <p>Converted from {@code assets/got/geo/got_horse.geo.json} (texture 128×128).
 *
 * <p>Skeleton mirrors the geo bone hierarchy:
 * <pre>
 *   root
 *   └─ body
 *      ├─ neck
 *      │  └─ head
 *      │     ├─ umouth / lmouth
 *      │     ├─ ear1 / ear2 / muleearl / muleearr
 *      │     ├─ headsaddle / saddlemouthl / saddlemouthr / …
 *      ├─ mane
 *      ├─ taila → tailb → tailc
 *      ├─ saddle / saddleb / saddlec / saddlel/r / bag1 / bag2
 *   root (sibling legs)
 *   ├─ leg1a → leg1b → leg1c   (front-left)
 *   ├─ leg2a → leg2b → leg2c   (front-right)
 *   ├─ leg3a → leg3b → leg3c   (back-left)
 *   └─ leg4a → leg4b → leg4c   (back-right)
 * </pre>
 *
 * <p>Animations driven from {@code got_horse.animation.json}:
 * idle, walk, run, rear, swim, eat, tail_wag.
 *
 * <p>Register via {@link GotModelLayers#GOT_HORSE}.
 */
public class GotHorseModel extends EntityModel<GotHorseRenderState> {

    // ── Bone references ───────────────────────────────────────────────────────

    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart umouth;
    private final ModelPart lmouth;
    private final ModelPart ear1;
    private final ModelPart ear2;
    private final ModelPart mane;
    private final ModelPart tailA;
    private final ModelPart tailB;
    private final ModelPart tailC;

    // Legs — upper (a), middle (b), lower hoof (c)
    private final ModelPart leg1a; // front-left
    private final ModelPart leg1b;
    private final ModelPart leg1c;
    private final ModelPart leg2a; // front-right
    private final ModelPart leg2b;
    private final ModelPart leg2c;
    private final ModelPart leg3a; // back-left
    private final ModelPart leg3b;
    private final ModelPart leg3c;
    private final ModelPart leg4a; // back-right
    private final ModelPart leg4b;
    private final ModelPart leg4c;

    // ── Constructor ───────────────────────────────────────────────────────────

    public GotHorseModel(ModelPart root) {
        super(root, RenderType::entityCutoutNoCull);
        this.body   = root.getChild("body");
        this.neck   = body.getChild("neck");
        this.head   = body.getChild("head");
        this.umouth = head.getChild("umouth");
        this.lmouth = head.getChild("lmouth");
        this.ear1   = body.getChild("ear1");
        this.ear2   = body.getChild("ear2");
        this.mane   = body.getChild("mane");
        this.tailA  = body.getChild("taila");
        this.tailB  = tailA.getChild("tailb");
        this.tailC  = tailB.getChild("tailc");
        this.leg1a  = root.getChild("leg1a");
        this.leg1b  = leg1a.getChild("leg1b");
        this.leg1c  = leg1b.getChild("leg1c");
        this.leg2a  = root.getChild("leg2a");
        this.leg2b  = leg2a.getChild("leg2b");
        this.leg2c  = leg2b.getChild("leg2c");
        this.leg3a  = root.getChild("leg3a");
        this.leg3b  = leg3a.getChild("leg3b");
        this.leg3c  = leg3b.getChild("leg3c");
        this.leg4a  = root.getChild("leg4a");
        this.leg4b  = leg4a.getChild("leg4b");
        this.leg4c  = leg4b.getChild("leg4c");
    }

    // ── Layer definition ──────────────────────────────────────────────────────

    /**
     * Builds the mesh for {@link GotModelLayers#GOT_HORSE}.
     *
     * <p>Coordinate conversion from Bedrock geo.json (pivot root = ground at y=0):
     * <pre>
     *   Java part pivot:  jPivX = -geoPivotX,  jPivY = 24 - geoPivotY,  jPivZ = geoPivotZ
     *   Cube relative to pivot:
     *     jRelX = -(geoOriginX + geoSizeX - geoPivotX)
     *     jRelY = geoPivotY - (geoOriginY + geoSizeY)
     *     jRelZ = geoOriginZ - geoPivotZ
     * </pre>
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd   = mesh.getRoot();

        // ── body  geo pivot [0,15,8] → jPiv (0,9,8) ──────────────────────────
        PartDefinition pBody = pd.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 34).addBox(-5f, -6f, -18f, 10, 10, 24, new CubeDeformation(0f)),
                PartPose.offset(0f, 9f, 8f));

        // ── neck  geo pivot [0,20,-10], child of body ─────────────────────────
        PartDefinition pNeck = pBody.addOrReplaceChild("neck",
                CubeListBuilder.create()
                        .texOffs(0, 12).addBox(-1.95f, -9.8f, -2f, 4, 14, 8, new CubeDeformation(0f)),
                PartPose.offset(0f, -5f, -18f));

        // ── head  geo pivot [0,20,-10], child of body (same pivot as neck) ────
        PartDefinition pHead = pBody.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2.5f, -10f, -1.5f, 5, 5, 7, new CubeDeformation(0f)),
                PartPose.offset(0f, -5f, -18f));

        pHead.addOrReplaceChild("umouth",
                CubeListBuilder.create()
                        .texOffs(24, 18).addBox(-2f, -10f, -7f, 4, 3, 6, new CubeDeformation(0f)),
                PartPose.offset(0f, 0.05f, 0f));

        pHead.addOrReplaceChild("lmouth",
                CubeListBuilder.create()
                        .texOffs(24, 27).addBox(-2f, -7f, -6.5f, 4, 2, 5, new CubeDeformation(0f)),
                PartPose.ZERO);

        pBody.addOrReplaceChild("ear1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2.45f, -12f, 4f, 2, 3, 1, new CubeDeformation(0f)),
                PartPose.offset(0f, -5f, -18f));

        pBody.addOrReplaceChild("ear2",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(0.45f, -12f, 4f, 2, 3, 1, new CubeDeformation(0f)),
                PartPose.offset(0f, -5f, -18f));

        pBody.addOrReplaceChild("mane",
                CubeListBuilder.create()
                        .texOffs(58, 0).addBox(-1f, -11.5f, 5f, 2, 16, 4, new CubeDeformation(0f)),
                PartPose.offset(0f, -5f, -18f));

        // ── tail  geo pivot [0,21,14] ─────────────────────────────────────────
        PartDefinition pTailA = pBody.addOrReplaceChild("taila",
                CubeListBuilder.create()
                        .texOffs(44, 0).addBox(-1f, -1f, 0f, 2, 2, 3, new CubeDeformation(0f)),
                PartPose.offset(0f, -6f, 6f));
        PartDefinition pTailB = pTailA.addOrReplaceChild("tailb",
                CubeListBuilder.create()
                        .texOffs(38, 7).addBox(-1.5f, -2f, 3f, 3, 4, 7, new CubeDeformation(0f)),
                PartPose.ZERO);
        pTailB.addOrReplaceChild("tailc",
                CubeListBuilder.create()
                        .texOffs(24, 3).addBox(-1.5f, -4.5f, 9f, 3, 4, 7, new CubeDeformation(0f)),
                PartPose.ZERO);

        // ── Legs ──────────────────────────────────────────────────────────────
        PartDefinition pLeg1a = pd.addOrReplaceChild("leg1a",
                CubeListBuilder.create()
                        .texOffs(78, 29).addBox(-1.5f, -2f, -2.5f, 4, 9, 5, new CubeDeformation(0f)),
                PartPose.offset(-4f, 9f, 11f));
        PartDefinition pLeg1b = pLeg1a.addOrReplaceChild("leg1b",
                CubeListBuilder.create()
                        .texOffs(78, 43).addBox(-1f, 0f, -1.5f, 3, 5, 3, new CubeDeformation(0f)),
                PartPose.offset(0f, 7f, 0f));
        pLeg1b.addOrReplaceChild("leg1c",
                CubeListBuilder.create()
                        .texOffs(78, 51).addBox(-1.5f, 0.1f, -2f, 4, 3, 4, new CubeDeformation(0f)),
                PartPose.offset(0f, 5f, 0f));

        PartDefinition pLeg2a = pd.addOrReplaceChild("leg2a",
                CubeListBuilder.create()
                        .texOffs(96, 29).addBox(-2.5f, -2f, -2.5f, 4, 9, 5, new CubeDeformation(0f)),
                PartPose.offset(4f, 9f, 11f));
        PartDefinition pLeg2b = pLeg2a.addOrReplaceChild("leg2b",
                CubeListBuilder.create()
                        .texOffs(96, 43).addBox(-1f, 0f, -1.5f, 3, 5, 3, new CubeDeformation(0f)),
                PartPose.offset(0f, 7f, 0f));
        pLeg2b.addOrReplaceChild("leg2c",
                CubeListBuilder.create()
                        .texOffs(96, 51).addBox(-1.5f, 0.1f, -2f, 4, 3, 4, new CubeDeformation(0f)),
                PartPose.offset(0f, 5f, 0f));

        PartDefinition pLeg3a = pd.addOrReplaceChild("leg3a",
                CubeListBuilder.create()
                        .texOffs(44, 29).addBox(-1.1f, -1f, -2.1f, 3, 8, 4, new CubeDeformation(0f)),
                PartPose.offset(-4f, 9f, -8f));
        PartDefinition pLeg3b = pLeg3a.addOrReplaceChild("leg3b",
                CubeListBuilder.create()
                        .texOffs(44, 41).addBox(-1.1f, 0f, -1.6f, 3, 5, 3, new CubeDeformation(0f)),
                PartPose.offset(0f, 7f, 0f));
        pLeg3b.addOrReplaceChild("leg3c",
                CubeListBuilder.create()
                        .texOffs(44, 51).addBox(-1.6f, 0.1f, -2.1f, 4, 3, 4, new CubeDeformation(0f)),
                PartPose.offset(0f, 5f, 0f));

        PartDefinition pLeg4a = pd.addOrReplaceChild("leg4a",
                CubeListBuilder.create()
                        .texOffs(60, 29).addBox(-1.9f, -1f, -2.1f, 3, 8, 4, new CubeDeformation(0f)),
                PartPose.offset(4f, 9f, -8f));
        PartDefinition pLeg4b = pLeg4a.addOrReplaceChild("leg4b",
                CubeListBuilder.create()
                        .texOffs(60, 41).addBox(-1.9f, 0f, -1.6f, 3, 5, 3, new CubeDeformation(0f)),
                PartPose.offset(0f, 7f, 0f));
        pLeg4b.addOrReplaceChild("leg4c",
                CubeListBuilder.create()
                        .texOffs(60, 51).addBox(-2.4f, 0.1f, -2.1f, 4, 3, 4, new CubeDeformation(0f)),
                PartPose.offset(0f, 5f, 0f));

        return LayerDefinition.create(mesh, 128, 128);
    }

    // ── setupAnim ─────────────────────────────────────────────────────────────

    @Override
    public void setupAnim(GotHorseRenderState state) {
        resetAllParts();

        float limbSwing       = state.walkAnimationPos;
        float limbSwingAmount = state.walkAnimationSpeed;
        float ageInTicks      = state.ageInTicks;
        float netHeadYaw      = state.yHeadRot;
        float headPitch       = state.xRot;
        float speed           = limbSwingAmount;
        float isRunning       = Mth.clamp(speed * 4f - 1f, 0f, 1f);

        // Head look
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        head.xRot = headPitch  * Mth.DEG_TO_RAD;

        if (state.isInWater) {
            body.xRot = 15f * Mth.DEG_TO_RAD;
            float swimPhase = Mth.sin(limbSwing * 0.6662f);
            leg1a.xRot =  swimPhase * 25f * Mth.DEG_TO_RAD;
            leg2a.xRot = -swimPhase * 25f * Mth.DEG_TO_RAD;
            leg3a.xRot = -swimPhase * 25f * Mth.DEG_TO_RAD;
            leg4a.xRot =  swimPhase * 25f * Mth.DEG_TO_RAD;
        } else if (state.isStanding) {
            body.xRot   = -32.5f * Mth.DEG_TO_RAD;
            neck.xRot   =  -5f   * Mth.DEG_TO_RAD;
            leg3a.xRot  = -95f   * Mth.DEG_TO_RAD;
            leg4a.xRot  = -95f   * Mth.DEG_TO_RAD;
            leg3a.y     =  7f;
            leg4a.y     =  6.5f;
        } else if (state.isEating) {
            neck.xRot  =  35f * Mth.DEG_TO_RAD;
            head.xRot  =  35f * Mth.DEG_TO_RAD;
            umouth.xRot = -15f * Mth.DEG_TO_RAD;
        } else if (speed > 0.05f) {
            float swing = Mth.lerp(isRunning, 25f, 40f);
            float phase = Mth.sin(limbSwing * 0.6662f);
            leg1a.xRot = phase *  swing * Mth.DEG_TO_RAD;
            leg1b.xRot = Mth.clamp(-phase, 0f, 1f) * 20f * Mth.DEG_TO_RAD;
            leg2a.xRot = -phase * swing * Mth.DEG_TO_RAD;
            leg2b.xRot = Mth.clamp(phase, 0f, 1f) * 20f * Mth.DEG_TO_RAD;
            leg3a.xRot = -phase * swing * Mth.DEG_TO_RAD;
            leg3b.xRot = Mth.clamp(phase, 0f, 1f) * 20f * Mth.DEG_TO_RAD;
            leg4a.xRot = phase *  swing * Mth.DEG_TO_RAD;
            leg4b.xRot = Mth.clamp(-phase, 0f, 1f) * 20f * Mth.DEG_TO_RAD;
            float bobAmp = Mth.lerp(isRunning, 0.5f, 1.0f);
            body.y = Mth.sin(limbSwing * 2f * 0.3f) * bobAmp;
            float neckNod = isRunning * (Mth.sin(limbSwing * 2f * 0.3f) * 5f);
            neck.xRot += neckNod * Mth.DEG_TO_RAD;
            head.xRot += neckNod * Mth.DEG_TO_RAD;
        } else {
            float breathe = Mth.sin(ageInTicks * 0.033f);
            body.y    = breathe * 0.2f;
            neck.xRot = -breathe * 3f * Mth.DEG_TO_RAD;
            head.xRot = -breathe * 3f * Mth.DEG_TO_RAD;
        }

        tailA.zRot = Mth.sin(ageInTicks * 0.07f) * 25f * Mth.DEG_TO_RAD;
    }

    /** Reset all part transforms (renamed to avoid conflict with final Model.resetPose). */
    void resetAllParts() {
        body.resetPose();
        neck.resetPose();
        head.resetPose();
        umouth.resetPose();
        lmouth.resetPose();
        ear1.resetPose();
        ear2.resetPose();
        mane.resetPose();
        tailA.resetPose();
        leg1a.resetPose(); leg1b.resetPose(); leg1c.resetPose();
        leg2a.resetPose(); leg2b.resetPose(); leg2c.resetPose();
        leg3a.resetPose(); leg3b.resetPose(); leg3c.resetPose();
        leg4a.resetPose(); leg4b.resetPose(); leg4c.resetPose();
    }

    /**
     * Copy bone transforms to another GotHorseModel (for overlay layers).
     *
     * <p>Called by {@link GotHorseMarkingsLayer} and {@link GotHorseArmorLayer}
     * so overlay models share the same animated pose.
     */
    public void copyState(GotHorseModel target) {
        target.body.copyFrom(this.body);
        target.neck.copyFrom(this.neck);
        target.head.copyFrom(this.head);
        target.umouth.copyFrom(this.umouth);
        target.lmouth.copyFrom(this.lmouth);
        target.ear1.copyFrom(this.ear1);
        target.ear2.copyFrom(this.ear2);
        target.mane.copyFrom(this.mane);
        target.tailA.copyFrom(this.tailA);
        target.tailB.copyFrom(this.tailB);
        target.tailC.copyFrom(this.tailC);
        target.leg1a.copyFrom(this.leg1a); target.leg1b.copyFrom(this.leg1b); target.leg1c.copyFrom(this.leg1c);
        target.leg2a.copyFrom(this.leg2a); target.leg2b.copyFrom(this.leg2b); target.leg2c.copyFrom(this.leg2c);
        target.leg3a.copyFrom(this.leg3a); target.leg3b.copyFrom(this.leg3b); target.leg3c.copyFrom(this.leg3c);
        target.leg4a.copyFrom(this.leg4a); target.leg4b.copyFrom(this.leg4b); target.leg4c.copyFrom(this.leg4c);
    }
}