package net.got.faction;

import java.util.List;

/**
 * A named road or trade route on the Known-World map, expressed as an ordered
 * list of pixel coordinates on the {@code known_world.png} texture (4207×3277).
 *
 * <p>Pixel coordinates map directly to the texture:
 * <ul>
 *   <li>{@code pixelX} – horizontal position on the map image (0 = left edge)</li>
 *   <li>{@code pixelY} – vertical position on the map image (0 = top edge)</li>
 * </ul>
 *
 * <p>Roads are rendered as polylines on the map by connecting each {@link Point}
 * in order.  Add more points for gentle curves; fewer for straight roads.
 *
 * @param id    Unique string identifier used to reference this road in code.
 * @param name  Display name shown as a tooltip when hovering the road on the map.
 * @param type  Road category — controls colour/width in the renderer.
 *              Well-known values: {@code "kingsroad"}, {@code "road"},
 *              {@code "path"}, {@code "sea_lane"}.
 * @param points Ordered list of pixel coordinates that define the road path.
 */
public record RoadData(String id, String name, String type, List<Point> points) {

    /**
     * A single vertex along a road, in map-texture pixel coordinates.
     *
     * @param pixelX Horizontal pixel coordinate (0 = left edge of known_world.png).
     * @param pixelY Vertical pixel coordinate   (0 = top  edge of known_world.png).
     */
    public record Point(int pixelX, int pixelY) {}
}
