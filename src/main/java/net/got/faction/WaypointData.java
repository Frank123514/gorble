package net.got.faction;

public record WaypointData(String name, int pixelX, int pixelY, double zoom) {

    public WaypointData(String name, int pixelX, int pixelY) {
        this(name, pixelX, pixelY, 7.0);
    }
}