package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;

/**
 * Per-frame snapshot of Smallfolk NPC state needed by the model and renderer.
 *
 * <p>Populated in {@link SmallfolkRenderer#extractRenderState}.
 * Inherits locomotion fields ({@code walkAnimationPos}, {@code walkAnimationSpeed},
 * {@code ageInTicks}, {@code yHeadRot}, {@code xRot}, {@code isInWater},
 * {@code isBaby}) from {@link LivingEntityRenderState}.
 */
public class SmallfolkRenderState extends LivingEntityRenderState {

    /** True when the entity is female (uses female model skeleton). */
    public boolean isFemale;

    /** Raw variant index from {@link net.got.entity.npc.smallfolk.SmallfolkEntity#getVariant()}. */
    public int variant;

    /** Number of male texture variants (used to split male/female index). */
    public int variantsPerGender;

    /** Resolved texture for this frame. */
    public ResourceLocation texture;

    /** True when entity is riding a mount. */
    public boolean isRiding;

    /** True when entity is sneaking. */
    public boolean isSneaking;

    /** True when entity is in a talking state. */
    public boolean isTalking;

    /** Head Y rotation in degrees (populated by SmallfolkRenderer). */
    public float yHeadRot;
    /** Head X rotation (pitch) in degrees (populated by SmallfolkRenderer). */
    public float xRot;

    // ── Humanoid animation fields (ported from HumanoidRenderState) ──────────

    /** Left arm pose (EMPTY, ITEM, BLOCK, BOW, CROSSBOW_CHARGE, etc.) */
    public HumanoidModel.ArmPose leftArmPose = HumanoidModel.ArmPose.EMPTY;
    /** Right arm pose. */
    public HumanoidModel.ArmPose rightArmPose = HumanoidModel.ArmPose.EMPTY;

    /** Swim amount (0.0 = not swimming, 1.0 = fully swimming). */
    public float swimAmount;

    /** True when entity is fall-flying (elytra). */
    public boolean isFallFlying;

    /** True when entity is actively using an item (eating, drinking, blocking, etc.) */
    public boolean isUsingItem;

    /** Which hand is using the item. */
    public InteractionHand useItemHand = InteractionHand.MAIN_HAND;

    /** The entity's main arm (RIGHT or LEFT). */
    public HumanoidArm mainArm = HumanoidArm.RIGHT;

    /** Which arm is attacking (for the attack swing animation). */
    public HumanoidArm attackArm = HumanoidArm.RIGHT;

    /** Attack progress (0.0 to 1.0). */
    public float attackTime;

    /** Scale factor for age (baby = smaller). */
    public float ageScale = 1.0F;

    /** Max duration for crossbow charge animation. */
    public int maxCrossbowChargeDuration;

    /** Ticks the entity has been using its current item. */
    public int ticksUsingItem;
}