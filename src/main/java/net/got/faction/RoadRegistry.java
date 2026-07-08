package net.got.faction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Central registry of every road and trade route on the Known-World map.
 *
 * <h2>Loading</h2>
 * Roads are loaded from individual JSON files under
 * {@code data/got/worldgen/roads/}. The file
 * {@code _manifest.json} in that directory lists every road file to load —
 * add an entry there whenever you create a new road file.
 *
 * <p>No Java recompilation is required to add, remove, or reshape roads.
 *
 * <h2>Road JSON format</h2>
 * <pre>{@code
 * {
 *   "id":   "kingsroad_north",
 *   "name": "The Kingsroad (North)",
 *   "type": "kingsroad",
 *   "palette": {
 *     "surface": ["minecraft:cobblestone", "minecraft:cobblestone", "minecraft:stone"],
 *     "walls": false
 *   },
 *   "points": [
 *     { "pixelX": 753, "pixelY": 750 },
 *     { "pixelX": 614, "pixelY": 1036 },
 *     ...
 *   ]
 * }
 * }</pre>
 *
 * <p>The {@code palette} field is optional; if omitted, a sensible default is
 * chosen based on the road {@code type} (see {@link RoadData.Palette#defaultForType}).
 *
 * <h2>Lookup</h2>
 * Roads are grouped by type for easy renderer lookup via {@link #BY_TYPE},
 * and exposed in full via {@link #ALL}.
 */
public final class RoadRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoadRegistry.class);
    private static final Gson   GSON   = new Gson();

    private static final String ROADS_DIR       = "/data/got/worldgen/roads/";
    private static final String MANIFEST_FILE   = ROADS_DIR + "_manifest.json";

    /** Maps road type → its list of roads.  Useful for type-specific rendering. */
    public static final Map<String, List<RoadData>> BY_TYPE;

    /** Every road across all types. */
    public static final List<RoadData> ALL;

    static {
        Map<String, List<RoadData>> typeMap = new LinkedHashMap<>();
        List<RoadData> allList = new ArrayList<>();

        // ── 1. Load the manifest to get the list of road file names ──────────
        List<String> roadFiles = loadManifest();

        // ── 2. Load each individual road JSON ────────────────────────────────
        for (String fileName : roadFiles) {
            String resourcePath = ROADS_DIR + fileName;
            try (InputStream in = RoadRegistry.class.getResourceAsStream(resourcePath)) {
                if (in == null) {
                    LOGGER.error("Road file listed in manifest but not found: {}", resourcePath);
                    continue;
                }
                JsonObject obj = GSON.fromJson(
                        new InputStreamReader(in, StandardCharsets.UTF_8),
                        JsonObject.class);
                RoadData road = parseRoad(obj);
                allList.add(road);
                typeMap.computeIfAbsent(road.type(), k -> new ArrayList<>()).add(road);
            } catch (Exception e) {
                LOGGER.error("Failed to load road from {}", resourcePath, e);
            }
        }

        // ── 3. Seal everything ────────────────────────────────────────────────
        Map<String, List<RoadData>> unmodifiable = new LinkedHashMap<>();
        for (Map.Entry<String, List<RoadData>> entry : typeMap.entrySet()) {
            unmodifiable.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
        }
        BY_TYPE = Collections.unmodifiableMap(unmodifiable);
        ALL     = Collections.unmodifiableList(allList);

        LOGGER.info("[GoT][DEBUG] RoadRegistry: manifest listed {} file(s), successfully loaded {} road(s) into ALL, {} type bucket(s)",
                roadFiles.size(), ALL.size(), BY_TYPE.size());
        for (RoadData r : ALL) {
            LOGGER.info("[GoT][DEBUG] RoadRegistry: loaded road id='{}' type='{}' points={}",
                    r.id(), r.type(), r.points().size());
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * Reads {@code _manifest.json} and returns the ordered list of road file names.
     * Returns an empty list (with a logged error) if the manifest cannot be read.
     */
    private static List<String> loadManifest() {
        try (InputStream in = RoadRegistry.class.getResourceAsStream(MANIFEST_FILE)) {
            if (in == null) {
                LOGGER.error("[GoT][DEBUG] Road manifest not found on classpath: {}", MANIFEST_FILE);
                return List.of();
            }
            JsonObject root = GSON.fromJson(
                    new InputStreamReader(in, StandardCharsets.UTF_8),
                    JsonObject.class);
            JsonArray files = root.getAsJsonArray("files");
            if (files == null) {
                LOGGER.error("Road manifest has no 'files' array: {}", MANIFEST_FILE);
                return List.of();
            }
            List<String> result = new ArrayList<>();
            for (JsonElement el : files) {
                result.add(el.getAsString());
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("Failed to read road manifest: {}", MANIFEST_FILE, e);
            return List.of();
        }
    }

    /**
     * Parses a single road JSON object into a {@link RoadData} instance.
     * The {@code "palette"} field is optional; a type-appropriate default is
     * used when absent.
     */
    private static RoadData parseRoad(JsonObject obj) {
        String id   = obj.get("id").getAsString();
        String name = obj.get("name").getAsString();
        String type = obj.get("type").getAsString();

        // ── Palette (optional) ────────────────────────────────────────────────
        RoadData.Palette palette;
        if (obj.has("palette")) {
            palette = parsePalette(obj.getAsJsonObject("palette"), type);
        } else {
            palette = RoadData.Palette.defaultForType(type);
        }

        // ── Points ────────────────────────────────────────────────────────────
        List<RoadData.Point> points = new ArrayList<>();
        JsonArray pointArray = obj.getAsJsonArray("points");
        for (JsonElement pe : pointArray) {
            JsonObject p = pe.getAsJsonObject();
            points.add(new RoadData.Point(
                    p.get("pixelX").getAsInt(),
                    p.get("pixelY").getAsInt()
            ));
        }

        return new RoadData(id, name, type, palette, Collections.unmodifiableList(points));
    }

    /**
     * Parses a {@code "palette"} JSON object.
     * Missing fields fall back to the type-appropriate default palette values.
     */
    private static RoadData.Palette parsePalette(JsonObject obj, String type) {
        RoadData.Palette defaults = RoadData.Palette.defaultForType(type);

        List<String> surface;
        if (obj.has("surface")) {
            surface = new ArrayList<>();
            for (JsonElement el : obj.getAsJsonArray("surface")) {
                surface.add(el.getAsString());
            }
            surface = Collections.unmodifiableList(surface);
        } else {
            surface = defaults.surface();
        }

        boolean walls = obj.has("walls")
                ? obj.get("walls").getAsBoolean()
                : defaults.walls();

        return new RoadData.Palette(surface, walls);
    }

    private RoadRegistry() {}
}