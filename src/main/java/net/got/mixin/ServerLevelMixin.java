package net.got.mixin;

import net.got.climate.SeasonCache;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Redirects Biome.getPrecipitationAt inside ServerLevel.tickPrecipitation.
 *
 * Mirrors Serene Seasons exactly: instead of forcing SNOW, we adjust the biome
 * temperature by -0.8 in winter so that coldEnoughToSnow() returns true naturally.
 * This means tickPrecipitation (which vanilla only calls during active weather)
 * will place snow/ice gradually — no instant mass coverage on season change.
 *
 * remap=false — see WeatherEffectRendererMixin for explanation.
 */
@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    private static final float WINTER_TEMP_ADJUSTMENT = -0.8f;

    @Redirect(
        method = "tickPrecipitation",
        at = @At(value = "INVOKE",
                 target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;I)Lnet/minecraft/world/level/biome/Biome$Precipitation;"),
        remap = false
    )
    public Biome.Precipitation gotSeason_tickIceAndSnow_getPrecipitationAt(
            Biome biome, BlockPos pos, int seaLevel)
    {
        if (!biome.hasPrecipitation()) return Biome.Precipitation.NONE;

        if (!SeasonCache.get().isWinter()) {
            return biome.getPrecipitationAt(pos, seaLevel);
        }

        // Adjust temperature by -0.8 (same as Serene Seasons default winter adjustment).
        // A typical plains biome (temp 0.8) becomes 0.0, which is below the 0.15
        // rain threshold, so coldEnoughToSnow returns true → SNOW.
        // Desert biomes (base temp > 0.8) are excluded per SS logic.
        float baseTemp = biome.getBaseTemperature();
        float adjustedTemp;
        if (baseTemp <= 0.8f) {
            adjustedTemp = Mth.clamp(biome.getTemperature(pos, seaLevel) + WINTER_TEMP_ADJUSTMENT, -0.5f, 2.0f);
        } else {
            adjustedTemp = biome.getTemperature(pos, seaLevel);
        }

        boolean shouldSnow = adjustedTemp < 0.15f;
        return shouldSnow ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN;
    }
}
