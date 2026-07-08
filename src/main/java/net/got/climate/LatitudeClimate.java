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
}
