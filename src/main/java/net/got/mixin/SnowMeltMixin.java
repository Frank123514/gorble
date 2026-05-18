package net.got.mixin;

import net.got.climate.ClimateSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents vanilla's snow-melt random tick from removing snow in cold latitudes.
 *
 * <h3>Why this is needed</h3>
 * The GoT mod uses custom biomes that are not configured as vanilla "snowy" biomes
 * (their biome temperature value is not set to {@code < 0.15}).  Vanilla's
 * {@code SnowLayerBlock.randomTick} calls {@code biome.warmEnoughToRain(pos)} which
 * reads the raw biome temperature, ignoring our latitude system.  In any biome that
 * vanilla considers "warm" the snow melts between rain events, resetting every column
 * back to 1 layer (or zero) no matter how long it has been snowing.
 *
 * <h3>Fix</h3>
 * If the latitude temperature at the snow block's position is below the snow
 * threshold ({@code < 0.15}), cancel the randomTick entirely so the snow block
 * cannot melt.  In warm latitudes we fall through and let vanilla melt snow normally.
 */
@Mixin(SnowLayerBlock.class)
public abstract class SnowMeltMixin {

    @Inject(
            method = "randomTick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void got_preventMeltInColdLatitude(
            BlockState state, ServerLevel level, BlockPos pos, RandomSource random,
            CallbackInfo ci) {
        float latTemp = ClimateSystem.getLatitudeTemperature(pos);
        if (latTemp < 0.15f) {
            // Cancel the randomTick — snow does not melt in cold latitudes.
            ci.cancel();
        }
        // else: fall through; vanilla melts snow in warm/temperate areas as expected.
    }
}