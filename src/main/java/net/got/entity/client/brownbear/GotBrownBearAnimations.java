package net.got.entity.client.brownbear;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animations for {@link GotBrownBearModel}.
 * Generated from brown_bear.bbmodel via Blockbench 5.1.4 (Minecraft 1.19+, Mojang mappings).
 *
 * Animations:
 *   IDLE   — gentle body sway, ear flicks, jaw yawn, tail wag (4.0 s, looping)
 *   WALK   — 4-beat diagonal walk with body roll (1.356 s, looping)
 *   RUN    — bounding gallop with spine flex (0.51 s, looping)
 *   ATTACK — bear rears up on hind legs and swipes with front paws (1.2 s, one-shot)
 *   STAND  — rears up onto hind legs (1.667 s, one-shot / hold last frame)
 */
public final class GotBrownBearAnimations {

    private GotBrownBearAnimations() {}

    // ── IDLE ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition IDLE =
            AnimationDefinition.Builder.withLength(4.0F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-5.0F, -4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(-3.0F,  4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-4.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("snout", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F, KeyframeAnimations.degreeVec(4.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(8.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(5.0F, 0.0F,  4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, 12.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, -12.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(8.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, -12.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.25F, KeyframeAnimations.degreeVec(5.0F, 0.0F,  -4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, -12.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F,  12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, -12.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── WALK ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition WALK =
            AnimationDefinition.Builder.withLength(1.3559F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.339F,    KeyframeAnimations.degreeVec(3.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F, 0.0F,  1.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0169F,   KeyframeAnimations.degreeVec(3.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,      KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.339F,    KeyframeAnimations.posVec(0.0F, -0.5F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0169F,   KeyframeAnimations.posVec(0.0F, -0.5F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.339F,    KeyframeAnimations.degreeVec(-3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0169F,   KeyframeAnimations.degreeVec(-3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.339F,    KeyframeAnimations.degreeVec(-4.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0169F,   KeyframeAnimations.degreeVec(-4.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F, -8.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F,  8.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F, -8.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Front-right leg
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1695F,   KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.339F,    KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5932F,   KeyframeAnimations.degreeVec(20.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1695F,   KeyframeAnimations.degreeVec(-20.0F,0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4237F,   KeyframeAnimations.degreeVec(-10.0F,0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5932F,   KeyframeAnimations.degreeVec(5.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right_lower", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,      KeyframeAnimations.posVec(0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1695F,   KeyframeAnimations.posVec(0.0F, -2.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4237F,   KeyframeAnimations.posVec(0.0F, -1.5F, -0.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5932F,   KeyframeAnimations.posVec(0.0F, -0.5F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Back-left leg (diagonal with front-right)
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F,     0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1695F,   KeyframeAnimations.degreeVec(38.0F,    0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.339F,    KeyframeAnimations.degreeVec(32.0F,    0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5085F,   KeyframeAnimations.degreeVec(-21.33F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5932F,   KeyframeAnimations.degreeVec(-18.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F,     0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F,     0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2542F,   KeyframeAnimations.degreeVec(-10.0F,0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5085F,   KeyframeAnimations.degreeVec(-10.0F,0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5932F,   KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left_lower", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,      KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2542F,   KeyframeAnimations.posVec(0.0F, 0.0F, -0.3F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5085F,   KeyframeAnimations.posVec(0.0F, 0.0F, -0.3F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5932F,   KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Front-left leg
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8475F,   KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0169F,   KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2712F,   KeyframeAnimations.degreeVec(20.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5932F,   KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7627F,   KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0169F,   KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.1864F,   KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left_lower", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,      KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5932F,   KeyframeAnimations.posVec(0.0F, 0.0F, 0.2F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7627F,   KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0169F,   KeyframeAnimations.posVec(0.0F, 0.0F, 0.7F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.1864F,   KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Back-right leg
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F,    0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F,    0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8475F,   KeyframeAnimations.degreeVec(38.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0169F,   KeyframeAnimations.degreeVec(32.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.1864F,   KeyframeAnimations.degreeVec(-11.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2712F,   KeyframeAnimations.degreeVec(-18.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F,    0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,      KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8475F,   KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9322F,   KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.1864F,   KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right_lower", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,      KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.678F,    KeyframeAnimations.posVec(0.0F, 0.0F, 0.09F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8475F,   KeyframeAnimations.posVec(0.0F, 0.0F, 1.7F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9322F,   KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.1864F,   KeyframeAnimations.posVec(0.0F, 0.0F, 1.5F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3559F,   KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── RUN ──────────────────────────────────────────────────────────────────

    public static final AnimationDefinition RUN =
            AnimationDefinition.Builder.withLength(0.51F).looping()
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(5.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,  KeyframeAnimations.degreeVec(-3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(3.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.51F, KeyframeAnimations.degreeVec(2.5F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,  KeyframeAnimations.posVec(0.0F, -1.5F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F,  1.5F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.posVec(0.0F, -1.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(5.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,  KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(5.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(6.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-5.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(5.0F,   5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-5.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(30.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(-5.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(10.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(10.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.12F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(30.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(10.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(5.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.12F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-5.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(10.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(5.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(45.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-5.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right_lower", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(45.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.48F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec(-30.0F,0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(5.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.51F, KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left_lower", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F, KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0.0F, 0.1F,  0.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.51F, KeyframeAnimations.posVec(0.0F, 0.1F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── ATTACK ───────────────────────────────────────────────────────────────

    public static final AnimationDefinition ATTACK =
            AnimationDefinition.Builder.withLength(1.2F)
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(5.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0.0F, 7.0F,  1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 1.0F, -0.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 1.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(20.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0.0F, 8.0F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 2.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.degreeVec(-5.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0.0F, 7.0F, 3.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-70.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(20.0F,  0.0F,   5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F,   0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0.0F, 5.0F,    2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, -0.39F,  1.44F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.posVec(0.0F, 0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(-70.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.degreeVec(20.0F,  0.0F, -5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F,   0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0.0F, 5.0F,   1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.posVec(0.0F, 0.78F,  0.56F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.posVec(0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left_lower", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right_lower", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left_lower", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.posVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── STAND (bear rears up on hind legs — one-shot, holds last frame) ───────

    public static final AnimationDefinition STAND =
            AnimationDefinition.Builder.withLength(1.6667F)
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 17.0F, 10.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 24.5F, 15.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 24.0F, 16.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 16.0F, 9.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,   0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 16.0F, 9.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}