package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.worldgen.layer.GotBiomeGenSettings;
import net.got.worldgen.layer.GotBiomeRegistry;
import net.got.worldgen.layer.GotWorldLayers;
import net.got.worldgen.layer.LayerArea;
import net.got.worldgen.layer.GotMapLayer;
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
 * Layer-based biome source — LOTR-style pipeline, pure-Java implementation.
 * Uses the same codec pattern as the original GotBiomeSource.
 */
public final class GotBiomeSource extends BiomeSource {

    // Seed used to build the layer stack. Stored so the codec can round-trip it.
    // In normal play the level seed is injected via createWithSeed() below.
    private static volatile long activeSeed = 0L;

    /** Called by GotChunkGenerator when the level seed is known. */
    public static void setSeed(long seed) { activeSeed = seed; }

    /** Latest biomemap loaded by MapReloadListener; null until first resource load. */
    private static volatile GotMapLayer.PreparedMap latestMap = null;

    /** Incremented each time the map is reloaded, so getLayer() knows to rebuild. */
    private static volatile int mapGeneration = 0;

    /**
     * Called by {@link net.got.worldgen.MapReloadListener#apply} after
     * {@code biomemap.png} has been decoded off-thread. Invalidates the cached
     * layer so it gets rebuilt with the new map data on next sample.
     */
    public static void setMapLayer(GotMapLayer.PreparedMap prepared) {
        latestMap = prepared;
        mapGeneration++;
    }

    // ── Codec — mirrors the original GotBiomeSource pattern exactly ───────

    public static final MapCodec<GotBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RegistryCodecs.homogeneousList(Registries.BIOME)
                            .fieldOf("biomes")
                            .forGetter(s -> HolderSet.direct(s.biomes))
            ).apply(instance, instance.stable(
                    holderSet -> new GotBiomeSource(holderSet.stream().collect(Collectors.toList()))
            ))
    );

    // ── Fields ────────────────────────────────────────────────────────────

    private final List<Holder<Biome>> biomes;
    private final Map<ResourceLocation, Holder<Biome>> locationToHolder;
    private final Holder<Biome> fallback;
    private volatile LayerArea genLayer;
    private volatile long builtForSeed = Long.MIN_VALUE;

    public GotBiomeSource(List<Holder<Biome>> biomes) {
        this.biomes = List.copyOf(biomes);
        this.locationToHolder = new HashMap<>(biomes.size() * 2);
        for (Holder<Biome> h : biomes) {
            h.unwrapKey().ifPresent(key -> locationToHolder.put(key.location(), h));
        }
        Holder<Biome> fb = locationToHolder.get(net.got.GotMod.id("north"));
        if (fb == null) fb = locationToHolder.get(net.got.GotMod.id("ocean"));
        if (fb == null && !biomes.isEmpty()) fb = biomes.get(0);
        this.fallback = Objects.requireNonNull(fb, "GotBiomeSource: biome list is empty!");
    }

    // ── BiomeSource ───────────────────────────────────────────────────────

    @Override
    protected @NotNull MapCodec<? extends BiomeSource> codec() { return CODEC; }

    @Override
    protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() { return biomes.stream(); }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z,
                                       Climate.@NotNull Sampler sampler) {
        int id = getLayer().get(x, z);
        ResourceLocation loc = GotBiomeRegistry.locationFor(id);
        Holder<Biome> h = locationToHolder.get(loc);
        return h != null ? h : fallback;
    }

    // ── Layer access ──────────────────────────────────────────────────────

    private volatile int builtForMapGen = -1;

    private LayerArea getLayer() {
        long seed   = activeSeed;
        int  mapGen = mapGeneration;
        if (genLayer == null || builtForSeed != seed || builtForMapGen != mapGen) {
            synchronized (this) {
                if (genLayer == null || builtForSeed != seed || builtForMapGen != mapGen) {
                    LayerArea classic   = GotWorldLayers.create(seed, new GotBiomeGenSettings());
                    GotMapLayer.PreparedMap map = latestMap;
                    genLayer         = (map != null)
                            ? GotMapLayer.createLayer(map, classic)
                            : classic;
                    builtForSeed     = seed;
                    builtForMapGen   = mapGen;
                }
            }
        }
        return genLayer;
    }

    /** Exposes the layer for chunk gen biome-ID sampling. */
    int sampleId(int noiseX, int noiseZ) {
        return getLayer().get(noiseX, noiseZ);
    }

    /** Pre-warms the layer cache for the given noise-coord rectangle. */
    void prewarm(int minX, int minZ, int maxX, int maxZ) {
        getLayer().prewarm(minX, minZ, maxX, maxZ);
    }
}