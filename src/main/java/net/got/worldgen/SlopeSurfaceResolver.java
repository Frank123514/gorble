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
 * every surface column, computes the <em>maximum integer block height difference</em>
 * to its four immediate neighbours, looks up the biome, and applies the first
 * matching rule.
 *
 * <p>Using integer heights (via {@link GotChunkGenerator#computeSurfaceY}) means
 * the slope value maps directly to what you see in-game: a slope of {@code 1.0}
 * means adjacent columns differ by 1 block, {@code 2.0} by 2 blocks, etc. This
 * spikes sharply at step risers, producing thin bands along contour lines rather
 * than smooth blobs.
 *
 * <p>Per-column noise jitter shifts the threshold slightly at each position so
 * band edges are organic and irregular rather than geometric.
 *
 * <h3>Data file</h3>
 * {@code data/got/worldgen/slope_rules/slope_rules.json}
 *
 * <ul>
 *   <li>{@code min_slope} — integer block height-diff threshold. {@code 1} = any
 *       step edge; {@code 2} = 2-block drop; {@code 3+} = cliff face.</li>
 *   <li>{@code block}     — namespaced block ID (default {@code minecraft:stone}).</li>
 *   <li>{@code depth}     — blocks below surface top to replace (default 3, min 1).</li>
 *   <li>{@code jitter}    — noise magnitude added to threshold per-column to break
 *       up blob edges (default 0.4, range 0–1 recommended).</li>
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
     * Returns the maximum integer block height-difference between the column at
     * ({@code worldX}, {@code worldZ}) and its four cardinal immediate neighbours.
     *
     * <p>Using integer heights means thresholds are intuitive:
     * <ul>
     *   <li>≥ 1 — any single-block step edge (common on moderate hills)</li>
     *   <li>≥ 2 — two-block drop (steep hillside)</li>
     *   <li>≥ 3 — cliff face (mountain escarpment)</li>
     *   <li>≥ 4 — near-vertical wall</li>
     * </ul>
     *
     * @return maximum integer height-diff to any immediate neighbour (≥ 0)
     */
    public static int computeSlope(int worldX, int worldZ) {
        int y00 = GotChunkGenerator.computeSurfaceY(worldX,     worldZ);
        int yPX = GotChunkGenerator.computeSurfaceY(worldX + 1, worldZ);
        int yNX = GotChunkGenerator.computeSurfaceY(worldX - 1, worldZ);
        int yPZ = GotChunkGenerator.computeSurfaceY(worldX,     worldZ + 1);
        int yNZ = GotChunkGenerator.computeSurfaceY(worldX,     worldZ - 1);

        int maxDiff = Math.abs(y00 - yPX);
        maxDiff = Math.max(maxDiff, Math.abs(y00 - yNX));
        maxDiff = Math.max(maxDiff, Math.abs(y00 - yPZ));
        maxDiff = Math.max(maxDiff, Math.abs(y00 - yNZ));
        return maxDiff;
    }

    // ── Chunk post-processor ───────────────────────────────────────────────

    /**
     * Post-processes surface columns in {@code chunk}, replacing the top
     * {@code depth} solid blocks wherever slope exceeds a (noise-jittered)
     * threshold.
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

                // Use the terrain formula directly — more reliable than the
                // heightmap during buildSurface since it doesn't depend on
                // when/whether the heightmap was last updated.
                int surfaceY = GotChunkGenerator.computeSurfaceY(wx, wz);
                if (surfaceY <= region.getMinY()) continue;

                // Biome at surface level (world coordinates)
                String biomeId = region.getBiome(new BlockPos(wx, surfaceY, wz))
                        .unwrapKey()
                        .map(k -> k.location().toString())
                        .orElse("");

                List<SlopeRuleDef> biomeRules = rules.get(biomeId);
                if (biomeRules == null || biomeRules.isEmpty()) continue;

                int slope = computeSlope(wx, wz);
                if (slope == 0) continue; // flat column, skip early

                // Find first matching rule (steepest-first), applying per-column
                // noise jitter to break up smooth blob boundaries.
                SlopeRuleDef matched = null;
                for (SlopeRuleDef rule : biomeRules) {
                    float effectiveThreshold = rule.minSlope();
                    if (rule.jitter() > 0f) {
                        // noise.eval in [-1,1]; scale by jitter magnitude
                        double n = noise.eval(
                                wx / JITTER_NOISE_SCALE,
                                wz / JITTER_NOISE_SCALE);
                        effectiveThreshold += (float) n * rule.jitter();
                    }
                    if (slope >= effectiveThreshold) {
                        matched = rule;
                        break;
                    }
                }
                if (matched == null) continue;

                // Replace top `depth` solid blocks (local coords for chunk writes,
                // world Y for iteration bounds)
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
                // Skip top-level comment/guide fields
                if (biomeId.startsWith("_") || !entry.getValue().isJsonArray()) continue;
                JsonArray arr = entry.getValue().getAsJsonArray();

                List<SlopeRuleDef> defs = new ArrayList<>(arr.size());
                for (JsonElement el : arr) {
                    JsonObject obj = el.getAsJsonObject();
                    // Skip comment-only objects
                    if (!obj.has("block") && !obj.has("min_slope")) continue;

                    float  minSlope = obj.has("min_slope")
                            ? obj.get("min_slope").getAsFloat() : 2.0f;
                    String blockId  = obj.has("block")
                            ? obj.get("block").getAsString() : "minecraft:stone";
                    int    depth    = obj.has("depth")
                            ? Math.max(1, obj.get("depth").getAsInt()) : 3;
                    float  jitter   = obj.has("jitter")
                            ? obj.get("jitter").getAsFloat() : 0.4f;

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
        int slope = computeSlope(worldX, worldZ);
        List<SlopeRuleDef> rules = ruleMap.get(biomeId);
        if (rules == null || rules.isEmpty())
            return String.format("slope=%d  no rules for %s", slope, biomeId);
        for (SlopeRuleDef rule : rules) {
            if (slope >= rule.minSlope() - rule.jitter()) {
                return String.format("slope=%d  -> %s (depth=%d, min=%.1f±%.1f)",
                        slope,
                        BuiltInRegistries.BLOCK.getKey(rule.block().getBlock()),
                        rule.depth(), rule.minSlope(), rule.jitter());
            }
        }
        return String.format("slope=%d  (below all thresholds, lowest=%.1f)",
                slope, rules.get(rules.size() - 1).minSlope());
    }
}
