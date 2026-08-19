package net.francis.got.worldgen;

import net.francis.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;

import java.util.List;

public final class ModDimensions {

    public static final ResourceKey<DimensionType> KNOWNWORLD_DIMENSION_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, GotMod.id("knownworld"));

    public static final ResourceKey<LevelStem> KNOWNWORLD_LEVEL_STEM =
            ResourceKey.create(Registries.LEVEL_STEM, GotMod.id("knownworld"));

    public static final ResourceKey<WorldPreset> KNOWNWORLD_WORLD_PRESET =
            ResourceKey.create(Registries.WORLD_PRESET, GotMod.id("knownworld"));

    public static final ResourceKey<NoiseGeneratorSettings> KNOWNWORLD_NOISE_SETTINGS =
            ResourceKey.create(Registries.NOISE_SETTINGS, GotMod.id("overworld"));

    /** Spawn point, in biome-map pixel coordinates (see GotChunkGenerator). */
    public static final int SPAWN_PIXEL_X = 1500;
    public static final int SPAWN_PIXEL_Z = 677;

    public static final List<ResourceKey<Biome>> KNOWNWORLD_BIOMES = List.of(
            biome("north"), biome("barrowlands"), biome("stony_shore"), biome("north_hills"),
            biome("flint_cliffs"), biome("gift"), biome("neck"), biome("ironwood"),
            biome("wolfswood"), biome("north_forest"), biome("ocean"), biome("deep_ocean"),
            biome("river"), biome("neck_river"), biome("frozen_river"), biome("beyond_the_wall"),
            biome("frostfangs"), biome("always_winter"), biome("north_mountains"), biome("haunted_forest"),
            biome("frozen_lake"), biome("iron_hills"), biome("lake"), biome("sheepshead_hills"),
            biome("riverlands"), biome("vale"), biome("mountains_of_the_moon"), biome("westerlands"),
            biome("western_hills"), biome("reach"), biome("stormlands"), biome("lower_reach"),
            biome("dorne"), biome("dorne_desert"), biome("dry_bone_mountains"), biome("central_bone_mountains"),
            biome("white_bone_mountains"), biome("subbiome/beech_maple_forest"), biome("subbiome/wheat_field")
    );

    private static ResourceKey<Biome> biome(String path) {
        return ResourceKey.create(Registries.BIOME, GotMod.id(path));
    }

    private ModDimensions() {}
}
