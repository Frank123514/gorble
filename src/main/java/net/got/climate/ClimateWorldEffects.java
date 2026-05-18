package net.got.climate;

import net.got.GotMod;
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * Climate-driven world utility class.
 *
 * <h3>Snow and ice</h3>
 * <p>Snow accumulation and water freezing are now handled entirely by vanilla —
 * no artificial block placement is done here.  {@code BiomeTemperatureMixin}
 * overrides {@code Biome#getTemperature(BlockPos)} per-position using
 * {@link ClimateSystem#getLatitudeTemperature}, so vanilla's own weather tick
 * sees a temperature below {@code 0.15f} in cold latitudes and naturally
 * produces snow instead of rain, freezes exposed water, etc.
 *
 * <h3>Vegetation growth</h3>
 * <p>Exposes {@link #getGrowthRateModifier(int)} for use in crop and plant
 * {@code randomTick} overrides to scale growth speed by latitude.
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class ClimateWorldEffects {

    // ── Vegetation growth modifier ────────────────────────────────────────────

    /**
     * Returns the climate-adjusted growth rate multiplier for the given world Z.
     *
     * <p>Use this in crop/plant tick overrides:
     * <pre>{@code
     * float modifier = ClimateWorldEffects.getGrowthRateModifier(pos.getZ());
     * if (random.nextFloat() < BASE_CHANCE * modifier) {
     *     // grow one stage
     * }
     * }</pre>
     *
     * @param worldZ the block Z coordinate of the plant
     * @return growth rate multiplier; 0.0 = no growth (Polar), up to ~1.5 (Tropical)
     */
    public static float getGrowthRateModifier(int worldZ) {
        return ClimateSystem.getFeaturesAt(worldZ).vegetationGrowthRate;
    }

    /** @see #getGrowthRateModifier(int) */
    public static float getGrowthRateModifier(double worldZ) {
        return getGrowthRateModifier((int) worldZ);
    }

    // ── No instances ─────────────────────────────────────────────────────────

    private ClimateWorldEffects() {}
}