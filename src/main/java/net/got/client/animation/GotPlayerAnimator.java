package net.got.client.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3f;

import net.minecraft.client.renderer.RenderType;

import java.util.Optional;
import java.util.List;
import java.util.Map;

/**
 * Drives {@link GotPlayerCombatAnimations} onto the vanilla player model arms.
 *
 * <p>Uses the same {@link KeyframeAnimations#animate} call as every other
 * animation in the mod.  A lightweight {@link BoneAdapter} resolves bone name
 * lookups without needing to extend {@link net.minecraft.client.model.Model}
 * or touch its private fields.
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

    /** Apply the current animation frame to the local player's arm model. */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void applyToPlayer(Player player) {
        if (currentAnim == null || player == null) return;

        Minecraft mc = Minecraft.getInstance();
        // getRenderer() is wildcarded; cast through Object to avoid the capture error
        Object renderer = mc.getEntityRenderDispatcher().getRenderer(player);
        if (!(renderer instanceof PlayerRenderer playerRenderer)) return;

        // PlayerModel is non-generic in 1.21.4 — use raw type
        PlayerModel playerModel = playerRenderer.getModel();

        // Reset the four bones we touch so each frame starts from rest pose
        playerModel.rightArm.resetPose();
        playerModel.leftArm.resetPose();
        playerModel.body.resetPose();
        playerModel.head.resetPose();

        float t = animPlaying
                ? Math.min(animTime, currentAnim.lengthInSeconds())
                : currentAnim.lengthInSeconds();

        KeyframeAnimations.animate(
                new BoneAdapter(playerModel),
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

    /**
     * Minimal {@link net.minecraft.client.model.Model} subclass whose only job
     * is to route {@link #getAnyDescendantWithName} to the four player model
     * parts we animate.
     *
     * <p>We do NOT override {@code renderToBuffer} (it is {@code final} in
     * {@link net.minecraft.client.model.Model} in 1.21.4) and do NOT touch
     * {@code allParts} (it is {@code private}).  The superclass constructor
     * takes a {@link net.minecraft.client.renderer.RenderType}; we pull it
     * from the wrapped {@link PlayerModel} so nothing is hard-coded.
     */
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