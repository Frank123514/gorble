package net.got.worldgen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.got.worldgen.SimplexNoise;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Resolves procedural sub-biomes — smaller biomes that randomly generate
 * inside a parent (map-painted) biome.
 *
 * <h3>How it works</h3>
 * <p>For each world position the {@link GotBiomeSource} calls
 * {@link #resolve(String, int, int)} after it has determined the normal
 * "winner" biome.  If the noise field for any registered subbiome exceeds
 * its threshold at that position, the subbiome ID is returned and used
 * instead, creating organic, irregularly-shaped patches.
 *
 * <h3>Data file</h3>
 * <p>Subbiomes are declared in
 * {@code data/got/worldgen/subbiomes/subbiomes.json} (data-pack reloadable).
 * Format:
 *
 * <pre>{@code
 * {
 *   "got:north": [
 *     {
 *       "subbiome":   "got:maple_forest",
 *       "noise_scale": 250.0,
 *       "threshold":   0.65,
 *       "priority":    0
 *     }
 *   ],
 *   "got:wolfswood": [
 *     {
 *       "subbiome":   "got:wolfswood_clearing",
 *       "noise_scale": 180.0,
 *       "threshold":   0.60,
 *       "priority":    0
 *     },
 *     {
 *       "subbiome":   "got:wolfswood_dark_hollow",
 *       "noise_scale": 120.0,
 *       "threshold":   0.72,
 *       "priority":    1
 *     }
 *   ]
 * }
 * }</pre>
 *
 * <ul>
 *   <li>{@code subbiome}    — namespaced biome ID to place (must be registered).</li>
 *   <li>{@code noise_scale} — world-space patch size in blocks; larger = bigger patches.</li>
 *   <li>{@code threshold}   — 0–1; fraction of parent covered ≈ {@code 1 - threshold}.</li>
 *   <li>{@code priority}    — higher = checked first; first match wins.  Defaults to 0.</li>
 * </ul>
 *
 * <h3>Thread safety</h3>
 * <p>{@link #load} runs off the main thread (inside
 * {@code MapReloadListener#prepare}).  {@link #apply} and {@link #initSeed}
 * must be called on the main thread.  {@link #resolve} is safe on any thread
 * after {@link #apply} returns.
 */
public final class SubbiomeResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation SUBBIOMES_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/subbiomes/subbiomes.json");

    /**
     * Spread applied to noise coordinates so each subbiome uses a different
     * region of noise space, preventing every subbiome in the same parent from
     * having the same patch layout.
     *
     * <p>Derived at load time from {@code subbiome_id.hashCode()} so the
     * offsets are stable across reloads for the same biome ID.
     */
    private static final double OFFSET_SPREAD = 3_000.0;

    // ── Live state (written on main thread, read everywhere) ───────────────

    /**
     * Map from parent biome ID → list of {@link SubbiomeDef} sorted by
     * descending priority (highest priority checked first).
     */
    private static volatile Map<String, List<SubbiomeDef>> subbiomeMap = Map.of();

    /**
     * World-seeded noise instance used for all subbiome noise queries.
     * Seeded separately from terrain noise to avoid correlated patterns.
     */
    private static volatile SimplexNoise noise = SimplexNoise.seeded(0L);

    private SubbiomeResolver() {}

    // ── Seed initialisation ────────────────────────────────────────────────

    /**
     * Seeds the subbiome noise from the world seed.  Call this alongside
     * {@link GotChunkGenerator#initNoise(long)}.
     *
     * <p>An XOR constant is mixed in so subbiome patches are independent of
     * terrain noise even when both use the same underlying permutation table.
     */
    public static void initSeed(long worldSeed) {
        noise = SimplexNoise.seeded(worldSeed ^ 0xB16B00B5_DEADBEEFL);
        LOGGER.debug("[GoT] SubbiomeResolver seeded with world seed {}", worldSeed);
    }

    // ── Query ──────────────────────────────────────────────────────────────

    /**
     * Returns the subbiome ID that should replace {@code parentBiomeId} at
     * world position ({@code worldX}, {@code worldZ}), or {@code null} if the
     * parent biome should be kept as-is.
     *
     * <p>Subbiomes are evaluated in descending priority order; the first one
     * whose noise value at this position exceeds its threshold is returned.
     *
     * @param parentBiomeId the biome that would normally be placed here
     *                      (e.g. {@code "got:north"})
     * @param worldX        world X block coordinate
     * @param worldZ        world Z block coordinate
     * @return override subbiome ID, or {@code null}
     */
    @Nullable
    public static String resolve(String parentBiomeId, int worldX, int worldZ) {
        List<SubbiomeDef> defs = subbiomeMap.get(parentBiomeId);
        if (defs == null || defs.isEmpty()) return null;

        SimplexNoise n = noise; // capture volatile once
        for (SubbiomeDef def : defs) {
            // Simplex returns [-1, 1]; normalise to [0, 1] for threshold comparison.
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

    /**
     * Terrain-aware variant of {@link #resolve}.
     *
     * <p>Unlike {@link #resolve} (which is a hard threshold cutoff — you're
     * either in the patch or you're not, because a named biome/feature swap
     * genuinely needs a boundary), this is deliberately NOT threshold-gated.
     * Height should never have a hard edge — a threshold cutoff always
     * looks like a stamped-down patch no matter how wide you make the
     * blend band at the edge, because everything outside the band is
     * still 100% untouched. Instead this always returns the first def
     * with a terrain override (if the parent has one) along with the
     * raw, continuous, normalised noise value at this exact position, so
     * the chunk generator can blend height proportionally to the same
     * smooth noise field the patch shape is drawn from — the terrain
     * just gradually rises and falls with the noise, same as the base
     * terrain shape does, with the "hills" simply being where that noise
     * happens to peak.
     *
     * @param parentBiomeId parent biome ID
     * @param worldX        world X block coordinate
     * @param worldZ        world Z block coordinate
     * @param noiseOut      single-element array; receives the normalised noise
     *                      value [0,1] at this position for the returned def,
     *                      or -1 if the parent has no terrain-override
     *                      subbiome at all. Pass {@code null} to skip.
     * @return the first {@link SubbiomeDef} with a terrain override for this
     *         parent, or {@code null} if the parent has none
     */
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

    // ── Load / apply ───────────────────────────────────────────────────────

    /**
     * Parses {@code subbiomes.json} from the resource manager.
     * Safe to call off the main thread.
     *
     * @return map from parent biome ID → sorted list of {@link SubbiomeDef},
     *         or an empty map if the file is absent or unparsable
     */
    public static Map<String, List<SubbiomeDef>> load(ResourceManager manager) {
        Map<String, List<SubbiomeDef>> result = new LinkedHashMap<>();

        Optional<Resource> res = manager.getResource(SUBBIOMES_LOC);
        if (res.isEmpty()) {
            // File is optional — not all setups need subbiomes.
            LOGGER.debug("[GoT] No subbiomes.json found at {} — subbiome system disabled", SUBBIOMES_LOC);
            return result;
        }

        try (var reader = new InputStreamReader(res.get().open(), StandardCharsets.UTF_8)) {
            JsonObject root = new Gson().fromJson(reader, JsonObject.class);

            for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                String parentId = entry.getKey();

                // Skip comment fields or any non-array values at the root level.
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

                    // Terrain override fields — negative sentinel = inherit from parent
                    float baseHeight      = obj.has("base_height")
                            ? obj.get("base_height").getAsFloat()       : -1f;
                    float heightVariation = obj.has("height_variation")
                            ? obj.get("height_variation").getAsFloat()   : -1f;
                    float blendRadius     = obj.has("blend_radius")
                            ? obj.get("blend_radius").getAsFloat()       : 24f;

                    // Derive unique, stable noise offsets from the subbiome ID's
                    // hash code so every subbiome has its own patch layout even
                    // within the same parent biome.
                    int hash = subbiomeId.hashCode();
                    double offsetX = ((hash & 0xFFFF)       - 32768) / 32768.0 * OFFSET_SPREAD;
                    double offsetZ = (((hash >> 16) & 0xFFFF) - 32768) / 32768.0 * OFFSET_SPREAD;

                    defs.add(new SubbiomeDef(subbiomeId, noiseScale, threshold,
                            priority, offsetX, offsetZ,
                            baseHeight, heightVariation, blendRadius));
                }

                // Sort descending by priority so highest-priority entries are
                // checked first in resolve().
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

    /**
     * Applies a freshly loaded subbiome map.  Must be called on the main thread.
     */
    public static void apply(Map<String, List<SubbiomeDef>> map) {
        subbiomeMap = map;
    }

    // ── Debug helpers ──────────────────────────────────────────────────────

    /** Returns an unmodifiable view of the loaded subbiome map for debugging. */
    public static Map<String, List<SubbiomeDef>> getSubbiomeMap() {
        return Collections.unmodifiableMap(subbiomeMap);
    }

    /**
     * Returns the raw normalised noise value (0-1) that would be compared
     * against the threshold for a given subbiome def at world position (x, z).
     */
    public static double sampleNoise(SubbiomeDef def, int worldX, int worldZ) {
        SimplexNoise n = noise;
        double raw = n.eval(
                (worldX + def.noiseOffsetX()) / def.noiseScale(),
                (worldZ + def.noiseOffsetZ()) / def.noiseScale()
        );
        return (raw + 1.0) * 0.5;
    }
}