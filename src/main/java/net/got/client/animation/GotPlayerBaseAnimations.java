package net.got.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Base locomotion and state animations for the GOT player.
 *
 * <p>All data ported directly from CPA (Custom Player Animations) JSON files
 * and converted to Minecraft's AnimationDefinition / KeyframeAnimations system.
 * Bone names are remapped: CPA "torso" → "body", rest stay the same.
 *
 * <p>NOTE: CPA uses a "scale" channel that vanilla AnimationDefinition does not
 * support directly. Scale keyframes are omitted; the visual squash/stretch is
 * approximated through position offsets where it mattered most.
 *
 * <p>Looping animations should be played with AnimationDefinition.Builder.looping().
 */
public final class GotPlayerBaseAnimations {

    private GotPlayerBaseAnimations() {}

    // ─────────────────────────────────────────────────────────────────────────
    // IDLE STANDING  –  gentle breathing bob, 4 s loop
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition IDLE_STANDING =
            AnimationDefinition.Builder.withLength(4.0F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(0,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0,-0.65F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.posVec(0,-0.58F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,  KeyframeAnimations.posVec(0,-0.42F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0,-0.32F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,  KeyframeAnimations.posVec(0,-0.14F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.posVec(0, 0.0F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.4F,  KeyframeAnimations.posVec(0,-0.07F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.8F,  KeyframeAnimations.posVec(0,-0.23F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.2F,  KeyframeAnimations.posVec(0,-0.42F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.6F,  KeyframeAnimations.posVec(0,-0.58F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.posVec(0,-0.65F,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // head bobs gently with the breath
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0,-0.60F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0,-0.30F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.posVec(0, 0.0F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.posVec(0,-0.30F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.posVec(0,-0.60F,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // arms sway very slightly outward and back
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-1.19F, 0.11F,  2.70F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.15F, KeyframeAnimations.degreeVec( 4.98F,-0.42F,  4.79F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.2F,  KeyframeAnimations.degreeVec( 0.0F,  0.0F,   7.5F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.15F, KeyframeAnimations.degreeVec(-5.60F, 0.50F,  5.17F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(-1.19F, 0.11F,  2.70F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0,-0.5F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.posVec(0, 0.0F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.posVec(0,-0.5F,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-1.19F,-0.11F, -2.70F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.15F, KeyframeAnimations.degreeVec( 4.98F, 0.42F, -4.79F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.2F,  KeyframeAnimations.degreeVec( 0.0F,  0.0F,  -7.5F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.15F, KeyframeAnimations.degreeVec(-5.60F,-0.50F, -5.17F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(-1.19F,-0.11F, -2.70F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0,-0.5F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.posVec(0, 0.0F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.posVec(0,-0.5F,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // WALKING  –  full arm/leg swing + torso sway, 0.6 s loop
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition WALKING =
            AnimationDefinition.Builder.withLength(0.6F).looping()
                    // torso counter-rotates against leg stride
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(10.81F,-22.14F,-4.11F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.05F, KeyframeAnimations.degreeVec( 9.51F,-18.04F,-5.39F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( 7.11F, -9.02F,-5.75F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec( 5.81F,  0.0F, -5.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( 7.11F,  9.02F,-2.40F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec( 9.51F, 18.04F, 1.31F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(10.81F, 22.14F, 4.11F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( 9.51F, 18.04F, 5.39F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.degreeVec( 7.11F,  9.02F, 5.75F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( 5.81F,  0.0F,  5.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.degreeVec( 7.11F, -9.02F, 2.40F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec( 9.51F,-18.04F,-1.31F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec(10.81F,-22.14F,-4.11F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0,-2,-2), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(-1, 0,-1), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec( 0,-2,-2), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 1, 0,-1), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec( 0,-2,-2), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // head swings opposite to torso
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.0F,-2,-3), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(-0.5F,-0.5F,-1), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec( 0.0F,-2,-3), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 0.5F,-0.5F,-1), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec( 0.0F,-2,-3), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // right arm swings forward when left leg steps
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec( 45,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec( 18.44F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-45,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(-20,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec( 45,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-0.25F,-3.5F,-1), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(-0.75F, 0.0F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec(-0.5F, -3.0F,-3), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 0.75F, 0.0F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec(-0.25F,-3.5F,-1), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(-45,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-20,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec( 45,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( 18.44F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec(-45,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0.5F,-3,-3), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(-0.75F, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec( 0.25F,-3.5F,-1), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 0.75F, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec( 0.5F,-3,-3), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // legs alternate stride
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(-40,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec( -1.5F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec( 40,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( 46.93F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( 13.53F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec(-40,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(0,-1.5F,-2), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec(0,-1.5F, 2), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.posVec(0, 1.35F,-1.47F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(0, 2.44F,-3), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec(0, 0.7F,-2.63F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec(0,-1.5F,-2), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec( 40,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.05F, KeyframeAnimations.degreeVec( 46.93F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec( 13.53F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-40,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( -1.5F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec( 40,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(0,-1.5F, 2), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec(0, 1.35F,-1.47F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(0, 2.44F,-3), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(0, 0.7F,-2.63F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec(0,-1.5F,-2), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec(0,-1.5F, 2), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // RUNNING  –  wide stride, forward lean, arms pump hard, 1.2 s loop
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition RUNNING =
            AnimationDefinition.Builder.withLength(1.2F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(26.78F,-20.30F,-9.93F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(18.29F,  0.0F, -5.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(26.78F, 20.30F, 9.93F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(18.29F,  0.0F,  5.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec(26.78F,-20.30F,-9.93F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(18.29F,  0.0F, -5.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.degreeVec(26.78F, 20.30F, 9.93F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec(18.29F,  0.0F,  5.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.degreeVec(26.78F,-20.30F,-9.93F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-0.54F,-3.16F,-2.16F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(-1.0F, -1.18F,-2.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec( 0.54F,-3.16F,-2.16F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 1.0F, -1.18F,-2.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec(-0.54F,-3.16F,-2.16F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec(-1.0F, -1.18F,-2.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec( 0.54F,-3.16F,-2.16F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec( 1.0F, -1.18F,-2.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.posVec(-0.54F,-3.16F,-2.16F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-0.54F,-3.16F,-2.16F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(-1.0F, -1.09F,-2.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec( 0.54F,-3.16F,-2.16F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 1.0F, -1.09F,-2.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec(-0.54F,-3.16F,-2.16F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.posVec(-0.54F,-3.16F,-2.16F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // arms pump hard with a slight outward roll from the sprint lean
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec( 44.55F, -1.56F, 26.85F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec( 16.82F, -5.98F, 19.40F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-40.99F,-17.85F, 11.46F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(-17.61F,-13.99F, 18.84F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec( 44.55F, -1.56F, 26.85F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec( 16.82F, -5.98F, 19.40F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.degreeVec(-40.99F,-17.85F, 11.46F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec(-17.61F,-13.99F, 18.84F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.degreeVec( 44.55F, -1.56F, 26.85F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-0.22F,-3.48F,-0.18F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(-0.98F,-0.63F,-1.92F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec( 0.89F,-3.29F,-3.05F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 1.15F,-0.52F,-1.83F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec(-0.22F,-3.48F,-0.18F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.posVec(-0.22F,-3.48F,-0.18F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(-40.99F, 17.85F,-11.46F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-17.88F, 14.69F,-18.67F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec( 44.55F,  1.56F,-26.85F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec( 16.82F,  5.98F,-19.40F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec(-40.99F, 17.85F,-11.46F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-17.88F, 14.69F,-18.67F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.degreeVec( 44.55F,  1.56F,-26.85F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec( 16.82F,  5.98F,-19.40F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.degreeVec(-40.99F, 17.85F,-11.46F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-0.89F,-3.29F,-3.05F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(-1.02F,-0.52F,-1.83F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec( 0.22F,-3.48F,-0.18F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec( 1.11F,-0.63F,-1.92F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec(-0.89F,-3.29F,-3.05F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.posVec(-0.89F,-3.29F,-3.05F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // legs do wide galloping strides
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(-38.5F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(  0.0F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec( 38.5F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( 40.08F,0,0),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(  2.94F,0,0),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(-37.46F,0,0),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec(-38.5F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.degreeVec(-38.5F,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(0,-1.3F,-0.1F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec(0,-0.47F, 3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec(0,-0.74F, 2.41F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.40F, KeyframeAnimations.posVec(0, 3.42F,-3.26F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(0, 4.41F,-4.6F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec(0, 0.45F,-1.72F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec(0,-1.3F,-0.1F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.posVec(0,-1.3F,-0.1F),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec( 38.5F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.05F, KeyframeAnimations.degreeVec( 40.08F,0,0),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(  2.94F,0,0),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-37.46F,0,0),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.degreeVec(-38.5F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(  0.0F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.degreeVec( 38.5F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.degreeVec( 38.5F,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(0,-1.3F, 4.1F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec(0, 2.42F,-0.26F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(0, 3.54F,-2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(0, 0.22F,-1.04F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.30F, KeyframeAnimations.posVec(0,-1.3F,-0.1F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.50F, KeyframeAnimations.posVec(0,-0.47F, 3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.60F, KeyframeAnimations.posVec(0,-1.3F, 4.1F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.20F, KeyframeAnimations.posVec(0,-1.3F, 4.1F),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // IDLE SNEAK  –  crouched breathing, 4 s loop
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition IDLE_SNEAK =
            AnimationDefinition.Builder.withLength(4.0F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.1F, KeyframeAnimations.degreeVec(35.0F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(30.0F,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0,-3.25F,-3), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0,-4.0F, -3), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0,-3.25F,-3), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0,-4.25F,-3), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0,-5.0F, -3), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0,-4.25F,-3), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.15F,-2.71F, 1.74F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.1F, KeyframeAnimations.degreeVec(14.39F,-5.50F,12.33F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(15.15F,-2.71F, 1.74F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0,-2.21F,-2.53F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0,-3.11F,-2.53F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0,-2.21F,-2.53F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.15F, 2.71F,-1.74F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.1F, KeyframeAnimations.degreeVec(14.39F, 5.50F,-12.33F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(15.15F, 2.71F,-1.74F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0,-2.21F,-2.53F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0,-3.11F,-2.53F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0,-2.21F,-2.53F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // FALLING  –  arms flare out, body tilts back, gentle loop (0.83 s)
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition FALLING =
            AnimationDefinition.Builder.withLength(0.8333F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-0.78F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.0833F, KeyframeAnimations.degreeVec( 0.0F, 0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,    KeyframeAnimations.degreeVec(-7.5F, 0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-0.78F,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0,0,0),       AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4167F, KeyframeAnimations.posVec(0,1.0F,1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8333F, KeyframeAnimations.posVec(0,0,0),       AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0,0,0),       AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4167F, KeyframeAnimations.posVec(0,0,1.25F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8333F, KeyframeAnimations.posVec(0,0,0),       AnimationChannel.Interpolations.LINEAR)
                    ))
                    // arms splay outward and rotate
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-12.88F,  8.66F, 112.17F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.0833F, KeyframeAnimations.degreeVec(  0.0F,   0.0F,  127.5F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,   KeyframeAnimations.degreeVec( 22.03F,-17.99F,  88.63F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,    KeyframeAnimations.degreeVec(  0.0F,   0.0F,   37.5F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,   KeyframeAnimations.degreeVec(-24.38F, 15.91F,  84.15F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-12.88F,  8.66F, 112.17F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0, 0,-1.1F),  AnimationChannel.Interpolations.LINEAR),  // NOTE: CPA has Z positive, but vanilla arm origin differs
                            new Keyframe(0.25F,   KeyframeAnimations.posVec(0,-0.62F,2.03F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4167F, KeyframeAnimations.posVec(0,-1.0F, 1.1F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5833F, KeyframeAnimations.posVec(0,-0.62F,0.06F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8333F, KeyframeAnimations.posVec(0, 0,-1.1F),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-12.88F, -8.66F,-112.17F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.0833F, KeyframeAnimations.degreeVec(  0.0F,   0.0F, -127.5F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,   KeyframeAnimations.degreeVec( 22.03F, 17.99F, -88.63F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,    KeyframeAnimations.degreeVec(  0.0F,   0.0F,  -37.5F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,   KeyframeAnimations.degreeVec(-24.38F,-15.91F, -84.15F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-12.88F, -8.66F,-112.17F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0, 0,-1.1F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,   KeyframeAnimations.posVec(0,-0.62F,2.03F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4167F, KeyframeAnimations.posVec(0,-1.0F, 1.1F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5833F, KeyframeAnimations.posVec(0,-0.62F,0.06F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8333F, KeyframeAnimations.posVec(0, 0,-1.1F),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // JUMP (idle / airborne)  –  body squash on takeoff, legs tuck, 0.8824 s loop
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition JUMP =
            AnimationDefinition.Builder.withLength(0.8824F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(  0.0F,    0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.degreeVec( 21.84F,  -4.13F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.degreeVec( -7.5F,    0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.degreeVec( -7.5F,    0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.posVec( 0.0F,  3.0F, -4.0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.posVec( 0.0F,  2.0F,  0.0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F),    AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.posVec( 0.0F, -1.99F,-3.69F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.posVec( 0.0F,  0.0F,  1.12F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.posVec( 0.0F,  0.0F,  1.12F),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // arms flare outward on the jump squash
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(  0.0F,  0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.degreeVec(-22.5F,  0.0F, 104.1F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.degreeVec( 15.0F,  0.0F,  37.5F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec( 0.0F,  0.0F,   0.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.posVec(-0.99F,-1.48F, -2.79F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.posVec( 0.0F, -0.87F,  0.55F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.posVec( 0.0F, -0.87F,  0.55F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(  0.0F,  0.0F,    0.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.degreeVec(-22.5F,  0.0F, -104.1F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.degreeVec(  0.0F,  0.0F,  -15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.degreeVec( 15.0F,  0.0F,  -37.5F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec( 0.0F,  0.0F,   0.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.posVec( 0.99F,-1.48F, -2.79F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.posVec( 0.0F, -0.87F,  0.55F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.posVec( 0.0F, -0.87F,  0.55F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // legs tuck up
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(  0.0F, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.degreeVec(-11.24F, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.degreeVec( 42.5F,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.degreeVec( 17.5F,  0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0,  0.0F,   0.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.posVec(0,  3.66F,  -0.2F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.posVec(0,  2.79F,  -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.posVec(0,  0.74F,  -1.3F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(  0.0F,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.degreeVec(-11.24F, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.degreeVec( 17.5F,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.degreeVec(-11.34F, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0,  0.0F,   0.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2941F, KeyframeAnimations.posVec(0,  3.66F,  -0.2F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5882F, KeyframeAnimations.posVec(0,  2.79F,  -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8824F, KeyframeAnimations.posVec(0,  0.63F,  -1.31F),AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // HORSE IDLE  –  seated lean forward, gentle breathing, 4 s loop
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition HORSE_IDLE =
            AnimationDefinition.Builder.withLength(4.0F).looping()
                    // body pitched forward like sitting on a saddle
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0,3,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0,3,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0,-0.6F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0, 0.0F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0,-0.6F,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // torso pitched forward for the horse seat
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(15,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.1F, KeyframeAnimations.degreeVec(12.5F,0,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(15,0,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // arms hold reins — forward and slightly outward
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-38.99F,-14.63F,-4.46F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.2F, KeyframeAnimations.degreeVec(-36.67F,-14.84F, 0.13F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(-38.99F,-14.63F,-4.46F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0,-0.5F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0, 0.0F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0,-0.5F,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-38.99F, 14.63F, 4.46F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.2F, KeyframeAnimations.degreeVec(-36.67F, 14.84F,-0.13F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(-38.99F, 14.63F, 4.46F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0,-0.5F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0, 0.0F,0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0,-0.5F,0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // legs splayed out in stirrups — vanilla leg pivot is top-of-leg, 0° = hanging down
                    // Rotate outward (Z) to splay over the saddle, slight forward bend (X)
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F,  35.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(28.0F, 0.0F,  35.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F,  35.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, -35.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(28.0F, 0.0F, -35.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, -35.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────────
    // HORSE RUNNING  –  body bobs with gallop rhythm, 1.05 s loop
    // ─────────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition HORSE_RUNNING =
            AnimationDefinition.Builder.withLength(1.05F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(12.93F,14.64F,3.32F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(28.28F,13.24F,6.95F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(29.49F,13.19F,7.34F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(12.93F,14.64F,3.32F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.degreeVec(12.93F,14.64F,3.32F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.80F, KeyframeAnimations.degreeVec(28.28F,13.24F,6.95F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.85F, KeyframeAnimations.degreeVec(29.49F,13.19F,7.34F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec(12.93F,14.64F,3.32F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(0,3,0),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec(0,1,-1),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec(0,2.5F,-2.5F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec(0,3,0),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.posVec(0,3,0),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.80F, KeyframeAnimations.posVec(0,1,-1),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec(0,2.5F,-2.5F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec(0,3,0),    AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(0,  0,   0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec(0, -3,  -3),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec(0,  0,   0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec(0, -3,  -3),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.70F, KeyframeAnimations.posVec(0,  0,   0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.90F, KeyframeAnimations.posVec(0, -3,  -3),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec(0,  0,   0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // arms brace against the gallop
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec( 0.38F,-19.5F, 45.07F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec( 0.86F,-20.3F, 50.49F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec( 8.71F,-18.44F,31.70F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec( 0.38F,-19.5F, 45.07F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec( 0.38F,-19.5F, 45.07F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0,-0.5F, 1),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec(-0.04F,-2.92F,0.01F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec( 0,-1.5F, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0,-0.5F, 1),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec( 0,-0.5F, 1),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(-30.3F,14.74F, 7.16F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(-50.3F,14.74F, 7.16F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-38.7F,13.65F, 8.67F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-30.3F,14.74F, 7.16F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec(-30.3F,14.74F, 7.16F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec( 0,  0,   0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec( 0.03F,-0.86F,0.52F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec(-0.35F,-1.16F,-0.08F),AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec( 0,  0,   0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec( 0,  0,   0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // legs stay in stirrups, bob with the gallop
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(-27.79F, 14.25F, 17.60F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(-34.36F,  9.20F,  9.82F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-13.89F, 12.05F, 18.96F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-27.79F, 14.25F, 17.60F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec(-27.79F, 14.25F, 17.60F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(-0.55F,0.23F,2.28F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec(-0.55F,2.23F,2.28F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec(-0.55F,0.62F,2.28F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec(-0.55F,0.23F,2.28F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec(-0.55F,0.23F,2.28F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.00F, KeyframeAnimations.degreeVec(-27.79F,-14.25F,-17.60F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.degreeVec(-34.36F, -9.20F, -9.82F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.degreeVec(-13.89F,-12.05F,-18.96F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(-27.79F,-14.25F,-17.60F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.degreeVec(-27.79F,-14.25F,-17.60F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.00F, KeyframeAnimations.posVec(0.55F,0.23F,2.28F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.10F, KeyframeAnimations.posVec(0.55F,2.23F,2.28F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20F, KeyframeAnimations.posVec(0.55F,0.62F,2.28F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec(0.55F,0.23F,2.28F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F, KeyframeAnimations.posVec(0.55F,0.23F,2.28F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}