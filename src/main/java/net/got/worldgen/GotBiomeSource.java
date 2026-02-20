package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Biome source that reads colors from biome_map.png
 * Coordinates are aligned with heightmap (both use same transform)
 */
public final class GotBiomeSource extends BiomeSource {

    /* ================= CODEC ================= */

    public static final MapCodec<GotBiomeSource> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            RegistryOps.retrieveGetter(Registries.BIOME)
                    ).apply(instance, GotBiomeSource::new)
            );

    /* =============== FALLBACK =============== */

    private static final ResourceKey<Biome> FALLBACK =
            ResourceKey.create(
                    Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath("minecraft", "plains")
            );

    private final HolderGetter<Biome> biomeLookup;
    private final Set<Holder<Biome>> possibleBiomes = new HashSet<>();

    public GotBiomeSource(HolderGetter<Biome> biomeLookup) {
        this.biomeLookup = biomeLookup;

        // Declare all biomes from biome map
        for (ResourceKey<Biome> key : BiomeMapLoader.getAllBiomes()) {
            biomeLookup.get(key).ifPresent(possibleBiomes::add);
        }

        // Always include fallback
        possibleBiomes.add(biomeLookup.getOrThrow(FALLBACK));

        System.out.println("[GoT] BiomeSource initialized with " + possibleBiomes.size() + " possible biomes");
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    /**
     * CRITICAL: must NOT be empty
     */
    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return possibleBiomes.stream();
    }

    /**
     * Get biome at chunk coordinates
     * Note: x and z are in CHUNK coordinates (divide by 4 for block coords)
     */
    @Override
    public Holder<Biome> getNoiseBiome(
            int x,
            int y,
            int z,
            Climate.Sampler sampler
    ) {
        // Convert from chunk coords to block coords
        // In Minecraft's biome system:
        // - Biomes are sampled at 4x4x4 resolution
        // - x/z in getNoiseBiome are already divided by 4
        // So we multiply by 4 to get block coordinates
        int blockX = x * 4;
        int blockZ = z * 4;

        ResourceKey<Biome> key = BiomeMapLoader.getBiome(blockX, blockZ);

        if (key == null) {
            // Outside map or unknown color - use fallback
            return biomeLookup.getOrThrow(FALLBACK);
        }

        // Try to get the biome, fall back if not found
        return biomeLookup.get(key)
                .orElseGet(() -> {
                    System.err.println("[GoT] Biome not found in registry: " + key);
                    return biomeLookup.getOrThrow(FALLBACK);
                });
    }
}