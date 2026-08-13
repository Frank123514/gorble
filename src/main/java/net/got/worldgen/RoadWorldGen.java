package net.got.worldgen;

import com.mojang.logging.LogUtils;
import net.got.faction.RoadData;
import net.got.faction.RoadRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RoadWorldGen {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Random RANDOM = new Random();

    static final float BLOCKS_PER_PIXEL    = 45.0f;
    static final float WORLD_WIDTH_BLOCKS  = 189315f;
    static final float WORLD_HEIGHT_BLOCKS = 147465f;

    private static final double HALF_KINGSROAD = 4.0;
    private static final double HALF_ROAD      = 2.0;
    private static final double HALF_PATH      = 1.0;

    private static final int BEZIER_STEPS = 8;

    private static final BlockState STONE_BRICKS    = Blocks.STONE_BRICKS.defaultBlockState();
    private static final BlockState COBBLESTONE     = Blocks.COBBLESTONE.defaultBlockState();
    private static final BlockState GRAVEL          = Blocks.GRAVEL.defaultBlockState();
    private static final BlockState AIR             = Blocks.AIR.defaultBlockState();

    private static volatile List<Segment> cachedSegments = null;

    public static boolean isPositionOnRoad(int worldX, int worldZ) {
        for (Segment seg : segments()) {
            double dist = seg.distanceTo(worldX, worldZ);
            if (dist <= seg.halfWidth) {
                return true;
            }
        }
        return false;
    }

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

                    if (surfaceY < GotChunkGenerator.SEA_LEVEL) continue;

                    for (int y = surfaceY + 1; y <= surfaceY + 3; y++) {
                        chunk.setBlockState(new BlockPos(lx, y, lz), AIR, 3);
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
                        if ("sea_lane".equals(road.type())) continue;
                        
                        List<RoadData.Point> smoothed = catmullRomTessellate(road.points(), BEZIER_STEPS);
                        for (int i = 0; i < smoothed.size() - 1; i++) {
                            list.add(new Segment(smoothed.get(i), smoothed.get(i + 1), road.type()));
                        }
                    }
                    cachedSegments = list;
                    LOGGER.info("[GoT][DEBUG] RoadWorldGen: built {} segment(s) from {} road(s) in RoadRegistry.ALL",
                            list.size(), RoadRegistry.ALL.size());
                    if (!list.isEmpty()) {
                        Segment first = list.get(0);
                        Segment last  = list.get(list.size() - 1);
                        LOGGER.info("[GoT][DEBUG] RoadWorldGen: first segment ({}, {}) -> ({}, {}), type={}",
                                first.ax, first.az, first.bx, first.bz, first.type);
                    }
                }
            }
        }
        return cachedSegments;
    }

    public static void buildRoadsInChunk(ChunkAccess chunk) {
        ChunkPos cp  = chunk.getPos();
        int chunkMinX = cp.getMinBlockX();
        int chunkMinZ = cp.getMinBlockZ();

        List<Segment> relevant = new ArrayList<>();
        for (Segment seg : segments()) {
            double expand = seg.halfWidth + 2;
            if (seg.maxBX + expand >= chunkMinX     && seg.minBX - expand <= chunkMinX + 15
                    && seg.maxBZ + expand >= chunkMinZ     && seg.minBZ - expand <= chunkMinZ + 15) {
                relevant.add(seg);
            }
        }
        if (relevant.isEmpty()) return;

        LOGGER.debug("[GoT][DEBUG] RoadWorldGen: chunk ({},{}) [worldX {}..{}, worldZ {}..{}] has {} relevant road segment(s)",
                cp.x, cp.z, chunkMinX, chunkMinX + 15, chunkMinZ, chunkMinZ + 15, relevant.size());

        int placed = 0;
        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int wx = chunkMinX + lx;
                int wz = chunkMinZ + lz;

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

                if (surfaceY < GotChunkGenerator.SEA_LEVEL) continue;

                chunk.setBlockState(new BlockPos(lx, surfaceY, lz),
                        surfaceBlock(nearest), 3);
                
                chunk.setBlockState(new BlockPos(lx, surfaceY + 1, lz), AIR, 3);
                placed++;
            }
        }
        LOGGER.debug("[GoT][DEBUG] RoadWorldGen: chunk ({},{}) placed {} road block(s)", cp.x, cp.z, placed);
    }

    private static BlockState surfaceBlock(Segment seg) {
        for (RoadData road : RoadRegistry.ALL) {
            if (road.type().equals(seg.type)) {
                List<String> palette = road.palette().surface();
                if (palette.isEmpty()) {
                    return AIR;
                }
                String blockId = palette.get(RANDOM.nextInt(palette.size()));
                try {
                    return net.minecraft.core.registries.BuiltInRegistries.BLOCK
                            .getOptional(net.minecraft.resources.Identifier.parse(blockId))
                            .map(block -> block.defaultBlockState())
                            .orElse(GRAVEL);
                } catch (Exception e) {
                    return GRAVEL;
                }
            }
        }
        return GRAVEL;
    }

    static List<RoadData.Point> catmullRomTessellate(List<RoadData.Point> pts, int steps) {
        int n = pts.size();
        if (n < 2) return new ArrayList<>(pts);

        List<RoadData.Point> result = new ArrayList<>(n * steps);

        for (int i = 0; i < n - 1; i++) {
            
            RoadData.Point p0 = pts.get(Math.max(0, i - 1));
            RoadData.Point p1 = pts.get(i);
            RoadData.Point p2 = pts.get(i + 1);
            RoadData.Point p3 = pts.get(Math.min(n - 1, i + 2));

            for (int step = 0; step < steps; step++) {
                double t  = step / (double) steps;
                result.add(catmullRomPoint(p0, p1, p2, p3, t));
            }
        }
        
        result.add(pts.get(n - 1));
        return result;
    }

    private static RoadData.Point catmullRomPoint(
            RoadData.Point p0, RoadData.Point p1,
            RoadData.Point p2, RoadData.Point p3,
            double t) {

        double t2 = t * t;
        double t3 = t2 * t;

        double x = 0.5 * (
                (2.0 * p1.pixelX())
                        + (-p0.pixelX() + p2.pixelX()) * t
                        + (2.0 * p0.pixelX() - 5.0 * p1.pixelX() + 4.0 * p2.pixelX() - p3.pixelX()) * t2
                        + (-p0.pixelX() + 3.0 * p1.pixelX() - 3.0 * p2.pixelX() + p3.pixelX()) * t3
        );
        double y = 0.5 * (
                (2.0 * p1.pixelY())
                        + (-p0.pixelY() + p2.pixelY()) * t
                        + (2.0 * p0.pixelY() - 5.0 * p1.pixelY() + 4.0 * p2.pixelY() - p3.pixelY()) * t2
                        + (-p0.pixelY() + 3.0 * p1.pixelY() - 3.0 * p2.pixelY() + p3.pixelY()) * t3
        );
        return new RoadData.Point((int) Math.round(x), (int) Math.round(y));
    }

    private static final class Segment {
        final double ax, az;
        final double bx, bz;
        final String type;
        final double halfWidth;
        
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

    static int pixelToBlockX(int pixelX) {
        return Math.round(pixelX * BLOCKS_PER_PIXEL - WORLD_WIDTH_BLOCKS  / 2.0f);
    }

    static int pixelToBlockZ(int pixelY) {
        return Math.round(pixelY * BLOCKS_PER_PIXEL - WORLD_HEIGHT_BLOCKS / 2.0f);
    }

    private RoadWorldGen() {}
}