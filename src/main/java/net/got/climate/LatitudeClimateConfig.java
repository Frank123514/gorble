package net.got.climate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * Single source of truth for every latitude-dependent climate setting: the
 * frozen-latitude ice/temperature spine used by {@link LatitudeClimate} and
 * {@link LatitudeIceHandler}/{@link LatitudeIcebergHandler}, and the
 * dead-grass line used by
 * {@code net.got.client.color.SeasonFoliageColorProvider}.
 *
 * <p>All of it now lives in one bundled JSON resource,
 * {@code /net/got/climate/latitude_climate.json}, the same way LOTR Mod
 * keeps its {@code water_latitude.json} as a single settings file instead of
 * scattering magic numbers across multiple classes. This replaces the old
 * {@code latitude_spine.csv} (which only held the ice spine) plus a set of
 * hardcoded constants that used to live directly in
 * {@code SeasonFoliageColorProvider}.
 *
 * <p>The spine is still kept out of Java source (rather than a literal
 * array) for the same reason as before: a ~4207-entry array initializer
 * compiles into a {@code <clinit>} whose bytecode exceeds the JVM's 64KB
 * per-method limit ("code too large"). Parsing it once, lazily, from a
 * bundled resource avoids that entirely.
 */
public final class LatitudeClimateConfig {

    private static final String RESOURCE = "/net/got/climate/latitude_climate.json";

    // ── Ice / temperature spine ─────────────────────────────────────────────
    private final int[][] spine;
    private final float iceFadeDistance;
    private final float iceMaxAdjustment;

    // ── Dead-grass line ──────────────────────────────────────────────────────
    private final int deadGrassMapX0;
    private final int deadGrassRowX0;
    private final int deadGrassMapX1;
    private final int deadGrassRowX1;
    private final float deadGrassFadeRows;
    private final int deadGrassColor;

    private static final LatitudeClimateConfig INSTANCE = load();

    public static LatitudeClimateConfig get() {
        return INSTANCE;
    }

    private LatitudeClimateConfig(int[][] spine, float iceFadeDistance, float iceMaxAdjustment,
                                   int deadGrassMapX0, int deadGrassRowX0,
                                   int deadGrassMapX1, int deadGrassRowX1,
                                   float deadGrassFadeRows, int deadGrassColor) {
        this.spine = spine;
        this.iceFadeDistance = iceFadeDistance;
        this.iceMaxAdjustment = iceMaxAdjustment;
        this.deadGrassMapX0 = deadGrassMapX0;
        this.deadGrassRowX0 = deadGrassRowX0;
        this.deadGrassMapX1 = deadGrassMapX1;
        this.deadGrassRowX1 = deadGrassRowX1;
        this.deadGrassFadeRows = deadGrassFadeRows;
        this.deadGrassColor = deadGrassColor;
    }

    private static LatitudeClimateConfig load() {
        try (InputStream in = LatitudeClimateConfig.class.getResourceAsStream(RESOURCE)) {
            if (in == null) {
                throw new IllegalStateException("Missing resource " + RESOURCE);
            }
            JsonObject root;
            try (InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                root = JsonParser.parseReader(reader).getAsJsonObject();
            }

            JsonObject ice = root.getAsJsonObject("ice");
            JsonArray spineJson = ice.getAsJsonArray("spine");
            int[][] spine = new int[spineJson.size()][2];
            for (int i = 0; i < spineJson.size(); i++) {
                JsonArray point = spineJson.get(i).getAsJsonArray();
                spine[i][0] = point.get(0).getAsInt();
                spine[i][1] = point.get(1).getAsInt();
            }
            float iceFadeDistance = ice.get("fade_distance").getAsFloat();
            float iceMaxAdjustment = ice.get("max_adjustment").getAsFloat();

            JsonObject deadGrass = root.getAsJsonObject("dead_grass");
            int deadGrassMapX0 = deadGrass.get("map_x0").getAsInt();
            int deadGrassRowX0 = deadGrass.get("row_x0").getAsInt();
            int deadGrassMapX1 = deadGrass.get("map_x1").getAsInt();
            int deadGrassRowX1 = deadGrass.get("row_x1").getAsInt();
            float deadGrassFadeRows = deadGrass.get("fade_rows").getAsFloat();
            int deadGrassColor = parseColor(deadGrass.get("color"));

            return new LatitudeClimateConfig(
                    spine, iceFadeDistance, iceMaxAdjustment,
                    deadGrassMapX0, deadGrassRowX0, deadGrassMapX1, deadGrassRowX1,
                    deadGrassFadeRows, deadGrassColor);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load " + RESOURCE, e);
        }
    }

    /** Accepts either a JSON number or a "0xRRGGBB"/"#RRGGBB" string. */
    private static int parseColor(JsonElement element) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsInt();
        }
        String text = element.getAsString().trim();
        if (text.startsWith("0x") || text.startsWith("0X")) {
            text = text.substring(2);
        } else if (text.startsWith("#")) {
            text = text.substring(1);
        }
        return Integer.parseInt(text, 16);
    }

    // ── Accessors ────────────────────────────────────────────────────────────

    public int[][] spine() {
        return spine;
    }

    public float iceFadeDistance() {
        return iceFadeDistance;
    }

    public float iceMaxAdjustment() {
        return iceMaxAdjustment;
    }

    public int deadGrassMapX0() {
        return deadGrassMapX0;
    }

    public int deadGrassRowX0() {
        return deadGrassRowX0;
    }

    public int deadGrassMapX1() {
        return deadGrassMapX1;
    }

    public int deadGrassRowX1() {
        return deadGrassRowX1;
    }

    public float deadGrassFadeRows() {
        return deadGrassFadeRows;
    }

    public int deadGrassColor() {
        return deadGrassColor;
    }
}
