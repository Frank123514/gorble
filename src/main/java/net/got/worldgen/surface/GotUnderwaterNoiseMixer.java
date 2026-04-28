package net.got.worldgen.surface;

import net.got.worldgen.BiomemapLoader;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Controls what block is placed on the submerged ocean/river floor —
 * a port of LOTR Renewed's {@code UnderwaterNoiseMixer} enum.
 *
 * <h3>Modes</h3>
 * <dl>
 *   <dt>{@link #NONE}</dt>
 *   <dd>No replacement; the vanilla surface builder's underwater material
 *       ({@code gravel}, {@code sand}, etc.) is kept as-is.</dd>
 *   <dt>{@link #SEA_LATITUDE}</dt>
 *   <dd>Blends between gravel and sand based on the column's north-south
 *       position in the biomemap — southern waters become increasingly sandy,
 *       matching the warm/cold latitude gradient.  The transition zone uses
 *       channel-1 noise to give organic, non-linear coverage edges.</dd>
 * </dl>
 *
 * <h3>Usage in a surface config</h3>
 * <pre>{@code
 * config.setUnderwaterMixer(GotUnderwaterNoiseMixer.SEA_LATITUDE);
 * }</pre>
 */
public enum GotUnderwaterNoiseMixer {

    // ── Enum constants ────────────────────────────────────────────────────

    /** Pass-through — no underwater block replacement. */
    NONE {
        @Override
        public BlockState getReplacement(int x, int z, BlockState in, RandomSource rand) {
            return in;
        }
    },

    /**
     * Replaces the underwater floor with sand in warmer (southern) latitudes,
     * gravel in colder (northern) ones, with a noise-driven blend zone.
     *
     * <p>Latitude is derived from the column's normalised Z position on the
     * biomemap (0 = northernmost row, 1 = southernmost row).  Coverage of sand
     * ramps linearly from 0 at the top of the map to full coverage at the bottom.
     * A noise check breaks the transition into organic patches rather than a
     * hard horizontal line.
     */
    SEA_LATITUDE {
        private static final double NOISE_SCALE_COARSE = 0.10;
        private static final double NOISE_SCALE_FINE   = 0.03;

        @Override
        public BlockState getReplacement(int x, int z, BlockState in, RandomSource rand) {
            if (!BiomemapLoader.isLoaded()) return in;

            // Normalise Z to [0, 1]: 0 = north edge, 1 = south edge.
            float latitudeF = (float) z / (BiomemapLoader.MAP_SCALE * BiomemapLoader.getHeight());
            latitudeF = Math.max(0f, Math.min(1f, latitudeF + 0.5f));  // offset to centre on map

            // No sand in the far north; full sand in the far south.
            if (latitudeF <= 0f) return in;
            if (latitudeF >= 1f) return Blocks.SAND.defaultBlockState();

            // In the transition band, use noise to create organic edges.
            double noiseAvg = GotSurfaceNoiseBank.getChannel(
                    1, x, z, new double[]{ NOISE_SCALE_COARSE, NOISE_SCALE_FINE });
            // Remap noise from [-1,1] to [0,1].
            double noiseNorm = (noiseAvg + 1.0) / 2.0;

            boolean sandy = noiseNorm < latitudeF;
            return sandy ? Blocks.SAND.defaultBlockState() : in;
        }
    };

    // ── Abstract contract ─────────────────────────────────────────────────

    /**
     * Returns the block to place on the submerged floor at (x, z).
     *
     * @param x    world block X
     * @param z    world block Z
     * @param in   the current underwater floor block (from vanilla surface builder)
     * @param rand random source (available if stochastic replacement is needed)
     * @return replacement block, or {@code in} if no change
     */
    public abstract BlockState getReplacement(int x, int z, BlockState in, RandomSource rand);
}