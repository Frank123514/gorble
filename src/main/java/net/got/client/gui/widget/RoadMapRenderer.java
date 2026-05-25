package net.got.client.gui.widget;

import net.got.faction.RoadData;
import net.got.faction.RoadRegistry;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;
import java.util.Map;

/**
 * Draws the data-driven road network (loaded by {@link RoadRegistry}) onto the
 * Known-World map widget.
 *
 * <h2>Usage</h2>
 * Call {@link #render} inside {@code GotMapWidget.renderWidget}, after the map
 * texture is drawn and before pins / waypoint labels.
 *
 * <pre>{@code
 * // Inside GotMapWidget.renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick):
 * RoadMapRenderer.render(graphics, getX(), getY(), panX, panY, zoom);
 * }</pre>
 *
 * <h2>Colours (ARGB hex)</h2>
 * <ul>
 *   <li>{@code kingsroad} — dark gold  0xDCA0823C</li>
 *   <li>{@code road}      — warm grey  0xB48C7A6A</li>
 *   <li>{@code path}      — muted tan  0x966B5B4E</li>
 *   <li>{@code sea_lane}  — pale blue  0xA04A7EBF</li>
 * </ul>
 */
public final class RoadMapRenderer {

    // ── Colours (0xAARRGGBB) ─────────────────────────────────────────────

    private static final int COLOR_KINGSROAD = 0xDCA0823C; // alpha=DC, dark gold
    private static final int COLOR_ROAD      = 0xB48C7A6A; // alpha=B4, warm grey
    private static final int COLOR_PATH      = 0x966B5B4E; // alpha=96, muted tan
    private static final int COLOR_SEA_LANE  = 0xA04A7EBF; // alpha=A0, pale blue

    // ── Dash settings ─────────────────────────────────────────────────────

    /** Pixel length of each dash segment (in zoomed screen pixels). */
    private static final double DASH_ON  = 6.0;
    /** Pixel gap between dashes. */
    private static final double DASH_OFF = 4.0;

    // ── Public API ────────────────────────────────────────────────────────

    /**
     * Renders all roads for the current map view.
     *
     * @param graphics  The current {@link GuiGraphics} context.
     * @param widgetX   Left edge of the map widget in screen coordinates.
     * @param widgetY   Top  edge of the map widget in screen coordinates.
     * @param panX      Current horizontal scroll offset (pixels into the zoomed texture).
     * @param panY      Current vertical   scroll offset (pixels into the zoomed texture).
     * @param zoom      Current zoom multiplier (1.0 = 1 screen pixel per texture pixel).
     */
    public static void render(GuiGraphics graphics,
                              int widgetX, int widgetY,
                              double panX, double panY,
                              double zoom) {
        for (Map.Entry<String, List<RoadData>> entry : RoadRegistry.BY_TYPE.entrySet()) {
            String type = entry.getKey();
            int    color   = colorForType(type);
            boolean dashed = isDashed(type);

            for (RoadData road : entry.getValue()) {
                drawRoad(graphics, road, widgetX, widgetY, panX, panY, zoom, color, dashed);
            }
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────

    private static void drawRoad(GuiGraphics graphics,
                                 RoadData road,
                                 int wx, int wy,
                                 double panX, double panY, double zoom,
                                 int color, boolean dashed) {
        List<RoadData.Point> points = road.points();
        if (points.size() < 2) return;

        for (int i = 0; i < points.size() - 1; i++) {
            RoadData.Point a = points.get(i);
            RoadData.Point b = points.get(i + 1);

            // Convert from texture-pixel space to screen space
            int ax = (int) (wx - panX + a.pixelX() * zoom);
            int ay = (int) (wy - panY + a.pixelY() * zoom);
            int bx = (int) (wx - panX + b.pixelX() * zoom);
            int by = (int) (wy - panY + b.pixelY() * zoom);

            if (dashed) {
                drawDashedLine(graphics, ax, ay, bx, by, color);
            } else {
                graphics.fill(Math.min(ax, bx), Math.min(ay, by),
                        Math.max(ax, bx) + 1, Math.max(ay, by) + 1, color);
                // Approximate a 1-px antialiased line with a series of fills
                drawSolidLine(graphics, ax, ay, bx, by, color);
            }
        }
    }

    /**
     * Draws a solid polyline segment using Bresenham-style stepping.
     * Minecraft's {@code GuiGraphics.fill} only draws axis-aligned rects, so
     * we step along the longer axis one pixel at a time.
     */
    private static void drawSolidLine(GuiGraphics g, int x0, int y0, int x1, int y1, int color) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        int x = x0, y = y0;
        while (true) {
            g.fill(x, y, x + 1, y + 1, color);
            if (x == x1 && y == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) { err -= dy; x += sx; }
            if (e2 <  dx) { err += dx; y += sy; }
        }
    }

    /**
     * Dashed variant — skips pixels according to {@link #DASH_ON}/{@link #DASH_OFF}.
     */
    private static void drawDashedLine(GuiGraphics g, int x0, int y0, int x1, int y1, int color) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        double cycle = DASH_ON + DASH_OFF;

        int x = x0, y = y0;
        double dist = 0;
        while (true) {
            if (dist % cycle < DASH_ON) {
                g.fill(x, y, x + 1, y + 1, color);
            }
            if (x == x1 && y == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) { err -= dy; x += sx; dist++; }
            if (e2 <  dx) { err += dx; y += sy; dist++; }
        }
    }

    private static int colorForType(String type) {
        return switch (type) {
            case "kingsroad" -> COLOR_KINGSROAD;
            case "road"      -> COLOR_ROAD;
            case "sea_lane"  -> COLOR_SEA_LANE;
            default          -> COLOR_PATH;   // "path" and anything unknown
        };
    }

    private static boolean isDashed(String type) {
        return "path".equals(type) || "sea_lane".equals(type);
    }

    private RoadMapRenderer() {}
}