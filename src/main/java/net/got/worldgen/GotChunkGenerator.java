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
 * GoT chunk generator.
 *
 *  Terrain   — HeightmapLoader (topographic PNG → Y)
 *  Biomes    — GotBiomeSource  (painted biome map + biome_colors.json)
 *  Surface   — vanilla NoiseBasedChunkGenerator.buildSurface()
 *  Ores/deco — default ChunkGenerator.applyBiomeDecoration() (not overridden)
 *  Caves     — vanilla NoiseBasedChunkGenerator.applyCarvers()
 */
public final class GotChunkGenerator extends ChunkGenerator {

    /* ------------------------------------------------------------------ */
    /* Y constants                                                          */
    /* ------------------------------------------------------------------ */

    /** Sea-level Y — 2 lower than vanilla default (63 → 61). */
    public static final int SEA_LEVEL    = 61;

    /** Deepest ocean floor Y. */
    private static final int OCEAN_FLOOR = 40;

    /**
     * Elevation fraction that marks the end of the beach zone.
     * Land at 0 < t ≤ BEACH_T slopes gently from sea level to LAND_MIN_Y.
     */
    private static final float BEACH_T   = 0.06f;

    /** World Y at the inner edge of the beach (elevation = BEACH_T). */
    private static final int LAND_MIN_Y  = 68;

    /** World Y at peak elevation = 1.0. */
    private static final int LAND_MAX_Y  = 280;

    /* ------------------------------------------------------------------ */
    /* Fields                                                               */
    /* ------------------------------------------------------------------ */

    private final Holder<NoiseGeneratorSettings> settings;
    /**
     * Vanilla generator kept as a delegate for surface building, cave carving,
     * and anything else we don't want to reimplement.
     */
    private final NoiseBasedChunkGenerator vanilla;

    /* ------------------------------------------------------------------ */
    /* Codec                                                                */
    /* ------------------------------------------------------------------ */

    public static final MapCodec<GotChunkGenerator> CODEC =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    BiomeSource.CODEC.fieldOf("biome_source")
                            .forGetter(ChunkGenerator::getBiomeSource),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings")
                            .forGetter(g -> g.settings)
            ).apply(i, GotChunkGenerator::new));

    public GotChunkGenerator(BiomeSource biomeSource,
                             Holder<NoiseGeneratorSettings> settings) {
        super(biomeSource);
        this.settings = settings;
        this.vanilla  = new NoiseBasedChunkGenerator(biomeSource, settings);
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    /* ------------------------------------------------------------------ */
    /* 1. Stone skeleton                                                    */
    /* ------------------------------------------------------------------ */

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender,
            @NotNull RandomState random,
            @NotNull StructureManager structures,
            @NotNull ChunkAccess chunk
    ) {
        NoiseSettings noise = settings.value().noiseSettings();
        int minY = noise.minY();
        int maxY = minY + noise.height();
        int sea  = getSeaLevel();
        ChunkPos pos = chunk.getPos();

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = pos.getBlockX(lx);
                int wz = pos.getBlockZ(lz);
                int surfaceY = elevToY(HeightmapLoader.getElevationAtWorld(wx, wz));

                for (int y = minY; y < maxY; y++) {
                    BlockState state;
                    if      (y <= surfaceY) state = Blocks.STONE.defaultBlockState();
                    else if (y <= sea)      state = settings.value().defaultFluid();
                    else                   state = Blocks.AIR.defaultBlockState();
                    chunk.setBlockState(new BlockPos(lx, y, lz), state, false);
                }
            }
        }

        return CompletableFuture.completedFuture(chunk);
    }

    /* ------------------------------------------------------------------ */
    /* 2. Surface — fully delegated to vanilla                             */
    /* ------------------------------------------------------------------ */

    /**
     * Vanilla's SurfaceSystem handles grass, dirt, sand, gravel, podzol,
     * snow, mycelium — all driven by each biome's surface_rule.
     * It operates on the chunk's heightmaps (which are populated by
     * fillFromNoise above), so it works correctly without noise data.
     */
    @Override
    public void buildSurface(
            @NotNull WorldGenRegion region,
            @NotNull StructureManager structures,
            @NotNull RandomState random,
            @NotNull ChunkAccess chunk
    ) {
        vanilla.buildSurface(region, structures, random, chunk);
    }

    /* ------------------------------------------------------------------ */
    /* Elevation → world Y                                                  */
    /* ------------------------------------------------------------------ */

    static int elevToY(float t) {
        final float OV = HeightmapLoader.OCEAN_VALUE; // -0.05

        if (t <= OV)      return OCEAN_FLOOR;
        if (t <= 0f)      return Mth.floor(Mth.lerp((t - OV) / -OV,       OCEAN_FLOOR, SEA_LEVEL));
        if (t <= BEACH_T) return Mth.floor(Mth.lerp(t / BEACH_T,           SEA_LEVEL,   LAND_MIN_Y));
        return                   Mth.floor(Mth.lerp((t - BEACH_T) / (1f - BEACH_T), LAND_MIN_Y, LAND_MAX_Y));
    }

    /* ------------------------------------------------------------------ */
    /* Height queries                                                       */
    /* ------------------------------------------------------------------ */

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level,
                             @NotNull RandomState random) {
        return elevToY(HeightmapLoader.getElevationAtWorld(x, z));
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z,
                                              @NotNull LevelHeightAccessor level,
                                              @NotNull RandomState random) {
        int minY    = level.getMinY();
        int surface = elevToY(HeightmapLoader.getElevationAtWorld(x, z));
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

    /* ------------------------------------------------------------------ */
    /* Delegated systems                                                    */
    /* ------------------------------------------------------------------ */

    @Override
    public void applyCarvers(WorldGenRegion region, long seed, RandomState random,
                             BiomeManager biomeManager, StructureManager structures,
                             ChunkAccess chunk) {
        vanilla.applyCarvers(region, seed, random, biomeManager, structures, chunk);
    }

    // applyBiomeDecoration NOT overridden — default impl walks each biome's
    // feature list, giving ores, trees, flowers etc. for free.

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion region) {
        ChunkPos pos = region.getCenter();
        Holder<Biome> b = region.getBiome(pos.getWorldPosition().atY(region.getMaxY() - 1));
        WorldgenRandom rand = new WorldgenRandom(RandomSource.create());
        rand.setDecorationSeed(region.getSeed(), pos.getMinBlockX(), pos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(region, b, pos, rand);
    }

    /* ------------------------------------------------------------------ */
    /* Misc                                                                 */
    /* ------------------------------------------------------------------ */

    @Override public int getSeaLevel()  { return SEA_LEVEL; }
    @Override public int getMinY()      { return settings.value().noiseSettings().minY(); }
    @Override public int getGenDepth()  { return settings.value().noiseSettings().height(); }

    @Override
    public void addDebugScreenInfo(java.util.List<String> info,
                                   RandomState random, BlockPos pos) {
        float elev = HeightmapLoader.getElevationAtWorld(pos.getX(), pos.getZ());
        info.add(String.format("[GoT] elev=%.3f  Y=%d  sea=%d",
                elev, elevToY(elev), SEA_LEVEL));
    }
}