package net.got.worldgen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Resolves procedural sub-biomes — smaller biomes that randomly generate
 * inside a parent (map-painted) biome.
 *
 */
public final class SubbiomeResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation SUBBIOMES_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/subbiomes/subbiomes.json");

    private static final double OFFSET_SPREAD   = 3_000.0;

    // ── Live state ─────────────────────────────────────────────────────────

    private static volatile Map<String, List<SubbiomeDef>> subbiomeMap = Map.of();
    private static volatile SimplexNoise noise = SimplexNoise.seeded(0L);

    private SubbiomeResolver() {}

    // ── Seed ───────────────────────────────────────────────────────────────

    public static void initSeed(long worldSeed) {
        noise = SimplexNoise.seeded(worldSeed ^ 0xB16B00B5_DEADBEEFL);
        LOGGER.debug("[GoT] SubbiomeResolver seeded with world seed {}", worldSeed);
    }

    // ── Noise helper ───────────────────────────────────────────────────────

    /** Returns normalised noise in [0,1] for the given def and position. */
    private static double sampleNormalised(SubbiomeDef def, int worldX, int worldZ) {
        double raw = noise.eval(
                (worldX + def.noiseOffsetX()) / def.noiseScale(),
                (worldZ + def.noiseOffsetZ()) / def.noiseScale()
        );
        return (raw + 1.0) * 0.5;
    }


    // ── Query ──────────────────────────────────────────────────────────────

    /**
     * Returns the subbiome ID that should replace {@code parentBiomeId} at
     * ({@code worldX}, {@code worldZ}), or {@code null} to keep the parent.
     */
    @Nullable
    public static String resolve(String parentBiomeId, int worldX, int worldZ) {
        List<SubbiomeDef> defs = subbiomeMap.get(parentBiomeId);
        if (defs == null || defs.isEmpty()) return null;

        SimplexNoise n = noise;
        for (SubbiomeDef def : defs) {
            double normalised = sampleNormalised(def, worldX, worldZ);
            if (normalised >= def.threshold()) {
                return def.subbiomeId();
            }
        }
        return null;
    }


    // ── Load / apply ───────────────────────────────────────────────────────

    public static Map<String, List<SubbiomeDef>> load(ResourceManager manager) {
        Map<String, List<SubbiomeDef>> result = new LinkedHashMap<>();

        Optional<Resource> res = manager.getResource(SUBBIOMES_LOC);
        if (res.isEmpty()) {
            LOGGER.debug("[GoT] No subbiomes.json found — subbiome system disabled");
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

                    int hash = subbiomeId.hashCode();
                    double offsetX = ((hash & 0xFFFF)         - 32768) / 32768.0 * OFFSET_SPREAD;
                    double offsetZ = (((hash >> 16) & 0xFFFF) - 32768) / 32768.0 * OFFSET_SPREAD;

                    defs.add(new SubbiomeDef(subbiomeId, noiseScale, threshold,
                            priority, offsetX, offsetZ));
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

    // ── Debug helpers ──────────────────────────────────────────────────────

    public static Map<String, List<SubbiomeDef>> getSubbiomeMap() {
        return Collections.unmodifiableMap(subbiomeMap);
    }

    public static double sampleNoise(SubbiomeDef def, int worldX, int worldZ) {
        return sampleNormalised(def, worldX, worldZ);
    }
}