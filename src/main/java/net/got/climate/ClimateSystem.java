package net.got.climate;

import net.got.GotMod;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.core.BlockPos;

/**
 * Central API for the latitude-based climate system.
 */
public final class ClimateSystem {

    private static final float POLAR_COLD_DAMAGE    = 0.5f;
    private static final float TROPICAL_HEAT_DAMAGE = 0.3f;
    private static final float POLAR_WIND           = 0.95f;
    private static final float TROPICAL_WIND        = 0.45f;

    // ── Boundary noise ────────────────────────────────────────────────────────

    private static final ImprovedNoise BOUNDARY_NOISE =
            new ImprovedNoise(RandomSource.create(937_462_847L));

    /** Lower = broader sweeping curves, higher = tighter wiggles. */
    private static final double BOUNDARY_FREQ = 1.0 / 600.0;

    /** Max east-west boundary deviation in blocks. */
    private static final float BOUNDARY_WANDER = 800f;

    // ── Public API ────────────────────────────────────────────────────────────

    public static ClimateZone getZoneAt(int worldZ)       { return ClimateZone.fromZ(worldZ); }
    public static ClimateZone getZoneAt(double worldZ)    { return ClimateZone.fromZ((int) worldZ); }
    public static ClimateZone getZoneFor(Entity entity)   { return getZoneAt(entity.getZ()); }

    public static ClimateFeatures getFeaturesAt(int worldZ) {
        ClimateZone zone = ClimateZone.fromZ(worldZ);
        float frac = latitudeFraction(worldZ);
        return buildFeatures(zone, worldZ, frac);
    }

    public static ClimateFeatures getFeaturesAt(double worldZ) { return getFeaturesAt((int) worldZ); }
    public static ClimateFeatures getFeaturesFor(Entity entity) { return getFeaturesAt(entity.getZ()); }

    // ── Latitude fraction ─────────────────────────────────────────────────────

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
        float zoneCount      = ClimateZone.values().length - 1;
        float baseTempNorm   = zone.ordinal() / zoneCount;
        float oneStep        = 1.0f / zoneCount;
        float normalizedTemp = clamp01(baseTempNorm + frac * oneStep);

        boolean isFreezing = normalizedTemp < 0.25f;
        boolean isWarm     = normalizedTemp >= 0.5f;
        boolean isHot      = normalizedTemp >= 0.75f;

        float rainfallAdj   = clamp01(zone.rainfallModifier + (frac - 0.5f) * 0.1f);
        boolean precipAsSnow    = normalizedTemp < 0.3f || zone.alwaysSnowing;
        boolean thundersAllowed = zone.thunderstormsAllowed && rainfallAdj > 0.4f;

        float windStrength = switch (zone) {
            case POLAR           -> lerp(POLAR_WIND, 0.75f, frac);
            case SUBARCTIC       -> lerp(0.75f, 0.55f, frac);
            case TEMPERATE_NORTH -> lerp(0.55f, 0.45f, frac);
            case RIVERLANDS      -> lerp(0.45f, 0.40f, frac);
            case TEMPERATE_SOUTH -> lerp(0.40f, 0.35f, frac);
            case SUBTROPICAL     -> lerp(0.35f, TROPICAL_WIND, frac);
            case TROPICAL        -> lerp(TROPICAL_WIND, 0.40f, frac);
        };

        float windChillFactor = 1.0f + (1.0f - normalizedTemp) * windStrength * 0.5f;

        float growthRate = switch (zone) {
            case POLAR           -> 0.0f;
            case SUBARCTIC       -> lerp(0.1f, 0.5f, frac);
            case TEMPERATE_NORTH -> lerp(0.7f, 0.9f, frac);
            case RIVERLANDS      -> lerp(0.9f, 1.0f, frac);
            case TEMPERATE_SOUTH -> lerp(1.0f, 1.1f, frac);
            case SUBTROPICAL     -> lerp(1.1f, 1.3f, frac);
            case TROPICAL        -> lerp(1.3f, 1.5f, frac);
        };

        int foliageTint = switch (zone) {
            case POLAR           -> 0;
            case SUBARCTIC       -> 1;
            case TEMPERATE_NORTH -> 2;
            case RIVERLANDS      -> 2;
            case TEMPERATE_SOUTH -> 2;
            case SUBTROPICAL     -> 3;
            case TROPICAL        -> 4;
        };

        float coldDamage = switch (zone) {
            case POLAR     -> lerp(POLAR_COLD_DAMAGE, POLAR_COLD_DAMAGE * 0.6f, frac);
            case SUBARCTIC -> lerp(POLAR_COLD_DAMAGE * 0.5f, 0.0f, frac);
            default        -> 0.0f;
        };

        float heatDamage = switch (zone) {
            case SUBTROPICAL -> lerp(0.0f, TROPICAL_HEAT_DAMAGE * 0.4f, frac);
            case TROPICAL    -> lerp(TROPICAL_HEAT_DAMAGE * 0.5f, TROPICAL_HEAT_DAMAGE, frac);
            default          -> 0.0f;
        };

        return new ClimateFeatures(
                zone, worldZ, normalizedTemp, isFreezing, isWarm, isHot,
                rainfallAdj, precipAsSnow, thundersAllowed,
                windStrength, windChillFactor, growthRate, foliageTint,
                coldDamage, heatDamage
        );
    }

    // ── Zone edge helpers ─────────────────────────────────────────────────────

    public static int zoneNorthEdge(ClimateZone zone) {
        return switch (zone) {
            case POLAR           -> Integer.MIN_VALUE / 2;
            case SUBARCTIC       -> ClimateZone.POLAR_NORTH_MAX + 1;
            case TEMPERATE_NORTH -> ClimateZone.SUBARCTIC_MAX + 1;
            case RIVERLANDS      -> ClimateZone.TEMPERATE_NORTH_MAX + 1;
            case TEMPERATE_SOUTH -> ClimateZone.RIVERLANDS_MAX + 1;
            case SUBTROPICAL     -> ClimateZone.TEMPERATE_SOUTH_MAX + 1;
            case TROPICAL        -> ClimateZone.SUBTROPICAL_MAX + 1;
        };
    }

    public static int zoneSouthEdge(ClimateZone zone) {
        return switch (zone) {
            case POLAR           -> ClimateZone.POLAR_NORTH_MAX;
            case SUBARCTIC       -> ClimateZone.SUBARCTIC_MAX;
            case TEMPERATE_NORTH -> ClimateZone.TEMPERATE_NORTH_MAX;
            case RIVERLANDS      -> ClimateZone.RIVERLANDS_MAX;
            case TEMPERATE_SOUTH -> ClimateZone.TEMPERATE_SOUTH_MAX;
            case SUBTROPICAL     -> ClimateZone.SUBTROPICAL_MAX;
            case TROPICAL        -> Integer.MAX_VALUE / 2;
        };
    }

    // ── Math utilities ────────────────────────────────────────────────────────

    private static float clamp01(float v) { return Math.max(0f, Math.min(1f, v)); }
    private static float lerp(float a, float b, float t) { return a + (b - a) * clamp01(t); }

    // ── Temperature lookups ───────────────────────────────────────────────────

    /**
     * Plain Z-only lookup — no noise. Used for zone detection, HUD, damage etc.
     */
    public static float getLatitudeTemperature(int worldZ) {
        ClimateZone zone = ClimateZone.fromZ(worldZ);
        float frac = latitudeFraction(worldZ);
        return switch (zone) {
            case POLAR           -> lerp(0.00f, 0.05f, frac);
            case SUBARCTIC       -> lerp(0.05f, 0.14f, frac);
            case TEMPERATE_NORTH -> lerp(0.15f, 0.35f, frac);
            case RIVERLANDS      -> lerp(0.35f, 0.55f, frac);
            case TEMPERATE_SOUTH -> lerp(0.55f, 0.75f, frac);
            case SUBTROPICAL     -> lerp(0.75f, 0.95f, frac);
            case TROPICAL        -> lerp(1.00f, 2.00f, frac);
        };
    }

    public static float getLatitudeTemperature(double worldZ) {
        return getLatitudeTemperature((int) worldZ);
    }

    /**
     * BlockPos lookup — applies X-based noise to Z so zone boundaries meander
     * naturally. Used by weather rendering and snow block placement.
     */
    public static float getLatitudeTemperature(BlockPos pos) {
        double noise = BOUNDARY_NOISE.noise(pos.getX() * BOUNDARY_FREQ, 0, 0);
        int noisyZ = pos.getZ() + (int)(noise * BOUNDARY_WANDER);
        return getLatitudeTemperature(noisyZ);
    }

    // ── Debug ─────────────────────────────────────────────────────────────────

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

    private ClimateSystem() {}
}