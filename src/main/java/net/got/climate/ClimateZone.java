package net.got.climate;

/**
 * Latitude-based climate zones for the GoT world.
 *
 * <p>The world's Z-axis runs north–south: <b>more-negative Z = further north</b>
 * (toward the Land of Always Winter / Frostfangs), <b>more-positive Z = further south</b>
 * (toward Dorne / Essos). Zone boundaries are expressed as world-block Z coordinates
 * and are tunable via the constants at the top of this file.
 *
 * <h3>Zone layout (north → south)</h3>
 * <pre>
 *  Z ≤ POLAR_NORTH_MAX      → POLAR           (Land of Always Winter, Frostfangs)
 *  Z ≤ SUBARCTIC_MAX        → SUBARCTIC        (The Wall, Gift, Far North)
 *  Z ≤ TEMPERATE_NORTH_MAX  → TEMPERATE_NORTH  (The North, Riverlands)
 *  Z ≤ TEMPERATE_SOUTH_MAX  → TEMPERATE_SOUTH  (Westerlands, Vale, Stormlands)
 *  Z ≤ SUBTROPICAL_MAX      → SUBTROPICAL      (Reach, Crownlands, Narrow Sea coast)
 *  else                     → TROPICAL         (Dorne, Summer Islands, Essos desert)
 * </pre>
 *
 * <p>All environment calculations go through {@link ClimateSystem}, which is the
 * single public API. Do not hard-code Z ranges outside this file.
 */
public enum ClimateZone {

    // ── Zone declarations (north → south) ────────────────────────────────────

    /** Land of Always Winter, Frostfangs — perpetual blizzard, extreme cold. */
    POLAR(
            "Polar",
            -0.8f,   // base temperature modifier (applied on top of biome temp)
            0.0f,    // base rainfall modifier
            true,    // always snowing
            false,   // never thunderstorms
            "got.climate.polar"
    ),

    /** The Wall, the Gift, extreme North — harsh winters, sparse vegetation. */
    SUBARCTIC(
            "Subarctic",
            -0.4f,
            0.2f,
            false,
            false,
            "got.climate.subarctic"
    ),

    /** The North, Riverlands — temperate continental, cold winters, mild summers. */
    TEMPERATE_NORTH(
            "Temperate (North)",
            -0.1f,
            0.5f,
            false,
            true,
            "got.climate.temperate_north"
    ),

    /** Westerlands, Vale, Stormlands — mild maritime, storms common. */
    TEMPERATE_SOUTH(
            "Temperate (South)",
            0.1f,
            0.6f,
            false,
            true,
            "got.climate.temperate_south"
    ),

    /** Reach, Crownlands — warm, fertile, Mediterranean-like. */
    SUBTROPICAL(
            "Subtropical",
            0.3f,
            0.3f,
            false,
            false,
            "got.climate.subtropical"
    ),

    /** Dorne, Summer Islands — hot and arid or humid, minimal rainfall. */
    TROPICAL(
            "Tropical",
            0.6f,
            0.1f,
            false,
            false,
            "got.climate.tropical"
    );

    // ──────────────────────────────────────────────────────────────────────────
    //  Zone boundary constants (world-block Z coordinates)
    //  Negative Z = north, positive Z = south.
    //  Adjust these to match your world's actual coordinate layout.
    // ──────────────────────────────────────────────────────────────────────────

    /** Northern edge of the Subarctic zone (everything more north is Polar). */
    public static final int POLAR_NORTH_MAX       = -12_000;

    /** Northern edge of the Temperate North zone. */
    public static final int SUBARCTIC_MAX         =  -6_000;

    /** Northern edge of the Temperate South zone. */
    public static final int TEMPERATE_NORTH_MAX   =      0;

    /** Northern edge of the Subtropical zone. */
    public static final int TEMPERATE_SOUTH_MAX   =  6_000;

    /** Northern edge of the Tropical zone. */
    public static final int SUBTROPICAL_MAX       = 12_000;

    // ── Fields ────────────────────────────────────────────────────────────────

    /** Human-readable display name (e.g. for HUD or debug). */
    public final String displayName;

    /**
     * Temperature modifier added to (or subtracted from) the raw biome temperature.
     * Range roughly –1.0 (freezing) to +1.0 (scorching).
     */
    public final float temperatureModifier;

    /**
     * Rainfall modifier; 0.0 = arid, 1.0 = very wet.
     * Used when deciding precipitation type and frequency.
     */
    public final float rainfallModifier;

    /** If true, snow particles always fall regardless of biome temperature. */
    public final boolean alwaysSnowing;

    /** If true, thunderstorms can occur in this zone. */
    public final boolean thunderstormsAllowed;

    /** Translation key for the zone name (for localisation). */
    public final String translationKey;

    // ── Constructor ───────────────────────────────────────────────────────────

    ClimateZone(String displayName, float temperatureModifier, float rainfallModifier,
                boolean alwaysSnowing, boolean thunderstormsAllowed, String translationKey) {
        this.displayName          = displayName;
        this.temperatureModifier  = temperatureModifier;
        this.rainfallModifier     = rainfallModifier;
        this.alwaysSnowing        = alwaysSnowing;
        this.thunderstormsAllowed = thunderstormsAllowed;
        this.translationKey       = translationKey;
    }

    // ── Factory ───────────────────────────────────────────────────────────────

    /**
     * Returns the {@link ClimateZone} for the given world Z coordinate.
     *
     * @param worldZ the block Z position (negative = north, positive = south)
     * @return the matching zone, never {@code null}
     */
    public static ClimateZone fromZ(int worldZ) {
        if (worldZ <= POLAR_NORTH_MAX)     return POLAR;
        if (worldZ <= SUBARCTIC_MAX)       return SUBARCTIC;
        if (worldZ <= TEMPERATE_NORTH_MAX) return TEMPERATE_NORTH;
        if (worldZ <= TEMPERATE_SOUTH_MAX) return TEMPERATE_SOUTH;
        if (worldZ <= SUBTROPICAL_MAX)     return SUBTROPICAL;
        return TROPICAL;
    }

    /**
     * Convenience overload accepting a double (e.g. {@code entity.getZ()}).
     */
    public static ClimateZone fromZ(double worldZ) {
        return fromZ((int) worldZ);
    }

    // ── Queries ───────────────────────────────────────────────────────────────

    /** Returns true if this zone is colder than (north of) {@code other}. */
    public boolean isColderThan(ClimateZone other) {
        return this.ordinal() < other.ordinal();
    }

    /** Returns true if this zone is warmer than (south of) {@code other}. */
    public boolean isWarmerThan(ClimateZone other) {
        return this.ordinal() > other.ordinal();
    }

    /**
     * Effective temperature of this zone, useful for scalar comparisons.
     * Returns the {@link #temperatureModifier} as a convenient alias.
     */
    public float effectiveTemperature() {
        return temperatureModifier;
    }

    @Override
    public String toString() {
        return displayName + " [" + name() + "]";
    }
}