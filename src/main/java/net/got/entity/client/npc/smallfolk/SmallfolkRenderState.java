package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

/**
 * Per-frame snapshot of Smallfolk NPC state.
 *
 * <p>Extends {@link HumanoidRenderState} which provides all vanilla humanoid
 * animation fields for free: {@code walkAnimationPos}, {@code walkAnimationSpeed},
 * {@code leftArmPose}, {@code rightArmPose}, {@code attackTime}, {@code attackArm},
 * {@code swimAmount}, {@code isFallFlying}, {@code isUsingItem}, {@code useItemHand},
 * {@code isCrouching}, {@code isPassenger}, {@code mainArm}, {@code xRot},
 * {@code yRot}, {@code speedValue}, {@code ageScale}, {@code ticksUsingItem},
 * {@code maxCrossbowChargeDuration} — all populated automatically by
 * {@link SmallfolkRenderer} via {@code super.extractRenderState}.
 */
public class SmallfolkRenderState extends HumanoidRenderState {

    /** True when the entity is female (uses female model skeleton). */
    public boolean isFemale;

    /** Raw variant index from {@code SmallfolkEntity#getVariant()}. */
    public int variant;

    /** Number of male variants (used to split male/female texture index). */
    public int variantsPerGender;

    /** Resolved texture for this frame. */
    public ResourceLocation texture;

    /** True when entity is in a talking state. */
    public boolean isTalking;

    /** Sinusoidal head-yaw offset during talking (±0.3 rad). */
    public float talkHeadYaw;

    /** Sinusoidal head-pitch offset during talking (±0.15 rad). */
    public float talkHeadPitch;

    /** Right-arm gesture magnitude during talking (±0.5 rad xRot). */
    public float talkGesture;
}