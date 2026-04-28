package net.got.worldgen.surface;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/**
 * Per-biome surface configuration — a port of LOTR Renewed's
 * {@code MiddleEarthSurfaceConfig}.
 *
 * <p>Each biome registers one instance in {@link GotBiomeSurfaces}.  During
 * chunk generation, {@link net.got.worldgen.GotChunkGenerator#buildSurface}
 * fetches the config for each column's biome and applies it column-by-column
 * after the vanilla surface pass.
 *
 * <h3>Features</h3>
 * <ul>
 *   <li><b>SurfaceNoiseMixer</b> — replaces top / filler blocks with noise-driven
 *       variants (gravel patches, sandy strips, rocky outcrops, etc.).</li>
 *   <li><b>SurfaceNoisePaths</b> — generates winding dirt-path trails along
 *       noise contour edges.</li>
 *   <li><b>UnderwaterNoiseMixer</b> — controls the submerged floor block (sand
 *       vs. gravel) via latitude noise.</li>
 *   <li><b>MountainTerrainProvider</b> — applies height-conditional block layers
 *       (snow above 150, stone above 120, etc.).</li>
 *   <li><b>SubSoilLayers</b> — ordered list of blocks placed beneath the filler
 *       layer (chalk, clay seams, sandstone bands, etc.).</li>
 * </ul>
 *
 * <h3>Applying order (matches LOTR's surface builder)</h3>
 * <ol>
 *   <li>Mountain terrain (highest priority — overrides everything else above the
 *       threshold).</li>
 *   <li>Surface noise mixer (replaces whatever vanilla or mountain terrain left).</li>
 *   <li>Surface noise paths (on top surface only, after mixer).</li>
 *   <li>Sub-soil layers (below the filler, depth-ordered).</li>
 *   <li>Underwater noise mixer (floor of flooded columns only).</li>
 * </ol>
 */
public final class GotBiomeSurfaceConfig {

    // ── Sub-soil layer ────────────────────────────────────────────────────

    /**
     * A geological stratum placed below the filler layer at a configurable depth.
     * Layers are applied in the order they were added; the first layer at the
     * current depth wins.
     */
    public record SubSoilLayer(BlockState material, int minDepth, int maxDepth) {
        /** Returns a depth in [minDepth, maxDepth] chosen randomly. */
        public int getDepth(RandomSource rand) {
            return minDepth == maxDepth ? minDepth
                    : minDepth + rand.nextInt(maxDepth - minDepth + 1);
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────

    private GotSurfaceNoiseMixer       surfaceNoiseMixer   = GotSurfaceNoiseMixer.NONE;
    private boolean                    hasSurfaceNoisePaths = false;
    private GotUnderwaterNoiseMixer    underwaterNoiseMixer = GotUnderwaterNoiseMixer.NONE;
    private GotMountainTerrainProvider mountainTerrain      = GotMountainTerrainProvider.NONE;
    private final List<SubSoilLayer>   subSoilLayers        = new ArrayList<>();

    /** Stone type used by MountainTerrainProvider.useStone() for this biome. */
    private BlockState localStone = Blocks.STONE.defaultBlockState();

    // ── Constructor ───────────────────────────────────────────────────────

    private GotBiomeSurfaceConfig() {}

    public static GotBiomeSurfaceConfig create() { return new GotBiomeSurfaceConfig(); }

    // ── Fluent setters ────────────────────────────────────────────────────

    public GotBiomeSurfaceConfig setSurfaceNoiseMixer(GotSurfaceNoiseMixer mixer) {
        this.surfaceNoiseMixer = mixer; return this;
    }

    public GotBiomeSurfaceConfig setSurfaceNoisePaths(boolean enabled) {
        this.hasSurfaceNoisePaths = enabled; return this;
    }

    public GotBiomeSurfaceConfig setUnderwaterNoiseMixer(GotUnderwaterNoiseMixer mixer) {
        this.underwaterNoiseMixer = mixer; return this;
    }

    public GotBiomeSurfaceConfig setMountainTerrain(GotMountainTerrainProvider provider) {
        this.mountainTerrain = provider; return this;
    }

    /**
     * Sets the biome's local stone type — used by
     * {@link GotMountainTerrainProvider.Layer.Builder#useStone()}.
     * Defaults to {@link Blocks#STONE}.
     */
    public GotBiomeSurfaceConfig setLocalStone(BlockState stone) {
        this.localStone = stone; return this;
    }

    /**
     * Adds a sub-soil layer at a fixed depth below the surface.
     * Layers are applied in the order they are added.
     */
    public GotBiomeSurfaceConfig addSubSoilLayer(BlockState material, int depth) {
        return addSubSoilLayer(material, depth, depth);
    }

    /**
     * Adds a sub-soil layer at a randomised depth in [minDepth, maxDepth].
     */
    public GotBiomeSurfaceConfig addSubSoilLayer(BlockState material, int minDepth, int maxDepth) {
        subSoilLayers.add(new SubSoilLayer(material, minDepth, maxDepth));
        return this;
    }

    // ── Accessors ─────────────────────────────────────────────────────────

    public GotSurfaceNoiseMixer       getSurfaceNoiseMixer()   { return surfaceNoiseMixer; }
    public boolean                    hasSurfaceNoisePaths()   { return hasSurfaceNoisePaths; }
    public GotUnderwaterNoiseMixer    getUnderwaterNoiseMixer(){ return underwaterNoiseMixer; }
    public GotMountainTerrainProvider getMountainTerrain()     { return mountainTerrain; }
    public List<SubSoilLayer>         getSubSoilLayers()       { return subSoilLayers; }
    public BlockState                 getLocalStone()          { return localStone; }

    public boolean hasMountainTerrain()   { return !mountainTerrain.isNone(); }
    public boolean hasSurfaceNoiseMixer() { return !surfaceNoiseMixer.isNone(); }
    public boolean hasUnderwaterMixer()   { return underwaterNoiseMixer != GotUnderwaterNoiseMixer.NONE; }

    // ── Application helpers ───────────────────────────────────────────────

    /**
     * Applies the surface noise mixer and optional noise paths to a single block
     * position.  The mountain terrain is applied separately (see
     * {@link #applyMountainTerrain}).
     *
     * @param x    world block X
     * @param z    world block Z
     * @param in   current block state at this position
     * @param top  true if this is the topmost solid block in the column
     * @param rand random source
     * @return replacement block (may be {@code in} unchanged)
     */
    public BlockState applySurfaceNoise(int x, int z, BlockState in, boolean top, RandomSource rand) {
        BlockState out = surfaceNoiseMixer.getReplacement(x, z, in, top, rand);
        if (top && hasSurfaceNoisePaths) {
            out = GotSurfaceNoisePaths.getReplacement(x, z, out, true, rand);
        }
        return out;
    }

    /**
     * Applies the mountain terrain provider to a single block position.
     *
     * @param x              world block X
     * @param z              world block Z
     * @param y              world block Y
     * @param in             current block state
     * @param top            true if this is the topmost solid block
     * @param stoneNoiseDepth per-column jitter from
     *                       {@link GotMountainTerrainProvider#computeStoneNoiseDepth}
     * @return replacement block (may be {@code in} unchanged)
     */
    public BlockState applyMountainTerrain(int x, int z, int y,
                                           BlockState in, boolean top,
                                           int stoneNoiseDepth) {
        return mountainTerrain.getReplacement(x, z, y, in, localStone, top, stoneNoiseDepth);
    }

    /**
     * Applies the underwater noise mixer to a submerged floor block.
     */
    public BlockState applyUnderwaterNoise(int x, int z, BlockState in, RandomSource rand) {
        return underwaterNoiseMixer.getReplacement(x, z, in, rand);
    }
}