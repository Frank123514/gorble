package net.got.climate;

/**
 * Immutable snapshot of all environment properties for a specific world position.
 *
 * <p>Create via {@link ClimateSystem#getFeaturesAt(int)} or
 * {@link ClimateSystem#getFeaturesAt(double)}.  Every field is pre-computed so
 * callers pay no extra cost after construction.
 *
 * <h3>Usage</h3>
 * <pre>{@code
 * ClimateFeatures f = ClimateSystem.getFeaturesAt(player.getZ());
 * if (f.isFreezingCold()) {
 *     // apply frostbite effect
 * }
 * }</pre>
 */
public final class ClimateFeatures {

    // ── Core zone ─────────────────────────────────────────────────────────────

    /** The climate zone this snapshot belongs to. */
    public final ClimateZone zone;

    /** World-block Z coordinate used to derive these features. */
    public final int worldZ;

    // ── Temperature ───────────────────────────────────────────────────────────

    /**
     * Normalised temperature in [0, 1]: 0 = coldest (Polar), 1 = hottest (Tropical).
     * Computed from the zone's temperature modifier mapped into a [0,1] range.
     */
    public final float normalizedTemperature;

    /** True if the temperature is low enough to freeze water and cause snow. */
    public final boolean isFreezing;

    /** True if the temperature is in the "warm" half of the scale (≥ 0.5). */
    public final boolean isWarm;

    /** True only in the hottest zones (SUBTROPICAL / TROPICAL). */
    public final boolean isHot;

    // ── Precipitation ─────────────────────────────────────────────────────────

    /**
     * Normalised rainfall in [0, 1].  Derived from the zone's rainfall modifier,
     * with a small latitude-based gradient applied so rainfall peaks in temperate
     * zones and drops toward the poles and tropics.
     */
    public final float rainfallAmount;

    /** If true, precipitation falls as snow rather than rain. */
    public final boolean precipitatesAsSnow;

    /** If true, thunderstorms can occur (zone flag + rainfall threshold). */
    public final boolean thunderstormsAllowed;

    // ── Wind ─────────────────────────────────────────────────────────────────

    /**
     * Wind strength in [0, 1]. Polar and subarctic zones have high base wind;
     * tropical zones have moderate wind from trade-wind simulation.
     */
    public final float windStrength;

    /**
     * Apparent wind chill modifier: how much the effective cold feels amplified.
     * 1.0 = no chill beyond raw temperature; > 1.0 = extra cold perceived.
     */
    public final float windChillFactor;

    // ── Vegetation / growth ───────────────────────────────────────────────────

    /**
     * Plant growth rate modifier.  1.0 = normal vanilla speed;
     * values < 1.0 slow growth (cold climates), > 1.0 speed it up (tropical).
     */
    public final float vegetationGrowthRate;

    /**
     * Foliage colour tint category, useful for shader/texture overrides:
     * 0 = white/grey (polar snow), 1 = dark green (north), 2 = vivid green (temperate),
     * 3 = golden/olive (subtropical), 4 = bright lime-green / tropical.
     */
    public final int foliageTintCategory;

    // ── Survival hazards ─────────────────────────────────────────────────────

    /**
     * How quickly an unprotected player loses body heat per second, in arbitrary
     * "cold damage" units.  0 = no passive cold damage; values scale up toward
     * the Polar zone.  Callers should apply armour / fire resistance reductions.
     */
    public final float passiveColdDamageRate;

    /**
     * How quickly an unprotected player gains heat exhaustion per second.
     * 0 = no heat damage; increases in Subtropical / Tropical zones.
     */
    public final float passiveHeatDamageRate;

    // ── Package-private constructor (built by ClimateSystem) ──────────────────

    ClimateFeatures(
            ClimateZone zone,
            int worldZ,
            float normalizedTemperature,
            boolean isFreezing,
            boolean isWarm,
            boolean isHot,
            float rainfallAmount,
            boolean precipitatesAsSnow,
            boolean thunderstormsAllowed,
            float windStrength,
            float windChillFactor,
            float vegetationGrowthRate,
            int foliageTintCategory,
            float passiveColdDamageRate,
            float passiveHeatDamageRate
    ) {
        this.zone                   = zone;
        this.worldZ                 = worldZ;
        this.normalizedTemperature  = normalizedTemperature;
        this.isFreezing             = isFreezing;
        this.isWarm                 = isWarm;
        this.isHot                  = isHot;
        this.rainfallAmount         = rainfallAmount;
        this.precipitatesAsSnow     = precipitatesAsSnow;
        this.thunderstormsAllowed   = thunderstormsAllowed;
        this.windStrength           = windStrength;
        this.windChillFactor        = windChillFactor;
        this.vegetationGrowthRate   = vegetationGrowthRate;
        this.foliageTintCategory    = foliageTintCategory;
        this.passiveColdDamageRate  = passiveColdDamageRate;
        this.passiveHeatDamageRate  = passiveHeatDamageRate;
    }

    // ── Convenience helpers ───────────────────────────────────────────────────

    /** True if passive cold damage is non-trivial (> 0.01 units/sec). */
    public boolean isFreezingCold() { return passiveColdDamageRate > 0.01f; }

    /** True if passive heat damage is non-trivial (> 0.01 units/sec). */
    public boolean isScorchingHot() { return passiveHeatDamageRate > 0.01f; }

    /** True if the zone supports any precipitation at all. */
    public boolean hasPrecipitation() { return rainfallAmount > 0.05f; }

    @Override
    public String toString() {
        return String.format(
                "ClimateFeatures{zone=%s, z=%d, temp=%.2f, rain=%.2f, wind=%.2f}",
                zone.name(), worldZ, normalizedTemperature, rainfallAmount, windStrength
        );
    }
}