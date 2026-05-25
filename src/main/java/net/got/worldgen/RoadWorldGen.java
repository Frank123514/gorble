package net.got.worldgen;

import net.got.faction.RoadData;
import net.got.faction.RoadRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Places road blocks into chunks during world generation.
 *
 * <p>Call {@link #buildRoadsInChunk(ChunkAccess)} at the end of
 * {@code GotChunkGenerator.buildSurface}, after
 * {@code vanilla.buildSurface(...)} has already run.
 *
 * <h2>Road cross-section (Kingsroad example, 9 wide)</h2>
 * <pre>
 *   B  B  B  B  B  B  B  B  B
 *   ←──── cobblestone/stone ────→
 * </pre>
 */
public final class RoadWorldGen {

    private static final Random RANDOM = new Random();

    // ── Coordinate constants (must match GotMapWidget) ────────────────────

    static final float BLOCKS_PER_PIXEL    = 46.0f;
    static final float WORLD_WIDTH_BLOCKS  = 193522f;
    static final float WORLD_HEIGHT_BLOCKS = 150742f;

    // ── Road widths (half-width of paved surface, in blocks) ─────────────

    /** Total paved width = HALF * 2 + 1 center column. */
    private static final double HALF_KINGSROAD = 4.0; // 9 blocks wide
    private static final double HALF_ROAD      = 2.0; // 5 blocks wide
    private static final double HALF_PATH      = 1.0; // 3 blocks wide

    // ── Block palette ─────────────────────────────────────────────────────

    private static final BlockState STONE_BRICKS    = Blocks.STONE_BRICKS.defaultBlockState();
    private static final BlockState COBBLESTONE     = Blocks.COBBLESTONE.defaultBlockState();
    private static final BlockState GRAVEL          = Blocks.GRAVEL.defaultBlockState();
    private static final BlockState AIR             = Blocks.AIR.defaultBlockState();

    // ── Segment cache (lazily built once from RoadRegistry) ──────────────

    private static volatile List<Segment> cachedSegments = null;

    /**
     * Checks if the given world position falls within any road's paved area.
     * Used to prevent feature/vegetation generation on roads.
     *
     * @param worldX X coordinate in world space
     * @param worldZ Z coordinate in world space
     * @return true if the position is on a road surface
     */
    public static boolean isPositionOnRoad(int worldX, int worldZ) {
        for (Segment seg : segments()) {
            double dist = seg.distanceTo(worldX, worldZ);
            if (dist <= seg.halfWidth) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clears any vegetation or features from road surfaces in the given chunk.
     * Call this after biome decoration to ensure roads stay clear.
     */
    public static void clearVegetationFromRoads(ChunkAccess chunk) {
        ChunkPos cp = chunk.getPos();
        int chunkMinX = cp.getMinBlockX();
        int chunkMinZ = cp.getMinBlockZ();

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = chunkMinX + lx;
                int wz = chunkMinZ + lz;

                if (isPositionOnRoad(wx, wz)) {
                    int surfaceY = GotChunkGenerator.computeSurfaceY(wx, wz);
                    
                    // Skip underwater roads
                    if (surfaceY < GotChunkGenerator.SEA_LEVEL) continue;

                    // Clear everything above the road surface up to a reasonable height
                    // This removes grass, flowers, crops, tall grass, etc.
                    for (int y = surfaceY + 1; y <= surfaceY + 3; y++) {
                        chunk.setBlockState(new BlockPos(lx, y, lz), AIR, false);
                    }
                }
            }
        }
    }

    private static List<Segment> segments() {
        if (cachedSegments == null) {
            synchronized (RoadWorldGen.class) {
                if (cachedSegments == null) {
                    List<Segment> list = new ArrayList<>();
                    for (RoadData road : RoadRegistry.ALL) {
                        if ("sea_lane".equals(road.type())) continue; // no blocks in water
                        List<RoadData.Point> pts = road.points();
                        for (int i = 0; i < pts.size() - 1; i++) {
                            list.add(new Segment(pts.get(i), pts.get(i + 1), road.type()));
                        }
                    }
                    cachedSegments = list;
                }
            }
        }
        return cachedSegments;
    }

    // ── Public entry point ────────────────────────────────────────────────

    /**
     * Called once per chunk from {@code GotChunkGenerator.buildSurface},
     * after {@code vanilla.buildSurface} has already run.
     */
    public static void buildRoadsInChunk(ChunkAccess chunk) {
        ChunkPos cp  = chunk.getPos();
        int chunkMinX = cp.getMinBlockX();
        int chunkMinZ = cp.getMinBlockZ();

        // Quick cull: collect only segments whose expanded bounding box overlaps this chunk
        List<Segment> relevant = new ArrayList<>();
        for (Segment seg : segments()) {
            double expand = seg.halfWidth + 2;
            if (seg.maxBX + expand >= chunkMinX     && seg.minBX - expand <= chunkMinX + 15
                    && seg.maxBZ + expand >= chunkMinZ     && seg.minBZ - expand <= chunkMinZ + 15) {
                relevant.add(seg);
            }
        }
        if (relevant.isEmpty()) return;

        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = chunkMinX + lx;
                int wz = chunkMinZ + lz;

                // Find the nearest segment and its perpendicular distance
                double minDist = Double.MAX_VALUE;
                Segment nearest = null;
                for (Segment seg : relevant) {
                    double d = seg.distanceTo(wx, wz);
                    if (d < minDist) {
                        minDist = d;
                        nearest = seg;
                    }
                }
                if (nearest == null) continue;

                if (minDist > nearest.halfWidth) continue;

                int surfaceY = GotChunkGenerator.computeSurfaceY(wx, wz);

                // Skip underwater road placement (rivers, sea coast)
                if (surfaceY < GotChunkGenerator.SEA_LEVEL) continue;

                // Place surface block from the road's palette
                chunk.setBlockState(new BlockPos(lx, surfaceY, lz),
                        surfaceBlock(nearest), false);
                // Clear the block directly above (removes grass, flowers, saplings, etc.)
                chunk.setBlockState(new BlockPos(lx, surfaceY + 1, lz), AIR, false);
            }
        }
    }

    // ── Block selection ───────────────────────────────────────────────────

    /**
     * Selects a random surface block from the road's configured palette.
     * The palette allows weighted probabilities by repeating entries.
     */
    private static BlockState surfaceBlock(Segment seg) {
        // Get the road data to access its palette
        for (RoadData road : RoadRegistry.ALL) {
            if (road.type().equals(seg.type)) {
                List<String> palette = road.palette().surface();
                if (palette.isEmpty()) {
                    return AIR; // e.g., sea_lane
                }
                // Random selection from weighted palette
                String blockId = palette.get(RANDOM.nextInt(palette.size()));
                try {
                    return net.minecraft.core.registries.BuiltInRegistries.BLOCK
                            .getOptional(net.minecraft.resources.ResourceLocation.parse(blockId))
                            .map(block -> block.defaultBlockState())
                            .orElse(GRAVEL);
                } catch (Exception e) {
                    return GRAVEL; // fallback
                }
            }
        }
        return GRAVEL; // fallback
    }

    // ── Segment ───────────────────────────────────────────────────────────

    private static final class Segment {
        final double ax, az; // start point in block coords
        final double bx, bz; // end   point in block coords
        final String type;
        final double halfWidth;
        // AABB for chunk culling
        final double minBX, maxBX, minBZ, maxBZ;

        Segment(RoadData.Point a, RoadData.Point b, String type) {
            this.ax   = pixelToBlockX(a.pixelX());
            this.az   = pixelToBlockZ(a.pixelY());
            this.bx   = pixelToBlockX(b.pixelX());
            this.bz   = pixelToBlockZ(b.pixelY());
            this.type = type;
            this.halfWidth = switch (type) {
                case "kingsroad" -> HALF_KINGSROAD;
                case "road"      -> HALF_ROAD;
                default          -> HALF_PATH;
            };
            this.minBX = Math.min(ax, bx);
            this.maxBX = Math.max(ax, bx);
            this.minBZ = Math.min(az, bz);
            this.maxBZ = Math.max(az, bz);
        }

        /** Perpendicular distance from point (px, pz) to this segment. */
        double distanceTo(double px, double pz) {
            double dx = bx - ax, dz = bz - az;
            double lenSq = dx * dx + dz * dz;
            if (lenSq == 0.0) {
                double ex = px - ax, ez = pz - az;
                return Math.sqrt(ex * ex + ez * ez);
            }
            double t = Math.max(0.0, Math.min(1.0,
                    ((px - ax) * dx + (pz - az) * dz) / lenSq));
            double cx = ax + t * dx, cz = az + t * dz;
            double ex = px - cx, ez = pz - cz;
            return Math.sqrt(ex * ex + ez * ez);
        }
    }

    // ── Coordinate conversion ─────────────────────────────────────────────

    static int pixelToBlockX(int pixelX) {
        return Math.round(pixelX * BLOCKS_PER_PIXEL - WORLD_WIDTH_BLOCKS  / 2.0f);
    }

    static int pixelToBlockZ(int pixelY) {
        return Math.round(pixelY * BLOCKS_PER_PIXEL - WORLD_HEIGHT_BLOCKS / 2.0f);
    }

    private RoadWorldGen() {}
}