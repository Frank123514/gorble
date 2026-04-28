package net.got.worldgen.surface;

import net.got.init.GotModBlocks;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

/**
 * Static registry of {@link GotBiomeSurfaceConfig} instances keyed by biome
 * registry path (e.g. {@code "north"}, {@code "north_mountains"}).
 *
 * <p>This is the single place to define what each GoT biome's surface looks like
 * — gravel patches, mountain snow/stone layers, dirt paths, underwater sand
 * coverage, sub-soil geology — all driven by the ported LOTR noise system.
 *
 * <h3>How to add a config for a new biome</h3>
 * <ol>
 *   <li>Create a {@link GotBiomeSurfaceConfig} with
 *       {@link GotBiomeSurfaceConfig#create()}.</li>
 *   <li>Chain the setters you need ({@code setSurfaceNoiseMixer},
 *       {@code setMountainTerrain}, etc.).</li>
 *   <li>Call {@code register("my_biome_name", config)} inside the
 *       {@code static} block.</li>
 * </ol>
 *
 * <h3>Noise mixer quick-reference</h3>
 * <pre>
 *   channel(1) scales(0.4, 0.07) threshold(0.25)  — medium gravel patches
 *   channel(2) scales(0.3, 0.05) threshold(0.30)  — smaller stone patches
 *   channel(3) scales(0.2, 0.04) threshold(0.35)  — sparse coarse dirt
 * </pre>
 */
public final class GotBiomeSurfaces {

    private static final Map<String, GotBiomeSurfaceConfig> REGISTRY = new HashMap<>();

    static {

        // ── NORTH ─────────────────────────────────────────────────────────
        register("north",
                GotBiomeSurfaceConfig.create()
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(1)
                                        .scales(0.4, 0.07)
                                        .threshold(0.30)
                                        .state(Blocks.GRAVEL.defaultBlockState())
                                        .topOnly()
                                        .build(),
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(2)
                                        .scales(0.25, 0.05)
                                        .threshold(0.35)
                                        .state(Blocks.COARSE_DIRT.defaultBlockState())
                                        .topOnly()
                                        .build()
                        ))
        );

        // ── NORTH HILLS ───────────────────────────────────────────────────
        register("north_hills",
                GotBiomeSurfaceConfig.create()
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(1)
                                        .scales(0.4, 0.07)
                                        .threshold(0.22)
                                        .states(
                                                Blocks.GRAVEL.defaultBlockState(), 3,
                                                Blocks.STONE.defaultBlockState(), 1
                                        )
                                        .topOnly()
                                        .build()
                        ))
                        .addSubSoilLayer(Blocks.GRAVEL.defaultBlockState(), 1, 2)
        );

        // ── NORTH MOUNTAINS ───────────────────────────────────────────────
        register("north_mountains",
                GotBiomeSurfaceConfig.create()
                        .setMountainTerrain(GotMountainTerrainProvider.create(
                                GotMountainTerrainProvider.Layer.builder()
                                        .above(145).state(Blocks.POWDER_SNOW.defaultBlockState()).topOnly().build(),
                                GotMountainTerrainProvider.Layer.builder()
                                        .above(130).state(Blocks.SNOW_BLOCK.defaultBlockState()).topOnly().build(),
                                GotMountainTerrainProvider.Layer.builder()
                                        .above(105).useStone().build()
                        ))
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(1)
                                        .scales(0.35, 0.06)
                                        .threshold(0.28)
                                        .state(Blocks.GRAVEL.defaultBlockState())
                                        .topOnly()
                                        .build()
                        ))
                        .addSubSoilLayer(GotModBlocks.GREY_GRANITE_ROCK.get().defaultBlockState(), 2, 4)
        );

        // ── FROSTFANGS ────────────────────────────────────────────────────
        register("frostfangs",
                GotBiomeSurfaceConfig.create()
                        .setMountainTerrain(GotMountainTerrainProvider.create(
                                GotMountainTerrainProvider.Layer.builder()
                                        .above(140).state(Blocks.POWDER_SNOW.defaultBlockState()).topOnly().build(),
                                GotMountainTerrainProvider.Layer.builder()
                                        .above(120).state(Blocks.SNOW_BLOCK.defaultBlockState()).topOnly().build(),
                                GotMountainTerrainProvider.Layer.builder()
                                        .above(90).useStone().build()
                        ))
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(1)
                                        .scales(0.40, 0.08)
                                        .threshold(0.20)
                                        .state(Blocks.STONE.defaultBlockState())
                                        .topOnly()
                                        .build()
                        ))
                        .addSubSoilLayer(Blocks.STONE.defaultBlockState(), 2, 5)
        );

        // ── ALWAYS WINTER ─────────────────────────────────────────────────
        register("always_winter",
                GotBiomeSurfaceConfig.create()
                        .setMountainTerrain(GotMountainTerrainProvider.create(
                                GotMountainTerrainProvider.Layer.builder()
                                        .above(100).state(Blocks.POWDER_SNOW.defaultBlockState()).topOnly().build(),
                                GotMountainTerrainProvider.Layer.builder()
                                        .above(88).state(Blocks.SNOW_BLOCK.defaultBlockState()).topOnly().build()
                        ))
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(2)
                                        .scales(0.30, 0.06)
                                        .threshold(0.32)
                                        .state(Blocks.GRAVEL.defaultBlockState())
                                        .topOnly()
                                        .build()
                        ))
        );

        // ── WOLFSWOOD ─────────────────────────────────────────────────────
        register("wolfswood",
                GotBiomeSurfaceConfig.create()
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(3)
                                        .scales(0.20, 0.04)
                                        .threshold(0.35)
                                        .states(
                                                Blocks.COARSE_DIRT.defaultBlockState(), 3,
                                                Blocks.ROOTED_DIRT.defaultBlockState(), 1
                                        )
                                        .topOnly()
                                        .build()
                        ))
                        .addSubSoilLayer(Blocks.COARSE_DIRT.defaultBlockState(), 1)
        );

        // ── HAUNTED FOREST ────────────────────────────────────────────────
        register("haunted_forest",
                GotBiomeSurfaceConfig.create()
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(1)
                                        .scales(0.35, 0.07)
                                        .threshold(0.28)
                                        .states(
                                                Blocks.COARSE_DIRT.defaultBlockState(), 2,
                                                Blocks.GRAVEL.defaultBlockState(), 1
                                        )
                                        .topOnly()
                                        .build()
                        ))
        );

        // ── IRONWOOD ──────────────────────────────────────────────────────
        register("ironwood",
                GotBiomeSurfaceConfig.create()
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(2)
                                        .scales(0.25, 0.05)
                                        .threshold(0.25)
                                        .states(
                                                Blocks.COARSE_DIRT.defaultBlockState(), 2,
                                                Blocks.ROOTED_DIRT.defaultBlockState(), 2
                                        )
                                        .topOnly()
                                        .build()
                        ))
                        .addSubSoilLayer(Blocks.COARSE_DIRT.defaultBlockState(), 1, 2)
        );

        // ── BARROWLANDS ───────────────────────────────────────────────────
        register("barrowlands",
                GotBiomeSurfaceConfig.create()
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(1)
                                        .scales(0.40, 0.08)
                                        .threshold(0.28)
                                        .states(
                                                Blocks.GRAVEL.defaultBlockState(), 2,
                                                Blocks.COBBLESTONE.defaultBlockState(), 1
                                        )
                                        .topOnly()
                                        .build()
                        ))
                        .addSubSoilLayer(GotModBlocks.LIMESTONE_ROCK.get().defaultBlockState(), 2, 3)
        );

        // ── STONY SHORE ───────────────────────────────────────────────────
        register("stony_shore",
                GotBiomeSurfaceConfig.create()
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(1)
                                        .scales(0.45, 0.09)
                                        .threshold(0.18)
                                        .states(
                                                Blocks.GRAVEL.defaultBlockState(), 4,
                                                Blocks.STONE.defaultBlockState(), 1
                                        )
                                        .topOnly()
                                        .build()
                        ))
                        .setUnderwaterNoiseMixer(GotUnderwaterNoiseMixer.SEA_LATITUDE)
                        .addSubSoilLayer(Blocks.GRAVEL.defaultBlockState(), 2, 3)
        );

        // ── IRON HILLS ────────────────────────────────────────────────────
        register("iron_hills",
                GotBiomeSurfaceConfig.create()
                        .setLocalStone(GotModBlocks.GREY_GRANITE_ROCK.get().defaultBlockState())
                        .setMountainTerrain(GotMountainTerrainProvider.create(
                                GotMountainTerrainProvider.Layer.builder()
                                        .above(110).useStone().build()
                        ))
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(1)
                                        .scales(0.40, 0.08)
                                        .threshold(0.22)
                                        .states(
                                                GotModBlocks.GREY_GRANITE_ROCK.get().defaultBlockState(), 3,
                                                Blocks.GRAVEL.defaultBlockState(), 2
                                        )
                                        .topOnly()
                                        .build()
                        ))
                        .addSubSoilLayer(GotModBlocks.GREY_GRANITE_ROCK.get().defaultBlockState(), 2, 4)
                        .addSubSoilLayer(GotModBlocks.FLINT_ROCK.get().defaultBlockState(), 1, 2)
        );

        // ── NECK ──────────────────────────────────────────────────────────
        // Waterlogged marshland at the coast: mostly vanilla mud/clay, but
        // the underwater floor blends toward sand in southern areas.
        register("neck",
                GotBiomeSurfaceConfig.create()
                        .setUnderwaterNoiseMixer(GotUnderwaterNoiseMixer.SEA_LATITUDE)
        );

        // ── SHEEPSHEAD HILLS ──────────────────────────────────────────────
        register("sheepshead_hills",
                GotBiomeSurfaceConfig.create()
                        .setSurfaceNoiseMixer(GotSurfaceNoiseMixer.createNoiseMixer(
                                GotSurfaceNoiseMixer.Condition.builder()
                                        .channel(2)
                                        .scales(0.35, 0.07)
                                        .threshold(0.30)
                                        .state(Blocks.GRAVEL.defaultBlockState())
                                        .topOnly()
                                        .build()
                        ))
                        .addSubSoilLayer(GotModBlocks.LIMESTONE_ROCK.get().defaultBlockState(), 1, 3)
        );

        // ── OCEAN / DEEP OCEAN ────────────────────────────────────────────
        // Latitude-blended sand/gravel seabed.
        register("ocean",
                GotBiomeSurfaceConfig.create()
                        .setUnderwaterNoiseMixer(GotUnderwaterNoiseMixer.SEA_LATITUDE)
        );
        register("deep_ocean",
                GotBiomeSurfaceConfig.create()
                        .setUnderwaterNoiseMixer(GotUnderwaterNoiseMixer.SEA_LATITUDE)
        );

        // ── RIVERS ────────────────────────────────────────────────────────
        // No special surface config — vanilla clay/gravel is fine.
        // (No register call → getConfig returns null → chunk gen skips the pass.)
    }

    // ── Registry API ──────────────────────────────────────────────────────

    private static void register(String biomeName, GotBiomeSurfaceConfig config) {
        REGISTRY.put(biomeName, config);
    }

    /**
     * Returns the surface config for the given biome registry path, or
     * {@code null} if this biome has no special surface config (vanilla defaults
     * are kept as-is).
     *
     * @param biomePath e.g. {@code "north"}, {@code "north_mountains"}
     */
    public static GotBiomeSurfaceConfig getConfig(String biomePath) {
        return REGISTRY.get(biomePath);
    }

    private GotBiomeSurfaces() {}
}