package net.got.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Combat animations for the GOT player.
 * Written from scratch — KCD-inspired. Heavy, committed, medieval.
 */
public final class GotPlayerCombatAnimations {

    private GotPlayerCombatAnimations() {}

    // ─────────────────────────────────────────────────────────────────────
    // SWORD ATTACK 1 — Rising diagonal cut, right to left.
    // Windup pulls right shoulder back, full rotation through the body,
    // sword finishes high left. Stance drops slightly on impact frame.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SWORD_ATTACK =
            AnimationDefinition.Builder.withLength(0.8F)
                    // Body rotates into and through the swing
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  5.0F,  25.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(  8.0F,  30.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.degreeVec( 22.0F, -28.0F,-10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec( 28.0F, -35.0F,-12.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,   KeyframeAnimations.degreeVec( 10.0F, -18.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(-1.0F,  0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.posVec( 0.0F, -2.5F, -6.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.posVec( 0.0F, -3.0F, -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,   KeyframeAnimations.posVec(-0.5F, -1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Head follows the swing slightly, then resets
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(-1.0F, -1.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.posVec( 0.0F, -2.0F, -4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,   KeyframeAnimations.posVec( 0.0F,  0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Sword arm — coil back, explode forward and through
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  15.0F,  25.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec( -60.0F,  95.0F,  25.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(-155.0F,  85.0F,  32.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.42F,  KeyframeAnimations.degreeVec(-185.0F,  20.0F, -85.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,  KeyframeAnimations.degreeVec( -80.0F,   5.0F, -70.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,   KeyframeAnimations.degreeVec(  -8.0F, -12.0F, -52.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec( 0.0F, -2.0F,  1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.posVec( 0.0F,  2.0F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.42F,  KeyframeAnimations.posVec( 1.5F,  0.0F,  0.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,  KeyframeAnimations.posVec( 2.0F, -1.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,   KeyframeAnimations.posVec( 1.0F, -1.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Off-hand — braces, extends slightly for balance
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  8.0F, -10.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(-45.0F,  30.0F,-38.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(-50.0F,  10.0F,-58.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,   KeyframeAnimations.degreeVec( 15.0F, -18.0F,-26.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.posVec( 0.3F, -1.5F, -0.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,   KeyframeAnimations.posVec(-1.0F,  1.5F,  1.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // SWORD ATTACK 2 — Chained horizontal slash, left to right return.
    // Flows from where SWORD_ATTACK ended. Body crosses the other way,
    // sword recovers in a tight arc and cuts back across.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SWORD_ATTACK_2 =
            AnimationDefinition.Builder.withLength(0.75F)
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(10.0F, -18.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec( 5.0F, -22.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.degreeVec(25.0F,  30.0F,-14.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(18.0F,  22.0F, -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.degreeVec( 5.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(-0.5F, -1.0F, -1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.posVec( 0.2F, -1.0F,  3.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.posVec(-0.1F, -1.0F,  2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec( 0.0F,  0.0F, -1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.posVec(-0.8F, -0.5F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Sword arm chases back the other way — tight recovery
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( -8.0F, -12.0F, -52.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,   KeyframeAnimations.degreeVec( 38.0F,-110.0F,-108.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(125.0F, -58.0F,-200.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,   KeyframeAnimations.degreeVec(210.0F, -30.0F,-235.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(370.0F,  15.0F,-305.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.degreeVec(360.0F,   0.0F,-360.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec( 1.0F, -1.5F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,   KeyframeAnimations.posVec( 0.0F, -1.2F, -2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.posVec( 2.3F, -2.0F, -1.8F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.posVec(-1.0F,  0.0F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 15.0F, -18.0F, -26.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,  KeyframeAnimations.degreeVec( 60.0F,  12.0F, -42.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,   KeyframeAnimations.degreeVec(-28.0F,  28.0F, -20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(-42.0F,  12.0F,  -6.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(-1.0F,  1.5F,  1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,  KeyframeAnimations.posVec(-1.3F,  0.0F,  3.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.posVec(-1.0F, -0.8F,  0.8F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // SWORD BLOCK
    // Half-sword guard. Blade angled, both hands on it or off-hand
    // raised. Weight forward on front foot. Slow breathing sway.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SWORD_BLOCK =
            AnimationDefinition.Builder.withLength(4.0F).looping()
                    // Torso turned slightly to present shoulder — classic guard
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 8.0F, 22.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(11.0F, 22.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec( 8.0F, 22.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.5F,  KeyframeAnimations.degreeVec( 5.0F, 22.0F,-2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec( 8.0F, 22.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.5F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Sword arm — extended, blade up and forward
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( -5.0F, -3.0F, 28.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec( 12.0F, -3.0F, 12.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(  6.0F, -3.0F, 18.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec( -5.0F, -3.0F, 28.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, -1.0F,  3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5F,  KeyframeAnimations.posVec(0.0F,  0.0F,  3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.5F,  KeyframeAnimations.posVec(0.0F,  0.5F,  2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.posVec(0.0F, -1.0F,  3.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Off-hand raised — supporting the blade or deflecting
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-148.0F, 84.0F, -62.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec( -65.0F, 85.0F,  20.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  12.0F, 82.0F,  86.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(  18.0F, 82.0F,  84.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(-148.0F, 84.0F, -62.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.5F, -3.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0F,  KeyframeAnimations.posVec(0.5F, -4.5F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.posVec(0.5F, -3.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // GREATSWORD ATTACK
    // Two-handed overhead — slow, devastating. Raise high above head,
    // pause at the apex, then crash down with full body weight.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition GREATSWORD_ATTACK =
            AnimationDefinition.Builder.withLength(1.4F)
                    // Body coils up, drives down through the strike
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(-18.0F,  8.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,  KeyframeAnimations.degreeVec(-26.0F, 12.0F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.degreeVec( 28.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,   KeyframeAnimations.degreeVec( 35.0F,-10.0F, -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.posVec(0.0F, -0.5F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,  KeyframeAnimations.posVec(1.0F, -0.8F,  4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.posVec(0.0F, -1.5F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,   KeyframeAnimations.posVec(0.0F, -2.5F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,   KeyframeAnimations.posVec(0.0F,  1.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.posVec(0.0F,  0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Both arms raise together — greatsword overhead
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec( -50.0F,  10.0F,  10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,  KeyframeAnimations.degreeVec(-145.0F,  18.0F,  22.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.degreeVec( -55.0F,  -5.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,   KeyframeAnimations.degreeVec(  -8.0F,  -9.0F, -14.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.posVec( 0.0F,  3.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.posVec( 0.0F,  2.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,   KeyframeAnimations.posVec( 2.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec( -30.0F, -10.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,  KeyframeAnimations.degreeVec(-135.0F, -15.0F, -22.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.degreeVec( -45.0F,   5.0F,  15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,   KeyframeAnimations.degreeVec( -16.0F,   7.0F,  20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.posVec( -0.8F,  3.0F,  1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.posVec(  0.0F,  2.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,   KeyframeAnimations.posVec( -3.0F, -1.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // AXE ATTACK
    // Single-handed axe — different from sword. Shorter swing arc,
    // more shoulder-driven, brutal chop with a slight step-through.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition AXE_ATTACK =
            AnimationDefinition.Builder.withLength(1.2F)
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  0.0F,  12.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(-12.0F,  18.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,  KeyframeAnimations.degreeVec( 30.0F,  -8.0F, -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,   KeyframeAnimations.degreeVec( 32.0F, -15.0F,-12.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,   KeyframeAnimations.degreeVec(  0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,  KeyframeAnimations.posVec(0.0F,  0.5F,  1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.posVec(0.0F, -2.5F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,   KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,   KeyframeAnimations.posVec(0.0F, 0.5F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,   KeyframeAnimations.posVec(0.0F,-1.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,   KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Axe arm — high raise, shoulder-driven chop
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,  KeyframeAnimations.degreeVec( -32.0F,  20.0F,  15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(-125.0F,  35.0F,  26.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F,  KeyframeAnimations.degreeVec( -28.0F,  10.0F,  -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.85F,  KeyframeAnimations.degreeVec(   8.0F, -15.0F, -40.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.1F,   KeyframeAnimations.degreeVec(   0.0F,  -8.0F, -24.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,   KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.posVec( 0.5F,  3.5F, -0.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F,  KeyframeAnimations.posVec( 1.5F, -0.2F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.85F,  KeyframeAnimations.posVec( 2.8F, -1.5F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,   KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Off-hand — extended for counterbalance
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(-20.0F,  10.0F, -15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,   KeyframeAnimations.degreeVec(-48.0F,  20.0F, -40.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,   KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // SPEAR ATTACK
    // Straight thrust — totally different feel from the slashes.
    // Body coils back, shoots forward. One hand drives, one guides.
    // Fast extension, sharp snap-back.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SPEAR_ATTACK =
            AnimationDefinition.Builder.withLength(0.65F)
                    // Step-through thrust — body pitches forward on release
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  0.0F,  5.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.18F,  KeyframeAnimations.degreeVec( -8.0F,  5.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F,  KeyframeAnimations.degreeVec( 18.0F, -5.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.52F,  KeyframeAnimations.degreeVec( 22.0F, -5.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F,  KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F,  KeyframeAnimations.posVec(0.0F,  0.5F,  1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.52F,  KeyframeAnimations.posVec(0.0F, -1.5F, -4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F,  KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F,  KeyframeAnimations.posVec(0.0F, 0.5F,  1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.52F,  KeyframeAnimations.posVec(0.0F,-2.0F, -4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Rear hand drives the thrust — punches forward hard
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(   0.0F,  -8.0F,  -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.18F,  KeyframeAnimations.degreeVec( -22.0F,  -8.0F,  -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F,  KeyframeAnimations.degreeVec( -82.0F, -15.0F, -10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(-105.0F, -20.0F, -12.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F,  KeyframeAnimations.degreeVec(   0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.38F,  KeyframeAnimations.posVec(0.0F,  1.0F,  2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.posVec(1.5F,  0.5F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F,  KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Guide hand — slides along shaft, braces
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.28F,  KeyframeAnimations.degreeVec(-32.0F,  10.0F, -20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(-65.0F,  25.0F, -36.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F,  KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();
}
