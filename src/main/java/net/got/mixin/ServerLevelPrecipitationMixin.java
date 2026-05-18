package net.got.mixin;

import net.got.climate.ClimateSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Overrides Biome.shouldSnow / shouldFreeze to use latitude temperature instead of
 * biome temperature, but ONLY during active server-side rain.
 *
 * The critical guard: both injections return early (fall through to vanilla) when
 * the level is not a live, raining ServerLevel. This means:
 *  - Worldgen: level is a WorldGenRegion, not a ServerLevel → guard fails → vanilla
 *    biome logic runs → chunks generate WITHOUT snow. Snow only accumulates once
 *    it actually rains, exactly like vanilla behaviour in a snowy biome.
 *  - Runtime weather tick: level IS a raining ServerLevel → latitude check applies →
 *    vanilla's tickChunk loop gradually places snow layer by layer (1 random column
 *    per loaded chunk per tick) and freezes water from shores inward.
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
        // Only override during active rain — not worldgen, not clear weather.
        if (!(level instanceof ServerLevel serverLevel) || !serverLevel.isRaining()) return;

        float latTemp = ClimateSystem.getLatitudeTemperature(pos);
        if (latTemp >= 0.15f) {
            cir.setReturnValue(false);
            return;
        }
        if (level.getBrightness(LightLayer.BLOCK, pos) >= 10) {
            cir.setReturnValue(false);
            return;
        }
        BlockState state = level.getBlockState(pos);
        // true for bare ground (place first layer) or existing snow (add a layer)
        cir.setReturnValue(state.isAir() || state.is(Blocks.SNOW));
    }

    @Inject(
            method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void got_shouldFreeze(LevelReader level, BlockPos pos, boolean mustBeAtEdge,
                                  CallbackInfoReturnable<Boolean> cir) {
        // Only override during active rain — not worldgen, not clear weather.
        if (!(level instanceof ServerLevel serverLevel) || !serverLevel.isRaining()) return;

        float latTemp = ClimateSystem.getLatitudeTemperature(pos);
        if (latTemp >= 0.15f) {
            cir.setReturnValue(false);
            return;
        }
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof LiquidBlock)) { cir.setReturnValue(false); return; }
        if (level.getFluidState(pos).getType() != Fluids.WATER) { cir.setReturnValue(false); return; }
        if (level.getBrightness(LightLayer.BLOCK, pos) >= 10) { cir.setReturnValue(false); return; }

        // mustBeAtEdge=true (vanilla default from tickChunk): ice forms at shores first
        if (mustBeAtEdge) {
            boolean surrounded = level.isWaterAt(pos.west()) && level.isWaterAt(pos.east())
                    && level.isWaterAt(pos.north()) && level.isWaterAt(pos.south());
            cir.setReturnValue(!surrounded);
        } else {
            cir.setReturnValue(true);
        }
    }
}
