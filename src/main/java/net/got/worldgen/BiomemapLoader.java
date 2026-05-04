package net.got.worldgen;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * Loads {@code biomemap.png} and holds it as a static pixel grid that the
 * chunk generator and biome source can query directly via {@link #getRawPixel}.
 *
 * <p>Call {@link #load(ResourceManager)} off-thread (in
 * {@code MapReloadListener#prepare}), then {@link #apply(int[][], int, int)}
 * on the main thread.
 */
public final class BiomemapLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation BIOMEMAP_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/map/biomemap.png");

    /** World blocks represented by one pixel. */
    public static final int MAP_SCALE = 46;

    // ── Static pixel store ─────────────────────────────────────────────────

    private static volatile int[][] pixels = null;
    private static volatile int     mapWidth  = 0;
    private static volatile int     mapHeight = 0;

    private BiomemapLoader() {}

    // ── Query API ──────────────────────────────────────────────────────────

    public static boolean isLoaded() { return pixels != null; }

    public static int getWidth()  { return mapWidth; }
    public static int getHeight() { return mapHeight; }

    /**
     * Returns the raw 0xRRGGBB pixel color at pixel coordinate (px, pz),
     * clamped to the image boundary. Returns 0 if the map is not loaded.
     */
    public static int getRawPixel(int px, int pz) {
        int[][] p = pixels;
        if (p == null) return 0;
        px = Math.max(0, Math.min(mapWidth  - 1, px));
        pz = Math.max(0, Math.min(mapHeight - 1, pz));
        return p[px][pz];
    }

    // ── Load / apply ───────────────────────────────────────────────────────

    /** Reads the biomemap off-thread. Returns null on failure. */
    public static int[][] load(ResourceManager manager) {
        try {
            Optional<Resource> res = manager.getResource(BIOMEMAP_LOC);
            if (res.isEmpty()) {
                LOGGER.warn("[GoT] biomemap.png not found at {}", BIOMEMAP_LOC);
                return null;
            }
            BufferedImage img = ImageIO.read(res.get().open());
            int w = img.getWidth(), h = img.getHeight();
            int[][] grid = new int[w][h];
            for (int x = 0; x < w; x++)
                for (int z = 0; z < h; z++)
                    grid[x][z] = img.getRGB(x, z) & 0xFFFFFF;
            LOGGER.info("[GoT] Loaded biomemap.png ({}x{})", w, h);
            return grid;
        } catch (Exception e) {
            LOGGER.error("[GoT] Failed to load biomemap.png", e);
            return null;
        }
    }

    /** Pushes a loaded pixel grid into the static store. Call on the main thread. */
    public static void apply(int[][] grid, int width, int height) {
        pixels    = grid;
        mapWidth  = width;
        mapHeight = height;
    }
}
