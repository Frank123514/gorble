package net.got.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides the worldgen snowline to Y=140, regardless of biome base temperature.
 *
 * Vanilla's SnowAndFreezeFeature.place() scans the surface of a chunk and places
 * snow/ice wherever the biome temperature is cold enough. Cold biomes (like north_mountains)
 * place snow all the way down to sea level. We cancel the entire feature for chunks
 * whose surface is below our target snowline, letting only high-altitude terrain get snow.
 *
 * We inject at the top of place() and cancel if the origin Y is below SNOW_MIN_Y.
 * SnowAndFreezeFeature is called per-chunk with the chunk's base position, so
 * checking the context origin Y is sufficient to gate by elevation.
 */
@Mixin(SnowAndFreezeFeature.class)
public class SnowAndFreezeMixin {

    private static final int SNOW_MIN_Y = 140;

    @Inject(method = "place", at = @At("HEAD"), cancellable = true, remap = false)
    private void gotSnowline_place(
            FeaturePlaceContext<NoneFeatureConfiguration> context,
            CallbackInfoReturnable<Boolean> cir) {
        BlockPos origin = context.origin();
        if (origin.getY() < SNOW_MIN_Y) {
            cir.setReturnValue(false);
        }
    }
}
