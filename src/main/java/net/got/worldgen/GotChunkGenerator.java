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

    public static final int SEA_LEVEL = 61;
    private static final int OCEAN_FLOOR = 40;
    private static final int RIVER_FLOOR_Y = 55;
    private static final int RIVER_PROX_RADIUS = 14;
    private static final float COASTAL_EASE_POWER = 3.0f;

    private static final float BEACH_T     = 0.01f;
    private static final int   BEACH_TOP_Y = 63;
    private static final int   LAND_MAX_Y  = 280;

    // ── Terrain detail noise ─────────────────────────────────────────────

    /**
     * Base amplitude applied to all land above the coastal ramp.
     * Raised from 11 → 16 so already-hilly areas carved by the elevation map
     * get noticeably more dramatic relief on top of their painted shape.
     */
    private static final float NOISE_AMP_BASE       = 16f;

    /**
     * Extra amplitude bonus on flat lowland (small t values).
     * Raised from 4 → 7 so plains get meaningful rolling variation.
     * Total flat amplitude = NOISE_AMP_BASE + NOISE_AMP_FLAT_BONUS = 23 blocks.
     */
    private static final float NOISE_AMP_FLAT_BONUS = 7f;

    /**
     * Elevation fraction at which the flat bonus has fully faded.
     * Kept at 0.12 — bonus is lowland-only.
     */
    private static final float NOISE_FLAT_FADE_T    = 0.12f;

    /**
     * Width of the coastal noise ramp above BEACH_T.
     * Noise stays at zero here so the beach/shore stays clean.
     * Kept at 0.07 — this is what fixed the cliff edges.
     */
    private static final float NOISE_RAMP_WIDTH     = 0.07f;

    /**
     * Noise fades to zero above this — high peaks keep their painted silhouette.
     * Kept at 0.85.
     */
    private static final float NOISE_FADE_T         = 0.85f;

    /** Medium layer: broad smooth rolling bumps. ~110 block wavelength. */
    private static final float NOISE_FREQ_MED       = 1f / 110f;

    /** Fine layer: mid-scale knolls and hollows. ~50 block wavelength. */
    private static final float NOISE_FREQ_FINE      = 1f / 50f;

    /** Micro layer: surface roughness / contour steps. ~22 block wavelength. */
    private static final float NOISE_FREQ_MICRO     = 1f / 22f;

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator vanilla;

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
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

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
                int surfaceY = elevToY(HeightmapLoader.getElevationAtWorld(wx, wz), wx, wz);

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

    /**
     * Zone layout:
     *   t <= -0.05          Zone 1  ocean floor        (Y 40–55)
     *   -0.05 < t <= 0      Zone 2  coastal blend      (Y 40–61)
     *   0 < t <= BEACH_T    Zone 3  beach              (Y 61–63)
     *   BEACH_T < t <= 1    Zone 4  land               (Y 63–280) + terrain noise
     *
     * Noise amplitude summary:
     *   Coast buffer (BEACH_T..BEACH_T+0.07) → 0   (clean shore, no cliff steps)
     *   Flat lowland (t < 0.12)              → up to 23 blocks
     *   Mid slopes   (0.12 .. 0.85)          → 16 blocks
     *   High peaks   (> 0.85)                → fades to 0
     */
    static int elevToY(float t, int worldX, int worldZ) {
        final float OV = HeightmapLoader.OCEAN_VALUE; // -0.05

        // Zone 1 — ocean floor, raised near land for river shallows
        if (t <= OV) {
            float dist = HeightmapLoader.nearestLandDistanceF(worldX, worldZ, RIVER_PROX_RADIUS);
            if (dist <= RIVER_PROX_RADIUS) {
                float proximity = smoothstep(1f - dist / RIVER_PROX_RADIUS);
                return Mth.floor(Mth.lerp(proximity, OCEAN_FLOOR, RIVER_FLOOR_Y));
            }
            return OCEAN_FLOOR;
        }

        // Zone 2 — coastal ease-in
        if (t <= 0f) {
            float u = (t - OV) / -OV;
            float dist = HeightmapLoader.nearestLandDistanceF(worldX, worldZ, RIVER_PROX_RADIUS);
            float zoneOneFloor = (dist <= RIVER_PROX_RADIUS)
                    ? Mth.lerp(smoothstep(1f - dist / RIVER_PROX_RADIUS), OCEAN_FLOOR, RIVER_FLOOR_Y)
                    : OCEAN_FLOOR;
            return Mth.floor(Mth.lerp(easeIn(u, COASTAL_EASE_POWER), zoneOneFloor, SEA_LEVEL));
        }

        // Zone 3 — beach
        if (t <= BEACH_T) {
            return Mth.floor(Mth.lerp(smoothstep(t / BEACH_T), SEA_LEVEL, BEACH_TOP_Y));
        }

        // Zone 4 — land
        float baseY = Mth.lerp(smoothstep((t - BEACH_T) / (1f - BEACH_T)), BEACH_TOP_Y, LAND_MAX_Y);

        float amp = noiseAmplitude(t);
        if (amp > 0.5f) {
            float noise = terrainNoise(worldX, worldZ);
            float finalY = baseY + noise * amp;
            finalY = Math.max(finalY, BEACH_TOP_Y + 1f);
            return Mth.floor(finalY);
        }

        return Mth.floor(baseY);
    }

    /**
     * Noise amplitude envelope:
     *
     *   [BEACH_T .. BEACH_T + NOISE_RAMP_WIDTH]  smoothstep ramp 0 → full
     *   [rampEnd .. NOISE_FLAT_FADE_T]            NOISE_AMP_BASE + NOISE_AMP_FLAT_BONUS (23)
     *   [NOISE_FLAT_FADE_T .. NOISE_FADE_T]       NOISE_AMP_BASE (16)
     *   [NOISE_FADE_T .. 1.0]                     smoothstep fade to 0
     */
    private static float noiseAmplitude(float t) {
        if (t <= BEACH_T) return 0f;
        if (t >= 1.0f)    return 0f;

        // Coastal ramp-in
        float rampEnd = BEACH_T + NOISE_RAMP_WIDTH;
        float rampFactor;
        if (t < rampEnd) {
            float u = (t - BEACH_T) / NOISE_RAMP_WIDTH;
            rampFactor = smoothstep(u);
        } else {
            rampFactor = 1f;
        }

        // Peak fade-out
        float fadeFactor;
        if (t >= NOISE_FADE_T) {
            float u = (t - NOISE_FADE_T) / (1f - NOISE_FADE_T);
            fadeFactor = 1f - smoothstep(u);
        } else {
            fadeFactor = 1f;
        }

        // Flat-terrain bonus — fades out above NOISE_FLAT_FADE_T
        float bonus = 0f;
        if (t < NOISE_FLAT_FADE_T) {
            float u = 1f - (t - BEACH_T) / (NOISE_FLAT_FADE_T - BEACH_T);
            u = Math.max(0f, u);
            bonus = smoothstep(u) * NOISE_AMP_FLAT_BONUS;
        }

        return (NOISE_AMP_BASE + bonus) * rampFactor * fadeFactor;
    }

    /**
     * Three-layer value noise. Returns approximately [-1, 1].
     */
    private static float terrainNoise(int worldX, int worldZ) {
        float med   = valueNoise(worldX * NOISE_FREQ_MED,
                worldZ * NOISE_FREQ_MED,    0x3F9A1B);

        float fine  = valueNoise(worldX * NOISE_FREQ_FINE  + 31.7f,
                worldZ * NOISE_FREQ_FINE  + 17.3f,   0x7C4D2E);

        float micro = valueNoise(worldX * NOISE_FREQ_MICRO + 93.1f,
                worldZ * NOISE_FREQ_MICRO + 57.9f,   0xA18C55);

        return med * 0.40f + fine * 0.35f + micro * 0.25f;
    }

    /** Smooth 2D value noise, bilinearly interpolated with smoothstep. [-1, 1]. */
    private static float valueNoise(float x, float z, int seed) {
        int   ix = (int) Math.floor(x);
        int   iz = (int) Math.floor(z);
        float fx = x - ix;
        float fz = z - iz;

        float ux = fx * fx * (3f - 2f * fx);
        float uz = fz * fz * (3f - 2f * fz);

        float v00 = noiseHash(ix,     iz,     seed);
        float v10 = noiseHash(ix + 1, iz,     seed);
        float v01 = noiseHash(ix,     iz + 1, seed);
        float v11 = noiseHash(ix + 1, iz + 1, seed);

        return lerp(uz, lerp(ux, v00, v10), lerp(ux, v01, v11));
    }

    /** Deterministic integer hash → [-1, 1]. */
    private static float noiseHash(int x, int z, int seed) {
        int n = x * 1619 + z * 31337 + seed * 6971;
        n = (n << 13) ^ n;
        n = n * (n * n * 15731 + 789221) + 1376312589;
        return 1f - (n & 0x7FFFFFFF) / 1073741824f;
    }

    private static float lerp(float t, float a, float b) { return a + t * (b - a); }

    private static float easeIn(float t, float power) {
        t = Mth.clamp(t, 0f, 1f);
        return (float) Math.pow(t, power);
    }

    private static float smoothstep(float t) {
        t = Mth.clamp(t, 0f, 1f);
        return t * t * (3f - 2f * t);
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level, @NotNull RandomState random) {
        return elevToY(HeightmapLoader.getElevationAtWorld(x, z), x, z);
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z, @NotNull LevelHeightAccessor level,
                                              @NotNull RandomState random) {
        int minY    = level.getMinY();
        int surface = elevToY(HeightmapLoader.getElevationAtWorld(x, z), x, z);
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

    @Override public int getSeaLevel()  { return SEA_LEVEL; }
    @Override public int getMinY()      { return settings.value().noiseSettings().minY(); }
    @Override public int getGenDepth()  { return settings.value().noiseSettings().height(); }

    @Override
    public void addDebugScreenInfo(java.util.List<String> info,
                                   RandomState random, BlockPos pos) {
        float elev = HeightmapLoader.getElevationAtWorld(pos.getX(), pos.getZ());
        float amp  = noiseAmplitude(elev);
        float dist = HeightmapLoader.nearestLandDistanceF(pos.getX(), pos.getZ(), RIVER_PROX_RADIUS);
        info.add(String.format("[GoT] elev=%.3f  Y=%d  sea=%d  noiseAmp=%.1f  landDist=%.1fpx",
                elev, elevToY(elev, pos.getX(), pos.getZ()), SEA_LEVEL, amp, dist));
    }
}