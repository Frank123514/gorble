package net.got.event.entity.npc.data.name;

import net.minecraft.resources.Identifier;

public final class NpcNames {

    private NpcNames() {}

    private static SimpleGenderedNameGenerator gendered(String culture) {
        return new SimpleGenderedNameGenerator(
                rl(culture + "_male"),
                rl(culture + "_female"));
    }

    private static Identifier rl(String path) {
        return Identifier.fromNamespaceAndPath("got", path);
    }

    public static final NameGenerator NORTHMAN    = gendered("northman");
    public static final NameGenerator RIVERLANDER = gendered("riverlander");
    public static final NameGenerator VALEMAN     = gendered("valeman");
    public static final NameGenerator WESTERMAN   = gendered("westerman");
    public static final NameGenerator STORMLORDER = gendered("stormlorder");
    public static final NameGenerator IRONBORN    = gendered("ironborn");
    public static final NameGenerator DORNISHMAN  = gendered("dornishman");
    public static final NameGenerator REACHMAN    = gendered("reachman");

    // Crownlands
    public static final NameGenerator CROWNLANDER = gendered("crownlander");

    // Free Folk (beyond the Wall). One generic bank plus distinct banks for peoples with their own naming.
    public static final NameGenerator FREEFOLK_GENERIC = gendered("freefolk");
    /** Female-only bank (spearwives); used for either gender flag so it never yields a male name. */
    public static final NameGenerator FREEFOLK_FEMALE  = new SimpleGenderedNameGenerator(
            rl("freefolk_female"), rl("freefolk_female"));
    public static final NameGenerator SKAGOSSI = gendered("skagossi");
    public static final NameGenerator THENN    = gendered("thenn");

}