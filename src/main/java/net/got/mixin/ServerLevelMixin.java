package net.got.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Previously redirected Biome.getPrecipitationAt inside ServerLevel.tickPrecipitation
 * to force biomes into SNOW mode during winter (temperature -0.8 adjustment, mirroring
 * Serene Seasons). That drove actual snow-layer placement on the ground.
 *
 * Snow placement is no longer wanted at all — winter should only show the visual
 * snow particle effect (see WeatherEffectRendererMixin), not place real snow blocks.
 * The real gate for that is BiomeMixin#shouldSnow, which now always returns false,
 * so this redirect is left as a plain passthrough to vanilla behaviour and no longer
 * does any winter-based temperature adjustment.
 *
 * remap=false — see WeatherEffectRendererMixin for explanation.
 */
@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Redirect(
            method = "tickPrecipitation",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;I)Lnet/minecraft/world/level/biome/Biome$Precipitation;"),
            remap = false
    )
    public Biome.Precipitation gotSeason_tickIceAndSnow_getPrecipitationAt(
            Biome biome, BlockPos pos, int seaLevel)
    {
        return biome.getPrecipitationAt(pos, seaLevel);
    }
}