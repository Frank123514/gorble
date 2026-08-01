package net.got.event.entity.npc.data.name;

import net.minecraft.resources.ResourceLocation;

/**
 * Central registry of all NPC name generators for the GoT mod.
 *
 * <p>Each constant references JSON name banks stored under
 * {@code data/got/npc_names/<culture>_male.json} and
 * {@code data/got/npc_names/<culture>_female.json}.
 *
 * <p>Mirrors LOTR's {@code NPCNameGenerators}.
 */
public final class GotNpcNames {

    private GotNpcNames() {}

    // ── Helper ────────────────────────────────────────────────────────────────

    private static GotSimpleGenderedNameGenerator gendered(String culture) {
        return new GotSimpleGenderedNameGenerator(
                rl(culture + "_male"),
                rl(culture + "_female"));
    }

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath("got", path);
    }

    // ── Tier-1 Smallfolk (civilians) ──────────────────────────────────────────

    public static final GotNameGenerator NORTHMAN    = gendered("northman");
    public static final GotNameGenerator RIVERLANDER = gendered("riverlander");
    public static final GotNameGenerator VALEMAN     = gendered("valeman");
    public static final GotNameGenerator WESTERMAN   = gendered("westerman");
    public static final GotNameGenerator STORMLORDER = gendered("stormlorder");
    public static final GotNameGenerator IRONBORN    = gendered("ironborn");
    public static final GotNameGenerator DORNISHMAN  = gendered("dornishman");
    public static final GotNameGenerator REACHMAN    = gendered("reachman");

    // ── Tier-2 Levies ─────────────────────────────────────────────────────────

    public static final GotNameGenerator STARK_LEVY     = gendered("northman");
    public static final GotNameGenerator TULLY_LEVY     = gendered("riverlander");
    public static final GotNameGenerator LANNISTER_LEVY = gendered("westerman");
    public static final GotNameGenerator BARATHEON_LEVY = gendered("stormlorder");
    public static final GotNameGenerator GREYJOY_LEVY   = gendered("ironborn");
    public static final GotNameGenerator MARTELL_LEVY   = gendered("dornishman");
    public static final GotNameGenerator TYRELL_LEVY    = gendered("reachman");
    public static final GotNameGenerator ARRYN_LEVY     = gendered("valeman");

    // ── Tier-3 Skilled Fighters ───────────────────────────────────────────────

    public static final GotNameGenerator NORTH_SOLDIER = gendered("northman");
    public static final GotNameGenerator VALE_KNIGHT   = gendered("valeman");
}