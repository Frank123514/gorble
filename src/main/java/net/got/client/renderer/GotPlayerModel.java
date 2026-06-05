package net.got.client.renderer;

import net.got.client.animation.GotPlayerAnimator;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.joml.Vector3f;

/**
 * GOT player model that layers two animation passes on top of vanilla:
 * <ol>
 *   <li>Base locomotion (idle/walk/run/fall from GotPlayerBaseAnimations) — always playing.</li>
 *   <li>Combat animation (attack, block) — overrides the base when active.</li>
 * </ol>
 * Both passes are applied after super.setupAnim() so they sit on top of the
 * vanilla arm-swing and crouch poses.
 */
public class GotPlayerModel extends PlayerModel {

    private static final Vector3f ANIM_VEC = new Vector3f();
    private static final org.slf4j.Logger LOGGER =
            org.slf4j.LoggerFactory.getLogger("GotPlayerModel");

    // Set by GotPlayerRenderer before each render call
    AnimationDefinition activeAnimation   = null;
    float animationTimeTicks              = 0F;
    AnimationDefinition activeBaseAnimation = null;
    float baseAnimationTimeTicks          = 0F;

    // Throttle noisy per-frame log
    private int logThrottle = 0;

    public GotPlayerModel(ModelPart root, boolean slim) {
        super(root, slim);
    }

    @Override
    public void setupAnim(PlayerRenderState state) {
        super.setupAnim(state);

        if (logThrottle++ % 120 == 0) {
            LOGGER.debug("[GOT-ANIM] setupAnim base={} combat={}",
                    activeBaseAnimation != null ? "playing" : "null",
                    activeAnimation     != null ? "playing" : "null");
        }

        // ── Layer 1: base locomotion ──────────────────────────────────────────
        if (activeBaseAnimation != null) {
            KeyframeAnimations.animate(
                    this,
                    activeBaseAnimation,
                    (long)(baseAnimationTimeTicks * 50F),
                    1.0F,
                    ANIM_VEC
            );
        }

        // ── Layer 2: combat (overrides base for affected bones) ───────────────
        if (activeAnimation != null) {
            LOGGER.debug("[GOT-ANIM] Applying combat animation at t={}ticks", animationTimeTicks);
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
