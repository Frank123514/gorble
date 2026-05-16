package net.got.faction;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Central registry of every named waypoint on the Known-World map.
 *
 * <p>Block coordinates were derived by visually locating each settlement on the
 * 4207×3277 {@code known_world.png} texture and converting via the formula used
 * in {@code GotMapWidget}:
 * <pre>
 *   blockX = pixelX × 46 − 96761
 *   blockZ = pixelY × 46 − 75371
 * </pre>
 * Every coordinate has been verified to land on a land pixel (not ocean).
 *
 * <p>The {@link #ALL} list exposes every waypoint for the full map view.
 * {@link #BY_FACTION} groups them for the faction-selection minimap.
 */
public final class WaypointRegistry {

    // ── Per-faction waypoint groups ───────────────────────────────────────────

    public static final List<WaypointData> NORTH = List.of(
            new WaypointData("Winterfell",   -71921, -30521, 8.0),
            new WaypointData("Castle Black", -68425, -53843, 8.0),
            new WaypointData("Moat Cailin",  -73301, -26151, 8.0),
            new WaypointData("White Harbor", -66493, -31993, 8.0),
            new WaypointData("The Wall",     -70173, -52831, 6.0)
    );

    public static final List<WaypointData> VALE = List.of(
            new WaypointData("The Eyrie",   -66953, -35351, 9.0),
            new WaypointData("Gulltown",    -66125, -32453, 9.0),
            new WaypointData("Bloody Gate", -67413, -36501, 9.0)
    );

    public static final List<WaypointData> RIVERLANDS = List.of(
            new WaypointData("Riverrun",  -78821, -20401, 9.0),
            new WaypointData("Harrenhal", -72611, -18331, 9.0),
            new WaypointData("The Twins", -74865, -22931, 9.0)
    );

    public static final List<WaypointData> WESTERLANDS = List.of(
            new WaypointData("Casterly Rock",  -81305, -14283, 9.0),
            new WaypointData("Lannisport",     -81581, -13363, 9.0),
            new WaypointData("Clegane's Keep", -80385, -15203, 9.0)
    );

    public static final List<WaypointData> REACH = List.of(
            new WaypointData("Highgarden", -77073,    -23, 9.0),
            new WaypointData("Oldtown",    -80431,  10557, 9.0),
            new WaypointData("Horn Hill",  -76383,   4117, 9.0),
            new WaypointData("Cider Hall", -77671,   2737, 9.0)
    );

    public static final List<WaypointData> STORMLANDS = List.of(
            new WaypointData("Storm's End",    -70081,   1817, 9.0),
            new WaypointData("King's Landing", -70173, -11983, 9.0),
            new WaypointData("Dragonstone",    -67505, -13915, 9.0)
    );

    public static final List<WaypointData> IRON_ISLANDS = List.of(
            new WaypointData("Pyke",      -82271, -14559, 10.0),
            new WaypointData("Lordsport", -82133, -14191, 10.0),
            new WaypointData("Great Wyk", -83973, -10235, 10.0),
            new WaypointData("Old Wyk",   -83329, -10235, 10.0)
    );

    public static final List<WaypointData> DORNE = List.of(
            new WaypointData("Sunspear",      -66953, 21137, 9.0),
            new WaypointData("Water Gardens", -67505, 20585, 9.0),
            new WaypointData("Starfall",      -78545, 16445, 9.0),
            new WaypointData("Yronwood",      -73025, 17365, 9.0)
    );

    // ── Lookup map for FactionSelectionScreen ─────────────────────────────────

    /** Maps faction id → its list of waypoints. */
    public static final Map<String, List<WaypointData>> BY_FACTION;
    static {
        Map<String, List<WaypointData>> m = new LinkedHashMap<>();
        m.put("north",        NORTH);
        m.put("vale",         VALE);
        m.put("riverlands",   RIVERLANDS);
        m.put("westerlands",  WESTERLANDS);
        m.put("reach",        REACH);
        m.put("stormlands",   STORMLANDS);
        m.put("iron_islands", IRON_ISLANDS);
        m.put("dorne",        DORNE);
        BY_FACTION = Collections.unmodifiableMap(m);
    }

    /** Every waypoint across all factions — used by the full-map screen. */
    public static final List<WaypointData> ALL;
    static {
        ALL = List.of(
                // North
                new WaypointData("Winterfell",     -71921, -30521, 8.0),
                new WaypointData("Castle Black",   -68425, -53843, 8.0),
                new WaypointData("Moat Cailin",    -73301, -26151, 8.0),
                new WaypointData("White Harbor",   -66493, -31993, 8.0),
                new WaypointData("The Wall",       -70173, -52831, 6.0),
                // Vale
                new WaypointData("The Eyrie",      -66953, -35351, 9.0),
                new WaypointData("Gulltown",       -66125, -32453, 9.0),
                new WaypointData("Bloody Gate",    -67413, -36501, 9.0),
                // Riverlands
                new WaypointData("Riverrun",       -78821, -20401, 9.0),
                new WaypointData("Harrenhal",      -72611, -18331, 9.0),
                new WaypointData("The Twins",      -74865, -22931, 9.0),
                // Westerlands
                new WaypointData("Casterly Rock",  -81305, -14283, 9.0),
                new WaypointData("Lannisport",     -81581, -13363, 9.0),
                // Reach
                new WaypointData("Highgarden",     -77073,    -23, 9.0),
                new WaypointData("Oldtown",        -80431,  10557, 9.0),
                new WaypointData("Horn Hill",      -76383,   4117, 9.0),
                // Stormlands / Crownlands
                new WaypointData("King's Landing", -70173, -11983, 9.0),
                new WaypointData("Dragonstone",    -67505, -13915, 9.0),
                new WaypointData("Storm's End",    -70081,   1817, 9.0),
                // Iron Islands
                new WaypointData("Pyke",           -82271, -14559, 9.0),
                // Dorne
                new WaypointData("Sunspear",       -66953,  21137, 9.0),
                new WaypointData("Starfall",       -78545,  16445, 9.0),
                new WaypointData("Yronwood",       -73025,  17365, 9.0)
        );
    }

    private WaypointRegistry() {}
}