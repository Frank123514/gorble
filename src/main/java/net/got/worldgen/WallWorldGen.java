package net.got.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.ChunkPos;

/**
 * Generates The Wall — the 700-foot-tall barrier of ice that stretches
 * across the northern border of Westeros, near Castle Black.
 *
 * <h2>Shape</h2>
 * The wall follows a curved spine derived directly from the red marker drawn
 * on the biomemap at the boundary between {@code got:north} and
 * {@code got:always_winter}.  Each entry in {@link #WALL_SPINE} is a
 * (worldX, wallCentreZ) pair; the spine is sorted ascending by X so any
 * column can be looked up with a binary search.
 *
 * <p>For a given world X the centre Z of the wall is obtained by linearly
 * interpolating between the two nearest spine points.  The wall body then
 * extends {@link #HALF} blocks north and south of that centre Z.
 *
 * <h2>Structure</h2>
 * <ul>
 *   <li>Height: {@value #WALL_HEIGHT} blocks</li>
 *   <li>Thickness: {@value #WALL_THICKNESS} blocks (north–south, perpendicular
 *       to the local wall direction)</li>
 *   <li>Material: packed-ice core, blue-ice north/south faces</li>
 *   <li>Top: packed-ice walkway with snow interior</li>
 *   <li>Battlements: packed-ice merlons every {@value #BATTLEMENT_PERIOD} blocks
 *       on the south parapet</li>
 * </ul>
 *
 * <h2>Castle Black gate tunnel</h2>
 * A 3-wide × 5-tall passage is carved at {@link #CASTLE_BLACK_X}.
 */
public final class WallWorldGen {

    // ── Spine (worldX → wallCentreZ) ──────────────────────────────────────
    // Derived from the red-marker line on the biomemap (4207×3277 px,
    // MAP_SCALE=46, origin at image centre).  Points are sorted by X ascending.

    private static final int[][] WALL_SPINE = {
            {-66493, -39997},
            {-66447, -39997},
            {-66401, -40043},
            {-66355, -40043},
            {-66309, -40043},
            {-66263, -40089},
            {-66217, -40089},
            {-66171, -40089},
            {-66125, -40135},
            {-66079, -40135},
            {-66033, -40181},
            {-65987, -40181},
            {-65941, -40227},
            {-65895, -40273},
            {-65849, -40273},
            {-65803, -40273},
            {-65757, -40273},
            {-65711, -40273},
            {-65665, -40273},
            {-65619, -40319},
            {-65573, -40319},
            {-65527, -40319},
            {-65481, -40319},
            {-65435, -40319},
            {-65389, -40365},
            {-65343, -40365},
            {-65297, -40365},
            {-65251, -40365},
            {-65205, -40411},
            {-65159, -40411},
            {-65113, -40411},
            {-65067, -40411},
            {-65021, -40411},
            {-64975, -40457},
            {-64929, -40457},
            {-64883, -40457},
            {-64837, -40457},
            {-64791, -40457},
            {-64745, -40503},
            {-64699, -40503},
            {-64653, -40503},
            {-64607, -40503},
            {-64561, -40549},
            {-64515, -40549},
            {-64469, -40549},
            {-64423, -40549},
            {-64377, -40595},
            {-64331, -40595},
            {-64285, -40595},
            {-64239, -40641},
            {-64193, -40641},
            {-64147, -40687},
            {-64101, -40687},
            {-64055, -40687},
            {-64009, -40687},
            {-63963, -40687},
            {-63917, -40687},
            {-63871, -40733},
            {-63825, -40733},
            {-63779, -40733},
            {-63733, -40733},
            {-63687, -40733},
            {-63641, -40733},
            {-63595, -40733},
            {-63549, -40733},
            {-63503, -40733},
            {-63457, -40733},
            {-63411, -40733},
            {-63365, -40733},
            {-63319, -40733},
            {-63273, -40733},
            {-63227, -40733},
            {-63181, -40687},
            {-63135, -40687},
            {-63089, -40687},
            {-63043, -40687},
            {-62997, -40687},
            {-62951, -40687},
            {-62905, -40687},
            {-62859, -40687},
            {-62813, -40687},
            {-62767, -40687},
            {-62721, -40687},
            {-62675, -40687},
            {-62629, -40733},
            {-62583, -40733},
            {-62537, -40733},
            {-62491, -40733},
            {-62445, -40779},
            {-62399, -40779},
            {-62353, -40779},
            {-62307, -40779},
            {-62261, -40779},
            {-62215, -40779},
            {-62169, -40779},
            {-62123, -40779},
            {-62077, -40779},
            {-62031, -40779},
            {-61985, -40779},
            {-61939, -40779},
            {-61893, -40733},
            {-61847, -40733},
            {-61801, -40733},
            {-61755, -40733},
            {-61709, -40733},
            {-61663, -40733},
            {-61617, -40687},
            {-61571, -40687},
            {-61525, -40687},
            {-61479, -40687},
            {-61433, -40687},
            {-61387, -40687},
            {-61341, -40687},
            {-61295, -40687},
            {-61249, -40687},
            {-61203, -40687},
            {-61157, -40687},
            {-61111, -40687},
            {-61065, -40687},
            {-61019, -40733},
            {-60973, -40733},
            {-60927, -40733},
            {-60881, -40733},
            {-60835, -40733},
            {-60789, -40733},
            {-60743, -40733},
            {-60697, -40733},
            {-60651, -40733},
            {-60605, -40733},
            {-60559, -40733},
            {-60513, -40733},
            {-60467, -40733},
            {-60421, -40687},
            {-60375, -40687},
            {-60329, -40687},
            {-60283, -40687},
            {-60237, -40687},
            {-60191, -40687},
            {-60145, -40687},
            {-60099, -40733},
            {-60053, -40733},
            {-60007, -40733},
            {-59961, -40733},
            {-59915, -40733},
            {-59869, -40779},
            {-59823, -40779},
            {-59777, -40779},
            {-59731, -40779},
            {-59685, -40779},
            {-59639, -40825},
            {-59593, -40871},
            {-59547, -40871},
            {-59501, -40871},
            {-59455, -40871},
            {-59409, -40871},
            {-59363, -40871},
            {-59317, -40871},
            {-59271, -40871},
            {-59225, -40871},
            {-59179, -40917},
            {-59133, -40917},
            {-59087, -40917},
            {-59041, -40917},
            {-58995, -40917},
            {-58949, -40963},
            {-58903, -40963},
            {-58857, -40963},
            {-58811, -40963},
            {-58765, -40963},
            {-58719, -40963},
            {-58673, -40963},
            {-58627, -40963},
    };

    /** X of the westernmost spine point. */
    public static final int WALL_X_WEST = WALL_SPINE[0][0];
    /** X of the easternmost spine point. */
    public static final int WALL_X_EAST = WALL_SPINE[WALL_SPINE.length - 1][0];

    /** X coordinate of the Castle Black gate tunnel. */
    public static final int CASTLE_BLACK_X = -66_309;

    // ── Wall dimensions ────────────────────────────────────────────────────

    /** Height of The Wall in blocks (~700 ft). */
    public static final int WALL_HEIGHT = 200;

    /** North–south thickness in blocks (wide enough for a dozen men abreast). */
    public static final int WALL_THICKNESS = 12;

    private static final int HALF = WALL_THICKNESS / 2; // 6

    /** Battlement merlon spacing along the south parapet. */
    private static final int BATTLEMENT_PERIOD = 4;

    /** Height of a merlon above the walkway. */
    private static final int BATTLEMENT_HEIGHT = 2;

    /** Castle Black tunnel half-width (3 blocks total). */
    private static final int TUNNEL_HALF_WIDTH = 1;

    /** Castle Black tunnel height from surface (inclusive). */
    private static final int TUNNEL_HEIGHT = 5;

    // ── Block palette ──────────────────────────────────────────────────────

    private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.defaultBlockState();
    private static final BlockState BLUE_ICE   = Blocks.BLUE_ICE.defaultBlockState();
    private static final BlockState SNOW       = Blocks.SNOW_BLOCK.defaultBlockState();
    private static final BlockState AIR        = Blocks.AIR.defaultBlockState();

    // ── Spine lookup ──────────────────────────────────────────────────────

    /**
     * Returns the wall centre Z for the given world X by linearly interpolating
     * between the two nearest spine points.  Returns {@link Integer#MIN_VALUE}
     * if {@code worldX} is outside the spine's X range.
     */
    public static int wallCentreZ(int worldX) {
        if (worldX < WALL_X_WEST || worldX > WALL_X_EAST) return Integer.MIN_VALUE;

        // Binary search for the last spine point whose X <= worldX
        int lo = 0, hi = WALL_SPINE.length - 1;
        while (lo < hi) {
            int mid = (lo + hi + 1) >>> 1;
            if (WALL_SPINE[mid][0] <= worldX) lo = mid; else hi = mid - 1;
        }

        if (lo == WALL_SPINE.length - 1) return WALL_SPINE[lo][1];

        int x0 = WALL_SPINE[lo][0],     z0 = WALL_SPINE[lo][1];
        int x1 = WALL_SPINE[lo + 1][0], z1 = WALL_SPINE[lo + 1][1];
        if (x0 == x1) return z0;

        // Linear interpolation
        return (int) Math.round(z0 + (double)(z1 - z0) * (worldX - x0) / (x1 - x0));
    }

    // ── Entry point ───────────────────────────────────────────────────────

    /**
     * Called from {@link GotChunkGenerator#buildSurface}.
     * Generates the curved wall through any chunk that overlaps the spine's
     * X extent and whose Z range touches the wall footprint at any point.
     */
    public static void buildWallInChunk(ChunkAccess chunk) {
        ChunkPos cp       = chunk.getPos();
        int chunkMinX     = cp.getMinBlockX();
        int chunkMaxX     = chunkMinX + 15;
        int chunkMinZ     = cp.getMinBlockZ();
        int chunkMaxZ     = chunkMinZ + 15;

        // Quick X reject
        if (chunkMaxX < WALL_X_WEST || chunkMinX > WALL_X_EAST) return;

        // Quick Z reject: find the Z range the wall occupies across this chunk's X span
        int minCentreZ = Integer.MAX_VALUE, maxCentreZ = Integer.MIN_VALUE;
        for (int lx = 0; lx < 16; lx++) {
            int wx = chunkMinX + lx;
            if (wx < WALL_X_WEST || wx > WALL_X_EAST) continue;
            int cz = wallCentreZ(wx);
            if (cz < minCentreZ) minCentreZ = cz;
            if (cz > maxCentreZ) maxCentreZ = cz;
        }
        if (minCentreZ == Integer.MAX_VALUE) return;

        if (chunkMaxZ < minCentreZ - HALF || chunkMinZ > maxCentreZ + HALF + BATTLEMENT_HEIGHT) return;

        for (int lx = 0; lx < 16; lx++) {
            int wx = chunkMinX + lx;
            if (wx < WALL_X_WEST || wx > WALL_X_EAST) continue;

            int centreZ  = wallCentreZ(wx);
            boolean inTunnel = Math.abs(wx - CASTLE_BLACK_X) <= TUNNEL_HALF_WIDTH;

            // Base Y = minimum surface across the N–S thickness
            int baseY = Integer.MAX_VALUE;
            for (int dz = -HALF; dz <= HALF; dz++) {
                int sy = GotChunkGenerator.computeSurfaceY(wx, centreZ + dz);
                if (sy < baseY) baseY = sy;
            }

            for (int lz = 0; lz < 16; lz++) {
                int wz   = chunkMinZ + lz;
                int dz   = wz - centreZ;

                boolean inBody       = dz >= -HALF && dz <= HALF;
                boolean isBattlement = dz == HALF + 1;

                if (!inBody && !isBattlement) continue;

                boolean isSouthFace = inBody && (dz == HALF);
                boolean isNorthFace = inBody && (dz == -HALF);

                if (inBody) {
                    for (int y = baseY; y <= baseY + WALL_HEIGHT; y++) {
                        int relY = y - baseY;

                        // Tunnel carve
                        if (inTunnel && relY >= 0 && relY < TUNNEL_HEIGHT) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), AIR, false);
                            continue;
                        }

                        // Top walkway
                        if (relY == WALL_HEIGHT) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), PACKED_ICE, false);
                            if (!isSouthFace && !isNorthFace) {
                                chunk.setBlockState(new BlockPos(lx, y + 1, lz), SNOW, false);
                            }
                            continue;
                        }

                        // Faces vs core
                        if (isSouthFace || isNorthFace) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), BLUE_ICE, false);
                        } else {
                            chunk.setBlockState(new BlockPos(lx, y, lz), PACKED_ICE, false);
                        }
                    }

                } else {
                    // South battlement merlons
                    int merlonBase = baseY + WALL_HEIGHT + 1;
                    boolean isMerlon =
                            Math.abs(wx - WALL_X_WEST) % BATTLEMENT_PERIOD < BATTLEMENT_PERIOD / 2;
                    if (isMerlon) {
                        for (int h = 0; h < BATTLEMENT_HEIGHT; h++) {
                            chunk.setBlockState(
                                    new BlockPos(lx, merlonBase + h, lz),
                                    PACKED_ICE, false);
                        }
                    }
                }
            }
        }
    }

    // ── Utility ───────────────────────────────────────────────────────────

    /**
     * Returns {@code true} if (worldX, worldZ) falls within the wall's
     * footprint (including battlement row).  Useful for suppressing
     * vegetation and mob spawns on the structure.
     */
    public static boolean isPositionOnWall(int worldX, int worldZ) {
        int cz = wallCentreZ(worldX);
        if (cz == Integer.MIN_VALUE) return false;
        int dz = worldZ - cz;
        return dz >= -HALF && dz <= HALF + BATTLEMENT_HEIGHT;
    }

    private WallWorldGen() {}
}