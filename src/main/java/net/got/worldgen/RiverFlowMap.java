package net.got.worldgen;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Set;

/**
 * Computes a per-biomemap-pixel water flow direction for river biomes,
 * pointing from each river pixel toward the nearest connected ocean/lake —
 * i.e. downstream, from source to mouth.
 *
 * <h3>How it works</h3>
 * <p>A breadth-first flood fill starts from every river pixel that's
 * adjacent to a "sink" (ocean, deep ocean, lake, or frozen lake) and
 * spreads inland through connected river pixels only (4-directional),
 * recording each pixel's distance in pixels from the nearest sink. Once the
 * whole reachable river network has a distance value, each pixel's flow
 * direction is simply "which of its 8 neighbors is closer to a sink" — the
 * same logic water actually follows downhill.
 *
 * <p>River pixels that never reach a sink (an isolated pond painted with a
 * river color, or a river that dead-ends without reaching the coast) are
 * left with no flow direction and behave as plain still water.
 *
 * <h3>Resolution</h3>
 * <p>One biomemap pixel = {@link BiomemapLoader#MAP_SCALE} world blocks, so
 * flow direction is coarse — a broad "which way is downstream here" signal
 * for aesthetic current/particle effects, not a precise per-block vector
 * field. That matches the resolution the rest of the biome system already
 * operates at.
 *
 * <h3>Thread safety</h3>
 * <p>{@link #compute} runs off the main thread in
 * {@code MapReloadListener#prepare}, after {@link GotBiomeTerrainParams}
 * has already been applied (it needs {@code forColor} to resolve biome IDs).
 * {@link #apply} pushes the result to the volatile store on the main
 * thread. {@link #flowAt} is safe any time after {@link #apply} returns.
 *
 * <h3>Tributary support</h3>
 * <p>Alongside the downstream direction, this also tracks each river
 * pixel's distance-to-mouth ({@link #distanceAt}) and a "bank normal" —
 * the land-ward direction roughly perpendicular to the current
 * ({@link #bankNormalAt}) — for {@link RiverBranchPoints}, which picks
 * where tributary creeks should start and which way to grow.
 */
public final class RiverFlowMap {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Set<String> RIVER_BIOME_IDS = Set.of(
            "got:river", "got:neck_river", "got:frozen_river"
    );

    /** Where a river is considered to have "arrived" — its mouth. */
    private static final Set<String> SINK_BIOME_IDS = Set.of(
            "got:ocean", "got:deep_ocean", "got:lake", "got:frozen_lake"
    );

    // 8-directional offsets. Index 0 is reserved for "no flow".
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

    /** Normalized downstream direction at a world column. */
    public record FlowVector(float dx, float dz) {}

    public record Fields(byte[][] direction, byte[][] bankNormal, short[][] distance) {}

    // Volatile so writes from apply() are visible to reader threads.
    private static volatile byte[][]  directionField = null;
    private static volatile byte[][]  bankNormalField = null;
    private static volatile short[][] distanceField   = null;
    private static volatile int       fieldWidth      = 0;
    private static volatile int       fieldHeight      = 0;

    private RiverFlowMap() {}

    // ── Compute ────────────────────────────────────────────────────────────

    /**
     * Builds the flow-direction field from a biomemap pixel grid. Must be
     * called after {@link GotBiomeTerrainParams#apply} so that
     * {@link GotBiomeTerrainParams#forColor} resolves correctly — see
     * {@code MapReloadListener#prepare}, which already does this for
     * {@link MountainSlopemapResolver}.
     */
    public static Fields compute(int[][] pixels, int width, int height) {
        if (pixels == null || width == 0 || height == 0) {
            return new Fields(new byte[0][0], new byte[0][0], new short[0][0]);
        }

        boolean[][] isRiver = new boolean[width][height];
        boolean[][] isSink  = new boolean[width][height];

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                String id = GotBiomeTerrainParams.forColor(pixels[x][z]).biomeId();
                isRiver[x][z] = RIVER_BIOME_IDS.contains(id);
                isSink[x][z]  = SINK_BIOME_IDS.contains(id);
            }
        }

        short[][] distance = new short[width][height];
        for (short[] row : distance) Arrays.fill(row, (short) -1);

        int[] sdx = {1, -1, 0, 0};
        int[] sdz = {0, 0, 1, -1};

        ArrayDeque<int[]> queue = new ArrayDeque<>();

        // Seed: river pixels directly adjacent to a sink start at distance 0.
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

        // Flood fill distance-to-sink through the connected river network.
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

        // Direction: for every reachable river pixel, point toward whichever
        // 8-neighbor is closer to a sink — a sink itself always wins, else
        // the neighboring river pixel with the smaller distance.
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
                        score = -1; // reaching the sink always wins
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

        // Bank normal: for every river pixel with a flow direction, the
        // land-ward direction a tributary should start growing from — i.e.
        // roughly perpendicular to the current, like a real confluence,
        // rather than pointing straight up- or downstream. Prefers whichever
        // of the two perpendicular neighbors is actually land (not river,
        // not sink); falls back to any adjacent land neighbor; 0 if the
        // pixel has no land neighbor at all (mid-river / wide channel).
        byte[][] bankNormal = new byte[width][height];
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < height; z++) {
                int d = direction[x][z];
                if (d == 0) continue;

                // Directions are indexed 1-8 clockwise from north in steps of
                // 45°; ±2 steps = ±90° = perpendicular to the flow.
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

    // ── Apply ──────────────────────────────────────────────────────────────

    public static void apply(Fields fields, int width, int height) {
        directionField  = fields.direction();
        bankNormalField = fields.bankNormal();
        distanceField   = fields.distance();
        fieldWidth  = width;
        fieldHeight = height;
    }

    // ── Query ──────────────────────────────────────────────────────────────

    /**
     * Distance, in biomemap pixels, from this river column to the nearest
     * connected sink (ocean/lake). -1 if not part of a connected river.
     */
    public static int distanceAt(int worldX, int worldZ) {
        int[] px = worldToPixel(worldX, worldZ);
        short[][] field = distanceField;
        if (px == null || field == null) return -1;
        return field[px[0]][px[1]];
    }

    /**
     * The land-ward direction, roughly perpendicular to the current, that a
     * tributary should grow away from at this river column — see
     * {@link #compute}'s bank-normal pass. {@code null} if this isn't a
     * connected river column or has no adjacent land pixel.
     */
    public static FlowVector bankNormalAt(int worldX, int worldZ) {
        int[] px = worldToPixel(worldX, worldZ);
        byte[][] field = bankNormalField;
        if (px == null || field == null) return null;
        byte dir = field[px[0]][px[1]];
        if (dir == 0) return null;
        return new FlowVector(NORM_DX[dir], NORM_DZ[dir]);
    }

    /** Biomemap pixel width/height of the loaded field, for iterating the whole network. */
    public static int fieldWidth()  { return fieldWidth; }
    public static int fieldHeight() { return fieldHeight; }

    /** World-space center of the given biomemap pixel. */
    public static int pixelToWorldX(int px) {
        return Math.round((px - fieldWidth  * 0.5f) * BiomemapLoader.MAP_SCALE);
    }

    public static int pixelToWorldZ(int pz) {
        return Math.round((pz - fieldHeight * 0.5f) * BiomemapLoader.MAP_SCALE);
    }

    /** {x, z} pixel coordinates for a world column, or null if the field isn't loaded / out of range. */
    private static int[] worldToPixel(int worldX, int worldZ) {
        if (fieldWidth == 0 || fieldHeight == 0) return null;
        float cx = worldX / (float) BiomemapLoader.MAP_SCALE + fieldWidth  * 0.5f;
        float cz = worldZ / (float) BiomemapLoader.MAP_SCALE + fieldHeight * 0.5f;
        int px = (int) Math.floor(cx);
        int pz = (int) Math.floor(cz);
        if (px < 0 || pz < 0 || px >= fieldWidth || pz >= fieldHeight) return null;
        return new int[]{px, pz};
    }

    /**
     * Returns the downstream flow direction at the given world column, or
     * {@code null} if that column isn't part of a connected river network
     * (ocean, lake, dry land, or an unconnected river dead-end all return
     * {@code null} — meaning "no current here").
     */
    public static FlowVector flowAt(int worldX, int worldZ) {
        int[] px = worldToPixel(worldX, worldZ);
        byte[][] field = directionField;
        if (px == null || field == null) return null;

        byte dir = field[px[0]][px[1]];
        if (dir == 0) return null;

        return new FlowVector(NORM_DX[dir], NORM_DZ[dir]);
    }
}
