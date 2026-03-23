package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Custom BiomeSource that uses bicubic interpolation for water boundaries
 * to stay perfectly synchronized with GotChunkGenerator terrain.
 *
 * <p>Hard guarantee: waterFrac >= 0.5 returns ONLY water biomes,
 * waterFrac < 0.5 returns ONLY land biomes. No leaks.
 */
public final class GotBiomeSource extends BiomeSource {

    private static final Map<Integer, ResourceLocation> PALETTE;
    private static final Set<ResourceLocation> WATER_BIOMES;

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

        Set<ResourceLocation> w = new HashSet<>();
        w.add(rl("ocean"));
        w.add(rl("deep_ocean"));
        w.add(rl("river"));
        w.add(rl("neck_river"));
        w.add(rl("frozen_river"));
        WATER_BIOMES = Collections.unmodifiableSet(w);
    }

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath("got", path);
    }

    public static final MapCodec<GotBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RegistryCodecs.homogeneousList(Registries.BIOME)
                            .fieldOf("biomes")
                            .forGetter(s -> HolderSet.direct(s.biomes))
            ).apply(instance, instance.stable(
                    holderSet -> new GotBiomeSource(holderSet.stream().collect(Collectors.toList()))
            ))
    );

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

    @Override
    protected @NotNull MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() {
        return biomes.stream();
    }

    /**
     * Hard boundary guarantee:
     * <ul>
     *   <li>If bicubic interpolation says water (>= 0.5), returns ONLY water biomes</li>
     *   <li>If bicubic interpolation says land (< 0.5), returns ONLY land biomes</li>
     * </ul>
     * Searches 5x5 neighbourhood to find nearest matching biome type.
     */
    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z,
                                                Climate.@NotNull Sampler sampler) {
        int worldX = x << 2;
        int worldZ = z << 2;

        // Domain-warp the pixel coordinate — identical call to GotChunkGenerator
        // so biome boundaries and terrain features are always co-located.
        float rawCx = worldX / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float rawCz = worldZ / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;
        float[] warped = GotChunkGenerator.warpPixelCoord(rawCx, rawCz);
        float cx = warped[0];
        float cz = warped[1];

        float waterFrac = bicubicWaterFraction(cx, cz);

        if (waterFrac >= GotChunkGenerator.WATER_THRESHOLD) {
            // FORCE water biome - search for nearest water pixel
            return findNearestBiome(cx, cz, true);
        } else {
            // FORCE land biome - search for nearest land pixel
            return findNearestBiome(cx, cz, false);
        }
    }

    /**
     * Finds the nearest biome of the specified type (water or land) within
     * a 5x5 pixel neighbourhood. Guarantees type consistency with terrain.
     */
    private Holder<Biome> findNearestBiome(float cx, float cz, boolean wantWater) {
        int icx = (int) Math.floor(cx);
        int icz = (int) Math.floor(cz);
        float subX = cx - icx; // fractional offset within pixel
        float subZ = cz - icz;

        Holder<Biome> best = null;
        float bestDist = Float.MAX_VALUE;

        // Search 5x5 grid - large enough to handle interpolation mismatches
        for (int dz = -2; dz <= 2; dz++) {
            for (int dx = -2; dx <= 2; dx++) {
                int color = BiomemapLoader.getRawPixel(icx + dx, icz + dz);
                ResourceLocation loc = closestPaletteMatch(color);
                if (loc == null) continue;

                boolean isWater = WATER_BIOMES.contains(loc);
                if (wantWater != isWater) continue; // Skip wrong type

                // Calculate distance from sample point to this pixel center
                float px = dx + 0.5f - subX;
                float pz = dz + 0.5f - subZ;
                float dist = px * px + pz * pz;

                if (dist < bestDist) {
                    bestDist = dist;
                    Holder<Biome> h = locationToHolder.get(loc);
                    if (h != null) best = h;
                }
            }
        }

        if (best != null) return best;

        // Emergency fallback - should never happen if map has both types
        if (wantWater) {
            for (String name : new String[]{"river", "ocean", "deep_ocean", "frozen_river"}) {
                Holder<Biome> h = locationToHolder.get(rl(name));
                if (h != null) return h;
            }
        } else {
            for (String name : new String[]{"north", "barrowlands", "stony_shore", "wolfswood"}) {
                Holder<Biome> h = locationToHolder.get(rl(name));
                if (h != null) return h;
            }
        }
        return fallback;
    }

    /* ------------------------------------------------------------------ */
    /* Bicubic interpolation — identical to GotChunkGenerator             */
    /* ------------------------------------------------------------------ */

    private static float bicubicWaterFraction(float cx, float cz) {
        int ix = (int) Math.floor(cx);
        int iz = (int) Math.floor(cz);
        float fx = cx - ix;
        float fz = cz - iz;

        float[][] grid = new float[4][4];
        for (int dz = 0; dz < 4; dz++) {
            for (int dx = 0; dx < 4; dx++) {
                int color = BiomemapLoader.getRawPixel(ix - 1 + dx, iz - 1 + dz);
                grid[dz][dx] = isWaterColor(color) ? 1.0f : 0.0f;
            }
        }

        float[] rowVals = new float[4];
        for (int dz = 0; dz < 4; dz++) {
            rowVals[dz] = catmullRom(fx, grid[dz][0], grid[dz][1], grid[dz][2], grid[dz][3]);
        }
        return Mth.clamp(catmullRom(fz, rowVals[0], rowVals[1], rowVals[2], rowVals[3]), 0f, 1f);
    }

    private static float catmullRom(float t, float p0, float p1, float p2, float p3) {
        float t2 = t * t;
        float t3 = t2 * t;
        return 0.5f * (
                (2f * p1) +
                        (-p0 + p2) * t +
                        (2f * p0 - 5f * p1 + 4f * p2 - p3) * t2 +
                        (-p0 + 3f * p1 - 3f * p2 + p3) * t3
        );
    }

    private static boolean isWaterColor(int color) {
        ResourceLocation loc = PALETTE.get(color);
        if (loc != null) return WATER_BIOMES.contains(loc);
        return WATER_BIOMES.contains(closestPaletteMatch(color));
    }

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