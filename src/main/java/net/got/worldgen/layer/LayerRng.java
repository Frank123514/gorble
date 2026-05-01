package net.got.worldgen.layer;

/**
 * Deterministic per-cell RNG for the layer system.
 * Pure Java — no Minecraft API dependency.
 */
public final class LayerRng {
    private static final long A = 6364136223846793005L;
    private static final long C = 1442695040888963407L;

    private final long layerSeed;
    private long localSeed;

    public LayerRng(long worldSeed, long salt) {
        this.layerSeed = mixSeed(worldSeed, salt);
    }

    public void initCoord(int x, int z) {
        long s = layerSeed;
        s = mixSeed(s, x);
        s = mixSeed(s, z);
        s = mixSeed(s, x);
        s = mixSeed(s, z);
        localSeed = s;
    }

    public int nextInt(int bound) {
        int r = (int) ((localSeed >> 24) % bound);
        if (r < 0) r += bound;
        localSeed = localSeed * A + C;
        return r;
    }

    public float nextFloat() { return nextInt(1_000_000) / 1_000_000f; }

    private static long mixSeed(long seed, long salt) {
        seed *= seed * A + C;
        seed += salt;
        return seed;
    }
}
