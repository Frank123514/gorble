package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.worldgen.BiomemapLoader;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Custom {@link BiomeSource} that reads biome placement from a hand-painted
 * PNG ({@code got:worldgen/map/biomemap.png}) using the colour palette defined
 * in {@code biome_colors.json}.
 *
 * <h3>Colour matching</h3>
 * Each pixel is matched to the nearest palette entry by Euclidean RGB distance,
 * so minor PNG compression artefacts at biome boundaries are handled gracefully.
 *
 * <h3>Coordinate mapping</h3>
 * Biome noise coordinates (1/4 block resolution) are multiplied by 4 to obtain
 * world block coordinates, then passed to {@link BiomemapLoader#getColorAtWorld}.
 */
public final class GotBiomeSource extends BiomeSource {



    /* ------------------------------------------------------------------ */
    /* Palette — mirrors biome_colors.json exactly                         */
    /* ------------------------------------------------------------------ */

    /**
     * Maps 24-bit RGB palette colours → {@code got:*} biome resource locations.
     * Order determines tie-break priority for exact-distance matches.
     */
    private static final Map<Integer, ResourceLocation> PALETTE;
    static {
        Map<Integer, ResourceLocation> m = new LinkedHashMap<>();
        m.put(0x949038, rl("north"));
        m.put(0xADA942, rl("barrowlands"));
        m.put(0x92B0AC, rl("stony_shore"));
        m.put(0x808F81, rl("north_hills"));
        m.put(0x2F4A33, rl("neck"));
        m.put(0x02450D, rl("ironwood"));
        m.put(0x047D17, rl("wolfswood"));
        m.put(0x00229D, rl("ocean"));
        m.put(0x110751, rl("deep_ocean"));
        m.put(0x2D6796, rl("river"));
        m.put(0x35A180, rl("neck_river"));
        m.put(0x4B91E6, rl("frozen_river"));
        m.put(0xBBCCCD, rl("frostfangs"));
        m.put(0xFFFFFF, rl("always_winter"));
        m.put(0xA5B7B9, rl("north_mountains"));
        PALETTE = Collections.unmodifiableMap(m);
    }

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath("got", path);
    }

    /* ------------------------------------------------------------------ */
    /* Codec                                                                */
    /* ------------------------------------------------------------------ */

    /**
     * The codec encodes/decodes the list of possible biomes from the dimension
     * JSON.  The dimension JSON must include every biome that the biomemap can
     * reference so that NeoForge's biome feature pipeline is aware of them.
     *
     * <pre>{@code
     * "biome_source": {
     *   "type": "got:biome_source",
     *   "biomes": [
     *     "got:north",
     *     "got:barrowlands",
     *     …
     *   ]
     * }
     * }</pre>
     */
    public static final MapCodec<GotBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RegistryCodecs.homogeneousList(Registries.BIOME)
                            .fieldOf("biomes")
                            .forGetter(s -> HolderSet.direct(s.biomes))
            ).apply(instance, instance.stable(
                    holderSet -> new GotBiomeSource(holderSet.stream().collect(Collectors.toList()))
            ))
    );

    /* ------------------------------------------------------------------ */
    /* State                                                                */
    /* ------------------------------------------------------------------ */

    /** All biomes this source may return — mirrors the JSON "biomes" list. */
    private final List<Holder<Biome>> biomes;

    /**
     * Fast lookup: {@code got:*} resource location → biome holder.
     * Built from {@link #biomes} at construction time.
     */
    private final Map<ResourceLocation, Holder<Biome>> locationToHolder;

    /** Returned when the biomemap is not yet loaded or has an unknown colour. */
    private final Holder<Biome> fallback;

    /* ------------------------------------------------------------------ */
    /* Constructor                                                          */
    /* ------------------------------------------------------------------ */

    public GotBiomeSource(List<Holder<Biome>> biomes) {
        this.biomes = List.copyOf(biomes);
        this.locationToHolder = new HashMap<>(biomes.size() * 2);

        for (Holder<Biome> h : biomes) {
            h.unwrapKey().ifPresent(key -> locationToHolder.put(key.location(), h));
        }

        // Prefer "got:north" as the out-of-bounds / fallback biome
        Holder<Biome> fb = locationToHolder.get(rl("north"));
        if (fb == null) fb = locationToHolder.get(rl("ocean"));
        if (fb == null && !biomes.isEmpty()) fb = biomes.get(0);
        this.fallback = Objects.requireNonNull(fb, "GotBiomeSource: biome list is empty!");
    }

    /* ------------------------------------------------------------------ */
    /* BiomeSource overrides                                                */
    /* ------------------------------------------------------------------ */

    @Override
    protected @NotNull MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() {
        return biomes.stream();
    }

    /**
     * Maps biome noise coordinates to a biome holder by:
     * <ol>
     *   <li>Converting biome coords (×4) to world block coords.</li>
     *   <li>Sampling the biomemap PNG pixel at that position.</li>
     *   <li>Matching the pixel colour to the nearest palette entry.</li>
     *   <li>Returning the corresponding {@link Holder}.</li>
     * </ol>
     */
    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        // Biome noise grid is at 1/4 block resolution → shift left by 2 for world blocks
        int worldX = x << 2;
        int worldZ = z << 2;

        int color = BiomemapLoader.getColorAtWorld(worldX, worldZ);

        ResourceLocation loc = closestPaletteMatch(color);
        if (loc != null) {
            Holder<Biome> h = locationToHolder.get(loc);
            if (h != null) return h;
        }

        return fallback;
    }

    /* ------------------------------------------------------------------ */
    /* Colour matching                                                      */
    /* ------------------------------------------------------------------ */

    /**
     * Returns the palette entry whose RGB colour is closest (Euclidean distance
     * in RGB space) to the sampled pixel colour.
     *
     * @param color 24-bit RGB sampled from the biomemap
     * @return the matching biome's resource location, or {@code null} if the
     *         palette is empty (should never happen in practice)
     */
    private static ResourceLocation closestPaletteMatch(int color) {
        // Exact hit first — saves the distance loop for well-painted areas
        ResourceLocation exact = PALETTE.get(color);
        if (exact != null) return exact;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8)  & 0xFF;
        int b =  color        & 0xFF;

        int bestDist = Integer.MAX_VALUE;
        ResourceLocation bestLoc = null;

        for (Map.Entry<Integer, ResourceLocation> entry : PALETTE.entrySet()) {
            int c  = entry.getKey();
            int dr = ((c >> 16) & 0xFF) - r;
            int dg = ((c >> 8)  & 0xFF) - g;
            int db = ( c        & 0xFF) - b;
            int dist = dr * dr + dg * dg + db * db;

            if (dist < bestDist) {
                bestDist = dist;
                bestLoc  = entry.getValue();
            }
        }

        return bestLoc;
    }
}