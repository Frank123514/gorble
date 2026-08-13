package net.got.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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