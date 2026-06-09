package net.got.faction;

import java.util.List;

/**
 * Immutable descriptor for a single playable faction (a Great House / region).
 *
 * <p>The record captures everything needed to:
 * <ul>
 *   <li>Render the faction-selection screen (display name, lore, sigil colours).</li>
 *   <li>Drive the reputation / standing system (allies, enemies, vassal list).</li>
 *   <li>Gate content behind faction membership (armour, weapons, NPCs, structures).</li>
 *   <li>Provide flavour text for titles as the player climbs the social ladder.</li>
 * </ul>
 *
 * @param id              Unique registry key, e.g. {@code "north"}.
 * @param continent       Parent continent key, e.g. {@code "westeros"}.
 * @param displayName     Human-readable region name, e.g. {@code "The North"}.
 * @param greatHouse      The ruling Great House name, e.g. {@code "House Stark"}.
 * @param words           House words / motto, e.g. {@code "Winter is Coming"}.
 * @param seat            Primary castle or seat of power, e.g. {@code "Winterfell"}.
 * @param fealtyTo        Who the Lord Paramount ultimately swears fealty to.
 * @param sigil           Short description of the house sigil for tooltip / lore text.
 * @param primaryColour   Primary heraldic colour as ARGB int (used for UI tinting).
 * @param secondaryColour Secondary heraldic colour as ARGB int.
 * @param culture         Cultural identity key used to resolve NPC skins and names.
 * @param religion        Primary religion of the faction.
 * @param economy         Economic focus: governs trade bonuses and starting resources.
 * @param militaryStyle   Combat doctrine: governs unlockable unit types.
 * @param allies          Faction ids with which this house begins as Allied.
 * @param enemies         Faction ids with which this house begins as Hostile.
 * @param vassals         Faction ids of minor houses sworn to this Great House.
 * @param rankTitles      Ordered list of {@link RankTitle} objects (lowest → highest).
 * @param lore            Long flavour description shown in the info panel.
 */
public record GotFactionData(
        String id,
        String continent,
        String displayName,
        String greatHouse,
        String words,
        String seat,
        String fealtyTo,
        String sigil,
        int primaryColour,
        int secondaryColour,
        String culture,
        Religion religion,
        Economy economy,
        MilitaryStyle militaryStyle,
        List<String> allies,
        List<String> enemies,
        List<String> vassals,
        List<RankTitle> rankTitles,
        String lore
) {

    // ── Enums embedded in the record for locality ────────────────────────────

    /** The faith practiced by the majority of this faction's smallfolk. */
    public enum Religion {
        FAITH_OF_THE_SEVEN("The Faith of the Seven"),
        OLD_GODS("The Old Gods of the Forest"),
        DROWNED_GOD("The Drowned God"),
        RED_GOD("R'hllor, the Lord of Light"),
        MANY_FACED_GOD("The Many-Faced God"),
        UNKNOWN("Unknown");

        public final String displayName;
        Religion(String displayName) { this.displayName = displayName; }
    }

    /** Primary economic base, driving trade-good production and shop unlocks. */
    public enum Economy {
        MILITARY ("Martial — arms, armour and warhorses"),
        AGRICULTURAL("Agricultural — grain, livestock and timber"),
        MARITIME  ("Maritime — ships, fish and exotic goods"),
        MINING    ("Mining — gold, silver and raw materials"),
        TRADE     ("Trade — coin, luxury goods and influence");

        public final String description;
        Economy(String description) { this.description = description; }
    }

    /**
     * Combat doctrine — determines which specialised soldier tiers are unlockable
     * and which weapons/armour sets get faction discounts.
     */
    public enum MilitaryStyle {
        HEAVY_INFANTRY ("Heavy Infantry — plate armour, axes and greatswords"),
        LIGHT_CAVALRY  ("Light Cavalry — swift riders, lances and harrying"),
        HEAVY_CAVALRY  ("Heavy Cavalry — knights in full armour"),
        NAVAL          ("Naval — longships, boarding and amphibious raids"),
        SKIRMISHER     ("Skirmisher — guerrilla archers, ambush and retreat"),
        PIKEMEN        ("Pikemen — disciplined spear walls and castle defence");

        public final String description;
        MilitaryStyle(String description) { this.description = description; }
    }

    /**
     * A single step in the social-rank ladder for this faction.
     *
     * @param minReputation  Standing threshold to reach this rank (inclusive, 0–10000).
     * @param title          Title awarded, e.g. {@code "Bannerman of the North"}.
     * @param privileges     One-line description of what this rank unlocks.
     */
    public record RankTitle(int minReputation, String title, String privileges) {}

    // ── Convenience helpers ──────────────────────────────────────────────────

    /** Returns the {@link RankTitle} the player currently holds given their standing. */
    public RankTitle rankFor(int standing) {
        RankTitle current = rankTitles.isEmpty() ? null : rankTitles.get(0);
        for (RankTitle rt : rankTitles) {
            if (standing >= rt.minReputation()) current = rt;
        }
        return current;
    }

    /** {@code true} if {@code otherId} is a registered ally of this faction. */
    public boolean isAlly(String otherId) {
        return allies.contains(otherId);
    }

    /** {@code true} if {@code otherId} is a registered enemy of this faction. */
    public boolean isEnemy(String otherId) {
        return enemies.contains(otherId);
    }
}
