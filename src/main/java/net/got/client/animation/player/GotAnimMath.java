package net.got.client.animation.player;

import net.minecraft.util.Mth;

/**
 * Procedural ("math", not keyframe) player-limb animation curves.
 *
 * <p>Every method here is a pure function of some progress/time input to a
 * rotation in <b>radians</b> (the unit {@link net.minecraft.client.model.geom.ModelPart#xRot}
 * etc. expect) — no Minecraft entity/model state is touched in this class,
 * which keeps the actual formulas easy to read, tune, and unit-test in
 * isolation from the mixin plumbing that feeds them.
 *
 * <p>{@link GotPlayerAnimator} is the only caller; it decides *which* of
 * these to use and how to blend them, then writes the results onto the
 * real {@code ModelPart}s.
 *
 * <p><b>Kelvin's Better Player Animations pass:</b> the amplitudes and
 * secondary-axis motion below (sneak arm splay, idle breathing sway, swing
 * off-arm counter, weight-shift legs, etc.) were reverse-engineered from
 * Kelvin's Better Player Animations' GeckoLib/Bedrock keyframe JSONs
 * (walking.json, running.json, sneak_walk.json, sneak_idle.json,
 * idle.json, jump_first/second.json, landing.json, climbing.json,
 * punch_left/right.json, sword_swing_first/second.json) by taking the
 * peak-to-peak amplitude and center offset of each rotation channel per
 * bone. Only rotation channels were ported — this project's rig is
 * rotation-only by design (see class doc history), so Kelvin's
 * position/scale channels (root bob, head bounce) were intentionally left
 * out rather than bolted on as a new, untested unit (Kelvin's position
 * units aren't guaranteed to line up 1:1 with this project's
 * {@code ModelPart} pixel space). Values were then rounded and re-tuned by
 * feel against this project's existing constants rather than copied
 * verbatim, since Kelvin's rig has a different rest pose/scale than
 * vanilla's {@code HumanoidModel}.
 *
 * <p><b>Second walking/running reference pass:</b> the walk/run locomotion
 * section below was superseded again by a direct keyframe port of a
 * different, more literal walking.json/running.json (rightArm/leftArm/
 * torso/rightLeg/leftLeg bone names) — every axis is sampled straight out
 * of that file's own keyframe timeline via {@link #sampleCycle} instead of
 * being approximated as a sine wave, so this second pass is exact rather
 * than fitted. See the "Locomotion (walk / run)" section doc below for
 * specifics. Position channels were skipped for the same reason as above.
 *
 * <p><b>Third pass — smoothing/polish, no API changes:</b> every public
 * method here kept its exact signature (this is a drop-in replacement,
 * {@link GotPlayerAnimator} needed zero changes); only the curve shapes
 * feeding those signatures changed:
 * <ul>
 *   <li>{@link #stepBob} switched from {@code abs(cos(x))} to an
 *   equal-phase smooth double-frequency cosine ({@code 0.5+0.5cos(2x)}).
 *   {@code abs(cos(x))} has a sharp velocity reversal (a kink) every time
 *   it touches zero — twice a stride — which read as a tiny jolt at each
 *   foot-plant. The smooth form has the same peak/trough timing and the
 *   same 0..amplitude range but a continuous derivative throughout, so the
 *   bob eases through the bottom of each step instead of bouncing off it.</li>
 *   <li>{@link #idleArmSway} and {@link #idleBodySway} gained a small
 *   second sine term at an incommensurate frequency/phase. A single sine
 *   is a perfect metronome — obviously looping if you watch it for more
 *   than a cycle or two. Layering in a much smaller, differently-timed
 *   second wave keeps the motion just as deterministic and cheap but
 *   breaks the "always exactly the same swing" read.</li>
 *   <li>{@link #jumpLegTuck} and {@link #jumpArmFlare} switched from
 *   scaling linearly by {@code airborne} to scaling by
 *   {@link #smoothstep(float)} of it. A linear ramp has a constant
 *   velocity that then stops dead the instant {@code airborne} hits its
 *   clamp, which reads as a snap at takeoff/landing; smoothstep eases in
 *   and back out at both ends of the transition instead.</li>
 * </ul>
 *
 * <p><b>Fourth pass — real limb translation, not just rotation:</b> the
 * walk/run leg and arm swing was rotation-only — every limb pivoted
 * around a fixed hip/shoulder point like a rigid pendulum, which reads as
 * stiff no matter how good the rotation curve is, because a real stride
 * also lifts the foot and reaches it forward through space. Added
 * {@link #legPosY}/{@link #legPosZ} and {@link #armPosX}/
 * {@link #armPosY}/{@link #armPosZ}, ported the same way as the rotation
 * tables (direct keyframe port via {@link #sampleCycle}, walk/run
 * crossfaded via {@code runBlend}) but sourced from
 * {@code GotPlayerBaseAnimations.WALKING}/{@code RUNNING}'s
 * <em>position</em> channels instead of rotation — this project's own
 * reference rig, not Kelvin's, so (unlike the position channels skipped
 * elsewhere in this file over a unit-mismatch concern) these are safe to
 * trust 1:1 against this project's {@code ModelPart} pixel space. These
 * are new public methods, additive only — every existing method kept its
 * signature; {@link GotPlayerAnimator} needed new calls added (it now
 * offsets {@code ModelPart.x/y/z} in addition to the rotation fields it
 * already set) but nothing it already called changed shape.
 *
 * <p><b>Fifth pass — literal weapon-swing keyframe ports:</b> the
 * {@code SWORD} swing style's {@link #swordSlashPitch}/{@link #swordSlashYaw}/
 * {@link #swordSlashRoll} (a fitted sine approximation, like the very first
 * Kelvin's-derived pass above) is superseded for sword-family weapons by a
 * direct keyframe port of this project's own {@code sword_attack} /
 * {@code sword_attack_2} / {@code greatsword_attack} Blockbench clips —
 * same {@code sampleCycle}-style Catmull-Rom idea as the "Second
 * walking/running reference pass", but through a new non-cyclic sibling,
 * {@link #sampleAttackTimeline} (a one-shot swing doesn't loop back on
 * itself the way a gait cycle does, so the tangent handling at the ends of
 * the table differs — see that method's doc). A new {@code GREATSWORD}
 * swing style was added alongside {@code SWORD} for this project's
 * two-handed greatswords/claymores, which reuse {@code SwordItem} rather
 * than getting their own Java type (see {@link GotSwingStyle#GREATSWORD}).
 * Unlike every earlier pass in this file, these methods return a
 * {@link LimbPose} (a whole bone at once — rotation *and* position, for
 * rightArm/leftArm/body/head) rather than one axis per call, and the pose
 * they return is the *absolute* authored pose rather than a delta — see
 * the "Weapon attack keyframe ports" section doc below for why callers
 * assign rather than add it.
 */
public final class GotAnimMath {

    private GotAnimMath() {}

    /**
     * A single bone's full pose sample: rotation in radians (matching
     * {@code ModelPart.xRot/yRot/zRot}) plus translation in the model's own
     * pixel space (matching {@code ModelPart.x/y/z}). Returned by the
     * literal weapon-swing keyframe ports below (see "Weapon attack
     * keyframe ports" section) since those, unlike the rest of this file,
     * need to hand back a whole bone at once rather than one axis per call.
     */
    public record LimbPose(float xRot, float yRot, float zRot, float x, float y, float z) {}

    /**
     * Left-right mirror of a {@link LimbPose}, for playing a right-handed
     * source animation on the left arm (or vice versa) when the attacking
     * hand isn't the one the source was authored for. Pitch (xRot) and the
     * vertical/depth position (y/z) are symmetric across the body's
     * left-right axis, so they carry over unchanged; yaw/roll (yRot/zRot)
     * and sideways position (x) flip sign.
     */
    public static LimbPose mirrorPose(LimbPose pose) {
        return new LimbPose(pose.xRot(), -pose.yRot(), -pose.zRot(), -pose.x(), pose.y(), pose.z());
    }

    /** Vanilla's own walk-gait frequency constant — reused so our gait still lines up with footstep sounds/particles. */
    private static final float GAIT_FREQUENCY = 0.6662F;

    private static float rad(float degrees) {
        return degrees * Mth.DEG_TO_RAD;
    }

    /** Smoothly ramps {@code current} toward {@code target}, independent of framerate-ish (call once per extraction). */
    public static float approach(float current, float target, float speed) {
        return Mth.lerp(Mth.clamp(speed, 0.0F, 1.0F), current, target);
    }

    /** Classic Hermite smoothstep: zero velocity at both x=0 and x=1, unlike a raw linear ramp which stops dead at the clamp. */
    private static float smoothstep(float x) {
        x = Mth.clamp(x, 0.0F, 1.0F);
        return x * x * (3.0F - 2.0F * x);
    }

    // ── Locomotion (walk / run) ─────────────────────────────────────────────
    //
    // This section is a direct keyframe port of a second walking.json /
    // running.json reference pair (rightArm/leftArm/torso/rightLeg/leftLeg
    // bone names — a different, more literal source than the earlier
    // Kelvin's Better Player Animations pass above). Rather than fitting a
    // sine curve to the amplitude like the rest of this file, every axis
    // below is sampled straight out of that JSON's own keyframe timeline
    // via {@link #sampleCycle}, so the *shape* of each curve (not just its
    // peak amplitude) matches the source exactly. Position/scale channels
    // are still skipped for the same rig-mismatch reason documented above.
    //
    // Both reference files repeat every 6 authored seconds (walking) / 1.5
    // authored seconds (running) — walking.json's raw 12s length is just
    // that 6s cycle written out twice back-to-back, and leftArm's missing
    // keyframe at the 6s mark is filled in here from its (identical)
    // t=0 value, confirmed by the fact every other 6s-offset pair in that
    // channel (2↔8, 5↔11) matches exactly. One full 2π revolution of this
    // project's existing walk-phase driver (walkPos * GAIT_FREQUENCY, the
    // same one the old sine curves used, so footstep-sound sync is
    // unchanged) maps to one full lap of whichever table is sampled.

    /** Turns the existing walk-phase driver into a 0..1 fraction of one gait cycle. */
    private static float cyclePhase(float walkPos) {
        float cycles = (walkPos * GAIT_FREQUENCY) / (2.0F * Mth.PI);
        return cycles - Mth.floor(cycles);
    }

    /**
     * Cyclic Catmull-Rom (cubic Hermite, finite-difference tangents) lookup
     * into a (time, value-in-degrees) keyframe table, {@code period}
     * seconds long, given a 0..1 phase fraction.
     *
     * <p>This used to be plain piecewise-linear interpolation, which is
     * exactly what it sounds like: straight lines between keyframes, with
     * velocity snapping instantly to a new direction/speed at every
     * keyframe. That's barely noticeable when a table is dense, but the run
     * tables above only carry 3 points across a 1.5s cycle, so each stride
     * was effectively a sharp triangle wave — a hard, instant reversal right
     * at the peak of every swing. Catmull-Rom fixes that by giving each
     * point a tangent derived from its neighbors (see {@link #wrappedTime}/
     * {@link #wrappedValue}) and blending in with a cubic Hermite curve, so
     * motion eases through every keyframe with continuous velocity instead
     * of bouncing off it. It still passes through the exact same authored
     * values at the exact same times — only the motion *between* them
     * changes.
     *
     * <p>The table's first and last entries are assumed equal (that's what
     * makes the loop periodic), so there are {@code times.length - 1}
     * unique control points; tangent lookups wrap around that loop using
     * period-shifted neighbor times/values, so pace carries smoothly across
     * the seam instead of kinking there too.
     */
    private static float sampleCycle(float phase01, float[] timesSeconds, float[] valuesDeg, float period) {
        int segCount = timesSeconds.length - 1; // unique control points; index segCount duplicates index 0
        float time = phase01 * period;

        int i = segCount - 1;
        for (int c = 0; c < segCount; c++) {
            if (time >= timesSeconds[c] && time <= timesSeconds[c + 1]) {
                i = c;
                break;
            }
        }

        float t0 = timesSeconds[i], t1 = timesSeconds[i + 1];
        float v0 = valuesDeg[i], v1 = valuesDeg[i + 1];
        float dt = t1 - t0;
        if (dt <= 1.0e-5F) {
            return v0;
        }

        float tPrev = wrappedTime(timesSeconds, i - 1, period, segCount);
        float vPrev = wrappedValue(valuesDeg, i - 1, segCount);
        float tNext = wrappedTime(timesSeconds, i + 2, period, segCount);
        float vNext = wrappedValue(valuesDeg, i + 2, segCount);

        // Finite-difference (Catmull-Rom) tangents, each scaled to *this*
        // segment's own duration so pace is preserved even when neighboring
        // segments have very different lengths.
        float m0 = (v1 - vPrev) / (t1 - tPrev) * dt;
        float m1 = (vNext - v0) / (tNext - t0) * dt;

        float u = (time - t0) / dt;
        float u2 = u * u, u3 = u2 * u;
        float h00 = 2.0F * u3 - 3.0F * u2 + 1.0F;
        float h10 = u3 - 2.0F * u2 + u;
        float h01 = -2.0F * u3 + 3.0F * u2;
        float h11 = u3 - u2;
        return h00 * v0 + h10 * m0 + h01 * v1 + h11 * m1;
    }

    /** Time of control point {@code k} (may be outside [0, segCount]), unwrapped by whole periods so tangents stay continuous across the loop seam. */
    private static float wrappedTime(float[] times, int k, float period, int segCount) {
        int wraps = Math.floorDiv(k, segCount);
        int idx = k - wraps * segCount;
        return times[idx] + wraps * period;
    }

    /** Value of control point {@code k} (may be outside [0, segCount]), wrapping the index into the unique-point range. */
    private static float wrappedValue(float[] values, int k, int segCount) {
        int wraps = Math.floorDiv(k, segCount);
        int idx = k - wraps * segCount;
        return values[idx];
    }

    /**
     * Non-cyclic counterpart to {@link #sampleCycle}: a Catmull-Rom lookup
     * into a one-shot (time, value-in-degrees) keyframe table, used for the
     * weapon-swing keyframe ports below instead of a looping gait. Two
     * differences from {@link #sampleCycle}:
     * <ul>
     *   <li>Tangents at the first/last control point are <i>clamped</i>
     *   (the missing neighbor is the endpoint itself, i.e. a one-sided
     *   secant slope) rather than wrapped around a period, since a sword
     *   swing doesn't loop back into itself the way a stride does.</li>
     *   <li>{@code t01} is a 0..1 fraction of the *authored* clip
     *   ({@code animLength}, matching the source Blockbench file's own
     *   timing), and any {@code t01} past the last keyframe holds that
     *   keyframe's value rather than extrapolating — matching how
     *   Blockbench/Bedrock animations behave once their last keyframe has
     *   played, since several of the source clips' last keyframes land
     *   before the clip's nominal end (the rest is just a held pose).</li>
     * </ul>
     */
    private static float sampleAttackTimeline(float t01, float[] timesSeconds, float[] valuesDeg, float animLength) {
        float time = Mth.clamp(t01, 0.0F, 1.0F) * animLength;
        int last = timesSeconds.length - 1;
        if (time >= timesSeconds[last]) {
            return valuesDeg[last];
        }

        int i = last - 1;
        for (int c = 0; c < last; c++) {
            if (time >= timesSeconds[c] && time <= timesSeconds[c + 1]) {
                i = c;
                break;
            }
        }

        float t0 = timesSeconds[i], t1 = timesSeconds[i + 1];
        float v0 = valuesDeg[i], v1 = valuesDeg[i + 1];
        float dt = t1 - t0;
        if (dt <= 1.0e-5F) {
            return v0;
        }

        // Clamped tangents: at either end of the table, the "missing"
        // neighbor is just the boundary point itself, which collapses the
        // Catmull-Rom tangent to the plain secant slope of the boundary
        // segment instead of reaching past the authored data.
        float tPrev = i > 0 ? timesSeconds[i - 1] : t0;
        float vPrev = i > 0 ? valuesDeg[i - 1] : v0;
        float tNext = i < last - 1 ? timesSeconds[i + 2] : t1;
        float vNext = i < last - 1 ? valuesDeg[i + 2] : v1;

        float m0 = (v1 - vPrev) / Math.max(t1 - tPrev, 1.0e-5F) * dt;
        float m1 = (vNext - v0) / Math.max(tNext - t0, 1.0e-5F) * dt;

        float u = (time - t0) / dt;
        float u2 = u * u, u3 = u2 * u;
        float h00 = 2.0F * u3 - 3.0F * u2 + 1.0F;
        float h10 = u3 - 2.0F * u2 + u;
        float h01 = -2.0F * u3 + 3.0F * u2;
        float h11 = u3 - u2;
        return h00 * v0 + h10 * m0 + h01 * v1 + h11 * m1;
    }

    private static final float WALK_PERIOD = 6.0F;
    private static final float RUN_PERIOD = 1.5F;

    private static final float[] WALK_TORSO_T = {0F, 2F, 3F, 4F, 6F};
    private static final float[] WALK_TORSO_Y = {0F, 5F, 0F, -5F, 0F};

    private static final float[] WALK_RARM_T = {0F, 2F, 3F, 5F, 6F};
    private static final float[] WALK_RARM_X = {0F, -40.67815F, 0F, 40.24258F, 0F};
    private static final float[] WALK_RARM_Y = {0F, -9.54385F, 0F, 5.73853F, 0F};
    private static final float[] WALK_RARM_Z = {2.5F, 10.61019F, 2.5F, 2.33711F, 2.5F};

    private static final float[] WALK_LARM_T = {0F, 2F, 5F, 6F};
    private static final float[] WALK_LARM_X = {-0.22F, 40.24258F, -40.67815F, -0.22F};
    private static final float[] WALK_LARM_Y = {1.9F, -5.7385F, 9.5438F, 1.9F};
    private static final float[] WALK_LARM_Z = {-6.47F, -2.3371F, -10.6102F, -6.47F};

    private static final float[] WALK_RLEG_T = {0F, 2F, 3F, 4F, 5F, 6F};
    private static final float[] WALK_RLEG_X = {0F, 20F, 0F, -25F, -7.5F, 0F};

    private static final float[] WALK_LLEG_T = {0F, 1F, 2F, 3F, 5F, 6F};
    private static final float[] WALK_LLEG_X = {0F, -25F, -7.5F, 0F, 20F, 0F};

    private static final float[] RUN_TORSO_T = {0F, 0.75F, 1.5F};
    private static final float[] RUN_TORSO_Y = {7.3854F, -7.3854F, 7.3854F};
    private static final float[] RUN_TORSO_Z = {1.3096F, -1.30962F, 1.3096F};
    private static final float RUN_TORSO_X = 10.08453F; // constant forward lean, doesn't vary through the cycle

    private static final float[] RUN_RARM_T = {0F, 0.75F, 1.5F};
    private static final float[] RUN_RARM_X = {69.55418F, -72.48435F, 69.55418F};
    private static final float[] RUN_RARM_Y = {-11.7351F, -2.3842F, -11.7351F};
    private static final float[] RUN_RARM_Z = {4.3361F, -0.7522F, 4.3361F};

    private static final float[] RUN_LARM_T = {0F, 0.75F, 1.5F};
    private static final float[] RUN_LARM_X = {-72.48435F, 69.55418F, -72.48435F};
    private static final float[] RUN_LARM_Y = {2.38424F, 11.73507F, 2.38424F};
    private static final float[] RUN_LARM_Z = {0.75218F, -4.3361F, 0.75218F};

    private static final float[] RUN_RLEG_T = {0F, 0.5F, 0.75F, 1.5F};
    private static final float[] RUN_RLEG_X = {-32.5F, -2.5F, 75F, -32.5F};

    private static final float[] RUN_LLEG_T = {0F, 0.75F, 1.25F, 1.5F};
    private static final float[] RUN_LLEG_X = {75F, -32.5F, -2.5F, 75F};

    // ── Locomotion position (leg/arm translation) ───────────────────────────
    //
    // Ported from GotPlayerBaseAnimations.WALKING/RUNNING's position
    // channels (this project's own reference rig, not Kelvin's — so unlike
    // the position channels skipped elsewhere in this file, these units
    // are guaranteed to line up 1:1 with this project's ModelPart pixel
    // space). This is what turns the leg/arm swing from a rigid pendulum
    // hinging around a fixed pivot into an actual step: the leg lifts
    // (Y) and reaches forward/back (Z) as it swings, instead of just
    // rotating in place. Legs have no meaningful X translation in the
    // source (always 0), so only Y/Z tables exist for them; arms use
    // all three axes. Same {@code cyclePhase}/{@code sampleCycle}
    // machinery as the rotation tables above, just with their own
    // (unrelated) period constants — the period only has to be internally
    // consistent with a table's own time values, it doesn't need to match
    // the rotation tables' period since both are driven by the same 0..1
    // phase fraction of a stride.

    private static final float WALK_POS_PERIOD = 1.2247F;
    private static final float RUN_POS_PERIOD = 0.6124F;

    private static final float[] WALK_RLEG_POS_T = {0F, 0.3061F, 0.6122F, 0.8163F, 0.9184F, 1.1224F, 1.2247F};
    private static final float[] WALK_RLEG_POS_Y = {-1.5F, 0F, -1.5F, 1.35F, 2.44F, 0.7F, -1.5F};
    private static final float[] WALK_RLEG_POS_Z = {-2F, 0F, 2F, -1.47F, -3F, -2.63F, -2F};

    private static final float[] WALK_LLEG_POS_T = {0F, 0.2041F, 0.3061F, 0.5102F, 0.6122F, 0.9184F, 1.2247F};
    private static final float[] WALK_LLEG_POS_Y = {-1.5F, 1.35F, 2.44F, 0.7F, -1.5F, 0F, -1.5F};
    private static final float[] WALK_LLEG_POS_Z = {2F, -1.47F, -3F, -2.63F, -2F, 0F, 2F};

    private static final float[] WALK_RARM_POS_T = {0F, 0.3061F, 0.6122F, 0.9184F, 1.2247F};
    private static final float[] WALK_RARM_POS_X = {-0.25F, 0.25F, -0.5F, 0.75F, -0.25F};
    private static final float[] WALK_RARM_POS_Y = {-1.5F, 0F, -1F, 0F, -1.5F};
    private static final float[] WALK_RARM_POS_Z = {0F, 0F, -1F, 0F, 0F};

    private static final float[] WALK_LARM_POS_T = {0F, 0.3061F, 0.6122F, 0.9184F, 1.2247F};
    private static final float[] WALK_LARM_POS_X = {-0.5F, -0.75F, 0.25F, -0.25F, -0.5F};
    private static final float[] WALK_LARM_POS_Y = {-1F, 0F, -1.5F, 0F, -1F};
    private static final float[] WALK_LARM_POS_Z = {-1F, 0F, 1F, 0F, -1F};

    private static final float[] RUN_RLEG_POS_T = {0F, 0.15F, 0.3F, 0.4F, 0.45F, 0.55F, 0.6124F};
    private static final float[] RUN_RLEG_POS_Y = {-1.5F, 0F, -1.5F, 1.35F, 2.44F, 0.7F, -1.5F};
    private static final float[] RUN_RLEG_POS_Z = {-2F, 0F, 2F, -1.47F, -3F, -2.63F, -2F};

    private static final float[] RUN_LLEG_POS_T = {0F, 0.1F, 0.15F, 0.25F, 0.3F, 0.45F, 0.6124F};
    private static final float[] RUN_LLEG_POS_Y = {-1.5F, 1.35F, 2.44F, 0.7F, -1.5F, 0F, -1.5F};
    private static final float[] RUN_LLEG_POS_Z = {2F, -1.47F, -3F, -2.63F, -2F, 0F, 2F};

    private static final float[] RUN_RARM_POS_T = {0F, 0.15F, 0.3F, 0.45F, 0.6124F};
    private static final float[] RUN_RARM_POS_X = {-0.25F, 0.25F, -0.5F, 0.75F, -0.25F};
    private static final float[] RUN_RARM_POS_Y = {-1.5F, 0F, -1F, 0F, -1.5F};
    private static final float[] RUN_RARM_POS_Z = {0F, 0F, -1F, 0F, 0F};

    private static final float[] RUN_LARM_POS_T = {0F, 0.15F, 0.3F, 0.45F, 0.6124F};
    private static final float[] RUN_LARM_POS_X = {-0.5F, -0.75F, 0.25F, -0.25F, -0.5F};
    private static final float[] RUN_LARM_POS_Y = {-1F, 0F, -1.5F, 0F, -1F};
    private static final float[] RUN_LARM_POS_Z = {-1F, 0F, 1F, 0F, -1F};

    /** Leg vertical lift (translation, not rotation) as it swings through the stride — this is what makes the foot actually leave the ground instead of just pivoting. */
    public static float legPosY(float walkPos, float intensity, float runBlend, boolean rightSide) {
        float phase = cyclePhase(walkPos);
        float walkVal = sampleCycle(phase, rightSide ? WALK_RLEG_POS_T : WALK_LLEG_POS_T, rightSide ? WALK_RLEG_POS_Y : WALK_LLEG_POS_Y, WALK_POS_PERIOD);
        float runVal = sampleCycle(phase, rightSide ? RUN_RLEG_POS_T : RUN_LLEG_POS_T, rightSide ? RUN_RLEG_POS_Y : RUN_LLEG_POS_Y, RUN_POS_PERIOD);
        return Mth.lerp(runBlend, walkVal, runVal) * intensity;
    }

    /** Leg forward/back reach (translation) — the leg physically steps forward in space rather than swinging in place around a fixed hip pivot. */
    public static float legPosZ(float walkPos, float intensity, float runBlend, boolean rightSide) {
        float phase = cyclePhase(walkPos);
        float walkVal = sampleCycle(phase, rightSide ? WALK_RLEG_POS_T : WALK_LLEG_POS_T, rightSide ? WALK_RLEG_POS_Z : WALK_LLEG_POS_Z, WALK_POS_PERIOD);
        float runVal = sampleCycle(phase, rightSide ? RUN_RLEG_POS_T : RUN_LLEG_POS_T, rightSide ? RUN_RLEG_POS_Z : RUN_LLEG_POS_Z, RUN_POS_PERIOD);
        return Mth.lerp(runBlend, walkVal, runVal) * intensity;
    }

    /** Arm sideways drift (translation) through the swing. */
    public static float armPosX(float walkPos, float intensity, float runBlend, boolean rightSide) {
        float phase = cyclePhase(walkPos);
        float walkVal = sampleCycle(phase, rightSide ? WALK_RARM_POS_T : WALK_LARM_POS_T, rightSide ? WALK_RARM_POS_X : WALK_LARM_POS_X, WALK_POS_PERIOD);
        float runVal = sampleCycle(phase, rightSide ? RUN_RARM_POS_T : RUN_LARM_POS_T, rightSide ? RUN_RARM_POS_X : RUN_LARM_POS_X, RUN_POS_PERIOD);
        return Mth.lerp(runBlend, walkVal, runVal) * intensity;
    }

    /** Arm vertical lift (translation) through the swing, same idea as {@link #legPosY} but for the arm. */
    public static float armPosY(float walkPos, float intensity, float runBlend, boolean rightSide) {
        float phase = cyclePhase(walkPos);
        float walkVal = sampleCycle(phase, rightSide ? WALK_RARM_POS_T : WALK_LARM_POS_T, rightSide ? WALK_RARM_POS_Y : WALK_LARM_POS_Y, WALK_POS_PERIOD);
        float runVal = sampleCycle(phase, rightSide ? RUN_RARM_POS_T : RUN_LARM_POS_T, rightSide ? RUN_RARM_POS_Y : RUN_LARM_POS_Y, RUN_POS_PERIOD);
        return Mth.lerp(runBlend, walkVal, runVal) * intensity;
    }

    /** Arm forward/back reach (translation) through the swing, same idea as {@link #legPosZ} but for the arm. */
    public static float armPosZ(float walkPos, float intensity, float runBlend, boolean rightSide) {
        float phase = cyclePhase(walkPos);
        float walkVal = sampleCycle(phase, rightSide ? WALK_RARM_POS_T : WALK_LARM_POS_T, rightSide ? WALK_RARM_POS_Z : WALK_LARM_POS_Z, WALK_POS_PERIOD);
        float runVal = sampleCycle(phase, rightSide ? RUN_RARM_POS_T : RUN_LARM_POS_T, rightSide ? RUN_RARM_POS_Z : RUN_LARM_POS_Z, RUN_POS_PERIOD);
        return Mth.lerp(runBlend, walkVal, runVal) * intensity;
    }

    /** Leg pitch (xRot). {@code intensity} 0 (standing) to 1 (full stride); {@code runBlend} 0..1 crossfades the walk table into the run table. */
    public static float legSwing(float walkPos, float intensity, float runBlend, boolean rightSide) {
        float phase = cyclePhase(walkPos);
        float walkDeg = sampleCycle(phase, rightSide ? WALK_RLEG_T : WALK_LLEG_T, rightSide ? WALK_RLEG_X : WALK_LLEG_X, WALK_PERIOD);
        float runDeg = sampleCycle(phase, rightSide ? RUN_RLEG_T : RUN_LLEG_T, rightSide ? RUN_RLEG_X : RUN_LLEG_X, RUN_PERIOD);
        return rad(Mth.lerp(runBlend, walkDeg, runDeg)) * intensity;
    }

    /** Arm pitch (xRot). */
    public static float armSwing(float walkPos, float intensity, float runBlend, boolean rightSide) {
        float phase = cyclePhase(walkPos);
        float walkDeg = sampleCycle(phase, rightSide ? WALK_RARM_T : WALK_LARM_T, rightSide ? WALK_RARM_X : WALK_LARM_X, WALK_PERIOD);
        float runDeg = sampleCycle(phase, rightSide ? RUN_RARM_T : RUN_LARM_T, rightSide ? RUN_RARM_X : RUN_LARM_X, RUN_PERIOD);
        return rad(Mth.lerp(runBlend, walkDeg, runDeg)) * intensity;
    }

    /** Arm yaw (yRot). */
    public static float armYaw(float walkPos, float intensity, float runBlend, boolean rightSide) {
        float phase = cyclePhase(walkPos);
        float walkDeg = sampleCycle(phase, rightSide ? WALK_RARM_T : WALK_LARM_T, rightSide ? WALK_RARM_Y : WALK_LARM_Y, WALK_PERIOD);
        float runDeg = sampleCycle(phase, rightSide ? RUN_RARM_T : RUN_LARM_T, rightSide ? RUN_RARM_Y : RUN_LARM_Y, RUN_PERIOD);
        return rad(Mth.lerp(runBlend, walkDeg, runDeg)) * intensity;
    }

    /** Arm roll (zRot). */
    public static float armRoll(float walkPos, float intensity, float runBlend, boolean rightSide) {
        float phase = cyclePhase(walkPos);
        float walkDeg = sampleCycle(phase, rightSide ? WALK_RARM_T : WALK_LARM_T, rightSide ? WALK_RARM_Z : WALK_LARM_Z, WALK_PERIOD);
        float runDeg = sampleCycle(phase, rightSide ? RUN_RARM_T : RUN_LARM_T, rightSide ? RUN_RARM_Z : RUN_LARM_Z, RUN_PERIOD);
        return rad(Mth.lerp(runBlend, walkDeg, runDeg)) * intensity;
    }

    /**
     * Torso forward lean (xRot). The walk table holds this at a flat 0° and
     * only the run table leans forward (a constant 10.08° through the whole
     * cycle, not an oscillation) — kept at that literal value since it's
     * already well inside the safe range found while fixing the earlier
     * torso/leg disconnect (see {@link #climbBodyPitch}).
     */
    public static float torsoPitch(float intensity, float runBlend) {
        return rad(Mth.lerp(runBlend, 0.0F, RUN_TORSO_X)) * intensity;
    }

    /** Torso yaw twist (yRot), shoulders counter-rotating against the stride. */
    public static float torsoTwist(float walkPos, float intensity, float runBlend) {
        float phase = cyclePhase(walkPos);
        float walkDeg = sampleCycle(phase, WALK_TORSO_T, WALK_TORSO_Y, WALK_PERIOD);
        float runDeg = sampleCycle(phase, RUN_TORSO_T, RUN_TORSO_Y, RUN_PERIOD);
        return rad(Mth.lerp(runBlend, walkDeg, runDeg)) * intensity;
    }

    /** Torso roll (zRot). The walk table has no z motion at all (flat 0°); only running rolls the torso. */
    public static float torsoRoll(float walkPos, float intensity, float runBlend) {
        float phase = cyclePhase(walkPos);
        float runDeg = sampleCycle(phase, RUN_TORSO_T, RUN_TORSO_Z, RUN_PERIOD);
        return rad(Mth.lerp(runBlend, 0.0F, runDeg)) * intensity;
    }

    public static float stepBob(float walkPos, float intensity, float runBlend) {
        float amplitude = Mth.lerp(runBlend, 0.03F, 0.08F);
        // Same peak timing and 0..amplitude range as the old abs(cos(x)),
        // but continuous velocity through the zero-crossings — see the
        // "Third pass" class doc for why this replaced abs(cos(x)).
        float phase = walkPos * GAIT_FREQUENCY;
        return (0.5F + 0.5F * Mth.cos(2.0F * phase)) * amplitude * intensity;
    }

    // ── Idle sway (standing still) ──────────────────────────────────────────
    // Kelvin's idle.json is a slow ~2s breathing loop with tiny arm-roll
    // drift and a fixed relaxed leg turnout; ported here as continuous
    // sine/constant terms (rather than a literal 2s keyframe loop) so it
    // blends seamlessly with the walk-cycle math above via intensity.

    private static final float IDLE_FREQUENCY = 0.05F;

    /** Subtle slow arm roll "breathing" drift while standing still. Caller negates for the opposite arm. */
    public static float idleArmSway(float ageInTicks) {
        float primary = rad(2.2F) * Mth.sin(ageInTicks * IDLE_FREQUENCY);
        // Small, incommensurate-frequency second wave so the idle drift
        // doesn't read as a perfect metronome loop — see "Third pass" doc.
        float secondary = rad(0.6F) * Mth.sin(ageInTicks * IDLE_FREQUENCY * 2.7F + 1.3F);
        return primary + secondary;
    }

    /** Relaxed standing leg turnout — a fixed small splay, not an oscillation. */
    public static float idleLegSplay(boolean rightSide) {
        return rightSide ? rad(3.5F) : -rad(3.5F);
    }

    /** Very subtle idle torso yaw drift, half the frequency of the arm sway so the two don't lock in phase. */
    public static float idleBodySway(float ageInTicks) {
        float primary = rad(1.5F) * Mth.sin(ageInTicks * IDLE_FREQUENCY * 0.5F);
        float secondary = rad(0.4F) * Mth.sin(ageInTicks * IDLE_FREQUENCY * 1.9F + 0.7F);
        return primary + secondary;
    }

    // ── Sneak ────────────────────────────────────────────────────────────────

    public static float sneakBodyPitch() {
        return rad(14.0F);
    }

    public static float sneakArmForward() {
        return rad(10.0F);
    }

    /**
     * Tighter, forward-biased leg gait while crouched, replacing the
     * normal walk leg swing — Kelvin's sneak_walk.json legs swing a
     * shorter ~16° arc mostly forward of rest rather than symmetric
     * front/back like a regular stride.
     */
    public static float sneakLegSwing(float walkPos, float intensity) {
        return rad(16.0F) * Mth.cos(walkPos * GAIT_FREQUENCY) * intensity - rad(7.0F) * intensity;
    }

    /**
     * Arms held slightly out from the body for balance while sneaking —
     * Kelvin's sneak_idle/sneak_walk right_arm.z / left_arm.z sit around
     * +11°/-10° at rest. Caller negates/adjusts sign per side as needed.
     */
    public static float sneakArmSideways(boolean rightSide) {
        return rightSide ? rad(11.0F) : -rad(10.0F);
    }

    /** Torso twists more while sneak-walking than at a normal walk (Kelvin: ~9.5° vs ~7.5°). */
    public static float sneakTorsoTwist(float walkPos, float intensity) {
        return rad(9.5F) * Mth.sin(walkPos * GAIT_FREQUENCY) * intensity;
    }

    public static float sneakTorsoRoll(float walkPos, float intensity) {
        return rad(3.0F) * Mth.cos(walkPos * GAIT_FREQUENCY) * intensity;
    }

    // ── Jump / airborne ──────────────────────────────────────────────────────

    /** Legs tuck slightly up-and-back the instant a player leaves the ground, relaxing back down as {@code airborne} fades. */
    public static float jumpLegTuck(float airborne) {
        return -rad(24.0F) * smoothstep(airborne);
    }

    /** Arms drift outward a little for "balance" while airborne. */
    public static float jumpArmFlare(float airborne) {
        return rad(16.0F) * smoothstep(airborne);
    }

    /**
     * A brief windmill-reach hump through the takeoff/landing transition
     * (peaks mid-transition, relaxes at both full-ground and full-apex),
     * echoing the big arm arc Kelvin's jump_first/jump_second bake into
     * the actual leap. Since {@code airborneProgress} is a single smoothed
     * 0..1 value used for both rising and settling, this naturally plays
     * once on takeoff and once on landing without needing extra state.
     */
    public static float jumpArmReach(float airborne) {
        return rad(30.0F) * Mth.sin(Mth.clamp(airborne, 0.0F, 1.0F) * Mth.PI);
    }

    /** Torso pitches forward slightly through the jump transition, matching Kelvin's torso easing out of its rest lean mid-leap. */
    public static float jumpBodyLean(float airborne) {
        return rad(4.0F) * Mth.sin(Mth.clamp(airborne, 0.0F, 1.0F) * Mth.PI);
    }

    // ── Climbing (ladders/vines/scaffolding) ────────────────────────────────

    /**
     * Alternating reach-and-pull climb cycle. Right arm/left leg move
     * together, opposite left arm/right leg — like a real ladder climb
     * rather than vanilla's plain walk-swing on ladders. Kelvin's
     * climbing.json holds a static arms-raised grip with a pronounced
     * ~35-47° torso lean into the wall, but that lean is on their
     * hip-pivoted torso bone; kept modest here (see {@link #climbBodyPitch}).
     */
    public static float climbArmReach(float ageInTicks, boolean rightSide) {
        float phase = rightSide ? 0.0F : (float) Math.PI;
        return rad(50.0F) * Mth.sin(ageInTicks * 0.5F + phase) - rad(22.0F);
    }

    public static float climbLegPush(float ageInTicks, boolean rightSide) {
        // legs move opposite the same-side arm for a natural cross-climb pattern
        float phase = rightSide ? (float) Math.PI : 0.0F;
        return rad(35.0F) * Mth.sin(ageInTicks * 0.5F + phase);
    }

    public static float climbBodyPitch() {
        return rad(10.0F);
    }

    // ── Attack swings (punch / weapon), 0..1 progress ───────────────────────

    /**
     * Shapes a raw 0..1 swing progress into an eased "fast out, slower
     * recovery" curve shared by all swing styles, just with different
     * amplitudes/axes layered on top by the style-specific methods below.
     */
    private static float easedSwing(float t) {
        t = Mth.clamp(t, 0.0F, 1.0F);
        // fast rise to the strike (first 35%), slower relaxed return
        if (t < 0.35F) {
            float u = t / 0.35F;
            return Mth.sin(u * (Mth.PI / 2.0F));
        } else {
            float u = (t - 0.35F) / 0.65F;
            return Mth.cos(u * (Mth.PI / 2.0F));
        }
    }

    public static float punchPitch(float t) {
        return -rad(65.0F) * easedSwing(t);
    }

    /** Small forward hook as the punch lands, per Kelvin's punch arm.y sweeping well past straight-forward at the strike. */
    public static float punchYaw(float t, boolean rightSide) {
        float sign = rightSide ? -1.0F : 1.0F;
        return sign * rad(12.0F) * easedSwing(t);
    }

    /**
     * Superseded by the literal {@link #sword1RightArm}/{@link #sword2RightArm}
     * keyframe ports (see the "Weapon attack keyframe ports" section below)
     * — {@code GotPlayerAnimator} no longer calls this for {@code SWORD}.
     * Left in place (not deleted) as a lightweight fallback shape in case
     * the keyframe port ever needs to be bypassed.
     */
    public static float swordSlashPitch(float t) {
        return -rad(95.0F) * easedSwing(t);
    }

    /** Horizontal component of the sword slash, arcing the arm across the body. Superseded, see {@link #swordSlashPitch}. */
    public static float swordSlashYaw(float t, boolean rightSide) {
        float sign = rightSide ? -1.0F : 1.0F;
        return sign * rad(40.0F) * easedSwing(t);
    }

    /** Blade roll through the slash. Superseded, see {@link #swordSlashPitch}. */
    public static float swordSlashRoll(float t, boolean rightSide) {
        float sign = rightSide ? 1.0F : -1.0F;
        return sign * rad(30.0F) * easedSwing(t);
    }

    public static float axeChopPitch(float t) {
        // deeper wind-up (negative = raised overhead) before the strike than a sword
        t = Mth.clamp(t, 0.0F, 1.0F);
        if (t < 0.3F) {
            return -rad(110.0F) * (t / 0.3F);
        } else {
            float u = (t - 0.3F) / 0.7F;
            return Mth.lerp(Mth.sin(u * (Mth.PI / 2.0F)), -rad(110.0F), rad(15.0F));
        }
    }

    public static float toolStrikePitch(float t) {
        return -rad(60.0F) * easedSwing(t);
    }

    public static float genericSwingPitch(float t) {
        return -rad(45.0F) * easedSwing(t);
    }

    /** Slight torso rotation following the swinging arm, sold across all styles. */
    public static float swingBodyFollow(float t, boolean rightSide) {
        float sign = rightSide ? -1.0F : 1.0F;
        return sign * rad(15.0F) * easedSwing(t);
    }

    /**
     * Torso pitches forward on impact — Kelvin's swing torso.x snaps from
     * a small wind-up lean to ~33-36° forward right at the strike frame.
     */
    public static float swingBodyPitchSnap(float t) {
        return rad(6.0F) * easedSwing(t);
    }

    /**
     * The non-swinging arm pulls back for counter-balance/guard, mirroring
     * how dramatically Kelvin's off-arm moves opposite the striking arm
     * during punches and sword swings.
     */
    public static float offArmCounterPitch(float t) {
        return rad(22.0F) * easedSwing(t);
    }

    public static float offArmCounterRoll(float t, boolean offArmIsRight) {
        float sign = offArmIsRight ? 1.0F : -1.0F;
        return sign * rad(18.0F) * easedSwing(t);
    }

    /** Small same-side-as-swing weight-shift through the legs, per Kelvin's leg channels moving through every punch/sword swing. */
    public static float swingLegWeightShift(float t, boolean rightSide) {
        float sign = rightSide ? -1.0F : 1.0F;
        return sign * rad(10.0F) * easedSwing(t);
    }

    // Keyframe tables sourced from this project's model_animation.json —
    // animation.got_player.sword_attack / sword_attack_2 / greatsword_attack.

    private static final float SWORD1_LENGTH = 1.125F;
    private static final float[] SWORD1_RARM_ROT_T = {0F, 0.125F, 0.25F, 0.3125F, 0.375F, 0.4375F, 0.5F, 0.5625F, 0.625F, 0.75F, 1F};
    private static final float[] SWORD1_RARM_ROT_X = {0F, 19.11F, -45F, -85F, -160.54F, -198.96F, -112.95F, -6.12F, -35.64F, -30.2404F, -30.2404F};
    private static final float[] SWORD1_RARM_ROT_Y = {0F, 22.68F, 83.42F, 100F, 69.49F, 25.95F, 10.32F, -1F, -8.61F, -2.9324F, -2.9324F};
    private static final float[] SWORD1_RARM_ROT_Z = {0F, -8.09F, 27.45F, 30F, -30.88F, -91.93F, -76.26F, -55.2F, -70.48F, -70.6404F, -70.6404F};
    private static final float[] SWORD1_RARM_POS_T = {0F, 0.125F, 0.3125F, 0.4375F, 0.5F, 0.5625F, 0.625F, 0.75F, 1F};
    private static final float[] SWORD1_RARM_POS_X = {0F, 0.07F, 0F, 1.62F, 0.31F, 2F, 1.99F, 1.97F, 1.98F};
    private static final float[] SWORD1_RARM_POS_Y = {0F, -1.91F, 2F, 0.15F, -0.42F, -1F, -0.59F, -0.78F, -1.38F};
    private static final float[] SWORD1_RARM_POS_Z = {0F, 1.36F, 3F, 0.87F, -3.06F, -3F, -2.53F, -2.6F, -2.33F};
    private static final float[] SWORD1_LARM_ROT_T = {0F, 0.125F, 0.25F, 0.375F, 0.5F, 0.625F, 0.75F, 0.875F};
    private static final float[] SWORD1_LARM_ROT_X = {0F, 7.8F, -7.4F, -48.08F, -54.06F, -0.49F, 12.12F, 19.42F};
    private static final float[] SWORD1_LARM_ROT_Y = {0F, -9.06F, 0.69F, 32.43F, 10.73F, -14.63F, -7.9F, 0.93F};
    private static final float[] SWORD1_LARM_ROT_Z = {0F, -1.98F, -16.35F, -37.75F, -60.39F, -77.02F, -70.1F, -58.94F};
    private static final float[] SWORD1_LARM_POS_T = {0F, 0.125F, 0.375F, 0.5625F, 0.625F, 0.875F};
    private static final float[] SWORD1_LARM_POS_X = {0F, -1F, 0.33F, 0.03F, 0.27F, 0.44F};
    private static final float[] SWORD1_LARM_POS_Y = {0F, -1.2F, -1.35F, -2.17F, -2.44F, -0.77F};
    private static final float[] SWORD1_LARM_POS_Z = {0F, -1.18F, -0.64F, -2.03F, -1.83F, 0.15F};
    private static final float[] SWORD1_BODY_ROT_T = {0F, 0.125F, 0.25F, 0.3125F, 0.5F, 0.5625F, 0.75F};
    private static final float[] SWORD1_BODY_ROT_X = {0F, 13.18F, -6.66F, -10.63F, 29.21F, 37.69F, 33.29F};
    private static final float[] SWORD1_BODY_ROT_Y = {0F, 18.8F, 23.08F, 19.68F, -11.68F, -20.25F, -25.07F};
    private static final float[] SWORD1_BODY_ROT_Z = {0F, -4.03F, -3.05F, -3.62F, -13.17F, -14.97F, -13.66F};
    private static final float[] SWORD1_BODY_POS_T = {0F, 0.125F, 0.3125F, 0.5F, 0.5625F, 0.75F};
    private static final float[] SWORD1_BODY_POS_X = {0F, -0.99F, 0F, -1F, 0F, -0.26F};
    private static final float[] SWORD1_BODY_POS_Y = {0F, -0.11F, -0.25F, -2.31F, -3F, -2.35F};
    private static final float[] SWORD1_BODY_POS_Z = {0F, -2.25F, 2F, -4.75F, -7F, -6.02F};
    private static final float[] SWORD1_HEAD_POS_T = {0F, 0.125F, 0.3125F, 0.5625F, 0.875F};
    private static final float[] SWORD1_HEAD_POS_X = {0F, -1F, 0F, 0F, 0F};
    private static final float[] SWORD1_HEAD_POS_Y = {0F, -1.2F, -0.25F, -2F, -1.5F};
    private static final float[] SWORD1_HEAD_POS_Z = {0F, -1.18F, 2F, -4F, -3.33F};

    private static final float SWORD2_LENGTH = 1.05F;
    private static final float[] SWORD2_RARM_ROT_T = {0F, 0.1F, 0.25F, 0.3F, 0.45F, 0.5F, 0.65F, 0.8F, 0.95F};
    private static final float[] SWORD2_RARM_ROT_X = {-6.12F, 35.55F, 126.4F, 214.43F, 380.9F, 386.12F, 375.57F, 367.45F, 360F};
    private static final float[] SWORD2_RARM_ROT_Y = {-140F, -110.78F, -58.51F, -39.66F, -12.48F, -1.99F, 30F, 17.66F, 0F};
    private static final float[] SWORD2_RARM_ROT_Z = {-55.2F, -109.45F, -204.55F, -232.89F, -257.15F, -267.66F, -310.18F, -338.4F, -360F};
    private static final float[] SWORD2_RARM_POS_T = {0F, 0.1F, 0.3F, 0.45F, 0.5F, 0.65F, 0.8F, 0.95F};
    private static final float[] SWORD2_RARM_POS_X = {1.02F, 0.03F, 2.35F, -1F, -0.98F, 1.09F, 0.55F, 0F};
    private static final float[] SWORD2_RARM_POS_Y = {-1.47F, -1.26F, -2.18F, 0F, -1.2F, -1.8F, -0.7F, 0F};
    private static final float[] SWORD2_RARM_POS_Z = {-3.32F, -2.53F, -1.86F, 2F, 1.57F, 1.26F, -1.04F, 0F};
    private static final float[] SWORD2_LARM_ROT_T = {0F, 0.1F, 0.25F, 0.3F, 0.45F, 0.5F, 0.65F, 0.8F, 0.95F};
    private static final float[] SWORD2_LARM_ROT_X = {14.1F, 28.26F, 62.6F, 46.84F, -28.52F, -38.85F, -42.85F, -22.32F, 0F};
    private static final float[] SWORD2_LARM_ROT_Y = {-18.1F, -9.52F, 10.79F, 16.14F, 28.4F, 26.94F, 13.55F, 5.85F, 0F};
    private static final float[] SWORD2_LARM_ROT_Z = {-26.66F, -15.43F, -41.74F, -39.76F, -21.18F, -16.87F, -6.35F, -2.25F, 0F};
    private static final float[] SWORD2_LARM_POS_T = {0F, 0.1F, 0.25F, 0.45F, 0.65F, 0.8F, 0.95F};
    private static final float[] SWORD2_LARM_POS_X = {-1.06F, -1.23F, -1.29F, -0.98F, -0.59F, -0.27F, 0F};
    private static final float[] SWORD2_LARM_POS_Y = {1.52F, -1.34F, -0.03F, -0.86F, -1.6F, -0.85F, 0F};
    private static final float[] SWORD2_LARM_POS_Z = {1.25F, 0.67F, 3.64F, 0.88F, -0.65F, -0.42F, 0F};
    private static final float[] SWORD2_BODY_ROT_T = {0F, 0.1F, 0.25F, 0.4F, 0.5F, 0.65F, 0.8F, 0.95F};
    private static final float[] SWORD2_BODY_ROT_X = {10.76F, 3.55F, 28.46F, -2.7F, -15.7F, -15.25F, 4.22F, 0F};
    private static final float[] SWORD2_BODY_ROT_Y = {-26.53F, -21.95F, -34.7F, 14.72F, 31.59F, 24.58F, 9.22F, 0F};
    private static final float[] SWORD2_BODY_ROT_Z = {-7.71F, -1.81F, -15.25F, -5.38F, -2.03F, -3.8F, 0.13F, 0F};
    private static final float[] SWORD2_BODY_POS_T = {0F, 0.1F, 0.3F, 0.45F, 0.65F, 0.8F, 0.95F};
    private static final float[] SWORD2_BODY_POS_X = {-0.89F, -0.89F, 0.13F, -0.11F, -0.08F, -0.03F, 0F};
    private static final float[] SWORD2_BODY_POS_Y = {-0.12F, -1.12F, -0.9F, -0.12F, -1F, -0.42F, 0F};
    private static final float[] SWORD2_BODY_POS_Z = {-1.45F, -0.45F, -1.35F, 3.55F, 2.43F, -1.14F, 0F};
    private static final float[] SWORD2_HEAD_POS_T = {0F, 0.1F, 0.25F, 0.45F, 0.65F, 0.8F, 0.95F};
    private static final float[] SWORD2_HEAD_POS_X = {0F, -1F, 0F, -0.75F, -0.5F, -0.42F, 0F};
    private static final float[] SWORD2_HEAD_POS_Y = {0F, -1.25F, -2F, 0F, -0.86F, -0.36F, 0F};
    private static final float[] SWORD2_HEAD_POS_Z = {-1.5F, -0.36F, -2.5F, 3.5F, 2.4F, -1.15F, 0F};

    private static final float GSWORD_LENGTH = 1.4813F;
    private static final float[] GSWORD_RARM_ROT_T = {0F, 0.2083F, 0.375F, 0.7083F, 0.875F, 1.0833F};
    private static final float[] GSWORD_RARM_ROT_X = {0F, -45F, -120F, -153.2871F, -60F, -6.6674F};
    private static final float[] GSWORD_RARM_ROT_Y = {0F, 10F, 20F, 2.3188F, -5F, -8.9808F};
    private static final float[] GSWORD_RARM_ROT_Z = {0F, 10F, 20F, 25.8284F, -10F, -14.9135F};
    private static final float[] GSWORD_RARM_POS_T = {0F, 0.2917F, 0.375F, 0.7083F, 0.875F};
    private static final float[] GSWORD_RARM_POS_X = {0F, 0F, 0F, 0F, 2F};
    private static final float[] GSWORD_RARM_POS_Y = {0F, 3F, 3F, 2F, -1F};
    private static final float[] GSWORD_RARM_POS_Z = {0F, 0F, 0F, -2F, -2F};
    private static final float[] GSWORD_LARM_ROT_T = {0F, 0.2083F, 0.375F, 0.7083F, 0.875F, 1.0833F};
    private static final float[] GSWORD_LARM_ROT_X = {0F, -30F, -110F, -143.7762F, -50F, -16.4665F};
    private static final float[] GSWORD_LARM_ROT_Y = {0F, -10F, -15F, 0.2434F, 5F, 7.2818F};
    private static final float[] GSWORD_LARM_ROT_Z = {0F, -10F, -20F, -24.2602F, 15F, 20.2648F};
    private static final float[] GSWORD_LARM_POS_T = {0F, 0.2917F, 0.375F, 0.7083F, 0.875F};
    private static final float[] GSWORD_LARM_POS_X = {0F, -0.75F, -1F, 0F, -3F};
    private static final float[] GSWORD_LARM_POS_Y = {0F, 3F, 3F, 2F, -1F};
    private static final float[] GSWORD_LARM_POS_Z = {0F, 1F, 1F, -2F, -2F};
    private static final float[] GSWORD_BODY_ROT_T = {0F, 0.2917F, 0.5833F, 0.875F, 1.1667F};
    private static final float[] GSWORD_BODY_ROT_X = {0F, -15F, -25F, 25F, 15F};
    private static final float[] GSWORD_BODY_ROT_Y = {0F, 8F, 12F, -5F, -8F};
    private static final float[] GSWORD_BODY_ROT_Z = {0F, 0F, 3F, -5F, -3F};
    private static final float[] GSWORD_BODY_POS_T = {0F, 0.2917F, 0.375F, 0.5833F, 0.7083F, 0.875F};
    private static final float[] GSWORD_BODY_POS_X = {0F, 0F, 0F, 1F, 0F, 0F};
    private static final float[] GSWORD_BODY_POS_Y = {0F, -0.5F, 0F, -0.67F, -1F, -2F};
    private static final float[] GSWORD_BODY_POS_Z = {0F, 3.25F, 3F, 4F, 1F, -5F};
    private static final float[] GSWORD_HEAD_POS_T = {0F, 0.375F, 0.7083F, 0.875F};
    private static final float[] GSWORD_HEAD_POS_X = {0F, 0F, 0F, 0F};
    private static final float[] GSWORD_HEAD_POS_Y = {0F, 1F, 0.4F, 0F};
    private static final float[] GSWORD_HEAD_POS_Z = {0F, 0F, -0.8F, -1F};


    // ── Weapon attack keyframe ports (sword 1 / sword 2 / greatsword) ────────
    //
    // Direct Catmull-Rom keyframe port (same idea as the walk/run locomotion
    // section above, via sampleAttackTimeline instead of the cyclic
    // sampleCycle) of this project's own Blockbench player rig animations
    // "sword_attack", "sword_attack_2" and "greatsword_attack" —
    // rightArm/leftArm/body/head bones, both rotation and position channels
    // (this project's own rig, not Kelvin's, so — per the "Fourth pass"
    // reasoning above — position is safe to port 1:1 alongside rotation).
    // head only ever carried a position channel in the source clips (no
    // head rotation), so only *Head(t) position fields are populated;
    // GotPlayerAnimator never touches head rotation anyway (that's vanilla's
    // look-around code). Each returned LimbPose is the *absolute* authored
    // pose for that instant, not a delta — callers should assign it onto
    // the ModelPart's rotation fields (not add it to whatever pose came
    // before), the same way the base walk/run pose is assigned rather than
    // added. Source clips are authored right-handed (rightArm is the
    // weapon arm); {@link #mirrorPose} flips one for left-handed swings.

    private static LimbPose limbPose(
            float t, float animLength,
            float[] rotT, float[] rotX, float[] rotY, float[] rotZ,
            float[] posT, float[] posX, float[] posY, float[] posZ) {
        float xRot = rotT == null ? 0.0F : rad(sampleAttackTimeline(t, rotT, rotX, animLength));
        float yRot = rotT == null ? 0.0F : rad(sampleAttackTimeline(t, rotT, rotY, animLength));
        float zRot = rotT == null ? 0.0F : rad(sampleAttackTimeline(t, rotT, rotZ, animLength));
        float x = posT == null ? 0.0F : sampleAttackTimeline(t, posT, posX, animLength);
        float y = posT == null ? 0.0F : sampleAttackTimeline(t, posT, posY, animLength);
        float z = posT == null ? 0.0F : sampleAttackTimeline(t, posT, posZ, animLength);
        return new LimbPose(xRot, yRot, zRot, x, y, z);
    }

    public static LimbPose sword1RightArm(float t) {
        return limbPose(t, SWORD1_LENGTH,
                SWORD1_RARM_ROT_T, SWORD1_RARM_ROT_X, SWORD1_RARM_ROT_Y, SWORD1_RARM_ROT_Z,
                SWORD1_RARM_POS_T, SWORD1_RARM_POS_X, SWORD1_RARM_POS_Y, SWORD1_RARM_POS_Z);
    }

    public static LimbPose sword1LeftArm(float t) {
        return limbPose(t, SWORD1_LENGTH,
                SWORD1_LARM_ROT_T, SWORD1_LARM_ROT_X, SWORD1_LARM_ROT_Y, SWORD1_LARM_ROT_Z,
                SWORD1_LARM_POS_T, SWORD1_LARM_POS_X, SWORD1_LARM_POS_Y, SWORD1_LARM_POS_Z);
    }

    public static LimbPose sword1Body(float t) {
        return limbPose(t, SWORD1_LENGTH,
                SWORD1_BODY_ROT_T, SWORD1_BODY_ROT_X, SWORD1_BODY_ROT_Y, SWORD1_BODY_ROT_Z,
                SWORD1_BODY_POS_T, SWORD1_BODY_POS_X, SWORD1_BODY_POS_Y, SWORD1_BODY_POS_Z);
    }

    public static LimbPose sword1Head(float t) {
        return limbPose(t, SWORD1_LENGTH,
                null, null, null, null,
                SWORD1_HEAD_POS_T, SWORD1_HEAD_POS_X, SWORD1_HEAD_POS_Y, SWORD1_HEAD_POS_Z);
    }

    public static LimbPose sword2RightArm(float t) {
        return limbPose(t, SWORD2_LENGTH,
                SWORD2_RARM_ROT_T, SWORD2_RARM_ROT_X, SWORD2_RARM_ROT_Y, SWORD2_RARM_ROT_Z,
                SWORD2_RARM_POS_T, SWORD2_RARM_POS_X, SWORD2_RARM_POS_Y, SWORD2_RARM_POS_Z);
    }

    public static LimbPose sword2LeftArm(float t) {
        return limbPose(t, SWORD2_LENGTH,
                SWORD2_LARM_ROT_T, SWORD2_LARM_ROT_X, SWORD2_LARM_ROT_Y, SWORD2_LARM_ROT_Z,
                SWORD2_LARM_POS_T, SWORD2_LARM_POS_X, SWORD2_LARM_POS_Y, SWORD2_LARM_POS_Z);
    }

    public static LimbPose sword2Body(float t) {
        return limbPose(t, SWORD2_LENGTH,
                SWORD2_BODY_ROT_T, SWORD2_BODY_ROT_X, SWORD2_BODY_ROT_Y, SWORD2_BODY_ROT_Z,
                SWORD2_BODY_POS_T, SWORD2_BODY_POS_X, SWORD2_BODY_POS_Y, SWORD2_BODY_POS_Z);
    }

    public static LimbPose sword2Head(float t) {
        return limbPose(t, SWORD2_LENGTH,
                null, null, null, null,
                SWORD2_HEAD_POS_T, SWORD2_HEAD_POS_X, SWORD2_HEAD_POS_Y, SWORD2_HEAD_POS_Z);
    }

    public static LimbPose greatswordRightArm(float t) {
        return limbPose(t, GSWORD_LENGTH,
                GSWORD_RARM_ROT_T, GSWORD_RARM_ROT_X, GSWORD_RARM_ROT_Y, GSWORD_RARM_ROT_Z,
                GSWORD_RARM_POS_T, GSWORD_RARM_POS_X, GSWORD_RARM_POS_Y, GSWORD_RARM_POS_Z);
    }

    public static LimbPose greatswordLeftArm(float t) {
        return limbPose(t, GSWORD_LENGTH,
                GSWORD_LARM_ROT_T, GSWORD_LARM_ROT_X, GSWORD_LARM_ROT_Y, GSWORD_LARM_ROT_Z,
                GSWORD_LARM_POS_T, GSWORD_LARM_POS_X, GSWORD_LARM_POS_Y, GSWORD_LARM_POS_Z);
    }

    public static LimbPose greatswordBody(float t) {
        return limbPose(t, GSWORD_LENGTH,
                GSWORD_BODY_ROT_T, GSWORD_BODY_ROT_X, GSWORD_BODY_ROT_Y, GSWORD_BODY_ROT_Z,
                GSWORD_BODY_POS_T, GSWORD_BODY_POS_X, GSWORD_BODY_POS_Y, GSWORD_BODY_POS_Z);
    }

    public static LimbPose greatswordHead(float t) {
        return limbPose(t, GSWORD_LENGTH,
                null, null, null, null,
                GSWORD_HEAD_POS_T, GSWORD_HEAD_POS_X, GSWORD_HEAD_POS_Y, GSWORD_HEAD_POS_Z);
    }


    // ── Blocking (shield raised) ─────────────────────────────────────────────

    public static float blockArmPitch(float progress) {
        return -rad(75.0F) * progress;
    }

    public static float blockArmYawAcrossChest(float progress, boolean rightSide) {
        float sign = rightSide ? 1.0F : -1.0F;
        return sign * rad(28.0F) * progress;
    }

    public static float blockBodyLean(float progress) {
        return rad(5.0F) * progress;
    }
}