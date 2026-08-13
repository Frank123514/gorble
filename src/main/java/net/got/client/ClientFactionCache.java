package net.got.client;

import net.got.faction.FactionData;
import net.got.faction.Factions;
import net.got.network.FactionSyncPayload;

public final class ClientFactionCache {

    private static String factionId = "";
    private static int    standing  = 0;
    private static String title     = "";

    public static void onSyncReceived(FactionSyncPayload payload) {
        factionId = payload.factionId();
        standing  = payload.standing();
        title     = payload.title();
    }

    public static String getFactionId() { return factionId; }

    public static int getStanding() { return standing; }

    public static String getTitle() { return title; }

    public static FactionData getFaction() {
        return factionId.isEmpty() ? null : Factions.get(factionId);
    }

    public static float getStandingFraction() {
        return standing / 10_000f;
    }

    public static int getNextRankThreshold() {
        FactionData faction = getFaction();
        if (faction == null) return -1;
        for (FactionData.RankTitle rt : faction.rankTitles()) {
            if (rt.minReputation() > standing) return rt.minReputation();
        }
        return -1;
    }

    private ClientFactionCache() {}
}
