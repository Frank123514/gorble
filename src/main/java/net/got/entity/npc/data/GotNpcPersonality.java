package net.got.entity.npc.data;

import net.minecraft.util.RandomSource;

/**
 * A simple personality trait assigned to each NPC at spawn, influencing
 * speech-bank selection and (future) trade behaviour.
 *
 * <p>Mirrors LOTR's {@code PersonalityTrait} enum concept, trimmed to our needs.
 */
public enum GotNpcPersonality {

    BRAVE     ("brave"),
    CAUTIOUS  ("cautious"),
    FRIENDLY  ("friendly"),
    GRUFF     ("gruff"),
    PIOUS     ("pious"),
    SUSPICIOUS("suspicious");

    public final String id;

    GotNpcPersonality(String id) { this.id = id; }

    private static final GotNpcPersonality[] VALUES = values();

    public static GotNpcPersonality random(RandomSource rand) {
        return VALUES[rand.nextInt(VALUES.length)];
    }

    public static GotNpcPersonality fromString(String s) {
        for (GotNpcPersonality p : VALUES) {
            if (p.id.equals(s)) return p;
        }
        return FRIENDLY;
    }
}
