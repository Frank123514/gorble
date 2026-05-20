package net.got.climate;

/**
 * Tracks whether the chunk currently being processed by ServerLevel.tickChunk
 * is within render distance of at least one player.
 *
 * Server-side only. Set and cleared by ServerLevelMixin around each tickChunk
 * call. BiomeMixin reads it on the server to prevent the temperature override
 * from firing on every loaded chunk and instantly blanketing the world in snow.
 *
 * On the client this flag is never set (stays false), but BiomeMixin checks
 * the physical dist instead and skips this gate entirely on the client side.
 */
public final class WinterWeatherContext {

    private static boolean chunkIsRendered = false;

    public static boolean isChunkRendered() { return chunkIsRendered; }
    public static void set(boolean rendered) { chunkIsRendered = rendered; }
    public static void clear()              { chunkIsRendered = false; }

    private WinterWeatherContext() {}
}
