package net.got.worldgen;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class BiomeMapLoader {

    /** 1 pixel = 10 blocks (MUST MATCH HeightmapLoader) */
    private static final int MAP_SCALE = 10;
    private static final double DEFAULT_TERRAIN_MODIFIER = 4.0;

    private static BufferedImage biomeMap;
    private static int width;
    private static int height;

    private static final Map<Integer, ResourceKey<Biome>> COLOR_TO_BIOME = new HashMap<>();
    private static final Map<Integer, Double> COLOR_TO_MODIFIER = new HashMap<>();
    private static boolean loaded = false;

    /* ========================================================= */
    /* === LOADING (CALLED FROM MapReloadListener)             === */
    /* ========================================================= */

    public static void loadBiomeMap(InputStream stream) {
        try {
            biomeMap = ImageIO.read(stream);

            if (biomeMap == null) {
                throw new IllegalStateException("Biome map PNG read as null");
            }

            width = biomeMap.getWidth();
            height = biomeMap.getHeight();

            System.out.println("[GoT] Biome map loaded: " + width + "x" + height);

        } catch (Exception e) {
            throw new RuntimeException("Failed to read biome map PNG", e);
        }
    }

    public static void loadBiomeColors(InputStream stream) {
        try {
            System.out.println("[GoT] Reading biome_colors.json...");

            Gson gson = new Gson();
            JsonObject root = gson.fromJson(new InputStreamReader(stream), JsonObject.class);

            if (root == null) {
                throw new IllegalStateException("Biome color JSON parsed as null");
            }

            COLOR_TO_BIOME.clear();
            COLOR_TO_MODIFIER.clear();

            for (Map.Entry<String, com.google.gson.JsonElement> entry : root.entrySet()) {
                String hex = entry.getKey();

                if (!hex.startsWith("#") || hex.length() != 7) {
                    throw new IllegalArgumentException("Invalid color key: " + hex);
                }

                int rgb = Integer.parseInt(hex.substring(1), 16);

                JsonObject biomeData = entry.getValue().getAsJsonObject();
                String biomeId = biomeData.get("biome").getAsString();

                // Load terrain modifier (defaults to 4.0 if not specified)
                double terrainModifier = DEFAULT_TERRAIN_MODIFIER;
                if (biomeData.has("terrain_modifier")) {
                    terrainModifier = biomeData.get("terrain_modifier").getAsDouble();
                }

                System.out.println("[GoT] Color entry: " + hex + " -> " + biomeId +
                        " (modifier: " + terrainModifier + ")");

                COLOR_TO_BIOME.put(
                        rgb,
                        ResourceKey.create(
                                Registries.BIOME,
                                ResourceLocation.parse(biomeId)
                        )
                );

                COLOR_TO_MODIFIER.put(rgb, terrainModifier);
            }

            System.out.println("[GoT] Loaded " + COLOR_TO_BIOME.size() + " biome color entries");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read biome color map", e);
        }
    }

    /** Called AFTER both map + colors are loaded */
    public static void finishLoading() {
        loaded = true;
        System.out.println("[GoT] BiomeMapLoader initialized");

        // Validate alignment with heightmap
        if (HeightmapLoader.isLoaded()) {
            if (width != HeightmapLoader.getWidth() || height != HeightmapLoader.getHeight()) {
                System.err.println("[GoT] WARNING: Biome map (" + width + "x" + height +
                        ") doesn't match heightmap (" + HeightmapLoader.getWidth() + "x" +
                        HeightmapLoader.getHeight() + ")");
            } else {
                System.out.println("[GoT] Biome map and heightmap dimensions aligned perfectly");
            }
        }
    }

    /* ========================================================= */
    /* === BIOME LOOKUP (USED BY GotBiomeSource)               === */
    /* ========================================================= */

    /**
     * Get biome at world coordinates (aligned with heightmap)
     * Uses exact same coordinate transformation as heightmap
     */
    public static ResourceKey<Biome> getBiome(int worldX, int worldZ) {
        if (!loaded) {
            System.err.println("[GoT] BiomeMapLoader accessed before initialization!");
            return null;
        }

        if (biomeMap == null || COLOR_TO_BIOME.isEmpty()) {
            return null;
        }

        // Convert world coords to map pixel coords (SAME AS HEIGHTMAP)
        int mapCenterX = width / 2;
        int mapCenterZ = height / 2;

        int px = (worldX / MAP_SCALE) + mapCenterX;
        int pz = (worldZ / MAP_SCALE) + mapCenterZ;

        // Bounds check
        if (px < 0 || pz < 0 || px >= width || pz >= height) {
            return null;
        }

        // Read color from biome map
        int rgb = biomeMap.getRGB(px, pz) & 0xFFFFFF;

        ResourceKey<Biome> biome = COLOR_TO_BIOME.get(rgb);

        if (biome == null) {
            // Log unknown colors for debugging
            if (Math.random() < 0.001) {
                System.out.println("[GoT] Unknown biome color at (" + px + "," + pz +
                        "): #" + String.format("%06X", rgb));
            }
        }

        return biome;
    }

    /* ========================================================= */
    /* === TERRAIN MODIFIER (USED BY GotChunkGenerator)        === */
    /* ========================================================= */

    /**
     * Get terrain modifier at world coordinates
     * This controls how much noise affects the terrain height
     * Higher values = more dramatic noise variation
     */
    public static double getTerrainModifier(int worldX, int worldZ) {
        if (!loaded || biomeMap == null || COLOR_TO_MODIFIER.isEmpty()) {
            return DEFAULT_TERRAIN_MODIFIER;
        }

        int mapCenterX = width / 2;
        int mapCenterZ = height / 2;

        int px = (worldX / MAP_SCALE) + mapCenterX;
        int pz = (worldZ / MAP_SCALE) + mapCenterZ;

        // Bounds check
        if (px < 0 || pz < 0 || px >= width || pz >= height) {
            return DEFAULT_TERRAIN_MODIFIER;
        }

        int rgb = biomeMap.getRGB(px, pz) & 0xFFFFFF;
        return COLOR_TO_MODIFIER.getOrDefault(rgb, DEFAULT_TERRAIN_MODIFIER);
    }

    /* ========================================================= */
    /* === GETTERS                                              === */
    /* ========================================================= */

    public static Set<ResourceKey<Biome>> getAllBiomes() {
        return Set.copyOf(COLOR_TO_BIOME.values());
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static boolean isLoaded() {
        return loaded;
    }

    private BiomeMapLoader() {}
}