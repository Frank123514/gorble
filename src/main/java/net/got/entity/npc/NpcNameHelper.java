package net.got.entity.npc;

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
 * <p>Files live at:
 * <ul>
 *   <li>{@code assets/got/names_male.txt}</li>
 *   <li>{@code assets/got/names_female.txt}</li>
 * </ul>
 * Each file contains one name per line; blank lines and lines starting
 * with {@code #} are ignored.
 */
public final class NpcNameHelper {

    private static final String MALE_RESOURCE   = "/assets/got/names_male.txt";
    private static final String FEMALE_RESOURCE = "/assets/got/names_female.txt";

    private static volatile List<String> MALE_NAMES   = null;
    private static volatile List<String> FEMALE_NAMES = null;

    private NpcNameHelper() {}

    public static String randomName(NpcGender gender, RandomSource random) {
        List<String> pool = (gender == NpcGender.FEMALE) ? getFemaleNames() : getMaleNames();
        if (pool.isEmpty()) {
            return gender == NpcGender.FEMALE ? "Unnamed Woman" : "Unnamed Man";
        }
        return pool.get(random.nextInt(pool.size()));
    }

    private static List<String> getMaleNames() {
        if (MALE_NAMES == null) {
            synchronized (NpcNameHelper.class) {
                if (MALE_NAMES == null) MALE_NAMES = Collections.unmodifiableList(loadNames(MALE_RESOURCE));
            }
        }
        return MALE_NAMES;
    }

    private static List<String> getFemaleNames() {
        if (FEMALE_NAMES == null) {
            synchronized (NpcNameHelper.class) {
                if (FEMALE_NAMES == null) FEMALE_NAMES = Collections.unmodifiableList(loadNames(FEMALE_RESOURCE));
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
                    if (!line.isEmpty() && !line.startsWith("#")) names.add(line);
                }
            }
        } catch (Exception e) {
            System.err.println("[GOT] NpcNameHelper: failed to load " + resourcePath + " — " + e.getMessage());
        }
        return names;
    }
}