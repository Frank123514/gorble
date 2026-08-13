package net.got.worldgen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
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

public final class SlopeSurfaceResolver {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Identifier SLOPE_RULES_LOC =
            Identifier.fromNamespaceAndPath("got", "worldgen/slope_rules/slope_rules.json");

    private static final double JITTER_NOISE_SCALE = 40.0;

    private static final int SLOPE_SAMPLE_OFFSET = 3;

    private static volatile Map<String, List<SlopeRuleDef>> ruleMap = Map.of();

    private static volatile SimplexNoise jitterNoise = SimplexNoise.seeded(0L);

    private SlopeSurfaceResolver() {}

    public static void initSeed(long worldSeed) {
        jitterNoise = SimplexNoise.seeded(worldSeed ^ 0xC0FFEE1AFADEL);
        LOGGER.debug("[GoT] SlopeSurfaceResolver seeded with world seed {}", worldSeed);
    }

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

    public static void applySlopeBlocks(ChunkAccess chunk, WorldGenLevel region) {
        Map<String, List<SlopeRuleDef>> rules = ruleMap;
        if (rules.isEmpty()) return;

        SimplexNoise noise = jitterNoise;
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
                        .map(k -> k.identifier().toString())
                        .orElse("");

                List<SlopeRuleDef> biomeRules = rules.get(biomeId);
                if (biomeRules == null || biomeRules.isEmpty()) continue;

                float slopeDegrees = computeSlope(wx, wz);
                if (slopeDegrees < 0.5f) continue;

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
                        chunk.setBlockState(localPos, replacement, 3);
                    }
                    replaced++;
                }
            }
        }
    }

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
                            .getOptional(Identifier.parse(blockId))
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