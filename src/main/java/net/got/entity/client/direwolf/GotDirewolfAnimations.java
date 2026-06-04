package net.got.entity.client.direwolf;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animations for {@link GotDirewolfModel}.
 *
 * Animations:
 *   IDLE   — relaxed breathing, ear flick, tail sway (3.5 s, looping)
 *   WALK   — 4-beat trot with neck bob (0.9 s, looping)
 *   RUN    — bounding gallop with back flex (0.55 s, looping)
 *   ATTACK — lunge-and-bite (0.6 s, looping while attacking)
 *   HOWL   — head-up howl display (2.0 s, looping)
 */
public final class GotDirewolfAnimations {

    private GotDirewolfAnimations() {}

    // ── IDLE ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition IDLE =
            AnimationDefinition.Builder.withLength(3.5F).looping()

                    // Subtle breathing
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.75F, KeyframeAnimations.posVec(0F, 0.3F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F,  KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Head rests slightly forward
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.75F, KeyframeAnimations.degreeVec(-2F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F,  KeyframeAnimations.degreeVec(0F,  0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Ear flicks — right ear first, then left offset
                    .addAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,   0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8F,  KeyframeAnimations.degreeVec(0F, -12F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.6F,  KeyframeAnimations.degreeVec(0F,   0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.4F,  KeyframeAnimations.degreeVec(0F,   8F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F,  KeyframeAnimations.degreeVec(0F,   0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,   0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0F,  12F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0F,   0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.8F,  KeyframeAnimations.degreeVec(0F,  -8F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F,  KeyframeAnimations.degreeVec(0F,   0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Tail sways lazily
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,  0F,  8F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.75F, KeyframeAnimations.degreeVec(0F,  0F, -8F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F,  KeyframeAnimations.degreeVec(0F,  0F,  8F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("tail_b", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,  0F,  6F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.75F, KeyframeAnimations.degreeVec(0F,  0F, -6F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5F,  KeyframeAnimations.degreeVec(0F,  0F,  6F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── WALK ─────────────────────────────────────────────────────────────────
    // Diagonal trot: FL+BR move together, FR+BL move together.

    public static final AnimationDefinition WALK =
            AnimationDefinition.Builder.withLength(0.9F).looping()

                    // Body bobs once per half-cycle
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F, KeyframeAnimations.posVec(0F, 0.4F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.posVec(0F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F, KeyframeAnimations.posVec(0F, 0.4F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.posVec(0F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Neck swings with body
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F, KeyframeAnimations.degreeVec(3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F, KeyframeAnimations.degreeVec(3F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Tail carried upright, wagging slightly
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0F, 0F,  5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(0F, 0F, -5F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec(0F, 0F,  5F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Shoulder + leg: front-left (diagonal with back-right)
                    .addAnimation("shoulder_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec( 18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec(-18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(-18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec( 18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec(-18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec( 18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.45F,  KeyframeAnimations.degreeVec( 18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.675F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9F,   KeyframeAnimations.degreeVec(-18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── RUN ──────────────────────────────────────────────────────────────────
    // Bounding gallop: back arches, front/back pairs launch and land.

    public static final AnimationDefinition RUN =
            AnimationDefinition.Builder.withLength(0.55F).looping()

                    // Body arches (spine flexion in gallop)
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1375F, KeyframeAnimations.degreeVec(-5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec( 5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4125F, KeyframeAnimations.degreeVec(-5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.degreeVec( 5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,    KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1375F, KeyframeAnimations.posVec(0F, 1.0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4125F, KeyframeAnimations.posVec(0F, 1.0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.posVec(0F, 0F, 0F),    AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Neck stretches forward during gallop
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec( 5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F, KeyframeAnimations.degreeVec(-8F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,  KeyframeAnimations.degreeVec( 5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Tail streams back but bounces upward on thrust
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-15F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F, KeyframeAnimations.degreeVec(  5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,  KeyframeAnimations.degreeVec(-15F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Front shoulders pump hard
                    .addAnimation("shoulder_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1375F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4125F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1375F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4125F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1375F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4125F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,    KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1375F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.275F,  KeyframeAnimations.degreeVec( 40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4125F, KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.55F,   KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── ATTACK ───────────────────────────────────────────────────────────────
    // Quick forward lunge and jaw snap.

    public static final AnimationDefinition ATTACK =
            AnimationDefinition.Builder.withLength(0.6F).looping()

                    // Body lunges forward (position shift)
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0F, 0F,  0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.posVec(0F, 0F, -2F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,  KeyframeAnimations.posVec(0F, 0F,  0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.posVec(0F, 0F,  0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Head dips forward into bite
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,  KeyframeAnimations.degreeVec(-15F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec( 10F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Jaw snaps open and closed on the bite
                    .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,  KeyframeAnimations.degreeVec(30F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2F,  KeyframeAnimations.degreeVec( 0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec( 0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Ears pin back aggressively
                    .addAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,  0F,  0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(0F, 20F,  0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(0F,  0F,  0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(0F,  0F,  0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,   0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F, KeyframeAnimations.degreeVec(0F, -20F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(0F,   0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(0F,   0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── HOWL ─────────────────────────────────────────────────────────────────
    // Head-up howl with tail raised.

    public static final AnimationDefinition HOWL =
            AnimationDefinition.Builder.withLength(2.0F).looping()

                    // Head lifts back into howl
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-30F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,  KeyframeAnimations.degreeVec(-28F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Neck extends back with head
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-15F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,  KeyframeAnimations.degreeVec(-12F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Jaw opens on howl
                    .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec( 0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(20F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,  KeyframeAnimations.degreeVec(18F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec( 0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Ears prick forward alert
                    .addAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(0F, -8F, 0F),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0F,  0F, 0F),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(0F, 8F, 0F),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0F, 0F, 0F),   AnimationChannel.Interpolations.LINEAR)
                    ))

                    // Tail sweeps upward during howl
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4F,  KeyframeAnimations.degreeVec(-25F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4F,  KeyframeAnimations.degreeVec(-22F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(  0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}