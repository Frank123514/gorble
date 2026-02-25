package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Custom {@link BiomeSource} that reads biome placement from a hand-painted
 * PNG ({@code got:worldgen/map/biomemap.png}) using the colour palette defined
 * in {@code biome_colors.json}.
 *
 * <h3>Biome border naturalisation — domain-warp jitter</h3>
 * A naïve pixel-lookup produces perfectly straight grid-aligned biome borders
 * wherever painted biome regions meet, which looks obviously artificial.
 * To break this up, two independent low-frequency {@link SimplexNoise} fields
 * are used to offset the query coordinates before sampling the biomemap:
 *
 * <pre>
 *   jitterX = simplexX.getValue(wx * freq, wz * freq) * amplitude
 *   jitterZ = simplexZ.getValue(wx * freq, wz * freq) * amplitude
 *   sampleColor = biomemap.getColor(wx + jitterX, wz + jitterZ)
 * </pre>
 *
 * The noise frequency is tuned so that the jitter pattern has a wavelength
 * of roughly 400 blocks — wide enough to create sweeping natural-looking
 * border curves without visibly distorting the overall painted map layout.
 * The amplitude (≈ 1.8 pixels × 28 blocks/pixel ≈ 50 blocks) is large enough
 * to fully break straight lines but small enough that biome positions on the
 * map remain faithful to the original painting.
 *
 * <h3>Colour matching</h3>
 * Each (jittered) pixel is matched to the nearest palette entry by Euclidean
 * RGB distance, so minor PNG compression artefacts are handled gracefully.
 */
public final class GotBiomeSource extends BiomeSource {

    // -----------------------------------------------------------------------
    // Palette — mirrors biome_colors.json exactly
    // -----------------------------------------------------------------------

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

    // -----------------------------------------------------------------------
    // Domain-warp noise configuration
    // -----------------------------------------------------------------------

    /**
     * Fixed seed for reproducible jitter regardless of world seed.
     * Using a separate constant means the biome layout never changes between
     * worlds, matching the hand-painted map.
     */
    private static final long JITTER_SEED = 0xB10ME_JITTER_77L;

    /**
     * Noise frequency (blocks⁻¹).
     * 1 / 400 → wavelength ≈ 400 blocks, producing gently sweeping borders.
     * Lower values = wider, smoother curves.  Higher values = tighter wiggles.
     */
    private static final double JITTER_FREQ = 1.0 / 400.0;

    /**
     * Maximum displacement in world blocks.
     * 50 blocks ≈ 1.8 biomemap pixels at 28 blocks/pixel.
     * Large enough to fully break pixel-grid lines; small enough that the
     * overall painted map layout is visually unchanged.
     *
     * Water biomes (river, ocean) are given reduced jitter so river channels
     * don't get distorted into unrecognisable shapes.
     */
    private static final double JITTER_AMPLITUDE_LAND  = 50.0;
    private static final double JITTER_AMPLITUDE_WATER = 18.0;

    // -----------------------------------------------------------------------
    // Codec
    // -----------------------------------------------------------------------

    public static final MapCodec<GotBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RegistryCodecs.homogeneousList(Registries.BIOME)
                            .fieldOf("biomes")
                            .forGetter(s -> HolderSet.direct(s.biomes))
            ).apply(instance, instance.stable(
                    holderSet -> new GotBiomeSource(holderSet.stream().collect(Collectors.toList()))
            ))
    );

    // -----------------------------------------------------------------------
    // State
    // -----------------------------------------------------------------------

    private final List<Holder<Biome>> biomes;
    private final Map<ResourceLocation, Holder<Biome>> locationToHolder;
    private final Holder<Biome> fallback;

    /** X-axis displacement noise. */
    private final SimplexNoise jitterX;
    /** Z-axis displacement noise. Seeded differently from jitterX for independence. */
    private final SimplexNoise jitterZ;

    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    public GotBiomeSource(List<Holder<Biome>> biomes) {
        this.biomes = List.copyOf(biomes);
        this.locationToHolder = new HashMap<>(biomes.size() * 2);

        for (Holder<Biome> h : biomes) {
            h.unwrapKey().ifPresent(key -> locationToHolder.put(key.location(), h));
        }

        Holder<Biome> fb = locationToHolder.get(rl("north"));
        if (fb == null) fb = locationToHolder.get(rl("ocean"));
        if (fb == null && !biomes.isEmpty()) fb = biomes.get(0);
        this.fallback = Objects.requireNonNull(fb, "GotBiomeSource: biome list is empty!");

        // Seed two independent noise fields from the same fixed seed stream.
        // Using RandomSource guarantees the two SimplexNoise instances pull from
        // different positions in the permutation table.
        RandomSource rng = RandomSource.create(JITTER_SEED);
        this.jitterX = new SimplexNoise(rng);
        this.jitterZ = new SimplexNoise(rng);
    }

    // -----------------------------------------------------------------------
    // BiomeSource overrides
    // -----------------------------------------------------------------------

    @Override
    protected @NotNull MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() {
        return biomes.stream();
    }

    /**
     * Maps biome-noise coordinates to a biome holder.
     *
     * <ol>
     *   <li>Convert biome coords (×4) to world block coords.</li>
     *   <li>Sample the two jitter noise fields to compute a displacement.</li>
     *   <li>Reduce jitter near water pixels to keep river channels intact.</li>
     *   <li>Sample the biomemap at the displaced coordinates.</li>
     *   <li>Match the colour to the nearest palette entry.</li>
     * </ol>
     */
    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z,
                                                Climate.@NotNull Sampler sampler) {
        // Biome noise grid is 1/4 block resolution
        int worldX = x << 2;
        int worldZ = z << 2;

        // ── Step 1: sample unjittered colour to decide amplitude ──────────
        // Water biomes (rivers, ocean) get much smaller jitter so channels
        // don't dissolve.  We detect water by testing the raw map colour.
        int rawColor  = BiomemapLoader.getColorAtWorld(worldX, worldZ);
        boolean isWater = isWaterColor(rawColor);
        double amplitude = isWater ? JITTER_AMPLITUDE_WATER : JITTER_AMPLITUDE_LAND;

        // ── Step 2: compute jitter displacement ───────────────────────────
        double nx = worldX * JITTER_FREQ;
        double nz = worldZ * JITTER_FREQ;

        // Offset the second lookup so jitterX and jitterZ are decorrelated
        double dx = jitterX.getValue(nx,          nz         ) * amplitude;
        double dz = jitterZ.getValue(nx + 31.41,  nz + 27.18 ) * amplitude;

        int jx = worldX + (int) Math.round(dx);
        int jz = worldZ + (int) Math.round(dz);

        // ── Step 3: sample jittered colour ────────────────────────────────
        int color = BiomemapLoader.getColorAtWorld(jx, jz);

        // ── Step 4: palette lookup ────────────────────────────────────────
        ResourceLocation loc = closestPaletteMatch(color);
        if (loc != null) {
            Holder<Biome> h = locationToHolder.get(loc);
            if (h != null) return h;
        }

        return fallback;
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /**
     * Returns true if this palette colour belongs to an ocean, river or
     * frozen-water biome.  Used to scale down jitter near water features.
     */
    private static boolean isWaterColor(int color) {
        return color == 0x00229D   // ocean
                || color == 0x110751   // deep_ocean
                || color == 0x2D6796   // river
                || color == 0x35A180   // neck_river
                || color == 0x4B91E6;  // frozen_river
    }

    /**
     * Returns the palette entry whose RGB colour is closest (Euclidean
     * distance in RGB space) to the sampled pixel colour.
     */
    private static ResourceLocation closestPaletteMatch(int color) {
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