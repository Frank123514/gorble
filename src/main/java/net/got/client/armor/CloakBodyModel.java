package net.got.client.armor;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;

/**
 * Torso + arms model for the cape and fur cloak. Everything follows the normal HumanoidModel body / arm animation
 * (the shoulder pads ride on the arms), and on top of that the hinged back panel is pulled backwards while the
 * wearer moves: further when walking, more again when sprinting, lifted while falling, with a small flutter in
 * time with the stride. It hangs straight when the wearer stands still, and does not swing forwards when
 * they back up (it would clip into the body).
 */
public class CloakBodyModel extends GotBodyArmorModel {

    /** Name of the hinged back panel part (child of "body") in {@link CapeModel} and {@link FurCloakModel}. */
    public static final String BACK_PART = "cloak_back";

    // Radians.
    private static final float REST_ANGLE      = 0.06F;  // hangs a hair away from the back
    private static final float WALK_PULL       = 0.38F;  // ~22 degrees at full walking speed
    private static final float SPRINT_PULL     = 0.20F;  // ~11 more when sprinting
    private static final float FALL_LIFT       = 0.45F;  // billows up when falling
    private static final float IDLE_FLUTTER    = 0.025F;
    private static final float STRIDE_FLUTTER  = 0.07F;
    private static final float SIDE_SWAY       = 0.05F;
    private static final float MAX_ANGLE       = 0.70F;  // ~40 degrees

    private final ModelPart back;

    public CloakBodyModel(ModelPart root) {
        super(root, false);
        this.back = this.body.getChild(BACK_PART);
    }

    @Override
    public void setupAnim(HumanoidRenderState state) {
        super.setupAnim(state);   // also resets every part to its initial pose, so the additions below don't pile up

        CloakMotion motion = state.getRenderData(CloakLayer.MOTION_KEY);
        if (motion == null) motion = CloakMotion.STILL;

        float walk = Mth.clamp(state.walkAnimationSpeed, 0.0F, 1.0F);
        float pull = walk * Math.max(motion.forward(), 0.0F);

        float angle = REST_ANGLE
                + pull * WALK_PULL
                + pull * motion.sprint() * SPRINT_PULL
                + motion.fall() * FALL_LIFT
                + Mth.sin(state.ageInTicks * 0.08F) * IDLE_FLUTTER
                + Mth.cos(state.walkAnimationPos * 1.3324F) * walk * STRIDE_FLUTTER;

        // Positive xRot swings the bottom of the panel towards +z, i.e. backwards.
        this.back.xRot += Mth.clamp(angle, 0.03F, MAX_ANGLE);
        this.back.zRot += Mth.sin(state.walkAnimationPos * 0.6662F) * walk * SIDE_SWAY;
    }
}
