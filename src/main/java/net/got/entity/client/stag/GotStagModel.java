package net.got.entity.client.stag;

import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HorseRenderState;

/**
 * Stag model extending vanilla {@link HorseModel}.
 *
 * <p>The createBodyLayer() skeleton mirrors the EXACT bone names and hierarchy
 * that vanilla 1.21.4 {@code AbstractEquineModel} expects, the same way
 * {@code GotSmallfolkModel} mirrors {@code HumanoidModel}'s skeleton.
 * Only the cube geometry differs — stag silhouette instead of horse.
 *
 * <p>Vanilla 1.21.4 AbstractEquineModel bone tree (root-level children):
 * <pre>
 *   head_parts
 *     neck
 *       head
 *         upper_mouth
 *         lower_mouth
 *         left_ear   / right_ear
 *         left_cheek_ear / right_cheek_ear
 *         head_saddle
 *         mouth_saddle_wrap1 / mouth_saddle_wrap2
 *   body
 *     tail
 *     saddle
 *     left_saddle_mouth / right_saddle_mouth
 *     left_saddle_line  / right_saddle_line
 *     left_chest / right_chest
 *   left_front_leg  / right_front_leg
 *   left_back_leg   / right_back_leg
 *   left_front_baby_leg / right_front_baby_leg
 *   left_back_baby_leg  / right_back_baby_leg
 *   mane  (child of neck in old model; now top-level stub expected by HorseModel)
 * </pre>
 */
public class GotStagModel extends HorseModel {

    public GotStagModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd   = mesh.getRoot();

        // ── head_parts  (AnimatedPart container — AbstractEquineModel fetches this first) ─
        // Zero-size pivot at origin; all head/neck animation goes through this group.
        PartDefinition headParts = pd.addOrReplaceChild("head_parts",
                CubeListBuilder.create(),
                PartPose.ZERO);

        // neck  (child of head_parts)
        // Vanilla pivot: (0, 4, -12).  Stag neck same placement, slight forward lean baked in.
        PartDefinition neck = headParts.addOrReplaceChild("neck",
                CubeListBuilder.create()
                        .texOffs(36, 31).addBox(-2.5f, -5.86f, -1.94f, 5, 5, 5, new CubeDeformation(0f))
                        .texOffs(40,  0).addBox(-2.0f, -9.08f, -4.39f, 4, 5, 4, new CubeDeformation(0f)),
                PartPose.offsetAndRotation(0f, 4f, -12f, 0.5236f, 0f, 0f));

        // mane  (child of neck — vanilla HorseModel looks this up; stag has none so invisible stub)
        neck.addOrReplaceChild("mane",
                CubeListBuilder.create(),
                PartPose.ZERO);

        // head  (child of neck)
        PartDefinition head = neck.addOrReplaceChild("head",
                CubeListBuilder.create()
                        // cranium
                        .texOffs(36, 20).addBox(-2.0f, -5.5f, -6.5f, 4, 5, 6, new CubeDeformation(0f))
                        // right antler flat plane
                        .texOffs(46, 41).addBox(-7.74f, -9.0f, -2.06f, 7, 9, 0, new CubeDeformation(0f))
                        // left antler flat plane
                        .texOffs(40,  9).addBox( 0.74f, -9.0f, -2.06f, 7, 9, 0, new CubeDeformation(0f))
                        // nose
                        .texOffs(32, 38).addBox(-0.5f, -2.0f, -10.2f, 1, 1, 1, new CubeDeformation(0f)),
                PartPose.offsetAndRotation(0f, -11f, -3f, 0.5236f, 0f, 0f));

        head.addOrReplaceChild("upper_mouth",
                CubeListBuilder.create()
                        .texOffs(54,  9).addBox(-1.5f, -1.0f, -8.5f, 3, 2, 3, new CubeDeformation(0f)),
                PartPose.ZERO);
        head.addOrReplaceChild("lower_mouth",
                CubeListBuilder.create()
                        .texOffs(54, 14).addBox(-1.5f,  0.5f, -8.0f, 3, 1, 3, new CubeDeformation(0f)),
                PartPose.ZERO);

        head.addOrReplaceChild("left_ear",
                CubeListBuilder.create()
                        .texOffs(54, 55).addBox(-1.0f, -3.5f, 3.5f, 2, 3, 1, new CubeDeformation(0f)),
                PartPose.offsetAndRotation(-3.0f, -6.5f, 0f, 0f, 0f,  0.35f));
        head.addOrReplaceChild("right_ear",
                CubeListBuilder.create()
                        .texOffs(56,  0).addBox(-1.0f, -3.5f, 3.5f, 2, 3, 1, new CubeDeformation(0f)),
                PartPose.offsetAndRotation( 3.0f, -6.5f, 0f, 0f, 0f, -0.35f));

        // Invisible stubs required by HorseModel/AbstractEquineModel constructor
        head.addOrReplaceChild("left_cheek_ear",   CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("right_cheek_ear",  CubeListBuilder.create(), PartPose.ZERO);

        // head_saddle and mouth_saddle_wrap are children of head_parts (this.head in vanilla), not the deep head bone
        headParts.addOrReplaceChild("head_saddle",       CubeListBuilder.create(), PartPose.ZERO);
        headParts.addOrReplaceChild("mouth_saddle_wrap", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("mouth_saddle_wrap2", CubeListBuilder.create().texOffs(54, 14).addBox(-1.5f,  0.5f, -8.0f, 3, 1, 3, new CubeDeformation(0f)), PartPose.ZERO);

        // ── body  (root-level) ───────────────────────────────────────────────
        PartDefinition body = pd.addOrReplaceChild("body",
                CubeListBuilder.create()
                        // rump
                        .texOffs(0, 20).addBox(-4.5f, -6.7f, -21.0f, 9, 9,  9, new CubeDeformation(0f))
                        // torso
                        .texOffs(0,  0).addBox(-4.0f, -5.5f, -31.0f, 8, 8, 12, new CubeDeformation(0f)),
                PartPose.offset(0f, 11f, 10f));

        body.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(54, 50).addBox(-1.5f, -1.0f, -0.5f, 2, 3, 2, new CubeDeformation(0f)),
                PartPose.offsetAndRotation(0f, -7f, 2f, 0.5236f, 0f, 0f));

        // Saddle / strap stubs
        body.addOrReplaceChild("saddle",             CubeListBuilder.create(), PartPose.ZERO);
        // saddle mouth/line stubs are children of head_parts (this.head in vanilla)
        headParts.addOrReplaceChild("left_saddle_mouth",  CubeListBuilder.create(), PartPose.ZERO);
        headParts.addOrReplaceChild("right_saddle_mouth", CubeListBuilder.create(), PartPose.ZERO);
        headParts.addOrReplaceChild("left_saddle_line",   CubeListBuilder.create(), PartPose.ZERO);
        headParts.addOrReplaceChild("right_saddle_line",  CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("left_chest",         CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("right_chest",        CubeListBuilder.create(), PartPose.ZERO);

        // ── Legs (root-level) ────────────────────────────────────────────────
        pd.addOrReplaceChild("left_front_leg",
                CubeListBuilder.create()
                        .texOffs( 0, 38).addBox(-1.5f, -2.0f, -2.5f, 3, 6, 5, new CubeDeformation(0f))
                        .texOffs(34, 50).addBox(-0.5f,  3.5f, -1.5f, 2, 8, 3, new CubeDeformation(0f)),
                PartPose.offset(2.5f, 13f, 10f));

        pd.addOrReplaceChild("right_front_leg",
                CubeListBuilder.create()
                        .texOffs(16, 38).addBox(-1.5f, -2.0f, -2.5f, 3, 6, 5, new CubeDeformation(0f))
                        .texOffs(44, 50).addBox(-1.5f,  3.5f, -1.5f, 2, 8, 3, new CubeDeformation(0f)),
                PartPose.offset(-3.5f, 13f, 10f));

        pd.addOrReplaceChild("left_hind_leg",
                CubeListBuilder.create()
                        .texOffs(32, 41).addBox(-1.7f, -1.2f, -1.8f, 3, 4, 4, new CubeDeformation(0f))
                        .texOffs(14, 49).addBox(-1.5f,  2.8f, -1.3f, 2, 8, 3, new CubeDeformation(0f)),
                PartPose.offset(2.5f, 13f, -8f));

        pd.addOrReplaceChild("right_hind_leg",
                CubeListBuilder.create()
                        .texOffs( 0, 49).addBox(-1.3f, -1.2f, -1.8f, 3, 4, 4, new CubeDeformation(0f))
                        .texOffs(24, 50).addBox(-0.5f,  2.8f, -1.3f, 2, 8, 3, new CubeDeformation(0f)),
                PartPose.offset(-3.5f, 13f, -8f));

        // Baby leg stubs — AgeableMobRenderer scales these for foals
        pd.addOrReplaceChild("left_front_baby_leg",  CubeListBuilder.create(), PartPose.offset( 2.5f, 13f,  10f));
        pd.addOrReplaceChild("right_front_baby_leg", CubeListBuilder.create(), PartPose.offset(-3.5f, 13f,  10f));
        pd.addOrReplaceChild("left_hind_baby_leg",   CubeListBuilder.create(), PartPose.offset( 2.5f, 13f,  -8f));
        pd.addOrReplaceChild("right_hind_baby_leg",  CubeListBuilder.create(), PartPose.offset(-3.5f, 13f,  -8f));

        return LayerDefinition.create(mesh, 128, 128);
    }
}