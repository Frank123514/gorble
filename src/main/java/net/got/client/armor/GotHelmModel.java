package net.got.client.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

/**
 * A cubeless HumanoidModel returned in place of a helm's vanilla armor
 * model. Since 1.21.9, Minecraft's armor layer walks a Model's ModelParts
 * and submits their cubes with one shared equipment texture -- there's no
 * per-item hook there anymore to substitute a different render (like an
 * item model). Because every part here has zero cubes, that walk submits
 * nothing, so vanilla's flat head box never appears.
 * <p>
 * The actual rendering of the helm's own Blockbench item model happens
 * separately, in {@link GotHelmLayer}, which is added alongside (not
 * through) the vanilla armor layer.
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