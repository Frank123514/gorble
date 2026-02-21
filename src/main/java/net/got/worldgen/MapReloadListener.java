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

/**
 * Loads the biome map PNG and biome_colors.json on server/datapack reload.
 * The heightmap PNG is NOT used — terrain height comes entirely from the
 * base_height / height_variation values in biome_colors.json.
 */
public class MapReloadListener extends SimplePreparableReloadListener<Void> {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation BIOME_MAP =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/map/biome_map.png");

    private static final ResourceLocation BIOME_COLORS =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/biomecolors/biome_colors.json");

    @Override
    protected @NotNull Void prepare(@NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        try {
            // Load biome colors (must come before biome map)
            Resource biomeColors = manager.getResourceOrThrow(BIOME_COLORS);
            try (InputStream bc = biomeColors.open()) {
                BiomeMapLoader.loadBiomeColors(bc);
            }

            // Load biome map PNG
            Resource biomeMap = manager.getResourceOrThrow(BIOME_MAP);
            try (InputStream bm = biomeMap.open()) {
                BiomeMapLoader.loadBiomeMap(bm);
            }

            // Finalize
            BiomeMapLoader.finishLoading();

            LOGGER.info("[GoT Worldgen] Biome map loaded successfully (biome-driven heights)");

        } catch (Exception e) {
            LOGGER.error("[GoT Worldgen] Failed to load worldgen maps: {}", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void apply(@NotNull Void object, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        // Nothing extra required
    }
}