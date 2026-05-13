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

public final class GotChunkGenerator extends ChunkGenerator {

    // ── Constants ──────────────────────────────────────────────────────────

    public static final int SEA_LEVEL = 63;

    /** Base noise frequency. Larger = more zoomed-in features. */
    private static final double NOISE_SCALE_X = 220.0;
    private static final double NOISE_SCALE_Z = 190.0;

    /**
     * fBm settings — slightly rougher than before.
     *
     * 3 octaves: the first gives broad sweeping hills, the second adds
     * gentle undulation, and the third adds a bit of texture on top.
     * Gain raised to 0.35 so the extra detail is slightly more audible.
     */
    private static final int    FBM_OCTAVES    = 3;
    private static final double FBM_LACUNARITY = 2.0;   // each octave doubles frequency
    private static final double FBM_GAIN       = 0.35;  // slightly more detail than before

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

    private static volatile int configuredSpawnPixelX = -1;
    private static volatile int configuredSpawnPixelZ = -1;

    private static volatile SimplexNoise seededNoise = SimplexNoise.seeded(0L);

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

    public static void initNoise(long worldSeed) {
        seededNoise = SimplexNoise.seeded(worldSeed);
    }

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

    // ── buildSurface ────────────────────────────────────────────────────────

    @Override
    public void buildSurface(@NotNull WorldGenRegion region,
                             @NotNull StructureManager structures,
                             @NotNull RandomState random,
                             @NotNull ChunkAccess chunk) {
        vanilla.buildSurface(region, structures, random, chunk);
    }

    // ── Surface height ─────────────────────────────────────────────────────

    public static int computeSurfaceY(int worldX, int worldZ) {
        return Mth.floor(computeRawSurfaceY(worldX, worldZ));
    }

    public static float computeRawSurfaceY(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;

        float cx = worldX / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;

        int   ipx = (int) Math.floor(cx);
        int   ipz = (int) Math.floor(cz);
        float fx  = cx - ipx;
        float fz  = cz - ipz;

        float[] h = new float[16];
        float[] v = new float[16];

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int px = ipx + col - 1;
                int pz = ipz + row - 1;
                GotBiomeTerrainParams.Params p = paramsAt(px, pz);
                // Only amplify terrain that is genuinely mountain-level (base > 90).
                // Below that threshold the height is passed through untouched so
                // plains/hills are completely unaffected. Exponent 1.15 gives a
                // subtle steepening at mountain edges without being too aggressive.
                float bh = p.baseHeight();
                if (bh > 90f) {
                    float aboveMtn = bh - 90f;
                    bh = (float) Math.pow(aboveMtn, 1.15) + 90f;
                }
                h[row * 4 + col] = bh;
                v[row * 4 + col] = p.heightVariation();
            }
        }

        float baseHeight      = bicubicBspline(h, fx, fz);
        float heightVariation = bicubicBspline(v, fx, fz);

        double noiseVal = seededNoise.fbm(
                worldX / NOISE_SCALE_X,
                worldZ / NOISE_SCALE_Z,
                FBM_OCTAVES,
                FBM_LACUNARITY,
                FBM_GAIN);

        return baseHeight + (float) noiseVal * heightVariation;
    }

    // ── Bicubic B-spline ───────────────────────────────────────────────────

    private static float bicubicBspline(float[] grid, float fx, float fz) {
        float r0 = cubicBspline1D(grid[0],  grid[1],  grid[2],  grid[3],  fx);
        float r1 = cubicBspline1D(grid[4],  grid[5],  grid[6],  grid[7],  fx);
        float r2 = cubicBspline1D(grid[8],  grid[9],  grid[10], grid[11], fx);
        float r3 = cubicBspline1D(grid[12], grid[13], grid[14], grid[15], fx);
        return cubicBspline1D(r0, r1, r2, r3, fz);
    }

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
                    : y <= sea       ? settings.value().defaultFluid()
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