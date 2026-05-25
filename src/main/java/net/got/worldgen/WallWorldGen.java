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
 * <h2>Coordinate derivation</h2>
 * <p>The biomemap image is 4207×3277 pixels.
 * {@code MAP_SCALE = 46} blocks per pixel, with world origin at the image centre:
 * <pre>
 *   blockX = round(pixelX * 46 − 193522 / 2)
 *   blockZ = round(pixelY * 46 − 150742 / 2)
 * </pre>
 *
 * <p>The Wall runs along the southern edge of the {@code got:always_winter} biome
 * (pixel Y ≈ 729–782, pixel X ≈ 400–885), giving world-space block coordinates:
 * <pre>
 *   Z centre  ≈ −40 641    (pixel Y ≈ 755)
 *   X west    ≈ −78 361    (pixel X ≈  400)
 *   X east    ≈ −56 051    (pixel X ≈  885)
 * </pre>
 *
 * <p>Castle Black sits approximately at the centre of The Wall, slightly to the
 * east, at block (−66 309, ?, −40 641).
 *
 * <h2>Structure</h2>
 * <ul>
 *   <li>Base: rests on the natural terrain surface</li>
 *   <li>Height: {@value #WALL_HEIGHT} blocks (≈ 700 ft / 213 m, roughly 1:1 scale
 *       at Minecraft's 1 block = 1 metre)</li>
 *   <li>Thickness: {@value #WALL_THICKNESS} blocks north–south</li>
 *   <li>Material: packed ice core with blue ice exterior face</li>
 *   <li>Top walkway: 3-block-wide flat path of packed ice</li>
 *   <li>Battlements: every 4 blocks along the southern parapet</li>
 * </ul>
 *
 * <h2>Castle Black gate tunnel</h2>
 * A 3-wide × 5-tall passage is carved through The Wall at the Castle Black
 * X position so players can pass through.
 */
public final class WallWorldGen {

    // ── World-space coordinates (derived from biomemap pixel analysis) ────

    /**
     * World Z coordinate for the centre of The Wall.
     * Derived from pixel Y ≈ 755 on the 4207×3277 biomemap.
     */
    public static final int WALL_Z = -40_641;

    /** Westernmost block X of The Wall (pixel X ≈ 400). */
    public static final int WALL_X_WEST = -78_361;

    /** Easternmost block X of The Wall (pixel X ≈ 885). */
    public static final int WALL_X_EAST = -56_051;

    /**
     * X coordinate of the Castle Black gate tunnel through The Wall.
     * Approximately the east-of-centre point (pixel X ≈ 642).
     */
    public static final int CASTLE_BLACK_X = -66_309;

    // ── Wall dimensions ────────────────────────────────────────────────────

    /**
     * Height of The Wall in blocks.
     * The Wall is described as ~700 feet tall; at 1 block ≈ 1 metre that is
     * ~213 blocks. We use 200 to stay comfortably within the Minecraft world
     * height limit while still being imposing.
     */
    public static final int WALL_HEIGHT = 200;

    /**
     * North–south thickness of The Wall in blocks.
     * The Wall is described as wide enough for a dozen men to ride abreast;
     * 12 blocks captures this.
     */
    public static final int WALL_THICKNESS = 12;

    /** Half-thickness: how far north and south of WALL_Z the wall extends. */
    private static final int HALF = WALL_THICKNESS / 2; // 6

    /** Width of the top walkway inset from each face (in blocks). */
    private static final int WALKWAY_INSET = 1;

    /** Battlement spacing along the parapet (every N blocks). */
    private static final int BATTLEMENT_PERIOD = 4;

    /** Height of a battlement merlon above the walkway surface. */
    private static final int BATTLEMENT_HEIGHT = 2;

    /** Castle Black tunnel width (centred on CASTLE_BLACK_X). */
    private static final int TUNNEL_HALF_WIDTH = 1; // 3 blocks total

    /** Castle Black tunnel height (from surface up). */
    private static final int TUNNEL_HEIGHT = 5;

    // ── Block palette ──────────────────────────────────────────────────────

    /** Core fill: packed ice. */
    private static final BlockState PACKED_ICE   = Blocks.PACKED_ICE.defaultBlockState();
    /** Outer face: blue ice for the shimmering cold look. */
    private static final BlockState BLUE_ICE     = Blocks.BLUE_ICE.defaultBlockState();
    /** Top walkway and battlements. */
    private static final BlockState WALKWAY      = Blocks.PACKED_ICE.defaultBlockState();
    /** Air for the tunnel passage. */
    private static final BlockState AIR          = Blocks.AIR.defaultBlockState();
    /** Snow layer on top of the walkway. */
    private static final BlockState SNOW         = Blocks.SNOW_BLOCK.defaultBlockState();

    // ── Entry point ───────────────────────────────────────────────────────

    /**
     * Called from {@link GotChunkGenerator#buildSurface} after terrain and
     * roads have been placed.  Generates The Wall across any chunk whose
     * X range overlaps the wall's east–west extent and whose Z range
     * overlaps the wall's north–south footprint.
     *
     * @param chunk the chunk being generated
     */
    public static void buildWallInChunk(ChunkAccess chunk) {
        ChunkPos cp       = chunk.getPos();
        int chunkMinX     = cp.getMinBlockX();
        int chunkMaxX     = chunkMinX + 15;
        int chunkMinZ     = cp.getMinBlockZ();
        int chunkMaxZ     = chunkMinZ + 15;

        // Quick reject: does this chunk overlap the wall's footprint?
        int wallMinZ = WALL_Z - HALF - BATTLEMENT_HEIGHT - 1;
        int wallMaxZ = WALL_Z + HALF + BATTLEMENT_HEIGHT + 1;

        if (chunkMaxX < WALL_X_WEST || chunkMinX > WALL_X_EAST) return;
        if (chunkMaxZ < wallMinZ    || chunkMinZ > wallMaxZ)     return;

        for (int lx = 0; lx < 16; lx++) {
            int wx = chunkMinX + lx;

            // Outside the wall's east–west span?
            if (wx < WALL_X_WEST || wx > WALL_X_EAST) continue;

            // Is this the Castle Black gate column?
            boolean inTunnel = Math.abs(wx - CASTLE_BLACK_X) <= TUNNEL_HALF_WIDTH;

            int surfaceY = GotChunkGenerator.computeSurfaceY(wx, WALL_Z);

            for (int lz = 0; lz < 16; lz++) {
                int wz = chunkMinZ + lz;

                // Inside the wall body?
                int dz = wz - WALL_Z; // negative = north, positive = south
                boolean inBody = dz >= -HALF && dz <= HALF;

                if (!inBody) continue;

                // Determine face vs core
                boolean isSouthFace = (dz == HALF);
                boolean isNorthFace = (dz == -HALF);

                for (int y = surfaceY; y <= surfaceY + WALL_HEIGHT; y++) {
                    int relY = y - surfaceY; // 0 = ground level, WALL_HEIGHT = top

                    // Tunnel passage at Castle Black gate
                    if (inTunnel && relY > 0 && relY <= TUNNEL_HEIGHT) {
                        chunk.setBlockState(new BlockPos(lx, y, lz), AIR, false);
                        continue;
                    }

                    // Top surface: walkway + battlements
                    if (relY == WALL_HEIGHT) {
                        chunk.setBlockState(new BlockPos(lx, y, lz), WALKWAY, false);
                        // Snow cap on the walkway interior
                        if (!isSouthFace && !isNorthFace) {
                            chunk.setBlockState(new BlockPos(lx, y + 1, lz), SNOW, false);
                        }
                        continue;
                    }

                    // Battlement merlons on the south parapet (the walkable face)
                    if (isSouthFace && relY > WALL_HEIGHT && relY <= WALL_HEIGHT + BATTLEMENT_HEIGHT) {
                        // Place merlon every BATTLEMENT_PERIOD blocks
                        if (Math.abs(wx - WALL_X_WEST) % BATTLEMENT_PERIOD < BATTLEMENT_PERIOD / 2) {
                            chunk.setBlockState(new BlockPos(lx, y, lz), PACKED_ICE, false);
                        }
                        continue;
                    }

                    // Outer (south) face: blue ice
                    if (isSouthFace || isNorthFace) {
                        chunk.setBlockState(new BlockPos(lx, y, lz), BLUE_ICE, false);
                    } else {
                        // Interior: packed ice
                        chunk.setBlockState(new BlockPos(lx, y, lz), PACKED_ICE, false);
                    }
                }
            }
        }
    }

    // ── Utility: is a world position inside the wall? ────────────────────

    /**
     * Returns {@code true} if the given world position falls within the
     * north–south footprint of The Wall.  Useful for suppressing vegetation
     * and mob spawns on and around the structure.
     *
     * @param worldX block X coordinate
     * @param worldZ block Z coordinate
     * @return true if (worldX, worldZ) is inside the wall's horizontal bounds
     */
    public static boolean isPositionOnWall(int worldX, int worldZ) {
        if (worldX < WALL_X_WEST || worldX > WALL_X_EAST) return false;
        int dz = worldZ - WALL_Z;
        return dz >= -HALF && dz <= HALF;
    }

    private WallWorldGen() {}
}