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
 * Gorble chunk generator.
 *
 * <h3>Terrain pipeline</h3>
 * <ol>
 *   <li>World coordinates are mapped to a floating-point biomemap pixel.</li>
 *   <li>A 4×4 grid of biome parameters ({@code base_height},
 *       {@code height_variation}) is sampled from the biomemap.</li>
 *   <li>Bicubic B-spline interpolation blends {@code base_height} and
 *       {@code height_variation} across the grid, producing smooth organic
 *       biome transitions with C² continuity.</li>
 *   <li>OpenSimplex2 fBm noise scaled by the interpolated
 *       {@code height_variation} is added on top of the interpolated
 *       {@code base_height} to give each biome its characteristic hilliness.</li>
 * </ol>
 *
 * <p>That's it — deliberately minimal for this iteration.
 */
public final class GotChunkGenerator extends ChunkGenerator {

    // ── Constants ──────────────────────────────────────────────────────────

    public static final int SEA_LEVEL = 63;

    /** Noise frequency — smaller = broader features. */
    private static final double NOISE_SCALE_X = 220.0;
    private static final double NOISE_SCALE_Z = 190.0;

    // ── Codec ──────────────────────────────────────────────────────────────

    public static final MapCodec<GotChunkGenerator> CODEC =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    BiomeSource.CODEC
                            .fieldOf("biome_source")
                            .forGetter(ChunkGenerator::getBiomeSource),
                    NoiseGeneratorSettings.CODEC
                            .fieldOf("settings")
                            .forGetter(g -> g.settings),
                    com.mojang.serialization.Codec.INT
                            .optionalFieldOf("spawn_pixel_x", -1)
                            .forGetter(g -> g.spawnPixelX),
                    com.mojang.serialization.Codec.INT
                            .optionalFieldOf("spawn_pixel_z", -1)
                            .forGetter(g -> g.spawnPixelZ)
            ).apply(i, GotChunkGenerator::new));

    // ── Fields ─────────────────────────────────────────────────────────────

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator       vanilla;
    private final int spawnPixelX;
    private final int spawnPixelZ;

    // Cached so GotBiomeSource can read it after construction
    private static volatile int configuredSpawnPixelX = -1;
    private static volatile int configuredSpawnPixelZ = -1;

    // ── Constructor ────────────────────────────────────────────────────────

    public GotChunkGenerator(BiomeSource biomeSource,
                             Holder<NoiseGeneratorSettings> settings,
                             int spawnPixelX, int spawnPixelZ) {
        super(biomeSource);
        this.settings     = settings;
        this.spawnPixelX  = spawnPixelX;
        this.spawnPixelZ  = spawnPixelZ;
        this.vanilla      = new NoiseBasedChunkGenerator(biomeSource, settings);
        configuredSpawnPixelX = spawnPixelX;
        configuredSpawnPixelZ = spawnPixelZ;
    }

    public static int getConfiguredSpawnPixelX() { return configuredSpawnPixelX; }
    public static int getConfiguredSpawnPixelZ() { return configuredSpawnPixelZ; }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

    // ── fillFromNoise ──────────────────────────────────────────────────────

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender, @NotNull RandomState random,
            @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {

        NoiseSettings ns = settings.value().noiseSettings();
        int minY  = ns.minY();
        int maxY  = minY + ns.height();
        int sea   = getSeaLevel();
        ChunkPos cp = chunk.getPos();

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = cp.getBlockX(lx);
                int wz = cp.getBlockZ(lz);
                int surfaceY = computeSurfaceY(wx, wz);

                for (int y = minY; y < maxY; y++) {
                    BlockState state;
                    if (y > surfaceY) {
                        state = y <= sea
                                ? settings.value().defaultFluid()
                                : Blocks.AIR.defaultBlockState();
                    } else {
                        state = Blocks.STONE.defaultBlockState();
                    }
                    chunk.setBlockState(new BlockPos(lx, y, lz), state, false);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    // ── buildSurface — delegated to vanilla ────────────────────────────────

    @Override
    public void buildSurface(@NotNull WorldGenRegion region,
                             @NotNull StructureManager structures,
                             @NotNull RandomState random,
                             @NotNull ChunkAccess chunk) {
        vanilla.buildSurface(region, structures, random, chunk);
    }

    // ── Surface height ─────────────────────────────────────────────────────

    /**
     * Returns the integer surface Y for the given world coordinates.
     */
    public static int computeSurfaceY(int worldX, int worldZ) {
        return Mth.floor(computeRawSurfaceY(worldX, worldZ));
    }

    /**
     * Computes the floating-point surface Y for the given world coordinates.
     *
     * <p>Pipeline:
     * <ol>
     *   <li>World → biomemap pixel coordinates (float).</li>
     *   <li>Sample a 4×4 grid of biome parameters.</li>
     *   <li>Bicubic B-spline blend → interpolated base height and height variation.</li>
     *   <li>OpenSimplex2 fBm × height variation added to base height.</li>
     * </ol>
     */
    public static float computeRawSurfaceY(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;

        // World coordinates → fractional biomemap pixel position
        float cx = worldX / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;

        int   ipx = (int) Math.floor(cx);
        int   ipz = (int) Math.floor(cz);
        float fx  = cx - ipx;   // fractional offset [0, 1)
        float fz  = cz - ipz;

        // Sample a 4×4 grid of parameters centred on (ipx, ipz)
        // Indices: col/row i ∈ {-1, 0, 1, 2}
        float[] h = new float[16];   // base_height values
        float[] v = new float[16];   // height_variation values

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int px = ipx + col - 1;
                int pz = ipz + row - 1;
                GotBiomeTerrainParams.Params p = paramsAt(px, pz);
                h[row * 4 + col] = p.baseHeight();
                v[row * 4 + col] = p.heightVariation();
            }
        }

        // Bicubic B-spline blend of base height and height variation
        float baseHeight      = bicubicBspline(h, fx, fz);
        float heightVariation = bicubicBspline(v, fx, fz);

        // Simplex noise
        double noiseVal = SimplexNoise.noise(
                worldX / NOISE_SCALE_X,
                worldZ / NOISE_SCALE_Z);

        return baseHeight + (float) noiseVal * heightVariation;
    }

    // ── Bicubic B-spline ───────────────────────────────────────────────────

    /**
     * Bicubic B-spline interpolation over a 4×4 flat grid.
     *
     * <p>Uniform cubic B-spline has C² continuity — continuous up to the
     * second derivative — giving rounder, gentler transitions than Catmull-Rom
     * without overshoot at control points.
     *
     * <p>Grid layout (row-major, rows = Z, cols = X):
     * <pre>
     *   index = row*4 + col,   col ∈ {0,1,2,3} = {ipx-1, ipx, ipx+1, ipx+2}
     *                          row ∈ {0,1,2,3} = {ipz-1, ipz, ipz+1, ipz+2}
     * </pre>
     *
     * @param grid 16-element array of values in row-major order
     * @param fx   fractional X offset in [0, 1)
     * @param fz   fractional Z offset in [0, 1)
     */
    private static float bicubicBspline(float[] grid, float fx, float fz) {
        // Interpolate each row along X, then interpolate those four results along Z
        float r0 = cubicBspline1D(grid[0],  grid[1],  grid[2],  grid[3],  fx);
        float r1 = cubicBspline1D(grid[4],  grid[5],  grid[6],  grid[7],  fx);
        float r2 = cubicBspline1D(grid[8],  grid[9],  grid[10], grid[11], fx);
        float r3 = cubicBspline1D(grid[12], grid[13], grid[14], grid[15], fx);
        return cubicBspline1D(r0, r1, r2, r3, fz);
    }

    /**
     * 1D uniform cubic B-spline segment.
     *
     * <p>Basis matrix (×1/6):
     * <pre>
     *  [ -1  3 -3  1 ]
     *  [  3 -6  3  0 ]
     *  [ -3  0  3  0 ]
     *  [  1  4  1  0 ]
     * </pre>
     *
     * @param p0..p3 four control points
     * @param t      parameter in [0, 1)
     */
    private static float cubicBspline1D(float p0, float p1, float p2, float p3, float t) {
        float t2 = t * t;
        float t3 = t2 * t;
        return (1f / 6f) * (
                (-p0 + 3f * p1 - 3f * p2 + p3) * t3
                        + ( 3f * p0 - 6f * p1 + 3f * p2)  * t2
                        + (-3f * p0              + 3f * p2)  * t
                        + (       p0 + 4f * p1  +       p2)
        );
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    /**
     * Returns biome terrain params for a biomemap pixel, clamped to bounds.
     * Returns {@link GotBiomeTerrainParams#FALLBACK} for out-of-bounds pixels.
     */
    private static GotBiomeTerrainParams.Params paramsAt(int px, int pz) {
        if (px < 0 || pz < 0
                || px >= BiomemapLoader.getWidth()
                || pz >= BiomemapLoader.getHeight()) {
            return GotBiomeTerrainParams.FALLBACK;
        }
        return GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
    }

    // ── ChunkGenerator boilerplate ─────────────────────────────────────────

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
                    : y <= sea    ? settings.value().defaultFluid()
                      : Blocks.AIR.defaultBlockState();
        }
        return new NoiseColumn(minY, states);
    }

    @Override
    public void applyCarvers(@NotNull WorldGenRegion region, long seed,
                             @NotNull RandomState random,
                             @NotNull BiomeManager biomeManager,
                             @NotNull StructureManager structures,
                             @NotNull ChunkAccess chunk) {
        vanilla.applyCarvers(region, seed, random, biomeManager, structures, chunk);
    }

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion region) {
        ChunkPos pos = region.getCenter();
        Holder<Biome> b = region.getBiome(
                pos.getWorldPosition().atY(region.getMaxY() - 1));
        WorldgenRandom rand = new WorldgenRandom(RandomSource.create());
        rand.setDecorationSeed(region.getSeed(),
                pos.getMinBlockX(), pos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(region, b, pos, rand);
    }

    @Override public int getSeaLevel() { return SEA_LEVEL; }
    @Override public int getMinY()     { return settings.value().noiseSettings().minY(); }
    @Override public int getGenDepth() { return settings.value().noiseSettings().height(); }

    @Override
    public void addDebugScreenInfo(java.util.List<String> info,
                                   RandomState random, BlockPos pos) {
        if (!BiomemapLoader.isLoaded()) return;

        float cx = pos.getX() / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float cz = pos.getZ() / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;
        int px = Math.round(cx);
        int pz = Math.round(cz);

        GotBiomeTerrainParams.Params p =
                GotBiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
        int surfY = computeSurfaceY(pos.getX(), pos.getZ());

        info.add(String.format(
                "[GoT] Y=%d  base=%.0f  var=%.1f  px=(%d,%d)",
                surfY, p.baseHeight(), p.heightVariation(), px, pz));
    }
}