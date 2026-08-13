package net.got.climate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public final class LatitudeClimateConfig {

    private static final String RESOURCE = "/net/got/climate/latitude_climate.json";

    private final int iceFreezeLineZ;
    private final float iceFadeDistance;
    private final float iceMaxAdjustment;

    private final int deadGrassMapX0;
    private final int deadGrassRowX0;
    private final int deadGrassMapX1;
    private final int deadGrassRowX1;
    private final float deadGrassFadeRows;
    private final int deadGrassColorDark;
    private final int deadGrassColorLight;

    private static final LatitudeClimateConfig INSTANCE = load();

    public static LatitudeClimateConfig get() {
        return INSTANCE;
    }

    private LatitudeClimateConfig(int iceFreezeLineZ, float iceFadeDistance, float iceMaxAdjustment,
                                  int deadGrassMapX0, int deadGrassRowX0,
                                  int deadGrassMapX1, int deadGrassRowX1,
                                  float deadGrassFadeRows, int deadGrassColorDark, int deadGrassColorLight) {
        this.iceFreezeLineZ = iceFreezeLineZ;
        this.iceFadeDistance = iceFadeDistance;
        this.iceMaxAdjustment = iceMaxAdjustment;
        this.deadGrassMapX0 = deadGrassMapX0;
        this.deadGrassRowX0 = deadGrassRowX0;
        this.deadGrassMapX1 = deadGrassMapX1;
        this.deadGrassRowX1 = deadGrassRowX1;
        this.deadGrassFadeRows = deadGrassFadeRows;
        this.deadGrassColorDark = deadGrassColorDark;
        this.deadGrassColorLight = deadGrassColorLight;
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
            int iceFreezeLineZ = ice.get("freeze_line_z").getAsInt();
            float iceFadeDistance = ice.get("fade_distance").getAsFloat();
            float iceMaxAdjustment = ice.get("max_adjustment").getAsFloat();

            JsonObject deadGrass = root.getAsJsonObject("dead_grass");
            int deadGrassMapX0 = deadGrass.get("map_x0").getAsInt();
            int deadGrassRowX0 = deadGrass.get("row_x0").getAsInt();
            int deadGrassMapX1 = deadGrass.get("map_x1").getAsInt();
            int deadGrassRowX1 = deadGrass.get("row_x1").getAsInt();
            float deadGrassFadeRows = deadGrass.get("fade_rows").getAsFloat();
            int deadGrassColorDark = parseColor(deadGrass.get("color_dark"));
            int deadGrassColorLight = parseColor(deadGrass.get("color_light"));

            return new LatitudeClimateConfig(
                    iceFreezeLineZ, iceFadeDistance, iceMaxAdjustment,
                    deadGrassMapX0, deadGrassRowX0, deadGrassMapX1, deadGrassRowX1,
                    deadGrassFadeRows, deadGrassColorDark, deadGrassColorLight);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load " + RESOURCE, e);
        }
    }

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

    public int iceFreezeLineZ() {
        return iceFreezeLineZ;
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

    public int deadGrassColorDark() {
        return deadGrassColorDark;
    }

    public int deadGrassColorLight() {
        return deadGrassColorLight;
    }
}