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
 * Chunk generator for the GoT mod.
 *
 * <p>Pixel-based terrain with Gaussian-blended height transitions between biomes.
 * Each block's height is computed by sampling neighboring pixels in a Gaussian
 * window, blending base heights and scales smoothly across biome boundaries.
 * Rivers and oceans stay where the biomemap says they are, but their edges
 * transition smoothly into land.
 */
public final class GotChunkGenerator extends ChunkGenerator {

    public static final int SEA_LEVEL = 61;

    // ── Gaussian noise/height sampling parameters ─────────────────────────

    private static final int   SAMPLE_RADIUS = 6;
    private static final float GAUSSIAN_SIGMA   = 0.8f;
    private static final float GAUSSIAN_INV_2S2 = 1f / (2f * GAUSSIAN_SIGMA * GAUSSIAN_SIGMA);

    // ── Simplex noise parameters ────────────────────────────────────────────

    private static final float NOISE_FREQ = 1f / 80f;
    private static final int   NOISE_SEED = 0x5EED;

    // ── Codec / vanilla delegate ────────────────────────────────────────────

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator vanilla;

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
                             int spawnPixelX,
                             int spawnPixelZ) {
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

    // ── Block fill ──────────────────────────────────────────────────────────

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender, @NotNull RandomState random,
            @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {

        NoiseSettings noise = settings.value().noiseSettings();
        int minY = noise.minY();
        int maxY = minY + noise.height();
        int sea  = getSeaLevel();
        ChunkPos pos = chunk.getPos();

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = pos.getBlockX(lx);
                int wz = pos.getBlockZ(lz);
                int surfaceY = computeSurfaceY(wx, wz);

                for (int y = minY; y < maxY; y++) {
                    BlockState state;
                    if      (y <= surfaceY) state = Blocks.STONE.defaultBlockState();
                    else if (y <= sea)      state = settings.value().defaultFluid();
                    else                    state = Blocks.AIR.defaultBlockState();
                    chunk.setBlockState(new BlockPos(lx, y, lz), state, false);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public void buildSurface(@NotNull WorldGenRegion region, @NotNull StructureManager structures,
                             @NotNull RandomState random, @NotNull ChunkAccess chunk) {
        vanilla.buildSurface(region, structures, random, chunk);
    }

    // ── Surface Y computation ─────────────────────────────────────────────

    /**
     * Computes terrain surface Y at (worldX, worldZ) with Gaussian-blended
     * biome height transitions.
     *
     * <p>GAUSSIAN BLENDING APPROACH:
     * <ol>
     *   <li>Sample all pixels within a Gaussian window around the target point.</li>
     *   <li>Blend base heights using Gaussian weights — smooth transitions at biome edges.</li>
     *   <li>Blend noise scales using Gaussian weights — consistent terrain roughness.</li>
     *   <li>Apply Simplex noise on top of the blended base height.</li>
     *   <li>Water biomes (rivers, oceans, lakes) are blended separately so their
     *       beds transition smoothly into land, but water surface stays at sea level.</li>
     * </ol>
     */
    public static int computeSurfaceY(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;

        float rawCx = worldX / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float rawCz = worldZ / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;

        int icx = (int) Math.floor(rawCx);
        int icz = (int) Math.floor(rawCz);

        // ── Gaussian window sampling ──────────────────────────────────────
        // Blend base heights and scales from all nearby pixels
        float blendedBaseY   = 0f;
        float blendedScale   = 0f;
        float waterWeight    = 0f;
        float landWeight     = 0f;
        float totalWeight    = 0f;

        for (int dx = -SAMPLE_RADIUS; dx <= SAMPLE_RADIUS; dx++) {
            for (int dz = -SAMPLE_RADIUS; dz <= SAMPLE_RADIUS; dz++) {
                int color = BiomemapLoader.getRawPixel(icx + dx, icz + dz);
                GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(color);

                float ddx   = (icx + dx) - rawCx;
                float ddz   = (icz + dz) - rawCz;
                float dist2 = ddx * ddx + ddz * ddz;
                float w = (float) Math.exp(-dist2 * GAUSSIAN_INV_2S2);

                totalWeight  += w;
                blendedBaseY += p.baseY() * w;
                blendedScale += p.scale() * w;

                if (p.isWater()) {
                    waterWeight += w;
                } else {
                    landWeight += w;
                }
            }
        }

        if (totalWeight <= 0f) {
            return SEA_LEVEL;
        }

        float avgBaseY = blendedBaseY / totalWeight;
        float avgScale = blendedScale / totalWeight;

        // ── Water/land mix factor ─────────────────────────────────────────
        // Determines how "watery" this location is (0 = pure land, 1 = pure water)
        float waterMix = waterWeight / totalWeight;

        // ── Noise ─────────────────────────────────────────────────────────
        float n = simplexNoise(worldX * NOISE_FREQ, worldZ * NOISE_FREQ, NOISE_SEED);

        // ── Height computation with blended transitions ─────────────────
        if (waterMix > 0.85f) {
            // Deep water: river/ocean bed with small noise variation
            float waterBed = SEA_LEVEL - 3f;
            return Mth.floor(waterBed + n * avgScale * 0.2f);
        } else if (waterMix > 0.15f) {
            // Transition zone: blend between land and water heights
            // Smooth interpolation factor (0 at 15% water, 1 at 85% water)
            float t = (waterMix - 0.15f) / 0.70f;
            // Land height from blended base + noise
            float landHeight = avgBaseY + n * avgScale;
            landHeight = Math.max(landHeight, SEA_LEVEL);
            // Water bed height
            float waterBed = SEA_LEVEL - 3f + n * avgScale * 0.2f;
            // Smooth blend between the two
            float blendedHeight = Mth.lerp(t, landHeight, waterBed);
            return Mth.floor(blendedHeight);
        } else {
            // Pure land: blended base height + noise, clamped above sea level
            float landHeight = avgBaseY + n * avgScale;
            return Mth.floor(Math.max(landHeight, SEA_LEVEL));
        }
    }

    // ── Simplex noise (2D) ────────────────────────────────────────────────

    private static final int[] PERM = new int[512];
    private static final int[] PERM_MOD = new int[512];
    private static final float F2 = 0.5f * ((float) Math.sqrt(3.0) - 1.0f);
    private static final float G2 = (3.0f - (float) Math.sqrt(3.0)) / 6.0f;

    static {
        int[] p = new int[256];
        java.util.Random rand = new java.util.Random(NOISE_SEED);
        for (int i = 0; i < 256; i++) p[i] = i;
        for (int i = 255; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int tmp = p[i]; p[i] = p[j]; p[j] = tmp;
        }
        for (int i = 0; i < 512; i++) {
            PERM[i] = p[i & 255];
            PERM_MOD[i] = PERM[i] % 12;
        }
    }

    private static final float[] GRAD3 = {
            1f,  1f,  0f,   -1f,  1f,  0f,    1f, -1f,  0f,   -1f, -1f,  0f,
            1f,  0f,  1f,   -1f,  0f,  1f,    1f,  0f, -1f,   -1f,  0f, -1f,
            0f,  1f,  1f,    0f, -1f,  1f,    0f,  1f, -1f,    0f, -1f, -1f
    };

    private static float simplexNoise(float xin, float zin, int seed) {
        float s = (xin + zin) * F2;
        int i = fastFloor(xin + s);
        int j = fastFloor(zin + s);
        float t = (i + j) * G2;
        float X0 = i - t;
        float Z0 = j - t;
        float x0 = xin - X0;
        float z0 = zin - Z0;

        int i1, j1;
        if (x0 > z0) { i1 = 1; j1 = 0; }
        else         { i1 = 0; j1 = 1; }

        float x1 = x0 - i1 + G2;
        float z1 = z0 - j1 + G2;
        float x2 = x0 - 1.0f + 2.0f * G2;
        float z2 = z0 - 1.0f + 2.0f * G2;

        int ii = i & 255;
        int jj = j & 255;
        int gi0 = PERM_MOD[ii + PERM[jj]];
        int gi1 = PERM_MOD[ii + i1 + PERM[jj + j1]];
        int gi2 = PERM_MOD[ii + 1 + PERM[jj + 1]];

        float n0 = 0f, n1 = 0f, n2 = 0f;
        float t0 = 0.5f - x0 * x0 - z0 * z0;
        if (t0 >= 0f) {
            t0 *= t0;
            n0 = t0 * t0 * dot(GRAD3, gi0 * 3, x0, z0);
        }
        float t1 = 0.5f - x1 * x1 - z1 * z1;
        if (t1 >= 0f) {
            t1 *= t1;
            n1 = t1 * t1 * dot(GRAD3, gi1 * 3, x1, z1);
        }
        float t2 = 0.5f - x2 * x2 - z2 * z2;
        if (t2 >= 0f) {
            t2 *= t2;
            n2 = t2 * t2 * dot(GRAD3, gi2 * 3, x2, z2);
        }

        return 70.0f * (n0 + n1 + n2);
    }

    private static int fastFloor(float x) {
        int xi = (int) x;
        return x < xi ? xi - 1 : xi;
    }

    private static float dot(float[] g, int off, float x, float z) {
        return g[off] * x + g[off + 2] * z;
    }

    // ── ChunkGenerator boilerplate ──────────────────────────────────────────

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
            if      (y <= surface) states[i] = Blocks.STONE.defaultBlockState();
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

    @Override
    public void addDebugScreenInfo(java.util.List<String> info,
                                   RandomState random, BlockPos pos) {
        if (!BiomemapLoader.isLoaded()) return;

        int cx = Math.round(pos.getX() / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f);
        int cz = Math.round(pos.getZ() / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f);
        int color = BiomemapLoader.getRawPixel(cx, cz);
        GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(color);
        int surfY = computeSurfaceY(pos.getX(), pos.getZ());

        info.add(String.format(
                "[GoT] Y=%d  base=%.0f  scale=%.2f  %s  sea=%d  px=(%d,%d)",
                surfY, p.baseY(), p.scale(), p.isWater() ? "WATER" : "land",
                SEA_LEVEL, cx, cz));
    }
}