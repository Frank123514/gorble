package net.got.entity;

import net.minecraft.util.RandomSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Loads gender-specific name pools from text files bundled in the mod's
 * resource pack and exposes a thread-safe way to pick a random name.
 *
 * <p>Files are at:
 * <ul>
 *   <li>{@code assets/got/names_male.txt}</li>
 *   <li>{@code assets/got/names_female.txt}</li>
 * </ul>
 *
 * <p>Each file contains one name per line; blank lines and lines starting
 * with {@code #} are ignored.  The lists are loaded lazily the first time
 * {@link #randomName} is called.
 */
public final class NpcNameHelper {

    private static final String MALE_RESOURCE   = "/assets/got/names_male.txt";
    private static final String FEMALE_RESOURCE = "/assets/got/names_female.txt";

    // Lazy-loaded, immutable after first load
    private static volatile List<String> MALE_NAMES   = null;
    private static volatile List<String> FEMALE_NAMES = null;

    private NpcNameHelper() {}

    /**
     * Returns a random name appropriate for the given gender, chosen with
     * the entity's own {@link RandomSource} so it is deterministic and
     * reproducible across game restarts given the same seed.
     *
     * @param gender the gender to draw from
     * @param random the entity's random source
     * @return a name string, never {@code null}
     */
    public static String randomName(NpcGender gender, RandomSource random) {
        List<String> pool = (gender == NpcGender.FEMALE) ? getFemaleNames() : getMaleNames();
        if (pool.isEmpty()) {
            return gender == NpcGender.FEMALE ? "Unnamed Woman" : "Unnamed Man";
        }
        return pool.get(random.nextInt(pool.size()));
    }

    // ── Private helpers ────────────────────────────────────────────────

    private static List<String> getMaleNames() {
        if (MALE_NAMES == null) {
            synchronized (NpcNameHelper.class) {
                if (MALE_NAMES == null) {
                    MALE_NAMES = Collections.unmodifiableList(loadNames(MALE_RESOURCE));
                }
            }
        }
        return MALE_NAMES;
    }

    private static List<String> getFemaleNames() {
        if (FEMALE_NAMES == null) {
            synchronized (NpcNameHelper.class) {
                if (FEMALE_NAMES == null) {
                    FEMALE_NAMES = Collections.unmodifiableList(loadNames(FEMALE_RESOURCE));
                }
            }
        }
        return FEMALE_NAMES;
    }

    private static List<String> loadNames(String resourcePath) {
        List<String> names = new ArrayList<>();
        try {
            InputStream is = NpcNameHelper.class.getResourceAsStream(resourcePath);
            if (is == null) {
                System.err.println("[GOT] NpcNameHelper: could not find resource " + resourcePath);
                return names;
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.strip();
                    if (!line.isEmpty() && !line.startsWith("#")) {
                        names.add(line);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[GOT] NpcNameHelper: failed to load " + resourcePath + " — " + e.getMessage());
        }
        return names;
    }
}
