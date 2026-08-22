package net.got.faction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static net.got.faction.FactionData.*;

public final class Factions {

    public static final String WESTEROS  = "westeros";
    public static final String ESSOS     = "essos";
    public static final String SOTHORYOS = "sothoryos";

    public static final Map<String, String> CONTINENTS = new LinkedHashMap<>();

    public static final Map<String, FactionData> BY_ID = new LinkedHashMap<>();

    public static final Map<String, List<FactionData>> BY_CONTINENT = new LinkedHashMap<>();

    private static final int COL_STARK_GREY     = 0xFF708090;
    private static final int COL_STARK_WHITE    = 0xFFF0F0F0;
    private static final int COL_ARRYN_BLUE     = 0xFF1E4D8C;
    private static final int COL_ARRYN_WHITE    = 0xFFEEEEEE;
    private static final int COL_TULLY_BLUE     = 0xFF1A5276;
    private static final int COL_TULLY_RED      = 0xFFB22222;
    private static final int COL_LANNISTER_GOLD = 0xFFD4AF37;
    private static final int COL_LANNISTER_RED  = 0xFF8B0000;
    private static final int COL_TYRELL_GREEN   = 0xFF228B22;
    private static final int COL_TYRELL_GOLD    = 0xFFCFB53B;
    private static final int COL_BARATHEON_GOLD = 0xFFDAA520;
    private static final int COL_BARATHEON_BLCK = 0xFF1C1C1C;
    private static final int COL_GREYJOY_GOLD   = 0xFFB8860B;
    private static final int COL_GREYJOY_GREY   = 0xFF555555;
    private static final int COL_MARTELL_ORANGE = 0xFFCC5500;
    private static final int COL_MARTELL_YELLOW = 0xFFFFD700;
    private static final int COL_NIGHTS_WATCH   = 0xFF1A1A1A;
    private static final int COL_NIGHTS_BLACK   = 0xFF0D0D0D;

    private static List<RankTitle> westernLadder(String houseName, String cultureSuffix) {
        return List.of(
                new RankTitle(    0, "Refugee of " + cultureSuffix,
                        "No privileges. Hostility from guards and soldiers."),
                new RankTitle(  200, "Smallfolk of " + cultureSuffix,
                        "May enter villages and markets. Basic NPC trade unlocked."),
                new RankTitle(  600, "Freeman of " + cultureSuffix,
                        "Access to faction crafting recipes (leather armour tier). " +
                                "May hire common labourers."),
                new RankTitle( 1200, "Retainer of House " + houseName,
                        "Access to levy-tier equipment. Common soldiers will not attack on sight. " +
                                "Discounted food prices at faction markets."),
                new RankTitle( 2000, "Bannerman of House " + houseName,
                        "May fly the banner of " + cultureSuffix + ". " +
                                "Access to chainmail and faction-specific weapons. " +
                                "Can hire levy soldiers."),
                new RankTitle( 3200, "Sworn Sword of House " + houseName,
                        "Named and acknowledged by the castellan. " +
                                "Access to half-plate armour and faction shields. " +
                                "May receive minor quests from lords."),
                new RankTitle( 4800, "Household Knight of House " + houseName,
                        "Seat at the lord's table. Full plate armour unlocked. " +
                                "May duel rival factions for reputation. " +
                                "Eligible for command of small garrison."),
                new RankTitle( 6500, "Knight of " + cultureSuffix,
                        "Knighted by a lord. Heavy cavalry unit access. " +
                                "May establish a camp in faction territory."),
                new RankTitle( 8000, "Landed Knight of " + cultureSuffix,
                        "Granted a modest keep. Can collect taxes from nearby NPCs. " +
                                "Garrison of four soldiers assigned."),
                new RankTitle( 9200, "Lord of " + cultureSuffix,
                        "Holds a lordship in the name of the Great House. " +
                                "Full NPC soldier roster unlocked. Feasts and trade rights."),
                new RankTitle(10000, "Lord Paramount of " + cultureSuffix,
                        "The highest honour. Commands the entire faction army. " +
                                "May declare war and forge alliances. " +
                                "Faction's strongest armour and weapons available.")
        );
    }

    private static List<RankTitle> nightsWatchLadder() {
        return List.of(
                new RankTitle(    0, "Steward's Charge",
                        "Newly arrived. Must prove worth to the Watch."),
                new RankTitle(  200, "Steward of the Watch",
                        "Assigned duties. Access to Watch food stores and barracks."),
                new RankTitle(  600, "Builder of the Watch",
                        "May repair and reinforce Wall structures. Stone tools unlocked."),
                new RankTitle( 1200, "Ranger of the Watch",
                        "Permitted beyond the Wall. Access to black iron armour."),
                new RankTitle( 2000, "Senior Ranger",
                        "Leads patrols. Can recruit Sworn Brothers. Access to obsidian tools."),
                new RankTitle( 3200, "Sworn Brother",
                        "Full brother of the Watch. May carry dragonglass weapons."),
                new RankTitle( 4800, "First Ranger",
                        "Commands ranging parties. Heavy black plate unlocked. " +
                                "May issue patrol orders to NPC rangers."),
                new RankTitle( 6500, "First Steward",
                        "Master of the Watch's logistics. Trade privileges at Castle Black."),
                new RankTitle( 8000, "First Builder",
                        "Engineers the Wall's defences. Unique construction unlocks."),
                new RankTitle( 9200, "Lord Commander's Champion",
                        "Named blade of the Lord Commander. " +
                                "Dragonglass and Valyrian-quality steel available."),
                new RankTitle(10000, "Lord Commander of the Night's Watch",
                        "Commands the entire Watch. " +
                                "All Watch garrison units available. Unique Lord Commander armour.")
        );
    }

    private static List<RankTitle> ironbornLadder() {
        return List.of(
                new RankTitle(    0, "Thrall of Pyke",
                        "No standing among the Ironborn. Treated as a salt wife or slave."),
                new RankTitle(  200, "Fisherman of the Iron Islands",
                        "Acknowledged as useful. May trade at docks."),
                new RankTitle(  600, "Oarsman",
                        "Earns a place at the oar. Access to basic ironborn gear."),
                new RankTitle( 1200, "Deckhand",
                        "Trusted crew. May man a longship's rail. Access to axes and shields."),
                new RankTitle( 2000, "Salt Wife's Husband",
                        "Has taken plunder. Iron-forged weapons unlocked. May hire oarsmen."),
                new RankTitle( 3200, "Reaver",
                        "Has led a raid. Ironborn mail unlocked. May command a small crew."),
                new RankTitle( 4800, "Thane",
                        "Holds a small island or hamlet. Can collect iron price from surroundings. " +
                                "Heavy naval equipment unlocked."),
                new RankTitle( 6500, "Captain of the Iron Fleet",
                        "Commands a longship and full crew. May declare reaving campaigns."),
                new RankTitle( 8000, "Lord of the Iron Islands",
                        "Commands a fleet of ships. All iron-price equipment unlocked. " +
                                "May raid allied coastlines for tribute."),
                new RankTitle( 9200, "High King of the Iron Islands",
                        "Named by the kingsmoot. Near-complete ironborn power."),
                new RankTitle(10000, "Lord Reaper of Pyke",
                        "Lord of all the Iron Islands, seat at Pyke. " +
                                "Legendary Drowned God blessing. Full fleet command.")
        );
    }

    private static List<RankTitle> dornishLadder() {
        return List.of(
                new RankTitle(    0, "Sand of Dorne",
                        "Lowest born. No privileges in Dornish society."),
                new RankTitle(  200, "Smallfolk of Dorne",
                        "Acknowledged. May trade in the markets of Sunspear."),
                new RankTitle(  600, "Sand-rider",
                        "Proven survival skills. Desert travel speed bonus. Light armour unlocked."),
                new RankTitle( 1200, "Retainer of House Martell",
                        "Sworn in service. Dornish short sword and viper shield unlocked."),
                new RankTitle( 2000, "Shadow City Guard",
                        "Recognised warrior. Dornish scale armour unlocked. NPC trade improved."),
                new RankTitle( 3200, "Knight of the Sun",
                        "Honoured fighter of Dorne. Access to Dornish cavalry. " +
                                "May duel in the honour court."),
                new RankTitle( 4800, "Castellan of the Red Mountains",
                        "Commands a pass or watchtower in the mountain passes. " +
                                "Scorpion bolt weapons unlocked."),
                new RankTitle( 6500, "Lady/Lord of the Sandstone",
                        "Holds a castle on the sands. Full Dornish plate unlocked."),
                new RankTitle( 8000, "Princess/Prince of the Reach",
                        "Commands a domain. Dornish poison weapons available."),
                new RankTitle( 9200, "Voice of Sunspear",
                        "Speaks for the Prince. Access to the Water Gardens. " +
                                "Legendary spear of the Martells."),
                new RankTitle(10000, "Prince/Princess of Dorne",
                        "Rules Dorne. Sunspear stronghold access, full Dornish power. " +
                                "Unbowed, Unbent, Unbroken.")
        );
    }

    static {
        
        CONTINENTS.put(WESTEROS,  "Westeros");
        CONTINENTS.put(ESSOS,     "Essos");
        CONTINENTS.put(SOTHORYOS, "Sothoryos");

        List<FactionData> westeros = List.of(

                new FactionData(
                        "north", WESTEROS,
                        "The North",
                        "House Stark",
                        "Winter is Coming",
                        "Winterfell",
                        "The Iron Throne",
                        "A grey direwolf on a white field",
                        COL_STARK_GREY, COL_STARK_WHITE,
                        "northman",
                        Religion.OLD_GODS,
                        Economy.MILITARY,
                        MilitaryStyle.HEAVY_INFANTRY,
                        List.of("nights_watch"),
                        List.of("iron_islands"),
                        List.of("karstark", "umber", "manderly",
                                "mormont", "glover", "reed"),
                        westernLadder("Stark", "the North"),
                        "Ruled by House Stark since the Age of Heroes. Honour, duty and hard winters " +
                                "forged a people who never forget — the North remembers."
                ),

                new FactionData(
                        "vale", WESTEROS,
                        "The Vale of Arryn",
                        "House Arryn",
                        "As High as Honour",
                        "The Eyrie",
                        "The Iron Throne",
                        "A white moon-and-falcon on a sky-blue field",
                        COL_ARRYN_BLUE, COL_ARRYN_WHITE,
                        "valeman",
                        Religion.FAITH_OF_THE_SEVEN,
                        Economy.MILITARY,
                        MilitaryStyle.HEAVY_CAVALRY,
                        List.of("north", "riverlands"),
                        List.of("iron_islands"),
                        List.of("royce", "waynwood", "hunter",
                                "corbray", "grafton", "hersy"),
                        westernLadder("Arryn", "the Vale"),
                        "The Eyrie has never fallen by force, making the Vale the most defensible " +
                                "realm in Westeros. Its knights are among the finest in the land."
                ),

                new FactionData(
                        "riverlands", WESTEROS,
                        "The Riverlands",
                        "House Tully",
                        "Family, Duty, Honour",
                        "Riverrun",
                        "The Iron Throne",
                        "A leaping silver trout on a red-and-blue striped field",
                        COL_TULLY_BLUE, COL_TULLY_RED,
                        "riverlander",
                        Religion.FAITH_OF_THE_SEVEN,
                        Economy.AGRICULTURAL,
                        MilitaryStyle.HEAVY_INFANTRY,
                        List.of("north", "vale"),
                        List.of("westerlands"),
                        List.of("frey", "mallister", "blackwood",
                                "bracken", "mooton", "whent"),
                        westernLadder("Tully", "the Riverlands"),
                        "Fought over by every army marching south. House Tully endures through " +
                                "family and honour rather than wealth or fear."
                ),

                new FactionData(
                        "westerlands", WESTEROS,
                        "The Westerlands",
                        "House Lannister",
                        "Hear Me Roar",
                        "Casterly Rock",
                        "The Iron Throne",
                        "A golden lion on a crimson field",
                        COL_LANNISTER_GOLD, COL_LANNISTER_RED,
                        "westerman",
                        Religion.FAITH_OF_THE_SEVEN,
                        Economy.MINING,
                        MilitaryStyle.HEAVY_CAVALRY,
                        List.of("reach"),
                        List.of("north", "riverlands"),
                        List.of("clegane", "swyft", "westerling",
                                "marbrand", "lefford", "payne"),
                        westernLadder("Lannister", "the Westerlands"),
                        "Home to the richest gold mines in Westeros. House Lannister rules from " +
                                "Casterly Rock, wealthy and proud — and always pays its debts."
                ),

                new FactionData(
                        "reach", WESTEROS,
                        "The Reach",
                        "House Tyrell",
                        "Growing Strong",
                        "Highgarden",
                        "The Iron Throne",
                        "A golden rose on a green field",
                        COL_TYRELL_GREEN, COL_TYRELL_GOLD,
                        "reachman",
                        Religion.FAITH_OF_THE_SEVEN,
                        Economy.AGRICULTURAL,
                        MilitaryStyle.HEAVY_CAVALRY,
                        List.of("westerlands"),
                        List.of("iron_islands"),
                        List.of("hightower", "redwyne", "tarly",
                                "florent", "fossoway", "oakheart"),
                        westernLadder("Tyrell", "the Reach"),
                        "The most fertile land in Westeros, feeding the realm and fielding its " +
                                "largest armies. Highgarden thrives on beauty and political cunning."
                ),

                new FactionData(
                        "stormlands", WESTEROS,
                        "The Stormlands",
                        "House Baratheon",
                        "Ours is the Fury",
                        "Storm's End",
                        "The Iron Throne",
                        "A crowned black stag on a golden field",
                        COL_BARATHEON_GOLD, COL_BARATHEON_BLCK,
                        "stormlander",
                        Religion.FAITH_OF_THE_SEVEN,
                        Economy.MILITARY,
                        MilitaryStyle.HEAVY_INFANTRY,
                        List.of("vale"),
                        List.of("reach", "iron_islands"),
                        List.of("selmy", "dondarrion", "swann",
                                "connington", "morrigen", "caeron"),
                        westernLadder("Baratheon", "the Stormlands"),
                        "Storm's End has never fallen to siege. Stormlanders are among the fiercest " +
                                "warriors in Westeros — their fury is no idle boast."
                ),

                new FactionData(
                        "iron_islands", WESTEROS,
                        "The Iron Islands",
                        "House Greyjoy",
                        "We Do Not Sow",
                        "Pyke",
                        "The Iron Throne",
                        "A golden kraken on a black field",
                        COL_GREYJOY_GOLD, COL_GREYJOY_GREY,
                        "ironborn",
                        Religion.DROWNED_GOD,
                        Economy.MARITIME,
                        MilitaryStyle.NAVAL,
                        List.of(),
                        List.of("north", "riverlands",
                                "reach", "stormlands"),
                        List.of("harlaw", "botley", "stonehouse",
                                "goodbrother", "merlyn", "farwynd"),
                        ironbornLadder(),
                        "The Ironborn take what they want by axe and oar, under the Old Way of the " +
                                "Iron Price. What is dead may never die, but rises again, harder and stronger."
                ),

                new FactionData(
                        "dorne", WESTEROS,
                        "Dorne",
                        "House Martell",
                        "Unbowed, Unbent, Unbroken",
                        "Sunspear",
                        "The Iron Throne",
                        "A gold spear piercing a red sun on an orange field",
                        COL_MARTELL_ORANGE, COL_MARTELL_YELLOW,
                        "dornishman",
                        Religion.FAITH_OF_THE_SEVEN,
                        Economy.TRADE,
                        MilitaryStyle.SKIRMISHER,
                        List.of(),
                        List.of("reach", "stormlands"),
                        List.of("yronwood", "uller", "qorgyle",
                                "dayne", "allyrion", "jordayne"),
                        dornishLadder(),
                        "Never conquered by force, joining the realm only by marriage. Proud, sun-scorched " +
                                "and unbowed — Unbowed, Unbent, Unbroken."
                ),

                new FactionData(
                        "nights_watch", WESTEROS,
                        "The Night's Watch",
                        "The Night's Watch",
                        "Night Gathers and Now My Watch Begins",
                        "Castle Black",
                        "No One (Ancient Oath)",
                        "A black sword on a black field",
                        COL_NIGHTS_WATCH, COL_NIGHTS_BLACK,
                        "watchman",
                        Religion.OLD_GODS,
                        Economy.MILITARY,
                        MilitaryStyle.HEAVY_INFANTRY,
                        List.of("north"),
                        List.of(),
                        List.of("shadow_tower", "eastwatch"),
                        nightsWatchLadder(),
                        "Older than the Seven Kingdoms, the Watch guards the Wall so the realm may " +
                                "sleep. A man who takes the black gives up name and family, keeping only his oath."
                )
        );

        List<FactionData> essos = List.of(
                new FactionData(
                        "essos_coming_soon", ESSOS, "Coming Soon",
                        "Unknown", "Unknown", "Unknown", "Unknown",
                        "Unknown",
                        0xFF888888, 0xFF444444,
                        "essosi", Religion.UNKNOWN, Economy.TRADE, MilitaryStyle.LIGHT_CAVALRY,
                        List.of(), List.of(), List.of(),
                        List.of(new RankTitle(0, "Traveller", "No faction privileges.")),
                        "The great cities of Essos — Pentos, Braavos, Volantis, Meereen " +
                                "and beyond — will open their gates in a future update."
                )
        );

        List<FactionData> sothoryos = List.of(
                new FactionData(
                        "sothoryos_coming_soon", SOTHORYOS, "Coming Soon",
                        "Unknown", "Unknown", "Unknown", "Unknown",
                        "Unknown",
                        0xFF335533, 0xFF112211,
                        "sothoryosi", Religion.UNKNOWN, Economy.MILITARY, MilitaryStyle.SKIRMISHER,
                        List.of(), List.of(), List.of(),
                        List.of(new RankTitle(0, "Explorer", "No faction privileges.")),
                        "The dark jungles of Sothoryos hold many secrets. " +
                                "This continent will be explored in a future update."
                )
        );

        for (FactionData f : westeros)  BY_ID.put(f.id(), f);
        for (FactionData f : essos)     BY_ID.put(f.id(), f);
        for (FactionData f : sothoryos) BY_ID.put(f.id(), f);

        BY_CONTINENT.put(WESTEROS,  westeros);
        BY_CONTINENT.put(ESSOS,     essos);
        BY_CONTINENT.put(SOTHORYOS, sothoryos);
    }

    public static FactionData get(String id) {
        return BY_ID.get(id);
    }

    public static List<FactionData> forContinent(String continentKey) {
        return BY_CONTINENT.getOrDefault(continentKey, List.of());
    }

    private Factions() {}
}