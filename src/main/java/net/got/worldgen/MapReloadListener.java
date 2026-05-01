package net.got.worldgen;

import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Server reload listener retained for forward compatibility.
 *
 * <p>The biomemap PNG ({@code got:worldgen/map/biomemap.png}) and its loader
 * ({@code BiomemapLoader}) have been removed. Biome placement is now handled
 * entirely by the LOTR-style layer system in {@link net.got.worldgen.layer.GotWorldLayers}.
 *
 * <p>This listener is a no-op. It is kept so that any datapack or reload hook
 * that expects {@code got:map_reload} to exist does not throw a missing-listener
 * error. It can be safely removed if the listener registration in
 * {@link net.got.GotMod} is also removed.
 */
public class MapReloadListener extends SimplePreparableReloadListener<Void> {

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    protected @NotNull Void prepare(@NotNull ResourceManager manager,
                                    @NotNull ProfilerFiller profiler) {
        // No-op: biomemap loading removed. Biomes are now layer-generated.
        LOGGER.debug("[GoT Worldgen] MapReloadListener prepare() — no-op (biomemap removed)");
        return null;
    }

    @Override
    protected void apply(@NotNull Void prepared,
                         @NotNull ResourceManager manager,
                         @NotNull ProfilerFiller profiler) {
        // No-op.
        LOGGER.debug("[GoT Worldgen] MapReloadListener apply() — no-op (biomemap removed)");
    }
}
