package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * LOTR-inspired chunk generator.
 *
 * Terrain height is driven entirely by biome map data:
 *   - Each biome color has a base_height and height_variation.
 *   - Bilinear interpolation is used between grid points for smooth transitions.
 *   - Two octaves of Perlin-simplex noise add detail on top.
 *
 * HeightmapLoader is NOT used at all.
 */
public final class GotChunkGenerator extends ChunkGenerator {

    /* ============================================================= */
    /* CODEC                                                         */
    /* ============================================================= */

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator vanilla;

    public static final MapCodec<GotChunkGenerator> CODEC =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    BiomeSource.CODEC.fieldOf("biome_source")
                            .forGetter(ChunkGenerator::getBiomeSource),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings")
                            .forGetter(g -> g.settings)
            ).apply(i, GotChunkGenerator::new));

    public GotChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
        super(biomeSource);
        this.settings = settings;
        this.vanilla  = new NoiseBasedChunkGenerator(biomeSource, settings);
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

    /* ============================================================= */
    /* NOISE (lazy init)                                             */
    /* ============================================================= */

    private PerlinSimplexNoise continentNoise;
    private PerlinSimplexNoise detailNoise;

    /** Transition size for bilinear interpolation between biome values. */
    private static final int TRANSITION_SIZE = 32; // This can be configurable later
    
    private static double smoothStep(double t) {
        return t * t * (3 - 2 * t);
    }
    
    private double getValueWithTransition(int x, int y, java.util.function.Function<Double, Double> function) {
        // Determine the base coordinates for the current grid
        int baseX = (x / TRANSITION_SIZE) * TRANSITION_SIZE;
        int baseY = (y / TRANSITION_SIZE) * TRANSITION_SIZE;
    
        // Adjust base coordinates for negative values
        if (x < 0) baseX -= TRANSITION_SIZE;
        if (y < 0) baseY -= TRANSITION_SIZE;
    
        // Get biome values at the four corners of the grid
        double value00 = function.apply(BiomeMapLoader.getBaseHeight(baseX, baseY));      // Top-left
        double value10 = function.apply(BiomeMapLoader.getBaseHeight(baseX + TRANSITION_SIZE, baseY));      // Top-right
        double value01 = function.apply(BiomeMapLoader.getBaseHeight(baseX, baseY + TRANSITION_SIZE));      // Bottom-left
        double value11 = function.apply(BiomeMapLoader.getBaseHeight(baseX + TRANSITION_SIZE, baseY + TRANSITION_SIZE)); // Bottom-right
    
        // Calculate the fractional positions within the grid, relative to base coordinates
        double xPercent = (double) (x - baseX) / TRANSITION_SIZE;
        double yPercent = (double) (y - baseY) / TRANSITION_SIZE;
    
        // Ensure fractional values are within [0, 1] (handling negative values)
        xPercent = Math.abs(xPercent);
        yPercent = Math.abs(yPercent);
    
        // Introduce cubic-like transitions based on weight differences
        xPercent = smoothStep(xPercent);
        yPercent = smoothStep(yPercent);
    
        // Calculate bi-linear interpolation
        return (value00 * (1 - xPercent) * (1 - yPercent)) +
                (value10 * xPercent * (1 - yPercent)) +
                (value01 * (1 - xPercent) * yPercent) +
                (value11 * xPercent * yPercent);
    }
    
    private double getValueWithTransitionForVariation(int x, int y) {
        // Determine the base coordinates for the current grid
        int baseX = (x / TRANSITION_SIZE) * TRANSITION_SIZE;
        int baseY = (y / TRANSITION_SIZE) * TRANSITION_SIZE;
    
        // Adjust base coordinates for negative values
        if (x < 0) baseX -= TRANSITION_SIZE;
        if (y < 0) baseY -= TRANSITION_SIZE;
    
        // Get biome variation values at the four corners of the grid
        double value00 = BiomeMapLoader.getHeightVariation(baseX, baseY);      // Top-left
        double value10 = BiomeMapLoader.getHeightVariation(baseX + TRANSITION_SIZE, baseY);      // Top-right
        double value01 = BiomeMapLoader.getHeightVariation(baseX, baseY + TRANSITION_SIZE);      // Bottom-left
        double value11 = BiomeMapLoader.getHeightVariation(baseX + TRANSITION_SIZE, baseY + TRANSITION_SIZE); // Bottom-right
    
        // Calculate the fractional positions within the grid, relative to base coordinates
        double xPercent = (double) (x - baseX) / TRANSITION_SIZE;
        double yPercent = (double) (y - baseY) / TRANSITION_SIZE;
    
        // Ensure fractional values are within [0, 1] (handling negative values)
        xPercent = Math.abs(xPercent);
        yPercent = Math.abs(yPercent);
    
        // Introduce cubic-like transitions based on weight differences
        xPercent = smoothStep(xPercent);
        yPercent = smoothStep(yPercent);
    
        // Calculate bi-linear interpolation
        return (value00 * (1 - xPercent) * (1 - yPercent)) +
                (value10 * xPercent * (1 - yPercent)) +
                (value01 * (1 - xPercent) * yPercent) +
                (value11 * xPercent * yPercent);
    }

    private void ensureNoise(RandomState state) {
        if (continentNoise != null) return;
        RandomSource rand = state.getOrCreateRandomFactory(
                ResourceLocation.fromNamespaceAndPath("got", "terrain")
        ).at(0, 0, 0);
        continentNoise = new PerlinSimplexNoise(rand, List.of(-6));
        detailNoise    = new PerlinSimplexNoise(rand, List.of(-2));
    }

    /* ============================================================= */
    /* TERRAIN FILL                                                   */
    /* ============================================================= */

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender,
            @NotNull RandomState random,
            @NotNull StructureManager structures,
            @NotNull ChunkAccess chunk
    ) {
        ensureNoise(random);

        NoiseSettings noise = settings.value().noiseSettings();
        int minY = noise.minY();
        int maxY = minY + noise.height();
        int sea  = getSeaLevel();

        ChunkPos pos = chunk.getPos();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int wx = pos.getBlockX(x);
                int wz = pos.getBlockZ(z);
                int h  = computeTerrainHeight(wx, wz);

                for (int y = minY; y < maxY; y++) {
                    BlockState state =
                            y <= h   ? Blocks.STONE.defaultBlockState()
                                    : y <= sea ? settings.value().defaultFluid()
                                    : Blocks.AIR.defaultBlockState();
                    chunk.setBlockState(new BlockPos(x, y, z), state, false);
                }
            }
        }

        return CompletableFuture.completedFuture(chunk);
    }

    /* ============================================================= */
    /* LOTR-STYLE BIOME-BLENDED HEIGHT                               */
    /* ============================================================= */

    /**
     * Use bilinear interpolation to smoothly transition between biome heights,
     * then add two noise octaves for surface detail.
     */
    private int computeTerrainHeight(int wx, int wz) {
        // --- 1. Get base height and variation using bilinear interpolation ---
        double blendedBase = getValueWithTransition(wx, wz, value -> value);
        
        // For height variation, we need to interpolate the variation values
        double blendedVar = getValueWithTransitionForVariation(wx, wz);

        // --- 2. Continent noise (large scale) ---
        double continent = continentNoise.getValue(wx / 160.0, wz / 160.0, false)
                * blendedVar * 0.8;

        // --- 3. Detail noise (small scale) ---
        double detail = detailNoise.getValue(wx / 24.0, wz / 24.0, false)
                * blendedVar * 0.2;

        return Mth.floor(blendedBase + continent + detail);
    }

    /* ============================================================= */
    /* VANILLA DELEGATES                                             */
    /* ============================================================= */

    @Override
    public void applyCarvers(WorldGenRegion region, long seed, RandomState random,
                             BiomeManager biomeManager, StructureManager structures, ChunkAccess chunk) {
        vanilla.applyCarvers(region, seed, random, biomeManager, structures, chunk);
    }

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structures,
                             RandomState random, ChunkAccess chunk) {
        vanilla.buildSurface(region, structures, random, chunk);
    }

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion region) {
        ChunkPos pos = region.getCenter();
        Holder<Biome> biome = region.getBiome(
                pos.getWorldPosition().atY(region.getMaxY() - 1));
        WorldgenRandom rand = new WorldgenRandom(RandomSource.create());
        rand.setDecorationSeed(region.getSeed(), pos.getMinBlockX(), pos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(region, biome, pos, rand);
    }

    /* ============================================================= */
    /* HEIGHT QUERIES (used by structures & features)               */
    /* ============================================================= */

    @Override
    public int getSeaLevel() { return settings.value().seaLevel(); }

    @Override
    public int getMinY() { return settings.value().noiseSettings().minY(); }

    @Override
    public int getGenDepth() { return settings.value().noiseSettings().height(); }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level, @NotNull RandomState random) {
        ensureNoise(random);
        return computeTerrainHeight(x, z);
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z,
                                              @NotNull LevelHeightAccessor level,
                                              @NotNull RandomState random) {
        ensureNoise(random);
        int minY  = level.getMinY();
        int h     = computeTerrainHeight(x, z);
        int sea   = getSeaLevel();
        BlockState[] states = new BlockState[level.getHeight()];
        for (int i = 0; i < states.length; i++) {
            int y = minY + i;
            states[i] = y <= h   ? Blocks.STONE.defaultBlockState()
                    : y <= sea ? settings.value().defaultFluid()
                    : Blocks.AIR.defaultBlockState();
        }
        return new NoiseColumn(minY, states);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState random, BlockPos pos) {
        ensureNoise(random);
        int h = computeTerrainHeight(pos.getX(), pos.getZ());
        info.add("[GoT] terrain_y=" + h
                + " base=" + String.format("%.1f", BiomeMapLoader.getBaseHeight(pos.getX(), pos.getZ()))
                + " var=" + String.format("%.1f", BiomeMapLoader.getHeightVariation(pos.getX(), pos.getZ())));
    }
}