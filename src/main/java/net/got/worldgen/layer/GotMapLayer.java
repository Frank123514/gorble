package net.got.worldgen.layer;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Reads {@code data/got/worldgen/map/biomemap.png} and converts pixel RGB
 * values into GoT biome IDs, providing a {@link LayerArea} that returns the
 * correct biome for any noise-coordinate pair.
 *
 * <p><b>Why this class was missing:</b> {@link net.got.worldgen.MapReloadListener}
 * explicitly stated that "BiomemapLoader has been removed" and biomes are now
 * layer-generated. However, {@code GotWorldLayers} only implements the classic
 * <em>random</em> path — it never reads the map. This meant every world produced
 * random biome placement instead of the hand-crafted Westeros geography painted
 * in {@code biomemap.png}. This class re-implements the missing loader.
 *
 * <h3>Coordinate mapping</h3>
 * <p>The PNG is treated as a top-down map centred at the world origin.
 * One pixel = {@value #BLOCKS_PER_PIXEL} blocks. Noise cells are 4 blocks wide,
 * so {@code pixelX = noiseX / NOISE_CELLS_PER_PIXEL + imgWidth/2}.
 * Coordinates outside the image fall back to the classic layer system.
 *
 * <h3>Colour → biome matching</h3>
 * <p>Colours are matched against the authoritative {@code biome_colors.json} table
 * using nearest squared Euclidean distance in RGB space, tolerating minor PNG artefacts.
 */
public final class GotMapLayer {

    private static final Logger LOGGER = LogUtils.getLogger();

    /** Resource path for the biomemap image inside the mod's data pack. */
    public static final ResourceLocation BIOMEMAP_LOCATION =
            ResourceLocation.fromNamespaceAndPath("got", "worldgen/map/biomemap.png");

    // ── World ↔ pixel coordinate constants (from GotMapWidget) ───────────

    /**
     * Blocks per pixel — sourced from {@code GotMapWidget.BLOCKS_PER_PIXEL}.
     * The map GUI uses this same constant to position the player dot,
     * so it is the authoritative scale for the biomemap image.
     */
    private static final double BLOCKS_PER_PIXEL    = 96.0;

    /**
     * Total world width in blocks that the image represents, from
     * {@code GotMapWidget.WORLD_WIDTH_BLOCKS}. The world X origin sits at
     * pixel {@code WORLD_WIDTH_BLOCKS / 2 / BLOCKS_PER_PIXEL} from the left edge.
     */
    private static final double WORLD_WIDTH_BLOCKS  = 241248.0;

    /**
     * Total world height (Z-axis) in blocks that the image represents, from
     * {@code GotMapWidget.WORLD_HEIGHT_BLOCKS}.
     */
    private static final double WORLD_HEIGHT_BLOCKS = 188352.0;

    // ── Colour table ──────────────────────────────────────────────────────

    /**
     * Authoritative colour → biome mapping sourced directly from
     * {@code biome_colors.json}. Each hex entry is the exact pixel colour
     * painted in {@code biomemap.png}; matched via nearest-RGB-distance so
     * minor PNG compression artefacts are tolerated.
     *
     * <p>Note: {@code wolfswood} (#047D17), {@code ironwood} (#02450D), and
     * {@code neck} (#2F4A33) were previously missing from the guessed table and
     * are now correctly included. {@code lake}, {@code sheepshead_hills}, and
     * {@code iron_hills} are absent from the authoritative JSON and have been
     * removed — those biomes are placed by the classic layer system as fallback.
     */
    private static final int[] COLOR_TABLE_RGB = {
            rgb(0x94, 0x90, 0x38),  // #949038 → got:north
            rgb(0xAD, 0xA9, 0x42),  // #ADA942 → got:barrowlands
            rgb(0x92, 0xB0, 0xAC),  // #92B0AC → got:stony_shore
            rgb(0x80, 0x8F, 0x81),  // #808F81 → got:north_hills
            rgb(0x2F, 0x4A, 0x33),  // #2F4A33 → got:neck
            rgb(0x02, 0x45, 0x0D),  // #02450D → got:ironwood
            rgb(0x04, 0x7D, 0x17),  // #047D17 → got:wolfswood
            rgb(0x00, 0x22, 0x9D),  // #00229D → got:ocean
            rgb(0x11, 0x07, 0x51),  // #110751 → got:deep_ocean
            rgb(0x2D, 0x67, 0x96),  // #2D6796 → got:river
            rgb(0x35, 0xA1, 0x80),  // #35A180 → got:neck_river
            rgb(0x4B, 0x91, 0xE6),  // #4B91E6 → got:frozen_river
            rgb(0xBB, 0xCC, 0xCD),  // #BBCCCD → got:frostfangs
            rgb(0xFF, 0xFF, 0xFF),  // #FFFFFF → got:always_winter
            rgb(0xA5, 0xB7, 0xB9),  // #A5B7B9 → got:north_mountains
            rgb(0x53, 0x70, 0x53),  // #537053 → got:haunted_forest
    };

    private static final int[] COLOR_TABLE_ID = {
            GotBiomeRegistry.ID_NORTH,
            GotBiomeRegistry.ID_BARROWLANDS,
            GotBiomeRegistry.ID_STONY_SHORE,
            GotBiomeRegistry.ID_NORTH_HILLS,
            GotBiomeRegistry.ID_NECK,
            GotBiomeRegistry.ID_IRONWOOD,
            GotBiomeRegistry.ID_WOLFSWOOD,
            GotBiomeRegistry.ID_OCEAN,
            GotBiomeRegistry.ID_DEEP_OCEAN,
            GotBiomeRegistry.ID_RIVER,
            GotBiomeRegistry.ID_NECK_RIVER,
            GotBiomeRegistry.ID_FROZEN_RIVER,
            GotBiomeRegistry.ID_FROSTFANGS,
            GotBiomeRegistry.ID_ALWAYS_WINTER,
            GotBiomeRegistry.ID_NORTH_MOUNTAINS,
            GotBiomeRegistry.ID_HAUNTED_FOREST,
    };

    // ── PreparedMap (result of off-thread prepare step) ───────────────────

    /**
     * Holds the raw pixel data after {@link #prepare(ResourceManager)} completes.
     * Passed to {@link net.got.worldgen.GotBiomeSource#setMapLayer(PreparedMap)}
     * on the main thread via the reload listener's {@code apply()} call.
     */
    public static final class PreparedMap {
        final int[] pixels;
        final int   width;
        final int   height;
        final boolean loaded;

        private PreparedMap(int[] pixels, int width, int height) {
            this.pixels  = pixels;
            this.width   = width;
            this.height  = height;
            this.loaded  = pixels != null;
        }

        /** Sentinel returned when loading fails; callers should use the classic layer. */
        static final PreparedMap FAILED = new PreparedMap(null, 0, 0);
    }

    // ── Public API ────────────────────────────────────────────────────────

    /**
     * Off-thread step: reads and decodes {@code biomemap.png}.
     * Returns {@link PreparedMap#FAILED} if the resource is absent or cannot be decoded.
     */
    public static PreparedMap prepare(ResourceManager manager) {
        try {
            Optional<Resource> optRes = manager.getResource(BIOMEMAP_LOCATION);
            if (optRes.isEmpty()) {
                LOGGER.warn("[GoT Worldgen] biomemap.png not found at {}. Classic layer system will be used.",
                        BIOMEMAP_LOCATION);
                return PreparedMap.FAILED;
            }
            try (InputStream is = optRes.get().open()) {
                BufferedImage img = ImageIO.read(is);
                if (img == null) {
                    LOGGER.error("[GoT Worldgen] ImageIO could not decode biomemap.png. Classic layers will be used.");
                    return PreparedMap.FAILED;
                }
                int w = img.getWidth();
                int h = img.getHeight();
                int[] pixels = img.getRGB(0, 0, w, h, null, 0, w);
                LOGGER.info("[GoT Worldgen] biomemap.png loaded successfully ({}×{} pixels).", w, h);
                return new PreparedMap(pixels, w, h);
            }
        } catch (IOException e) {
            LOGGER.error("[GoT Worldgen] IO error loading biomemap.png. Classic layers will be used.", e);
            return PreparedMap.FAILED;
        }
    }

    /**
     * Main-thread step: wraps the loaded pixel data as a {@link LayerArea}.
     *
     * @param prepared the result of {@link #prepare(ResourceManager)}
     * @param fallback the classic layer to use for out-of-bounds coordinates,
     *                 or as the full result when {@code prepared} failed
     * @return a {@link LayerArea} backed by the biomemap image, or {@code fallback}
     */
    public static LayerArea createLayer(PreparedMap prepared, LayerArea fallback) {
        if (!prepared.loaded) {
            return fallback;
        }
        int[] pixels  = prepared.pixels;
        int   imgW    = prepared.width;
        int   imgH    = prepared.height;

        return new LayerArea((noiseX, noiseZ) -> {
            // Convert noise coords (1 cell = 4 blocks) → pixel coords.
            // Formula mirrors GotMapWidget exactly:
            //   pixelX = (blockX + WORLD_WIDTH_BLOCKS/2)  / BLOCKS_PER_PIXEL
            //   pixelZ = (blockZ + WORLD_HEIGHT_BLOCKS/2) / BLOCKS_PER_PIXEL
            // Since noiseX = blockX >> 2, blockX = noiseX * 4:
            int px = (int) ((noiseX * 4 + WORLD_WIDTH_BLOCKS  * 0.5) / BLOCKS_PER_PIXEL);
            int pz = (int) ((noiseZ * 4 + WORLD_HEIGHT_BLOCKS * 0.5) / BLOCKS_PER_PIXEL);

            if (px < 0 || px >= imgW || pz < 0 || pz >= imgH) {
                return fallback.get(noiseX, noiseZ);
            }
            return closestBiome(pixels[pz * imgW + px]);
        });
    }

    // ── Colour matching ───────────────────────────────────────────────────

    private static int closestBiome(int argb) {
        int r = (argb >> 16) & 0xFF;
        int g = (argb >>  8) & 0xFF;
        int b =  argb        & 0xFF;

        int bestId   = GotBiomeRegistry.ID_OCEAN;
        int bestDist = Integer.MAX_VALUE;

        for (int i = 0; i < COLOR_TABLE_RGB.length; i++) {
            int packed = COLOR_TABLE_RGB[i];
            int dr = r - ((packed >> 16) & 0xFF);
            int dg = g - ((packed >>  8) & 0xFF);
            int db = b - ( packed        & 0xFF);
            int dist = dr * dr + dg * dg + db * db;
            if (dist < bestDist) {
                bestDist = dist;
                bestId   = COLOR_TABLE_ID[i];
                if (dist == 0) break;
            }
        }
        return bestId;
    }

    private static int rgb(int r, int g, int b) { return (r << 16) | (g << 8) | b; }

    private GotMapLayer() {}
}