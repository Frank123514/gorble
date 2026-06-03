package net.got.entity.client.mammoth;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Keyframe animations for {@link GotMammothModel}.
 *
 * <p>All animations reference the new model's part names from mammoth.bbmodel:
 * {@code trunk_a/b/c/d}, segmented tusks, {@code shoulder}, and the
 * richer leg geometry.
 *
 * <p>Animations:
 * <ul>
 *   <li>{@link #IDLE}   — slow breathing sway, 4-segment trunk curl, tail swing (5.0 s, looping)</li>
 *   <li>{@link #WALK}   — ponderous 4-beat plod with shoulder roll (2.0 s, looping)</li>
 *   <li>{@link #CHARGE} — thundering run, head lowered, trunk raised (1.0 s, looping)</li>
 * </ul>
 */
public final class GotMammothAnimations {

    private GotMammothAnimations() {}

    // ── IDLE ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition IDLE =
            AnimationDefinition.Builder.withLength(5.0F).looping()
                    // Body — deep slow breathing rise
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.posVec(0, 0.4F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.posVec(0, 0, 0),    AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head — very gentle nod
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(-3, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec(0, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk A — lazy side-to-side sweep
                    .addAnimation("trunk_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F,  KeyframeAnimations.degreeVec(-10, 6, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,   KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.75F,  KeyframeAnimations.degreeVec(-8, -6, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,   KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk B — amplifies A's motion
                    .addAnimation("trunk_b", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,   KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25F,  KeyframeAnimations.degreeVec(12, 4, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,   KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,   KeyframeAnimations.degreeVec(0,  0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk C — tip curls inward
                    .addAnimation("trunk_c", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(10, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec(0, 0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk D — slight tip flick
                    .addAnimation("trunk_d", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(8, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.0F,  KeyframeAnimations.degreeVec(0, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec(0, 0, 0),   AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail — slow pendulum sway
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0,  8, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(0, -8, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec(0,  8, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Shoulder — subtle sway matching body breathing
                    .addAnimation("shoulder", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5F,  KeyframeAnimations.degreeVec(-2, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.0F,  KeyframeAnimations.degreeVec(0,   0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── WALK ─────────────────────────────────────────────────────────────────

    public static final AnimationDefinition WALK =
            AnimationDefinition.Builder.withLength(2.0F).looping()
                    // Body — heavy plodding bob
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0,    0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0, 0.8F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0, 0,    0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.posVec(0, 0.8F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.posVec(0, 0,    0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head — nod with each stride
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(4, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(4, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk A — gentle forward-back swing
                    .addAnimation("trunk_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-8, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(5, 0, 0),   AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-8, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk B — follows A with slight lag
                    .addAnimation("trunk_b", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(8,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0,  0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Shoulder — rolls slightly side to side
                    .addAnimation("shoulder", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0,  2, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0, -2, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0,  2, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Tail — passive wag
                    .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0,  6, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(0, -6, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(0,  6, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Legs — ponderous 4-beat diagonal gait
                    // Front left & back right move together; front right & back left opposite
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-18, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(18,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-18, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(18,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-18, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(18,  0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(18,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-18, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(18,  0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-18, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(18,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0,   0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0F,  KeyframeAnimations.degreeVec(-18, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();

    // ── CHARGE ───────────────────────────────────────────────────────────────

    public static final AnimationDefinition CHARGE =
            AnimationDefinition.Builder.withLength(1.0F).looping()
                    // Body — powerful bounce with gallop
                    .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.0F,  KeyframeAnimations.posVec(0, 0,     0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.posVec(0, -1.2F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.posVec(0, 0,     0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75F, KeyframeAnimations.posVec(0, -1.2F, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.posVec(0, 0,     0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Head — lowered for intimidation
                    .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(18, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(18, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk A — raised and pulled back (battle-ready posture)
                    .addAnimation("trunk_a", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-40, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Trunk B — curls upward with A
                    .addAnimation("trunk_b", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-20, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Shoulder — forward thrust with momentum
                    .addAnimation("shoulder", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-5, 0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(5,  0, 0),  AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-5, 0, 0),  AnimationChannel.Interpolations.LINEAR)
                    ))
                    // Legs — fast thundering gallop
                    .addAnimation("leg_front_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-38, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-38, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-38, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_front_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-38, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-38, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .addAnimation("leg_back_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.0F,  KeyframeAnimations.degreeVec(-38, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25F, KeyframeAnimations.degreeVec(28,  0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5F,  KeyframeAnimations.degreeVec(-38, 0, 0), AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0F,  KeyframeAnimations.degreeVec(-38, 0, 0), AnimationChannel.Interpolations.LINEAR)
                    ))
                    .build();
}
