package net.got.event.entity.npc.data;

import net.minecraft.util.RandomSource;

@FunctionalInterface
public interface GenderProvider {

    GenderProvider MALE_OR_FEMALE = rand -> rand.nextBoolean();

    GenderProvider MALE = rand -> true;

    GenderProvider FEMALE = rand -> false;

    boolean isMale(RandomSource rand);
}