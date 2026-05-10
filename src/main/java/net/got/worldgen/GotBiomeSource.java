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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Biome source for the GoT mod — synced with terrain's bicubic B-spline pipeline.
 *
 * <p>Uses the same coordinate conversion, domain warp, and 4×4 grid sampling
 * as {@link GotChunkGenerator#computeRawSurfaceY} so biomes stay perfectly
 * aligned with terrain height transitions.
 *
 * <h3>Water containment + creek assignment</h3>
 * <p>Two terrain-height checks run after the bicubic vote resolves a winner:
 * <ol>
 *   <li><b>Containment</b> — if a water biome won but terrain is dry
 *       ({@code surfaceY >= SEA_LEVEL}), the best-weighted land candidate
 *       is promoted instead.  Stops river/lake biomes bleeding onto dry shore.
 *   <li><b>Creek assignment</b> — if a land biome won but terrain is
 *       underwater ({@code surfaceY < SEA_LEVEL}) AND the central biomemap
 *       pixel is not already an explicit water pixel, the biome is replaced
 *       with {@code got:creek}.  This creates muddy shallow-water areas
 *       wherever terrain noise dips land below sea level near rivers and lakes.
 * </ol>
 */
public final class GotBiomeSource extends BiomeSource {

    /**
     * All explicitly-painted water biome IDs — must match biome_colors.json
     * entries whose base_height is below SEA_LEVEL.
     * {@code got:creek} is included so containment strips it from dry land.
     */
    private static final Set<String> WATER_BIOME_IDS = Set.of(
            "got:ocean",
            "got:deep_ocean",
            "got:river",
            "got:neck_river",
            "got:frozen_river",
            "got:lake",
            "got:frozen_lake",
            "got:creek"
    );

    /** Biome assigned when land terrain dips below sea level (not a painted water pixel). */
    private static final String CREEK_BIOME_ID = "got:creek";

    private static volatile int reloadGeneration = 0;
    public static void onMapReloaded() { reloadGeneration++; }

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

        // Biome-grid (quarter-resolution) → world block coordinates
        int worldX = x << 2;
        int worldZ = z << 2;

        // World block → biomemap pixel (same coordinate conversion as terrain)
        float cx = worldX / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;

        // Same domain warp as terrain
        float warpX = (float) DomainWarpNoise.fbm(worldX / 320.0, worldZ / 320.0, 3, 2.0, 0.5);
        float warpZ = (float) DomainWarpNoise.fbm(worldX / 320.0 + 3.7, worldZ / 320.0 + 8.1, 3, 2.0, 0.5);
        cx += warpX * 0.9f;
        cz += warpZ * 0.9f;

        int   ipx = (int) Math.floor(cx);
        int   ipz = (int) Math.floor(cz);
        float fx  = cx - ipx;
        float fz  = cz - ipz;

        // Sample 4×4 grid
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

        // Bicubic B-spline voting
        Map<String, Float> biomeVotes = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String id = biomeIds[i][j];
                if (id == null || id.isEmpty()) continue;
                float wx     = cubicBsplineWeight(i - 1, fx);
                float wz     = cubicBsplineWeight(j - 1, fz);
                float weight = wx * wz;
                if (isWater[i][j]) weight *= 1.15f; // thin rivers survive
                biomeVotes.merge(id, weight, Float::sum);
            }
        }

        // Overall winner
        String winner    = null;
        float  maxWeight = -1f;
        for (Map.Entry<String, Float> entry : biomeVotes.entrySet()) {
            if (entry.getValue() > maxWeight) {
                maxWeight = entry.getValue();
                winner    = entry.getKey();
            }
        }
        if (winner == null) return fallback;

        // ── Central pixel tells us what the biomemap intended here ────────────
        int clampedPx = Math.max(0, Math.min(BiomemapLoader.getWidth()  - 1, ipx));
        int clampedPz = Math.max(0, Math.min(BiomemapLoader.getHeight() - 1, ipz));
        GotBiomeTerrainParams.Params centralParams =
                GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(clampedPx, clampedPz));
        boolean centralIsWater = centralParams.isWater();

        // ── Terrain height (same function the chunk generator uses) ───────────
        float surfaceY = GotChunkGenerator.computeRawSurfaceY(worldX, worldZ);
        boolean terrainIsWet = surfaceY < GotChunkGenerator.SEA_LEVEL;

        if (terrainIsWet) {
            // CREEK ASSIGNMENT — land pixel where noise carved underwater.
            // Explicitly painted water pixels stay as their own biome (river, lake, etc.)
            if (!centralIsWater && !WATER_BIOME_IDS.contains(winner)) {
                winner = CREEK_BIOME_ID;
            }
        } else {
            // CONTAINMENT — voted water biome but terrain is actually dry.
            // Find the best non-water candidate from the votes instead.
            if (WATER_BIOME_IDS.contains(winner)) {
                String landWinner    = null;
                float  landMaxWeight = -1f;
                for (Map.Entry<String, Float> entry : biomeVotes.entrySet()) {
                    if (!WATER_BIOME_IDS.contains(entry.getKey())
                            && entry.getValue() > landMaxWeight) {
                        landMaxWeight = entry.getValue();
                        landWinner    = entry.getKey();
                    }
                }
                if (landWinner != null) winner = landWinner;
                // If fully surrounded by water pixels, keep the water winner.
            }
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