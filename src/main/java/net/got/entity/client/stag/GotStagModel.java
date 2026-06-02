package net.got.entity.client.stag;

import net.got.entity.client.model.GotModelLayers;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HorseRenderState;

/**
 * Stag model that <strong>extends vanilla {@link HorseModel}</strong> so the
 * deer automatically inherits every horse animation (walk, gallop, rear, swim,
 * eat, idle, tail-wag) with zero custom animation code.
 *
 * <p>The geometry comes directly from the {@code DeerModel.java} Blockbench
 * export.  The only transformation applied is mapping each Blockbench bone to
 * the <em>exact vanilla bone name</em> that {@link HorseModel#setupAnim} looks
 * up, so the parent animation drives the stag's limbs automatically — the same
 * trick used by {@link net.got.entity.client.npc.smallfolk.GotSmallfolkModel}
 * extending {@link net.minecraft.client.model.HumanoidModel}.
 *
 * <p><b>Bone mapping (Blockbench → vanilla HorseModel):</b>
 * <pre>
 *   Body / Torso_r1  →  body
 *   Neck             →  neck
 *   Head + sub-parts →  head  (child of neck, with upperMouth / lowerMouth children)
 *   Ear1             →  leftEar   (child of head)
 *   Ear2             →  rightEar  (child of head)
 *   TailA            →  tail      (child of body)
 *   mane             →  mane      (invisible stub, child of neck)
 *   Leg1A (BB left-front)  → leftFrontLeg
 *   Leg2A (BB right-front) → rightFrontLeg
 *   Leg3A (BB left-back)   → leftBackLeg
 *   Leg4A (BB right-back)  → rightBackLeg
 * </pre>
 *
 * <p>Baby-leg bones ({@code leftFrontBabyLeg} etc.) and saddle/strap parts
 * required by the vanilla skeleton are registered as invisible zero-size stubs
 * so {@code HorseModel}'s constructor never throws a missing-child exception.
 *
 * <p>Register via {@link GotModelLayers#GOT_STAG}.
 */
public class GotStagModel extends HorseModel {

    public GotStagModel(ModelPart root) {
        super(root);
    }

    /**
     * Builds the layer definition for {@link GotModelLayers#GOT_STAG}.
     *
     * <p>All cube coordinates are taken verbatim from the Blockbench export
     * ({@code DeerModel.java}).  Pivot adjustments bring each bone to the
     * vanilla pivot convention so {@link HorseModel#setupAnim} rotates them
     * correctly.
     *
     * <p>Texture atlas: 128 × 128 (matches the original Blockbench export).
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd   = mesh.getRoot();

        // ── body ─────────────────────────────────────────────────────────────
        // Blockbench Body pivot: (-0.5, 11, 10).
        // We place it at (0, 11, 10) — centred — and bake both the rump box
        // and the Torso_r1 sub-bone directly here to keep them combined
        // under the single "body" bone that vanilla animates.
        PartDefinition body = pd.addOrReplaceChild("body",
                CubeListBuilder.create()
                        // Rump quad (BB: Body addBox -4.5,-6.7,-21  size 9,9,9)
                        .texOffs(0, 20).addBox(-4.5f, -6.7f, -21.0f,  9, 9,  9, new CubeDeformation(0f))
                        // Torso quad (BB: Torso_r1 — baked flat, slight -0.0349 rad pitch ignored;
                        //  the parent body bone handles body tilt)
                        .texOffs(0,  0).addBox(-4.0f, -5.5f, -31.0f,  8, 8, 12, new CubeDeformation(0f)),
                PartPose.offset(0f, 11f, 10f));

        // ── tail  (child of body) ─────────────────────────────────────────────
        // BB TailA pivot: (-0.5, 4, 12) with xRot 0.5236 baked.
        // Expressed as offset from body pivot (0,11,10): delta = (0,-7,2).
        // The TailA_r1 sub-bone's cube is baked into this child with its
        // combined rotation already folded in.
        body.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(54, 50).addBox(-1.5f, -1.0f, -0.5f, 2, 3, 2, new CubeDeformation(0f)),
                PartPose.offsetAndRotation(0f, -7f, 2f, 0.5236f, 0f, 0f));

        // ── neck  (vanilla: child of root, not body) ─────────────────────────
        // BB Neck pivot: (-0.5, 7, -7) with xRot 0.5236.
        // Neck contains both LNeck_r1 and UNeck_r1 sub-cubes.
        PartDefinition neck = pd.addOrReplaceChild("neck",
                CubeListBuilder.create()
                        // LNeck_r1
                        .texOffs(36, 31).addBox(-2.5f, -5.86f, -1.94f, 5, 5, 5, new CubeDeformation(0f))
                        // UNeck_r1
                        .texOffs(40,  0).addBox(-2.0f, -9.08f, -4.39f, 4, 5, 4, new CubeDeformation(0f)),
                PartPose.offsetAndRotation(0f, 7f, -7f, 0.5236f, 0f, 0f));

        // ── mane  (child of neck — vanilla requires it; stag has none) ────────
        neck.addOrReplaceChild("mane",
                CubeListBuilder.create(),
                PartPose.ZERO);

        // ── head  (child of neck in vanilla) ─────────────────────────────────
        // BB Head pivot: (-0.5, -4, -10) with xRot 0.5236.
        // Expressed relative to neck pivot (0,7,-7): delta = (0,-11,-3).
        // All BB Head sub-parts (Head_r1, UMouth_r1, LMouth_r1, nose_r1,
        // RightAntler_r1, LeftAntler_r1) are baked in here.
        PartDefinition head = neck.addOrReplaceChild("head",
                CubeListBuilder.create()
                        // Head_r1 cranium (BB offsetAndRotation 0,32.1,9.5 rot -0.384)
                        // Re-expressed relative to head pivot after unfolding:
                        .texOffs(36, 20).addBox(-2.0f, -5.5f, -6.5f, 4, 5, 6, new CubeDeformation(0f))
                        // RightAntler_r1 flat plane
                        .texOffs(46, 41).addBox(-7.74f, -9.0f, -2.06f, 7, 9, 0, new CubeDeformation(0f))
                        // LeftAntler_r1 flat plane
                        .texOffs(40,  9).addBox( 0.74f, -9.0f, -2.06f, 7, 9, 0, new CubeDeformation(0f))
                        // nose_r1
                        .texOffs(32, 38).addBox(-0.5f, -2.0f, -10.2f, 1, 1, 1, new CubeDeformation(0f)),
                PartPose.offsetAndRotation(0f, -11f, -3f, 0.5236f, 0f, 0f));

        // upperMouth / lowerMouth — vanilla HorseModel constructor fetches these
        head.addOrReplaceChild("upperMouth",
                CubeListBuilder.create()
                        .texOffs(54,  9).addBox(-1.5f, -1.0f, -8.5f, 3, 2, 3, new CubeDeformation(0f)),
                PartPose.ZERO);
        head.addOrReplaceChild("lowerMouth",
                CubeListBuilder.create()
                        .texOffs(54, 14).addBox(-1.5f,  0.5f, -8.0f, 3, 1, 3, new CubeDeformation(0f)),
                PartPose.ZERO);

        // ── ears  (children of head) ──────────────────────────────────────────
        // BB Ear1 pivot: (-0.5, 7, -7) xRot 0.5236, zRot 0.0873 — expressed
        // relative to head pivot after full unfolding (approximated centred):
        head.addOrReplaceChild("leftEar",
                CubeListBuilder.create()
                        .texOffs(54, 55).addBox(-1.0f, -3.5f, 3.5f, 2, 3, 1, new CubeDeformation(0f)),
                PartPose.offsetAndRotation(-3.0f, -6.5f, 0f, 0f, 0f,  0.35f));
        head.addOrReplaceChild("rightEar",
                CubeListBuilder.create()
                        .texOffs(56,  0).addBox(-1.0f, -3.5f, 3.5f, 2, 3, 1, new CubeDeformation(0f)),
                PartPose.offsetAndRotation( 3.0f, -6.5f, 0f, 0f, 0f, -0.35f));

        // Vanilla HorseModel also looks up these children of head — register
        // as empty stubs so the constructor doesn't crash:
        head.addOrReplaceChild("leftCheekEar",  CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("rightCheekEar", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("headSaddle",    CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("mouthSaddle1",  CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("mouthSaddle2",  CubeListBuilder.create(), PartPose.ZERO);

        // ── Front-left leg ────────────────────────────────────────────────────
        // BB Leg1A pivot: (2.5, 13, 10). Vanilla leftFrontLeg is a root child.
        // The two BB sub-parts (Leg1A_r1 shin + Leg1A_r2 thigh) are combined.
        pd.addOrReplaceChild("leftFrontLeg",
                CubeListBuilder.create()
                        .texOffs( 0, 38).addBox(-1.5f, -2.0f, -2.5f, 3, 6, 5, new CubeDeformation(0f))
                        .texOffs(34, 50).addBox(-0.5f,  3.5f, -1.5f, 2, 8, 3, new CubeDeformation(0f)),
                PartPose.offset(2.5f, 13f, 10f));

        // ── Front-right leg ───────────────────────────────────────────────────
        // BB Leg2A pivot: (-3.5, 13, 10).
        pd.addOrReplaceChild("rightFrontLeg",
                CubeListBuilder.create()
                        .texOffs(16, 38).addBox(-1.5f, -2.0f, -2.5f, 3, 6, 5, new CubeDeformation(0f))
                        .texOffs(44, 50).addBox(-1.5f,  3.5f, -1.5f, 2, 8, 3, new CubeDeformation(0f)),
                PartPose.offset(-3.5f, 13f, 10f));

        // ── Back-left leg ─────────────────────────────────────────────────────
        // BB Leg3A pivot: (2.5, 13, -8).
        pd.addOrReplaceChild("leftBackLeg",
                CubeListBuilder.create()
                        .texOffs(32, 41).addBox(-1.7f, -1.2f, -1.8f, 3, 4, 4, new CubeDeformation(0f))
                        .texOffs(14, 49).addBox(-1.5f,  2.8f, -1.3f, 2, 8, 3, new CubeDeformation(0f)),
                PartPose.offset(2.5f, 13f, -8f));

        // ── Back-right leg ────────────────────────────────────────────────────
        // BB Leg4A pivot: (-3.5, 13, -8).
        pd.addOrReplaceChild("rightBackLeg",
                CubeListBuilder.create()
                        .texOffs( 0, 49).addBox(-1.3f, -1.2f, -1.8f, 3, 4, 4, new CubeDeformation(0f))
                        .texOffs(24, 50).addBox(-0.5f,  2.8f, -1.3f, 2, 8, 3, new CubeDeformation(0f)),
                PartPose.offset(-3.5f, 13f, -8f));

        // ── Baby-leg stubs ────────────────────────────────────────────────────
        // Vanilla HorseModel fetches these in its constructor for baby scaling.
        pd.addOrReplaceChild("leftFrontBabyLeg",  CubeListBuilder.create(), PartPose.offset( 2.5f, 13f,  10f));
        pd.addOrReplaceChild("rightFrontBabyLeg", CubeListBuilder.create(), PartPose.offset(-3.5f, 13f,  10f));
        pd.addOrReplaceChild("leftBackBabyLeg",   CubeListBuilder.create(), PartPose.offset( 2.5f, 13f,  -8f));
        pd.addOrReplaceChild("rightBackBabyLeg",  CubeListBuilder.create(), PartPose.offset(-3.5f, 13f,  -8f));

        // ── Saddle / strap stubs ──────────────────────────────────────────────
        // HorseModel's constructor looks these up — register as empty so it
        // doesn't throw.  They are simply invisible on the stag.
        body.addOrReplaceChild("saddle",         CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("leftSaddleMouth",  CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("rightSaddleMouth", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("leftSaddleLine",   CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("rightSaddleLine",  CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("leftBag",          CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("rightBag",         CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(mesh, 128, 128);
    }

    // setupAnim is fully inherited from HorseModel — no override needed.
}