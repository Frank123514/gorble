package net.got.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.ChunkPos;
import org.joml.SimplexNoise;

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
 *   <li>Batter: {@value #WALL_BATTER} blocks — the south face leans outward at
 *       the base and tapers back to the wall centre at the top, giving the
 *       classic medieval "battered" profile.  Noise is layered on top so the
 *       face is not a perfect geometric slope.</li>
 *   <li>Snow drift: a {@value #SNOW_DRIFT_RADIUS}-block snow apron is built up
 *       against the south face so the wall grows organically out of the
 *       landscape rather than starting with a sharp 90-degree edge.</li>
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
    // MAP_SCALE=50, origin at image centre).  Points are sorted by X ascending.
    // (Re-scaled 2026-07-05 from the original MAP_SCALE=46 coordinate set.)

    private static final int[][] WALL_SPINE = {
            {-72275, -43475},
            {-72225, -43475},
            {-72175, -43525},
            {-72125, -43525},
            {-72075, -43525},
            {-72025, -43575},
            {-71975, -43575},
            {-71925, -43575},
            {-71875, -43625},
            {-71825, -43625},
            {-71775, -43675},
            {-71725, -43675},
            {-71675, -43725},
            {-71625, -43775},
            {-71575, -43775},
            {-71525, -43775},
            {-71475, -43775},
            {-71425, -43775},
            {-71375, -43775},
            {-71325, -43825},
            {-71275, -43825},
            {-71225, -43825},
            {-71175, -43825},
            {-71125, -43825},
            {-71075, -43875},
            {-71025, -43875},
            {-70975, -43875},
            {-70925, -43875},
            {-70875, -43925},
            {-70825, -43925},
            {-70775, -43925},
            {-70725, -43925},
            {-70675, -43925},
            {-70625, -43975},
            {-70575, -43975},
            {-70525, -43975},
            {-70475, -43975},
            {-70425, -43975},
            {-70375, -44025},
            {-70325, -44025},
            {-70275, -44025},
            {-70225, -44025},
            {-70175, -44075},
            {-70125, -44075},
            {-70075, -44075},
            {-70025, -44075},
            {-69975, -44125},
            {-69925, -44125},
            {-69875, -44125},
            {-69825, -44175},
            {-69775, -44175},
            {-69725, -44225},
            {-69675, -44225},
            {-69625, -44225},
            {-69575, -44225},
            {-69525, -44225},
            {-69475, -44225},
            {-69425, -44275},
            {-69375, -44275},
            {-69325, -44275},
            {-69275, -44275},
            {-69225, -44275},
            {-69175, -44275},
            {-69125, -44275},
            {-69075, -44275},
            {-69025, -44275},
            {-68975, -44275},
            {-68925, -44275},
            {-68875, -44275},
            {-68825, -44275},
            {-68775, -44275},
            {-68725, -44275},
            {-68675, -44225},
            {-68625, -44225},
            {-68575, -44225},
            {-68525, -44225},
            {-68475, -44225},
            {-68425, -44225},
            {-68375, -44225},
            {-68325, -44225},
            {-68275, -44225},
            {-68225, -44225},
            {-68175, -44225},
            {-68125, -44225},
            {-68075, -44275},
            {-68025, -44275},
            {-67975, -44275},
            {-67925, -44275},
            {-67875, -44325},
            {-67825, -44325},
            {-67775, -44325},
            {-67725, -44325},
            {-67675, -44325},
            {-67625, -44325},
            {-67575, -44325},
            {-67525, -44325},
            {-67475, -44325},
            {-67425, -44325},
            {-67375, -44325},
            {-67325, -44325},
            {-67275, -44275},
            {-67225, -44275},
            {-67175, -44275},
            {-67125, -44275},
            {-67075, -44275},
            {-67025, -44275},
            {-66975, -44225},
            {-66925, -44225},
            {-66875, -44225},
            {-66825, -44225},
            {-66775, -44225},
            {-66725, -44225},
            {-66675, -44225},
            {-66625, -44225},
            {-66575, -44225},
            {-66525, -44225},
            {-66475, -44225},
            {-66425, -44225},
            {-66375, -44225},
            {-66325, -44275},
            {-66275, -44275},
            {-66225, -44275},
            {-66175, -44275},
            {-66125, -44275},
            {-66075, -44275},
            {-66025, -44275},
            {-65975, -44275},
            {-65925, -44275},
            {-65875, -44275},
            {-65825, -44275},
            {-65775, -44275},
            {-65725, -44275},
            {-65675, -44225},
            {-65625, -44225},
            {-65575, -44225},
            {-65525, -44225},
            {-65475, -44225},
            {-65425, -44225},
            {-65375, -44225},
            {-65325, -44275},
            {-65275, -44275},
            {-65225, -44275},
            {-65175, -44275},
            {-65125, -44275},
            {-65075, -44325},
            {-65025, -44325},
            {-64975, -44325},
            {-64925, -44325},
            {-64875, -44325},
            {-64825, -44375},
            {-64775, -44425},
            {-64725, -44425},
            {-64675, -44425},
            {-64625, -44425},
            {-64575, -44425},
            {-64525, -44425},
            {-64475, -44425},
            {-64425, -44425},
            {-64375, -44425},
            {-64325, -44475},
            {-64275, -44475},
            {-64225, -44475},
            {-64175, -44475},
            {-64125, -44475},
            {-64075, -44525},
            {-64025, -44525},
            {-63975, -44525},
            {-63925, -44525},
            {-63875, -44525},
            {-63825, -44525},
            {-63775, -44525},
            {-63725, -44525},
    };

    /** X of the westernmost spine point. */
    public static final int WALL_X_WEST = WALL_SPINE[0][0];
    /** X of the easternmost spine point. */
    public static final int WALL_X_EAST = WALL_SPINE[WALL_SPINE.length - 1][0];

    /** X coordinate of the Castle Black gate tunnel. */
    public static final int CASTLE_BLACK_X = -67587;

    // ── Wall dimensions ────────────────────────────────────────────────────

    /** Height of The Wall in blocks (~700 ft). */
    public static final int WALL_HEIGHT = 200;

    /** North–south thickness in blocks (wide enough for a dozen men abreast). */
    public static final int WALL_THICKNESS = 24;

    private static final int HALF = WALL_THICKNESS / 2; // 12

    /**
     * How many blocks the south face is pushed outward at the base relative to
     * the top.  Gives the wall the classic medieval "battered" profile —
     * slightly wider and more massive-looking at the foot than at the parapet.
     * The actual outward offset at height {@code relY} is:
     * <pre>
     *   southOffset(relY) = WALL_BATTER * (1 - relY / WALL_HEIGHT)
     * </pre>
     * so it is {@code WALL_BATTER} at the base and 0 at the top.
     */
    private static final int WALL_BATTER = 20;

    /**
     * How many blocks the north face is pushed outward at the base relative to
     * the top.  Mirrors the south batter for a symmetrical profile.
     */
    private static final int NORTH_BATTER = WALL_BATTER;

    /**
     * Noise amplitude (in blocks) applied to the south face surface.
     * Creates subtle undulations so the face is not a perfect geometric plane.
     * Noise is sampled at two octaves (large scale + detail).
     */
    private static final double FACE_NOISE_AMPLITUDE = 3.5;

    /**
     * Noise amplitude (in blocks) applied to the north face surface.
     * Mirrors the south face noise for symmetrical undulations.
     */
    private static final double NORTH_FACE_NOISE_AMPLITUDE = FACE_NOISE_AMPLITUDE;

    /**
     * How many blocks of snow are built up as a drift against the south
     * base of the wall.  At distance {@code d} (0 = wall base, positive =
     * further south), the drift fills from the terrain surface up to:
     * <pre>
     *   driftTop(d) = baseY + SNOW_DRIFT_HEIGHT * max(0, 1 - d / SNOW_DRIFT_RADIUS)
     * </pre>
     * This creates an organic snow apron so the wall doesn't start with a
     * knife-edge at ground level.
     */
    private static final int SNOW_DRIFT_RADIUS = 20;
    private static final int SNOW_DRIFT_HEIGHT = 12;

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
     * Returns the wall centre Z for the given world X using a uniform cubic
     * B-spline evaluated over the four nearest spine control points.
     * Returns {@link Integer#MIN_VALUE} if {@code worldX} is outside the
     * spine's X range.
     */
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

    // ── Face profile helpers ───────────────────────────────────────────────

    /**
     * Returns the linear batter offset in blocks at the given relative height.
     * Positive = outward (south), so the face leans outward toward the base.
     */
    private static double batterAt(int relY) {
        return WALL_BATTER * (1.0 - (double) relY / WALL_HEIGHT);
    }

    /**
     * Returns the linear north batter offset in blocks at the given relative height.
     * Positive = outward (north), so the face leans outward toward the base.
     */
    private static double northBatterAt(int relY) {
        return NORTH_BATTER * (1.0 - (double) relY / WALL_HEIGHT);
    }

    /**
     * Returns the noisy south-face edge offset (from centreZ) for a given
     * world (X, Y) position.  Two noise octaves add large-scale undulations
     * and fine-scale chipping.
     *
     * <p>The noise is intentionally independent of Z so that at a given X,Y
     * the face protrudes uniformly north/south — it appears as bumps and
     * recesses when viewed head-on, not as diagonal streaks.
     */
    private static double faceNoise(int worldX, int relY) {
        // Large undulations (period ~40 blocks in X, ~60 in Y)
        double n1 = SimplexNoise.noise((float) (worldX / 40.0), (float) (relY / 60.0));
        // Fine chipping detail (period ~12 blocks in X, ~18 in Y)
        double n2 = SimplexNoise.noise((float) (worldX / 12.0 + 31.7), (float) (relY / 18.0 + 17.3));
        // Blend: 70% large, 30% fine.  Returns value in [-1, 1].
        return n1 * 0.7 + n2 * 0.3;
    }

    /**
     * Returns the noisy north-face edge offset (from centreZ) for a given
     * world (X, Y) position.  Mirrors the south face noise.
     */
    private static double northFaceNoise(int worldX, int relY) {
        // Use different offsets for independent north/south noise patterns
        double n1 = SimplexNoise.noise((float) (worldX / 40.0 + 50.0), (float) (relY / 60.0 + 50.0));
        double n2 = SimplexNoise.noise((float) (worldX / 12.0 + 81.7), (float) (relY / 18.0 + 67.3));
        return n1 * 0.7 + n2 * 0.3;
    }

    /**
     * Returns the effective south edge of the wall (offset from centreZ)
     * at the given world X and relative height.  Combines the linear batter
     * with surface noise.
     */
    private static double southEdgeAt(int worldX, int relY) {
        return HALF + batterAt(relY) + faceNoise(worldX, relY) * FACE_NOISE_AMPLITUDE;
    }

    /**
     * Returns the effective north edge of the wall (offset from centreZ)
     * at the given world X and relative height.  Negative values indicate
     * positions north of centre.  Combines the linear batter with surface noise.
     */
    private static double northEdgeAt(int worldX, int relY) {
        return -(HALF + northBatterAt(relY) + northFaceNoise(worldX, relY) * NORTH_FACE_NOISE_AMPLITUDE);
    }

    /**
     * Returns the height of the snow drift at distance {@code driftDist}
     * blocks south of the wall base.  Zero when outside the drift radius.
     */
    private static int driftHeightAt(int driftDist, int baseY) {
        if (driftDist < 0 || driftDist > SNOW_DRIFT_RADIUS) return 0;
        double fraction = 1.0 - (double) driftDist / SNOW_DRIFT_RADIUS;
        return baseY + (int) Math.round(SNOW_DRIFT_HEIGHT * fraction * fraction);
    }

    // ── Entry point ───────────────────────────────────────────────────────

    /**
     * Called from {@link GotChunkGenerator#buildSurface}.
     * Generates the curved wall (including batter, face noise, and snow drift)
     * through any chunk that overlaps the spine's X extent and whose Z range
     * touches the wall footprint at any point.
     */
    public static void buildWallInChunk(ChunkAccess chunk) {
        ChunkPos cp       = chunk.getPos();
        int chunkMinX     = cp.getMinBlockX();
        int chunkMaxX     = chunkMinX + 15;
        int chunkMinZ     = cp.getMinBlockZ();
        int chunkMaxZ     = chunkMinZ + 15;

        // Quick X reject
        if (chunkMaxX < WALL_X_WEST || chunkMinX > WALL_X_EAST) {
            return;
        }

        // Quick Z reject: find the Z range the wall + drift occupies across this chunk's X span
        int minCentreZ = Integer.MAX_VALUE, maxCentreZ = Integer.MIN_VALUE;
        for (int lx = 0; lx < 16; lx++) {
            int wx = chunkMinX + lx;
            if (wx < WALL_X_WEST || wx > WALL_X_EAST) continue;
            int cz = wallCentreZ(wx);
            if (cz < minCentreZ) minCentreZ = cz;
            if (cz > maxCentreZ) maxCentreZ = cz;
        }
        if (minCentreZ == Integer.MAX_VALUE) return;

        // Worst-case south/north extent = HALF + WALL_BATTER + face noise amplitude + drift radius
        int maxSouthReach = HALF + WALL_BATTER + (int) Math.ceil(FACE_NOISE_AMPLITUDE) + SNOW_DRIFT_RADIUS;
        int maxNorthReach = HALF + NORTH_BATTER + (int) Math.ceil(NORTH_FACE_NOISE_AMPLITUDE) + SNOW_DRIFT_RADIUS;
        if (chunkMaxZ < minCentreZ - maxNorthReach || chunkMinZ > maxCentreZ + maxSouthReach + BATTLEMENT_HEIGHT) {
            return;
        }

        for (int lx = 0; lx < 16; lx++) {
            int wx = chunkMinX + lx;
            if (wx < WALL_X_WEST || wx > WALL_X_EAST) continue;

            int centreZ  = wallCentreZ(wx);
            boolean inTunnel = Math.abs(wx - CASTLE_BLACK_X) <= TUNNEL_HALF_WIDTH;

            // Base Y = minimum surface across the N–S thickness (use a slightly
            // wider sample to account for the batter footprint at ground level)
            int baseY = Integer.MAX_VALUE;
            int groundSampleHalf = HALF + WALL_BATTER;
            for (int dz = -groundSampleHalf; dz <= groundSampleHalf; dz++) {
                int sy = GotChunkGenerator.computeSurfaceY(wx, centreZ + dz);
                if (sy < baseY) baseY = sy;
            }

            for (int lz = 0; lz < 16; lz++) {
                int wz = chunkMinZ + lz;
                int dz = wz - centreZ;

                // ── North battered half ───────────────────────────────────
                // The north face is now battered with noise, mirroring the south side.
                // We need to process this Z column if it could possibly be inside
                // the wall at any height.
                boolean couldBeNorth = dz >= -HALF - NORTH_BATTER - (int) Math.ceil(NORTH_FACE_NOISE_AMPLITUDE) && dz < 0;

                // ── South battered half ───────────────────────────────────
                // The south face is determined per-Y; we only need to process
                // this Z column if it could possibly be inside the wall at
                // any height.
                boolean couldBeSouth = dz >= 0 && dz <= HALF + WALL_BATTER + (int) Math.ceil(FACE_NOISE_AMPLITUDE);

                // ── Snow drift (south) ────────────────────────────────────
                // The drift extends further south than the wall batter.
                int driftDist = dz - HALF; // positive = south of south-face wall edge
                boolean inDriftZone = driftDist > 0 && driftDist <= SNOW_DRIFT_RADIUS;

                // ── Snow drift (north) ────────────────────────────────────
                // Mirrors the south drift on the north side.
                int northDriftDist = -HALF - dz; // positive = north of north-face wall edge
                boolean inNorthDriftZone = northDriftDist > 0 && northDriftDist <= SNOW_DRIFT_RADIUS;

                if (!couldBeNorth && !couldBeSouth && !inDriftZone && !inNorthDriftZone) continue;

                // ── Per-Y placement ───────────────────────────────────────
                if (couldBeNorth) {
                    // North side: battered — check each Y to see if this Z is inside
                    for (int y = baseY; y <= baseY + WALL_HEIGHT; y++) {
                        int relY = y - baseY;
                        double northEdge = northEdgeAt(wx, relY);
                        boolean insideBody = dz <= 0 && dz >= northEdge;
                        if (!insideBody) continue;

                        if (inTunnel && relY >= 0 && relY < TUNNEL_HEIGHT) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), AIR, false);
                            continue;
                        }

                        boolean isNorthFace = dz <= northEdge + 1.0; // outermost layer

                        if (relY == WALL_HEIGHT) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), PACKED_ICE, false);
                            if (!isNorthFace) {
                                chunk.setBlockState(new BlockPos(lx, y + 1, lz), SNOW, false);
                            }
                            continue;
                        }

                        chunk.setBlockState(new BlockPos(lx, y, lz),
                                isNorthFace ? BLUE_ICE : PACKED_ICE, false);
                    }

                    // North battlement merlons (placed at top of north face column,
                    // one block outside the wall face at the parapet level)
                    double topEdge = northEdgeAt(wx, WALL_HEIGHT);
                    if (dz < topEdge && dz >= topEdge - 1) {
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

                } else if (couldBeSouth) {
                    // South side: battered — check each Y to see if this Z is inside
                    for (int y = baseY; y <= baseY + WALL_HEIGHT; y++) {
                        int relY = y - baseY;
                        double southEdge = southEdgeAt(wx, relY);
                        boolean insideBody = dz >= 0 && dz <= southEdge;
                        if (!insideBody) continue;

                        if (inTunnel && relY >= 0 && relY < TUNNEL_HEIGHT) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), AIR, false);
                            continue;
                        }

                        boolean isSouthFace = dz >= southEdge - 1.0; // outermost layer

                        if (relY == WALL_HEIGHT) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), PACKED_ICE, false);
                            if (!isSouthFace) {
                                chunk.setBlockState(new BlockPos(lx, y + 1, lz), SNOW, false);
                            }
                            continue;
                        }

                        chunk.setBlockState(new BlockPos(lx, y, lz),
                                isSouthFace ? BLUE_ICE : PACKED_ICE, false);
                    }

                    // South battlement merlons (placed at top of south face column,
                    // one block outside the wall face at the parapet level)
                    double topEdge = southEdgeAt(wx, WALL_HEIGHT);
                    if (dz > topEdge && dz <= topEdge + 1) {
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

                } else if (inDriftZone) {
                    // Snow drift against south base — fills up to a parabolic height
                    int driftTop = driftHeightAt(driftDist, baseY);
                    int terrainY = GotChunkGenerator.computeSurfaceY(wx, wz);
                    int fillFrom = Math.min(terrainY, baseY);
                    for (int y = fillFrom; y <= driftTop; y++) {
                        chunk.setBlockState(new BlockPos(lx, y, lz), SNOW, false);
                    }

                } else if (inNorthDriftZone) {
                    // Snow drift against north base — mirrors the south drift
                    int driftTop = driftHeightAt(northDriftDist, baseY);
                    int terrainY = GotChunkGenerator.computeSurfaceY(wx, wz);
                    int fillFrom = Math.min(terrainY, baseY);
                    for (int y = fillFrom; y <= driftTop; y++) {
                        chunk.setBlockState(new BlockPos(lx, y, lz), SNOW, false);
                    }
                }
            }
        }
    }

    // ── Utility ───────────────────────────────────────────────────────────

    /**
     * Returns {@code true} if (worldX, worldZ) falls within the wall's
     * footprint (including batter, battlement row, and snow drift on both sides).
     * Useful for suppressing vegetation and mob spawns on the structure.
     */
    public static boolean isPositionOnWall(int worldX, int worldZ) {
        int cz = wallCentreZ(worldX);
        if (cz == Integer.MIN_VALUE) return false;
        int dz = worldZ - cz;
        // Conservative check: max possible north extent to max possible south extent
        int maxNorth = HALF + NORTH_BATTER + (int) Math.ceil(NORTH_FACE_NOISE_AMPLITUDE) + SNOW_DRIFT_RADIUS;
        int maxSouth = HALF + WALL_BATTER + (int) Math.ceil(FACE_NOISE_AMPLITUDE) + BATTLEMENT_HEIGHT;
        return dz >= -maxNorth && dz <= maxSouth;
    }

    private WallWorldGen() {}
}