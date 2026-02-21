package net.got.worldgen;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class BiomeMapLoader {

    /** 1 pixel = 10 blocks */
    private static final int MAP_SCALE = 10;

    // Default height values (generic plains-ish land)
    private static final double DEFAULT_BASE_HEIGHT   = 68.0;
    private static final double DEFAULT_HEIGHT_VARIATION = 6.0;

    private static BufferedImage biomeMap;
    private static int width;
    private static int height;

    /** Hex RGB -> biome key */
    private static final Map<Integer, ResourceKey<Biome>> COLOR_TO_BIOME     = new HashMap<>();
    /** Hex RGB -> base Y height */
    private static final Map<Integer, Double>             COLOR_TO_BASE_H    = new HashMap<>();
    /** Hex RGB -> height variation */
    private static final Map<Integer, Double>             COLOR_TO_VARIATION  = new HashMap<>();

    private static boolean loaded = false;

    /* ========================================================= */
    /* LOADING                                                   */
    /* ========================================================= */

    public static void loadBiomeMap(InputStream stream) {
        try {
            biomeMap = ImageIO.read(stream);
            if (biomeMap == null) throw new IllegalStateException("Biome map PNG read as null");
            width  = biomeMap.getWidth();
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
            if (root == null) throw new IllegalStateException("Biome color JSON parsed as null");

            COLOR_TO_BIOME.clear();
            COLOR_TO_BASE_H.clear();
            COLOR_TO_VARIATION.clear();

            for (Map.Entry<String, com.google.gson.JsonElement> entry : root.entrySet()) {
                String hex = entry.getKey();
                if (!hex.startsWith("#") || hex.length() != 7) {
                    throw new IllegalArgumentException("Invalid color key: " + hex);
                }
                int rgb = Integer.parseInt(hex.substring(1), 16);
                JsonObject data = entry.getValue().getAsJsonObject();

                String biomeId = data.get("biome").getAsString();
                double baseHeight     = data.has("base_height")
                        ? data.get("base_height").getAsDouble()
                        : DEFAULT_BASE_HEIGHT;
                double heightVariation = data.has("height_variation")
                        ? data.get("height_variation").getAsDouble()
                        : DEFAULT_HEIGHT_VARIATION;

                System.out.println("[GoT] Color entry: " + hex + " -> " + biomeId
                        + " (base=" + baseHeight + " var=" + heightVariation + ")");

                COLOR_TO_BIOME.put(rgb, ResourceKey.create(
                        Registries.BIOME, ResourceLocation.parse(biomeId)));
                COLOR_TO_BASE_H.put(rgb, baseHeight);
                COLOR_TO_VARIATION.put(rgb, heightVariation);
            }

            System.out.println("[GoT] Loaded " + COLOR_TO_BIOME.size() + " biome color entries");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read biome color map", e);
        }
    }

    public static void finishLoading() {
        loaded = true;
        System.out.println("[GoT] BiomeMapLoader initialized (biome-driven heights, no heightmap PNG)");
    }

    /* ========================================================= */
    /* PIXEL LOOKUP (shared by all public methods)               */
    /* ========================================================= */

    /** Returns the raw RGB (24-bit) at world position, or -1 if out of bounds / not loaded. */
    private static int getRGB(int worldX, int worldZ) {
        if (!loaded || biomeMap == null) return -1;
        int mapCenterX = width  / 2;
        int mapCenterZ = height / 2;
        int px = (worldX / MAP_SCALE) + mapCenterX;
        int pz = (worldZ / MAP_SCALE) + mapCenterZ;
        if (px < 0 || pz < 0 || px >= width || pz >= height) return -1;
        return biomeMap.getRGB(px, pz) & 0xFFFFFF;
    }

    /* ========================================================= */
    /* BIOME LOOKUP (used by GotBiomeSource)                     */
    /* ========================================================= */

    public static ResourceKey<Biome> getBiome(int worldX, int worldZ) {
        int rgb = getRGB(worldX, worldZ);
        if (rgb == -1) return null;
        ResourceKey<Biome> key = COLOR_TO_BIOME.get(rgb);
        if (key == null && Math.random() < 0.001) {
            System.out.println("[GoT] Unknown biome color at (" + worldX + "," + worldZ
                    + "): #" + String.format("%06X", rgb));
        }
        return key;
    }

    /* ========================================================= */
    /* HEIGHT DATA (used by GotChunkGenerator)                   */
    /* ========================================================= */

    /** Base Y height for the biome at this world position. */
    public static double getBaseHeight(int worldX, int worldZ) {
        int rgb = getRGB(worldX, worldZ);
        if (rgb == -1) return DEFAULT_BASE_HEIGHT;
        return COLOR_TO_BASE_H.getOrDefault(rgb, DEFAULT_BASE_HEIGHT);
    }

    /** Height variation (controls noise amplitude) for the biome at this position. */
    public static double getHeightVariation(int worldX, int worldZ) {
        int rgb = getRGB(worldX, worldZ);
        if (rgb == -1) return DEFAULT_HEIGHT_VARIATION;
        return COLOR_TO_VARIATION.getOrDefault(rgb, DEFAULT_HEIGHT_VARIATION);
    }

    /* ========================================================= */
    /* GETTERS                                                   */
    /* ========================================================= */

    public static Set<ResourceKey<Biome>> getAllBiomes() { return Set.copyOf(COLOR_TO_BIOME.values()); }
    public static int getWidth()  { return width;  }
    public static int getHeight() { return height; }
    public static boolean isLoaded() { return loaded; }

    private BiomeMapLoader() {}
}