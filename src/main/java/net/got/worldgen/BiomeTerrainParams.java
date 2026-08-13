package net.got.worldgen;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class BiomeTerrainParams {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Identifier COLORS_LOC =
            Identifier.fromNamespaceAndPath("got", "worldgen/biomecolors/biome_colors.json");

    private static final int SEA_LEVEL = GotChunkGenerator.SEA_LEVEL;

    public static final Params FALLBACK = new Params(71f, 4f, false, "got:north");

    private static volatile Map<Integer, Params> colorToParams = Map.of();

    private BiomeTerrainParams() {}

    public record Params(float baseHeight, float heightVariation,
                         boolean isWater, String biomeId) {}

    public static Params forColor(int rgb) {
        Map<Integer, Params> map = colorToParams;
        if (map.isEmpty()) return FALLBACK;

        Params hit = map.get(rgb & 0xFFFFFF);
        if (hit != null) return hit;

        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >>  8) & 0xFF;
        int b =  rgb        & 0xFF;
        int   bestDist = Integer.MAX_VALUE;
        Params best    = FALLBACK;
        for (var entry : map.entrySet()) {
            int k  = entry.getKey();
            int dr = r - ((k >> 16) & 0xFF);
            int dg = g - ((k >>  8) & 0xFF);
            int db = b - ( k        & 0xFF);
            int d  = dr*dr + dg*dg + db*db;
            if (d < bestDist) { bestDist = d; best = entry.getValue(); }
        }
        return best;
    }

    public static Map<Integer, Params> load(ResourceManager manager) {
        Map<Integer, Params> map = new LinkedHashMap<>();
        try {
            Optional<Resource> res = manager.getResource(COLORS_LOC);
            if (res.isEmpty()) {
                LOGGER.warn("[GoT] biome_colors.json not found at {}", COLORS_LOC);
                return map;
            }

            try (var reader = new InputStreamReader(res.get().open(), StandardCharsets.UTF_8)) {
                JsonObject root = new Gson().fromJson(reader, JsonObject.class);
                for (Map.Entry<String, JsonElement> kv : root.entrySet()) {
                    int rgb = Integer.parseInt(kv.getKey().replace("#", ""), 16);
                    JsonObject obj = kv.getValue().getAsJsonObject();

                    float  baseHeight      = obj.get("base_height").getAsFloat();
                    float  heightVariation = obj.has("height_variation")
                            ? obj.get("height_variation").getAsFloat() : 4f;
                    String biomeId         = obj.has("biome")
                            ? obj.get("biome").getAsString() : "got:north";
                    boolean isWater        = baseHeight < SEA_LEVEL;

                    map.put(rgb, new Params(baseHeight, heightVariation, isWater, biomeId));
                }
            }
            LOGGER.info("[GoT] Loaded {} biome color entries", map.size());

        } catch (Exception e) {
            LOGGER.error("[GoT] Failed to parse biome_colors.json", e);
        }
        return map;
    }

    public static void apply(Map<Integer, Params> params) {
        colorToParams = params;
    }
}
