package net.got.entity.npc;

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

/**
 * Holds a list of dialogue strings for an NPC type and picks from them randomly.
 *
 * <p>Speech banks are loaded from JSON files bundled in the mod's assets.
 * To edit a bank, open the corresponding file in
 * {@code src/main/resources/assets/got/dialogue/} and add or change
 * entries in the {@code "lines"} array, then rebuild.
 *
 * <p>Each subclass of {@link net.got.entity.npc.smallfolk.SmallfolkEntity}
 * can override {@code getSpeechBank()} to return a different bank loaded via
 * {@link #load(String)}, pointing at its own JSON file.
 */
public final class GotNpcSpeechBank {

    private static final Gson GSON = new Gson();

    /**
     * Default bank shared by all civilian Smallfolk.
     *
     * <p>Edit: {@code src/main/resources/assets/got/dialogue/smallfolk_civilian.json}
     */
    public static final GotNpcSpeechBank SMALLFOLK_CIVILIAN =
            load("/assets/got/dialogue/smallfolk_civilian.json");

    private final List<String> lines;

    private GotNpcSpeechBank(List<String> lines) {
        this.lines = Collections.unmodifiableList(lines);
    }

    // ── Factory ───────────────────────────────────────────────────────────────

    /**
     * Loads a speech bank from a JSON file on the classpath.
     *
     * <p>The file must contain a top-level {@code "lines"} array of strings:
     * <pre>{@code
     * {
     *   "lines": [
     *     "Good day to you, traveller.",
     *     "Have you come far?"
     *   ]
     * }
     * }</pre>
     *
     * @param classpathResource absolute classpath path, e.g.
     *                          {@code "/assets/got/dialogue/smallfolk_civilian.json"}
     * @return a populated bank, or an empty bank if the file is missing or invalid
     */
    public static GotNpcSpeechBank load(String classpathResource) {
        try (InputStream is = GotNpcSpeechBank.class.getResourceAsStream(classpathResource)) {
            if (is == null) return empty();
            JsonObject root = GSON.fromJson(new InputStreamReader(is), JsonObject.class);
            JsonArray arr = root.getAsJsonArray("lines");
            List<String> list = new ArrayList<>();
            for (JsonElement e : arr) {
                list.add(e.getAsString());
            }
            return new GotNpcSpeechBank(list);
        } catch (Exception e) {
            return empty();
        }
    }

    private static GotNpcSpeechBank empty() {
        return new GotNpcSpeechBank(new ArrayList<>());
    }

    // ── API ───────────────────────────────────────────────────────────────────

    /** {@code true} if this bank has no dialogue lines. */
    public boolean isEmpty() {
        return lines.isEmpty();
    }

    /**
     * Returns a random dialogue line, or an empty string if the bank is empty.
     */
    public String randomLine(RandomSource random) {
        if (lines.isEmpty()) return "";
        return lines.get(random.nextInt(lines.size()));
    }
}
