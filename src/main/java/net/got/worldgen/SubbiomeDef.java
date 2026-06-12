package net.got.worldgen;

/**
 * Definition of one subbiome that can randomly appear inside a parent biome.
 *
 * <p>Instances are created by {@link SubbiomeResolver#load} when reading
 * {@code data/got/worldgen/subbiomes/subbiomes.json}.
 *
 * @param subbiomeId   Namespaced biome registry ID that replaces the parent
 *                     at positions where the noise field exceeds the threshold
 *                     (e.g. {@code "got:maple_forest"}).
 * @param noiseScale   World-space scale of the noise field in blocks.
 *                     Larger values = bigger, more spread-out patches.
 *                     Typical range: 100 (small clearings) – 400 (vast forests).
 * @param threshold    Normalised noise threshold in [0, 1].
 *                     The fraction of parent area covered by the subbiome is
 *                     roughly {@code (1 - threshold)}.
 *                     Guideline: 0.55 ≈ 22 % coverage, 0.65 ≈ 12 %,
 *                     0.75 ≈ 5 %, 0.85 ≈ 1 % (rare pockets).
 * @param priority     Tie-breaking order when several subbiomes share the same
 *                     parent.  Higher value = checked first.  Use 0 for most
 *                     entries; raise it if one subbiome should override another.
 * @param noiseOffsetX X offset applied to the noise input so that every
 *                     subbiome has its own independent patch pattern even when
 *                     sharing a parent.  Derived from the subbiome ID hash at
 *                     load time — never set manually.
 * @param noiseOffsetZ Z offset applied to the noise input (same reasoning).
 */
public record SubbiomeDef(
        String subbiomeId,
        double noiseScale,
        double threshold,
        int    priority,
        double noiseOffsetX,
        double noiseOffsetZ
) {}