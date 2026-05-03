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
 * Biome source that reads the static pixel store in {@link BiomemapLoader}.
 * Pure pixel-based: each block gets the biome of the center pixel.
 * No water/land blending. Rivers stay exactly where the biomemap says.
 */
public final class GotBiomeSource extends BiomeSource {

    // ── Reload signal from MapReloadListener ───────────────────────────────

    private static volatile int reloadGeneration = 0;

    /** Called by MapReloadListener after both stores have been updated. */
    public static void onMapReloaded() { reloadGeneration++; }

    // ── Gaussian parameters (for noise scale only, NOT biome blending) ──

    private static final int   SAMPLE_RADIUS = 6;
    private static final float GAUSSIAN_SIGMA   = 0.8f;
    private static final float GAUSSIAN_INV_2S2 = 1f / (2f * GAUSSIAN_SIGMA * GAUSSIAN_SIGMA);

    // ── Codec ──────────────────────────────────────────────────────────────

    public static final MapCodec<GotBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RegistryCodecs.homogeneousList(Registries.BIOME)
                            .fieldOf("biomes")
                            .forGetter(s -> HolderSet.direct(s.biomes))
            ).apply(instance, instance.stable(
                    holderSet -> new GotBiomeSource(holderSet.stream().collect(Collectors.toList()))
            ))
    );

    // ── Fields ─────────────────────────────────────────────────────────────

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

    // ── BiomeSource ────────────────────────────────────────────────────────

    @Override protected @NotNull MapCodec<? extends BiomeSource> codec() { return CODEC; }
    @Override protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() { return biomes.stream(); }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        if (!BiomemapLoader.isLoaded()) return fallback;

        // Convert noise coords (1 unit = 4 blocks) to world-block coords, then pixel-space.
        int worldX = x * 4;
        int worldZ = z * 4;
        float rawCx = worldX / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float rawCz = worldZ / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;

        int icx = (int) Math.floor(rawCx);
        int icz = (int) Math.floor(rawCz);

        // ── PURE PIXEL: center pixel decides biome ────────────────────────
        int centerColor = BiomemapLoader.getRawPixel(icx, icz);
        GotBiomeTerrainParams.Params centerParams = GotBiomeTerrainParams.forColor(centerColor);

        // For the transition band, we do a simple Gaussian vote to pick the
        // dominant nearby biome, but we do NOT blend land into water.
        float waterWeight = 0f;
        float totalWeight = 0f;
        java.util.HashMap<Integer, Float> landVotes  = new java.util.HashMap<>();
        java.util.HashMap<Integer, Float> waterVotes = new java.util.HashMap<>();

        for (int dx = -SAMPLE_RADIUS; dx <= SAMPLE_RADIUS; dx++) {
            for (int dz = -SAMPLE_RADIUS; dz <= SAMPLE_RADIUS; dz++) {
                int color = BiomemapLoader.getRawPixel(icx + dx, icz + dz);
                GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(color);

                float ddx   = (icx + dx) - rawCx;
                float ddz   = (icz + dz) - rawCz;
                float dist2 = ddx * ddx + ddz * ddz;
                float w = (float) Math.exp(-dist2 * GAUSSIAN_INV_2S2);

                totalWeight += w;
                if (p.isWater()) {
                    waterWeight += w;
                    waterVotes.merge(color, w, Float::sum);
                } else {
                    landVotes.merge(color, w, Float::sum);
                }
            }
        }

        // ── CENTER PIXEL DECIDES THE CLASS ────────────────────────────────
        // If center is land, we stay land unless we're deep in water.
        // If center is water, we stay water unless we're deep on land.
        boolean pickWaterBiome = centerParams.isWater();

        java.util.HashMap<Integer, Float> votes = pickWaterBiome ? waterVotes : landVotes;

        int bestColor = -1;
        float bestWeight = -1f;
        for (var e : votes.entrySet()) {
            if (e.getValue() > bestWeight) {
                bestWeight = e.getValue();
                bestColor = e.getKey();
            }
        }

        // Fallback: if no votes in the winning class, use centre pixel
        if (bestColor == -1) {
            bestColor = centerColor;
        }

        ResourceLocation loc = colorToBiome(bestColor);
        if (loc != null) {
            Holder<Biome> h = locationToHolder.get(loc);
            if (h != null) return h;
        }
        return fallback;
    }

    // ── Color → biome name ─────────────────────────────────────────────────

    private static volatile Map<Integer, ResourceLocation> colorToBiomeMap = Map.of();
    private static volatile int colorMapGen = -1;

    private static ResourceLocation colorToBiome(int rgb) {
        refreshColorMap();
        Map<Integer, ResourceLocation> map = colorToBiomeMap;
        ResourceLocation direct = map.get(rgb & 0xFFFFFF);
        if (direct != null) return direct;

        int bestDist = Integer.MAX_VALUE;
        ResourceLocation best = null;
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        for (var e : map.entrySet()) {
            int k  = e.getKey();
            int dr = r - ((k >> 16) & 0xFF);
            int dg = g - ((k >>  8) & 0xFF);
            int db = b - ( k        & 0xFF);
            int d  = dr*dr + dg*dg + db*db;
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