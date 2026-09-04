package net.got.client.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

/**
 * A minimal HumanoidModel wrapping one of the custom-baked helm ModelParts
 * from {@link GotHelmModels}. Only .head is ever visible -- body/arms/legs
 * exist solely because HumanoidModel's constructor requires those named
 * children to be present, and are zero-size / hidden.
 */
public class GotHelmModel extends HumanoidModel<HumanoidRenderState> {

    public GotHelmModel(ModelPart root) {
        super(root);
        this.hat.visible = false;
        this.body.visible = false;
        this.rightArm.visible = false;
        this.leftArm.visible = false;
        this.rightLeg.visible = false;
        this.leftLeg.visible = false;
        this.head.visible = true;
    }
}
