package net.got.worldgen.biome;

import net.got.GotMod;
import net.got.init.GotModEntities;
import net.got.sounds.ModSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class GotBiomes {

    // ─────────────────────────────────────────────────────────────────────────
    // Resource Keys
    // ─────────────────────────────────────────────────────────────────────────

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

    // got:patch_leaves — registered in data gen but not yet in GotPlacedFeatures
    private static final ResourceKey<PlacedFeature> PATCH_LEAVES =
            ResourceKey.create(Registries.PLACED_FEATURE, GotMod.id("patch_leaves"));

    // ─────────────────────────────────────────────────────────────────────────
    // Bootstrap
    // ─────────────────────────────────────────────────────────────────────────

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
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Shared generation helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Adds the three universal overworld feature layers shared by every GOT biome:
     * amethyst geodes (LOCAL_MODIFICATIONS), vanilla underground variety ores
     * (UNDERGROUND_ORES), and surface freezing (TOP_LAYER_MODIFICATION).
     */
    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    /** Adds the four GOT regional rock pockets (granite, limestone, flint, slate). */
    private static void addRockOres(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.ORE_GREY_GRANITE_ROCK);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.ORE_LIMESTONE_ROCK);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.ORE_FLINT_ROCK);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.ORE_SLATE_ROCK);
    }

    /**
     * Adds GOT disk features (got:disk_sand, got:disk_clay) plus vanilla
     * minecraft:disk_gravel at the UNDERGROUND_ORES step.
     * Used by rivers, barrowlands, north, and neck biomes.
     */
    private static void addGotDisks(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.DISK_SAND);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.DISK_CLAY);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_GRAVEL);
    }

    /**
     * Adds fully vanilla disk features (minecraft:disk_sand, minecraft:disk_clay,
     * minecraft:disk_gravel) at UNDERGROUND_ORES. Used by ocean and deep-ocean biomes.
     */
    private static void addVanillaDisks(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_SAND);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_GRAVEL);
    }

    /**
     * Wild crops and blackberry placed at UNDERGROUND_ORES, matching the JSON data
     * for north_hills and sheepshead_hills where they appear in that step.
     */
    private static void addWildCropsOre(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.WILD_BARLEY);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.WILD_COTTON);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.WILD_RYE);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.WILD_WHEAT);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.WILD_OAT);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.WILD_ONION);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.WILD_GARLIC);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.WILD_CARROT);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.WILD_CABBAGE);
        b.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GotPlacedFeatures.BLACKBERRY_BUSH);
    }

    /** Four GOT boulder variants placed at VEGETAL_DECORATION. */
    private static void addBoulders(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.BOULDER);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.LIMESTONE_BOULDER);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.GREY_GRANITE_BOULDER);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.SLATE_BOULDER);
    }

    /**
     * Open-land vegetation tail shared by barrowlands and north:
     * grass patches, fern patches, all wild crops, blackberry bush, and patch_leaves.
     * Placed at VEGETAL_DECORATION.
     */
    private static void addOpenLandVegetation(BiomeGenerationSettings.Builder b) {
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WILD_BARLEY);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WILD_COTTON);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WILD_RYE);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WILD_WHEAT);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WILD_OAT);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WILD_ONION);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WILD_GARLIC);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WILD_CARROT);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WILD_CABBAGE);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.BLACKBERRY_BUSH);
        b.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_LEAVES);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Biome methods
    // ─────────────────────────────────────────────────────────────────────────

    // Always Winter ────────────────────────────────────────────────────────────

    private static Biome alwaysWinter(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.creatureGenerationProbability(0.07f);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WEIRWOOD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(-0.5f)
                .downfall(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(0)
                        .skyColor(7964853)
                        .grassColorOverride(294167)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // Barrowlands ──────────────────────────────────────────────────────────────

    private static Biome barrowlands(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP,  12, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW,    8, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE,  5, 2, 6));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 3));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.HAWTHORN_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.ASH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.BEECH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.PINE_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.SHRUB);
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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8103167)
                        .grassColorOverride(2391380)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_MEADOW))
                        .build())
                .build();
    }

    // Deep Ocean ───────────────────────────────────────────────────────────────

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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8103167)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // Frostfangs ───────────────────────────────────────────────────────────────

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
                        .waterFogColor(329011)
                        .fogColor(0)
                        .skyColor(7964853)
                        .grassColorOverride(12433480)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JAGGED_PEAKS))
                        .build())
                .build();
    }

    // Frozen Lake ──────────────────────────────────────────────────────────────

    private static Biome frozenLake(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        // Icebergs at LOCAL_MODIFICATIONS (alongside the amethyst geode added by globalOverworldGeneration)
        biomeBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.ICEBERG_PACKED);
        biomeBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.ICEBERG_BLUE);

        // Blue ice at SURFACE_STRUCTURES
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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8364543)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // Frozen River ─────────────────────────────────────────────────────────────

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
                        .waterFogColor(329011)
                        .fogColor(0)
                        .skyColor(7964853)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // Haunted Forest ───────────────────────────────────────────────────────────

    private static Biome hauntedForest(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF,   8, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX,    8, 2, 4));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS_2);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.IRONWOOD_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.SENTINAL);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.SOLDIER_PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.ASH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WEIRWOOD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(-0.5f)
                .downfall(0.4f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4020182)
                        .waterFogColor(329011)
                        .fogColor(0)
                        .skyColor(7964853)
                        .grassColorOverride(294167)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // Iron Hills ───────────────────────────────────────────────────────────────

    private static Biome ironHills(BootstrapContext<Biome> context) {
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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8233727)
                        .grassColorOverride(294167)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // Ironwood ─────────────────────────────────────────────────────────────────

    private static Biome ironwood(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS_2);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.ASPEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.IRONWOOD);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.SENTINAL);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.FIR);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.SOLDIER_PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.HAWTHORN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WEIRWOOD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.25f)
                .downfall(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8233983)
                        .grassColorOverride(2391380)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(ModSounds.WINDY_FOREST))
                        .build())
                .build();
    }

    // Lake ─────────────────────────────────────────────────────────────────────

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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8103167)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // The Neck ─────────────────────────────────────────────────────────────────

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
                        .waterFogColor(5077600)
                        .fogColor(12638463)
                        .skyColor(7907327)
                        .foliageColorOverride(9285927)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SWAMP))
                        .build())
                .build();
    }

    // Neck River ───────────────────────────────────────────────────────────────

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
                        .waterFogColor(5077600)
                        .fogColor(12638463)
                        .skyColor(8103167)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // The North ────────────────────────────────────────────────────────────────

    private static Biome north(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(GotModEntities.NORTHMAN.get(),     10, 2, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(GotModEntities.NORTH_BOWMAN.get(),  4, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(GotModEntities.NORTH_WARRIOR.get(), 4, 1, 2));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT,  4, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP,  12, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG,    10, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN,10, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW,     8, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE,   5, 2, 6));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY,  1, 1, 3));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addGotDisks(biomeBuilder);
        addRockOres(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.HAWTHORN_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.ASH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.BEECH_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.FIR_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.SHRUB);
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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(7907327)
                        .grassColorOverride(2391380)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // North Hills ──────────────────────────────────────────────────────────────

    private static Biome northHills(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addRockOres(biomeBuilder);
        // Wild crops appear at UNDERGROUND_ORES in the JSON for this biome
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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8233727)
                        .grassColorOverride(2391380)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // North Mountains ──────────────────────────────────────────────────────────

    private static Biome northMountains(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8560639)
                        .grassColorOverride(2391380)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SNOWY_SLOPES))
                        .build())
                .build();
    }

    // Ocean ────────────────────────────────────────────────────────────────────

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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8103167)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // River ────────────────────────────────────────────────────────────────────

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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8103167)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // Sheepshead Hills ─────────────────────────────────────────────────────────

    private static Biome sheepsheadHills(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP,  15, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE,   5, 2, 6));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY,  1, 1, 3));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        addRockOres(biomeBuilder);
        // Wild crops appear at UNDERGROUND_ORES in the JSON for this biome
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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8233727)
                        .grassColorOverride(2391380)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // Stony Shore ──────────────────────────────────────────────────────────────

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
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8233727)
                        .grassColorOverride(2391380)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    // Wolfswood ────────────────────────────────────────────────────────────────

    private static Biome wolfswood(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF,   8, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX,    8, 2, 4));

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS_2);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.ASPEN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.IRONWOOD_SPARSE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.SENTINAL);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.FIR);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.BEECH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.SOLDIER_PINE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.HAWTHORN);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.ASH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GotPlacedFeatures.WEIRWOOD);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.25f)
                .downfall(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8233983)
                        .grassColorOverride(2391380)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(ModSounds.WINDY_FOREST))
                        .build())
                .build();
    }
}