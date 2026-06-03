package net.got.entity.client.direwolf;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animations for {@link GotDirewolfModel}.
 *
 * <p>All animations reference the new model's part names from direwolf.bbmodel:
 * shoulder hierarchy ({@code shoulder_front/back_left/right}), articulated
 * lower legs ({@code leg_*_lower}), 3-segment tail ({@code tail_a/b/c}),
 * and {@code jaw}.
 *
 * <p>Animations:
 * <ul>
 *   <li>{@link #IDLE}   — slow breathing bob, ear twitch, tail sway, jaw rest (4.0 s, looping)</li>
 *   <li>{@link #WALK}   — 4-beat walk gait with lower leg articulation (1.0 s, looping)</li>
 *   <li>{@link #RUN}    — bounding gallop with shoulder drive (0.6 s, looping)</li>
 *   <li>{@link #ATTACK} — lunge-snap with jaw open (0.5 s, looping)</li>
 *   <li>{@link #SIT}    — direwolf sits back on haunches, tail curled forward (3.0 s, looping)</li>
 * </ul>
 */
public final class GotDirewolfAnimations {

    private GotDirewolfAnimations() {}

    // ── IDLE ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition IDLE =
            AnimationDefinition.Builder.withLength(4.0F).looping()
                    // Body — slow breathing rise
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F, KeyframeAnimations.posVec(0, 0,     0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.posVec(0, 0.25F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.posVec(0, 0,     0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head — subtle alert sway
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(-3, 2, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Ear left — twitch
                    .addAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0,   0,  0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0, -10,  5),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0,   0,  0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0,   0,  0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Ear right — offset twitch
                    .addAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0,  0,  0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F, KeyframeAnimations.degreeVec(0,  8, -5),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0,  0,  0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail A — lazy pendulum sway
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F, KeyframeAnimations.degreeVec(0,  8, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F, KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0, -8, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail B — follows A with slight lag
                    .addAnimation("tail_b", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F, KeyframeAnimations.degreeVec(0,  6, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F, KeyframeAnimations.degreeVec(0, -6, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.0F, KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── WALK ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition WALK =
            AnimationDefinition.Builder.withLength(1.0F).looping()
                    // Body — light bob
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0,     0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(0, 0.4F,  0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0, 0,     0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec(0, 0.4F,  0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0, 0,     0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head — nod with each stride pair
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(4, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(4, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail — gentle wag during walk
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0,  12, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0, -12, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0,  12, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Shoulders — rock gently side to side
                    .addAnimation("shoulder_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(20,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(20,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(20,  0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(20,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(20,  0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(20,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Lower legs — bend on the back-stroke
                    .addAnimation("leg_front_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(20, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(20, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(20, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(20, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(20, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(20, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── RUN ──────────────────────────────────────────────────────────────────

    public static final AnimationDefinition RUN =
            AnimationDefinition.Builder.withLength(0.6F).looping()
                    // Body — strong gallop pitch + vertical spring
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-6, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(6,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,   KeyframeAnimations.posVec(0, 0,    0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.posVec(0, -0.8F, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.posVec(0,  0.8F, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.posVec(0,  0,    0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Shoulders — powerful drive
                    .addAnimation("shoulder_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(32,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-32, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(32,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(32,  0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(-32, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(32,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(-32, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(-32, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Lower legs — sharp kick on extension
                    .addAnimation("leg_front_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(30, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(30, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(30, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(30, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(30, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(30, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(30, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(30, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,   KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail streams back
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(18, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6F,  KeyframeAnimations.degreeVec(18, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── ATTACK ───────────────────────────────────────────────────────────────

    public static final AnimationDefinition ATTACK =
            AnimationDefinition.Builder.withLength(0.5F).looping()
                    // Neck — forward lunge
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(22,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(-10, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head — snapping bite
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,   KeyframeAnimations.degreeVec(-18, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(14,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Jaw — open on lunge, snap shut
                    .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1F,   KeyframeAnimations.degreeVec(22, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F,  KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Body — lurch forward with the bite
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.15F,  KeyframeAnimations.degreeVec(-10, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3F,   KeyframeAnimations.degreeVec(5,   0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,   KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── SIT ──────────────────────────────────────────────────────────────────

    /**
     * The direwolf sits back on its haunches — a tame/resting pose.
     *
     * <p>Back haunches fold, forelegs straighten to prop the body upright,
     * the tail curls around beside the body, and the head tilts slightly
     * forward with an alert but relaxed look. A slow breathing bob and
     * occasional ear-twitch keep the pose feeling alive.
     */
    public static final AnimationDefinition SIT =
            AnimationDefinition.Builder.withLength(3.0F).looping()
                    // Body — shifted upright and back for seated posture; gentle breathing
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-30, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(-32, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(-30, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, -2,    0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.posVec(0, -1.6F, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.posVec(0, -2,    0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Neck — raised and relaxed
                    .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(20, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(20, 0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head — relaxed forward gaze, slow bob
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-5, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(-7, 2, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(-5, 0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Ear twitch — idle alert behaviour
                    .addAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0, -8, 4),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Front legs — straighter, supporting the upright torso
                    .addAnimation("shoulder_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(30, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(30, 0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(30, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(30, 0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(-20, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(-20, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Back haunches — folded under the body
                    .addAnimation("shoulder_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-60, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(-60, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("shoulder_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-60, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(-60, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(80, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(80, 0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right_lower", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(80, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(80, 0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail A — curled forward alongside body
                    .addAnimation("tail_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-70, 20, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(-70, 20, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail B — drapes down
                    .addAnimation("tail_b", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(40, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(40, 0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}
