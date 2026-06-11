package net.got.worldgen;

import java.util.OptionalDouble;

/**
 * Definition of one subbiome that can randomly appear inside a parent biome.
 *
 * <p>Instances are created by {@link SubbiomeResolver#load} when reading
 * {@code data/got/worldgen/subbiomes/subbiomes.json}.
 *
 * @param subbiomeId      Namespaced biome registry ID that replaces the parent.
 * @param noiseScale      World-space scale of the noise field in blocks.
 * @param threshold       Normalised noise threshold in [0, 1]. Subbiome activates
 *                        when noise >= threshold.
 * @param priority        Tie-breaking order; higher = checked first.
 * @param noiseOffsetX    X offset for noise independence (derived from ID hash).
 * @param noiseOffsetZ    Z offset for noise independence (derived from ID hash).
 * @param baseHeight      Optional terrain base height override (absolute world Y).
 *                        Absent = inherit parent's bicubic-interpolated height.
 * @param heightVariation Optional terrain height-variation override (noise amplitude).
 *                        Absent = inherit parent's bicubic-interpolated variation.
 * @param blendRange      Noise range above the threshold over which terrain values
 *                        are blended from parent → subbiome.  At the threshold the
 *                        parent's terrain is used (weight 0); at
 *                        {@code threshold + blendRange} the subbiome's terrain is
 *                        fully active (weight 1).  Defaults to 0.15, giving a
 *                        smooth ~30-block-wide transition zone at typical scales.
 *                        Set smaller for sharper edges, larger for softer ones.
 */
public record SubbiomeDef(
        String subbiomeId,
        double noiseScale,
        double threshold,
        int    priority,
        double noiseOffsetX,
        double noiseOffsetZ,
        OptionalDouble baseHeight,
        OptionalDouble heightVariation,
        double blendRange
) {
    /** Returns {@code true} if this subbiome overrides either terrain parameter. */
    public boolean hasTerrainOverride() {
        return baseHeight.isPresent() || heightVariation.isPresent();
    }
}
