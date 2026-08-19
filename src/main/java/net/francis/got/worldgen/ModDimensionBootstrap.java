package net.francis.got.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;

import java.util.List;
import java.util.Map;

/**
 * Code-driven definition of the "knownworld" dimension and world preset.
 * Registered against a RegistrySetBuilder in
 * net.francis.got.data.GotWorldgenDatapackProvider, which turns this into the
 * actual datapack JSON at datagen time (./gradlew runClientData).
 */
public final class ModDimensionBootstrap {

    public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        Holder<DimensionType> dimensionType = dimensionTypes.getOrThrow(ModDimensions.KNOWNWORLD_DIMENSION_TYPE);
        Holder<NoiseGeneratorSettings> settings = noiseSettings.getOrThrow(ModDimensions.KNOWNWORLD_NOISE_SETTINGS);

        List<Holder<Biome>> biomeList = ModDimensions.KNOWNWORLD_BIOMES.stream()
                .<Holder<Biome>>map(biomes::getOrThrow)
                .toList();

        GotBiomeSource biomeSource = new GotBiomeSource(biomeList);
        GotChunkGenerator generator = new GotChunkGenerator(
                biomeSource,
                settings,
                ModDimensions.SPAWN_PIXEL_X,
                ModDimensions.SPAWN_PIXEL_Z
        );

        context.register(ModDimensions.KNOWNWORLD_LEVEL_STEM, new LevelStem(dimensionType, generator));
    }

    public static void bootstrapWorldPreset(BootstrapContext<WorldPreset> context) {
        HolderGetter<LevelStem> levelStems = context.lookup(Registries.LEVEL_STEM);
        LevelStem knownworld = levelStems.getOrThrow(ModDimensions.KNOWNWORLD_LEVEL_STEM).value();

        context.register(
                ModDimensions.KNOWNWORLD_WORLD_PRESET,
                new WorldPreset(Map.of(LevelStem.OVERWORLD, knownworld))
        );
    }

    private ModDimensionBootstrap() {}
}