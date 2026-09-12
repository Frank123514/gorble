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

    @Override
    public void setupAnim(HumanoidRenderState state) {
        super.setupAnim(state);
        // No extra rotation here. This class is shared by every helm type
        // (see ClientSetup#registerHelm -- they all use GotHelmModel::new),
        // so anything added to head.yRot here stacks on top of every helm's
        // live look-based pitch/yaw, every frame. A constant yaw baked into
        // that field fights the pitch rotation applied after it, which is
        // what caused helmets to swing/orbit instead of sitting still on
        // camera movement. Any per-helm facing correction belongs in that
        // helm's own geometry (see HalfhelmModel), not here.
    }
}