package net.got.client.animation.player;

import net.minecraft.util.Mth;

public final class AnimMath {

    private AnimMath() {}

    private static final float PUNCH_DURATION_TICKS = 6.0F;
    private static final float TRIDENT_DURATION_TICKS = 10.0F;
    private static final float TOOL_DURATION_TICKS = 8.0F;
    private static final float GENERIC_DURATION_TICKS = 7.0F;

    public static float swingVisualDuration(SwingStyle style) {
        return switch (style) {
            case PUNCH -> PUNCH_DURATION_TICKS;
            case TRIDENT -> TRIDENT_DURATION_TICKS;
            case TOOL -> TOOL_DURATION_TICKS;
            case GENERIC -> GENERIC_DURATION_TICKS;
            case SWORD, GREATSWORD, AXE ->
                    throw new IllegalArgumentException(style + " sizes its swing window off its PlayerAnimations clip length, not AnimMath");
        };
    }

    private static float rad(float degrees) {
        return degrees * Mth.DEG_TO_RAD;
    }

    public static float approach(float current, float target, float speed) {
        return Mth.lerp(Mth.clamp(speed, 0.0F, 1.0F), current, target);
    }

    private static final float IDLE_FREQUENCY = 0.05F;

    public static float idleArmSway(float ageInTicks) {
        float primary = rad(2.2F) * Mth.sin(ageInTicks * IDLE_FREQUENCY);

        float secondary = rad(0.6F) * Mth.sin(ageInTicks * IDLE_FREQUENCY * 2.7F + 1.3F);
        return primary + secondary;
    }

    public static float idleLegSplay(boolean rightSide) {
        return rightSide ? rad(3.5F) : -rad(3.5F);
    }

    public static float idleBodySway(float ageInTicks) {
        float primary = rad(1.5F) * Mth.sin(ageInTicks * IDLE_FREQUENCY * 0.5F);
        float secondary = rad(0.4F) * Mth.sin(ageInTicks * IDLE_FREQUENCY * 1.9F + 0.7F);
        return primary + secondary;
    }

    public static float climbArmReach(float ageInTicks, boolean rightSide) {
        float phase = rightSide ? 0.0F : (float) Math.PI;
        return rad(50.0F) * Mth.sin(ageInTicks * 0.5F + phase) - rad(22.0F);
    }

    public static float climbLegPush(float ageInTicks, boolean rightSide) {

        float phase = rightSide ? (float) Math.PI : 0.0F;
        return rad(35.0F) * Mth.sin(ageInTicks * 0.5F + phase);
    }

    public static float climbBodyPitch() {
        return rad(10.0F);
    }

    private static float easedSwing(float t) {
        t = Mth.clamp(t, 0.0F, 1.0F);

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

    public static float tridentThrustPitch(float t) {
        t = Mth.clamp(t, 0.0F, 1.0F);
        if (t < 0.3F) {
            return -rad(110.0F) * (t / 0.3F);
        } else {
            float u = (t - 0.3F) / 0.7F;
            return Mth.lerp(Mth.sin(u * (Mth.PI / 2.0F)), -rad(110.0F), rad(15.0F));
        }
    }

    public static float swingBodyFollow(float t, boolean rightSide) {
        float sign = rightSide ? -1.0F : 1.0F;
        return sign * rad(15.0F) * easedSwing(t);
    }

    public static float swingBodyPitchSnap(float t) {
        return rad(6.0F) * easedSwing(t);
    }

    public static float offArmCounterPitch(float t) {
        return rad(22.0F) * easedSwing(t);
    }

    public static float offArmCounterRoll(float t, boolean offArmIsRight) {
        float sign = offArmIsRight ? 1.0F : -1.0F;
        return sign * rad(18.0F) * easedSwing(t);
    }

    public static float swingLegWeightShift(float t, boolean rightSide) {
        float sign = rightSide ? -1.0F : 1.0F;
        return sign * rad(10.0F) * easedSwing(t);
    }

    public static float bowArmPitch() {
        return -rad(95.0F);
    }

    public static float bowArmYaw(boolean rightSide) {
        float sign = rightSide ? -1.0F : 1.0F;
        return sign * rad(16.0F);
    }

    public static float bowArmSpread(boolean rightSide) {
        float sign = rightSide ? 1.0F : -1.0F;
        return sign * rad(7.0F);
    }
}