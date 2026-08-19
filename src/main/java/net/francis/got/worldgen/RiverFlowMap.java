package net.francis.got.worldgen;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Set;

public final class RiverFlowMap {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Set<String> RIVER_BIOME_IDS = Set.of(
            "got:river", "got:neck_river", "got:frozen_river"
    );

    private static final Set<String> SINK_BIOME_IDS = Set.of(
            "got:ocean", "got:deep_ocean", "got:lake", "got:frozen_lake"
    );

    private static final int[] DX = {0,  0, 1, 1, 1,  0, -1, -1, -1};
    private static final int[] DZ = {0, -1, -1, 0, 1,  1,  1,  0, -1};
    private static final float[] NORM_DX = new float[9];
    private static final float[] NORM_DZ = new float[9];
    static {
        for (int i = 1; i < 9; i++) {
            float len = (float) Math.sqrt(DX[i] * DX[i] + DZ[i] * DZ[i]);
            NORM_DX[i] = DX[i] / len;
            NORM_DZ[i] = DZ[i] / len;
        }
    }

    public record FlowVector(float dx, float dz) {}

    public record Fields(byte[][] direction, byte[][] bankNormal, short[][] distance) {}

    private static volatile byte[][]  directionField = null;
    private static volatile byte[][]  bankNormalField = null;
    private static volatile short[][] distanceField   = null;
    private static volatile int       fieldWidth      = 0;
    private static volatile int       fieldHeight      = 0;

    private RiverFlowMap() {}

    public static Fields compute(int[][] pixels, int width, int height) {
        if (pixels == null || width == 0 || height == 0) {
            return new Fields(new byte[0][0], new byte[0][0], new short[0][0]);
        }

        boolean[][] isRiver = new boolean[width][height];
        boolean[][] isSink  = new boolean[width][height];

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                String id = BiomeTerrainParams.forColor(pixels[x][z]).biomeId();
                isRiver[x][z] = RIVER_BIOME_IDS.contains(id);
                isSink[x][z]  = SINK_BIOME_IDS.contains(id);
            }
        }

        // BFS from every river pixel touching a sink (ocean/lake) to get distance-to-sink
        short[][] distance = new short[width][height];
        for (short[] row : distance) Arrays.fill(row, (short) -1);

        int[] sdx = {1, -1, 0, 0};
        int[] sdz = {0, 0, 1, -1};

        ArrayDeque<int[]> queue = new ArrayDeque<>();

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                if (!isRiver[x][z]) continue;
                for (int d = 0; d < 4; d++) {
                    int nx = x + sdx[d], nz = z + sdz[d];
                    if (nx < 0 || nz < 0 || nx >= width || nz >= height) continue;
                    if (isSink[nx][nz]) {
                        distance[x][z] = 0;
                        queue.add(new int[]{x, z});
                        break;
                    }
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cx = cur[0], cz = cur[1];
            short d = distance[cx][cz];
            for (int dir = 0; dir < 4; dir++) {
                int nx = cx + sdx[dir], nz = cz + sdz[dir];
                if (nx < 0 || nz < 0 || nx >= width || nz >= height) continue;
                if (!isRiver[nx][nz] || distance[nx][nz] != -1) continue;
                distance[nx][nz] = (short) (d + 1);
                queue.add(new int[]{nx, nz});
            }
        }

        // for each river pixel, flow toward whichever 8-neighbor is closest to a sink
        byte[][] direction = new byte[width][height];
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                if (!isRiver[x][z] || distance[x][z] < 0) continue;

                int bestDir   = 0;
                int bestScore = distance[x][z];
                for (int dir = 1; dir < 9; dir++) {
                    int nx = x + DX[dir], nz = z + DZ[dir];
                    if (nx < 0 || nz < 0 || nx >= width || nz >= height) continue;

                    int score;
                    if (isSink[nx][nz]) {
                        score = -1;
                    } else if (isRiver[nx][nz] && distance[nx][nz] >= 0) {
                        score = distance[nx][nz];
                    } else {
                        continue;
                    }
                    if (score < bestScore) {
                        bestScore = score;
                        bestDir = dir;
                    }
                }
                direction[x][z] = (byte) bestDir;
            }
        }

        // find the bank direction (roughly perpendicular to flow, pointing to nearest land)
        byte[][] bankNormal = new byte[width][height];
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                int d = direction[x][z];
                if (d == 0) continue;

                int perp1 = ((d - 1 + 2) % 8) + 1;
                int perp2 = ((d - 1 - 2 + 8) % 8) + 1;

                byte chosen = 0;
                if (isLandNeighbor(isRiver, isSink, x, z, perp1, width, height)) {
                    chosen = (byte) perp1;
                } else if (isLandNeighbor(isRiver, isSink, x, z, perp2, width, height)) {
                    chosen = (byte) perp2;
                } else {
                    for (int dir = 1; dir < 9; dir++) {
                        if (isLandNeighbor(isRiver, isSink, x, z, dir, width, height)) {
                            chosen = (byte) dir;
                            break;
                        }
                    }
                }
                bankNormal[x][z] = chosen;
            }
        }

        LOGGER.info("[GoT] Computed river flow map ({}x{})", width, height);
        return new Fields(direction, bankNormal, distance);
    }

    private static boolean isLandNeighbor(boolean[][] isRiver, boolean[][] isSink,
                                           int x, int z, int dir, int width, int height) {
        int nx = x + DX[dir], nz = z + DZ[dir];
        if (nx < 0 || nz < 0 || nx >= width || nz >= height) return false;
        return !isRiver[nx][nz] && !isSink[nx][nz];
    }

    public static void apply(Fields fields, int width, int height) {
        directionField  = fields.direction();
        bankNormalField = fields.bankNormal();
        distanceField   = fields.distance();
        fieldWidth  = width;
        fieldHeight = height;
    }

    public static int distanceAt(int worldX, int worldZ) {
        int[] px = worldToPixel(worldX, worldZ);
        short[][] field = distanceField;
        if (px == null || field == null) return -1;
        return field[px[0]][px[1]];
    }

    public static FlowVector bankNormalAt(int worldX, int worldZ) {
        int[] px = worldToPixel(worldX, worldZ);
        byte[][] field = bankNormalField;
        if (px == null || field == null) return null;
        byte dir = field[px[0]][px[1]];
        if (dir == 0) return null;
        return new FlowVector(NORM_DX[dir], NORM_DZ[dir]);
    }

    public static int fieldWidth()  { return fieldWidth; }
    public static int fieldHeight() { return fieldHeight; }

    public static int pixelToWorldX(int px) {
        return Math.round((px - fieldWidth  * 0.5f) * BiomemapLoader.MAP_SCALE);
    }

    public static int pixelToWorldZ(int pz) {
        return Math.round((pz - fieldHeight * 0.5f) * BiomemapLoader.MAP_SCALE);
    }

    // converts world block coords to biome-map pixel coords (map is centered on origin)
    private static int[] worldToPixel(int worldX, int worldZ) {
        if (fieldWidth == 0 || fieldHeight == 0) return null;
        float cx = worldX / (float) BiomemapLoader.MAP_SCALE + fieldWidth  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE + fieldHeight * 0.5f;
        int px = (int) Math.floor(cx);
        int pz = (int) Math.floor(cz);
        if (px < 0 || pz < 0 || px >= fieldWidth || pz >= fieldHeight) return null;
        return new int[]{px, pz};
    }

    public static FlowVector flowAt(int worldX, int worldZ) {
        int[] px = worldToPixel(worldX, worldZ);
        byte[][] field = directionField;
        if (px == null || field == null) return null;

        byte dir = field[px[0]][px[1]];
        if (dir == 0) return null;

        return new FlowVector(NORM_DX[dir], NORM_DZ[dir]);
    }
}
