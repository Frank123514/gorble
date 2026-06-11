package net.got.worldgen;

import java.util.OptionalDouble;

/**
 * Terrain-parameter override from a subbiome, with a blend weight so the
 * transition from parent terrain to subbiome terrain is smooth rather than
 * a hard cliff at the noise threshold.
 *
 * <p>{@code blendWeight} is in [0, 1]:
 * <ul>
 *   <li>0 = right at the threshold edge — use 100 % parent terrain values.</li>
 *   <li>1 = deep inside the subbiome — use 100 % subbiome terrain values.</li>
 * </ul>
 * The caller should lerp: {@code lerp(parentValue, subbiomeValue, blendWeight)}.
 *
 * @param baseHeight      Optional absolute world-Y surface target for the subbiome.
 * @param heightVariation Optional noise amplitude (in blocks) for the subbiome.
 * @param blendWeight     Smooth 0-1 weight; 0 at threshold edge, 1 deep inside.
 */
public record SubbiomeTerrainOverride(
        OptionalDouble baseHeight,
        OptionalDouble heightVariation,
        float blendWeight
) {}
