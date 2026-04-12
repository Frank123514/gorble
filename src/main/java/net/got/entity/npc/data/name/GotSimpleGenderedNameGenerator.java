package net.got.entity.npc.data.name;

import net.minecraft.resources.ResourceLocation;

import java.util.Random;

/**
 * Picks a random name from a male bank or a female bank depending on gender.
 * Mirrors LOTR's {@code SimpleGenderedNameGenerator}.
 */
public final class GotSimpleGenderedNameGenerator implements GotNameGenerator {

    private final ResourceLocation maleBank;
    private final ResourceLocation femaleBank;

    public GotSimpleGenderedNameGenerator(ResourceLocation maleBank, ResourceLocation femaleBank) {
        this.maleBank = maleBank;
        this.femaleBank = femaleBank;
    }

    @Override
    public String generateName(Random rand, boolean male) {
        ResourceLocation bank = male ? maleBank : femaleBank;
        return GotNameBankManager.INSTANCE.fetchBank(bank).getRandomName(rand);
    }
}
