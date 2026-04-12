package net.got.entity.npc.data;

import java.util.Random;

/**
 * Functional interface that decides whether a newly-spawned NPC is male.
 * Mirrors LOTR's {@code NPCGenderProvider}.
 */
@FunctionalInterface
public interface GotGenderProvider {

    /** 50/50 male-or-female — default for most cultures. */
    GotGenderProvider MALE_OR_FEMALE = rand -> rand.nextBoolean();

    /** Always male. */
    GotGenderProvider MALE = rand -> true;

    /** Always female. */
    GotGenderProvider FEMALE = rand -> false;

    /**
     * Returns {@code true} if the NPC should be male.
     *
     * @param rand the entity's RNG, seeded at spawn time
     */
    boolean isMale(Random rand);
}
