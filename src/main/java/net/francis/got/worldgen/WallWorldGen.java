package net.francis.got.worldgen;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.ChunkPos;
import org.joml.SimplexNoise;
import org.slf4j.Logger;

public final class WallWorldGen {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static volatile boolean loggedRange = false;

    private static final int[][] WALL_SPINE = {
            {-65048, -39128},
            {-65002, -39128},
            {-64958, -39172},
            {-64912, -39172},
            {-64868, -39172},
            {-64822, -39218},
            {-64778, -39218},
            {-64732, -39218},
            {-64688, -39262},
            {-64642, -39262},
            {-64598, -39308},
            {-64552, -39308},
            {-64508, -39352},
            {-64462, -39398},
            {-64418, -39398},
            {-64372, -39398},
            {-64328, -39398},
            {-64282, -39398},
            {-64238, -39398},
            {-64192, -39442},
            {-64148, -39442},
            {-64102, -39442},
            {-64058, -39442},
            {-64012, -39442},
            {-63968, -39488},
            {-63922, -39488},
            {-63878, -39488},
            {-63832, -39488},
            {-63788, -39532},
            {-63742, -39532},
            {-63698, -39532},
            {-63652, -39532},
            {-63608, -39532},
            {-63562, -39578},
            {-63518, -39578},
            {-63472, -39578},
            {-63428, -39578},
            {-63382, -39578},
            {-63338, -39622},
            {-63292, -39622},
            {-63248, -39622},
            {-63202, -39622},
            {-63158, -39668},
            {-63112, -39668},
            {-63068, -39668},
            {-63022, -39668},
            {-62978, -39712},
            {-62932, -39712},
            {-62888, -39712},
            {-62842, -39758},
            {-62798, -39758},
            {-62752, -39802},
            {-62708, -39802},
            {-62662, -39802},
            {-62618, -39802},
            {-62572, -39802},
            {-62528, -39802},
            {-62482, -39848},
            {-62438, -39848},
            {-62392, -39848},
            {-62348, -39848},
            {-62302, -39848},
            {-62258, -39848},
            {-62212, -39848},
            {-62168, -39848},
            {-62122, -39848},
            {-62078, -39848},
            {-62032, -39848},
            {-61988, -39848},
            {-61942, -39848},
            {-61898, -39848},
            {-61852, -39848},
            {-61808, -39802},
            {-61762, -39802},
            {-61718, -39802},
            {-61672, -39802},
            {-61628, -39802},
            {-61582, -39802},
            {-61538, -39802},
            {-61492, -39802},
            {-61448, -39802},
            {-61402, -39802},
            {-61358, -39802},
            {-61312, -39802},
            {-61268, -39848},
            {-61222, -39848},
            {-61178, -39848},
            {-61132, -39848},
            {-61088, -39892},
            {-61042, -39892},
            {-60998, -39892},
            {-60952, -39892},
            {-60908, -39892},
            {-60862, -39892},
            {-60818, -39892},
            {-60772, -39892},
            {-60728, -39892},
            {-60682, -39892},
            {-60638, -39892},
            {-60592, -39892},
            {-60548, -39848},
            {-60502, -39848},
            {-60458, -39848},
            {-60412, -39848},
            {-60368, -39848},
            {-60322, -39848},
            {-60278, -39802},
            {-60232, -39802},
            {-60188, -39802},
            {-60142, -39802},
            {-60098, -39802},
            {-60052, -39802},
            {-60008, -39802},
            {-59962, -39802},
            {-59918, -39802},
            {-59872, -39802},
            {-59828, -39802},
            {-59782, -39802},
            {-59738, -39802},
            {-59692, -39848},
            {-59648, -39848},
            {-59602, -39848},
            {-59558, -39848},
            {-59512, -39848},
            {-59468, -39848},
            {-59422, -39848},
            {-59378, -39848},
            {-59332, -39848},
            {-59288, -39848},
            {-59242, -39848},
            {-59198, -39848},
            {-59152, -39848},
            {-59108, -39802},
            {-59062, -39802},
            {-59018, -39802},
            {-58972, -39802},
            {-58928, -39802},
            {-58882, -39802},
            {-58838, -39802},
            {-58792, -39848},
            {-58748, -39848},
            {-58702, -39848},
            {-58658, -39848},
            {-58612, -39848},
            {-58568, -39892},
            {-58522, -39892},
            {-58478, -39892},
            {-58432, -39892},
            {-58388, -39892},
            {-58342, -39938},
            {-58298, -39982},
            {-58252, -39982},
            {-58208, -39982},
            {-58162, -39982},
            {-58118, -39982},
            {-58072, -39982},
            {-58028, -39982},
            {-57982, -39982},
            {-57938, -39982},
            {-57892, -40028},
            {-57848, -40028},
            {-57802, -40028},
            {-57758, -40028},
            {-57712, -40028},
            {-57668, -40072},
            {-57622, -40072},
            {-57578, -40072},
            {-57532, -40072},
            {-57488, -40072},
            {-57442, -40072},
            {-57398, -40072},
            {-57352, -40072},
    };

    public static final int WALL_X_WEST = WALL_SPINE[0][0];
    
    public static final int WALL_X_EAST = WALL_SPINE[WALL_SPINE.length - 1][0];

    public static final int CASTLE_BLACK_X = -60828;

    public static final int WALL_HEIGHT = 200;

    public static final int WALL_THICKNESS = 24;

    private static final int HALF = WALL_THICKNESS / 2;

    private static final int WALL_BATTER = 20;

    private static final int NORTH_BATTER = WALL_BATTER;

    private static final double FACE_NOISE_AMPLITUDE = 3.5;

    private static final double NORTH_FACE_NOISE_AMPLITUDE = FACE_NOISE_AMPLITUDE;

    private static final int SNOW_DRIFT_RADIUS = 20;
    private static final int SNOW_DRIFT_HEIGHT = 12;

    private static final int BATTLEMENT_PERIOD = 4;

    private static final int BATTLEMENT_HEIGHT = 2;

    private static final int TUNNEL_HALF_WIDTH = 1;

    private static final int TUNNEL_HEIGHT = 5;

    private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.defaultBlockState();
    private static final BlockState BLUE_ICE   = Blocks.BLUE_ICE.defaultBlockState();
    private static final BlockState SNOW       = Blocks.SNOW_BLOCK.defaultBlockState();
    private static final BlockState AIR        = Blocks.AIR.defaultBlockState();

    public static int wallCentreZ(int worldX) {
        if (worldX < WALL_X_WEST || worldX > WALL_X_EAST) return Integer.MIN_VALUE;

        int lo = 0, hi = WALL_SPINE.length - 1;
        while (lo < hi) {
            int mid = (lo + hi + 1) >>> 1;
            if (WALL_SPINE[mid][0] <= worldX) lo = mid; else hi = mid - 1;
        }

        if (lo == WALL_SPINE.length - 1) return WALL_SPINE[lo][1];

        int i0 = Math.max(0, lo - 1);
        int i1 = lo;
        int i2 = lo + 1;
        int i3 = Math.min(WALL_SPINE.length - 1, lo + 2);

        double z0 = WALL_SPINE[i0][1];
        double z1 = WALL_SPINE[i1][1];
        double z2 = WALL_SPINE[i2][1];
        double z3 = WALL_SPINE[i3][1];

        double x1 = WALL_SPINE[i1][0];
        double x2 = WALL_SPINE[i2][0];
        if (x1 == x2) return (int) Math.round(z1);

        double t  = (worldX - x1) / (x2 - x1);
        double t2 = t * t;
        double t3 = t2 * t;

        double b0 = -t3 + 3*t2 - 3*t + 1;
        double b1 =  3*t3 - 6*t2       + 4;
        double b2 = -3*t3 + 3*t2 + 3*t + 1;
        double b3 =  t3;

        double z = (b0*z0 + b1*z1 + b2*z2 + b3*z3) / 6.0;
        return (int) Math.round(z);
    }

    private static double batterAt(int relY) {
        return WALL_BATTER * (1.0 - (double) relY / WALL_HEIGHT);
    }

    private static double northBatterAt(int relY) {
        return NORTH_BATTER * (1.0 - (double) relY / WALL_HEIGHT);
    }

    private static double faceNoise(int worldX, int relY) {
        
        double n1 = SimplexNoise.noise((float) (worldX / 40.0), (float) (relY / 60.0));
        
        double n2 = SimplexNoise.noise((float) (worldX / 12.0 + 31.7), (float) (relY / 18.0 + 17.3));
        
        return n1 * 0.7 + n2 * 0.3;
    }

    private static double northFaceNoise(int worldX, int relY) {
        
        double n1 = SimplexNoise.noise((float) (worldX / 40.0 + 50.0), (float) (relY / 60.0 + 50.0));
        double n2 = SimplexNoise.noise((float) (worldX / 12.0 + 81.7), (float) (relY / 18.0 + 67.3));
        return n1 * 0.7 + n2 * 0.3;
    }

    private static double southEdgeAt(int worldX, int relY) {
        return HALF + batterAt(relY) + faceNoise(worldX, relY) * FACE_NOISE_AMPLITUDE;
    }

    private static double northEdgeAt(int worldX, int relY) {
        return -(HALF + northBatterAt(relY) + northFaceNoise(worldX, relY) * NORTH_FACE_NOISE_AMPLITUDE);
    }

    private static int driftHeightAt(int driftDist, int baseY) {
        if (driftDist < 0 || driftDist > SNOW_DRIFT_RADIUS) return 0;
        double fraction = 1.0 - (double) driftDist / SNOW_DRIFT_RADIUS;
        return baseY + (int) Math.round(SNOW_DRIFT_HEIGHT * fraction * fraction);
    }

    public static void buildWallInChunk(ChunkAccess chunk) {
        if (!loggedRange) {
            loggedRange = true;
            LOGGER.info("[GoT][DEBUG] WallWorldGen: spine covers worldX {} .. {} ({} points), CASTLE_BLACK_X={}",
                    WALL_X_WEST, WALL_X_EAST, WALL_SPINE.length, CASTLE_BLACK_X);
        }

        ChunkPos cp       = chunk.getPos();
        int chunkMinX     = cp.getMinBlockX();
        int chunkMaxX     = chunkMinX + 15;
        int chunkMinZ     = cp.getMinBlockZ();
        int chunkMaxZ     = chunkMinZ + 15;

        if (chunkMaxX < WALL_X_WEST || chunkMinX > WALL_X_EAST) {
            LOGGER.debug("[GoT][DEBUG] WallWorldGen: chunk ({},{}) [worldX {}..{}] rejected — outside spine X range {}..{}",
                    cp.x, cp.z, chunkMinX, chunkMaxX, WALL_X_WEST, WALL_X_EAST);
            return;
        }

        int minCentreZ = Integer.MAX_VALUE, maxCentreZ = Integer.MIN_VALUE;
        for (int lx = 0; lx < 16; lx++) {
            int wx = chunkMinX + lx;
            if (wx < WALL_X_WEST || wx > WALL_X_EAST) continue;
            int cz = wallCentreZ(wx);
            if (cz < minCentreZ) minCentreZ = cz;
            if (cz > maxCentreZ) maxCentreZ = cz;
        }
        if (minCentreZ == Integer.MAX_VALUE) return;

        int maxSouthReach = HALF + WALL_BATTER + (int) Math.ceil(FACE_NOISE_AMPLITUDE) + SNOW_DRIFT_RADIUS;
        int maxNorthReach = HALF + NORTH_BATTER + (int) Math.ceil(NORTH_FACE_NOISE_AMPLITUDE) + SNOW_DRIFT_RADIUS;
        if (chunkMaxZ < minCentreZ - maxNorthReach || chunkMinZ > maxCentreZ + maxSouthReach + BATTLEMENT_HEIGHT) {
            LOGGER.debug("[GoT][DEBUG] WallWorldGen: chunk ({},{}) in X range but Z reject — chunkZ {}..{}, wall centreZ {}..{} (+reach N{} S{})",
                    cp.x, cp.z, chunkMinZ, chunkMaxZ, minCentreZ, maxCentreZ, maxNorthReach, maxSouthReach);
            return;
        }

        LOGGER.debug("[GoT][DEBUG] WallWorldGen: chunk ({},{}) [worldX {}..{}, worldZ {}..{}] PASSED both rejects — building wall, centreZ range {}..{}",
                cp.x, cp.z, chunkMinX, chunkMaxX, chunkMinZ, chunkMaxZ, minCentreZ, maxCentreZ);

        for (int lx = 0; lx < 16; lx++) {
            int wx = chunkMinX + lx;
            if (wx < WALL_X_WEST || wx > WALL_X_EAST) continue;

            int centreZ  = wallCentreZ(wx);
            boolean inTunnel = Math.abs(wx - CASTLE_BLACK_X) <= TUNNEL_HALF_WIDTH;

            int baseY = Integer.MAX_VALUE;
            int groundSampleHalf = HALF + WALL_BATTER;
            for (int dz = -groundSampleHalf; dz <= groundSampleHalf; dz++) {
                int sy = GotChunkGenerator.computeSurfaceY(wx, centreZ + dz);
                if (sy < baseY) baseY = sy;
            }

            for (int lz = 0; lz < 16; lz++) {
                int wz = chunkMinZ + lz;
                int dz = wz - centreZ;

                boolean couldBeNorth = dz >= -HALF - NORTH_BATTER - (int) Math.ceil(NORTH_FACE_NOISE_AMPLITUDE) && dz < 0;

                boolean couldBeSouth = dz >= 0 && dz <= HALF + WALL_BATTER + (int) Math.ceil(FACE_NOISE_AMPLITUDE);

                int driftDist = dz - HALF;
                boolean inDriftZone = driftDist > 0 && driftDist <= SNOW_DRIFT_RADIUS;

                int northDriftDist = -HALF - dz;
                boolean inNorthDriftZone = northDriftDist > 0 && northDriftDist <= SNOW_DRIFT_RADIUS;

                if (!couldBeNorth && !couldBeSouth && !inDriftZone && !inNorthDriftZone) continue;

                if (couldBeNorth) {
                    
                    for (int y = baseY; y <= baseY + WALL_HEIGHT; y++) {
                        int relY = y - baseY;
                        double northEdge = northEdgeAt(wx, relY);
                        boolean insideBody = dz <= 0 && dz >= northEdge;
                        if (!insideBody) continue;

                        if (inTunnel && relY >= 0 && relY < TUNNEL_HEIGHT) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), AIR, 3);
                            continue;
                        }

                        boolean isNorthFace = dz <= northEdge + 1.0;

                        if (relY == WALL_HEIGHT) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), PACKED_ICE, 3);
                            if (!isNorthFace) {
                                chunk.setBlockState(new BlockPos(lx, y + 1, lz), SNOW, 3);
                            }
                            continue;
                        }

                        chunk.setBlockState(new BlockPos(lx, y, lz),
                                isNorthFace ? BLUE_ICE : PACKED_ICE, 3);
                    }

                    double topEdge = northEdgeAt(wx, WALL_HEIGHT);
                    if (dz < topEdge && dz >= topEdge - 1) {
                        int merlonBase = baseY + WALL_HEIGHT + 1;
                        boolean isMerlon =
                                Math.abs(wx - WALL_X_WEST) % BATTLEMENT_PERIOD < BATTLEMENT_PERIOD / 2;
                        if (isMerlon) {
                            for (int h = 0; h < BATTLEMENT_HEIGHT; h++) {
                                chunk.setBlockState(
                                        new BlockPos(lx, merlonBase + h, lz),
                                        PACKED_ICE, 3);
                            }
                        }
                    }

                } else if (couldBeSouth) {
                    
                    for (int y = baseY; y <= baseY + WALL_HEIGHT; y++) {
                        int relY = y - baseY;
                        double southEdge = southEdgeAt(wx, relY);
                        boolean insideBody = dz >= 0 && dz <= southEdge;
                        if (!insideBody) continue;

                        if (inTunnel && relY >= 0 && relY < TUNNEL_HEIGHT) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), AIR, 3);
                            continue;
                        }

                        boolean isSouthFace = dz >= southEdge - 1.0;

                        if (relY == WALL_HEIGHT) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), PACKED_ICE, 3);
                            if (!isSouthFace) {
                                chunk.setBlockState(new BlockPos(lx, y + 1, lz), SNOW, 3);
                            }
                            continue;
                        }

                        chunk.setBlockState(new BlockPos(lx, y, lz),
                                isSouthFace ? BLUE_ICE : PACKED_ICE, 3);
                    }

                    double topEdge = southEdgeAt(wx, WALL_HEIGHT);
                    if (dz > topEdge && dz <= topEdge + 1) {
                        int merlonBase = baseY + WALL_HEIGHT + 1;
                        boolean isMerlon =
                                Math.abs(wx - WALL_X_WEST) % BATTLEMENT_PERIOD < BATTLEMENT_PERIOD / 2;
                        if (isMerlon) {
                            for (int h = 0; h < BATTLEMENT_HEIGHT; h++) {
                                chunk.setBlockState(
                                        new BlockPos(lx, merlonBase + h, lz),
                                        PACKED_ICE, 3);
                            }
                        }
                    }

                } else if (inDriftZone) {
                    
                    int driftTop = driftHeightAt(driftDist, baseY);
                    int terrainY = GotChunkGenerator.computeSurfaceY(wx, wz);
                    int fillFrom = Math.min(terrainY, baseY);
                    for (int y = fillFrom; y <= driftTop; y++) {
                        chunk.setBlockState(new BlockPos(lx, y, lz), SNOW, 3);
                    }

                } else if (inNorthDriftZone) {
                    
                    int driftTop = driftHeightAt(northDriftDist, baseY);
                    int terrainY = GotChunkGenerator.computeSurfaceY(wx, wz);
                    int fillFrom = Math.min(terrainY, baseY);
                    for (int y = fillFrom; y <= driftTop; y++) {
                        chunk.setBlockState(new BlockPos(lx, y, lz), SNOW, 3);
                    }
                }
            }
        }
    }

    public static boolean isPositionOnWall(int worldX, int worldZ) {
        int cz = wallCentreZ(worldX);
        if (cz == Integer.MIN_VALUE) return false;
        int dz = worldZ - cz;
        
        int maxNorth = HALF + NORTH_BATTER + (int) Math.ceil(NORTH_FACE_NOISE_AMPLITUDE) + SNOW_DRIFT_RADIUS;
        int maxSouth = HALF + WALL_BATTER + (int) Math.ceil(FACE_NOISE_AMPLITUDE) + BATTLEMENT_HEIGHT;
        return dz >= -maxNorth && dz <= maxSouth;
    }

    private WallWorldGen() {}
}