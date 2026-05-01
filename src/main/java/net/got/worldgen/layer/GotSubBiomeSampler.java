package net.got.worldgen.layer;

import net.got.worldgen.GotSubBiomeSystem;

/** Bridges GotSubBiomeSystem terrain deltas into the chunk generator's depth/scale sampling. */
public final class GotSubBiomeSampler {
    private GotSubBiomeSampler() {}

    public static float effectiveDepth(int biomeId, int blockX, int blockZ) {
        float base = GotBiomeRegistry.getDepth(biomeId);
        String path = GotBiomeRegistry.locationFor(biomeId).getPath();
        return base + GotSubBiomeSystem.getTerrainDelta(path, blockX, blockZ)[0];
    }

    public static float effectiveScale(int biomeId, int blockX, int blockZ) {
        float base = GotBiomeRegistry.getScale(biomeId);
        String path = GotBiomeRegistry.locationFor(biomeId).getPath();
        float delta = GotSubBiomeSystem.getTerrainDelta(path, blockX, blockZ)[1];
        return Math.max(0.01f, base + delta);
    }
}
