package net.got.faction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Central registry of every playable faction.
 *
 * <p>Add new continents / regions here as the mod expands.
 * Order is preserved — entries appear on screen in definition order.
 */
public final class GotFactions {

    // ── Continent keys ────────────────────────────────────────────────────────
    public static final String WESTEROS  = "westeros";
    public static final String ESSOS     = "essos";
    public static final String SOTHORYOS = "sothoryos";

    /** Ordered map: continent key → display label shown on the tab. */
    public static final Map<String, String> CONTINENTS = new LinkedHashMap<>();

    /** All factions, keyed by their unique id. */
    public static final Map<String, GotFactionData> BY_ID = new LinkedHashMap<>();

    /** Factions grouped by continent key. */
    public static final Map<String, List<GotFactionData>> BY_CONTINENT = new LinkedHashMap<>();

    static {
        // ── Register continents ───────────────────────────────────────────────
        CONTINENTS.put(WESTEROS,  "Westeros");
        CONTINENTS.put(ESSOS,     "Essos");
        CONTINENTS.put(SOTHORYOS, "Sothoryos");

        // ── Westeros factions ─────────────────────────────────────────────────
        List<GotFactionData> westeros = List.of(

                new GotFactionData(
                        "north", WESTEROS, "The North",
                        "House Stark",
                        "Winterfell",
                        "The Iron Throne",
                        "The Starks of Winterfell have ruled the North for eight thousand years. " +
                                "Honour, duty and the old ways define the northmen. Winter is always coming, " +
                                "and only a Stark can truly prepare for it."
                ),

                new GotFactionData(
                        "vale", WESTEROS, "The Vale",
                        "House Arryn",
                        "The Eyrie",
                        "The Iron Throne",
                        "Nestled among the Mountains of the Moon, the Vale is the most defensible " +
                                "region in all of Westeros. House Arryn, one of the oldest and most noble " +
                                "houses in the realm, has held the Eyrie for generations."
                ),

                new GotFactionData(
                        "riverlands", WESTEROS, "The Riverlands",
                        "House Tully",
                        "Riverrun",
                        "The Iron Throne",
                        "The Riverlands sit at the heart of Westeros, a crossroads of rivers and " +
                                "roads. House Tully commands loyalty through honour and family ties. Family, " +
                                "duty, honour — in that order."
                ),

                new GotFactionData(
                        "westerlands", WESTEROS, "The Westerlands",
                        "House Lannister",
                        "Casterly Rock",
                        "The Iron Throne",
                        "Rich beyond measure, the Westerlands are built upon the greatest gold mines " +
                                "in the known world. House Lannister always pays its debts — and expects the " +
                                "same from those who serve them."
                ),

                new GotFactionData(
                        "reach", WESTEROS, "The Reach",
                        "House Tyrell",
                        "Highgarden",
                        "The Iron Throne",
                        "The most fertile land in Westeros, the Reach feeds the realm and fields the " +
                                "largest armies. House Tyrell's wealth in grain, roses and knights makes them " +
                                "an indispensable pillar of the Seven Kingdoms."
                ),

                new GotFactionData(
                        "stormlands", WESTEROS, "The Stormlands",
                        "House Baratheon",
                        "Storm's End",
                        "The Iron Throne",
                        "Storm's End has never fallen to siege — a testament to the iron stubbornness " +
                                "of House Baratheon. Born in battle and forged by the fury of the Narrow Sea, " +
                                "stormlanders are among the fiercest warriors in Westeros."
                ),

                new GotFactionData(
                        "iron_islands", WESTEROS, "The Iron Islands",
                        "House Greyjoy",
                        "Pyke",
                        "The Iron Throne",
                        "The Ironborn take what they want with axe and oar. House Greyjoy rules the " +
                                "windswept Iron Islands under the ancient custom of the Old Way — we do not " +
                                "sow, we reap. What is dead may never die."
                ),

                new GotFactionData(
                        "dorne", WESTEROS, "Dorne",
                        "House Martell",
                        "Sunspear",
                        "The Iron Throne",
                        "Dorne was never truly conquered — it joined the realm through a marriage " +
                                "pact, and its prince rules with retained privileges no other lord can claim. " +
                                "Unbowed, unbent, unbroken: the words of House Martell say everything."
                )
        );

        // ── Essos factions (placeholder) ──────────────────────────────────────
        List<GotFactionData> essos = List.of(
                new GotFactionData(
                        "essos_coming_soon", ESSOS, "Coming Soon",
                        "Unknown",
                        "Unknown",
                        "Unknown",
                        "The great cities of Essos — Pentos, Braavos, Volantis and beyond — will " +
                                "open their gates in a future update."
                )
        );

        // ── Sothoryos factions (placeholder) ─────────────────────────────────
        List<GotFactionData> sothoryos = List.of(
                new GotFactionData(
                        "sothoryos_coming_soon", SOTHORYOS, "Coming Soon",
                        "Unknown",
                        "Unknown",
                        "Unknown",
                        "The dark jungles of Sothoryos hold many secrets. This continent " +
                                "will be explored in a future update."
                )
        );

        // ── Populate lookup maps ──────────────────────────────────────────────
        for (GotFactionData f : westeros)  BY_ID.put(f.id(), f);
        for (GotFactionData f : essos)     BY_ID.put(f.id(), f);
        for (GotFactionData f : sothoryos) BY_ID.put(f.id(), f);

        BY_CONTINENT.put(WESTEROS,  westeros);
        BY_CONTINENT.put(ESSOS,     essos);
        BY_CONTINENT.put(SOTHORYOS, sothoryos);
    }

    /** Returns the faction with the given id, or {@code null} if not found. */
    public static GotFactionData get(String id) {
        return BY_ID.get(id);
    }

    /** Returns all factions belonging to the given continent key. */
    public static List<GotFactionData> forContinent(String continentKey) {
        return BY_CONTINENT.getOrDefault(continentKey, List.of());
    }

    private GotFactions() {}
}