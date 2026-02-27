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
 * Custom {@link BiomeSource} that reads biome placement from a hand-painted
 * PNG ({@code got:worldgen/map/biomemap.png}) using the colour palette defined
 * in {@code biome_colors.json}.
 *
 * <h3>Elevation cross-check — bidirectional</h3>
 * The domain warp applied to both maps can cause a small misalignment between
 * what the biomemap says and what the heightmap carved:
 *
 * <ul>
 *   <li><b>Water carved but biomemap says land</b> — pick a contextual water biome
 *       so carved river channels are always filled with a river/ocean biome.</li>
 *   <li><b>Biomemap says water but terrain is land</b> — pick a contextual land biome
 *       so river/ocean pixels that warped onto land don't leak the water biome.</li>
 * </ul>
 */
public final class GotBiomeSource extends BiomeSource {

    /* ------------------------------------------------------------------ */
    /* Palette — mirrors biome_colors.json exactly                         */
    /* ------------------------------------------------------------------ */

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

    /** Water-type biomes — used for the elevation cross-check. */
    private static final Set<String> WATER_BIOME_PATHS = Set.of(
            "river", "neck_river", "frozen_river", "ocean", "deep_ocean",
            "frozen_lake", "lake"
    );

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath("got", path);
    }

    /* ------------------------------------------------------------------ */
    /* Codec                                                                */
    /* ------------------------------------------------------------------ */

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

    private final List<Holder<Biome>> biomes;
    private final Map<ResourceLocation, Holder<Biome>> locationToHolder;
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
     * Returns a biome holder for the given noise coordinates.
     *
     * After resolving the biomemap colour, the result is cross-checked against
     * the heightmap elevation to catch warp-induced mismatches:
     *
     * <ul>
     *   <li>If terrain is carved below sea level (elev ≤ 0) but the biomemap
     *       resolved to a land biome → pick a contextual water biome.</li>
     *   <li>If the biomemap resolved to a water biome but terrain is land
     *       (elev &gt; 0) → scan nearby pixels for a land biome, preventing
     *       water-biome "leaks" onto land.</li>
     * </ul>
     */
    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        int worldX = x << 2;
        int worldZ = z << 2;

        // Sample biomemap (warped the same way as the heightmap)
        int color = BiomemapLoader.getColorAtWorld(worldX, worldZ);
        ResourceLocation loc = closestPaletteMatch(color);

        // Sample heightmap elevation for cross-check.
        // Use actual block Y rather than raw float to avoid the elev=0.0 boundary
        // case where Y=61=sea level gets grass but still passes as "water terrain".
        float elev = HeightmapLoader.getElevationAtWorld(worldX, worldZ);
        int blockY = GotChunkGenerator.elevToY(elev, worldX, worldZ);
        boolean terrainIsWater = blockY < GotChunkGenerator.SEA_LEVEL;
        boolean biomeIsWater   = loc != null && isWaterBiome(loc);

        // ── Case 1: terrain carved water but biomemap says land ───────────
        if (terrainIsWater && !biomeIsWater) {
            loc = pickWaterBiomeForContext(worldX, worldZ, loc);
        }

        // ── Case 2: biomemap says water but terrain is land ───────────────
        // The domain warp shifted the lookup onto a painted river/ocean pixel.
        // Bypass warp entirely: do a raw unwarped pixel lookup to get what is
        // actually painted at this world position, then if it's still water
        // fall back to the nearest non-water neighbour in raw image space.
        else if (!terrainIsWater && biomeIsWater) {
            int[] rawPx = BiomemapLoader.getWarpedPixel(worldX, worldZ);
            // Walk outward from the warped pixel in raw image space until we
            // find a non-water pixel — guaranteed to terminate since land
            // surrounds every river channel.
            ResourceLocation landLoc = null;
            outer:
            for (int r = 1; r <= 12; r++) {
                for (int dx = -r; dx <= r; dx++) {
                    for (int dz = -r; dz <= r; dz++) {
                        if (Math.abs(dx) < r && Math.abs(dz) < r) continue;
                        int c2 = BiomemapLoader.getRawPixel(rawPx[0] + dx, rawPx[1] + dz);
                        ResourceLocation cand = closestPaletteMatch(c2);
                        if (cand != null && !isWaterBiome(cand)) {
                            landLoc = cand;
                            break outer;
                        }
                    }
                }
            }
            loc = (landLoc != null) ? landLoc : rl("north");
        }

        if (loc != null) {
            Holder<Biome> h = locationToHolder.get(loc);
            if (h != null) return h;
        }

        return fallback;
    }

    /* ------------------------------------------------------------------ */
    /* Water-biome helpers                                                  */
    /* ------------------------------------------------------------------ */

    private static boolean isWaterBiome(ResourceLocation loc) {
        return WATER_BIOME_PATHS.contains(loc.getPath());
    }

    /**
     * Picks the most contextually correct water biome for a position that the
     * heightmap carved below sea level but whose biomemap pixel is land.
     *
     * Scans outward in rings for a nearby painted water biome (respects the
     * artist's river/neck_river/frozen_river intent), then falls back to
     * heuristics based on the surrounding land biome name.
     */
    private ResourceLocation pickWaterBiomeForContext(int worldX, int worldZ,
                                                      ResourceLocation nearbyLand) {
        // Scan in raw image-pixel space to avoid re-warping neighbour lookups.
        int[] origin = BiomemapLoader.getWarpedPixel(worldX, worldZ);
        int opx = origin[0];
        int opz = origin[1];
        int range = 5;

        for (int r = 1; r <= range; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.abs(dx) < r && Math.abs(dz) < r) continue;
                    int c = BiomemapLoader.getRawPixel(opx + dx, opz + dz);
                    ResourceLocation nearby = closestPaletteMatch(c);
                    if (nearby != null && isWaterBiome(nearby)) return nearby;
                }
            }
        }

        // Heuristic fallback based on surrounding land context
        if (nearbyLand != null) {
            String path = nearbyLand.getPath();
            if (path.equals("neck")) return rl("neck_river");
            if (path.equals("frostfangs") || path.equals("always_winter")
                    || path.equals("north_mountains") || path.equals("haunted_forest")) {
                return rl("frozen_river");
            }
        }

        return rl("river");
    }

    /* ------------------------------------------------------------------ */
    /* Land-biome helpers                                                   */
    /* ------------------------------------------------------------------ */

    /**
     * Picks the most contextually correct land biome for a position whose
     * biomemap pixel warped onto a painted water tile but whose terrain is
     * actually above sea level (elev &gt; 0).
     *
     * Scans outward in rings, returning the first non-water biome found whose
     * own heightmap elevation also agrees it is land — this double-check prevents
     * picking another warped-water pixel as the "land" representative.
     */
    private ResourceLocation pickLandBiomeForContext(int worldX, int worldZ) {
        // Convert the problem position to unwarped image-pixel coords.
        // Scanning via getColorAtWorld() would re-warp every neighbour lookup,
        // curving each step back onto the same river pixel — use raw pixels instead.
        int[] origin = BiomemapLoader.getWarpedPixel(worldX, worldZ);
        int opx = origin[0];
        int opz = origin[1];

        int range = 8; // 8 px covers full warp displacement (~2.8 px) with margin

        for (int r = 1; r <= range; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.abs(dx) < r && Math.abs(dz) < r) continue; // ring only
                    int c = BiomemapLoader.getRawPixel(opx + dx, opz + dz);
                    ResourceLocation nearby = closestPaletteMatch(c);
                    if (nearby != null && !isWaterBiome(nearby)) return nearby;
                }
            }
        }

        return rl("north"); // safe fallback
    }

    /* ------------------------------------------------------------------ */
    /* Colour matching                                                      */
    /* ------------------------------------------------------------------ */

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