package net.got.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animation definitions for player combat poses.
 *
 * <p>These drive the {@link GotPlayerAnimator} via Minecraft's built-in
 * {@link AnimationDefinition} / {@link KeyframeAnimations} system — the same
 * system used by the mod's bear, direwolf, and mammoth animations.
 *
 * <p>Bone names match the vanilla player model parts as exposed through
 * {@code HumanoidModel}: {@code right_arm}, {@code left_arm}, {@code body},
 * {@code head}.  They are referenced as strings here and resolved at runtime
 * by {@link GotPlayerAnimator}.
 *
 * <p>Animations:
 * <ul>
 *   <li>{@link #SWORD_ATTACK}    — diagonal horizontal slash, 0.45 s, one-shot</li>
 *   <li>{@link #SWORD_BLOCK}     — raised-guard idle, 0.25 s, one-shot / hold last</li>
 *   <li>{@link #GREATSWORD_ATTACK} — wide two-handed overhead, 0.6 s, one-shot</li>
 *   <li>{@link #AXE_ATTACK}      — downward chopping arc, 0.5 s, one-shot</li>
 *   <li>{@link #SPEAR_ATTACK}    — forward thrust lunge, 0.4 s, one-shot</li>
 * </ul>
 *
 * <p>All degrees follow right-hand rule: positive X = pitch forward,
 * positive Y = yaw left, positive Z = roll clockwise when viewed from behind.
 */
public final class GotPlayerCombatAnimations {

    private GotPlayerCombatAnimations() {}

    // ── SWORD ATTACK ─────────────────────────────────────────────────────────
    // Quick diagonal slash: arm raises, rotates inward, snaps forward, recovers.

    public static final AnimationDefinition SWORD_ATTACK =
            AnimationDefinition.Builder.withLength(0.45F)
                    // Right arm — the sword arm
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            // Start: arm slightly raised and pulled back
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(-30.0F,  20.0F,  10.0F), AnimationChannel.Interpolations.LINEAR),
                            // Wind-up: arm sweeps back and upward
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(-55.0F,  35.0F,  15.0F), AnimationChannel.Interpolations.LINEAR),
                            // Strike: arm drives down-forward in a diagonal arc
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec( 45.0F, -20.0F,  -5.0F), AnimationChannel.Interpolations.LINEAR),
                            // Follow-through: arm extends past target
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( 55.0F, -30.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
                            // Recovery: return to neutral
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( 1.0F,  1.5F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(-0.5F, -0.5F,  1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Left arm — slight counter-rotation for realism
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(-10.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec( 10.0F,  5.0F,  8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Body — slight torso rotation into the swing
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(0.0F,   5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -15.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── SWORD BLOCK ──────────────────────────────────────────────────────────
    // Guard raised diagonally across the body — held until the player releases.

    public static final AnimationDefinition SWORD_BLOCK =
            AnimationDefinition.Builder.withLength(0.25F)
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-80.0F, 20.0F,  20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-85.0F, 25.0F,  22.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(1.5F, 2.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── GREATSWORD ATTACK ────────────────────────────────────────────────────
    // Overhead two-handed swing: both arms raise together, crash down, recover.

    public static final AnimationDefinition GREATSWORD_ATTACK =
            AnimationDefinition.Builder.withLength(0.60F)
                    // Right arm leads
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-130.0F,  10.0F,   5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-145.0F,  15.0F,   8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(  60.0F, -10.0F,  -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec(  70.0F, -15.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(-1.0F,  3.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.posVec( 1.0F, -1.0F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Left arm mirrors — both grip the hilt
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-120.0F,  -8.0F,  -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-135.0F, -12.0F,  -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(  55.0F,   8.0F,   5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec(  65.0F,  12.0F,  10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec( 1.0F,  2.5F, -1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.posVec(-1.0F, -0.5F,  1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Body leans into the swing hard
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec( 0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-8.0F,  5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(12.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec( 0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── AXE ATTACK ───────────────────────────────────────────────────────────
    // Downward chopping arc — arm hoists high, crashes straight down, short rebound.

    public static final AnimationDefinition AXE_ATTACK =
            AnimationDefinition.Builder.withLength(0.50F)
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.12F, KeyframeAnimations.degreeVec(-140.0F,  5.0F,  5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.14F, KeyframeAnimations.degreeVec(-150.0F,  7.0F,  7.0F), AnimationChannel.Interpolations.LINEAR),
                            // Impact — snappy
                            new Keyframe(0.28F, KeyframeAnimations.degreeVec(  50.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            // Rebound
                            new Keyframe(0.36F, KeyframeAnimations.degreeVec(  30.0F, -3.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec(   0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.12F, KeyframeAnimations.posVec(0.5F,  3.5F, -1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.28F, KeyframeAnimations.posVec(0.0F, -1.0F,  1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Left arm braces slightly
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.12F, KeyframeAnimations.degreeVec(-20.0F, -8.0F, -6.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.28F, KeyframeAnimations.degreeVec( 15.0F,  5.0F,  5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,  8.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.12F, KeyframeAnimations.degreeVec( -5.0F, 12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.28F, KeyframeAnimations.degreeVec( 10.0F, -8.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec(  0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── SPEAR ATTACK ─────────────────────────────────────────────────────────
    // Forward lunge / thrust: arm draws back, body steps into it, drives forward, retracts.

    public static final AnimationDefinition SPEAR_ATTACK =
            AnimationDefinition.Builder.withLength(0.40F)
                    // Right arm — overhand grip thrust
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            // Draw back
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(-30.0F, 15.0F, 10.0F), AnimationChannel.Interpolations.LINEAR),
                            // Drive forward — arm straightens along the thrust line
                            new Keyframe(0.22F, KeyframeAnimations.degreeVec(-60.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            // Hold point briefly
                            new Keyframe(0.28F, KeyframeAnimations.degreeVec(-65.0F, -8.0F, -8.0F), AnimationChannel.Interpolations.LINEAR),
                            // Retract
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( 1.0F,  0.5F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.22F, KeyframeAnimations.posVec(-0.5F,  0.0F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Left arm braces near the shaft mid-point
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(-40.0F, -10.0F,  -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.22F, KeyframeAnimations.degreeVec(-55.0F,  -5.0F,  -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec(0.5F,  0.5F, -1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.22F, KeyframeAnimations.posVec(0.0F, -0.5F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Body leans forward into the thrust
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,  5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( -3.0F,  8.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.22F, KeyframeAnimations.degreeVec( 10.0F, -8.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(  0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}
