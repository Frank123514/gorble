package net.got.worldgen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Loads the biome-paint PNG and exposes pixel colours for world coordinates.
 *
 * Coordinate mapping:
 *   1 pixel = {@value MAP_SCALE} world blocks
 *   pixel (0,0) = world block (−width/2 * scale, −height/2 * scale)
 *
 * Pixel colours are matched against the palette in biome_colors.json
 * by {@link net.got.worldgen.GotBiomeSource} using nearest-RGB-distance,
 * so minor JPEG/PNG compression artefacts are handled gracefully.
 *
 * Pixel colours are matched against the palette in biome_colors.json
 * by {@link net.got.worldgen.GotBiomeSource} using nearest-RGB-distance.
 */
public final class BiomemapLoader {

    /** World blocks per pixel — must match the map art's scale. */
    public static final int MAP_SCALE = 128;

    private static int   imageWidth;
    private static int   imageHeight;
    /** [pixelX][pixelZ] = 0xRRGGBB (alpha stripped). */
    private static int[][] pixels;
    private static boolean loaded = false;

    private BiomemapLoader() {}

    /* ------------------------------------------------------------------ */
    /* Load                                                                 */
    /* ------------------------------------------------------------------ */

    /**
     * Called once by {@link MapReloadListener} during datapack (re)load.
     * Reads every pixel and stores its 24-bit RGB value.
     */
    public static void load(InputStream stream) {
        try {
            BufferedImage img = ImageIO.read(stream);
            if (img == null) throw new IllegalStateException("ImageIO returned null – check PNG validity");

            imageWidth  = img.getWidth();
            imageHeight = img.getHeight();
            pixels = new int[imageWidth][imageHeight];

            for (int x = 0; x < imageWidth; x++) {
                for (int z = 0; z < imageHeight; z++) {
                    pixels[x][z] = img.getRGB(x, z) & 0xFF_FF_FF; // strip alpha
                }
            }

            loaded = true;
            System.out.printf("[GoT] Biomemap loaded: %d×%d  (1 px = %d blocks)%n",
                    imageWidth, imageHeight, MAP_SCALE);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load GoT biomemap", e);
        }
    }

    /* ------------------------------------------------------------------ */
    /* Query                                                                */
    /* ------------------------------------------------------------------ */

    /**
     * Returns the 0xRRGGBB pixel colour at the given world XZ position.
     *
     * Maps world coordinates directly to pixel space with no domain warp, so
     * biome placement is pixel-accurate and perfectly aligned with the terrain
     * generator (which also uses un-warped coordinates).
     *
     * Out-of-bounds coordinates return {@code 0x110751} (deep ocean) so the
     * world borders gracefully.
     *
     * @param worldX world block X
     * @param worldZ world block Z
     * @return 24-bit RGB colour
     */
    public static int getColorAtWorld(int worldX, int worldZ) {
        if (!loaded) return 0x110751; // deep ocean fallback while loading

        // Use Math.floor (not Math.round) to match GotChunkGenerator.computeSurfaceY,
        // which also floors its pixel coordinate.  Math.round was shifting biome
        // boundaries ~48 blocks (half a pixel at MAP_SCALE=96) relative to the
        // terrain, causing the river biome outline to appear misaligned from the
        // actual carved river bed.
        int px = (int) Math.floor(worldX / (float) MAP_SCALE + imageWidth  * 0.5f);
        int pz = (int) Math.floor(worldZ / (float) MAP_SCALE + imageHeight * 0.5f);

        // Clamp to image bounds — out-of-range → ocean-coloured border
        if (px < 0 || pz < 0 || px >= imageWidth || pz >= imageHeight) {
            return 0x110751;
        }

        return pixels[px][pz];
    }

    /* ------------------------------------------------------------------ */
    /* Raw pixel lookup (no warp)                                          */
    /* ------------------------------------------------------------------ */

    /**
     * Returns the 0xRRGGBB colour at the given IMAGE-SPACE pixel coordinate,
     * with NO domain warp applied.  Used by {@link GotBiomeSource} when scanning
     * neighbouring pixels in image space to find surrounding land/water context —
     * scanning in world space via {@link #getColorAtWorld} is wrong because that
     * method also warps, causing every scan step to curve back onto the same
     * river pixel that triggered the scan.
     *
     * @param px pixel column (clamped to image bounds)
     * @param pz pixel row    (clamped to image bounds)
     * @return 24-bit RGB colour, or {@code 0x110751} if image not yet loaded
     */
    public static int getRawPixel(int px, int pz) {
        if (!loaded) return 0x110751;
        px = Math.max(0, Math.min(imageWidth  - 1, px));
        pz = Math.max(0, Math.min(imageHeight - 1, pz));
        return pixels[px][pz];
    }

    /**
     * Returns the pixel coordinate for the given world position (no warp).
     *
     * @return int[2] { pixelX, pixelZ } (clamped to image bounds)
     */
    public static int[] getPixelForWorld(int worldX, int worldZ) {
        if (!loaded) return new int[]{0, 0};
        int px = Math.max(0, Math.min(imageWidth  - 1,
                (int) Math.floor(worldX / (float) MAP_SCALE + imageWidth  * 0.5f)));
        int pz = Math.max(0, Math.min(imageHeight - 1,
                (int) Math.floor(worldZ / (float) MAP_SCALE + imageHeight * 0.5f)));
        return new int[]{ px, pz };
    }

    /* ------------------------------------------------------------------ */
    /* Voronoi boundary lookup with domain warp                            */
    /* ------------------------------------------------------------------ */

    /**
     * Maximum jitter offset applied to each pixel's Voronoi feature point,
     * in pixels.  0.45 keeps every feature point inside its own cell while
     * maximising edge irregularity.
     */
    public static final float VORONOI_JITTER = 0.45f;

    // ── Domain-warp parameters (pixel space) ─────────────────────────────
    //
    // Three independent octaves bend the pixel-space coordinate before the
    // Voronoi lookup, replicating the multi-scale organic river shapes that
    // LOTR achieves through its GenLayer zoom passes.
    //
    // The core insight: to actually *curve* a ~1–2-pixel-wide river rather
    // than just translate it, the warp displacement across the river's width
    // must vary significantly.  That requires high-amplitude, short-to-medium
    // period noise.  Three octaves give:
    //
    //   Coarse  — long lazy meanders   (≈ 1 500 blocks / cycle)
    //   Mid     — secondary bends      (≈   480 blocks / cycle)
    //   Fine    — edge roughness        (≈   160 blocks / cycle)
    //
    // Each axis (X and Z) gets its own independent noise field (six total),
    // so warping is isotropic — rivers running N/S curve differently from
    // rivers running E/W, just like in LOTR.
    //
    // Max total displacement ≈ 2.5 + 0.9 + 0.35 = 3.75 px.
    // VORONOI_SEARCH must be ≥ ceil(3.75 + 0.45 + 1) = 6.

    private static final float WARP_COARSE_AMP  = 2.50f;   // px — big sweeping meanders
    private static final float WARP_COARSE_FREQ = 0.065f;  // /px ≈ 1 cycle / 1 488 blocks
    private static final float WARP_MID_AMP     = 0.90f;   // px — secondary curves
    private static final float WARP_MID_FREQ    = 0.205f;  // /px ≈ 1 cycle /   469 blocks
    private static final float WARP_FINE_AMP    = 0.35f;   // px — edge roughness
    private static final float WARP_FINE_FREQ   = 0.610f;  // /px ≈ 1 cycle /   157 blocks

    /**
     * Pixel search radius for the nearest-point Voronoi query.
     * Must be ≥ ceil(totalWarpAmp + VORONOI_JITTER + 1)
     *        = ceil(2.50 + 0.90 + 0.35 + 0.45 + 1) = 6.
     */
    private static final int VORONOI_SEARCH = 6;

    /**
     * Returns the biomemap pixel colour that owns the given float pixel-space
     * coordinate, using three-octave domain-warped Voronoi nearest-point
     * interpolation.
     *
     * <p><b>Pipeline:</b>
     * <ol>
     *   <li><em>Domain warp (3 octaves)</em> — six independent smooth value-noise
     *       fields (one per warp axis per octave) displace {@code (cx,cz)} by up
     *       to ±3.75 pixels.  The coarse octave creates broad river meanders;
     *       the mid octave adds secondary bends; the fine octave roughens edges.
     *       All three together replicate the multi-scale organic character that
     *       LOTR produces via iterative GenLayer zoom passes.</li>
     *   <li><em>Voronoi F1</em> — nearest seeded feature point in the warped
     *       coordinate's neighbourhood determines the owning pixel.</li>
     * </ol>
     *
     * <p>Both {@link GotChunkGenerator} and {@link GotBiomeSource} call this
     * method so terrain height and biome placement share identical boundaries.
     *
     * @param cx float pixel-space X  ({@code worldX / MAP_SCALE + width  * 0.5f})
     * @param cz float pixel-space Z  ({@code worldZ / MAP_SCALE + height * 0.5f})
     * @return 24-bit RGB biomemap colour
     */
    public static int voronoiColor(float cx, float cz) {
        // ── Three-octave domain warp ──────────────────────────────────────
        // Six noise fields, all seeded independently to eliminate cross-axis
        // and cross-octave correlation.  Offset constants break lattice alignment.
        float warpX =
                WARP_COARSE_AMP * warpNoise(cx * WARP_COARSE_FREQ,          cz * WARP_COARSE_FREQ,          0x3D7A1C)
              + WARP_MID_AMP    * warpNoise(cx * WARP_MID_FREQ   +  23.4f,  cz * WARP_MID_FREQ   +  67.8f,  0xA21F4B)
              + WARP_FINE_AMP   * warpNoise(cx * WARP_FINE_FREQ  + 112.7f,  cz * WARP_FINE_FREQ  +  38.5f,  0xB94F62);

        float warpZ =
                WARP_COARSE_AMP * warpNoise(cx * WARP_COARSE_FREQ +   5.7f, cz * WARP_COARSE_FREQ +  89.2f, 0x6C2E9A)
              + WARP_MID_AMP    * warpNoise(cx * WARP_MID_FREQ    +  51.3f, cz * WARP_MID_FREQ    + 134.6f, 0xE7531D)
              + WARP_FINE_AMP   * warpNoise(cx * WARP_FINE_FREQ   +  77.1f, cz * WARP_FINE_FREQ   +  19.3f, 0xF1835D);

        float wcx = cx + warpX;
        float wcz = cz + warpZ;

        // ── Voronoi F1 (nearest feature point) ───────────────────────────
        int icx = (int) Math.floor(wcx);
        int icz = (int) Math.floor(wcz);

        int   bestPx    = icx;
        int   bestPz    = icz;
        float bestDist2 = Float.MAX_VALUE;

        for (int dx = -VORONOI_SEARCH; dx <= VORONOI_SEARCH; dx++) {
            for (int dz = -VORONOI_SEARCH; dz <= VORONOI_SEARCH; dz++) {
                int px = icx + dx;
                int pz = icz + dz;

                // Feature point: cell centre + random jitter in [-jitter, +jitter]
                float fx = px + 0.5f + VORONOI_JITTER * pixelHash(px, pz, 0x1A3F7C);
                float fz = pz + 0.5f + VORONOI_JITTER * pixelHash(px, pz, 0x9B2E4D);

                float ddx = fx - wcx;
                float ddz = fz - wcz;
                float d2  = ddx * ddx + ddz * ddz;

                if (d2 < bestDist2) {
                    bestDist2 = d2;
                    bestPx    = px;
                    bestPz    = pz;
                }
            }
        }

        return getRawPixel(bestPx, bestPz);
    }

    /**
     * Smooth 2D value noise in {@code [-1, 1]}, bilinear with smoothstep.
     * Used exclusively for the two-octave domain-warp displacement fields.
     */
    private static float warpNoise(float x, float z, int seed) {
        int   ix = (int) Math.floor(x);
        int   iz = (int) Math.floor(z);
        float fx = x - ix;
        float fz = z - iz;
        // Smoothstep — C¹ continuity, no derivative discontinuity at lattice points
        float ux = fx * fx * (3f - 2f * fx);
        float uz = fz * fz * (3f - 2f * fz);
        float v00 = pixelHash(ix,     iz,     seed);
        float v10 = pixelHash(ix + 1, iz,     seed);
        float v01 = pixelHash(ix,     iz + 1, seed);
        float v11 = pixelHash(ix + 1, iz + 1, seed);
        float lo  = v00 + ux * (v10 - v00);
        float hi  = v01 + ux * (v11 - v01);
        return lo + uz * (hi - lo);
    }

    /**
     * Deterministic hash in {@code [-1, 1]}.  Shared by the Voronoi jitter
     * and all four domain-warp noise lattices.
     */
    private static float pixelHash(int x, int z, int seed) {
        int n = x * 1619 + z * 31337 + seed * 6971;
        n = (n << 13) ^ n;
        n = n * (n * n * 15731 + 789221) + 1376312589;
        return (n & 0x7FFFFFFF) / 1073741824f - 1f;  // map [0, 2^31) → [-1, 1]
    }

    /* ------------------------------------------------------------------ */
    /* Accessors                                                            */
    /* ------------------------------------------------------------------ */

    public static boolean isLoaded()    { return loaded;      }
    public static int     getWidth()    { return imageWidth;  }
    public static int     getHeight()   { return imageHeight; }
}