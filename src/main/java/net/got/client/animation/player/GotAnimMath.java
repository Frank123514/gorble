package net.got.client.animation.player;

import net.minecraft.util.Mth;

/**
 * Procedural ("math", not keyframe) player-limb animation curves for the
 * poses {@link PlayerAnimations} doesn't cover.
 *
 * <p>Every method here is a pure function of some progress/time input to a
 * rotation in <b>radians</b> (the unit {@link net.minecraft.client.model.geom.ModelPart#xRot}
 * etc. expect) — no Minecraft entity/model state is touched in this class,
 * which keeps the actual formulas easy to read, tune, and unit-test in
 * isolation from the mixin plumbing that feeds them.
 *
 * <p>{@link GotPlayerAnimator} is the only caller; it decides *which* of
 * these to use (and, for the styles that have a real keyframe clip, whether
 * to use this file at all) then writes the results onto the real
 * {@code ModelPart}s.
 *
 * <p><b>Seventh pass — locomotion and sword/greatsword/axe swings move to
 * literal keyframe playback:</b> walking, running, jumping, and the
 * sword/greatsword/axe attack swings are no longer approximated here at
 * all — {@link GotPlayerAnimator} now plays {@link PlayerAnimations}'
 * {@code WALKING}/{@code RUNNING}/{@code JUMP}/{@code SWORD_ATTACK}/
 * {@code SWORD_ATTACK_2}/{@code GREATSWORD_ATTACK}/{@code AXE_ATTACK}
 * clips directly via {@code KeyframeAnimations.animate}, the same vanilla
 * playback path {@code GotStagModel}/{@code GotBrownBearModel}/etc. already
 * use. Everything that pass removed from this file (the pendulum-gait leg/
 * arm/torso formulas, the jump tuck/flare/reach curves, and the sword1/
 * sword2/greatsword {@code SwingProfile}/{@code LimbPose} machinery) is
 * gone rather than left dead, since {@code PlayerAnimations} is now the
 * single source of truth for those poses.
 *
 * <p><b>What's still procedural, and why:</b> idle breathing sway, sneak,
 * climbing, and the punch/trident/tool/generic swing styles have no
 * Blockbench-authored clip to play back (the source export only covered
 * walk/run/jump/sword/greatsword/axe), so those stay as the continuous
 * sine/eased curves reverse-engineered from Kelvin's Better Player
 * Animations in earlier passes — unchanged by this one.
 */
public final class GotAnimMath {

    private GotAnimMath() {}

    /** Vanilla's own walk-gait frequency constant — reused so sneak's gait still lines up with footstep sounds/particles the same way the old full-body walk cycle did. */
    private static final float GAIT_FREQUENCY = 0.6662F;

    /**
     * How long (in ticks) each *procedural* swing style's pose curves get to
     * play, regardless of the attacking weapon's real attack-speed cooldown
     * — see {@code GotPlayerAnimator.swingDurationTicks}. SWORD/GREATSWORD/
     * AXE aren't listed here any more: those now size their swing window
     * off {@link PlayerAnimations}' own clip length instead of a hand-tuned
     * constant, since they play the literal authored clip rather than an
     * eased approximation.
     */
    private static final float PUNCH_DURATION_TICKS = 6.0F;
    private static final float TRIDENT_DURATION_TICKS = 10.0F;
    private static final float TOOL_DURATION_TICKS = 8.0F;
    private static final float GENERIC_DURATION_TICKS = 7.0F;

    /** How long (in ticks) {@code style}'s procedural swing pose gets to play out — see the per-style constants above. SWORD/GREATSWORD/AXE are handled by the caller directly off {@link PlayerAnimations} clip length instead of this method. */
    public static float swingVisualDuration(GotSwingStyle style) {
        return switch (style) {
            case PUNCH -> PUNCH_DURATION_TICKS;
            case TRIDENT -> TRIDENT_DURATION_TICKS;
            case TOOL -> TOOL_DURATION_TICKS;
            case GENERIC -> GENERIC_DURATION_TICKS;
            case SWORD, GREATSWORD, AXE ->
                    throw new IllegalArgumentException(style + " sizes its swing window off its PlayerAnimations clip length, not GotAnimMath");
        };
    }

    private static float rad(float degrees) {
        return degrees * Mth.DEG_TO_RAD;
    }

    /** Smoothly ramps {@code current} toward {@code target}, independent of framerate-ish (call once per extraction). */
    public static float approach(float current, float target, float speed) {
        return Mth.lerp(Mth.clamp(speed, 0.0F, 1.0F), current, target);
    }

    // ── Idle sway (standing still) ──────────────────────────────────────────
    // Kelvin's idle.json is a slow ~2s breathing loop with tiny arm-roll
    // drift and a fixed relaxed leg turnout; ported here as continuous
    // sine/constant terms (rather than a literal 2s keyframe loop) so it
    // blends seamlessly with the walk/run keyframe clips via weight as
    // movement intensity fades to 0.

    private static final float IDLE_FREQUENCY = 0.05F;

    /** Subtle slow arm roll "breathing" drift while standing still. Caller negates for the opposite arm. */
    public static float idleArmSway(float ageInTicks) {
        float primary = rad(2.2F) * Mth.sin(ageInTicks * IDLE_FREQUENCY);
        // Small, incommensurate-frequency second wave so the idle drift
        // doesn't read as a perfect metronome loop.
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
     * normal walk leg swing entirely (sneaking doesn't blend with
     * {@link PlayerAnimations#WALKING} — see {@code GotPlayerAnimator}) —
     * Kelvin's sneak_walk.json legs swing a shorter ~16° arc mostly forward
     * of rest rather than symmetric front/back like a regular stride.
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

    // ── Attack swings (punch / trident / tool / generic), 0..1 progress ─────
    // SWORD/GREATSWORD/AXE no longer go through this section at all — see
    // GotPlayerAnimator.applyKeyframeSwing, which plays the real
    // PlayerAnimations clip instead.

    /**
     * Shapes a raw 0..1 swing progress into an eased "fast out, slower
     * recovery" curve shared by the remaining procedural swing styles, just
     * with different amplitudes/axes layered on top by the style-specific
     * methods below.
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

    public static float toolStrikePitch(float t) {
        return -rad(60.0F) * easedSwing(t);
    }

    public static float genericSwingPitch(float t) {
        return -rad(45.0F) * easedSwing(t);
    }

    /**
     * Trident overhead thrust — deeper wind-up (negative = raised overhead)
     * before the strike than a sword. Also used to cover {@code AXE} prior
     * to this pass; now AXE plays {@link PlayerAnimations#AXE_ATTACK}
     * instead, and this is kept solely for TRIDENT.
     */
    public static float tridentThrustPitch(float t) {
        t = Mth.clamp(t, 0.0F, 1.0F);
        if (t < 0.3F) {
            return -rad(110.0F) * (t / 0.3F);
        } else {
            float u = (t - 0.3F) / 0.7F;
            return Mth.lerp(Mth.sin(u * (Mth.PI / 2.0F)), -rad(110.0F), rad(15.0F));
        }
    }

    /** Slight torso rotation following the swinging arm, sold across all procedural styles. */
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

    // ── Blocking (shield raised) ─────────────────────────────────────────────
    // No longer called by GotPlayerAnimator — superseded by the real
    // authored PlayerAnimations.SWORD_BLOCK clip (see
    // GotPlayerAnimator#applyBlockIfNeeded). Left in place rather than
    // deleted in case the clip needs a quick procedural fallback again.

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

    // ── Bow draw (vanilla bow & our longbow share ArmPose.BOW_AND_ARROW) ────
    // Static held pose, same idea as the block values above: no time input,
    // applied at full strength for as long as either arm reports
    // ArmPose.BOW_AND_ARROW. Vanilla's own bow pose is likewise a fixed
    // rotation for the whole draw, not something that animates in over the
    // pull — so a plain constant reads correctly here rather than needing a
    // progress curve. GotPlayerAnimator raises BOTH arms whenever either one
    // is drawing (only the actual drawing arm reports BOW_AND_ARROW —
    // the other arm's own pose stays EMPTY/ITEM the whole time, so this
    // can't be driven off each arm's individual pose) — the mirrored
    // yaw/roll below is what makes the two arms converge in front of the
    // body instead of both just pointing straight ahead in parallel, i.e.
    // the "other arm comes up to help hold it" look.

    /** Raises the arm forward and up into an aiming/drawing position. */
    public static float bowArmPitch() {
        return -rad(95.0F);
    }

    /** Angles the raised arm in toward the body's centerline so both arms converge in front of the chest rather than pointing straight ahead in parallel. */
    public static float bowArmYaw(boolean rightSide) {
        float sign = rightSide ? -1.0F : 1.0F;
        return sign * rad(16.0F);
    }

    /** Small elbow-out roll so the raised arms don't look pinned flat against the body. */
    public static float bowArmSpread(boolean rightSide) {
        float sign = rightSide ? 1.0F : -1.0F;
        return sign * rad(7.0F);
    }
}