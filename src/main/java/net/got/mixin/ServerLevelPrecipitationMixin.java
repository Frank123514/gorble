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
 * <h3>Fix 1 – No snow on water</h3>
 * {@code shouldSnow} now checks whether the block directly below {@code pos} is a
 * water fluid.  If it is, the method returns {@code false} so that vanilla never
 * places a snow-layer on top of open water.  Water freezing is handled exclusively
 * by {@code shouldFreeze} (which is called on {@code pos.below()} by vanilla's
 * tickChunk, i.e. on the water block itself).
 *
 * <h3>Fix 2 – Layer accumulation during rain</h3>
 * Returning {@code true} for an existing {@code Blocks.SNOW} position tells vanilla's
 * tickChunk to increment {@link net.minecraft.world.level.block.SnowLayerBlock#LAYERS}
 * (up to 8) rather than placing a fresh 1-layer block.  Combined with the snow-melt
 * prevention in {@link net.got.mixin.SnowMeltMixin}, layers now accumulate permanently.
 *
 * <h3>Worldgen guard</h3>
 * Both injections return early (fall through to vanilla) when the level is not a
 * live, raining ServerLevel.  This keeps worldgen unaffected.
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

        // ── Fix 1: Never place snow directly above liquid water ──────────────
        // vanilla passes pos = getHeightmapPos(MOTION_BLOCKING, …) which for
        // an open water surface is the AIR block above the water at pos.below().
        // Without this check the mixin would return true for air-above-water and
        // vanilla would place a floating snow layer there.
        // shouldFreeze (below) converts the water to ice independently.
        BlockState stateBelow = level.getBlockState(pos.below());
        if (!stateBelow.getFluidState().isEmpty()
                && stateBelow.getFluidState().getType() == Fluids.WATER) {
            cir.setReturnValue(false);
            return;
        }

        BlockState state = level.getBlockState(pos);
        // true for bare ground (place first layer) or existing snow (add a layer).
        // Vanilla's tickChunk checks state.is(Blocks.SNOW) after we return true and
        // increments SnowLayerBlock.LAYERS instead of resetting to default state.
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
        if (!level.getFluidState(pos).isSource()) { cir.setReturnValue(false); return; }
        if (level.getBrightness(LightLayer.BLOCK, pos) >= 10) { cir.setReturnValue(false); return; }

        // ── Fix: Freeze all exposed water, not just edges ────────────────────
        // The vanilla default (mustBeAtEdge = true from tickChunk) caused large
        // open-ocean surfaces to NEVER freeze because every column is surrounded
        // by water on all four sides.  We ignore the edge constraint: latitude
        // temperature is the sole criterion.
        cir.setReturnValue(true);
    }
}