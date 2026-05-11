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
 * Loads {@code got:worldgen/map/biomemap.png} and exposes it as a static
 * pixel grid for the chunk generator and biome source.
 *
 * <p>Thread safety: {@link #load} runs off-thread in
 * {@code MapReloadListener#prepare}; {@link #apply} is called on the
 * main thread in {@code apply}.  Reads via {@link #getRawPixel} are safe
 * at any point after {@link #apply} returns.
 */
public final class BiomemapLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation BIOMEMAP_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/map/biomemap.png");

    /** Number of world blocks represented by one biomemap pixel. */
    public static final int MAP_SCALE = 46;

    // Volatile so that writes from apply() are visible to reader threads
    private static volatile int[][] pixels   = null;
    private static volatile int     mapWidth  = 0;
    private static volatile int     mapHeight = 0;

    private BiomemapLoader() {}

    // ── Query ──────────────────────────────────────────────────────────────

    /** Returns {@code true} once a biomemap has been successfully applied. */
    public static boolean isLoaded() {
        return pixels != null;
    }

    public static int getWidth()  { return mapWidth; }
    public static int getHeight() { return mapHeight; }

    /**
     * Returns the raw {@code 0xRRGGBB} color at biomemap pixel (px, pz).
     * Coordinates are clamped to the image boundary.
     * Returns {@code 0} if the map has not been loaded yet.
     */
    public static int getRawPixel(int px, int pz) {
        int[][] p = pixels;
        if (p == null) return 0;
        px = Math.max(0, Math.min(mapWidth  - 1, px));
        pz = Math.max(0, Math.min(mapHeight - 1, pz));
        return p[px][pz];
    }

    // ── Load / apply ───────────────────────────────────────────────────────

    /**
     * Reads {@code biomemap.png} from the resource manager.
     * Intended to run off the main thread in {@code MapReloadListener#prepare}.
     *
     * @return pixel grid {@code [x][z] = 0xRRGGBB}, or {@code null} on failure
     */
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

    /**
     * Pushes a freshly loaded pixel grid into the static store.
     * Must be called on the main thread.
     */
    public static void apply(int[][] grid, int width, int height) {
        pixels    = grid;
        mapWidth  = width;
        mapHeight = height;
    }
}
