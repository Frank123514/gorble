package net.got.worldgen;

import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface GotChunk {
    @NotNull CompletableFuture<ChunkAccess> fillFromNoise(
            @NotNull Blender blender,
            @NotNull RandomState randomState,
            @NotNull StructureManager structureManager,
            @NotNull ChunkAccess chunk
    );
}
