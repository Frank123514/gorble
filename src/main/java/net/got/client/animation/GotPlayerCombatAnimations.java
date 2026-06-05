package net.got.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animation definitions for player combat poses.
 *
 * <p>All combat animations are ported directly from CPA (Custom Player Animations)
 * JSON files:  {@code sword_attack_1_animation.json}, {@code sword_attack_2_animation.json},
 * and {@code shield_animation.json}.  Bone name mapping: CPA "torso" → "body",
 * "rightArm" → "right_arm", "leftArm" → "left_arm".
 *
 * <p>Animations:
 * <ul>
 *   <li>{@link #SWORD_ATTACK}   — diagonal slash, 0.9 s, one-shot (CPA sword_attack_1)</li>
 *   <li>{@link #SWORD_ATTACK_2} — follow-through return slash, 1.05 s, one-shot (CPA sword_attack_2)</li>
 *   <li>{@link #SWORD_BLOCK}    — left-arm shield guard, 5 s looping (CPA shield_animation)</li>
 *   <li>{@link #GREATSWORD_ATTACK} — heavy two-handed overhead, 0.75 s, one-shot</li>
 *   <li>{@link #AXE_ATTACK}     — heavy chop arc, 0.65 s, one-shot</li>
 *   <li>{@link #SPEAR_ATTACK}   — step-in thrust lunge, 0.55 s, one-shot</li>
 * </ul>
 */
public final class GotPlayerCombatAnimations {

    private GotPlayerCombatAnimations() {}

    // ─────────────────────────────────────────────────────────────────────────
    // SWORD ATTACK 1  (CPA sword_attack_1_animation.json)
    // Length 0.9 s, one-shot
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SWORD_ATTACK =
            AnimationDefinition.Builder.withLength(0.9F)

                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.00F,    0.00F,    0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(  19.11F,   22.68F,   -8.09F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( -45.00F,   83.42F,   27.45F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec( -85.00F,  100.00F,   30.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-160.54F,   69.49F,  -30.88F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-198.96F,   25.95F,  -91.93F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(-112.95F,   10.32F,  -76.26F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(  -6.12F,   -1.00F,  -55.20F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec(  19.36F,   -8.61F,  -70.48F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec(  44.05F,  -26.19F, -122.70F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.degreeVec(  41.89F,  -58.23F, -127.09F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.80F, KeyframeAnimations.degreeVec(  16.20F, -104.52F,  -88.62F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.degreeVec(  -6.12F, -140.00F,  -55.20F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( 0.07F, -1.91F,  1.36F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec( 0.00F,  2.00F,  3.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 1.62F, -0.85F, -1.13F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 3.00F, -4.00F, -9.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.posVec( 1.95F, -3.30F, -7.34F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec( 1.02F, -2.47F, -5.32F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.00F,   0.00F,   0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(  7.80F,  -9.06F,  -1.98F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( -7.40F,   0.69F, -16.35F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-48.08F,  32.43F, -37.75F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(-54.06F,  10.73F, -60.39F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec( -0.49F, -14.63F, -77.02F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec( 12.12F,  -7.90F, -70.10F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.degreeVec( 19.42F,   0.93F, -58.94F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.degreeVec( 14.10F, -18.10F, -26.66F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(  0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( -1.00F, -1.20F, -1.18F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec(  0.33F, -1.35F, -0.64F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.posVec(  1.27F, -2.44F, -3.83F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.posVec(  0.44F, -0.77F,  0.15F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec( -1.06F,  1.52F,  1.25F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.00F,   0.00F,   0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( 13.18F,  18.80F,  -4.03F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( -6.66F,  23.08F,  -3.05F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.63F,  19.68F,  -3.62F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec( 29.21F, -11.68F, -13.17F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( 37.69F, -20.25F, -14.97F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec( 33.29F, -25.07F, -13.66F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.degreeVec( 10.76F, -26.53F,  -7.71F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(  0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( -0.99F, -0.11F, -2.25F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(  0.00F, -0.25F,  2.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(  0.00F, -3.00F, -7.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec( -0.26F, -2.35F, -6.02F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec( -0.89F, -0.12F, -1.45F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(  0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( -1.00F, -1.20F, -1.18F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(  0.00F, -0.25F,  2.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(  0.00F, -3.00F, -7.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.posVec(  0.00F, -1.50F, -4.33F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec(  0.00F,  0.00F, -1.50F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // SWORD ATTACK 2  (CPA sword_attack_2_animation.json)
    // Chains after SWORD_ATTACK — starts from the end pose of attack 1.
    // Length 1.05 s, one-shot
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SWORD_ATTACK_2 =
            AnimationDefinition.Builder.withLength(1.05F)

                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  -6.12F, -140.00F,  -55.20F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(  35.55F, -110.78F, -109.45F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec( 126.40F,  -58.51F, -204.55F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec( 214.43F,  -39.66F, -232.89F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( 380.90F,  -12.48F, -257.15F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec( 386.12F,   -1.99F, -267.66F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.degreeVec( 375.57F,   30.00F, -310.18F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.80F, KeyframeAnimations.degreeVec( 367.45F,   17.66F, -338.40F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.95F, KeyframeAnimations.degreeVec( 360.00F,    0.00F, -360.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec( 360.00F,    0.00F, -360.00F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 1.02F, -2.47F, -5.32F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( 0.03F, -2.26F, -4.53F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec( 2.35F, -2.18F, -2.86F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(-1.00F,  0.00F,  7.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec(-0.91F, -4.80F,  4.26F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.80F, KeyframeAnimations.posVec(-0.45F, -2.70F, -1.04F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.95F, KeyframeAnimations.posVec( 0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec( 0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  14.10F, -18.10F, -26.66F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(  28.26F,  -9.52F, -15.43F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(  62.60F,  10.79F, -41.74F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(  46.84F,  16.14F, -39.76F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( -28.52F,  28.40F, -21.18F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec( -38.85F,  26.94F, -16.87F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.degreeVec( -42.85F,  13.55F,  -6.35F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.80F, KeyframeAnimations.degreeVec( -22.32F,   5.85F,  -2.25F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.95F, KeyframeAnimations.degreeVec(   0.00F,   0.00F,   0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec(   0.00F,   0.00F,   0.00F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-1.06F,  1.52F,  1.25F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec(-1.23F, -1.34F,  0.67F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(-1.29F, -0.03F,  3.64F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(-0.98F, -0.86F,  0.88F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec(-0.59F, -1.60F, -0.65F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.80F, KeyframeAnimations.posVec(-0.27F, -0.85F, -0.42F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.95F, KeyframeAnimations.posVec( 0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec( 0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  10.76F, -26.53F,  -7.71F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(   3.55F, -21.95F,  -1.81F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(  28.46F, -34.70F, -15.25F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(  -2.70F,  14.72F,  -5.38F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec( -15.70F,  31.59F,  -2.03F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.degreeVec( -15.25F,  24.58F,  -3.80F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.80F, KeyframeAnimations.degreeVec(   4.22F,   9.22F,   0.13F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.95F, KeyframeAnimations.degreeVec(   0.00F,   0.00F,   0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec(   0.00F,   0.00F,   0.00F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-0.89F, -0.12F, -1.45F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec(-0.89F, -1.12F, -0.45F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec( 0.13F, -0.90F, -1.35F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(-0.11F, -0.12F,  3.55F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec(-0.08F, -1.00F,  2.43F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.80F, KeyframeAnimations.posVec(-0.03F, -0.42F, -1.14F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.95F, KeyframeAnimations.posVec( 0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec( 0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.00F,  0.00F, -1.50F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec(-1.00F, -1.25F, -0.36F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec( 0.00F, -2.00F, -2.50F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(-0.75F,  0.00F,  3.50F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec(-0.50F, -0.86F,  2.40F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.80F, KeyframeAnimations.posVec(-0.42F, -0.36F, -1.15F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.95F, KeyframeAnimations.posVec( 0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec( 0.00F,  0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // SWORD BLOCK  (CPA shield_animation.json)
    // Left arm raised with shield, body turned.  5 s looping.
    // Key pose (t=0): body rotated 25° right, left arm held across body.
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SWORD_BLOCK =
            AnimationDefinition.Builder.withLength(5.0F).looping()

                    // Body — gentle sway while holding block guard (yaw stays ~25°)
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(  7.50F, 25.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F, KeyframeAnimations.degreeVec( 10.04F, 24.97F,  1.48F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec( 10.86F, 24.90F,  2.63F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F, KeyframeAnimations.degreeVec(  8.85F, 24.91F,  2.52F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(  4.19F, 24.97F,  1.37F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F, KeyframeAnimations.degreeVec(  0.00F, 25.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F, KeyframeAnimations.degreeVec( -2.83F, 24.92F, -1.60F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F, KeyframeAnimations.degreeVec( -4.52F, 24.80F, -3.14F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec( -3.15F, 24.79F, -3.30F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.5F, KeyframeAnimations.degreeVec(  2.40F, 24.91F, -1.75F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F, KeyframeAnimations.degreeVec(  7.50F, 25.00F,  0.00F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.00F, 0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.posVec(0.00F, 0.00F, -1.06F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0.00F, 0.00F, -0.52F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F, KeyframeAnimations.posVec(0.00F, 0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F, KeyframeAnimations.posVec(0.00F, 0.00F,  0.91F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0.00F, 0.00F,  0.95F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F, KeyframeAnimations.posVec(0.00F, 0.00F,  0.00F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Right arm — sword-hand hangs at side, relaxed
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec( -3.66F,  -2.20F, 31.25F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec(  2.62F,  -2.37F, 24.12F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F, KeyframeAnimations.degreeVec( 14.18F,  -2.69F, 11.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F, KeyframeAnimations.degreeVec(  7.90F,  -2.52F, 18.13F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F, KeyframeAnimations.degreeVec( -3.66F,  -2.20F, 31.25F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.00F, -1.00F, 3.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.posVec(0.00F, -1.57F, 3.01F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F, KeyframeAnimations.posVec(0.00F,  0.00F, 3.00F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F, KeyframeAnimations.posVec(0.00F,  0.54F, 1.86F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F, KeyframeAnimations.posVec(0.00F, -1.00F, 3.00F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Left arm — shield is raised across body (CPA leftArm data)
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-151.81F, 85.99F, -65.56F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-143.71F, 86.08F, -54.72F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec( -63.63F, 86.50F,  22.92F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F, KeyframeAnimations.degreeVec(  -4.57F, 86.01F,  77.75F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(  11.32F, 83.77F,  87.36F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F, KeyframeAnimations.degreeVec(  13.70F, 81.61F,  83.42F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F, KeyframeAnimations.degreeVec(  17.79F, 81.95F,  84.46F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F, KeyframeAnimations.degreeVec(  20.88F, 84.46F,  86.95F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(   4.27F, 86.24F,  72.45F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.5F, KeyframeAnimations.degreeVec( -57.31F, 86.49F,  17.67F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F, KeyframeAnimations.degreeVec(-151.81F, 85.99F, -65.56F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec( 0.50F, -3.69F, -2.68F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.posVec( 0.50F, -4.85F, -1.69F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F, KeyframeAnimations.posVec( 0.50F, -4.99F, -1.51F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F, KeyframeAnimations.posVec( 0.50F, -4.23F, -2.33F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F, KeyframeAnimations.posVec( 0.50F, -2.47F, -3.29F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.5F, KeyframeAnimations.posVec( 0.50F, -2.47F, -3.29F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F, KeyframeAnimations.posVec( 0.50F, -3.69F, -2.68F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // GREATSWORD ATTACK  –  full two-handed overhead crash, 0.75 s, one-shot
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition GREATSWORD_ATTACK =
            AnimationDefinition.Builder.withLength(0.75F)

                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( -45.0F,   10.0F,   10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-120.0F,   20.0F,   20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-180.0F,   10.0F,   10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( -60.0F,   -5.0F,  -10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(  30.0F,  -10.0F,  -25.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(   0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec( 0.0F,  4.0F,  4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 1.0F,  2.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 2.0F, -3.0F, -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( -30.0F,  -10.0F,  -10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-110.0F,  -15.0F,  -20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-170.0F,  -10.0F,  -10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( -50.0F,    5.0F,   15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(  20.0F,   10.0F,   30.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(   0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec( -1.0F,  4.0F,  4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( -2.0F,  2.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( -3.0F, -3.0F, -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-15.0F,   8.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-25.0F,  12.0F,   3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( 25.0F,  -5.0F,  -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec( 15.0F,  -8.0F,  -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec( 0.0F,  2.0F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0.0F, -1.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 0.0F, -4.0F, -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec( 0.0F,  2.0F,  3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 0.0F, -5.0F, -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // AXE ATTACK  –  heavy looping chop arc, 0.65 s, one-shot
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition AXE_ATTACK =
            AnimationDefinition.Builder.withLength(0.65F)

                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( -30.0F,   20.0F,   15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-120.0F,   35.0F,   25.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec( -30.0F,   10.0F,   -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec(  40.0F,  -15.0F,  -40.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(  20.0F,   -8.0F,  -25.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.degreeVec(   0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec( 0.5F,  3.5F,  3.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 2.0F, -2.0F, -6.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.posVec( 1.5F, -2.5F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-20.0F,  10.0F, -15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-50.0F,  20.0F, -40.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec(-10.0F,   5.0F, -20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-10.0F,  15.0F,  -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec( 20.0F,  -8.0F,  -8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( 28.0F, -15.0F, -12.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec( 0.0F,  1.5F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0.0F, -2.5F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec( 0.0F,  1.5F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0.0F, -3.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.65F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // SPEAR ATTACK  –  step-in lunge, 0.55 s, one-shot
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition SPEAR_ATTACK =
            AnimationDefinition.Builder.withLength(0.55F)

                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(   0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( -20.0F,   -8.0F,   -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec( -80.0F,  -15.0F,  -10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-100.0F,  -20.0F,  -12.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( -60.0F,  -10.0F,   -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(   0.0F,    0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec( 0.0F,  1.0F,  2.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 1.5F,  0.5F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 1.0F,  0.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-30.0F,  10.0F, -20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-60.0F,  25.0F, -35.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(-25.0F,  10.0F, -15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( -8.0F,  5.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( 15.0F, -5.0F, -3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec( 0.0F,  0.5F,  1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0.0F, -1.5F, -4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec( 0.0F,  0.5F,  1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0.0F, -2.0F, -4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    .build();
}
