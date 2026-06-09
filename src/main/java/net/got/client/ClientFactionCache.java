package net.got.client;

import net.got.faction.GotFactionData;
import net.got.faction.GotFactions;
import net.got.network.FactionSyncPayload;

/**
 * Client-side cache of the local player's faction state.
 *
 * <p>Updated whenever a {@link FactionSyncPayload} arrives from the server.
 * Use this class in all client-side rendering code (HUD overlays, screens)
 * instead of touching server-only player NBT.
 */
public final class ClientFactionCache {

    private static String factionId = "";
    private static int    standing  = 0;
    private static String title     = "";

    // ── Called by the client-side packet handler ──────────────────────────────

    public static void onSyncReceived(FactionSyncPayload payload) {
        factionId = payload.factionId();
        standing  = payload.standing();
        title     = payload.title();
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    /** The local player's faction id, or empty string if none chosen. */
    public static String getFactionId() { return factionId; }

    /** The local player's standing with their faction (0 – 10 000). */
    public static int getStanding() { return standing; }

    /** The local player's current rank title string. */
    public static String getTitle() { return title; }

    /** Returns the {@link GotFactionData} for the local player, or {@code null}. */
    public static GotFactionData getFaction() {
        return factionId.isEmpty() ? null : GotFactions.get(factionId);
    }

    /**
     * Normalised standing as a fraction in [0.0, 1.0].
     * Useful for progress-bar rendering.
     */
    public static float getStandingFraction() {
        return standing / 10_000f;
    }

    /**
     * Returns the standing required for the <em>next</em> rank title,
     * or {@code -1} if the player is at the maximum rank.
     */
    public static int getNextRankThreshold() {
        GotFactionData faction = getFaction();
        if (faction == null) return -1;
        for (GotFactionData.RankTitle rt : faction.rankTitles()) {
            if (rt.minReputation() > standing) return rt.minReputation();
        }
        return -1; // already at max
    }

    private ClientFactionCache() {}
}
