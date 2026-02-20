package net.got.worldgen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public final class HeightmapLoader {

    /** 1 pixel = 10 blocks (MUST MATCH BiomeMapLoader) */
    private static final int MAP_SCALE = 10;

    private static int width;
    private static int height;
    private static float[][] heightData;
    private static boolean loaded = false;

    private static float minHeight = Float.MAX_VALUE;
    private static float maxHeight = Float.MIN_VALUE;

    private HeightmapLoader() {}

    public static void load(InputStream stream) {
        try {
            BufferedImage image = ImageIO.read(stream);
            width = image.getWidth();
            height = image.getHeight();

            heightData = new float[width][height];

            for (int x = 0; x < width; x++) {
                for (int z = 0; z < height; z++) {

                    int rgb = image.getRGB(x, z);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;

                    float gray = (r + g + b) / (3.0f * 255.0f);

                    // store RAW normalized height
                    heightData[x][z] = gray;

                    minHeight = Math.min(minHeight, gray);
                    maxHeight = Math.max(maxHeight, gray);
                }
            }

            loaded = true;
            System.out.println("[GoT] Heightmap loaded: " + width + "x" + height +
                    " | min=" + minHeight + " max=" + maxHeight);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load GoT heightmap", e);
        }
    }

    /* ------------------------------------------------------------ */
    /* SAMPLING                                                     */
    /* ------------------------------------------------------------ */

    /**
     * Get interpolated height at map coordinates (not world coords)
     * This is called by chunk generator with pre-transformed coordinates
     */
    public static float getInterpolatedHeight(float mapX, float mapZ) {
        if (!loaded) return 0.5f;

        int x0 = (int) Math.floor(mapX);
        int z0 = (int) Math.floor(mapZ);
        int x1 = x0 + 1;
        int z1 = z0 + 1;

        // Clamp to valid range
        if (x0 < 0 || z0 < 0 || x1 >= width || z1 >= height) {
            return minHeight;
        }

        float h00 = heightData[x0][z0];
        float h10 = heightData[x1][z0];
        float h01 = heightData[x0][z1];
        float h11 = heightData[x1][z1];

        float tx = mapX - x0;
        float tz = mapZ - z0;

        float hx0 = h00 + tx * (h10 - h00);
        float hx1 = h01 + tx * (h11 - h01);

        return hx0 + tz * (hx1 - hx0);
    }

    /**
     * Get height at world coordinates (convenience method)
     */
    public static float getHeightAtWorld(int worldX, int worldZ) {
        if (!loaded) return 0.5f;

        // Transform world coords to map coords (same as BiomeMapLoader)
        int mapCenterX = width / 2;
        int mapCenterZ = height / 2;

        float mapX = (worldX / (float) MAP_SCALE) + mapCenterX;
        float mapZ = (worldZ / (float) MAP_SCALE) + mapCenterZ;

        return getInterpolatedHeight(mapX, mapZ);
    }

    /* ------------------------------------------------------------ */

    public static float getMinHeight() { return minHeight; }
    public static float getMaxHeight() { return maxHeight; }
    public static int getWidth() { return width; }
    public static int getHeight() { return height; }
    public static boolean isLoaded() { return loaded; }
    public static int getMapScale() { return MAP_SCALE; }
}