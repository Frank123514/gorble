package net.got.mixin;

import net.got.climate.SeasonCache;
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

    @Inject(method = "tickChunk", at = @At("HEAD"), remap = false)
    private void gotWinter_preTickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        if (!SeasonCache.get().isWinter()) {
            WinterWeatherContext.set(false);
            return;
        }
        ServerLevel self = (ServerLevel) (Object) this;
        WinterWeatherContext.set(isChunkInViewDistance(self, chunk.getPos()));
    }

    @Inject(method = "tickChunk", at = @At("RETURN"), remap = false)
    private void gotWinter_postTickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        WinterWeatherContext.clear();
    }

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
