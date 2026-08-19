package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ExecuteSellPayload(int entityId, int offerIndex) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "execute_sell");

    public static final Type<ExecuteSellPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, ExecuteSellPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> { buf.writeInt(pkt.entityId); buf.writeInt(pkt.offerIndex); },
                    buf -> new ExecuteSellPayload(buf.readInt(), buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
