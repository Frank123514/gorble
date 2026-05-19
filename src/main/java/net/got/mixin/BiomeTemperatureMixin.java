package net.got.mixin;

import net.got.climate.GotSeasonTemperature;
import net.got.climate.SeasonManager;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides Biome.getBaseTemperature() so that every vanilla system that reads
 * biome temperature — precipitation placement, snow/ice checks, particle
 * rendering, all of it — sees the season-adjusted value automatically.
 *
 * Winter returns 0.0 (below the 0.15 freeze threshold for every biome).
 * Summer returns 2.0 (well above it for every biome).
 * Spring/Autumn return the real base temperature ± a small offset.
 */
@Mixin(value = Biome.class, remap = false)
public abstract class BiomeTemperatureMixin {

    @Inject(method = "getBaseTemperature()F", at = @At("HEAD"), cancellable = true, remap = false)
    private void got_overrideBaseTemperature(CallbackInfoReturnable<Float> cir) {
        switch (SeasonManager.getCurrentSeason()) {
            case WINTER -> cir.setReturnValue(0.0f);
            case SUMMER -> cir.setReturnValue(2.0f);
            default     -> { /* spring/autumn — let vanilla return the real value */ }
        }
    }
}
