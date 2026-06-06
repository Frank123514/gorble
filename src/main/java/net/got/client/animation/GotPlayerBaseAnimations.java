package net.got.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Base locomotion animations for the GOT player.
 * Written from scratch — KCD-inspired grounded, heavy medieval feel.
 */
public final class GotPlayerBaseAnimations {

    private GotPlayerBaseAnimations() {}

    // ─────────────────────────────────────────────────────────────────────
    // IDLE STANDING
    // Soldier at ease — subtle chest rise/fall, tiny weight shifts.
    // Very still. Not game-y. Like a man who has stood watch before.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition IDLE_STANDING =
            AnimationDefinition.Builder.withLength(5.0F).looping()
                    // Slow chest breath — body sinks slightly on exhale
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 2.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec( 3.5F, 0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec( 2.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, -0.3F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.5F,  KeyframeAnimations.posVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.0F,  KeyframeAnimations.posVec(0.0F, -0.3F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Head very slightly lifts with breath, tiny lateral drift
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0.0F,  1.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(-1.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec( 0.0F,  1.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Sword arm hangs naturally, slight elbow sway
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 5.0F, -3.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec( 3.0F, -1.0F, 6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec( 5.0F, -3.0F, 8.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 5.0F,  3.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec( 3.0F,  1.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec( 5.0F,  3.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // WALKING
    // Heavy boots on stone. Torso counter-rotates with each stride.
    // Arms swing with purpose, not loosely. Weight in every step.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition WALKING =
            AnimationDefinition.Builder.withLength(0.9F).looping()
                    // Torso counter-rotate — hips and shoulders opposing
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 6.0F, -8.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F, KeyframeAnimations.degreeVec( 4.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec( 6.0F,  8.0F,  2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.675F, KeyframeAnimations.degreeVec( 4.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec( 6.0F, -8.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Slight vertical bob from footfalls — down on plant
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec( 0.0F, -1.5F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F, KeyframeAnimations.posVec(-0.5F,  0.0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,  KeyframeAnimations.posVec( 0.0F, -1.5F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.675F, KeyframeAnimations.posVec( 0.5F,  0.0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.9F,   KeyframeAnimations.posVec( 0.0F, -1.5F, -1.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Head stays level despite body bob — like a soldier
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec( 0.0F, -1.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F, KeyframeAnimations.posVec(-0.3F,  0.0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,  KeyframeAnimations.posVec( 0.0F, -1.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.675F, KeyframeAnimations.posVec( 0.3F,  0.0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.9F,   KeyframeAnimations.posVec( 0.0F, -1.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Right arm — swings back when left leg forward
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 38.0F, 0.0F,  4.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F, KeyframeAnimations.degreeVec( 12.0F, 0.0F,  6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(-32.0F, 0.0F,  8.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.675F, KeyframeAnimations.degreeVec( -8.0F, 0.0F,  6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec( 38.0F, 0.0F,  4.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-32.0F, 0.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F, KeyframeAnimations.degreeVec( -8.0F, 0.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec( 38.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.675F, KeyframeAnimations.degreeVec( 12.0F, 0.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec(-32.0F, 0.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Legs — deliberate heel-toe, slight knee flex
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec( 42.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.675F, KeyframeAnimations.degreeVec( 14.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F, -1.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F, KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,  KeyframeAnimations.posVec(0.0F, -1.0F,  2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.675F, KeyframeAnimations.posVec(0.0F,  2.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.9F,   KeyframeAnimations.posVec(0.0F, -1.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 42.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F, KeyframeAnimations.degreeVec( 14.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.675F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec( 42.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F, -1.0F,  2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F, KeyframeAnimations.posVec(0.0F,  2.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,  KeyframeAnimations.posVec(0.0F, -1.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.675F, KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.9F,   KeyframeAnimations.posVec(0.0F, -1.0F,  2.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // RUNNING
    // Full sprint — torso leans hard forward, arms pump aggressively,
    // legs extend fully. A man running for his life or into battle.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition RUNNING =
            AnimationDefinition.Builder.withLength(0.45F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(18.0F, -10.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.1125F, KeyframeAnimations.degreeVec(15.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F,  KeyframeAnimations.degreeVec(18.0F,  10.0F,  3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3375F, KeyframeAnimations.degreeVec(15.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,   KeyframeAnimations.degreeVec(18.0F, -10.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec( 0.0F, -2.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.1125F, KeyframeAnimations.posVec(-1.0F,  0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F,  KeyframeAnimations.posVec( 0.0F, -2.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3375F, KeyframeAnimations.posVec( 1.0F,  0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,   KeyframeAnimations.posVec( 0.0F, -2.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec( 0.0F, -1.5F, -3.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F,  KeyframeAnimations.posVec( 0.0F, -1.5F, -3.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,   KeyframeAnimations.posVec( 0.0F, -1.5F, -3.5F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 65.0F,  0.0F,  6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.1125F, KeyframeAnimations.degreeVec( 20.0F,  0.0F,  5.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F,  KeyframeAnimations.degreeVec(-55.0F,  0.0F,  6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3375F, KeyframeAnimations.degreeVec(-15.0F,  0.0F,  5.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,   KeyframeAnimations.degreeVec( 65.0F,  0.0F,  6.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-55.0F,  0.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.1125F, KeyframeAnimations.degreeVec(-15.0F,  0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F,  KeyframeAnimations.degreeVec( 65.0F,  0.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3375F, KeyframeAnimations.degreeVec( 20.0F,  0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,   KeyframeAnimations.degreeVec(-55.0F,  0.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.1125F, KeyframeAnimations.degreeVec( -5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F,  KeyframeAnimations.degreeVec( 55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3F,    KeyframeAnimations.degreeVec( 65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3375F, KeyframeAnimations.degreeVec( 20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,   KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0.0F, -1.5F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F,  KeyframeAnimations.posVec(0.0F, -1.5F,  2.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3F,    KeyframeAnimations.posVec(0.0F,  2.5F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,   KeyframeAnimations.posVec(0.0F, -1.5F, -3.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.075F,  KeyframeAnimations.degreeVec( 65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.1125F, KeyframeAnimations.degreeVec( 20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F,  KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3375F, KeyframeAnimations.degreeVec( -5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,   KeyframeAnimations.degreeVec( 55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0.0F, -1.5F,  2.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.075F,  KeyframeAnimations.posVec(0.0F,  2.5F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.225F,  KeyframeAnimations.posVec(0.0F, -1.5F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.45F,   KeyframeAnimations.posVec(0.0F, -1.5F,  2.5F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // IDLE SNEAK
    // Low crouch — real tension. Not cartoon sneak. Like picking a lock
    // or pressing against a wall. Torso hunched, arms guarded.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition IDLE_SNEAK =
            AnimationDefinition.Builder.withLength(3.0F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(28.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(32.0F,  2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(28.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, -2.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5F,  KeyframeAnimations.posVec(0.0F, -2.5F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.0F,  KeyframeAnimations.posVec(0.0F, -2.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Head stays level — always watching
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(-22.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Arms held close to body — guarded
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 20.0F, -5.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec( 18.0F, -8.0F, 14.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec( 20.0F, -5.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 20.0F,  5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec( 18.0F,  8.0F, -14.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec( 20.0F,  5.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // FALLING
    // Not flailing — a trained fighter falling tries to control it.
    // Arms out wide to catch balance, body opens up, slight tuck.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition FALLING =
            AnimationDefinition.Builder.withLength(0.6F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.6F,  KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Arms spread wide — balance
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-15.0F, 10.0F, 90.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-10.0F,  5.0F, 80.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(-15.0F, 10.0F, 90.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-15.0F, -10.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-10.0F,  -5.0F, -80.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(-15.0F, -10.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Legs slightly apart and bent — ready for landing
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(25.0F, 8.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(30.0F, 8.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(25.0F, 8.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(25.0F, -8.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(30.0F, -8.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(25.0F, -8.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // JUMP
    // Explosive push-off — crouch into launch, tuck legs, land hard.
    // Not floaty. This guy has armour on.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition JUMP =
            AnimationDefinition.Builder.withLength(0.7F).looping()
                    // Push-off crouch → extension → tuck descent
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec(-8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.posVec(0.0F,  4.0F,-3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,   KeyframeAnimations.posVec(0.0F,  2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.7F,   KeyframeAnimations.posVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Arms drive upward on launch, settle in air
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  0.0F, 0.0F,   5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-50.0F, 0.0F,  80.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,   KeyframeAnimations.degreeVec(-15.0F, 0.0F,  30.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec( 15.0F, 0.0F,  35.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  0.0F, 0.0F,  -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-50.0F, 0.0F, -80.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,   KeyframeAnimations.degreeVec(-15.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec( 15.0F, 0.0F, -35.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Legs tuck on ascent
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,   KeyframeAnimations.degreeVec( 40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec( 20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,   KeyframeAnimations.degreeVec( 35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec( 15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // HORSE IDLE
    // Seated in saddle — upright posture, weight in the stirrups,
    // gentle sway as horse breathes underneath.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition HORSE_IDLE =
            AnimationDefinition.Builder.withLength(4.0F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(10.0F, 0.0F,  1.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(12.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(10.0F, 0.0F,  1.5F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0F,  KeyframeAnimations.posVec(0.0F, 3.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.posVec(0.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-8.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-6.0F, 2.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(-8.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Reins hand — held forward and steady
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-35.0F, 15.0F,  5.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-32.0F, 12.0F,  3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(-35.0F, 15.0F,  5.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Sword hand rests on thigh
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-40.0F, -15.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-37.0F, -12.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(-40.0F, -15.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    // Legs draped in stirrups
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-75.0F, 20.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-73.0F, 20.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(-75.0F, 20.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-75.0F, -20.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-73.0F, -20.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(-75.0F, -20.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();

    // ─────────────────────────────────────────────────────────────────────
    // HORSE RUNNING
    // Posting trot / full gallop — rider absorbs horse's movement,
    // leans slightly forward, reins tight, sword hand braced.
    // ─────────────────────────────────────────────────────────────────────
    public static final AnimationDefinition HORSE_RUNNING =
            AnimationDefinition.Builder.withLength(0.8F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(18.0F, 12.0F,  2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(25.0F, 10.0F,  4.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(18.0F, 12.0F,  2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(25.0F, 10.0F,  4.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.8F,  KeyframeAnimations.degreeVec(18.0F, 12.0F,  2.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 3.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2F,  KeyframeAnimations.posVec(0.0F, 1.5F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4F,  KeyframeAnimations.posVec(0.0F, 3.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.6F,  KeyframeAnimations.posVec(0.0F, 1.5F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.8F,  KeyframeAnimations.posVec(0.0F, 3.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2F,  KeyframeAnimations.posVec(0.0F, -2.5F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4F,  KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.6F,  KeyframeAnimations.posVec(0.0F, -2.5F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.8F,  KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-30.0F, 14.0F,  7.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(-45.0F, 14.0F,  7.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-30.0F, 14.0F,  7.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.8F,  KeyframeAnimations.degreeVec(-30.0F, 14.0F,  7.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 5.0F, -18.0F, 42.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(10.0F, -18.0F, 48.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec( 5.0F, -18.0F, 42.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.8F,  KeyframeAnimations.degreeVec( 5.0F, -18.0F, 42.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-28.0F, 14.0F, 16.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(-32.0F, 10.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-28.0F, 14.0F, 16.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.8F,  KeyframeAnimations.degreeVec(-28.0F, 14.0F, 16.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-28.0F, -14.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(-32.0F, -10.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-28.0F, -14.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.8F,  KeyframeAnimations.degreeVec(-28.0F, -14.0F, -16.0F), AnimationChannel.Interpolations.CATMULLROM)
                    ))
                    .build();
}
