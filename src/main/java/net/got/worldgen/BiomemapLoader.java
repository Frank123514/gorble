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
 * Pixel colours are matched against the palette in {@link GotBiomeSource}
 * using nearest-RGB-distance, so minor PNG compression artefacts are
 * handled gracefully.
 */
public final class BiomemapLoader {

    /** World blocks per pixel — must match the map art's scale. */
    public static final int MAP_SCALE = 96;

    private static int     imageWidth;
    private static int     imageHeight;
    /** [pixelX][pixelZ] = 0xRRGGBB (alpha stripped). */
    private static int[][] pixels;
    private static boolean loaded = false;

    private BiomemapLoader() {}

    // ── Load ──────────────────────────────────────────────────────────────

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

    // ── Query ─────────────────────────────────────────────────────────────

    /**
     * Returns the 0xRRGGBB pixel colour at the given world XZ position.
     * Out-of-bounds coordinates return {@code 0x110751} (deep ocean).
     *
     * @param worldX world block X
     * @param worldZ world block Z
     * @return 24-bit RGB colour
     */
    public static int getColorAtWorld(int worldX, int worldZ) {
        if (!loaded) return 0x110751;

        int px = (int) Math.floor(worldX / (float) MAP_SCALE + imageWidth  * 0.5f);
        int pz = (int) Math.floor(worldZ / (float) MAP_SCALE + imageHeight * 0.5f);

        if (px < 0 || pz < 0 || px >= imageWidth || pz >= imageHeight) {
            return 0x110751;
        }

        return pixels[px][pz];
    }

    /**
     * Returns the 0xRRGGBB colour at the given IMAGE-SPACE pixel coordinate.
     * Coordinates are clamped to image bounds.
     *
     * @param px pixel column
     * @param pz pixel row
     * @return 24-bit RGB colour, or {@code 0x110751} if image not yet loaded
     */
    public static int getRawPixel(int px, int pz) {
        if (!loaded) return 0x110751;
        px = Math.max(0, Math.min(imageWidth  - 1, px));
        pz = Math.max(0, Math.min(imageHeight - 1, pz));
        return pixels[px][pz];
    }

    /**
     * Returns the pixel coordinate for the given world position.
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

    // ── Accessors ─────────────────────────────────────────────────────────

    public static boolean isLoaded()  { return loaded;      }
    public static int     getWidth()  { return imageWidth;  }
    public static int     getHeight() { return imageHeight; }
}
