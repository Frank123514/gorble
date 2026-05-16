package net.got.faction;

/**
 * A named point of interest on the world map, expressed in world-block coordinates
 * (matching the coordinate system used by {@code GotMapWidget}).
 *
 * <p>Block coordinates originate at the map centre:
 * <ul>
 *   <li>{@code blockX} – west(-) / east(+)</li>
 *   <li>{@code blockZ} – north(-) / south(+)</li>
 * </ul>
 *
 * @param name   Display name shown on the minimap pin and location nav.
 * @param blockX World X coordinate (Minecraft block).
 * @param blockZ World Z coordinate (Minecraft block).
 * @param zoom   Desired minimap zoom multiplier when this waypoint is active (e.g. 7.0 = 7×).
 */
public record WaypointData(String name, int blockX, int blockZ, double zoom) {

    /** Convenience constructor that uses a sensible default zoom level. */
    public WaypointData(String name, int blockX, int blockZ) {
        this(name, blockX, blockZ, 7.0);
    }
}