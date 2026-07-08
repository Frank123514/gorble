package net.got.climate;

import net.minecraft.util.Mth;

/**
 * Latitude-based climate override.
 *
 * <p>The line and everything north of it (smaller world Z, since north is
 * "up" in this mod) is the "frozen latitude" — permanently cold regardless
 * of what biome happens to sit there. This is independent of the physical
 * Wall structure built in {@link net.got.worldgen.WallWorldGen}; that's a
 * localized building, this is a world-spanning climate band.
 *
 * <h3>Line data</h3>
 * The freeze line is a single flat world Z coordinate,
 * {@link #FREEZE_LINE_Z}, the same for every worldX. This used to be a
 * ~4207-point spine traced from a marker line on biomemap.png with B-spline
 * interpolation between points; that was replaced with one flat value plus
 * the gradual fade below, which gives the same "colder the further north"
 * feel with far less complexity to maintain.
 *
 * <h3>Gradient</h3>
 * Positions south of / on the line get no adjustment at all — every biome
 * and season effect already in place is untouched.  Positions north of the
 * line fade linearly from 0 down to {@link #MAX_ADJUSTMENT} over
 * {@link #FADE_DISTANCE} blocks, so the far north gets colder the further
 * north you go, eventually bottoming out at a guaranteed deep freeze that
 * overrides even the hottest biome's base temperature.
 */
public final class LatitudeClimate {

    // Ice tuning constants live in /net/got/climate/latitude_climate.json
    // (see LatitudeClimateConfig), alongside the dead-grass line data used
    // by SeasonFoliageColorProvider, instead of being hardcoded here.
    private static final LatitudeClimateConfig CONFIG = LatitudeClimateConfig.get();

    // Flat world Z of the freeze line. Same value for every worldX.
    private static final int FREEZE_LINE_Z = CONFIG.iceFreezeLineZ();

    // Distance (in blocks) north of the line before the freeze effect fully saturates.
    private static final float FADE_DISTANCE = CONFIG.iceFadeDistance();

    // Adjustment applied at full saturation. Large enough to floor even the
    // hottest biome (base temperature up to 2.0) down to the absolute
    // minimum (-0.5) once far enough north of the line.
    private static final float MAX_ADJUSTMENT = CONFIG.iceMaxAdjustment();

    // Separate flat world Z of the snow-dusting line — independent of the
    // ice/temperature freeze line above. Ground snow (LatitudeSnowHandler)
    // gates off this line instead, so it can start further south than water
    // actually starts icing over.
    private static final int SNOW_LINE_Z = CONFIG.snowFreezeLineZ();

    // Fade distance for the snow line's own gradient (separate from the ice fade).
    private static final float SNOW_FADE_DISTANCE = CONFIG.snowFadeDistance();

    // Adjustment applied at full saturation of the snow-line gradient. Used
    // only to keep ground snow from melting out north of the line — floors
    // the seasonal temperature the same way MAX_ADJUSTMENT does for ice.
    private static final float SNOW_MAX_ADJUSTMENT = CONFIG.snowMaxAdjustment();

    private LatitudeClimate() {}

    /**
     * Returns the freeze-line world Z. The line is flat, so this is the
     * same value regardless of worldX; the parameter is kept so callers
     * don't need to change if the line becomes X-dependent again later.
     */
    public static int freezeLineZ(int worldX) {
        return FREEZE_LINE_Z;
    }

    /**
     * Returns {@code true} if (worldX, worldZ) lies north of (or on) the
     * frozen latitude line.
     */
    public static boolean isBeyondLine(int worldX, int worldZ) {
        return worldZ <= freezeLineZ(worldX);
    }

    /**
     * Returns 0..1 — how far into the frozen-latitude fade a position is.
     * {@code 0} south of/on the line, ramping linearly up to {@code 1} at
     * {@link #FADE_DISTANCE} blocks north of it. This is the raw gradient
     * fraction {@link #temperatureAdjustment} scales {@link #MAX_ADJUSTMENT}
     * by, exposed separately for anything that wants the gradient itself
     * (e.g. a freeze-chance roll) rather than a temperature offset.
     */
    public static float latitudeStrength(int worldX, int worldZ) {
        int lineZ = freezeLineZ(worldX);
        int northOf = lineZ - worldZ; // positive = north of the line
        if (northOf <= 0) return 0f;
        return Mth.clamp(northOf / FADE_DISTANCE, 0f, 1f);
    }

    /**
     * Temperature offset to layer on top of a biome's own (seasonal) effective
     * temperature. Returns {@code 0} south of the line (no effect at all —
     * every other climate system is left completely untouched there), fading
     * linearly to {@link #MAX_ADJUSTMENT} over {@link #FADE_DISTANCE} blocks
     * north of it.
     */
    public static float temperatureAdjustment(int worldX, int worldZ) {
        return latitudeStrength(worldX, worldZ) * MAX_ADJUSTMENT;
    }

    /**
     * Returns the snow line's world Z. Flat, same as {@link #freezeLineZ},
     * but a distinct value used only for ground-snow-dusting
     * ({@link LatitudeSnowHandler}) — kept separate from the
     * ice/temperature line so the two can sit at different latitudes.
     */
    public static int snowLineZ(int worldX) {
        return SNOW_LINE_Z;
    }

    /** Returns {@code true} if (worldX, worldZ) lies north of (or on) the snow line. */
    public static boolean isBeyondSnowLine(int worldX, int worldZ) {
        return worldZ <= snowLineZ(worldX);
    }

    /**
     * Same shape as {@link #latitudeStrength}, but gated off the separate
     * {@link #SNOW_LINE_Z} line and its own {@link #SNOW_FADE_DISTANCE}
     * instead of the ice line's.
     */
    public static float snowLatitudeStrength(int worldX, int worldZ) {
        int lineZ = snowLineZ(worldX);
        int northOf = lineZ - worldZ;
        if (northOf <= 0) return 0f;
        return Mth.clamp(northOf / SNOW_FADE_DISTANCE, 0f, 1f);
    }

    /**
     * Temperature offset for ground-snow melt checks only ({@link
     * SnowMeltHandler}) — {@code 0} south of the snow line,
     * fading linearly to {@link #SNOW_MAX_ADJUSTMENT} over {@link
     * #SNOW_FADE_DISTANCE} blocks north of it, so snow stops melting out in
     * warm seasons the further north of the line it sits.
     */
    public static float snowTemperatureAdjustment(int worldX, int worldZ) {
        return snowLatitudeStrength(worldX, worldZ) * SNOW_MAX_ADJUSTMENT;
    }
}
