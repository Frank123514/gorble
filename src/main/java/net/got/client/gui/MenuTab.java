package net.got.client.gui;

public enum MenuTab {
    MAP("Map"), SKILLS("Skills"), MAGIC("Magic"), CULTURE("Culture");

    public final String label;

    MenuTab(String label) {
        this.label = label;
    }
}
