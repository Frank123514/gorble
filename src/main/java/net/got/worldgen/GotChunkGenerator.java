package net.got.worldgen;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
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
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class GotChunkGenerator extends ChunkGenerator {

    // ── Constants ──────────────────────────────────────────────────────────

    public static final int SEA_LEVEL = 63;

    // Base shape — large rolling hills
    private static final double NOISE_SCALE_X = 280.0;
    private static final double NOISE_SCALE_Z = 240.0;

    // Detail layer — smaller ridges on top of the base shape
    private static final double DETAIL_SCALE_X = 80.0;
    private static final double DETAIL_SCALE_Z = 80.0;
    private static final double DETAIL_WEIGHT   = 0.35; // how much detail contributes vs base


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

    // ── Inline simplex noise ───────────────────────────────────────────────
    private static final double F2 = 0.5 * (Math.sqrt(3.0) - 1.0);
    private static final double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;
    private static final double[][] GRAD2 = {
            { 1, 1}, {-1, 1}, { 1,-1}, {-1,-1},
            { 1, 0}, {-1, 0}, { 0, 1}, { 0,-1}
    };
    // Two independent perm tables so base and detail don't share the same pattern
    private static volatile short[] noisePerm       = buildPerm(0L);
    private static volatile short[] noisePermDetail = buildPerm(0x9E3779B97F4A7C15L);
    private static volatile double  noiseOffX = 0, noiseOffZ = 0;

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
        noisePerm       = buildPerm(worldSeed);
        noisePermDetail = buildPerm(worldSeed ^ 0x9E3779B97F4A7C15L);
        noiseOffX = ((worldSeed       & 0xFFFFL) / 65536.0) * 1000.0;
        noiseOffZ = ((worldSeed >> 16 & 0xFFFFL) / 65536.0) * 1000.0;
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

        List<StructureStart> nearbyStructures = structures.startsForStructure(
                SectionPos.bottomOf(chunk).chunk(), structure -> true);

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = cp.getBlockX(lx);
                int wz = cp.getBlockZ(lz);
                int surfaceY = Mth.floor(computeBlendedSurfaceY(wx, wz, nearbyStructures));

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

    private static final Logger LOGGER = LogUtils.getLogger();
    private static volatile boolean loggedBuildSurface = false;

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structures,
                             RandomState random, ChunkAccess chunk) {
        if (!loggedBuildSurface) {
            loggedBuildSurface = true;
            ChunkPos cp0 = chunk.getPos();
            LOGGER.info("[GoT][DEBUG] GotChunkGenerator.buildSurface: FIRST CALL — dimension={}, chunk=({},{})",
                    region.getLevel().dimension().location(), cp0.x, cp0.z);
        }

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

                float gridH = p.baseHeight();
                float gridV = p.heightVariation();

                // If this grid cell is a land biome, check whether a
                // terrain-overriding subbiome is active at this pixel's world
                // position and lerp its h/v values in-place.
                // The bicubic spline then blends these modified values with the
                // surrounding unmodified cells — EXACTLY the same interpolation
                // used for all normal biome borders.
                if (!p.isWater()) {
                    int gridWorldX = Math.round(
                            (px - BiomemapLoader.getWidth()  * 0.5f) * BiomemapLoader.MAP_SCALE);
                    int gridWorldZ = Math.round(
                            (pz - BiomemapLoader.getHeight() * 0.5f) * BiomemapLoader.MAP_SCALE);

                    float[] noiseOut = { -1f };
                    SubbiomeDef sub = SubbiomeResolver.resolveTerrain(
                            p.biomeId(), gridWorldX, gridWorldZ, noiseOut);

                    if (sub != null) {
                        // Map noise [threshold, 1] → blend weight [0, 1], smoothstepped.
                        float t      = (float) sub.threshold();
                        float range  = Math.max(1f - t, 0.001f);
                        float blendT = Mth.clamp((noiseOut[0] - t) / range, 0f, 1f);
                        float weight = blendT * blendT * (3f - 2f * blendT);

                        float subH = sub.baseHeight()      >= 0 ? sub.baseHeight()      : gridH;
                        float subV = sub.heightVariation() >= 0 ? sub.heightVariation() : gridV;

                        gridH = Mth.lerp(weight, gridH, subH);
                        gridV = Mth.lerp(weight, gridV, subV);
                    }
                }

                h[row * 4 + col] = gridH;
                v[row * 4 + col] = gridV;
            }
        }

        float rawHeight       = bicubicBspline(h, fx, fz);
        float heightVariation = bicubicBspline(v, fx, fz);

        double ox = noiseOffX + worldX;
        double oz = noiseOffZ + worldZ;

        // Base shape — large smooth hills
        double base   = simplexEval(noisePerm,       ox / NOISE_SCALE_X,  oz / NOISE_SCALE_Z);
        // Detail layer — smaller rolling ridges, weighted down
        double detail = simplexEval(noisePermDetail, ox / DETAIL_SCALE_X, oz / DETAIL_SCALE_Z);

        double noiseVal = (base + detail * DETAIL_WEIGHT) / (1.0 + DETAIL_WEIGHT);

        // ── Slopemap height bonus ──────────────────────────────────────────
        // For mountain biomes, add a bonus derived from how far this pixel
        // sits from the edge of its mountain blob on the biomemap.
        // Edge pixels → bonus 0.  Deep interior pixels → bonus up to MAX_HEIGHT_BONUS.
        // The bicubic spline already smoothly interpolates between biomemap pixels,
        // so the slope at the mountain border is already blended. The bonus is
        // sampled at the same bilinear position and also interpolated across the
        // 4x4 bicubic neighbourhood so it transitions seamlessly.
        float slopemapBonus = 0f;
        if (MountainSlopemapResolver.isLoaded()) {
            // Sample the distance-field bonus at the same 4x4 bicubic grid as height
            float[] sb = new float[16];
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    int px = ipx + col - 1;
                    int pz = ipz + row - 1;
                    sb[row * 4 + col] = MountainSlopemapResolver.heightBonus(px, pz);
                }
            }
            slopemapBonus = bicubicBspline(sb, fx, fz);
        }

        return rawHeight + slopemapBonus + (float) noiseVal * heightVariation;
    }

    // ── Structure terrain blend ───────────────────────────────────────────
    // Same smoothstep weighting used above for subbiome/biome-border blending
    // (see computeRawSurfaceY), just applied radially from structure pieces
    // instead of radially from a biomemap pixel. No per-structure config —
    // every jigsaw structure gets this for free based on its own piece boxes.
    //
    // The blend target used to just be `box.minY() - 1`, which assumes the
    // piece sits ON TOP of untouched natural terrain (true for e.g. the
    // watchtower/windmill, which don't capture any ground blocks of their
    // own). That assumption breaks for a structure like the hamlet, which
    // has its own dirt/grass foundation baked directly into row 0 of its
    // NBT (box.minY() itself IS the walkable grass surface, not the natural
    // terrain a fixed 1 block below it). Blending toward box.minY()-1 in
    // that case digs a moat exactly 1 block deep around the whole structure
    // instead of meeting its actual surface.
    //
    // `ground_level_delta` (set per template_pool element, default 1) is
    // the existing vanilla-standard way of describing how many blocks of a
    // piece are "buried" below its own natural ground line. We reuse it
    // here instead of inventing new per-structure config: delta=1 (the
    // default, used by watchtower/windmill) reproduces the old box.minY()-1
    // behaviour exactly; a structure with its own captured ground layer at
    // row 0 sets delta=2 in its start_pool.json so the blend meets that
    // layer directly instead of tunnelling under it.

    private static final float STRUCTURE_PAD_RADIUS = 6f;

    private static float computeBlendedSurfaceY(int wx, int wz, List<StructureStart> starts) {
        float naturalY = computeRawSurfaceY(wx, wz);
        if (starts.isEmpty()) return naturalY;

        float blended = naturalY;
        for (StructureStart start : starts) {
            for (StructurePiece piece : start.getPieces()) {
                BoundingBox box = piece.getBoundingBox();
                float dist = distanceToBox(wx, wz, box);
                if (dist >= STRUCTURE_PAD_RADIUS) continue;

                float t      = Mth.clamp(dist / STRUCTURE_PAD_RADIUS, 0f, 1f);
                float weight = t * t * (3f - 2f * t); // smoothstep, same as line ~222

                int groundLevelDelta = 1; // vanilla default — matches old hardcoded behaviour
                if (piece instanceof PoolElementStructurePiece pep) {
                    groundLevelDelta = pep.getElement().getGroundLevelDelta();
                }
                float floorY = box.minY() + groundLevelDelta - 2;

                blended = Mth.lerp(weight, floorY, blended);
            }
        }
        return blended;
    }

    private static float distanceToBox(int x, int z, BoundingBox box) {
        float dx = Math.max(Math.max(box.minX() - x, 0), x - box.maxX());
        float dz = Math.max(Math.max(box.minZ() - z, 0), z - box.maxZ());
        return Mth.sqrt(dx * dx + dz * dz);
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
        float rawY  = computeRawSurfaceY(pos.getX(), pos.getZ());
        int   surfY = Mth.floor(rawY);

        info.add(String.format(
                "[GoT] Y=%d  raw=%.1f  nearestPixelBase=%.0f  px=(%d,%d)",
                surfY, rawY, p.baseHeight(), px, pz));
        info.add("[GoT] " + SlopeSurfaceResolver.debugInfo(p.biomeId(), pos.getX(), pos.getZ()));
    }

    // ── Inline 2-D simplex helpers ─────────────────────────────────────────

    private static short[] buildPerm(long seed) {
        short[] p = new short[256];
        for (short i = 0; i < 256; i++) p[i] = i;
        long rng = seed ^ 0x6C62272E07BB0142L;
        for (int i = 255; i > 0; i--) {
            rng = rng * 6364136223846793005L + 1442695040888963407L;
            int j = (int) ((rng >>> 33) % (i + 1));
            short tmp = p[i]; p[i] = p[j]; p[j] = tmp;
        }
        short[] perm = new short[512];
        for (int i = 0; i < 512; i++) perm[i] = p[i & 255];
        return perm;
    }

    private static double simplexEval(short[] perm, double xin, double yin) {
        double s  = (xin + yin) * F2;
        int i     = (int) Math.floor(xin + s);
        int j     = (int) Math.floor(yin + s);
        double t  = (i + j) * G2;
        double x0 = xin - (i - t), y0 = yin - (j - t);
        int i1 = x0 > y0 ? 1 : 0, j1 = x0 > y0 ? 0 : 1;
        double x1 = x0 - i1 + G2,        y1 = y0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2, y2 = y0 - 1.0 + 2.0 * G2;
        int gi0 = perm[(i      + perm[ j      & 255]) & 255] & 7;
        int gi1 = perm[(i + i1 + perm[(j + j1) & 255]) & 255] & 7;
        int gi2 = perm[(i + 1  + perm[(j + 1)  & 255]) & 255] & 7;
        double n0 = 0, n1 = 0, n2 = 0;
        double t0 = 0.5 - x0*x0 - y0*y0; if (t0 > 0) { t0*=t0; n0 = t0*t0*(GRAD2[gi0][0]*x0 + GRAD2[gi0][1]*y0); }
        double t1 = 0.5 - x1*x1 - y1*y1; if (t1 > 0) { t1*=t1; n1 = t1*t1*(GRAD2[gi1][0]*x1 + GRAD2[gi1][1]*y1); }
        double t2 = 0.5 - x2*x2 - y2*y2; if (t2 > 0) { t2*=t2; n2 = t2*t2*(GRAD2[gi2][0]*x2 + GRAD2[gi2][1]*y2); }
        return 70.0 * (n0 + n1 + n2);
    }

}