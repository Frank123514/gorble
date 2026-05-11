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
 * Maps biomemap pixel colors → terrain shape parameters.
 *
 * <p>Each entry in {@code got:worldgen/biomecolors/biome_colors.json}
 * describes one biomemap color and has the form:
 *
 * <pre>{@code
 * "#RRGGBB": {
 *   "base_height":      64,
 *   "height_variation": 8,
 *   "biome":            "got:some_biome"
 * }
 * }</pre>
 *
 * <ul>
 *   <li>{@code base_height} — the target surface Y for this biome.</li>
 *   <li>{@code height_variation} — noise amplitude in blocks; controls how
 *       hilly this biome is.  Defaults to {@code 4} if omitted.</li>
 *   <li>{@code biome} — namespaced biome registry ID.
 *       Defaults to {@code "got:north"} if omitted.</li>
 * </ul>
 */
public final class GotBiomeTerrainParams {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation COLORS_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/biomecolors/biome_colors.json");

    /** Sea level used to derive {@link Params#isWater()}. */
    private static final int SEA_LEVEL = GotChunkGenerator.SEA_LEVEL;

    /** Used when no color match is found. */
    public static final Params FALLBACK = new Params(71f, 4f, false, "got:north");

    private static volatile Map<Integer, Params> colorToParams = Map.of();

    private GotBiomeTerrainParams() {}

    // ── Params record ──────────────────────────────────────────────────────

    /**
     * Terrain parameters for one biomemap color.
     *
     * @param baseHeight      target surface Y (absolute world height)
     * @param heightVariation noise amplitude in blocks
     * @param isWater         {@code true} when {@code baseHeight < SEA_LEVEL}
     * @param biomeId         namespaced biome registry ID (e.g. {@code "got:north"})
     */
    public record Params(float baseHeight, float heightVariation,
                         boolean isWater, String biomeId) {}

    // ── Query ──────────────────────────────────────────────────────────────

    /**
     * Returns the {@link Params} for the given {@code 0xRRGGBB} color.
     * Falls back to the nearest color in Euclidean RGB space if no exact
     * match exists, and ultimately to {@link #FALLBACK} if the map is empty.
     */
    public static Params forColor(int rgb) {
        Map<Integer, Params> map = colorToParams;
        if (map.isEmpty()) return FALLBACK;

        // Exact match (most common path)
        Params hit = map.get(rgb & 0xFFFFFF);
        if (hit != null) return hit;

        // Nearest-colour fallback
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

    // ── Load / apply ───────────────────────────────────────────────────────

    /**
     * Parses {@code biome_colors.json} from the resource manager.
     * Safe to call off the main thread.
     */
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

    /**
     * Applies a freshly loaded parameter map.  Must be called on the main thread.
     */
    public static void apply(Map<Integer, Params> params) {
        colorToParams = params;
    }
}
