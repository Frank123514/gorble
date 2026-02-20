package net.got.worldgen;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.InputStream;

public class MapReloadListener extends SimplePreparableReloadListener<Void> {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation HEIGHTMAP =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/map/heightmap.png");

    private static final ResourceLocation BIOME_MAP =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/map/biome_map.png");

    private static final ResourceLocation BIOME_COLORS =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/biomecolors/biome_colors.json");

    @Override
    protected @NotNull Void prepare(@NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        try {
            // Load heightmap
            Resource height = manager.getResourceOrThrow(HEIGHTMAP);
            try (InputStream h = height.open()) {
                HeightmapLoader.load(h);
            }

            // Load biome colors (must be before biome map)
            Resource biomeColors = manager.getResourceOrThrow(BIOME_COLORS);
            try (InputStream bc = biomeColors.open()) {
                BiomeMapLoader.loadBiomeColors(bc);
            }

            // Load biome map
            Resource biomeMap = manager.getResourceOrThrow(BIOME_MAP);
            try (InputStream bm = biomeMap.open()) {
                BiomeMapLoader.loadBiomeMap(bm);
            }

            // Finish loading (validates alignment)
            BiomeMapLoader.finishLoading();

            LOGGER.info("[GoT Worldgen] All maps loaded successfully");

        } catch (Exception e) {
            LOGGER.error("[GoT Worldgen] Failed to load worldgen maps: {}", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void apply(@NotNull Void object, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        // Nothing else required
    }
}