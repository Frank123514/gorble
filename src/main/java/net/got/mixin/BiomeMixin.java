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
 * Adjusts biome temperature for snow/freeze checks.
 *
 * Snowline: vanilla temperature drops by 0.00166667 per block above Y=64.
 * Snow triggers when temp < 0.15. We shift the queried pos down by SNOWLINE_Y_SHIFT
 * so vanilla sees a warmer temperature, pushing the snowline up to ~Y=140.
 * This applies to BOTH worldgen (SnowAndFreezeFeature) and live ticking,
 * so the snowline is consistent everywhere.
 *
 * Winter season: on top of the snowline shift, we apply a temperature adjustment
 * that makes cold biomes freeze during winter.
 *
 * remap=false — see WeatherEffectRendererMixin for explanation.
 */
@Mixin(Biome.class)
public class BiomeMixin {

    private static final float WINTER_TEMP_ADJUSTMENT = -0.8f;

    /**
     * Shifting pos down by 60 blocks makes vanilla think the block is 60 blocks lower
     * (warmer), so snow only forms where the real altitude is ~60 blocks higher than
     * vanilla would normally place it — pushing the snowline from ~Y=80 up to ~Y=140.
     */
    private static final int SNOWLINE_Y_SHIFT = 60;

    @Inject(method = "shouldSnow", at = @At("HEAD"), cancellable = true, remap = false)
    public void gotSeason_shouldSnow(LevelReader level, BlockPos pos,
                                     CallbackInfoReturnable<Boolean> cir) {
        Biome self = (Biome)(Object)this;
        if (!self.hasPrecipitation()) return;
        if (self.getBaseTemperature() > 0.8f) {
            cir.setReturnValue(false);
            return;
        }

        BlockPos shiftedPos = pos.below(SNOWLINE_Y_SHIFT);

        float baseTemp = self.getTemperature(shiftedPos, level.getSeaLevel());

        // Apply winter adjustment only during live server ticking, not worldgen
        float temp = baseTemp;
        if (level instanceof ServerLevel && SeasonCache.get().isWinter()) {
            temp = Mth.clamp(baseTemp + WINTER_TEMP_ADJUSTMENT, -0.5f, 2.0f);
        }

        if (temp >= 0.15f) {
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
        BlockPos shiftedPos = pos.below(SNOWLINE_Y_SHIFT);

        if (level instanceof ServerLevel && SeasonCache.get().isWinter()
                && biome.getBaseTemperature() <= 0.8f) {
            float adjustedTemp = Mth.clamp(
                    biome.getTemperature(shiftedPos, seaLevel) + WINTER_TEMP_ADJUSTMENT,
                    -0.5f, 2.0f);
            return adjustedTemp >= 0.15f;
        }

        return biome.warmEnoughToRain(shiftedPos, seaLevel);
    }
}
