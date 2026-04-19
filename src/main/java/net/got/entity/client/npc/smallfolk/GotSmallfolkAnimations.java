package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Standard-arm (Steve, 4 px) animation definitions for {@link GotSmallfolkModel}.
 *
 * <p>Generated from Blockbench 5.1.3 / Minecraft 1.19+ Mojang-mappings exports,
 * then adapted to valid Java identifiers and integrated into the Smallfolk
 * animation pipeline.
 *
 * <p>Static pose constants (length = 0 s) are applied via
 * {@code EntityModel.animate(AnimationState, AnimationDefinition, float)}.
 * Multi-keyframe animations (e.g. {@link #SWIMMING}, {@link #BRUSHING}) loop
 * over their full duration.
 *
 * <p>Angles that differ from the female model are noted in the Javadoc.
 *
 * @see GotSmallfolkFemaleAnimations
 */
public final class GotSmallfolkAnimations {

    private GotSmallfolkAnimations() {}

    // ── Pose / combat animations ──────────────────────────────────────────────

    /** Base attack stance — small rightArm pitch (~-0.0175°). */
    public static final AnimationDefinition ATTACK_ROTATIONS =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.0175F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Neutral waist pose reset. */
    public static final AnimationDefinition BASE_POSE =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("waist", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Big-head scale gag (1.4× head scale). */
    public static final AnimationDefinition BIG_HEAD =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE,
                new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.4F, 1.4F, 1.4F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /**
     * Idle arm splay — standard-arm angle (±5.6494°).
     * Female uses ±5.73° — see {@link GotSmallfolkFemaleAnimations#BOB}.
     */
    public static final AnimationDefinition BOB =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -5.6494F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 5.6494F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /**
     * Bow/crossbow draw pose — slightly asymmetric for standard arms.
     * Female uses symmetric −90° pitch — see {@link GotSmallfolkFemaleAnimations#BOW_AND_ARROW}.
     */
    public static final AnimationDefinition BOW_AND_ARROW =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-90.709F, 28.65F, -5.6494F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-89.291F, -5.73F, 5.6494F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /**
     * Overhead spear brandish — standard-arm z-splay of 5.6494°.
     * Female uses 5.73° — see {@link GotSmallfolkFemaleAnimations#BRANDISH_SPEAR}.
     */
    public static final AnimationDefinition BRANDISH_SPEAR =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-143.5F, 0.0F, -5.6494F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Spyglass hold — rightArm raised. */
    public static final AnimationDefinition HOLDING_SPYGLASS =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-105.0F, -15.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Goat horn toot — rightArm raised and item rotated. */
    public static final AnimationDefinition TOOTING_GOAT_HORN =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-75.0F, -30.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 100.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F, KeyframeAnimations.posVec(4.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Brush held ready — item rotated and scaled. */
    public static final AnimationDefinition HOLDING_BRUSH =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, -30.0F, -10.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F, KeyframeAnimations.posVec(-7.0F, -1.0F, -1.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.SCALE,
                new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.5F, 1.5F, 1.5F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Archaeology brush stroke — 0.5 s looping item sweep. */
    public static final AnimationDefinition BRUSHING =
        AnimationDefinition.Builder.withLength(0.5F).looping()
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-50.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.099F, KeyframeAnimations.degreeVec(0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.1F,   KeyframeAnimations.degreeVec(0.0F, -30.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.199F, KeyframeAnimations.degreeVec(0.0F, -30.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.2F,   KeyframeAnimations.degreeVec(0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.299F, KeyframeAnimations.degreeVec(0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.3F,   KeyframeAnimations.degreeVec(0.0F,  30.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.399F, KeyframeAnimations.degreeVec(0.0F,  30.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4F,   KeyframeAnimations.degreeVec(0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F),  AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.099F, KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F),  AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.1F,   KeyframeAnimations.posVec(1.5F, 0.0F, -3.0F),  AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.199F, KeyframeAnimations.posVec(1.5F, 0.0F, -3.0F),  AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.2F,   KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F),  AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.299F, KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F),  AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.3F,   KeyframeAnimations.posVec(1.5F, 0.0F,  3.0F),  AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.399F, KeyframeAnimations.posVec(1.5F, 0.0F,  3.0F),  AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4F,   KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F),  AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5F,   KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F),  AnimationChannel.Interpolations.LINEAR)))
            .build();

    /**
     * Celebration pose — arms raised wide (0.7047° pitch for standard arms).
     * Female uses 2.865° — see {@link GotSmallfolkFemaleAnimations#CELEBRATING}.
     */
    public static final AnimationDefinition CELEBRATING =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.7047F, 180.0F, -135.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.7047F, 180.0F,  153.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Charging pose — rightArm reset to neutral. */
    public static final AnimationDefinition CHARGING =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** AOE attack pose — arms and legs extended outward. */
    public static final AnimationDefinition DAMAGE_NEARBY_MOBS =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec( 45.0F, -0.1F, -0.1F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec( 45.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F,  0.1F,  0.1F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Both arms held neutral (generic hold pose). */
    public static final AnimationDefinition HOLDING =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Default head look-at-target reset. */
    public static final AnimationDefinition LOOK_AT_TARGET =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Head look-at-target while gliding (−45° pitch). */
    public static final AnimationDefinition LOOK_AT_TARGET_GLIDING =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Head look-at-target while swimming (reset). */
    public static final AnimationDefinition LOOK_AT_TARGET_SWIMMING =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Walk / move base reset — used as a starting point before procedural walk math. */
    public static final AnimationDefinition MOVE =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -0.1F, -0.1F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F,  0.1F,  0.1F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    // ── Riding poses ──────────────────────────────────────────────────────────

    /** Riding body position reset. */
    public static final AnimationDefinition RIDING_BODY =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Riding arm pose — both arms pitched −36° forward (rein grip). */
    public static final AnimationDefinition RIDING_ARMS =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-36.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-36.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Riding leg pose — legs splayed forward (−81° pitch, ±18° yaw) for saddle straddle. */
    public static final AnimationDefinition RIDING_LEGS =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-81.0F, -18.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(-81.0F,  18.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    // ── Movement animations ───────────────────────────────────────────────────

    /**
     * Sneaking / crouching pose — body pitches forward, arms hang low,
     * legs shift up and forward.
     */
    public static final AnimationDefinition SNEAKING =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(72.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -3.1F, 3.9F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(72.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.9F, 4.1F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** 1.3-second looping freestyle swim — arm strokes cycle in and out. */
    public static final AnimationDefinition SWIMMING =
        AnimationDefinition.Builder.withLength(1.3F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.700F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.701F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.100F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.299F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.300F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -0.1F, -0.1F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.700F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.701F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.100F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.299F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.300F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.1F, 0.1F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Item-use progress pose — rightArm reset (overridden by item use lerp). */
    public static final AnimationDefinition USE_ITEM_PROGRESS =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    // ── Spear poses ───────────────────────────────────────────────────────────

    /** Spear held at rest — item shifted down and back along the shaft. */
    public static final AnimationDefinition MELEE_SPEAR_HOLD =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.5F, -1.5F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Zombie-style two-handed spear hold. */
    public static final AnimationDefinition ZOMBIE_MELEE_SPEAR_HOLD =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.5F, -1.5F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Spear thrust — item and arm at neutral (thrust applied procedurally). */
    public static final AnimationDefinition MELEE_SPEAR_USE =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.5F, -1.5F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    /** Zombie-style two-handed spear thrust. */
    public static final AnimationDefinition ZOMBIE_MELEE_SPEAR_USE =
        AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rightItem", new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.5F, -1.5F), AnimationChannel.Interpolations.LINEAR)))
            .build();
}
