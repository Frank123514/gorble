package net.got.climate;

/**
 * Tracks whether the chunk currently being processed by ServerLevel.tickChunk
 * is within render distance of at least one player.
 *
 * <p>ServerLevel ticks chunks serially on the server main thread, so a plain
 * static boolean is safe here — no synchronisation required.
 *
 * <p>ServerLevelMixin sets this before each tickChunk call and clears it on
 * return. BiomeMixin reads it to gate the winter temperature override so that
 * only rendered (nearby) chunks get forced to freezing, preventing the world
 * from being instantly blanketed in snow when winter starts.
 */
public final class WinterWeatherContext {

    private WinterWeatherContext() {}

    private static boolean chunkIsRendered = false;

    /** Returns true iff the chunk currently under tickChunk is within a player's view distance. */
    public static boolean isChunkRendered() {
        return chunkIsRendered;
    }

    /** Called by ServerLevelMixin at the head of each tickChunk. */
    public static void set(boolean rendered) {
        chunkIsRendered = rendered;
    }

    /** Called by ServerLevelMixin on tickChunk return to ensure the flag never leaks. */
    public static void clear() {
        chunkIsRendered = false;
    }
}