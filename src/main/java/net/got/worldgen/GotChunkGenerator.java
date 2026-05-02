package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.worldgen.layer.GotBiomeRegistry;
import net.got.worldgen.layer.GotSubBiomeSampler;
import net.got.worldgen.surface.GotBiomeSurfaces;
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
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Chunk generator using the LOTR Renewed terrain algorithm, ported to 1.21.4.
 */
public final class GotChunkGenerator extends ChunkGenerator {

    public static final int SEA_LEVEL = 63;

    private static final int NOISE_SIZE_XZ = 4;
    private static final int NOISE_SIZE_Y  = 48;
    private static final int CELL_H = 4;
    private static final int CELL_V = 8;

    private static final int SAMPLE_RADIUS = 6;
    private static final int SAMPLE_WIDTH  = 13;
    private static final float[] BIOME_SIGNIFICANCE = new float[SAMPLE_WIDTH * SAMPLE_WIDTH];

    static {
        for (int dz = -SAMPLE_RADIUS; dz <= SAMPLE_RADIUS; dz++) {
            for (int dx = -SAMPLE_RADIUS; dx <= SAMPLE_RADIUS; dx++) {
                float f = 10.0f / Mth.sqrt(dx * dx + dz * dz + 0.2f);
                BIOME_SIGNIFICANCE[(dz + SAMPLE_RADIUS) * SAMPLE_WIDTH + (dx + SAMPLE_RADIUS)] = f;
            }
        }
    }

    // ── Codec ─────────────────────────────────────────────────────────────

    public static final MapCodec<GotChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(g -> g.settings)
            ).apply(i, GotChunkGenerator::new));

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator vanilla;
    private volatile boolean seedPushed = false;

    public GotChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
        super(biomeSource);
        this.settings = settings;
        this.vanilla  = new NoiseBasedChunkGenerator(biomeSource, settings);
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

    // ── Terrain generation ────────────────────────────────────────────────

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender, @NotNull RandomState random,
            @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {

        // Derive and push the level seed to the biome source on first chunk gen.
        // Same pattern as the original GotChunkGenerator — use getOrCreateRandomFactory
        // to extract a stable long that varies per world seed.
        if (!seedPushed) {
            long s = random.getOrCreateRandomFactory(net.got.GotMod.id("layer_seed"))
                    .at(new BlockPos(0, 0, 0)).nextLong();
            GotBiomeSource.setSeed(s);
            seedPushed = true;
        }

        NoiseSettings ns = settings.value().noiseSettings();
        int minY  = ns.minY();
        int maxY  = minY + ns.height();
        int sea   = getSeaLevel();
        ChunkPos cp = chunk.getPos();
        int baseX = cp.getMinBlockX();
        int baseZ = cp.getMinBlockZ();

        // Pre-warm the layer cache for the region this chunk will sample.
        // The 13×13 biome window is centred on each noise column, so we need
        // (chunkNoiseMin - SAMPLE_RADIUS) .. (chunkNoiseMax + SAMPLE_RADIUS).
        if (getBiomeSource() instanceof GotBiomeSource gbs) {
            int noiseMinX = (baseX >> 2) - SAMPLE_RADIUS - 1;
            int noiseMinZ = (baseZ >> 2) - SAMPLE_RADIUS - 1;
            int noiseMaxX = (baseX >> 2) + NOISE_SIZE_XZ + SAMPLE_RADIUS + 1;
            int noiseMaxZ = (baseZ >> 2) + NOISE_SIZE_XZ + SAMPLE_RADIUS + 1;
            gbs.prewarm(noiseMinX, noiseMinZ, noiseMaxX, noiseMaxZ);
        }

        int COLS = NOISE_SIZE_XZ + 1;
        int ROWS = NOISE_SIZE_Y  + 1;

        double[][][] grid = new double[COLS][COLS][ROWS];
        for (int cx = 0; cx < COLS; cx++) {
            for (int cz = 0; cz < COLS; cz++) {
                int noiseX = (baseX >> 2) + cx;
                int noiseZ = (baseZ >> 2) + cz;
                double[] col = buildNoiseColumn(noiseX, noiseZ, random);
                for (int cy = 0; cy < ROWS; cy++) {
                    grid[cx][cz][cy] = (cy < col.length) ? col[cy] : -30.0;
                }
            }
        }

        BlockPos.MutableBlockPos mp = new BlockPos.MutableBlockPos();
        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int   cx = lx / CELL_H;
                int   cz = lz / CELL_H;
                float tx = (lx % CELL_H) / (float) CELL_H;
                float tz = (lz % CELL_H) / (float) CELL_H;

                for (int y = minY; y < maxY; y++) {
                    int   cy = (y - minY) / CELL_V;
                    float ty = ((y - minY) % CELL_V) / (float) CELL_V;
                    if (cy >= NOISE_SIZE_Y) break;

                    double density = trilinear(tx, ty, tz,
                            grid[cx    ][cz    ][cy    ], grid[cx + 1][cz    ][cy    ],
                            grid[cx    ][cz + 1][cy    ], grid[cx + 1][cz + 1][cy    ],
                            grid[cx    ][cz    ][cy + 1], grid[cx + 1][cz    ][cy + 1],
                            grid[cx    ][cz + 1][cy + 1], grid[cx + 1][cz + 1][cy + 1]);

                    BlockState state;
                    if      (density > 0.0) state = Blocks.STONE.defaultBlockState();
                    else if (y <= sea)      state = settings.value().defaultFluid();
                    else                    state = Blocks.AIR.defaultBlockState();

                    chunk.setBlockState(mp.set(lx, y, lz), state, false);
                }
            }
        }
        // Classic 5-layer bedrock: minY is always bedrock, minY+1..minY+4 are
        // bedrock with decreasing probability. fillFromNoise previously never
        // wrote BEDROCK at all, leaving an open void at the bottom of the world.
        WorldgenRandom bedrockRng = new WorldgenRandom(RandomSource.create());
        bedrockRng.setDecorationSeed(0L, baseX, baseZ);
        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                for (int layer = 0; layer < 5; layer++) {
                    int y = minY + layer;
                    if (layer == 0 || bedrockRng.nextInt(layer + 1) == 0) {
                        chunk.setBlockState(mp.set(lx, y, lz), Blocks.BEDROCK.defaultBlockState(), false);
                    }
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    // ── LOTR noise column ─────────────────────────────────────────────────

    private double[] buildNoiseColumn(int noiseX, int noiseZ, RandomState random) {
        double[] col    = new double[NOISE_SIZE_Y + 1];
        double[] ds     = getBiomeDepthScale(noiseX, noiseZ);
        double avgDepth = ds[0];
        double avgScale = ds[1];

        // Two noise passes blended by a third — matches LOTR's OctavesNoiseGenerator
        // Using SURFACE and SURFACE_SECONDARY which are available in 1.21.4
        NormalNoise n1 = random.getOrCreateNoise(Noises.SURFACE);
        NormalNoise n2 = random.getOrCreateNoise(Noises.SURFACE_SECONDARY);

        double scaleXZ = 684.412 / 80.0;
        double scaleY  = 684.412 / 5000.0;

        for (int y = 0; y <= NOISE_SIZE_Y; y++) {
            double wx = noiseX * scaleXZ;
            double wy = y      * scaleY;
            double wz = noiseZ * scaleXZ;

            double raw1  = n1.getValue(wx,       wy,       wz);
            double raw2  = n2.getValue(wx * 0.5, wy * 0.5, wz * 0.5);
            // Use n1 at a different scale as the blender weight (avoids needing a 3rd noise key)
            double blend = Mth.clamp(n1.getValue(wx * 0.25, 10.0, wz * 0.25) * 0.5 + 0.5, 0.0, 1.0);
            // NormalNoise amplitude is ≈ ±128 (matching LOTR's OctavesNoiseGenerator).
            // The original code divided by 512 which is a ~96× amplitude mismatch,
            // flattening terrain almost to zero and causing everything to be underwater.
            double raw   = Mth.lerp(blend, raw1, raw2) * 128.0;

            double d = raw - terrainGradient(avgDepth, avgScale, y);

            if (y > NOISE_SIZE_Y - 4) d = Mth.clampedLerp(d,  3.0, (y - (NOISE_SIZE_Y - 4)) / -10.0);
            else if (y < 1)           d = Mth.clampedLerp(d, -30.0, (1.0 - y));

            col[y] = d;
        }
        return col;
    }

    private static double terrainGradient(double depth, double scale, int y) {
        // seaRef is the noise-cell index of sea level, computed dynamically.
        // In 1.21.4: minY=-64, CELL_V=8 → seaRef = (63-(-64))/8 = 15.875.
        final double seaRef = (SEA_LEVEL - (-64)) / (double) CELL_V;

        // The LOTR convention is: depth > 0 → terrain ABOVE sea level,
        //                         depth < 0 → terrain BELOW sea level (ocean/deep ocean).
        // The gradient shifts the noise baseline so that density=0 (surface) occurs at:
        //   y_surface = seaRef + depth * seaRef/2
        // which means depth must ADD to seaRef (not subtract).
        // An earlier version subtracted here, inverting all terrain heights so that
        // deep-ocean biomes generated as mountains and mountains generated underground.
        double d = (y - seaRef - depth * seaRef / 8.0 * 4.0) * 12.0 * 128.0 / 256.0 / scale;
        if (d < 0.0) d *= 4.0;
        return d;
    }

    // ── 13×13 biome depth/scale sampling ─────────────────────────────────

    private double[] getBiomeDepthScale(int noiseX, int noiseZ) {
        float totalScale = 0, totalDepth = 0, totalSig = 0;
        float centralDepth = getBiomeDepth(noiseX, noiseZ);

        for (int dk = -SAMPLE_RADIUS; dk <= SAMPLE_RADIUS; dk++) {
            for (int dl = -SAMPLE_RADIUS; dl <= SAMPLE_RADIUS; dl++) {
                float depth = getBiomeDepth(noiseX + dk, noiseZ + dl);
                float scale = getBiomeScale(noiseX + dk, noiseZ + dl);
                if (scale == 0f) scale = 1e-7f;

                int   idx    = (dk + SAMPLE_RADIUS) * SAMPLE_WIDTH + (dl + SAMPLE_RADIUS);
                float sig    = BIOME_SIGNIFICANCE[idx];
                float modSig = sig / (depth + 2.0f);
                if (depth > centralDepth) modSig /= 2.0f;
                if (depth < -0.2f && depth > -1.0f) modSig *= 5.0f;

                totalScale += scale * modSig;
                totalDepth += depth * modSig;
                totalSig   += modSig;
            }
        }

        float avgDepth = totalDepth / totalSig;
        float avgScale = totalScale / totalSig;

        if (centralDepth < 0f && avgDepth >= 0f) {
            avgDepth = Mth.lerp(0.5f, avgDepth, centralDepth / 2.0f);
        }
        avgDepth = (avgDepth * 4.0f - 1.0f) / 8.0f;

        return new double[]{ avgDepth, Math.max(0.001, avgScale) };
    }

    private float getBiomeDepth(int nx, int nz) {
        return GotSubBiomeSampler.effectiveDepth(sampleBiomeId(nx, nz), nx * CELL_H, nz * CELL_H);
    }

    private float getBiomeScale(int nx, int nz) {
        return GotSubBiomeSampler.effectiveScale(sampleBiomeId(nx, nz), nx * CELL_H, nz * CELL_H);
    }

    private int sampleBiomeId(int nx, int nz) {
        if (getBiomeSource() instanceof GotBiomeSource gbs) return gbs.sampleId(nx, nz);
        return GotBiomeRegistry.ID_NORTH;
    }

    // ── Surface builder ───────────────────────────────────────────────────

    @Override
    public void buildSurface(@NotNull WorldGenRegion region, @NotNull StructureManager structures,
                             @NotNull RandomState random, @NotNull ChunkAccess chunk) {
        vanilla.buildSurface(region, structures, random, chunk);
        applyGotSurface(region, chunk, random);
    }

    private void applyGotSurface(WorldGenRegion region, ChunkAccess chunk, RandomState random) {
        NormalNoise surfNoise = random.getOrCreateNoise(Noises.SURFACE);
        NormalNoise secNoise  = random.getOrCreateNoise(Noises.SURFACE_SECONDARY);
        ChunkPos pos = chunk.getPos();
        int baseX = pos.getMinBlockX();
        int baseZ = pos.getMinBlockZ();
        RandomSource rand = random.getOrCreateRandomFactory(net.got.GotMod.id("surface"))
                .at(new BlockPos(baseX, 0, baseZ));

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = baseX + lx;
                int wz = baseZ + lz;
                int sy = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, lx, lz);

                var biomeKey = region.getBiome(new BlockPos(wx, sy, wz)).unwrapKey();
                if (biomeKey.isEmpty()) continue;
                GotBiomeSurfaces.BiomeConfig cfg =
                        GotBiomeSurfaces.getConfig(biomeKey.get().location().getPath());
                if (cfg == null) continue;

                BlockPos bp = new BlockPos(lx, sy, lz);
                BlockState top = chunk.getBlockState(bp);
                if (!top.isSolid()) continue;

                if (cfg.stoneAbove >= 0 && sy >= cfg.stoneAbove) {
                    chunk.setBlockState(bp, Blocks.STONE.defaultBlockState(), false);
                } else if (cfg.snowBlockAbove >= 0 && sy >= cfg.snowBlockAbove) {
                    chunk.setBlockState(bp, Blocks.SNOW_BLOCK.defaultBlockState(), false);
                } else if (cfg.powderSnowAbove >= 0 && sy >= cfg.powderSnowAbove) {
                    chunk.setBlockState(bp, Blocks.POWDER_SNOW.defaultBlockState(), false);
                }

                if (cfg.podzol && top.is(Blocks.GRASS_BLOCK)
                        && secNoise.getValue(wx, 12, wz) > 0.5) {
                    int r = rand.nextInt(100);
                    if      (r < 45) chunk.setBlockState(bp, Blocks.PODZOL.defaultBlockState(), false);
                    else if (r < 60) chunk.setBlockState(bp, Blocks.COARSE_DIRT.defaultBlockState(), false);
                }

                if (cfg.hasPatch()) {
                    double nv = cfg.useSecondary
                            ? secNoise.getValue(wx, 0, wz)
                            : surfNoise.getValue(wx, 0, wz);
                    if (nv >= cfg.minThreshold && nv <= cfg.maxThreshold) {
                        BlockState cur = chunk.getBlockState(bp);
                        if (!cur.is(Blocks.SNOW_BLOCK) && !cur.is(Blocks.POWDER_SNOW)
                                && !cur.is(Blocks.BEDROCK)) {
                            chunk.setBlockState(bp, cfg.mainPatch.pick(rand), false);
                        }
                    }
                }
            }
        }
    }

    // ── Boilerplate ───────────────────────────────────────────────────────

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types type,
                             @NotNull LevelHeightAccessor level, @NotNull RandomState random) {
        float depth = GotBiomeRegistry.getDepth(sampleBiomeId(x >> 2, z >> 2));
        return Mth.clamp(Math.round(depth * 17.0f + 64.0f), level.getMinY(), level.getMaxY());
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z,
                                              @NotNull LevelHeightAccessor level,
                                              @NotNull RandomState random) {
        int minY  = level.getMinY();
        int sea   = getSeaLevel();
        int surfY = getBaseHeight(x, z, Heightmap.Types.OCEAN_FLOOR_WG, level, random);
        BlockState[] states = new BlockState[level.getHeight()];
        for (int i = 0; i < states.length; i++) {
            int y = minY + i;
            if      (y < surfY) states[i] = Blocks.STONE.defaultBlockState();
            else if (y <= sea)  states[i] = settings.value().defaultFluid();
            else                states[i] = Blocks.AIR.defaultBlockState();
        }
        return new NoiseColumn(minY, states);
    }

    @Override
    public void applyCarvers(@NotNull WorldGenRegion region, long seed,
                             @NotNull RandomState random, @NotNull BiomeManager biomes,
                             @NotNull StructureManager structures, @NotNull ChunkAccess chunk) {
        vanilla.applyCarvers(region, seed, random, biomes, structures, chunk);
    }

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion region) {
        ChunkPos cp = region.getCenter();
        Holder<Biome> b = region.getBiome(cp.getWorldPosition().atY(region.getMaxY() - 1));
        WorldgenRandom rng = new WorldgenRandom(RandomSource.create());
        rng.setDecorationSeed(region.getSeed(), cp.getMinBlockX(), cp.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(region, b, cp, rng);
    }

    @Override
    public void addDebugScreenInfo(@NotNull List<String> info,
                                   @NotNull RandomState random, @NotNull BlockPos pos) {
        int id = sampleBiomeId(pos.getX() >> 2, pos.getZ() >> 2);
        info.add("GoT Biome: " + GotBiomeRegistry.locationFor(id).getPath());
    }

    @Override public int getSeaLevel() { return SEA_LEVEL; }
    @Override public int getMinY()     { return settings.value().noiseSettings().minY(); }
    @Override public int getGenDepth() { return settings.value().noiseSettings().height(); }

    // ── Trilinear interpolation ───────────────────────────────────────────

    private static double trilinear(float tx, float ty, float tz,
                                    double d000, double d100, double d010, double d110,
                                    double d001, double d101, double d011, double d111) {
        return lerp(ty,
                lerp(tz, lerp(tx, d000, d100), lerp(tx, d010, d110)),
                lerp(tz, lerp(tx, d001, d101), lerp(tx, d011, d111)));
    }

    private static double lerp(double t, double a, double b) { return a + t * (b - a); }
}