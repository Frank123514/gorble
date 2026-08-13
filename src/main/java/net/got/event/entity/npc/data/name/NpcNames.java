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

    public static final NameGenerator STARK_LEVY     = gendered("northman");
    public static final NameGenerator TULLY_LEVY     = gendered("riverlander");
    public static final NameGenerator LANNISTER_LEVY = gendered("westerman");
    public static final NameGenerator BARATHEON_LEVY = gendered("stormlorder");
    public static final NameGenerator GREYJOY_LEVY   = gendered("ironborn");
    public static final NameGenerator MARTELL_LEVY   = gendered("dornishman");
    public static final NameGenerator TYRELL_LEVY    = gendered("reachman");
    public static final NameGenerator ARRYN_LEVY     = gendered("valeman");

    public static final NameGenerator NORTH_SOLDIER = gendered("northman");
    public static final NameGenerator VALE_KNIGHT   = gendered("valeman");
}