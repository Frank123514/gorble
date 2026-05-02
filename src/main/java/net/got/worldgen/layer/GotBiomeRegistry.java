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
 * set to their <b>LOTR Renewed-equivalent</b> values so the terrain character
 * of each GoT biome directly matches its Middle-earth counterpart.
 *
 * <h3>Depth / Scale convention (LOTR Renewed style)</h3>
 * <p>These are the same dimensionless values used by LOTR Renewed 1.16.5.
 * They feed directly into {@link net.got.worldgen.GotChunkGenerator}'s
 * {@code terrainGradient} function unchanged.
 *
 * <pre>
 *  depth =  0.0  → average surface at sea level (Y 63)
 *  depth =  0.125→ gentle plains  ~Y 71  (Rohan / Westlands)
 *  depth =  0.30 → rolling hills  ~Y 79  (Barrow-downs)
 *  depth =  0.55 → significant hills ~Y 91 (Emyn Muil)
 *  depth =  1.10 → tall mountains ~Y 116 (Misty Mountains)
 *  depth =  1.40 → extreme peaks  ~Y 128 (Frostfangs / Caradhras)
 *  depth = -0.05 → river channel  ~Y 60  (3 below sea — LOTR river style)
 *  depth = -0.30 → inland lake    ~Y 56
 *  depth = -0.70 → ocean floor    ~Y 43
 *  depth = -1.20 → deep abyss     ~Y 29
 *
 *  scale: larger = hillier / more noise variation:
 *    0.01–0.05 = nearly flat (rivers, wetlands)
 *    0.10–0.20 = gentle plains
 *    0.25–0.40 = hills
 *    0.50–0.70 = mountains
 * </pre>
 *
 * <p><b>Do not reorder the ID constants</b> — doing so scrambles existing worlds.
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

    // ── Terrain depth and scale — LOTR Renewed equivalents ───────────────
    //
    //  LOTR Renewed analogues used as reference:
    //   North        ≈ Rohan / Eriador plains
    //   Barrowlands  ≈ Barrow-downs
    //   Wolfswood    ≈ Mirkwood (forest edge)
    //   Ironwood     ≈ Fangorn (lower slopes)
    //   North Hills  ≈ Emyn Muil
    //   N. Mountains ≈ Misty Mountains
    //   Always Winter≈ Forodwaith plateau
    //   Frostfangs   ≈ Forodwaith extreme peaks / Caradhras
    //   Neck         ≈ Dagorlad / bog lowlands

    private static final float[] DEPTH = new float[COUNT];
    private static final float[] SCALE = new float[COUNT];

    static {
        // depth / scale — direct LOTR Renewed biome JSON equivalents
        //                              depth    scale
        set(ID_NECK,               0.200f,  0.050f);  // near-sea wetland marshes
        set(ID_STONY_SHORE,        0.400f,  0.060f);  // coastal rock shelf at sea level
        set(ID_HAUNTED_FOREST,     0.350f,  0.050f);  // eerily flat dark forest
        set(ID_NORTH,              0.350f,  0.050f);  // rolling plains (Rohan equivalent)
        set(ID_WOLFSWOOD,          0.350f,  0.050f);  // dense forested rolling slopes
        set(ID_IRONWOOD,           0.350f,  0.050f);  // forested hill country
        set(ID_BARROWLANDS,        0.400f,  0.100f);  // barrow-mound hill country
        set(ID_SHEEPSHEAD_HILLS,   0.350f,  0.280f);  // rounded open sheepland hills
        set(ID_ALWAYS_WINTER,      0.500f,  0.250f);  // frozen high plateau (Forodwaith)
        set(ID_NORTH_HILLS,        0.550f,  0.350f);  // significant hills (Emyn Muil)
        set(ID_IRON_HILLS,         0.550f,  0.350f);  // iron-rich hill range
        set(ID_NORTH_MOUNTAINS,    1.100f,  0.550f);  // tall mountains (Misty Mountains)
        set(ID_FROSTFANGS,         1.400f,  0.650f);  // extreme jagged peaks (Caradhras)

        // ── Water — all below sea level ───────────────────────────────────
        //
        // River depth -0.05 → average bed Y≈60 (3 blocks below sea).
        // scale 0.025 makes the gradient very steep → sharp channel 1–6 blocks
        // wide, exactly matching LOTR Renewed's narrow river appearance.
        // ── Water — all below sea level ───────────────────────────────────
        //
        // Formula: surface blockY = 63 + depth * 63.5
        //   river bed    Y ≈ 60  (depth −0.047) — 3 below sea
        //   lake floor   Y ≈ 51  (depth −0.189) — 12 below sea, visible open lake
        //   ocean floor  Y ≈ 46  (depth −0.268) — 17 below sea, vanilla-matching
        //   deep abyss   Y ≈ 30  (depth −0.520) — 33 below sea
        //
        // scale 0.025 on rivers → very steep gradient → sharp 1–6 block wide
        // channel matching LOTR Renewed's narrow river appearance.
        set(ID_RIVER,             -0.047f,  0.025f);  // river bed  Y ≈ 60
        set(ID_NECK_RIVER,        -0.047f,  0.025f);  // neck swamp channel
        set(ID_FROZEN_RIVER,      -0.047f,  0.025f);  // frozen river channel

        set(ID_LAKE,              -0.189f,  0.030f);  // lake floor Y ≈ 51
        set(ID_FROZEN_LAKE,       -0.189f,  0.030f);  // frozen lake floor Y ≈ 51

        set(ID_OCEAN,             -0.268f,  0.050f);  // ocean floor Y ≈ 46
        set(ID_DEEP_OCEAN,        -0.520f,  0.060f);  // deep ocean  Y ≈ 30
    }

    private static void set(int id, float depth, float scale) {
        DEPTH[id] = depth;
        SCALE[id] = Math.max(0.005f, scale);
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
            case ID_NECK                          -> ID_NECK_RIVER;
            case ID_ALWAYS_WINTER, ID_FROSTFANGS -> ID_FROZEN_RIVER;
            default                               -> ID_RIVER;
        };
    }

    public static int getShoreFor(int landId) {
        return ID_STONY_SHORE;
    }

    /** Returns a sub-biome replacement ID, or -1 to keep the base. */
    public static int getSubtype(int baseId, float f) {
        if (baseId == ID_WOLFSWOOD && f < 0.15f) return ID_IRONWOOD;
        return -1;
    }

    // ── Resource key helpers ──────────────────────────────────────────────

    public static ResourceLocation locationFor(int id) {
        if (id < 0 || id >= COUNT) id = ID_NORTH;
        return GotMod.id(PATHS[id]);
    }

    public static ResourceKey<Biome> keyFor(int id) {
        return ResourceKey.create(Registries.BIOME, locationFor(id));
    }
}
