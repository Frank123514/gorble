package net.got.worldgen;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class GotChunkGenerator extends ChunkGenerator {

    public static final int SEA_LEVEL = 63;

    private static final double  BASE_NOISE_SCALE_X = 280.0;
    private static final double  BASE_NOISE_SCALE_Z = 240.0;

    // Detail layer — smaller ridges blended on top of the base shape
    private static final double  DETAIL_SCALE_X = 80.0;
    private static final double  DETAIL_SCALE_Z = 80.0;
    private static final double  DETAIL_WEIGHT   = 0.35; // how much detail contributes vs base

    // Third hill layer — smaller rolling hills than the detail layer, blended in at
    // real weight so it reads as terrain shaping, not surface texture
    private static final double  FINE_DETAIL_SCALE_X = 50.0;
    private static final double  FINE_DETAIL_SCALE_Z = 50.0;
    private static final double  FINE_DETAIL_WEIGHT   = 0.30;

    // Three independent perm tables so each layer doesn't echo the others' pattern
    private static volatile short[] noisePerm           = buildPerm(0L);
    private static volatile short[] noisePermDetail      = buildPerm(0x9E3779B97F4A7C15L);
    private static volatile short[] noisePermFineDetail  = buildPerm(0x9E3779B97F4A7C15L ^ 0x632BE59BD9B4E019L);

    private static final double HEIGHT_BLEND_CURVE = 5.0;

    private static final float RIDGE_VALLEY_FLOOR = 0.3f;

    private static final float RIDGE_SHOULDER_FRACTION = 0.75f;

    private static final float FOOT_HEIGHT_VARIATION = 8f;

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

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator       vanilla;
    private final int spawnPixelX;
    private final int spawnPixelZ;

    private static volatile int configuredSpawnPixelX = -1;
    private static volatile int configuredSpawnPixelZ = -1;

    private static final double F2 = 0.5 * (Math.sqrt(3.0) - 1.0);
    private static final double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;
    private static final double[][] GRAD2 = {
            { 1, 1}, {-1, 1}, { 1,-1}, {-1,-1},
            { 1, 0}, {-1, 0}, { 0, 1}, { 0,-1}
    };
    private static volatile double  noiseOffX = 0, noiseOffZ = 0;

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
        noisePerm          = buildPerm(worldSeed);
        noisePermDetail    = buildPerm(worldSeed ^ 0x9E3779B97F4A7C15L);
        noisePermFineDetail = buildPerm(worldSeed ^ 0x9E3779B97F4A7C15L ^ 0x632BE59BD9B4E019L);
        noiseOffX = ((worldSeed       & 0xFFFFL) / 65536.0) * 1000.0;
        noiseOffZ = ((worldSeed >> 16 & 0xFFFFL) / 65536.0) * 1000.0;
        SubbiomeResolver.initSeed(worldSeed);
        SlopeSurfaceResolver.initSeed(worldSeed);
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

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
                    chunk.setBlockState(new BlockPos(lx, y, lz), state, 3);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    private static final Logger LOGGER = LogUtils.getLogger();
    private static volatile boolean loggedBuildSurface = false;

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structures,
                             RandomState random, ChunkAccess chunk) {
        if (!loggedBuildSurface) {
            loggedBuildSurface = true;
            ChunkPos cp0 = chunk.getPos();
            LOGGER.info("[GoT][DEBUG] GotChunkGenerator.buildSurface: FIRST CALL — dimension={}, chunk=({},{})",
                    region.getLevel().dimension().identifier(), cp0.x, cp0.z);
        }

        vanilla.buildSurface(region, structures, random, chunk);
        WallWorldGen.buildWallInChunk(chunk);
        SlopeSurfaceResolver.applySlopeBlocks(chunk, region);
    }

    private static final int STRUCTURE_WATER_AVOID_RADIUS = 12;

    private static final int STRUCTURE_WATER_SAMPLE_STEPS = 12;

    @Override
    public void createStructures(@NotNull RegistryAccess registryAccess, @NotNull ChunkGeneratorStructureState structureState,
                                 @NotNull StructureManager structures, @NotNull ChunkAccess chunk,
                                 @NotNull StructureTemplateManager templateManager, @NotNull ResourceKey<Level> dimension) {
        super.createStructures(registryAccess, structureState, structures, chunk, templateManager, dimension);

        for (Map.Entry<Structure, StructureStart> entry : chunk.getAllStarts().entrySet()) {
            StructureStart start = entry.getValue();
            if (start == null || !start.isValid()) continue;

            if (isNearWater(start.getBoundingBox())) {
                LOGGER.info("[GoT] Cancelled structure {} at {} — too close to water",
                        entry.getKey(), start.getBoundingBox());
                chunk.setStartForStructure(entry.getKey(), StructureStart.INVALID_START);
            }
        }
    }

    private boolean isNearWater(BoundingBox box) {
        int minX = box.minX() - STRUCTURE_WATER_AVOID_RADIUS;
        int maxX = box.maxX() + STRUCTURE_WATER_AVOID_RADIUS;
        int minZ = box.minZ() - STRUCTURE_WATER_AVOID_RADIUS;
        int maxZ = box.maxZ() + STRUCTURE_WATER_AVOID_RADIUS;
        int sea  = getSeaLevel();

        int stepX = Math.max(1, (maxX - minX) / STRUCTURE_WATER_SAMPLE_STEPS);
        int stepZ = Math.max(1, (maxZ - minZ) / STRUCTURE_WATER_SAMPLE_STEPS);

        for (int x = minX; x <= maxX; x += stepX) {
            for (int z = minZ; z <= maxZ; z += stepZ) {
                if (computeSurfaceY(x, z) <= sea) return true;
            }
        }
        return false;
    }

    private static final int RAW_SURFACE_CACHE_CAPACITY = 256;

    private static final ThreadLocal<LinkedHashMap<Long, Float>> RAW_SURFACE_CACHE =
            ThreadLocal.withInitial(() -> new LinkedHashMap<>(RAW_SURFACE_CACHE_CAPACITY * 4 / 3, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<Long, Float> eldest) {
                    return size() > RAW_SURFACE_CACHE_CAPACITY;
                }
            });

    private static long packColumnKey(int worldX, int worldZ) {
        return ((long) worldX << 32) ^ (worldZ & 0xFFFFFFFFL);
    }

    public static int computeSurfaceY(int worldX, int worldZ) {
        return Mth.floor(computeRawSurfaceY(worldX, worldZ));
    }

    public static float computeRawSurfaceY(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;

        Map<Long, Float> cache = RAW_SURFACE_CACHE.get();
        long key = packColumnKey(worldX, worldZ);
        Float cached = cache.get(key);
        if (cached != null) return cached;

        float result = computeRawSurfaceYUncached(worldX, worldZ);
        cache.put(key, result);
        return result;
    }

    private static float computeRawSurfaceYUncached(int worldX, int worldZ) {
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
                BiomeTerrainParams.Params p = paramsAt(px, pz);

                float cellH = p.baseHeight();
                float cellV = p.heightVariation();

                if (!p.isWater()) {
                    float[] noiseOut = { -1f };
                    SubbiomeDef sub = SubbiomeResolver.resolveTerrain(
                            p.biomeId(), worldX, worldZ, noiseOut);

                    if (sub != null && noiseOut[0] >= 0f) {
                        float weight = (float) Math.pow(Mth.clamp(noiseOut[0], 0f, 1f), HEIGHT_BLEND_CURVE);

                        float subH = sub.baseHeight()      >= 0 ? sub.baseHeight()      : cellH;
                        float subV = sub.heightVariation() >= 0 ? sub.heightVariation() : cellV;

                        cellH = Mth.lerp(weight, cellH, subH);
                        cellV = Mth.lerp(weight, cellV, subV);
                    }
                }

                if (MountainSlopemapResolver.isLoaded()) {
                    float rampWeight = MountainSlopemapResolver.rampWeight(px, pz);
                    if (rampWeight > 0f) {
                        cellH = Mth.lerp(rampWeight, MountainSlopemapResolver.FOOT_HEIGHT, cellH);
                        cellV = Mth.lerp(rampWeight, FOOT_HEIGHT_VARIATION, cellV);

                        float ridgeWeight  = MountainSlopemapResolver.ridgeWeight(px, pz);
                        float ridgeFactor  = RIDGE_VALLEY_FLOOR
                                + (RIDGE_SHOULDER_FRACTION - RIDGE_VALLEY_FLOOR) * ridgeWeight;

                        float passWeight = MountainSlopemapResolver.passWeight(px, pz) * ridgeWeight;
                        ridgeFactor = ridgeFactor + (RIDGE_VALLEY_FLOOR - ridgeFactor) * passWeight;

                        float peakWeight   = MountainSlopemapResolver.peakWeight(px, pz);
                        float totalFactor  = ridgeFactor + peakWeight * (1f - ridgeFactor);

                        float foldWeight = MountainSlopemapResolver.foldWeight(px, pz);
                        float foldFactor = 1f - MountainSlopemapResolver.FOLD_STRENGTH * rampWeight * (1f - foldWeight);
                        totalFactor *= foldFactor;

                        cellH = MountainSlopemapResolver.FOOT_HEIGHT
                                + (cellH - MountainSlopemapResolver.FOOT_HEIGHT) * totalFactor;
                    }
                }

                h[row * 4 + col] = cellH;
                v[row * 4 + col] = cellV;
            }
        }

        float rawHeight       = bicubicBspline(h, fx, fz);
        float heightVariation = bicubicBspline(v, fx, fz);

        double ox = noiseOffX + worldX;
        double oz = noiseOffZ + worldZ;

        double noiseVal = computeTerrainNoise(ox, oz);

        float finalHeight = rawHeight + (float) noiseVal * heightVariation;

        return finalHeight;
    }

    // Ported from Middle Earth mod's MiddleEarthChunkGenerator#buildSurface structure-adaptation logic.
    // Rigid, beard_box pieces get a hard flatten to their floor level directly under their footprint,
    // fading back out to natural terrain over a margin around the edges.
    private static final float STRUCTURE_ADAPT_MARGIN = 10f;
    private static final float STRUCTURE_ADAPT_DIRT_HEIGHT = 1f;

    private static float computeBlendedSurfaceY(int wx, int wz, List<StructureStart> starts) {
        float naturalY = computeRawSurfaceY(wx, wz);
        if (starts.isEmpty()) return naturalY;

        float newHeight = naturalY;
        float bestInfluence = 0f;

        outer:
        for (StructureStart start : starts) {
            Structure structure = start.getStructure();
            if (structure.terrainAdaptation() != TerrainAdjustment.BEARD_BOX) continue;

            for (StructurePiece piece : start.getPieces()) {
                if (!(piece instanceof PoolElementStructurePiece pep)) continue;
                if (pep.getElement().getProjection() != StructureTemplatePool.Projection.RIGID) continue;

                BoundingBox box = piece.getBoundingBox();
                float floorY = box.minY() - STRUCTURE_ADAPT_DIRT_HEIGHT;

                int minX = box.minX(), maxX = box.maxX();
                int minZ = box.minZ(), maxZ = box.maxZ();

                // quick reject: outside the margin-expanded box entirely
                if (wx < minX - STRUCTURE_ADAPT_MARGIN - 1 || wx > maxX + STRUCTURE_ADAPT_MARGIN + 1
                        || wz < minZ - STRUCTURE_ADAPT_MARGIN - 1 || wz > maxZ + STRUCTURE_ADAPT_MARGIN + 1) {
                    continue;
                }

                if (wx >= minX && wx <= maxX && wz >= minZ && wz <= maxZ) {
                    // directly under the piece's footprint: fully flatten to its floor
                    bestInfluence = 1.0f;
                    newHeight = floorY;
                    break outer;
                } else {
                    // within the margin outside the footprint: fade toward the floor by distance to the nearest edge
                    double dx = Math.max(0, Math.max(minX - wx, wx - maxX));
                    double dz = Math.max(0, Math.max(minZ - wz, wz - maxZ));
                    float distanceToEdge = (float) Math.sqrt(dx * dx + dz * dz);

                    float influence = 1.0f - Math.min(1.0f, distanceToEdge / STRUCTURE_ADAPT_MARGIN);
                    if (influence > bestInfluence) {
                        bestInfluence = influence;
                        newHeight = Mth.lerp(influence, naturalY, floorY);
                    }
                }
            }
        }
        return newHeight;
    }

    static float bicubicBspline(float[] grid, float fx, float fz) {
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

    private static BiomeTerrainParams.Params paramsAt(int px, int pz) {
        if (px < 0 || pz < 0
                || px >= BiomemapLoader.getWidth()
                || pz >= BiomemapLoader.getHeight()) {
            return BiomeTerrainParams.FALLBACK;
        }
        return BiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level,
                             @NotNull RandomState random) {
        int surface = computeSurfaceY(x, z);

        if (type == Heightmap.Types.WORLD_SURFACE || type == Heightmap.Types.WORLD_SURFACE_WG) {
            int sea = getSeaLevel();
            if (surface < sea) return sea;
        }
        return surface;
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

        BiomeTerrainParams.Params p =
                BiomeTerrainParams.forColor(BiomemapLoader.getRawPixel(px, pz));
        float rawY  = computeRawSurfaceY(pos.getX(), pos.getZ());
        int   surfY = Mth.floor(rawY);

        info.add(String.format(
                "[GoT] Y=%d  raw=%.1f  nearestPixelBase=%.0f  px=(%d,%d)",
                surfY, rawY, p.baseHeight(), px, pz));
        info.add("[GoT] " + SlopeSurfaceResolver.debugInfo(p.biomeId(), pos.getX(), pos.getZ()));
    }

    public static double computeTerrainNoise(double x, double z) {
        // Base shape — large smooth hills
        double base       = simplexEval(noisePerm,           x / BASE_NOISE_SCALE_X,       z / BASE_NOISE_SCALE_Z);
        // Detail layer — smaller rolling ridges, weighted down
        double detail      = simplexEval(noisePermDetail,     x / DETAIL_SCALE_X,           z / DETAIL_SCALE_Z);
        // Third hill layer — even smaller rolling hills, blended in as real shape
        double fineDetail  = simplexEval(noisePermFineDetail, x / FINE_DETAIL_SCALE_X,      z / FINE_DETAIL_SCALE_Z);

        double sum = base + detail * DETAIL_WEIGHT + fineDetail * FINE_DETAIL_WEIGHT;
        return sum / (1.0 + DETAIL_WEIGHT + FINE_DETAIL_WEIGHT);
    }

    public static double computeTerrainNoiseAtWorldPos(double worldX, double worldZ) {
        return computeTerrainNoise(noiseOffX + worldX, noiseOffZ + worldZ);
    }

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