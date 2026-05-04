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
 * Biome source kept in exact sync with {@link GotChunkGenerator}'s SDF pipeline.
 *
 * Pipeline:
 *   1. Domain warp  — identical simplex offsets to computeSurfaceY
 *   2. riverSdf     — same call the terrain makes; inside channel → river biome,
 *                     inside bank zone → centre-pixel class decides
 *   3. Gaussian vote — outside any river, weighted color vote picks land/ocean biome
 */
public final class GotBiomeSource extends BiomeSource {

    private static volatile int reloadGeneration = 0;
    public static void onMapReloaded() { reloadGeneration++; }

    // Must stay identical to GotChunkGenerator
    private static final int   SAMPLE_RADIUS    = 6;
    private static final float GAUSSIAN_SIGMA   = 0.8f;
    private static final float GAUSSIAN_INV_2S2 = 1f / (2f * GAUSSIAN_SIGMA * GAUSSIAN_SIGMA);
    private static final float RIVER_HALF_WIDTH = 28f;
    private static final float RIVER_BANK_WIDTH = 22f;
    private static final int   RIVER_SEARCH_RADIUS = 3;

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

        float rawCx = worldX / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float rawCz = worldZ / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;

        // 1. Domain warp — same call as computeSurfaceY
        float[] wc = GotChunkGenerator.warpCoord(rawCx, rawCz);
        float warpedCx = wc[0];
        float warpedCz = wc[1];
        int icx = (int) Math.floor(warpedCx);
        int icz = (int) Math.floor(warpedCz);

        // 2. SDF river check — same function as terrain pipeline
        float riverDist = GotChunkGenerator.riverSdf(warpedCx, warpedCz);

        if (riverDist < RIVER_HALF_WIDTH) {
            // Inside channel: use nearest river pixel's biome
            return nearestRiverBiome(icx, icz, warpedCx, warpedCz);

        } else if (riverDist < RIVER_HALF_WIDTH + RIVER_BANK_WIDTH) {
            // Bank zone: let centre pixel class decide to avoid biome flicker
            if (GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(icx, icz)).isRiver()) {
                return nearestRiverBiome(icx, icz, warpedCx, warpedCz);
            }
        }

        // 3. Gaussian vote for land / ocean / lake
        HashMap<Integer, Float> votes = new HashMap<>();
        for (int dx = -SAMPLE_RADIUS; dx <= SAMPLE_RADIUS; dx++) {
            for (int dz = -SAMPLE_RADIUS; dz <= SAMPLE_RADIUS; dz++) {
                int color = BiomemapLoader.getRawPixel(icx + dx, icz + dz) & 0xFFFFFF;
                float ddx = (icx + dx) - warpedCx;
                float ddz = (icz + dz) - warpedCz;
                float w   = (float) Math.exp(-(ddx*ddx + ddz*ddz) * GAUSSIAN_INV_2S2);
                votes.merge(color, w, Float::sum);
            }
        }

        int bestColor = -1; float bestW = -1f;
        for (var e : votes.entrySet()) {
            if (e.getValue() > bestW) { bestW = e.getValue(); bestColor = e.getKey(); }
        }

        ResourceLocation loc = colorToBiome(bestColor);
        if (loc != null) {
            Holder<Biome> h = locationToHolder.get(loc);
            if (h != null) return h;
        }
        return fallback;
    }

    private Holder<Biome> nearestRiverBiome(int icx, int icz, float warpedCx, float warpedCz) {
        float nearestSq = Float.MAX_VALUE;
        int nearestColor = -1;
        for (int dx = -RIVER_SEARCH_RADIUS; dx <= RIVER_SEARCH_RADIUS; dx++) {
            for (int dz = -RIVER_SEARCH_RADIUS; dz <= RIVER_SEARCH_RADIUS; dz++) {
                int color = BiomemapLoader.getRawPixel(icx + dx, icz + dz);
                if (!GotBiomeTerrainParams.forColor(color).isRiver()) continue;
                float ddx = (icx + dx + 0.5f) - warpedCx;
                float ddz = (icz + dz + 0.5f) - warpedCz;
                float sq  = ddx*ddx + ddz*ddz;
                if (sq < nearestSq) { nearestSq = sq; nearestColor = color; }
            }
        }
        if (nearestColor == -1) return fallback;
        ResourceLocation loc = colorToBiome(nearestColor);
        if (loc == null) return fallback;
        Holder<Biome> h = locationToHolder.get(loc);
        return h != null ? h : fallback;
    }

    private static volatile Map<Integer, ResourceLocation> colorToBiomeMap = Map.of();
    private static volatile int colorMapGen = -1;

    private static ResourceLocation colorToBiome(int rgb) {
        refreshColorMap();
        Map<Integer, ResourceLocation> map = colorToBiomeMap;
        ResourceLocation direct = map.get(rgb & 0xFFFFFF);
        if (direct != null) return direct;
        int bestDist = Integer.MAX_VALUE; ResourceLocation best = null;
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        for (var e : map.entrySet()) {
            int k = e.getKey();
            int dr = r-((k>>16)&0xFF), dg = g-((k>>8)&0xFF), db = b-(k&0xFF);
            int d = dr*dr + dg*dg + db*db;
            if (d < bestDist) { bestDist = d; best = e.getValue(); }
        }
        return best;
    }

    private static void refreshColorMap() {
        int gen = reloadGeneration;
        if (colorMapGen == gen) return;
        Map<Integer, ResourceLocation> fresh = new LinkedHashMap<>();
        fresh.put(0x949038, GotMod.id("north"));
        fresh.put(0xADA942, GotMod.id("barrowlands"));
        fresh.put(0x92B0AC, GotMod.id("stony_shore"));
        fresh.put(0x808F81, GotMod.id("north_hills"));
        fresh.put(0x2F4A33, GotMod.id("neck"));
        fresh.put(0x02450D, GotMod.id("ironwood"));
        fresh.put(0x047D17, GotMod.id("wolfswood"));
        fresh.put(0x00229D, GotMod.id("ocean"));
        fresh.put(0x110751, GotMod.id("deep_ocean"));
        fresh.put(0x2D6796, GotMod.id("river"));
        fresh.put(0x35A180, GotMod.id("neck_river"));
        fresh.put(0x4B91E6, GotMod.id("frozen_river"));
        fresh.put(0xBBCCCD, GotMod.id("frostfangs"));
        fresh.put(0xFFFFFF, GotMod.id("always_winter"));
        fresh.put(0xA5B7B9, GotMod.id("north_mountains"));
        fresh.put(0x537053, GotMod.id("haunted_forest"));
        fresh.put(0x6B5C3E, GotMod.id("iron_hills"));
        fresh.put(0xC8B87A, GotMod.id("sheepshead_hills"));
        fresh.put(0x1A5C8F, GotMod.id("lake"));
        fresh.put(0x4B91C0, GotMod.id("frozen_lake"));
        colorToBiomeMap = Collections.unmodifiableMap(fresh);
        colorMapGen = gen;
    }
}