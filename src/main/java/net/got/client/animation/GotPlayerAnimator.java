package net.got.client.animation;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Drives {@link GotPlayerCombatAnimations} onto the vanilla player model.
 *
 * In MC 1.21.4 the render state system means the model's bones are set by
 * vanilla's setupAnim() on every frame, overwriting anything we put there
 * from a tick event. The fix is to apply our animation inside the render
 * pipeline itself, after setupAnim — see {@link net.got.mixin.PlayerRendererMixin}.
 *
 * This class manages animation state (current pose, time, playing/blocking)
 * and provides {@link #applyToModel(PlayerModel)} for the Mixin to call.
 */
public final class GotPlayerAnimator {

    public static final GotPlayerAnimator INSTANCE = new GotPlayerAnimator();
    private GotPlayerAnimator() {}

    // ── State ─────────────────────────────────────────────────────────────────
    private GotArmPose          currentPose = GotArmPose.NONE;
    private AnimationDefinition currentAnim = null;
    private float               animTime    = 0F;
    private boolean             animPlaying = false;
    private boolean             isBlocking  = false;
    private long                lastNanos   = -1L;

    private static final Vector3f ANIM_VEC = new Vector3f();

    // ── Public API ────────────────────────────────────────────────────────────

    public boolean hasActiveAnimation() {
        return currentAnim != null;
    }

    public void triggerAttack(GotArmPose pose) {
        if (pose == GotArmPose.NONE || pose == GotArmPose.BLOCK) return;
        currentPose = pose;
        currentAnim = animationFor(pose);
        animTime    = 0F;
        animPlaying = true;
        lastNanos   = -1L;
    }

    public void setBlocking(boolean blocking) {
        this.isBlocking = blocking;
        if (blocking && !animPlaying) {
            currentPose = GotArmPose.BLOCK;
            currentAnim = GotPlayerCombatAnimations.SWORD_BLOCK;
            animTime    = GotPlayerCombatAnimations.SWORD_BLOCK.lengthInSeconds();
            animPlaying = false;
        } else if (!blocking && currentPose == GotArmPose.BLOCK) {
            resetState();
        }
    }

    public void tick(float partialTick) {
        if (!animPlaying || currentAnim == null) return;
        long now = System.nanoTime();
        if (lastNanos < 0) { lastNanos = now; return; }
        float delta = (now - lastNanos) / 1_000_000_000F;
        lastNanos = now;
        animTime += delta;
        if (animTime >= currentAnim.lengthInSeconds()) {
            if (isBlocking && currentPose != GotArmPose.BLOCK) {
                currentPose = GotArmPose.BLOCK;
                currentAnim = GotPlayerCombatAnimations.SWORD_BLOCK;
                animTime    = GotPlayerCombatAnimations.SWORD_BLOCK.lengthInSeconds();
                animPlaying = false;
            } else {
                resetState();
            }
        }
    }

    /**
     * Apply the current animation frame directly to a {@link PlayerModel}.
     * Called by {@link net.got.mixin.PlayerRendererMixin} after vanilla's
     * setupAnim() has already set the rest pose, so our transforms win.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void applyToModel(PlayerModel model) {
        if (currentAnim == null || model == null) return;

        float t = animPlaying
                ? Math.min(animTime, currentAnim.lengthInSeconds())
                : currentAnim.lengthInSeconds();

        KeyframeAnimations.animate(
                new BoneAdapter(model),
                currentAnim,
                (long)(t * 1000F),
                1.0F,
                ANIM_VEC
        );
    }

    // ── Internals ─────────────────────────────────────────────────────────────

    private void resetState() {
        currentPose = GotArmPose.NONE;
        currentAnim = null;
        animTime    = 0F;
        animPlaying = false;
    }

    private static AnimationDefinition animationFor(GotArmPose pose) {
        return switch (pose) {
            case SWORD      -> GotPlayerCombatAnimations.SWORD_ATTACK;
            case GREATSWORD -> GotPlayerCombatAnimations.GREATSWORD_ATTACK;
            case AXE        -> GotPlayerCombatAnimations.AXE_ATTACK;
            case SPEAR      -> GotPlayerCombatAnimations.SPEAR_ATTACK;
            case BLOCK      -> GotPlayerCombatAnimations.SWORD_BLOCK;
            default         -> null;
        };
    }

    // ── Bone adapter ──────────────────────────────────────────────────────────

    @SuppressWarnings("rawtypes")
    private static final class BoneAdapter extends net.minecraft.client.model.Model {

        private final PlayerModel inner;

        BoneAdapter(PlayerModel inner) {
            super(new ModelPart(List.of(), Map.of()), RenderType::entityCutout);
            this.inner = inner;
        }

        @Override
        public Optional<ModelPart> getAnyDescendantWithName(String name) {
            return switch (name) {
                case "right_arm" -> Optional.of(inner.rightArm);
                case "left_arm"  -> Optional.of(inner.leftArm);
                case "body"      -> Optional.of(inner.body);
                case "head"      -> Optional.of(inner.head);
                default          -> Optional.empty();
            };
        }
    }
}
