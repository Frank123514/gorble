package net.got.mixin;

import net.got.climate.ClimateSystem;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WeatherEffectRenderer.class, remap = false)
public abstract class WeatherRendererMixin {

    @Inject(
            method = "getPrecipitationAt",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void got_overridePrecipitationByLatitude(Level level, BlockPos pos, CallbackInfoReturnable<Biome.Precipitation> cir) {
        float latTemp = ClimateSystem.getLatitudeTemperature(pos);
        if (latTemp < 0.15f) {
            cir.setReturnValue(Biome.Precipitation.SNOW);
        } else if (latTemp > 1.0f) {
            cir.setReturnValue(Biome.Precipitation.NONE);
        }
        // else fall through to vanilla (returns RAIN)
    }
}