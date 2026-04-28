package net.got.worldgen.surface;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Generates winding dirt-path trails on terrain surfaces by detecting contour
 * edges in a pair of noise fields — a direct port of LOTR Renewed's
 * {@code SurfaceNoisePaths}.
 *
 * <h3>Algorithm</h3>
 * <p>For the top surface block at each column:
 * <ol>
 *   <li>Evaluate {@code noise1(x, z) + noise2(x, z)} (quantised to int).</li>
 *   <li>Do the same for the 8 Moore-neighbourhood cells around (x, z).</li>
 *   <li>If <em>any</em> neighbour has a different quantised value, the current
 *       block sits on a contour edge → replace it with {@link Blocks#DIRT_PATH}.</li>
 * </ol>
 * The resulting paths are thin, organic lines that snake through the landscape
 * following the zero-crossings of the noise field.  At scale 0.003 one full
 * noise cycle spans ~333 blocks, giving path spacings of roughly 100–200 blocks.
 *
 * <p>This is only applied to the topmost solid block ({@code top == true}) so
 * it does not affect underground filler or sub-soil layers.
 */
public final class GotSurfaceNoisePaths {

    private GotSurfaceNoisePaths() {}

    /** Scale used for both noise channels — matches LOTR's {@code 0.003} constant. */
    private static final double SCALE = 0.003;

    /**
     * Returns {@link Blocks#DIRT_PATH} if (x, z) is a contour edge at the top
     * surface, otherwise returns {@code in} unchanged.
     *
     * @param x    world block X
     * @param z    world block Z
     * @param in   current block state
     * @param top  must be {@code true} for the path to be applied
     * @param rand unused (present for API symmetry with {@link GotSurfaceNoiseMixer})
     */
    public static BlockState getReplacement(int x, int z,
                                            BlockState in, boolean top,
                                            @SuppressWarnings("unused") RandomSource rand) {
        if (!top) return in;

        int central = quantise(x, z);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) continue;
                if (quantise(x + dx, z + dz) != central) {
                    return Blocks.DIRT_PATH.defaultBlockState();
                }
            }
        }
        return in;
    }

    /**
     * Quantises the combined channel-1 + channel-2 noise at (x, z) to an
     * integer so that neighbouring blocks can be compared for contour detection.
     * Multiplying by 2.0 then casting widens each noise band to ~1 block thick
     * before quantisation, matching LOTR's {@code * 2.0D} factor.
     */
    private static int quantise(int x, int z) {
        double n1 = GotSurfaceNoiseBank.getNoise1(x, z, SCALE);
        double n2 = GotSurfaceNoiseBank.getNoise2(x, z, SCALE);
        return (int) ((n1 + n2) * 2.0);
    }
}