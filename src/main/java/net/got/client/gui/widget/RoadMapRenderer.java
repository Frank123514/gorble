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
 * <h2>Rendering</h2>
 * Roads are drawn as <b>Catmull-Rom splines</b>: each pair of consecutive
 * control points is subdivided into {@value #CURVE_STEPS} segments, giving
 * smooth, natural-looking curves instead of the jagged polyline that would
 * result from straight-line interpolation.  The curve passes through every
 * original control point, so roads still align with cities and landmarks.
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

    // ── Catmull-Rom curve settings ────────────────────────────────────────

    /**
     * Number of line segments used to approximate each Catmull-Rom spline
     * section (between two adjacent control points).  Higher values produce
     * smoother curves but cost slightly more per frame.  20 is imperceptible
     * at normal map zoom levels.
     */
    private static final int CURVE_STEPS = 20;

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
            String  type   = entry.getKey();
            int     color  = colorForType(type);
            boolean dashed = isDashed(type);

            for (RoadData road : entry.getValue()) {
                drawRoad(graphics, road, widgetX, widgetY, panX, panY, zoom, color, dashed);
            }
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────

    /**
     * Draws a single road as a Catmull-Rom spline in screen space.
     *
     * <p>For each consecutive pair of control points the method evaluates
     * {@link #CURVE_STEPS} intermediate positions using the Catmull-Rom
     * formula and connects them with Bresenham line segments.  This gives
     * smooth curves at any zoom level without changing the road data format.
     */
    private static void drawRoad(GuiGraphics graphics,
                                 RoadData road,
                                 int wx, int wy,
                                 double panX, double panY, double zoom,
                                 int color, boolean dashed) {
        List<RoadData.Point> pts = road.points();
        int n = pts.size();
        if (n < 2) return;

        // We track a running "dash distance" so dashes are continuous across
        // the spline segments rather than restarting at every control point.
        double[] dashAccum = { 0.0 };

        for (int i = 0; i < n - 1; i++) {
            // Four Catmull-Rom control points; clamp at the ends.
            RoadData.Point p0 = pts.get(Math.max(0, i - 1));
            RoadData.Point p1 = pts.get(i);
            RoadData.Point p2 = pts.get(i + 1);
            RoadData.Point p3 = pts.get(Math.min(n - 1, i + 2));

            // Evaluate the curve and connect successive samples.
            int prevSX = toScreenX(wx, panX, zoom, p1.pixelX());
            int prevSY = toScreenY(wy, panY, zoom, p1.pixelY());

            for (int step = 1; step <= CURVE_STEPS; step++) {
                double t = step / (double) CURVE_STEPS;
                double[] pt = catmullRom(p0, p1, p2, p3, t);

                int sx = toScreenX(wx, panX, zoom, pt[0]);
                int sy = toScreenY(wy, panY, zoom, pt[1]);

                if (dashed) {
                    drawDashedLine(graphics, prevSX, prevSY, sx, sy, color, dashAccum);
                } else {
                    drawSolidLine(graphics, prevSX, prevSY, sx, sy, color);
                }

                prevSX = sx;
                prevSY = sy;
            }
        }
    }

    // ── Catmull-Rom evaluation ────────────────────────────────────────────

    /**
     * Evaluates the Catmull-Rom spline at parameter {@code t} ∈ [0, 1] for
     * the segment from {@code p1} to {@code p2}.
     *
     * <p>Standard uniform Catmull-Rom formula (α = 0.5):
     * <pre>
     *   q(t) = 0.5 * [ (2·P1)
     *                + (−P0 + P2)·t
     *                + (2·P0 − 5·P1 + 4·P2 − P3)·t²
     *                + (−P0 + 3·P1 − 3·P2 + P3)·t³ ]
     * </pre>
     *
     * @return {@code double[2]} — {x, y} in texture-pixel coordinates.
     */
    private static double[] catmullRom(
            RoadData.Point p0, RoadData.Point p1,
            RoadData.Point p2, RoadData.Point p3,
            double t) {

        double t2 = t * t;
        double t3 = t2 * t;

        double x = 0.5 * (
                (2.0 * p1.pixelX())
                + (-p0.pixelX() + p2.pixelX()) * t
                + (2.0 * p0.pixelX() - 5.0 * p1.pixelX() + 4.0 * p2.pixelX() - p3.pixelX()) * t2
                + (-p0.pixelX() + 3.0 * p1.pixelX() - 3.0 * p2.pixelX() + p3.pixelX()) * t3
        );
        double y = 0.5 * (
                (2.0 * p1.pixelY())
                + (-p0.pixelY() + p2.pixelY()) * t
                + (2.0 * p0.pixelY() - 5.0 * p1.pixelY() + 4.0 * p2.pixelY() - p3.pixelY()) * t2
                + (-p0.pixelY() + 3.0 * p1.pixelY() - 3.0 * p2.pixelY() + p3.pixelY()) * t3
        );
        return new double[]{ x, y };
    }

    // ── Coordinate helpers ────────────────────────────────────────────────

    private static int toScreenX(int widgetX, double panX, double zoom, double texX) {
        return (int) (widgetX - panX + texX * zoom);
    }

    private static int toScreenY(int widgetY, double panY, double zoom, double texY) {
        return (int) (widgetY - panY + texY * zoom);
    }

    // ── Line drawing ──────────────────────────────────────────────────────

    /**
     * Draws a solid line from (x0, y0) to (x1, y1) using Bresenham's
     * algorithm.  Minecraft's {@code GuiGraphics.fill} only draws
     * axis-aligned rects, so we step one pixel at a time.
     */
    private static void drawSolidLine(GuiGraphics g,
                                      int x0, int y0, int x1, int y1,
                                      int color) {
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
     * Dashed variant.  {@code dashAccum} is a single-element array used as an
     * in/out accumulator so dash phase is preserved across consecutive curve
     * sub-segments — that way dashes don't restart at every control point.
     */
    private static void drawDashedLine(GuiGraphics g,
                                       int x0, int y0, int x1, int y1,
                                       int color, double[] dashAccum) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        double cycle = DASH_ON + DASH_OFF;

        int x = x0, y = y0;
        while (true) {
            if (dashAccum[0] % cycle < DASH_ON) {
                g.fill(x, y, x + 1, y + 1, color);
            }
            if (x == x1 && y == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) { err -= dy; x += sx; dashAccum[0]++; }
            if (e2 <  dx) { err += dx; y += sy; dashAccum[0]++; }
        }
    }

    // ── Type → style mapping ──────────────────────────────────────────────

    private static int colorForType(String type) {
        return switch (type) {
            case "kingsroad" -> COLOR_KINGSROAD;
            case "road"      -> COLOR_ROAD;
            case "sea_lane"  -> COLOR_SEA_LANE;
            default          -> COLOR_PATH; // "path" and anything unknown
        };
    }

    private static boolean isDashed(String type) {
        return "path".equals(type) || "sea_lane".equals(type);
    }

    private RoadMapRenderer() {}
}
