package net.got.climate;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;

/**
 * Season-driven precipitation logic.
 *
 * <ul>
 *   <li><b>Winter</b> — always cold enough to snow/freeze, every biome.</li>
 *   <li><b>Summer</b> — always warm enough to rain, every biome.</li>
 *   <li><b>Spring / Autumn</b> — biome base temperature ± a small offset,
 *       so naturally cold biomes may still snow while warm ones don't.</li>
 * </ul>
 */
public final class GotSeasonTemperature {

    private static final float SPRING_OFFSET = -0.05f;
    private static final float AUTUMN_OFFSET = -0.1f;
    private static final float FREEZE_THRESHOLD = 0.15f;

    public static boolean warmEnoughToRain(Biome biome, BlockPos pos, GotSeason season) {
        return switch (season) {
            case WINTER -> false; // always cold — snow and ice everywhere
            case SUMMER -> true;  // always warm — no snow or ice anywhere
            case SPRING -> biome.getBaseTemperature() + SPRING_OFFSET >= FREEZE_THRESHOLD;
            case AUTUMN -> biome.getBaseTemperature() + AUTUMN_OFFSET >= FREEZE_THRESHOLD;
        };
    }

    public static boolean coldEnoughToSnow(Biome biome, BlockPos pos, GotSeason season) {
        return !warmEnoughToRain(biome, pos, season);
    }

    public static Biome.Precipitation getPrecipitationType(Biome biome, BlockPos pos, GotSeason season) {
        if (!biome.hasPrecipitation()) return Biome.Precipitation.NONE;
        return coldEnoughToSnow(biome, pos, season)
                ? Biome.Precipitation.SNOW
                : Biome.Precipitation.RAIN;
    }

    private GotSeasonTemperature() {}
}
