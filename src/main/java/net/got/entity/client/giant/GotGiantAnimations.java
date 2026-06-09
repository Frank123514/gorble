package net.got.entity.client.giant;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animations for {@link GotGiantModel}.
 *
 * <p>All timings are in seconds; Minecraft converts to ticks at runtime.
 *
 * <p>Part names match the updated Blockbench 5.1.4 model export:
 * <ul>
 *   <li>{@code shoulders} — replaces old "chest" (child of body)</li>
 *   <li>{@code beard}     — replaces old "jaw"/"brow" (child of head)</li>
 * </ul>
 *
 * Animations:
 *   IDLE   — slow breathing sway, occasional head turn (4.0 s, looping)
 *   WALK   — heavy bipedal lumber with arm swing (1.0833 s, looping)
 *   RUN    — lumbering charge with forward lean (0.55 s, looping)
 *   ATTACK — wide overhead club smash (1.25 s, one-shot)
 *   ROAR   — open-mouthed bellow, first enrage (2.5 s, one-shot)
 *   DEATH  — topple-and-crash collapse (3.0 s, one-shot)
 */
public final class GotGiantAnimations {

    private GotGiantAnimations() {}

    // ── IDLE (4 s, looping) ───────────────────────────────────────────────────

    public static final AnimationDefinition IDLE =
            AnimationDefinition.Builder.withLength(4.0F).looping()
                    // Slow chest-rise breathing
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.5F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulders", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(2.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head loll — slow side-to-side look
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec( 0.0F,  8.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-2.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F, KeyframeAnimations.degreeVec( 0.0F, -8.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Arms hang, slight sway
                    .addAnimation("arm_upper_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(3.0F, 0.0F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("arm_upper_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(3.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── WALK (1.0833 s, looping) ──────────────────────────────────────────────

    public static final AnimationDefinition WALK =
            AnimationDefinition.Builder.withLength(1.0833F).looping()
                    // Body rocks forward/back on each step
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(4.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(6.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(4.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head stays roughly level
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-2.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec( 2.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-2.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Arm swing — opposite to legs
                    .addAnimation("arm_upper_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-25.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec( 25.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-25.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("arm_upper_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 25.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-25.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec( 25.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Leg stride with position shifts for a heavy plodding gait
                    .addAnimation("leg_upper_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-30.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec( 30.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-30.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_upper_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0.0F, 0.0F,  4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_upper_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 30.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-30.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec( 30.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_upper_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,  4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,  4.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Lower legs bend on each step
                    .addAnimation("leg_lower_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,   KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,    KeyframeAnimations.degreeVec( 5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,    KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0833F, KeyframeAnimations.degreeVec(13.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_lower_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.5F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_lower_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec( 5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── RUN (0.55 s, looping) ─────────────────────────────────────────────────

    public static final AnimationDefinition RUN =
            AnimationDefinition.Builder.withLength(0.55F).looping()
                    // Body leans forward aggressively; position shift anchors the lean
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec( 5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5417F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec(-4.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.degreeVec(-8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("arm_upper_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec( 40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("arm_upper_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.degreeVec( 40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_upper_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec( 45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_upper_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_upper_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5417F, KeyframeAnimations.degreeVec( 45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_upper_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0.0F, 1.0F, 8.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,   KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 1.0F, 8.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_lower_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.14F,   KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec( 8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_lower_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125F,  KeyframeAnimations.posVec(0.0F, 1.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_lower_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.14F,   KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.41F,   KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.degreeVec( 8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_lower_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125F,  KeyframeAnimations.posVec(0.0F,  0.0F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,   KeyframeAnimations.posVec(0.0F,  0.0F,  0.5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F,  0.0F,  1.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── ATTACK — overhead club smash (1.25 s, one-shot) ───────────────────────

    public static final AnimationDefinition ATTACK =
            AnimationDefinition.Builder.withLength(1.25F)
                    // Wind-up: pull arm back and raise club overhead
                    .addAnimation("arm_upper_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(   0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-130.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-145.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(  60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(   0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("arm_lower_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec( 15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Body lunges forward on smash
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-5.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec( 2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head follows the swing
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec( 15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Club arc — starts behind, crashes down fast
                    .addAnimation("club", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec( 15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Anchor lower legs so feet don't drift
                    .addAnimation("leg_lower_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_lower_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── ROAR — first-enrage bellow (2.5 s, one-shot) ──────────────────────────

    public static final AnimationDefinition ROAR =
            AnimationDefinition.Builder.withLength(2.5F)
                    // Head lifts and tilts back to bellow
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.degreeVec(-28.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.6F,  KeyframeAnimations.degreeVec(-28.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Beard flares out during the bellow (replaces old jaw open)
                    .addAnimation("beard", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("beard", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F,  1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0.0F, 2.0F,  1.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.posVec(0.0F, 2.5F,  1.2F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.posVec(0.0F, 0.0F,  1.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Arms spread wide in a threat display
                    .addAnimation("arm_upper_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-50.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(-50.0F, 0.0F, -30.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("arm_upper_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-50.0F, 0.0F,  30.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(-50.0F, 0.0F,  30.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Shoulders expand during bellow
                    .addAnimation("shoulders", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,  KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Anchor lower legs
                    .addAnimation("leg_lower_right", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_lower_left", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -0.2F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── DEATH — topple-and-crash (3.0 s, one-shot) ────────────────────────────

    public static final AnimationDefinition DEATH =
            AnimationDefinition.Builder.withLength(3.0F)
                    // Body topples forward then settles right
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(15.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(45.0F, 15.0F, 30.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(55.0F, 20.0F, 85.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec( 0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.posVec( 3.0F, -4.0F,  2.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.posVec( 8.0F,-16.0F,  4.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head lolls to the side
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,  KeyframeAnimations.degreeVec(-10.0F,  0.0F, 10.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec( 20.0F, 15.0F, 40.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Arms flail outward as giant falls
                    .addAnimation("arm_upper_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-60.0F, 0.0F, -20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(-80.0F, 0.0F, -40.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("arm_upper_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0.0F, 0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-60.0F, 0.0F,  20.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(-80.0F, 0.0F,  40.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Legs buckle
                    .addAnimation("leg_upper_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_upper_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec( 5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}