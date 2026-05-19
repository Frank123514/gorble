package net.got.mixin;

import net.got.climate.SeasonManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides Biome.shouldSnow / shouldFreeze to use the current GoT season
 * instead of biome temperature.
 *
 * <ul>
 *   <li><b>Winter</b> — shouldSnow returns true, shouldFreeze returns true.</li>
 *   <li><b>Summer</b> — shouldSnow returns false (no snow in summer).</li>
 *   <li><b>Spring / Autumn</b> — falls through to vanilla biome temperature logic.</li>
 * </ul>
 */
@Mixin(value = Biome.class, remap = false)
public abstract class ServerLevelPrecipitationMixin {

    @Inject(
            method = "shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void got_shouldSnow(LevelReader level, BlockPos pos,
                                CallbackInfoReturnable<Boolean> cir) {
        switch (SeasonManager.getCurrentSeason()) {
            case WINTER -> cir.setReturnValue(true);
            case SUMMER -> cir.setReturnValue(false);
            default     -> { /* fall through to vanilla */ }
        }
    }

    @Inject(
            method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void got_shouldFreeze(LevelReader level, BlockPos pos, boolean mustBeAtEdge,
                                  CallbackInfoReturnable<Boolean> cir) {
        switch (SeasonManager.getCurrentSeason()) {
            case WINTER -> cir.setReturnValue(true);
            case SUMMER -> cir.setReturnValue(false);
            default     -> { /* fall through to vanilla */ }
        }
    }
}
