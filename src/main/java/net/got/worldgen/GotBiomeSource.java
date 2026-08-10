package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.GotMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GotBiomeSource extends BiomeSource {

    /**
     * How far (in blocks) the frozen_lake swap reaches into the
     * surrounding land past the actual waterline. This is a physical radius —
     * a column only gets pulled in if real submerged ground is within this
     * distance, not just because it happens to sit near sea-level elevation
     * somewhere far from any water.
     */
    private static final int SHORE_RADIUS = 8;

    /** Number of ring samples used to probe for nearby submerged ground. */
    private static final int SHORE_PROBE_COUNT = 4;

    /**
     * Per-thread scratch map for the biome-voting step in {@link #getNoiseBiome}.
     * Chunk generation runs multiple worker threads concurrently, so this can't
     * be a single shared field — each thread gets its own map and reuses it
     * across calls instead of allocating a fresh HashMap every time. The map is
     * cleared at the top of each call before use.
     */
    private static final ThreadLocal<Map<String, Float>> VOTE_SCRATCH =
            ThreadLocal.withInitial(HashMap::new);

    /**
     * Cold biomes where an open water pocket gets frozen_lake instead of
     * staying as plain land. North, North Hills, and Barrowlands are chilly
     * but not frozen-tundra cold, so they're left off this list and get no
     * swap at all.
     */
    private static final Set<String> COLD_BIOME_IDS = Set.of(
            "got:always_winter",
            "got:frostfangs",
            "got:north_mountains",
            "got:haunted_forest",
            "got:the_wall"
    );

    private static final Set<String> WATER_BIOME_IDS = Set.of(
            "got:ocean",
            "got:deep_ocean",
            "got:river",
            "got:neck_river",
            "got:frozen_river",
            "got:lake",
            "got:frozen_lake"
    );

    /**
     * "Real" water bodies — as opposed to the small frozen_lake
     * pockets the shore-radius swap itself produces. Used to tell an isolated
     * puddle sitting deep inside a land biome apart from a frozen_lake
     * patch that's actually just the fringe of a river/lake/ocean.
     */
    private static final Set<String> BIG_WATER_BIOME_IDS = Set.of(
            "got:ocean",
            "got:deep_ocean",
            "got:river",
            "got:neck_river",
            "got:frozen_river",
            "got:lake"
    );

    public static final MapCodec<GotBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RegistryCodecs.homogeneousList(Registries.BIOME)
                            .fieldOf("biomes")
                            .forGetter(s -> HolderSet.direct(s.biomes))
            ).apply(instance, instance.stable(
                    holderSet -> new GotBiomeSource(holderSet.stream().collect(Collectors.toList()))
            ))
    );

    private final List<Holder<Biome>>                  biomes;
    private final Map<Identifier, Holder<Biome>> locationToHolder;
    private final Holder<Biome>                        fallback;

    public GotBiomeSource(List<Holder<Biome>> biomes) {
        this.biomes = List.copyOf(biomes);
        this.locationToHolder = new HashMap<>(biomes.size() * 2);
        for (Holder<Biome> h : biomes)
            h.unwrapKey().ifPresent(key -> locationToHolder.put(key.identifier(), h));

        Holder<Biome> fb = locationToHolder.get(GotMod.id("north"));
        if (fb == null) fb = locationToHolder.get(GotMod.id("ocean"));
        if (fb == null && !biomes.isEmpty()) fb = biomes.get(0);
        this.fallback = Objects.requireNonNull(fb, "GotBiomeSource: biome list is empty!");
    }

    @Override protected @NotNull MapCodec<? extends BiomeSource> codec() { return CODEC; }
    @Override protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() { return biomes.stream(); }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        if (!BiomemapLoader.isLoaded()) return fallback;

        int worldX = x << 2;
        int worldZ = z << 2;

        float cx = worldX / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;

        int   ipx = (int) Math.floor(cx);
        int   ipz = (int) Math.floor(cz);
        float fx  = cx - ipx;
        float fz  = cz - ipz;

        String[][]  biomeIds = new String[4][4];
        boolean[][] isWater  = new boolean[4][4];

        for (int i = -1; i <= 2; i++) {
            for (int j = -1; j <= 2; j++) {
                int px = ipx + i;
                int pz = ipz + j;
                if (px < 0 || pz < 0 || px >= BiomemapLoader.getWidth() || pz >= BiomemapLoader.getHeight()) {
                    biomeIds[i + 1][j + 1] = null;
                    isWater[i + 1][j + 1]  = false;
                } else {
                    var params = GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
                    biomeIds[i + 1][j + 1] = params.biomeId();
                    isWater[i + 1][j + 1]  = params.isWater();
                }
            }
        }

        Map<String, Float> biomeVotes = VOTE_SCRATCH.get();
        biomeVotes.clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String id = biomeIds[i][j];
                if (id == null || id.isEmpty()) continue;
                float wx     = cubicBsplineWeight(i - 1, fx);
                float wz     = cubicBsplineWeight(j - 1, fz);
                float weight = wx * wz;
                if (isWater[i][j]) {
                    weight *= 1.15f;
                }
                biomeVotes.merge(id, weight, Float::sum);
            }
        }

        String winner    = null;
        float  maxWeight = -1f;
        for (Map.Entry<String, Float> entry : biomeVotes.entrySet()) {
            if (entry.getValue() > maxWeight) {
                maxWeight = entry.getValue();
                winner    = entry.getKey();
            }
        }
        if (winner == null) return fallback;

        // CONTAINMENT — water won but terrain is dry → promote to best land biome
        float surfaceY = GotChunkGenerator.computeRawSurfaceY(worldX, worldZ);
        if (surfaceY >= GotChunkGenerator.SEA_LEVEL && WATER_BIOME_IDS.contains(winner)) {
            String landCandidate  = null;
            float  landCandidateW = -1f;
            for (Map.Entry<String, Float> entry : biomeVotes.entrySet()) {
                if (!WATER_BIOME_IDS.contains(entry.getKey()) && entry.getValue() > landCandidateW) {
                    landCandidateW = entry.getValue();
                    landCandidate  = entry.getKey();
                }
            }
            if (landCandidate != null) winner = landCandidate;
        }

        // FROZEN_LAKE SWAP — a dry land column becomes a frozen-lake
        // pocket if its own terrain dips below sea level, OR if
        // real submerged ground is within SHORE_RADIUS blocks of it AND that
        // submerged ground is an isolated pocket fully surrounded by land.
        // The radius check pushes the biome out into the banks physically
        // surrounding an isolated low spot without grabbing unrelated land
        // elsewhere that just happens to sit near sea-level elevation. It
        // deliberately does NOT fire when the nearby submerged ground is
        // actually part of a real river/lake/ocean — that shoreline is
        // already handled by the water biome's own shape, so stacking a
        // frozen_lake fringe around it as well just looks wrong.
        // Same trick Middle Earth uses for its ponds: no separate noise
        // system, the low spot IS the water. Cold biomes get a frozen lake
        // pocket; everything else keeps its own land biome — no swap.
        boolean ownTerrainSubmerged = surfaceY < GotChunkGenerator.SEA_LEVEL;
        boolean isolatedShorePocket = !ownTerrainSubmerged
                && isNearSubmergedGround(worldX, worldZ)
                && !isNearBigWaterBiome(worldX, worldZ);
        boolean nearWater = ownTerrainSubmerged || isolatedShorePocket;
        if (!WATER_BIOME_IDS.contains(winner) && nearWater && COLD_BIOME_IDS.contains(winner)) {
            winner = "got:frozen_lake";
        }

        // SUB-BIOME CHECK
        if (!WATER_BIOME_IDS.contains(winner)) {
            String subbiome = SubbiomeResolver.resolve(winner, worldX, worldZ);
            if (subbiome != null) winner = subbiome;
        }

        Identifier loc = Identifier.tryParse(winner);
        if (loc == null) return fallback;
        Holder<Biome> h = locationToHolder.get(loc);
        return h != null ? h : fallback;
    }

    /**
     * Samples a ring of points {@link #SHORE_RADIUS} blocks out from
     * (worldX, worldZ) and returns true if any of them are actually
     * below sea level. Used to pull dry land into the frozen_lake swap
     * only when it's physically close to real water, not just near sea-level
     * elevation somewhere unrelated.
     */
    private static boolean isNearSubmergedGround(int worldX, int worldZ) {
        for (int i = 0; i < SHORE_PROBE_COUNT; i++) {
            double angle = (2 * Math.PI * i) / SHORE_PROBE_COUNT;
            int px = worldX + Math.round((float) (Math.cos(angle) * SHORE_RADIUS));
            int pz = worldZ + Math.round((float) (Math.sin(angle) * SHORE_RADIUS));
            if (GotChunkGenerator.computeRawSurfaceY(px, pz) < GotChunkGenerator.SEA_LEVEL) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the biomemap around (worldX, worldZ) for any real water biome
     * ({@link #BIG_WATER_BIOME_IDS}) — ocean, river, lake, etc. — as opposed
     * to a frozen_lake pocket. Used to tell apart an isolated
     * low spot fully surrounded by land (should get the frozen_lake
     * shore swap) from one that's actually just the muddy edge of a real
     * water body (shouldn't get an extra fringe stacked on top of the water
     * biome's own shoreline).
     *
     * <p>{@code SHORE_RADIUS} (8 blocks) is well under one biomemap
     * pixel ({@code MAP_SCALE} = 46 blocks), so a 3x3-pixel neighbourhood
     * around the column's own biomemap pixel comfortably covers the same
     * physical area the shore-radius probe reaches into.
     */
    private static boolean isNearBigWaterBiome(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return false;

        float cx = worldX / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;
        int centerPx = Math.round(cx);
        int centerPz = Math.round(cz);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                int px = centerPx + dx;
                int pz = centerPz + dz;
                if (px < 0 || pz < 0 || px >= BiomemapLoader.getWidth() || pz >= BiomemapLoader.getHeight())
                    continue;
                var params = GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
                if (BIG_WATER_BIOME_IDS.contains(params.biomeId())) return true;
            }
        }
        return false;
    }

    private static float cubicBsplineWeight(int i, float t) {
        float t2 = t * t;
        float t3 = t2 * t;
        switch (i) {
            case -1: return (1f - 3f*t + 3f*t2 - t3) / 6f;
            case  0: return (4f - 6f*t2 + 3f*t3) / 6f;
            case  1: return (1f + 3f*t + 3f*t2 - 3f*t3) / 6f;
            case  2: return t3 / 6f;
            default: return 0f;
        }
    }
}