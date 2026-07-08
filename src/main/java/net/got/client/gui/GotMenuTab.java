package net.got.client.gui;

/**
 * The four tabs shown on {@link GotMainMenuScreen}'s tab bar. Clicking one
 * opens its own screen - {@link GotMapScreen} for {@link #MAP},
 * {@link GotPlaceholderScreen} for the rest.
 */
public enum GotMenuTab {
    MAP("Map"), SKILLS("Skills"), MAGIC("Magic"), CULTURE("Culture");

    public final String label;

    GotMenuTab(String label) {
        this.label = label;
    }
}
