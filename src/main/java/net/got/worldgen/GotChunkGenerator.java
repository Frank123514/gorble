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
 * <p>This class is <b>only</b> responsible for terrain <em>shape</em>:
 * <ul>
 *   <li>{@link #fillFromNoise} — fills stone / water / air and carves caves.</li>
 *   <li>{@link #getBaseHeight} / {@link #getBaseColumn} — height queries.</li>
 * </ul>
 *
 * <p>Surface blocks (grass, sand, snow, etc.) are handled entirely by the
 * biome JSON {@code surface_rule} definitions and the noise_settings file.
 * There is no slope-map logic, no block-layering code, and no
 * {@code buildSurface} override here — vanilla's default implementation
 * applies the biome surface rules correctly on top of our stone skeleton.
 *
 * <h2>Terrain pipeline (computeSurfaceY)</h2>
 * <ol>
 *   <li>Coordinate projection — world blocks to fractional pixel coords.</li>
 *   <li>Turbulence warp — Value-noise displacement makes biome boundaries meander.</li>
 *   <li>Radial cosine-bell blend — accumulates baseY and scale from surrounding pixels.</li>
 *   <li>5-octave fractal Value noise — 1/f amplitude, per-biome scale.</li>
 *   <li>Water-depth damping — noise shrinks under deep water.</li>
 *   <li>Ridge amplifier — sharpens peaks for high-altitude biomes.</li>
 *   <li>Wetland oscillation — sinusoidal micro-bumps for marshy biomes.</li>
 *   <li>Waterway SDF carving — channel / levee / floodplain cross-section.</li>
 * </ol>
 */
public final class GotChunkGenerator extends ChunkGenerator {

    public static final int SEA_LEVEL = 61;

    // Turbulence warp
    private static final float WARP_FREQ     = 1f / 290f;
    private static final float WARP_STRENGTH = 4.2f;
    private static final float WPX1 = 2.37f,  WPZ1 = 8.91f;
    private static final float WPX2 = 13.55f, WPZ2 = 5.04f;

    // Radial cosine-bell pixel blend
    private static final int BLEND_RADIUS = 5;

    // 5-octave fractal Value noise
    private static final double NOISE_BASE_X = 195.0;
    private static final double NOISE_BASE_Z = 165.0;
    private static final double LACUNARITY   = 0.48;
    private static final float  NOISE_NORM   = 1f + 0.5f + 0.25f + 0.125f + 0.0625f;

    // Ridge amplifier
    private static final float  RIDGE_THRESHOLD = SEA_LEVEL + 50f;
    private static final double RIDGE_STRETCH   = 32.0;
    private static final float  RIDGE_AMP       = 18f;

    // Water-depth noise damping
    private static final float WATER_DAMP_SCALE = 4.0f;

    // Wetland oscillation
    private static final float WETLAND_BAND = 3.5f;
    private static final float WETLAND_FX   = 0.031f;
    private static final float WETLAND_FZ   = 0.019f;
    private static final float WETLAND_AMP  = 2.8f;

    // Waterway SDF carving
    private static final float CHANNEL_HALF  = 26f;
    private static final float LEVEE_WIDTH   = 36f;
    private static final float FLOODPLAIN_W  = 148f;
    private static final float CHANNEL_DEPTH = 7f;
    private static final float LEVEE_FLOOR   = 9f;
    private static final int   RIVER_SCAN_R  = 6;

    // Cave system
    private static final float BLOB_HSCALE = 55f;
    private static final float BLOB_VSCALE = 48f;
    private static final float VEIN_HSCALE = 82f;
    private static final float MASK_SCALE  = 95f;
    private static final float BLOB_CARVE  = 0.38f;
    private static final float MASK_GATE   = 0.72f;
    private static final float VEIN_CARVE  = 0.085f;

    // Codec / vanilla delegate
    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator       vanilla;
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
                             int spawnPixelX, int spawnPixelZ) {
        super(biomeSource);
        this.settings    = settings;
        this.spawnPixelX = spawnPixelX;
        this.spawnPixelZ = spawnPixelZ;
        this.vanilla     = new NoiseBasedChunkGenerator(biomeSource, settings);
        configuredSpawnPixelX = spawnPixelX;
        configuredSpawnPixelZ = spawnPixelZ;
    }

    public static int getConfiguredSpawnPixelX() { return configuredSpawnPixelX; }
    public static int getConfiguredSpawnPixelZ() { return configuredSpawnPixelZ; }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

    // ─────────────────────────────────────────────────────────────────────
    // fillFromNoise — stone skeleton + cave carving, nothing else
    // ─────────────────────────────────────────────────────────────────────

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender, @NotNull RandomState random,
            @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {

        NoiseSettings ns = settings.value().noiseSettings();
        int minY = ns.minY();
        int maxY = minY + ns.height();
        int sea  = getSeaLevel();
        ChunkPos cp = chunk.getPos();

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = cp.getBlockX(lx);
                int wz = cp.getBlockZ(lz);
                int surfaceY = computeSurfaceY(wx, wz);

                for (int y = minY; y < maxY; y++) {
                    BlockState state;
                    if (y > surfaceY) {
                        state = (y <= sea)
                                ? settings.value().defaultFluid()
                                : Blocks.AIR.defaultBlockState();
                    } else if (isCavoid(wx, y, wz)) {
                        state = Blocks.AIR.defaultBlockState();
                    } else {
                        state = Blocks.STONE.defaultBlockState();
                    }
                    chunk.setBlockState(new BlockPos(lx, y, lz), state, false);
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    // ─────────────────────────────────────────────────────────────────────
    // Cave carving — blob chambers + vein tunnels, gated by mask
    // ─────────────────────────────────────────────────────────────────────

    private static boolean isCavoid(int x, int y, int z) {
        float mask = valueNoise3(x / MASK_SCALE, y / MASK_SCALE, z / MASK_SCALE);
        if (mask > MASK_GATE) return false;

        double ty  = Math.tan(y / (double) BLOB_VSCALE);
        float blob  = valueNoise3(x / BLOB_HSCALE, ty, z / BLOB_HSCALE);
        blob       += 0.45f * valueNoise3(x / (BLOB_HSCALE * 0.5f),
                y / (BLOB_VSCALE * 0.5f),
                z / (BLOB_HSCALE * 0.5f));
        blob /= 1.45f;
        if (blob > BLOB_CARVE) return false;

        float v1 = Math.abs(valueNoise3(x / (VEIN_HSCALE * 1.4f), ty, z / (VEIN_HSCALE * 1.4f)));
        float v2 = Math.abs(valueNoise3((z + 72341f) / VEIN_HSCALE, y / BLOB_VSCALE, x / VEIN_HSCALE));
        float v3 = Math.abs(valueNoise3((x + 913877f) / (VEIN_HSCALE * 0.6f),
                y / BLOB_VSCALE, (z + 413171f) / (VEIN_HSCALE * 0.6f)));
        return (v1 + v2 + v3) / 3f < VEIN_CARVE;
    }

    // ─────────────────────────────────────────────────────────────────────
    // computeSurfaceY — terrain height pipeline
    // ─────────────────────────────────────────────────────────────────────

    public static int computeSurfaceY(int worldX, int worldZ) {
        if (!BiomemapLoader.isLoaded()) return SEA_LEVEL;

        float[] wc  = warpedPixelCoord(worldX, worldZ);
        float cx = wc[0], cz = wc[1];
        int  icx = (int) Math.floor(cx), icz = (int) Math.floor(cz);

        // Cosine-bell blend of surrounding pixels
        float totalW = 0f, sumBaseY = 0f, sumScale = 0f;
        for (int dx = -BLEND_RADIUS; dx <= BLEND_RADIUS; dx++) {
            for (int dz = -BLEND_RADIUS; dz <= BLEND_RADIUS; dz++) {
                float pdx = (icx + dx) - cx, pdz = (icz + dz) - cz;
                float w   = blendWeight(pdx, pdz);
                if (w <= 0f) continue;
                GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(
                        BiomemapLoader.getRawPixel(icx + dx, icz + dz) & 0xFFFFFF);
                totalW   += w;
                sumBaseY += p.baseY() * w;
                sumScale += p.scale() * w;
            }
        }
        float avgBase  = sumBaseY / totalW;
        float avgScale = sumScale / totalW;

        // Fractal noise, water-depth damped
        float raw = fractalNoise2D(worldX, worldZ);
        float depth = avgBase - SEA_LEVEL;
        if (depth < 0f)
            raw /= Math.max(1f, Math.min(5f, -depth / WATER_DAMP_SCALE));
        float noiseH = raw * avgScale;

        // Ridge amplifier for high-altitude biomes
        if (avgBase >= RIDGE_THRESHOLD) {
            float over = (avgBase / RIDGE_THRESHOLD) - 1f;
            float r1 = 1f - Math.abs((float) valueNoise2(worldX / RIDGE_STRETCH, worldZ / RIDGE_STRETCH));
            float r2 = 1f - Math.abs((float) valueNoise2(worldX / (RIDGE_STRETCH * 0.5), worldZ / (RIDGE_STRETCH * 0.5)));
            noiseH  += (r1 * 0.65f + r2 * 0.35f) * RIDGE_AMP * over;
            avgBase += avgBase * over * 0.18f;
        }

        float totalH = avgBase + noiseH;

        // Wetland oscillation
        if (depth >= -WETLAND_BAND && depth < 0f) {
            float wx2 = worldX * WETLAND_FX, wz2 = worldZ * WETLAND_FZ;
            totalH += (float)(Math.sin(wx2) * Math.cos(wz2)
                    + 0.4f * Math.sin(wx2 * 2.1f + 1.3f) * Math.sin(wz2 * 1.7f + 0.8f)) * WETLAND_AMP;
        }

        // Waterway SDF
        float wd     = waterwaySdf(cx, cz);
        float lvEdge = CHANNEL_HALF + LEVEE_WIDTH;
        float plEdge = lvEdge + FLOODPLAIN_W;

        float shapedH = totalH;
        if (wd < plEdge) {
            float floor = SEA_LEVEL + LEVEE_FLOOR;
            if (shapedH > floor) {
                float t = wd / plEdge; t = t * t * (3f - 2f * t);
                shapedH -= (1f - t) * (shapedH - floor);
            }
        }
        if (wd < CHANNEL_HALF)
            return Mth.floor(SEA_LEVEL - CHANNEL_DEPTH - Math.max(0f, raw * 0.15f * avgScale));
        if (wd < lvEdge) {
            float bedY = SEA_LEVEL - CHANNEL_DEPTH - Math.max(0f, raw * 0.15f * avgScale);
            float t    = (wd - CHANNEL_HALF) / LEVEE_WIDTH; t = t * t * (3f - 2f * t);
            return Mth.floor(Mth.lerp(t, bedY, shapedH));
        }
        return Mth.floor(shapedH);
    }

    // ─────────────────────────────────────────────────────────────────────
    // Warp + SDF helpers (also aliased for GotBiomeSource below)
    // ─────────────────────────────────────────────────────────────────────

    static float[] warpedPixelCoord(int worldX, int worldZ) {
        float rawCx = worldX / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
        float rawCz = worldZ / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;
        float wx = (float) valueNoise2(rawCx * WARP_FREQ + WPX1, rawCz * WARP_FREQ + WPZ1);
        float wz = (float) valueNoise2(rawCx * WARP_FREQ + WPX2, rawCz * WARP_FREQ + WPZ2);
        return new float[]{ rawCx + wx * WARP_STRENGTH, rawCz + wz * WARP_STRENGTH };
    }

    static float waterwaySdf(float cx, float cz) {
        int icx = (int) Math.floor(cx), icz = (int) Math.floor(cz);
        float nearSq = Float.MAX_VALUE;
        for (int dx = -RIVER_SCAN_R; dx <= RIVER_SCAN_R; dx++) {
            for (int dz = -RIVER_SCAN_R; dz <= RIVER_SCAN_R; dz++) {
                if (!GotBiomeTerrainParams.forColor(
                        BiomemapLoader.getRawPixel(icx + dx, icz + dz)).isRiver()) continue;
                float ddx = (icx + dx + 0.5f) - cx, ddz = (icz + dz + 0.5f) - cz;
                float sq  = ddx * ddx + ddz * ddz;
                if (sq < nearSq) nearSq = sq;
            }
        }
        return nearSq == Float.MAX_VALUE
                ? Float.MAX_VALUE
                : (float) Math.sqrt(nearSq) * BiomemapLoader.MAP_SCALE;
    }

    /** Back-compat alias for GotBiomeSource. */
    static float[] warpCoord(float rawCx, float rawCz) {
        float wx = (float) valueNoise2(rawCx * WARP_FREQ + WPX1, rawCz * WARP_FREQ + WPZ1);
        float wz = (float) valueNoise2(rawCx * WARP_FREQ + WPX2, rawCz * WARP_FREQ + WPZ2);
        return new float[]{ rawCx + wx * WARP_STRENGTH, rawCz + wz * WARP_STRENGTH };
    }

    /** Back-compat alias for GotBiomeSource. */
    static float riverSdf(float cx, float cz) { return waterwaySdf(cx, cz); }

    // ─────────────────────────────────────────────────────────────────────
    // Blend weight  (cosine-bell, zero outside BLEND_RADIUS)
    // ─────────────────────────────────────────────────────────────────────

    private static float blendWeight(float pdx, float pdz) {
        float r = BLEND_RADIUS + 0.5f;
        float d2 = pdx * pdx + pdz * pdz;
        if (d2 > r * r) return 0f;
        float angle = (float)(Math.PI * Math.sqrt(d2) / (2.0 * BLEND_RADIUS));
        if (angle >= Math.PI * 0.5f) return 0f;
        float c = (float) Math.cos(angle);
        return c * c;
    }

    // ─────────────────────────────────────────────────────────────────────
    // Fractal Value noise  (5 octaves, 1/f decay)
    // ─────────────────────────────────────────────────────────────────────

    private static float fractalNoise2D(int worldX, int worldZ) {
        double x = worldX, z = worldZ, bx = NOISE_BASE_X, bz = NOISE_BASE_Z;
        double n  = 1.000 * valueNoise2(x / bx, z / bz); bx *= LACUNARITY; bz *= LACUNARITY;
        n        += 0.500 * valueNoise2(x / bx, z / bz); bx *= LACUNARITY; bz *= LACUNARITY;
        n        += 0.250 * valueNoise2(x / bx, z / bz); bx *= LACUNARITY; bz *= LACUNARITY;
        n        += 0.125 * valueNoise2(x / bx, z / bz); bx *= LACUNARITY; bz *= LACUNARITY;
        n        += 0.0625 * valueNoise2(x / bx, z / bz);
        return (float)(n / NOISE_NORM);
    }

    // ─────────────────────────────────────────────────────────────────────
    // Value noise  (hash-lattice, quintic fade curve)
    // ─────────────────────────────────────────────────────────────────────

    private static double fade(double t) { return t * t * t * (t * (t * 6.0 - 15.0) + 10.0); }
    private static double lerp(double a, double b, double t) { return a + t * (b - a); }

    private static double latticeHash(int ix, int iz) {
        int h = ix * 1664525 + iz * 1013904223 + 0x9e3779b9;
        h ^= (h >>> 13); h *= 0x5c4d7e83; h ^= (h >>> 17);
        return (h & 0x7FFFFFFF) / (double) 0x7FFFFFFF * 2.0 - 1.0;
    }

    private static double latticeHash3(int ix, int iy, int iz) {
        int h = ix * 1664525 + iy * 214013 + iz * 1013904223 + 0x9e3779b9;
        h ^= (h >>> 13); h *= 0x5c4d7e83; h ^= (h >>> 7); h *= 0xd1b54a35; h ^= (h >>> 17);
        return (h & 0x7FFFFFFF) / (double) 0x7FFFFFFF * 2.0 - 1.0;
    }

    static double valueNoise2(double x, double z) {
        int ix = (int) Math.floor(x), iz = (int) Math.floor(z);
        double fx = fade(x - ix), fz = fade(z - iz);
        return lerp(lerp(latticeHash(ix, iz),   latticeHash(ix+1, iz),   fx),
                lerp(latticeHash(ix, iz+1),  latticeHash(ix+1, iz+1), fx), fz);
    }

    static float valueNoise3(double x, double y, double z) {
        int ix = (int) Math.floor(x), iy = (int) Math.floor(y), iz = (int) Math.floor(z);
        double fx = fade(x-ix), fy = fade(y-iy), fz = fade(z-iz);
        double lo = lerp(lerp(latticeHash3(ix,  iy,  iz),   latticeHash3(ix+1,iy,  iz),   fx),
                lerp(latticeHash3(ix,  iy+1,iz),   latticeHash3(ix+1,iy+1,iz),   fx), fy);
        double hi = lerp(lerp(latticeHash3(ix,  iy,  iz+1), latticeHash3(ix+1,iy,  iz+1), fx),
                lerp(latticeHash3(ix,  iy+1,iz+1), latticeHash3(ix+1,iy+1,iz+1), fx), fy);
        return (float) lerp(lo, hi, fz);
    }

    // ─────────────────────────────────────────────────────────────────────
    // ChunkGenerator boilerplate
    // ─────────────────────────────────────────────────────────────────────

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
        int minY = level.getMinY(), surface = computeSurfaceY(x, z), sea = getSeaLevel();
        BlockState[] states = new BlockState[level.getHeight()];
        for (int i = 0; i < states.length; i++) {
            int y = minY + i;
            states[i] = y <= surface ? Blocks.STONE.defaultBlockState()
                    : y <= sea     ? settings.value().defaultFluid()
                      :                Blocks.AIR.defaultBlockState();
        }
        return new NoiseColumn(minY, states);
    }

    @Override
    public void applyCarvers(@NotNull WorldGenRegion region, long seed,
                             @NotNull RandomState random, @NotNull BiomeManager biomeManager,
                             @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {
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
    public void addDebugScreenInfo(java.util.List<String> info, RandomState random, BlockPos pos) {
        if (!BiomemapLoader.isLoaded()) return;
        float[] wc = warpedPixelCoord(pos.getX(), pos.getZ());
        GotBiomeTerrainParams.Params p = GotBiomeTerrainParams.forColor(
                BiomemapLoader.getRawPixel(Math.round(wc[0]), Math.round(wc[1])));
        int surfY = computeSurfaceY(pos.getX(), pos.getZ());
        float cave = valueNoise3(pos.getX() / BLOB_HSCALE, pos.getY() / (double) BLOB_VSCALE,
                pos.getZ() / BLOB_HSCALE);
        info.add(String.format("[GoT] Y=%d base=%.0f scale=%.2f cave=%.2f %s px=(%.1f,%.1f)",
                surfY, p.baseY(), p.scale(), cave, p.isWater() ? "WATER" : "land", wc[0], wc[1]));
    }
}