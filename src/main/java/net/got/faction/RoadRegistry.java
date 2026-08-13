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

public final class RoadRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoadRegistry.class);
    private static final Gson   GSON   = new Gson();

    private static final String ROADS_DIR       = "/data/got/worldgen/roads/";
    private static final String MANIFEST_FILE   = ROADS_DIR + "_manifest.json";

    public static final Map<String, List<RoadData>> BY_TYPE;

    public static final List<RoadData> ALL;

    static {
        Map<String, List<RoadData>> typeMap = new LinkedHashMap<>();
        List<RoadData> allList = new ArrayList<>();

        List<String> roadFiles = loadManifest();

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

    private static RoadData parseRoad(JsonObject obj) {
        String id   = obj.get("id").getAsString();
        String name = obj.get("name").getAsString();
        String type = obj.get("type").getAsString();

        RoadData.Palette palette;
        if (obj.has("palette")) {
            palette = parsePalette(obj.getAsJsonObject("palette"), type);
        } else {
            palette = RoadData.Palette.defaultForType(type);
        }

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