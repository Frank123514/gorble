package net.got.mixin;

import net.got.client.color.SeasonFoliageColorProvider;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public abstract class BiomeColorsMixin {

    @Inject(method = "getAverageGrassColor", at = @At("RETURN"), cancellable = true, remap = false)
    private static void gotSeason_grassColor(BlockAndTintGetter level, BlockPos pos,
                                             CallbackInfoReturnable<Integer> cir) {
        int biomeColor = cir.getReturnValue();

        float blend = SeasonFoliageColorProvider.getSeasonBlend();
        int color = blend == 0f
                ? biomeColor
                : SeasonFoliageColorProvider.blendColors(
                biomeColor, SeasonFoliageColorProvider.getGrassSeasonColor(), blend);

        float deadGrassBlend = SeasonFoliageColorProvider.getDeadGrassBlend(pos.getX(), pos.getZ());
        if (deadGrassBlend > 0f) {
            int deadGrassColor = SeasonFoliageColorProvider.getDeadGrassColor(pos.getX(), pos.getZ());
            color = SeasonFoliageColorProvider.blendColors(color, deadGrassColor, deadGrassBlend);
        }

        float variation = SeasonFoliageColorProvider.getGrassPatchVariation(pos.getX(), pos.getZ());
        cir.setReturnValue(SeasonFoliageColorProvider.applyBrightness(color, variation));
    }

    @Inject(method = "getAverageFoliageColor", at = @At("RETURN"), cancellable = true, remap = false)
    private static void gotSeason_foliageColor(BlockAndTintGetter level, BlockPos pos,
                                               CallbackInfoReturnable<Integer> cir) {
        float blend = SeasonFoliageColorProvider.getSeasonBlend();
        if (blend == 0f) return;
        int biomeColor  = cir.getReturnValue();
        int seasonColor = SeasonFoliageColorProvider.getFoliageSeasonColor();
        cir.setReturnValue(SeasonFoliageColorProvider.blendColors(biomeColor, seasonColor, blend));
    }
}