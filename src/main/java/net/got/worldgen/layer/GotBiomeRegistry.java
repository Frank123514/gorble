package net.got.worldgen.layer;

import net.got.GotMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

/**
 * Central registry of GoT biome metadata used by the layer system.
 *
 * <p>Assigns stable integer IDs to each biome. Depth and scale values are
 * taken directly from {@link net.got.worldgen.GotBiomeDensityParams} so
 * terrain character matches the original GoT design.
 *
 * <p><b>Do not reorder the ID constants</b> — doing so scrambles existing worlds.
 *
 * <h3>Depth/scale convention</h3>
 * <p>These use the LOTR Renewed vanilla convention, NOT block-Y coordinates:
 * <pre>
 *   vanilla_depth = (blockY - 64) / 17.0f   (approximate)
 *   vanilla_scale = blockAmplitude / 55.0f  (approximate)
 * </pre>
 * Converted from GotBiomeDensityParams values (which are in block units).
 */
public final class GotBiomeRegistry {

    // ── Stable integer IDs ────────────────────────────────────────────────

    public static final int ID_NORTH            = 0;
    public static final int ID_BARROWLANDS      = 1;
    public static final int ID_HAUNTED_FOREST   = 2;
    public static final int ID_WOLFSWOOD        = 3;
    public static final int ID_IRONWOOD         = 4;
    public static final int ID_NORTH_HILLS      = 5;
    public static final int ID_NORTH_MOUNTAINS  = 6;
    public static final int ID_ALWAYS_WINTER    = 7;
    public static final int ID_FROSTFANGS       = 8;
    public static final int ID_STONY_SHORE      = 9;
    public static final int ID_NECK             = 10;
    public static final int ID_OCEAN            = 11;
    public static final int ID_DEEP_OCEAN       = 12;
    public static final int ID_RIVER            = 13;
    public static final int ID_NECK_RIVER       = 14;
    public static final int ID_FROZEN_RIVER     = 15;
    // These are derived/rare — not in major seed list
    public static final int ID_IRON_HILLS       = 16;
    public static final int ID_SHEEPSHEAD_HILLS = 17;
    public static final int ID_LAKE             = 18;
    public static final int ID_FROZEN_LAKE      = 19;

    private static final int COUNT = 20;

    // ── Registry paths ────────────────────────────────────────────────────

    private static final String[] PATHS = {
        /* 0  */ "north",
        /* 1  */ "barrowlands",
        /* 2  */ "haunted_forest",
        /* 3  */ "wolfswood",
        /* 4  */ "ironwood",
        /* 5  */ "north_hills",
        /* 6  */ "north_mountains",
        /* 7  */ "always_winter",
        /* 8  */ "frostfangs",
        /* 9  */ "stony_shore",
        /* 10 */ "neck",
        /* 11 */ "ocean",
        /* 12 */ "deep_ocean",
        /* 13 */ "river",
        /* 14 */ "neck_river",
        /* 15 */ "frozen_river",
        /* 16 */ "iron_hills",
        /* 17 */ "sheepshead_hills",
        /* 18 */ "lake",
        /* 19 */ "frozen_lake",
    };

    /** Major biomes that can be randomly seeded during classic generation. */
    public static final int[] MAJOR_IDS = {
        ID_NORTH, ID_BARROWLANDS, ID_HAUNTED_FOREST, ID_WOLFSWOOD,
        ID_IRONWOOD, ID_NORTH_HILLS, ID_NORTH_MOUNTAINS, ID_ALWAYS_WINTER,
        ID_FROSTFANGS, ID_STONY_SHORE, ID_NECK,
        ID_IRON_HILLS, ID_SHEEPSHEAD_HILLS,
    };

    // ── Terrain depth and scale ───────────────────────────────────────────
    // Converted from GotBiomeDensityParams block-Y values using:
    //   depth = (blockDepth - 64) / 17f
    //   scale = blockScale / 55f
    // This preserves the original GoT terrain character in the LOTR formula.

    private static final float[] DEPTH = new float[COUNT];
    private static final float[] SCALE = new float[COUNT];

    static {
        // Land — source values from GotBiomeDensityParams
        set(ID_NORTH,            d(70),  s(15));   // rolling plains
        set(ID_BARROWLANDS,      d(75),  s(35));   // gentle barrow hills
        set(ID_HAUNTED_FOREST,   d(70),  s(18));   // eerily flat dark forest
        set(ID_WOLFSWOOD,        d(74),  s(22));   // dense forested slopes
        set(ID_IRONWOOD,         d(76),  s(25));   // forested hill country
        set(ID_NORTH_HILLS,      d(88),  s(35));   // pronounced hill range
        set(ID_NORTH_MOUNTAINS,  d(120), s(75));   // tall mountain range
        set(ID_ALWAYS_WINTER,    d(85),  s(28));   // frozen high plateau
        set(ID_FROSTFANGS,       d(135), s(90));   // extreme jagged peaks
        set(ID_STONY_SHORE,      d(66),  s(12));   // coastal rock shelves
        set(ID_NECK,             d(62),  s(8));    // near-sea-level wetlands
        set(ID_IRON_HILLS,       d(88),  s(35));   // iron-rich hill range
        set(ID_SHEEPSHEAD_HILLS, d(78),  s(25));   // rounded sheep hills
        // Water
        set(ID_OCEAN,            d(45),  s(5));    // open ocean floor
        set(ID_DEEP_OCEAN,       d(35),  s(6));    // abyssal depths
        set(ID_RIVER,            d(55),  s(3));    // shallow river bed
        set(ID_NECK_RIVER,       d(55),  s(3));    // neck wetland channels
        set(ID_FROZEN_RIVER,     d(55),  s(3));    // frozen river bed
        set(ID_LAKE,             d(48),  s(4));    // inland lake
        set(ID_FROZEN_LAKE,      d(48),  s(4));    // frozen inland lake
    }

    /** Converts a block-Y depth value to LOTR vanilla depth convention. */
    private static float d(float blockY) { return (blockY - 64f) / 17f; }

    /** Converts a block-amplitude scale value to LOTR vanilla scale convention. */
    private static float s(float blockAmp) { return Math.max(0.01f, blockAmp / 55f); }

    private static void set(int id, float depth, float scale) {
        DEPTH[id] = depth;
        SCALE[id] = scale;
    }

    // ── Accessors ─────────────────────────────────────────────────────────

    public static float getDepth(int id) { return (id >= 0 && id < COUNT) ? DEPTH[id] : DEPTH[ID_NORTH]; }
    public static float getScale(int id) { return (id >= 0 && id < COUNT) ? SCALE[id] : SCALE[ID_NORTH]; }

    // ── Role helpers ──────────────────────────────────────────────────────

    public static boolean isSea(int id) {
        return id == ID_OCEAN || id == ID_DEEP_OCEAN;
    }

    public static boolean isWater(int id) {
        return isSea(id)
            || id == ID_LAKE || id == ID_FROZEN_LAKE
            || id == ID_RIVER || id == ID_NECK_RIVER || id == ID_FROZEN_RIVER;
    }

    public static int getRiverFor(int landId) {
        return switch (landId) {
            case ID_NECK                       -> ID_NECK_RIVER;
            case ID_ALWAYS_WINTER, ID_FROSTFANGS -> ID_FROZEN_RIVER;
            default                            -> ID_RIVER;
        };
    }

    public static int getShoreFor(int landId) {
        // All GoT coastal biomes currently use stony_shore
        return ID_STONY_SHORE;
    }

    /** Returns a sub-biome replacement ID, or -1 to keep the base. */
    public static int getSubtype(int baseId, float f) {
        // wolfswood → ironwood at 15% chance
        if (baseId == ID_WOLFSWOOD && f < 0.15f) return ID_IRONWOOD;
        return -1;
    }

    // ── Resource key helpers ──────────────────────────────────────────────
    // NOTE: these do NOT call Registry.getHolder() — biome holders are looked
    // up at query time from the locationToHolder map in GotBiomeSource.

    public static ResourceLocation locationFor(int id) {
        if (id < 0 || id >= COUNT) id = ID_NORTH;
        return GotMod.id(PATHS[id]);
    }

    public static ResourceKey<Biome> keyFor(int id) {
        return ResourceKey.create(Registries.BIOME, locationFor(id));
    }
}
