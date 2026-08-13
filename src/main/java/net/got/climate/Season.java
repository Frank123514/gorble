package net.got.climate;

public enum Season {

    SPRING("Spring"),
    SUMMER("Summer"),
    AUTUMN("Autumn"),
    WINTER("Winter");

    public final String displayName;

    Season(String displayName) {
        this.displayName = displayName;
    }

    public Season next() {
        Season[] values = values();
        return values[(ordinal() + 1) % values.length];
    }

    public boolean isWinter() { return this == WINTER; }

    public boolean isTransitional() { return this == SPRING || this == AUTUMN; }
}
