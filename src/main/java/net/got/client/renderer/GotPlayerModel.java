package net.got.client.renderer;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;

/**
 * GOT player model subclass.
 *
 * <p>Animation application is handled entirely by PlayerRendererMixin, which
 * injects at the TAIL of vanilla PlayerModel.setupAnim(). This class must NOT
 * call KeyframeAnimations.animate() — doing so causes every bone to be
 * transformed twice per frame (once here, once by the mixin), producing
 * corrupted/double-transformed poses that look nothing like the Blockbench source.
 */
public class GotPlayerModel extends PlayerModel {

    public GotPlayerModel(ModelPart root, boolean slim) {
        super(root, slim);
    }

    @Override
    public void setupAnim(PlayerRenderState state) {
        // Vanilla handles cosmetic layers (hat/cape/elytra), render-state flags,
        // and head rotation from look direction. The mixin does the rest.
        super.setupAnim(state);
    }
}