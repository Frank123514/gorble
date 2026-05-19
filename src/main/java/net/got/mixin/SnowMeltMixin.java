package net.got.mixin;

import net.got.climate.SeasonManager;
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
 * Prevents vanilla snow from melting during Winter.
 * In all other seasons vanilla melt behaviour is unchanged.
 */
@Mixin(value = SnowLayerBlock.class, remap = false)
public abstract class SnowMeltMixin {

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true, remap = false)
    private void got_preventMeltInWinter(
            BlockState state, ServerLevel level, BlockPos pos, RandomSource random,
            CallbackInfo ci) {
        if (SeasonManager.getCurrentSeason().isWinter()) {
            ci.cancel();
        }
    }
}
