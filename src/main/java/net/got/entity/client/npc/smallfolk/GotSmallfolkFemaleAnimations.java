package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Slim-arm (Alex, 3 px) animation definitions for {@link GotSmallfolkFemaleModel}.
 *
 * <p>Most constants are identical to {@link GotSmallfolkAnimations} and are
 * delegated directly to that class. Only the handful of poses whose arm angles
 * differ for slim geometry are redefined here — the slim arm pivot is 0.5 px
 * narrower, which shifts the natural resting z-splay from 5.6494° to 5.73°.
 *
 * <p>Changes vs. the male class:
 * <ul>
 *   <li>{@link #BOB} — ±5.73° z-splay (vs. ±5.6494°)</li>
 *   <li>{@link #BOW_AND_ARROW} — symmetric −90° pitch on both arms</li>
 *   <li>{@link #BRANDISH_SPEAR} — rightArm −5.73° z-splay</li>
 *   <li>{@link #CELEBRATING} — 2.865° pitch (vs. 0.7047°)</li>
 * </ul>
 */
public final class GotSmallfolkFemaleAnimations {

    private GotSmallfolkFemaleAnimations() {}

    // ── Delegated (identical to male) ─────────────────────────────────────────

    public static final AnimationDefinition ATTACK_ROTATIONS    = GotSmallfolkAnimations.ATTACK_ROTATIONS;
    public static final AnimationDefinition BASE_POSE           = GotSmallfolkAnimations.BASE_POSE;
    public static final AnimationDefinition BIG_HEAD            = GotSmallfolkAnimations.BIG_HEAD;
    public static final AnimationDefinition CHARGING            = GotSmallfolkAnimations.CHARGING;
    public static final AnimationDefinition DAMAGE_NEARBY_MOBS  = GotSmallfolkAnimations.DAMAGE_NEARBY_MOBS;
    public static final AnimationDefinition HOLDING             = GotSmallfolkAnimations.HOLDING;
    public static final AnimationDefinition HOLDING_BRUSH       = GotSmallfolkAnimations.HOLDING_BRUSH;
    public static final AnimationDefinition HOLDING_SPYGLASS    = GotSmallfolkAnimations.HOLDING_SPYGLASS;
    public static final AnimationDefinition BRUSHING            = GotSmallfolkAnimations.BRUSHING;
    public static final AnimationDefinition LOOK_AT_TARGET          = GotSmallfolkAnimations.LOOK_AT_TARGET;
    public static final AnimationDefinition LOOK_AT_TARGET_GLIDING  = GotSmallfolkAnimations.LOOK_AT_TARGET_GLIDING;
    public static final AnimationDefinition LOOK_AT_TARGET_SWIMMING = GotSmallfolkAnimations.LOOK_AT_TARGET_SWIMMING;
    public static final AnimationDefinition MELEE_SPEAR_HOLD        = GotSmallfolkAnimations.MELEE_SPEAR_HOLD;
    public static final AnimationDefinition MELEE_SPEAR_USE         = GotSmallfolkAnimations.MELEE_SPEAR_USE;
    public static final AnimationDefinition MOVE                    = GotSmallfolkAnimations.MOVE;
    public static final AnimationDefinition RIDING_ARMS             = GotSmallfolkAnimations.RIDING_ARMS;
    public static final AnimationDefinition RIDING_BODY             = GotSmallfolkAnimations.RIDING_BODY;
    public static final AnimationDefinition RIDING_LEGS             = GotSmallfolkAnimations.RIDING_LEGS;
    public static final AnimationDefinition SNEAKING                = GotSmallfolkAnimations.SNEAKING;
    public static final AnimationDefinition SWIMMING                = GotSmallfolkAnimations.SWIMMING;
    public static final AnimationDefinition TOOTING_GOAT_HORN       = GotSmallfolkAnimations.TOOTING_GOAT_HORN;
    public static final AnimationDefinition USE_ITEM_PROGRESS       = GotSmallfolkAnimations.USE_ITEM_PROGRESS;
    public static final AnimationDefinition ZOMBIE_MELEE_SPEAR_HOLD = GotSmallfolkAnimations.ZOMBIE_MELEE_SPEAR_HOLD;
    public static final AnimationDefinition ZOMBIE_MELEE_SPEAR_USE  = GotSmallfolkAnimations.ZOMBIE_MELEE_SPEAR_USE;

    // ── Slim-arm overrides ────────────────────────────────────────────────────

    /**
     * Idle arm splay — slim-arm angle (±5.73°).
     * Male uses ±5.6494° — see {@link GotSmallfolkAnimations#BOB}.
     */
    public static final AnimationDefinition BOB =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.73F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F,  5.73F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /**
     * Bow/crossbow draw pose — symmetric −90° pitch for slim arms.
     * Male uses asymmetric −90.709°/−89.291° — see {@link GotSmallfolkAnimations#BOW_AND_ARROW}.
     */
    public static final AnimationDefinition BOW_AND_ARROW =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-90.0F, 28.65F, -5.73F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-90.0F, -5.73F,  5.73F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /**
     * Overhead spear brandish — slim-arm z-splay of 5.73°.
     * Male uses 5.6494° — see {@link GotSmallfolkAnimations#BRANDISH_SPEAR}.
     */
    public static final AnimationDefinition BRANDISH_SPEAR =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-143.5F, 0.0F, -5.73F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /**
     * Celebration pose — slim-arm pitch of 2.865°.
     * Male uses 0.7047° — see {@link GotSmallfolkAnimations#CELEBRATING}.
     */
    public static final AnimationDefinition CELEBRATING =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.865F, 180.0F, -135.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.865F, 180.0F,  153.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();
}
