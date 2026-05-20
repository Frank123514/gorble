package net.got.mixin;

import net.got.climate.SeasonCache;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatherEffectRenderer.class)
public class WeatherEffectRendererMixin
{
    /**
     * During winter, redirects rain particles to snow particles.
     *
     * Targets WeatherEffectRenderer.getPrecipitationAt — the dedicated method
     * that determines what particle type to render at each column. This is the
     * same approach used by Serene Seasons (MixinWeatherEffectRenderer).
     *
     * Only RAIN → SNOW. Dry biomes (NONE) are untouched.
     * This class is client-only so no server-side gate is needed.
     */
    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true, remap = false)
    public void gotSeason_forceSnowInWinter(Level level, BlockPos pos, CallbackInfoReturnable<Biome.Precipitation> cir)
    {
        if (!SeasonCache.get().isWinter()) return;

        Biome.Precipitation precipitation = level.getBiome(pos).value().getPrecipitationAt(pos, level.getSeaLevel());
        if (precipitation == Biome.Precipitation.RAIN)
        {
            cir.setReturnValue(Biome.Precipitation.SNOW);
        }
    }
}