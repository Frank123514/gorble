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
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * GoT chunk generator.
 *
 * Terrain   — HeightmapLoader (topographic PNG → base elevation)
 *           + 5-octave Minecraft SimplexNoise detail layer (land only)
 * Biomes    — GotBiomeSource  (painted biome map + biome_colors.json)
 * Surface   — vanilla NoiseBasedChunkGenerator.buildSurface()
 * Ores/deco — default ChunkGenerator.applyBiomeDecoration()
 * Caves     — vanilla NoiseBasedChunkGenerator.applyCarvers()
 *
 * ── Noise design ────────────────────────────────────────────────────────────
 * Five octaves of SimplexNoise are summed with halving amplitudes ("fBm").
 * The noise offset is multiplied by a coast-proximity weight that is exactly
 * 0 at the waterline and rises via a smoothstep curve to 1 inland.  This means
 * rivers, ocean shores and beaches stay exactly as painted; roughness builds
 * up gradually as you move inland, peaking on hills and midland terrain.
 * High mountain pixels get slightly reduced noise amplitude so peaks remain
 * sharp rather than jagged.
 * ────────────────────────────────────────────────────────────────────────────
 */
public final class GotChunkGenerator extends ChunkGenerator {

    // -----------------------------------------------------------------------
    // Y constants
    // -----------------------------------------------------------------------

    /** Sea-level Y */
    public static final int SEA_LEVEL    = 61;
    private static final int OCEAN_FLOOR = 40;

    private static final float UNDERWATER_SHELF_T = 0.72f;
    private static final int   UNDERWATER_SHELF_Y = 50;

    private static final float BEACH_T   = 0.06f;
    private static final int   LAND_MIN_Y  = 68;
    private static final int   LAND_MAX_Y  = 280;

    // -----------------------------------------------------------------------
    // Simplex noise — 5 octaves
    // -----------------------------------------------------------------------

    /**
     * Fixed seed for deterministic terrain detail.
     * The same world seed always produces the same noise overlay,
     * matching the painted heightmap.
     */
    private static final long NOISE_SEED = 0xC0FFEE_DEAD_1234L;

    /** Number of noise octaves. */
    private static final int OCTAVE_COUNT = 5;

    /**
     * Per-octave frequencies (blocks⁻¹).
     * Octave 0 = coarse hills (~830 block wavelength)
     * Octave 4 = micro surface roughness (~20 block wavelength)
     */
    private static final double[] FREQUENCIES = {
            0.0012,   // ~830 block wavelength
            0.0030,   // ~333
            0.0075,   // ~133
            0.0190,   //  ~53
            0.0480    //  ~21
    };

    /**
     * Per-octave amplitudes (as elevation fraction).
     * Total max deviation ≈ ±0.12 elevation units when all octaves align,
     * which translates to roughly ±25–30 Y blocks on mid-elevation terrain.
     */
    private static final double[] AMPLITUDES = {
            1.000,
            0.500,
            0.250,
            0.125,
            0.063
    };

    /** Sum of all amplitudes — used to normalise to [-1, 1]. */
    private static final double AMP_SUM;
    static {
        double s = 0;
        for (double a : AMPLITUDES) s += a;
        AMP_SUM = s;
    }

    /** Maximum noise displacement applied to elevation (before coast weighting). */
    private static final double MAX_NOISE_ELEV = 0.13;

    private final SimplexNoise[] terrainOctaves = new SimplexNoise[OCTAVE_COUNT];

    // -----------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator vanilla;

    // -----------------------------------------------------------------------
    // Codec
    // -----------------------------------------------------------------------

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

        // Each SimplexNoise is seeded from a different position in the same RNG
        // stream, guaranteeing independence between octaves.
        RandomSource rng = RandomSource.create(NOISE_SEED);
        for (int i = 0; i < OCTAVE_COUNT; i++) {
            terrainOctaves[i] = new SimplexNoise(rng);
        }
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    // -----------------------------------------------------------------------
    // 1. Stone skeleton
    // -----------------------------------------------------------------------

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

                float baseElev   = HeightmapLoader.getElevationAtWorld(wx, wz);
                float noisedElev = applyTerrainNoise(baseElev, wx, wz);
                int   surfaceY   = elevToY(noisedElev);

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

    // -----------------------------------------------------------------------
    // 2. Surface — delegated to vanilla
    // -----------------------------------------------------------------------

    @Override
    public void buildSurface(
            @NotNull WorldGenRegion region,
            @NotNull StructureManager structures,
            @NotNull RandomState random,
            @NotNull ChunkAccess chunk
    ) {
        vanilla.buildSurface(region, structures, random, chunk);
    }

    // -----------------------------------------------------------------------
    // Noise helpers
    // -----------------------------------------------------------------------

    /**
     * Samples 5-octave fBm simplex noise at the given world position.
     *
     * @return value in [-1, 1] (approximate; rare extremes may slightly exceed)
     */
    private double sampleFbm(int wx, int wz) {
        double total = 0;
        for (int i = 0; i < OCTAVE_COUNT; i++) {
            total += terrainOctaves[i].getValue(wx * FREQUENCIES[i], wz * FREQUENCIES[i])
                    * AMPLITUDES[i];
        }
        return total / AMP_SUM; // normalise to ≈ [-1, 1]
    }

    /**
     * Applies the noise layer on top of the base heightmap elevation.
     *
     * The key design goals:
     * <ul>
     *   <li>Rivers and ocean coasts stay exactly as painted — noise weight is 0
     *       at and below the waterline.</li>
     *   <li>Noise ramps smoothly (smoothstep) from 0 → 1 across the beach zone,
     *       so the transition is gradual rather than abrupt.</li>
     *   <li>Mid-elevation land gets the full noise amplitude for realistic hills.</li>
     *   <li>High mountain areas get slightly reduced amplitude so peaks keep
     *       their dramatic painted silhouettes.</li>
     * </ul>
     *
     * @param baseElev raw heightmap elevation
     * @param wx world X
     * @param wz world Z
     * @return noise-modified elevation
     */
    private float applyTerrainNoise(float baseElev, int wx, int wz) {
        // Deep ocean — no noise at all
        if (baseElev <= HeightmapLoader.OCEAN_VALUE) return baseElev;

        // ── Coast weight ─────────────────────────────────────────────────
        // 0 at waterline, rises to 1 inland, using smoothstep for a gentle ramp.
        // The ramp spans BEACH_T * 6 (~18% elevation) so that beaches and river
        // banks receive only a small amount of roughness.
        final float RAMP_WIDTH = BEACH_T * 6f;

        float coastWeight;
        if (baseElev <= 0f) {
            // Shallow underwater shelf: very slight noise so the sea floor isn't
            // completely flat, but nowhere near the inland amplitude.
            float u = (baseElev - HeightmapLoader.OCEAN_VALUE) / (-HeightmapLoader.OCEAN_VALUE);
            // u: 0 at OCEAN_VALUE (deep), 1 at waterline
            float shallowNoise = u * u * 0.18f;  // max 18 % at waterline
            double noiseVal = sampleFbm(wx, wz);
            return baseElev + (float)(noiseVal * MAX_NOISE_ELEV * 0.4 * shallowNoise);
        } else if (baseElev < RAMP_WIDTH) {
            float t = baseElev / RAMP_WIDTH;
            coastWeight = t * t * (3f - 2f * t);  // smoothstep
        } else {
            coastWeight = 1f;
        }

        // ── Elevation amplitude scale ────────────────────────────────────
        // Slightly reduce noise at very high elevations so mountain peaks
        // keep their bold shapes from the painted heightmap.
        float elevScale = 1f - Mth.clamp((baseElev - 0.55f) / 0.45f, 0f, 1f) * 0.45f;

        // ── Apply ─────────────────────────────────────────────────────────
        double noiseVal = sampleFbm(wx, wz);
        float offset = (float)(noiseVal * MAX_NOISE_ELEV * coastWeight * elevScale);

        // Never push land below the waterline — that would create unexpected lakes
        return Math.max(0.001f, baseElev + offset);
    }

    // -----------------------------------------------------------------------
    // Elevation → world Y
    // -----------------------------------------------------------------------

    static int elevToY(float t) {
        final float OV = HeightmapLoader.OCEAN_VALUE;

        if (t <= OV)  return OCEAN_FLOOR;
        if (t <= 0f) {
            float u = (t - OV) / -OV;
            if (u <= UNDERWATER_SHELF_T) {
                return Mth.floor(Mth.lerp(u / UNDERWATER_SHELF_T, OCEAN_FLOOR, UNDERWATER_SHELF_Y));
            }
            float bankU = (u - UNDERWATER_SHELF_T) / (1f - UNDERWATER_SHELF_T);
            return Mth.floor(Mth.lerp(bankU, UNDERWATER_SHELF_Y, SEA_LEVEL));
        }
        if (t <= BEACH_T) return Mth.floor(Mth.lerp(t / BEACH_T,                       SEA_LEVEL,   LAND_MIN_Y));
        return                   Mth.floor(Mth.lerp((t - BEACH_T) / (1f - BEACH_T), LAND_MIN_Y, LAND_MAX_Y));
    }

    // -----------------------------------------------------------------------
    // Height queries
    // -----------------------------------------------------------------------

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level,
                             @NotNull RandomState random) {
        float base = HeightmapLoader.getElevationAtWorld(x, z);
        return elevToY(applyTerrainNoise(base, x, z));
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z,
                                              @NotNull LevelHeightAccessor level,
                                              @NotNull RandomState random) {
        int minY    = level.getMinY();
        float base  = HeightmapLoader.getElevationAtWorld(x, z);
        int surface = elevToY(applyTerrainNoise(base, x, z));
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

    // -----------------------------------------------------------------------
    // Delegated systems
    // -----------------------------------------------------------------------

    @Override
    public void applyCarvers(WorldGenRegion region, long seed, RandomState random,
                             BiomeManager biomeManager, StructureManager structures,
                             ChunkAccess chunk) {
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

    // -----------------------------------------------------------------------
    // Misc
    // -----------------------------------------------------------------------

    @Override public int getSeaLevel()  { return SEA_LEVEL; }
    @Override public int getMinY()      { return settings.value().noiseSettings().minY(); }
    @Override public int getGenDepth()  { return settings.value().noiseSettings().height(); }

    @Override
    public void addDebugScreenInfo(java.util.List<String> info,
                                   RandomState random, BlockPos pos) {
        float base  = HeightmapLoader.getElevationAtWorld(pos.getX(), pos.getZ());
        float noised = applyTerrainNoise(base, pos.getX(), pos.getZ());
        double fbm  = sampleFbm(pos.getX(), pos.getZ());
        info.add(String.format("[GoT] baseElev=%.3f  noisedElev=%.3f  fbm=%.3f  Y=%d  sea=%d",
                base, noised, fbm, elevToY(noised), SEA_LEVEL));
    }
}