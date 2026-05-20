package net.got.climate;

/**
 * Lightweight shared cache for the current season, readable on both the
 * server and client logical sides.
 *
 * <p>The server writes here directly whenever SeasonManager advances.
 * The client writes here when it receives a {@code SeasonSyncPayload} packet.
 * BiomeMixin reads from here so it works correctly on both sides.
 */
public final class SeasonCache {

    private static volatile GotSeason season = GotSeason.SUMMER;

    public static GotSeason get() {
        return season;
    }

    public static void set(GotSeason s) {
        season = s;
    }

    private SeasonCache() {}
}
