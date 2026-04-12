package net.got.entity.npc.data.name;

import net.minecraft.util.RandomSource;

/**
 * Functional interface that generates a name for a newly-spawned NPC.
 * Mirrors LOTR's {@code NPCNameGenerator}.
 */
@FunctionalInterface
public interface GotNameGenerator {

    /** Fallback — returns an empty string (no personal name displayed). */
    GotNameGenerator NAMELESS = (rand, male) -> "";

    /**
     * Generate and return a name string.
     *
     * @param rand the entity's RNG
     * @param male {@code true} if the NPC is male
     * @return a name string, or {@code ""} for no name
     */
    String generateName(RandomSource rand, boolean male);
}