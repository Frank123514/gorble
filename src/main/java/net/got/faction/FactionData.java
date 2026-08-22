package net.got.faction;

import java.util.List;

public record FactionData(
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

    public enum Religion {
        FAITH_OF_THE_SEVEN("The Seven"),
        OLD_GODS("The Old Gods"),
        DROWNED_GOD("The Drowned God"),
        RED_GOD("R'hllor, the Lord of Light"),
        MANY_FACED_GOD("The Many-Faced God"),
        UNKNOWN("Unknown");

        public final String displayName;
        Religion(String displayName) { this.displayName = displayName; }
    }

    public enum Economy {
        MILITARY ("Martial — arms, armour and warhorses"),
        AGRICULTURAL("Agricultural — grain, livestock and timber"),
        MARITIME  ("Maritime — ships, fish and exotic goods"),
        MINING    ("Mining — gold, silver and raw materials"),
        TRADE     ("Trade — coin, luxury goods and influence");

        public final String description;
        Economy(String description) { this.description = description; }
    }

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

    public record RankTitle(int minReputation, String title, String privileges) {}

    public RankTitle rankFor(int standing) {
        RankTitle current = rankTitles.isEmpty() ? null : rankTitles.get(0);
        for (RankTitle rt : rankTitles) {
            if (standing >= rt.minReputation()) current = rt;
        }
        return current;
    }

    public boolean isAlly(String otherId) {
        return allies.contains(otherId);
    }

    public boolean isEnemy(String otherId) {
        return enemies.contains(otherId);
    }
}
