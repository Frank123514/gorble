package net.got.worldgen.layer;

/**
 * Configuration constants for the layer-based biome generator.
 *
 * <p>Tuned to match LOTR Renewed's biome generation behaviour:
 * <ul>
 *   <li><b>biomeSize 5</b> — same 5-zoom chain as LOTR Renewed; produces biomes
 *       roughly 512–2048 blocks across at world scale.</li>
 *   <li><b>riverSize 3</b> — LOTR uses a shorter river zoom chain which yields
 *       narrow, well-defined channels (1–6 blocks wide after terrain blending),
 *       matching the rivers seen in Middle-earth. The original value of 6 produced
 *       rivers almost as wide as vanilla, which is wrong for this setting.</li>
 * </ul>
 */
public class GotBiomeGenSettings {
    public int getBiomeSize() { return 5; }
    public int getRiverSize() { return 3; }   // was 6 — halved for LOTR-narrow rivers
}
