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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Maps biomemap pixel colors to terrain shape parameters used by
 * {@link GotChunkGenerator}.
 *
 * <p>Data is read from {@code biome_colors.json}, which stores
 * {@code base_height} (blocks above sea level, negative = below) and
 * {@code height_variation} (amplitude of terrain noise in blocks) per biome.
 * Water biomes (negative {@code base_height}) are flagged with
 * {@link Params#isWater}.
 *
 * <p>Call {@link #load(ResourceManager)} off-thread, then
 * {@link #apply(Map)} on the main thread.
 */
public final class GotBiomeTerrainParams {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation COLORS_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/biomecolors/biome_colors.json");

    /**
     * Global amplitude multiplier applied on top of each biome's
     * {@code height_variation} when computing terrain noise.
     * Matches the {@code AMP_SMOOTH} constant the chunk generator references.
     */
    public static final float AMP_SMOOTH = 1.0f;

    // ── Static param store ─────────────────────────────────────────────────

    private static volatile Map<Integer, Params> colorToParams = Map.of();
    private static final Params FALLBACK = new Params(8f, 4f, false);

    private GotBiomeTerrainParams() {}

    // ── Params record ──────────────────────────────────────────────────────

    /**
     * Terrain shape parameters for one biome.
     *
     * @param baseY    Surface Y at the centre of the biome
     *                 ({@code SEA_LEVEL + base_height} from JSON).
     * @param scale    Noise amplitude in blocks ({@code height_variation}).
     * @param isWater  True when the biome sits below sea level (rivers, lakes,
     *                 oceans). The chunk generator uses this to select the water
     *                 path vs the land path during blending.
     */
    public record Params(float baseY, float scale, boolean isWater) {}

    // ── Query ──────────────────────────────────────────────────────────────

    /**
     * Returns the {@link Params} for a raw 0xRRGGBB pixel color.
     * Falls back to nearest-color match for PNG-compressed pixels, and to
     * {@link #FALLBACK} (gentle plains) if the color table is empty.
     */
    public static Params forColor(int rgb) {
        Map<Integer, Params> map = colorToParams;
        if (map.isEmpty()) return FALLBACK;
        Params direct = map.get(rgb & 0xFFFFFF);
        if (direct != null) return direct;

        // Nearest squared RGB distance
        int    bestDist = Integer.MAX_VALUE;
        Params best     = FALLBACK;
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

    /** Reads biome_colors.json off-thread. Returns an empty map on failure. */
    public static Map<Integer, Params> load(ResourceManager manager) {
        Map<Integer, Params> map = new LinkedHashMap<>();
        int seaLevel = GotChunkGenerator.SEA_LEVEL;
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
                    float baseHeight      = obj.get("base_height").getAsFloat();
                    float heightVariation = obj.get("height_variation").getAsFloat();
                    float baseY  = seaLevel + baseHeight;
                    boolean isWater = baseHeight < 0;
                    map.put(rgb, new Params(baseY, heightVariation, isWater));
                }
            }
            LOGGER.info("[GoT] Loaded {} terrain param entries", map.size());
        } catch (Exception e) {
            LOGGER.error("[GoT] Failed to parse biome_colors.json", e);
        }
        return map;
    }

    /** Pushes loaded params into the static store. Call on the main thread. */
    public static void apply(Map<Integer, Params> params) {
        colorToParams = params;
    }
}
