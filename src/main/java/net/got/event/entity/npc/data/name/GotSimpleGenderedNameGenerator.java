package net.got.event.entity.npc.data.name;

import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;

/**
 * Picks a random name from a male bank or a female bank depending on gender.
 * Mirrors LOTR's {@code SimpleGenderedNameGenerator}.
 */
public final class GotSimpleGenderedNameGenerator implements GotNameGenerator {

    private final Identifier maleBank;
    private final Identifier femaleBank;

    public GotSimpleGenderedNameGenerator(Identifier maleBank, Identifier femaleBank) {
        this.maleBank = maleBank;
        this.femaleBank = femaleBank;
    }

    @Override
    public String generateName(RandomSource rand, boolean male) {
        Identifier bank = male ? maleBank : femaleBank;
        return GotNameBankManager.INSTANCE.fetchBank(bank).getRandomName(rand);
    }
}