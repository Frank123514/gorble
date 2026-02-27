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

    /** Sea-level Y. */
    public static final int SEA_LEVEL = 61;

    /** Deepest ocean floor Y — used far from any land. */
    private static final int OCEAN_FLOOR = 40;

    /**
     * Shallowest river/narrow-channel floor Y.
     * Ocean pixels very close to land (1–2 px) rise to this Y.
     */
    private static final int RIVER_FLOOR_Y = 55;

    /**
     * Search radius (heightmap pixels) for the land-proximity check.
     * 14 px × 28 blocks/px = 392 blocks — enough to span any painted river.
     * Open-ocean columns exit early because the early-out triggers as soon as
     * the ring-start distance exceeds the current best.
     */
    private static final int RIVER_PROX_RADIUS = 14;

    /**
     * Power exponent for the ease-in curve used on the coastal approach
     * (the bicubic blend zone where {@code OCEAN_VALUE < t < 0}).
     *
     * <ul>
     *   <li>2.0 — moderate concave-up slope</li>
     *   <li>3.0 — steeper, stays flat longer then rises fast (matches red guideline)</li>
     * </ul>
     */
    private static final float COASTAL_EASE_POWER = 3.0f;

    /**
     * Elevation fraction marking the end of the beach zone.
     * Land at 0 < t ≤ BEACH_T slopes gently from sea level to LAND_MIN_Y.
     */
    private static final float BEACH_T    = 0.06f;

    /** World Y at the inner edge of the beach. */
    private static final int LAND_MIN_Y   = 68;

    /** World Y at peak elevation = 1.0. */
    private static final int LAND_MAX_Y   = 280;

    /* ------------------------------------------------------------------ */
    /* Fields                                                               */
    /* ------------------------------------------------------------------ */

    private final Holder<NoiseGeneratorSettings> settings;
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

    /* ------------------------------------------------------------------ */
    /* 2. Surface — delegated to vanilla                                   */
    /* ------------------------------------------------------------------ */

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

    /**
     * Converts a heightmap elevation value to a world Y coordinate.
     *
     * <h3>Underwater profile (matches the red guideline curve)</h3>
     * <pre>
     *  SEA_LEVEL (61) ─────────────────────────────────────── /
     *                                                        /
     *  RIVER_FLOOR (55) ──────────────────────────────────/ /
     *                                           (ease-in) /
     *  OCEAN_FLOOR (40) ─────────────────────────────────
     *                   far                          shore
     * </pre>
     *
     * <h4>Zone 1 — pure ocean pixels ({@code t ≤ OCEAN_VALUE})</h4>
     * The floor is raised from {@link #OCEAN_FLOOR} toward {@link #RIVER_FLOOR_Y}
     * using a smoothstep curve driven by proximity to the nearest land pixel.
     * Rivers (narrow → always 1–3 px from land) become shallow; open ocean stays deep.
     *
     * <h4>Zone 2 — coastal blend ({@code OCEAN_VALUE < t < 0})</h4>
     * These are the bicubic-interpolated pixels between ocean and land edges.
     * A single <em>ease-in</em> power curve (slow start, fast finish) maps
     * the zone-1 floor height up to {@link #SEA_LEVEL}.  This produces the
     * concave-up shape shown in the red guideline — the bed stays flat far from
     * the coast, then sweeps upward steeply just before the shoreline.
     *
     * <h4>Zone 3 — beach ({@code 0 < t ≤ BEACH_T})</h4>
     * Gentle smoothstep from sea level to {@link #LAND_MIN_Y}.
     *
     * <h4>Zone 4 — land ({@code BEACH_T < t ≤ 1})</h4>
     * Smoothstep from {@link #LAND_MIN_Y} to {@link #LAND_MAX_Y}.
     */
    static int elevToY(float t, int worldX, int worldZ) {
        final float OV = HeightmapLoader.OCEAN_VALUE; // -0.05

        /* ── Zone 1: pure ocean pixel ──────────────────────────────────── */
        if (t <= OV) {
            float dist = HeightmapLoader.nearestLandDistanceF(worldX, worldZ, RIVER_PROX_RADIUS);
            if (dist <= RIVER_PROX_RADIUS) {
                // smoothstep: S-curve from 0 (open ocean) to 1 (touching land)
                float proximity = smoothstep(1f - dist / RIVER_PROX_RADIUS);
                return Mth.floor(Mth.lerp(proximity, OCEAN_FLOOR, RIVER_FLOOR_Y));
            }
            return OCEAN_FLOOR;
        }

        /* ── Zone 2: coastal bicubic blend — ease-in concave-up slope ──── */
        if (t <= 0f) {
            // u = 0 at the deep-ocean edge (t = OV), u = 1 at the waterline (t = 0)
            float u = (t - OV) / -OV;

            // Determine the floor Y that Zone 1 would give at this position
            // (so Zone 1 and Zone 2 join seamlessly at t = OV)
            float dist = HeightmapLoader.nearestLandDistanceF(worldX, worldZ, RIVER_PROX_RADIUS);
            float zoneOneFloor;
            if (dist <= RIVER_PROX_RADIUS) {
                float proximity = smoothstep(1f - dist / RIVER_PROX_RADIUS);
                zoneOneFloor = Mth.lerp(proximity, OCEAN_FLOOR, RIVER_FLOOR_Y);
            } else {
                zoneOneFloor = OCEAN_FLOOR;
            }

            // ease-in: t^COASTAL_EASE_POWER  — concave-up (flat far, steep near shore)
            // This matches the red guideline curve exactly: the seabed sits at
            // zoneOneFloor until ~70% of the way to the coast, then sweeps up fast.
            float eased = easeIn(u, COASTAL_EASE_POWER);
            return Mth.floor(Mth.lerp(eased, zoneOneFloor, SEA_LEVEL));
        }

        /* ── Zone 3: beach ─────────────────────────────────────────────── */
        if (t <= BEACH_T) {
            return Mth.floor(Mth.lerp(smoothstep(t / BEACH_T), SEA_LEVEL, LAND_MIN_Y));
        }

        /* ── Zone 4: land ──────────────────────────────────────────────── */
        return Mth.floor(Mth.lerp(smoothstep((t - BEACH_T) / (1f - BEACH_T)), LAND_MIN_Y, LAND_MAX_Y));
    }

    /* ------------------------------------------------------------------ */
    /* Curve helpers                                                        */
    /* ------------------------------------------------------------------ */

    /**
     * Ease-in power curve: {@code t^power}.
     * Starts near-flat (low gradient) and ends steep (high gradient).
     * Produces the concave-up bowl shape seen in the red guideline.
     * Higher power = flatter longer, steeper near the coast.
     */
    private static float easeIn(float t, float power) {
        t = Mth.clamp(t, 0f, 1f);
        return (float) Math.pow(t, power);
    }

    /**
     * Smoothstep: {@code 3t² − 2t³}.
     * Zero first-derivative at both ends — removes visible kinks at zone boundaries.
     * Used for the proximity blend and land transitions.
     */
    private static float smoothstep(float t) {
        t = Mth.clamp(t, 0f, 1f);
        return t * t * (3f - 2f * t);
    }

    /* ------------------------------------------------------------------ */
    /* Height queries                                                       */
    /* ------------------------------------------------------------------ */

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level,
                             @NotNull RandomState random) {
        return elevToY(HeightmapLoader.getElevationAtWorld(x, z), x, z);
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z,
                                              @NotNull LevelHeightAccessor level,
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

    /* ------------------------------------------------------------------ */
    /* Delegated systems                                                    */
    /* ------------------------------------------------------------------ */

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
        float dist = HeightmapLoader.nearestLandDistanceF(pos.getX(), pos.getZ(), RIVER_PROX_RADIUS);
        info.add(String.format("[GoT] elev=%.3f  Y=%d  sea=%d  landDist=%.2fpx",
                elev, elevToY(elev, pos.getX(), pos.getZ()), SEA_LEVEL, dist));
    }
}