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

public final class WaypointRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaypointRegistry.class);
    private static final Gson GSON = new Gson();
    private static final String WAYPOINTS_RESOURCE = "/data/got/worldgen/waypoints/waypoints.json";

    public static final Map<String, List<WaypointData>> BY_FACTION;

    public static final List<WaypointData> ALL;

    static {
        Map<String, List<WaypointData>> factionMap = new LinkedHashMap<>();
        List<WaypointData> allList = new ArrayList<>();

        try (InputStream in = WaypointRegistry.class.getResourceAsStream(WAYPOINTS_RESOURCE)) {
            if (in == null) {
                LOGGER.error("Waypoints JSON not found: {}", WAYPOINTS_RESOURCE);
            } else {
                JsonObject root = GSON.fromJson(
                        new InputStreamReader(in, StandardCharsets.UTF_8),
                        JsonObject.class
                );

                JsonObject factions = root.getAsJsonObject("factions");
                if (factions != null) {
                    for (Map.Entry<String, JsonElement> entry : factions.entrySet()) {
                        List<WaypointData> list = parseWaypointArray(entry.getValue().getAsJsonArray());
                        factionMap.put(entry.getKey(), Collections.unmodifiableList(list));
                    }
                }

                for (List<WaypointData> list : factionMap.values()) {
                    allList.addAll(list);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed to load waypoints from {}", WAYPOINTS_RESOURCE, e);
        }

        BY_FACTION = Collections.unmodifiableMap(factionMap);
        ALL = Collections.unmodifiableList(allList);
    }

    private static List<WaypointData> parseWaypointArray(JsonArray array) {
        List<WaypointData> result = new ArrayList<>();
        for (JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();
            String name  = obj.get("name").getAsString();
            int pixelX   = obj.get("pixelX").getAsInt();
            int pixelY   = obj.get("pixelY").getAsInt();
            double zoom  = obj.has("zoom") ? obj.get("zoom").getAsDouble() : 7.0;
            result.add(new WaypointData(name, pixelX, pixelY, zoom));
        }
        return result;
    }

    private WaypointRegistry() {}
}