package net.francis.got.worldgen;

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
    
    public boolean hasTerrainOverride() {
        return baseHeight >= 0 || heightVariation >= 0;
    }
}
