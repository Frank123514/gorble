package net.got.worldgen.layer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Lazily-evaluated, properly-memoized 2-D integer grid.
 *
 * <p>Uses a {@link ConcurrentHashMap} instead of a fixed-size ring buffer.
 * The original 256-slot ring buffer caused world gen to hang at 0% because
 * each {@code get(x,z)} recursively fans out through 20+ nested parent layers,
 * flooding the tiny cache before any entry survived long enough to be reused.
 * This map persists entries across the full recursive call tree so each
 * coordinate is computed exactly once.
 */
public final class LayerArea {

    private static final int MAX_CACHE = 1 << 15; // 32 768 entries

    private final BiFunction<Integer, Integer, Integer> sampler;
    private final ConcurrentHashMap<Long, Integer> cache = new ConcurrentHashMap<>(1024);

    public LayerArea(BiFunction<Integer, Integer, Integer> sampler) {
        this.sampler = sampler;
    }

    public int get(int x, int z) {
        long key = ((long) x << 32) | (z & 0xFFFFFFFFL);
        Integer cached = cache.get(key);
        if (cached != null) return cached;

        int val = sampler.apply(x, z);

        // Evict a batch if over cap to keep memory bounded
        if (cache.size() >= MAX_CACHE) {
            int toRemove = MAX_CACHE / 4;
            for (Long k : cache.keySet()) {
                cache.remove(k);
                if (--toRemove <= 0) break;
            }
        }
        cache.put(key, val);
        return val;
    }

    /**
     * Pre-warms the cache for a rectangular region in a single left-to-right,
     * top-to-bottom sweep. Call this before the 13×13 biome sample loop in
     * the chunk generator so the recursive layer chain resolves in a controlled
     * order rather than exploding ad-hoc per-call.
     */
    public void prewarm(int minX, int minZ, int maxX, int maxZ) {
        for (int z = minZ; z <= maxZ; z++) {
            for (int x = minX; x <= maxX; x++) {
                get(x, z);
            }
        }
    }
}
