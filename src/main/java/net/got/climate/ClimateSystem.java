package net.got.climate;

import net.got.GotMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.core.BlockPos;

/**
 * Central API for the latitude-based climate system.
 *
 * <p>All callers should use the static methods here rather than constructing
 * {@link ClimateZone} or {@link ClimateFeatures} directly.
 *
 * <h3>Coordinate convention</h3>
 * <ul>
 *   <li>Negative Z = north (toward the Frostfangs / Wall)
 *   <li>Positive Z = south (toward Dorne / Essos)
 * </ul>
 *
 * <h3>Design notes</h3>
 * <p>Feature values are computed from raw zone parameters plus a smooth
 * <em>latitude fraction</em> (how far through the zone the position sits),
 * so there are no hard jumps at zone boundaries.  The fraction is calculated
 * by {@link #latitudeFraction(int)} and is always in [0, 1] where 0 = at the
 * northern edge of the zone and 1 = at the southern edge.
 *
 * <p>Results are <em>not</em> cached — call-site caching (e.g. once per player
 * tick at 20-tick intervals) is the callers' responsibility.
 */
public final class ClimateSystem {

    // ── Tuning constants ──────────────────────────────────────────────────────

    /** Minimum normalised temperature (absolute coldest = Polar zone centre). */
    private static final float TEMP_MIN = 0.0f;

    /** Maximum normalised temperature (absolute hottest = Tropical zone centre). */
    private static final float TEMP_MAX = 1.0f;

    /**
     * Passive cold damage rate for the Polar zone (units/sec).
     * Scales linearly to 0 at TEMPERATE_NORTH.
     */
    private static final float POLAR_COLD_DAMAGE = 0.5f;

    /**
     * Passive heat damage rate for the Tropical zone (units/sec).
     * Scales linearly to 0 at SUBTROPICAL.
     */
    private static final float TROPICAL_HEAT_DAMAGE = 0.3f;

    /** Base wind in the Polar zone — strongest in the world. */
    private static final float POLAR_WIND = 0.95f;

    /** Base wind in the Tropical zone (trade winds). */
    private static final float TROPICAL_WIND = 0.45f;

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Returns the {@link ClimateZone} for the given world-block Z coordinate.
     *
     * @param worldZ block Z position
     * @return the zone, never {@code null}
     */
    public static ClimateZone getZoneAt(int worldZ) {
        return ClimateZone.fromZ(worldZ);
    }

    /** @see #getZoneAt(int) */
    public static ClimateZone getZoneAt(double worldZ) {
        return ClimateZone.fromZ((int) worldZ);
    }

    /** Returns the zone the player is currently standing in. */
    public static ClimateZone getZoneFor(Entity entity) {
        return getZoneAt(entity.getZ());
    }

    /**
     * Returns the full {@link ClimateFeatures} snapshot for a given Z coordinate.
     *
     * <p>This is the primary method callers should use when they need more than
     * just the zone name (e.g. temperature, rainfall, damage rates).
     *
     * @param worldZ block Z position
     * @return an immutable features snapshot
     */
    public static ClimateFeatures getFeaturesAt(int worldZ) {
        ClimateZone zone = ClimateZone.fromZ(worldZ);
        float frac = latitudeFraction(worldZ);   // 0 = zone north edge, 1 = zone south edge
        return buildFeatures(zone, worldZ, frac);
    }

    /** @see #getFeaturesAt(int) */
    public static ClimateFeatures getFeaturesAt(double worldZ) {
        return getFeaturesAt((int) worldZ);
    }

    /** Returns features for the entity's current position. */
    public static ClimateFeatures getFeaturesFor(Entity entity) {
        return getFeaturesAt(entity.getZ());
    }

    // ── Latitude fraction ─────────────────────────────────────────────────────

    /**
     * Computes how far through its zone a given Z coordinate sits.
     *
     * <p>Returns a value in [0, 1] where 0 = at the northern (top) edge of the
     * zone and 1 = at the southern (bottom) edge.  This is used to smoothly
     * interpolate feature values so there are no abrupt changes at zone borders.
     *
     * @param worldZ block Z coordinate
     * @return fraction in [0, 1]
     */
    public static float latitudeFraction(int worldZ) {
        ClimateZone zone = ClimateZone.fromZ(worldZ);
        int zoneMin = zoneNorthEdge(zone);
        int zoneMax = zoneSouthEdge(zone);
        int span = zoneMax - zoneMin;
        if (span == 0) return 0.5f;
        return clamp01((float)(worldZ - zoneMin) / (float) span);
    }

    // ── Feature builder ───────────────────────────────────────────────────────

    private static ClimateFeatures buildFeatures(ClimateZone zone, int worldZ, float frac) {

        // ── Temperature ───────────────────────────────────────────────────────
        // Map zone ordinal (0=polar → 5=tropical) to [0,1], then blend with
        // the within-zone fraction for smooth transitions.
        float zoneCount    = ClimateZone.values().length - 1; // 5
        float baseTempNorm = zone.ordinal() / zoneCount;
        // Advance by a fraction of one zone step to reach the south edge
        float oneStep      = 1.0f / zoneCount;
        float normalizedTemp = clamp01(baseTempNorm + frac * oneStep);

        boolean isFreezing = normalizedTemp < 0.25f;
        boolean isWarm     = normalizedTemp >= 0.5f;
        boolean isHot      = normalizedTemp >= 0.75f;

        // ── Rainfall ──────────────────────────────────────────────────────────
        // Rainfall peaks in the temperate zones; drops off toward poles and tropics.
        // We compute a simple triangle centred on TEMPERATE_SOUTH.
        float rainfallBase = zone.rainfallModifier;
        // Small gradient: blend slightly toward neighbour zone values
        float rainfallAdj  = clamp01(rainfallBase + (frac - 0.5f) * 0.1f);

        boolean precipAsSnow    = normalizedTemp < 0.3f || zone.alwaysSnowing;
        boolean thundersAllowed = zone.thunderstormsAllowed && rainfallAdj > 0.4f;

        // ── Wind ──────────────────────────────────────────────────────────────
        // Wind is highest at the poles, drops through temperate zones, picks up
        // slightly again in the tropics (trade winds / monsoons).
        float windStrength;
        switch (zone) {
            case POLAR       -> windStrength = lerp(POLAR_WIND, 0.75f, frac);
            case SUBARCTIC   -> windStrength = lerp(0.75f, 0.55f, frac);
            case TEMPERATE_NORTH -> windStrength = lerp(0.55f, 0.40f, frac);
            case TEMPERATE_SOUTH -> windStrength = lerp(0.40f, 0.35f, frac);
            case SUBTROPICAL -> windStrength = lerp(0.35f, TROPICAL_WIND, frac);
            case TROPICAL    -> windStrength = lerp(TROPICAL_WIND, 0.40f, frac);
            default          -> windStrength = 0.4f;
        }

        // Wind chill amplifies perceived cold; only meaningful in cold zones.
        float windChillFactor = 1.0f + (1.0f - normalizedTemp) * windStrength * 0.5f;

        // ── Vegetation ────────────────────────────────────────────────────────
        float growthRate = switch (zone) {
            case POLAR           -> 0.0f;   // nothing grows
            case SUBARCTIC       -> lerp(0.1f, 0.5f, frac);
            case TEMPERATE_NORTH -> lerp(0.7f, 0.9f, frac);
            case TEMPERATE_SOUTH -> lerp(0.9f, 1.1f, frac);
            case SUBTROPICAL     -> lerp(1.1f, 1.3f, frac);
            case TROPICAL        -> lerp(1.3f, 1.5f, frac);
        };

        int foliageTint = switch (zone) {
            case POLAR           -> 0;  // white / grey
            case SUBARCTIC       -> 1;  // dark green
            case TEMPERATE_NORTH -> 2;  // vivid green
            case TEMPERATE_SOUTH -> 2;  // vivid green
            case SUBTROPICAL     -> 3;  // golden-olive
            case TROPICAL        -> 4;  // bright lime
        };

        // ── Survival hazards ─────────────────────────────────────────────────
        // Cold damage: only in POLAR and SUBARCTIC
        float coldDamage = switch (zone) {
            case POLAR     -> lerp(POLAR_COLD_DAMAGE, POLAR_COLD_DAMAGE * 0.6f, frac);
            case SUBARCTIC -> lerp(POLAR_COLD_DAMAGE * 0.5f, 0.0f, frac);
            default        -> 0.0f;
        };

        // Heat damage: only in SUBTROPICAL and TROPICAL
        float heatDamage = switch (zone) {
            case SUBTROPICAL -> lerp(0.0f, TROPICAL_HEAT_DAMAGE * 0.4f, frac);
            case TROPICAL    -> lerp(TROPICAL_HEAT_DAMAGE * 0.5f, TROPICAL_HEAT_DAMAGE, frac);
            default          -> 0.0f;
        };

        return new ClimateFeatures(
                zone,
                worldZ,
                normalizedTemp,
                isFreezing,
                isWarm,
                isHot,
                rainfallAdj,
                precipAsSnow,
                thundersAllowed,
                windStrength,
                windChillFactor,
                growthRate,
                foliageTint,
                coldDamage,
                heatDamage
        );
    }

    // ── Zone edge helpers ─────────────────────────────────────────────────────

    /**
     * Returns the world-Z of the northern (lower Z) edge of the zone.
     * The Polar zone's north edge is set to an extreme value.
     */
    public static int zoneNorthEdge(ClimateZone zone) {
        return switch (zone) {
            case POLAR           -> Integer.MIN_VALUE / 2;
            case SUBARCTIC       -> ClimateZone.POLAR_NORTH_MAX + 1;
            case TEMPERATE_NORTH -> ClimateZone.SUBARCTIC_MAX + 1;
            case TEMPERATE_SOUTH -> ClimateZone.TEMPERATE_NORTH_MAX + 1;
            case SUBTROPICAL     -> ClimateZone.TEMPERATE_SOUTH_MAX + 1;
            case TROPICAL        -> ClimateZone.SUBTROPICAL_MAX + 1;
        };
    }

    /**
     * Returns the world-Z of the southern (higher Z) edge of the zone.
     * The Tropical zone's south edge is set to an extreme value.
     */
    public static int zoneSouthEdge(ClimateZone zone) {
        return switch (zone) {
            case POLAR           -> ClimateZone.POLAR_NORTH_MAX;
            case SUBARCTIC       -> ClimateZone.SUBARCTIC_MAX;
            case TEMPERATE_NORTH -> ClimateZone.TEMPERATE_NORTH_MAX;
            case TEMPERATE_SOUTH -> ClimateZone.TEMPERATE_SOUTH_MAX;
            case SUBTROPICAL     -> ClimateZone.SUBTROPICAL_MAX;
            case TROPICAL        -> Integer.MAX_VALUE / 2;
        };
    }

    // ── Math utilities ────────────────────────────────────────────────────────

    private static float clamp01(float v) {
        return Math.max(0f, Math.min(1f, v));
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * clamp01(t);
    }

    // ── Vanilla biome temperature override ───────────────────────────────────

    /**
     * Returns the climate-adjusted biome temperature for a given world Z coordinate,
     * expressed in vanilla's biome temperature scale:
     * <ul>
     *   <li>{@code < 0.15} — snow falls instead of rain (frozen biome threshold)
     *   <li>{@code 0.15 – 0.95} — rain falls normally
     *   <li>{@code > 1.0} — hot / desert, no precipitation
     * </ul>
     *
     * <p>This value is intended to <em>replace</em> the base temperature returned by
     * {@link net.minecraft.world.level.biome.Biome#getBaseTemperature()} for any
     * position queried through the climate system, so that vanilla's own precipitation
     * logic (snow vs rain) responds naturally to latitude rather than relying on
     * artificial block placement.
     *
     * <p>The mapping is:
     * <pre>
     *  POLAR           → 0.0   (deep freeze — always snow)
     *  SUBARCTIC       → 0.05–0.14  (below snow threshold → snow)
     *  TEMPERATE_NORTH → 0.15–0.45  (just above threshold → rain, cold)
     *  TEMPERATE_SOUTH → 0.45–0.75  (mild — rain)
     *  SUBTROPICAL     → 0.75–0.95  (warm — rain / no precip near edge)
     *  TROPICAL        → 1.0–2.0   (hot — no precipitation)
     * </pre>
     *
     * @param worldZ block Z coordinate
     * @return vanilla biome temperature float
     */
    public static float getLatitudeTemperature(int worldZ) {
        ClimateZone zone = ClimateZone.fromZ(worldZ);
        float frac = latitudeFraction(worldZ);

        return switch (zone) {
            // Permanently below vanilla's snow threshold of 0.15
            case POLAR           -> lerp(0.00f, 0.05f, frac);
            case SUBARCTIC       -> lerp(0.05f, 0.14f, frac);
            // Rain zone — above 0.15 so vanilla produces rain, not snow
            case TEMPERATE_NORTH -> lerp(0.15f, 0.45f, frac);
            case TEMPERATE_SOUTH -> lerp(0.45f, 0.75f, frac);
            // Warm — approaching the point where precipitation tapers off
            case SUBTROPICAL     -> lerp(0.75f, 0.95f, frac);
            // Hot — vanilla treats temps > ~1.0 as desert (no precip)
            case TROPICAL        -> lerp(1.00f, 2.00f, frac);
        };
    }

    /** @see #getLatitudeTemperature(int) */
    public static float getLatitudeTemperature(double worldZ) {
        return getLatitudeTemperature((int) worldZ);
    }

    /** @see #getLatitudeTemperature(int) */
    public static float getLatitudeTemperature(BlockPos pos) {
        return getLatitudeTemperature(pos.getZ());
    }

    // ── Debug helper ──────────────────────────────────────────────────────────

    /**
     * Logs a human-readable summary of the climate at the given Z to the mod logger.
     * Intended for in-game debug commands.
     *
     * @param worldZ block Z coordinate to inspect
     */
    public static void debugLog(int worldZ) {
        ClimateFeatures f = getFeaturesAt(worldZ);
        GotMod.LOGGER.info(
                "[Climate] z={} zone={} temp={:.2f} rain={:.2f} wind={:.2f} snow={} thunder={} "
                        + "coldDmg={:.3f} heatDmg={:.3f} growth={:.2f} foliage={}",
                worldZ, f.zone.name(),
                f.normalizedTemperature, f.rainfallAmount, f.windStrength,
                f.precipitatesAsSnow, f.thunderstormsAllowed,
                f.passiveColdDamageRate, f.passiveHeatDamageRate,
                f.vegetationGrowthRate, f.foliageTintCategory
        );
    }

    // ── No instances ─────────────────────────────────────────────────────────

    private ClimateSystem() {}
}