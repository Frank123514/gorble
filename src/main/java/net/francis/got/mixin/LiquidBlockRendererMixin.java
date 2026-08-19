package net.francis.got.mixin;

import com.mojang.logging.LogUtils;
import net.francis.got.worldgen.RiverFlowMap;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin {

    private static final Logger LOGGER = LogUtils.getLogger();
    
    private static final AtomicInteger LOG_BUDGET = new AtomicInteger(10);

    @Redirect(
            method = "tesselate",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/material/FluidState;getFlow(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/Vec3;"),
            remap = false
    )
    private Vec3 gotRiver_overrideFlow(FluidState fluidState, BlockGetter level, BlockPos pos) {
        Vec3 vanillaFlow = fluidState.getFlow(level, pos);
        if (vanillaFlow.x != 0.0 || vanillaFlow.z != 0.0) return vanillaFlow;

        RiverFlowMap.FlowVector flow = RiverFlowMap.flowAt(pos.getX(), pos.getZ());
        if (flow == null) return vanillaFlow;

        if (LOG_BUDGET.getAndDecrement() > 0) {
            LOGGER.info("[GoT] river flow texture override at {} -> ({}, {})", pos, flow.dx(), flow.dz());
        }

        return new Vec3(flow.dx() * 0.014, 0.0, flow.dz() * 0.014);
    }
}
