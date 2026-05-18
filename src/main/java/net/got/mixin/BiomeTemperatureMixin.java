package net.got.mixin;

import net.got.climate.ClimateSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides the per-position temperature lookup in {@link Biome} so that
 * vanilla's own precipitation logic (snow vs rain) responds to latitude
 * instead of the biome's baked-in base temperature.
 *
 * <h3>1.21.4 method signature</h3>
 * <p>In 1.21.4 the relevant method is:
 * <pre>
 *   public float getTemperature(BlockPos pos, float baseTemperature)
 * </pre>
 * It takes the biome's own base temperature as a second argument and applies
 * a height-based modifier before returning.  We cancel it and return the
 * latitude temperature directly, bypassing both the biome base value and the
 * altitude adjustment (latitude already accounts for overall coldness).
 *
 * <h3>Registration</h3>
 * <p>Ensure {@code "BiomeTemperatureMixin"} is listed in the {@code "mixins"}
 * array of {@code got.mixins.json}.
 */
@Mixin(Biome.class)
public abstract class BiomeTemperatureMixin {

    @Inject(
            method = "getTemperature(Lnet/minecraft/core/BlockPos;F)F",
            at = @At("HEAD"),
            cancellable = true
    )
    private void got_overrideTemperatureByLatitude(BlockPos pos, float baseTemperature,
                                                   CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(ClimateSystem.getLatitudeTemperature(pos));
    }
}