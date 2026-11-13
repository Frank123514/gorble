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

}