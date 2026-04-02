package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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
 * Chunk generator for the GoT mod — cell-based 3D density field with Perlin noise.
 *
 * <h2>Algorithm</h2>
 *
 * <h3>Step 1 — Biome blend at cell corners (bilinear)</h3>
 * <p>For each XZ column corner in the cell grid, compute a float pixel-space
 * coordinate and bilinearly interpolate the {@code depth} and {@code scale}
 * values from the four surrounding biomemap pixels.  This is performed once per
 * corner (every {@value CELL_H} blocks in XZ) rather than per block.
 *
 * <h3>Step 2 — Density at cell corners</h3>
 * <p>At every (x, y, z) cell corner evaluate:
 * <pre>
 *   density = (blendedDepth − y) + fbm3D(x, y, z) × blendedScale
 * </pre>
 * The first term is a vertical gradient; positive below the nominal surface,
 * negative above.  The Perlin fBm term bends this gradient, creating overhangs,
 * peaks, and undercuts wherever the noise is strong enough.
 *
 * <h3>Step 3 — Trilinear interpolation within cells</h3>
 * <p>Each block samples the density of its enclosing cell's 8 corners via
 * trilinear interpolation.  Because the Perlin noise is already C² continuous,
 * linear interpolation between corners is sufficient and avoids seam artefacts
 * at cell boundaries.  Cells are {@value CELL_H}×{@value CELL_V}×{@value CELL_H}
 * blocks (XZ × Y × XZ), giving a good balance between smoothness and detail.
 *
 * <h3>Step 4 — Block assignment</h3>
 * <ul>
 *   <li>{@code density > 0} → STONE</li>
 *   <li>{@code density ≤ 0 && y ≤ SEA_LEVEL} → default fluid</li>
 *   <li>otherwise → AIR</li>
 * </ul>
 *
 * <h2>Noise architecture</h2>
 * <p>Three-octave fBm via {@link GotPerlinNoise#fbm}.  The Y coordinate is
 * fixed at 0 inside the noise call so the field is purely horizontal.
 * Because noise is constant along the Y axis for any (X, Z) column the
 * density function is strictly monotonically decreasing with altitude —
 * guaranteeing solid, overhang-free terrain with no floating islands.
 * Mountain shapes come entirely from horizontal frequency content.
 *
 * <pre>
 *   Coarse  H = 1/256   broad landscape undulation
 *   Mid     H = 1/102   hill-scale bumps and ridges
 *   Fine    H = 1/ 43   surface roughness and ledge detail
 *
 *   (internally driven by GotPerlinNoise.fbm — coarse × 0.50, mid × 0.35, fine × 0.15)
 * </pre>
 *
 * <h2>Biome sync</h2>
 * <p>{@link GotBiomeSource} performs the same bilinear 4-pixel lookup and picks
 * the dominant biome by weight, so biome boundaries are guaranteed to follow the
 * same pixel-space interpolation as the terrain depth/scale blend.
 */
public final class GotChunkGenerator extends ChunkGenerator {

    public static final int SEA_LEVEL = 61;

    // ── Cell grid dimensions ──────────────────────────────────────────────
    //
    // Density is evaluated at a 5 × (rows+1) × 5 lattice of cell corners
    // and trilinearly interpolated within each CELL_H × CELL_V × CELL_H cell.
    //
    // CELL_H = 4 → 4 complete XZ cells per chunk axis (16 / 4 = 4).
    //              Corner count per axis: 4 + 1 = 5.
    // CELL_V = 8 → cells are taller than wide to match the vertical scale of
    //              terrain features, reducing cell count in the most expensive
    //              axis without losing significant detail.

    private static final int CELL_H = 4;   // horizontal cell size in blocks
    private static final int CELL_V = 8;   // vertical   cell size in blocks

    // ── Perlin noise frequencies ──────────────────────────────────────────
    //
    // FREQ_H is applied to world X and Z only.
    //
    // The Y axis is intentionally excluded from the noise evaluation so that
    // the density function is strictly monotonically decreasing with altitude.
    // Passing a varying Y coordinate into the noise caused the density to
    // oscillate as altitude increases, producing overhangs and floating
    // islands in mountain biomes.  With Y fixed at 0 the noise is a pure 2D
    // horizontal field: at any given (X, Z) column, density = (depth - Y) + C
    // where C is a constant in [-scale, scale], which is guaranteed to cross
    // zero exactly once and never reverse — giving solid, overhang-free peaks.
    //
    // These are passed into GotPerlinNoise.fbm which internally applies
    // sub-octave multipliers of 2.5× and 6.0×.

    private static final float FREQ_H = 1f / 256f;  // horizontal base frequency

    // Base seed constant — XOR'd with the world seed at runtime so every
    // world/save produces different terrain within the same biome layout.
    private static final int SEED_TERRAIN = 0x3F9A1B;

    // ── Codec ─────────────────────────────────────────────────────────────

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

    // ── Fields ────────────────────────────────────────────────────────────

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator vanilla;
    private final int spawnPixelX;
    private final int spawnPixelZ;

    /** Mixed into the noise seed so every world seed produces different terrain. */
    private volatile int noiseSeed = SEED_TERRAIN;

    /**
     * Shared copy of {@link #noiseSeed} written on first chunk generation and
     * readable by {@link GotBiomeSource} so its Y-aware density check uses the
     * same seed as the terrain — keeping biomes block-for-block in sync.
     *
     * <p>Initialised to {@link #SEED_TERRAIN} (the same default the instance
     * field uses) so that biome queries issued before the first chunk generate
     * still agree with the terrain once it is produced.
     */
    static volatile int sharedNoiseSeed = SEED_TERRAIN;

    private static int configuredSpawnPixelX = -1;
    private static int configuredSpawnPixelZ = -1;

    // ── Constructor ───────────────────────────────────────────────────────

    public GotChunkGenerator(BiomeSource biomeSource,
                             Holder<NoiseGeneratorSettings> settings,
                             int spawnPixelX,
                             int spawnPixelZ) {
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

    // ── Terrain generation ────────────────────────────────────────────────

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender, @NotNull RandomState random,
            @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {

        if (!BiomemapLoader.isLoaded()) return fillFlat(chunk);

        // Derive a per-world noise seed from the level seed the first time we
        // generate a chunk.  This is what makes different world seeds produce
        // different terrain shapes within the same biome layout.
        if (noiseSeed == SEED_TERRAIN) {
            long s = random.getOrCreateRandomFactory(
                            ResourceLocation.fromNamespaceAndPath("got", "terrain_seed"))
                    .at(BlockPos.ZERO).nextLong();
            noiseSeed = SEED_TERRAIN ^ (int)(s ^ (s >>> 32));
            sharedNoiseSeed = noiseSeed;   // visible to GotBiomeSource
        }

        NoiseSettings ns = settings.value().noiseSettings();
        int minY = ns.minY();
        int maxY = minY + ns.height();
        int sea  = getSeaLevel();
        ChunkPos pos = chunk.getPos();
        int chunkX = pos.getMinBlockX();
        int chunkZ = pos.getMinBlockZ();

        // ── Build the cell-corner density grid ────────────────────────────
        //
        // cornerDensity[cx][cz][cy] is the density at world position:
        //   (chunkX + cx*CELL_H,  minY + cy*CELL_V,  chunkZ + cz*CELL_H)
        //
        // The grid is (COLS × COLS × ROWS) where:
        //   COLS = 16/CELL_H + 1 = 5
        //   ROWS = ceil((maxY-minY) / CELL_V) + 1  (one extra to cover partial top cell)

        int COLS     = 16 / CELL_H + 1;                                // 5
        int ROWS     = (maxY - minY + CELL_V - 1) / CELL_V + 1;

        float[][][] cornerDensity = new float[COLS][COLS][ROWS];

        for (int cx = 0; cx < COLS; cx++) {
            for (int cz = 0; cz < COLS; cz++) {
                int wx = chunkX + cx * CELL_H;
                int wz = chunkZ + cz * CELL_H;

                // Bilinear blend: one call per XZ corner, not per block.
                float[] bp    = bilinearBlend(wx, wz);
                float   depth = bp[0];
                float   scale = bp[1];

                for (int cy = 0; cy < ROWS; cy++) {
                    int wy = minY + cy * CELL_V;
                    cornerDensity[cx][cz][cy] = cornerDensity(wx, wy, wz, depth, scale, noiseSeed);
                }
            }
        }

        // ── Trilinearly interpolate and fill blocks ────────────────────────

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int cellX = lx / CELL_H;          // XZ cell index (0-3)
                int cellZ = lz / CELL_H;
                float tx = (lx % CELL_H) / (float) CELL_H;   // fraction within cell
                float tz = (lz % CELL_H) / (float) CELL_H;

                for (int y = minY; y < maxY; y++) {
                    int   cellY = (y - minY) / CELL_V;
                    float ty    = ((y - minY) % CELL_V) / (float) CELL_V;

                    // Trilinear interpolation of the 8 surrounding corner densities.
                    float density = trilinear(tx, ty, tz,
                            cornerDensity[cellX    ][cellZ    ][cellY    ],
                            cornerDensity[cellX + 1][cellZ    ][cellY    ],
                            cornerDensity[cellX    ][cellZ + 1][cellY    ],
                            cornerDensity[cellX + 1][cellZ + 1][cellY    ],
                            cornerDensity[cellX    ][cellZ    ][cellY + 1],
                            cornerDensity[cellX + 1][cellZ    ][cellY + 1],
                            cornerDensity[cellX    ][cellZ + 1][cellY + 1],
                            cornerDensity[cellX + 1][cellZ + 1][cellY + 1]);

                    BlockState state;
                    if (density > 0f) {
                        state = Blocks.STONE.defaultBlockState();
                    } else if (y <= sea) {
                        state = settings.value().defaultFluid();
                    } else {
                        state = Blocks.AIR.defaultBlockState();
                    }

                    chunk.setBlockState(new BlockPos(lx, y, lz), state, false);
                }
            }
        }

        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public void buildSurface(@NotNull WorldGenRegion region,
                             @NotNull StructureManager structures,
                             @NotNull RandomState random,
                             @NotNull ChunkAccess chunk) {
        vanilla.buildSurface(region, structures, random, chunk);
    }

    // ── Density evaluation ────────────────────────────────────────────────

    /**
     * Evaluates the signed density at a cell corner.
     *
     * <pre>
     *   density = (blendedDepth − worldY) + fbm3D(worldX, worldY, worldZ) × blendedScale
     * </pre>
     *
     * <ul>
     *   <li>Positive → solid (STONE)</li>
     *   <li>Negative → empty (fluid if {@code y ≤ SEA_LEVEL}, else AIR)</li>
     * </ul>
     *
     * @param depth blended baseline surface Y from the biomemap
     * @param scale blended noise amplitude in blocks
     */
    private static float cornerDensity(int wx, int wy, int wz,
                                       float depth, float scale, int seed) {
        float gradient = depth - wy;
        // Y is fixed at 0 — purely horizontal noise — preserving the
        // no-overhang guarantee.  The seed varies per world so mountains,
        // plains, etc. all look different in every new world.
        float noise = GotPerlinNoise.fbm(
                wx * FREQ_H,
                0f,
                wz * FREQ_H,
                seed);
        return gradient + noise * scale;
    }

    // ── Package-private API used by GotBiomeSource ────────────────────────

    /**
     * Evaluates the signed density at an arbitrary world position using the
     * same formula as {@link #cornerDensity} but reading {@link #sharedNoiseSeed}
     * so that {@link GotBiomeSource} can reproduce the terrain generator's exact
     * solid/open decision for every noise cell.
     *
     * <p>Called once per noise cell (4 × 4 × 4 blocks) that is at or below sea
     * level, so the extra cost is negligible compared with full chunk generation.
     */
    static float evalDensity(int wx, int wy, int wz, float depth, float scale) {
        return cornerDensity(wx, wy, wz, depth, scale, sharedNoiseSeed);
    }

    // ── Biomemap bilinear blend ───────────────────────────────────────────

    /**
     * Bilinearly blends the {@code depth} and {@code scale} terrain parameters
     * from the four biomemap pixels surrounding the world position (wx, wz).
     *
     * <p>The biomemap is a regular integer pixel grid.  Converting (wx, wz) to
     * float pixel-space gives a sub-pixel position {@code (cx, cz)}.  The four
     * pixels at {@code (⌊cx⌋, ⌊cz⌋)}, {@code (⌊cx⌋+1, ⌊cz⌋)}, etc. are each
     * weighted by their bilinear proximity ({@code (1-tx)(1-tz)} and so on).
     * Each weight is between 0 and 1; the four weights sum to exactly 1.
     *
     * <p>This replaces the old 7×7 Gaussian kernel: bilinear is exact on the
     * pixel grid (Gaussian over-smoothed), is faster (4 samples vs 49), and
     * matches the bilinear lookup that {@link GotBiomeSource} uses — so the
     * blended terrain parameters and the biome boundaries come from the same
     * mathematical operation.
     *
     * @return float[2] { blendedDepth, blendedScale }
     */
    static float sharpenBlend(float t) {
        // First smoothstep pass
        t = t * t * (3f - 2f * t);
        // Second smoothstep pass — steeper plateau + sharper transition zone
        t = t * t * (3f - 2f * t);
        return t;
    }

// ─── REPLACE the existing bilinearBlend() method with this version ───
//     (only change: sharpenBlend() applied to tx and tz)

    static float[] bilinearBlend(int wx, int wz) {
        float cx = wx / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float cz = wz / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;

        int   px0 = (int) Math.floor(cx);
        int   pz0 = (int) Math.floor(cz);
        // ↓ sharpen: replaces the old linear (cx - px0) / (cz - pz0) fractions
        float tx  = sharpenBlend(cx - px0);
        float tz  = sharpenBlend(cz - pz0);

        GotBiomeDensityParams.Params p00 = GotBiomeDensityParams.forColor(BiomemapLoader.getRawPixel(px0,     pz0));
        GotBiomeDensityParams.Params p10 = GotBiomeDensityParams.forColor(BiomemapLoader.getRawPixel(px0 + 1, pz0));
        GotBiomeDensityParams.Params p01 = GotBiomeDensityParams.forColor(BiomemapLoader.getRawPixel(px0,     pz0 + 1));
        GotBiomeDensityParams.Params p11 = GotBiomeDensityParams.forColor(BiomemapLoader.getRawPixel(px0 + 1, pz0 + 1));

        float w00 = (1f - tx) * (1f - tz);
        float w10 = tx        * (1f - tz);
        float w01 = (1f - tx) * tz;
        float w11 = tx        * tz;

        float depth = p00.depth * w00 + p10.depth * w10 + p01.depth * w01 + p11.depth * w11;
        float scale = p00.scale * w00 + p10.scale * w10 + p01.scale * w01 + p11.scale * w11;

        return new float[]{ depth, scale };
    }

    // ── Interpolation helpers ─────────────────────────────────────────────

    /**
     * Trilinear interpolation of a density value within a cell.
     *
     * <p>Corners are labelled by which face of the cell they belong to:
     * first index = X side (0 = near, 1 = far), second = Z, third = Y.
     *
     * @param tx fraction in X (0 = near corner, 1 = far corner)
     * @param ty fraction in Y (0 = bottom, 1 = top)
     * @param tz fraction in Z
     */
    private static float trilinear(float tx, float ty, float tz,
                                   float d000, float d100,
                                   float d010, float d110,
                                   float d001, float d101,
                                   float d011, float d111) {
        float x0 = lerp(tx, d000, d100);
        float x1 = lerp(tx, d010, d110);
        float x2 = lerp(tx, d001, d101);
        float x3 = lerp(tx, d011, d111);
        float z0  = lerp(tz, x0, x1);
        float z1  = lerp(tz, x2, x3);
        return lerp(ty, z0, z1);
    }

    private static float lerp(float t, float a, float b) { return a + t * (b - a); }

    // ── ChunkGenerator boilerplate ────────────────────────────────────────

    /**
     * Estimates the surface Y for structure placement and heightmaps.
     * The blended {@code depth} is the expected zero-crossing of the density
     * function (noise averages to 0), so it is a good approximation of the surface.
     */
    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level,
                             @NotNull RandomState random) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;
        float[] bp = bilinearBlend(x, z);
        return Mth.clamp(Mth.floor(bp[0]), level.getMinY(), level.getMaxY());
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z,
                                              @NotNull LevelHeightAccessor level,
                                              @NotNull RandomState random) {
        int   minY  = level.getMinY();
        int   sea   = getSeaLevel();
        float[] bp  = BiomemapLoader.isLoaded() ? bilinearBlend(x, z)
                : new float[]{ SEA_LEVEL, 10f };
        float depth = bp[0];
        float scale = bp[1];

        BlockState[] states = new BlockState[level.getHeight()];
        for (int i = 0; i < states.length; i++) {
            int   y       = minY + i;
            float density = cornerDensity(x, y, z, depth, scale, noiseSeed);
            if      (density > 0f) states[i] = Blocks.STONE.defaultBlockState();
            else if (y <= sea)     states[i] = settings.value().defaultFluid();
            else                   states[i] = Blocks.AIR.defaultBlockState();
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
        Holder<Biome> b = region.getBiome(pos.getWorldPosition().atY(region.getMaxY() - 1));
        WorldgenRandom rand = new WorldgenRandom(RandomSource.create());
        rand.setDecorationSeed(region.getSeed(), pos.getMinBlockX(), pos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(region, b, pos, rand);
    }

    @Override public int getSeaLevel()  { return SEA_LEVEL; }
    @Override public int getMinY()      { return settings.value().noiseSettings().minY(); }
    @Override public int getGenDepth()  { return settings.value().noiseSettings().height(); }

    // ── Debug ─────────────────────────────────────────────────────────────

    @Override
    public void addDebugScreenInfo(java.util.List<String> info,
                                   RandomState random, BlockPos pos) {
        if (!BiomemapLoader.isLoaded()) return;
        int wx = pos.getX(), wy = pos.getY(), wz = pos.getZ();
        float[] bp  = bilinearBlend(wx, wz);
        float density = cornerDensity(wx, wy, wz, bp[0], bp[1], noiseSeed);
        int[] px = BiomemapLoader.getPixelForWorld(wx, wz);
        info.add(String.format(
                "[GoT] px=(%d,%d)  depth=%.1f  scale=%.1f  density@Y%d=%.2f  sea=%d",
                px[0], px[1], bp[0], bp[1], wy, density, SEA_LEVEL));
    }

    // ── Fallback flat fill (pre-load) ─────────────────────────────────────

    /** Flat sea-level fill used before the biomemap is loaded. */
    private CompletableFuture<ChunkAccess> fillFlat(ChunkAccess chunk) {
        NoiseSettings ns  = settings.value().noiseSettings();
        int minY = ns.minY();
        int maxY = minY + ns.height();
        int sea  = getSeaLevel();
        ChunkPos pos = chunk.getPos();
        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                for (int y = minY; y < maxY; y++) {
                    BlockState state;
                    if      (y < sea)  state = Blocks.STONE.defaultBlockState();
                    else if (y == sea) state = settings.value().defaultFluid();
                    else               state = Blocks.AIR.defaultBlockState();
                    chunk.setBlockState(new BlockPos(lx, y, lz), state, false);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }
}