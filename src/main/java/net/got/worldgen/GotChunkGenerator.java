package net.got.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class GotChunkGenerator extends ChunkGenerator {

    /* ============================================================= */
    /* VANILLA SETTINGS + DELEGATE                                   */
    /* ============================================================= */

    private final Holder<NoiseGeneratorSettings> settings;
    private final NoiseBasedChunkGenerator vanilla;

    /* ============================================================= */
    /* NOISE                                                        */
    /* ============================================================= */

    private PerlinSimplexNoise continentNoise;
    private PerlinSimplexNoise detailNoise;

    /* ============================================================= */
    /* CODEC                                                        */
    /* ============================================================= */

    public static final MapCodec<GotChunkGenerator> CODEC =
            RecordCodecBuilder.mapCodec(i -> i.group(
                    BiomeSource.CODEC.fieldOf("biome_source")
                            .forGetter(ChunkGenerator::getBiomeSource),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings")
                            .forGetter(g -> g.settings)
            ).apply(i, GotChunkGenerator::new));

    public GotChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
        super(biomeSource);
        this.settings = settings;

        // 🔑 Vanilla delegate (NEVER call fillFromNoise on this)
        this.vanilla = new NoiseBasedChunkGenerator(biomeSource, settings);
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    /* ============================================================= */
    /* NOISE INIT                                                   */
    /* ============================================================= */

    private void ensureNoise(RandomState state) {
        if (continentNoise != null) return;

        RandomSource rand = state.getOrCreateRandomFactory(
                ResourceLocation.fromNamespaceAndPath("got", "terrain")
        ).at(0, 0, 0);

        continentNoise = new PerlinSimplexNoise(rand, List.of(-6));
        detailNoise    = new PerlinSimplexNoise(rand, List.of(-2));
    }

    /* ============================================================= */
    /* TERRAIN FILL (YOU OWN THIS)                                  */
    /* ============================================================= */

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender,
            @NotNull RandomState random,
            @NotNull StructureManager structures,
            @NotNull ChunkAccess chunk
    ) {
        ensureNoise(random);

        NoiseSettings noise = settings.value().noiseSettings();
        int minY = noise.minY();
        int maxY = minY + noise.height();
        int sea  = getSeaLevel();

        ChunkPos pos = chunk.getPos();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

                int wx = pos.getBlockX(x);
                int wz = pos.getBlockZ(z);
                int height = getTerrainHeight(wx, wz);

                for (int y = minY; y < maxY; y++) {
                    BlockState state =
                            y <= height
                                    ? Blocks.STONE.defaultBlockState()
                                    : y <= sea
                                    ? settings.value().defaultFluid()
                                    : Blocks.AIR.defaultBlockState();

                    chunk.setBlockState(
                            new BlockPos(x, y, z),
                            state,
                            false
                    );
                }
            }
        }

        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return settings.value().seaLevel();
    }

    @Override
    public int getMinY() {
        return settings.value().noiseSettings().minY();
    }

    /* ============================================================= */
    /* VANILLA SYSTEMS (DELEGATED)                                  */
    /* ============================================================= */

    @Override
    public void applyCarvers(
            WorldGenRegion region,
            long seed,
            RandomState random,
            BiomeManager biomeManager,
            StructureManager structures,
            ChunkAccess chunk
    ) {
        // ✔️ Vanilla caves + aquifers
        vanilla.applyCarvers(region, seed, random, biomeManager, structures, chunk);
    }

    @Override
    public void buildSurface(
            WorldGenRegion region,
            StructureManager structures,
            RandomState random,
            ChunkAccess chunk
    ) {
        // ✔️ Vanilla surface rules (grass, sand, snow, etc)
        vanilla.buildSurface(region, structures, random, chunk);
    }

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion region) {
        ChunkPos pos = region.getCenter();
        Holder<Biome> biome = region.getBiome(
                pos.getWorldPosition().atY(region.getMaxY() - 1)
        );

        WorldgenRandom rand = new WorldgenRandom(RandomSource.create());
        rand.setDecorationSeed(
                region.getSeed(),
                pos.getMinBlockX(),
                pos.getMinBlockZ()
        );

        NaturalSpawner.spawnMobsForChunkGeneration(region, biome, pos, rand);
    }

    @Override
    public int getGenDepth() {
        return settings.value().noiseSettings().height();
    }

    /* ============================================================= */
    /* HEIGHT QUERIES (STRUCTURES / FEATURES)                        */
    /* ============================================================= */

    @Override
    public int getBaseHeight(
            int x,
            int z,
            Heightmap.@NotNull Types type,
            @NotNull LevelHeightAccessor level,
            @NotNull RandomState random
    ) {
        ensureNoise(random);
        return getTerrainHeight(x, z);
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(
            int x,
            int z,
            @NotNull LevelHeightAccessor level,
            @NotNull RandomState random
    ) {
        ensureNoise(random);

        int minY = level.getMinY();
        int height = getTerrainHeight(x, z);
        int sea = getSeaLevel();

        BlockState[] states = new BlockState[level.getHeight()];

        for (int i = 0; i < states.length; i++) {
            int y = minY + i;
            states[i] =
                    y <= height
                            ? Blocks.STONE.defaultBlockState()
                            : y <= sea
                            ? settings.value().defaultFluid()
                            : Blocks.AIR.defaultBlockState();
        }

        return new NoiseColumn(minY, states);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState random, BlockPos pos) {

    }

    /* ============================================================= */
    /* TERRAIN SHAPE                                                */
    /* ============================================================= */

    private int getTerrainHeight(int x, int z) {
        // 1️⃣ Sample heightmap (pixel space)
        float raw = HeightmapLoader.getHeightAtWorld(x, z);

        // 2️⃣ Normalize heightmap value (0–1)
        float t = Mth.clamp(
                (raw - HeightmapLoader.getMinHeight()) /
                        (HeightmapLoader.getMaxHeight() - HeightmapLoader.getMinHeight()),
                0f,
                1f
        );

        // 3️⃣ Map to world Y range (THIS IS THE FIX)
        int minLand = 64;    // plains baseline
        int maxLand = 220;   // mountain max

        double baseHeight = Mth.lerp(t, minLand, maxLand);

        // 4️⃣ Apply noise as DETAIL, not base
        double continent =
                continentNoise.getValue(
                        x / 160.0,
                        z / 160.0,
                        false
                ) * 12.0;

        double detail =
                detailNoise.getValue(
                        x / 24.0,
                        z / 24.0,
                        false
                ) * 3.0;

        return Mth.floor(baseHeight + continent + detail);
    }
}