package net.got.client.gui.widget;

import net.got.faction.RoadData;
import net.got.faction.RoadRegistry;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;
import java.util.Map;

public final class RoadMapRenderer {

    private static final int COLOR_KINGSROAD = 0xDCA0823C;
    private static final int COLOR_ROAD      = 0xB48C7A6A;
    private static final int COLOR_PATH      = 0x966B5B4E;
    private static final int COLOR_SEA_LANE  = 0xA04A7EBF;

    private static final double DASH_ON  = 6.0;
    
    private static final double DASH_OFF = 4.0;

    private static final int CURVE_STEPS = 20;

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

    private static void drawRoad(GuiGraphics graphics,
                                 RoadData road,
                                 int wx, int wy,
                                 double panX, double panY, double zoom,
                                 int color, boolean dashed) {
        List<RoadData.Point> pts = road.points();
        int n = pts.size();
        if (n < 2) return;

        double[] dashAccum = { 0.0 };

        for (int i = 0; i < n - 1; i++) {
            
            RoadData.Point p0 = pts.get(Math.max(0, i - 1));
            RoadData.Point p1 = pts.get(i);
            RoadData.Point p2 = pts.get(i + 1);
            RoadData.Point p3 = pts.get(Math.min(n - 1, i + 2));

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

    private static int toScreenX(int widgetX, double panX, double zoom, double texX) {
        return (int) (widgetX - panX + texX * zoom);
    }

    private static int toScreenY(int widgetY, double panY, double zoom, double texY) {
        return (int) (widgetY - panY + texY * zoom);
    }

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

    private static int colorForType(String type) {
        return switch (type) {
            case "kingsroad" -> COLOR_KINGSROAD;
            case "road"      -> COLOR_ROAD;
            case "sea_lane"  -> COLOR_SEA_LANE;
            default          -> COLOR_PATH;
        };
    }

    private static boolean isDashed(String type) {
        return "path".equals(type) || "sea_lane".equals(type);
    }

    private RoadMapRenderer() {}
}
