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

    private static final float BEACH_T    = 0.01f;
    private static final int   BEACH_TOP_Y = 63;   // where Zone 3 ends
    private static final int   LAND_MAX_Y  = 280;

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
     *   -0.05 < t <= 0      Zone 2  coastal blend      (Y 40–61)  eases from zone1 floor → SEA_LEVEL
     *   0 < t <= BEACH_T    Zone 3  beach              (Y 61–63)  eases from SEA_LEVEL  → BEACH_TOP_Y
     *   BEACH_T < t <= 1    Zone 4  land               (Y 63–280) eases from BEACH_TOP_Y → LAND_MAX_Y
     *
     * Each zone uses the previous zone's endpoint as its own start value,
     * exactly the same pattern as the Zone 1 → Zone 2 riverbank transition.
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

        // Zone 2 — coastal ease-in: starts from zone1 floor, rises to SEA_LEVEL
        if (t <= 0f) {
            float u = (t - OV) / -OV;
            float dist = HeightmapLoader.nearestLandDistanceF(worldX, worldZ, RIVER_PROX_RADIUS);
            float zoneOneFloor = (dist <= RIVER_PROX_RADIUS)
                    ? Mth.lerp(smoothstep(1f - dist / RIVER_PROX_RADIUS), OCEAN_FLOOR, RIVER_FLOOR_Y)
                    : OCEAN_FLOOR;
            return Mth.floor(Mth.lerp(easeIn(u, COASTAL_EASE_POWER), zoneOneFloor, SEA_LEVEL));
        }

        // Zone 3 — beach: starts from SEA_LEVEL (zone2 endpoint), rises to BEACH_TOP_Y
        if (t <= BEACH_T) {
            return Mth.floor(Mth.lerp(smoothstep(t / BEACH_T), SEA_LEVEL, BEACH_TOP_Y));
        }

        // Zone 4 — land: starts from BEACH_TOP_Y (zone3 endpoint), rises to LAND_MAX_Y
        return Mth.floor(Mth.lerp(smoothstep((t - BEACH_T) / (1f - BEACH_T)), BEACH_TOP_Y, LAND_MAX_Y));
    }

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
        float dist = HeightmapLoader.nearestLandDistanceF(pos.getX(), pos.getZ(), RIVER_PROX_RADIUS);
        info.add(String.format("[GoT] elev=%.3f  Y=%d  sea=%d  landDist=%.2fpx",
                elev, elevToY(elev, pos.getX(), pos.getZ()), SEA_LEVEL, dist));
    }
}