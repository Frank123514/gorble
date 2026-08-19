package net.francis.got.mixin;

import net.francis.got.climate.SeasonCache;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class LevelMixin {

    @Inject(method = "isRainingAt", at = @At("HEAD"), cancellable = true, remap = false)
    public void gotSeason_isRainingAt(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Level self = (Level)(Object)this;
        if (!self.isRaining()) { cir.setReturnValue(false); return; }
        if (!self.canSeeSky(pos)) { cir.setReturnValue(false); return; }
        if (!SeasonCache.get().isWinter()) return;

        Biome biome = self.getBiome(pos).value();
        Biome.Precipitation precip = biome.getPrecipitationAt(pos, self.getSeaLevel());
        if (precip == Biome.Precipitation.RAIN) {
            cir.setReturnValue(false);
        }
    }
}
