package net.francis.got.worldgen;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Optional;

public final class BiomemapLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Identifier BIOMEMAP_LOC =
            Identifier.fromNamespaceAndPath("got", "worldgen/map/biomemap.png");

    public static final int MAP_SCALE = 45;

    private static volatile int[][] pixels   = null;
    private static volatile int     mapWidth  = 0;
    private static volatile int     mapHeight = 0;

    private BiomemapLoader() {}

    public static boolean isLoaded() {
        return pixels != null;
    }

    public static int getWidth()  { return mapWidth; }
    public static int getHeight() { return mapHeight; }

    public static int getRawPixel(int px, int pz) {
        int[][] p = pixels;
        if (p == null) return 0;
        px = Math.max(0, Math.min(mapWidth  - 1, px));
        pz = Math.max(0, Math.min(mapHeight - 1, pz));
        return p[px][pz];
    }

    public static int[][] load(ResourceManager manager) {
        try {
            Optional<Resource> res = manager.getResource(BIOMEMAP_LOC);
            if (res.isEmpty()) {
                LOGGER.warn("[GoT] biomemap.png not found at {}", BIOMEMAP_LOC);
                return null;
            }

            BufferedImage img = ImageIO.read(res.get().open());
            int w = img.getWidth();
            int h = img.getHeight();

            int[][] grid = new int[w][h];
            for (int x = 0; x < w; x++) {
                for (int z = 0; z < h; z++) {
                    grid[x][z] = img.getRGB(x, z) & 0xFFFFFF;
                }
            }
            LOGGER.info("[GoT] Loaded biomemap.png ({}x{})", w, h);
            return grid;

        } catch (Exception e) {
            LOGGER.error("[GoT] Failed to load biomemap.png", e);
            return null;
        }
    }

    public static void apply(int[][] grid, int width, int height) {
        pixels    = grid;
        mapWidth  = width;
        mapHeight = height;
    }
}
