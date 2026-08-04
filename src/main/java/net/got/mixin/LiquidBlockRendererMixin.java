package net.got.mixin;

import com.mojang.logging.LogUtils;
import net.got.worldgen.RiverFlowMap;
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

/**
 * River water is placed by worldgen as plain source blocks (uniform fluid
 * level), so {@code FluidState#getFlow} naturally computes a zero vector —
 * there's no height gradient between neighboring source blocks for it to
 * read a direction from. Vanilla's {@link LiquidBlockRenderer} treats a
 * zero flow vector as "still water" and renders the non-scrolling still
 * sprite, which is why rivers never visually appeared to flow.
 *
 * <p>This redirects that lookup: when vanilla's own computation is zero
 * <i>and</i> the column is part of a connected river ({@link
 * RiverFlowMap#flowAt} non-null), it substitutes the river's current
 * direction instead. That's enough to make the renderer pick the flowing
 * sprite and orient its scroll to match {@link RiverCurrentParticles}
 * and {@code RiverCurrentSystem}'s push direction. Any real vanilla flow
 * (waterfalls, springs, flowing non-river water) is left untouched.
 *
 * <p>remap=false: this project runs Mixin against named/parchment
 * mappings directly (see {@code WeatherEffectRendererMixin}), so method
 * and field names here are already the runtime names.
 *
 * <p><b>Note:</b> the redirect target's descriptor
 * ({@code FluidState#getFlow(BlockGetter, BlockPos)}) is written from
 * memory of the 1.21.x mappings. If the mixin fails to apply at launch,
 * this is the first place to check — decompile {@code LiquidBlockRenderer
 * #tesselate} and confirm the exact parameter type it calls
 * {@code getFlow} with (should be {@code BlockGetter} or a supertype of
 * it) and adjust the target string below to match.
 */
@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin {

    private static final Logger LOGGER = LogUtils.getLogger();
    // Logs the first few substitutions only, so you can grep the console to
    // confirm this is actually firing without spamming — it's called once
    // per rendered water block face, every frame.
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
        if (flow == null) return vanillaFlow; // not a connected river here — correctly still

        if (LOG_BUDGET.getAndDecrement() > 0) {
            LOGGER.info("[GoT] river flow texture override at {} -> ({}, {})", pos, flow.dx(), flow.dz());
        }

        // Magnitude only needs to be nonzero to select the flowing sprite;
        // this matches the rough scale of vanilla's own flow vectors.
        return new Vec3(flow.dx() * 0.014, 0.0, flow.dz() * 0.014);
    }
}
