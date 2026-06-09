package net.got.faction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static net.got.faction.GotFactionData.*;

/**
 * Central registry of every playable faction.
 *
 * <p>Each faction encodes:
 * <ul>
 *   <li>Heraldic colours for UI tinting.</li>
 *   <li>Cultural identity and religion.</li>
 *   <li>Economic base and military doctrine.</li>
 *   <li>Political allies, enemies and sworn vassals.</li>
 *   <li>A full social rank ladder (Smallfolk → Lord Paramount).</li>
 *   <li>Rich lore shown on the selection screen.</li>
 * </ul>
 *
 * <p>Add new continents / regions here as the mod expands.
 * Order is preserved — entries appear on screen in definition order.
 */
public final class GotFactions {

    // ── Continent keys ────────────────────────────────────────────────────────
    public static final String WESTEROS  = "westeros";
    public static final String ESSOS     = "essos";
    public static final String SOTHORYOS = "sothoryos";

    /** Ordered map: continent key → display label shown on the continent tab. */
    public static final Map<String, String> CONTINENTS = new LinkedHashMap<>();

    /** All factions, keyed by their unique id. */
    public static final Map<String, GotFactionData> BY_ID = new LinkedHashMap<>();

    /** Factions grouped by continent key. */
    public static final Map<String, List<GotFactionData>> BY_CONTINENT = new LinkedHashMap<>();

    // ── Heraldic colour constants (ARGB) ──────────────────────────────────────
    // These drive UI tinting (faction name colour, panel border, confirm button glow, etc.)

    private static final int COL_STARK_GREY     = 0xFF708090; // slate grey
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

    // ── Shared rank-ladder builder ────────────────────────────────────────────

    /**
     * Constructs the standard Westerosi social ladder for a Great House.
     *
     * <p>Rank titles are customised per culture via the {@code houseName}
     * and {@code cultureSuffix} parameters.
     *
     * @param houseName      e.g. "Stark", used in titles like "Bannerman of Stark"
     * @param cultureSuffix  e.g. "the North", "the Vale", "Dorne"
     */
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

    /** Specialised rank ladder for the Night's Watch — no noble titles. */
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

    /** Ironborn ladder uses their own cultural hierarchy. */
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

    /** Dornish ladder reflects their unique cultural customs and equal inheritance. */
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

    // ═════════════════════════════════════════════════════════════════════════
    // STATIC INITIALISER
    // ═════════════════════════════════════════════════════════════════════════

    static {
        // ── Continents ────────────────────────────────────────────────────────
        CONTINENTS.put(WESTEROS,  "Westeros");
        CONTINENTS.put(ESSOS,     "Essos");
        CONTINENTS.put(SOTHORYOS, "Sothoryos");

        // ── Westeros ──────────────────────────────────────────────────────────
        List<GotFactionData> westeros = List.of(

            // ─ THE NORTH ─────────────────────────────────────────────────────
            new GotFactionData(
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
                List.of("nights_watch"),                       // allies
                List.of("iron_islands"),                       // enemies
                List.of("karstark", "umber", "manderly",       // vassals
                        "mormont", "glover", "reed"),
                westernLadder("Stark", "the North"),
                "The Starks of Winterfell have ruled the North for eight thousand years, " +
                "since the days of Bran the Builder who raised the Wall. Honour, duty, " +
                "and the Old Gods define the northmen. They are a hard people forged by " +
                "hard winters — every decision is made with the certainty that the cold " +
                "will always come again. A Stark does not lie, does not play the game of " +
                "thrones, and does not forget. Their bannermen are fiercely loyal, and the " +
                "North remembers. Those who earn the trust of Winterfell earn a brotherhood " +
                "that holds through ice and war alike."
            ),

            // ─ THE VALE ──────────────────────────────────────────────────────
            new GotFactionData(
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
                "Nestled among the Mountains of the Moon, the Vale is the most defensible " +
                "region in all of Westeros. The Eyrie has never been taken by force, and " +
                "the mountain clans ensure that few armies ever reach its base. House Arryn " +
                "are among the oldest of the great houses, their blood traced back to the " +
                "First Men. Their knights are the finest in the realm — disciplined, armoured " +
                "in gleaming plate, trained since birth in lance and sword. As High as Honour " +
                "is not merely a motto: it is a way of life."
            ),

            // ─ THE RIVERLANDS ────────────────────────────────────────────────
            new GotFactionData(
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
                "The Riverlands sit at the crossroads of Westeros — every army that has ever " +
                "marched south has passed through these fertile flatlands, burning and plundering " +
                "as they go. House Tully endures. They command loyalty not through wealth or " +
                "terror, but through honour and the bonds of kinship forged over generations. " +
                "Family first, duty second, honour last — in that order, always. The rivers " +
                "that gave this region its name feed its people and carry its trade, and those " +
                "who serve Riverrun faithfully will never go hungry."
            ),

            // ─ THE WESTERLANDS ───────────────────────────────────────────────
            new GotFactionData(
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
                "Rich beyond measure, the Westerlands are built upon the greatest gold mines " +
                "in the known world. Casterly Rock is not merely a castle — it is a mountain, " +
                "hollowed out and made impregnable over a thousand years of Lannister tenure. " +
                "House Lannister always pays its debts, and never forgets an insult. Their " +
                "words are 'Hear Me Roar' but their unofficial motto is worth far more: a " +
                "Lannister pays his debts. Serve them well and you will be rewarded beyond " +
                "your expectations. Cross them and you will wish you hadn't."
            ),

            // ─ THE REACH ─────────────────────────────────────────────────────
            new GotFactionData(
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
                "The most fertile land in Westeros, the Reach feeds the realm and fields its " +
                "largest armies. Highgarden is a place of beauty — roses, warm summers, and " +
                "chivalry flourish here where they wither elsewhere. House Tyrell rose to " +
                "prominence by choosing the right side at the right moment, and they have " +
                "turned political cunning into an art form. Their wealth in grain, roses and " +
                "knights makes them indispensable. Those who grow strong in the Reach do not " +
                "merely survive — they thrive."
            ),

            // ─ THE STORMLANDS ────────────────────────────────────────────────
            new GotFactionData(
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
                "Storm's End has never fallen to siege — a testament to the iron stubbornness " +
                "of House Baratheon. Built by Bran the Builder to withstand the eternal storms " +
                "of the Narrow Sea, it has endured sieges that broke other castles like twigs. " +
                "House Baratheon was born in battle when Orys Baratheon slew Argilac the " +
                "Arrogant and claimed his lands, his seat, and his daughter. Stormlanders are " +
                "among the fiercest warriors in Westeros — direct, blunt, proud. Their fury " +
                "is not an idle boast."
            ),

            // ─ THE IRON ISLANDS ──────────────────────────────────────────────
            new GotFactionData(
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
                List.of(),                                     // no natural allies
                List.of("north", "riverlands",
                        "reach", "stormlands"),
                List.of("harlaw", "botley", "stonehouse",
                        "goodbrother", "merlyn", "farwynd"),
                ironbornLadder(),
                "The Ironborn take what they want with axe and oar. They call it the Iron " +
                "Price, and they are very proud of it. House Greyjoy rules the windswept " +
                "Iron Islands under the Old Way — an ancient code that demands all wealth " +
                "be seized by strength. The Drowned God waits beneath the waves and calls " +
                "his faithful home. What is dead may never die, but rises again, harder and " +
                "stronger. The sea is their road, their food, their weapon and their god. " +
                "Those who follow the Old Way embrace a life of glorious, brutal simplicity."
            ),

            // ─ DORNE ─────────────────────────────────────────────────────────
            new GotFactionData(
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
                "Dorne was never truly conquered — not by the Targaryens with their dragons, " +
                "not by the combined armies of six kingdoms. It joined the realm through a " +
                "marriage pact, retaining unique rights no other domain possesses. The Dornish " +
                "are a proud and passionate people, shaped by a blazing sun and a refusal to " +
                "bow. They are the only kingdom where inheritance passes through the eldest " +
                "child regardless of sex. Their warriors favour swift, poisoned blades over " +
                "heavy armour, and their spears have never been broken. Unbowed, Unbent, " +
                "Unbroken — three words that say everything."
            ),

            // ─ THE NIGHT'S WATCH ─────────────────────────────────────────────
            new GotFactionData(
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
                List.of(),                                     // enemy to none by oath
                List.of("shadow_tower", "eastwatch"),
                nightsWatchLadder(),
                "The Night's Watch is older than the Seven Kingdoms, older than the Wall " +
                "itself in spirit if not in stone. When a man takes the black, he gives up " +
                "name, land, titles and family. He gives up everything except his oath. He " +
                "stands the watch so that the realm of men may sleep. Their numbers have " +
                "dwindled over centuries as the threat beyond the Wall was forgotten, dismissed " +
                "as legend. But the wildlings mass, the cold grows deeper, and the dead walk " +
                "again. The Watch needs men. Good men. Strong men. The kind who don't turn " +
                "away from what the darkness holds."
            )
        );

        // ── Essos (placeholders, expandable) ─────────────────────────────────
        List<GotFactionData> essos = List.of(
            new GotFactionData(
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

        // ── Sothoryos (placeholder) ───────────────────────────────────────────
        List<GotFactionData> sothoryos = List.of(
            new GotFactionData(
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

        // ── Populate lookup maps ──────────────────────────────────────────────
        for (GotFactionData f : westeros)  BY_ID.put(f.id(), f);
        for (GotFactionData f : essos)     BY_ID.put(f.id(), f);
        for (GotFactionData f : sothoryos) BY_ID.put(f.id(), f);

        BY_CONTINENT.put(WESTEROS,  westeros);
        BY_CONTINENT.put(ESSOS,     essos);
        BY_CONTINENT.put(SOTHORYOS, sothoryos);
    }

    // ── API ───────────────────────────────────────────────────────────────────

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
