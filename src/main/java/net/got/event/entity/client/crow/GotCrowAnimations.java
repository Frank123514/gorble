package net.got.event.entity.client.crow;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animations for {@link GotCrowModel}.
 *
 * Animations:
 *   IDLE  — head tilt, blink-bob, tail bob (2.5 s, looping)
 *   WALK  — hopping stride with wing-balance adjustments (0.4 s, looping)
 *   FLY   — flapping wing cycle with tail spread (0.4 s, looping)
 */
public final class GotCrowAnimations {

    private GotCrowAnimations() {}

    // ── IDLE ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition IDLE =
            AnimationDefinition.Builder.withLength(2.5F).looping()
                    // Head bob and glance
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(-5, 15, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.8F,  KeyframeAnimations.degreeVec(-5, -12, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail bob
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Body breathe
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.posVec(0, 0.2F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── WALK ─────────────────────────────────────────────────────────────────
    // Crows hop — quick forward lurch, brief airborne, land

    public static final AnimationDefinition WALK =
            AnimationDefinition.Builder.withLength(0.4F).looping()
                    // Body hop
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,  KeyframeAnimations.posVec(0, -1, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0, -1, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head jerk forward with each hop (crows do this)
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,  KeyframeAnimations.posVec(0, 0, -0.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0, 0, -0.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Wings balance slightly
                    .addAnimation("wing_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,  KeyframeAnimations.degreeVec(0, 0, 8), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("wing_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,  KeyframeAnimations.degreeVec(0, 0, -8), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── FLY ──────────────────────────────────────────────────────────────────
    // Fast wing beats — crows flap rapidly for their size

    public static final AnimationDefinition FLY =
            AnimationDefinition.Builder.withLength(0.4F).looping()
                    // Left wing up-down
                    .addAnimation("wing_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-50, 0, -20), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(20, 0, 15), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-50, 0, -20), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Right wing mirrored
                    .addAnimation("wing_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-50, 0, 20), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(20, 0, -15), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-50, 0, 20), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Body tilts into flight posture
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F, KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail fans out in flight
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F, KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head stays level
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}