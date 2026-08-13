package net.got.event.entity.npc.data.name;

import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;

public final class SimpleGenderedNameGenerator implements NameGenerator {

    private final Identifier maleBank;
    private final Identifier femaleBank;

    public SimpleGenderedNameGenerator(Identifier maleBank, Identifier femaleBank) {
        this.maleBank = maleBank;
        this.femaleBank = femaleBank;
    }

    @Override
    public String generateName(RandomSource rand, boolean male) {
        Identifier bank = male ? maleBank : femaleBank;
        return NameBankManager.INSTANCE.fetchBank(bank).getRandomName(rand);
    }
}