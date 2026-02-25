package net.got.worldgen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Loads the topographic colour heightmap PNG and decodes elevation from HSV hue.
 *
 * Colour → elevation:
 *   Ocean  (V < 0.25 AND H > 0.60)  →  OCEAN_VALUE (-0.05)
 *   Cyan   (H ≈ 0.49)               →  0.0   (sea-level coast)
 *   Red    (H ≈ 0.02)               →  1.0   (mountain peak)
 *
 * Using OCEAN_VALUE = -0.05 (not -1) means the bicubic curve smoothly crosses
 * zero between ocean and land pixels, placing the coastline exactly on the
 * smooth S-curve through the pixel grid — no staircase edges.
 *
 * 1 pixel = MAP_SCALE blocks (100).
 */
public final class HeightmapLoader {

    /** 1 pixel = 100 world blocks. */
    public static final int MAP_SCALE = 100;

    private static final float H_SEA_LEVEL = 0.49f;
    private static final float H_PEAK      = 0.02f;
    private static final float OCEAN_MAX_V = 0.25f;
    private static final float OCEAN_MIN_H = 0.60f;

    /**
     * Value stored for ocean pixels.
     * Small negative so the bicubic smoothly interpolates to 0 at coastlines.
     * GotChunkGenerator uses this as the "deep ocean" threshold.
     */
    public static final float OCEAN_VALUE = -0.05f;

    private static int       imageWidth;
    private static int       imageHeight;
    /** OCEAN_VALUE = ocean, [0,1] = land. */
    private static float[][] elev;
    private static boolean   loaded = false;

    private HeightmapLoader() {}

    /* ------------------------------------------------------------------ */
    /* Load                                                                 */
    /* ------------------------------------------------------------------ */

    public static void load(InputStream stream) {
        try {
            BufferedImage img = ImageIO.read(stream);
            imageWidth  = img.getWidth();
            imageHeight = img.getHeight();
            elev = new float[imageWidth][imageHeight];

            int oceanPx = 0, landPx = 0;
            for (int px = 0; px < imageWidth; px++) {
                for (int pz = 0; pz < imageHeight; pz++) {
                    int rgb = img.getRGB(px, pz);
                    float r = ((rgb >> 16) & 0xFF) / 255f;
                    float g = ((rgb >>  8) & 0xFF) / 255f;
                    float b = ( rgb        & 0xFF) / 255f;

                    float[] hsv = rgbToHsv(r, g, b);
                    float h = hsv[0], v = hsv[2];

                    if (v < OCEAN_MAX_V && h > OCEAN_MIN_H) {
                        elev[px][pz] = OCEAN_VALUE;
                        oceanPx++;
                    } else {
                        float t = (H_SEA_LEVEL - h) / (H_SEA_LEVEL - H_PEAK);
                        elev[px][pz] = Math.max(0f, Math.min(1f, t));
                        landPx++;
                    }
                }
            }

            loaded = true;
            System.out.printf("[GoT] Heightmap loaded: %dx%d  1px=%dblocks  ocean=%d land=%d%n",
                    imageWidth, imageHeight, MAP_SCALE, oceanPx, landPx);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load GoT heightmap", e);
        }
    }

    /* ------------------------------------------------------------------ */
    /* Public API                                                           */
    /* ------------------------------------------------------------------ */

    /**
     * Returns interpolated elevation at world coordinates.
     *  ≤ OCEAN_VALUE  → deep ocean
     *  < 0            → shallow/coastal water
     *  0–1            → land (0 = coast, 1 = peak)
     */
    public static float getElevationAtWorld(int worldX, int worldZ) {
        if (!loaded) return 0f;
        float mapX = worldX / (float) MAP_SCALE + imageWidth  * 0.5f;
        float mapZ = worldZ / (float) MAP_SCALE + imageHeight * 0.5f;
        return bicubic(mapX, mapZ);
    }

    /* ------------------------------------------------------------------ */
    /* Catmull-Rom bicubic — overshoot clamped                             */
    /* ------------------------------------------------------------------ */

    private static float bicubic(float mx, float mz) {
        int x1 = (int) Math.floor(mx);
        int z1 = (int) Math.floor(mz);
        float tx = mx - x1;
        float tz = mz - z1;

        // Min/max of 4 central pixels — used to hard-clamp Runge overshoot
        float c00 = raw(x1,     z1);
        float c10 = raw(x1 + 1, z1);
        float c01 = raw(x1,     z1 + 1);
        float c11 = raw(x1 + 1, z1 + 1);
        float lo  = Math.min(Math.min(c00, c10), Math.min(c01, c11));
        float hi  = Math.max(Math.max(c00, c10), Math.max(c01, c11));

        // Full 4×4 Catmull-Rom (OCEAN_VALUE pixels participate directly —
        // this is what makes coastlines smooth)
        float r0 = cubicRow(x1, z1 - 1, tx);
        float r1 = cubicRow(x1, z1,     tx);
        float r2 = cubicRow(x1, z1 + 1, tx);
        float r3 = cubicRow(x1, z1 + 2, tx);
        float result = cubic(r0, r1, r2, r3, tz);

        return Math.max(lo, Math.min(hi, result));
    }

    private static float cubicRow(int x1, int pz, float tx) {
        return cubic(raw(x1-1,pz), raw(x1,pz), raw(x1+1,pz), raw(x1+2,pz), tx);
    }

    /** Returns stored elevation; out-of-bounds → OCEAN_VALUE. */
    private static float raw(int px, int pz) {
        if (px < 0 || pz < 0 || px >= imageWidth || pz >= imageHeight) return OCEAN_VALUE;
        return elev[px][pz];
    }

    /** Catmull-Rom cubic kernel. */
    private static float cubic(float a, float b, float c, float d, float t) {
        float t2 = t*t, t3 = t2*t;
        return (-0.5f*a + 1.5f*b - 1.5f*c + 0.5f*d)*t3
                + (       a - 2.5f*b + 2.0f*c - 0.5f*d)*t2
                + (-0.5f*a            + 0.5f*c          )*t
                + b;
    }

    /* ------------------------------------------------------------------ */
    /* Colour helpers                                                        */
    /* ------------------------------------------------------------------ */

    private static float[] rgbToHsv(float r, float g, float b) {
        float cmax = Math.max(r, Math.max(g, b));
        float cmin = Math.min(r, Math.min(g, b));
        float delta = cmax - cmin;
        float v = cmax, s = cmax > 0 ? delta/cmax : 0, h = 0;
        if (delta > 0) {
            if      (cmax == r) h = ((g-b)/delta) % 6f;
            else if (cmax == g) h = (b-r)/delta + 2f;
            else                h = (r-g)/delta + 4f;
            h /= 6f;
            if (h < 0) h += 1f;
        }
        return new float[]{ h, s, v };
    }

    /* ------------------------------------------------------------------ */
    /* Accessors                                                            */
    /* ------------------------------------------------------------------ */

    public static boolean isLoaded()   { return loaded;      }
    public static int     getWidth()   { return imageWidth;  }
    public static int     getHeight()  { return imageHeight; }
}