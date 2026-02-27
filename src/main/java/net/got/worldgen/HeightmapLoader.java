package net.got.worldgen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Loads the topographic colour heightmap PNG and decodes elevation from HSV hue.
 *
 * <p>All pixel coordinate lookups go through {@link MapWarp#warp} so terrain
 * elevation uses exactly the same organic distortion as the biome map in
 * {@link BiomemapLoader}.  Keeping both maps on the same warp is essential —
 * if they ever diverge, biome borders will misalign with terrain features.</p>
 *
 * Colour → elevation:
 * <ul>
 *   <li>Ocean  (V &lt; 0.25 AND H &gt; 0.60) → {@link #OCEAN_VALUE} (−0.05)</li>
 *   <li>Cyan   (H ≈ 0.49)                 → 0.0  (sea-level coast)</li>
 *   <li>Red    (H ≈ 0.02)                 → 1.0  (mountain peak)</li>
 * </ul>
 */
public final class HeightmapLoader {

    /** 1 pixel = 28 world blocks. Must match BiomemapLoader.MAP_SCALE. */
    public static final int MAP_SCALE = 56;

    private static final float H_SEA_LEVEL = 0.49f;
    private static final float H_PEAK      = 0.02f;
    private static final float OCEAN_MAX_V = 0.25f;
    private static final float OCEAN_MIN_H = 0.60f;

    /**
     * Value stored for ocean pixels.
     * Small negative so bicubic interpolation smoothly crosses 0 at coastlines.
     */
    public static final float OCEAN_VALUE = -0.05f;

    /* ------------------------------------------------------------------ */
    /* State                                                                */
    /* ------------------------------------------------------------------ */

    private static int       imageWidth;
    private static int       imageHeight;
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
     * Returns bicubic-interpolated elevation at world coordinates after warping.
     * ≤ OCEAN_VALUE → deep ocean | &lt; 0 → coastal water | 0–1 → land
     */
    public static float getElevationAtWorld(int worldX, int worldZ) {
        if (!loaded) return 0f;
        float[] wc = MapWarp.warp(worldX, worldZ, imageWidth, imageHeight);
        return bicubic(wc[0], wc[1]);
    }

    /**
     * Euclidean distance (in warped heightmap pixels) to the nearest land pixel.
     *
     * <p>Uses the same warp as {@link #getElevationAtWorld} so the distance field
     * follows the same organic bank curves as the elevation field — the river-floor
     * blending contours match the terrain precisely.</p>
     *
     * @param worldX    world block X
     * @param worldZ    world block Z
     * @param maxRadius maximum search radius in heightmap pixels
     * @return warped pixel distance to nearest land, or {@code maxRadius+1f} if none found
     */
    public static float nearestLandDistanceF(int worldX, int worldZ, int maxRadius) {
        if (!loaded) return maxRadius + 1f;

        float[] wc = MapWarp.warp(worldX, worldZ, imageWidth, imageHeight);
        float   fx = wc[0];
        float   fz = wc[1];

        float ox = fx - (float) Math.floor(fx);
        float oz = fz - (float) Math.floor(fz);
        int   px = (int) Math.floor(fx);
        int   pz = (int) Math.floor(fz);

        float   bestDist2 = (maxRadius + 1f) * (maxRadius + 1f);
        boolean found     = false;

        for (int r = 0; r <= maxRadius; r++) {
            float ringStart = Math.max(0, r - 1);
            if (found && ringStart * ringStart > bestDist2) break;

            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (r > 0 && Math.abs(dx) < r && Math.abs(dz) < r) continue;
                    int nx = px + dx, nz = pz + dz;
                    if (nx < 0 || nz < 0 || nx >= imageWidth || nz >= imageHeight) continue;
                    if (elev[nx][nz] > 0f) {
                        float ddx = dx - ox + 0.5f;
                        float ddz = dz - oz + 0.5f;
                        float d2  = ddx * ddx + ddz * ddz;
                        if (d2 < bestDist2) { bestDist2 = d2; found = true; }
                    }
                }
            }
        }

        return found ? (float) Math.sqrt(bestDist2) : maxRadius + 1f;
    }

    /* ------------------------------------------------------------------ */
    /* Catmull-Rom bicubic — overshoot clamped                             */
    /* ------------------------------------------------------------------ */

    private static float bicubic(float mx, float mz) {
        int x1 = (int) Math.floor(mx);
        int z1 = (int) Math.floor(mz);
        float tx = mx - x1;
        float tz = mz - z1;

        float c00 = raw(x1,     z1);
        float c10 = raw(x1 + 1, z1);
        float c01 = raw(x1,     z1 + 1);
        float c11 = raw(x1 + 1, z1 + 1);
        float lo  = Math.min(Math.min(c00, c10), Math.min(c01, c11));
        float hi  = Math.max(Math.max(c00, c10), Math.max(c01, c11));

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

    private static float raw(int px, int pz) {
        if (px < 0 || pz < 0 || px >= imageWidth || pz >= imageHeight) return OCEAN_VALUE;
        return elev[px][pz];
    }

    private static float cubic(float a, float b, float c, float d, float t) {
        float t2 = t*t, t3 = t2*t;
        return (-0.5f*a + 1.5f*b - 1.5f*c + 0.5f*d)*t3
                + (       a - 2.5f*b + 2.0f*c - 0.5f*d)*t2
                + (-0.5f*a            + 0.5f*c          )*t
                + b;
    }

    /* ------------------------------------------------------------------ */
    /* Colour helpers                                                       */
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