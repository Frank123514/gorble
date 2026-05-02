package net.got.worldgen;

import com.mojang.logging.LogUtils;
import net.got.worldgen.layer.GotMapLayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Server reload listener that (re-)loads {@code biomemap.png} and pushes a
 * freshly built {@link GotMapLayer} into {@link GotBiomeSource}.
 *
 * <p><b>History:</b> the original GoT code loaded the biomemap here via a now-
 * deleted {@code BiomemapLoader}. A previous refactor replaced the body with
 * no-op stubs and a comment claiming "the loader was removed". This broke biome
 * placement: every world generated with random biomes instead of the hand-crafted
 * Westeros geography painted in {@code biomemap.png}. This class re-implements
 * the missing load.
 *
 * <p>Loading is split across {@link #prepare} (done off-thread, reads pixels)
 * and {@link #apply} (on-thread, pushes the layer to {@link GotBiomeSource}).
 */
public class MapReloadListener extends SimplePreparableReloadListener<GotMapLayer.PreparedMap> {

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    protected @NotNull GotMapLayer.PreparedMap prepare(@NotNull ResourceManager manager,
                                                       @NotNull ProfilerFiller profiler) {
        profiler.push("got/biomemap_load");
        try {
            LOGGER.info("[GoT Worldgen] Loading biomemap.png…");
            return GotMapLayer.prepare(manager);
        } finally {
            profiler.pop();
        }
    }

    @Override
    protected void apply(@NotNull GotMapLayer.PreparedMap prepared,
                         @NotNull ResourceManager manager,
                         @NotNull ProfilerFiller profiler) {
        profiler.push("got/biomemap_apply");
        try {
            GotBiomeSource.setMapLayer(prepared);
            LOGGER.info("[GoT Worldgen] biomemap.png applied to GotBiomeSource.");
        } finally {
            profiler.pop();
        }
    }
}