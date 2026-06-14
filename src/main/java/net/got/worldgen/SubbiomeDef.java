package net.got.worldgen;

/**
 * Definition of one subbiome that can randomly appear inside a parent biome.
 *
 * <p>Instances are created by {@link SubbiomeResolver#load} when reading
 * {@code data/got/worldgen/subbiomes/subbiomes.json}.
 *
 * @param subbiomeId      Namespaced biome registry ID that replaces the parent
 *                        at positions where the noise field exceeds the threshold
 *                        (e.g. {@code "got:lake"}).
 * @param noiseScale      World-space scale of the noise field in blocks.
 *                        Larger values = bigger, more spread-out patches.
 *                        Typical range: 100 (small clearings) – 400 (vast regions).
 * @param threshold       Normalised noise threshold in [0, 1].
 *                        Fraction of parent covered ≈ {@code (1 - threshold)}.
 *                        Guideline: 0.55 ≈ 22%, 0.65 ≈ 12%, 0.75 ≈ 5%, 0.85 ≈ 1%.
 * @param priority        Tie-breaking order when several subbiomes share the same
 *                        parent. Higher = checked first.
 * @param noiseOffsetX    X offset applied to noise input (derived from subbiome ID
 *                        hash at load time — never set manually).
 * @param noiseOffsetZ    Z offset applied to noise input (same reasoning).
 * @param baseHeight      If >= 0, overrides the terrain base height for this
 *                        subbiome patch (e.g. 51 for a lake depression).
 *                        Set to -1 to inherit from the parent biome.
 * @param heightVariation If >= 0, overrides the terrain height variation for this
 *                        subbiome patch (e.g. 2 for a flat lake floor).
 *                        Set to -1 to inherit from the parent biome.
 * @param blendRadius     Controls the width of the terrain transition at patch
 *                        edges, expressed as noise-space margin × 1000.
 *                        E.g. {@code 70} → margin of 0.07 in [0,1] noise space.
 *                        Larger = wider, gentler slope. Typical range: 50–120.
 *                        Has no effect when there is no terrain override.
 */
public record SubbiomeDef(
        String subbiomeId,
        double noiseScale,
        double threshold,
        int    priority,
        double noiseOffsetX,
        double noiseOffsetZ,
        float  baseHeight,
        float  heightVariation,
        float  blendRadius
) {
    /** Returns true when this subbiome overrides the terrain shape. */
    public boolean hasTerrainOverride() {
        return baseHeight >= 0 || heightVariation >= 0;
    }
}
