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
 */
public final class GotAnimMath {

    private GotAnimMath() {}

    /** Vanilla's own walk-gait frequency constant — reused so our gait still lines up with footstep sounds/particles. */
    private static final float GAIT_FREQUENCY = 0.6662F;

    private static float rad(float degrees) {
        return degrees * Mth.DEG_TO_RAD;
    }

    /** Smoothly ramps {@code current} toward {@code target}, independent of framerate-ish (call once per extraction). */
    public static float approach(float current, float target, float speed) {
        return Mth.lerp(Mth.clamp(speed, 0.0F, 1.0F), current, target);
    }

    // ── Locomotion (walk / run) ─────────────────────────────────────────────

    /**
     * Leg swing angle. {@code intensity} is 0 (standing) to 1 (full stride);
     * {@code runBlend} 0..1 fades from the tighter walk gait into a longer,
     * more forward-leaning run gait.
     */
    public static float legSwing(float walkPos, float intensity, float runBlend) {
        float amplitude = Mth.lerp(runBlend, 32.0F, 54.0F);
        return rad(amplitude) * Mth.cos(walkPos * GAIT_FREQUENCY) * intensity;
    }

    /** Arms swing opposite the same-side leg, slightly smaller amplitude. */
    public static float armSwing(float walkPos, float intensity, float runBlend) {
        float amplitude = Mth.lerp(runBlend, 24.0F, 40.0F);
        return -rad(amplitude) * Mth.cos(walkPos * GAIT_FREQUENCY) * intensity;
    }

    /** Small opposite-phase side-to-side sway of the legs, more pronounced when running. */
    public static float legSway(float walkPos, float intensity, float runBlend) {
        float amplitude = Mth.lerp(runBlend, 2.0F, 5.0F);
        return rad(amplitude) * Mth.sin(walkPos * GAIT_FREQUENCY) * intensity;
    }

    /** Torso bob: leans forward slightly at a run, bobs vertically each step. */
    public static float runTorsoLean(float runBlend) {
        return rad(6.0F) * runBlend;
    }

    public static float stepBob(float walkPos, float intensity, float runBlend) {
        float amplitude = Mth.lerp(runBlend, 0.03F, 0.08F);
        return Mth.abs(Mth.cos(walkPos * GAIT_FREQUENCY)) * amplitude * intensity;
    }

    // ── Sneak ────────────────────────────────────────────────────────────────

    public static float sneakBodyPitch() {
        return rad(14.0F);
    }

    public static float sneakArmForward() {
        return rad(10.0F);
    }

    // ── Jump / airborne ──────────────────────────────────────────────────────

    /** Legs tuck slightly up-and-back the instant a player leaves the ground, relaxing back down as {@code airborne} fades. */
    public static float jumpLegTuck(float airborne) {
        return -rad(18.0F) * airborne;
    }

    /** Arms drift outward a little for "balance" while airborne. */
    public static float jumpArmFlare(float airborne) {
        return rad(10.0F) * airborne;
    }

    // ── Climbing (ladders/vines/scaffolding) ────────────────────────────────

    /**
     * Alternating reach-and-pull climb cycle. Right arm/left leg move
     * together, opposite left arm/right leg — like a real ladder climb
     * rather than vanilla's plain walk-swing on ladders.
     */
    public static float climbArmReach(float ageInTicks, boolean rightSide) {
        float phase = rightSide ? 0.0F : (float) Math.PI;
        return rad(50.0F) * Mth.sin(ageInTicks * 0.5F + phase) - rad(20.0F);
    }

    public static float climbLegPush(float ageInTicks, boolean rightSide) {
        // legs move opposite the same-side arm for a natural cross-climb pattern
        float phase = rightSide ? (float) Math.PI : 0.0F;
        return rad(35.0F) * Mth.sin(ageInTicks * 0.5F + phase);
    }

    public static float climbBodyPitch() {
        return rad(8.0F);
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
        return -rad(55.0F) * easedSwing(t);
    }

    public static float swordSlashPitch(float t) {
        return -rad(70.0F) * easedSwing(t);
    }

    /** Horizontal component of the sword slash, arcing the arm across the body. */
    public static float swordSlashYaw(float t, boolean rightSide) {
        float sign = rightSide ? -1.0F : 1.0F;
        return sign * rad(35.0F) * easedSwing(t);
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
        return sign * rad(6.0F) * easedSwing(t);
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
