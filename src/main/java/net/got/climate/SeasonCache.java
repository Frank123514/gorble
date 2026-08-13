package net.got.climate;

public final class SeasonCache {

    private static volatile Season season = Season.SUMMER;

    public static Season get() {
        return season;
    }

    public static void set(Season s) {
        season = s;
    }

    private SeasonCache() {}
}
