package net.got.client.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

/**
 * Like {@link GotHelmModel}, but for head-slot items whose geometry also
 * covers the torso and arms (the mail coif's jacket and sleeves). Head, body
 * and both arms stay visible so those parts render and follow the normal
 * HumanoidModel body/arm animation; the hat layer and legs stay hidden.
 */
public class GotHelmBodyModel extends HumanoidModel<HumanoidRenderState> {

    public GotHelmBodyModel(ModelPart root) {
        super(root);
        this.hat.visible = false;
        this.rightLeg.visible = false;
        this.leftLeg.visible = false;
        this.head.visible = true;
        this.body.visible = true;
        this.rightArm.visible = true;
        this.leftArm.visible = true;
    }
}
