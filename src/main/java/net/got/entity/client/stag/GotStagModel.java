package net.got.entity.client.stag;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.joml.Vector3f;

/**
 * Custom deer model for {@link net.got.entity.stag.GotStagEntity}.
 * Geometry is a direct port of the Blockbench export (gotdeer.bbmodel).
 * Animations are driven by {@link KeyframeAnimations} via
 * {@link #applyAnimation}, called from {@link GotStagRenderer}.
 *
 * <p>In 1.21.4, {@code EntityModel<T>} is parameterised on the
 * <em>render state</em> type, not the entity.  {@code setupAnim} receives
 * a {@link GotStagRenderState}; actual animation selection is done in
 * {@link GotStagRenderer#render} before the super-call so the model just
 * delegates to the last animation applied via {@link #applyAnimation}.
 */
public class GotStagModel extends EntityModel<GotStagRenderState> {

    // ── Root-level bones ──────────────────────────────────────────────────────

    final ModelPart Body;
    final ModelPart TailA;
    final ModelPart Leg1A;
    final ModelPart Leg2A;
    final ModelPart Leg3A;
    final ModelPart Leg4A;
    final ModelPart Head;
    final ModelPart Ear1;
    final ModelPart Ear2;
    final ModelPart Neck;

    public GotStagModel(ModelPart root) {
        super(root);
        this.Body   = root.getChild("Body");
        this.TailA  = root.getChild("TailA");
        this.Leg1A  = root.getChild("Leg1A");
        this.Leg2A  = root.getChild("Leg2A");
        this.Leg3A  = root.getChild("Leg3A");
        this.Leg4A  = root.getChild("Leg4A");
        this.Head   = root.getChild("Head");
        this.Ear1   = root.getChild("Ear1");
        this.Ear2   = root.getChild("Ear2");
        this.Neck   = root.getChild("Neck");
        // hitbox: invisible pivot — obtained lazily via root if needed
    }

    // ── Layer definition ──────────────────────────────────────────────────────

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd   = mesh.getRoot();

        // ── Body ──────────────────────────────────────────────────────────────
        PartDefinition Body = pd.addOrReplaceChild("Body",
                CubeListBuilder.create()
                        .texOffs(0, 20).addBox(-4.5F, -6.7F, -21.0F, 9, 9, 9, new CubeDeformation(0.0F)),
                PartPose.offset(-0.5F, 11.0F, 10.0F));

        Body.addOrReplaceChild("Body_r1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.0F, -21.0F, -13.0F, 8, 8, 12, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 15.5F, -0.7F, -0.0349F, 0.0F, 0.0F));

        // ── TailA ─────────────────────────────────────────────────────────────
        PartDefinition TailA = pd.addOrReplaceChild("TailA",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-0.5F, 4.0F, 12.0F, 0.5236F, 0.0F, 0.0F));

        TailA.addOrReplaceChild("TailA_r1",
                CubeListBuilder.create()
                        .texOffs(54, 50).addBox(-1.5F, -19.1986F, 9.3012F, 2, 3, 2, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, 15.0F, -19.5F, -0.3142F, 0.0F, 0.0F));

        // ── Leg1A (front-right) ───────────────────────────────────────────────
        PartDefinition Leg1A = pd.addOrReplaceChild("Leg1A",
                CubeListBuilder.create(),
                PartPose.offset(2.5F, 13.0F, 10.0F));

        Leg1A.addOrReplaceChild("Leg1A_r1",
                CubeListBuilder.create()
                        .texOffs(34, 50).addBox(1.0F, -7.8F, -10.0F, 2, 8, 3, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, 10.4F, 5.6F, 0.0698F, 0.0F, 0.0F));

        Leg1A.addOrReplaceChild("Leg1A_r2",
                CubeListBuilder.create()
                        .texOffs(0, 38).addBox(-2.0F, -4.0F, -1.0F, 3, 6, 5, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.3F, 1.6F, -5.5F, 0.1309F, 0.0F, 0.0F));

        // ── Leg2A (front-left) ────────────────────────────────────────────────
        PartDefinition Leg2A = pd.addOrReplaceChild("Leg2A",
                CubeListBuilder.create(),
                PartPose.offset(-3.5F, 13.0F, 10.0F));

        Leg2A.addOrReplaceChild("Leg2A_r1",
                CubeListBuilder.create()
                        .texOffs(44, 50).addBox(-3.0F, -7.6F, -10.0F, 2, 8, 3, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, 10.4F, 5.6F, 0.0698F, 0.0F, 0.0F));

        Leg2A.addOrReplaceChild("Leg2A_r2",
                CubeListBuilder.create()
                        .texOffs(16, 38).addBox(-2.0F, -4.0F, -1.0F, 3, 6, 5, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.7F, 1.6F, -5.5F, 0.0873F, 0.0F, 0.0F));

        // ── Leg3A (back-right) ────────────────────────────────────────────────
        pd.addOrReplaceChild("Leg3A",
                CubeListBuilder.create()
                        .texOffs(14, 49).addBox(-1.5F,  2.8F, -1.3F, 2, 8, 3, new CubeDeformation(0.0F))
                        .texOffs(40,  9).addBox(-1.7F, -2.2F, -1.8F, 3, 5, 4, new CubeDeformation(0.0F)),
                PartPose.offset(2.5F, 13.0F, -8.0F));

        // ── Leg4A (back-left) ─────────────────────────────────────────────────
        pd.addOrReplaceChild("Leg4A",
                CubeListBuilder.create()
                        .texOffs(24, 50).addBox(-0.5F,  2.8F, -1.3F, 2, 8, 3, new CubeDeformation(0.0F))
                        .texOffs( 0, 49).addBox(-1.3F, -1.2F, -1.8F, 3, 4, 4, new CubeDeformation(0.0F)),
                PartPose.offset(-3.5F, 13.0F, -8.0F));

        // ── Head ──────────────────────────────────────────────────────────────
        PartDefinition Head = pd.addOrReplaceChild("Head",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-0.5F, -4.0F, -10.0F, 0.5236F, 0.0F, 0.0F));

        Head.addOrReplaceChild("Head_r1",
                CubeListBuilder.create()
                        .texOffs(46, 41).addBox(-7.7396F, -8.9634F, -1.0642F, 7, 9, 0, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.4F, -0.1F, -1.0F, -0.6404F, 0.284F, -0.5112F));

        Head.addOrReplaceChild("Head_r2",
                CubeListBuilder.create()
                        .texOffs(32, 41).addBox(0.7396F, -8.9634F, -1.0642F, 7, 9, 0, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.4F, -0.1F, -1.0F, -0.6404F, -0.284F, 0.5112F));

        Head.addOrReplaceChild("Head_r3",
                CubeListBuilder.create()
                        .texOffs(32, 38).addBox(-1.0F, -1.9848F, 0.1737F, 1, 1, 1, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -0.4F, -9.6F, -0.3491F, 0.0F, 0.0F));

        Head.addOrReplaceChild("Head_r4",
                CubeListBuilder.create()
                        .texOffs(54, 14).addBox(-2.0F, -1.9724F, -1.7665F, 3, 1, 3, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, 2.5F, -8.0F, -0.288F, 0.0F, 0.0F));

        Head.addOrReplaceChild("Head_r5",
                CubeListBuilder.create()
                        .texOffs(54, 9).addBox(-2.0F, -25.0938F, -30.5109F, 3, 2, 3, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, 32.7F, 10.3F, -0.384F, 0.0F, 0.0F));

        Head.addOrReplaceChild("Head_r6",
                CubeListBuilder.create()
                        .texOffs(36, 20).addBox(-2.0F, -26.0938F, -26.5109F, 4, 5, 6, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 32.1F, 9.5F, -0.384F, 0.0F, 0.0F));

        // ── Ear1 ──────────────────────────────────────────────────────────────
        PartDefinition Ear1 = pd.addOrReplaceChild("Ear1",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-0.5F, 7.0F, -7.0F, 0.5236F, 0.0F, 0.0873F));

        Ear1.addOrReplaceChild("Ear1_r1",
                CubeListBuilder.create()
                        .texOffs(54, 55).mirror()
                        .addBox(10.1795F, -31.4863F, -15.0362F, 2, 2, 1, new CubeDeformation(0.0F))
                        .mirror(false),
                PartPose.offsetAndRotation(17.3F, 17.5F, -4.6F, -0.4638F, -0.4179F, -0.9008F));

        // ── Ear2 ──────────────────────────────────────────────────────────────
        PartDefinition Ear2 = pd.addOrReplaceChild("Ear2",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-0.5F, 7.0F, -7.0F, 0.5236F, 0.0F, -0.0873F));

        Ear2.addOrReplaceChild("Ear2_r1",
                CubeListBuilder.create()
                        .texOffs(56, 0).addBox(-12.1795F, -31.4863F, -15.0362F, 2, 2, 1, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-17.3F, 17.5F, -4.6F, -0.4638F, 0.4179F, 0.9008F));

        // ── Neck ──────────────────────────────────────────────────────────────
        PartDefinition Neck = pd.addOrReplaceChild("Neck",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-0.5F, 7.0F, -7.0F, 0.5236F, 0.0F, 0.0F));

        Neck.addOrReplaceChild("Neck_r1",
                CubeListBuilder.create()
                        .texOffs(40, 0).addBox(-1.0F, -21.0795F, -10.3907F, 4, 5, 4, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 12.0F, 6.0F, -0.1222F, 0.0F, 0.0F));

        Neck.addOrReplaceChild("Neck_r2",
                CubeListBuilder.create()
                        .texOffs(36, 31).addBox(-3.0F, -21.1613F, -11.5446F, 5, 5, 5, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, 15.3F, 9.6F, 0.0524F, 0.0F, 0.0F));

        // ── Hitbox pivot (invisible, no cubes) ────────────────────────────────
        pd.addOrReplaceChild("hitbox", CubeListBuilder.create(),
                PartPose.offset(-1.5F, 26.5F, 7.3F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    // ── Animation ─────────────────────────────────────────────────────────────

    /** Reusable scratch vector — avoids allocation every frame. */
    private static final Vector3f ANIMATION_VEC = new Vector3f();

    /**
     * Applies a {@link AnimationDefinition} clip to this model.
     * Called from {@link GotStagRenderer} before the render super-call.
     *
     * @param definition animation clip to play
     * @param ageInTicks running tick counter used as the animation clock
     * @param weight     blend weight in [0, 1]
     */
    public void applyAnimation(AnimationDefinition definition, float ageInTicks, float weight) {
        KeyframeAnimations.animate(this, definition, (long) ageInTicks, weight, ANIMATION_VEC);
    }

    /**
     * {@inheritDoc}
     *
     * <p>In 1.21.4, {@code setupAnim} receives the pre-built render state.
     * Actual animation selection happens in {@link GotStagRenderer#render}
     * via {@link #applyAnimation} before the super-call, so this override
     * is intentionally empty — {@code renderToBuffer} (final in
     * {@code Model}) reads whatever the animator already wrote.
     */
    @Override
    public void setupAnim(GotStagRenderState state) {
        // Animation is driven externally by GotStagRenderer.
    }
}
