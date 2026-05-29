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
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class GotChunkGenerator extends ChunkGenerator {

    // ── Constants ──────────────────────────────────────────────────────────

    public static final int SEA_LEVEL = 63;

    // Broad base shape — same scales as before, these feel right
    private static final double NOISE_SCALE_X = 220.0;
    private static final double NOISE_SCALE_Z = 190.0;

    // 4 octaves to get the fine rocky surface detail real mountains have
    private static final int    FBM_OCTAVES    = 4;
    private static final double FBM_LACUNARITY = 2.0;
    private static final double FBM_GAIN       = 0.45;

    /**
     * REAL MOUNTAIN PROFILE SYSTEM
     *
     * Real mountains have a very specific cross-sectional shape — NOT a simple
     * cone or dome. Looking at any real mountain (Alps, Rockies, Himalayas):
     *
     *   SUMMIT ZONE  (very top): near-flat plateau or sharp knife-edge peak
     *   UPPER CLIFF  (80-100%):  very steep, nearly vertical rock faces (55-70°)
     *   SHOULDER     (60-80%):   convex transition — the "bulge" you see on a
     *                            mountain flank before it drops into cliffs
     *   MID SLOPE    (30-60%):   consistent steep face, scree and rock (30-45°)
     *   LOWER FLARE  (10-30%):   slope eases off and spreads wide (15-25°)
     *   BASE/VALLEY  (0-10%):    gentle approach, merging into the plain (5-10°)
     *
     * This S-curve profile is produced mathematically by combining:
     *   1. A power curve that steepens the upper portion (cliffs)
     *   2. A smoothstep that creates the shoulder/flare transition
     *   3. Fine ridge noise at TIGHT scale (37 blocks) that only fires on steep zones
     *   4. A separate micro-noise for surface texture (not shape)
     *
     * The key insight: real mountains are NOT smooth — the slope INCREASES as you
     * go up (concave lower, convex upper), then the very summit flattens slightly.
     * This is the opposite of a simple pow() curve which rounds off at the top.
     */

    // Height below which terrain is "flat lowland" — no mountain shaping applied
    private static final float  MTN_BASE        = 75f;
    // Height at which terrain transitions to full steep cliff profile
    private static final float  MTN_PEAK        = 130f;
    // The range between base and peak
    private static final float  MTN_RANGE       = MTN_PEAK - MTN_BASE;  // 55

    // Ridge noise — TIGHT scale like real mountain ridgelines (37 blocks ≈ ME's value)
    // This creates the craggy, jagged upper face. ONLY fires on steep terrain.
    private static final double RIDGE_SCALE     = 37.0;
    private static final float  RIDGE_AMPLITUDE = 12.0f;   // blocks of ridge roughness

    // Secondary detail noise — medium scale, fires on mid-slope
    private static final double DETAIL_SCALE    = 18.0;
    private static final float  DETAIL_AMP      = 4.0f;

    // How much the SHAPE amplifier pushes peaks up
    // Real mountains: base heights of ~100 in biome_colors.json end up 150-180 in world
    private static final float  PEAK_AMPLIFY    = 2.2f;

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

    private static volatile SimplexNoise seededNoise  = SimplexNoise.seeded(0L);
    private static volatile SimplexNoise ridgeNoise   = SimplexNoise.seeded(1L);
    private static volatile SimplexNoise detailNoise  = SimplexNoise.seeded(2L);

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
        ridgeNoise  = SimplexNoise.seeded(worldSeed ^ 0xDEADBEEF_C0FFEEL);
        detailNoise = SimplexNoise.seeded(worldSeed ^ 0xFACEFACE_BABE5L);
        SubbiomeResolver.initSeed(worldSeed);
        SlopeSurfaceResolver.initSeed(worldSeed);
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
    public void buildSurface(WorldGenRegion region, StructureManager structures,
                             RandomState random, ChunkAccess chunk) {
        vanilla.buildSurface(region, structures, random, chunk);
        RoadWorldGen.buildRoadsInChunk(chunk);
        RoadWorldGen.clearVegetationFromRoads(chunk);
        WallWorldGen.buildWallInChunk(chunk);
        SlopeSurfaceResolver.applySlopeBlocks(chunk, region);
    }

    // ── Surface height ─────────────────────────────────────────────────────

    public static int computeSurfaceY(int worldX, int worldZ) {
        return Mth.floor(computeRawSurfaceY(worldX, worldZ));
    }

    public static float computeRawSurfaceY(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;

        // ── Step 1: Sample the 4×4 bicubic grid from the biomemap ──────────
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
                h[row * 4 + col] = p.baseHeight();
                v[row * 4 + col] = p.heightVariation();
            }
        }

        float rawHeight       = bicubicBspline(h, fx, fz);
        float heightVariation = bicubicBspline(v, fx, fz);

        // ── Step 2: Apply the real mountain S-curve profile ─────────────────
        //
        // Below MTN_BASE: flat terrain, no mountain shaping.
        // Between MTN_BASE and MTN_PEAK: apply progressive steepening.
        // Above MTN_PEAK: aggressive peak amplification + cliff noise.
        //
        // The S-curve works by splitting into THREE zones:
        //
        //  LOWER FLARE (t = 0.0 → 0.35):
        //    Slope eases in gently. Uses smoothstep so the base of the
        //    mountain doesn't feel like it slams out of flat ground.
        //    Real mountains have a wide, gradual base skirt.
        //
        //  MID SHOULDER (t = 0.35 → 0.65):
        //    The convex "shoulder" — this is the bulge you see on a real
        //    mountain. Slope increases faster here than at the base.
        //    Uses a linear ramp that feeds into the upper zone.
        //
        //  UPPER CLIFF (t = 0.65 → 1.0):
        //    Steep face. Height compounds using pow(t, 2.4) which creates
        //    the concave-upward cliff profile — the slope keeps getting
        //    steeper as you go higher. This is what makes cliffs look like
        //    cliffs instead of rounded domes.
        //
        float shapedHeight = rawHeight;

        if (rawHeight > MTN_BASE) {
            float t = Math.min((rawHeight - MTN_BASE) / MTN_RANGE, 1.0f);

            float amplified;
            if (t < 0.35f) {
                // Lower flare — smooth gentle start (smoothstep derivative)
                float st = t / 0.35f;
                float smooth = st * st * (3f - 2f * st);   // smoothstep
                amplified = MTN_BASE + smooth * 0.35f * MTN_RANGE * 1.1f;
            } else if (t < 0.65f) {
                // Mid shoulder — linear steepening
                float base  = MTN_BASE + 0.35f * MTN_RANGE * 1.1f;
                float st    = (t - 0.35f) / 0.30f;
                amplified   = base + st * 0.30f * MTN_RANGE * 1.5f;
            } else {
                // Upper cliff — aggressive exponential (the steep face)
                float base  = MTN_BASE + 0.35f * MTN_RANGE * 1.1f
                                       + 0.30f * MTN_RANGE * 1.5f;
                float st    = (t - 0.65f) / 0.35f;
                // pow(st, 0.6) makes it concave-upward → slope increases going up
                float cliff = (float) Math.pow(st, 0.6f);
                amplified   = base + cliff * 0.35f * MTN_RANGE * PEAK_AMPLIFY;
            }

            shapedHeight = amplified;
        }

        // ── Step 3: Broad base noise (4-octave fBm) — surface texture ──────
        double noiseVal = seededNoise.fbm(
                worldX / NOISE_SCALE_X,
                worldZ / NOISE_SCALE_Z,
                FBM_OCTAVES,
                FBM_LACUNARITY,
                FBM_GAIN);

        // ── Step 4: Ridge noise — ONLY on steep terrain (upper cliff zone) ──
        //
        // Real mountains have jagged ridgelines and cliff faces caused by
        // jointing in the rock, glacial carving, and differential erosion.
        // This noise ONLY fires above the shoulder zone so flat terrain and
        // gentle slopes stay clean. Scale 37 = tight ridgeline frequency.
        //
        float ridgeAdd = 0f;
        if (shapedHeight > MTN_BASE + MTN_RANGE * 0.6f) {
            // How far into the upper cliff zone are we (0 at shoulder, 1 at peak)
            float cliffness = Math.min(
                    (shapedHeight - (MTN_BASE + MTN_RANGE * 0.6f)) / (MTN_RANGE * 0.4f * PEAK_AMPLIFY),
                    1.0f);

            // Primary ridge noise — tight frequency, large amplitude
            float r1 = (float) ridgeNoise.eval(
                    worldX / RIDGE_SCALE,
                    worldZ / RIDGE_SCALE);

            // Secondary ridge — 2x frequency, half amplitude (adds sub-ridges)
            float r2 = (float) ridgeNoise.eval(
                    worldX / (RIDGE_SCALE * 0.5),
                    worldZ / (RIDGE_SCALE * 0.5));

            ridgeAdd = cliffness * RIDGE_AMPLITUDE * (r1 + 0.5f * r2);
        }

        // ── Step 5: Detail noise — mid slope texture ────────────────────────
        //
        // Scree and rocky mid-slopes have small-scale roughness from boulders
        // and talus accumulation. Fires between base and cliff zone only.
        //
        float detailAdd = 0f;
        if (shapedHeight > MTN_BASE && shapedHeight < MTN_BASE + MTN_RANGE * 0.75f) {
            float midness = Math.min((shapedHeight - MTN_BASE) / (MTN_RANGE * 0.75f), 1.0f);
            detailAdd = midness * DETAIL_AMP * (float) detailNoise.eval(
                    worldX / DETAIL_SCALE,
                    worldZ / DETAIL_SCALE);
        }

        return shapedHeight
               + (float) noiseVal * heightVariation
               + ridgeAdd
               + detailAdd;
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
                "[GoT] Y=%d  raw=%.0f  shaped  px=(%d,%d)",
                surfY, p.baseHeight(), px, pz));
        info.add("[GoT] " + SlopeSurfaceResolver.debugInfo(p.biomeId(), pos.getX(), pos.getZ()));
    }
}
