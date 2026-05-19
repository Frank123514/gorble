package net.got.climate;

/**
 * The four seasons of the Game of Thrones world.
 *
 * <p>Unlike real-world seasons, GoT seasons are wildly unpredictable in length —
 * a summer or winter can last for years.  Lengths are determined per-world via
 * {@link SeasonManager} and stored in world saved data.
 */
public enum GotSeason {

    SPRING("Spring"),
    SUMMER("Summer"),
    AUTUMN("Autumn"),
    WINTER("Winter");

    /** Human-readable display name. */
    public final String displayName;

    GotSeason(String displayName) {
        this.displayName = displayName;
    }

    /** Returns the season that follows this one in the cycle. */
    public GotSeason next() {
        GotSeason[] values = values();
        return values[(ordinal() + 1) % values.length];
    }

    /** True only during Winter — used by weather and environment checks. */
    public boolean isWinter() { return this == WINTER; }

    /** True during Spring or Autumn — transitional seasons. */
    public boolean isTransitional() { return this == SPRING || this == AUTUMN; }
}
