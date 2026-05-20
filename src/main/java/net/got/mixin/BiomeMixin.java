package net.got.mixin;

import net.got.climate.SeasonManager;
import net.got.climate.WinterWeatherContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {

    /**
     * Forces snow accumulation during winter for rendered chunks.
     *
     * coldEnoughToSnow(BlockPos) is what ServerLevel's weather tick calls
     * directly to decide whether to place a snow layer. Returning true here
     * makes every biome cold enough to snow regardless of its base temperature.
     */
    @Inject(method = "coldEnoughToSnow", at = @At("HEAD"), cancellable = true, remap = false)
    private void gotWinter_coldEnoughToSnow(BlockPos pos, int fluidHeight, CallbackInfoReturnable<Boolean> cir) {
        if (SeasonManager.getCurrentSeason().isWinter() && WinterWeatherContext.isChunkRendered()) {
            cir.setReturnValue(true);
        }
    }

    /**
     * Forces water freezing during winter for rendered chunks.
     *
     * shouldFreeze() opens with an early-return: if warmEnoughToRain() is true,
     * skip freezing. By returning false here we let shouldFreeze proceed past
     * that guard and do its normal water-block + sky-access checks — so only
     * valid exposed water surfaces freeze, exactly like vanilla cold biomes.
     */
    @Inject(method = "warmEnoughToRain", at = @At("HEAD"), cancellable = true, remap = false)
    private void gotWinter_warmEnoughToRain(BlockPos pos, int fluidHeight, CallbackInfoReturnable<Boolean> cir) {
        if (SeasonManager.getCurrentSeason().isWinter() && WinterWeatherContext.isChunkRendered()) {
            cir.setReturnValue(false);
        }
    }
}