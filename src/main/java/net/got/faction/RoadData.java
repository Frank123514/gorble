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
 * @param id      Unique string identifier used to reference this road in code.
 * @param name    Display name shown as a tooltip when hovering the road on the map.
 * @param type    Road category — controls colour/width in the renderer.
 *                Well-known values: {@code "kingsroad"}, {@code "road"},
 *                {@code "path"}, {@code "sea_lane"}.
 * @param palette Block palette used when placing this road in the world.
 *                Controls surface block variety and whether border walls are placed.
 * @param points  Ordered list of pixel coordinates that define the road path.
 */
public record RoadData(String id, String name, String type, Palette palette, List<Point> points) {

    /**
     * A single vertex along a road, in map-texture pixel coordinates.
     *
     * @param pixelX Horizontal pixel coordinate (0 = left edge of known_world.png).
     * @param pixelY Vertical pixel coordinate   (0 = top  edge of known_world.png).
     */
    public record Point(int pixelX, int pixelY) {}

    /**
     * Block palette used when placing this road in the world.
     *
     * <p>The {@code surface} list provides the pool of blocks to draw from when
     * placing road surface tiles — repeat entries to weight them. For example:
     * <pre>{@code
     *   "surface": ["minecraft:cobblestone", "minecraft:cobblestone", "minecraft:stone"]
     * }</pre>
     * gives a 2-in-3 chance of cobblestone and 1-in-3 chance of stone.
     *
     * <p>{@code walls} controls whether a decorative wall block is placed on both
     * edges of the road. Set {@code false} (the default) for a clean flat road.
     *
     * @param surface Weighted list of block IDs to use for the road surface.
     *                An empty list means no surface blocks are placed (e.g. sea lanes).
     * @param walls   If {@code true}, a wall block is placed on each side of the road.
     *                Defaults to {@code false}.
     */
    public record Palette(List<String> surface, boolean walls) {

        /**
         * Returns a sensible default palette for roads that omit the {@code "palette"}
         * field in their JSON.
         *
         * @param type Road type string (e.g. {@code "kingsroad"}, {@code "road"}).
         * @return A default {@link Palette} appropriate for the given type.
         */
        public static Palette defaultForType(String type) {
            return switch (type) {
                case "kingsroad" -> new Palette(
                        List.of("minecraft:cobblestone", "minecraft:cobblestone", "minecraft:stone"),
                        false);
                case "road" -> new Palette(
                        List.of("minecraft:gravel", "minecraft:gravel", "minecraft:cobblestone"),
                        false);
                case "sea_lane" -> new Palette(
                        List.of(),
                        false);
                default -> new Palette(  // "path" and anything unknown
                        List.of("minecraft:dirt_path", "minecraft:dirt_path", "got:path_block"),
                        false);
            };
        }
    }
}
