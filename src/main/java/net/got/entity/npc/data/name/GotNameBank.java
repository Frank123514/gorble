package net.got.entity.npc.data.name;

import java.util.List;
import net.minecraft.util.RandomSource;

/**
 * An immutable list of names that can return a random entry.
 * Loaded from JSON by {@link GotNameBankManager}.
 */
public final class GotNameBank {

    private final List<String> names;

    public GotNameBank(List<String> names) {
        this.names = List.copyOf(names);
    }

    public String getRandomName(RandomSource rand) {
        if (names.isEmpty()) return "Unknown";
        return names.get(rand.nextInt(names.size()));
    }
}