package net.got.mixin;

import net.got.climate.SeasonCache;
import net.got.climate.WinterWeatherContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {

    /**
     * During winter, overrides every biome's effective temperature to -1.0f.
     *
     * Two different rules depending on side:
     *
     *   CLIENT — always override during winter. This is what was broken before:
     *   the client calls getPrecipitationAt independently for rendering, so
     *   without this it would show rain even while snow falls server-side.
     *
     *   SERVER — only override for chunks within a player's view distance
     *   (gated by WinterWeatherContext, set by ServerLevelMixin). Without this
     *   gate the server overrides temperature on every loaded chunk and
     *   instantly blankets the entire loaded world in snow.
     */
    @Inject(method = "getHeightAdjustedTemperature", at = @At("HEAD"), cancellable = true, remap = false)
    private void gotWinter_freezeTemperature(BlockPos pos, int fluidHeight, CallbackInfoReturnable<Float> cir) {
        if (!SeasonCache.get().isWinter()) return;
        if (FMLEnvironment.dist == Dist.CLIENT || WinterWeatherContext.isChunkRendered()) {
            cir.setReturnValue(-1.0f);
        }
    }
}
