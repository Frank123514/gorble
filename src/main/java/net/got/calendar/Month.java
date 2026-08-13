package net.got.calendar;

public enum Month {

    FIRST_MOON          ("First Moon"),
    MOON_OF_FALSE_SPRING("Moon of False Spring"),
    MOON_OF_PLANTING    ("Moon of Planting"),
    MOON_OF_FLOWERS     ("Moon of Flowers"),
    MOON_OF_THE_LONG_DAY("Moon of the Long Day"),
    MOON_OF_HARVEST     ("Moon of Harvest"),
    MOON_OF_THE_TURNING ("Moon of the Turning"),
    MOON_OF_RED_LEAVES  ("Moon of Red Leaves"),
    MOON_OF_FIRST_FROST ("Moon of the First Frost"),
    MOON_OF_THE_LONG_NIGHT("Moon of the Long Night"),
    MOON_OF_ICE         ("Moon of Ice"),
    LAST_MOON           ("Last Moon");

    public final String displayName;

    Month(String displayName) {
        this.displayName = displayName;
    }

    public static Month of(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12)
            throw new IllegalArgumentException("Month must be 1–12, got: " + monthNumber);
        return values()[monthNumber - 1];
    }

    public int number() {
        return ordinal() + 1;
    }
}