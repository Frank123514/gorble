package net.got.event.entity.client.stag;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animations for {@link GotStagModel}, exported directly from
 * gotdeer.bbmodel via Blockbench 5.1.4.
 *
 * Animations:
 *   IDLE     — gentle breathing bob + ear/tail flicks (3.0 s, looping)
 *   WALK     — 4-beat walk gait with body bob (1.1619 s, looping)
 *   RUN      — bounding gallop (0.7353 s, looping)
 *   REAR     — rearing on hind legs (1.3267 s, looping)
 *   TAIL_WAG — excited tail wag when tamed (0.9 s, looping)
 */
public final class GotStagAnimations {

        private GotStagAnimations() {}

        // ── IDLE ─────────────────────────────────────────────────────────────────

        public static final AnimationDefinition IDLE =
                AnimationDefinition.Builder.withLength(3.0F).looping()
                        .addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F,  KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5F,  KeyframeAnimations.posVec(0.0F, 0.4F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(3.0F,  KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5F,  KeyframeAnimations.degreeVec(-3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(3.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5F,  KeyframeAnimations.degreeVec(-3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(3.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("TailA", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F,  5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(3.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Ear1", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.9F,  KeyframeAnimations.degreeVec(0.0F, -8.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.8F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2.7F,  KeyframeAnimations.degreeVec(0.0F,  5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(3.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Ear2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.9F,  KeyframeAnimations.degreeVec(0.0F,  8.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.8F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2.7F,  KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(3.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .build();

        // ── WALK ─────────────────────────────────────────────────────────────────

        public static final AnimationDefinition WALK =
                AnimationDefinition.Builder.withLength(1.1619F).looping()
                        .addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F,    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.2903F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5806F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8708F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.1611F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.2903F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5806F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8708F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.1611F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.2903F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5806F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8708F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.1611F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg4A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.2903F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5806F, KeyframeAnimations.degreeVec( 25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8708F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.1611F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg1A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.2903F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5806F, KeyframeAnimations.degreeVec( 25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8708F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.1611F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg3A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.2903F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5806F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8708F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.1611F, KeyframeAnimations.degreeVec( 25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg2A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.2903F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5806F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8708F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.1611F, KeyframeAnimations.degreeVec( 25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .build();

        // ── RUN ──────────────────────────────────────────────────────────────────

        public static final AnimationDefinition RUN =
                AnimationDefinition.Builder.withLength(0.7353F).looping()
                        .addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.1912F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3676F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5588F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.7353F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F,    KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.1912F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3676F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5588F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.7353F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.1912F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3676F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5588F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.7353F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.1912F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3676F, KeyframeAnimations.degreeVec( 5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5588F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.7353F, KeyframeAnimations.degreeVec( 5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg4A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.1912F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3676F, KeyframeAnimations.degreeVec( 40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5588F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.7353F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg1A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.1912F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3676F, KeyframeAnimations.degreeVec( 25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5588F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.7353F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg3A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.1912F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3676F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5588F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.7353F, KeyframeAnimations.degreeVec( 40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg2A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.1912F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3676F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5588F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.7353F, KeyframeAnimations.degreeVec( 25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .build();

        // ── REAR ─────────────────────────────────────────────────────────────────

        public static final AnimationDefinition REAR =
                AnimationDefinition.Builder.withLength(1.3267F).looping()
                        .addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(   0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec( -32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F,  0.0F,  0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -2.0F, -1.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 7.0F, 3.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 7.0F, 5.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg1A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg1A", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.3F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg2A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg3A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.degreeVec(-95.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.0F, KeyframeAnimations.degreeVec(-70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.3F, KeyframeAnimations.degreeVec(-87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg3A", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 11.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg4A", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec(  0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.7F, KeyframeAnimations.degreeVec(-95.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.3F, KeyframeAnimations.degreeVec(-65.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Leg4A", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F,  0.00F, 0.00F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F,  9.69F, 0.72F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 10.43F, 0.86F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, 11.00F, 1.00F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.3F, KeyframeAnimations.posVec(0.0F, 11.00F, 1.00F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("TailA", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F, KeyframeAnimations.degreeVec( 0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.2F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Ear1", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 8.0F, 6.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .addAnimation("Ear2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 8.0F, 6.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .build();

        // ── TAIL_WAG ─────────────────────────────────────────────────────────────

        public static final AnimationDefinition TAIL_WAG =
                AnimationDefinition.Builder.withLength(0.9F).looping()
                        .addAnimation("TailA", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F,  0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3F,  KeyframeAnimations.degreeVec(0.0F,  0.0F, -25.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.55F, KeyframeAnimations.degreeVec(0.0F,  0.0F,  25.0F), AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8F,  KeyframeAnimations.degreeVec(0.0F,  0.0F,   0.0F), AnimationChannel.Interpolations.LINEAR)
                        ))
                        .build();
}