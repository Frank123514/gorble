package net.francis.got.worldgen.biome;

import net.francis.got.GotMod;
import net.francis.got.init.ModEntities;
import net.francis.got.sounds.ModSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;

import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class Biomes {

    public static final ResourceKey<Biome> ALWAYS_WINTER =
            ResourceKey.create(Registries.BIOME, GotMod.id("always_winter"));

    public static final ResourceKey<Biome> BARROWLANDS =
            ResourceKey.create(Registries.BIOME, GotMod.id("barrowlands"));

    public static final ResourceKey<Biome> DEEP_OCEAN =
            ResourceKey.create(Registries.BIOME, GotMod.id("deep_ocean"));

    public static final ResourceKey<Biome> FROSTFANGS =
            ResourceKey.create(Registries.BIOME, GotMod.id("frostfangs"));

    public static final ResourceKey<Biome> FROZEN_LAKE =
            ResourceKey.create(Registries.BIOME, GotMod.id("frozen_lake"));

    public static final ResourceKey<Biome> FROZEN_RIVER =
            ResourceKey.create(Registries.BIOME, GotMod.id("frozen_river"));

    public static final ResourceKey<Biome> HAUNTED_FOREST =
            ResourceKey.create(Registries.BIOME, GotMod.id("haunted_forest"));

    public static final ResourceKey<Biome> IRON_HILLS =
            ResourceKey.create(Registries.BIOME, GotMod.id("iron_hills"));

    public static final ResourceKey<Biome> IRONWOOD =
            ResourceKey.create(Registries.BIOME, GotMod.id("ironwood"));

    public static final ResourceKey<Biome> LAKE =
            ResourceKey.create(Registries.BIOME, GotMod.id("lake"));

    public static final ResourceKey<Biome> NECK =
            ResourceKey.create(Registries.BIOME, GotMod.id("neck"));

    public static final ResourceKey<Biome> NECK_RIVER =
            ResourceKey.create(Registries.BIOME, GotMod.id("neck_river"));

    public static final ResourceKey<Biome> NORTH =
            ResourceKey.create(Registries.BIOME, GotMod.id("north"));

    public static final ResourceKey<Biome> NORTH_HILLS =
            ResourceKey.create(Registries.BIOME, GotMod.id("north_hills"));

    public static final ResourceKey<Biome> NORTH_MOUNTAINS =
            ResourceKey.create(Registries.BIOME, GotMod.id("north_mountains"));

    public static final ResourceKey<Biome> OCEAN =
            ResourceKey.create(Registries.BIOME, GotMod.id("ocean"));

    public static final ResourceKey<Biome> RIVER =
            ResourceKey.create(Registries.BIOME, GotMod.id("river"));

    public static final ResourceKey<Biome> SHEEPSHEAD_HILLS =
            ResourceKey.create(Registries.BIOME, GotMod.id("sheepshead_hills"));

    public static final ResourceKey<Biome> STONY_SHORE =
            ResourceKey.create(Registries.BIOME, GotMod.id("stony_shore"));

    public static final ResourceKey<Biome> WOLFSWOOD =
            ResourceKey.create(Registries.BIOME, GotMod.id("wolfswood"));

    public static final ResourceKey<Biome> RIVERLANDS =
            ResourceKey.create(Registries.BIOME, GotMod.id("riverlands"));

    public static final ResourceKey<Biome> VALE =
            ResourceKey.create(Registries.BIOME, GotMod.id("vale"));

    public static final ResourceKey<Biome> WESTERLANDS =
            ResourceKey.create(Registries.BIOME, GotMod.id("westerlands"));

    public static final ResourceKey<Biome> REACH =
            ResourceKey.create(Registries.BIOME, GotMod.id("reach"));

    private static final ResourceKey<PlacedFeature> PATCH_LEAVES =
            ResourceKey.create(Registries.PLACED_FEATURE, GotMod.id("patch_leaves"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(ALWAYS_WINTER,    alwaysWinter(context));
        context.register(BARROWLANDS,      barrowlands(context));
        context.register(DEEP_OCEAN,       deepOcean(context));
        context.register(FROSTFANGS,       frostfangs(context));
        context.register(FROZEN_LAKE,      frozenLake(context));
        context.register(FROZEN_RIVER,     frozenRiver(context));
        context.register(HAUNTED_FOREST,   hauntedForest(context));
        context.register(IRON_HILLS,       ironHills(context));
        context.register(IRONWOOD,         ironwood(context));
        context.register(LAKE,             lake(context));
        context.register(NECK,             neck(context));
        context.register(NECK_RIVER,       neckRiver(context));
        context.register(NORTH,            north(context));
        context.register(NORTH_HILLS,      northHills(context));
        context.register(NORTH_MOUNTAINS,  northMountains(context));
        context.register(OCEAN,            ocean(context));
        context.register(RIVER,            river(context));
        context.register(SHEEPSHEAD_HILLS, sheepsheadHills(context));
        context.register(STONY_SHORE,      stonyShore(context));
        context.register(WOLFSWOOD,        wolfswood(context));
        context.register(RIVERLANDS,       riverlands(context));
        context.register(VALE,             vale(context));
        context.register(WESTERLANDS,      westerlands(context));
        context.register(REACH,            reach(context));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    private static void addRockOres(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.ORE_GREY_GRANITE_ROCK);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.ORE_LIMESTONE_ROCK);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.ORE_FLINT_ROCK);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.ORE_SLATE_ROCK);
    }

    private static void addGotDisks(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.DISK_SAND);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.DISK_CLAY);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_GRAVEL);
    }

    private static void addVanillaDisks(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_SAND);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_GRAVEL);
    }

    private static void addWildCropsOre(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.WILD_BARLEY);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.WILD_COTTON);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.WILD_RYE);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.WILD_WHEAT);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.WILD_OAT);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.WILD_ONION);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.WILD_GARLIC);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.WILD_CARROT);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.WILD_CABBAGE);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatures.BLACKBERRY_BUSH);
    }

    private static void addBoulders(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BOULDER);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.LIMESTONE_BOULDER);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.GREY_GRANITE_BOULDER);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SLATE_BOULDER);
    }

    private static void addOpenLandVegetation(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WILD_BARLEY);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WILD_COTTON);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WILD_RYE);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WILD_WHEAT);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WILD_OAT);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WILD_ONION);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WILD_GARLIC);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WILD_CARROT);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WILD_CABBAGE);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BLACKBERRY_BUSH);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_LEAVES);
    }

    private static Biome alwaysWinter(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.creatureGenerationProbability(0.07f);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WEIRWOOD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(-0.5f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(294167)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 7964853)
                
                .build();
    }

    private static Biome barrowlands(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 6));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(ModEntities.NORTHMAN.get(), 2, 5));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 7, new MobSpawnSettings.SpawnerData(ModEntities.STARK_LEVY.get(), 2, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 3, new MobSpawnSettings.SpawnerData(ModEntities.NORTH_SOLDIER.get(), 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.HAWTHORN_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ASH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BEECH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.PINE_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SHRUB);
        addBoulders(biomeBuilder);
        addOpenLandVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(937679)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8103167)
                
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEvents.MUSIC_BIOME_MEADOW))
                .build();
    }

    private static Biome deepOcean(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addVanillaDisks(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_NORMAL);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.KELP_COLD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8103167)
                
                .build();
    }

    private static Biome frostfangs(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(-0.7f)
                .downfall(0.9f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(12433480)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 7964853)
                
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEvents.MUSIC_BIOME_JAGGED_PEAKS))
                .build();
    }

    private static Biome frozenLake(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.ICEBERG_PACKED);
        biomeBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.ICEBERG_BLUE);

        biomeBuilder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.BLUE_ICE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.0f)
                .temperatureAdjustment(Biome.TemperatureModifier.FROZEN)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(3750089)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8364543)
                
                .build();
    }

    private static Biome frozenRiver(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.0f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(3750089)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 7964853)
                
                .build();
    }

    private static Biome hauntedForest(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 4));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS_2);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.IRONWOOD_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SENTINAL);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SOLDIER_PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ASH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WEIRWOOD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(-0.5f)
                .downfall(0.4f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4020182)
                        .grassColorOverride(294167)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 7964853)
                
                .build();
    }

    private static Biome ironHills(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(ModEntities.NORTHMAN.get(), 2, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(ModEntities.STARK_LEVY.get(), 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.2f)
                .downfall(0.3f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(294167)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8233727)
                
                .build();
    }

    private static Biome ironwood(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(ModEntities.NORTHMAN.get(), 1, 3));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS_2);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ASPEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BIRCH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.IRONWOOD);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SENTINAL);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.FIR);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SOLDIER_PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.HAWTHORN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WEIRWOOD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.25f)
                .downfall(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8233983)
                
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(Musics.createGameMusic(ModSounds.WINDY_FOREST)))
                .build();
    }

    private static Biome lake(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8103167)
                
                .build();
    }

    private static Biome neck(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.9f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(3832426)
                        .foliageColorOverride(9285927)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 5077600)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
                
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEvents.MUSIC_BIOME_SWAMP))
                .build();
    }

    private static Biome neckRiver(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_NORMAL);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.KELP_COLD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(3832426)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 5077600)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8103167)
                
                .build();
    }

    private static Biome north(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(ModEntities.NORTHMAN.get(), 2, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(ModEntities.STARK_LEVY.get(), 1, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 3, new MobSpawnSettings.SpawnerData(ModEntities.NORTH_SOLDIER.get(), 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.PIG, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 6));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 3));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.HAWTHORN_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ASH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BEECH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.FIR_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SHRUB);
        addBoulders(biomeBuilder);
        addOpenLandVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.4f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
                
                .build();
    }

    private static Biome northHills(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(ModEntities.NORTHMAN.get(), 2, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(ModEntities.STARK_LEVY.get(), 1, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 3, new MobSpawnSettings.SpawnerData(ModEntities.NORTH_SOLDIER.get(), 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addRockOres(biomeBuilder);
        
        addWildCropsOre(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN);
        addBoulders(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.2f)
                .downfall(0.3f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8233727)
                
                .build();
    }

    private static Biome northMountains(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(ModEntities.NORTHMAN.get(), 1, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(ModEntities.STARK_LEVY.get(), 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(-0.3f)
                .downfall(0.9f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8560639)
                
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEvents.MUSIC_BIOME_SNOWY_SLOPES))
                .build();
    }

    private static Biome northPineGrove(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(ModEntities.NORTHMAN.get(), 2, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(ModEntities.STARK_LEVY.get(), 1, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 2, new MobSpawnSettings.SpawnerData(ModEntities.NORTH_SOLDIER.get(), 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 1, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 3, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 4));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.FIR_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.HAWTHORN_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SHRUB);
        addBoulders(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.6f)
                .downfall(0.6f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 7973647)
                
                .build();
    }

    private static Biome northHighland(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 7, new MobSpawnSettings.SpawnerData(ModEntities.NORTHMAN.get(), 2, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(ModEntities.STARK_LEVY.get(), 1, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 5));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.FIR_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.HAWTHORN_SPARSE);
        addBoulders(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8036351)
                
                .build();
    }

    private static Biome ocean(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addVanillaDisks(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_NORMAL);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.KELP_COLD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8103167)
                
                .build();
    }

    private static Biome river(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8103167)
                
                .build();
    }

    private static Biome sheepsheadHills(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 15, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 6));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(ModEntities.NORTHMAN.get(), 2, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(ModEntities.STARK_LEVY.get(), 1, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(ModEntities.NORTH_SOLDIER.get(), 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addRockOres(biomeBuilder);
        
        addWildCropsOre(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN);
        addBoulders(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.2f)
                .downfall(0.3f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8233727)
                
                .build();
    }

    private static Biome stonyShore(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.2f)
                .downfall(0.3f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8233727)
                
                .build();
    }

    private static Biome wolfswood(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(ModEntities.NORTHMAN.get(), 1, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 3, new MobSpawnSettings.SpawnerData(ModEntities.STARK_LEVY.get(), 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS_2);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ASPEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BIRCH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.IRONWOOD_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SENTINAL);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.FIR);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BEECH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SOLDIER_PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.HAWTHORN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ASH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.WEIRWOOD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.25f)
                .downfall(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8233983)
                
                .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(Musics.createGameMusic(ModSounds.WINDY_FOREST)))
                .build();
    }

    private static Biome riverlands(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.PIG, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 6));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.HAWTHORN_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ASH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BEECH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ELM_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SHRUB);
        addBoulders(biomeBuilder);
        addOpenLandVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.6f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(2391380)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 7907327)
                
                .build();
    }

    private static Biome vale(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.PIG, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 5));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 3, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.HAWTHORN_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ASH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BEECH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ELM_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SHRUB);
        addBoulders(biomeBuilder);
        addOpenLandVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7f)
                .downfall(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(3487232)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8036351)
                
                .build();
    }

    private static Biome westerlands(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.PIG, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 5));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.HAWTHORN_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ASH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BEECH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ELM_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SHRUB);
        addBoulders(biomeBuilder);
        addOpenLandVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(4539008)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 7973647)
                
                .build();
    }

    private static Biome reach(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 15, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityType.PIG, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 6));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.HAWTHORN_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ASH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.BEECH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.ELM_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SHRUB);
        addBoulders(biomeBuilder);
        addOpenLandVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.9f)
                .downfall(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .grassColorOverride(5658112)
                        .build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 329011)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 12638463)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 8103167)
                
                .build();
    }
}