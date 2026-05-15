package net.got.entity.client.stag;

import net.got.entity.client.model.GotModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

/**
 * Custom Java model for {@link net.got.entity.stag.GotStagEntity}.
 *
 * <p>Converted from {@code assets/got/geo/got_stag.geo.json} (texture 64×64).
 *
 * <p>Animations driven inline from {@code assets/got/animations/got_stag.animation.json}:
 * idle, walk, run, rear, swim, eat, tail_wag.
 *
 * <p>Register via {@link GotModelLayers#GOT_STAG}.
 */
public class GotStagModel extends EntityModel<GotStagRenderState> {

    // ── Bones ──────────────────────────────────────────────────────────────────
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart ear1;
    private final ModelPart ear2;
    private final ModelPart tailA;
    private final ModelPart leg1a;
    private final ModelPart leg2a;
    private final ModelPart leg3a;
    private final ModelPart leg4a;

    // ── Constructor ────────────────────────────────────────────────────────────

    public GotStagModel(ModelPart root) {
        super(root, RenderType::entityCutoutNoCull);
        this.body  = root.getChild("body");
        this.neck  = root.getChild("neck");
        this.head  = root.getChild("head");
        this.ear1  = root.getChild("ear1");
        this.ear2  = root.getChild("ear2");
        this.tailA = root.getChild("tailA");
        this.leg1a = root.getChild("leg1a");
        this.leg2a = root.getChild("leg2a");
        this.leg3a = root.getChild("leg3a");
        this.leg4a = root.getChild("leg4a");
    }

    // ── Layer definition ───────────────────────────────────────────────────────

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd   = mesh.getRoot();

        // ── Body  geo pivot [-0.5,13,10] → jPiv (0.5,11,10) ─────────────────
        PartDefinition pBody = pd.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 44).addBox(-4f, -5.5f, -13.7f, 8, 8, 12, new CubeDeformation(0f))
                        .texOffs(28,  3).addBox(-4.5f, -6.7f, -21f, 9, 9, 9, new CubeDeformation(0f)),
                PartPose.offset(0.5f, 11f, 10f));

        // ── Neck  geo pivot [-0.5,17,-7] → jPiv (0.5, 7, -7) ────────────────
        PartDefinition pNeck = pd.addOrReplaceChild("neck",
                CubeListBuilder.create()
                        .texOffs(12, 21).addBox(-2.5f, -5.86f, -1.94f, 5, 5, 5, new CubeDeformation(0f))
                        .texOffs(16, 35).addBox(-2f, -9.08f, -4.39f, 4, 5, 4, new CubeDeformation(0f)),
                PartPose.offset(0.5f, 7f, -7f));

        // ── Head  geo pivot [-0.5,28,-10] → jPiv (0.5,-4,-10) ───────────────
        pd.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(17,  1).addBox(-2f,    6f,    -17f,   4, 5, 6, new CubeDeformation(0f))
                        .texOffs( 0, 20).addBox(-1.5f,  7.61f, -20.21f,3, 2, 3, new CubeDeformation(0f))
                        .texOffs( 0, 16).addBox(-2.5f,  0.53f, -9.77f, 3, 1, 3, new CubeDeformation(0f))
                        .texOffs( 0,  8).addBox(-7.14f,-8.06f, -2.06f, 6, 8, 0, new CubeDeformation(0f))
                        .texOffs( 0, 25).addBox( 1.14f,-8.06f, -2.06f, 6, 8, 0, new CubeDeformation(0f)),
                PartPose.offset(0.5f, -4f, -10f));

        // ── Ear1 (left)  same pivot as neck ──────────────────────────────────
        pd.addOrReplaceChild("ear1",
                CubeListBuilder.create()
                        .texOffs(22, 31).addBox(-31.08f, -14.19f, -17.84f, 2, 3, 1, new CubeDeformation(0f)),
                PartPose.offset(0.5f, 7f, -7f));

        // ── Ear2 (right)  same pivot as neck ─────────────────────────────────
        pd.addOrReplaceChild("ear2",
                CubeListBuilder.create()
                        .texOffs(22, 31).addBox(27.48f, -13.99f, -18.14f, 2, 3, 1, new CubeDeformation(0f)),
                PartPose.offset(0.5f, 7f, -7f));

        // ── TailA  geo pivot [-0.5,20,12] → jPiv (0.5, 4, 12) ───────────────
        pd.addOrReplaceChild("tailA",
                CubeListBuilder.create()
                        .texOffs(11, 33).addBox(-1f, -4.2f, -10.2f, 2, 3, 2, new CubeDeformation(0f)),
                PartPose.offset(0.5f, 4f, 12f));

        // ── Legs ──────────────────────────────────────────────────────────────
        pd.addOrReplaceChild("leg1a",
                CubeListBuilder.create()
                        .texOffs(42, 44).addBox(-1.3f, -2.4f, -6.5f, 3, 6, 5, new CubeDeformation(0f))
                        .texOffs(54, 34).addBox(-0.5f,  2.6f, -4.4f, 2, 7, 3, new CubeDeformation(0f)),
                PartPose.offset(-2.5f, 13f, 10f));

        pd.addOrReplaceChild("leg2a",
                CubeListBuilder.create()
                        .texOffs( 0, 33).addBox(-1.7f, -2.4f, -6.5f, 3, 6, 5, new CubeDeformation(0f))
                        .texOffs(44, 34).addBox(-1.5f,  2.8f, -4.4f, 2, 7, 3, new CubeDeformation(0f)),
                PartPose.offset(3.5f, 13f, 10f));

        pd.addOrReplaceChild("leg3a",
                CubeListBuilder.create()
                        .texOffs(43, 23).addBox(-0.5f,  2.8f, -1.3f, 2, 8, 3, new CubeDeformation(0f))
                        .texOffs(40, 55).addBox(-1.3f, -2.2f, -1.8f, 3, 5, 4, new CubeDeformation(0f)),
                PartPose.offset(-2.5f, 13f, -8f));

        pd.addOrReplaceChild("leg4a",
                CubeListBuilder.create()
                        .texOffs(53, 23).addBox(-1.5f,  2.8f, -1.3f, 2, 8, 3, new CubeDeformation(0f))
                        .texOffs(28, 48).addBox(-1.7f, -1.2f, -1.8f, 3, 4, 4, new CubeDeformation(0f)),
                PartPose.offset(3.5f, 13f, -8f));

        return LayerDefinition.create(mesh, 64, 64);
    }

    // ── setupAnim ─────────────────────────────────────────────────────────────

    @Override
    public void setupAnim(GotStagRenderState state) {
        resetAllParts();

        float limbSwing       = state.walkAnimationPos;
        float limbSwingAmount = state.walkAnimationSpeed;
        float ageInTicks      = state.ageInTicks;
        float netHeadYaw      = state.yHeadRot;
        float headPitch       = state.xRot;

        float speed     = limbSwingAmount;
        float isRunning = Mth.clamp(speed * 3f - 1f, 0f, 1f);

        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        head.xRot = headPitch  * Mth.DEG_TO_RAD;

        if (state.isInWater) {
            float swimPhase = Mth.sin(limbSwing * 0.6662f);
            leg1a.xRot =  swimPhase * 25f * Mth.DEG_TO_RAD;
            leg2a.xRot = -swimPhase * 25f * Mth.DEG_TO_RAD;
            leg3a.xRot = -swimPhase * 25f * Mth.DEG_TO_RAD;
            leg4a.xRot =  swimPhase * 25f * Mth.DEG_TO_RAD;
            body.xRot  = 15f * Mth.DEG_TO_RAD;
        } else if (state.isStanding) {
            body.xRot  = -32.5f * Mth.DEG_TO_RAD;
            neck.xRot  = -10f   * Mth.DEG_TO_RAD;
            head.xRot  = -10f   * Mth.DEG_TO_RAD;
            leg3a.xRot = -95f   * Mth.DEG_TO_RAD;
            leg4a.xRot = -95f   * Mth.DEG_TO_RAD;
        } else if (speed > 0.05f) {
            float legSwing = Mth.lerp(isRunning, 25f, 40f);
            float phase    = Mth.sin(limbSwing * 0.6662f);
            leg1a.xRot =  phase * legSwing * Mth.DEG_TO_RAD;
            leg2a.xRot = -phase * legSwing * Mth.DEG_TO_RAD;
            leg3a.xRot = -phase * legSwing * Mth.DEG_TO_RAD;
            leg4a.xRot =  phase * legSwing * Mth.DEG_TO_RAD;
            float neckBob = Mth.lerp(isRunning, 5f, -5f);
            neck.xRot  = neckBob * Mth.DEG_TO_RAD;
            head.xRot  = neckBob * Mth.DEG_TO_RAD;
            float bobFreq = Mth.lerp(isRunning, 2f, 4f);
            body.y = Mth.sin(limbSwing * bobFreq * 0.3f) * 0.3f;
        } else {
            float breathe = Mth.sin(ageInTicks * 0.033f);
            body.y    = breathe * 0.2f;
            neck.xRot = -breathe * 3f * Mth.DEG_TO_RAD;
            head.xRot = -breathe * 3f * Mth.DEG_TO_RAD;
            tailA.yRot = Mth.sin(ageInTicks * 0.07f) * 5f * Mth.DEG_TO_RAD;
            ear1.yRot = -Mth.sin(ageInTicks * 0.035f) *  8f * Mth.DEG_TO_RAD;
            ear2.yRot =  Mth.sin(ageInTicks * 0.035f) *  8f * Mth.DEG_TO_RAD;
        }

        if (state.isEating) {
            head.xRot += 30f * Mth.DEG_TO_RAD;
            neck.xRot += 20f * Mth.DEG_TO_RAD;
        }
    }

    /** Reset all part transforms (renamed to avoid conflict with final Model.resetPose). */
    private void resetAllParts() {
        body.resetPose();
        neck.resetPose();
        head.resetPose();
        ear1.resetPose();
        ear2.resetPose();
        tailA.resetPose();
        leg1a.resetPose();
        leg2a.resetPose();
        leg3a.resetPose();
        leg4a.resetPose();
    }
}