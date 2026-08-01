package net.got.event.entity.giant;

import net.minecraft.util.RandomSource;

/**
 * A small bank of giant names drawn from the ASOIAF / GoT lore.
 * Giants use Old Tongue names — guttural and distinctive.
 *
 * <p>Named giants from the books/show: Mag Mar Tun Doh Weg ("Mag the Mighty"),
 * Dongo the Doomed, Wun Weg Wun Dar Wun ("Wun Wun"), Giant Claw.
 * The rest follow the same phonetic pattern.
 */
public final class GiantNameBank {

    private GiantNameBank() {}

    private static final String[] NAMES = {
            // Canonical lore names
            "Mag Mar Tun Doh Weg",
            "Wun Weg Wun Dar Wun",
            "Dongo the Doomed",
            // Procedurally-flavoured additions following Old Tongue patterns
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

    /** Returns a random giant name. */
    public static String randomName(RandomSource rng) {
        return NAMES[rng.nextInt(NAMES.length)];
    }
}