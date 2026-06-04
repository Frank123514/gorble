package net.got.entity.client.mammoth;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animations for {@link GotMammothModel}.
 *
 * Animations:
 *   IDLE   — slow breathing sway + trunk curl + ear/tail flick (4.0 s, looping)
 *   WALK   — heavy 4-beat plod with shoulder roll (1.4 s, looping)
 *   RUN    — lumbering charge with trunk raised (0.9 s, looping)
 *   ROAR   — head-lift, trunk-raise threat display (2.0 s, looping)
 */
public final class GotMammothAnimations {

    private GotMammothAnimations() {}

    // ── IDLE ─────────────────────────────────────────────────────────────────
    // Gentle breathing bob, slow trunk sway, occasional tail flick.

    public static final AnimationDefinition IDLE =
            AnimationDefinition.Builder.withLength(4.0F).looping()

                    // Body breathes slowly up-down
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0F, 0.5F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Head nods very slightly
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Trunk curls down and back up lazily
                    .addAnimation("trunk_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(8F,  0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(-5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("trunk_b", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(10F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(-8F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("trunk_c", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(12F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.8F,  KeyframeAnimations.degreeVec(-6F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Tail lazy flick
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0F,  8F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(0F, -8F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── WALK ─────────────────────────────────────────────────────────────────
    // Heavy plodding walk: diagonal pairs (FL+BR then FR+BL), shoulders roll,
    // head bobs with each stride, tail sways gently.

    public static final AnimationDefinition WALK =
            AnimationDefinition.Builder.withLength(1.4F).looping()

                    // Body bobs twice per cycle (once per diagonal pair landing)
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.posVec(0F, 0.6F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,   KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F,  KeyframeAnimations.posVec(0F, 0.6F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Shoulder sways laterally with body
                    .addAnimation("shoulder", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0F,  0F,  2F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec(0F,  0F, -2F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.degreeVec(0F,  0F,  2F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Head nods forward with each step
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.degreeVec(5F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec(0F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F,  KeyframeAnimations.degreeVec(5F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.degreeVec(0F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Trunk swings loosely as head nods
                    .addAnimation("trunk_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-5F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec( 5F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.degreeVec(-5F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Front-left leg: forward on beat 1, back on beat 2
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec( 25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.degreeVec(-25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Front-right leg: opposite phase to front-left
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec(-25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.degreeVec( 25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Back-left leg: in phase with front-right (diagonal pair)
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec(-25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.degreeVec( 25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Back-right leg: in phase with front-left
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.35F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,   KeyframeAnimations.degreeVec( 25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.05F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,   KeyframeAnimations.degreeVec(-25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── RUN ──────────────────────────────────────────────────────────────────
    // Lumbering charge: trunk held high, big body pitch, faster leg swing.

    public static final AnimationDefinition RUN =
            AnimationDefinition.Builder.withLength(0.9F).looping()

                    // Body pitches forward and dips rhythmically
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F, KeyframeAnimations.posVec(0F, 1.2F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F, KeyframeAnimations.posVec(0F, 1.2F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Head pitches forward with momentum
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 8F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(-3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec( 8F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Trunk raised and bouncing with the stride
                    .addAnimation("trunk_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-20F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(-15F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec(-20F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("trunk_b", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(-20F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec(-25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Front-left leg big swing
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,   KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,    KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,   KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,    KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,   KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,    KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,   KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,    KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── ROAR ─────────────────────────────────────────────────────────────────
    // Threat display: head swings up, trunk rises and fans, body rocks back.

    public static final AnimationDefinition ROAR =
            AnimationDefinition.Builder.withLength(2.0F).looping()

                    // Body rocks back then forward
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec( 5F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(-5F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec( 0F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Head sweeps upward dramatically
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-20F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(-15F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Trunk raises high and curls upward — full trumpet pose
                    .addAnimation("trunk_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-30F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-40F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.6F,  KeyframeAnimations.degreeVec(-30F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("trunk_b", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-35F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.1F,  KeyframeAnimations.degreeVec(-50F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.7F,  KeyframeAnimations.degreeVec(-35F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("trunk_c", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(-40F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2F,  KeyframeAnimations.degreeVec(-55F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.8F,  KeyframeAnimations.degreeVec(-40F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("trunk_d", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7F,  KeyframeAnimations.degreeVec(-45F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3F,  KeyframeAnimations.degreeVec(-60F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.9F,  KeyframeAnimations.degreeVec(-45F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F),  AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Tail stiffens upward during roar
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-20F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(-20F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}