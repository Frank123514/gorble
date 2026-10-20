package net.got.client.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

/**
 * HumanoidModel wrapper for chest-slot armor with custom geometry (gambeson,
 * brigandine, hauberk). Counterpart of {@link GotHelmModel} (head only) and
 * {@link GotHelmBodyModel} (head + torso + arms).
 *
 * Torso and both arms are always visible so the jacket / sleeve cubes follow
 * the normal HumanoidModel body and arm animation (swinging, sneaking, using
 * items...). Head and hat stay hidden. Legs are only shown for pieces whose
 * geometry includes pants (the hauberk), in which case the pants cubes ride
 * on the leg parts and follow the walk cycle.
 */
public class GotBodyArmorModel extends HumanoidModel<HumanoidRenderState> {

    public GotBodyArmorModel(ModelPart root) {
        this(root, false);
    }

    public GotBodyArmorModel(ModelPart root, boolean showLegs) {
        super(root);
        this.head.visible = false;
        this.hat.visible = false;
        this.body.visible = true;
        this.rightArm.visible = true;
        this.leftArm.visible = true;
        this.rightLeg.visible = showLegs;
        this.leftLeg.visible = showLegs;
    }
}
