package net.got.event.entity.npc.data.name;

import net.minecraft.util.RandomSource;

@FunctionalInterface
public interface NameGenerator {

    NameGenerator NAMELESS = (rand, male) -> "";

    String generateName(RandomSource rand, boolean male);
}