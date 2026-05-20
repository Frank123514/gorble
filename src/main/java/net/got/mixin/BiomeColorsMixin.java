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
 * Intercepts the two biome-color lookups that ALL grass/foliage-tinted blocks
 * (vanilla and modded) route through, and blends in the current GoT season color.
 *
 * <p>This is the same strategy used by Serene Seasons: rather than registering a
 * per-block {@link net.minecraft.client.color.block.BlockColor} for every single
 * leaf or grass block in existence, we patch the shared resolver so that any block
 * that calls {@code BiomeColors.getAverageGrassColor} or
 * {@code BiomeColors.getAverageFoliageColor} automatically gets the season tint —
 * including vanilla blocks and blocks added by other mods.
 *
 * <p>Season colors and the blend factor are defined in
 * {@link SeasonFoliageColorProvider} so they stay in one place.
 */
@Mixin(BiomeColors.class)
public abstract class BiomeColorsMixin {

    /**
     * After the biome grass color is resolved, blend it toward the current
     * season's grass color.
     */
    @Inject(method = "getAverageGrassColor", at = @At("RETURN"), cancellable = true, remap = false)
    private static void gotSeason_grassColor(BlockAndTintGetter level, BlockPos pos,
                                             CallbackInfoReturnable<Integer> cir) {
        int biomeColor  = cir.getReturnValue();
        int seasonColor = SeasonFoliageColorProvider.getGrassSeasonColor();
        cir.setReturnValue(SeasonFoliageColorProvider.blendColors(biomeColor, seasonColor,
                SeasonFoliageColorProvider.SEASON_BLEND));
    }

    /**
     * After the biome foliage color is resolved, blend it toward the current
     * season's foliage color.
     */
    @Inject(method = "getAverageFoliageColor", at = @At("RETURN"), cancellable = true, remap = false)
    private static void gotSeason_foliageColor(BlockAndTintGetter level, BlockPos pos,
                                               CallbackInfoReturnable<Integer> cir) {
        int biomeColor  = cir.getReturnValue();
        int seasonColor = SeasonFoliageColorProvider.getFoliageSeasonColor();
        cir.setReturnValue(SeasonFoliageColorProvider.blendColors(biomeColor, seasonColor,
                SeasonFoliageColorProvider.SEASON_BLEND));
    }
}