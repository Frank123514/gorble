package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MapTeleportPayload(int x, int z) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "map_teleport");

    public static final Type<MapTeleportPayload> TYPE =
            new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, MapTeleportPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeInt(pkt.x);
                        buf.writeInt(pkt.z);
                    },
                    buf -> new MapTeleportPayload(
                            buf.readInt(),
                            buf.readInt()
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
