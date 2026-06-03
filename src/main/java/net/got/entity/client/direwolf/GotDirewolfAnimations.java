package net.got.entity.client.direwolf;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animations for {@link GotDirewolfModel}.
 *
 * Animations:
 *   IDLE    — slow breathing bob, ear twitch, tail sway (4.0 s, looping)
 *   WALK    — 4-beat walk gait (1.0 s, looping)
 *   RUN     — bounding gallop (0.6 s, looping)
 *   ATTACK  — lunge snap (0.5 s, looping)
 *   SWIM    — paddling doggy-paddle (1.0 s, looping)
 */
public final class GotDirewolfAnimations {

    private GotDirewolfAnimations() {}

    // ── IDLE ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition IDLE =
            AnimationDefinition.Builder.withLength(4.0F).looping()
                    // Body breathe
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0, 0.3F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head slight sway
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-3, 2, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Ear twitch left
                    .addAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0, -10, 5), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Ear twitch right (offset)
                    .addAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0, 8, -5), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail lazy sway
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0, 8, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0, -8, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── WALK ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition WALK =
            AnimationDefinition.Builder.withLength(1.0F).looping()
                    // Body bob
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(0, 0.5F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec(0, 0.5F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head nod with body
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Front left leg — forward phase
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Front right leg — opposite phase
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(25, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Back left leg — diagonal to front left
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(25, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Back right leg — diagonal to front right
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(25, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-25, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail wagging during walk
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 10, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0, -10, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0, 10, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── RUN ──────────────────────────────────────────────────────────────────

    public static final AnimationDefinition RUN =
            AnimationDefinition.Builder.withLength(0.6F).looping()
                    // Body strong pitch with gallop
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.posVec(0, -1, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.posVec(0, 1, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.posVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Front left — big swing
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-45, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(-45, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(-45, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-45, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(30, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(35, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-35, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(35, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(35, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-35, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(35, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(-35, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(-35, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail streams back at speed
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F, KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── ATTACK ───────────────────────────────────────────────────────────────

    public static final AnimationDefinition ATTACK =
            AnimationDefinition.Builder.withLength(0.5F).looping()
                    // Lunge forward with neck and head snap
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(-10, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,   KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Body lurch
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-10, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(5, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── SWIM ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition SWIM =
            AnimationDefinition.Builder.withLength(1.0F).looping()
                    // Paddling — front and back legs alternate
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-30, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-30, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(15, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Body tilts slightly upward for doggy paddle
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec(-15, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}