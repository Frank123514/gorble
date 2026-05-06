package net.got.worldgen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Maps biomemap pixel colors to terrain shape parameters used by
 * {@link GotChunkGenerator}.
 *
 * <p>Data is read from {@code biome_colors.json}.  Each entry may carry an
 * optional {@code slope_map} array — a port of Middle Earth's {@link SlopeMap}
 * system that selects the surface block based on terrain steepness.
 * When absent a sensible default slope map is used (grass on flat ground,
 * stone on steep slopes).
 */
public final class GotBiomeTerrainParams {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation COLORS_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/biomecolors/biome_colors.json");

    public static final float AMP_SMOOTH = 1.0f;

    private static volatile Map<Integer, Params> colorToParams = Map.of();

    /** Default slope map used when biome_colors.json doesn't specify one. */
    private static final SlopeMap DEFAULT_SLOPE_MAP = new SlopeMap()
            .addSlopeData(30f, Blocks.GRASS_BLOCK)
            .addSlopeData(50f, Blocks.DIRT)
            .addSlopeData(90f, Blocks.STONE);

    /** Slope map for water biomes — sand on flat beds, gravel on slopes, stone on steep. */
    private static final SlopeMap WATER_SLOPE_MAP = new SlopeMap()
            .addSlopeData(20f, Blocks.SAND)
            .addSlopeData(50f, Blocks.GRAVEL)
            .addSlopeData(90f, Blocks.STONE);

    private static final Params FALLBACK = new Params(71f, 0.5f, false, false, DEFAULT_SLOPE_MAP);

    private GotBiomeTerrainParams() {}

    // ── Params record ──────────────────────────────────────────────────────

    /**
     * Terrain shape parameters for one biome.
     *
     * @param baseY     Absolute surface Y for this biome.
     * @param scale     Noise amplitude modifier (0–1, ME: noiseModifier).
     * @param isWater   True for any water biome (river, ocean, lake).
     * @param isRiver   True for narrow river biomes carved by the SDF system.
     * @param slopeMap  ME-style slope map for surface block selection.
     */
    public record Params(float baseY, float scale, boolean isWater, boolean isRiver, SlopeMap slopeMap) {}

    // ── Query ──────────────────────────────────────────────────────────────

    public static Params forColor(int rgb) {
        Map<Integer, Params> map = colorToParams;
        if (map.isEmpty()) return FALLBACK;
        Params direct = map.get(rgb & 0xFFFFFF);
        if (direct != null) return direct;

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
                    float heightVariation = obj.has("height_variation")
                            ? obj.get("height_variation").getAsFloat() : 0.5f;
                    boolean isWater = baseHeight < seaLevel;
                    boolean isRiver = isWater && baseHeight > (seaLevel - 8f);

                    // ── slope_map (optional) ──────────────────────────────
                    // Format: [ { "angle": 30, "block": "minecraft:grass_block" }, ... ]
                    // Entries must be in ascending angle order.
                    SlopeMap slopeMap = parseSlopeMap(obj, isWater);

                    map.put(rgb, new Params(baseHeight, heightVariation, isWater, isRiver, slopeMap));
                }
            }
            LOGGER.info("[GoT] Loaded {} terrain param entries", map.size());
        } catch (Exception e) {
            LOGGER.error("[GoT] Failed to parse biome_colors.json", e);
        }
        return map;
    }

    /**
     * Parses the {@code slope_blocks} array from a biome JSON object.
     * Falls back to a water or land default when the field is absent or empty.
     *
     * JSON format: [ { "max_angle": 30, "block": "minecraft:grass_block" }, ... ]
     */
    private static SlopeMap parseSlopeMap(JsonObject obj, boolean isWater) {
        String key = obj.has("slope_blocks") ? "slope_blocks"
                : obj.has("slope_map")    ? "slope_map"
                  : null;

        if (key != null) {
            JsonArray arr = obj.getAsJsonArray(key);
            if (arr.size() > 0) {
                SlopeMap sm = new SlopeMap();
                for (JsonElement el : arr) {
                    JsonObject entry = el.getAsJsonObject();
                    // support both "max_angle" and "angle"
                    float angle = entry.has("max_angle")
                            ? entry.get("max_angle").getAsFloat()
                            : entry.get("angle").getAsFloat();
                    String blockId = entry.get("block").getAsString();
                    Block block = net.minecraft.core.registries.BuiltInRegistries.BLOCK
                            .getValue(ResourceLocation.parse(blockId));
                    if (block == null || block == Blocks.AIR) {
                        LOGGER.warn("[GoT] Unknown block in slope_blocks: {}", blockId);
                        block = Blocks.STONE;
                    }
                    sm.addSlopeData(angle, block);
                }
                return sm;
            }
        }
        // Water biomes get sand/gravel/stone; land biomes get grass/dirt/stone
        return isWater ? new SlopeMap(WATER_SLOPE_MAP) : new SlopeMap(DEFAULT_SLOPE_MAP);
    }

    public static void apply(Map<Integer, Params> params) {
        colorToParams = params;
    }
}