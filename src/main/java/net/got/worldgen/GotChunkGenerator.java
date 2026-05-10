package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Gorble chunk generator — bicubic B-spline terrain pipeline.
 *
 * <p>Height interpolation uses bicubic B-spline over a 4×4 cell for
 * ultra-smooth organic transitions.  B-spline has C2 continuity (continuous
 * second derivative) compared to Catmull-Rom's C1, producing rounder,
 * gentler corners without overshoot.  Land pixels use
 * {@link #getSmoothHeight} so slopes and biome transitions stay organic,
 * BUT water neighbours are excluded from a land pixel's smooth average.
 * This prevents land from being dragged down prematurely toward water,
 * eliminating the wide underwater shelf.
 */
public final class GotChunkGenerator extends ChunkGenerator {

    public static final int SEA_LEVEL = 61;

    // Primary terrain noise
    private static final double PRIMARY_SCALE_X   = 220.0;
    private static final double PRIMARY_SCALE_Z   = 190.0;
    private static final int    PRIMARY_OCTAVES   = 5;

    // Mountain exponential
    private static final float  MOUNTAIN_START_ABOVE_SEA  = 30f;
    private static final float  MOUNTAIN_EXPONENTIAL      = 1.018f;

    // Mountain slope detail noise
    private static final double MOUNTAIN_DETAIL_SCALE  = 42.0;
    private static final float  MOUNTAIN_DETAIL_RANGE  = 3.5f;
    private static final double MOUNTAIN_DETAIL_SCALE2 = 21.0;
    private static final float  MOUNTAIN_DETAIL_RANGE2 = 1.8f;

    // Water noise suppression
    private static final float  WATER_NOISE_DIVIDER       = 1.2f;

    // Caves (tuned)
    private static final float  BLOB_HSCALE = 52f;
    private static final float  BLOB_VSCALE = 45f;
    private static final float  VEIN_HSCALE = 78f;
    private static final float  MASK_HSCALE = 90f;
    private static final float  BLOB_CARVE  = 0.36f;
    private static final float  MASK_GATE   = 0.74f;
    private static final float  VEIN_CARVE  = 0.08f;

    // Codec / vanilla delegate
    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator       vanilla;
    private final int spawnPixelX;
    private final int spawnPixelZ;

    private static int configuredSpawnPixelX = -1;
    private static int configuredSpawnPixelZ = -1;

    public static final MapCodec<GotChunkGenerator> CODEC =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    BiomeSource.CODEC.fieldOf("biome_source")
                            .forGetter(ChunkGenerator::getBiomeSource),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings")
                            .forGetter(g -> g.settings),
                    com.mojang.serialization.Codec.INT
                            .optionalFieldOf("spawn_pixel_x", -1)
                            .forGetter(g -> g.spawnPixelX),
                    com.mojang.serialization.Codec.INT
                            .optionalFieldOf("spawn_pixel_z", -1)
                            .forGetter(g -> g.spawnPixelZ)
            ).apply(i, GotChunkGenerator::new));

    public GotChunkGenerator(BiomeSource biomeSource,
                             Holder<NoiseGeneratorSettings> settings,
                             int spawnPixelX, int spawnPixelZ) {
        super(biomeSource);
        this.settings    = settings;
        this.spawnPixelX = spawnPixelX;
        this.spawnPixelZ = spawnPixelZ;
        this.vanilla     = new NoiseBasedChunkGenerator(biomeSource, settings);
        configuredSpawnPixelX = spawnPixelX;
        configuredSpawnPixelZ = spawnPixelZ;
    }

    public static int getConfiguredSpawnPixelX() { return configuredSpawnPixelX; }
    public static int getConfiguredSpawnPixelZ() { return configuredSpawnPixelZ; }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

    // =========================================================================
    // fillFromNoise
    // =========================================================================

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender, @NotNull RandomState random,
            @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {

        NoiseSettings ns = settings.value().noiseSettings();
        int minY = ns.minY();
        int maxY = minY + ns.height();
        int sea  = getSeaLevel();
        ChunkPos cp = chunk.getPos();

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = cp.getBlockX(lx);
                int wz = cp.getBlockZ(lz);
                int surfaceY = computeSurfaceY(wx, wz);

                for (int y = minY; y < maxY; y++) {
                    BlockState state;
                    if (y > surfaceY) {
                        state = (y <= sea)
                                ? settings.value().defaultFluid()
                                : Blocks.AIR.defaultBlockState();
                    } else if (isCave(wx, y, wz)) {
                        state = Blocks.AIR.defaultBlockState();
                    } else {
                        state = Blocks.STONE.defaultBlockState();
                    }
                    chunk.setBlockState(new BlockPos(lx, y, lz), state, false);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    // =========================================================================
    // buildSurface — delegated to vanilla
    // =========================================================================

    @Override
    public void buildSurface(@NotNull WorldGenRegion region, @NotNull StructureManager structures,
                             @NotNull RandomState random, @NotNull ChunkAccess chunk) {
        vanilla.buildSurface(region, structures, random, chunk);
    }

    // =========================================================================
    // computeSurfaceY / computeRawSurfaceY
    // =========================================================================

    /**
     * 9×9 box blur for land pixels.  Water pixels are returned exactly.
     *
     * <p>Crucially, when a land pixel averages its neighbours, any water
     * neighbours are <b>excluded</b> — they do not drag the land height down.
     * This keeps land high right up to the water's edge.
     */
    private static float getSmoothHeight(int px, int pz) {
        if (GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz)).isWater())
            return pixelBaseY(px, pz);

        float total = 0f;
        int count = 0;
        for (int i = -SMOOTH_BRUSH_SIZE; i <= SMOOTH_BRUSH_SIZE; i++) {
            for (int j = -SMOOTH_BRUSH_SIZE; j <= SMOOTH_BRUSH_SIZE; j++) {
                int npx = px + i, npz = pz + j;
                if (npx < 0 || npz < 0 || npx >= BiomemapLoader.getWidth() || npz >= BiomemapLoader.getHeight())
                    continue;
                // Skip water neighbours — they must not pull land down
                if (GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(npx, npz)).isWater())
                    continue;
                total += pixelBaseY(npx, npz);
                count++;
            }
        }
        // If no valid land neighbours (surrounded by water), fall back to own baseY
        return count > 0 ? total / count : pixelBaseY(px, pz);
    }

    private static final int SMOOTH_BRUSH_SIZE = 4; // radius-4 (9×9)

    public static int computeSurfaceY(int worldX, int worldZ) {
        return Mth.floor(computeRawSurfaceY(worldX, worldZ));
    }

    public static float computeRawSurfaceY(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;

        float cx = worldX / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;

        int   ipx = (int) Math.floor(cx);
        int   ipz = (int) Math.floor(cz);
        float fx  = cx - ipx;
        float fz  = cz - ipz;

        // Bicubic B-spline height: sample 4×4 grid around the cell
        float biomeHeight = bicubicBsplineBlend(
                heightForBicubic(ipx-1, ipz-1), heightForBicubic(ipx, ipz-1), heightForBicubic(ipx+1, ipz-1), heightForBicubic(ipx+2, ipz-1),
                heightForBicubic(ipx-1, ipz  ), heightForBicubic(ipx, ipz  ), heightForBicubic(ipx+1, ipz  ), heightForBicubic(ipx+2, ipz  ),
                heightForBicubic(ipx-1, ipz+1), heightForBicubic(ipx, ipz+1), heightForBicubic(ipx+1, ipz+1), heightForBicubic(ipx+2, ipz+1),
                heightForBicubic(ipx-1, ipz+2), heightForBicubic(ipx, ipz+2), heightForBicubic(ipx+1, ipz+2), heightForBicubic(ipx+2, ipz+2),
                fx, fz);

        // Bicubic B-spline scale: sample 4×4 grid around the cell
        float biomeScale = bicubicBsplineBlend(
                scaleForBicubic(ipx-1, ipz-1), scaleForBicubic(ipx, ipz-1), scaleForBicubic(ipx+1, ipz-1), scaleForBicubic(ipx+2, ipz-1),
                scaleForBicubic(ipx-1, ipz  ), scaleForBicubic(ipx, ipz  ), scaleForBicubic(ipx+1, ipz  ), scaleForBicubic(ipx+2, ipz  ),
                scaleForBicubic(ipx-1, ipz+1), scaleForBicubic(ipx, ipz+1), scaleForBicubic(ipx+1, ipz+1), scaleForBicubic(ipx+2, ipz+1),
                scaleForBicubic(ipx-1, ipz+2), scaleForBicubic(ipx, ipz+2), scaleForBicubic(ipx+1, ipz+2), scaleForBicubic(ipx+2, ipz+2),
                fx, fz);

        // Plain fBm
        double primary = DomainWarpNoise.fbm(
                worldX / PRIMARY_SCALE_X, worldZ / PRIMARY_SCALE_Z,
                PRIMARY_OCTAVES, 2.0, 0.5);
        float perlinBlocks = (float) primary * biomeScale;

        // Water suppression
        if (biomeHeight < SEA_LEVEL) {
            float depth   = SEA_LEVEL - biomeHeight;
            float divider = Math.max(1f, Math.min(5f, depth / WATER_NOISE_DIVIDER));
            perlinBlocks /= divider;
        }

        // Mountain exponential boost + slope detail noise
        if (biomeHeight >= SEA_LEVEL + MOUNTAIN_START_ABOVE_SEA) {
            float relHeight  = biomeHeight - SEA_LEVEL;
            float multiplier = (relHeight / MOUNTAIN_START_ABOVE_SEA) - 1f;
            biomeHeight += biomeHeight * multiplier * MOUNTAIN_EXPONENTIAL;

            float detail1 = (float) DomainWarpNoise.fbm(
                    worldX / MOUNTAIN_DETAIL_SCALE, worldZ / MOUNTAIN_DETAIL_SCALE, 3, 2.0, 0.5);
            float detail2 = (float) DomainWarpNoise.fbm(
                    worldX / MOUNTAIN_DETAIL_SCALE2, worldZ / MOUNTAIN_DETAIL_SCALE2, 2, 2.0, 0.5);
            perlinBlocks += multiplier * MOUNTAIN_DETAIL_RANGE  * detail1;
            perlinBlocks += multiplier * MOUNTAIN_DETAIL_RANGE2 * detail2;
        }

        return biomeHeight + perlinBlocks;
    }

    /** Height for a bicubic sample: land = smoothed, water = raw baseY. */
    private static float heightForBicubic(int px, int pz) {
        if (px < 0 || pz < 0 || px >= BiomemapLoader.getWidth() || pz >= BiomemapLoader.getHeight())
            return SEA_LEVEL;
        var params = GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
        return params.isWater() ? params.baseY() : getSmoothHeight(px, pz);
    }

    /** Scale for a bicubic sample: land = 3×3 blur, water = raw scale. */
    private static float scaleForBicubic(int px, int pz) {
        if (px < 0 || pz < 0 || px >= BiomemapLoader.getWidth() || pz >= BiomemapLoader.getHeight())
            return 4f;
        var params = GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
        return params.isWater() ? params.scale() : smoothPixelScale(px, pz);
    }

    // =========================================================================
    // Interpolation helpers
    // =========================================================================

    /**
     * Bicubic B-spline interpolation over a 4×4 grid.
     *
     * <p>B-spline has C2 continuity (continuous second derivative) producing
     * rounder, gentler curves than Catmull-Rom's C1.  It approximates rather
     * than interpolates the control points, so corners are smoother with
     * no overshoot.
     */
    private static float bicubicBsplineBlend(
            float v00, float v10, float v20, float v30,
            float v01, float v11, float v21, float v31,
            float v02, float v12, float v22, float v32,
            float v03, float v13, float v23, float v33,
            float fx, float fz) {
        float r0 = cubicBspline(v00, v10, v20, v30, fx);
        float r1 = cubicBspline(v01, v11, v21, v31, fx);
        float r2 = cubicBspline(v02, v12, v22, v32, fx);
        float r3 = cubicBspline(v03, v13, v23, v33, fx);
        return cubicBspline(r0, r1, r2, r3, fz);
    }

    /**
     * 1D cubic B-spline basis.
     *
     * <p>Coefficients from the uniform cubic B-spline basis matrix:
     * <pre>
     *  1/6 * [ -1  3 -3  1 ]
     *        [  3 -6  3  0 ]
     *        [ -3  0  3  0 ]
     *        [  1  4  1  0 ]
     * </pre>
     */
    private static float cubicBspline(float p0, float p1, float p2, float p3, float t) {
        float t2 = t * t;
        float t3 = t2 * t;
        return (1f / 6f) * (
                (-p0 + 3f * p1 - 3f * p2 + p3) * t3
                        + (3f * p0 - 6f * p1 + 3f * p2)     * t2
                        + (-3f * p0 + 3f * p2)              * t
                        + (p0 + 4f * p1 + p2)
        );
    }

    /** 3×3 box-blur for noise-scale smoothing (land only). */
    private static final int SCALE_BLUR_SIZE  = 1;
    private static final int SCALE_BLUR_COUNT = (SCALE_BLUR_SIZE * 2 + 1)
            * (SCALE_BLUR_SIZE * 2 + 1);

    private static float smoothPixelScale(int px, int pz) {
        float total = 0;
        for (int i = -SCALE_BLUR_SIZE; i <= SCALE_BLUR_SIZE; i++) {
            for (int j = -SCALE_BLUR_SIZE; j <= SCALE_BLUR_SIZE; j++) {
                total += pixelScale(px + i, pz + j);
            }
        }
        return total / SCALE_BLUR_COUNT;
    }

    // =========================================================================
    // Pixel helpers
    // =========================================================================

    private static float pixelBaseY(int px, int pz) {
        return GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz)).baseY();
    }
    private static float pixelScale(int px, int pz) {
        return GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz)).scale();
    }

    private static GotBiomeTerrainParams.Params terrainParamsAt(int worldX, int worldZ) {
        int px = (int) Math.floor(worldX / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f);
        int pz = (int) Math.floor(worldZ / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f);
        return GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
    }

    // =========================================================================
    // Caves
    // =========================================================================

    private static boolean isCave(int x, int y, int z) {
        float mask = (float) SimplexNoise.noise3(x/MASK_HSCALE, y/MASK_HSCALE, z/MASK_HSCALE);
        if (mask > MASK_GATE) return false;
        double ty   = Math.tan(y / (double) BLOB_VSCALE);
        float  blob = (float) SimplexNoise.fbm3(x, ty*BLOB_VSCALE, z, BLOB_HSCALE, BLOB_VSCALE);
        if (blob > BLOB_CARVE) return false;
        float v1 = Math.abs((float)SimplexNoise.noise3(x/(VEIN_HSCALE*1.4f), ty, z/(VEIN_HSCALE*1.4f)));
        float v2 = Math.abs((float)SimplexNoise.noise3((z+72341f)/VEIN_HSCALE, y/(double)BLOB_VSCALE, x/VEIN_HSCALE));
        float v3 = Math.abs((float)SimplexNoise.noise3((x+913877f)/(VEIN_HSCALE*0.6f), y/(double)BLOB_VSCALE, (z+413171f)/(VEIN_HSCALE*0.6f)));
        return (v1+v2+v3)/3f < VEIN_CARVE;
    }

    // =========================================================================
    // ChunkGenerator boilerplate
    // =========================================================================

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level,
                             @NotNull RandomState random) {
        return computeSurfaceY(x, z);
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z,
                                              @NotNull LevelHeightAccessor level,
                                              @NotNull RandomState random) {
        int minY    = level.getMinY();
        int surface = computeSurfaceY(x, z);
        int sea     = getSeaLevel();
        BlockState[] states = new BlockState[level.getHeight()];
        for (int i = 0; i < states.length; i++) {
            int y = minY + i;
            states[i] = y <= surface ? Blocks.STONE.defaultBlockState()
                    : y <= sea     ? settings.value().defaultFluid()
                      : Blocks.AIR.defaultBlockState();
        }
        return new NoiseColumn(minY, states);
    }

    @Override
    public void applyCarvers(@NotNull WorldGenRegion region, long seed,
                             @NotNull RandomState random, @NotNull BiomeManager biomeManager,
                             @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {
        vanilla.applyCarvers(region, seed, random, biomeManager, structures, chunk);
    }

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion region) {
        ChunkPos pos = region.getCenter();
        Holder<Biome> b = region.getBiome(pos.getWorldPosition().atY(region.getMaxY() - 1));
        WorldgenRandom rand = new WorldgenRandom(RandomSource.create());
        rand.setDecorationSeed(region.getSeed(), pos.getMinBlockX(), pos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(region, b, pos, rand);
    }

    @Override public int getSeaLevel()  { return SEA_LEVEL; }
    @Override public int getMinY()      { return settings.value().noiseSettings().minY(); }
    @Override public int getGenDepth()  { return settings.value().noiseSettings().height(); }

    @Override
    public void addDebugScreenInfo(java.util.List<String> info, RandomState random, BlockPos pos) {
        if (!BiomemapLoader.isLoaded()) return;
        float cx = pos.getX() / (float)BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float cz = pos.getZ() / (float)BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;
        GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(
                BiomemapLoader.getRawPixel(Math.round(cx), Math.round(cz)));
        int   surfY      = computeSurfaceY(pos.getX(), pos.getZ());
        info.add(String.format(
                "[GoT] Y=%d base=%.0f var=%.1f %s px=(%.1f,%.1f)",
                surfY, p.baseY(), p.scale(),
                p.isWater() ? (p.isRiver() ? "RIVER" : "WATER") : "land",
                cx, cz));
    }
}