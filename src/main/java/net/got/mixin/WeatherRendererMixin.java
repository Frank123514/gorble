package net.got.mixin;

import net.got.climate.SeasonManager;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides client-side precipitation rendering to match the current GoT season.
 *
 * <ul>
 *   <li><b>Winter</b> — always render snow particles during a weather event.</li>
 *   <li><b>Summer</b> — always render rain particles.</li>
 *   <li><b>Spring / Autumn</b> — fall through to vanilla biome logic.</li>
 * </ul>
 */
@Mixin(value = WeatherEffectRenderer.class, remap = false)
public abstract class WeatherRendererMixin {

    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true, remap = false)
    private void got_overridePrecipitationBySeason(
            Level level, BlockPos pos,
            CallbackInfoReturnable<Biome.Precipitation> cir) {
        switch (SeasonManager.getCurrentSeason()) {
            case WINTER -> cir.setReturnValue(Biome.Precipitation.SNOW);
            case SUMMER -> cir.setReturnValue(Biome.Precipitation.RAIN);
            default     -> { /* fall through to vanilla biome logic */ }
        }
    }
}
