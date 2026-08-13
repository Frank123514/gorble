package net.got.climate;

import net.minecraft.util.Mth;

public final class LatitudeClimate {

    private static final LatitudeClimateConfig CONFIG = LatitudeClimateConfig.get();

    private static final int FREEZE_LINE_Z = CONFIG.iceFreezeLineZ();

    private static final float FADE_DISTANCE = CONFIG.iceFadeDistance();

    private static final float MAX_ADJUSTMENT = CONFIG.iceMaxAdjustment();

    private LatitudeClimate() {}

    public static int freezeLineZ(int worldX) {
        return FREEZE_LINE_Z;
    }

    public static boolean isBeyondLine(int worldX, int worldZ) {
        return worldZ <= freezeLineZ(worldX);
    }

    public static float latitudeStrength(int worldX, int worldZ) {
        int lineZ = freezeLineZ(worldX);
        int northOf = lineZ - worldZ;
        if (northOf <= 0) return 0f;
        return Mth.clamp(northOf / FADE_DISTANCE, 0f, 1f);
    }

    public static float temperatureAdjustment(int worldX, int worldZ) {
        return latitudeStrength(worldX, worldZ) * MAX_ADJUSTMENT;
    }
}