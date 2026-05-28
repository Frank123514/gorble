package net.got.worldgen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Data-driven system that replaces surface blocks on steep terrain with
 * biome-specific "exposed rock" to create natural cliff faces, mountain flanks,
 * and eroded hillsides.
 *
 * <h3>How it works</h3>
 * <p>After vanilla {@code buildSurface} runs, {@link #applySlopeBlocks} iterates
 * every surface column, computes the terrain slope angle in <em>degrees</em>
 * using the same approach as the Middle Earth mod — rise/run gradient sampled
 * at offset 3 across all four cardinal directions, averaged, then passed through
 * {@code Math.atan} — looks up the biome, and applies the first matching rule.
 *
 * <p>Using a real angle (0°–90°) instead of raw integer height-diffs eliminates
 * the staircase-quantisation artefact where {@code Mth.floor} on gentle slopes
 * produced spurious 2–3 block integer jumps that triggered rules everywhere.
 *
 * <p>Per-column noise jitter shifts the threshold slightly at each position so
 * band edges are organic and irregular rather than geometric.
 *
 * <h3>Data file</h3>
 * {@code data/got/worldgen/slope_rules/slope_rules.json}
 *
 * <ul>
 *   <li>{@code min_slope} — angle threshold in <strong>degrees</strong> (0–90).
 *       Typical values: 20° = noticeable hill, 35° = steep hillside,
 *       50° = cliff face, 65° = near-vertical escarpment.</li>
 *   <li>{@code block}     — namespaced block ID (default {@code minecraft:stone}).</li>
 *   <li>{@code depth}     — blocks below surface top to replace (default 3, min 1).</li>
 *   <li>{@code jitter}    — noise magnitude in degrees added to threshold per-column
 *       to break up band edges (default 3.0, range 0–10 recommended).</li>
 * </ul>
 */
public final class SlopeSurfaceResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation SLOPE_RULES_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/slope_rules/slope_rules.json");

    /**
     * World-space scale of the threshold-jitter noise in blocks.
     * ~40 blocks gives island-sized organic variation within each slope band.
     */
    private static final double JITTER_NOISE_SCALE = 40.0;

    /**
     * Offset in blocks used when sampling neighbours for slope computation.
     * Matching Middle Earth's value of 3 gives a gradient averaged over a
     * wider base, smoothing out single-block terrain irregularities.
     */
    private static final int SLOPE_SAMPLE_OFFSET = 3;

    // ── Live state ─────────────────────────────────────────────────────────

    /** Biome ID → sorted (steepest-first) rule list. */
    private static volatile Map<String, List<SlopeRuleDef>> ruleMap = Map.of();

    /**
     * Noise used to jitter per-column thresholds so band edges are organic.
     * Seeded differently from terrain noise to avoid correlation.
     */
    private static volatile SimplexNoise jitterNoise = SimplexNoise.seeded(0L);

    private SlopeSurfaceResolver() {}

    // ── Seed initialisation ────────────────────────────────────────────────

    public static void initSeed(long worldSeed) {
        jitterNoise = SimplexNoise.seeded(worldSeed ^ 0xC0FFEE1AFADEL);
        LOGGER.debug("[GoT] SlopeSurfaceResolver seeded with world seed {}", worldSeed);
    }

    // ── Slope computation ──────────────────────────────────────────────────

    /**
     * Returns the terrain slope angle in <strong>degrees</strong> (0–90) at
     * ({@code worldX}, {@code worldZ}).
     *
     * <p>Uses a <em>central-difference Euclidean gradient</em>:
     * <ol>
     *   <li>Compute X and Z partial derivatives via symmetric central differences:
     *       {@code dX = (h(x+off) - h(x-off)) / (2*off)},
     *       {@code dZ = (h(z+off) - h(z-off)) / (2*off)}.</li>
     *   <li>Combine into the true 2D gradient magnitude:
     *       {@code |grad| = sqrt(dX^2 + dZ^2)}.</li>
     *   <li>Convert to degrees: {@code atan(|grad|) * (180/pi)}.</li>
     * </ol>
     *
     * <p>Central differences are second-order accurate and treat the terrain
     * as a proper 2D vector field, giving a steepness measure that is
     * rotationally symmetric and does not over-count diagonal slopes.
     *
     * @return slope angle in degrees (0 = flat, 90 = vertical)
     */
    public static float computeSlope(int worldX, int worldZ) {
        int off = SLOPE_SAMPLE_OFFSET;
        float span = 2.0f * off;

        float dX = (GotChunkGenerator.computeRawSurfaceY(worldX + off, worldZ)
                  - GotChunkGenerator.computeRawSurfaceY(worldX - off, worldZ)) / span;
        float dZ = (GotChunkGenerator.computeRawSurfaceY(worldX, worldZ + off)
                  - GotChunkGenerator.computeRawSurfaceY(worldX, worldZ - off)) / span;

        float gradientMagnitude = (float) Math.sqrt(dX * dX + dZ * dZ);
        return (float) Math.toDegrees(Math.atan(gradientMagnitude));
    }

    // ── Chunk post-processor ───────────────────────────────────────────────

    /**
     * Post-processes surface columns in {@code chunk}, replacing the top
     * {@code depth} solid blocks wherever slope angle exceeds a (noise-jittered)
     * threshold in degrees.
     *
     * <p>Call from {@link GotChunkGenerator#buildSurface} after vanilla surface
     * generation and road/wall passes.
     */
    public static void applySlopeBlocks(ChunkAccess chunk, WorldGenLevel region) {
        Map<String, List<SlopeRuleDef>> rules = ruleMap;
        if (rules.isEmpty()) return;

        SimplexNoise noise = jitterNoise; // capture volatile once
        int baseX = chunk.getPos().getMinBlockX();
        int baseZ = chunk.getPos().getMinBlockZ();

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {

                int wx = baseX + lx;
                int wz = baseZ + lz;

                int surfaceY = GotChunkGenerator.computeSurfaceY(wx, wz);
                if (surfaceY <= region.getMinY()) continue;

                String biomeId = region.getBiome(new BlockPos(wx, surfaceY, wz))
                        .unwrapKey()
                        .map(k -> k.location().toString())
                        .orElse("");

                List<SlopeRuleDef> biomeRules = rules.get(biomeId);
                if (biomeRules == null || biomeRules.isEmpty()) continue;

                float slopeDegrees = computeSlope(wx, wz);
                if (slopeDegrees < 0.5f) continue; // effectively flat, skip early

                // Find first matching rule (steepest-first), applying per-column
                // noise jitter (in degrees) to break up smooth band boundaries.
                SlopeRuleDef matched = null;
                for (SlopeRuleDef rule : biomeRules) {
                    float effectiveThreshold = rule.minSlope();
                    if (rule.jitter() > 0f) {
                        double n = noise.eval(
                                wx / JITTER_NOISE_SCALE,
                                wz / JITTER_NOISE_SCALE);
                        effectiveThreshold += (float) n * rule.jitter();
                    }
                    if (slopeDegrees >= effectiveThreshold) {
                        matched = rule;
                        break;
                    }
                }
                if (matched == null) continue;

                BlockState replacement = matched.block();
                int replaced = 0;
                int maxDepth = matched.depth();

                for (int y = surfaceY; y >= region.getMinY() && replaced < maxDepth; y--) {
                    BlockPos localPos = new BlockPos(lx, y, lz);
                    BlockState curr = chunk.getBlockState(localPos);
                    if (curr.isAir() || curr.liquid()) continue;
                    if (!curr.equals(replacement)) {
                        chunk.setBlockState(localPos, replacement, false);
                    }
                    replaced++;
                }
            }
        }
    }

    // ── Load / apply ───────────────────────────────────────────────────────

    public static Map<String, List<SlopeRuleDef>> load(ResourceManager manager) {
        Map<String, List<SlopeRuleDef>> result = new LinkedHashMap<>();

        Optional<Resource> res = manager.getResource(SLOPE_RULES_LOC);
        if (res.isEmpty()) {
            LOGGER.debug("[GoT] No slope_rules.json at {} — slope surface system disabled",
                    SLOPE_RULES_LOC);
            return result;
        }

        try (var reader = new InputStreamReader(res.get().open(), StandardCharsets.UTF_8)) {
            JsonObject root = new Gson().fromJson(reader, JsonObject.class);

            for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                String biomeId = entry.getKey();
                if (biomeId.startsWith("_") || !entry.getValue().isJsonArray()) continue;
                JsonArray arr = entry.getValue().getAsJsonArray();

                List<SlopeRuleDef> defs = new ArrayList<>(arr.size());
                for (JsonElement el : arr) {
                    JsonObject obj = el.getAsJsonObject();
                    if (!obj.has("block") && !obj.has("min_slope")) continue;

                    float  minSlope = obj.has("min_slope")
                            ? obj.get("min_slope").getAsFloat() : 35.0f;
                    String blockId  = obj.has("block")
                            ? obj.get("block").getAsString() : "minecraft:stone";
                    int    depth    = obj.has("depth")
                            ? Math.max(1, obj.get("depth").getAsInt()) : 3;
                    float  jitter   = obj.has("jitter")
                            ? obj.get("jitter").getAsFloat() : 3.0f;

                    Block block = BuiltInRegistries.BLOCK
                            .getOptional(ResourceLocation.parse(blockId))
                            .orElse(null);
                    if (block == null) {
                        LOGGER.warn("[GoT] slope_rules.json — unknown block '{}' for biome '{}', skipping",
                                blockId, biomeId);
                        continue;
                    }

                    defs.add(new SlopeRuleDef(minSlope, block.defaultBlockState(), depth, jitter));
                }

                if (defs.isEmpty()) continue;
                defs.sort(Comparator.comparingDouble(SlopeRuleDef::minSlope).reversed());
                result.put(biomeId, Collections.unmodifiableList(defs));
            }

            int total = result.values().stream().mapToInt(List::size).sum();
            LOGGER.info("[GoT] Loaded {} slope surface rules across {} biomes", total, result.size());

        } catch (Exception e) {
            LOGGER.error("[GoT] Failed to parse slope_rules.json", e);
        }

        return result;
    }

    public static void apply(Map<String, List<SlopeRuleDef>> map) {
        ruleMap = map;
    }

    public static Map<String, List<SlopeRuleDef>> getRuleMap() {
        return Collections.unmodifiableMap(ruleMap);
    }

    /** Debug string for the F3 overlay. */
    public static String debugInfo(String biomeId, int worldX, int worldZ) {
        float slopeDeg = computeSlope(worldX, worldZ);
        List<SlopeRuleDef> rules = ruleMap.get(biomeId);
        if (rules == null || rules.isEmpty())
            return String.format("slope=%.1f°  no rules for %s", slopeDeg, biomeId);
        for (SlopeRuleDef rule : rules) {
            if (slopeDeg >= rule.minSlope() - rule.jitter()) {
                return String.format("slope=%.1f°  -> %s (depth=%d, min=%.1f°±%.1f°)",
                        slopeDeg,
                        BuiltInRegistries.BLOCK.getKey(rule.block().getBlock()),
                        rule.depth(), rule.minSlope(), rule.jitter());
            }
        }
        return String.format("slope=%.1f°  (below all thresholds, lowest=%.1f°)",
                slopeDeg, rules.get(rules.size() - 1).minSlope());
    }
}
