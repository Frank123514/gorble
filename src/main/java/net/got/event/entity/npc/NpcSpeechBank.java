package net.got.event.entity.npc;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.RandomSource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NpcSpeechBank {

    private static final Gson GSON = new Gson();

    public static final NpcSpeechBank SMALLFOLK_CIVILIAN =
            load("/assets/got/dialogue/smallfolk_civilian.json");

    private final List<String> lines;

    private NpcSpeechBank(List<String> lines) {
        this.lines = Collections.unmodifiableList(lines);
    }

    public static NpcSpeechBank load(String classpathResource) {
        try (InputStream is = NpcSpeechBank.class.getResourceAsStream(classpathResource)) {
            if (is == null) return empty();
            JsonObject root = GSON.fromJson(new InputStreamReader(is), JsonObject.class);
            JsonArray arr = root.getAsJsonArray("lines");
            List<String> list = new ArrayList<>();
            for (JsonElement e : arr) {
                list.add(e.getAsString());
            }
            return new NpcSpeechBank(list);
        } catch (Exception e) {
            return empty();
        }
    }

    private static NpcSpeechBank empty() {
        return new NpcSpeechBank(new ArrayList<>());
    }

    public boolean isEmpty() {
        return lines.isEmpty();
    }

    public String randomLine(RandomSource random) {
        if (lines.isEmpty()) return "";
        return lines.get(random.nextInt(lines.size()));
    }
}
