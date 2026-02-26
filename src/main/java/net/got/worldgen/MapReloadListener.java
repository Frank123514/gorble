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
 * Datapack reload listener that loads the two worldgen PNGs:
 * <ul>
 *   <li>{@code got:worldgen/map/heightmap.png} — topographic elevation</li>
 *   <li>{@code got:worldgen/map/biomemap.png}  — biome paint-over</li>
 * </ul>
 *
 * Both are reloaded whenever datapacks are applied so hot-reloading with
 * {@code /reload} works correctly during development.
 */
public class MapReloadListener extends SimplePreparableReloadListener<Void> {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation HEIGHTMAP =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/map/heightmap.png");

    private static final ResourceLocation BIOMEMAP =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/map/biomemap.png");

    @Override
    protected @NotNull Void prepare(@NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {

        // ── Heightmap ──────────────────────────────────────────────────────
        try {
            Resource heightRes = manager.getResourceOrThrow(HEIGHTMAP);
            try (InputStream stream = heightRes.open()) {
                HeightmapLoader.load(stream);
            }
            LOGGER.info("[GoT Worldgen] Heightmap loaded successfully");
        } catch (Exception e) {
            LOGGER.error("[GoT Worldgen] Failed to load heightmap: {}", e.getMessage());
            e.printStackTrace();
        }

        // ── Biomemap ───────────────────────────────────────────────────────
        try {
            Resource biomeRes = manager.getResourceOrThrow(BIOMEMAP);
            try (InputStream stream = biomeRes.open()) {
                BiomemapLoader.load(stream);
            }
            LOGGER.info("[GoT Worldgen] Biomemap loaded successfully");
        } catch (Exception e) {
            LOGGER.error("[GoT Worldgen] Failed to load biomemap: {}", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void apply(@NotNull Void object, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        // Nothing to apply — data is already live in the static loaders
    }
}