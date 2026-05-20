package net.got.mixin;

import net.got.climate.SeasonCache;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Adjusts biome temperature for snow/freeze checks in winter.
 *
 * IMPORTANT: shouldSnow is called from TWO places:
 *   1. ServerLevel.tickPrecipitation — only when it's raining/snowing (gated in vanilla)
 *   2. SnowAndFreezeFeature.place() during WORLDGEN — fires on every surface block
 *      when a chunk generates, regardless of weather or season change timing.
 *
 * We gate on (level instanceof ServerLevel) to block the worldgen path.
 * This matches Serene Seasons' generateSnowAndIce=false default: newly generated
 * chunks don't get instant snow coverage from worldgen; snow only accumulates
 * during live tickPrecipitation weather events.
 *
 * remap=false — see WeatherEffectRendererMixin for explanation.
 */
@Mixin(Biome.class)
public class BiomeMixin {

    private static final float WINTER_TEMP_ADJUSTMENT = -0.8f;

    @Inject(method = "shouldSnow", at = @At("HEAD"), cancellable = true, remap = false)
    public void gotSeason_shouldSnow(LevelReader level, BlockPos pos,
                                     CallbackInfoReturnable<Boolean> cir) {
        // Only intercept during live server ticking, not worldgen.
        // WorldGenRegion is not a ServerLevel, so this blocks the worldgen snow path.
        if (!(level instanceof ServerLevel)) return;
        if (!SeasonCache.get().isWinter()) return;

        Biome self = (Biome)(Object)this;
        if (!self.hasPrecipitation()) return;
        if (self.getBaseTemperature() > 0.8f) {
            // Hot biome: explicitly return false in winter so vanilla doesn't override
            cir.setReturnValue(false);
            return;
        }

        float adjustedTemp = Mth.clamp(
                self.getTemperature(pos, level.getSeaLevel()) + WINTER_TEMP_ADJUSTMENT,
                -0.5f, 2.0f);
        boolean coldEnough = adjustedTemp < 0.15f;

        // Always set the return value when we've decided (mirrors SS's unconditional set)
        if (!coldEnough) {
            cir.setReturnValue(false);
            return;
        }

        cir.setReturnValue(
            level.isInsideBuildHeight(pos.getY())
            && Blocks.SNOW.defaultBlockState().canSurvive(level, pos)
            && level.getBrightness(LightLayer.BLOCK, pos) < 10
        );
    }

    @Redirect(
        method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z",
        at = @At(value = "INVOKE",
                 target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;I)Z"),
        remap = false
    )
    public boolean gotSeason_shouldFreeze_warmEnoughToRain(Biome biome, BlockPos pos, int seaLevel,
                                                           LevelReader level) {
        if (!(level instanceof ServerLevel)) return biome.warmEnoughToRain(pos, seaLevel);
        if (!SeasonCache.get().isWinter()) return biome.warmEnoughToRain(pos, seaLevel);
        if (biome.getBaseTemperature() > 0.8f) return biome.warmEnoughToRain(pos, seaLevel);

        float adjustedTemp = Mth.clamp(
                biome.getTemperature(pos, seaLevel) + WINTER_TEMP_ADJUSTMENT,
                -0.5f, 2.0f);
        return adjustedTemp >= 0.15f;
    }
}
