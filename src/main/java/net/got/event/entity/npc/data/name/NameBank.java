package net.got.event.entity.npc.data.name;

import java.util.List;
import net.minecraft.util.RandomSource;

public final class NameBank {

    private final List<String> names;

    public NameBank(List<String> names) {
        this.names = List.copyOf(names);
    }

    public String getRandomName(RandomSource rand) {
        if (names.isEmpty()) return "Unknown";
        return names.get(rand.nextInt(names.size()));
    }
}