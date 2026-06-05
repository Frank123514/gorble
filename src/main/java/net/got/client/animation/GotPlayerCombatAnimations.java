package net.got.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animation definitions for player combat poses.
 *
 * <p>Heavily inspired by CPA (Custom Player Animations) — richer keyframe
 * density, full torso/body lean, head displacement, and position offsets on
 * every bone for that "weight" feel.
 *
 * <p>Bone names match vanilla HumanoidModel: {@code right_arm}, {@code left_arm},
 * {@code body}, {@code head}.
 *
 * <p>Animations:
 * <ul>
 *   <li>{@link #SWORD_ATTACK}      — horizontal slash combo, 0.9 s, one-shot</li>
 *   <li>{@link #SWORD_BLOCK}       — snap-up guard with body turn, 0.35 s, hold last</li>
 *   <li>{@link #GREATSWORD_ATTACK} — full two-handed overhead crash, 0.75 s, one-shot</li>
 *   <li>{@link #AXE_ATTACK}        — heavy looping chop arc, 0.65 s, one-shot</li>
 *   <li>{@link #SPEAR_ATTACK}      — step-in thrust lunge, 0.55 s, one-shot</li>
 * </ul>
 *
 * <p>Convention: positive X = pitch forward, positive Y = yaw left,
 * positive Z = roll clockwise (viewed from behind). Degrees only.
 */
public final class GotPlayerCombatAnimations {

    private GotPlayerCombatAnimations() {}

    // ─────────────────────────────────────────────────────────────────────────
    // SWORD ATTACK  –  diagonal slash, body rotates into swing, arm loops wide
    // Length 0.9 s  (matches CPA sword_attack_1 + sword_attack_2 feel)
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SWORD_ATTACK =
            AnimationDefinition.Builder.withLength(0.9F)

                    // ── Right arm (sword arm) ──────────────────────────────────────
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            // Neutral
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR),
                            // Wind-up: raises arm back and out
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(  19.0F,   23.0F,   -8.0F), AnimationChannel.Interpolations.LINEAR),
                            // Peak wind-up: arm coiled behind
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( -45.0F,   83.0F,   27.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec( -85.0F,  100.0F,   30.0F), AnimationChannel.Interpolations.LINEAR),
                            // Strike: arm drives hard forward-down
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-160.0F,   69.0F,  -31.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-199.0F,   26.0F,  -92.0F), AnimationChannel.Interpolations.LINEAR),
                            // Follow-through: arm sweeps past
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(-113.0F,   10.0F,  -76.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(  -6.0F,   -1.0F,  -55.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec(  19.0F,   -9.0F,  -70.0F), AnimationChannel.Interpolations.LINEAR),
                            // Recovery: arm drifts back to rest
                            new Keyframe(0.70F, KeyframeAnimations.degreeVec(  42.0F,  -58.0F, -127.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.degreeVec(   0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( 0.1F, -1.9F,  1.4F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec( 0.0F,  2.0F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            // Arm shoots forward during strike
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 3.0F, -4.0F, -9.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.posVec( 2.0F, -3.3F, -7.3F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Left arm — counter-swing for balance ───────────────────────
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(  8.0F,  -9.0F,  -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( -7.0F,   1.0F, -16.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-48.0F,  32.0F, -38.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(-54.0F,  11.0F, -60.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec( -0.5F, -15.0F, -77.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.degreeVec( 19.0F,   1.0F, -59.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( -1.0F, -1.2F, -1.2F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec(  0.3F, -1.4F, -0.6F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.posVec(  1.3F, -2.4F, -3.8F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.posVec(  0.4F, -0.8F,  0.2F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Body — full torso rotation into the slash ──────────────────
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( 13.0F,  19.0F,  -4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( -7.0F,  23.0F,  -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-11.0F,  20.0F,  -4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec( 29.0F, -12.0F, -13.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( 38.0F, -20.0F, -15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec( 33.0F, -25.0F, -14.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( -1.0F, -0.1F, -2.3F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(  0.0F, -0.3F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(  0.0F, -3.0F, -7.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec( -0.3F, -2.4F, -6.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Head — bobs/shifts with the body to feel alive ─────────────
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( -1.0F, -1.9F, -2.6F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(  0.0F,  0.0F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(  0.0F, -4.0F, -7.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.posVec(  0.0F, -1.9F, -4.4F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // SWORD BLOCK  –  snap arm up into a high guard, body turns to face threat
    // Length 0.35 s, hold last frame
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SWORD_BLOCK =
            AnimationDefinition.Builder.withLength(0.35F)

                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            // Snap up fast
                            new Keyframe(0.12F, KeyframeAnimations.degreeVec( -90.0F,  15.0F,  25.0F), AnimationChannel.Interpolations.LINEAR),
                            // Settle into guard — slight flare outward
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec( -82.0F,  22.0F,  20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( -80.0F,  25.0F,  18.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.12F, KeyframeAnimations.posVec( 1.0F,  2.5F,  2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 1.5F,  2.0F,  2.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Left arm lifts slightly — bracing instinct
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-20.0F,  -5.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-15.0F,  -3.0F,  -8.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Body turns toward the threat (right shoulder forward)
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec( 0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( 5.0F,  14.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( 7.5F,  25.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0.0F,  0.0F, -1.1F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Head stays forward — player is looking past the guard
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0.0F, -0.7F, -1.1F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // GREATSWORD ATTACK  –  massive two-handed overhead crash + recovery
    // Length 0.75 s
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition GREATSWORD_ATTACK =
            AnimationDefinition.Builder.withLength(0.75F)

                    // ── Right arm leads overhead ───────────────────────────────────
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(    0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR),
                            // Hoist: arm swings up and behind
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec( -120.0F,   12.0F,    6.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( -145.0F,   18.0F,    9.0F), AnimationChannel.Interpolations.LINEAR),
                            // Brief hold at apex
                            new Keyframe(0.23F, KeyframeAnimations.degreeVec( -150.0F,   20.0F,   10.0F), AnimationChannel.Interpolations.LINEAR),
                            // Crash down hard
                            new Keyframe(0.38F, KeyframeAnimations.degreeVec(   55.0F,   -8.0F,   -6.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.46F, KeyframeAnimations.degreeVec(   75.0F,  -15.0F,  -12.0F), AnimationChannel.Interpolations.LINEAR),
                            // Rebound
                            new Keyframe(0.54F, KeyframeAnimations.degreeVec(   55.0F,  -10.0F,   -8.0F), AnimationChannel.Interpolations.LINEAR),
                            // Recover to neutral
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(    0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec( -1.0F,  3.5F, -2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.23F, KeyframeAnimations.posVec( -1.5F,  4.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F, KeyframeAnimations.posVec(  1.0F, -1.5F,  2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.46F, KeyframeAnimations.posVec(  1.5F, -2.0F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Left arm mirrors — both hands on the hilt ──────────────────
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(    0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec( -110.0F,  -10.0F,   -6.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( -132.0F,  -14.0F,   -9.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.23F, KeyframeAnimations.degreeVec( -138.0F,  -16.0F,  -11.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F, KeyframeAnimations.degreeVec(   50.0F,    7.0F,    5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.46F, KeyframeAnimations.degreeVec(   68.0F,   12.0F,   11.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.54F, KeyframeAnimations.degreeVec(   50.0F,    7.0F,    7.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(    0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(  1.0F,  3.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.23F, KeyframeAnimations.posVec(  1.5F,  3.5F, -2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F, KeyframeAnimations.posVec( -1.0F, -1.0F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.46F, KeyframeAnimations.posVec( -1.5F, -1.5F,  2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Body leans WAY in — this is a big swing ────────────────────
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-10.0F,  6.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.23F, KeyframeAnimations.degreeVec(-14.0F,  8.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F, KeyframeAnimations.degreeVec( 16.0F, -7.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.46F, KeyframeAnimations.degreeVec( 22.0F,-10.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.54F, KeyframeAnimations.degreeVec( 16.0F, -5.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(-1.0F,  0.5F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F, KeyframeAnimations.posVec( 0.5F, -2.5F,  1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.46F, KeyframeAnimations.posVec( 0.5F, -3.5F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Head — dips with the hoist, snaps forward at impact ────────
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec(-0.5F,  1.5F, -2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F, KeyframeAnimations.posVec( 0.0F, -2.0F,  1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.46F, KeyframeAnimations.posVec( 0.0F, -3.0F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // AXE ATTACK  –  heavy looping overhead chop, body rotates fully through
    // Length 0.65 s  (matches CPA axe_animation)
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition AXE_ATTACK =
            AnimationDefinition.Builder.withLength(0.65F)

                    // ── Right arm — full arc chop ──────────────────────────────────
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            // Start in raised ready position (like CPA idle-into-axe)
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec( -58.7F,  13.9F, -56.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.05F, KeyframeAnimations.degreeVec( -54.2F,  11.6F, -53.8F), AnimationChannel.Interpolations.LINEAR),
                            // Rising sweep
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( -57.8F,  14.6F, -56.7F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec( -84.7F,  37.3F, -79.3F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-123.2F,  56.0F,-102.1F), AnimationChannel.Interpolations.LINEAR),
                            // Apex — arm overhead
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-175.7F,  42.4F,-110.7F), AnimationChannel.Interpolations.LINEAR),
                            // Crash down
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-215.2F,  24.5F,-103.6F), AnimationChannel.Interpolations.LINEAR),
                            // Impact snap — arm drives past
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-208.8F,  26.7F, -61.1F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(-176.3F,  30.8F, -22.6F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(-115.1F,  22.7F, -34.5F), AnimationChannel.Interpolations.LINEAR),
                            // Hold end position
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec( -58.7F,  13.9F, -56.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.degreeVec( -58.7F,  13.9F, -56.5F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F, -2.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec( 0.0F, -1.5F, -0.7F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec( 0.0F, -1.1F,  5.2F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec( 0.0F, -1.0F,  5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 0.0F, -1.8F, -1.9F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.posVec( 0.0F, -2.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec( 0.0F, -2.0F, -5.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Left arm — balances the chop, braces at bottom ────────────
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec( 53.2F,  -7.6F, -25.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( 30.9F,   1.1F, -11.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( -4.5F,   5.2F,  -7.6F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-41.5F,   3.0F, -15.7F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(-14.4F,  -8.2F, -31.1F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec( 71.2F, -15.6F, -27.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.degreeVec( 53.2F,  -7.6F, -25.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-1.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec( 0.4F, -0.6F, -0.3F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec(-1.0F, -1.0F,  1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(-1.0F, -0.2F, -0.6F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec(-1.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Body — rotates fully with the chop arc ─────────────────────
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec( 19.2F, -23.8F, -15.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( 17.2F,  -5.1F,  -4.8F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(  2.9F,  12.5F,  -2.7F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.5F,  19.5F,  -6.4F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-15.9F,  19.3F, -10.4F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( -9.2F,   9.9F, -13.6F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(  4.0F,  -5.6F, -17.3F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec( 26.6F, -31.4F, -22.3F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec( 27.7F, -33.9F, -21.7F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.degreeVec( 19.2F, -23.8F, -15.5F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-1.0F, -1.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec( 0.0F, -0.9F, -1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec(-1.0F, -1.0F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.posVec(-1.0F, -2.0F, -4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec(-1.0F, -1.0F, -3.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Head — tracks with the body arc ───────────────────────────
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-1.0F, -1.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec( 0.0F, -1.3F, -1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec(-1.0F, -2.0F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.posVec(-1.0F, -3.0F, -4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec(-1.0F, -1.0F, -3.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // SPEAR ATTACK  –  step-in thrust: draw back, drive the tip, retract
    // Length 0.55 s
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SPEAR_ATTACK =
            AnimationDefinition.Builder.withLength(0.55F)

                    // ── Right arm — overhand grip drives the thrust ────────────────
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            // Draw: arm pulls back/up to cock the throw
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( -30.0F,  15.0F,  10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec( -38.0F,  20.0F,  12.0F), AnimationChannel.Interpolations.LINEAR),
                            // Drive: arm punches straight down the thrust axis
                            new Keyframe(0.28F, KeyframeAnimations.degreeVec( -65.0F,  -6.0F,  -6.0F), AnimationChannel.Interpolations.LINEAR),
                            // Hold — tip planted
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( -70.0F, -10.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
                            // Retract
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( -45.0F,  -5.0F,  -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            // Pull back
                            new Keyframe(0.12F, KeyframeAnimations.posVec(  1.0F,  0.5F, -2.5F), AnimationChannel.Interpolations.LINEAR),
                            // Shoot forward hard
                            new Keyframe(0.28F, KeyframeAnimations.posVec( -0.5F, -0.5F,  3.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( -0.5F, -0.5F,  4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Left arm — braces spear shaft mid-point ────────────────────
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( -40.0F, -10.0F,  -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.28F, KeyframeAnimations.degreeVec( -58.0F,  -5.0F,  -6.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( -62.0F,  -6.0F,  -7.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( 0.5F,  0.5F, -1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.28F, KeyframeAnimations.posVec( 0.0F, -0.5F,  2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0.0F, -0.5F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Body — steps into the lunge, whole torso commits ──────────
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,  5.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            // Draw-back: body rotates away (coiling)
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( -3.0F,  9.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec( -4.0F, 12.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            // Thrust: body commits forward and rotates through
                            new Keyframe(0.28F, KeyframeAnimations.degreeVec( 12.0F, -9.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( 15.0F,-12.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            // Recover
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(  0.0F,  5.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            // Body rocks back slightly on draw
                            new Keyframe(0.12F, KeyframeAnimations.posVec( 0.0F,  0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            // Steps forward into thrust
                            new Keyframe(0.28F, KeyframeAnimations.posVec( 0.0F, -1.0F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0.0F, -1.5F,  2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // ── Head — slight bob tracking the thrust ──────────────────────
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.12F, KeyframeAnimations.posVec(-0.5F,  0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.28F, KeyframeAnimations.posVec( 0.0F, -1.5F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0.0F, -2.0F,  2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();
}