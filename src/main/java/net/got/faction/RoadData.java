package net.got.faction;

import java.util.List;

public record RoadData(String id, String name, String type, Palette palette, List<Point> points) {

    public record Point(int pixelX, int pixelY) {}

    public record Palette(List<String> surface, boolean walls) {

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
                default -> new Palette(
                        List.of("minecraft:dirt_path", "minecraft:dirt_path", "got:path_block"),
                        false);
            };
        }
    }
}
