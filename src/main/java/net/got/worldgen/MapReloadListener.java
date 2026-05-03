package net.got.worldgen;

import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Map;

/**
 * Loads {@code biomemap.png} and {@code biome_colors.json} off-thread,
 * then pushes both into their respective static stores on the main thread.
 */
public class MapReloadListener extends SimplePreparableReloadListener<MapReloadListener.Prepared> {

    private static final Logger LOGGER = LogUtils.getLogger();

    record Prepared(int[][] pixels, int width, int height,
                    Map<Integer, GotBiomeTerrainParams.Params> params) {}

    @Override
    protected @NotNull Prepared prepare(@NotNull ResourceManager manager,
                                        @NotNull ProfilerFiller profiler) {
        profiler.push("got/biomemap_load");
        try {
            int[][] pixels = BiomemapLoader.load(manager);
            int w = 0, h = 0;
            if (pixels != null) { w = pixels.length; h = pixels[0].length; }
            Map<Integer, GotBiomeTerrainParams.Params> params = GotBiomeTerrainParams.load(manager);
            return new Prepared(pixels, w, h, params);
        } finally {
            profiler.pop();
        }
    }

    @Override
    protected void apply(@NotNull Prepared prepared,
                         @NotNull ResourceManager manager,
                         @NotNull ProfilerFiller profiler) {
        profiler.push("got/biomemap_apply");
        try {
            if (prepared.pixels() != null)
                BiomemapLoader.apply(prepared.pixels(), prepared.width(), prepared.height());
            GotBiomeTerrainParams.apply(prepared.params());
            // Notify GotBiomeSource so getNoiseBiome re-reads the new data.
            GotBiomeSource.onMapReloaded();
            LOGGER.info("[GoT] BiomeMap applied ({}x{}, {} biome colors)",
                    prepared.width(), prepared.height(), prepared.params().size());
        } finally {
            profiler.pop();
        }
    }
}
