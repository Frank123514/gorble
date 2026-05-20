package net.got.mixin;

import net.got.client.color.SeasonFoliageColorProvider;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Intercepts biome color lookups and blends in the GoT season color.
 *
 * remap=false is required for the same reason as WeatherEffectRendererMixin —
 * the Mixin AP cannot locate SRG mappings in this build setup, so we use
 * named (parchment) method names directly and skip remapping.
 */
@Mixin(BiomeColors.class)
public abstract class BiomeColorsMixin {

    @Inject(method = "getAverageGrassColor", at = @At("RETURN"), cancellable = true, remap = false)
    private static void gotSeason_grassColor(BlockAndTintGetter level, BlockPos pos,
                                             CallbackInfoReturnable<Integer> cir) {
        int biomeColor  = cir.getReturnValue();
        int seasonColor = SeasonFoliageColorProvider.getGrassSeasonColor();
        cir.setReturnValue(SeasonFoliageColorProvider.blendColors(biomeColor, seasonColor,
                SeasonFoliageColorProvider.SEASON_BLEND));
    }

    @Inject(method = "getAverageFoliageColor", at = @At("RETURN"), cancellable = true, remap = false)
    private static void gotSeason_foliageColor(BlockAndTintGetter level, BlockPos pos,
                                               CallbackInfoReturnable<Integer> cir) {
        int biomeColor  = cir.getReturnValue();
        int seasonColor = SeasonFoliageColorProvider.getFoliageSeasonColor();
        cir.setReturnValue(SeasonFoliageColorProvider.blendColors(biomeColor, seasonColor,
                SeasonFoliageColorProvider.SEASON_BLEND));
    }
}
