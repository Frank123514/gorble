package net.got.mixin;

import net.got.climate.SeasonManager;
import net.got.climate.WinterWeatherContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    /**
     * Before each chunk tick, decide whether this chunk counts as "rendered"
     * (i.e. within the view distance of at least one player). If we're not in
     * winter the flag is just set to false so the Biome mixin short-circuits
     * cheaply without doing any distance math.
     */
    @Inject(method = "tickChunk", at = @At("HEAD"), remap = false)
    private void gotWinter_preTickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        if (!SeasonManager.getCurrentSeason().isWinter()) {
            WinterWeatherContext.set(false);
            return;
        }
        ServerLevel self = (ServerLevel) (Object) this;
        WinterWeatherContext.set(isChunkInViewDistance(self, chunk.getPos()));
    }

    /**
     * Always clear the context on the way out, even if tickChunk throws,
     * so the flag can never leak into unrelated code.
     */
    @Inject(method = "tickChunk", at = @At("RETURN"), remap = false)
    private void gotWinter_postTickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        WinterWeatherContext.clear();
    }

    // -------------------------------------------------------------------------

    /**
     * Returns true if {@code chunkPos} falls within the server's configured
     * view distance of any currently-online player.
     *
     * <p>Using view distance (rather than simulation distance) matches what the
     * client actually renders, which is the behavior the user wants.
     */
    private static boolean isChunkInViewDistance(ServerLevel level, ChunkPos chunkPos) {
        int viewDist = level.getServer().getPlayerList().getViewDistance();
        for (ServerPlayer player : level.players()) {
            ChunkPos playerChunk = player.chunkPosition();
            if (Math.abs(chunkPos.x - playerChunk.x) <= viewDist
                    && Math.abs(chunkPos.z - playerChunk.z) <= viewDist) {
                return true;
            }
        }
        return false;
    }
}