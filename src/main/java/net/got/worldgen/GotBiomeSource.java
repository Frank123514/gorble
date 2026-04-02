package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
 * Custom BiomeSource that stays in lock-step with {@link GotChunkGenerator}.
 *
 * <h2>Horizontal sync (XZ)</h2>
 * <p>Both the terrain generator and this source derive biome boundaries from the
 * same underlying operation: <em>bilinear interpolation of the four nearest
 * biomemap pixels</em>.  This means a column the terrain generator has carved
 * into a river bed (because a low-{@code depth} river pixel dominates the bilinear
 * blend) will also receive the river biome here — there is no independent
 * palette lookup that could disagree.
 *
 * <h2>Vertical sync (Y — water biomes)</h2>
 * <p>Minecraft assigns biomes in 4 × 4 × 4 noise cells.  Without Y awareness,
 * a cell the terrain carved into open water could still receive a land biome
 * because an adjacent land pixel outweighs the river/ocean pixel at that XZ
 * position.  This would create a block-level mismatch: water blocks sitting in
 * a land biome, showing wrong foliage colour and affecting mob spawning.
 *
 * <p>To eliminate this mismatch, {@link #getNoiseBiome} evaluates the exact
 * terrain density at every noise cell that is at or below sea level (using
 * {@link GotChunkGenerator#evalDensity} with the shared {@link GotChunkGenerator#sharedNoiseSeed}).
 * If the density is ≤ 0 (open, filled with water by the generator) the method
 * returns the <em>water</em> biome carrying the highest pixel weight — making
 * biome assignment block-for-block identical to the carved terrain for rivers,
 * oceans, and deep oceans alike.
 *
 * <h2>Biome selection (XZ)</h2>
 * <ol>
 *   <li>Convert the noise-space coordinate to world blocks ({@code x &lt;&lt; 2}).</li>
 *   <li>Compute the float pixel-space coordinate, identical to
 *       {@link GotChunkGenerator#bilinearBlend}.</li>
 *   <li>For the four surrounding pixels compute the same bilinear weights
 *       {@code (1-tx)(1-tz)}, {@code tx(1-tz)}, etc.</li>
 *   <li>Accumulate each weight into a per-{@link ResourceLocation} total.</li>
 *   <li>If the cell is open water (Y-sync check passes), return the highest-weight
 *       water biome; otherwise return the highest-weight biome overall.</li>
 * </ol>
 */
public final class GotBiomeSource extends BiomeSource {

    // ── Colour → registry path palette ───────────────────────────────────

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
        m.put(0x537053, rl("haunted_forest"));
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

    // ── Codec ─────────────────────────────────────────────────────────────

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
    }

    // ── BiomeSource overrides ─────────────────────────────────────────────

    @Override
    protected @NotNull MapCodec<? extends BiomeSource> codec() { return CODEC; }

    @Override
    protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() { return biomes.stream(); }

    /**
     * Returns the biome for the given noise-space coordinate.
     *
     * <h3>Horizontal sync (XZ)</h3>
     * <p>Bilinear interpolation of the four surrounding biomemap pixels —
     * identical to {@link GotChunkGenerator#bilinearBlend} — is used to
     * accumulate per-biome weights.  The dominant weight (nearest pixel centre)
     * wins, keeping XZ biome boundaries exactly where the terrain blend
     * transitions.
     *
     * <h3>Vertical sync (Y — water biomes)</h3>
     * <p>Minecraft assigns biomes in 4 × 4 × 4 noise cells (noise coords × 4 =
     * world blocks).  Without Y awareness a cell that the terrain carved into
     * open water could still receive a land biome because the adjacent land
     * pixel outweighs the river/ocean pixel at that XZ.  This creates a
     * block-level mismatch: water in a "north" biome, grass-coloured and
     * wrongly affecting spawns.
     *
     * <p>To fix this, for every noise cell whose bottom edge ({@code worldY})
     * is at or below {@link GotChunkGenerator#SEA_LEVEL} this method evaluates
     * the terrain density at that point using {@link GotChunkGenerator#evalDensity}
     * — the exact same formula the chunk generator uses.  If the density is
     * ≤ 0 (open, filled with water by the generator) the method returns the
     * <em>water</em> biome with the highest accumulated pixel weight instead of
     * the overall dominant biome, making the biome assignment block-for-block
     * identical to the carved terrain.
     */
    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z,
                                                Climate.@NotNull Sampler sampler) {
        int worldX = x << 2;
        int worldY = y << 2;
        int worldZ = z << 2;

        if (!BiomemapLoader.isLoaded()) return fallback;

        float cx = worldX / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;

        int   px0 = (int) Math.floor(cx);
        int   pz0 = (int) Math.floor(cz);

        // ↓ Apply the same double-smoothstep sharpening as GotChunkGenerator
        //   so biome weights transition in the same narrow band as the terrain.
        float tx  = GotChunkGenerator.sharpenBlend(cx - px0);
        float tz  = GotChunkGenerator.sharpenBlend(cz - pz0);

        float w00 = (1f - tx) * (1f - tz);
        float w10 = tx        * (1f - tz);
        float w01 = (1f - tx) * tz;
        float w11 = tx        * tz;

        Map<ResourceLocation, Float> weights = new HashMap<>(8);
        addPixelWeight(weights, px0,     pz0,     w00);
        addPixelWeight(weights, px0 + 1, pz0,     w10);
        addPixelWeight(weights, px0,     pz0 + 1, w01);
        addPixelWeight(weights, px0 + 1, pz0 + 1, w11);

        // ── Y-aware water biome sync ──────────────────────────────────────
        // Track whether this 4-block noise cell is genuinely open water.
        // Used below to decide whether water biomes are eligible candidates.
        boolean cellIsOpenWater = false;

        if (worldY <= GotChunkGenerator.SEA_LEVEL) {
            float[] bp      = GotChunkGenerator.bilinearBlend(worldX, worldZ);
            float   density = GotChunkGenerator.evalDensity(worldX, worldY, worldZ, bp[0], bp[1]);

            if (density <= 0f) {
                cellIsOpenWater = true;

                // Return the water biome carrying the largest pixel weight.
                ResourceLocation bestWater = null;
                float            bestW     = -1f;
                for (Map.Entry<ResourceLocation, Float> e : weights.entrySet()) {
                    if (isWaterBiome(e.getKey()) && e.getValue() > bestW) {
                        bestW     = e.getValue();
                        bestWater = e.getKey();
                    }
                }
                if (bestWater != null) {
                    Holder<Biome> wh = locationToHolder.get(bestWater);
                    if (wh != null) return wh;
                }
                // Fell through: density <= 0 (open water) but no water pixel
                // contributes weight here.  We keep cellIsOpenWater = true so
                // the dominant-weight pass below can still pick a water biome
                // if one happens to be in the weight map via another path.
            }
        }

        // ── Dominant-weight biome selection ───────────────────────────────
        // FIX: Water biomes (river, ocean, deep_ocean) are only eligible when
        // the cell is confirmed open water.  Without this guard, a river pixel
        // that is the nearest neighbour to a land column could win the weight
        // comparison and assign a river biome to solid ground — causing wrong
        // foliage colours and spawn behaviour along riverbanks.
        ResourceLocation bestLoc    = null;
        float            bestWeight = -1f;
        for (Map.Entry<ResourceLocation, Float> e : weights.entrySet()) {
            // Skip water biomes for solid/above-sea-level cells.
            if (!cellIsOpenWater && isWaterBiome(e.getKey())) continue;
            if (e.getValue() > bestWeight) {
                bestWeight = e.getValue();
                bestLoc    = e.getKey();
            }
        }

        // If every candidate was a water biome (edge case: cell is above sea
        // level but all four surrounding pixels are ocean), fall back to the
        // nearest overall winner including water biomes.
        if (bestLoc == null) {
            for (Map.Entry<ResourceLocation, Float> e : weights.entrySet()) {
                if (e.getValue() > bestWeight) {
                    bestWeight = e.getValue();
                    bestLoc    = e.getKey();
                }
            }
        }

        if (bestLoc == null) return fallback;
        Holder<Biome> h = locationToHolder.get(bestLoc);
        return h != null ? h : fallback;
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    /**
     * Looks up the pixel colour at {@code (px, pz)}, resolves it to a
     * {@link ResourceLocation} via nearest-palette-distance, and adds
     * {@code weight} to that location's running total in {@code acc}.
     */
    private static void addPixelWeight(Map<ResourceLocation, Float> acc,
                                       int px, int pz, float weight) {
        if (weight <= 0f) return;
        int color = BiomemapLoader.getRawPixel(px, pz);
        ResourceLocation loc = closestPaletteMatch(color);
        if (loc != null) acc.merge(loc, weight, Float::sum);
    }

    /**
     * Returns the palette {@link ResourceLocation} whose RGB is nearest (by
     * squared Euclidean distance in RGB space) to the given 24-bit colour.
     * Handles minor PNG compression artefacts at pixel boundaries.
     */
    private static ResourceLocation closestPaletteMatch(int color) {
        ResourceLocation exact = PALETTE.get(color);
        if (exact != null) return exact;

        int r = (color >> 16) & 0xFF;
        int g = (color >>  8) & 0xFF;
        int b =  color        & 0xFF;

        int              bestDist = Integer.MAX_VALUE;
        ResourceLocation bestLoc  = null;

        for (Map.Entry<Integer, ResourceLocation> entry : PALETTE.entrySet()) {
            int c  = entry.getKey();
            int dr = ((c >> 16) & 0xFF) - r;
            int dg = ((c >>  8) & 0xFF) - g;
            int db = ( c        & 0xFF) - b;
            int d  = dr * dr + dg * dg + db * db;
            if (d < bestDist) { bestDist = d; bestLoc = entry.getValue(); }
        }

        return bestLoc;
    }

    /**
     * Returns {@code true} if the biome identified by {@code loc} is a water
     * biome (river, ocean, deep ocean, etc.) as declared in
     * {@link GotBiomeDensityParams}.  Used by the Y-aware water-sync logic to
     * prefer the correct water biome over a dominant land biome when a noise
     * cell is open water in the generated terrain.
     */
    private static boolean isWaterBiome(ResourceLocation loc) {
        GotBiomeDensityParams.Params p = GotBiomeDensityParams.forName(loc.getPath());
        return p != null && p.isWater;
    }
}