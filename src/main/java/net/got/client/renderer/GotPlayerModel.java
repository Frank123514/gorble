package net.got.client.renderer;

import net.got.client.animation.GotPlayerAnimator;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.joml.Vector3f;

/**
 * GOT player model that replaces vanilla's pose with two GOT animation passes:
 * <ol>
 *   <li>Base locomotion (idle/walk/run/fall from GotPlayerBaseAnimations) — always playing.</li>
 *   <li>Combat animation (attack, block) — overrides the base when active.</li>
 * </ol>
 *
 * <p>super.setupAnim() still runs first (vanilla needs it to position cosmetic
 * layers like the hat/cape/elytra, set up render-state flags, and — important —
 * to apply head rotation from the player's actual look direction). Every bone
 * except the head is then explicitly reset to rest pose before either GOT layer
 * is applied: KeyframeAnimations.animate() is additive onto whatever the bone's
 * current pose already is, so without this reset GOT's animation would stack
 * on top of vanilla's arm-swing/crouch pose instead of replacing it. The head is
 * deliberately left un-reset so GOT's head keyframes add on top of look
 * direction rather than overriding it (see the inline comment below).
 *
 * <p>The base layer is applied after the reset, then the combat layer is
 * applied on top of base WITHOUT a second reset — that's intentional, so combat
 * overrides base only on the bones it actually keys (e.g. arms/torso during a
 * sword swing), while base's leg motion from walking/running keeps playing
 * underneath it.
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

        // Nothing for GOT to do — leave vanilla's pose untouched.
        if (activeBaseAnimation == null && activeAnimation == null) {
            return;
        }

        // ── Reset bones GOT fully owns back to rest pose ───────────────────────
        // This is what makes GOT's animations REPLACE vanilla's pose rather than
        // add on top of it, for the bones where GOT is the sole source of truth.
        //
        // The head is deliberately NOT reset: vanilla's setupAnim sets head
        // rotation from where the player is actually looking (mouse look), and
        // that has to survive. GOT's head keyframes (small idle sway/bob) are
        // meant to add on top of look-direction, the same way vanilla's own
        // head animations work — resetting it would snap the head to face
        // forward regardless of where the player is looking.
        this.body.resetPose();
        this.rightArm.resetPose();
        this.leftArm.resetPose();
        this.rightLeg.resetPose();
        this.leftLeg.resetPose();

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

