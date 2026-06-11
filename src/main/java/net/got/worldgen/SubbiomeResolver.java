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
 * <h3>Terrain blending</h3>
 * <p>When a subbiome declares {@code base_height} or {@code height_variation},
 * those values are <em>blended</em> against the parent's bicubic-interpolated
 * terrain values using a smooth weight derived from how deep into the subbiome's
 * noise field the position sits:
 *
 * <pre>
 *   blendWeight = smoothstep( (noise - threshold) / blendRange )
 *   finalHeight = lerp(parentHeight, subbiomeHeight, blendWeight)
 * </pre>
 *
 * This produces the same kind of gradual slope transition that the biomemap's
 * bicubic interpolation gives to normal biomes — no hard cliff at the edge.
 *
 * <h3>Data file format</h3>
 * <pre>{@code
 * {
 *   "got:north": [
 *     {
 *       "subbiome":        "got:subbiome/maple_forest",
 *       "noise_scale":      250.0,
 *       "threshold":        0.65,
 *       "priority":         0,
 *       "base_height":      68,
 *       "height_variation": 5,
 *       "blend_range":      0.15
 *     }
 *   ]
 * }
 * }</pre>
 *
 * <ul>
 *   <li>{@code subbiome}         — namespaced biome ID (must be registered).</li>
 *   <li>{@code noise_scale}      — patch size in blocks.</li>
 *   <li>{@code threshold}        — 0–1; coverage ≈ {@code 1 - threshold}.</li>
 *   <li>{@code priority}         — higher = checked first.</li>
 *   <li>{@code base_height}      — <em>optional</em> target surface Y.</li>
 *   <li>{@code height_variation} — <em>optional</em> noise amplitude in blocks.</li>
 *   <li>{@code blend_range}      — <em>optional</em> noise width of the blend zone
 *                                  (default 0.15). Smaller = sharper edge.</li>
 * </ul>
 */
public final class SubbiomeResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation SUBBIOMES_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/subbiomes/subbiomes.json");

    private static final double OFFSET_SPREAD   = 3_000.0;
    private static final double DEFAULT_BLEND   = 0.15;

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

    /** Smoothstep: 3t²-2t³, clamped to [0,1]. */
    private static float smoothstep(float t) {
        t = Math.max(0f, Math.min(1f, t));
        return t * t * (3f - 2f * t);
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

    /**
     * Returns blended terrain-parameter overrides for the winning subbiome at
     * ({@code worldX}, {@code worldZ}), or {@code null} if no subbiome matches
     * or the matching subbiome has no terrain overrides.
     *
     * <p>The returned {@link SubbiomeTerrainOverride#blendWeight()} is a
     * smoothstepped value in [0,1]:
     * <ul>
     *   <li>0 at the threshold edge → caller should use 100% parent values.</li>
     *   <li>1 deep inside the subbiome → caller should use 100% subbiome values.</li>
     * </ul>
     * Lerp between parent and subbiome values with this weight to get a
     * seamless, cliff-free height transition matching biomemap behaviour.
     */
    @Nullable
    public static SubbiomeTerrainOverride resolveTerrainParams(
            String parentBiomeId, int worldX, int worldZ) {
        List<SubbiomeDef> defs = subbiomeMap.get(parentBiomeId);
        if (defs == null || defs.isEmpty()) return null;

        for (SubbiomeDef def : defs) {
            double normalised = sampleNormalised(def, worldX, worldZ);
            if (normalised >= def.threshold() && def.hasTerrainOverride()) {
                // How far past the threshold are we, as a fraction of blendRange?
                // 0 = right at the edge, 1 = fully inside (blendRange past threshold).
                float rawT = (float) ((normalised - def.threshold()) / def.blendRange());
                float blendWeight = smoothstep(rawT);
                return new SubbiomeTerrainOverride(
                        def.baseHeight(), def.heightVariation(), blendWeight);
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
                    double blendRange = obj.has("blend_range")
                            ? obj.get("blend_range").getAsDouble()  : DEFAULT_BLEND;

                    OptionalDouble baseHeight = obj.has("base_height")
                            ? OptionalDouble.of(obj.get("base_height").getAsDouble())
                            : OptionalDouble.empty();
                    OptionalDouble heightVariation = obj.has("height_variation")
                            ? OptionalDouble.of(obj.get("height_variation").getAsDouble())
                            : OptionalDouble.empty();

                    int hash = subbiomeId.hashCode();
                    double offsetX = ((hash & 0xFFFF)         - 32768) / 32768.0 * OFFSET_SPREAD;
                    double offsetZ = (((hash >> 16) & 0xFFFF) - 32768) / 32768.0 * OFFSET_SPREAD;

                    defs.add(new SubbiomeDef(subbiomeId, noiseScale, threshold,
                            priority, offsetX, offsetZ, baseHeight, heightVariation, blendRange));
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
