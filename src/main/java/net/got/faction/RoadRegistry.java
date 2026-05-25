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
 * <p>All road data is loaded from the JSON resource file at
 * {@code data/got/worldgen/roads/roads.json}. Edit that file to add, remove,
 * or reshape roads — no Java recompilation required.
 *
 * <p>Roads are grouped by type for easy renderer lookup via {@link #BY_TYPE},
 * and exposed in full via {@link #ALL}.
 *
 * <p>JSON format:
 * <pre>{@code
 * {
 *   "roads": [
 *     {
 *       "id":   "kingsroad_north",
 *       "name": "The Kingsroad (North)",
 *       "type": "kingsroad",
 *       "points": [
 *         { "pixelX": 753, "pixelY": 750 },
 *         { "pixelX": 614, "pixelY": 1036 },
 *         ...
 *       ]
 *     }
 *   ]
 * }
 * }</pre>
 */
public final class RoadRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoadRegistry.class);
    private static final Gson   GSON   = new Gson();
    private static final String ROADS_RESOURCE = "/data/got/worldgen/roads/roads.json";

    /** Maps road type → its list of roads.  Useful for type-specific rendering. */
    public static final Map<String, List<RoadData>> BY_TYPE;

    /** Every road across all types. */
    public static final List<RoadData> ALL;

    static {
        Map<String, List<RoadData>> typeMap = new LinkedHashMap<>();
        List<RoadData> allList = new ArrayList<>();

        try (InputStream in = RoadRegistry.class.getResourceAsStream(ROADS_RESOURCE)) {
            if (in == null) {
                LOGGER.error("Roads JSON not found: {}", ROADS_RESOURCE);
            } else {
                JsonObject root = GSON.fromJson(
                        new InputStreamReader(in, StandardCharsets.UTF_8),
                        JsonObject.class
                );

                JsonArray roads = root.getAsJsonArray("roads");
                if (roads != null) {
                    for (JsonElement element : roads) {
                        RoadData road = parseRoad(element.getAsJsonObject());
                        allList.add(road);
                        typeMap.computeIfAbsent(road.type(), k -> new ArrayList<>()).add(road);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed to load roads from {}", ROADS_RESOURCE, e);
        }

        // Make everything unmodifiable
        Map<String, List<RoadData>> unmodifiable = new LinkedHashMap<>();
        for (Map.Entry<String, List<RoadData>> entry : typeMap.entrySet()) {
            unmodifiable.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
        }
        BY_TYPE = Collections.unmodifiableMap(unmodifiable);
        ALL     = Collections.unmodifiableList(allList);
    }

    private static RoadData parseRoad(JsonObject obj) {
        String id   = obj.get("id").getAsString();
        String name = obj.get("name").getAsString();
        String type = obj.get("type").getAsString();

        List<RoadData.Point> points = new ArrayList<>();
        JsonArray pointArray = obj.getAsJsonArray("points");
        for (JsonElement pe : pointArray) {
            JsonObject p = pe.getAsJsonObject();
            points.add(new RoadData.Point(
                    p.get("pixelX").getAsInt(),
                    p.get("pixelY").getAsInt()
            ));
        }

        return new RoadData(id, name, type, Collections.unmodifiableList(points));
    }

    private RoadRegistry() {}
}
