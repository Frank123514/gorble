package net.got.event.entity.npc.data;

import net.minecraft.util.RandomSource;

public enum NpcPersonality {

    BRAVE     ("brave"),
    CAUTIOUS  ("cautious"),
    FRIENDLY  ("friendly"),
    GRUFF     ("gruff"),
    PIOUS     ("pious"),
    SUSPICIOUS("suspicious");

    public final String id;

    NpcPersonality(String id) { this.id = id; }

    private static final NpcPersonality[] VALUES = values();

    public static NpcPersonality random(RandomSource rand) {
        return VALUES[rand.nextInt(VALUES.length)];
    }

    public static NpcPersonality fromString(String s) {
        for (NpcPersonality p : VALUES) {
            if (p.id.equals(s)) return p;
        }
        return FRIENDLY;
    }
}
