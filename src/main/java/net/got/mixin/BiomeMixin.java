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

@Mixin(Biome.class)
public class BiomeMixin {

    private static final float WINTER_TEMP_ADJUSTMENT = -0.8f;

    // NOTE: the frozen-latitude line intentionally does NOT affect snowfall.
    // North of the line, water still freezes (see gotSeason_shouldFreeze_warmEnoughToRain
    // below), but whether it snows is left entirely to each biome's normal
    // temperature/season behaviour.
    @Inject(method = "shouldSnow", at = @At("HEAD"), cancellable = true, remap = false)
    public void gotSeason_shouldSnow(LevelReader level, BlockPos pos,
                                     CallbackInfoReturnable<Boolean> cir) {
        Biome self = (Biome)(Object)this;
        if (!self.hasPrecipitation()) return;

        boolean hotBiome = self.getBaseTemperature() > 0.8f;
        if (hotBiome) {
            // Hot biomes are untouched — unchanged behaviour.
            cir.setReturnValue(false);
            return;
        }

        float baseTemp = self.getTemperature(pos, level.getSeaLevel());
        float temp = baseTemp;
        if (level instanceof ServerLevel && SeasonCache.get().isWinter()) {
            temp = baseTemp + WINTER_TEMP_ADJUSTMENT;
        }
        temp = Mth.clamp(temp, -0.5f, 2.0f);

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

    // NOTE: the frozen latitude line does NOT use this hook. Vanilla's
    // shouldFreeze only turns water into ice when it's adjacent to already-
    // cold/snowy land, so relying on it here would mean latitude ice can only
    // ever spread in from an existing frozen shore — exactly the shore-
    // dependent behaviour we don't want north of the line. See
    // LatitudeIceHandler for the independent gradient-based instant freeze.
    @Redirect(
        method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z",
        at = @At(value = "INVOKE",
                 target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;I)Z"),
        remap = false
    )
    public boolean gotSeason_shouldFreeze_warmEnoughToRain(Biome biome, BlockPos pos, int seaLevel,
                                                           LevelReader level) {
        if (!(level instanceof ServerLevel)) {
            return biome.warmEnoughToRain(pos, seaLevel);
        }

        boolean hotBiome = biome.getBaseTemperature() > 0.8f;
        if (hotBiome) {
            // Hot biomes are untouched — unchanged behaviour.
            return biome.warmEnoughToRain(pos, seaLevel);
        }

        float temp = biome.getTemperature(pos, seaLevel);
        if (SeasonCache.get().isWinter()) {
            temp += WINTER_TEMP_ADJUSTMENT;
        }
        temp = Mth.clamp(temp, -0.5f, 2.0f);
        return temp >= 0.15f;
    }
}
