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
 * In Summer the blend factor is 0, so the raw biome color passes through
 * unchanged — summer is the "normal" reference look.
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
        int biomeColor = cir.getReturnValue();

        float blend = SeasonFoliageColorProvider.getSeasonBlend();
        int color = blend == 0f
                ? biomeColor // Summer — use pure biome color as the base
                : SeasonFoliageColorProvider.blendColors(
                biomeColor, SeasonFoliageColorProvider.getGrassSeasonColor(), blend);

        // Far north of the cold-latitude line, grass gradually turns a dead
        // yellow-brown regardless of season, with dark/light patches of its
        // own rather than one flat tint.
        float deadGrassBlend = SeasonFoliageColorProvider.getDeadGrassBlend(pos.getX(), pos.getZ());
        if (deadGrassBlend > 0f) {
            int deadGrassColor = SeasonFoliageColorProvider.getDeadGrassColor(pos.getX(), pos.getZ());
            color = SeasonFoliageColorProvider.blendColors(color, deadGrassColor, deadGrassBlend);
        }

        // Regional patch variation applies every season, including summer,
        // so grass reads with natural light/dark patches everywhere.
        float variation = SeasonFoliageColorProvider.getGrassPatchVariation(pos.getX(), pos.getZ());
        cir.setReturnValue(SeasonFoliageColorProvider.applyBrightness(color, variation));
    }

    @Inject(method = "getAverageFoliageColor", at = @At("RETURN"), cancellable = true, remap = false)
    private static void gotSeason_foliageColor(BlockAndTintGetter level, BlockPos pos,
                                               CallbackInfoReturnable<Integer> cir) {
        float blend = SeasonFoliageColorProvider.getSeasonBlend();
        if (blend == 0f) return; // Summer — use pure biome color, no modification
        int biomeColor  = cir.getReturnValue();
        int seasonColor = SeasonFoliageColorProvider.getFoliageSeasonColor();
        cir.setReturnValue(SeasonFoliageColorProvider.blendColors(biomeColor, seasonColor, blend));
    }
}