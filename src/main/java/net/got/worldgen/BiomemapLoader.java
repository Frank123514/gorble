package net.got.worldgen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Loads the biome-paint PNG and exposes pixel colours for world coordinates.
 *
 * Coordinate mapping — identical to {@link HeightmapLoader}:
 *   1 pixel = {@value MAP_SCALE} world blocks
 *   pixel (0,0) = world block (−width/2 * scale, −height/2 * scale)
 *
 * Pixel colours are matched against the palette in biome_colors.json
 * by {@link net.got.worldgen.GotBiomeSource} using nearest-RGB-distance,
 * so minor JPEG/PNG compression artefacts are handled gracefully.
 *
 * IMPORTANT: Uses {@link MapWarp#warp} with the same parameters as
 * {@link HeightmapLoader} so biome borders track the warped terrain exactly.
 */
public final class BiomemapLoader {

    /** World blocks per pixel — must match the map art's scale. */
    public static final int MAP_SCALE = 56;

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
     * Uses the SAME {@link MapWarp#warp} domain-warp as {@link HeightmapLoader}
     * so biome borders follow the same organic curves as terrain features.
     * Without this, painted river/land edges look like blocky pixel outlines
     * that don't match the warped coastline carved by the heightmap.
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

        // Apply the SAME domain warp as HeightmapLoader so biome borders
        // follow the same organic curves as the terrain — no misalignment.
        float[] wc = MapWarp.warp(worldX, worldZ, imageWidth, imageHeight);
        int px = Math.round(wc[0]);
        int pz = Math.round(wc[1]);

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
     * Returns the actual post-warp pixel coordinate that
     * {@link #getColorAtWorld} sampled for this world position.
     * Use this as the origin for raw-pixel neighbour scans in image space.
     *
     * @return int[2] { pixelX, pixelZ } (clamped to image bounds)
     */
    public static int[] getWarpedPixel(int worldX, int worldZ) {
        if (!loaded) return new int[]{0, 0};
        float[] wc = MapWarp.warp(worldX, worldZ, imageWidth, imageHeight);
        int px = Math.max(0, Math.min(imageWidth  - 1, Math.round(wc[0])));
        int pz = Math.max(0, Math.min(imageHeight - 1, Math.round(wc[1])));
        return new int[]{ px, pz };
    }

    /* ------------------------------------------------------------------ */
    /* Accessors                                                            */
    /* ------------------------------------------------------------------ */

    public static boolean isLoaded()    { return loaded;      }
    public static int     getWidth()    { return imageWidth;  }
    public static int     getHeight()   { return imageHeight; }
}