package net.got.worldgen;

/**
 * Definition of one subbiome that can randomly appear inside a parent biome.
 *
 * <p>Instances are created by {@link SubbiomeResolver#load} when reading
 * {@code data/got/worldgen/subbiomes/subbiomes.json}.
 *
 * @param subbiomeId   Namespaced biome registry ID that replaces the parent.
 * @param noiseScale   World-space scale of the noise field in blocks.
 * @param threshold    Normalised noise threshold in [0, 1]. Subbiome activates
 *                     when noise >= threshold.
 * @param priority     Tie-breaking order; higher = checked first.
 * @param noiseOffsetX X offset for noise independence (derived from ID hash).
 * @param noiseOffsetZ Z offset for noise independence (derived from ID hash).
 */
public record SubbiomeDef(
        String subbiomeId,
        double noiseScale,
        double threshold,
        int    priority,
        double noiseOffsetX,
        double noiseOffsetZ
) {}
