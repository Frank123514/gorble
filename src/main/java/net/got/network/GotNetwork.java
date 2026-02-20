package net.got.network;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.Set;

public final class GotNetwork {

    public static void register(RegisterPayloadHandlersEvent event) {

        event.registrar("got")
                .playToServer(
                        MapTeleportPayload.TYPE,
                        MapTeleportPayload.STREAM_CODEC,
                        (payload, ctx) -> ctx.enqueueWork(() -> {

                            ServerPlayer player = (ServerPlayer) ctx.player();
                            if (player == null) return;

                            // OP-only teleport
                            if (!player.hasPermissions(2)) return;

                            ServerLevel level = player.serverLevel();

                            int x = payload.x();
                            int z = payload.z();

// 🔑 FORCE chunk generation / loading
                            level.getChunk(x >> 4, z >> 4);

// 🔑 Resolve correct surface Y
                            int y = level.getHeight(
                                    Heightmap.Types.WORLD_SURFACE,
                                    x,
                                    z
                            );

// Safety clamp (1.21+ API)
                            if (y < level.getMinY()) {
                                y = level.getMinY();
                            }

                            player.teleportTo(
                                    level,
                                    x + 0.5,
                                    y + 1, // always above surface
                                    z + 0.5,
                                    Set.of(),
                                    player.getYRot(),
                                    player.getXRot(),
                                    false
                            );
                        }
    )
                );
    }

    public static void init() {}
}
