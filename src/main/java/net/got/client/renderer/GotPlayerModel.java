package net.got.client.renderer;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.joml.Vector3f;

public class GotPlayerModel extends PlayerModel {

    private static final Vector3f ANIM_VEC = new Vector3f();
    private static final org.slf4j.Logger LOGGER =
            org.slf4j.LoggerFactory.getLogger("GotPlayerModel");

    // Set by GotPlayerRenderer before each render call
    AnimationDefinition activeAnimation = null;
    float animationTimeTicks = 0F;

    // Throttle logging so we don't spam every frame
    private int logThrottle = 0;

    public GotPlayerModel(ModelPart root, boolean slim) {
        super(root, slim);
    }

    @Override
    public void setupAnim(PlayerRenderState state) {
        super.setupAnim(state);

        if (logThrottle++ % 60 == 0) {
            LOGGER.info("[GOT-ANIM] setupAnim called, activeAnimation={}", activeAnimation);
        }

        if (activeAnimation != null) {
            LOGGER.info("[GOT-ANIM] Applying animation {} at t={}ticks", activeAnimation, animationTimeTicks);
            KeyframeAnimations.animate(
                    this,
                    activeAnimation,
                    (long)(animationTimeTicks * 50F),
                    1.0F,
                    ANIM_VEC
            );
        }
    }
}