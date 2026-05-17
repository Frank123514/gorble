package net.got.faction;

/**
 * A named point of interest on the world map, expressed in pixel coordinates
 * on the {@code known_world.png} texture (4207×3277).
 *
 * <p>Pixel coordinates map directly to the texture:
 * <ul>
 *   <li>{@code pixelX} – horizontal position on the map image (0 = left edge)</li>
 *   <li>{@code pixelY} – vertical position on the map image (0 = top edge)</li>
 * </ul>
 *
 * @param name   Display name shown on the minimap pin and location nav.
 * @param pixelX Horizontal pixel coordinate on the map texture.
 * @param pixelY Vertical pixel coordinate on the map texture.
 * @param zoom   Desired minimap zoom multiplier when this waypoint is active (e.g. 7.0 = 7×).
 */
public record WaypointData(String name, int pixelX, int pixelY, double zoom) {

    /** Convenience constructor that uses a sensible default zoom level. */
    public WaypointData(String name, int pixelX, int pixelY) {
        this(name, pixelX, pixelY, 7.0);
    }
}