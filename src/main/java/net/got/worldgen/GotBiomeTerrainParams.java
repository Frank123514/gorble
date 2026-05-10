package net.got.worldgen;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Maps biomemap pixel colors to terrain shape parameters.
 * Surface block placement is handled entirely by JSON disk features —
 * there is no slope-map system in this class.
 */
public final class GotBiomeTerrainParams {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation COLORS_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/biomecolors/biome_colors.json");

    private static volatile Map<Integer, Params> colorToParams = Map.of();

    public static final Params FALLBACK =
            new Params(71f, 4f, false, false, "got:north");

    private GotBiomeTerrainParams() {}

    // ── Params record ──────────────────────────────────────────────────────

    /**
     * Terrain shape parameters for one biome.
     *
     * @param baseY   Absolute surface Y target.
     * @param scale   Noise amplitude in blocks (height_variation).
     * @param isWater True for any water biome (ocean, lake, river).
     * @param isRiver True for narrow river biomes.
     * @param biomeId Namespaced biome ID string.
     */
    public record Params(float baseY, float scale, boolean isWater, boolean isRiver,
                         String biomeId) {}

    // ── Query ──────────────────────────────────────────────────────────────

    public static Params forColor(int rgb) {
        Map<Integer, Params> map = colorToParams;
        if (map.isEmpty()) return FALLBACK;
        Params direct = map.get(rgb & 0xFFFFFF);
        if (direct != null) return direct;

        // Nearest-colour fallback
        int bestDist = Integer.MAX_VALUE;
        Params best  = FALLBACK;
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        for (var e : map.entrySet()) {
            int k  = e.getKey();
            int dr = r - ((k >> 16) & 0xFF);
            int dg = g - ((k >>  8) & 0xFF);
            int db = b - ( k        & 0xFF);
            int d  = dr*dr + dg*dg + db*db;
            if (d < bestDist) { bestDist = d; best = e.getValue(); }
        }
        return best;
    }

    // ── Load / apply ───────────────────────────────────────────────────────

    public static Map<Integer, Params> load(ResourceManager manager) {
        Map<Integer, Params> map = new LinkedHashMap<>();
        int   seaLevel        = GotChunkGenerator.SEA_LEVEL;
        float riverThreshold  = seaLevel - BiomemapLoader.MAP_SCALE;

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

                    boolean isWater = baseHeight < seaLevel;
                    boolean isRiver = isWater && baseHeight > riverThreshold;

                    map.put(rgb, new Params(baseHeight, heightVariation,
                            isWater, isRiver, biomeId));
                }
            }
            LOGGER.info("[GoT] Loaded {} terrain param entries", map.size());
        } catch (Exception e) {
            LOGGER.error("[GoT] Failed to parse biome_colors.json", e);
        }
        return map;
    }

    public static void apply(Map<Integer, Params> params) {
        colorToParams = params;
    }

}
