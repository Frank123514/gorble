package net.got.entity.client.mammoth;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animations for {@link GotMammothModel}.
 *
 * Animations:
 *   IDLE   — slow breathing sway, trunk curl, ear flap, tail swing (5.0 s, looping)
 *   WALK   — ponderous 4-beat plod with body roll (2.0 s, looping)
 *   CHARGE — thundering run, head lowered, trunk pulled back (1.0 s, looping)
 *   SWIM   — slow paddle with trunk held high (1.5 s, looping)
 */
public final class GotMammothAnimations {

    private GotMammothAnimations() {}

    // ── IDLE ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition IDLE =
            AnimationDefinition.Builder.withLength(5.0F).looping()
                    // Body breathe — very slow, deep
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.posVec(0, 0.5F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head slight nod
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(-4, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk lazy swing / curl
                    .addAnimation("trunk_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-15, 5, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.75F, KeyframeAnimations.degreeVec(-10, -5, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("trunk_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail lazy sway
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 10, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(0, -10, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec(0, 10, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── WALK ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition WALK =
            AnimationDefinition.Builder.withLength(2.0F).looping()
                    // Body plod — heavy bob
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0, 1, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.posVec(0, 1, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head sway with stride
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(3, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(3, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk swings gently while walking
                    .addAnimation("trunk_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-10, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-10, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Front left leg
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Front right leg — opposite
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Back left — diagonal to front left
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Back right — diagonal to front right
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── CHARGE ───────────────────────────────────────────────────────────────

    public static final AnimationDefinition CHARGE =
            AnimationDefinition.Builder.withLength(1.0F).looping()
                    // Body strong pitch — thundering gallop
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(0, -1.5F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec(0, -1.5F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head low — intimidation charge posture
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk pulled back during charge
                    .addAnimation("trunk_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Legs: fast alternating gallop
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── SWIM ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition SWIM =
            AnimationDefinition.Builder.withLength(1.5F).looping()
                    // Body tilts upward — mammoths are buoyant and high-bodied
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-10, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk raised above water
                    .addAnimation("trunk_upper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-50, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(-55, 5, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(-50, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("trunk_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-30, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Legs paddle slowly
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,   KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,   KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,   KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F,  KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,   KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}