package net.got.event.entity.giant;

import net.minecraft.util.RandomSource;

public final class GiantNameBank {

    private GiantNameBank() {}

    private static final String[] NAMES = {
            
            "Mag Mar Tun Doh Weg",
            "Wun Weg Wun Dar Wun",
            "Dongo the Doomed",
            
            "Gur Mar Noth",
            "Bok Dun Wrath",
            "Tor Mag the Crusher",
            "Grenn Bone-Shaker",
            "Dun Wun Stonefoot",
            "Vul Gar Mar",
            "Haggar the Tall",
            "Ruk Mag Ironhide",
            "Bolg Dun Wun",
            "Nar Mar Dul",
            "Krog of the Ice",
            "Yam Nag the Elder",
            "Grath Bon Wrath",
            "Dor Wun Doh",
            "Mag Bol Stonefist",
            "Brun Nar the Black",
    };

    public static String randomName(RandomSource rng) {
        return NAMES[rng.nextInt(NAMES.length)];
    }
}