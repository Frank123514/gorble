package net.got.worldgen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class SubbiomeResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Identifier SUBBIOMES_LOC =
            Identifier.fromNamespaceAndPath("got", "worldgen/subbiomes/subbiomes.json");

    private static final double OFFSET_SPREAD = 3_000.0;

    private static volatile Map<String, List<SubbiomeDef>> subbiomeMap = Map.of();

    private static volatile SimplexNoise noise = SimplexNoise.seeded(0L);

    private SubbiomeResolver() {}

    public static void initSeed(long worldSeed) {
        noise = SimplexNoise.seeded(worldSeed ^ 0xB16B00B5_DEADBEEFL);
        LOGGER.debug("[GoT] SubbiomeResolver seeded with world seed {}", worldSeed);
    }

    @Nullable
    public static String resolve(String parentBiomeId, int worldX, int worldZ) {
        List<SubbiomeDef> defs = subbiomeMap.get(parentBiomeId);
        if (defs == null || defs.isEmpty()) return null;

        SimplexNoise n = noise;
        for (SubbiomeDef def : defs) {
            
            double raw        = n.eval(
                    (worldX + def.noiseOffsetX()) / def.noiseScale(),
                    (worldZ + def.noiseOffsetZ()) / def.noiseScale()
            );
            double normalised = (raw + 1.0) * 0.5;
            if (normalised >= def.threshold()) {
                return def.subbiomeId();
            }
        }
        return null;
    }

    @Nullable
    public static SubbiomeDef resolveTerrain(String parentBiomeId,
                                             int worldX, int worldZ,
                                             float @Nullable [] noiseOut) {
        List<SubbiomeDef> defs = subbiomeMap.get(parentBiomeId);
        if (defs == null || defs.isEmpty()) {
            if (noiseOut != null) noiseOut[0] = -1f;
            return null;
        }

        SimplexNoise n = noise;
        for (SubbiomeDef def : defs) {
            if (!def.hasTerrainOverride()) continue;
            double raw        = n.eval(
                    (worldX + def.noiseOffsetX()) / def.noiseScale(),
                    (worldZ + def.noiseOffsetZ()) / def.noiseScale()
            );
            double normalised = (raw + 1.0) * 0.5;
            if (noiseOut != null) noiseOut[0] = (float) normalised;
            return def;
        }
        if (noiseOut != null) noiseOut[0] = -1f;
        return null;
    }

    public static Map<String, List<SubbiomeDef>> load(ResourceManager manager) {
        Map<String, List<SubbiomeDef>> result = new LinkedHashMap<>();

        Optional<Resource> res = manager.getResource(SUBBIOMES_LOC);
        if (res.isEmpty()) {
            
            LOGGER.debug("[GoT] No subbiomes.json found at {} — subbiome system disabled", SUBBIOMES_LOC);
            return result;
        }

        try (var reader = new InputStreamReader(res.get().open(), StandardCharsets.UTF_8)) {
            JsonObject root = new Gson().fromJson(reader, JsonObject.class);

            for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                String parentId = entry.getKey();

                if (!entry.getValue().isJsonArray()) continue;
                JsonArray arr = entry.getValue().getAsJsonArray();

                List<SubbiomeDef> defs = new ArrayList<>(arr.size());
                for (JsonElement el : arr) {
                    JsonObject obj = el.getAsJsonObject();

                    String subbiomeId = obj.get("subbiome").getAsString();
                    double noiseScale = obj.has("noise_scale")
                            ? obj.get("noise_scale").getAsDouble() : 200.0;
                    double threshold  = obj.has("threshold")
                            ? obj.get("threshold").getAsDouble()   : 0.65;
                    int    priority   = obj.has("priority")
                            ? obj.get("priority").getAsInt()        : 0;

                    float baseHeight      = obj.has("base_height")
                            ? obj.get("base_height").getAsFloat()       : -1f;
                    float heightVariation = obj.has("height_variation")
                            ? obj.get("height_variation").getAsFloat()   : -1f;
                    float blendRadius     = obj.has("blend_radius")
                            ? obj.get("blend_radius").getAsFloat()       : 24f;

                    int hash = subbiomeId.hashCode();
                    double offsetX = ((hash & 0xFFFF)       - 32768) / 32768.0 * OFFSET_SPREAD;
                    double offsetZ = (((hash >> 16) & 0xFFFF) - 32768) / 32768.0 * OFFSET_SPREAD;

                    defs.add(new SubbiomeDef(subbiomeId, noiseScale, threshold,
                            priority, offsetX, offsetZ,
                            baseHeight, heightVariation, blendRadius));
                }

                defs.sort(Comparator.comparingInt(SubbiomeDef::priority).reversed());
                result.put(parentId, Collections.unmodifiableList(defs));
            }

            int totalDefs = result.values().stream().mapToInt(List::size).sum();
            LOGGER.info("[GoT] Loaded {} subbiome definitions across {} parent biomes",
                    totalDefs, result.size());

        } catch (Exception e) {
            LOGGER.error("[GoT] Failed to parse subbiomes.json", e);
        }

        return result;
    }

    public static void apply(Map<String, List<SubbiomeDef>> map) {
        subbiomeMap = map;
    }

    public static Map<String, List<SubbiomeDef>> getSubbiomeMap() {
        return Collections.unmodifiableMap(subbiomeMap);
    }

    public static double sampleNoise(SubbiomeDef def, int worldX, int worldZ) {
        SimplexNoise n = noise;
        double raw = n.eval(
                (worldX + def.noiseOffsetX()) / def.noiseScale(),
                (worldZ + def.noiseOffsetZ()) / def.noiseScale()
        );
        return (raw + 1.0) * 0.5;
    }
}