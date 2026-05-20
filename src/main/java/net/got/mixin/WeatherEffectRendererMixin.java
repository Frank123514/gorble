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
     * remap=false is required: this project uses NeoGradle with named/parchment
     * mappings but does NOT wire SRG mappings into the Mixin annotation processor.
     * The mod runs in the named environment at runtime, so method names are
     * already deobfuscated — remap=false tells the AP to skip the SRG lookup
     * and use the named method name directly.
     */
    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true, remap = false)
    public void gotSeason_forceSnowInWinter(Level level, BlockPos pos,
                                            CallbackInfoReturnable<Biome.Precipitation> cir)
    {
        if (!SeasonCache.get().isWinter()) return;

        Biome.Precipitation precipitation =
                level.getBiome(pos).value().getPrecipitationAt(pos, level.getSeaLevel());
        if (precipitation == Biome.Precipitation.RAIN)
        {
            cir.setReturnValue(Biome.Precipitation.SNOW);
        }
    }
}
