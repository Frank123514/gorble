package net.got.worldgen;

import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Loads {@code biomemap.png}, {@code biome_colors.json}, {@code subbiomes.json},
 * and {@code slope_rules.json} off-thread, then pushes all four into their
 * respective static stores on the main thread.
 *
 * <p>Registered via {@link net.got.registry.ModWorldgen} on the
 * {@code AddServerReloadListenersEvent}.
 */
public class MapReloadListener extends SimplePreparableReloadListener<MapReloadListener.Prepared> {

    private static final Logger LOGGER = LogUtils.getLogger();

    record Prepared(
            int[][] pixels,
            int width,
            int height,
            Map<Integer, GotBiomeTerrainParams.Params> params,
            Map<String, List<SubbiomeDef>> subbiomes,
            Map<String, List<SlopeRuleDef>> slopeRules
    ) {}

    @Override
    protected @NotNull Prepared prepare(@NotNull ResourceManager manager,
                                        @NotNull ProfilerFiller profiler) {
        profiler.push("got/biomemap_load");
        try {
            // ── Biomemap PNG ───────────────────────────────────────────────
            int[][] pixels = BiomemapLoader.load(manager);
            int w = 0, h = 0;
            if (pixels != null) { w = pixels.length; h = pixels[0].length; }

            // ── Biome-colour params ────────────────────────────────────────
            Map<Integer, GotBiomeTerrainParams.Params> params =
                    GotBiomeTerrainParams.load(manager);

            // ── Subbiome definitions ───────────────────────────────────────
            Map<String, List<SubbiomeDef>> subbiomes =
                    SubbiomeResolver.load(manager);

            // ── Slope surface rules ────────────────────────────────────────
            Map<String, List<SlopeRuleDef>> slopeRules =
                    SlopeSurfaceResolver.load(manager);

            return new Prepared(pixels, w, h, params, subbiomes, slopeRules);
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
            SubbiomeResolver.apply(prepared.subbiomes());
            SlopeSurfaceResolver.apply(prepared.slopeRules());

            // Notify GotBiomeSource so getNoiseBiome re-reads the new data.
            GotBiomeSource.onMapReloaded();

            LOGGER.info("[GoT] BiomeMap applied ({}x{}, {} biome colors, {} subbiome parents, {} slope biomes)",
                    prepared.width(), prepared.height(),
                    prepared.params().size(), prepared.subbiomes().size(),
                    prepared.slopeRules().size());
        } finally {
            profiler.pop();
        }
    }
}
