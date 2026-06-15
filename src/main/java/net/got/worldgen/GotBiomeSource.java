package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.GotMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;
import org.joml.SimplexNoise;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GotBiomeSource extends BiomeSource {

    private static final Set<String> HOT_BIOME_IDS = Set.of(
            "got:dorne",
            "got:dorne_desert",
            "got:lower_reach"
    );

    private static final Set<String> WATER_BIOME_IDS = Set.of(
            "got:ocean",
            "got:deep_ocean",
            "got:river",
            "got:neck_river",
            "got:frozen_river",
            "got:lake",
            "got:frozen_lake",
            "got:creek",
            "got:oasis"
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
    private final Map<ResourceLocation, Holder<Biome>> locationToHolder;
    private final Holder<Biome>                        fallback;

    public GotBiomeSource(List<Holder<Biome>> biomes) {
        this.biomes = List.copyOf(biomes);
        this.locationToHolder = new HashMap<>(biomes.size() * 2);
        for (Holder<Biome> h : biomes)
            h.unwrapKey().ifPresent(key -> locationToHolder.put(key.location(), h));

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

        float warpX = (float) SimplexNoise.noise((float) (worldX / 320.0), (float) (worldZ / 320.0));
        float warpZ = (float) SimplexNoise.noise((float) (worldX / 320.0 + 3.7), (float) (worldZ / 320.0 + 8.1));
        cx += warpX * 0.9f;
        cz += warpZ * 0.9f;

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

        Map<String, Float> biomeVotes = new HashMap<>();
        // Also accumulate total water influence for CreekResolver
        float waterInfluence = 0f;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String id = biomeIds[i][j];
                if (id == null || id.isEmpty()) continue;
                float wx     = cubicBsplineWeight(i - 1, fx);
                float wz     = cubicBsplineWeight(j - 1, fz);
                float weight = wx * wz;
                if (isWater[i][j]) {
                    weight *= 1.15f;
                    waterInfluence += weight;
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

        // CREEK SWAP — fully submerged land cells always become creek/oasis.
        // The fringe band (sea level to sea level+1) is routed through CreekResolver
        // which uses domain-warped ridged noise to produce organic branching fingers
        // rather than a uniform collar.
        if (!WATER_BIOME_IDS.contains(winner)) {
            if (surfaceY < GotChunkGenerator.SEA_LEVEL) {
                // Genuinely underwater — always swap.
                winner = HOT_BIOME_IDS.contains(winner) ? "got:oasis" : "got:creek";
            } else if (surfaceY < GotChunkGenerator.SEA_LEVEL + 1
                    && CreekResolver.isCreek(winner, waterInfluence, worldX, worldZ)) {
                // Fringe band — only swap where CreekResolver's ridged noise says so.
                winner = HOT_BIOME_IDS.contains(winner) ? "got:oasis" : "got:creek";
            }
        }

        // SUB-BIOME CHECK
        if (!WATER_BIOME_IDS.contains(winner)) {
            String subbiome = SubbiomeResolver.resolve(winner, worldX, worldZ);
            if (subbiome != null) winner = subbiome;
        }

        ResourceLocation loc = ResourceLocation.tryParse(winner);
        if (loc == null) return fallback;
        Holder<Biome> h = locationToHolder.get(loc);
        return h != null ? h : fallback;
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