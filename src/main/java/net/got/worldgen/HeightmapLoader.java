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
 * <h3>Coastal smoothing — zero-crossing-preserving Gaussian</h3>
 * A single Gaussian blur pass is applied to pixels near the waterline
 * (elevation range {@code OCEAN_VALUE * 2.5 → 0.10}) to widen the
 * gradient zone, which improves the Catmull-Rom bicubic interpolation
 * at river banks and coastlines.
 *
 * <strong>Critically</strong>, the smoothed value is clamped so that:
 * <ul>
 *   <li>Land pixels (elev &gt;= 0) never become water (result clamped to ≥ 0.001)</li>
 *   <li>Water pixels (elev &lt; 0) never become land (result clamped to ≤ -0.001)</li>
 * </ul>
 * This prevents the coast from visually widening — the gradient is smoother
 * without the waterline physically moving from its painted position.
 *
 * 1 pixel = MAP_SCALE blocks (28).
 */
public final class HeightmapLoader {

    /** 1 pixel = 28 world blocks. */
    public static final int MAP_SCALE = 28;

    private static final float H_SEA_LEVEL = 0.49f;
    private static final float H_PEAK      = 0.02f;
    private static final float OCEAN_MAX_V = 0.25f;
    private static final float OCEAN_MIN_H = 0.60f;

    /**
     * Value stored for ocean pixels.
     * Small negative so the bicubic smoothly interpolates to 0 at coastlines.
     */
    public static final float OCEAN_VALUE = -0.05f;

    private static int       imageWidth;
    private static int       imageHeight;
    private static float[][] elev;
    private static boolean   loaded = false;

    // -----------------------------------------------------------------------
    // 5×5 Gaussian kernel (σ ≈ 1.0 pixel)
    // Smaller than a 7x7 so the blur doesn't reach far from the waterline.
    // -----------------------------------------------------------------------
    private static final float[][] GAUSS_5 = buildGauss5();

    private static float[][] buildGauss5() {
        float sigma = 1.0f;
        float[][] k = new float[5][5];
        float sum = 0f;
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                float v = (float) Math.exp(-(dx * dx + dz * dz) / (2f * sigma * sigma));
                k[dx + 2][dz + 2] = v;
                sum += v;
            }
        }
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                k[i][j] /= sum;
        return k;
    }

    private HeightmapLoader() {}

    // -----------------------------------------------------------------------
    // Load
    // -----------------------------------------------------------------------

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

            // Single smoothing pass — widens the gradient for smoother bicubic
            // sampling without moving the actual water/land boundary.
            smoothCoastalZone();

            loaded = true;
            System.out.printf("[GoT] Heightmap loaded: %dx%d  1px=%dblocks  ocean=%d land=%d%n",
                    imageWidth, imageHeight, MAP_SCALE, oceanPx, landPx);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load GoT heightmap", e);
        }
    }

    /**
     * Applies a 5×5 Gaussian blur selectively to pixels in a narrow coastal
     * band: {@code OCEAN_VALUE * 2.5 ≤ elev ≤ 0.10}.
     *
     * <p>The blended value is clamped to the same sign as the original elevation
     * so the painted waterline never moves — only the gradient around it softens.
     */
    private static void smoothCoastalZone() {
        final float LO = OCEAN_VALUE * 2.5f;  // ≈ −0.125
        final float HI = 0.10f;

        float[][] result = new float[imageWidth][imageHeight];

        for (int px = 0; px < imageWidth; px++) {
            for (int pz = 0; pz < imageHeight; pz++) {
                float e = elev[px][pz];

                if (e < LO || e > HI) {
                    result[px][pz] = e;
                    continue;
                }

                // Blend weight peaks at the waterline, tapers to 0 at LO and HI
                float blend;
                if (e < 0f) {
                    float u = (e - LO) / (0f - LO);  // 0 at LO, 1 near waterline
                    blend = u * u;
                } else {
                    float u = 1f - (e / HI);           // 1 near waterline, 0 at HI
                    blend = u * u;
                }
                blend = Math.min(0.75f, blend);  // cap at 75 % — preserve original shape

                // 5×5 Gaussian accumulation
                float sumVal = 0f, sumW = 0f;
                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        int nx = Math.max(0, Math.min(imageWidth  - 1, px + dx));
                        int nz = Math.max(0, Math.min(imageHeight - 1, pz + dz));
                        float w = GAUSS_5[dx + 2][dz + 2];
                        sumVal += elev[nx][nz] * w;
                        sumW   += w;
                    }
                }
                float blurred  = sumVal / sumW;
                float smoothed = e + (blurred - e) * blend;

                // ── Zero-crossing preservation ────────────────────────────
                // The painted coastline position is sacred.
                // Land stays land; water stays water.
                if (e >= 0f && smoothed < 0f) {
                    smoothed = 0.001f;
                } else if (e < 0f && smoothed > 0f) {
                    smoothed = -0.001f;
                }

                result[px][pz] = smoothed;
            }
        }

        elev = result;
    }

    // -----------------------------------------------------------------------
    // Public API
    // -----------------------------------------------------------------------

    /**
     * Returns interpolated elevation at world coordinates using Catmull-Rom
     * bicubic sampling over the (pre-smoothed) elevation grid.
     */
    public static float getElevationAtWorld(int worldX, int worldZ) {
        if (!loaded) return 0f;
        float mapX = worldX / (float) MAP_SCALE + imageWidth  * 0.5f;
        float mapZ = worldZ / (float) MAP_SCALE + imageHeight * 0.5f;
        return bicubic(mapX, mapZ);
    }

    // -----------------------------------------------------------------------
    // Catmull-Rom bicubic — overshoot clamped
    // -----------------------------------------------------------------------

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

    // -----------------------------------------------------------------------
    // Colour helpers
    // -----------------------------------------------------------------------

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

    // -----------------------------------------------------------------------
    // Accessors
    // -----------------------------------------------------------------------

    public static boolean isLoaded()   { return loaded;      }
    public static int     getWidth()   { return imageWidth;  }
    public static int     getHeight()  { return imageHeight; }
}