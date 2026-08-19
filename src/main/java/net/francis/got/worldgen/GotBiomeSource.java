package net.francis.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.francis.got.GotMod;
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

    private static final int SHORE_RADIUS = 8;

    private static final int SHORE_PROBE_COUNT = 4;

    private static final ThreadLocal<Map<String, Float>> VOTE_SCRATCH =
            ThreadLocal.withInitial(HashMap::new);

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

        // sample the 4x4 grid of biome-map pixels surrounding this position
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
                    var params = BiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
                    biomeIds[i + 1][j + 1] = params.biomeId();
                    isWater[i + 1][j + 1]  = params.isWater();
                }
            }
        }

        // each pixel votes for its biome, weighted by cubic B-spline smoothing (blends edges)
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

        // don't let a water biome win on dry land - fall back to the strongest land vote
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

        boolean ownTerrainSubmerged = surfaceY < GotChunkGenerator.SEA_LEVEL;
        boolean isolatedShorePocket = !ownTerrainSubmerged
                && isNearSubmergedGround(worldX, worldZ)
                && !isNearBigWaterBiome(worldX, worldZ);
        boolean nearWater = ownTerrainSubmerged || isolatedShorePocket;
        if (!WATER_BIOME_IDS.contains(winner) && nearWater && COLD_BIOME_IDS.contains(winner)) {
            winner = "got:frozen_lake";
        }

        if (!WATER_BIOME_IDS.contains(winner)) {
            String subbiome = SubbiomeResolver.resolve(winner, worldX, worldZ);
            if (subbiome != null) winner = subbiome;
        }

        Identifier loc = Identifier.tryParse(winner);
        if (loc == null) return fallback;
        Holder<Biome> h = locationToHolder.get(loc);
        return h != null ? h : fallback;
    }

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
                var params = BiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
                if (BIG_WATER_BIOME_IDS.contains(params.biomeId())) return true;
            }
        }
        return false;
    }

    // standard cubic B-spline basis function, used to smoothly blend the 4 nearest pixels
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