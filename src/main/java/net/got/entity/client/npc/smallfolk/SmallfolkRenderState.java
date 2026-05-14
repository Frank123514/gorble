package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

/**
 * Per-frame snapshot of Smallfolk NPC state needed by the model and renderer.
 *
 * <p>Extends {@link HumanoidRenderState} to inherit all vanilla humanoid
 * animation fields used by our ported setupAnim logic:
 * {@code walkAnimationPos}, {@code walkAnimationSpeed}, {@code ageInTicks},
 * {@code leftArmPose}, {@code rightArmPose}, {@code attackTime},
 * {@code attackArm}, {@code swimAmount}, {@code isFallFlying},
 * {@code isUsingItem}, {@code useItemHand}, {@code isCrouching},
 * {@code isPassenger}, {@code speedValue}, {@code ageScale}, {@code mainArm},
 * {@code xRot}, {@code yRot}, {@code maxCrossbowChargeDuration},
 * {@code ticksUsingItem} — all populated automatically by
 * {@link SmallfolkRenderer} via {@code super.extractRenderState}.
 */
public class SmallfolkRenderState extends HumanoidRenderState {

    /** True when the entity is female (uses female model skeleton). */
    public boolean isFemale;

    /** Raw variant index from {@link net.got.entity.npc.smallfolk.SmallfolkEntity#getVariant()}. */
    public int variant;

    /** Number of male texture variants (used to split male/female index). */
    public int variantsPerGender;

    /** Resolved texture for this frame. */
    public ResourceLocation texture;

    /** True when entity is in a talking state. */
    public boolean isTalking;

    /**
     * Synced head-yaw oscillation from {@link net.got.entity.npc.GotNpcTalkAnimations}.
     * Sinusoidal side-to-side, range roughly ±0.3 rad. Zero when not talking.
     */
    public float talkHeadYaw;

    /**
     * Synced head-pitch oscillation from {@link net.got.entity.npc.GotNpcTalkAnimations}.
     * Sinusoidal nod, range roughly ±0.15 rad. Zero when not talking.
     */
    public float talkHeadPitch;

    /**
     * Synced right-arm gesture from {@link net.got.entity.npc.GotNpcTalkAnimations}.
     * Active every other 20 ticks (±0.5 rad xRot on right arm), zero otherwise.
     */
    public float talkGesture;
}