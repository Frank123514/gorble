package net.got.client.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

/**
 * HumanoidModel wrapper for leg-slot / feet-slot armor with custom geometry (wool breeches, leather
 * boots). Only the two legs are visible; head, hat, body and arms exist solely because HumanoidModel's
 * constructor requires those named children to be present.
 */
public class GotLegArmorModel extends HumanoidModel<HumanoidRenderState> {

    public GotLegArmorModel(ModelPart root) {
        super(root);
        this.head.visible = false;
        this.hat.visible = false;
        this.body.visible = false;
        this.rightArm.visible = false;
        this.leftArm.visible = false;
        this.rightLeg.visible = true;
        this.leftLeg.visible = true;
    }
}
